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
package com.yxw.easyhealth.biz.datastorage.reports.thread;

import java.lang.reflect.InvocationTargetException;
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
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspect;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspectDetail;
import com.yxw.easyhealth.biz.datastorage.reports.service.DataInspectService;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;
import com.yxw.easyhealth.datas.manager.ReportsBizManager;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.inspection.Inspect;
import com.yxw.interfaces.vo.inspection.InspectDetail;

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
public class SaveDataInspectRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(SaveDataInspectRunnable.class);
	private List<Inspect> inspects;
	private ReportsParamsVo reportsParamsVo;
	private List<Map<String, Object>> data;
	private DataInspectService dataInspectService = SpringContextHolder.getBean(DataInspectService.class);
	private ReportsBizManager reportsBizManager = SpringContextHolder.getBean(ReportsBizManager.class);

	public SaveDataInspectRunnable() {
		super();
	}

	public SaveDataInspectRunnable(List<Inspect> inspects, ReportsParamsVo reportsParamsVo) {
		super();
		this.inspects = inspects;
		this.reportsParamsVo = reportsParamsVo;
	}

	public SaveDataInspectRunnable(List<Map<String, Object>> data) {
		this.data = data;
	}

	@Override
	public void run() {
		if (!CollectionUtils.isEmpty(inspects)) {
			saveData();
		} else {
			saveDataEx();
		}
	}

	private void saveData() {
		Long l = new Date().getTime();

		if (inspects != null && inspects.size() > 0) {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("hospitalCode", reportsParamsVo.getHospitalCode());
			List<String> inspectIds = new ArrayList<String>();
			paramsMap.put("branchHospitalCode", reportsParamsVo.getBranchHospitalCode());

			for (Inspect inspect : inspects) {
				inspectIds.add(inspect.getInspectId());
			}
			paramsMap.put("inspectIds", inspectIds);

			List<String> existsInspectIds = dataInspectService.findByBranchHospitalCodeAndInspectIds(paramsMap);
			if (logger.isDebugEnabled()) {
				logger.debug("已存在的inspectIds: " + existsInspectIds.toString());
			}

			// 需要插入的列表
			List<DataInspect> insertList = new ArrayList<DataInspect>();
			// 需要插入的详情
			List<DataInspectDetail> insertDetailList = new ArrayList<DataInspectDetail>();
			for (Inspect inspect : inspects) {
				if (!existsInspectIds.contains(inspect.getInspectId())) {
					if (logger.isDebugEnabled()) {
						logger.debug("分院不存在当前检验数据,分院 : {},检查ID : {}", reportsParamsVo.getBranchHospitalCode(), inspect.getInspectId());
					}
					DataInspect dataInspect = new DataInspect();

					//不要使用1.7的写法!
					try {
						BeanUtils.copyProperties(dataInspect, inspect);
					} catch (Exception e) {
						logger.error("copy inspect data error. errorMsg: {}, cause: {}", new Object[] { e.getMessage(), e.getCause() });
						continue;
					}

					dataInspect.setHospitalId(reportsParamsVo.getHospitalId());
					dataInspect.setHospitalCode(reportsParamsVo.getHospitalCode());
					dataInspect.setHospitalName(reportsParamsVo.getHospitalName());
					dataInspect.setBranchHospitalCode(reportsParamsVo.getBranchHospitalCode());
					dataInspect.setBranchHospitalId(reportsParamsVo.getBranchHospitalId());
					dataInspect.setBranchHospitalName(reportsParamsVo.getBranchHospitalName());
					dataInspect.setName(reportsParamsVo.getPatCardName());
					dataInspect.setCardType(Integer.valueOf(reportsParamsVo.getPatCardType()));
					dataInspect.setCardNo(reportsParamsVo.getPatCardNo());
					dataInspect.setIdNo(reportsParamsVo.getIdNo());
					dataInspect.setStorageTime(System.currentTimeMillis());
					dataInspect.setId(PKGenerator.generateId());

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("hospitalCode", dataInspect.getHospitalCode());
					params.put("branchHospitalCode", dataInspect.getBranchHospitalCode());
					params.put("inspectId", dataInspect.getInspectId());
					List<InspectDetail> details = reportsBizManager.getInspectDetail(params);
					if (details != null && details.size() > 0) {
						for (InspectDetail inspectDetail : details) {
							DataInspectDetail dataInspectDetail = new DataInspectDetail();
							try {
								BeanUtils.copyProperties(dataInspectDetail, inspectDetail);
								dataInspectDetail.setInspectId(dataInspect.getId());
								insertDetailList.add(dataInspectDetail);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
					insertList.add(dataInspect);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("分院已经存在当前检验数据,分院 : {},检查ID : {}", reportsParamsVo.getBranchHospitalCode(), inspect.getInspectId());
					}
				}
			}

			if (insertList.size() > 0) {
				dataInspectService.batchInsertAndInspectDetail(insertList, insertDetailList);
			}
		}
		logger.info("批量插入检验及检验详细数据耗时: {} 毫秒", ( System.currentTimeMillis() - l ));
	}

	private void saveDataEx() {
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> dataMap = data.get(i);

			@SuppressWarnings("unchecked")
			List<Inspect> inspectList = (List<Inspect>) dataMap.get(BizConstant.COMMON_ENTITY_LIST_KEY);

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

			if (inspectList != null && inspectList.size() > 0) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("hospitalCode", reportVo.getHospitalCode());
				List<String> inspectIds = new ArrayList<String>();
				paramsMap.put("branchHospitalCode", reportVo.getBranchHospitalCode());

				for (Inspect inspect : inspectList) {
					inspectIds.add(inspect.getInspectId());
				}
				paramsMap.put("inspectIds", inspectIds);

				List<String> existsInspectIds = dataInspectService.findByBranchHospitalCodeAndInspectIds(paramsMap);
				if (logger.isDebugEnabled()) {
					logger.debug("已存在的inspectIds: " + existsInspectIds.toString());
				}

				// 需要插入的列表
				List<DataInspect> insertList = new ArrayList<DataInspect>();
				// 需要插入的详情
				List<DataInspectDetail> insertDetailList = new ArrayList<DataInspectDetail>();
				for (Inspect inspect : inspectList) {
					if (!existsInspectIds.contains(inspect.getInspectId())) {
						if (logger.isDebugEnabled()) {
							logger.debug("分院不存在当前检验数据,分院 : {},检查ID : {}", reportVo.getBranchHospitalCode(), inspect.getInspectId());
						}
						DataInspect dataInspect = new DataInspect();

						//不要使用1.7的写法!
						try {
							BeanUtils.copyProperties(dataInspect, inspect);
						} catch (Exception e) {
							logger.error("copy inspect data error. errorMsg: {}, cause: {}", new Object[] { e.getMessage(), e.getCause() });
							continue;
						}

						dataInspect.setHospitalId(reportVo.getHospitalId());
						dataInspect.setHospitalCode(reportVo.getHospitalCode());
						dataInspect.setHospitalName(reportVo.getHospitalName());
						dataInspect.setBranchHospitalCode(reportVo.getBranchHospitalCode());
						dataInspect.setBranchHospitalId(reportVo.getBranchHospitalId());
						dataInspect.setBranchHospitalName(reportVo.getBranchHospitalName());
						dataInspect.setName(reportVo.getPatCardName());
						dataInspect.setCardType(Integer.valueOf(reportVo.getPatCardType()));
						dataInspect.setCardNo(reportVo.getPatCardNo());
						dataInspect.setIdNo(reportVo.getIdNo());
						dataInspect.setStorageTime(System.currentTimeMillis());
						dataInspect.setId(PKGenerator.generateId());

						Map<String, Object> params = new HashMap<String, Object>();
						params.put("hospitalCode", dataInspect.getHospitalCode());
						params.put("branchHospitalCode", dataInspect.getBranchHospitalCode());
						params.put("inspectId", dataInspect.getInspectId());
						List<InspectDetail> details = reportsBizManager.getInspectDetail(params);
						if (details != null && details.size() > 0) {
							for (InspectDetail inspectDetail : details) {
								DataInspectDetail dataInspectDetail = new DataInspectDetail();
								try {
									BeanUtils.copyProperties(dataInspectDetail, inspectDetail);
									dataInspectDetail.setInspectId(dataInspect.getId());
									insertDetailList.add(dataInspectDetail);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
						insertList.add(dataInspect);
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("分院已经存在当前检验数据,分院 : {},检查ID : {}", reportVo.getBranchHospitalCode(), inspect.getInspectId());
						}
					}
				}

				if (insertList.size() > 0) {
					dataInspectService.batchInsertAndInspectDetail(insertList, insertDetailList);
				}
			}
			logger.info("批量插入检验及检验详细数据耗时: {} 毫秒", ( System.currentTimeMillis() - l ));
		}

	}
}
