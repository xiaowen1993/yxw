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
package com.yxw.easyhealth.biz.smarttriage.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.mobile.biz.smarttriage.SymptomDisease;
import com.yxw.easyhealth.biz.smarttriage.dao.SymptomDiseaseDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.dao.impl
 * @ClassName: SymptomDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class SymptomDiseaseDaoImpl extends BaseDaoImpl<SymptomDisease, String> implements SymptomDiseaseDao {
	private Logger logger = LoggerFactory.getLogger(SymptomDiseaseDaoImpl.class);

	private final static String SQLNAME_DELETE_BY_DISEASE_ID = "deleteByDiseaseId";

	@Override
	public void deleteByDiseaseId(String diseaseId) {
		try {
			sqlSession.delete(getSqlName(SQLNAME_DELETE_BY_DISEASE_ID), diseaseId);
		} catch (Exception e) {
			logger.error(String.format("根据疾病ID删除关联关系出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_DISEASE_ID)), e);
			throw new SystemException(String.format("根据疾病ID删除关联关系出错！语句：%s", getSqlName(SQLNAME_DELETE_BY_DISEASE_ID)), e);
		}
	}
}
