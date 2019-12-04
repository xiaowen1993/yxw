package com.yxw.easyhealth.biz.datastorage.clinic.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataPayFeeDao;
import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataPayFeeDetailDao;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataPayFeeService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

@Service(value = "dataPayFeeService")
public class DataPayFeeServiceImpl implements DataPayFeeService {

	private DataPayFeeDao dataPayFeeDao = SpringContextHolder.getBean(DataPayFeeDao.class);

	private DataPayFeeDetailDao dataPayFeeDetailDao = SpringContextHolder.getBean(DataPayFeeDetailDao.class);

	@Override
	public List<DataPayFee> findByBranchHospitalCodeAndMzFeeId(DataPayFee dataPayFee) {
		return dataPayFeeDao.findByBranchHospitalCodeAndMzFeeId(dataPayFee);
	}

	@Override
	public void add(DataPayFee dataPayFee) {
		if (dataPayFee != null) {
			dataPayFeeDao.add(dataPayFee);
		}
	}

	@Override
	public void batchInsert(List<DataPayFee> dataPayFees) {
		if (!CollectionUtils.isEmpty(dataPayFees)) {
			dataPayFeeDao.batchInsert(dataPayFees);
		}
	}

	@Override
	public void batchInsertAndInspectDetail(List<DataPayFee> dataPayFees, List<DataPayFeeDetail> dataPayFeeDetails) {
		if (!CollectionUtils.isEmpty(dataPayFees)) {
			dataPayFeeDao.batchInsert(dataPayFees);
		}

		if (!CollectionUtils.isEmpty(dataPayFeeDetails)) {
			dataPayFeeDetailDao.batchInsert(dataPayFeeDetails);
		}
	}

	@Override
	public List<String> findByBranchHospitalCodeAndMzFeeIds(Map<String, Object> params) {
		return dataPayFeeDao.findByBranchHospitalCodeAndMzFeeIds(params);
	}

}
