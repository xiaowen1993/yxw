package com.yxw.biz.dept.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.GroupParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.biz.common.StatsConstant;
import com.yxw.biz.dept.callable.StatsPerMonthDeptCallable;
import com.yxw.biz.dept.service.DeptService;
import com.yxw.client.YxwSolrClient;
import com.yxw.constants.Cores;
import com.yxw.constants.PoolConstant;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.utils.DateUtils;
import com.yxw.vo.dept.DeptStatsRequest;
import com.yxw.vo.dept.DeptVo;

@Service(value = "deptService")
public class DeptServiceImpl implements DeptService {

	private static Logger logger = LoggerFactory.getLogger(DeptService.class);

	private YxwSolrClient solrClient = SpringContextHolder.getBean(YxwSolrClient.class);

	@Override
	public String statsInfos(DeptStatsRequest request) {
		String beginDate = StatsConstant.DEFAULT_BEGIN_DATE;
		String tempDate = beginDate;
		String endDate = DateUtils.getFirstDayOfThisMonth();

		// tempDate = "2017-01-01";

		// 需要生成索引的数据
		// List<Object> deptStatses = new ArrayList<>();

		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsInfos-dept"));
		List<FutureTask<String>> taskList = new ArrayList<>();

		// 首次查询，获取医院、分院科室、科室代码等信息先。
		String coreName = "";
		if (request.getPlatform() == 1) {
			coreName = Cores.CORE_STD_REG_INFOS;
		} else if (request.getPlatform() == 2) {
			coreName = Cores.CORE_HIS_REG_INFOS;
			return "暂不支持医院项目平台类型.";
		} else {
			logger.error("暂不支持该平台类型： platform: {}.", request.getPlatform());
			return "暂不支持该平台类型.";
		}
		Map<String, DeptVo> deptVos = getBaseDeptInfos(request.getHospitalCode(), request.getAreaCode(), coreName);
		logger.info(JSON.toJSONString(deptVos));
		/**
		 * 按月分来进行
		 */
		while (tempDate.compareTo(endDate) < 0) {
			String queryBeginDate = tempDate;
			String queryEndDate = DateUtils.getDayForDate(DateUtils.StringToDate(DateUtils.getFirstDayNextMonth(tempDate)), -1);

			StatsPerMonthDeptCallable callable = new StatsPerMonthDeptCallable(request, queryBeginDate, queryEndDate, deptVos, coreName);
			FutureTask<String> task = new FutureTask<>(callable);
			taskList.add(task);
			executorService.submit(task);

			tempDate = DateUtils.getFirstDayNextMonth(tempDate);
		}

		try {
			for (FutureTask<String> task : taskList) {
				// 后续可能需要改成1天
				String result = task.get(30, TimeUnit.MINUTES);
				if (!result.isEmpty()) {
					logger.info(result);
				}
			}
		} catch (Exception e) {
			logger.error("医院： {}. 年龄段&&性别信息统计错误. errorMsg: {}. cause: {}.", new Object[] { request.getHospitalName(), e.getMessage(), e.getCause() });
		} 

		executorService.shutdown();
		
		// logger.info("入库数据：" + JSON.toJSONString(deptStatses));
		// saveStatsInfos(request, deptStatses);
		
		deptVos.clear();
		deptVos = null;
		
		// deptStatses.clear();
		// deptStatses = null;

		return String.format("hospitalCode: [%s], all RegDept stats end...", new Object[] { request.getHospitalCode() });
	}

	/**
	 * 获取科室信息（每个医院只获取一次）
	 * @param hospitalCode
	 * @param areaCode
	 * @return <deptCode, dept>
	 */
	private Map<String, DeptVo> getBaseDeptInfos(String hospitalCode, String areaCode, String coreName) {
		Map<String, DeptVo> deptMap = new HashMap<>();

		SolrQuery solrQuery = new SolrQuery();
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("hospitalCode:" + hospitalCode);
		// 目前不根据areaCode来进行统计，也是没问题的。
		// sbQuery.append(" AND ");
		// sbQuery.append("areaCode:" + areaCode.replace("/", "\\/"));

		solrQuery.setRows(Integer.MAX_VALUE);
		solrQuery.setQuery(sbQuery.toString());
		// 指定查询返回的字段
		solrQuery.set(CommonParams.FL, "branchName,branchCode,deptName,deptCode");

		// 开启去重功能
		solrQuery.set(GroupParams.GROUP, "true");
		solrQuery.set(GroupParams.GROUP_FIELD, "deptCode");

		QueryResponse response = null;
		try {
			response = solrClient.query(coreName, solrQuery);
			GroupResponse groupResponse = response.getGroupResponse();
			if (!CollectionUtils.isEmpty(groupResponse.getValues())) {
				List<Group> groups = groupResponse.getValues().get(0).getValues();
				if (!CollectionUtils.isEmpty(groups)) {
					// logger.info("共有数据：{} 条.", groups.size());
					for (Group group : groups) {
						SolrDocument doc = group.getResult().get(0);
						DeptVo vo = new DeptVo();
						vo.setName((String) doc.getFieldValue("deptName"));
						vo.setBranchCode((String) doc.getFieldValue("branchCode"));
						vo.setBranchName((String) doc.getFieldValue("branchName"));
						vo.setCode((String) doc.getFieldValue("deptCode"));
						deptMap.put(vo.getCode(), vo);
					}
				} else {
					logger.info("返回的groups为空。 地区： {}， 医院： {}.", new Object[] { areaCode, hospitalCode });
				}
			} else {
				logger.info("返回的groupResponse为空。 地区： {}， 医院： {}.", new Object[] { areaCode, hospitalCode });
			}
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
		}

		return deptMap;
	}

}
