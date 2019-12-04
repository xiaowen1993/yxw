package com.yxw.easyhealth.biz.datastorage.clinic.dao;

import java.util.List;
import java.util.Map;

import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFee;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 门诊待缴费记录Dao
 * @Package: com.yxw.mobileapp.biz.datastorage.dao
 * @ClassName: DataMZFeeDao
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-17
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataMzFeeDao extends BaseDao<DataMzFee, String> {

	/**
	 * 通过分院代码和缴费编号去查询数据
	 * @param dataMZFee
	 * @return
	 */
	public List<DataMzFee> findByBranchHospitalCodeAndMzFeeId(DataMzFee dataMZFee);

	public List<String> findByBranchHospitalCodeAndMzFeeIds(Map<String, Object> params);

}
