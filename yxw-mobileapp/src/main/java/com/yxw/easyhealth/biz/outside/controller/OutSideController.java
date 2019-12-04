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
package com.yxw.easyhealth.biz.outside.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.OutsideConstant;
import com.yxw.commons.constants.biz.ReceiveConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.easyhealth.biz.outside.entity.in.OrdersParams;
import com.yxw.easyhealth.biz.outside.entity.in.SubRefundParams;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.common.threadpool.SimpleThreadFactory;
import com.yxw.mobileapp.invoke.dto.Response;
import com.yxw.mobileapp.invoke.dto.inside.RgParams;
import com.yxw.mobileapp.invoke.impl.OutsideInvokeServiceImpl;
import com.yxw.mobileapp.invoke.thread.OrdersQueryCallable;

/**
 * @Package: com.yxw.mobileapp.biz.outside
 * @ClassName: OutSideController
 * @Statement: <p>controller接口类</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/interface/outside")
public class OutSideController {

	private Logger logger = LoggerFactory.getLogger(OutSideController.class);
	private OutsideInvokeServiceImpl outsideInvokeService = SpringContextHolder.getBean(OutsideInvokeServiceImpl.class);

	private final static int DEFAULT_POOL_SIZE = 2;

	/**
	 * 退费
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/subRefundServlet")
	public String subRefundServlet(SubRefundParams params) {
		RgParams op =
				new RgParams(params.getAppId(), params.getAgencyType(), params.getPayType(), params.getHisNewOrdNum(),
						params.getPsOrdNum(), params.getRefundAmout(), params.getRefundTime(), params.getRefundReason(),
						params.getRefundType(), params.getPushType());
		return JSON.toJSONString(outsideInvokeService.refundGeneral(op));
	}

	/**
	 * 订单查询
	 * @param params
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getHospitalOrder")
	public String getHospitalOrder(OrdersParams params) {
		Response result = checkOrdersParams(params);
		if (result != null) {
			return JSON.toJSONString(result);
		} else {
			List<OrdersQueryResult> item = new ArrayList<OrdersQueryResult>();
			//			List<HospitalCodeInterfaceAndAppVo> vos = hospitalService.queryCodeAndInterfaceIdAndAppByAppId(params.getAppid());
			ServeComm comm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> queryParams = new ArrayList<Object>();
			// 此处的APPCode需要做判断，后期改动
			queryParams.add(params.getAgencyType()); // 怎么转具体，需要后面改动
			queryParams.add(params.getAppid());
			List<Object> objs = comm.get(CacheType.HOSPITAL_CACHE, "queryHospitalCodeByApp", queryParams);
			String source = JSON.toJSONString(objs);
			@SuppressWarnings("unchecked")
			List<HospitalCodeAndAppVo> vos = (List<HospitalCodeAndAppVo>) JSON.parseObject(source, HospitalCodeAndAppVo.class);

			if (CollectionUtils.isEmpty(vos)) {
				result = new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "appId does not exist");
				return JSON.toJSONString(result);
			}
			String hospitalId = vos.get(0).getHospitalId();
			try {
				if (StringUtils.isNotBlank(params.getOrderType())) {
					String hashTableName = (String) ReceiveConstant.tradeTypeParams.get(params.getOrderType());
					if (params.getOrderType().equals(String.valueOf(BizConstant.BIZ_TYPE_REGISTER))) {//挂号订单查询
						//获取表名及表名集合
						item.addAll(queryRegisterRecord(hospitalId, "", params.getAgencyType(), params.getBeginDate(), params.getEndDate(),
								hashTableName, params.getOrderType()));
					}
					if (params.getOrderType().equals(String.valueOf(BizConstant.BIZ_TYPE_CLINIC))) {//门诊订单查询
						//获取表名及表名集合
						item.addAll(queryClinicRecord(hospitalId, "", params.getAgencyType(), params.getBeginDate(), params.getEndDate(),
								hashTableName, params.getOrderType()));
					}
				} else {

					String hashTableName = (String) ReceiveConstant.tradeTypeParams.get(String.valueOf(BizConstant.BIZ_TYPE_REGISTER));
					item.addAll(queryRegisterRecord(hospitalId, "", params.getAgencyType(), params.getBeginDate(), params.getEndDate(),
							hashTableName, String.valueOf(BizConstant.BIZ_TYPE_REGISTER)));
					hashTableName = (String) ReceiveConstant.tradeTypeParams.get(String.valueOf(BizConstant.BIZ_TYPE_CLINIC));
					item.addAll(queryClinicRecord(hospitalId, "", params.getAgencyType(), params.getBeginDate(), params.getEndDate(),
							hashTableName, String.valueOf(BizConstant.BIZ_TYPE_CLINIC)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", JSON.toJSONString(item));
			return JSON.toJSONString(result);
		}
	}

	private Response checkOrdersParams(OrdersParams op) {
		if (StringUtils.isBlank(op.getAppid())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appid cannot be null");
		}
		if (StringUtils.isBlank(op.getSaction())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "saction cannot be null");
		}
		if (StringUtils.isBlank(op.getAgencyType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "agencyType cannot be null");
		}
		if (StringUtils.isBlank(op.getSecretCode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "secretCode cannot be null");
		}
		if (StringUtils.isBlank(op.getBeginDate())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "beginDate cannot be null");
		}
		if (StringUtils.isBlank(op.getEndDate())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "endDate cannot be null");
		}
		return null;
	}

	/**
	 * 查询挂号订单
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @param hashTableName	
	 * @param orderMode
	 * @return
	 */
	public List<OrdersQueryResult> queryRegisterRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode) {
		List<OrdersQueryResult> results = new ArrayList<OrdersQueryResult>();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE, new SimpleThreadFactory("ordersQuery:doBiz"));
		List<FutureTask<List<OrdersQueryResult>>> taskList = new ArrayList<FutureTask<List<OrdersQueryResult>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			OrdersQueryCallable queryRunnable =
					new OrdersQueryCallable(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName + "_" + ( i + 1 ),
							orderMode);
			// 创建每条指令的采集任务对象
			FutureTask<List<OrdersQueryResult>> collectTask = new FutureTask<List<OrdersQueryResult>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<OrdersQueryResult>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<OrdersQueryResult> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					if (logger.isDebugEnabled()) {
						logger.info("date:{} orders query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
					}

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("查询挂号订单:{}.", com.alibaba.fastjson.JSON.toJSONString(results));
		}
		return results;
	}

	/**
	 * 查询门诊订单
	 * @param hospitalId
	 * @param startTime
	 * @param endTime
	 * @param hashTableName
	 * @param orderMode
	 * @return
	 */
	public List<OrdersQueryResult> queryClinicRecord(String hospitalId, String branchCode, String tradeMode, String startTime,
			String endTime, String hashTableName, String orderMode) {
		List<OrdersQueryResult> results = new ArrayList<OrdersQueryResult>();
		// 设置线程池的数量
		ExecutorService collectExec = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE, new SimpleThreadFactory("ordersQuery:doBiz"));
		List<FutureTask<List<OrdersQueryResult>>> taskList = new ArrayList<FutureTask<List<OrdersQueryResult>>>();
		for (int i = 0; i < ReceiveConstant.TABLE_SIZE; i++) {
			OrdersQueryCallable queryRunnable =
					new OrdersQueryCallable(hospitalId, branchCode, tradeMode, startTime, endTime, hashTableName + "_" + ( i + 1 ),
							orderMode);
			// 创建每条指令的采集任务对象
			FutureTask<List<OrdersQueryResult>> collectTask = new FutureTask<List<OrdersQueryResult>>(queryRunnable);
			// 添加到list,方便后面取得结果
			taskList.add(collectTask);
			// 提交给线程池 exec.submit(task);
			collectExec.submit(collectTask);
		}
		// 阻塞主线程,等待采集所有子线程结束,获取所有子线程的执行结果,get方法阻塞主线程,再继续执行主线程逻辑
		try {
			for (FutureTask<List<OrdersQueryResult>> taskF : taskList) {
				// 防止某个子线程查询时间过长 超过默认时间没有拿到抛出异常
				List<OrdersQueryResult> collectData = taskF.get(Long.MAX_VALUE, TimeUnit.DAYS);
				if (collectData != null && logger.isInfoEnabled()) {
					results.addAll(collectData);
					if (logger.isDebugEnabled()) {
						logger.info("date:{} orders query success.", new Object[] { BizConstant.YYYYMMDDHHMMSS.format(new Date()) });
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 处理完毕,关闭线程池,这个不能在获取子线程结果之前关闭,因为如果线程多的话,执行中的可能被打断
		collectExec.shutdown();
		if (logger.isDebugEnabled()) {
			logger.debug("查询挂号订单:{}.", com.alibaba.fastjson.JSON.toJSONString(results));
		}
		return results;
	}
}
