package com.yxw.easyhealth.biz.datastorage.clinic.dao;

import java.util.List;

import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFeeDetail;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 门诊待缴费明细Dao
 * @Package: com.yxw.mobileapp.biz.datastorage.dao
 * @ClassName: DataMZFeeDetailDao
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-17
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataMzFeeDetailDao extends BaseDao<DataMzFeeDetail, String> {

	/**
	 * 通过分院代码、缴费编号、项目编号找数据
	 * @param dataMZFeeDetail
	 * @return
	 */
	public List<DataMzFeeDetail> findByBranchHospitalCodeAndFeeIdAndItemId(DataMzFeeDetail dataMZFeeDetail);

}
