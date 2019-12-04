package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleEdit
 * @Statement: <p>医院配置规则-->编辑规则</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleEdit extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -837205562924384298L;
	private static SimpleDateFormat df = new SimpleDateFormat("HH:mm");

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 微信皮肤颜色类型： 0 蓝色  1绿色
	 */
	private Integer skinWeixinType;

	/**
	 * 支付宝皮肤颜色类型： 0 蓝色  1绿色
	 */
	private Integer skinAlipayType;

	/**
	 * 是否有分院  1：有  0：没有
	 */
	private Integer isHasBranch;

	/**
	 * 是否显示接入平台的自身菜单
	 */
	private Integer isShowAppMenu;

	/**
	 * 是否兼容医院不修改接口 : 0:不兼容（默认），1：兼容-- dfw
	 */
	private Integer isCompatibleOtherPlatform;

	/**
	 * 同一个账号允许添加就诊人个数
	 */
	private Integer addVpNum;

	/**
	 * 接受停诊信息方式:0：医院his主动推送1：标准平台轮询查询
	 */
	private Integer acceptStopInfoType;

	/**
	 * 推送/查询停诊信息时间
	 */
	private String pushInfoTime;

	/**
	 * 推送/查询停诊查询天数 默认为1天
	 * 2015年7月23日 20:11:43 周鉴斌 添加
	 */
	private Integer pushInfoDay = 1;

	/**
	 * 获取患者门诊缴费信息的方式0：医院HIS主动推送1：标准平台定时轮询
	 */
	private Integer paymentInfoGetType;

	/**
	 * 住院功能查询方式0：仅通过门诊信息查询住院信息1：仅通过住院号/住院流水号查询住院信息 2：可通过以上两种方式查询住院信息
	 */
	private Integer inpatientInquiryMode;

	/**
	 * 页面底部logo信息
	 */
	private String footLogoInfo;

	/**
	 * 就诊通知-当天就诊推送的支付类型订单
	 * 0.已支付 1.全部
	 * */
	private int curDayVisitNoticeOrderPayType;

	/**
	 * 就诊通知-提前一天的就诊通知推送的支付类型订单
	 * 0.已支付 1.全部
	 * */
	private int preDayVisitNoticeOrderPayType;

	/**
	 * 停诊是否调用退费确认接口
	 * 0-不调用    1-调用
	 * */
	private int stopRegNeedInvokeAckRefund;

	/**
	 * 停诊是否限制超过就诊开始时间无法停诊
	 * 0-不限制    1-限制
	 * */
	private int overBeginTimeBanStopReg;

	/**
	 * 是否开放模版消息推送接口【对外接口】
	 * 0-不开放    1-开放
	 * */
	private int opentemplateMsgPush;

	/**
	 * 是否开启高频停诊 0 否 1 是
	 * */
	private int highFrequencyStopReg;
	/**
	 * 高频停诊 从当天到未来第 N 天
	 * */
	private int highFrequencyStopRegDayNum;

	/**
	 * his支付失败退费等待时间
	 */
	private Integer refundWaitingSeconds;

	/**
	 * 接受替诊信息方式:0：医院his主动推送1：标准平台轮询查询
	 */
	private Integer acceptReplaceRegInfoType;

	/**
	 * 推送/查询替诊信息时间
	 */
	private Date replaceRegPushInfoTime;

	/**
	 * 推送/查询替诊查询天数 默认为1天
	 */
	private Integer replaceRegPushInfoDay = 1;

	/**
	 * 停诊异常时需要通知的收件人
	 */
	private String recipientsForStopRegException;

	public RuleEdit() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 微信皮肤颜色类型： 0 蓝色  1绿色
	 * @return
	 */
	public Integer getSkinWeixinType() {
		return skinWeixinType;
	}

	public void setSkinWeixinType(Integer skinWeixinType) {
		this.skinWeixinType = skinWeixinType;
	}

	/**
	 * 支付宝皮肤颜色类型： 0 蓝色  1绿色
	 * @return
	 */
	public Integer getSkinAlipayType() {
		return skinAlipayType;
	}

	public void setSkinAlipayType(Integer skinAlipayType) {
		this.skinAlipayType = skinAlipayType;
	}

	/**
	 * 同一个账号允许添加就诊人个数
	 * @return
	 */
	public Integer getAddVpNum() {
		return addVpNum;
	}

	public void setAddVpNum(Integer addVpNum) {
		this.addVpNum = addVpNum;
	}

	/**
	 * 接受停诊信息方式:0：医院his主动推送1：标准平台轮询查询
	 * @return
	 */
	public Integer getAcceptStopInfoType() {
		return acceptStopInfoType;
	}

	public void setAcceptStopInfoType(Integer acceptStopInfoType) {
		this.acceptStopInfoType = acceptStopInfoType;
	}

	/**
	 * 推送/查询停诊信息时间
	 * @return
	 */
	public String getPushInfoTime() {
		return pushInfoTime;
	}

	public void setPushInfoTime(String pushInfoTime) {
		this.pushInfoTime = pushInfoTime;
	}

	/**
	 * 停诊查询天数  默认1天
	 * 2015年7月23日 20:11:13 周鉴斌 增加
	 * @return
	 */
	public Integer getPushInfoDay() {
		return pushInfoDay;
	}

	public void setPushInfoDay(Integer pushInfoDay) {
		this.pushInfoDay = pushInfoDay;
	}

	/**
	 * 获取患者门诊缴费信息的方式0：医院HIS主动推送1：标准平台定时轮询
	 * @return
	 */
	public Integer getPaymentInfoGetType() {
		return paymentInfoGetType;
	}

	public void setPaymentInfoGetType(Integer paymentInfoGetType) {
		this.paymentInfoGetType = paymentInfoGetType;
	}

	/**
	 * 住院功能查询方式0：仅通过门诊信息查询住院信息1：仅通过住院号/住院流水号查询住院信息 2：可通过以上两种方式查询住院信息
	 * @return
	 */
	public Integer getInpatientInquiryMode() {
		return inpatientInquiryMode;
	}

	public void setInpatientInquiryMode(Integer inpatientInquiryMode) {
		this.inpatientInquiryMode = inpatientInquiryMode;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Integer getIsHasBranch() {
		return isHasBranch;
	}

	public void setIsHasBranch(Integer isHasBranch) {
		this.isHasBranch = isHasBranch;
	}

	public Integer getIsShowAppMenu() {
		return isShowAppMenu;
	}

	public void setIsShowAppMenu(Integer isShowAppMenu) {
		this.isShowAppMenu = isShowAppMenu;
	}

	public Integer getIsCompatibleOtherPlatform() {
		return isCompatibleOtherPlatform;
	}

	public void setIsCompatibleOtherPlatform(Integer isCompatibleOtherPlatform) {
		this.isCompatibleOtherPlatform = isCompatibleOtherPlatform;
	}

	/**
	 * 得到默认的编辑规则
	 * @param hospitalId
	 * @return
	 */
	public static RuleEdit getDefaultRule(String hospitalId) {
		RuleEdit ruleEdit = new RuleEdit();
		ruleEdit.setHospitalId(hospitalId);
		ruleEdit.setIsHasBranch(0);
		ruleEdit.setIsShowAppMenu(1);
		ruleEdit.setSkinWeixinType(0);
		ruleEdit.setSkinAlipayType(0);
		ruleEdit.setIsCompatibleOtherPlatform(0);
		ruleEdit.setAddVpNum(4);
		ruleEdit.setAcceptStopInfoType(0);
		GregorianCalendar now = new GregorianCalendar();
		now.set(Calendar.HOUR_OF_DAY, 18);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		//ruleEdit.setPushInfoTime(now.getTime());
		ruleEdit.setPushInfoTime("18:00"); // TODO 字段类型更改
		ruleEdit.setAcceptReplaceRegInfoType(0);
		ruleEdit.setReplaceRegPushInfoTime(now.getTime());

		ruleEdit.setPaymentInfoGetType(0);
		ruleEdit.setInpatientInquiryMode(0);
		ruleEdit.setCurDayVisitNoticeOrderPayType(0);
		ruleEdit.setPreDayVisitNoticeOrderPayType(0);
		ruleEdit.setStopRegNeedInvokeAckRefund(0);
		ruleEdit.setOpentemplateMsgPush(1);
		ruleEdit.setHighFrequencyStopReg(0);
		ruleEdit.setOverBeginTimeBanStopReg(1);
		return ruleEdit;
	}

	public String getFootLogoInfo() {
		return footLogoInfo;
	}

	public void setFootLogoInfo(String footLogoInfo) {
		this.footLogoInfo = footLogoInfo;
	}

	public int getCurDayVisitNoticeOrderPayType() {
		return curDayVisitNoticeOrderPayType;
	}

	public void setCurDayVisitNoticeOrderPayType(int curDayVisitNoticeOrderPayType) {
		this.curDayVisitNoticeOrderPayType = curDayVisitNoticeOrderPayType;
	}

	public int getPreDayVisitNoticeOrderPayType() {
		return preDayVisitNoticeOrderPayType;
	}

	public void setPreDayVisitNoticeOrderPayType(int preDayVisitNoticeOrderPayType) {
		this.preDayVisitNoticeOrderPayType = preDayVisitNoticeOrderPayType;
	}

	public int getStopRegNeedInvokeAckRefund() {
		return stopRegNeedInvokeAckRefund;
	}

	public void setStopRegNeedInvokeAckRefund(int stopRegNeedInvokeAckRefund) {
		this.stopRegNeedInvokeAckRefund = stopRegNeedInvokeAckRefund;
	}

	public int getOverBeginTimeBanStopReg() {
		return overBeginTimeBanStopReg;
	}

	public void setOverBeginTimeBanStopReg(int overBeginTimeBanStopReg) {
		this.overBeginTimeBanStopReg = overBeginTimeBanStopReg;
	}

	public int getOpentemplateMsgPush() {
		return opentemplateMsgPush;
	}

	public void setOpentemplateMsgPush(int opentemplateMsgPush) {
		this.opentemplateMsgPush = opentemplateMsgPush;
	}

	public int getHighFrequencyStopReg() {
		return highFrequencyStopReg;
	}

	public void setHighFrequencyStopReg(int highFrequencyStopReg) {
		this.highFrequencyStopReg = highFrequencyStopReg;
	}

	public int getHighFrequencyStopRegDayNum() {
		return highFrequencyStopRegDayNum;
	}

	public void setHighFrequencyStopRegDayNum(int highFrequencyStopRegDayNum) {
		this.highFrequencyStopRegDayNum = highFrequencyStopRegDayNum;
	}

	public Integer getRefundWaitingSeconds() {
		return refundWaitingSeconds;
	}

	public void setRefundWaitingSeconds(Integer refundWaitingSeconds) {
		this.refundWaitingSeconds = refundWaitingSeconds;
	}

	public Integer getAcceptReplaceRegInfoType() {
		return acceptReplaceRegInfoType;
	}

	public void setAcceptReplaceRegInfoType(Integer acceptReplaceRegInfoType) {
		this.acceptReplaceRegInfoType = acceptReplaceRegInfoType;
	}

	public Date getReplaceRegPushInfoTime() {
		return replaceRegPushInfoTime;
	}

	public void setReplaceRegPushInfoTime(Date replaceRegPushInfoTime) {
		this.replaceRegPushInfoTime = replaceRegPushInfoTime;
	}

	public Integer getReplaceRegPushInfoDay() {
		return replaceRegPushInfoDay;
	}

	public void setReplaceRegPushInfoDay(Integer replaceRegPushInfoDay) {
		this.replaceRegPushInfoDay = replaceRegPushInfoDay;
	}

	public String getRecipientsForStopRegException() {
		return recipientsForStopRegException;
	}

	public void setRecipientsForStopRegException(String recipientsForStopRegException) {
		this.recipientsForStopRegException = recipientsForStopRegException;
	}
}