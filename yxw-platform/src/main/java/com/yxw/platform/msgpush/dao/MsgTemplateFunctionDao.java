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
package com.yxw.platform.msgpush.dao;

import com.yxw.commons.entity.platform.msgpush.MsgTemplateFunction;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.msgpush.dao
 * @ClassName: MsgTemplateFunctionDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月18日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgTemplateFunctionDao extends BaseDao<MsgTemplateFunction, String> {
	/**
	 * 根据模板ID删除模板功能
	 * @param templateId
	 */
	public abstract void deleteByTemplateId(String templateId);
}
