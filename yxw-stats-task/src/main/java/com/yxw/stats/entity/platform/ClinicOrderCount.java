package com.yxw.stats.entity.platform;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

public class ClinicOrderCount extends BaseEntity implements Serializable {

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
	 * 门诊未支付数量
	 */
	private Integer noPayment = 0;

	/**
	 * 门诊已支付数量
	 */
	private Integer payment = 0;

	/**
	 * 门诊已退费数量
	 */
	private Integer refund = 0;

	/**
	 * 门诊缴费总支付金额
	 */
	private Double clinicPayFee = 0.0;

	/**
	 * 门诊缴费总退费金额
	 */
	private Double clinicRefundFee = 0.0;

	/**
	 * 门诊缴费部分退费总金额
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
	 * 门诊缴费支付总金额(统计总数)
	 */
	private Double clinicPayTotalFee = 0.0;
	/**
	 * 门诊缴费退费总金额(统计总数)
	 */
	private Double clinicRefundTotalFee = 0.0;
	/**
	 * 门诊缴费部分退费总金额(统计总数)
	 */
	private Double partTotalRefund = 0.0;
	/**
	 * 渠道
	 */
	private Integer bizMode;

	/**
	 * 总计数量
	 */
	private Integer countByAlipay = 0;

	/**
	 * 门诊未支付数量
	 */
	private Integer noPaymentByAlipay = 0;

	/**
	 * 门诊已支付数量
	 */
	private Integer paymentByAlipay = 0;

	/**
	 * 门诊已退费数量
	 */
	private Integer refundByAlipay = 0;

	/**
	 * 门诊缴费总支付金额
	 */
	private Double clinicPayFeeByAlipay = 0.0;

	/**
	 * 门诊缴费总退费金额
	 */
	private Double clinicRefundFeeByAlipay = 0.0;

	/**
	 * 门诊缴费部分退费总金额
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
	 * 门诊缴费支付总金额(统计总数)
	 */
	private Double clinicPayTotalFeeByAlipay = 0.0;
	/**
	 * 门诊缴费退费总金额(统计总数)
	 */
	private Double clinicRefundTotalFeeByAlipay = 0.0;
	/**
	 * 门诊缴费部分退费总金额(统计总数)
	 */
	private Double partTotalRefundByAlipay = 0.0;

	/**
	 * 总计数量
	 */
	private Integer countByWechat = 0;

	/**
	 * 门诊未支付数量
	 */
	private Integer noPaymentByWechat = 0;

	/**
	 * 门诊已支付数量
	 */
	private Integer paymentByWechat = 0;

	/**
	 * 门诊已退费数量
	 */
	private Integer refundByWechat = 0;

	/**
	 * 门诊缴费总支付金额
	 */
	private Double clinicPayFeeByWechat = 0.0;

	/**
	 * 门诊缴费总退费金额
	 */
	private Double clinicRefundFeeByWechat = 0.0;

	/**
	 * 门诊缴费部分退费总金额
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
	 * 门诊缴费支付总金额(统计总数)
	 */
	private Double clinicPayTotalFeeByWechat = 0.0;
	/**
	 * 门诊缴费退费总金额(统计总数)
	 */
	private Double clinicRefundTotalFeeByWechat = 0.0;
	/**
	 * 门诊缴费部分退费总金额(统计总数)
	 */
	private Double partTotalRefundByWechat = 0.0;

	/**
	 * 是否扫码
	 */
	private Integer payMode;

	/**
	 * 是否医保、自费
	 */
	private Integer medicarePayments;

	/**
	 * @return the medicarePayments
	 */

	public Integer getMedicarePayments() {
		return medicarePayments;
	}

	/**
	 * @param medicarePayments
	 *            the medicarePayments to set
	 */

	public void setMedicarePayments(Integer medicarePayments) {
		this.medicarePayments = medicarePayments;
	}

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
	 * @return the clinicPayFeeByAlipay
	 */

	public Double getClinicPayFeeByAlipay() {
		return clinicPayFeeByAlipay;
	}

	/**
	 * @param clinicPayFeeByAlipay
	 *            the clinicPayFeeByAlipay to set
	 */

	public void setClinicPayFeeByAlipay(Double clinicPayFeeByAlipay) {
		this.clinicPayFeeByAlipay = clinicPayFeeByAlipay;
	}

	/**
	 * @return the clinicRefundFeeByAlipay
	 */

	public Double getClinicRefundFeeByAlipay() {
		return clinicRefundFeeByAlipay;
	}

	/**
	 * @param clinicRefundFeeByAlipay
	 *            the clinicRefundFeeByAlipay to set
	 */

	public void setClinicRefundFeeByAlipay(Double clinicRefundFeeByAlipay) {
		this.clinicRefundFeeByAlipay = clinicRefundFeeByAlipay;
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
	 * @return the clinicPayTotalFeeByAlipay
	 */

	public Double getClinicPayTotalFeeByAlipay() {
		return clinicPayTotalFeeByAlipay;
	}

	/**
	 * @param clinicPayTotalFeeByAlipay
	 *            the clinicPayTotalFeeByAlipay to set
	 */

	public void setClinicPayTotalFeeByAlipay(Double clinicPayTotalFeeByAlipay) {
		this.clinicPayTotalFeeByAlipay = clinicPayTotalFeeByAlipay;
	}

	/**
	 * @return the clinicRefundTotalFeeByAlipay
	 */

	public Double getClinicRefundTotalFeeByAlipay() {
		return clinicRefundTotalFeeByAlipay;
	}

	/**
	 * @param clinicRefundTotalFeeByAlipay
	 *            the clinicRefundTotalFeeByAlipay to set
	 */

	public void setClinicRefundTotalFeeByAlipay(Double clinicRefundTotalFeeByAlipay) {
		this.clinicRefundTotalFeeByAlipay = clinicRefundTotalFeeByAlipay;
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
	 * @return the clinicPayFeeByWechat
	 */

	public Double getClinicPayFeeByWechat() {
		return clinicPayFeeByWechat;
	}

	/**
	 * @param clinicPayFeeByWechat
	 *            the clinicPayFeeByWechat to set
	 */

	public void setClinicPayFeeByWechat(Double clinicPayFeeByWechat) {
		this.clinicPayFeeByWechat = clinicPayFeeByWechat;
	}

	/**
	 * @return the clinicRefundFeeByWechat
	 */

	public Double getClinicRefundFeeByWechat() {
		return clinicRefundFeeByWechat;
	}

	/**
	 * @param clinicRefundFeeByWechat
	 *            the clinicRefundFeeByWechat to set
	 */

	public void setClinicRefundFeeByWechat(Double clinicRefundFeeByWechat) {
		this.clinicRefundFeeByWechat = clinicRefundFeeByWechat;
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
	 * @return the clinicPayTotalFeeByWechat
	 */

	public Double getClinicPayTotalFeeByWechat() {
		return clinicPayTotalFeeByWechat;
	}

	/**
	 * @param clinicPayTotalFeeByWechat
	 *            the clinicPayTotalFeeByWechat to set
	 */

	public void setClinicPayTotalFeeByWechat(Double clinicPayTotalFeeByWechat) {
		this.clinicPayTotalFeeByWechat = clinicPayTotalFeeByWechat;
	}

	/**
	 * @return the clinicRefundTotalFeeByWechat
	 */

	public Double getClinicRefundTotalFeeByWechat() {
		return clinicRefundTotalFeeByWechat;
	}

	/**
	 * @param clinicRefundTotalFeeByWechat
	 *            the clinicRefundTotalFeeByWechat to set
	 */

	public void setClinicRefundTotalFeeByWechat(Double clinicRefundTotalFeeByWechat) {
		this.clinicRefundTotalFeeByWechat = clinicRefundTotalFeeByWechat;
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
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	 * @return the clinicPayTotalFee
	 */

	public Double getClinicPayTotalFee() {
		return clinicPayTotalFee;
	}

	/**
	 * @param clinicPayTotalFee
	 *            the clinicPayTotalFee to set
	 */

	public void setClinicPayTotalFee(Double clinicPayTotalFee) {
		this.clinicPayTotalFee = clinicPayTotalFee;
	}

	/**
	 * @return the clinicRefundTotalFee
	 */

	public Double getClinicRefundTotalFee() {
		return clinicRefundTotalFee;
	}

	/**
	 * @param clinicRefundTotalFee
	 *            the clinicRefundTotalFee to set
	 */

	public void setClinicRefundTotalFee(Double clinicRefundTotalFee) {
		this.clinicRefundTotalFee = clinicRefundTotalFee;
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
	 * @return the clinicPayFee
	 */

	public Double getClinicPayFee() {
		return clinicPayFee;
	}

	/**
	 * @param clinicPayFee
	 *            the clinicPayFee to set
	 */

	public void setClinicPayFee(Double clinicPayFee) {
		this.clinicPayFee = clinicPayFee;
	}

	/**
	 * @return the clinicRefundFee
	 */

	public Double getClinicRefundFee() {
		return clinicRefundFee;
	}

	/**
	 * @param clinicRefundFee
	 *            the clinicRefundFee to set
	 */

	public void setClinicRefundFee(Double clinicRefundFee) {
		this.clinicRefundFee = clinicRefundFee;
	}

	public ClinicOrderCount() {
		super();
	}

	public ClinicOrderCount(String date, String hospitalId, String branchId, Integer count, Integer noPayment, Integer payment,
			Integer refund, Double clinicPayFee, Double clinicRefundFee) {
		super();
		this.date = date;
		this.hospitalId = hospitalId;
		this.branchId = branchId;
		this.count = count;
		this.noPayment = noPayment;
		this.payment = payment;
		this.refund = refund;
		this.clinicPayFee = clinicPayFee;
		this.clinicRefundFee = clinicRefundFee;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getNoPayment() {
		return noPayment;
	}

	public void setNoPayment(Integer noPayment) {
		this.noPayment = noPayment;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

	public Integer getRefund() {
		return refund;
	}

	public void setRefund(Integer refund) {
		this.refund = refund;
	}

}
