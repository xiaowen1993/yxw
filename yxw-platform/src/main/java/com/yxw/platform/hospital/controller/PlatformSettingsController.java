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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.generator.AppIdGenerator;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.PlatformService;
import com.yxw.platform.hospital.service.PlatformSettingsService;

/**
 * 后台接入平台配置信息管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/platformSettings")
public class PlatformSettingsController extends BizBaseController<PlatformSettings, String> {

	private Logger logger = LoggerFactory.getLogger(PlatformSettingsController.class);

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PlatformService platformService;

	@Autowired
	private PlatformSettingsService platformSettingsService;

	//	@Autowired
	//	private HospitalPlatformSettingsService hospitalPlatformSettingsService;

	//	private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);

	@Override
	protected BaseService<PlatformSettings, String> getService() {
		return platformSettingsService;
	}

	@RequestMapping("/toView")
	public ModelAndView toView(@RequestParam(value = "hospitalId", required = true) String hospitalId, ModelMap modelMap) {
		logger.info("/sys/platformSettings/toView.hospitalId: " + hospitalId);

		List<Platform> platforms = platformService.findAll();
		Map<String, Platform> platformMap = new LinkedHashMap<>();
		String[] platformIds = new String[platforms.size()];
		if (platforms.size() > 0) {
			for (int i = 0; i < platforms.size(); i++) {
				// modify by dfw -- 0 means unusable, 1 means can be used.
				if (platforms.get(i).getState().equals("1")) {
					platformMap.put(platforms.get(i).getCode(), platforms.get(i));
					platformIds[i] = platforms.get(i).getId();
				}
			}
		}

		List<PlatformSettings> platformSettingsList = platformSettingsService.findByHospitalIdAndPlatformIds(hospitalId, platformIds);

		Map<String, PlatformSettings> platformSettingsMap = new HashMap<String, PlatformSettings>();

		if (platforms.size() > 0) {
			//遍历接入平台，根据code设置map值，提供前台展现接入平台类型
			for (Platform pf : platforms) {
				PlatformSettings platformSettings = new PlatformSettings();
				platformSettings.setPfId(pf.getId());
				//判断是否有token，如果没有则生成
				if (StringUtils.isEmpty(platformSettings.getToken())) {
					platformSettings.setToken(PKGenerator.generateId().toUpperCase());
				}
				platformSettingsMap.put(pf.getCode(), platformSettings);
			}
		}

		if (platformSettingsList.size() > 0) {
			//遍历接入参数对象，根据code设置map值，提供前台展现接入平台类型
			for (PlatformSettings platformSettings : platformSettingsList) {
				//判断是否有token，如果没有则生成
				if (StringUtils.isEmpty(platformSettings.getToken())) {
					platformSettings.setToken(PKGenerator.generateId().toUpperCase());
				}

				for (Entry<String, PlatformSettings> entry : platformSettingsMap.entrySet()) {
					if (platformSettings.getCode().equals(entry.getKey())) {
						platformSettingsMap.put(platformSettings.getCode(), platformSettings);
					}
				}

			}
		}

		modelMap.put("hospitalId", hospitalId);
		// modify by dfw...
		// modelMap.put("platforms", platforms);
		modelMap.put("platformsMap", platformMap);
		modelMap.put("platformSettingsMap", platformSettingsMap);
		modelMap.put("wechatAccessUrl", SystemConfig.getStringValue("wechat_access_url"));
		modelMap.put("alipayAccessUrl", SystemConfig.getStringValue("alipay_access_url"));
		modelMap.put("wechatDomain", SystemConfig.getStringValue("wechat_domain"));
		ModelAndView view = new ModelAndView("/sys/hospital/platformSettings");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(@RequestParam(value = "hospitalId", required = true) String hospitalId, String params, HttpServletRequest request) {
		logger.info("/sys/platformSettings/save.hospitalId: " + hospitalId);
		logger.info("/sys/platformSettings/save.params: " + params);
		try {
			//更新缓存时使用 add by yuce
			List<String> oldAppIdAndAppCodes = null;
			//			List<String> oldAppIdAndAppCodes = hospitalCache.findAppInfoCacheFields(hospitalId);
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> cacheParams = new ArrayList<Object>();
			List<HospIdAndAppSecretVo> hospIdAndAppSecretVos = hospitalService.findAppSecretByHospitalId(hospitalId);
			cacheParams.add(hospIdAndAppSecretVos);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppInfoCacheFields", cacheParams);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				oldAppIdAndAppCodes = JSON.parseArray(source, String.class);
			} else {
				oldAppIdAndAppCodes = new ArrayList<String>();
			}

			Hospital hospital = hospitalService.findById(hospitalId);
			// PlatformSettings platformSettings = new PlatformSettings(appId, privateKey, publicKey);
			User user = getLoginUser(request);
			List<PlatformSettings> platformSettingsList = new ArrayList<PlatformSettings>();
			List<HospitalPlatformSettings> hospitalPlatformSettingsList = new ArrayList<HospitalPlatformSettings>();
			JSONObject jParams = JSONObject.parseObject(params);
			for (String key : jParams.keySet()) {
				JSONObject jPlatform = jParams.getJSONObject(key);

				// 设置中间表的状态
				int status = jPlatform.getIntValue("status");
				jPlatform.remove("status");
				// 设置中间表的id，当更新的时候
				String hospitalPlatformSettingsId = jPlatform.getString("hospitalPlatformSettingsId");
				if (status == 0 && StringUtils.isBlank(hospitalPlatformSettingsId)) {
					// 即为不启用，且之前未启动
					continue;
				}

				HospitalPlatformSettings hospitalPlatformSettings = new HospitalPlatformSettings(hospitalId, status);

				if (StringUtils.isNoneBlank(hospitalPlatformSettingsId)) {
					hospitalPlatformSettings.setId(hospitalPlatformSettingsId);
					jPlatform.remove("hospitalPlatformSettingsId");
					hospitalPlatformSettings.setCp(user.getId());
				} else {
					hospitalPlatformSettings.setEp(user.getId());
				}

				// 设置接入平台id
				String platformId = jPlatform.getString("platformId");
				jPlatform.remove("platformId");

				PlatformSettings platformSettings = JSONObject.parseObject(jPlatform.toJSONString(), PlatformSettings.class);

				if (StringUtils.isNotEmpty(platformSettings.getId())) {
					platformSettings.setCp(user.getId());
				} else {
					platformSettings.setEp(user.getId());
				}

				Platform platform = new Platform();
				platform.setId(platformId);
				platformSettings.setPlatform(platform);
				platformSettings.setHospital(hospital);

				platformSettingsList.add(platformSettings);

				// 批量新增或更新的数据
				hospitalPlatformSettings.setPlatformSettings(platformSettings);
				hospitalPlatformSettingsList.add(hospitalPlatformSettings);
			}

			hospital.setStatus(0); //禁用
			hospitalService.update(hospital);

			// 保存接入平台配置信息
			// 1、更新 hospital.app.interface
			// 2、更新 hospital.app.secret
			platformSettingsService.batchSavePlatformSettings(platformSettingsList, hospitalId);
			// hospitalCache.updateAppCacheByHospitalId(hospitalId, oldAppIdAndAppCodes);
			cacheParams.clear();
			cacheParams.add(hospital.getId());
			cacheParams.add(oldAppIdAndAppCodes);
			serveComm.set(CacheType.HOSPITAL_CACHE, "updateAppCacheByHospitalId", cacheParams);

			// 更新医院平台配置 
			// 更新 hospital.app.interface
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

			// 更新平台支付信息
			// 更新 hospital.app.secret
			hospIdAndAppSecretVos = hospitalService.findAppSecretByHospitalId(hospitalId);
			cacheParams.clear();
			cacheParams.add(hospIdAndAppSecretVos);
			serveComm.set(CacheType.HOSPITAL_CACHE, "updateAppSecretCache", cacheParams);

			return new RespBody(hospitalId, Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存接入平台配置信息失败，数据保存异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getGenerat")
	public RespBody getGenerat(HttpServletRequest request, String hospitalId, String appCode) {

		try {
			String appId = "";

			PlatformSettings platformSettings = platformSettingsService.findByHospitalIdAndAppCode(hospitalId, appCode);

			if (platformSettings != null && StringUtils.isNotBlank(platformSettings.getAppId())) {
				appId = platformSettings.getAppId();
			} else {
				appId = AppIdGenerator.genAppId(hospitalId, appCode);
			}

			return new RespBody(Status.OK, appId);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存接入平台配置信息失败，数据保存异常！");
		}
	}

}
