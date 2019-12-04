/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.user.service;

import java.util.HashMap;
import java.util.List;

import com.yxw.framework.mvc.service.BaseService;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

/**
 * @Package: com.yxw.easyhealth.biz.user.dao
 * @ClassName: EasyHealthUserDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月6日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface EasyHealthUserService extends BaseService<EasyHealthUser, String> {

	/**
	 * 检查证件号是否已经注册
	 * @param params
	 * @return
	 */
	public abstract EasyHealthUser findEasyHealthForCardNo(HashMap<String, Object> params);

	/**
	 * 登录检查
	 * @param params
	 * @return
	 */
	public abstract EasyHealthUser findEasyHealthForCardNoAndPassWord(HashMap<String, Object> params);

	/**
	 * 根据手机号多线程查询所有表
	 * @return
	 */
	public abstract List<EasyHealthUser> findEasyHealthByMobileForAllTable(String mobile);

	/**
	 * 根据身份证查询
	 * @param account 账号
	 * @param hashTableName 表
	 * @return
	 */
	public abstract EasyHealthUser findEasyHealthByAccount(String account, String hashTableName);

	/**
	 * 根据证件号和手机号多线程查询所有表
	 * @param cardNo 证件号
	 * @param mobile 手机号（不等于）
	 * @return
	 */
	public abstract List<EasyHealthUser> findEasyHealthByCardNoAndMobileForAllTable(String cardNo, String mobile);

}
