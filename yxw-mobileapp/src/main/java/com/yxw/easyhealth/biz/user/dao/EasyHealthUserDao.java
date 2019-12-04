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
package com.yxw.easyhealth.biz.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yxw.framework.mvc.dao.BaseDao;
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
public interface EasyHealthUserDao extends BaseDao<EasyHealthUser, String> {

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
	 * 根据手机号查询用户
	 * @param params
	 * @return
	 */
	public abstract List<EasyHealthUser> findEasyHealthUserByMobile(Map<String, Object> params);

	/**
	 * 根据证件号和手机号查询用户
	 * @param params
	 * @return
	 */
	public abstract List<EasyHealthUser> findEasyHealthUserByCardNoAndMobile(Map<String, Object> params);

	/**
	 * 根据账号查询用户
	 * @param params
	 * @return
	 */
	public abstract EasyHealthUser findEasyHealthUserByAccount(Map<String, Object> params);

}
