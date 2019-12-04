package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.platform.UserSubscribe;

@Repository("userSubscribeDao")
public class UserSubscribeDaoImpl extends BaseDaoImpl<UserSubscribe, String> implements UserSubscribeDao {

	private Logger logger = LoggerFactory.getLogger(UserSubscribeDaoImpl.class);

	private final static String SQLNAME_GET_USER_SUBSCRIBES = "getUserSubscribes";

	private final static String SQLNAME_GET_USER_SUBSCRIBE_BY_DATE = "getUserSubscribeByDate";

	private final static String SQLNAME_GET_USER_SUBSCRIBE_LAST_ONE = "getUserSubscribeLastOne";

	@Override
	public List<UserSubscribe> getUserSubscribes(Map map) {
		// TODO Auto-generated method stub
		try {
			logger.info("----根据条件查询用户关注数 参数map:{}", JSON.toJSONString(map));
			return sqlSession.selectList(getSqlName(SQLNAME_GET_USER_SUBSCRIBES), map);
		} catch (Exception e) {
			logger.error(String.format("根据条件查询用户关注数 出错！语句：%s", getSqlName(SQLNAME_GET_USER_SUBSCRIBES)), e);
			throw new SystemException(String.format("根据条件查询用户关注数 出错！语句：%s", getSqlName(SQLNAME_GET_USER_SUBSCRIBES)), e);
		}
	}

	@Override
	public UserSubscribe getUserSubscribeByDate(Map map) {
		// TODO Auto-generated method stub
		try {
			logger.info("----根据日期查询用户关注数列 参数map:{}", JSON.toJSONString(map));
			return sqlSession.selectOne(getSqlName(SQLNAME_GET_USER_SUBSCRIBE_BY_DATE), map);
		} catch (Exception e) {
			logger.error(String.format("根据日期查询用户关注数列 出错！语句：%s", getSqlName(SQLNAME_GET_USER_SUBSCRIBE_BY_DATE)), e);
			throw new SystemException(String.format("根据日期查询用户关注数列 出错！语句：%s", getSqlName(SQLNAME_GET_USER_SUBSCRIBE_BY_DATE)), e);
		}
	}

	@Override
	public UserSubscribe getUserSubscribeLastOne(Map map) {
		// TODO Auto-generated method stub
		try {
			logger.info("----查询最后一条用户关注数据 参数map:{}", JSON.toJSONString(map));
			return sqlSession.selectOne(getSqlName(SQLNAME_GET_USER_SUBSCRIBE_LAST_ONE), map);
		} catch (Exception e) {
			logger.error(String.format("查询最后一条用户关注数据 出错！语句：%s", getSqlName(SQLNAME_GET_USER_SUBSCRIBE_LAST_ONE)), e);
			throw new SystemException(String.format("查询最后一条用户关注数据 出错！语句：%s", getSqlName(SQLNAME_GET_USER_SUBSCRIBE_LAST_ONE)), e);
		}
	}

}
