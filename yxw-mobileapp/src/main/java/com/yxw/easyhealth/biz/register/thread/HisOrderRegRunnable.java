/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.register.RegRecord;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: UnLockRegSourceThread
 * @Statement: <p>解锁号源线程</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HisOrderRegRunnable implements Runnable {
	private static final String QUERY_EXCEPTION_MSG = "查询his订单接口异常,处理失败";
	private static final String NOTLOCK_EXCEPTION_MSG = "取消his订单(解锁号源)异常,处理失败";
	private RegisterRecord record;

	private Logger logger = LoggerFactory.getLogger(HisOrderRegRunnable.class);

	// private RegisterExceptionCache registerExceptionCache = SpringContextHolder.getBean(RegisterExceptionCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
	private RegisterBizManager regBizManager = SpringContextHolder.getBean(RegisterBizManager.class);

	public HisOrderRegRunnable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HisOrderRegRunnable(RegisterRecord record) {
		super();
		this.record = record;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub

		if (record != null) {
			String hospitalCode = record.getHospitalCode();
			String branchCode = record.getBranchHospitalCode();

			//his订单查询  是否生成订单
			RegRecord regRecord = null;
			Map<String, Object> interfaceMap = regBizManager.queryRegisterRecords(record);

			Boolean isException = (Boolean) interfaceMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			if (isException) {
				//查询异常  
				handleException(RegisterConstant.STATE_EXCEPTION_HAVING, QUERY_EXCEPTION_MSG);
			} else {
				Boolean isSuccess = (Boolean) interfaceMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
				if (isSuccess) {
					Object obj = interfaceMap.get(BizConstant.INTERFACE_MAP_DATA_KEY);
					if (obj != null) {
						@SuppressWarnings("unchecked")
						List<RegRecord> records = (List<RegRecord>) obj;
						if (!CollectionUtils.isEmpty(records)) {
							regRecord = records.get(0);
						}
					}
				} else {
					//查询失败 当作异常处理
					handleException(RegisterConstant.STATE_EXCEPTION_HAVING, QUERY_EXCEPTION_MSG);
				}
			}

			//对于his生成订单的挂号记录->解锁 
			if (regRecord != null) {
				Integer regType = record.getRegType();
				if (regType == null) {
					regType = RegisterConstant.REG_TYPE_APPOINTMENT;
				}

				String cancleMode = String.valueOf(ModeTypeUtil.getPlatformModeType(record.getAppCode()));
				String hisOrdNum = regRecord.getHisOrdNum();
				String psOrdNum = record.getOrderNo();
				if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
					interfaceMap = regBizManager.cancelCurRegister(hospitalCode, branchCode, cancleMode, hisOrdNum, psOrdNum);
				} else {
					interfaceMap = regBizManager.cancelRegister(hospitalCode, branchCode, cancleMode, hisOrdNum, psOrdNum);
				}

				isException = (Boolean) interfaceMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
				if (isException) {
					handleException(RegisterConstant.STATE_EXCEPTION_HAVING, NOTLOCK_EXCEPTION_MSG);
				} else {
					String resCode = interfaceMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(resCode)) {
						//解锁成功    无效记录不存数据库
					} else if (BizConstant.INTERFACE_RES_SUCCESS_HIS_INVALID.equalsIgnoreCase(resCode)) {
						//不符合医院限定,无法取消预约   根据挂号规则的在线控制规则  变更为预约中/已预约
						boolean cacheFlag = false;
						if (BizConstant.PAYMENT_TYPE_MUST == record.getOnlinePaymentType().intValue()) {
							record.setRegStatus(RegisterConstant.STATE_NORMAL_HAVING);
							cacheFlag = true;
						} else {
							record.setRegStatus(RegisterConstant.STATE_NORMAL_HAD);
						}
						record.setIsValid(RegisterConstant.RECORD_IS_VALID_TRUE);
						registerService.saveRecordInfo(record);
						if (cacheFlag) {
							// RegisterRecordCache recordCache = SpringContextHolder.getBean(RegisterRecordCache.class);
							// recordCache.setNotPayRegsToCache(record.convertSimpleObj());
							List<Object> params = new ArrayList<Object>();
							params.add(record.convertSimpleObj());
							serveComm.set(CacheType.REGISTER_RECORD_CACHE, "setNotPayRegsToCache", params);
						}
					} else if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(resCode)) {
						//请求失败  加入到解锁异常队列
						handleException(RegisterConstant.STATE_EXCEPTION_HAVING, NOTLOCK_EXCEPTION_MSG);
						logger.error(
								"RegisterConfirmService.DealWithRegSrceRunnable.run is error! put exception RegisterRecord into exception list. recordId:{} , orderNo :{}",
								new Object[] { record.getId(), record.getOrderNo() });
					}
				}

			}
		}
	}

	public void handleException(int regStatus, String exceptonMsg) {
		//-1 查询失败 加入到解锁异常队列
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setRegStatus(regStatus);
		record.setHandleCount(1 + ( record.getHandleCount() == null ? 0 : record.getHandleCount().intValue() ));
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:1");
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:" + exceptonMsg + ";");
		record.setHandleLog(sbLog.toString());
		record.setIsValid(RegisterConstant.RECORD_IS_VALID_FALSE);
		// registerExceptionCache.setExceptionRegisterToCache(record.convertExceptionObj());
		List<Object> params = new ArrayList<Object>();
		params.add(record.convertExceptionObj());
		serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
	}

	public RegisterRecord getRecord() {
		return record;
	}

	public void setRecord(RegisterRecord record) {
		this.record = record;
	}
}
