/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.datastorage.clinic.service;

import java.util.List;
import java.util.Map;

import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFee;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataPayFeeDetail;

/**
 * 门诊缴费记录入库
 * @Package: com.yxw.mobileapp.biz.datastorage.clinic.service
 * @ClassName: DataMzFeeService
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-8-11
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataPayFeeService {

	/**
	 * 
	 * @param dataPayFee
	 * @return
	 */
	public List<DataPayFee> findByBranchHospitalCodeAndMzFeeId(DataPayFee dataPayFee);

	public List<String> findByBranchHospitalCodeAndMzFeeIds(Map<String, Object> params);

	/**
	 * 
	 * @param dataPayFee
	 */
	public void add(DataPayFee dataPayFee);

	/**
	 * 
	 * @param dataPayFees
	 */
	public void batchInsert(List<DataPayFee> dataPayFees);

	public void batchInsertAndInspectDetail(List<DataPayFee> dataPayFees, List<DataPayFeeDetail> dataPayFeeDetails);

}