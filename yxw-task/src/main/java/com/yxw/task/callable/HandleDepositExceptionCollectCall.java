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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.inpatient.DepositRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

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
public class HandleDepositExceptionCollectCall implements Callable<DepositRecord> {
	/**
	 * 医院接口 对应的spring id
	 */
	private String interfaceId;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	private Logger logger = LoggerFactory.getLogger(HandleDepositExceptionCollectCall.class);

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	//	private DepositExceptionCache exceptionCache = SpringContextHolder.getBean(DepositExceptionCache.class);

	//	private BizDepositExceptionHandleManager handler = SpringContextHolder.getBean(BizDepositExceptionHandleManager.class);

	public HandleDepositExceptionCollectCall(String hospitalCode, String branchHospitalCode, String interfaceId) {
		super();
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.interfaceId = interfaceId;
	}

	@Override
	public DepositRecord call() throws Exception {
		// TODO Auto-generated method stub
		DepositRecord record = null;
		try {
			// getExceptionRecordFromCache
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalCode);
			List<Object> results = serveComm.get(CacheType.DEPOSIT_EXCEPTION_CACHE, "getExceptionRecordFromCache", params);
			if (CollectionUtils.isEmpty(results)) {
				logger.info("this hospital's code :{} , not find exception record.", hospitalCode);
				return null;
			} else {
				record = (DepositRecord) results;
				logger.info("this hospital's code :{} , find exception record : {}", new Object[] { hospitalCode, JSON.toJSONString(record) });
			}

			// 暂不实现。
			//				record = handler.handleDepositException(record);

			// 移除异常队列
			// removeExceptionRecordFromCache
			serveComm.delete(CacheType.DEPOSIT_EXCEPTION_CACHE, "removeExceptionRecordFromCache", params);

			if (record.getIsHandleSuccess() != null && record.getIsHandleSuccess().intValue() == BizConstant.HANDLED_FAILURE) {
				//处理失败  添加到队列尾部  下次处理
				params.clear();
				params.add(record);
				// removeExceptionRecordFromCache
				serveComm.set(CacheType.DEPOSIT_EXCEPTION_CACHE, "setExceptionRecordToCache", params);
			}
		} catch (Exception e) {
			logger.error("订单号:{} , exec HandleExceptionTask is execption. msg:{}", new Object[] { record.getOrderNo(), e.getMessage() });
		}
		return null;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

}
