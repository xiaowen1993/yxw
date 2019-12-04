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

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.commons.entity.mobile.biz.smarttriage.Disease;
import com.yxw.easyhealth.biz.smarttriage.dao.DiseaseDao;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.dao.impl
 * @ClassName: DiseaseDaoImpl
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
public class DiseaseDaoImpl extends BaseDaoImpl<Disease, String> implements DiseaseDao {
	private Logger logger = LoggerFactory.getLogger(DiseaseDaoImpl.class);
	private final static String SQLNAME_FIND_DISEASE_BY_NAME = "findDiseaseByName";
	private final static String SQLNAME_FIND_DISEASE_BY_SYMPTOMIDS = "findDiseaseBySymptomIds";

	@Override
	public Disease findDiseaseByName(Map<String, Object> params) {
		Disease disease = null;
		try {
			disease = sqlSession.selectOne(getSqlName(SQLNAME_FIND_DISEASE_BY_NAME), params);
		} catch (Exception e) {
			logger.error(String.format("根据疾病名称查询疾病出错！语句：%s", getSqlName(SQLNAME_FIND_DISEASE_BY_NAME)), e);
			throw new SystemException(String.format("根据疾病名称查询疾病出错！语句：%s", getSqlName(SQLNAME_FIND_DISEASE_BY_NAME)), e);
		}
		return disease;
	}

	@Override
	public List<Disease> findDiseaseBySymptomIds(Map<String, Object> params) {
		List<Disease> list = null;
		try {
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_DISEASE_BY_SYMPTOMIDS), params);
		} catch (Exception e) {
			logger.error(String.format("根据症状ID集合查询疾病出错！语句：%s", getSqlName(SQLNAME_FIND_DISEASE_BY_SYMPTOMIDS)), e);
			throw new SystemException(String.format("根据症状ID集合查询疾病出错！语句：%s", getSqlName(SQLNAME_FIND_DISEASE_BY_SYMPTOMIDS)), e);
		}
		return list;
	}

}
