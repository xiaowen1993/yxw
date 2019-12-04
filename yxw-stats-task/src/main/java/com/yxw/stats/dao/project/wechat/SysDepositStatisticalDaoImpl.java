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
import com.yxw.stats.dao.project.SysDepositStatisticalDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.SysDepositStatistical;

@Repository("wechatSysDepositStatisticalDao")
public class SysDepositStatisticalDaoImpl extends BaseDaoImpl<SysDepositStatistical, Long> implements SysDepositStatisticalDao {

	private Logger logger = LoggerFactory.getLogger(SysDepositStatisticalDaoImpl.class);

	private final static String SQLNAME_FIND_CURR_DEPOSIT_STATS_MAXDATE = "findCurrDepositStatsMaxDate";
	private final static String SQLNAME_FIND_DEPOSIT_STATS_DATA = "findDepositStatsData";
	private final static String SQLNAME_BATCH_INSERT_DEPOSIT_STATS_DATA = "batchInsertDepositStatsData";

	public String findCurrDepositStatsMaxDate(String hospitalId) {
		String result = null;
		try {
			Assert.notNull(hospitalId);

			result = sqlSession.selectOne(getSqlName(SQLNAME_FIND_CURR_DEPOSIT_STATS_MAXDATE), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信住院押金统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_DEPOSIT_STATS_MAXDATE)), e);
			throw new SystemException(String.format("查询项目平台微信住院押金统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_DEPOSIT_STATS_MAXDATE)), e);
		}
		return result;
	}

	public List<SysDepositStatistical> findDepositStatsData(Map<String, Object> params) {
		List<SysDepositStatistical> list = null;
		try {
			Assert.notNull(params);

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_DEPOSIT_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台微信住院押金统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_DEPOSIT_STATS_DATA)), e);
			throw new SystemException(String.format("查询项目平台微信住院押金统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_DEPOSIT_STATS_DATA)), e);
		}
		return list;
	}

	public void batchInsertDepositStatsData(List<SysDepositStatistical> entitys) {
		try {
			Assert.notNull(entitys);
			for (SysDepositStatistical sysDepositStatistical : entitys) {
				if (StringUtils.isBlank(sysDepositStatistical.getId())) {
					sysDepositStatistical.setId(PKGenerator.generateId());
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("sysDepositStatistical", entitys);

			sqlSession.insert(getSqlName(SQLNAME_BATCH_INSERT_DEPOSIT_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_DEPOSIT_STATS_DATA)), e);
			throw new SystemException(String.format("批量插入对象出错！语句：%s", getSqlName(SQLNAME_BATCH_INSERT_DEPOSIT_STATS_DATA)), e);
		}
	}
}