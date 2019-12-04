package com.yxw.easyhealth.biz.datastorage.clinic.thread;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataMzFeeService;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.clinicpay.MZFee;
import com.yxw.interfaces.vo.clinicpay.MZFeeDetail;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.thread
 * @ClassName: SaveDataMZFeeRunnable
 * @Statement: <p>门诊待缴费记录入库</p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-8-10
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SaveDataMZFeeRunnable implements Runnable {

	private Logger logger = LoggerFactory.getLogger(SaveDataMZFeeRunnable.class);

	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);

	private DataMzFeeService service = SpringContextHolder.getBean(DataMzFeeService.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private List<MZFee> mzFees;

	private ClinicPayVo clinicPayVo;

	private List<Map<String, Object>> data;

	public SaveDataMZFeeRunnable() {
		super();
	}

	public SaveDataMZFeeRunnable(List<MZFee> mzFees, ClinicPayVo clinicPayVo) {
		super();
		this.mzFees = mzFees;
		this.clinicPayVo = clinicPayVo;
	}

	public SaveDataMZFeeRunnable(List<Map<String, Object>> data) {
		this.data = data;
	}

	@Override
	public void run() {
		if (!CollectionUtils.isEmpty(mzFees)) {
			saveData();
		} else {
			saveDataEx();
		}
	}

	public void saveData() {
		Long startTime = System.currentTimeMillis();

		if (mzFees != null && mzFees.size() > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hospitalCode", clinicPayVo.getHospitalCode());
			paramMap.put("branchHospitalCode", clinicPayVo.getBranchHospitalCode());
			List<String> mzFeeIds = new ArrayList<String>();
			for (MZFee mzFee : mzFees) {
				mzFeeIds.add(mzFee.getMzFeeId());
			}
			paramMap.put("mzFeeIds", mzFeeIds);

			List<String> existsMzFeeIds = service.findByBranchHospitalCodeAndMzFeeIds(paramMap);

			List<DataMzFee> dataMZFees = new ArrayList<DataMzFee>();
			// DataMZFee 和 MZFee
			List<DataMzFeeDetail> dataMZFeeDetails = new ArrayList<DataMzFeeDetail>();
			for (MZFee mzFee : mzFees) {
				if (!existsMzFeeIds.contains(mzFee.getMzFeeId())) {
					if (logger.isDebugEnabled()) {
						logger.debug("分院[{}]不存在数据[{}]", clinicPayVo.getBranchHospitalCode(), mzFee.getMzFeeId());
					}
					DataMzFee dataMzFee = new DataMzFee();

					try {
						org.springframework.beans.BeanUtils.copyProperties(mzFee, dataMzFee);
					} catch (Exception e) {
						logger.error("copy dataMZFee data error. errorMsg: {}. cause: {}", new Object[] { e.getMessage(), e.getCause() });
						continue;
					}
					dataMzFee.setClinicTime(mzFee.getTime());
					dataMzFee.setHospitalId(clinicPayVo.getHospitalId());
					dataMzFee.setHospitalCode(clinicPayVo.getHospitalCode());
					dataMzFee.setHospitalName(clinicPayVo.getHospitalName());
					dataMzFee.setBranchHospitalId(clinicPayVo.getBranchHospitalId());
					dataMzFee.setBranchHospitalName(clinicPayVo.getBranchHospitalName());
					dataMzFee.setBranchHospitalCode(clinicPayVo.getBranchHospitalCode());
					dataMzFee.setName(clinicPayVo.getPatientName());
					dataMzFee.setCardNo(clinicPayVo.getCardNo());
					dataMzFee.setCardType(Integer.valueOf(clinicPayVo.getCardType()));
					dataMzFee.setId(PKGenerator.generateId());
					dataMzFee.setStorageTime(System.currentTimeMillis());

					// 查详情信息
					Map<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("branchHospitalCode", clinicPayVo.getBranchHospitalCode());
					paramsMap.put("hospitalCode", clinicPayVo.getHospitalCode());
					paramsMap.put("mzFeeId", mzFee.getMzFeeId());
					paramsMap.put("appCode", clinicPayVo.getAppCode());

					if (StringUtils.isEmpty(clinicPayVo.getBranchHospitalCode())
							|| "null".equalsIgnoreCase(clinicPayVo.getBranchHospitalCode())) {
						// HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
						// CodeAndInterfaceVo codeAndInterfaceVo = hospitalCache.getDefCodeAndInterfaceVo(clinicPayVo.getHospitalCode());
						CodeAndInterfaceVo codeAndInterfaceVo = null;
						List<Object> params = new ArrayList<Object>();
						params.add(clinicPayVo.getHospitalCode());
						List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
						if (CollectionUtils.isNotEmpty(results)) {
							codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);
						}

						if (logger.isDebugEnabled()) {
							logger.debug("getPayDetail.codeAndInterfaceVo:{}", JSON.toJSONString(codeAndInterfaceVo));
						}
						paramsMap.put("branchHospitalCode", codeAndInterfaceVo.getBranchHospitalCode());
					}

					List<MZFeeDetail> mzFeeDetails = clinicBizManager.getMZFeeDetail(paramsMap);
					if (logger.isDebugEnabled()) {
						logger.debug("mzFeeDetails: {}", JSON.toJSONString(mzFeeDetails));
					}
					if (!CollectionUtils.isEmpty(mzFeeDetails)) {
						for (MZFeeDetail mzFeeDetail : mzFeeDetails) {
							DataMzFeeDetail dataMzFeeDetail = new DataMzFeeDetail();
							try {
								BeanUtils.copyProperties(dataMzFeeDetail, mzFeeDetail);
								dataMzFeeDetail.setMzFeeId(dataMzFee.getMzFeeId());

								dataMZFeeDetails.add(dataMzFeeDetail);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
						}
					}
					dataMZFees.add(dataMzFee);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("分院[{}]存在相同数据[{}]", clinicPayVo.getBranchHospitalCode(), mzFee.getMzFeeId());
					}
				}
			}

			if (dataMZFees.size() > 0) {
				service.batchInsertAndInspectDetail(dataMZFees, dataMZFeeDetails);
			}

		}

		logger.info("---------------------------------------门诊代缴费数据入库, 耗时：" + ( System.currentTimeMillis() - startTime )
				+ "ms --------------------------------------");
	}

	@SuppressWarnings("unchecked")
	public void saveDataEx() {
		for (int i = 0; i < data.size(); i++) {
			Map<String, Object> dataMap = data.get(i);
			List<MZFee> feeList = (List<MZFee>) dataMap.get(BizConstant.COMMON_ENTITY_LIST_KEY);
			ClinicPayVo payVo = new ClinicPayVo();
			payVo.setHospitalCode((String) dataMap.get("hospitalCode"));
			payVo.setHospitalId((String) dataMap.get("hospitalId"));
			payVo.setHospitalName((String) dataMap.get("hospitalName"));
			payVo.setBranchHospitalCode((String) dataMap.get("branchCode"));
			payVo.setBranchHospitalId((String) dataMap.get("branchId"));
			payVo.setBranchHospitalName((String) dataMap.get("branchName"));
			payVo.setCardType((Integer) dataMap.get("patCardType") + "");
			payVo.setCardNo((String) dataMap.get("patCardNo"));
			payVo.setPatientName((String) dataMap.get("patCardName"));

			Long startTime = System.currentTimeMillis();

			if (feeList != null && feeList.size() > 0) {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("hospitalCode", payVo.getHospitalCode());
				paramMap.put("branchHospitalCode", payVo.getBranchHospitalCode());
				List<String> mzFeeIds = new ArrayList<String>();
				for (MZFee mzFee : feeList) {
					mzFeeIds.add(mzFee.getMzFeeId());
				}
				paramMap.put("mzFeeIds", mzFeeIds);

				List<String> existsMzFeeIds = service.findByBranchHospitalCodeAndMzFeeIds(paramMap);

				List<DataMzFee> dataMZFees = new ArrayList<DataMzFee>();
				// DataMZFee 和 MZFee
				List<DataMzFeeDetail> dataMZFeeDetails = new ArrayList<DataMzFeeDetail>();
				for (MZFee mzFee : feeList) {
					if (!existsMzFeeIds.contains(mzFee.getMzFeeId())) {
						if (logger.isDebugEnabled()) {
							logger.debug("分院[{}]不存在数据[{}]", payVo.getBranchHospitalCode(), mzFee.getMzFeeId());
						}
						DataMzFee dataMzFee = new DataMzFee();

						try {
							BeanUtils.copyProperties(dataMzFee, mzFee);
						} catch (Exception e) {
							logger.error("copy dataMZFee data error. errorMsg: {}. cause: {}",
									new Object[] { e.getMessage(), e.getCause() });
							continue;
						}
						dataMzFee.setClinicTime(mzFee.getTime());
						dataMzFee.setHospitalId(payVo.getHospitalId());
						dataMzFee.setHospitalCode(payVo.getHospitalCode());
						dataMzFee.setHospitalName(payVo.getHospitalName());
						dataMzFee.setBranchHospitalId(payVo.getBranchHospitalId());
						dataMzFee.setBranchHospitalName(payVo.getBranchHospitalName());
						dataMzFee.setBranchHospitalCode(payVo.getBranchHospitalCode());
						dataMzFee.setName(payVo.getPatientName());
						dataMzFee.setCardNo(payVo.getCardNo());
						dataMzFee.setCardType(Integer.valueOf(payVo.getCardType()));
						dataMzFee.setId(PKGenerator.generateId());
						dataMzFee.setStorageTime(System.currentTimeMillis());

						// 查详情信息
						Map<String, String> paramsMap = new HashMap<String, String>();
						paramsMap.put("branchHospitalCode", payVo.getBranchHospitalCode());
						paramsMap.put("hospitalCode", payVo.getHospitalCode());
						paramsMap.put("mzFeeId", mzFee.getMzFeeId());
						paramsMap.put("appCode", BizConstant.MODE_TYPE_APP);

						if (StringUtils.isEmpty(payVo.getBranchHospitalCode()) || "null".equalsIgnoreCase(payVo.getBranchHospitalCode())) {
							// HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
							// CodeAndInterfaceVo codeAndInterfaceVo = hospitalCache.getDefCodeAndInterfaceVo(payVo.getHospitalCode());
							CodeAndInterfaceVo codeAndInterfaceVo = null;
							List<Object> params = new ArrayList<Object>();
							params.add(clinicPayVo.getHospitalCode());
							List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getDefCodeAndInterfaceVo", params);
							if (CollectionUtils.isNotEmpty(results)) {
								codeAndInterfaceVo = (CodeAndInterfaceVo) results.get(0);
							}

							if (logger.isDebugEnabled()) {
								logger.debug("getPayDetail.codeAndInterfaceVo:{}", JSON.toJSONString(codeAndInterfaceVo));
							}
							paramsMap.put("branchHospitalCode", codeAndInterfaceVo.getBranchHospitalCode());
						}

						List<MZFeeDetail> mzFeeDetails = clinicBizManager.getMZFeeDetail(paramsMap);
						if (logger.isDebugEnabled()) {
							logger.debug("mzFeeDetails: {}", JSON.toJSONString(mzFeeDetails));
						}
						if (!CollectionUtils.isEmpty(mzFeeDetails)) {
							for (MZFeeDetail mzFeeDetail : mzFeeDetails) {
								DataMzFeeDetail dataMzFeeDetail = new DataMzFeeDetail();
								try {
									BeanUtils.copyProperties(dataMzFeeDetail, mzFeeDetail);
									dataMzFeeDetail.setMzFeeId(dataMzFee.getMzFeeId());

									dataMZFeeDetails.add(dataMzFeeDetail);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
						dataMZFees.add(dataMzFee);
					} else {
						if (logger.isDebugEnabled()) {
							logger.debug("分院[{}]存在相同数据[{}]", payVo.getBranchHospitalCode(), mzFee.getMzFeeId());
						}
					}
				}

				if (dataMZFees.size() > 0) {
					service.batchInsertAndInspectDetail(dataMZFees, dataMZFeeDetails);
				}

			}

			logger.info("---------------------------------------门诊代缴费数据入库, 耗时：" + ( System.currentTimeMillis() - startTime )
					+ "ms --------------------------------------");
		}

	}

}
