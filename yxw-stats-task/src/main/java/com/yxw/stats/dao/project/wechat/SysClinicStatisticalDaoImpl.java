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
import com.yxw.stats.dao.project.SysClinicStatisticalDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.SysClinicStatistical;

@Repository("wechatSysClinicStatisticalDao")
public class SysClinicStatisticalDaoImpl extends BaseDaoImpl<SysClinicStatistical, String> implements SysClinicStatisticalDao {

	private Logger logger = LoggerFactory.getLogger(SysClinicStatisticalDaoImpl.class);

	private final static String SQLNAME_FIND_CURR_CLINIC_STATS_MAXDATE = "findCurrClinicStatsMaxDate";
	private final static String SQLNAME_FIND_CLINIC_STATS_DATA = "findClinicStatsData";
	private final static String SQLNAME_BATCH_INSERT_CLINIC_STATS_DATA = "batchInsertClinicStatsData";

	public String findCurrClinicStatsMaxDate(String hospitalId) {
		String result = null;
		try {
			Assert.notNull(hospitalId);

			result = sqlSession.selectOne(getSqlName(SQLNAME_FIND_CURR_CLINIC_STATS_MAXDATE), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信门诊统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_CLINIC_STATS_MAXDATE)), e);
			throw new SystemException(String.format("查询项目平台微信门诊统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_CLINIC_STATS_MAXDATE)), e);
		}
		return result;
	}

	public List<SysClinicStatistical> findClinicStatsData(Map<String, Object> params) {
		List<SysClinicStatistical> list = null;
		try {
			Assert.notNull(params);

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_CLINIC_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信门诊统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_CLINIC_STATS_DATA)), e);
			throw new SystemException(String.format("查询项目平台微信门诊统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_CLINIC_STATS_DATA)), e);
		}
		return list;
	}

	public void batchInsertClinicStatsData(List<SysClinicStatistical> entitys) {
		try {
			Assert.notNull(entitys);
			for (SysClinicStatistical sysClinicStatistical : entitys) {
				if (StringUtils.isBlank(sysClinicStatistical.getId())) {
					sysClinicStatistical.setId(PKGenerator.generateId());
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sysClinicStatistical", entitys);

			sqlSession.insert(getSqlName(SQLNAME_BATCH_INSERT_CLINIC_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_CLINIC_STATS_DATA)), e);
			throw new SystemException(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_CLINIC_STATS_DATA)), e);
		}
	}
}