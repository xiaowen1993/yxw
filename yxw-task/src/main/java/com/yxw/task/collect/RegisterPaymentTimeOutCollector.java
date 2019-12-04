package com.yxw.task.collect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.task.callable.RegisterPaymentTimeOutCollectCall;
import com.yxw.task.callable.RegisterPaymentTimeOutRunnable;
import com.yxw.task.vo.RegisterTaskResult;

/**
 * @Package: com.yxw.platform.quartz.collect
 * @ClassName: HospitalCollector
 * @Statement: <p>
 *             支付超时的挂号记录处理 轮询采集
 *             </p>
 * @JDK version used:
 * @Author: Yuce
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterPaymentTimeOutCollector {
	public static Logger logger = LoggerFactory.getLogger(RegisterPaymentTimeOutCollector.class);

	public void start() {
		// 根据采集的机器配置得出默认的线程数 
		int threadNum = CollectConstant.threadNum;

		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<CodeAndInterfaceVo> codeAndInterfaceLs = null;
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getValidCodeAndInterfaceMap", new ArrayList<Object>());
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			codeAndInterfaceLs = new ArrayList<CodeAndInterfaceVo>(results.size());
			codeAndInterfaceLs.addAll(JSON.parseArray(source, CodeAndInterfaceVo.class));
			if (codeAndInterfaceLs.size() < threadNum) {
				threadNum = codeAndInterfaceLs.size();
			}

			if (threadNum > 0) {

				//设置线程池的数量
				ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("regPayTimeOut"));
				List<FutureTask<RegisterTaskResult>> taskList = new ArrayList<FutureTask<RegisterTaskResult>>();
				for (CodeAndInterfaceVo vo : codeAndInterfaceLs) {
					if (HospitalConstant.HOSPITAL_VALID_STATUS == vo.getStatus()) {
						String hospitalCode = vo.getHospitalCode();
						String branchHospitalCode = vo.getBranchHospitalCode();
						String interfaceId = vo.getInterfaceId();

						RegisterPaymentTimeOutCollectCall collectCall =
								new RegisterPaymentTimeOutCollectCall(hospitalCode, branchHospitalCode, interfaceId);
						// 创建每条指令的采集任务对象
						FutureTask<RegisterTaskResult> collectTask = new FutureTask<RegisterTaskResult>(collectCall);
						// 添加到list,方便后面取得结果
						taskList.add(collectTask);
						// 提交给线程池 exec.submit(task);
						collectExec.submit(collectTask);
					}
				}

				// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
				List<SimpleRecord> records = new ArrayList<SimpleRecord>();
				try {

					for (FutureTask<RegisterTaskResult> taskF : taskList) {
						// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
						RegisterTaskResult collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
						if (collectData != null) {
							if (!CollectionUtils.isEmpty(collectData.getRecords())) {
								records.addAll(collectData.getRecords());
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
				collectExec.shutdown();

				if (records.size() > 0) {
					//更新数据库 缓存 
					updateRegisterRecord(records);
				}
			}
		}
	}

	/**
	 * 更新数据库 缓存 
	 * @param records
	 */
	private void updateRegisterRecord(List<SimpleRecord> records) {
		Map<String, List<SimpleRecord>> recordMap = new HashMap<String, List<SimpleRecord>>();
		List<SimpleRecord> subList = null;
		String tableName = null;
		boolean isNeedPut = false;
		for (SimpleRecord record : records) {
			tableName = record.getHashTableName();
			subList = recordMap.get(tableName);
			if (subList == null) {
				subList = new ArrayList<SimpleRecord>();
				isNeedPut = true;
			}
			subList.add(record);

			if (isNeedPut) {
				recordMap.put(tableName, subList);
			}
		}

		int threadPoolNum = recordMap.size();
		if (threadPoolNum > CollectConstant.threadNum) {
			threadPoolNum = CollectConstant.threadNum;
		}
		ExecutorService collectExec = Executors.newFixedThreadPool(threadPoolNum, new SimpleThreadFactory("updateRegisterPaymentTimeOut"));
		for (String key : recordMap.keySet()) {
			collectExec.execute(new RegisterPaymentTimeOutRunnable(recordMap.get(key), key));
		}

		collectExec.shutdown();

		try {
			while (!collectExec.awaitTermination(1, TimeUnit.SECONDS)) {
			}
		} catch (InterruptedException e) {
			logger.error("Thread name is updateRegisterPaymentTimeOut, throws interruptedException error info: ", e.getCause());
			Thread.currentThread().interrupt();
		}

	}
}
