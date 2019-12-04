package com.yxw.stats.entity.platform;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

public class DepositOrderCount extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8670881052540966574L;

	/**
	 * 日期
	 */
	private String date;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 分院ID
	 */
	private String branchId;

	/**
	 * 总计数量
	 */
	private Integer count = 0;

	/**
	 * 住院未支付数量
	 */
	private Integer noPayment = 0;

	/**
	 * 住院已支付数量
	 */
	private Integer payment = 0;

	/**
	 * 住院已退费数量
	 */
	private Integer refund = 0;

	/**
	 * 住院缴费总支付金额
	 */
	private Double depositPayFee = 0.0;

	/**
	 * 住院缴费总退费金额
	 */
	private Double depositRefundFee = 0.0;

	/**
	 * 住院缴费部分退费总金额
	 */
	private Double partRefund = 0.0;

	/**
	 * 支付订单总数
	 */
	private Integer payCount = 0;
	/**
	 * 退费订单总数
	 */
	private Integer refundCount = 0;
	/**
	 * 住院缴费支付总金额(统计总数)
	 */
	private Double depositPayTotalFee = 0.0;
	/**
	 * 住院缴费退费总金额(统计总数)
	 */
	private Double depositRefundTotalFee = 0.0;
	/**
	 * 住院缴费部分退费总金额(统计总数)
	 */
	private Double partTotalRefund = 0.0;

	/**
	 * 总计数量
	 */
	private Integer countByWechat = 0;

	/**
	 * 住院未支付数量
	 */
	private Integer noPaymentByWechat = 0;

	/**
	 * 住院已支付数量
	 */
	private Integer paymentByWechat = 0;

	/**
	 * 住院已退费数量
	 */
	private Integer refundByWechat = 0;

	/**
	 * 住院缴费总支付金额
	 */
	private Double depositPayFeeByWechat = 0.0;

	/**
	 * 住院缴费总退费金额
	 */
	private Double depositRefundFeeByWechat = 0.0;

	/**
	 * 住院缴费部分退费总金额
	 */
	private Double partRefundByWechat = 0.0;

	/**
	 * 支付订单总数
	 */
	private Integer payCountByWechat = 0;
	/**
	 * 退费订单总数
	 */
	private Integer refundCountByWechat = 0;
	/**
	 * 住院缴费支付总金额(统计总数)
	 */
	private Double depositPayTotalFeeByWechat = 0.0;
	/**
	 * 住院缴费退费总金额(统计总数)
	 */
	private Double depositRefundTotalFeeByWechat = 0.0;
	/**
	 * 住院缴费部分退费总金额(统计总数)
	 */
	private Double partTotalRefundByWechat = 0.0;

	/**
	 * 总计数量
	 */
	private Integer countByAlipay = 0;

	/**
	 * 住院未支付数量
	 */
	private Integer noPaymentByAlipay = 0;

	/**
	 * 住院已支付数量
	 */
	private Integer paymentByAlipay = 0;

	/**
	 * 住院已退费数量
	 */
	private Integer refundByAlipay = 0;

	/**
	 * 住院缴费总支付金额
	 */
	private Double depositPayFeeByAlipay = 0.0;

	/**
	 * 住院缴费总退费金额
	 */
	private Double depositRefundFeeByAlipay = 0.0;

	/**
	 * 住院缴费部分退费总金额
	 */
	private Double partRefundByAlipay = 0.0;

	/**
	 * 支付订单总数
	 */
	private Integer payCountByAlipay = 0;
	/**
	 * 退费订单总数
	 */
	private Integer refundCountByAlipay = 0;
	/**
	 * 住院缴费支付总金额(统计总数)
	 */
	private Double depositPayTotalFeeByAlipay = 0.0;
	/**
	 * 住院缴费退费总金额(统计总数)
	 */
	private Double depositRefundTotalFeeByAlipay = 0.0;
	/**
	 * 住院缴费部分退费总金额(统计总数)
	 */
	private Double partTotalRefundByAlipay = 0.0;

	/**
	 * 支付渠道
	 */
	private Integer bizMode = 0;

	/**
	 * 是否扫码
	 */
	private Integer payMode;

	/**
	 * @return the payMode
	 */

	public Integer getPayMode() {
		return payMode;
	}

	/**
	 * @param payMode
	 *            the payMode to set
	 */

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	/**
	 * @return the countByWechat
	 */

	public Integer getCountByWechat() {
		return countByWechat;
	}

	/**
	 * @param countByWechat
	 *            the countByWechat to set
	 */

	public void setCountByWechat(Integer countByWechat) {
		this.countByWechat = countByWechat;
	}

	/**
	 * @return the noPaymentByWechat
	 */

	public Integer getNoPaymentByWechat() {
		return noPaymentByWechat;
	}

	/**
	 * @param noPaymentByWechat
	 *            the noPaymentByWechat to set
	 */

	public void setNoPaymentByWechat(Integer noPaymentByWechat) {
		this.noPaymentByWechat = noPaymentByWechat;
	}

	/**
	 * @return the paymentByWechat
	 */

	public Integer getPaymentByWechat() {
		return paymentByWechat;
	}

	/**
	 * @param paymentByWechat
	 *            the paymentByWechat to set
	 */

	public void setPaymentByWechat(Integer paymentByWechat) {
		this.paymentByWechat = paymentByWechat;
	}

	/**
	 * @return the refundByWechat
	 */

	public Integer getRefundByWechat() {
		return refundByWechat;
	}

	/**
	 * @param refundByWechat
	 *            the refundByWechat to set
	 */

	public void setRefundByWechat(Integer refundByWechat) {
		this.refundByWechat = refundByWechat;
	}

	/**
	 * @return the depositPayFeeByWechat
	 */

	public Double getDepositPayFeeByWechat() {
		return depositPayFeeByWechat;
	}

	/**
	 * @param depositPayFeeByWechat
	 *            the depositPayFeeByWechat to set
	 */

	public void setDepositPayFeeByWechat(Double depositPayFeeByWechat) {
		this.depositPayFeeByWechat = depositPayFeeByWechat;
	}

	/**
	 * @return the depositRefundFeeByWechat
	 */

	public Double getDepositRefundFeeByWechat() {
		return depositRefundFeeByWechat;
	}

	/**
	 * @param depositRefundFeeByWechat
	 *            the depositRefundFeeByWechat to set
	 */

	public void setDepositRefundFeeByWechat(Double depositRefundFeeByWechat) {
		this.depositRefundFeeByWechat = depositRefundFeeByWechat;
	}

	/**
	 * @return the partRefundByWechat
	 */

	public Double getPartRefundByWechat() {
		return partRefundByWechat;
	}

	/**
	 * @param partRefundByWechat
	 *            the partRefundByWechat to set
	 */

	public void setPartRefundByWechat(Double partRefundByWechat) {
		this.partRefundByWechat = partRefundByWechat;
	}

	/**
	 * @return the payCountByWechat
	 */

	public Integer getPayCountByWechat() {
		return payCountByWechat;
	}

	/**
	 * @param payCountByWechat
	 *            the payCountByWechat to set
	 */

	public void setPayCountByWechat(Integer payCountByWechat) {
		this.payCountByWechat = payCountByWechat;
	}

	/**
	 * @return the refundCountByWechat
	 */

	public Integer getRefundCountByWechat() {
		return refundCountByWechat;
	}

	/**
	 * @param refundCountByWechat
	 *            the refundCountByWechat to set
	 */

	public void setRefundCountByWechat(Integer refundCountByWechat) {
		this.refundCountByWechat = refundCountByWechat;
	}

	/**
	 * @return the depositPayTotalFeeByWechat
	 */

	public Double getDepositPayTotalFeeByWechat() {
		return depositPayTotalFeeByWechat;
	}

	/**
	 * @param depositPayTotalFeeByWechat
	 *            the depositPayTotalFeeByWechat to set
	 */

	public void setDepositPayTotalFeeByWechat(Double depositPayTotalFeeByWechat) {
		this.depositPayTotalFeeByWechat = depositPayTotalFeeByWechat;
	}

	/**
	 * @return the depositRefundTotalFeeByWechat
	 */

	public Double getDepositRefundTotalFeeByWechat() {
		return depositRefundTotalFeeByWechat;
	}

	/**
	 * @param depositRefundTotalFeeByWechat
	 *            the depositRefundTotalFeeByWechat to set
	 */

	public void setDepositRefundTotalFeeByWechat(Double depositRefundTotalFeeByWechat) {
		this.depositRefundTotalFeeByWechat = depositRefundTotalFeeByWechat;
	}

	/**
	 * @return the partTotalRefundByWechat
	 */

	public Double getPartTotalRefundByWechat() {
		return partTotalRefundByWechat;
	}

	/**
	 * @param partTotalRefundByWechat
	 *            the partTotalRefundByWechat to set
	 */

	public void setPartTotalRefundByWechat(Double partTotalRefundByWechat) {
		this.partTotalRefundByWechat = partTotalRefundByWechat;
	}

	/**
	 * @return the countByAlipay
	 */

	public Integer getCountByAlipay() {
		return countByAlipay;
	}

	/**
	 * @param countByAlipay
	 *            the countByAlipay to set
	 */

	public void setCountByAlipay(Integer countByAlipay) {
		this.countByAlipay = countByAlipay;
	}

	/**
	 * @return the noPaymentByAlipay
	 */

	public Integer getNoPaymentByAlipay() {
		return noPaymentByAlipay;
	}

	/**
	 * @param noPaymentByAlipay
	 *            the noPaymentByAlipay to set
	 */

	public void setNoPaymentByAlipay(Integer noPaymentByAlipay) {
		this.noPaymentByAlipay = noPaymentByAlipay;
	}

	/**
	 * @return the paymentByAlipay
	 */

	public Integer getPaymentByAlipay() {
		return paymentByAlipay;
	}

	/**
	 * @param paymentByAlipay
	 *            the paymentByAlipay to set
	 */

	public void setPaymentByAlipay(Integer paymentByAlipay) {
		this.paymentByAlipay = paymentByAlipay;
	}

	/**
	 * @return the refundByAlipay
	 */

	public Integer getRefundByAlipay() {
		return refundByAlipay;
	}

	/**
	 * @param refundByAlipay
	 *            the refundByAlipay to set
	 */

	public void setRefundByAlipay(Integer refundByAlipay) {
		this.refundByAlipay = refundByAlipay;
	}

	/**
	 * @return the depositPayFeeByAlipay
	 */

	public Double getDepositPayFeeByAlipay() {
		return depositPayFeeByAlipay;
	}

	/**
	 * @param depositPayFeeByAlipay
	 *            the depositPayFeeByAlipay to set
	 */

	public void setDepositPayFeeByAlipay(Double depositPayFeeByAlipay) {
		this.depositPayFeeByAlipay = depositPayFeeByAlipay;
	}

	/**
	 * @return the depositRefundFeeByAlipay
	 */

	public Double getDepositRefundFeeByAlipay() {
		return depositRefundFeeByAlipay;
	}

	/**
	 * @param depositRefundFeeByAlipay
	 *            the depositRefundFeeByAlipay to set
	 */

	public void setDepositRefundFeeByAlipay(Double depositRefundFeeByAlipay) {
		this.depositRefundFeeByAlipay = depositRefundFeeByAlipay;
	}

	/**
	 * @return the partRefundByAlipay
	 */

	public Double getPartRefundByAlipay() {
		return partRefundByAlipay;
	}

	/**
	 * @param partRefundByAlipay
	 *            the partRefundByAlipay to set
	 */

	public void setPartRefundByAlipay(Double partRefundByAlipay) {
		this.partRefundByAlipay = partRefundByAlipay;
	}

	/**
	 * @return the payCountByAlipay
	 */

	public Integer getPayCountByAlipay() {
		return payCountByAlipay;
	}

	/**
	 * @param payCountByAlipay
	 *            the payCountByAlipay to set
	 */

	public void setPayCountByAlipay(Integer payCountByAlipay) {
		this.payCountByAlipay = payCountByAlipay;
	}

	/**
	 * @return the refundCountByAlipay
	 */

	public Integer getRefundCountByAlipay() {
		return refundCountByAlipay;
	}

	/**
	 * @param refundCountByAlipay
	 *            the refundCountByAlipay to set
	 */

	public void setRefundCountByAlipay(Integer refundCountByAlipay) {
		this.refundCountByAlipay = refundCountByAlipay;
	}

	/**
	 * @return the depositPayTotalFeeByAlipay
	 */

	public Double getDepositPayTotalFeeByAlipay() {
		return depositPayTotalFeeByAlipay;
	}

	/**
	 * @param depositPayTotalFeeByAlipay
	 *            the depositPayTotalFeeByAlipay to set
	 */

	public void setDepositPayTotalFeeByAlipay(Double depositPayTotalFeeByAlipay) {
		this.depositPayTotalFeeByAlipay = depositPayTotalFeeByAlipay;
	}

	/**
	 * @return the depositRefundTotalFeeByAlipay
	 */

	public Double getDepositRefundTotalFeeByAlipay() {
		return depositRefundTotalFeeByAlipay;
	}

	/**
	 * @param depositRefundTotalFeeByAlipay
	 *            the depositRefundTotalFeeByAlipay to set
	 */

	public void setDepositRefundTotalFeeByAlipay(Double depositRefundTotalFeeByAlipay) {
		this.depositRefundTotalFeeByAlipay = depositRefundTotalFeeByAlipay;
	}

	/**
	 * @return the partTotalRefundByAlipay
	 */

	public Double getPartTotalRefundByAlipay() {
		return partTotalRefundByAlipay;
	}

	/**
	 * @param partTotalRefundByAlipay
	 *            the partTotalRefundByAlipay to set
	 */

	public void setPartTotalRefundByAlipay(Double partTotalRefundByAlipay) {
		this.partTotalRefundByAlipay = partTotalRefundByAlipay;
	}

	/**
	 * @return the bizMode
	 */

	public Integer getBizMode() {
		return bizMode;
	}

	/**
	 * @param bizMode
	 *            the bizMode to set
	 */

	public void setBizMode(Integer bizMode) {
		this.bizMode = bizMode;
	}

	/**
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the date
	 */

	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */

	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the hospitalId
	 */

	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId
	 *            the hospitalId to set
	 */

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the branchId
	 */

	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId
	 *            the branchId to set
	 */

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the count
	 */

	public Integer getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */

	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the noPayment
	 */

	public Integer getNoPayment() {
		return noPayment;
	}

	/**
	 * @param noPayment
	 *            the noPayment to set
	 */

	public void setNoPayment(Integer noPayment) {
		this.noPayment = noPayment;
	}

	/**
	 * @return the payment
	 */

	public Integer getPayment() {
		return payment;
	}

	/**
	 * @param payment
	 *            the payment to set
	 */

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	/**
	 * @return the refund
	 */

	public Integer getRefund() {
		return refund;
	}

	/**
	 * @param refund
	 *            the refund to set
	 */

	public void setRefund(Integer refund) {
		this.refund = refund;
	}

	/**
	 * @return the depositPayFee
	 */

	public Double getDepositPayFee() {
		return depositPayFee;
	}

	/**
	 * @param depositPayFee
	 *            the depositPayFee to set
	 */

	public void setDepositPayFee(Double depositPayFee) {
		this.depositPayFee = depositPayFee;
	}

	/**
	 * @return the depositRefundFee
	 */

	public Double getDepositRefundFee() {
		return depositRefundFee;
	}

	/**
	 * @param depositRefundFee
	 *            the depositRefundFee to set
	 */

	public void setDepositRefundFee(Double depositRefundFee) {
		this.depositRefundFee = depositRefundFee;
	}

	/**
	 * @return the partRefund
	 */

	public Double getPartRefund() {
		return partRefund;
	}

	/**
	 * @param partRefund
	 *            the partRefund to set
	 */

	public void setPartRefund(Double partRefund) {
		this.partRefund = partRefund;
	}

	/**
	 * @return the payCount
	 */

	public Integer getPayCount() {
		return payCount;
	}

	/**
	 * @param payCount
	 *            the payCount to set
	 */

	public void setPayCount(Integer payCount) {
		this.payCount = payCount;
	}

	/**
	 * @return the refundCount
	 */

	public Integer getRefundCount() {
		return refundCount;
	}

	/**
	 * @param refundCount
	 *            the refundCount to set
	 */

	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	/**
	 * @return the depositPayTotalFee
	 */

	public Double getDepositPayTotalFee() {
		return depositPayTotalFee;
	}

	/**
	 * @param depositPayTotalFee
	 *            the depositPayTotalFee to set
	 */

	public void setDepositPayTotalFee(Double depositPayTotalFee) {
		this.depositPayTotalFee = depositPayTotalFee;
	}

	/**
	 * @return the depositRefundTotalFee
	 */

	public Double getDepositRefundTotalFee() {
		return depositRefundTotalFee;
	}

	/**
	 * @param depositRefundTotalFee
	 *            the depositRefundTotalFee to set
	 */

	public void setDepositRefundTotalFee(Double depositRefundTotalFee) {
		this.depositRefundTotalFee = depositRefundTotalFee;
	}

	/**
	 * @return the partTotalRefund
	 */

	public Double getPartTotalRefund() {
		return partTotalRefund;
	}

	/**
	 * @param partTotalRefund
	 *            the partTotalRefund to set
	 */

	public void setPartTotalRefund(Double partTotalRefund) {
		this.partTotalRefund = partTotalRefund;
	}

	/**
	 * @return
	 * @return the payCount
	 */
	public DepositOrderCount() {
		super();
	}

}
