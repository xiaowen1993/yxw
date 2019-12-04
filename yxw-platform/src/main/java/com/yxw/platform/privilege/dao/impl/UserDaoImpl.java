/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.platform.privilege.UserVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.privilege.dao.UserDao;

/**
 * @Package: com.yxw.platform.privilege.dao.impl
 * @ClassName: UserDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月31日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {

	private static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	private static final String SQLNAME_UPDATE_AVAILABLE = "updateAvailable";
	private static final String SQLNAME_FIND_BY_USERNAME = "findByUserName";

	private final static String SQLNAME_FIND_LIST_BY_PAGE = "findListByPage";

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.UserDao#updateAvailable(com.yxw.platform.privilege.entity.User)
	 */
	@Override
	public void updateAvailable(User user) {
		try {
			Assert.notNull(user);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_AVAILABLE), user);
		} catch (Exception e) {
			logger.error(String.format("更新用户状态出错！语句：%s", getSqlName(SQLNAME_UPDATE_AVAILABLE)), e);
			throw new SystemException(String.format("更新用户状态出错！语句：%s", getSqlName(SQLNAME_UPDATE_AVAILABLE)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.UserDao#findByUserName(java.lang.String)
	 */
	@Override
	public User findByUserName(String userName) {
		try {
			Assert.notNull(userName);
			User user = sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_USERNAME), userName);
			return user;
		} catch (Exception e) {
			logger.error(String.format("查找用户出错！语句：%s", getSqlName(SQLNAME_FIND_BY_USERNAME)), e);
			throw new SystemException(String.format("查找用户出错！语句：%s", getSqlName(SQLNAME_FIND_BY_USERNAME)), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.dao.UserDao#findListVoByPage(java.util.Map, com.github.pagehelper.Page)
	 */
	@Override
	public PageInfo<UserVo> findListVoByPage(Map<String, Object> params, Page<UserVo> page) {
		try {
			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<UserVo> list = sqlSession.selectList(getSqlName(SQLNAME_FIND_LIST_BY_PAGE), params);
			return new PageInfo<UserVo>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_LIST_BY_PAGE)), e);
		}
	}

}
