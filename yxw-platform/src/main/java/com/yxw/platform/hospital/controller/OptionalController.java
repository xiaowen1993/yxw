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
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONPath;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.Optional;
import com.yxw.commons.entity.platform.hospital.PlatformOptional;
import com.yxw.commons.vo.PlatformOptionalVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.BaseController;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.hospital.service.OptionalService;
import com.yxw.platform.hospital.service.PlatformOptionalService;
import com.yxw.platform.hospital.service.PlatformService;

/**
 * 后台功能选择管理 Controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Controller
@RequestMapping("/sys/optional")
public class OptionalController extends BaseController<Optional, String> {

	private Logger logger = LoggerFactory.getLogger(OptionalController.class);

	@Autowired
	private OptionalService optionalService;

	@Autowired
	private PlatformOptionalService platformOptionalService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private PlatformService platformService;

	@Override
	protected BaseService<Optional, String> getService() {
		return optionalService;
	}

	@RequestMapping("/toView")
	public ModelAndView toView(@RequestParam(value = "hospitalId", required = true) String hospitalId, ModelMap modelMap) {
		// logger.info("/sys/optional/toView.hospitalId: " + hospitalId);

		List<Optional> optionals = optionalService.findAll();

		// 需要改动，不直接和医院挂钩，和hospitalPlatformSettings挂钩
		List<PlatformOptionalVo> platformOptionalVos = platformOptionalService.findAllByHospitalId(hospitalId);
		Map<String, PlatformOptionalVo> selOptionalsMap = Maps.uniqueIndex(platformOptionalVos, new Function<PlatformOptionalVo, String>() {

			@Override
			public String apply(PlatformOptionalVo input) {
				// 可以返回platformCode或者platformId, 则页面同样进行更改即可
				return input.getPlatformCode();
			}

		});
		logger.info(JSON.toJSONString(platformOptionalVos));

		// 获取医院平台配置关系
		List<PlatformPaymentVo> platforms = platformService.findByHospitalId(hospitalId);
		// logger.info(JSON.toJSONString(platforms));
		modelMap.put("hospitalId", hospitalId);
		modelMap.put("optionals", optionals);
		modelMap.put("platforms", platforms);
		modelMap.put("selOptionalsMap", selOptionalsMap);

		// 初始化使用

		ModelAndView view = new ModelAndView("/sys/hospital/optional");
		return view;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(@RequestParam(value = "hospitalId", required = true) String hospitalId, String selOptionals, HttpServletRequest request) {
		logger.info("/sys/platformSettings/save.hospitalId: " + hospitalId);
		logger.info("/sys/platformSettings/save.selOptionals: " + selOptionals);

		/**只缓存健康易*/
		try {
			JSONArray arrays = JSON.parseArray(selOptionals);
			if (arrays.size() > 0) {
				// 入库数据
				Map<String, List<PlatformOptional>> optionsMap = new HashMap<>();
				// 放入缓存
				Map<String, Object> cacheOptionMap = new HashMap<String, Object>();
				// 医院对象实体
				Hospital hospital = hospitalService.findById(hospitalId);

				for (int i = 0; i < arrays.size(); i++) {
					Object object = arrays.get(i);
					String platformCode = (String) JSONPath.eval(object, "$.platformCode");
					String platformSettingsId = (String) JSONPath.eval(object, "$.platformSettingsId");
					String appId = (String) JSONPath.eval(object, "$.appId");
					List<Optional> optionals = JSON.parseArray(((JSONArray) JSONPath.eval(object, "$.optionals")).toJSONString(), Optional.class);

					List<String> transformIds = Lists.transform(optionals, new Function<Optional, String>() {
						public String apply(Optional optional) {
							Preconditions.checkNotNull(optional);
							return optional.getId();

						}
					});
					List<String> ids = Lists.newArrayList(transformIds);
					if (!CollectionUtils.isEmpty(ids)) {
						List<Optional> currentOptionals = optionalService.findByIds(ids);
						List<PlatformOptional> platformOptionals = new ArrayList<>(currentOptionals.size());
						for (Optional optional : currentOptionals) {
							StringBuilder sBuilder = new StringBuilder();
							platformOptionals.add(new PlatformOptional(optional, platformSettingsId));
							HospitalInfoByEasyHealthVo cacheVo = new HospitalInfoByEasyHealthVo(hospital.getId(), hospital.getCode(), hospital.getName(),
									hospital.getAreaCode(), appId, optional.getBizCode(), platformCode);
							sBuilder.append(platformCode).append(CacheConstant.SPLIT_CHAR).append(optional.getBizCode()).append(CacheConstant.SPLIT_CHAR)
									.append(hospital.getAreaCode());
							cacheOptionMap.put(sBuilder.toString(), cacheVo);
						}

						optionsMap.put(platformSettingsId, platformOptionals);
					}
				}

				// logger.info(JSON.toJSONString(optionsMap));
				// 入库
				platformOptionalService.batchSaveHospitalOptional(optionsMap);
  
				// 存入缓存
				logger.info(JSON.toJSONString(optionsMap));
				ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
				List<Object> params = new ArrayList<Object>();
				params.add(cacheOptionMap);
				params.add(hospital.getCode());
				serveComm.set(CacheType.HOSPITAL_AND_OPTION_CACHE, "updateHospitalByMap", params);
			}

			return new RespBody(hospitalId, Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "保存已选择的功能失败，数据保存异常！");
		}

	}

}
