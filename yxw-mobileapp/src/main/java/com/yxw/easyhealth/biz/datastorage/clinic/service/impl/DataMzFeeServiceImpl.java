package com.yxw.easyhealth.biz.datastorage.clinic.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataMzFeeDao;
import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataMzFeeDetailDao;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataMzFeeService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.datastorage.clinic.service.impl
 * @ClassName: DataMzFeeServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-8-11
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "dataMzFeeService")
public class DataMzFeeServiceImpl implements DataMzFeeService {

	private DataMzFeeDao dataMZFeeDao = SpringContextHolder.getBean(DataMzFeeDao.class);

	private DataMzFeeDetailDao dataMZFeeDetailDao = SpringContextHolder.getBean(DataMzFeeDetailDao.class);

	@Override
	public List<DataMzFee> findByBranchHospitalCodeAndMzFeeId(DataMzFee dataMZFee) {
		return dataMZFeeDao.findByBranchHospitalCodeAndMzFeeId(dataMZFee);
	}

	@Override
	public void add(DataMzFee dataMZFee) {
		dataMZFeeDao.add(dataMZFee);
	}

	@Override
	public void batchInsert(List<DataMzFee> dataMZFees) {
		if (!CollectionUtils.isEmpty(dataMZFees)) {
			dataMZFeeDao.batchInsert(dataMZFees);
		}
	}

	@Override
	public void batchInsertAndInspectDetail(List<DataMzFee> dataMZFees, List<DataMzFeeDetail> dataMZFeeDetails) {
		if (!CollectionUtils.isEmpty(dataMZFees)) {
			dataMZFeeDao.batchInsert(dataMZFees);
		}

		if (!CollectionUtils.isEmpty(dataMZFeeDetails)) {
			dataMZFeeDetailDao.batchInsert(dataMZFeeDetails);
		}
	}

	@Override
	public List<String> findByBranchHospitalCodeAndMzFeeIds(Map<String, Object> params) {
		return dataMZFeeDao.findByBranchHospitalCodeAndMzFeeIds(params);
	}

}
