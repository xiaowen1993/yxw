package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.platform.ExtensionCount;

@Repository("extensionCountDao")
public class ExtensionCountDaoImpl extends BaseDaoImpl<ExtensionCount, String> implements ExtensionCountDao {

	private static Logger logger = LoggerFactory.getLogger(ExtensionCountDaoImpl.class);

	private static final String FIND_EXTENSION_COUNT_BY_DATE = "findExtensionCountByDate";

	private static final String GET_EXTENSION_COUNT_SUM = "getExtensionCountSum";

	@Override
	public List<ExtensionCount> findExtensionCountByDate(Map params) {
		// TODO Auto-generated method stub
		try {
			return sqlSession.selectList(getSqlName(FIND_EXTENSION_COUNT_BY_DATE), params);
		} catch (Exception e) {
			logger.error(String.format("根据参数统计扫推广二维码订单统计出错！语句：%s", getSqlName(FIND_EXTENSION_COUNT_BY_DATE)), e);
			throw new SystemException(String.format("根据参数统计扫推广二维码订单统计出错！语句：%s", getSqlName(FIND_EXTENSION_COUNT_BY_DATE)), e);
		}
	}

}
