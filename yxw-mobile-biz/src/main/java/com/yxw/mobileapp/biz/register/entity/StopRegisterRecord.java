package com.yxw.mobileapp.biz.register.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.yxw.base.entity.BaseEntity;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.register.entity
 * @ClassName: StopRegisterRecord
 * @Statement: <p>停诊挂号记录信息</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年7月13日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegisterRecord extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7511071079107088231L;

	public static final byte HIS_REFUNDED = 1;
	public static final byte REFUNDED = 1 << 1;
	public static final byte ACKED_REFUND = 1 << 2;
	public static final byte FINISHED = 1 << 3;
	public static final byte PUSHED = 1 << 4;
	public static final byte HANDLED = (byte) ( 1 << 5 & 0xff );

	/**
	 * 发起时间
	 */
	private Long launchTime;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 分院ID
	 */
	private String branchId;

	/**
	 * 订单编号
	 */
	private String orderNo;

	/**
	 * 就诊科室
	 */
	private String deptName;

	/**
	 * 就诊医生
	 */
	private String doctorName;

	/**
	 * 就诊日期
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date scheduleDate;

	/**
	 * 就诊开始时间
	 */
	@DateTimeFormat(pattern = "HH:mm")
	private Date beginTime;

	/**
	 * 就诊结束时间
	 */
	@DateTimeFormat(pattern = "HH:mm")
	private Date endTime;

	/**
	 * 绑定诊疗卡类型 1：就诊卡、2：医保卡、3：社保卡、4：住院号
	 */
	protected Integer cardType;

	/**
	 * 诊疗卡号
	 */
	protected String cardNo;

	/**
	 * 患者姓名
	 */
	protected String patientName;

	/**
	 * 患者手机号码
	 */
	protected String patientMobile;

	/**
	 * 实体类对应的hash子表
	 * 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	/**
	 * 是否有交易
	 * 0: 否
	 * 1: 是
	 */
	private Byte hasTrade;

	/**
	 * 状态
	 * 使用后五位分别标识五种类型的状态
	 * 最后一位，标识是否发生异常
	 * 倒数第二位，标识是否已推送停诊消息
	 * 倒数第三位，标识his是否确认退费
	 * 倒数第四位，标识第三方是否已退费
	 * 倒数第五位，标识his是否已退费
	 */
	private Byte state;

	public StopRegisterRecord() {
		super();
		hasTrade = 0;
		state = 0;
	}

	public StopRegisterRecord(Long launchTime, String hospitalId, String branchId, String orderNo, String deptName, String doctorName,
			Date scheduleDate, Date beginTime, Date endTime, Integer cardType, String cardNo, String patientName, String patientMobile,
			String hashTableName) {
		this.launchTime = launchTime;
		this.hospitalId = hospitalId;
		this.branchId = branchId;
		this.orderNo = orderNo;
		this.deptName = deptName;
		this.doctorName = doctorName;
		this.scheduleDate = scheduleDate;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.patientName = patientName;
		this.patientMobile = patientMobile;
		this.hashTableName = hashTableName;
	}

	public Long getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(Long launchTime) {
		this.launchTime = launchTime;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile;
	}

	public String getHashTableName() {
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public Byte getHasTrade() {
		return hasTrade;
	}

	public void setHasTrade(Byte hasTrade) {
		this.hasTrade = hasTrade;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Byte getHisRefundState() {
		return (byte) ( ( state & 1 ) & 0xff );
	}

	public void setHisRefundState(Integer hisRefundState) {
		state = (byte) ( ( state | hisRefundState ) & 0xff );
	}

	public Byte getRefundState() {
		return (byte) ( ( ( state & 1 << 1 ) >> 1 ) & 0xff );
	}

	public void setRefundState(Integer refundState) {
		state = (byte) ( ( state | refundState << 1 ) & 0xff );
	}

	public Byte getAckRefundState() {
		return (byte) ( ( ( state & 1 << 2 ) >> 2 ) & 0xff );
	}

	public void setAckRefundState(Integer ackRefundState) {
		state = (byte) ( ( state | ackRefundState << 2 ) & 0xff );
	}

	public Byte getFinishState() {
		return (byte) ( ( ( state & 1 << 3 ) >> 3 ) & 0xff );
	}

	public void setFinishState(Integer finishState) {
		state = (byte) ( ( state | finishState << 3 ) & 0xff );
	}

	public Byte getPushState() {
		return (byte) ( ( ( state & 1 << 4 ) >> 4 ) & 0xff );
	}

	public void setPushState(Integer pushState) {
		state = (byte) ( ( state | pushState << 4 ) & 0xff );
	}

	public Byte getHandleState() {
		return (byte) ( ( ( state & 1 << 5 ) >> 5 ) & 0xff );
	}

	public void setHandleState(Integer handleState) {
		state = (byte) ( ( state | handleState << 5 ) & 0xff );
	}
}