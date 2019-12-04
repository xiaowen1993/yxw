package com.yxw.solr.biz.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.common.dao.HospitalInfoDao;
import com.yxw.solr.biz.common.service.HospitalInfoService;
import com.yxw.solr.vo.HospitalInfosVo;
import com.yxw.solr.vo.StatsHospitalInfosVo;

@Service(value = "hospitalInfoService")
public class HospitalInfoServiceImpl implements HospitalInfoService {
	
	private HospitalInfoDao dao = SpringContextHolder.getBean(HospitalInfoDao.class);

	@Override
	public Map<String, Map<String, List<String>>> getInfos() {
		Map<String, Map<String, List<String>>> map = new ConcurrentHashMap<String, Map<String, List<String>>>();

		// 暂时先只统计东莞 -- 信息全
		List<String> branchs = new ArrayList<>();
		branchs.add("0007");
		branchs.add("0001");
		branchs.add("0004");
		branchs.add("0003");

		Map<String, List<String>> platforms = new HashMap<>();
		platforms.put("1", branchs);
		platforms.put("2", branchs);

		map.put("dgsrmyy", platforms);

		return map;
	}

	@Override
	public Map<String, Map<String, Map<String, List<String>>>> getAllInfosMap() {
		Map<String, Map<String, Map<String, List<String>>>> resultMap = new HashMap<>();

		List<StatsHospitalInfosVo> infosVos = getAllInfos();
		for (StatsHospitalInfosVo vo : infosVos) {
			String hospitalCode = vo.getHospitalCode();
			String branchCode = vo.getBranchCode();
			String platformCode = vo.getPlatformCode();
			String tradeMode = vo.getTradeMode();

			if (resultMap.containsKey(hospitalCode)) {
				// 有医院, 获取医院下的平台Map
				Map<String, Map<String, List<String>>> platformMap = resultMap.get(hospitalCode);
				if (platformMap.containsKey(platformCode)) {
					// 有平台, 获取平台下的支付方式Map
					Map<String, List<String>> tradeModeMap = platformMap.get(platformCode);
					if (tradeModeMap.containsKey(tradeMode)) {
						// 有支付方式, 获取支付方式下的分院List, 添加数据
						List<String> branches = tradeModeMap.get(tradeMode);
						branches.add(branchCode);
					} else {
						// 没有该支付方式, 添加分院, 添加支付方式
						List<String> branches = new ArrayList<>();
						branches.add(branchCode);
						tradeModeMap.put(tradeMode, branches);
					}
				} else {
					// 无平台, 添加分院, 添加支付方式, 添加平台
					List<String> branches = new ArrayList<>();
					branches.add(branchCode);
					Map<String, List<String>> tradeModeMap = new HashMap<>();
					tradeModeMap.put(tradeMode, branches);
					platformMap.put(platformCode, tradeModeMap);
				}
			} else {
				// 无医院, 添加分院, 添加支付方式, 添加平台, 添加医院
				List<String> branches = new ArrayList<>();
				branches.add(branchCode);
				Map<String, List<String>> tradeModeMap = new HashMap<>();
				tradeModeMap.put(tradeMode, branches);
				Map<String, Map<String, List<String>>> platformMap = new HashMap<>();
				platformMap.put(platformCode, tradeModeMap);
				resultMap.put(hospitalCode, platformMap);
			}
		}

		return resultMap;
	}

	@Override
	public Map<String, Map<String, List<String>>> getInfosMapByAppId(String appId) {
		Map<String, Map<String, List<String>>> resultMap = new HashMap<>();

		List<HospitalInfosVo> infosVos = getInfosByAppId(appId);
		for (HospitalInfosVo vo : infosVos) {
			String hospitalCode = vo.getHospitalCode();
			String platformCode = vo.getPlatformCode();
			String tradeMode = vo.getTradeMode();

			if (resultMap.containsKey(hospitalCode)) {
				// 有医院, 获取医院下的平台Map
				Map<String, List<String>> platformMap = resultMap.get(hospitalCode);
				List<String> tradeList = null;
				if (platformMap.containsKey(platformCode)) {
					// 有平台, 获取平台下的支付方式
					tradeList = platformMap.get(platformCode);
					tradeList.add(tradeMode);
				} else {
					// 无平台, 添加支付方式, 添加平台
					tradeList = new ArrayList<>();
					tradeList.add(tradeMode);
				}
			} else {
				// 无医院, 添加支付方式, 添加平台, 添加医院
				List<String> tradeList = new ArrayList<>();
				tradeList.add(tradeMode);
				Map<String, List<String>> platformMap = new HashMap<>();
				platformMap.put(platformCode, tradeList);
				resultMap.put(hospitalCode, platformMap);
			}
		}

		return resultMap;
	}

	@Override
	public List<StatsHospitalInfosVo> getAllInfos() {
		return dao.findAllInfos();
	}

	@Override
	public List<HospitalInfosVo> getInfosByAppId(String appId) {
		return dao.findAllByAppId(appId);
	}

	@Override
	public Map<String, List<StatsHospitalInfosVo>> getAllStdInfos() {
		Map<String, List<StatsHospitalInfosVo>> map = new HashMap<>();
		
		List<StatsHospitalInfosVo> infos = getStdInfos();
		
		for (StatsHospitalInfosVo infosVo: infos) {
			// 已经进行了group ，不会出现医院重复的问题。
			String areaCode = infosVo.getAreaCode();
			if (map.containsKey(areaCode)) {
				map.get(areaCode).add(infosVo);
			} else {
				List<StatsHospitalInfosVo> list = new ArrayList<>();
				list.add(infosVo);
				map.put(areaCode, list);
			}
		}
		
		return map;
	}

	@Override
	public List<StatsHospitalInfosVo> getStdInfos() {
		return dao.getStdInfos();
	}

}
