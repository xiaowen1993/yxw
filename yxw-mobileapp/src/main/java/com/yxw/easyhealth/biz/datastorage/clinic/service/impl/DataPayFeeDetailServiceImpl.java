package com.yxw.easyhealth.biz.datastorage.clinic.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataPayFeeDetailDao;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataPayFeeDetailService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

@Service(value = "dataPayFeeDetailService")
public class DataPayFeeDetailServiceImpl implements DataPayFeeDetailService {
	private DataPayFeeDetailDao dataPayFeeDetailDao = SpringContextHolder.getBean(DataPayFeeDetailDao.class);

	@Override
	public void add(DataPayFeeDetail detail) {
		// TODO Auto-generated method stub
		if (detail != null) {
			dataPayFeeDetailDao.add(detail);
		}
	}

	@Override
	public void batchInsert(List<DataPayFeeDetail> details) {
		// TODO Auto-generated method stub
		if (!CollectionUtils.isEmpty(details)) {
			dataPayFeeDetailDao.batchInsert(details);
		}
	}

	@Override
	public List<DataPayFeeDetail> findPayFeeDetails(DataPayFee dataPayFee) {
		// TODO Auto-generated method stub
		return dataPayFeeDetailDao.findPayFeeDetails(dataPayFee);
	}

}
