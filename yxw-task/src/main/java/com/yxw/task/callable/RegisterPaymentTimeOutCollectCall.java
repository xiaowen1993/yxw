package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.platform.register.SimpleRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.task.vo.RegisterTaskResult;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HospitalBaseInfoCollectCall
 * @Statement: <p>
 *             支付超时的挂号记录 -> 需解锁队列  轮询采集
 *             </p>
 * @JDK version used:
 * @Author: Yuce
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterPaymentTimeOutCollectCall implements Callable<RegisterTaskResult> {
	private static Logger logger = LoggerFactory.getLogger(RegisterPaymentTimeOutCollectCall.class);
	// private RegisterRecordCache registerRecordCache = SpringContextHolder.getBean(RegisterRecordCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
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

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public RegisterPaymentTimeOutCollectCall(String hospitalCode, String branchHospitalCode, String interfaceId) {
		super();
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
		this.interfaceId = interfaceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public RegisterTaskResult call() {
		RegisterTaskResult result = new RegisterTaskResult();
		result.setHospitalCode(hospitalCode);
		result.setBranchHospitalCode(branchHospitalCode);
		if (!StringUtils.isEmpty(hospitalCode)) {
			List<SimpleRecord> records = null;
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalCode);
			List<Object> results = serveComm.get(CacheType.REGISTER_RECORD_CACHE, "handleTimeOutRegister", params);
			if (!CollectionUtils.isEmpty(results)) {
				String source = JSON.toJSONString(results);
				records = JSON.parseArray(source, SimpleRecord.class);
			}

			if (!CollectionUtils.isEmpty(records)) {
				result.setRecords(records);
				logger.info("hospitalCode:{},payMentout records.size:{}", hospitalCode, records.size());
			} else {
				result.setRecords(new ArrayList<SimpleRecord>());
				logger.info("hospitalCode:{},payMentout records.size:{}", hospitalCode, 0);
			}
		}
		return result;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

}
