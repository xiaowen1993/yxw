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
package com.yxw.platform.msgpush.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.entity.platform.msgpush.EHDeviceInfo;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.cache.redis.RedisLock;
import com.yxw.framework.cache.redis.RedisService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.framework.utils.QueryHashTableCallable;
import com.yxw.platform.msgpush.dao.EHDeviceInfoDao;
import com.yxw.platform.msgpush.service.EHDeviceInfoService;

/**
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: EHDeviceInfoServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年10月16日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class EHDeviceInfoServiceImpl extends BaseServiceImpl<EHDeviceInfo, String> implements EHDeviceInfoService {
	private static Logger logger = LoggerFactory.getLogger(EHDeviceInfoServiceImpl.class);
	@Autowired
	private EHDeviceInfoDao ehDeviceInfoDao;
	private RedisService redisSvc = SpringContextHolder.getBean(RedisService.class);

	@Override
	protected BaseDao<EHDeviceInfo, String> getDao() {
		return ehDeviceInfoDao;
	}

	@Override
	public EHDeviceInfo findByDeviceId(String deviceId, String deviceType) {
		List<EHDeviceInfo> ehDeviceInfos = new ArrayList<EHDeviceInfo>();
		try {
			// 设置线程池的数量
			int cpuNums = Runtime.getRuntime().availableProcessors();
			ExecutorService collectExec = Executors.newFixedThreadPool(cpuNums * 2);
			List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

			for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				String tableName = SimpleHashTableNameGenerator.EH_DEVICE_INFO + "_" + i;
				paramsMap.put("hashTableName", tableName);
				paramsMap.put("deviceId", deviceId);
				paramsMap.put("deviceType", deviceType);
				Object[] parameters = new Object[] { paramsMap };
				Class<?>[] parameterTypes = new Class[] { Map.class };
				QueryHashTableCallable collectCall = new QueryHashTableCallable(EHDeviceInfoDao.class, "findByDeviceId", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			}

			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				EHDeviceInfo threadRes = (EHDeviceInfo) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (threadRes != null) {
					ehDeviceInfos.add(threadRes);
				}
			}

			// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
			collectExec.shutdown();
		} catch (Exception e) {
			logger.error("根据设备ID查询设备出错!", e);
			e.printStackTrace();
		}

		if (ehDeviceInfos.size() > 0) {
			return ehDeviceInfos.get(0);
		}
		return null;
	}

	@Override
	public List<EHDeviceInfo> findByUserId(Map<String, Object> params) {
		return ehDeviceInfoDao.findByUserId(params);
	}

	@Override
	public boolean saveDeviceInfo(EHDeviceInfo ehDeviceInfo) {
		boolean status = true;

		if (ehDeviceInfo == null || StringUtils.isBlank(ehDeviceInfo.getUserId()) || StringUtils.isBlank(ehDeviceInfo.getDeviceId())
				|| StringUtils.isBlank(ehDeviceInfo.getDeviceType())) {
			return false;
		}
		//避免安卓并发上传设备信息,采用redis锁机制
		String cacheKey = ehDeviceInfo.getUserId();
		boolean isLock = false;
		RedisLock redisLock = new RedisLock(redisSvc.getRedisPool());
		try {
			do {
				isLock = redisLock.singleLock(cacheKey);
			} while (!isLock);
			try {
				EHDeviceInfo temp = null;
				List<EHDeviceInfo> ehDeviceInfos = new ArrayList<EHDeviceInfo>();
				// 设置线程池的数量
				int cpuNums = Runtime.getRuntime().availableProcessors();
				ExecutorService collectExec = Executors.newFixedThreadPool(cpuNums * 2);
				List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

				for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
					Map<String, Object> paramsMap = new HashMap<String, Object>();
					String tableName = SimpleHashTableNameGenerator.EH_DEVICE_INFO + "_" + i;
					paramsMap.put("hashTableName", tableName);
					paramsMap.put("deviceId", ehDeviceInfo.getDeviceId());
					paramsMap.put("deviceType", ehDeviceInfo.getDeviceType());
					Object[] parameters = new Object[] { paramsMap };
					Class<?>[] parameterTypes = new Class[] { Map.class };
					QueryHashTableCallable collectCall =
							new QueryHashTableCallable(EHDeviceInfoDao.class, "findByDeviceId", parameters, parameterTypes);
					// 创建每条指令的采集任务对象
					FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
					// 添加到list,方便后面取得结果
					taskList.add(collectTask);
					// 提交给线程池 exec.submit(task);
					collectExec.submit(collectTask);
				}

				for (FutureTask<Object> taskF : taskList) {
					// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
					EHDeviceInfo threadRes = (EHDeviceInfo) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
					if (threadRes != null) {
						ehDeviceInfos.add(threadRes);
					}
				}

				// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
				collectExec.shutdown();
				if (ehDeviceInfos.size() > 0) {
					temp = ehDeviceInfos.get(0);
				}
				logger.info("上传设备信息:" + JSON.toJSONString(ehDeviceInfo));
				if (temp == null) {
					ehDeviceInfoDao.add(ehDeviceInfo);
				} else {
					ehDeviceInfoDao.deleteByUserId(temp);
					ehDeviceInfoDao.add(ehDeviceInfo);
				}

			} catch (Exception e) {
				status = false;
				logger.error("根据设备ID查询设备出错!", e);
				e.printStackTrace();
			}
		} finally {
			redisLock.singleUnlock(cacheKey);
		}

		return status;
	}

}
