package com.yxw.easyhealth.biz.datastorage.clinic.thread;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataPayFeeService;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.clinicpay.Detail;
import com.yxw.interfaces.vo.clinicpay.PayFeeDetail;
import com.yxw.mobileapp.biz.clinic.vo.ClinicQueryVo;
import com.yxw.mobileapp.datas.manager.ClinicBizManager;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.thread
 * @ClassName: SaveDataMZFeeRunnable
 * @Statement: <p>门诊已缴费记录入库</p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-8-10
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class SaveDataPayFeeRunnable implements Runnable {

	private Logger logger = LoggerFactory.getLogger(SaveDataPayFeeRunnable.class);

	private ClinicBizManager clinicBizManager = SpringContextHolder.getBean(ClinicBizManager.class);

	private DataPayFeeService service = SpringContextHolder.getBean(DataPayFeeService.class);

	private List<ClinicRecord> records;

	//	private ClinicRecord qryRecord;
	private ClinicQueryVo vo;

	public SaveDataPayFeeRunnable() {
		super();
	}

	public SaveDataPayFeeRunnable(List<ClinicRecord> records, ClinicQueryVo vo) {
		super();
		this.records = records;
		this.vo = vo;
	}

	@Override
	public void run() {
		Long startTime = System.currentTimeMillis();

		if (records != null && records.size() > 0) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("hospitalCode", vo.getHospitalCode());
			paramMap.put("branchHospitalCode", vo.getBranchHospitalCode());
			List<String> mzFeeIds = new ArrayList<String>();
			for (ClinicRecord record : records) {
				mzFeeIds.add(record.getMzFeeId());
			}
			paramMap.put("mzFeeIds", mzFeeIds);

			List<String> existsMZFeeIds = service.findByBranchHospitalCodeAndMzFeeIds(paramMap);

			List<DataPayFee> dataPayFees = new ArrayList<DataPayFee>();
			List<DataPayFeeDetail> dataPayFeeDetails = new ArrayList<DataPayFeeDetail>();
			for (ClinicRecord record : records) {
				if (!existsMZFeeIds.contains(record.getMzFeeId())) {
					if (logger.isDebugEnabled()) {
						logger.debug("分院[{}]不存在数据[{}]", vo.getBranchHospitalCode(), record.getMzFeeId());
					}

					DataPayFee dataPayFee = new DataPayFee();
					try {
						org.springframework.beans.BeanUtils.copyProperties(record, dataPayFee);
					} catch (Exception e) {
						logger.error("copy dataPayFee data error. errorMsg: {}. cause: {}", new Object[] { e.getMessage(), e.getCause() });
						continue;
					}

					if (StringUtils.isNotBlank(record.getHisMessage())) {
						try {
							dataPayFee.setHisMessage(URLDecoder.decode(record.getHisMessage(), "utf-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					dataPayFee.setPayAmout(record.getPayFee());
					dataPayFee.setHisOrdNum(record.getHisOrdNo());
					dataPayFee.setPayMode(record.getPlatformMode().toString());
					dataPayFee.setPayTime(record.getPayTimeLabel());
					dataPayFee.setName(record.getPatientName());
					dataPayFee.setId(PKGenerator.generateId());
					dataPayFee.setStorageTime(System.currentTimeMillis());
					dataPayFee.setMzFeeId(record.getMzFeeId());

					// 查详情信息
					Map<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("branchHospitalCode", record.getBranchHospitalCode());
					paramsMap.put("hospitalCode", record.getHospitalCode());
					paramsMap.put("mzFeeId", record.getMzFeeId());
					paramsMap.put("hisOrdNum", record.getHisOrdNo());
					paramsMap.put("receiptNum", record.getReceiptNum());
					List<PayFeeDetail> payFeeDetails = clinicBizManager.getPayDetail(paramsMap);
					if (logger.isDebugEnabled()) {
						logger.debug("mzFeeDetails: {}", JSON.toJSONString(payFeeDetails));
					}
					if (!CollectionUtils.isEmpty(payFeeDetails)) {
						for (PayFeeDetail payFeeDetail : payFeeDetails) {
							for (Detail detail : payFeeDetail.getDetails()) {
								DataPayFeeDetail dataPayFeeDetail = new DataPayFeeDetail();
								try {
									BeanUtils.copyProperties(dataPayFeeDetail, detail);
									dataPayFeeDetail.setMzFeeId(payFeeDetail.getMzFeeId());

									dataPayFeeDetails.add(dataPayFeeDetail);
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								} catch (InvocationTargetException e) {
									e.printStackTrace();
								}
							}
						}
					}
					dataPayFees.add(dataPayFee);
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("分院[{}]存在相同数据[{}]", vo.getBranchHospitalCode(), record.getMzFeeId());
					}
				}
			}

			if (dataPayFees.size() > 0) {
				service.batchInsertAndInspectDetail(dataPayFees, dataPayFeeDetails);
			}
		}

		logger.info("---------------------------------------门诊已缴费数据入库, 耗时：" + ( System.currentTimeMillis() - startTime )
				+ "ms --------------------------------------");
	}

}
