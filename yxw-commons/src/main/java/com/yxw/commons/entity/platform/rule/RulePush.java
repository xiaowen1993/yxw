package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;
import com.yxw.base.entity.BaseEntity;

public class RulePush extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -837205562924384298L;

	/**
	 * 平台类型 wechat alipay（非数据库字段）
	 * */
	private String platformType;
	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 2017-7-26 16:44:24
	 * 推送模式
	 * 假设app下，有1，2，3 3种具体的推送方式
	 * 选中用,隔开
	 * === 0 为全部，这是个默认值。
	 */
	private String pushModes;

	@JSONField(serialize = false)
	private String[] modeArray;

	/**
	 * 绑卡成功是否推送(1-是 0-否)
	 * */
	private int bindCardSuc;
	/**
	 * 绑卡成功模版编码
	 * */
	private String bindCardSucCode;
	/**
	 * 建档成功是否推送(1-是 0-否)
	 * */
	private int createCardSuc;
	/**
	 * 建档成功模版编码
	 * */
	private String createCardSucCode;
	/**
	 * 就诊前一天是否推送(1-是 0-否)
	 * */
	private int predayVisit;
	/**
	 * 就诊前一天模版编码
	 * */
	private String predayVisitCode;
	/**
	 * 就诊当天是否推送(1-是 0-否)
	 * */
	private int curdayVisit;
	/**
	 * 就诊当天是否推送模版
	 * */
	private String curdayVisitCode;
	/**
	 * 锁号成功是否推送(1-是 0-否)
	 * */
	private int lockResSuc;
	/**
	 * 锁号成功模版编号
	 * */
	private String lockResSucCode;
	/**
	 * 当班挂号支付成功(1-是 0-否)
	 * */
	private int onDutyPaySuc;
	/**
	 * 当班挂号支付成功模版编号
	 * */
	private String onDutyPaySucCode;
	/**
	 * 预约支付成功是否推送(1-是 0-否)
	 * */
	private int appointPaySuc;
	/**
	 * 预约支付成功模版编号
	 * */
	private String appointPaySucCode;
	/**
	 * 挂号支付失败是否推送(1-是 0-否)
	 * */
	private int appointPayFail;
	/**
	 * 挂号支付失败模版编号
	 * */
	private String appointPayFailCode;
	/**
	 * 挂号支付异常是否推送(1-是 0-否)
	 * */
	private int appointPayExp;
	/**
	 * 挂号支付异常模版编号
	 * */
	private String appointPayExpCode;
	/**
	 * 取消当班挂号是否推送(1-是 0-否)
	 * */
	private int cancelOnDuty;
	/**
	 * 取消当班挂号模版编号
	 * */
	private String cancelOnDutyCode;
	/**
	 * 取消预约挂号是否推送(1-是 0-否)
	 * */
	private int cancelAppointment;
	/**
	 * 取消预约挂号模版编号
	 * */
	private String cancelAppointmentCode;
	/**
	 * 挂号退费成功是否推送(1-是 0-否)
	 * */
	private int refundSuccess;
	/**
	 * 挂号退费成功模版编号
	 * */
	private String refundSuccessCode;
	/**
	 * 挂号退费成功是否推送(1-是 0-否)【预约】
	 * */
	private int refundSuccessAppoint;
	/**
	 * 挂号退费成功模版编号【预约】
	 * */
	private String refundSuccessAppointCode;
	/**
	 * 挂号退费失败是否推送(1-是 0-否)
	 * */
	private int refundFail;
	/**
	 * 挂号退费失败模版编号
	 * */
	private String refundFailCode;
	/**
	 * 挂号退费异常是否推送(1-是 0-否)
	 * */
	private int refundException;
	/**
	 * 挂号退费异常模版编号
	 * */
	private String refundExceptionCode;
	/**
	 * 医生停诊是否推送(1-是 0-否)
	 * */
	private int stopVisit;
	/**
	 * 医生停诊模版编号
	 * */
	private String stopVisitCode;
	/**
	 * 医生替诊是否推送(1-是 0-否)
	 * */
	private int replaceRegVisit;
	/**
	 * 医生替诊模版编号
	 * */
	private String replaceRegVisitCode;
	/**
	 * 候诊排队是否推送(1-是 0-否)
	 * */
	private int waitVisit;
	/**
	 * 候诊排队模版编号
	 * */
	private String waitVisitCode;
	/**
	 * 门诊缴费成功是否推送(1-是 0-否)
	 * */
	private int clinicPaySuc;
	/**
	 * 门诊缴费成功模版编号
	 * */
	private String clinicPaySucCode;
	/**
	 * 门诊缴费失败是否推送(1-是 0-否)
	 * */
	private int clinicPayFail;
	/**
	 * 门诊缴费失败模版编号
	 * */
	private String clinicPayFailCode;
	/**
	 * 门诊缴费异常是否推送(1-是 0-否)
	 * */
	private int clinicPayExp;
	/**
	 * 门诊缴费异常模版编号
	 * */
	private String clinicPayExpCode;
	/**
	 * 押金补缴成功是否推送(1-是 0-否)
	 * */
	private int payDepositSuc;
	/**
	 * 押金补缴成功模版编号
	 * */
	private String payDepositSucCode;
	/**
	 * 押金补缴失败是否推送(1-是 0-否)
	 * */
	private int payDepositFail;
	/**
	 * 押金补缴失败模版编号
	 * */
	private String payDepositFailCode;
	/**
	 * 押金补缴异常是否推送(1-是 0-否)
	 * */
	private int payDepositExp;
	/**
	 * 押金补缴异常模版编号
	 * */
	private String payDepositExpCode;
	/**
	 * 报告出结果是否推送(1-是 0-否)
	 * */
	private int generateReport;
	/**
	 * 报告出结果模版编号
	 * */
	private String generateReportCode;

	/**
	 * 门诊退费成功(1-是 0-否)
	 */
	private int clinicRefundSuc;

	/**
	 * 门诊退费成功代码
	 */
	private String clinicRefundSucCode;

	/**
	 * 门诊部分退费成功(1-是 0-否)
	 */
	private int clinicPartRefundSuc;

	/**
	 * 门诊部分退费成功代码
	 */
	private String clinicPartRefundSucCode;
	
	/**
	 * 门诊缴费成功就诊评价消息(1-是 0-否)
	 */
	private int clinicPaySuccessComment;
	
	/**
	 * 门诊缴费成功就诊评价消息代码
	 */
	private String clinicPaySucCommentCode;
	
	/**
	 * 门诊缴费成功后是否推送送花(1-是  0-否)
	 */
	private int clinicPaySuccessFlower;
	
	/**
	 * 门诊缴费成功后是否推送送花code
	 */
	private String clinicPaySuccessFlowerCode;
	
	/**
	 * 送花推送的门诊支付时间类型
	 * 
	 * */
	private int presentFlowerDayFlag;
	
	/**
	 * 送花推送门诊支付开始时间
	 * */
	@DateTimeFormat(pattern = "HH:mm")
	private Date presentFlowerBeginTime;
	
	/**
	 * 送花推送门诊支付结束时间
	 * */
	@DateTimeFormat(pattern = "HH:mm")
	private Date presentFlowerEndTime;
	
	/**
	 * 住院全额退费成功后是否推送(1-是  0-否)
	 */
	private int depositRefundAllSuccess;
	
	/**
	 * 住院全额退费成功后是否推送code
	 */
	private String depositRefundAllSuccessCode;
	
	/**
	 * 住院部分退费成功后是否推送(1-是  0-否)
	 */
	private int depositRefundPartSuccess;
	
	/**
	 * 住院部分退费成功后是否推送code
	 */
	private String depositRefundPartSuccessCode;
	
	
	/**
	 * 取号成功是否推送(1-是  0-否)
	 */
	private int takeNoSuccess;
	
	/**
	 * 取号成功是否推送code
	 */
	private String takeNoSuccessCode;

	/**
	 * 是否发送评价
	 * */
	private int sendComment;

	/**
	 * 是否发送评价代码
	 * */
	private String sendCommentCode;

	/**
	 * 注册后是否发送完善资料消息
	 * */
	private int finishUserInfo;

	/**
	 * 注册后是否发送完善资料消息代码
	 * */
	private String finishUserInfoCode;

	public int getSendComment() {
		return sendComment;
	}

	public void setSendComment(int sendComment) {
		this.sendComment = sendComment;
	}

	public String getSendCommentCode() {
		return sendCommentCode;
	}

	public void setSendCommentCode(String sendCommentCode) {
		this.sendCommentCode = sendCommentCode;
	}

	public int getClinicRefundSuc() {
		return clinicRefundSuc;
	}

	public void setClinicRefundSuc(int clinicRefundSuc) {
		this.clinicRefundSuc = clinicRefundSuc;
	}

	public String getClinicRefundSucCode() {
		return clinicRefundSucCode;
	}

	public void setClinicRefundSucCode(String clinicRefundSucCode) {
		this.clinicRefundSucCode = clinicRefundSucCode;
	}

	public int getClinicPartRefundSuc() {
		return clinicPartRefundSuc;
	}

	public void setClinicPartRefundSuc(int clinicPartRefundSuc) {
		this.clinicPartRefundSuc = clinicPartRefundSuc;
	}

	public String getClinicPartRefundSucCode() {
		return clinicPartRefundSucCode;
	}

	public void setClinicPartRefundSucCode(String clinicPartRefundSucCode) {
		this.clinicPartRefundSucCode = clinicPartRefundSucCode;
	}
	
	public int getClinicPaySuccessComment() {
		return clinicPaySuccessComment;
	}

	public void setClinicPaySuccessComment(int clinicPaySuccessComment) {
		this.clinicPaySuccessComment = clinicPaySuccessComment;
	}

	public String getClinicPaySucCommentCode() {
		return clinicPaySucCommentCode;
	}

	public void setClinicPaySucCommentCode(String clinicPaySucCommentCode) {
		this.clinicPaySucCommentCode = clinicPaySucCommentCode;
	}
	
	public int getClinicPaySuccessFlower() {
		return clinicPaySuccessFlower;
	}

	public void setClinicPaySuccessFlower(int clinicPaySuccessFlower) {
		this.clinicPaySuccessFlower = clinicPaySuccessFlower;
	}

	public String getClinicPaySuccessFlowerCode() {
		return clinicPaySuccessFlowerCode;
	}

	public void setClinicPaySuccessFlowerCode(String clinicPaySuccessFlowerCode) {
		this.clinicPaySuccessFlowerCode = clinicPaySuccessFlowerCode;
	}
	
	public int getPresentFlowerDayFlag() {
		return presentFlowerDayFlag;
	}

	public void setPresentFlowerDayFlag(int presentFlowerDayFlag) {
		this.presentFlowerDayFlag = presentFlowerDayFlag;
	}

	public Date getPresentFlowerBeginTime() {
		return presentFlowerBeginTime;
	}

	public void setPresentFlowerBeginTime(Date presentFlowerBeginTime) {
		this.presentFlowerBeginTime = presentFlowerBeginTime;
	}

	public Date getPresentFlowerEndTime() {
		return presentFlowerEndTime;
	}

	public void setPresentFlowerEndTime(Date presentFlowerEndTime) {
		this.presentFlowerEndTime = presentFlowerEndTime;
	}
	
	public int getDepositRefundAllSuccess() {
		return depositRefundAllSuccess;
	}

	public void setDepositRefundAllSuccess(int depositRefundAllSuccess) {
		this.depositRefundAllSuccess = depositRefundAllSuccess;
	}

	public String getDepositRefundAllSuccessCode() {
		return depositRefundAllSuccessCode;
	}

	public void setDepositRefundAllSuccessCode(String depositRefundAllSuccessCode) {
		this.depositRefundAllSuccessCode = depositRefundAllSuccessCode;
	}

	public int getDepositRefundPartSuccess() {
		return depositRefundPartSuccess;
	}

	public void setDepositRefundPartSuccess(int depositRefundPartSuccess) {
		this.depositRefundPartSuccess = depositRefundPartSuccess;
	}

	public String getDepositRefundPartSuccessCode() {
		return depositRefundPartSuccessCode;
	}

	public void setDepositRefundPartSuccessCode(String depositRefundPartSuccessCode) {
		this.depositRefundPartSuccessCode = depositRefundPartSuccessCode;
	}

	public int getTakeNoSuccess() {
		return takeNoSuccess;
	}

	public void setTakeNoSuccess(int takeNoSuccess) {
		this.takeNoSuccess = takeNoSuccess;
	}

	public String getTakeNoSuccessCode() {
		return takeNoSuccessCode;
	}

	public void setTakeNoSuccessCode(String takeNoSuccessCode) {
		this.takeNoSuccessCode = takeNoSuccessCode;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public int getBindCardSuc() {
		return bindCardSuc;
	}

	public void setBindCardSuc(int bindCardSuc) {
		this.bindCardSuc = bindCardSuc;
	}

	public String getBindCardSucCode() {
		return bindCardSucCode;
	}

	public void setBindCardSucCode(String bindCardSucCode) {
		this.bindCardSucCode = bindCardSucCode;
	}

	public int getCreateCardSuc() {
		return createCardSuc;
	}

	public void setCreateCardSuc(int createCardSuc) {
		this.createCardSuc = createCardSuc;
	}

	public String getCreateCardSucCode() {
		return createCardSucCode;
	}

	public void setCreateCardSucCode(String createCardSucCode) {
		this.createCardSucCode = createCardSucCode;
	}

	public int getPredayVisit() {
		return predayVisit;
	}

	public void setPredayVisit(int predayVisit) {
		this.predayVisit = predayVisit;
	}

	public String getPredayVisitCode() {
		return predayVisitCode;
	}

	public void setPredayVisitCode(String predayVisitCode) {
		this.predayVisitCode = predayVisitCode;
	}

	public int getCurdayVisit() {
		return curdayVisit;
	}

	public void setCurdayVisit(int curdayVisit) {
		this.curdayVisit = curdayVisit;
	}

	public String getCurdayVisitCode() {
		return curdayVisitCode;
	}

	public void setCurdayVisitCode(String curdayVisitCode) {
		this.curdayVisitCode = curdayVisitCode;
	}

	public int getLockResSuc() {
		return lockResSuc;
	}

	public void setLockResSuc(int lockResSuc) {
		this.lockResSuc = lockResSuc;
	}

	public String getLockResSucCode() {
		return lockResSucCode;
	}

	public void setLockResSucCode(String lockResSucCode) {
		this.lockResSucCode = lockResSucCode;
	}

	public int getOnDutyPaySuc() {
		return onDutyPaySuc;
	}

	public void setOnDutyPaySuc(int onDutyPaySuc) {
		this.onDutyPaySuc = onDutyPaySuc;
	}

	public String getOnDutyPaySucCode() {
		return onDutyPaySucCode;
	}

	public void setOnDutyPaySucCode(String onDutyPaySucCode) {
		this.onDutyPaySucCode = onDutyPaySucCode;
	}

	public int getAppointPaySuc() {
		return appointPaySuc;
	}

	public void setAppointPaySuc(int appointPaySuc) {
		this.appointPaySuc = appointPaySuc;
	}

	public String getAppointPaySucCode() {
		return appointPaySucCode;
	}

	public void setAppointPaySucCode(String appointPaySucCode) {
		this.appointPaySucCode = appointPaySucCode;
	}

	public int getAppointPayFail() {
		return appointPayFail;
	}

	public void setAppointPayFail(int appointPayFail) {
		this.appointPayFail = appointPayFail;
	}

	public String getAppointPayFailCode() {
		return appointPayFailCode;
	}

	public void setAppointPayFailCode(String appointPayFailCode) {
		this.appointPayFailCode = appointPayFailCode;
	}

	public int getAppointPayExp() {
		return appointPayExp;
	}

	public void setAppointPayExp(int appointPayExp) {
		this.appointPayExp = appointPayExp;
	}

	public String getAppointPayExpCode() {
		return appointPayExpCode;
	}

	public void setAppointPayExpCode(String appointPayExpCode) {
		this.appointPayExpCode = appointPayExpCode;
	}

	public int getCancelOnDuty() {
		return cancelOnDuty;
	}

	public void setCancelOnDuty(int cancelOnDuty) {
		this.cancelOnDuty = cancelOnDuty;
	}

	public String getCancelOnDutyCode() {
		return cancelOnDutyCode;
	}

	public void setCancelOnDutyCode(String cancelOnDutyCode) {
		this.cancelOnDutyCode = cancelOnDutyCode;
	}

	public int getCancelAppointment() {
		return cancelAppointment;
	}

	public void setCancelAppointment(int cancelAppointment) {
		this.cancelAppointment = cancelAppointment;
	}

	public String getCancelAppointmentCode() {
		return cancelAppointmentCode;
	}

	public void setCancelAppointmentCode(String cancelAppointmentCode) {
		this.cancelAppointmentCode = cancelAppointmentCode;
	}

	public int getRefundSuccess() {
		return refundSuccess;
	}

	public void setRefundSuccess(int refundSuccess) {
		this.refundSuccess = refundSuccess;
	}

	public String getRefundSuccessCode() {
		return refundSuccessCode;
	}

	public void setRefundSuccessCode(String refundSuccessCode) {
		this.refundSuccessCode = refundSuccessCode;
	}

	public int getRefundSuccessAppoint() {
		return refundSuccessAppoint;
	}

	public void setRefundSuccessAppoint(int refundSuccessAppoint) {
		this.refundSuccessAppoint = refundSuccessAppoint;
	}

	public String getRefundSuccessAppointCode() {
		return refundSuccessAppointCode;
	}

	public void setRefundSuccessAppointCode(String refundSuccessAppointCode) {
		this.refundSuccessAppointCode = refundSuccessAppointCode;
	}

	public int getRefundFail() {
		return refundFail;
	}

	public void setRefundFail(int refundFail) {
		this.refundFail = refundFail;
	}

	public String getRefundFailCode() {
		return refundFailCode;
	}

	public void setRefundFailCode(String refundFailCode) {
		this.refundFailCode = refundFailCode;
	}

	public int getRefundException() {
		return refundException;
	}

	public void setRefundException(int refundException) {
		this.refundException = refundException;
	}

	public String getRefundExceptionCode() {
		return refundExceptionCode;
	}

	public void setRefundExceptionCode(String refundExceptionCode) {
		this.refundExceptionCode = refundExceptionCode;
	}

	public int getStopVisit() {
		return stopVisit;
	}

	public void setStopVisit(int stopVisit) {
		this.stopVisit = stopVisit;
	}

	public String getStopVisitCode() {
		return stopVisitCode;
	}

	public void setStopVisitCode(String stopVisitCode) {
		this.stopVisitCode = stopVisitCode;
	}
	
	public int getReplaceRegVisit() {
		return replaceRegVisit;
	}

	public void setReplaceRegVisit(int replaceRegVisit) {
		this.replaceRegVisit = replaceRegVisit;
	}

	public String getReplaceRegVisitCode() {
		return replaceRegVisitCode;
	}

	public void setReplaceRegVisitCode(String replaceRegVisitCode) {
		this.replaceRegVisitCode = replaceRegVisitCode;
	}

	public int getWaitVisit() {
		return waitVisit;
	}

	public void setWaitVisit(int waitVisit) {
		this.waitVisit = waitVisit;
	}

	public String getWaitVisitCode() {
		return waitVisitCode;
	}

	public void setWaitVisitCode(String waitVisitCode) {
		this.waitVisitCode = waitVisitCode;
	}

	public int getClinicPaySuc() {
		return clinicPaySuc;
	}

	public void setClinicPaySuc(int clinicPaySuc) {
		this.clinicPaySuc = clinicPaySuc;
	}

	public String getClinicPaySucCode() {
		return clinicPaySucCode;
	}

	public void setClinicPaySucCode(String clinicPaySucCode) {
		this.clinicPaySucCode = clinicPaySucCode;
	}

	public int getClinicPayFail() {
		return clinicPayFail;
	}

	public void setClinicPayFail(int clinicPayFail) {
		this.clinicPayFail = clinicPayFail;
	}

	public String getClinicPayFailCode() {
		return clinicPayFailCode;
	}

	public void setClinicPayFailCode(String clinicPayFailCode) {
		this.clinicPayFailCode = clinicPayFailCode;
	}

	public int getClinicPayExp() {
		return clinicPayExp;
	}

	public void setClinicPayExp(int clinicPayExp) {
		this.clinicPayExp = clinicPayExp;
	}

	public String getClinicPayExpCode() {
		return clinicPayExpCode;
	}

	public void setClinicPayExpCode(String clinicPayExpCode) {
		this.clinicPayExpCode = clinicPayExpCode;
	}

	public int getPayDepositSuc() {
		return payDepositSuc;
	}

	public void setPayDepositSuc(int payDepositSuc) {
		this.payDepositSuc = payDepositSuc;
	}

	public String getPayDepositSucCode() {
		return payDepositSucCode;
	}

	public void setPayDepositSucCode(String payDepositSucCode) {
		this.payDepositSucCode = payDepositSucCode;
	}

	public int getPayDepositFail() {
		return payDepositFail;
	}

	public void setPayDepositFail(int payDepositFail) {
		this.payDepositFail = payDepositFail;
	}

	public String getPayDepositFailCode() {
		return payDepositFailCode;
	}

	public void setPayDepositFailCode(String payDepositFailCode) {
		this.payDepositFailCode = payDepositFailCode;
	}

	public int getPayDepositExp() {
		return payDepositExp;
	}

	public void setPayDepositExp(int payDepositExp) {
		this.payDepositExp = payDepositExp;
	}

	public String getPayDepositExpCode() {
		return payDepositExpCode;
	}

	public void setPayDepositExpCode(String payDepositExpCode) {
		this.payDepositExpCode = payDepositExpCode;
	}

	public int getGenerateReport() {
		return generateReport;
	}

	public void setGenerateReport(int generateReport) {
		this.generateReport = generateReport;
	}

	public String getGenerateReportCode() {
		return generateReportCode;
	}

	public void setGenerateReportCode(String generateReportCode) {
		this.generateReportCode = generateReportCode;
	}

	public String getPlatformType() {
		return platformType;
	}

	public void setPlatformType(String platformType) {
		this.platformType = platformType;
	}

	public RulePush(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public int getFinishUserInfo() {
		return finishUserInfo;
	}

	public void setFinishUserInfo(int finishUserInfo) {
		this.finishUserInfo = finishUserInfo;
	}

	public String getFinishUserInfoCode() {
		return finishUserInfoCode;
	}

	public void setFinishUserInfoCode(String finishUserInfoCode) {
		this.finishUserInfoCode = finishUserInfoCode;
	}

	public RulePush() {
	}

	/**
	 * 得到默认的规则
	 * 
	 * @param hospitalId
	 * @return
	 */
	public static RulePush getDefaultRule(String hospitalId) {
		RulePush rulePush = new RulePush(hospitalId);
		rulePush.setPushModes("0");
		rulePush.setAppointPayExp(0);
		rulePush.setAppointPayFail(0);
		rulePush.setAppointPaySuc(0);
		rulePush.setBindCardSuc(0);
		rulePush.setCancelAppointment(0);
		rulePush.setCancelOnDuty(0);
		rulePush.setClinicPayExp(0);
		rulePush.setClinicPayFail(0);
		rulePush.setClinicPaySuc(0);
		rulePush.setCreateCardSuc(0);
		rulePush.setCurdayVisit(0);
		rulePush.setGenerateReport(0);
		rulePush.setWaitVisit(0);
		rulePush.setStopVisit(0);
		rulePush.setReplaceRegVisit(0);
		rulePush.setRefundSuccess(0);
		rulePush.setRefundSuccessAppoint(0);
		rulePush.setRefundFail(0);
		rulePush.setRefundException(0);
		rulePush.setPredayVisit(0);
		rulePush.setPayDepositSuc(0);
		rulePush.setPayDepositFail(0);
		rulePush.setOnDutyPaySuc(0);
		rulePush.setLockResSuc(0);
		rulePush.setClinicPartRefundSuc(0);
		rulePush.setClinicRefundSuc(0);
		rulePush.setClinicPaySuccessComment(0);
		rulePush.setClinicPaySuccessFlower(0);
		rulePush.setSendComment(0);
		rulePush.setFinishUserInfo(0);
		rulePush.setDepositRefundAllSuccess(0);
		rulePush.setDepositRefundPartSuccess(0);
		rulePush.setTakeNoSuccess(0);
		return rulePush;
	}

	public String getPushModes() {
		return pushModes;
	}

	public void setPushModes(String pushModes) {
		this.pushModes = pushModes;
	}

	public String[] getModeArray() {
		if (StringUtils.isNoneBlank(pushModes) && !pushModes.equals("0")) {
			modeArray = StringUtils.split(pushModes, ",");
		}
		return modeArray;
	}

	public void setModeArray(String[] modeArray) {
		this.modeArray = modeArray;
	}
}