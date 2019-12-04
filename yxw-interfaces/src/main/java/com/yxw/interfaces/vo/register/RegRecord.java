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

package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->挂号记录查询信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class RegRecord extends Reserve implements Serializable {

	private static final long serialVersionUID = 7490651211864454245L;

	/**
	 * 预约挂号流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;

	/**
	 * 预约挂号退费流水号,要求唯一，能标识单独的一笔退费预约挂号订单
	 */
	private String hisRefOrdNum;

	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 科室名称
	 */
	private String deptName;
	/**
	 * 医生代码
	 */
	private String doctorCode;
	/**
	 * 医生名称
	 */
	private String doctorName;
	/**
	 * 就诊日期 ,格式：YYYY-MM-DD
	 */
	private String bookDate;
	/**
	 * 预约/挂号开始时间,格式：HH24:MI	
	 */
	private String beginTIme;
	/**
	 * 预约/挂号结束时间,格式：HH24:MI
	 */
	private String endTIme;
	/**
	 * 挂号类型,1:预约,2:当天
	 */
	private String regType;
	/**
	 * 支付方式,见PayPlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String payMode;

	/**
	 * 当前状态,见PaymentStatusType
	 * @see com.yxw.interfaces.constants.PaymentStatusType
	 */
	private String status;

	/**
	 * 优惠挂号费,单位：分,优惠费用
	 */
	private String realRegFee;

	/**
	 * 优惠诊疗费,单位：分,优惠费用
	 */
	private String realTreatFee;

	/**
	 * 优惠说明
	 */
	private String desc;

	/**
	 * 收据号
	 */
	private String receiptNum;
	/**
	 * 就诊序号
	 */
	private String serialNum;
	/**
	 * 就诊位置 ,提醒患者就诊的地方
	 */
	private String visitLocation;

	/**
	 * 取号时间,格式：YYYY-MM-DD HH:mm:ss,提醒患者在此时间前取号
	 */
	private String takeTime;
	/**
	 * 条形码
	 */
	private String barCode;
	/**
	 * 就诊说明
	 */
	private String visitDesc;
	
	/**
	 * 个帐金额
	 * */
	private String personalAccountFee;
	
	/**
	 * 统筹金额
	 * */
	private String overallPlanFee;
	
	/**
	 * 医疗机构编码【医院编号】
	 * */
	private String hospOrgCode;
	
	/**
	 * 操作员编码
	 * */
	private String operatorCode;
	
	/**
	 * 操作员姓名
	 * */
	private String operatorName;
	
	/**
	 * 挂号类别
	 * */
	private String regCategory;
	
	/**
	 * 门诊流水号
	 * */
	private String mzFeeId;

	/**
	 * 退款流水号
	 * */
	private String cancelSerialNo;

	/**
	 * 退款单据号
	 * */
	private String cancelBillNo;

	public RegRecord() {
		super();
	}

	/**
	 * @param hisOrdNum
	 * @param hisRefOrdNum
	 * @param deptCode
	 * @param deptName
	 * @param doctorCode
	 * @param doctorName
	 * @param bookDate
	 * @param beginTIme
	 * @param endTIme
	 * @param regType
	 * @param payMode
	 * @param status
	 * @param realRegFee
	 * @param realTreatFee
	 * @param desc
	 * @param receiptNum
	 * @param serialNum
	 * @param visitLocation
	 * @param takeTime
	 * @param barCode
	 * @param visitDesc
	 */
	public RegRecord(String hisOrdNum, String hisRefOrdNum, String deptCode, String deptName, String doctorCode, String doctorName, String bookDate,
			String beginTIme, String endTIme, String regType, String payMode, String status, String realRegFee, String realTreatFee, String desc,
			String receiptNum, String serialNum, String visitLocation, String takeTime, String barCode, String visitDesc) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.hisRefOrdNum = hisRefOrdNum;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.bookDate = bookDate;
		this.beginTIme = beginTIme;
		this.endTIme = endTIme;
		this.regType = regType;
		this.payMode = payMode;
		this.status = status;
		this.realRegFee = realRegFee;
		this.realTreatFee = realTreatFee;
		this.desc = desc;
		this.receiptNum = receiptNum;
		this.serialNum = serialNum;
		this.visitLocation = visitLocation;
		this.takeTime = takeTime;
		this.barCode = barCode;
		this.visitDesc = visitDesc;
	}

	/**
	 * @return the hisOrdNum
	 */
	public String getHisOrdNum() {
		return hisOrdNum;
	}

	/**
	 * @param hisOrdNum the hisOrdNum to set
	 */
	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	/**
	 * @return the hisRefOrdNum
	 */
	public String getHisRefOrdNum() {
		return hisRefOrdNum;
	}

	/**
	 * @param hisRefOrdNum the hisRefOrdNum to set
	 */
	public void setHisRefOrdNum(String hisRefOrdNum) {
		this.hisRefOrdNum = hisRefOrdNum;
	}

	/**
	 * @return the payMode
	 */
	public String getPayMode() {
		return payMode;
	}

	/**
	 * @param payMode the payMode to set
	 */
	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the realRegFee
	 */
	public String getRealRegFee() {
		return realRegFee;
	}

	/**
	 * @param realRegFee the realRegFee to set
	 */
	public void setRealRegFee(String realRegFee) {
		this.realRegFee = realRegFee;
	}

	/**
	 * @return the realTreatFee
	 */
	public String getRealTreatFee() {
		return realTreatFee;
	}

	/**
	 * @param realTreatFee the realTreatFee to set
	 */
	public void setRealTreatFee(String realTreatFee) {
		this.realTreatFee = realTreatFee;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the receiptNum
	 */
	public String getReceiptNum() {
		return receiptNum;
	}

	/**
	 * @param receiptNum the receiptNum to set
	 */
	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	/**
	 * @return the serialNum
	 */
	public String getSerialNum() {
		return serialNum;
	}

	/**
	 * @param serialNum the serialNum to set
	 */
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	/**
	 * @return the visitLocation
	 */
	public String getVisitLocation() {
		return visitLocation;
	}

	/**
	 * @param visitLocation the visitLocation to set
	 */
	public void setVisitLocation(String visitLocation) {
		this.visitLocation = visitLocation;
	}

	/**
	 * @return the takeTime
	 */
	public String getTakeTime() {
		return takeTime;
	}

	/**
	 * @param takeTime the takeTime to set
	 */
	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

	/**
	 * @return the barCode
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode the barCode to set
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return the visitDesc
	 */
	public String getVisitDesc() {
		return visitDesc;
	}

	/**
	 * @param visitDesc the visitDesc to set
	 */
	public void setVisitDesc(String visitDesc) {
		this.visitDesc = visitDesc;
	}

	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the deptName
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName the deptName to set
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * @return the doctorCode
	 */
	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode the doctorCode to set
	 */
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * @return the doctorName
	 */
	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName the doctorName to set
	 */
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the bookDate
	 */
	public String getBookDate() {
		return bookDate;
	}

	/**
	 * @param bookDate the bookDate to set
	 */
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	/**
	 * @return the beginTIme
	 */
	public String getBeginTIme() {
		return beginTIme;
	}

	/**
	 * @param beginTIme the beginTIme to set
	 */
	public void setBeginTIme(String beginTIme) {
		this.beginTIme = beginTIme;
	}

	/**
	 * @return the endTIme
	 */
	public String getEndTIme() {
		return endTIme;
	}

	/**
	 * @param endTIme the endTIme to set
	 */
	public void setEndTIme(String endTIme) {
		this.endTIme = endTIme;
	}

	/**
	 * @return the regType
	 */
	public String getRegType() {
		return regType;
	}

	/**
	 * @param regType the regType to set
	 */
	public void setRegType(String regType) {
		this.regType = regType;
	}
	
	public String getPersonalAccountFee() {
		return personalAccountFee;
	}

	public void setPersonalAccountFee(String personalAccountFee) {
		this.personalAccountFee = personalAccountFee;
	}

	public String getOverallPlanFee() {
		return overallPlanFee;
	}

	public void setOverallPlanFee(String overallPlanFee) {
		this.overallPlanFee = overallPlanFee;
	}

	public String getHospOrgCode() {
		return hospOrgCode;
	}

	public void setHospOrgCode(String hospOrgCode) {
		this.hospOrgCode = hospOrgCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getRegCategory() {
		return regCategory;
	}

	public void setRegCategory(String regCategory) {
		this.regCategory = regCategory;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
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

}
