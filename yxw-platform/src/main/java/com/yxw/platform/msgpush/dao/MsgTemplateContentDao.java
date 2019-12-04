/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.dao;

import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.msgpush.dao
 * @ClassName: MsgTemplateContent
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgTemplateContentDao extends BaseDao<MsgTemplateContent, String> {

	/**
	 * 根据模板ID删除模板内容
	 * @param templateId
	 */
	public abstract void deleteByTemplateId(String templateId);
}
