/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2016 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2016-8-11</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.stats.dao.platform;

import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.stats.entity.platform.UserSubscribe;

public interface UserSubscribeDao extends BaseDao<UserSubscribe, String> {
	/**
	 * 获取用户关注数集合
	 * 
	 * @param map
	 * @return
	 */
	public List<UserSubscribe> getUserSubscribes(Map map);

	/**
	 * 根据日期获取用户关注列
	 * 
	 * @param map
	 * @return
	 */
	public UserSubscribe getUserSubscribeByDate(Map map);

	/**
	 * 获取最后一条用户关注数据
	 * 
	 * @return
	 */
	public UserSubscribe getUserSubscribeLastOne(Map map);
}
