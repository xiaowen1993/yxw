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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.task.manager.VisitWarnHandleManager;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HandleDepositExceptionCollectCall
 * @Statement: <p>处理押金补缴异常</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-24
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class CurDayVisitWarnCollectCall implements Callable<RegisterRecord> {

	private VisitWarnHandleManager handler = SpringContextHolder.getBean(VisitWarnHandleManager.class);

	private Logger logger = LoggerFactory.getLogger(CurDayVisitWarnCollectCall.class);

	/**	
	 * 医院code
	 */
	private String hospitalCode;

	private List<RegisterRecord> records = new ArrayList<RegisterRecord>();

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public CurDayVisitWarnCollectCall(String hospitalCode, List<RegisterRecord> records) {
		super();
		this.hospitalCode = hospitalCode;
		this.records = records;
	}

	@Override
	public RegisterRecord call() throws Exception {
		if (!CollectionUtils.isEmpty(records)) {
			logger.info("hospitalCode: {}, has {} today records to send...", new Object[] { hospitalCode, records.size() });
			handler.handleCurDateVisitWarn(hospitalCode, records);
			logger.info("hospitalCode: {}, today record warn done...", hospitalCode);
		} else {
			logger.info("hospitalCode: {}, has no records today...", hospitalCode);
		}
		return null;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public List<RegisterRecord> getRecords() {
		return records;
	}

	public void setRecordsMap(List<RegisterRecord> records) {
		this.records = records;
	}

}
