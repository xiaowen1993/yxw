/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.msgpush.MsgTemp;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.msgpush.dao.MsgTempDao;
import com.yxw.platform.msgpush.service.MsgTempService;

/**
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgTempServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class MsgTempServiceImpl extends BaseServiceImpl<MsgTemp, String> implements MsgTempService {

	@Autowired
	private MsgTempDao msgTempDao;

	@Override
	protected BaseDao<MsgTemp, String> getDao() {
		return this.msgTempDao;
	}

}
