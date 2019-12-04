package com.yxw.task.collect;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.StopRegisterLog;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.cache.StopRegisterException;
import com.yxw.commons.vo.platform.hospital.HospitalCodeInterfaceAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.interfaces.constants.PayPlatformType;
import com.yxw.interfaces.vo.register.stopreg.StopRegRequest;
import com.yxw.mobileapp.biz.register.service.StopRegisterLogService;
import com.yxw.task.callable.StopRegisterCollectCall;
import com.yxw.task.vo.StopRegTask;
import com.yxw.utils.DateUtils;

/*  停诊处理
*             </p>
* @JDK version used:
* @Author: Yuce
* @Create Date: 2015-5-15
* @modify By:
* @modify Date:
* @Why&What is modify:
* @Version: 1.0
*/
public class StopRegisterCollector {
	public static Logger logger = LoggerFactory.getLogger(StopRegisterCollector.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private StopRegisterLogService stopRegisterLogService = SpringContextHolder.getBean(StopRegisterLogService.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	private Long loopTime;

	/**
	 * 分表数量
	 */
	private static final int TABLE_SIZE = 10;

	/**
	 * 停诊接口查询(轮询)
	 */
	public static final int STOP_REGISTER_QUERY_INTERFACE_DEAL_TYPE = 0;

	/**
	 * 停诊业务处理(轮询)
	 */
	public static final int STOP_REGISTER_SERVICE_DEAL_TYPE = 1;

	/**
	 * 停诊业务处理(停诊接口调用)
	 */
	public static final int STOP_REGISTER_SERVICE_DEAL_TYPE_BY_OUTSIDE = 2;

	/**
	 * 异常执行次数
	 */
	private static int EXCEPTION_DEAL_COUNT = 5;

	/**
	 * 异常执行间隔
	 */
	private static long EXCEPTION_DEAL_TIME = 1000 * 10;

	/**
	 * 定时轮询标识
	 */
	public static final int STOP_REGISTER_ACCEPT_STOP_INFO_TYPE = 1;

	public StopRegisterCollector(Long loopTime) {
		super();
		this.loopTime = loopTime;
	}

	@SuppressWarnings("unchecked")
	public void start() {

		int dealCount = 1;
		// 根据采集的机器配置得出默认的线程数
		int threadNum = CollectConstant.threadNum;
		List<StopRegTask> stopRegTasks = null;
		// 获取全部停诊异常队列
		//		Map<String, String> mapException = stopRegisterExceptionCache.getStopExceptionRegisterFromCache();
		List<Object> params = new ArrayList<Object>();
		List<Object> results = serveComm.get(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "getStopExceptionRegisterFromCache", params);
		Map<String, String> mapException = new HashMap<String, String>();
		if (!CollectionUtils.isEmpty(results)) {
			mapException = (Map<String, String>) results.get(0);
		}
		logger.info("mapException:{}.", mapException);
		do {
			if (mapException.isEmpty()) {
				/**
				 * 第一步：分线程查询停诊接口
				 */
				stopRegTasks = queryInterfaceStopRegister(threadNum);
				logger.info("stopRegTasks:{}.", com.alibaba.fastjson.JSON.toJSONString(stopRegTasks));
				/**
				 * 第二步：处理需要停诊的号源
				 */
				if (!CollectionUtils.isEmpty(stopRegTasks)) {
					dealStopRegister(threadNum, stopRegTasks);
				} else {
					logger.info("date:{} stop register error. stopRegs is null.",
							new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
				}
			} else {
				try {
					logger.info("开始执行异常，当前执行次数： {} 次", dealCount);
					if (dealCount >= EXCEPTION_DEAL_COUNT) {
						//执行超过EXCEPTION_DEAL_COUNT不执行,日志入库且缓存全部删除
						saveExceptionStopRegisterData(mapException);
						serveComm.delete(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "removeExceptionStopRegisterAll", params);
					} else {
						//休眠一下
						long startLong = System.currentTimeMillis();
						Thread.sleep(EXCEPTION_DEAL_TIME);
						logger.info("休眠结束,休眠间隔： {} s.", ( ( System.currentTimeMillis() - startLong ) / 1000 ));
						stopRegTasks = queryInterfaceStopRegisterForException(threadNum, mapException);
						dealCount++;
						/**
						 * 第二步：处理需要停诊的号源
						 */
						if (!CollectionUtils.isEmpty(stopRegTasks)) {
							dealStopRegister(threadNum, stopRegTasks);
						} else {
							logger.info("date:{} stop register error. stopRegs is null.",
									new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
						}
					}
					logger.info("结束执行异常，当前执行次数： {} 次", dealCount);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			results = serveComm.get(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "getStopExceptionRegisterFromCache", params);
			if (!CollectionUtils.isEmpty(results)) {
				mapException = (Map<String, String>) results.get(0);
			}

		} while (!mapException.isEmpty());

	}

	/**
	 * 停诊异常日志入库
	 * @param mapException
	 */
	private void saveExceptionStopRegisterData(Map<String, String> mapException) {
		for (Map.Entry<String, String> entry : mapException.entrySet()) {
			StopRegisterException stopRegisterException = null;

			stopRegisterException = JSON.parseObject(entry.getValue(), StopRegisterException.class);
			StopRegisterLog stopRegisterLog = new StopRegisterLog();
			stopRegisterLog.setHospitalCode(stopRegisterException.getHospitalCode());
			stopRegisterLog.setBranchHospitalCode(stopRegisterException.getBranchCode());
			stopRegisterLog.setRegMode(Integer.parseInt(stopRegisterException.getRegType()));
			stopRegisterLog.setBeginDate(stopRegisterException.getBeginDate());
			stopRegisterLog.setEndDate(stopRegisterException.getEndDate());
			stopRegisterLogService.add(stopRegisterLog);

		}
	}

	/**
	 * 第一步：分线程查询停诊接口
	 * @param threadNum
	 */
	private List<StopRegTask> queryInterfaceStopRegister(Integer threadNum) {
		List<StopRegTask> stopRegs = new ArrayList<StopRegTask>();
		List<FutureTask<List<StopRegTask>>> taskStopRegList = new ArrayList<FutureTask<List<StopRegTask>>>();
		//		List<HospitalCodeInterfaceAndAppVo> hospitalCodeInterfaceAndAppVos = hospitalService.queryCodeAndInterfaceIdAndApp();
		List<HospitalCodeInterfaceAndAppVo> hospitalCodeInterfaceAndAppVos = null;
		List<Object> params = new ArrayList<>();
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getHospitalCodeInterfaceAndAppVos", params);
		if (!CollectionUtils.isEmpty(objects)) {
			String source = JSON.toJSONString(objects);
			hospitalCodeInterfaceAndAppVos = JSON.parseArray(source, HospitalCodeInterfaceAndAppVo.class);
		}

		if (!CollectionUtils.isEmpty(hospitalCodeInterfaceAndAppVos)) {
			if (hospitalCodeInterfaceAndAppVos.size() < threadNum) {
				threadNum = hospitalCodeInterfaceAndAppVos.size();
			}
			// 设置线程池的数量
			ExecutorService collectExecStop = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("stopReg:queryHis"));
			for (HospitalCodeInterfaceAndAppVo vo : hospitalCodeInterfaceAndAppVos) {
				String hospitalCode = vo.getHospitalCode();
				RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(hospitalCode);
				Date noeDate = new Date();
				logger.info("ruleEdit:{}", com.alibaba.fastjson.JSON.toJSONString(ruleEdit));
				if (ruleEdit != null) {
					logger.info("hospitalCode：{} , acceptStopInfoType:{} .",
							new Object[] { hospitalCode, ruleEdit.getAcceptStopInfoType() });
					if (ruleEdit.getAcceptStopInfoType().intValue() == STOP_REGISTER_ACCEPT_STOP_INFO_TYPE) {

						boolean isFlag = false;
						String timeStr = ruleEdit.getPushInfoTime();
						String[] times = timeStr.split(",");
						for (String time : times) {
							String item[] = time.split(":");
							Calendar calendar = Calendar.getInstance();
							calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(item[0]));
							calendar.set(Calendar.MINUTE, Integer.parseInt(item[1]));
							calendar.set(Calendar.SECOND, 0);
							Date startTime = calendar.getTime();
							Date endTime = new Date(startTime.getTime() + loopTime);
							boolean compareResult = ( noeDate.getTime() >= startTime.getTime() && noeDate.getTime() < endTime.getTime() );
							isFlag = isFlag || compareResult;
							logger.info("规则配置的推送停诊信息时间[" + hospitalCode + "]:" + time + "比较结果：isFlag= " + isFlag);
						}

						if (isFlag) {
							String branchHospitalCode = vo.getBranchHospitalCode();
							String appCode = vo.getAppCode();
							String cancleMode =
									appCode.equalsIgnoreCase(BizConstant.MODE_TYPE_ALIPAY) ? PayPlatformType.ALIPAY
											: PayPlatformType.WECHAT;
							StopRegRequest stopRegRequest = new StopRegRequest();
							stopRegRequest.setBranchCode(branchHospitalCode);
							stopRegRequest.setRegType(cancleMode);
							stopRegRequest.setBeginDate(DateUtils.today());
							stopRegRequest.setEndDate(DateUtils.getDayDate(ruleEdit.getPushInfoDay() + 1));
							Callable<List<StopRegTask>> call =
									new StopRegisterCollectCall(STOP_REGISTER_QUERY_INTERFACE_DEAL_TYPE, hospitalCode, branchHospitalCode,
											appCode, stopRegRequest);
							// 创建每条指令的采集任务对象
							FutureTask<List<StopRegTask>> collectTask = new FutureTask<List<StopRegTask>>(call);
							// 添加到list,方便后面取得结果
							taskStopRegList.add(collectTask);
							// 提交给线程池 exec.submit(task);
							collectExecStop.submit(collectTask);
						} else {
							logger.info("nowDate:{} ,hospitalCode:{}", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(noeDate),
									hospitalCode });
						}
					}
				} else {
					logger.info("date:{} ruleEdit is null.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
				}
			}
			// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
			try {
				for (FutureTask<List<StopRegTask>> taskF : taskStopRegList) {
					// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
					List<StopRegTask> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
					if (collectData != null && logger.isInfoEnabled()) {
						//将线程查询停诊接口返回的数据添加到停诊数据集
						stopRegs.addAll(collectData);
						logger.info("date:{} stop register success. StopRegs:{}.",
								new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()), JSON.toJSONString(collectData) });
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
			collectExecStop.shutdown();
		} else {
			logger.error("date:{} stop register error. hospitalCodeInterfaceAndAppVos is null",
					BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		}
		return stopRegs;
	}

	private List<StopRegTask> queryInterfaceStopRegisterForException(Integer threadNum, Map<String, String> mapException) {
		List<FutureTask<List<StopRegTask>>> taskStopRegList = new ArrayList<FutureTask<List<StopRegTask>>>();
		List<StopRegTask> stopRegs = new ArrayList<StopRegTask>();
		if (mapException.size() < threadNum) {
			threadNum = mapException.size();
		}

		ExecutorService collectExecStop = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("stopReg:queryExces"));

		for (Map.Entry<String, String> entry : mapException.entrySet()) {
			StopRegisterException stopRegisterException = JSON.parseObject(entry.getValue(), StopRegisterException.class);
			String hospitalCode = stopRegisterException.getHospitalCode();
			String branchHospitalCode = stopRegisterException.getBranchCode();
			String appCode = stopRegisterException.getRegType();
			String cancleMode = appCode.equalsIgnoreCase(BizConstant.MODE_TYPE_ALIPAY) ? PayPlatformType.ALIPAY : PayPlatformType.WECHAT;
			StopRegRequest stopRegRequest = new StopRegRequest();
			stopRegRequest.setBranchCode(branchHospitalCode);
			stopRegRequest.setRegType(cancleMode);
			stopRegRequest.setBeginDate(DateUtils.tomorrowDate());
			stopRegRequest.setEndDate(DateUtils.tomorrowDate());
			Callable<List<StopRegTask>> call =
					new StopRegisterCollectCall(STOP_REGISTER_QUERY_INTERFACE_DEAL_TYPE, hospitalCode, branchHospitalCode, appCode,
							stopRegRequest);
			// 创建每条指令的采集任务对象
			FutureTask<List<StopRegTask>> collectTask = new FutureTask<List<StopRegTask>>(call);
			// 添加到list,方便后面取得结果
			taskStopRegList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExecStop.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<StopRegTask>> taskF : taskStopRegList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<StopRegTask> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					//将线程查询停诊接口返回的数据添加到停诊数据集
					stopRegs.addAll(collectData);
					logger.info("date:{} stop register success. StopRegs:{}.",
							new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()), JSON.toJSONString(collectData) });
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExecStop.shutdown();
		return stopRegs;
	}

	/**
	 * 第二步：处理需要停诊的号源
	 * @param threadNum
	 * @param stopRegs
	 */
	private void dealStopRegister(Integer threadNum, List<StopRegTask> stopRegTasks) {
		if (TABLE_SIZE < threadNum) {
			threadNum = TABLE_SIZE;
		}

		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory("stopReg:doBiz"));

		List<FutureTask<List<StopRegTask>>> taskList = new ArrayList<FutureTask<List<StopRegTask>>>();
		for (int i = 0; i < TABLE_SIZE; i++) {
			StopRegisterCollectCall call =
					new StopRegisterCollectCall(STOP_REGISTER_SERVICE_DEAL_TYPE, stopRegTasks,
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
