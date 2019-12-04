/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.appointment;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->预约挂号-->预约挂号请求参数封装
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class OrderRegRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 5756101691995443523L;
	/**
	 * 公众服务平台订单号,公众服务平台（微信公众号、支付宝服务窗）用于唯一标识一笔交易的流水号
	 */
	private String psOrdNum;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 医生/专科代码
	 */
	private String doctorCode;
	/**
	 * 号源日期,格式：YYYY-MM-DD
	 */
	private String scheduleDate;
	/**
	 * 时段,见TimeType
	 * @see com.yxw.interfaces.constants.TimeType
	 */
	private String timeFlag;
	/**
	 * 分时开始时间,格式：HH24:MI
	 */
	private String beginTime;
	/**
	 * 分时结束时间,格式：HH24:MI
	 */
	private String endTime;
	/**
	 * 排班ID,用排班ID时，不需要科室代码、医生/专科代码及时段
	 */
	private String workId;
	/**
	 * 挂号费,单位：分
	 */
	private String regFee;
	/**
	 * 诊疗费,单位：分
	 */
	private String treatFee;

	/**
	 * 挂号类型,见RegType
	 * @see com.yxw.interfaces.constants.RegType
	 */
	private String regType;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 患者诊疗卡号码
	 */
	private String patCardNo;
	/**
	 * 预约方式,1：微信公众号,2：支付宝服务窗
	 */
	private String orderMode;
	/**
	 * 下单时间,格式：YYYY-MM-DD HH:mm:ss
	 */
	private String orderTime;

	public OrderRegRequest() {
		super();
	}

	/**
	 * @param psOrdNum
	 * @param branchCode
	 * @param deptCode
	 * @param doctorCode
	 * @param scheduleDate
	 * @param timeFlag
	 * @param beginTime
	 * @param endTime
	 * @param workId
	 * @param regFee
	 * @param treatFee
	 * @param regType
	 * @param patCardType
	 * @param patCardNo
	 * @param orderMode
	 * @param orderTime
	 */

	public OrderRegRequest(String psOrdNum, String branchCode, String deptCode, String doctorCode, String scheduleDate, String timeFlag,
			String beginTime, String endTime, String workId, String regFee, String treatFee, String regType, String patCardType, String patCardNo,
			String orderMode, String orderTime) {
		super();
		this.psOrdNum = psOrdNum;
		this.branchCode = branchCode;
		this.deptCode = deptCode;
		this.doctorCode = doctorCode;
		this.scheduleDate = scheduleDate;
		this.timeFlag = timeFlag;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.workId = workId;
		this.regFee = regFee;
		this.treatFee = treatFee;
		this.regType = regType;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.orderMode = orderMode;
		this.orderTime = orderTime;
	}

	/**
	 * @return the psOrdNum
	 */

	public String getPsOrdNum() {
		return psOrdNum;
	}

	/**
	 * @param psOrdNum
	 *            the psOrdNum to set
	 */

	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

	/**
	 * @return the branchCode
	 */

	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the deptCode
	 */

	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the doctorCode
	 */

	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode
	 *            the doctorCode to set
	 */

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * @return the scheduleDate
	 */

	public String getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate
	 *            the scheduleDate to set
	 */

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * @return the timeFlag
	 */

	public String getTimeFlag() {
		return timeFlag;
	}

	/**
	 * @param timeFlag
	 *            the timeFlag to set
	 */

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

	/**
	 * @return the beginTime
	 */

	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime
	 *            the beginTime to set
	 */

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */

	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the workId
	 */

	public String getWorkId() {
		return workId;
	}

	/**
	 * @param workId
	 *            the workId to set
	 */

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	/**
	 * @return the regFee
	 */

	public String getRegFee() {
		return regFee;
	}

	/**
	 * @param regFee
	 *            the regFee to set
	 */

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	/**
	 * @return the treatFee
	 */

	public String getTreatFee() {
		return treatFee;
	}

	/**
	 * @param treatFee
	 *            the treatFee to set
	 */

	public void setTreatFee(String treatFee) {
		this.treatFee = treatFee;
	}

	/**
	 * @return the regType
	 */

	public String getRegType() {
		return regType;
	}

	/**
	 * @param regType
	 *            the regType to set
	 */

	public void setRegType(String regType) {
		this.regType = regType;
	}

	/**
	 * @return the patCardType
	 */

	public String getPatCardType() {
		return patCardType;
	}

	/**
	 * @param patCardType
	 *            the patCardType to set
	 */

	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	/**
	 * @return the patCardNo
	 */

	public String getPatCardNo() {
		return patCardNo;
	}

	/**
	 * @param patCardNo
	 *            the patCardNo to set
	 */

	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	/**
	 * @return the orderMode
	 */

	public String getOrderMode() {
		return orderMode;
	}

	/**
	 * @param orderMode
	 *            the orderMode to set
	 */

	public void setOrderMode(String orderMode) {
		this.orderMode = orderMode;
	}

	/**
	 * @return the orderTime
	 */

	public String getOrderTime() {
		return orderTime;
	}

	/**
	 * @param orderTime
	 *            the orderTime to set
	 */

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

}
