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

import com.yxw.commons.entity.platform.msgpush.MsgTemplateLibraryFunction;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.msgpush.dao
 * @ClassName: MsgTemplateLibraryFunctionDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgTemplateLibraryFunctionDao extends BaseDao<MsgTemplateLibraryFunction, String> {
	public abstract void deleteByTempLibraryId(String tempLibraryId);
}
