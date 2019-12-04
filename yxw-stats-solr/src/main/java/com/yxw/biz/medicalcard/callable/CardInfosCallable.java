package com.yxw.biz.medicalcard.callable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.client.YxwSolrClient;
import com.yxw.constants.Cores;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.utils.CardUtils;
import com.yxw.vo.medicalcard.AgeGroupStats;
import com.yxw.vo.medicalcard.CardInfoStatsRequest;
import com.yxw.vo.medicalcard.CardInfosVo;
import com.yxw.vo.medicalcard.GenderStats;

public class CardInfosCallable implements Callable<Map<String, Object>> {

	private static Logger logger = LoggerFactory.getLogger(CardInfosCallable.class);

	private CardInfoStatsRequest request;

	private String queryBeginDate;

	private String queryEndDate;

	public CardInfosCallable(CardInfoStatsRequest request, String queryBeginDate, String queryEndDate) {
		this.request = request;
		this.queryBeginDate = queryBeginDate;
		this.queryEndDate = queryEndDate;
	}

	@Override
	public Map<String, Object> call() throws Exception {
		// 有两个值，一个是ageGroup-> ageGroupStats, 一个是gender - > genderStats
		Map<String, Object> resultMap = new HashMap<>();

		String thisMonth = queryBeginDate.substring(0, 7);
		List<CardInfosVo> vos = new ArrayList<>();

		// 初始化 [年龄段] 信息
		AgeGroupStats ageGroupStats = new AgeGroupStats();
		ageGroupStats.setThisMonth(thisMonth);
		ageGroupStats.setId(PKGenerator.generateId());
		BeanUtils.copyProperties(request, ageGroupStats);

		// 初始化 [性别] 信息
		GenderStats genderStats = new GenderStats();
		genderStats.setThisMonth(thisMonth);
		genderStats.setId(PKGenerator.generateId());
		BeanUtils.copyProperties(request, genderStats);

		// 查询
		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode:" + request.getHospitalCode());
		sbQuery.append(" AND ");
		sbQuery.append("areaCode:" + request.getAreaCode().replace("/", "\\/"));
		sbQuery.append(" AND ");
		sbQuery.append("createDate:[" + queryBeginDate + " TO " + queryEndDate + "]");

		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.setQuery(sbQuery.toString());
		// 指定查询返回的字段
		solrQuery.set(CommonParams.FL, "age,areaName,birth,hospitalName,id,idNo,name,ownership,platform,sex");

		// 开启去重功能
		solrQuery.set(GroupParams.GROUP, "true");
		solrQuery.set(GroupParams.GROUP_FIELD, "cardNo");

		YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);
		QueryResponse response = solrClient.query(Cores.CORE_CARD_INFOS, solrQuery);
		GroupResponse groupResponse = response.getGroupResponse();
		if (CollectionUtils.isNotEmpty(groupResponse.getValues())) {
			List<Group> groups = groupResponse.getValues().get(0).getValues();
			if (CollectionUtils.isNotEmpty(groups)) {
				// logger.info("共有数据：{} 条.", groups.size());
				for (Group group : groups) {
					SolrDocument doc = group.getResult().get(0);
					CardInfosVo vo = new CardInfosVo();
					vo.setId((String) doc.getFieldValue("id"));
					// vo.setName((String) doc.getFieldValue("name"));
					vo.setAge((Integer) doc.getFieldValue("age"));
					vo.setSex((Integer) doc.getFieldValue("sex"));
					vo.setBirth((String) doc.getFieldValue("birth"));
					// vo.setAreaName((String) doc.getFieldValue("areaName"));
					// vo.setHospitalName((String) doc.getFieldValue("hospitalName"));
					vo.setIdNo((String) doc.getFieldValue("idNo"));
					vo.setOwnership((String) doc.getFieldValue("ownership"));
					vo.setPlatform((String) doc.getFieldValue("platform"));
					vos.add(vo);
				}
			} else {
				logger.info("返回的groups为空。 地区： {}， 医院： {}， 统计月份： {}.",
						new Object[] { request.getAreaName(), request.getHospitalName(), thisMonth });
			}
		} else {
			logger.info("返回的groupResponse为空。 地区： {}， 医院： {}， 统计月份： {}.",
					new Object[] { request.getAreaName(), request.getHospitalName(), thisMonth });
		}

		// 遍历绑卡数据，进行业务的统计
		for (CardInfosVo vo : vos) {
			int gender = 0; // 默认-无效的性别
			int age = -1; // 默认-无效的年龄
			int platform = Integer.valueOf(vo.getPlatform()).intValue();

			/************************ 性别计算  ************************/
			if (vo.getSex() != null) {
				gender = vo.getSex().intValue();
				if (gender < 1 || gender > 2) {
					gender = CardUtils.getSexByIdNo(vo.getIdNo());
				}
			} else {
				// 通过身份证计算
				gender = CardUtils.getSexByIdNo(vo.getIdNo());
			}

			if (gender < 1 || gender > 2) {
				logger.info("[数据异常], 本身没有性别，通过身份证无法获取性别. vo:{}.", JSON.toJSONString(vo));
				continue;
			}

			/************************ 年龄计算  ************************/
			if (vo.getAge() != null) {
				age = vo.getAge().intValue();
				if (age < 0 || age > 150) {
					age = CardUtils.getAgeByBirth(vo.getBirth());
					if (age == -1) {
						age = CardUtils.getAgeByIdNo(vo.getIdNo());
					}
				}
			} else {
				age = CardUtils.getAgeByBirth(vo.getBirth());
				if (age == -1) {
					age = CardUtils.getAgeByIdNo(vo.getIdNo());
				}
			}

			// 接受的最大算150好了，没那么多长寿的吧
			if (age < 0 || age > 150) {
				logger.info("[数据异常], 本身没有年龄，通过出生日期及身份证无法获取年龄. vo:{}.", JSON.toJSONString(vo));
				continue;
			}

			/************************ 归档  ************************/
			if (platform == 1 || platform == 7) { // 微信
				// 性别归档
				if (gender == 1) {
					genderStats.setThisMonthWxMan(genderStats.getThisMonthWxMan() + 1);
				} else {
					genderStats.setThisMonthWxWoman(genderStats.getThisMonthWxWoman() + 1);
				}

				// 年龄归档
				if (age > 70) {
					ageGroupStats.setWxAgeGroup6(ageGroupStats.getWxAgeGroup6() + 1);
				} else if (age > 50) {
					ageGroupStats.setWxAgeGroup5(ageGroupStats.getWxAgeGroup5() + 1);
				} else if (age > 35) {
					ageGroupStats.setWxAgeGroup4(ageGroupStats.getWxAgeGroup4() + 1);
				} else if (age > 29) {
					ageGroupStats.setWxAgeGroup3(ageGroupStats.getWxAgeGroup3() + 1);
				} else if (age > 18) {
					ageGroupStats.setWxAgeGroup2(ageGroupStats.getWxAgeGroup2() + 1);
				} else if (age > 12) {
					ageGroupStats.setWxAgeGroup1(ageGroupStats.getWxAgeGroup1() + 1);
				} else {
					ageGroupStats.setWxAgeGroup0(ageGroupStats.getWxAgeGroup0() + 1);
				}
			} else if (platform == 2 || platform == 8) { // 支付宝
				// 性别归档
				if (gender == 1) {
					genderStats.setThisMonthAliMan(genderStats.getThisMonthAliMan() + 1);
				} else {
					genderStats.setThisMonthAliWoman(genderStats.getThisMonthAliWoman() + 1);
				}

				// 年龄归档
				if (age > 70) {
					ageGroupStats.setAliAgeGroup6(ageGroupStats.getAliAgeGroup6() + 1);
				} else if (age > 50) {
					ageGroupStats.setAliAgeGroup5(ageGroupStats.getAliAgeGroup5() + 1);
				} else if (age > 35) {
					ageGroupStats.setAliAgeGroup4(ageGroupStats.getAliAgeGroup4() + 1);
				} else if (age > 29) {
					ageGroupStats.setAliAgeGroup3(ageGroupStats.getAliAgeGroup3() + 1);
				} else if (age > 18) {
					ageGroupStats.setAliAgeGroup2(ageGroupStats.getAliAgeGroup2() + 1);
				} else if (age > 12) {
					ageGroupStats.setAliAgeGroup1(ageGroupStats.getAliAgeGroup1() + 1);
				} else {
					ageGroupStats.setAliAgeGroup0(ageGroupStats.getAliAgeGroup0() + 1);
				}
			} else {
				logger.info("[数据异常], 本身没有年龄，通过出生日期及身份证无法获取年龄. 医院： {}. vo:{}.", new Object[]{request.getHospitalName(), JSON.toJSONString(vo)});
				continue;
			}
		}
		
		vos.clear();
		vos = null;

		resultMap.put("ageGroup", ageGroupStats);
		resultMap.put("gender", genderStats);

		return resultMap;
	}

	public String getQueryBeginDate() {
		return queryBeginDate;
	}

	public void setQueryBeginDate(String queryBeginDate) {
		this.queryBeginDate = queryBeginDate;
	}

	public String getQueryEndDate() {
		return queryEndDate;
	}

	public void setQueryEndDate(String queryEndDate) {
		this.queryEndDate = queryEndDate;
	}

	public CardInfoStatsRequest getRequest() {
		return request;
	}

	public void setRequest(CardInfoStatsRequest request) {
		this.request = request;
	}

}
