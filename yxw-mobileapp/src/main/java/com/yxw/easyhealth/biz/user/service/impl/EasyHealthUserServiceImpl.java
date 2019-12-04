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
package com.yxw.easyhealth.biz.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.easyhealth.biz.user.dao.EasyHealthUserDao;
import com.yxw.easyhealth.biz.user.service.EasyHealthUserService;
import com.yxw.easyhealth.common.callable.QueryHashTableCallable;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.mobileapp.biz.user.entity.EasyHealthUser;

/**
 * 
 * @Package: com.yxw.easyhealth.biz.user.service.impl
 * @ClassName: EasyHealthUserServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月6日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "easyHealthUserService")
public class EasyHealthUserServiceImpl extends BaseServiceImpl<EasyHealthUser, String> implements EasyHealthUserService {
	private Logger logger = LoggerFactory.getLogger(EasyHealthUserServiceImpl.class);

	@Autowired
	private EasyHealthUserDao easyHealthUserDao;

	@Override
	protected BaseDao<EasyHealthUser, String> getDao() {
		return easyHealthUserDao;
	}

	@Override
	public EasyHealthUser findEasyHealthForCardNo(HashMap<String, Object> params) {
		return easyHealthUserDao.findEasyHealthForCardNo(params);
	}

	@Override
	public EasyHealthUser findEasyHealthForCardNoAndPassWord(HashMap<String, Object> params) {
		return easyHealthUserDao.findEasyHealthForCardNoAndPassWord(params);
	}

	@Override
	public List<EasyHealthUser> findEasyHealthByMobileForAllTable(String mobile) {
		List<EasyHealthUser> users = new ArrayList<EasyHealthUser>();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();
		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.EASY_HEALTH_USER_TABLE_NAME + "_" + i;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", mobile);
			params.put("hashTableName", hashTableName);
			Object[] parameters = new Object[] { params };
			Class<?>[] parameterTypes = new Class[] { Map.class };

			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(EasyHealthUserDao.class, "findEasyHealthUserByMobile", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				@SuppressWarnings("unchecked")
				List<EasyHealthUser> resUsers = (List<EasyHealthUser>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(resUsers)) {
					users.addAll(resUsers);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return users;
	}

	@Override
	public List<EasyHealthUser> findEasyHealthByCardNoAndMobileForAllTable(String cardNo, String mobile) {
		List<EasyHealthUser> users = new ArrayList<EasyHealthUser>();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(SimpleHashTableNameGenerator.subTableCount);
		List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();
		for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
			String hashTableName = SimpleHashTableNameGenerator.EASY_HEALTH_USER_TABLE_NAME + "_" + i;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("cardNo", cardNo);
			params.put("mobile", mobile);
			params.put("hashTableName", hashTableName);
			Object[] parameters = new Object[] { params };
			Class<?>[] parameterTypes = new Class[] { Map.class };

			QueryHashTableCallable collectCall =
					new QueryHashTableCallable(EasyHealthUserDao.class, "findEasyHealthUserByCardNoAndMobile", parameters, parameterTypes);
			// 创建每条指令的采集任务对象
			FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}

		try {
			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				@SuppressWarnings("unchecked")
				List<EasyHealthUser> resUsers = (List<EasyHealthUser>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(resUsers)) {
					users.addAll(resUsers);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();

		return users;
	}

	@Override
	public EasyHealthUser findEasyHealthByAccount(String account, String hashTableName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hashTableName", hashTableName);
		params.put("account", account);
		return easyHealthUserDao.findEasyHealthUserByAccount(params);
	}

}
