/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-27</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.register;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.MsgPushEntity;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;

/**
 * @Package: com.yxw.platform.register.entity
 * @ClassName: Record
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public abstract class Record extends MsgPushEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8588531030595649317L;

	/**
	 * 医院id
	 */
	protected String hospitalId;

	/**
	 * 医院code
	 */
	protected String hospitalCode;

	/**
	 * 医院名称  不存入数据库
	 */
	protected String hospitalName;

	/**
	 * 分院id
	 */
	protected String branchHospitalId;

	/**
	 * 分院code
	 */
	protected String branchHospitalCode;

	/**
	 * 分院code
	 */
	protected String branchHospitalName;

	/**
	 * 就诊人唯一标示
	 */
	protected String openId;

	/**
	 * 患者姓名
	 */
	protected String patientName;

	/**
	 * 脱敏后的患者姓名
	 */
	protected String encryptedPatientName;

	/**
	 * 患者手机号码
	 */
	protected String patientMobile;

	/**
	 * 患者性别
	 */
	protected Integer patientSex;

	/**
	 * 患者性别 中文显示 不写入数据库 消息发送时使用
	 */
	protected String patientSexLable;

	/**
	 * 证件类型
	 */
	protected Integer idType;

	/**
	 * 证件号码
	 */
	protected String idNo;

	/**
	 * 绑定诊疗卡类型 1：就诊卡、2：医保卡、3：社保卡、4：住院号
	 */
	protected Integer cardType;

	/**
	 * 诊疗卡号
	 */
	protected String cardNo;

	/**
	 * 脱敏后的患者姓名
	 */
	protected String encryptedCardNo;

	/**
	 * 修改时间
	 */
	protected Long updateTime;

	/**
	 * 交易方式  
	 * BizConstant.TRADE_MODE_WEIXIN = 1  微信
	 * BizConstant.TRADE_MODE_ALIPAY = 2 支付宝
	 * TRADE_MODE_EASYHEALTH_WEIXIN_VAL = 3  健康易-微信
	 * TRADE_MODE_EASYHEALTH_ALIPAY_VAL = 4  健康易-支付宝
	 * TRADE_MODE_EASYHEALTH_UNIONPAY_VAL = 5 健康易-银联
	 * BizConstant.TRADE_MODE_UNIONPAY = 6 银联
	 */
	protected Integer tradeMode;

	/**
	 * 平台类型
	 */
	protected Integer platformMode;

	/**
	 * 支付金额
	 */
	protected Integer payTotalFee;

	/**
	 * 支付时间(第3方支付成功后的时间)
	 */
	protected Long payTime;

	/**
	 * 退费金额
	 */
	protected Integer refundTotalFee;

	/**
	 * 退费时间
	 */
	protected Long refundTime;

	/**
	 * 订单编号
	 */
	protected String orderNo;

	/**
	 * 订单编号的hash值  用于提高查询性能
	 */
	protected Integer orderNoHashVal;

	/**
	 * 退款订单编号
	 */
	protected String refundOrderNo;

	/**
	 * 医院流水号
	 */
	protected String hisOrdNo;

	/**
	 * 退款医院流水号
	 */
	protected String refundHisOrdNo;

	/**
	 * 交易机构支付单号
	 */
	protected String agtOrdNum;

	/**
	 * 交易机构退费单号 
	 */
	protected String agtRefundOrdNum;

	/**
	 * 费用说明
	 */
	protected String feeDesc;

	/**
	 * 是否已经被支付回调过
	 */
	protected Integer isHadCallBack;

	/**
	 * 记录名称
	 */
	protected String recordTitle;

	/**
	 * 商户appId    传值不存数据库
	 */
	protected String appId;

	/**
	 * 商户接入的平台code   传值不存数据库
	 */
	protected String appCode;

	/**
	 * 应用的url  传值不存数据库
	 */
	protected String appUrl;

	/**
	 * 操作失败信息
	 */
	protected String failureMsg;

	/**
	 * 支付状态：1.未支付, 2.已支付  3.已退费 , 4.支付中  5.退费中
	 */
	protected Integer payStatus;

	/**
	 * 实体类对应的hash子表
	 * 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	/**
	 * his单据号/预约号
	 */
	protected String receiptNum;

	/**
	 * 支付时间，不入库  消息推送使用
	 */
	protected String payTimeLabel;

	/**
	 * 支付日期，不入库，缴费记录显示使用
	 */
	protected String payDate;

	/**
	 * 退费时间，不入库  消息推送使用
	 */
	protected String refundTimeLabel;

	/**
	 * 订单生成时间
	 */
	protected Long createTime;

	/**
	 * 是否是医保支付 1 是 0 否
	 */
	protected Integer isMedicarePayMents;

	protected Integer overallPlanFee;

	protected Integer personalAccountFee;

	public Record(String orderNo) {
		super();
		this.orderNo = orderNo;
	}

	public Record() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getBranchHospitalId() {
		return branchHospitalId;
	}

	public void setBranchHospitalId(String branchHospitalId) {
		this.branchHospitalId = branchHospitalId;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	public String getPatientName() {
		return patientName;
	}

	public String getPatientMobile() {
		return patientMobile;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName == null ? null : patientName.trim();
	}

	public void setPatientMobile(String patientMobile) {
		this.patientMobile = patientMobile == null ? null : patientMobile.trim();
	}

	public String getFeeDesc() {
		return feeDesc;
	}

	public void setFeeDesc(String feeDesc) {
		this.feeDesc = feeDesc == null ? null : feeDesc.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderNoHashVal() {
		return orderNoHashVal;
	}

	public void setOrderNoHashVal(Integer orderNoHashVal) {
		this.orderNoHashVal = orderNoHashVal;
	}

	public String getRefundOrderNo() {
		return refundOrderNo;
	}

	public void setRefundOrderNo(String refundOrderNo) {
		this.refundOrderNo = refundOrderNo;
	}

	public String getHisOrdNo() {
		return hisOrdNo;
	}

	public void setHisOrdNo(String hisOrdNo) {
		this.hisOrdNo = hisOrdNo;
	}

	public String getRefundHisOrdNo() {
		return refundHisOrdNo;
	}

	public void setRefundHisOrdNo(String refundHisOrdNo) {
		this.refundHisOrdNo = refundHisOrdNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	public String getAgtRefundOrdNum() {
		return agtRefundOrdNum;
	}

	public void setAgtRefundOrdNum(String agtRefundOrdNum) {
		this.agtRefundOrdNum = agtRefundOrdNum;
	}

	public Integer getIsHadCallBack() {
		return isHadCallBack;
	}

	public void setIsHadCallBack(Integer isHadCallBack) {
		this.isHadCallBack = isHadCallBack;
	}

	public String getRecordTitle() {
		return recordTitle;
	}

	public void setRecordTitle(String recordTitle) {
		this.recordTitle = recordTitle;
	}

	public String getFailureMsg() {
		return failureMsg;
	}

	public void setFailureMsg(String failureMsg) {
		this.failureMsg = failureMsg;
	}

	public Integer getPatientSex() {
		return patientSex;
	}

	public void setPatientSex(Integer patientSex) {
		this.patientSex = patientSex;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPatientSexLable() {
		if (patientSex != null) {
			if (patientSex.intValue() == BizConstant.SEX_MAN) {
				patientSexLable = "男";
			} else {
				patientSexLable = "女";
			}
		} else {
			patientSexLable = "男";
		}

		return patientSexLable;
	}

	public void setPatientSexLable(String patientSexLable) {
		this.patientSexLable = patientSexLable;
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

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	public Long getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}

	public String getEncryptedPatientName() {
		if (StringUtils.isNotEmpty(patientName)) {
			encryptedPatientName = patientName.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedPatientName;
	}

	public void setEncryptedPatientName(String encryptedPatientName) {
		this.encryptedPatientName = encryptedPatientName;
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getRegRecordHashTable(openId, true);
		}
		return hashTableName;
	}

	public String getPayDate() {
		if (payTime != null) {
			payDate = BizConstant.YYYYMMDD.format(new Date(payTime));
		}
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	@SuppressWarnings("unused")
	private void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getEncryptedCardNo() {
		if (StringUtils.isNotEmpty(cardNo) && cardNo.length() > 2) {
			int count = cardNo.length() / 3;
			if (count == 0) {
				count = count - 1;
			} else if (count > 3) {
				count = 3;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(cardNo.substring(0, count));
			sb.append(cardNo.substring(count, cardNo.length() - count).replaceAll("[0-9]", BizConstant.VIEW_SENSITIVE_CHAR));
			sb.append(cardNo.substring(cardNo.length() - count, cardNo.length()));
			encryptedCardNo = sb.toString();
		} else {
			encryptedCardNo = cardNo;
		}
		return encryptedCardNo;
	}

	public void setEncryptedCardNo(String encryptedCardNo) {
		this.encryptedCardNo = encryptedCardNo;
	}

	public abstract Integer getPayTotalFee();

	public void setPayTotalFee(Integer payTotalFee) {
		this.payTotalFee = payTotalFee;
	}

	public abstract Integer getRefundTotalFee();

	public void setRefundTotalFee(Integer refundTotalFee) {
		this.refundTotalFee = refundTotalFee;
	}

	public String getPayTimeLabel() {
		if (payTime != null) {
			payTimeLabel = BizConstant.YYYYMMDDHHMMSS.format(new Date(payTime));
		}
		return payTimeLabel;
	}

	public void setPayTimeLabel(String payTimeLabel) {
		this.payTimeLabel = payTimeLabel;
	}

	public String getRefundTimeLabel() {
		if (refundTime != null) {
			refundTimeLabel = BizConstant.YYYYMMDDHHMMSS.format(new Date(refundTime));
		}
		return refundTimeLabel;
	}

	public void setRefundTimeLabel(String refundTimeLabel) {
		this.refundTimeLabel = refundTimeLabel;
	}

	public String getReceiptNum() {
		if (StringUtils.isBlank(receiptNum) || "null".equals(receiptNum)) {
			receiptNum = "";
		}

		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}

	public String getBranchHospitalName() {
		return branchHospitalName;
	}

	public void setBranchHospitalName(String branchHospitalName) {
		this.branchHospitalName = branchHospitalName;
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public Integer getPlatformMode() {
		return platformMode;
	}

	public void setPlatformMode(Integer platformMode) {
		this.platformMode = platformMode;
	}

	/**
	 * 订单类型就是交易类型
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年5月17日 
	 * @return
	 */
	public int getOrderType() {
		if (tradeMode != null)
			return tradeMode;
		return 0;
	}

	public Integer getIsMedicarePayMents() {
		return isMedicarePayMents;
	}

	public void setIsMedicarePayMents(Integer isMedicarePayMents) {
		this.isMedicarePayMents = isMedicarePayMents;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getPersonalAccountFee() {
		if (personalAccountFee == null) {
			personalAccountFee = 0;
		}
		return personalAccountFee;
	}

	public void setPersonalAccountFee(Integer personalAccountFee) {
		this.personalAccountFee = personalAccountFee;
	}

	public Integer getOverallPlanFee() {
		if (overallPlanFee == null) {
			overallPlanFee = 0;
		}
		return overallPlanFee;
	}

	public void setOverallPlanFee(Integer overallPlanFee) {
		this.overallPlanFee = overallPlanFee;
	}
}
