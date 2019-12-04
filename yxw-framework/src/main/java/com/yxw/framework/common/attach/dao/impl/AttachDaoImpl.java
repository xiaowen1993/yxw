/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年3月13日</p>
 *  <p> Created by caiwq</p>
 *  </body>
 * </html>
 */

package com.yxw.framework.common.attach.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.framework.common.attach.dao.AttachDao;
import com.yxw.framework.common.attach.entity.Attach;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @author caiwq
 * @version 1.0
 */
@Repository
public class AttachDaoImpl extends BaseDaoImpl<Attach, String> implements AttachDao {

	private static Logger logger = LoggerFactory.getLogger(AttachDaoImpl.class);

	private final static String SQLNAME_FIND_BY_ATTACHID = "findByAttachId";

	@Override
	public List<Attach> selectList(Attach attach) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.common.attach.dao.AttachDao#findByAttachId(java.lang.String)
	 */
	@Override
	public Attach findByAttachId(String attachId) {
		Assert.notNull(attachId);
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ATTACHID), attachId);
		} catch (Exception e) {
			logger.error(String.format("根据attachId查询出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ATTACHID)), e);
			throw new SystemException(String.format("根据attachId查询出错！语句：%s", getSqlName(SQLNAME_FIND_BY_ATTACHID)), e);
		}
	}

}
