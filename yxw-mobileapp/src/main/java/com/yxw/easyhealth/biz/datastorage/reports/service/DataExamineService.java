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

import com.yxw.easyhealth.biz.datastorage.reports.entity.DataExamine;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.service
 * @ClassName: DataExamineService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataExamineService {
	/**
	 * 根据分院CODE和检查ID查询检查入库记录
	 * @param dataExamine
	 * @return
	 */
	public List<DataExamine> findByBranchHospitalCodeAndcheckId(DataExamine dataExamine);

	/**
	 * 查询是否存在数据
	 * @param params hospitalCode, branchHospitalCode, checkId
	 * @return 返回存在了的checkId
	 */
	public List<String> findByBranchHospitalCodeAndcheckIds(Map<String, Object> params);

	/**
	 * 插入数据
	 * @param dataExamine
	 */
	public void add(DataExamine dataExamine);

	/**
	 * 批量插入数据
	 * @param dataExamines
	 */
	public void batchInsert(List<DataExamine> dataExamines);

	/**
	 * 通过Id查列表
	 * @param idNo
	 * @return
	 */
	public List<DataExamine> findByIdNo(String idNo);

	/**
	 * 通过Id找数据
	 * @param examineId
	 * @return
	 */
	public DataExamine findByExamineId(String examineId);

	/**
	 * 通过身份证查询所有的
	 * @param idNo
	 * @return
	 */
	public abstract List<ReportsParamsVo> findReportsByIdNo(String idNo);

}
