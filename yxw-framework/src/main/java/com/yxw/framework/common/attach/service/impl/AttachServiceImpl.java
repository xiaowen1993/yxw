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
package com.yxw.framework.common.attach.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yxw.framework.common.attach.dao.AttachDao;
import com.yxw.framework.common.attach.entity.Attach;
import com.yxw.framework.common.attach.service.AttachService;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

/**
 * @author caiwq
 * @version 1.0
 */
@Service
public class AttachServiceImpl extends BaseServiceImpl<Attach, String> implements AttachService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AttachDao attchDao;

	@Override
	protected BaseDao<Attach, String> getDao() {
		return attchDao;
	}

	@Override
	public List<Attach> findByAttachList(Attach attach) throws SystemException {
		List<Attach> attachList = Lists.newArrayList();
		try {
			if (attach != null) {
				attachList = attchDao.selectList(attach);
			} else {
				logger.error(" is null.");
			}
		} catch (Exception e) {
			logger.error("", e);
			throw new SystemException("", e);
		}
		return attachList;
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.common.attach.service.AttachService#saveOrUpdate(com.yxw.framework.common.attach.entity.Attach)
	 */
	@Override
	public Attach saveOrUpdate(Attach attach) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.common.attach.service.AttachService#findByAttachId(java.lang.String)
	 */
	@Override
	public Attach findByAttachId(String attachId) {
		return attchDao.findByAttachId(attachId);
	}
}