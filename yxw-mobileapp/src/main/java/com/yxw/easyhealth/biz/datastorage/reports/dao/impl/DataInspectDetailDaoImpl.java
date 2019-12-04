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
package com.yxw.easyhealth.biz.datastorage.reports.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.easyhealth.biz.datastorage.reports.dao.DataInspectDetailDao;
import com.yxw.easyhealth.biz.datastorage.reports.entity.DataInspectDetail;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataInspectDetail
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年7月7日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class DataInspectDetailDaoImpl extends BaseDaoImpl<DataInspectDetail, String> implements DataInspectDetailDao {

	private Logger logger = LoggerFactory.getLogger(DataInspectDetailDaoImpl.class);

	private static final String SQLNAME_FIND_BY_INSPECT_ID = "findByInspectId";

	@Override
	public List<DataInspectDetail> findByInspectId(String inspectId) {
		List<DataInspectDetail> list = null;
		try {
			Assert.notNull(inspectId, "inspectId不能为空！");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("inspectId", inspectId);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_INSPECT_ID), params);
		} catch (Exception e) {
			logger.error(String.format("通过inspectId查检验详情数据出错!语句：%s", getSqlName(SQLNAME_FIND_BY_INSPECT_ID)), e);
			throw new SystemException(String.format("通过inspectId查检验详情数据出错!语句：%s", getSqlName(SQLNAME_FIND_BY_INSPECT_ID)), e);
		}
		return list;
	}

}
