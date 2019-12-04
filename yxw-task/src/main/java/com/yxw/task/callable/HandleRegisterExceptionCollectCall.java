/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-24</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.platform.register.ExceptionRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.task.manager.BizExceptionHandleManager;
import com.yxw.task.taskitem.HandleRegisterExceptionTask;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HandleExceptionCollectCall
 * @Statement: <p>处理异常挂号</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HandleRegisterExceptionCollectCall implements Callable<ExceptionRecord> {
	/**
	 * 医院接口 对应的spring id
	 */
	private String interfaceId;

	/**
	 * 医院code
	 */
	//	private String hospitalCode;

	/**
	 * 分院code
	 */
	//	private String branchHospitalCode;

	/**
	 * 异常订单
	 */
	private ExceptionRecord record;

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	private BizExceptionHandleManager handler = SpringContextHolder.getBean(BizExceptionHandleManager.class);

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	public HandleRegisterExceptionCollectCall(String interfaceId, ExceptionRecord record) {
		super();
		this.record = record;
		this.interfaceId = interfaceId;
	}

	@Override
	public ExceptionRecord call() throws Exception {

		try {
			if (this.record == null) {
				HandleRegisterExceptionTask.logger.info("this hospital's code :{} , not find exception record.",
						this.record.getHospitalCode());
				return new ExceptionRecord();
			} else {
				HandleRegisterExceptionTask.logger.info(
						"this hospital's code :{} , find exception record.orderNo:{},regStatus:{},payStatus:{}",
						new Object[] { this.record.getHospitalCode(),
								this.record.getOrderNo(),
								this.record.getRegStatus(),
								this.record.getPayStatus() });
			}

			this.record = handler.handleRegisterException(this.record);
			//更新挂号缓存中挂号记录的信息
			HandleRegisterExceptionTask.logger.info(
					"HandleExceptionCollectCall handleRegisterException end .订单号:{} , 更新状态->regStatus:{} , payStatus:{}",
					new Object[] { this.record.getOrderNo(), this.record.getRegStatus(), this.record.getPayStatus() });
		} catch (Exception e) {
			record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
			record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
			HandleRegisterExceptionTask.logger.error("订单号:{} , exec HandleExceptionTask is execption. msg:{}",
					new Object[] { this.record.getOrderNo(), e.getMessage() });
		} finally {
			if (this.record.getIsHandleSuccess().intValue() == BizConstant.HANDLED_FAILURE) {
				//处理失败  添加回异常有序集合
				List<Object> params = new ArrayList<Object>();
				params.add(this.record);
				serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
			}
		}

		return record;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public ExceptionRecord getRecord() {
		return record;
	}

	public void setRecord(ExceptionRecord record) {
		this.record = record;
	}

}
