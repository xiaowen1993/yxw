/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-23</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform.register;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.interfaces.vo.register.appointment.RefundRegRequest;

/**
 * @Package: com.yxw.platform.register.vo
 * @ClassName: ExceptionRecord
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-23
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ExceptionRecord extends SimpleRecord {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2889046881274531449L;

	/**
	 * 异常处理时间间隔数组
	 * 时间间隔为[0s, 30s, 1min, 2min, 5min, 10min, 1h, 2h, 6h, 15h]
	 */
	protected static final int[] HANDLE_DELAYS = { 0, 30000, 60000, 120000, 300000, 600000, 3600000, 7200000, 21600000, 54000000 };

	/**
	 * 是否异常  1 有   0 没有
	 */
	private Integer isException;

	/**
	 * 是否处理成功 
	 */
	private Integer isHandleSuccess;

	/**
	 * 处理次数  大于3次的异常不再处理
	 */
	private Integer handleCount;

	/**
	 * 处理日志
	 */
	private String handleLog;

	/**
	 * 退款流水号
	 * */
	private String cancelSerialNo;

	/**
	 * 退款单据号
	 * */
	private String cancelBillNo;

	public ExceptionRecord(Integer isException, Integer isHandleSuccess, Integer handleCount, String handleLog) {
		super();
		this.isException = isException;
		this.isHandleSuccess = isHandleSuccess;
		this.handleCount = handleCount;
		this.handleLog = handleLog;
	}

	public RegisterRecord convertRecord() {
		RegisterRecord record = new RegisterRecord();
		BeanUtils.copyProperties(this, record);
		return record;
	}

	public ExceptionRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionRecord(String id, String openId, String hospitalCode, String branchHospitalCode, Integer regStatus,
			Integer isHandleSuccess, Integer handleCount, String handleLog, Integer regMode, String hisOrdNo, String orderNo,
			Long payTimeoutTime) {
		super(id, openId, hospitalCode, branchHospitalCode, regStatus, isHandleSuccess, handleCount, handleLog, regMode, hisOrdNo, orderNo,
				payTimeoutTime);
		// TODO Auto-generated constructor stub
	}

	public RefundRegRequest covertRefundRegRequestVo() {
		RefundRegRequest request = new RefundRegRequest();
		request.setBranchCode(this.branchHospitalCode);
		request.setHisOrdNum(this.hisOrdNo);
		request.setPsOrdNum(this.orderNo);
		request.setPsRefOrdNum(this.refundOrderNo);
		request.setRefundAmout(String.valueOf( ( realRegFee == null ? 0 : realRegFee ) + ( realTreatFee == null ? 0 : realTreatFee )));
		request.setRefundMode(String.valueOf(ModeTypeUtil.getPlatformModeType(this.appCode)));
		request.setRefundTime(BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		request.setAgtRefOrdNum(agtRefundOrdNum);
		return request;
	}

	public Integer getIsException() {
		return isException;
	}

	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	public Integer getIsHandleSuccess() {
		return isHandleSuccess;
	}

	public void setIsHandleSuccess(Integer isHandleSuccess) {
		this.isHandleSuccess = isHandleSuccess;
	}

	public Integer getHandleCount() {
		if (handleCount == null) {
			handleCount = 0;
		}
		return handleCount;
	}

	public void setHandleCount(Integer handleCount) {
		this.handleCount = handleCount;
	}

	public String getHandleLog() {
		return handleLog;
	}

	public void setHandleLog(String handleLog) {
		this.handleLog = handleLog;
	}

	public String getCancelSerialNo() {
		return cancelSerialNo;
	}

	public void setCancelSerialNo(String cancelSerialNo) {
		this.cancelSerialNo = cancelSerialNo;
	}

	public String getCancelBillNo() {
		return cancelBillNo;
	}

	public void setCancelBillNo(String cancelBillNo) {
		this.cancelBillNo = cancelBillNo;
	}

	public Integer getPayTotalFee() {
		// TODO Auto-generated method stub
		return getRealRegFee() + getRealTreatFee();
	}

	public Integer getRefundTotalFee() {
		// TODO Auto-generated method stub
		return getRealRegFee() + getRealTreatFee();
	}

	public void addLogInfo(String logMsg) {
		StringBuilder sbLog = new StringBuilder();
		if (handleCount != null) {
			sbLog.append("HandleCount:" + handleCount + ",");
		}
		sbLog.append("HandleDate:" + BizConstant.YYYYMMDDHHMMSS.format(new Date()));
		sbLog.append(",HandleMsg:" + logMsg);

		if (StringUtils.isNotBlank(handleLog)) {
			if (handleLog.endsWith(";")) {
				setHandleLog(handleLog + sbLog.toString());
			} else {
				setHandleLog(handleLog + ";" + sbLog.toString());
			}
		} else {
			setHandleLog(sbLog.toString());
		}
	}

	public Long getNextFireTime() {
		return this.updateTime
				+ HANDLE_DELAYS[this.handleCount == null ? 0 : ( this.handleCount >= HANDLE_DELAYS.length ? HANDLE_DELAYS.length - 1
						: this.handleCount )];
	}

	public static int[] getHandleDelays() {
		return HANDLE_DELAYS;
	}

	public static int getMaxHandleCount() {
		return HANDLE_DELAYS.length;
	}
}
