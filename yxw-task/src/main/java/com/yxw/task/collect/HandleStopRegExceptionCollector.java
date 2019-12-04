package com.yxw.task.collect;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.cache.StopRegisterException;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.interfaces.vo.register.stopreg.StopReg;
import com.yxw.interfaces.vo.register.stopreg.StopRegRequest;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;
import com.yxw.task.callable.StopRegisterCollectCall;
import com.yxw.task.taskitem.StopRegisterTask;
import com.yxw.task.vo.StopRegTask;

/**
 * @Package: com.yxw.platform.quartz.collect
 * @ClassName: HandleStopRegExceptionCollector
 * @Statement: <p>
 *             停诊异常处理采集
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HandleStopRegExceptionCollector {

	public static Logger logger = LoggerFactory.getLogger(HandleStopRegExceptionCollector.class);

	//    private StopRegisterExceptionCache stopRegisterExceptionCache = SpringContextHolder.getBean(StopRegisterExceptionCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private RegisterBizManager registerBizManager = SpringContextHolder.getBean(RegisterBizManager.class);

	public void start() {

		List<StopRegTask> stopRegs = new ArrayList<StopRegTask>();

		//		Map<String, String> stopMap = stopRegisterExceptionCache.getStopExceptionRegisterFromCache();
		List<Object> params = new ArrayList<Object>();
		List<Object> results = serveComm.get(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "getStopExceptionRegisterFromCache", params);
		Map<String, String> stopMap = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(results)) {
			stopMap = (Map<String, String>) results.get(0);
		}
		logger.info("stopMap:{}.", stopMap);

		if (!CollectionUtils.isEmpty(stopMap)) {

			int threadNum = stopMap.size();
			Set<Entry<String, String>> entrySet = stopMap.entrySet();
			for (Entry<String, String> entry : entrySet) {

				String stopRegExcJson = entry.getValue();
				StopRegisterException stopRegisterException = JSON.parseObject(stopRegExcJson, StopRegisterException.class);

				String branchHospitalCode = stopRegisterException.getBranchCode();
				String regType = stopRegisterException.getRegType();
				String hospitalCode = stopRegisterException.getHospitalCode();

				StopRegRequest stopRegRequest = new StopRegRequest();
				stopRegRequest.setBranchCode(branchHospitalCode);
				stopRegRequest.setRegType(regType);
				stopRegRequest.setBeginDate(stopRegisterException.getBeginDate());
				stopRegRequest.setEndDate(stopRegisterException.getEndDate());

				//查询停诊接口
				Map<String, Object> resMap = registerBizManager.getStopReg(hospitalCode, stopRegRequest);
				StopRegisterTask.logger.info("停诊查询异常处理：查询停诊接口,resMap:{}.", com.alibaba.fastjson.JSON.toJSONString(resMap));
				boolean isException = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
				if (isException) {
					StopRegisterTask.logger.info("hospitalCode:{},查询停诊接口失败,异常写入:{}.", hospitalCode,
							com.alibaba.fastjson.JSON.toJSONString(stopRegisterException));
					//stopRegisterExceptionCache.setExceptionStopRegisterToCache(hospitalCode, stopRegisterException);
				} else {
					boolean isSuccess = (Boolean) resMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
					if (isSuccess) {
						List<StopReg> stopRegResult = (List<StopReg>) resMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
						if (stopRegs != null) {
							for (StopReg stopReg : stopRegResult) {
								StopRegTask stopRegTask = new StopRegTask();
								BeanUtils.copyProperties(stopReg, stopRegTask);
								stopRegTask.setBranchHospitalCode(branchHospitalCode);
								stopRegs.add(stopRegTask);
							}
						}
						//						stopRegisterExceptionCache.removeExceptionStopRegisterFromCache(hospitalCode, branchHospitalCode, stopRegRequest.getRegType());
						params.add(hospitalCode);
						params.add(branchHospitalCode);
						params.add(stopRegRequest.getRegType());
						serveComm.set(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "removeExceptionStopRegisterFromCache", params);
					}
				}
			}

			/***************************************停诊处理****************************************/
			if (stopRegs.size() > 0) {

				if (10 < CollectConstant.threadNum) {
					threadNum = 10;
				}

				// 设置线程池的数量
				ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("stopReg:doBiz"));

				List<FutureTask<List<StopRegTask>>> taskList = new ArrayList<FutureTask<List<StopRegTask>>>();
				for (int i = 0; i < 10; i++) {
					StopRegisterCollectCall call =
							new StopRegisterCollectCall(StopRegisterCollector.STOP_REGISTER_SERVICE_DEAL_TYPE, stopRegs,
									SimpleHashTableNameGenerator.REGISTER_RECORD_TABLE_NAME + "_" + ( i + 1 ));
					// 创建每条指令的采集任务对象
					FutureTask<List<StopRegTask>> collectTask = new FutureTask<List<StopRegTask>>(call);
					// 添加到list,方便后面取得结果
					taskList.add(collectTask);
					// 提交给线程池 exec.submit(task);
					collectExec.submit(collectTask);
				}

				// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
				try {
					for (FutureTask<List<StopRegTask>> taskF : taskList) {
						// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
						List<StopRegTask> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
						if (collectData != null && logger.isInfoEnabled()) {
							logger.info("date:{} stop register success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
				collectExec.shutdown();

			}
		}

	}

}
