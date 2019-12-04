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
public interface DataPayFeeDetailService {

	/**
	 * 根据分院CODE和检验ID查询检查入库记录
	 * @param dataInspect
	 * @return
	 */
	//	public List<DataPayFeeDetail> findByBranchHospitalCodeAndMzFeeIdAndItemId(DataPayFeeDetail detail);

	/**
	 * 插入数据
	 * @param dataInspect
	 */
	public void add(DataPayFeeDetail detail);

	/**
	 * 批量插入数据
	 * @param dataInspects
	 */
	public void batchInsert(List<DataPayFeeDetail> details);

	/**
	 * 查询已缴费详情
	 * @param params
	 * @return
	 */
	public List<DataPayFeeDetail> findPayFeeDetails(DataPayFee dataPayFee);

}