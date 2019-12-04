package com.yxw.easyhealth.biz.datastorage.clinic.thread;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.vo.mobile.biz.ClinicPayVo;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataPayFeeDetailService;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataPayFeeService;
import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.clinicpay.Detail;
import com.yxw.interfaces.vo.clinicpay.PayFeeDetail;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;

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
public class SaveDataPayFeeExRunnable implements Runnable {

	private Logger logger = LoggerFactory.getLogger(SaveDataPayFeeExRunnable.class);

	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);

	private DataPayFeeService dataPayFeeService = SpringContextHolder.getBean(DataPayFeeService.class);

	private DataPayFeeDetailService dataPayFeeDetailService = SpringContextHolder.getBean(DataPayFeeDetailService.class);

	private List<PayFeeDetail> payFeeDetails;

	private ClinicPayVo vo;

	public SaveDataPayFeeExRunnable() {
		super();
	}

	public SaveDataPayFeeExRunnable(List<PayFeeDetail> payFeeDetails, ClinicPayVo vo) {
		super();
		this.payFeeDetails = payFeeDetails;
		this.vo = vo;
	}

	@Override
	public void run() {
		Long startTime = System.currentTimeMillis();

		if (!CollectionUtils.isEmpty(payFeeDetails)) {

			ClinicRecord record = clinicService.findRecordByOrderNo(vo.getOrderNo());

			DataPayFee dataPayFee = new DataPayFee();
			dataPayFee.setBranchHospitalCode(record.getBranchHospitalCode());
			dataPayFee.setMzFeeId(record.getMzFeeId());
			List<DataPayFee> dataPayFees = dataPayFeeService.findByBranchHospitalCodeAndMzFeeId(dataPayFee);

			if (CollectionUtils.isEmpty(dataPayFees)) {

				try {
					org.springframework.beans.BeanUtils.copyProperties(record, dataPayFee);
				} catch (Exception e) {
					logger.error("copy dataPayFee data error. errorMsg: {}. cause: {}", new Object[] { e.getMessage(), e.getCause() });
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

				List<DataPayFeeDetail> dataPayFeeDetails = new ArrayList<DataPayFeeDetail>();
				for (PayFeeDetail payFeeDetail : payFeeDetails) {
					for (Detail detail : payFeeDetail.getDetails()) {
						DataPayFeeDetail dataPayFeeDetail = new DataPayFeeDetail();
						BeanUtils.copyProperties(detail, dataPayFeeDetail);
						dataPayFeeDetail.setMzFeeId(payFeeDetail.getMzFeeId());

						dataPayFeeDetails.add(dataPayFeeDetail);
					}
				}

				dataPayFeeService.add(dataPayFee);
				dataPayFeeDetailService.batchInsert(dataPayFeeDetails);

			}

		}

		logger.info("---------------------------------------门诊已缴费数据入库, 耗时：" + ( System.currentTimeMillis() - startTime )
				+ "ms --------------------------------------");
	}
}
