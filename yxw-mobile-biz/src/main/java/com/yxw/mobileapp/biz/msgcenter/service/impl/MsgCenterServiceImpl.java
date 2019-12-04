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
package com.yxw.mobileapp.biz.msgcenter.service.impl;

import java.io.Serializable;
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

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.yxw.commons.entity.mobile.biz.msgcenter.MsgCenter;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.framework.utils.QueryHashTableCallable;
import com.yxw.mobileapp.biz.msgcenter.dao.MsgCenterDao;
import com.yxw.mobileapp.biz.msgcenter.service.MsgCenterService;

/**
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgCenterServiceImpl
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
public class MsgCenterServiceImpl extends BaseServiceImpl<MsgCenter, String> implements MsgCenterService {
	private static Logger logger = LoggerFactory.getLogger(MsgCenterServiceImpl.class);
	@Autowired
	private MsgCenterDao msgCenterDao;

	@Override
	protected BaseDao<MsgCenter, String> getDao() {
		return msgCenterDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MsgCenter findById(String id) {
		List<MsgCenter> msgCenters = new ArrayList<MsgCenter>();
		try {
			// 设置线程池的数量
			int cpuNums = Runtime.getRuntime().availableProcessors();
			ExecutorService collectExec = Executors.newFixedThreadPool(cpuNums * 2);
			List<FutureTask<Object>> taskList = new ArrayList<FutureTask<Object>>();

			for (int i = 1; i < SimpleHashTableNameGenerator.subTableCount + 1; i++) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				String tableName = SimpleHashTableNameGenerator.SYS_MSG_CENTER + "_" + i;
				paramsMap.put("id", id);
				paramsMap.put("hashTableName", tableName);
				Object[] parameters = new Object[] { paramsMap };
				Class<?>[] parameterTypes = new Class[] { Map.class };
				QueryHashTableCallable collectCall = new QueryHashTableCallable(MsgCenterDao.class, "findById", parameters, parameterTypes);
				// 创建每条指令的采集任务对象
				FutureTask<Object> collectTask = new FutureTask<Object>(collectCall);
				// 添加到list,方便后面取得结果
				taskList.add(collectTask);
				// 提交给线程池 exec.submit(task);
				collectExec.submit(collectTask);
			}

			for (FutureTask<Object> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<MsgCenter> threadRes = (List<MsgCenter>) taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (!CollectionUtils.isEmpty(threadRes)) {
					msgCenters.addAll(threadRes);
				}
			}

			// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
			collectExec.shutdown();
		} catch (Exception e) {
			logger.error("根据设备ID查询设备出错!", e);
			e.printStackTrace();
		}

		if (msgCenters.size() > 0) {
			return msgCenters.get(0);
		}
		return null;
	}

	@Override
	public MsgCenter findById(String userId, String id) {
		Map<String, Serializable> params = new HashMap<String, Serializable>();
		params.put("userId", userId);
		params.put("id", id);
		return msgCenterDao.findById(params);
	}

	@Override
	public void updateIsRead(String userId, String id, Integer isRead) {
		Map<String, Serializable> params = new HashMap<String, Serializable>();
		params.put("userId", userId);
		params.put("id", id);
		params.put("isRead", isRead);
		msgCenterDao.updateIsRead(params);

	}

	@Override
	public Integer findCountByIsRead(String userId, Integer isRead) {
		Map<String, Serializable> params = new HashMap<String, Serializable>();
		params.put("userId", userId);
		params.put("isRead", isRead);
		return msgCenterDao.findCountByIsRead(params);
	}

}
