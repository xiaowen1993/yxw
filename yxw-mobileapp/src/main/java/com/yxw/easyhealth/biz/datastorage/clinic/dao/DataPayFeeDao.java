package com.yxw.easyhealth.biz.datastorage.clinic.dao;

import java.util.List;
import java.util.Map;

import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao
 * @ClassName: DataMZFeeDao
 * @Statement: <p>门诊已缴费记录</p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-17
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataPayFeeDao extends BaseDao<DataPayFee, String> {

	/**
	 * 通过分院代码和缴费编号去查询数据
	 * @param dataMZFee
	 * @return
	 */
	public List<DataPayFee> findByBranchHospitalCodeAndMzFeeId(DataPayFee dataPayFee);

	/**
	 * 检测已经存在的缴费编号
	 * @param params
	 * @return
	 */
	public List<String> findByBranchHospitalCodeAndMzFeeIds(Map<String, Object> params);

}
