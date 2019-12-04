package com.yxw.solr.outside.service.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.solr.biz.rebuild.callable.ClinicRebuildCallable;
import com.yxw.solr.biz.rebuild.callable.DepositRebuildCallable;
import com.yxw.solr.biz.rebuild.callable.MedicalcardRebuildCallable;
import com.yxw.solr.biz.rebuild.callable.OrderRebuildCallable;
import com.yxw.solr.biz.rebuild.callable.RebuildCallable;
import com.yxw.solr.biz.rebuild.callable.RegDeptRebuildCallable;
import com.yxw.solr.biz.rebuild.callable.RegisterRebuildCallable;
import com.yxw.solr.constants.BizConstant;
import com.yxw.solr.constants.PoolConstant;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.outside.service.YxwRebuildService;
import com.yxw.solr.vo.rebuild.RebuildRequest;
import com.yxw.solr.vo.rebuild.RebuildResponse;
import com.yxw.utils.DateUtils;

@Service("yxwRebuildService")
public class YxwRebuildServiceImpl implements YxwRebuildService {
	
	private final Map<Integer, Long> rebuildMap = new ConcurrentHashMap<>();
	
	private Logger logger = LoggerFactory.getLogger(YxwRebuildService.class);
	
	@Override
	public RebuildResponse rebuild(RebuildRequest request) {
		RebuildResponse response = null;
		
		Integer bizCode = Math.abs(request.getBizCode());
		
		long beginTime = getMapValue(bizCode);
		
		if (beginTime == 0) {		// 没有处理中的任务，可以执行
			beginTime = putMapValue(bizCode);
			logger.info("接受[{}]索引重建处理 -- {}.", new Object[]{bizCode, DateUtils.dateToString(new Date(beginTime), "yyyy-MM-dd HH:mm:ss")});
			
			switch (bizCode) {
			case BizConstant.BIZ_TYPE_ORDER:
				response = rebuildOrder(request);
				break;
			case BizConstant.BIZ_TYPE_REGISTER:
				response = rebuildRegister(request);
				break;
			case BizConstant.BIZ_TYPE_CLINIC:
				response = rebuildClinic(request);
				break;
			case BizConstant.BIZ_TYPE_DEPOSIT:
				response = rebuildDeposit(request);
				break;
			case BizConstant.BIZ_TYPE_CARD:
				response = rebuildCard(request) ;
				break;
			case BizConstant.BIZ_TYPE_DEPT:
				response = rebuildRegDept(request);
				break;
			default:
				response = new RebuildResponse();
				response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
				response.setResultMessage("非法的业务代码![" + bizCode + "]");
				break;
			}
		} else {					// 有对应任务，返回在处理，请稍后~
			response = new RebuildResponse();
			response.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			response.setResultMessage("任务正在处理中，请稍候！");
		}
		
		return response;
	}
	
	private RebuildResponse rebuildOrder(RebuildRequest vo) {
		RebuildResponse response = new RebuildResponse();
		
		try {
			if (rebuildOrderStats(OrderRebuildCallable.class, vo.getHospitalInfosMap(), vo.getHospitalCode(), vo.getBeginDate())) {
				response.setResultCode(ResultConstant.RESULT_CODE_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("订单索引重建异常. errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage("订单索引重建异常");
		}
		
		return response;
	}
	
	private RebuildResponse rebuildRegister(RebuildRequest vo) {
		RebuildResponse response = new RebuildResponse();
		
		try {
			if (handleOrderRebuild(RegisterRebuildCallable.class, vo.getHospitalInfosMap(), vo.getHospitalCode(), vo.getBeginDate())) {
				response.setResultCode(ResultConstant.RESULT_CODE_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("挂号索引重建异常. errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage("挂号索引重建异常");
		}
		
		return response;
	}
	
	private RebuildResponse rebuildClinic(RebuildRequest vo) {
		RebuildResponse response = new RebuildResponse();
		
		try {
			if (handleOrderRebuild(ClinicRebuildCallable.class, vo.getHospitalInfosMap(), vo.getHospitalCode(), vo.getBeginDate())) {
				response.setResultCode(ResultConstant.RESULT_CODE_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("门诊索引重建异常. errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage("门诊索引重建异常");
		}
		
		return response;
	}
	
	private RebuildResponse rebuildDeposit(RebuildRequest vo) {
		RebuildResponse response = new RebuildResponse();
		
		try {
			if (handleOrderRebuild(DepositRebuildCallable.class, vo.getHospitalInfosMap(), vo.getHospitalCode(), vo.getBeginDate())) {
				response.setResultCode(ResultConstant.RESULT_CODE_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("押金索引重建异常. errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage("押金索引重建异常");
		}
		
		return response;
	}
	
	private RebuildResponse rebuildCard(RebuildRequest vo) {
		RebuildResponse response = new RebuildResponse();
		
		try {
			if (handleCommonRebuild(MedicalcardRebuildCallable.class, vo.getHospitalInfosMap(), vo.getHospitalCode(), vo.getBeginDate())) {
				response.setResultCode(ResultConstant.RESULT_CODE_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("绑卡索引重建异常. errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage("绑卡索引重建异常");
		}
		
		return response;
	}
	
	private RebuildResponse rebuildRegDept(RebuildRequest vo) {
		RebuildResponse response = new RebuildResponse();
		
		try {
			if (handleNoBranchRebuild(RegDeptRebuildCallable.class, vo.getHospitalInfosMap(), vo.getHospitalCode(), vo.getBeginDate())) {
				response.setResultCode(ResultConstant.RESULT_CODE_SUCCESS);
			}
		} catch (Exception e) {
			logger.error("挂号科室索引重建异常. errorMsg: {}. cause by: {}.", new Object[]{e.getMessage(), e.getCause()});
			response.setResultCode(ResultConstant.RESULT_CODE_ERROR);
			response.setResultMessage("挂号科室索引重建异常");
		}
		
		return response;
	}
	
	private long getMapValue(Integer key) {
		long result = 0;
		synchronized (rebuildMap) {
			if (rebuildMap.containsKey(key)) {
				result = rebuildMap.get(key);
			}
		}
		
		return result;
	}
	
	private long putMapValue(Integer key) {
		long result = 0;
		synchronized (rebuildMap) {
			if (!rebuildMap.containsKey(key)) {
				result = new Date().getTime();
				rebuildMap.put(key, result);
			}
		}
		
		return result;
	}
	
	// 处理订单类的索引重建
	@SuppressWarnings("rawtypes")
	private boolean handleOrderRebuild(Class clazz, Map<String, List<String>> infosMap, String hospitalCode, String beginDate) throws Exception {
		boolean result = false;
		// 处理业务订单
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory("statsCard"));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		@SuppressWarnings("unchecked")
		Constructor<RebuildCallable> constructor = clazz.getConstructor(Integer.class, String.class, String.class, String.class);

		/** ------------------------------------- 指定平台的统计 -------------------------------------------- **/
		for (Entry<String, List<String>> platformMap : infosMap.entrySet()) {
			String platform = platformMap.getKey();
			List<String> branchCodes = platformMap.getValue();

			for (String branchCode : branchCodes) {
				Callable<String> callable = constructor.newInstance(Integer.valueOf(platform), hospitalCode, branchCode, beginDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
				
			}
			
			// 统计所有分院
			Callable<String> callable = constructor.newInstance(Integer.valueOf(platform), hospitalCode, "-1", beginDate);
			FutureTask<String> task = new FutureTask<String>(callable);
			taskList.add(task);
			executorService.submit(task);
		}
			
		for (FutureTask<String> task : taskList) {
			// 后续可能需要改成1天
			String object = task.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
			if (StringUtils.isNoneBlank(object)) {
				logger.info(object);
			} 
		}
		
		/** ------------------------------------- 全关平台的统计 -------------------------------------------- **/
		taskList.clear();
		for (Entry<String, List<String>> platformMap : infosMap.entrySet()) {
			List<String> branchCodes = platformMap.getValue();

			for (String branchCode : branchCodes) {
				Callable<String> callable = constructor.newInstance(-1, hospitalCode, branchCode, beginDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
				
			}
			
			// 统计所有分院
			Callable<String> callable = constructor.newInstance(-1, hospitalCode, "-1", beginDate);
			FutureTask<String> task = new FutureTask<String>(callable);
			taskList.add(task);
			executorService.submit(task);
			
			// 每家医院 按平台来 只要做一次即可
			break;
		}
		
		for (FutureTask<String> task : taskList) {
			// 后续可能需要改成1天
			String object = task.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
			if (StringUtils.isNoneBlank(object)) {
				logger.info(object);
			} 
		}
		
		executorService.shutdown();
		result = true;
		
		return result && rebuildOrderStats(OrderRebuildCallable.class, infosMap, hospitalCode, beginDate);
	}
	
	// 由handleOrderRebuild调用，在处理完本来的业务后，对总订单索引进行重建。
	@SuppressWarnings("rawtypes")
	private boolean rebuildOrderStats(Class clazz, Map<String, List<String>> infosMap, String hospitalCode, String beginDate) throws Exception {
		return handleCommonRebuild(clazz, infosMap, hospitalCode, beginDate);
	}
	
	// 处理绑卡和科室订单的索引重建
	@SuppressWarnings("rawtypes")
	private boolean handleCommonRebuild(Class clazz, Map<String, List<String>> infosMap, String hospitalCode, String beginDate) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory(clazz.getName()));
		List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

		@SuppressWarnings("unchecked")
		Constructor<RebuildCallable> constructor = clazz.getConstructor(Integer.class, String.class, String.class, String.class);

		/** ------------------------------------- 指定平台的统计 -------------------------------------------- **/
		for (Entry<String, List<String>> platformMap : infosMap.entrySet()) {
			String platform = platformMap.getKey();
			List<String> branchCodes = platformMap.getValue();

			for (String branchCode : branchCodes) {
				Callable<String> callable = constructor.newInstance(Integer.valueOf(platform), hospitalCode, branchCode, beginDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
				
			}
			
			// 统计所有分院
			Callable<String> callable = constructor.newInstance(Integer.valueOf(platform), hospitalCode, "-1", beginDate);
			FutureTask<String> task = new FutureTask<String>(callable);
			taskList.add(task);
			executorService.submit(task);
		}
			
		for (FutureTask<String> task : taskList) {
			// 后续可能需要改成1天
			String object = task.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
			if (StringUtils.isNoneBlank(object)) {
				logger.info(object);
			} 
		}
		
		/** ------------------------------------- 全关平台的统计 -------------------------------------------- **/
		taskList.clear();
		for (Entry<String, List<String>> platformMap : infosMap.entrySet()) {
			List<String> branchCodes = platformMap.getValue();

			for (String branchCode : branchCodes) {
				Callable<String> callable = constructor.newInstance(-1, hospitalCode, branchCode, beginDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
				
			}
			
			// 统计所有分院
			Callable<String> callable = constructor.newInstance(-1, hospitalCode, "-1", beginDate);
			FutureTask<String> task = new FutureTask<String>(callable);
			taskList.add(task);
			executorService.submit(task);
			
			// 每家医院 按平台来 只要做一次即可
			break;
		}
		
		for (FutureTask<String> task : taskList) {
			// 后续可能需要改成1天
			String object = task.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
			if (StringUtils.isNoneBlank(object)) {
				logger.info(object);
			} 
		}
		
		executorService.shutdown();
		return true;
	}
	
	// 处理绑卡和科室订单的索引重建
		@SuppressWarnings("rawtypes")
		private boolean handleNoBranchRebuild(Class clazz, Map<String, List<String>> infosMap, String hospitalCode, String beginDate) throws Exception {
			ExecutorService executorService = Executors.newFixedThreadPool(PoolConstant.threadNum, new SimpleThreadFactory(clazz.getName()));
			List<FutureTask<String>> taskList = new ArrayList<FutureTask<String>>();

			@SuppressWarnings("unchecked")
			Constructor<RebuildCallable> constructor = clazz.getConstructor(Integer.class, String.class, String.class, String.class);

			/** ------------------------------------- 指定平台的统计 -------------------------------------------- **/
			for (Entry<String, List<String>> platformMap : infosMap.entrySet()) {
				String platform = platformMap.getKey();
				Callable<String> callable = constructor.newInstance(Integer.valueOf(platform), hospitalCode, "-1", beginDate);
				FutureTask<String> task = new FutureTask<String>(callable);
				taskList.add(task);
				executorService.submit(task);
			}
				
			for (FutureTask<String> task : taskList) {
				// 后续可能需要改成1天
				String object = task.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
				if (StringUtils.isNoneBlank(object)) {
					logger.info(object);
				} 
			}
			
			/** ------------------------------------- 全关平台的统计 -------------------------------------------- **/
			taskList.clear();
			Callable<String> callable = constructor.newInstance(-1, hospitalCode, "-1", beginDate);
			FutureTask<String> tempTask = new FutureTask<String>(callable);
			taskList.add(tempTask);
			executorService.submit(tempTask);
			
			for (FutureTask<String> task : taskList) {
				// 后续可能需要改成1天
				String object = task.get(Integer.MAX_VALUE, TimeUnit.SECONDS);
				if (StringUtils.isNoneBlank(object)) {
					logger.info(object);
				} 
			}
			
			executorService.shutdown();
			return true;
		}
	
}
