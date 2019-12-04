package com.yxw.easyhealth.biz.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.easyhealth.biz.register.service.HadRegDoctorService;
import com.yxw.easyhealth.biz.search.vo.HospitalCircleVo;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;
import com.yxw.mobileapp.constant.EasyHealthConstant;

/**
 * @Package: com.yxw.mobileapp.biz.search.controller
 * @ClassName: HospitalCircleController
 * @Statement: <p>
 *             三公里就医圈
 *             </p>
 * @JDK version used: 1.6
 * @Author: luob
 * @Create Date: 2015-10-29
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/easyhealth/hospitalCircle/")
public class HospitalCircleController extends BaseAppController {

	private static Logger logger = LoggerFactory.getLogger(HospitalCircleController.class);

	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);

	// 曾挂号医生
	private HadRegDoctorService hadRegDoctorService = SpringContextHolder.getBean(HadRegDoctorService.class);

	/**
	 * 三公里就医圈主页
	 * 
	 * @param locationStr
	 *            app返回的定位数据，保存在request以免重新请求
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(value = "locationStr", required = false) String locationStr, @RequestParam(value = "version",
			required = false) String version) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/hospitalCircle/hospindex");
		modelAndView.addObject("locationStr", locationStr);
		modelAndView.addObject("version", version);
		return modelAndView;
	}

	/**
	 * 查看医院详情
	 * 
	 * @param hospId
	 * @param telphone
	 * @param address
	 * @param hospName
	 * @param appId
	 * @param appCode
	 * @param hospCode
	 * @param locationStr
	 *            app返回的定位数据，保存在request以免重新请求
	 * @param longitude
	 *            医院 经度
	 * @param latitude
	 *            医院 纬度
	 * @param version
	 *            版本号
	 * */
	@RequestMapping(value = "/toHospInfo")
	public ModelAndView toHospInfo(String hospId, String telphone, String address, String hospName, String appId, String appCode,
			String hospCode, String locationStr, String longitude, String latitude, String version) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/hospitalCircle/hospdetail");
		modelAndView.addObject("hospId", hospId);
		modelAndView.addObject("telphone", ( telphone == null || "null".equals(telphone) ) ? "" : telphone);
		modelAndView.addObject("address", ( address == null || "null".equals(address) ) ? "" : address);
		modelAndView.addObject("hospName", hospName);
		modelAndView.addObject("appId", appId);
		modelAndView.addObject("appCode", appCode);
		modelAndView.addObject("hospCode", hospCode);
		modelAndView.addObject("locationStr", locationStr);
		modelAndView.addObject("longitude", longitude);
		modelAndView.addObject("latitude", latitude);
		modelAndView.addObject("version", version);
		// System.out.println("locationStr----" + locationStr);
		return modelAndView;
	}

	/**
	 * 查看药店 社区 诊所详情
	 * 
	 * @param hospId
	 * @param telphone
	 * @param address
	 * @param hospName
	 * @param appId
	 * @param appCode
	 * @param hospCode
	 * @param locationStr
	 *            app返回的定位数据，保存在request以免重新请求
	 * @param longitude
	 *            医院 经度
	 * @param latitude
	 *            医院 纬度
	 * */
	@RequestMapping(value = "/toOtherInfo")
	public ModelAndView toOtherInfo(String hospId, String telphone, String address, String hospName, String appId, String appCode,
			String hospCode, String locationStr, String locateType, String longitude, String latitude) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/hospitalCircle/otherdetail");
		modelAndView.addObject("hospId", hospId);
		modelAndView.addObject("telphone", ( telphone == null || "null".equals(telphone) ) ? "" : telphone);
		modelAndView.addObject("address", ( address == null || "null".equals(address) ) ? "" : address);
		modelAndView.addObject("hospName", hospName);
		modelAndView.addObject("appId", appId);
		modelAndView.addObject("appCode", appCode);
		modelAndView.addObject("hospCode", hospCode);
		modelAndView.addObject("locationStr", locationStr);
		modelAndView.addObject("locateType", locateType);
		modelAndView.addObject("longitude", longitude);
		modelAndView.addObject("latitude", latitude);
		return modelAndView;
	}

	/**
	 * goto其他定位主页
	 * 
	 * @param type
	 *            类型
	 * */
	@RequestMapping(value = "/toOtherIndex")
	public ModelAndView toOtherIndex(String type) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/hospitalCircle/otherindex");
		modelAndView.addObject("locateType", type);
		return modelAndView;
	}

	/**
	 * 医院定位
	 * 
	 * @param jsonStr
	 *            app返回的医院定位数据 json集合
	 * */
	@ResponseBody
	@RequestMapping(value = "/locateHospital", method = RequestMethod.POST)
	public RespBody locateHospital(HttpServletRequest request, String jsonStr) {
		try {
			// 附件的医院
			List<HospitalCircleVo> nearbyResult = new ArrayList<HospitalCircleVo>();
			// 发现的医院
			List<HospitalCircleVo> discoverResult = new ArrayList<HospitalCircleVo>();
			// 返回的结果集合
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// 目前健康易上的医院集合
			// Map<String, HospitalCircleVo> hospMap = new HashMap<String, HospitalCircleVo>();

			String openId = getAppOpenId(request);
			// 曾挂号医生
			List<HadRegDoctor> hadRegDoctors = hadRegDoctorService.findByOpenId(openId);
			// 曾挂号医院
			List<String> hadRegHosps = new ArrayList<String>();
			// 过滤获得所有的曾挂号医院
			for (HadRegDoctor hadRegDoctor : hadRegDoctors) {
				if (!hadRegHosps.contains(hadRegDoctor.getHospitalId())) {
					hadRegHosps.add(hadRegDoctor.getHospitalId());
				}
			}
			if (logger.isDebugEnabled()) {
				logger.info(jsonStr);
			}
			logger.info("app定位jsonStr：" + jsonStr);
			logger.debug("app定位jsonStr：" + jsonStr);
			if (jsonStr != null && !"".equals(jsonStr)) {
				if (jsonStr.startsWith("\"") && jsonStr.endsWith("\"")) {
					jsonStr = StringEscapeUtils.unescapeJava(String.valueOf(JSON.parse(jsonStr)));
				}
				JSONObject jObject = JSONObject.parseObject(jsonStr);
				// 获取当前用户--更新用户经纬度
				EasyHealthUser user = getEasyHealthUser(request);
				user.setLongitude(jObject.getString("longitude"));
				user.setLatitude(jObject.getString("latitude"));
				if (logger.isDebugEnabled()) {
					logger.info("用户当前经度longitude:" + jObject.getString("longitude"));
					logger.info("用户当前纬度latitude:" + jObject.getString("latitude"));
				}
				request.getSession().setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, user);
				resultMap.put("location", jObject.getString("location"));// 用户当前位置
				JSONArray jsonArray = jObject.getJSONArray("hospitals");
				/*---------------------------------------TEST----------------------------------------*/
				/*
				 * JSONObject testJsonObject = new JSONObject();
				 * testJsonObject.put("addr", "龙岗区布澜路29号近李朗国际珠宝产业园深圳市第三人民医院 ");
				 * testJsonObject.put("distance", "450");
				 * testJsonObject.put("name", "深圳市第三人民医院");
				 * testJsonObject.put("tel", "13632224314");
				 * testJsonObject.put("longitude", "114.1286710102");
				 * testJsonObject.put("latitude", "22.6352801119");
				 * jsonArray.add(testJsonObject);
				 */
				/*-------------------------------------------------------------------------------*/
				if (jsonArray != null && jsonArray.size() > 0) {
					List<HospIdAndAppSecretVo> hospitalList = baseDatasManager.getHospitalListByAppCode("easyHealth");
					if (hospitalList != null && hospitalList.size() > 0) {
						for (HospIdAndAppSecretVo vo : hospitalList) {
							List<BranchHospital> branchHospitals = baseDatasManager.queryBranchsByHospitalCode(vo.getHospCode());
							if (branchHospitals != null && branchHospitals.size() > 0) {
								BranchHospital branchHospital = branchHospitals.get(0);
								if (branchHospital != null && branchHospital.getLatitude() != null && branchHospital.getLongitude() != null) {
									double latitude = Double.valueOf(branchHospital.getLatitude());
									double longitude = Double.valueOf(branchHospital.getLongitude());
									HospitalCircleVo hospVo =
											new HospitalCircleVo(vo.getHospId(), vo.getHospCode(), vo.getHospName(),
													branchHospital.getAddress(), branchHospital.getTel(), vo.getAppId(), vo.getAppCode());
									logger.info(vo.getHospName() + ":用户当前经度longitude:" + jObject.getString("longitude"));
									logger.info(vo.getHospName() + ":用户当前纬度latitude:" + jObject.getString("latitude"));
									logger.info(vo.getHospName() + ":分院纬度latitude:" + latitude);
									logger.info(vo.getHospName() + ":分院纬度longitude:" + longitude);
									boolean flag =
											judgeDistance(latitude, longitude, Double.valueOf(jObject.getString("latitude")),
													Double.valueOf(jObject.getString("longitude")));
									if (flag) {
										hospVo.setDistance("0");
										hospVo.setLatitude(branchHospital.getLatitude());
										hospVo.setLongitude(branchHospital.getLongitude());
										discoverResult.add(hospVo);
									}
								}
							}
						}
					}
					HospitalCircleVo tempVo = null;
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jo = jsonArray.getJSONObject(i);
						tempVo = new HospitalCircleVo();
						tempVo.setAddress(jo.getString("addr"));
						tempVo.setDistance(jo.getString("distance"));
						tempVo.setHospName(jo.getString("name"));
						tempVo.setTel(jo.getString("tel"));
						tempVo.setLongitude(jo.getString("longitude"));
						tempVo.setLatitude(jo.getString("latitude"));
						tempVo.setHospId("");
						nearbyResult.add(tempVo);
					}
					resultMap.put("nearbyHospitals", nearbyResult);
					resultMap.put("discoverHospitals", discoverResult);
					resultMap.put("locationStr", jsonStr);
					return new RespBody(Status.OK, resultMap);
				} else {
					return new RespBody(Status.ERROR, resultMap);
				}
			} else {
				return new RespBody(Status.ERROR, "三公里范围内未发现任何医院");
			}
		} catch (Exception e) {
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 其他定位
	 * 
	 * @param jsonStr
	 *            app返回的医院定位数据 json集合
	 * */
	@ResponseBody
	@RequestMapping(value = "/locateOther", method = RequestMethod.POST)
	public RespBody locateOther(HttpServletRequest request, String jsonStr) {
		try {
			List<HospitalCircleVo> nearbyResult = new ArrayList<HospitalCircleVo>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (jsonStr != null && !"".equals(jsonStr)) {
				if (jsonStr.startsWith("\"") && jsonStr.endsWith("\"")) {
					jsonStr = StringEscapeUtils.unescapeJava(String.valueOf(JSON.parse(jsonStr)));
				}
				JSONObject jObject = JSONObject.parseObject(jsonStr);
				EasyHealthUser user = getEasyHealthUser(request);
				user.setLongitude(jObject.getString("longitude"));
				user.setLatitude(jObject.getString("latitude"));
				request.getSession().setAttribute(EasyHealthConstant.SESSION_USER_ENTITY, user);
				JSONArray jsonArray = jObject.getJSONArray("hospitals");
				if (jsonArray != null && jsonArray.size() > 0) {
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jo = jsonArray.getJSONObject(i);
						HospitalCircleVo tempVo = new HospitalCircleVo();
						tempVo.setAddress(jo.getString("addr"));
						tempVo.setDistance(jo.getString("distance"));
						tempVo.setHospName(jo.getString("name"));
						tempVo.setTel(jo.getString("tel"));
						tempVo.setLatitude(jo.getString("latitude"));
						tempVo.setLongitude(jo.getString("longitude"));
						tempVo.setHospId("");
						nearbyResult.add(tempVo);
					}
					resultMap.put("nearbyHospitals", nearbyResult);
					return new RespBody(Status.OK, resultMap);
				} else {
					return new RespBody(Status.ERROR);
				}
			} else {
				return new RespBody(Status.ERROR);
			}
		} catch (Exception e) {
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 兼容旧版本 医院定位
	 * */
	@ResponseBody
	@RequestMapping(value = "/filterHospital", method = RequestMethod.POST)
	public RespBody doSearch(HttpServletRequest request, String jsonStr) {
		try {
			List<HospitalCircleVo> result = new ArrayList<HospitalCircleVo>();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			Map<String, HospitalCircleVo> hospMap = new HashMap<String, HospitalCircleVo>();
			//List<HospIdAndAppSecretVo> hospitalList = baseDatasManager.getHospitalListByAppCode("app");
			String appCode = "app";
			if (request.getSession().getAttribute(BizConstant.APPCODE) != null) {
				appCode = String.valueOf(request.getSession().getAttribute(BizConstant.APPCODE));
			}
			List<HospIdAndAppSecretVo> hospitalList = baseDatasManager.getHospitalListByAppCode(appCode, getAreaCode(request));
			System.out.println(jsonStr);
			if (jsonStr != null && !"".equals(jsonStr)) {
				if (jsonStr.startsWith("\"") && jsonStr.endsWith("\"")) {
					jsonStr = StringEscapeUtils.unescapeJava(String.valueOf(JSON.parse(jsonStr)));
				}
				JSONObject jObject = JSONObject.parseObject(jsonStr);
				resultMap.put("location", jObject.getString("location"));
				JSONArray jsonArray = jObject.getJSONArray("hospitals");
				if (jsonArray != null && jsonArray.size() > 0) {
					if (hospitalList != null && hospitalList.size() > 0) {
						for (HospIdAndAppSecretVo vo : hospitalList) {
							System.out.println("HospIdAndAppSecretVo");
							List<BranchHospital> branchHospitals = baseDatasManager.queryBranchsByHospitalCode(vo.getHospCode());
							System.out.println("BranchHospital");
							if (branchHospitals != null && branchHospitals.size() > 0) {
								BranchHospital branchHospital = branchHospitals.get(0);
								hospMap.put(vo.getHospName(), new HospitalCircleVo(vo.getHospId(), vo.getHospCode(), vo.getHospName(),
										branchHospital.getAddress(), branchHospital.getTel(), vo.getAppId(), vo.getAppCode()));
							}
						}

					}
					System.out.println(jsonArray.size());
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jo = jsonArray.getJSONObject(i);
						HospitalCircleVo hospVo = hospMap.get(jo.getString("name"));
						if (hospVo != null) {
							hospVo.setDistance(jo.getString("distance"));
							result.add(hospVo);
						} else {
							HospitalCircleVo tempVo = new HospitalCircleVo();
							tempVo.setAddress(jo.getString("addr"));
							tempVo.setDistance(jo.getString("distance"));
							tempVo.setHospName(jo.getString("name"));
							tempVo.setTel(jo.getString("tel"));
							tempVo.setHospId("");
							tempVo.setAppCode(appCode);
							tempVo.setAppId("");
							tempVo.setHospCode("");
							result.add(tempVo);
						}
					}
					resultMap.put("hospitalList", result);
					return new RespBody(Status.OK, resultMap);
				} else {
					return new RespBody(Status.ERROR, resultMap);
				}
			} else {
				return new RespBody(Status.ERROR, "三公里范围内未发现任何医院");
			}
		} catch (Exception e) {
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 兼容旧版本
	 * */
	@RequestMapping(value = "/toHospInfoLowVersion")
	public ModelAndView toHospInfo(HttpServletRequest request, String hospId, String telphone, String address, String hospName,
			String appId, String appCode, String hospCode, String locationStr) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/hospitalCircle/detail");
		modelAndView.addObject("hospId", hospId);
		modelAndView.addObject("telphone", telphone);
		modelAndView.addObject("address", address);
		modelAndView.addObject("hospName", hospName);
		modelAndView.addObject("appId", appId);
		//		modelAndView.addObject("appCode", appCode);
		modelAndView.addObject("hospCode", hospCode);
		modelAndView.addObject("locationStr", locationStr);
		return modelAndView;
	}

	private double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 判断两个经纬度坐标指向的是否是同一个地点 差距在100米内
	 * */
	public boolean judgeDistance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6378.137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s =
				2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * earthRadius;
		s = Math.round(s * 1000);
		logger.info("两个经纬度相距：" + s + "米");
		logger.debug("两个经纬度相距：" + s + "米");
		if (s < 750) {
			return true;
		} else {
			return false;
		}
	}
}
