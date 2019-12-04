package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.platform.MedicalCardCount;

@Repository("medicalCardCountDao")
public class MedicalCardCountDaoImpl extends BaseDaoImpl<MedicalCardCount, String> implements MedicalCardCountDao {
	private static Logger logger = LoggerFactory.getLogger(MedicalCardCountDaoImpl.class);

	private final static String SQLNAME_FIND_MEDICAL_CARD_COUNT_BY_DATE = "findMedicalCardCountByDate";

	@Override
	public List<MedicalCardCount> findMedicalCardCountByDate(Map map) {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_MEDICAL_CARD_COUNT_BY_DATE), map);
		} catch (Exception e) {
			logger.error(String.format("根据参数统计绑卡出错！语句：%s", getSqlName(SQLNAME_FIND_MEDICAL_CARD_COUNT_BY_DATE)), e);
			throw new SystemException(String.format("根据参数统计绑卡出错！语句：%s", getSqlName(SQLNAME_FIND_MEDICAL_CARD_COUNT_BY_DATE)), e);
		}
	}

}
