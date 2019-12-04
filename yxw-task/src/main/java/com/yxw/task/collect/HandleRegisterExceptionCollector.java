package com.yxw.task.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.HospitalConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.task.callable.HandleRegisterExceptionCollectCall;

/**
 * @Package: com.yxw.platform.quartz.collect
 * @ClassName: HospitalCollector
 * @Statement: <p>
 *             异常处理采集
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HandleRegisterExceptionCollector {
	public static Logger logger = LoggerFactory.getLogger(HandleRegisterExceptionCollector.class);

	public void start() {
		// 根据采集的机器配置得出默认的线程数
		int threadNum = CollectConstant.threadNum;

		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<CodeAndInterfaceVo> codeAndInterfaceLs = null;
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getValidCodeAndInterfaceMap", new ArrayList<Object>());

		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results);
			codeAndInterfaceLs = new ArrayList<CodeAndInterfaceVo>(results.size());
			codeAndInterfaceLs.addAll(JSON.parseArray(source, CodeAndInterfaceVo.class));

			long startTime = System.currentTimeMillis();
			List<Object> params = new ArrayList<Object>();
			params.add(startTime);
			List<Object> jsonObjects = serveComm.get(CacheType.REGISTER_EXCEPTION_CACHE, "getExceptionRecordsFromCache", params);

			if (jsonObjects.size() < threadNum) {
				threadNum = jsonObjects.size();
			}

			List<ExceptionRecord> exceptionRecords = new ArrayList<ExceptionRecord>(jsonObjects.size());

			if (threadNum > 0) {
				// 设置线程池的数量
				ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("regExce"));
				List<FutureTask<ExceptionRecord>> taskList = new ArrayList<FutureTask<ExceptionRecord>>();
				logger.info("需处理异常订单的医院集合:{}", JSON.toJSONString(codeAndInterfaceLs));
				exceptionRecords = JSON.parseArray(JSON.toJSONString(jsonObjects), ExceptionRecord.class);

				for (ExceptionRecord record : exceptionRecords) {
					String keys =
							record.getHospitalCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(record.getBranchHospitalCode());

					Map<String, CodeAndInterfaceVo> codeAndInterfaceVoMap =
							Maps.uniqueIndex(codeAndInterfaceLs, new Function<CodeAndInterfaceVo, String>() {
								public String apply(CodeAndInterfaceVo codeAndInterfaceVo) {
									return codeAndInterfaceVo.getHospitalCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR)
											.concat(codeAndInterfaceVo.getBranchHospitalCode());
								}
							});

					CodeAndInterfaceVo vo = codeAndInterfaceVoMap.get(keys);

					if (vo != null) {
						boolean isFlag = HospitalConstant.HOSPITAL_VALID_STATUS == vo.getStatus();
						logger.info("需处理异常订单的医院:{}, 是否处理:{}", new Object[] { vo.getHospitalName(), isFlag });

						if (isFlag) {
							String interfaceId = vo.getInterfaceId();

							HandleRegisterExceptionCollectCall collectCall = new HandleRegisterExceptionCollectCall(interfaceId, record);
							// 创建每条指令的采集任务对象
							FutureTask<ExceptionRecord> collectTask = new FutureTask<ExceptionRecord>(collectCall);

							// 添加到list,方便后面取得结果
							taskList.add(collectTask);
							// 提交给线程池 exec.submit(task);
							collectExec.submit(collectTask);
						}
					}
				}

				// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
				try {
					List<ExceptionRecord> updateRecord = new ArrayList<ExceptionRecord>();
					for (FutureTask<ExceptionRecord> taskF : taskList) {
						// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
						ExceptionRecord record = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
						if (record != null) {
							//updateRecord.add(record);
						}
					}

					if (updateRecord.size() > 0) {
						//批量更新RegisterRecord
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 清除旧数据
					params.clear();
					params.add(startTime);
					serveComm.delete(CacheType.REGISTER_EXCEPTION_CACHE, "removeExceptionRecordsFromCache", params);
				}
				// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
				collectExec.shutdown();
			}
		}
	}
}
