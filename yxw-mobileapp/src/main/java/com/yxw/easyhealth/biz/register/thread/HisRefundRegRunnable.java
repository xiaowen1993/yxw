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
package com.yxw.easyhealth.biz.register.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.MessageConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.datas.manager.RegisterBizManager;

/**
 * @Package: com.yxw.mobileapp.biz.register.thread
 * @ClassName: HisRefundRegRunnable
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-6
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HisRefundRegRunnable implements Runnable {
	private static final String FAILURE_MSG = "不符合医院限定,无法退费";
	private RegisterBizManager regBizManager = SpringContextHolder.getBean(RegisterBizManager.class);
	private RegisterService registerSvc = SpringContextHolder.getBean(RegisterService.class);
	// private RegisterExceptionCache exceptionCahce = SpringContextHolder.getBean(RegisterExceptionCache.class);
	// private RegisterRecordCache recordCache = SpringContextHolder.getBean(RegisterRecordCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);
	private RegisterRecord record;

	public HisRefundRegRunnable() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HisRefundRegRunnable(RegisterRecord record) {
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
			Integer regType = record.getRegType();
			if (regType == null) {
				regType = RegisterConstant.REG_TYPE_APPOINTMENT;
			}
			RefundRegRequest requet = record.covertRefundRegRequestVo();
			Map<String, Object> interfaceResMap = null;
			if (regType.intValue() == RegisterConstant.REG_TYPE_CUR) {
				interfaceResMap = regBizManager.refundCurReg(record.getHospitalCode(), record.getBranchHospitalCode(), requet);
			} else {
				interfaceResMap = regBizManager.refundReg(record.getHospitalCode(), record.getBranchHospitalCode(), requet);
			}

			Boolean isException = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_EXCEPTION);
			if (isException) {
				handleHisException(record);
				commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_REFUND_EXCEPTION);
			} else {
				Boolean isSuccess = (Boolean) interfaceResMap.get(BizConstant.INTERFACE_EXEC_IS_SUCCESS);
				if (isSuccess) {
					if (record.getIsException() != null && record.getIsException().intValue() == BizConstant.IS_HAPPEN_EXCEPTION_YES) {
						record.addLogInfo("调用HIS退费接口退费成功,状态".concat(record.getStatusDescription(record.getRegStatus())).concat(
								"变更为[STATE_EXCEPTION_CANCEL=13]异常处理成功"));
						record.setRegStatus(RegisterConstant.STATE_EXCEPTION_CANCEL);
					} else {
						record.addLogInfo("调用HIS退费接口退费成功,状态".concat(record.getStatusDescription(record.getRegStatus())).concat(
								"变更为[STATE_NORMAL_USER_CANCEL=2]已取消-用户取消"));
						record.setRegStatus(RegisterConstant.STATE_NORMAL_USER_CANCEL);
					}
					record.setUpdateTime(System.currentTimeMillis());
					registerSvc.updateRecordStatus(record);

					List<Object> params = new ArrayList<Object>();
					params.add(record);
					serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);

					if (regType == RegisterConstant.REG_TYPE_CUR) {
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_REFUND_SUCCESS);
					} else {
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_REFUND_SUCCESS_APPOINT);
					}

				} else {
					String resCode = interfaceResMap.get(BizConstant.INTERFACE_MAP_CODE_KEY).toString();
					if (BizConstant.INTERFACE_RES_SUCCESS_INVALID_DATA_CODE.equalsIgnoreCase(resCode)) {
						record.setRegStatus(RegisterConstant.STATE_EXCEPTION_NEED_PERSON_HANDLE);
						String failureMsg = (String) interfaceResMap.get(BizConstant.INTERFACE_MAP_MSG_KEY);
						if (failureMsg == null) {
							failureMsg = FAILURE_MSG;
						}
						record.setFailureMsg(failureMsg);
						record.addLogInfo("调用HIS退费接口退费失败,原因-".concat(failureMsg));
						registerSvc.updateRecordStatus(record);

						List<Object> params = new ArrayList<Object>();
						params.add(record);
						serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);

						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_REFUND_FAIL);
					} else {
						handleHisException(record);
						commonMsgPushSvc.pushMsg(record, MessageConstant.ACTION_TYPE_REFUND_EXCEPTION);
					}
				}
			}
		}

	}

	/**
	 * 出现异常处理
	 * @param record
	 */
	public void handleHisException(RegisterRecord record) {
		record.setRegStatus(RegisterConstant.STATE_EXCEPTION_REFUND_HIS_COMMIT);
		record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
		record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
		record.setHandleCount(1);
		StringBuilder sbLog = new StringBuilder();
		sbLog.append("HandleCount:1");
		sbLog.append(",HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:调用HIS退费接口退费异常,状态变更为HIS退费异常[STATE_EXCEPTION_REFUND_HIS_COMMIT=11];");
		record.setHandleLog(sbLog.toString());
		registerSvc.updateExceptionRecord(record.convertExceptionObj());
		// recordCache.updateRecordTradeInfoToCache(record);
		List<Object> params = new ArrayList<Object>();
		params.add(record);
		serveComm.set(CacheType.REGISTER_RECORD_CACHE, "updateRecordTradeInfoToCache", params);
		// exceptionCahce.setExceptionRegisterToCache(record.convertExceptionObj());
		params.clear();
		params.add(record.convertExceptionObj());
		serveComm.set(CacheType.REGISTER_EXCEPTION_CACHE, "setExceptionRegisterToCache", params);
	}
}
