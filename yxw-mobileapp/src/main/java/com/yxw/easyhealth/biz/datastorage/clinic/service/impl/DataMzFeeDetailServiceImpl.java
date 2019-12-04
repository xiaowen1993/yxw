package com.yxw.easyhealth.biz.datastorage.clinic.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataMzFeeDetailDao;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFeeDetail;
import com.yxw.easyhealth.biz.datastorage.clinic.service.DataMzFeeDetailService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.clinic.service.impl
 * @ClassName: DataMzFeeDetailServiceImpl
 * @Statement: <p>门诊代缴费详情入库</p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-8-11
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */

@Service(value = "dataMzFeeDetailService")
public class DataMzFeeDetailServiceImpl implements DataMzFeeDetailService {

	private DataMzFeeDetailDao dao = SpringContextHolder.getBean(DataMzFeeDetailDao.class);

	@Override
	public List<DataMzFeeDetail> findByBranchHospitalCodeAndMzFeeIdAndItemId(DataMzFeeDetail dataMZFeeDetail) {
		return dao.findByBranchHospitalCodeAndFeeIdAndItemId(dataMZFeeDetail);
	}

	@Override
	public void add(DataMzFeeDetail dataMZFeeDetail) {
		dao.add(dataMZFeeDetail);
	}

	@Override
	public void batchInsert(List<DataMzFeeDetail> dataMZFeeDetails) {
		if (!CollectionUtils.isEmpty(dataMZFeeDetails)) {
			dao.batchInsert(dataMZFeeDetails);
		}
	}

}
