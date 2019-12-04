/**
\ * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.datastorage.reports.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataExamine;
import com.yxw.easyhealth.biz.datastorage.reports.service.DataExamineService;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;
import com.yxw.easyhealth.datas.manager.ReportsBizManager;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.inspection.Examine;
import com.yxw.interfaces.vo.inspection.ExamineDetail;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.thread
 * @ClassName: SaveDataExamineRunnable
 * @Statement: <p>分线程插入检查数据</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SaveDataExamineRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(SaveDataExamineRunnable.class);
	private List<Examine> examines;
	private ReportsParamsVo reportsParamsVo;
	private List<Map<String, Object>> data;
	private DataExamineService dataExamineService = SpringContextHolder.getBean(DataExamineService.class);
	private ReportsBizManager reportsBizManager = SpringContextHolder.getBean(ReportsBizManager.class);

	public SaveDataExamineRunnable() {
		super();
	}

	public SaveDataExamineRunnable(List<Examine> examines, ReportsParamsVo reportsParamsVo) {
		super();
		this.examines = examines;
		this.reportsParamsVo = reportsParamsVo;
	}

	public SaveDataExamineRunnable(List<Map<String, Object>> data) {
		this.data = data;
	}

	@Override
	public void run() {
		if (!CollectionUtils.isEmpty(examines)) {
			saveData();
		} else {
			saveDataEx();
		}
	}

	private void saveData() {
		Long l = new Date().getTime();
		if (examines != null && examines.size() > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hospitalCode", reportsParamsVo.getHospitalCode());
			paramMap.put("branchHospitalCode", reportsParamsVo.getBranchHospitalCode());
			List<String> checkIds = new ArrayList<String>();
			for (Examine examine : examines) {
				checkIds.add(examine.getCheckId());
			}
			paramMap.put("checkIds", checkIds);

			List<String> existsCheckIds = dataExamineService.findByBranchHospitalCodeAndcheckIds(paramMap);

			List<DataExamine> insertList = new ArrayList<DataExamine>();
			for (Examine examine : examines) {
				if (!existsCheckIds.contains(examine.getCheckId())) {
					if (logger.isDebugEnabled()) {
						logger.debug("分院不存在当前检查数据,分院 : {},检查ID : {}", reportsParamsVo.getBranchHospitalCode(), examine.getCheckId());
					}
					DataExamine dataExamine = new DataExamine();
					try {
						BeanUtils.copyProperties(dataExamine, examine);
					} catch (Exception e) {
						logger.error("copy examine data error. errorMsg: {}. cause: {}", new Object[] { e.getMessage(), e.getCause() });
						continue;
					}
					dataExamine.setHospitalId(reportsParamsVo.getHospitalId());
					dataExamine.setHospitalCode(reportsParamsVo.getHospitalCode());
					dataExamine.setHospitalName(reportsParamsVo.getHospitalName());
					dataExamine.setBranchHospitalCode(reportsParamsVo.getBranchHospitalCode());
					dataExamine.setBranchHospitalId(reportsParamsVo.getBranchHospitalId());
					dataExamine.setBranchHospitalName(reportsParamsVo.getBranchHospitalName());
					dataExamine.setName(reportsParamsVo.getPatCardName());
					dataExamine.setCardNo(reportsParamsVo.getPatCardNo());
					dataExamine.setIdNo(reportsParamsVo.getIdNo());
					dataExamine.setCardType(Integer.valueOf(reportsParamsVo.getPatCardType()));
					dataExamine.setStorageTime(System.currentTimeMillis());
					dataExamine.setId(PKGenerator.generateId());

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("hospitalCode", dataExamine.getHospitalCode());
					params.put("branchHospitalCode", dataExamine.getBranchHospitalCode());
					params.put("checkId", dataExamine.getCheckId());
					params.put("checkType", dataExamine.getCheckType());
					ExamineDetail detail = reportsBizManager.getExamineDetail(params);
					if (detail != null) {
						dataExamine.setCheckPart(detail.getCheckPart());
						dataExamine.setCheckMethod(detail.getCheckMethod());
						dataExamine.setCheckSituation(detail.getCheckSituation());
						dataExamine.setOption(detail.getOption());
						dataExamine.setAdvice(detail.getAdvice());
					}
					insertList.add(dataExamine);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("分院已经存在当前检查数据,分院 : {},检查ID : {}", reportsParamsVo.getBranchHospitalCode(), examine.getCheckId());
					}
				}
			}
			if (insertList.size() > 0) {
				dataExamineService.batchInsert(insertList);
			}
		}
		logger.info("批量插入检验及检验详细数据耗时: {} 毫秒", ( System.currentTimeMillis() - l ));
	}

	@SuppressWarnings("unchecked")
	private void saveDataEx() {
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> dataMap = data.get(i);

			List<Examine> examineList = (List<Examine>) dataMap.get(BizConstant.COMMON_ENTITY_LIST_KEY);
			ReportsParamsVo reportVo = new ReportsParamsVo();
			reportVo.setHospitalCode((String) dataMap.get("hospitalCode"));
			reportVo.setHospitalId((String) dataMap.get("hospitalId"));
			reportVo.setHospitalName((String) dataMap.get("hospitalName"));
			reportVo.setBranchHospitalCode((String) dataMap.get("branchCode"));
			reportVo.setBranchHospitalId((String) dataMap.get("branchId"));
			reportVo.setBranchHospitalName((String) dataMap.get("branchName"));
			reportVo.setIdNo((String) dataMap.get("IdNo"));
			reportVo.setPatCardType((Integer) dataMap.get("patCardType") + "");
			reportVo.setPatCardNo((String) dataMap.get("patCardNo"));
			reportVo.setPatCardName((String) dataMap.get("patCardName"));

			Long l = new Date().getTime();
			if (examineList != null && examineList.size() > 0) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("hospitalCode", reportVo.getHospitalCode());
				paramMap.put("branchHospitalCode", reportVo.getBranchHospitalCode());
				List<String> checkIds = new ArrayList<String>();
				for (Examine examine : examineList) {
					checkIds.add(examine.getCheckId());
				}
				paramMap.put("checkIds", checkIds);

				List<String> existsCheckIds = dataExamineService.findByBranchHospitalCodeAndcheckIds(paramMap);

				List<DataExamine> insertList = new ArrayList<DataExamine>();
				for (Examine examine : examineList) {
					if (!existsCheckIds.contains(examine.getCheckId())) {
						if (logger.isDebugEnabled()) {
							logger.debug("分院不存在当前检查数据,分院 : {},检查ID : {}", reportVo.getBranchHospitalCode(), examine.getCheckId());
						}
						DataExamine dataExamine = new DataExamine();
						try {
							BeanUtils.copyProperties(dataExamine, examine);
						} catch (Exception e) {
							logger.error("copy examine data error. errorMsg: {}. cause: {}", new Object[] { e.getMessage(), e.getCause() });
							continue;
						}
						dataExamine.setHospitalId(reportVo.getHospitalId());
						dataExamine.setHospitalCode(reportVo.getHospitalCode());
						dataExamine.setHospitalName(reportVo.getHospitalName());
						dataExamine.setBranchHospitalCode(reportVo.getBranchHospitalCode());
						dataExamine.setBranchHospitalId(reportVo.getBranchHospitalId());
						dataExamine.setBranchHospitalName(reportVo.getBranchHospitalName());
						dataExamine.setName(reportVo.getPatCardName());
						dataExamine.setCardNo(reportVo.getPatCardNo());
						dataExamine.setIdNo(reportVo.getIdNo());
						dataExamine.setCardType(Integer.valueOf(reportVo.getPatCardType()));
						dataExamine.setStorageTime(System.currentTimeMillis());
						dataExamine.setId(PKGenerator.generateId());

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("hospitalCode", dataExamine.getHospitalCode());
						params.put("branchHospitalCode", dataExamine.getBranchHospitalCode());
						params.put("checkId", dataExamine.getCheckId());
						params.put("checkType", dataExamine.getCheckType());
						ExamineDetail detail = reportsBizManager.getExamineDetail(params);
						if (detail != null) {
							dataExamine.setCheckPart(detail.getCheckPart());
							dataExamine.setCheckMethod(detail.getCheckMethod());
							dataExamine.setCheckSituation(detail.getCheckSituation());
							dataExamine.setOption(detail.getOption());
							dataExamine.setAdvice(detail.getAdvice());
						}
						insertList.add(dataExamine);
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("分院已经存在当前检查数据,分院 : {},检查ID : {}", reportVo.getBranchHospitalCode(), examine.getCheckId());
						}
					}
				}
				if (insertList.size() > 0) {
					dataExamineService.batchInsert(insertList);
				}
			}
			logger.info("批量插入检验及检验详细数据耗时: {} 毫秒", ( System.currentTimeMillis() - l ));
		}

	}
}
