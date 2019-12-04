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
package com.yxw.easyhealth.biz.datastorage.reports.dao;

import java.util.List;
import java.util.Map;

import com.yxw.easyhealth.biz.datastorage.reports.entity.DataExamine;
import com.yxw.easyhealth.biz.vo.ReportsParamsVo;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataExamineDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DataExamineDao extends BaseDao<DataExamine, String> {

	/**
	 * 根据分院CODE和检查ID查询检查入库记录
	 * @param dataExamine
	 * @return
	 */
	public abstract List<DataExamine> findByBranchHospitalCodeAndcheckId(DataExamine dataExamine);

	/**
	 * 检测存在的checkId
	 * @param params hospitalCode, branchHospitalCode, checkId
	 * @return
	 */
	public abstract List<String> findByBranchHospitalCodeAndcheckIds(Map<String, Object> params);

	/**
	 * 通过身份证号码，查询该人的所有检查报告（三个月内）
	 * @param idNo
	 * @return
	 */
	public abstract List<DataExamine> findByIdNo(String idNo);

	/**
	 * 通过Id查询数据
	 * @param examineId
	 * @return
	 */
	public abstract DataExamine findByExamineId(String examineId);

	/**
	 * 查询所有的报告（三个月内，检查检验，目前暂时没有体检）
	 * 2015-11-05
	 * @param idNo
	 * @return
	 */
	public abstract List<ReportsParamsVo> findReportsByIdNo(String idNo);

}