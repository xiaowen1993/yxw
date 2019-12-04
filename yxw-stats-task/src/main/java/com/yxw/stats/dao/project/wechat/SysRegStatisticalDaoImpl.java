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
import com.yxw.stats.dao.project.SysRegStatisticalDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.SysRegStatistical;

@Repository("wechatSysRegStatisticalDao")
public class SysRegStatisticalDaoImpl extends BaseDaoImpl<SysRegStatistical, Long> implements SysRegStatisticalDao {

	private Logger logger = LoggerFactory.getLogger(SysRegStatisticalDaoImpl.class);

	private final static String SQLNAME_FIND_CURR_REGISTER_STATS_MAXDATE = "findCurrRegisterStatsMaxDate";
	private final static String SQLNAME_FIND_REGISTER_STATS_DATA = "findRegisterStatsData";
	private final static String SQLNAME_BATCH_INSERT_REGISTER_STATS_DATA = "batchInsertRegisterStatsData";

	public String findCurrRegisterStatsMaxDate(String hospitalId) {
		String result = null;
		try {
			Assert.notNull(hospitalId);

			result = sqlSession.selectOne(getSqlName(SQLNAME_FIND_CURR_REGISTER_STATS_MAXDATE), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信挂号统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_REGISTER_STATS_MAXDATE)), e);
			throw new SystemException(String.format("查询项目平台微信挂号统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_REGISTER_STATS_MAXDATE)), e);
		}
		return result;
	}

	public List<SysRegStatistical> findRegisterStatsData(Map<String, Object> params) {
		List<SysRegStatistical> list = null;
		try {
			Assert.notNull(params);

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_REGISTER_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信挂号统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_REGISTER_STATS_DATA)), e);
			throw new SystemException(String.format("查询项目平台微信挂号统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_REGISTER_STATS_DATA)), e);
		}
		return list;
	}

	public void batchInsertRegisterStatsData(List<SysRegStatistical> entitys) {
		try {
			Assert.notNull(entitys);
			for (SysRegStatistical sysRegisterStatistical : entitys) {
				if (StringUtils.isBlank(sysRegisterStatistical.getId())) {
					sysRegisterStatistical.setId(PKGenerator.generateId());
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sysRegisterStatistical", entitys);

			sqlSession.insert(getSqlName(SQLNAME_BATCH_INSERT_REGISTER_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_REGISTER_STATS_DATA)), e);
			throw new SystemException(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_REGISTER_STATS_DATA)), e);
		}
	}

}