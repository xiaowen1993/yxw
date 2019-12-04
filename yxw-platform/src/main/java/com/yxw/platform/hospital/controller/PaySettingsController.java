/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.PaySettings;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.attach.service.AttachService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.PayModeService;
import com.yxw.platform.hospital.service.PaySettingsService;
import com.yxw.platform.hospital.service.PlatformService;

/**
 * 后台支付方式配置信息管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/paySettings")
public class PaySettingsController extends BizBaseController<PaySettings, String> {

	private Logger logger = LoggerFactory.getLogger(PaySettingsController.class);

	// private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PaySettingsService paySettingsService;

	@Autowired
	private PayModeService payModeService;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Autowired
	private AttachService attachService;

	@Override
	protected BaseService<PaySettings, String> getService() {
		return paySettingsService;
	}

	@RequestMapping("/toView")
	public ModelAndView toView(@RequestParam(value = "hospitalId", required = true) String hospitalId, ModelMap modelMap) {
		logger.info("/sys/paySettings/toView.hospitalId: " + hospitalId);

		modelMap.put("hospitalId", hospitalId);

		ModelAndView view = new ModelAndView("/sys/hospital/paySettings");
		return view;
	}

	/**
	 * 加载平台，以及平台对应的支付方式
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年5月26日 
	 * @param hospitalId
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadPlatForm", method = RequestMethod.POST)
	public Object loadPlatForm(@RequestParam(value = "hospitalId", required = true) String hospitalId, HttpServletRequest request) {
		try {
			PlatformService platformService = SpringContextHolder.getBean(PlatformService.class);
			List<PlatformPaymentVo> vos = platformService.findAllPlatformPayModesByHospitalId(hospitalId);
			Map<String, Object> payModesMap = new HashMap<String, Object>(1);
			/*			if (!org.springframework.util.CollectionUtils.isEmpty(vos)) {
							Map<String, String> payModesMap = new HashMap<String, String>(vos.size());
							for (PlatformPaymentVo platformPaymentVo : vos) {

							}
						}*/

			payModesMap.put("platformPayment", vos);
			return new RespBody(Status.OK, payModesMap);
		} catch (Exception e) {
			logger.error("加载平台，以及平台对应的支付方式数据异常：", e);
			return new RespBody(Status.ERROR, "加载平台，以及平台对应的支付方式数据异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/loadPlatformPaySettings", method = RequestMethod.POST)
	public Object loadPlatformPaySettings(@RequestParam(value = "platformSettingsId", required = true) String platformSettingsId,
			@RequestParam(value = "payModeId", required = true) String payModeId, HttpServletRequest request) {
		try {
			PaySettingsService paySettingsService = SpringContextHolder.getBean(PaySettingsService.class);
			PaySettings paySettings = paySettingsService.findByPlatformSettingsIdAndPayModeId(platformSettingsId, payModeId);
			Map<String, Object> paySettingsMap = new HashMap<String, Object>(1);

			paySettingsMap.put("paySettings", paySettings);
			return new RespBody(Status.OK, paySettingsMap);
		} catch (Exception e) {
			logger.error("加载平台的支付方式数据异常：", e);
			return new RespBody(Status.ERROR, "加载平台的支付方式数据异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(@RequestParam(value = "hospitalId", required = true) String hospitalId, String params, HttpServletRequest request) {
		logger.info("/sys/platformSettings/save.hospitalId: " + hospitalId);
		logger.info("/sys/platformSettings/save.params: " + params);
		List<PaySettings> paySettingsList = new ArrayList<PaySettings>();
		try {
			User user = getLoginUser(request);
			//			Hospital hospital = hospitalService.findById(hospitalId);
			//更新缓存时使用 add by yuce
			// List<String> oldAppIdAndAppCodes = hospitalCache.findAppInfoCacheFields(hospitalId);
			//			List<String> oldAppIdAndAppCodes = null;
			List<Object> cacheParams = new ArrayList<Object>();
			/*List<HospIdAndAppSecretVo> hospIdAndAppSecretVos = hospitalService.findAppSecretByHospitalId(hospitalId);
			cacheParams.add(hospIdAndAppSecretVos);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppInfoCacheFields", cacheParams);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				oldAppIdAndAppCodes = JSON.parseArray(source, String.class);
			}*/

			LinkedList<Map<String, String>> platformPaySettingsAdd = new LinkedList<Map<String, String>>();
			LinkedList<Map<String, String>> platformPaySettingsDelete = new LinkedList<Map<String, String>>();
			JSONObject jParams = JSONObject.parseObject(params);
			for (String key : jParams.keySet()) {
				JSONObject jPlatform = jParams.getJSONObject(key);
				//				hospital.setStatus(jPlatform.getIntValue("status"));
				//				jPlatform.remove("status");
				int status = jPlatform.getIntValue("status");
				if (status == 1) {//需要入库
					//增加支付属性转换
					PaySettings paySettings = JSONObject.parseObject(jPlatform.toJSONString(), PaySettings.class);
					Map<String, String> platformPaySettingsMap = new HashMap<String, String>();
					platformPaySettingsMap.put("platformSettingsId", jPlatform.getString("platformSettingsId"));
					platformPaySettingsMap.put("paySettingsId", jPlatform.getString("id"));
					platformPaySettingsMap.put("status", String.valueOf(status));
					platformPaySettingsAdd.add(platformPaySettingsMap);

					Integer flag = jPlatform.getIntValue("flag");

					if (flag.equals(1)) {
						paySettings.setCp(user.getId());
						//paySettingsService.update(paySettings);
					} else {
						paySettings.setEp(user.getId());
						//paySettingsService.add(paySettings);
					}
					paySettingsList.add(paySettings);
				} else if (status == 0) {
					Map<String, String> platformPaySettingsMap = new HashMap<String, String>();
					platformPaySettingsMap.put("platformSettingsId", jPlatform.getString("platformSettingsId"));
					platformPaySettingsMap.put("paySettingsId", jPlatform.getString("id"));
					platformPaySettingsMap.put("status", String.valueOf(status));
					platformPaySettingsDelete.add(platformPaySettingsMap);
				}

			}
			paySettingsService.batchSavePaySettings(paySettingsList, platformPaySettingsAdd, platformPaySettingsDelete);

			List<HospIdAndAppSecretVo> hospIdAndAppSecretVos = hospitalService.findAppSecretByHospitalId(hospitalId);

			List<HospitalCodeAndAppVo> vos = hospitalService.queryCodeAndAppByHospitalId(hospitalId);

			List<HospitalCodeAndAppVo> tempLs = new ArrayList<HospitalCodeAndAppVo>();
			for (HospIdAndAppSecretVo hospIdAndAppSecretVo : hospIdAndAppSecretVos) {
				for (HospitalCodeAndAppVo hospitalCodeAndAppVo : vos) {
					if (StringUtils.equals(hospIdAndAppSecretVo.getAppCode(), hospitalCodeAndAppVo.getAppCode())) {
						tempLs.add(hospitalCodeAndAppVo);
					}
				}
				if (!CollectionUtils.isEmpty(tempLs)) {
					cacheParams.clear();
					cacheParams.add(hospIdAndAppSecretVo.getAppCode());
					cacheParams.add(hospIdAndAppSecretVo.getAppId());
					cacheParams.add(JSON.toJSONString(tempLs));
					serveComm.set(CacheType.HOSPITAL_CACHE, "updateAppPayCacheByHospitalId", cacheParams);
					tempLs.clear();
				}
			}

			return new RespBody(hospitalId, Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存接入平台配置信息失败，数据保存异常！");
		}

	}
}
