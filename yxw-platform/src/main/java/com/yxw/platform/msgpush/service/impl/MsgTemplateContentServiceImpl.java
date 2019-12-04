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

import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.msgpush.dao.MsgTemplateContentDao;

/**
 * 模板消息内容service实现类
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgTemplateContentServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class MsgTemplateContentServiceImpl extends BaseServiceImpl<MsgTemplateContent, String> {

	@Autowired
	private MsgTemplateContentDao msgTemplateContentDao;

	@Override
	protected BaseDao<MsgTemplateContent, String> getDao() {
		return this.msgTemplateContentDao;
	}

}
