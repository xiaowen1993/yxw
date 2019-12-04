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
package com.yxw.easyhealth.biz.datastorage.reports.service;

import java.util.List;
import java.util.Map;

import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspect;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspectDetail;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataInspectDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataInspectService {

	/**
	 * 根据分院CODE和检验ID查询检查入库记录
	 * @param dataInspect
	 * @return
	 */
	public List<DataInspect> findByBranchHospitalCodeAndInspectId(DataInspect dataInspect);

	/**
	 * 查询已经存在了的inspectids
	 * @param params hospitalCode, branchHospitalCode, inspectId
	 * @return 已经存在了的inspectId
	 */
	public List<String> findByBranchHospitalCodeAndInspectIds(Map<String, Object> params);

	/**
	 * 插入数据
	 * @param dataInspect
	 */
	public void add(DataInspect dataInspect);

	/**
	 * 批量插入数据
	 * @param dataInspects
	 */
	public void batchInsert(List<DataInspect> dataInspects);

	/**
	 * 批量插入检验及检验详细数据
	 * @param dataInspects
	 * @param dataInspectDetails
	 */
	public void batchInsertAndInspectDetail(List<DataInspect> dataInspects, List<DataInspectDetail> dataInspectDetails);

	/**
	 * 通过身份照号码找检验数据
	 * @param idNo
	 * @return
	 */
	public List<DataInspect> findByIdNo(String idNo);

	/**
	 * 通过检验Id找数据
	 * @param inspectId
	 * @return
	 */
	public List<DataInspectDetail> findByInspectId(String inspectId);

}