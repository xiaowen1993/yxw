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
package com.yxw.mobileapp.biz.msgcenter.service;

import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.platform.msgpush.service
 * @ClassName: MsgCenterService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface MsgCenterService extends BaseService<MsgCenter, String> {
	/**
	 * 根据ID查找消息
	 * @param userId
	 * @param id
	 * @return
	 */
	public abstract MsgCenter findById(String userId, String id);

	/**
	 * 更新是否已读状态
	 * @param params
	 */
	public void updateIsRead(String userId, String id, Integer isRead);

	/**
	 * 根据是否已读状态获取消息数量
	 * @param userId
	 * @param isRead
	 * @return
	 */
	public abstract Integer findCountByIsRead(String userId, Integer isRead);
}
