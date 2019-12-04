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

import com.yxw.commons.entity.mobile.biz.smarttriage.Symptom;
import com.yxw.easyhealth.biz.smarttriage.dao.SymptomDao;
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
public class SymptomDaoImpl extends BaseDaoImpl<Symptom, String> implements SymptomDao {
	private Logger logger = LoggerFactory.getLogger(SymptomDaoImpl.class);
	private final static String SQLNAME_FIND_SYMPTOM_BY_NAME = "findSymptomByName";
	private final static String SQLNAME_FIND_SYMPTOM_BY_DISEASE_ID = "findSymptomByDiseaseId";
	private final static String SQLNAME_FIND_ALL_BY_ISHIDE = "findAllByIsHide";

	@Override
	public Symptom findSymptomByName(Map<String, Object> params) {
		Symptom symptom = null;
		try {
			symptom = sqlSession.selectOne(getSqlName(SQLNAME_FIND_SYMPTOM_BY_NAME), params);
		} catch (Exception e) {
			logger.error(String.format("根据症状名称查询症状出错！语句：%s", getSqlName(SQLNAME_FIND_SYMPTOM_BY_NAME)), e);
			throw new SystemException(String.format("根据症状名称查询症状出错！语句：%s", getSqlName(SQLNAME_FIND_SYMPTOM_BY_NAME)), e);
		}
		return symptom;
	}

	@Override
	public List<Symptom> findSymptomByDiseaseId(String diseaseId) {
		List<Symptom> list = null;
		try {
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_SYMPTOM_BY_DISEASE_ID), diseaseId);
		} catch (Exception e) {
			logger.error(String.format("根据疾病ID查询其关联的症状出错！语句：%s", getSqlName(SQLNAME_FIND_SYMPTOM_BY_DISEASE_ID)), e);
			throw new SystemException(String.format("根据疾病ID查询其关联的症状出错！语句：%s", getSqlName(SQLNAME_FIND_SYMPTOM_BY_DISEASE_ID)), e);
		}
		return list;
	}

	@Override
	public List<Symptom> findAllByIsHide(Map<String, Object> params) {
		List<Symptom> list = null;
		try {
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_BY_ISHIDE), params);
		} catch (Exception e) {
			logger.error(String.format("查询是否显示的症状出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_ISHIDE)), e);
			throw new SystemException(String.format("查询是否显示的症状出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_ISHIDE)), e);
		}
		return list;
	}
}
