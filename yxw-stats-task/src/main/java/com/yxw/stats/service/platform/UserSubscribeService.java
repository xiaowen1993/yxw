package com.yxw.stats.service.platform;

import java.util.List;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.stats.entity.platform.UserSubscribe;

public interface UserSubscribeService extends BaseService<UserSubscribe, String> {
	/**
	 * 获取用户关注数集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param appId
	 * @return
	 */
	public List<UserSubscribe> getUserSubscribes(String beginDate, String endDate, String appId, String hospitalId, String platformMode);

	/**
	 * 插入用户关注数
	 * 
	 * @param beginDate
	 * @param hospitalId
	 * @param platformMode
	 * @param appId
	 * @param appSecret
	 */
	public List<UserSubscribe> findUserSubscribes(String beginDate, String hospitalId, String platformMode, String appId, String appSecret);

	/**
	 * 验证日期
	 * 
	 * @param date
	 * @param appId
	 * @param hospitalId
	 * @return
	 */
	public boolean verifyRefDate(String date, String appId, String hospitalId, String platformMode);

	/**
	 * 获取最后一条数据
	 * 
	 * @param map
	 * @return
	 */
	public UserSubscribe getUserSubscribeLastOne(String appId, String hospitalId, String platformMode);

}
