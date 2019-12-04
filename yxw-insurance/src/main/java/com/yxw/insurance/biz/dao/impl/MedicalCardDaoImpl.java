package com.yxw.insurance.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.MedicalCardDao;

@Repository
public class MedicalCardDaoImpl extends BaseDaoImpl<MedicalCard, String> implements MedicalCardDao {

	private Logger logger = LoggerFactory.getLogger(MedicalCardDaoImpl.class);
	private final static String SQLNAME_FIND_ALL_FAMILIES = "findAllMedicalCards";

	@Override
	public List<MedicalCard> findAllMedicalCards(String openId, int state) {
		List<MedicalCard> list = null;
		try {
			Assert.notNull(openId, "openId不能为空");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openId", openId);
			params.put("state", state);
			params.put("hashTableName", SimpleHashTableNameGenerator.getMedicalCardHashTable(openId, true));
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_FAMILIES), params);
		} catch (Exception e) {
			logger.error(String.format("获取所有的家人信息（含本人）出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_FAMILIES)), e);
			throw new SystemException(String.format("获取所有的家人信息（含本人）出错！语句：%s", getSqlName(SQLNAME_FIND_ALL_FAMILIES)), e);
		}

		return list;
	}

}
