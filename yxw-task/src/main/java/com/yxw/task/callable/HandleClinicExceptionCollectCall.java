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
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.task.manager.BizClinicExceptionHandleManager;
import com.yxw.task.taskitem.HandleClinicExceptionTask;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HandleExceptionCollectCall
 * @Statement: <p>处理门诊异常</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HandleClinicExceptionCollectCall implements Callable<ClinicRecord> {
	private Logger logger = LoggerFactory.getLogger(HandleClinicExceptionCollectCall.class);

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

	private ClinicRecord record;

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	private BizClinicExceptionHandleManager handler = SpringContextHolder.getBean(BizClinicExceptionHandleManager.class);

	public HandleClinicExceptionCollectCall(String interfaceId, ClinicRecord record) {
		super();
		this.interfaceId = interfaceId;
		this.record = record;
	}

	@Override
	public ClinicRecord call() throws Exception {

		try {
			if (record == null) {
				HandleClinicExceptionTask.logger.info("this hospital's code :{} , not find exception record.", record.getHospitalCode());
				return null;
			} else {
				HandleClinicExceptionTask.logger.info("this hospital's code :{} , find exception record : {}",
						new Object[] { record.getHospitalCode(), JSON.toJSONString(record) });
			}

			record = handler.handleClinicException(record);

		} catch (Exception e) {
			logger.error("订单号:{} , exec HandleExceptionTask is execption. msg:{}", new Object[] { record.getOrderNo(), e.getMessage() });
		}

		return record;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public ClinicRecord getRecord() {
		return record;
	}

	public void setRecord(ClinicRecord record) {
		this.record = record;
	}

}
