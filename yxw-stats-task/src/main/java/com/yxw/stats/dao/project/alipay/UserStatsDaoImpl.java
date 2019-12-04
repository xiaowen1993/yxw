package com.yxw.stats.dao.project.alipay;

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
import com.yxw.stats.dao.project.UserStatsDao;
import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.project.UserStats;

@Repository("alipayUserStatsDao")
public class UserStatsDaoImpl extends BaseDaoImpl<UserStats, Long> implements UserStatsDao {

	private Logger logger = LoggerFactory.getLogger(UserStatsDaoImpl.class);

	private final static String SQLNAME_FIND_CURR_USER_STATS_MAXDATE = "findCurrUserStatsMaxDate";
	private final static String SQLNAME_FIND_USER_STATS_DATA = "findUserStatsData";
	private final static String BATCH_INSERT_USER_STATS_DATA = "batchInsertUserStatsData";

	public String findCurrUserStatsMaxDate(String hospitalId) {

		String result = null;
		try {
			Assert.notNull(hospitalId);

			result = sqlSession.selectOne(getSqlName(SQLNAME_FIND_CURR_USER_STATS_MAXDATE), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("查询项目平台支付宝用户关注数统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_USER_STATS_MAXDATE)), e);
			throw new SystemException(String.format("查询项目平台支付宝用户关注数统计的最大日期出错！语句：%s", getSqlName(SQLNAME_FIND_CURR_USER_STATS_MAXDATE)), e);
		}
		return result;

	}

	public List<UserStats> findUserStatsData(Map<String, Object> params) {

		List<UserStats> list = null;
		try {
			Assert.notNull(params);

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_USER_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("查询汇总数据库绑卡统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_USER_STATS_DATA)), e);
			throw new SystemException(String.format("查询汇总数据库绑卡统计数据出错！语句：%s", getSqlName(SQLNAME_FIND_USER_STATS_DATA)), e);
		}
		return list;

	}

	public void batchInsertUserStatsData(List<UserStats> entitys) {
		try {
			Assert.notNull(entitys);
			for (UserStats userStats : entitys) {
				if (StringUtils.isBlank(userStats.getId())) {
					userStats.setId(PKGenerator.generateId());
				}
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userStats", entitys);

			sqlSession.insert(getSqlName(BATCH_INSERT_USER_STATS_DATA), params);
		} catch (Exception e) {
			logger.error(String.format("批量插入对象出错！语句：%s", getSqlName(BATCH_INSERT_USER_STATS_DATA)), e);
			throw new SystemException(String.format("批量插入对象出错！语句：%s", getSqlName(BATCH_INSERT_USER_STATS_DATA)), e);
		}
	}
}