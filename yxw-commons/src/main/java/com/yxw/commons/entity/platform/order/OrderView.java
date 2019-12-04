/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.order;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.order.entity
 * @ClassName: OrderQuery
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年8月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OrderView extends BaseEntity implements Serializable {
    
    /**
	 * 
	 */
    private static final long serialVersionUID = 5145615013054086638L;
    
    /**
     * 订单类型 1：挂号 2：门诊
     */
    private String orderType;
    
    /**
     * 所属表
     */
    private String tableName;
    
    /**
     * 医院id
     */
    private String hospitalId;
    
    /**
     * 医院code
     */
    private String hospitalCode;
    
    /**
     * 支付状态
     */
    private String payStatus;
    
    /**
     * 订单生成时间
     */
    private String createTime;
    
    /**
     * 卡号
     */
    private String cardNo;
    
    /**
     * 绑卡人姓名
     */
    private String patientName;
    
    /**
     * 绑卡人电话
     */
    private String patientMobile;
    
    /**
     * 订单号 标准平台
     */
    private String orderNo;
    
    /**
     * 订单号的hash值 标准平台
     */
    private Integer orderNoHashVal;
    
    /**
     * his订单号
     */
    private String hisOrdNo;
    
    /**
     * 第三方平台支付订单号
     */
    private String agtOrdNum;
    
    /**
     * 支付时间
     */
    private String payTime;
    
    /**
     * 退款单号 标准平台
     */
    private String refundOrderNo;
    
    /**
     * his退款单号
     */
    private String refundHisOrdNo;
    
    /**
     * 第三方退款单号
     */
    private String agtRefundOrdNum;
    
    /**
     * 退款时间
     */
    private String refundTime;
    
    /**
     * 关联单号
     */
    private String receiptNum;
    
    /**
     * 来源 1：微信 2：支付宝
     */
    private String bizMode;
    
    /**
     * 交易金额 单位：分
     */
    private String payFee;
    
    /**
     * title 列表显示备注内容
     */
    private String title;
    
    /**
     * 业务状态
     */
    private String bizStatus;
    
    /**
     * 用户openId
     */
    private String openId;
    
    /**
     * @return the openId
     */
    
    public String getOpenId() {
        return openId;
    }
    
    /**
     * @param openId
     *            the openId to set
     */
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public OrderView() {
        super();
    }
    
    public OrderView(String orderType, String tableName, String hospitalId, String hospitalCode, String payStatus,
            String createTime, String cardNo, String patientName, String patientMobile, String orderNo,
            Integer orderNoHashVal, String hisOrdNo, String agtOrdNum, String payTime, String refundOrderNo,
            String refundHisOrdNo, String agtRefundOrdNum, String refundTime, String openId) {
        super();
        this.orderType = orderType;
        this.tableName = tableName;
        this.hospitalId = hospitalId;
        this.hospitalCode = hospitalCode;
        this.payStatus = payStatus;
        this.createTime = createTime;
        this.cardNo = cardNo;
        this.patientName = patientName;
        this.patientMobile = patientMobile;
        this.orderNo = orderNo;
        this.orderNoHashVal = orderNoHashVal;
        this.hisOrdNo = hisOrdNo;
        this.agtOrdNum = agtOrdNum;
        this.payTime = payTime;
        this.refundOrderNo = refundOrderNo;
        this.refundHisOrdNo = refundHisOrdNo;
        this.agtRefundOrdNum = agtRefundOrdNum;
        this.refundTime = refundTime;
        this.openId = openId;
    }
    
    public String getOrderType() {
        return orderType;
    }
    
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
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
    
    public String getPayStatus() {
        return payStatus;
    }
    
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    
    public String getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
    
    public String getHisOrdNo() {
        return hisOrdNo;
    }
    
    public void setHisOrdNo(String hisOrdNo) {
        this.hisOrdNo = hisOrdNo;
    }
    
    public String getAgtOrdNum() {
        return agtOrdNum;
    }
    
    public void setAgtOrdNum(String agtOrdNum) {
        this.agtOrdNum = agtOrdNum;
    }
    
    public String getPayTime() {
        return payTime;
    }
    
    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
    
    public String getRefundOrderNo() {
        return refundOrderNo;
    }
    
    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }
    
    public String getRefundHisOrdNo() {
        return refundHisOrdNo;
    }
    
    public void setRefundHisOrdNo(String refundHisOrdNo) {
        this.refundHisOrdNo = refundHisOrdNo;
    }
    
    public String getAgtRefundOrdNum() {
        return agtRefundOrdNum;
    }
    
    public void setAgtRefundOrdNum(String agtRefundOrdNum) {
        this.agtRefundOrdNum = agtRefundOrdNum;
    }
    
    public String getRefundTime() {
        return refundTime;
    }
    
    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }
    
    public String getReceiptNum() {
        return receiptNum;
    }
    
    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum;
    }
    
    public String getBizMode() {
        return bizMode;
    }
    
    public void setBizMode(String bizMode) {
        this.bizMode = bizMode;
    }
    
    public String getPayFee() {
        return payFee;
    }
    
    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getBizStatus() {
        return bizStatus;
    }
    
    public void setBizStatus(String bizStatus) {
        this.bizStatus = bizStatus;
    }
    
}
