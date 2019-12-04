package com.yxw.easyhealth.biz.usercenter.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.easyhealth.biz.user.utils.SmsCode;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.vo.FamilyVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.constant.EasyHealthConstant;

@Controller
@RequestMapping("app/usercenter/myFamily/")
public class MyFamilyController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(MyFamilyController.class);
	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	public final static String FORWARD_URL = "/easyhealth/usercenter/myFamily/index";
	private final static String ADD_FAMILY_CODE_TYPE = "addFamily";
	private final static String testCode = "1233123";
	private final static boolean testCodeFlag = true;

	@RequestMapping(value = "/index")
	public ModelAndView familyList(FamilyVo vo, HttpServletRequest request) {
		if (StringUtils.isBlank(vo.getOpenId()) || vo.getOpenId().equals("null")) {
			vo.setOpenId(getOpenId(request));
		}

		ModelAndView view = new ModelAndView("easyhealth/biz/usercenter/familyList");

		// 设置openId
		if (StringUtils.isBlank(vo.getOpenId())) {
			String openId = getAppOpenId(request);
			vo.setOpenId(openId);
		}

		// 默认是5个
		int familyNumbers = SystemConfig.getInteger(FamilyConstants.MAX_FAMILY_NUM_KEY, 2);

		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		view.addObject("familyNumbers", familyNumbers);

		return view;
	}

	@ResponseBody
	@RequestMapping(value = "getFamilies", method = RequestMethod.POST)
	public Object getFamilies(FamilyVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			List<Family> families = familyService.findFamiliesByOpenId(vo.getOpenId());
			map.put(BizConstant.COMMON_ENTITY_LIST_KEY, families);
		} catch (Exception e) {
			logger.error("getAllCards error. errorMsg: {}, cause: {}.", new Object[] { e.getMessage(), e.getCause() });
		}

		return new RespBody(Status.OK, map);
	}

	@RequestMapping(value = "/addMyFamily")
	public ModelAndView addMyFamily(FamilyVo vo, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("easyhealth/biz/usercenter/addMyFamily");

		// 设置openId
		if (StringUtils.isBlank(vo.getOpenId())) {
			String openId = getAppOpenId(request);
			vo.setOpenId(openId);
		}
		view.addObject(BizConstant.COMMON_PARAMS_KEY, vo);
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public Object add(HttpServletRequest request, Family family) {
		if (logger.isDebugEnabled()) {
			logger.debug(JSON.toJSONString(family));
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (!ADD_FAMILY_CODE_TYPE.equals(family.getCodeType())) {
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "无效的验证类型。");
		}

		String mobile = family.getMobile();
		if (family.getOwnership() == FamilyConstants.FAMILY_OWNERSHIP_CHILD) {
			mobile = family.getGuardMobile();
		}

		//		CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class);
		String code = "";
		String cacheKey =
				CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
						.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(family.getCodeType());
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(cacheKey);
		List<Object> results = serveComm.get(CacheType.COMMON_CACHE, "getValueFromCache", parameters);
		if (CollectionUtils.isNotEmpty(results)) {
			code = (String) JSON.parse(String.valueOf(results.get(0)));
		}

		if (logger.isDebugEnabled()) {
			logger.debug("{}.缓存验证码：{}", cacheKey, code);
		}

		if ( ( family.getVerifyCode().equals(code) && StringUtils.isNotBlank(code) )
				|| ( testCode.equals(family.getVerifyCode()) && testCodeFlag )) {
			//			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
			//			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "验证码验证通过。");
			resultMap = familyService.saveFamilyInfo(family);
			if (StringUtils.equals(BizConstant.METHOD_INVOKE_SUCCESS, (String) resultMap.get(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY))) {
				HttpSession session = request.getSession();
				session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL);
			}
		} else {
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "验证码错误，请重新输入。");
		}

		return new RespBody(Status.OK, resultMap);
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @param codeType
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendCode")
	public RespBody sendcode(String mobile, String codeType, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 防止其他验证类型进入
		if (!ADD_FAMILY_CODE_TYPE.equals(codeType)) {
			resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
			resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "无效的验证类型。");
		} else {
			// 手机号码
			if (StringUtils.isBlank(mobile)) {
				resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
				resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "手机号码不能为空。");
			} else {
				//				CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class);
				Integer ttlFromCache;
				String cacheKey =
						CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
								.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(codeType);
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(cacheKey);
				List<Object> results = serveComm.get(CacheType.COMMON_CACHE, "getTtlFromCache", parameters);
				String code = SmsCode.getCode();
				if (CollectionUtils.isNotEmpty(results)) {
					ttlFromCache = Integer.valueOf(String.valueOf(JSON.parse(String.valueOf(results.get(0)))));//去空格后转换

					if (ttlFromCache.intValue() <= 5) {
						List<Object> requestParameters = new ArrayList<Object>();
						requestParameters.add(cacheKey);
						requestParameters.add((Object) code);
						serveComm.set(CacheType.COMMON_CACHE, "setValueFromCache", requestParameters);
						requestParameters.remove((Object) code);
						requestParameters.add(Integer.parseInt(SmsCode.SMS_CODE_FAILURE_TIME));
						serveComm.set(CacheType.COMMON_CACHE, "setExpireFromCache", requestParameters);
						//						commonCache.setValueFromCache(cacheKey, code);
						//						commonCache.setExpireFromCache(cacheKey, Integer.parseInt(SmsCode.SMS_CODE_FAILURE_TIME));
					} else {
						List<Object> requestParameters = new ArrayList<Object>();
						requestParameters.add(cacheKey);
						List<Object> resultValueFrom = serveComm.get(CacheType.COMMON_CACHE, "getValueFromCache", requestParameters);
						if (CollectionUtils.isNotEmpty(resultValueFrom)) {
							code = (String) JSON.parse(String.valueOf(resultValueFrom.get(0)));
						}
						//						code = commonCache.getValueFromCache(cacheKey);
						if (code != null) {
							// 把引号去掉
							code = String.valueOf(JSON.parse(code));
						}
					}

				}

				String content = new String("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");
				System.out.println(content);
				Map<String, Object> smsResultMap = SmsCode.sendCode(mobile, content);
				if (smsResultMap.get(SmsCode.RESULT_CODE).equals("2")) {
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_SUCCESS);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "发送成功");
				} else {
					resultMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, BizConstant.METHOD_INVOKE_FAILURE);
					resultMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, SmsCode.RESULT_MSG);
				}

			}
		}

		return new RespBody(Status.OK, resultMap);
	}

}
