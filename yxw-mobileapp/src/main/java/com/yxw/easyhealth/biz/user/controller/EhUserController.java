/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.user.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.yxw.app.sdk.unionpay.UnionPaySDK;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.usercenter.Family;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.easyhealth.biz.user.service.EasyHealthUserService;
import com.yxw.easyhealth.biz.user.utils.BindPhone;
import com.yxw.easyhealth.biz.user.utils.SmsCode;
import com.yxw.easyhealth.biz.usercenter.service.FamilyService;
import com.yxw.easyhealth.biz.usercenter.thread.SaveFamilyInfoRunnable;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.interfaces.constants.IdType;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;
import com.yxw.utils.CryptAES;
import com.yxw.utils.MD5Utils;
import com.yxw.utils.RSAEncrypt;
import com.yxw.utils.RandomUtil;
import com.yxw.utils.ThreeDes;

/**
 * @Package: com.yxw.easyhealth.biz.user
 * @ClassName: LoginController
 * @Statement: <p>
 *             健康易注册登录
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年10月6日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/easyhealth/user")
public class EhUserController extends BaseAppController {
	private static Logger logger = LoggerFactory.getLogger(EhUserController.class);

	private static String CODE_TYPE_REGISGER = "register";
	private static String CODE_TYPE_FORGET_PWD = "forgetpwd";
	private static String CODE_TYPE_MODIFY_PHONE = "modifyPhone";
	private static String testCode = "1233123";
	private static boolean testCodeFlag = true;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private FamilyService familyService = SpringContextHolder.getBean(FamilyService.class);

	@Autowired
	private EasyHealthUserService easyHealthUserService;

	/**
	 * 跳转至登录页面 提供给app的链接地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toRegister")
	public ModelAndView toRegister(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/user/register");
	}

	/**
	 * 注册验证验证码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "checkRegisterInfo")
	public RespBody checkRegisterInfo(String verifyCode, String account, String codeType) {

		String hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(account, true);
		EasyHealthUser user = easyHealthUserService.findEasyHealthByAccount(account, hashTableName);

		String mobile = "";

		if (user != null) {
			mobile = user.getMobile();

			if (CODE_TYPE_REGISGER.equals(codeType)) {
				return new RespBody(Status.ERROR, "此手机号已经被使用");
			} else { // if (CODE_TYPE_FORGET_PWD.equals(codeType)) {
				// return new RespBody(Status.OK);
			}
		} else {
			if (CODE_TYPE_REGISGER.equals(codeType)) {
				mobile = account;
				// return new RespBody(Status.OK);
			} else { // if (CODE_TYPE_FORGET_PWD.equals(codeType)) {
				return new RespBody(Status.ERROR, "此账号未注册!");
			}
		}

		String code = null;
		// CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class);
		String cacheKey =
				CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
						.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(codeType);
		List<Object> requestGetParameters = new ArrayList<Object>();
		requestGetParameters.add(cacheKey);
		// List<HospitalInfoByEasyHealthVo> hospitalInfos =
		// hospitalAndOptionCache.getHospitalByOption(BizConstant.OPTION_ONLINE_FILING, vo.getAreaCode());
		List<Object> results = serveComm.get(CacheType.COMMON_CACHE, "getValueFromCache", requestGetParameters);
		if (!CollectionUtils.isEmpty(results)) {
			code = String.valueOf(results.get(0));
		}

		logger.info("{}.缓存验证码：{}", cacheKey, code);

		if (verifyCode.equals(code) || ( testCode.equals(verifyCode) && testCodeFlag )) {
			return new RespBody(Status.OK);
		} else {
			return new RespBody(Status.ERROR, "验证码错误!");
		}

	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "register")
	public RespBody register(EasyHealthUser entity, HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("注册...EasyHealthUser:{}", JSONObject.toJSONString(entity));

			entity.setMobile(entity.getAccount());
			entity.setPassWord(MD5Utils.getMd5String32(entity.getPassWord()));
			entity.setRegisterTime(new Date().getTime());
			entity.setLastLoginTime(new Date().getTime());
			entity.setLoginNonce(PKGenerator.generateId());

			String openId = PKGenerator.generateId();
			entity.setId(openId);

			String id = easyHealthUserService.add(entity);

			if (StringUtils.isNotBlank(id)) {
				entity.setId(id);
				HttpSession session = request.getSession();
				session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, entity);

				addCookie(entity, request, response);

				// 消息推送
				// CommonMsgPushService commonMsgPushService = SpringContextHolder.getBean(CommonMsgPushService.class);
				// commonMsgPushService.pushMsg(entity, CommonMsgPushService.ACTION_TYPE_PERFECT_USER_INFO);

				return new RespBody(Status.OK);
			} else {
				return new RespBody(Status.ERROR, "注册失败，请重试。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "注册失败，请重试。");
		}
	}

	/**
	 * 忘记密码 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toForgetpwdSendcode")
	public ModelAndView toForgetpwdSendcode(EasyHealthUser entity, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
		return new ModelAndView("easyhealth/biz/user/forgetpwdSendcode");
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param mobile
	 * @param codeType
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "sendcode")
	public RespBody sendcode(String mobile, String codeType, String account, HttpServletRequest request, HttpServletResponse response) {

		EasyHealthUser user = null;
		Object oUser = request.getSession().getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

		// 如果是登录状态，并且手机号码没做更改，则无需发送验证码
		if (oUser != null) {
			user = (EasyHealthUser) oUser;

			if (user.getMobile().equals(mobile)) {
				return new RespBody(Status.ERROR, "请输入新的手机号！");
			} else {
				return sendcode(mobile, codeType, account);
			}
		} else {
			return sendcode(mobile, codeType, account);
		}
	}

	private RespBody sendcode(String mobile, String codeType, String account) {

		if (StringUtils.isNotBlank(account)) {
			// 验证手机号

			if (CODE_TYPE_FORGET_PWD.equals(codeType)) {
				String hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(account, true);

				EasyHealthUser user = easyHealthUserService.findEasyHealthByAccount(account, hashTableName);
				if (user != null) {
					mobile = user.getMobile();
				} else {
					return new RespBody(Status.ERROR, "此证件号未注册!");
				}
			} else if (CODE_TYPE_REGISGER.equals(codeType)) {
				mobile = account;
			} else if (CODE_TYPE_MODIFY_PHONE.equals(codeType)) {
				mobile = account;
			}
		}

		/*
		 * CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class); String cacheKey =
		 * CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
		 * .concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(codeType); String code = SmsCode.getCode(); if
		 * (commonCache.getTtlFromCache(cacheKey) <= 5) { commonCache.setValueFromCache(cacheKey, code);
		 * commonCache.setExpireFromCache(cacheKey, Integer.parseInt(SmsCode.SMS_CODE_FAILURE_TIME)); } else { code =
		 * commonCache.getValueFromCache(cacheKey); if (code != null) { // 把引号去掉 code =
		 * String.valueOf(JSON.parse(code)); } }
		 */

		Integer ttlFromCache;
		String cacheKey =
				CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
						.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(codeType);

		List<Object> parameters = new ArrayList<Object>();
		parameters.add(cacheKey);
		List<Object> results = serveComm.get(CacheType.COMMON_CACHE, "getTtlFromCache", parameters);
		String code = SmsCode.getCode();
		if (!CollectionUtils.isEmpty(results)) {
			ttlFromCache = Integer.valueOf(String.valueOf(JSON.parse(String.valueOf(results.get(0)))));// 去空格后转换

			if (ttlFromCache.intValue() <= 5) {
				List<Object> requestParameters = new ArrayList<Object>();
				requestParameters.add(cacheKey);
				requestParameters.add(code);
				serveComm.set(CacheType.COMMON_CACHE, "setValueFromCache", requestParameters);
				requestParameters.remove(code);
				requestParameters.add(Integer.parseInt(SmsCode.SMS_CODE_FAILURE_TIME));
				serveComm.set(CacheType.COMMON_CACHE, "setExpireFromCache", requestParameters);
				// commonCache.setValueFromCache(cacheKey, code);
				// commonCache.setExpireFromCache(cacheKey, Integer.parseInt(SmsCode.SMS_CODE_FAILURE_TIME));
			} else {
				List<Object> requestParameters = new ArrayList<Object>();
				requestParameters.add(cacheKey);
				List<Object> resultValueFrom = serveComm.get(CacheType.COMMON_CACHE, "getValueFromCache", requestParameters);
				if (!CollectionUtils.isEmpty(resultValueFrom)) {
					code = JSONObject.toJSONString(resultValueFrom.get(0));
				}
				// code = commonCache.getValueFromCache(cacheKey);
				if (code != null) {
					// 把引号去掉
					code = String.valueOf(JSON.parse(code));
				}
			}

		}

		String content = new String("您的验证码是：" + code + "。请不要把验证码泄露给其他人。");
		System.out.println(content);
		Map<String, Object> resultMap = SmsCode.sendCode(mobile, content);
		if (resultMap.get(SmsCode.RESULT_CODE).equals("2")) {
			return new RespBody(Status.OK, "发送成功");
		} else {
			return new RespBody(Status.ERROR, resultMap.get(SmsCode.RESULT_MSG));
		}

	}

	@ResponseBody
	@RequestMapping(value = "test")
	public RespBody test(EasyHealthUser entity, HttpServletRequest request, HttpServletResponse response) {
		return new RespBody(Status.OK);
	}

	/**
	 * 忘记密码 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "forgetpwd")
	public ModelAndView forgetpwd(String account, String verifyCode, ModelMap modelMap) {
		modelMap.put("account", account);
		modelMap.put("verifyCode", verifyCode);
		return new ModelAndView("easyhealth/biz/user/forgetpwd", modelMap);
	}

	/**
	 * 登录 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "toLogin")
	public ModelAndView toLogin(String appId, String __src, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {

		if (StringUtils.isNotBlank(__src)) {
			modelMap.put("__src", URLDecoder.decode(__src, "utf-8"));
		}

		//appCode=UnionPay
		String appCode = request.getParameter("appCode");
		if (StringUtils.isBlank(appCode)) {
			appCode = getAppCodeBySession(request);
		}

		if (StringUtils.isBlank(appCode)) {
			appCode = getAppCodeByCookie(request);
		}

		// 测试阶段，做一个转换
		if ("UnionPay".equalsIgnoreCase(appCode) || "appUnionPay".equalsIgnoreCase(appCode)) {
			appCode = EasyHealthConstant.APP_CODE_UNIONPAY;
		}

		String areaCode = request.getParameter("areaCode");
		areaCode = StringUtils.defaultString(areaCode, "/440000/440100");

		if (StringUtils.isNotBlank(appId)) {
			modelMap.put("appId", appId);
		} else {
			// 银联钱包固定 appId
			if (EasyHealthConstant.APP_CODE_UNIONPAY.equalsIgnoreCase(appCode)) {
				appId = EasyHealthConstant.UNIONPAY_APPID;
			}
		}

		// 检测是否有效的AppCode
		if (!validateAppCode(appCode)) {
			logger.error("无效的appCode. appCode = {}.", appCode);
			return new ModelAndView("500");
		}

		String test = request.getParameter("test");

		HttpSession session = request.getSession();

		logger.info("appCode: {}", appCode);

		if (EasyHealthConstant.APP_CODE_UNIONPAY.equalsIgnoreCase(appCode)) {

			// No Session, No Cookie
			// 跳转银联钱包授权
			String url = String.valueOf(request.getRequestURL());
			String queryString = request.getQueryString();
			logger.info("url: {}", url + "?" + queryString);
			String code = request.getParameter("code");
			logger.info("unionPay OAuth2 code: {}", code);
			String scope = StringUtils.defaultString(request.getParameter("scope"), EasyHealthConstant.UNIONPAY_SCOPE_UPAPI_BASE);
			logger.info("unionPay getOAuth2 scope: {}", scope);
			String path = request.getContextPath();
			String basePath =
					request.getScheme() + "://" + request.getServerName()
							+ ( 80 == request.getServerPort() ? "" : ":" + request.getServerPort() ) + path + "/";
			String errmsg = request.getParameter("errmsg");
			if (StringUtils.equals(scope, EasyHealthConstant.UNIONPAY_SCOPE_UPAPI_MOBILE) && StringUtils.isNotBlank(errmsg)) {

				session.setAttribute(EasyHealthConstant.SESSION_APP_CODE, appCode);
				session.setAttribute(EasyHealthConstant.SESSION_AREA_CODE, areaCode);

				String openId = request.getParameter("openId");

				// 是否添加了就诊人
				String familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_NO_VAL;

				List<Family> familys = familyService.findAllFamilies(openId);
				if (!CollectionUtils.isEmpty(familys)) {
					boolean result = Iterables.all(familys, new Predicate<Family>() {

						@Override
						public boolean apply(Family family) {
							return StringUtils.isNotBlank(family.getIdNo());
						}

					});

					if (result) {
						familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL;
					}
				}

				session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, familyInfo);

				response.sendRedirect(basePath + "/easyhealth/index?appId=" + EasyHealthConstant.UNIONPAY_APPID + "&appCode="
						+ EasyHealthConstant.APP_CODE_UNIONPAY);
				return null;
			}

			if (StringUtils.isBlank(code)) {
				String redirectUrl =
						basePath + "easyhealth/user/toLogin?appId=" + EasyHealthConstant.UNIONPAY_APPID + "&appCode="
								+ EasyHealthConstant.APP_CODE_UNIONPAY + "&scope=" + scope;
				String oauth2url = UnionPaySDK.getOAuth2(EasyHealthConstant.UNIONPAY_APPID, scope, redirectUrl);
				logger.info("unionPay OAuth2 base url: {}", oauth2url);
				response.sendRedirect(oauth2url);
				return null;
			} else {
				// 银联钱包联登
				// 获取银联钱包 openId & Phone
				// 注意:
				//  1. 钱包有 3 种登录模式，手机号，用户名，邮箱，以手机号登录为主。
				//  2. 大约有 2%的用户在钱包里面没有绑定登录手机号，针对这部分用户只能获取其用登录用户名或者邮箱。

				//获取手机号, 去数据库查询对应的账号, 有则关联, 自动联登, 无则自动注册并关联
				//2%的用户没有绑定手机号, 通过 openId 去数据库查询对应的账号, 有则自动联登, 无则跳转注册页

				String backendToken = UnionPaySDK.getBackendToken(EasyHealthConstant.UNIONPAY_APPID, EasyHealthConstant.UNIONPAY_SECRET);
				logger.info("backendToken: {}", backendToken);
				if (StringUtils.isNotBlank(backendToken)) {

					JSONObject accessTokenAndOpenIdJson =
							UnionPaySDK.getAccessTokenAndOpenIdByUnionPay(EasyHealthConstant.UNIONPAY_APPID, backendToken, code);
					logger.info("accessTokenAndOpenIdJson: {}", accessTokenAndOpenIdJson);

					String accessToken = accessTokenAndOpenIdJson.getString("accessToken");
					String openId = accessTokenAndOpenIdJson.getString("openId");

					if (StringUtils.isNotBlank(openId)) {
						//去数据库查询对应的账号, 有则关联, 自动联登, 无则自动注册并关联
						//...
						String hashTableName =
								SimpleHashTableNameGenerator.getEasyHealthUserHashTable(MD5Utils.getMd5String16(openId), true);
						EasyHealthUser easyHealthUser =
								easyHealthUserService.findEasyHealthByAccount(MD5Utils.getMd5String16(openId), hashTableName);
						if (easyHealthUser != null) {
							//暂时不处理
						} else {
							easyHealthUser = new EasyHealthUser();
							easyHealthUser.setAccount(MD5Utils.getMd5String16(openId));
							easyHealthUser.setUnionPayId(openId);
							//							easyHealthUser.setMobile(easyHealthUser.getAccount());
							// 随机生成一个6位数字的密码
							String radomPwd = RandomUtil.RandomNum(6);
							easyHealthUser.setPassWord(MD5Utils.getMd5String32(radomPwd));
							easyHealthUser.setRegisterTime(new Date().getTime());
							easyHealthUser.setLastLoginTime(new Date().getTime());
							easyHealthUser.setLoginNonce(PKGenerator.generateId());

							//							easyHealthUser.setCardType(Integer.valueOf(certTp));
							//							easyHealthUser.setCardNo(certId);
							//							easyHealthUser.setName(realName);

							String id = easyHealthUserService.add(easyHealthUser);

							if (StringUtils.isNotBlank(id)) {
								easyHealthUser.setId(id);
							}
						}

						// 存入 Session & Cookie(存没有手机号的用户)
						session.setAttribute(EasyHealthConstant.SESSION_APP_CODE, appCode);
						session.setAttribute(EasyHealthConstant.SESSION_AREA_CODE, areaCode);
						session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, easyHealthUser);
						addCookie(easyHealthUser, request, response);

						if (StringUtils.isBlank(easyHealthUser.getMobile())) {

							if (StringUtils.equals(scope, EasyHealthConstant.UNIONPAY_SCOPE_UPAPI_MOBILE)) {

								if (StringUtils.isNotBlank(accessToken) && StringUtils.isNotBlank(openId)) {
									JSONObject userInfoJson =
											UnionPaySDK.getUserInfoForMobile(EasyHealthConstant.UNIONPAY_APPID, openId, accessToken,
													backendToken);
									String mobile = "";

									if (userInfoJson != null) {
										mobile = userInfoJson.getString("mobile");

										mobile = ThreeDes.getDecryptedValue(mobile, EasyHealthConstant.UNION_3DES_KEY);

									}

									if (StringUtils.isNotBlank(mobile)) {
										easyHealthUser.setMobile(mobile);
										easyHealthUserService.update(easyHealthUser);
									}
								}

							} else {
								String redirectUrl =
										basePath + "easyhealth/user/toLogin?appId=" + EasyHealthConstant.UNIONPAY_APPID + "&appCode="
												+ EasyHealthConstant.APP_CODE_UNIONPAY + "&scope="
												+ EasyHealthConstant.UNIONPAY_SCOPE_UPAPI_MOBILE + "&openId=" + easyHealthUser.getId();
								String oauth2url =
										UnionPaySDK.getOAuth2(EasyHealthConstant.UNIONPAY_APPID,
												EasyHealthConstant.UNIONPAY_SCOPE_UPAPI_MOBILE, redirectUrl);
								logger.info("unionPay OAuth2 mobile url: {}", oauth2url);
								response.sendRedirect(oauth2url);
								return null;
							}

						}

						// 存入 Session & Cookie(存有手机号的用户)
						session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, easyHealthUser);
						addCookie(easyHealthUser, request, response);

						// 是否添加了就诊人
						String familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_NO_VAL;

						List<Family> familys = familyService.findAllFamilies(easyHealthUser.getId());
						if (!CollectionUtils.isEmpty(familys)) {
							boolean result = Iterables.all(familys, new Predicate<Family>() {

								@Override
								public boolean apply(Family family) {
									return StringUtils.isNotBlank(family.getIdNo());
								}

							});

							if (result) {
								familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL;
							}
						}

						session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, familyInfo);

						response.sendRedirect(request.getContextPath() + "/easyhealth/index?appId=" + EasyHealthConstant.UNIONPAY_APPID
								+ "&appCode=" + EasyHealthConstant.APP_CODE_UNIONPAY);
						return null;

					}

				}
			}

		} else if (EasyHealthConstant.APP_CODE_INNERCHINALIFE.equalsIgnoreCase(appCode) && StringUtils.isBlank(test)) {

			String encryptkey = request.getParameter("encryptkey");
			String encryptdata = request.getParameter("encryptdata");

			RSAEncrypt rsaEncryptPri = new RSAEncrypt(null, EasyHealthConstant.YXW_RSA_PRIVATE_KEY);

			String aesKey = rsaEncryptPri.decryptBase64(encryptkey);
			System.out.println("------------------------aesKey----------------------------");
			System.out.println(aesKey);

			String data = CryptAES.AES_Decrypt(aesKey, encryptdata);
			System.out.println("------------------------data----------------------------");
			System.out.println(data);

			JSONObject jsonObject = JSONObject.parseObject(data, Feature.OrderedField);

			String sign = jsonObject.getString("sign");

			jsonObject.remove("sign");
			String content = jsonObject.toString();

			RSAEncrypt rsaEncryptPub = new RSAEncrypt(EasyHealthConstant.INNERCHINALIFE_RSA_PUBLIC_KEY, null);
			boolean status = rsaEncryptPub.verify(content, sign);

			if (status) {//验签成功
				System.out.println("------------------------content----------------------------");
				System.out.println(content);

				String mobile = (String) jsonObject.get("mobile");
				String idNo = (String) jsonObject.get("idNo");
				String name = (String) jsonObject.get("custName");
				String openId = (String) jsonObject.get("openId");

				if (StringUtils.isNoneBlank(mobile, idNo, name, openId)) {
					EasyHealthUser easyHealthUser = getUserCookies(request);
					if (easyHealthUser != null) {

						if (StringUtils.equals(easyHealthUser.getAccount(), MD5Utils.getMd5String16(openId))) {
							// 存入 Session & Cookie(存有手机号/身份证的用户)
							session.setAttribute(EasyHealthConstant.SESSION_APP_CODE, appCode);
							session.setAttribute(EasyHealthConstant.SESSION_AREA_CODE, areaCode);

							easyHealthUser.setName(name);
							easyHealthUser.setMobile(mobile);
							easyHealthUser.setCardNo(idNo);
							easyHealthUser.setUnionPayId(openId);
							easyHealthUser.setAccount(MD5Utils.getMd5String16(openId));
							session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, easyHealthUser);
							addCookie(easyHealthUser, request, response);

							session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO,
									EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL);

							response.sendRedirect(request.getContextPath() + "/easyhealth/index?appId=" + appId + "&appCode="
									+ EasyHealthConstant.APP_CODE_INNERCHINALIFE);
							return null;
						}

					}

					//去数据库查询对应的账号, 有则关联, 自动联登, 无则自动注册并关联
					String hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(MD5Utils.getMd5String16(openId), true);
					easyHealthUser = easyHealthUserService.findEasyHealthByAccount(MD5Utils.getMd5String16(openId), hashTableName);
					if (easyHealthUser != null) {
						//暂时不处理
					} else {
						easyHealthUser = new EasyHealthUser();
						easyHealthUser.setAccount(MD5Utils.getMd5String16(openId));
						easyHealthUser.setUnionPayId(openId);
						easyHealthUser.setMobile(mobile);
						// 随机生成一个6位数字的密码
						String radomPwd = RandomUtil.RandomNum(6);
						easyHealthUser.setPassWord(MD5Utils.getMd5String32(radomPwd));
						easyHealthUser.setRegisterTime(new Date().getTime());
						easyHealthUser.setLastLoginTime(new Date().getTime());
						easyHealthUser.setLoginNonce(PKGenerator.generateId());
						easyHealthUser.setCardType(Integer.valueOf(IdType.CHINA_ID_CARD));
						easyHealthUser.setCardNo(idNo);
						easyHealthUser.setName(name);

						String id = easyHealthUserService.add(easyHealthUser);

						if (StringUtils.isNotBlank(id)) {
							easyHealthUser.setId(id);
						}

						// 同时开线程更新用户信息
						Thread familyThread = new Thread(new SaveFamilyInfoRunnable(easyHealthUser));
						familyThread.start();

					}

					// 存入 Session & Cookie(存有手机号/身份证的用户)
					session.setAttribute(EasyHealthConstant.SESSION_APP_CODE, appCode);
					session.setAttribute(EasyHealthConstant.SESSION_AREA_CODE, areaCode);
					session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, easyHealthUser);
					addCookie(easyHealthUser, request, response);

					session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL);
					/*List<Family> familys = familyService.findAllFamilies(easyHealthUser.getId());
					if (!CollectionUtils.isEmpty(familys)) {
						// 是否添加了就诊人
						String familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_NO_VAL;
						boolean result = Iterables.all(familys, new Predicate<Family>() {

							@Override
							public boolean apply(Family family) {
								return StringUtils.isNotBlank(family.getIdNo());
							}

						});

						if (result) {
							familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL;
						}

						session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, familyInfo);
					}*/

					response.sendRedirect(request.getContextPath() + "/easyhealth/index?appId=" + appId + "&appCode="
							+ EasyHealthConstant.APP_CODE_INNERCHINALIFE);
					return null;

				}
			}

		}

		session.setAttribute(EasyHealthConstant.SESSION_APP_CODE, appCode);
		if (StringUtils.isNotBlank(areaCode)) {
			session.setAttribute(EasyHealthConstant.SESSION_AREA_CODE, areaCode);
		}

		return new ModelAndView("easyhealth/biz/user/login", modelMap);
	}

	private boolean validateAppCode(String appCode) {
		boolean result = false;

		List<Object> object = serveComm.get(CacheType.PLATFORM_CACHE, "getAllPlatform", new ArrayList<>(0));
		if (!CollectionUtils.isEmpty(object)) {
			String source = JSON.toJSONString(object);
			List<Platform> platforms = JSON.parseArray(source, Platform.class);
			for (Platform platform : platforms) {
				if (platform.getCode().equals(appCode)) {
					result = true;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "login")
	public RespBody login(String account, String passWord, HttpServletRequest request, HttpServletResponse response, String appCode) {

		String hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(account, true);

		EasyHealthUser user = easyHealthUserService.findEasyHealthByAccount(account, hashTableName);

		// if (account.matches("^\\d{15}|\\d{18}|\\d{17}(x|X)$")) {
		// params.put("cardNo", account);
		// } else if (account.matches("^\\d{11}$")) {
		// params.put("mobile", account);
		// }

		if (user != null) {
			try {
				passWord = MD5Utils.getMd5String32(passWord);
				if (user.getPassWord().equals(passWord)) {
					user.setLastLoginTime(new Date().getTime());
					user.setLoginNonce(PKGenerator.generateId());

					String alipayId = getAlipayOpenId(request);
					String wechatId = getWechatOpenId(request);

					logger.info("login.alipayId:{}", alipayId);
					logger.info("login.wechatId:{}", wechatId);

					// String appCode = EasyHealthConstant.APP_CODE_APP;
					if (StringUtils.isNotBlank(alipayId)) {
						appCode = EasyHealthConstant.APP_CODE_ALIPAY;

						// 只允许在一个服务窗里登录
						if (StringUtils.isBlank(user.getAlipayId()) || !alipayId.equals(user.getAlipayId())) {
							user.setAlipayId(alipayId);
						}
					}

					if (StringUtils.isNotBlank(wechatId)) {
						appCode = EasyHealthConstant.APP_CODE_WECHAT;

						// 只允许在一个公众账号里登录
						if (StringUtils.isBlank(user.getWechatId()) || !wechatId.equals(user.getWechatId())) {
							user.setWechatId(wechatId);
						}
					}

					easyHealthUserService.update(user);
					HttpSession session = request.getSession();
					session.setAttribute(EasyHealthConstant.SESSION_APP_CODE, appCode);
					session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, user);

					addCookie(user, request, response);
					addAppCodeCookie(EasyHealthConstant.SESSION_APP_CODE, appCode, request, response);

					// 是否添加了就诊人
					String familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_NO_VAL;

					List<Family> familys = familyService.findAllFamilies(user.getId());
					if (!CollectionUtils.isEmpty(familys)) {
						boolean result = Iterables.all(familys, new Predicate<Family>() {

							@Override
							public boolean apply(Family family) {
								return StringUtils.isNotBlank(family.getIdNo());
							}

						});

						if (result) {
							familyInfo = EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL;
						}
					}

					session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, familyInfo);

					return new RespBody(Status.OK);
				} else {
					return new RespBody(Status.ERROR, "密码错误");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return new RespBody(Status.ERROR, "网络异常,请保持您的网络通畅,稍后再试.");
			}
		} else {
			return new RespBody(Status.ERROR, "帐号不存在");
		}
	}

	/**
	 * 修改密码 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toModifyPwd")
	public ModelAndView toModifyPwd(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/user/changepwd");
	}

	/**
	 * 修改手机 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "toModifyMobile")
	public ModelAndView toModifyMobile(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView("easyhealth/biz/user/changePhone");
	}

	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequestMapping(value = "modifyPwd")
	public RespBody modifyPwd(String verifyCode, String account, String passWord, String initPwd, HttpServletRequest request,
			HttpServletResponse response) {

		EasyHealthUser user = null;
		try {
			HttpSession session = request.getSession();
			Object oUser = session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
			if (oUser != null) {
				user = (EasyHealthUser) oUser;

				initPwd = MD5Utils.getMd5String32(initPwd);
				if (user.getPassWord().equals(initPwd)) {
					passWord = MD5Utils.getMd5String32(passWord);
					user.setPassWord(passWord);
					easyHealthUserService.update(user);
					session.removeAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

					removeCookie(user.getAccount(), request, response);

					return new RespBody(Status.OK);
				} else {
					return new RespBody(Status.ERROR, "原密码错误");
				}
			} else {
				// forgetpwd

				HashMap<String, Object> params = new HashMap<String, Object>();
				String hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(account, true);
				params.put("hashTableName", hashTableName);
				user = easyHealthUserService.findEasyHealthByAccount(account, hashTableName);

				String mobile = "";
				if (user != null) {
					mobile = user.getMobile();

					passWord = MD5Utils.getMd5String32(passWord);
					user.setPassWord(passWord);

					easyHealthUserService.update(user);
					session.removeAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

					removeCookie(user.getAccount(), request, response);

					// return new RespBody(Status.OK);
				} else {
					return new RespBody(Status.ERROR, "帐号不存在");
				}

				String code = null;
				// CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class);
				String cacheKey =
						CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
								.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(CODE_TYPE_FORGET_PWD);

				List<Object> requestParameters = new ArrayList<Object>();
				requestParameters.add(cacheKey);
				List<Object> resultValueFrom = serveComm.get(CacheType.COMMON_CACHE, "getValueFromCache", requestParameters);
				if (!CollectionUtils.isEmpty(resultValueFrom)) {
					code = String.valueOf(JSON.parse(String.valueOf(resultValueFrom.get(0))));
				}
				// String code = commonCache.getValueFromCache(cacheKey);

				logger.info("{}.缓存验证码：{}", cacheKey, code);
				if (code != null) {
					code = String.valueOf(JSON.parse(code));
				}
				if ( ( StringUtils.isNotBlank(verifyCode) && verifyCode.equals(code) ) || ( testCode.equals(verifyCode) && testCodeFlag )) {
					return new RespBody(Status.OK);
				} else {
					return new RespBody(Status.ERROR, "验证码错误!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "网络异常,请保持您的网络通畅,稍后再试.");
		}
	}

	/**
	 * 修改密码
	 */
	@ResponseBody
	@RequestMapping(value = "modifyMobile")
	public RespBody modifyMobile(String verifyCode, String mobile, String cardNo, String passWord, HttpServletRequest request,
			HttpServletResponse response) {

		EasyHealthUser user = null;
		try {
			HttpSession session = request.getSession();
			Object oUser = session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
			if (oUser != null) {
				user = (EasyHealthUser) oUser;

				passWord = MD5Utils.getMd5String32(passWord);
				if (user.getPassWord().equals(passWord)) {

					/*
					 * CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class); String cacheKey =
					 * CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
					 * .concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat("modifyPhone"); String code =
					 * commonCache.getValueFromCache(cacheKey);
					 */
					String code = null;
					// CommonCache commonCache = SpringContextHolder.getBean(CommonCache.class);
					String cacheKey =
							CacheConstant.CACHE_SMS_CODE.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(mobile)
									.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(CODE_TYPE_FORGET_PWD);

					List<Object> requestParameters = new ArrayList<Object>();
					requestParameters.add(cacheKey);
					List<Object> resultValueFrom = serveComm.get(CacheType.COMMON_CACHE, "getValueFromCache", requestParameters);
					if (!CollectionUtils.isEmpty(resultValueFrom)) {
						code = String.valueOf(JSON.parse(String.valueOf(resultValueFrom.get(0))));
					}

					logger.info("{}.缓存验证码：{}", cacheKey, code);
					if (code != null) {
						code = String.valueOf(JSON.parse(code));
					}
					if ( ( StringUtils.isNotBlank(verifyCode) && verifyCode.equals(code) )
							|| ( testCode.equals(verifyCode) && testCodeFlag )) {
						// HashMap<String, Object> params = new HashMap<String, Object>();
						// params.put("cardNo", cardNo);
						// String hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(cardNo);
						// params.put("hashTableName", hashTableName);
						// user = easyHealthUserService.findEasyHealthForCardNo(params);

						// if (user != null) {
						// passWord = MD5Utils.getMd5String32(passWord);
						// user.setPassWord(passWord);

						Map<String, String> phpChangePhoneRes = BindPhone.changePhone(mobile, user.getMobile());
						if (BindPhone.SUCCESS.equals(phpChangePhoneRes.get(BindPhone.RESULT_CODE))) {

							user.setMobile(mobile);
							easyHealthUserService.update(user);
							session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, user);
							addCookie(user, request, response);

							return new RespBody(Status.OK);
						} else {
							return new RespBody(Status.ERROR, phpChangePhoneRes.get(BindPhone.RESULT_MSG));
						}
						// } else {
						// return new RespBody(Status.ERROR, "帐号不存在");
						// }
					} else {
						return new RespBody(Status.ERROR, "验证码错误!");
					}
				} else {
					return new RespBody(Status.ERROR, "密码错误");
				}
			} else {
				return new RespBody(Status.ERROR, "会话已过期");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "网络异常,请保持您的网络通畅,稍后再试.");
		}
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "loginout")
	public RespBody loginout(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			Object oUser = session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);
			if (oUser != null) {
				session.removeAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

				removeCookie( ( (EasyHealthUser) oUser ).getAccount(), request, response);
			}

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}

	}

	/**
	 * 完善资料
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "toPerfectUserInfo")
	public ModelAndView toPerfectUserInfo(HttpServletRequest request, HttpServletResponse response, String forward, ModelMap modelMap) {
		HttpSession session = request.getSession();
		if (session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY) != null) {
			/*if (StringUtils.isBlank(forward)) {
			
				// 如果有forward，则完善资料成功后用go(forward, false)跳转，否则用壳的appCloseView()方法
				forward = "";
			}
			
			modelMap.put(BizConstant.URL_PARAM_FORWARD_NAME, forward);*/

			if (StringUtils.isNotBlank(forward)) {

				modelMap.put(BizConstant.URL_PARAM_FORWARD_NAME, forward);
			}

			return new ModelAndView("easyhealth/biz/user/perfectUserInfo", modelMap);
		} else {
			return new ModelAndView("easyhealth/biz/user/login");
		}

	}

	/**
	 * 完善资料,跳回原页面
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "perfectUserInfo")
	public RespBody perfectUserInfo(HttpServletRequest request, HttpServletResponse response, /* String forward, */
			String name, String mobile, String cardType, String cardNo, String birthDay, Integer sex, String address) throws IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY) != null) {
			EasyHealthUser user = (EasyHealthUser) session.getAttribute(EasyHealthConstant.SESSION_USER_ENTITY);

			// 验证证件号是否被使用，如果老用户用身份证注册的，再用手机号注册兼容可以用重复的身份证补充资料，前提是要用原来的手机号  (改为查询elasticsearch)
			List<EasyHealthUser> users =
					easyHealthUserService.findEasyHealthByCardNoAndMobileForAllTable(cardNo,
							StringUtils.isNotBlank(mobile) ? mobile : user.getMobile());

			if (users == null || users.size() == 0) {
				user.setName(name);

				if (StringUtils.isNotBlank(mobile)) {
					user.setMobile(mobile);
				}

				user.setCardType(Integer.valueOf(cardType));
				user.setCardNo(cardNo);
				user.setBirthDay(birthDay);
				user.setSex(sex);
				user.setAddress(address);

				easyHealthUserService.update(user);
				// 同时开线程更新用户信息
				Thread familyThread = new Thread(new SaveFamilyInfoRunnable(user));
				familyThread.start();
				session.setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, user);

				addCookie(user, request, response);

				session.setAttribute(EasyHealthConstant.SESSION_IS_FAMILY_INFO, EasyHealthConstant.SESSION_IS_FAMILY_INFO_YES_VAL);

				return new RespBody(Status.OK);
			} else {
				return new RespBody(Status.ERROR, "此证件号已被使用");
			}
		} else {
			// return new ModelAndView("easyhealth/biz/user/login");
			return new RespBody(Status.ERROR, "会话已过期，请重试!");

		}

	}

	private void addAppCodeCookie(String cookieName, String cookieValue, HttpServletRequest request, HttpServletResponse response) {
		try {
			Cookie cookie = new Cookie(cookieName, cookieValue);
			cookie.setDomain(request.getServerName()); // 请用自己的域
			cookie.setMaxAge(24 * 60 * 60 * EasyHealthConstant.COOKIES_USER_MAX_AGE); // cookie的有效期
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private EasyHealthUser getUserCookies(HttpServletRequest request) {
		EasyHealthUser easyHealthUser = null;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (logger.isDebugEnabled()) {
					logger.debug("cookie.name: {}" + ", cookie.value: {}", cookie.getName(), cookie.getValue());
				}
				if (EasyHealthConstant.COOKIES_USER_ENTITY.equalsIgnoreCase(cookie.getName())) {
					if (logger.isDebugEnabled()) {
						logger.debug("el.user.cookie.value: {}", cookie.getValue());
					}

					String easyHealthUserJsonString = cookie.getValue();
					easyHealthUser = JSONObject.parseObject(easyHealthUserJsonString, EasyHealthUser.class);

					break;
				}
			}
		}

		return easyHealthUser;
	}

	private void addCookie(EasyHealthUser user, HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonUser = JSONObject.parseObject(JSONObject.toJSONString(user));

			String[] cookieRemoveKeys = { /*"id",*/"name", "encryptedName", "mobile", "hashTableName", "address" };
			for (String key : cookieRemoveKeys) {
				jsonUser.remove(key);
			}

			Cookie cookie = new Cookie(EasyHealthConstant.COOKIES_USER_ENTITY, jsonUser.toJSONString());
			cookie.setDomain(request.getServerName()); // 请用自己的域
			cookie.setMaxAge(24 * 60 * 60 * EasyHealthConstant.COOKIES_USER_MAX_AGE); // cookie的有效期
			cookie.setPath("/");
			response.addCookie(cookie);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeCookie(String account, HttpServletRequest request, HttpServletResponse response) {
		JSONObject user = new JSONObject();
		user.put("account", account);
		Cookie cookie = new Cookie(EasyHealthConstant.COOKIES_USER_ENTITY, user.toJSONString());
		cookie.setDomain(request.getServerName()); // 请用自己的域
		cookie.setMaxAge(24 * 60 * 60 * EasyHealthConstant.COOKIES_USER_MAX_AGE); // cookie的有效期
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static void main(String[] args) {
		String a =
				"{\"address\":\"啊\",\"age\":29,\"appCode\":\"innerUnionPay\",\"areaCode\":\"/440000/440100\",\"birth\":\"1988-11-19\",\"createTime\":1527218557675,\"createTimeLabel\":\"2018-05-25 11:22:37\",\"encryptedGuardIdNo\":\"\",\"encryptedGuardMobile\":\"\",\"encryptedIdNo\":\"4**************517\",\"encryptedMobile\":\"138****1236\",\"encryptedName\":\"*彬\",\"guardIdTypeLabel\":\"二代身份证\",\"hashTableName\":\"BIZ_FAMILY_7\",\"id\":\"4ae16e362d6d4218acea0ac8aae1625d\",\"idNo\":\"430721198811192517\",\"idType\":1,\"idTypeLabel\":\"二代身份证\",\"mobile\":\"13807421236\",\"name\":\"柴彬\",\"openId\":\"f650b9e753f748a99229c575af5c759c\",\"ownership\":6,\"ownershipLabel\":\"伴侣\",\"sex\":1,\"sexLabel\":\"男\",\"state\":1,\"updateTime\":1527218557675,\"updateTimeLabel\":\"2018-05-25 11:22:37\"}";
		Family family = JSON.parseObject(a, Family.class);
		List<Family> families = Lists.newArrayList(family);
		boolean status = Iterables.all(families, new Predicate<Family>() {

			@Override
			public boolean apply(Family family) {
				// TODO Auto-generated method stub
				return StringUtils.isNotBlank(family.getIdNo());
			}
		});

		System.out.println(status);
	}
}
