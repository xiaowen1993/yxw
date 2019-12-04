/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-6</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.register.thread;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.outside.service.InterfaceService;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: StopRegRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-6
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegRunnable implements Runnable {
	private Logger logger = LoggerFactory.getLogger(StopRegRunnable.class);
	private List<RegisterRecord> records;
	private InterfaceService interfaceService = SpringContextHolder.getBean(InterfaceService.class);
	// private StopRegisterExceptionCache stopRegisterExceptionCache = SpringContextHolder.getBean(StopRegisterExceptionCache.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);

	public StopRegRunnable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public StopRegRunnable(List<RegisterRecord> records) {
		super();
		this.records = records;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		logger.info("停诊接口业务处理开始..........................");
		logger.info("records:{}", JSON.toJSONString(records));
		if (!CollectionUtils.isEmpty(records)) {
			for (RegisterRecord record : records) {
				logger.info("停诊处理订单：{}", JSON.toJSONString(record));
				if (record.getRegStatus() == RegisterConstant.STATE_NORMAL_HAD) {
					logger.info("消息推送开始......................");
					commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_STOP_VISIT);
					interfaceService.stopRegisterDealPay(record);
				} else {
					logger.info("非已预约状态，不参与停诊。订单号：{}, 状态：{} .", new Object[] { record.getOrderNo(), record.getRegStatus() });
					// stopRegisterExceptionCache.delStopRegOrdersByOrderNo(record.getOrderNo());
					ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
					List<Object> params = new ArrayList<Object>();
					params.add(record.getOrderNo());
					serveComm.delete(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "delStopRegOrdersByOrderNo", params);
				}
			}
		}
		logger.info("停诊接口业务处理结束..........................");
	}

	public List<RegisterRecord> getRecords() {
		return records;
	}

	public void setRecords(List<RegisterRecord> records) {
		this.records = records;
	}

}
