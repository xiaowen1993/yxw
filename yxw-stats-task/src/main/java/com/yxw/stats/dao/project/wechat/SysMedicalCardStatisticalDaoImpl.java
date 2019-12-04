package com.yxw.stats.dao.project.wechat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.framework.common.PKGenerator;
import com.yxw.framework.exception.SystemException;
import com.yxw.stats.dao.project.SysMedicalCardStatisticalDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.SysMedicalCardStatistical;

@Repository("wechatSysMedicalCardStatisticalDao")
public class SysMedicalCardStatisticalDaoImpl extends BaseDaoImpl<SysMedicalCardStatistical, Long> implements SysMedicalCardStatisticalDao {

	private Logger logger = LoggerFactory.getLogger(SysMedicalCardStatisticalDaoImpl.class);

	private final static String SQLNAME_FIND_CURR_MEDICALCARD_STATS_MAXDATE = "findCurrMedicalCardStatsMaxDate";
	private final static String SQLNAME_FIND_MEDICALCARD_STATS_DATA = "findMedicalCardStatsData";
	private final static String SQLNAME_BATCH_INSERT_MEDICALCARD_STATS_DATA = "batchInsertMedicalCardStatsData";

	public String findCurrMedicalCardStatsMaxDate(String hospitalId) {
		String result = null;
		try {
			Assert.notNull(hospitalId);

			result = sqlSession.selectOne(getSqlName(SQLNAME_FIND_CURR_MEDICALCARD_STATS_MAXDATE), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信绑卡统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_MEDICALCARD_STATS_MAXDATE)), e);
			throw new SystemException(String.format("查询项目平台微信绑卡统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_MEDICALCARD_STATS_MAXDATE)),
					e);
		}
		return result;
	}

	public List<SysMedicalCardStatistical> findMedicalCardStatsData(Map<String, Object> params) {
		List<SysMedicalCardStatistical> list = null;
		try {
			Assert.notNull(params);

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_MEDICALCARD_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信绑卡统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_MEDICALCARD_STATS_DATA)), e);
			throw new SystemException(String.format("查询项目平台微信绑卡统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_MEDICALCARD_STATS_DATA)), e);
		}
		return list;
	}

	public void batchInsertMedicalCardStatsData(List<SysMedicalCardStatistical> entitys) {
		try {
			Assert.notNull(entitys);
			for (SysMedicalCardStatistical sysMedicalCardStatistical : entitys) {
				if (StringUtils.isBlank(sysMedicalCardStatistical.getId())) {
					sysMedicalCardStatistical.setId(PKGenerator.generateId());
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sysMedicalCardStatistical", entitys);

			sqlSession.insert(getSqlName(SQLNAME_BATCH_INSERT_MEDICALCARD_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_MEDICALCARD_STATS_DATA)), e);
			throw new SystemException(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_MEDICALCARD_STATS_DATA)), e);
		}
	}
}