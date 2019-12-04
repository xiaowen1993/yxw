package com.yxw.easyhealth.biz.search.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.easyhealth.common.controller.BaseAppController;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;

/**
 * 健康易 周边生活圈
 * 
 */
@Controller
@RequestMapping("/easyhealth/life/")
public class EhLifeController extends BaseAppController {

	private static Logger logger = LoggerFactory.getLogger(HospitalCircleController.class);

	//	private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 主页
	 * */
	@RequestMapping(value = "/index")
	public ModelAndView toLifeCircle(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("/easyhealth/biz/life/lifeIndex");
		return modelAndView;
	}

	/**
	 * 生活区定位
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/lifeLocate")
	public RespBody location(HttpServletRequest request, String jsonStr) {
		try {

			// 获取缓存中所有医院基础信息

			// List<Hospital> hospitals = hospitalCache.queryAllHospBaseInfo();
			List<Hospital> hospitals = null;
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAllHospBaseInfo", new ArrayList<Object>());
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				hospitals = JSON.parseArray(source, Hospital.class);
			}
			// 返回结果
			Map<String, Object> resultMap = new HashMap<String, Object>();

			Map<String, Object> minDistanceMap = new HashMap<String, Object>();

			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			logger.info("app定位jsonStr：" + jsonStr);
			logger.debug("app定位jsonStr：" + jsonStr);
			if (jsonStr != null && !"".equals(jsonStr)) {
				if (jsonStr.startsWith("\"") && jsonStr.endsWith("\"")) {
					jsonStr = StringEscapeUtils.unescapeJava(String.valueOf(JSON.parse(jsonStr)));
				}
				JSONObject jObject = JSONObject.parseObject(jsonStr);
				Double selfLongitude = jObject.getDouble("longitude");
				Double selfLatitude = jObject.getDouble("latitude");
				if (selfLongitude != 0 && selfLatitude != 0 && hospitals != null && hospitals.size() > 0) {
					for (Hospital hospital : hospitals) {
						List<BranchHospital> branches = hospital.getBranchHospitals();
						if (branches != null && branches.size() > 0) {
							BranchHospital branchHospital = branches.get(0);
							double distance =
									getDistance(selfLatitude, selfLongitude, Double.valueOf(branchHospital.getLatitude()),
											Double.valueOf(branchHospital.getLongitude()));
							if (distance < 3000) {
								logger.info("周边生活圈，医院：" + branchHospital.getName() + ",距离:" + distance);
								logger.debug("周边生活圈，医院：" + branchHospital.getName() + ",距离:" + distance);
								if (minDistanceMap.get("distance") == null
										|| Double.valueOf(String.valueOf(minDistanceMap.get("distance"))) > distance) {
									minDistanceMap.put("latitude", branchHospital.getLatitude());
									minDistanceMap.put("longitude", branchHospital.getLongitude());
									minDistanceMap.put("hospName", branchHospital.getName());
									minDistanceMap.put("distance", distance);
								}
							} else {
								Map<String, Object> branchMap = new HashMap<String, Object>();
								branchMap.put("distance", distance);
								branchMap.put("latitude", branchHospital.getLatitude());
								branchMap.put("longitude", branchHospital.getLongitude());
								branchMap.put("hospName", branchHospital.getName());
								resultList.add(branchMap);
							}
						}
					}
					if (minDistanceMap.get("distance") != null) {
						resultMap.put("sign", "FOUND");
						resultMap.put("latitude", minDistanceMap.get("latitude"));
						resultMap.put("longitude", minDistanceMap.get("longitude"));
						resultMap.put("hospName", minDistanceMap.get("hospName"));
					} else {
						resultMap.put("sign", "NOTFOUND");
						resultMap.put("hospList", resultList);
					}
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

	private double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 判断两个经纬度坐标指向的是否是同一个地点 差距在100米内
	 * */
	public double getDistance(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 6378.137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * earthRadius;
		s = Math.round(s * 1000);
		// logger.info("两个经纬度相距：" + s + "米");
		// logger.debug("两个经纬度相距：" + s + "米");
		return s;
	}
}
