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
package com.yxw.mobileapp.biz.msgcenter.dao;

import java.io.Serializable;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.platform.msgpush.dao
 * @ClassName: MsgCenterDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgCenterDao extends BaseDao<MsgCenter, String> {
	/**
	 * 根据ID查找消息
	 * @param params
	 * @return
	 */
	public abstract MsgCenter findById(Map<String, Serializable> params);

	/**
	 * 更新是否已读标示
	 * @param params
	 */
	public abstract void updateIsRead(Map<String, Serializable> params);

	/**
	 * 获取未读消息总数
	 * @param params
	 * @return
	 */
	public abstract Integer findCountByIsRead(Map<String, Serializable> params);
}
