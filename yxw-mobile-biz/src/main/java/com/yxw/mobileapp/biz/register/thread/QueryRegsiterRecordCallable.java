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
package com.yxw.mobileapp.biz.register.thread;

import java.util.List;
import java.util.concurrent.Callable;

import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.register.dao.RegisterRecordDao;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: QueryRegsiterRecordRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-20
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class QueryRegsiterRecordCallable implements Callable<List<SimpleRecord>> {
	private RegisterRecordDao registerDao = SpringContextHolder.getBean(RegisterRecordDao.class);
	private String hashTableName;
	private Integer regStatus;
	private Integer payStatus;

	public QueryRegsiterRecordCallable(String hashTableName, Integer regStatus, Integer payStatus) {
		super();
		this.hashTableName = hashTableName;
		this.regStatus = regStatus;
		this.payStatus = payStatus;
	}

	public QueryRegsiterRecordCallable(String hashTableName, Integer payStatus) {
		this.hashTableName = hashTableName;
		this.payStatus = payStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public List<SimpleRecord> call() throws Exception {
		// TODO Auto-generated method stub
		if (regStatus == null) {
			return registerDao.findPayStatusRecord(hashTableName, payStatus);
		} else {
			return registerDao.findRegisterRecord(hashTableName, regStatus, payStatus);
		}
	}

}
