/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-20</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.task.callable;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.register.service.RegisterService;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: UpdateRegisterRecordRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterPaymentTimeOutRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RegisterPaymentTimeOutRunnable.class);

	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);

	private List<SimpleRecord> records;
	private String hashTableName;

	public RegisterPaymentTimeOutRunnable(List<SimpleRecord> records, String hashTableName) {
		super();
		this.records = records;
		this.hashTableName = hashTableName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<String> regPayTimeOutIds = new ArrayList<String>();
		for (SimpleRecord record : records) {
			regPayTimeOutIds.add(record.getId());
		}

		//List<String> recordIds = recordSvc.queryNotPayMentByIds(regPayTimeOutIds, hashTableName);

		if (!CollectionUtils.isEmpty(regPayTimeOutIds)) {
			//只更新未支付的挂号记录为超时取消  避免出现超时轮询中正好支付成功
			logger.info("regPayTimeOutIds size:{} , regPayTimeOutIds:{} , hashTableName", regPayTimeOutIds.size(), regPayTimeOutIds,
					hashTableName);
			registerService.updateRecordPayTimeOutStatus(regPayTimeOutIds, hashTableName);
			// RegisterRecordCache recordCache = SpringContextHolder.getBean(RegisterRecordCache.class);
			if (records.size() > 0) {
				ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
				// recordCache.updateRecordsStatusToCache(records, RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL);
				List<Object> params = new ArrayList<Object>();
				params.add(records);
				params.add(new Integer(RegisterConstant.STATE_NORMAL_PAY_TIMEOUT_CANCEL));
				serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordsStatusToCache", params);

				// recordCache.setNeedUnLockRegToCache(records);
				params.clear();
				params.add(records);
				serveComm.set(CacheType.REGISTER_RECORD_CACHE, "setNeedUnLockRegToCache", params);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("remove payment time out record's size:{}", records.size());
		}
	}

}
