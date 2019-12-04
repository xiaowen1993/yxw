package com.yxw.stats.entity.platform;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

public class OrderCount extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1383625226774097801L;

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

	private Integer countByWechat = 0;

	private Integer countByAlipay = 0;

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
	 * 预约总数量
	 */
	private Integer reservationCount = 0;

	private Integer reservationCountByWechat = 0;

	private Integer reservationCountByAlipay = 0;

	/**
	 * 当班总数量
	 */
	private Integer dutyCount = 0;

	private Integer dutyCountByWechat = 0;

	private Integer dutyCountByAlipay = 0;

	/**
	 * 预约未支付数量
	 */
	private Integer reservationNoPayment = 0;

	private Integer reservationNoPaymentByWechat = 0;

	private Integer reservationNoPaymentByAlipay = 0;

	/**
	 * 预约已支付数量
	 */
	private Integer reservationPayment = 0;

	private Integer reservationPaymentByWechat = 0;

	private Integer reservationPaymentByAlipay = 0;

	/**
	 * 预约已退费数量
	 */
	private Integer reservationRefund = 0;

	private Integer reservationRefundByWechat = 0;

	private Integer reservationRefundByAlipay = 0;

	/**
	 * 当班未支付数量
	 */
	private Integer dutyNoPayment = 0;

	private Integer dutyNoPaymentByWechat = 0;

	private Integer dutyNoPaymentByAlipay = 0;

	/**
	 * 当班已支付数量
	 */
	private Integer dutyPayment = 0;

	private Integer dutyPaymentByWechat = 0;

	private Integer dutyPaymentByAlipay = 0;

	/**
	 * 当班已退费数量
	 */
	private Integer dutyRefund = 0;

	private Integer dutyRefundByWechat = 0;

	private Integer dutyRefundByAlipay = 0;

	private Integer regType;

	/**
	 * 挂号支付总金额
	 */
	private Double regPayFee = 0.0;

	private Double regPayFeeByWechat = 0.0;

	private Double regPayFeeByAlipay = 0.0;

	/**
	 * 支付总笔数
	 */
	private Integer payCount = 0;

	private Integer payCountByWechat = 0;

	private Integer payCountByAlipay = 0;

	/**
	 * 退费总笔数
	 */
	private Integer refundCount = 0;

	private Integer refundCountByWechat = 0;

	private Integer refundCountByAlipay = 0;

	/**
	 * @return the pay_count
	 */
	/**
	 * 挂号支付总金额
	 */
	private Double regPayTotalFee = 0.0;

	private Double regPayTotalFeeByWechat = 0.0;

	private Double regPayTotalFeeByAlipay = 0.0;

	/**
	 * 挂号退费总金额
	 */
	private Double regRefundTotalFee = 0.0;

	private Double regRefundTotalFeeByWechat = 0.0;

	private Double regRefundTotalFeeByAlipay = 0.0;

	/**
	 * 渠道
	 */
	private Integer bizMode;

	/**
	 * 挂号退费总金额
	 */
	private Double regRefundFee = 0.0;

	private Double regRefundFeeByWechat = 0.0;

	private Double regRefundFeeByAlipay = 0.0;

	/**
	 * 门诊已支付订单数
	 */
	private Integer clinicPayment = 0;

	private Integer clinicPaymentByWechat = 0;

	private Integer clinicPaymentByAlipay = 0;

	/**
	 * 门诊已退费订单数
	 */
	private Integer clinicRefund = 0;

	private Integer clinicRefundByWechat = 0;

	private Integer clinicRefundByAlipay = 0;

	/**
	 * 门诊缴费支付总金额
	 */
	private Double clinicPayFee = 0.0;

	private Double clinicPayFeeByWechat = 0.0;

	private Double clinicPayFeeByAlipay = 0.0;

	/**
	 * 门诊缴费完全退费总金额
	 */
	private Double clinicRefundFee = 0.0;

	private Double clinicRefundFeeByWechat = 0.0;

	private Double clinicRefundFeeByAlipay = 0.0;

	/**
	 * 门诊缴费部分退费总金额
	 */
	private Double partRefund = 0.0;

	private Double partRefundByWechat = 0.0;

	private Double partRefundByAlipay = 0.0;

	/**
	 * 住院押金已支付订单数
	 */
	private Integer depositPayment = 0;

	private Integer depositPaymentByWechat = 0;

	private Integer depositPaymentByAlipay = 0;

	/**
	 * 住院押金已退费订单数
	 */
	private Integer depositRefund = 0;

	private Integer depositRefundByWechat = 0;

	private Integer depositRefundByAlipay = 0;

	/**
	 * 住院押金缴费支付总金额
	 */
	private Double depositPayFee = 0.0;

	private Double depositPayFeeByWechat = 0.0;

	private Double depositPayFeeByAlipay = 0.0;

	/**
	 * 住院押金缴费完全退费总金额
	 */
	private Double depositRefundFee = 0.0;

	private Double depositRefundFeeByWechat = 0.0;

	private Double depositRefundFeeByAlipay = 0.0;

	/**
	 * 是否扫码
	 */
	private Integer payMode;

	/**
	 * 是否医保、自费
	 */
	private Integer medicarePayments;

	/**
	 * @return the clinicPayFee
	 */

	/**
	 * @return the reg_pay_total_fee
	 */

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
	 * @return the depositPayment
	 */

	public Integer getDepositPayment() {
		return depositPayment;
	}

	/**
	 * @param depositPayment
	 *            the depositPayment to set
	 */

	public void setDepositPayment(Integer depositPayment) {
		this.depositPayment = depositPayment;
	}

	/**
	 * @return the depositPaymentByWechat
	 */

	public Integer getDepositPaymentByWechat() {
		return depositPaymentByWechat;
	}

	/**
	 * @param depositPaymentByWechat
	 *            the depositPaymentByWechat to set
	 */

	public void setDepositPaymentByWechat(Integer depositPaymentByWechat) {
		this.depositPaymentByWechat = depositPaymentByWechat;
	}

	/**
	 * @return the depositPaymentByAlipay
	 */

	public Integer getDepositPaymentByAlipay() {
		return depositPaymentByAlipay;
	}

	/**
	 * @param depositPaymentByAlipay
	 *            the depositPaymentByAlipay to set
	 */

	public void setDepositPaymentByAlipay(Integer depositPaymentByAlipay) {
		this.depositPaymentByAlipay = depositPaymentByAlipay;
	}

	/**
	 * @return the depositRefund
	 */

	public Integer getDepositRefund() {
		return depositRefund;
	}

	/**
	 * @param depositRefund
	 *            the depositRefund to set
	 */

	public void setDepositRefund(Integer depositRefund) {
		this.depositRefund = depositRefund;
	}

	/**
	 * @return the depositRefundByWechat
	 */

	public Integer getDepositRefundByWechat() {
		return depositRefundByWechat;
	}

	/**
	 * @param depositRefundByWechat
	 *            the depositRefundByWechat to set
	 */

	public void setDepositRefundByWechat(Integer depositRefundByWechat) {
		this.depositRefundByWechat = depositRefundByWechat;
	}

	/**
	 * @return the depositRefundByAlipay
	 */

	public Integer getDepositRefundByAlipay() {
		return depositRefundByAlipay;
	}

	/**
	 * @param depositRefundByAlipay
	 *            the depositRefundByAlipay to set
	 */

	public void setDepositRefundByAlipay(Integer depositRefundByAlipay) {
		this.depositRefundByAlipay = depositRefundByAlipay;
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
	 * @return the regRefundFeeByWechat
	 */

	public Double getRegRefundFeeByWechat() {
		return regRefundFeeByWechat;
	}

	/**
	 * @param regRefundFeeByWechat
	 *            the regRefundFeeByWechat to set
	 */

	public void setRegRefundFeeByWechat(Double regRefundFeeByWechat) {
		this.regRefundFeeByWechat = regRefundFeeByWechat;
	}

	/**
	 * @return the regRefundFeeByAlipay
	 */

	public Double getRegRefundFeeByAlipay() {
		return regRefundFeeByAlipay;
	}

	/**
	 * @param regRefundFeeByAlipay
	 *            the regRefundFeeByAlipay to set
	 */

	public void setRegRefundFeeByAlipay(Double regRefundFeeByAlipay) {
		this.regRefundFeeByAlipay = regRefundFeeByAlipay;
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
	 * @return the regPayTotalFee
	 */

	public Double getRegPayTotalFee() {
		return regPayTotalFee;
	}

	/**
	 * @param regPayTotalFee
	 *            the regPayTotalFee to set
	 */

	public void setRegPayTotalFee(Double regPayTotalFee) {
		this.regPayTotalFee = regPayTotalFee;
	}

	/**
	 * @return the regRefundTotalFee
	 */

	public Double getRegRefundTotalFee() {
		return regRefundTotalFee;
	}

	/**
	 * @param regRefundTotalFee
	 *            the regRefundTotalFee to set
	 */

	public void setRegRefundTotalFee(Double regRefundTotalFee) {
		this.regRefundTotalFee = regRefundTotalFee;
	}

	/**
	 * @return the regPayFee
	 */

	public Double getRegPayFee() {
		return regPayFee;
	}

	/**
	 * @param regPayFee
	 *            the regPayFee to set
	 */

	public void setRegPayFee(Double regPayFee) {
		this.regPayFee = regPayFee;
	}

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * @return the regRefundFee
	 */

	public Double getRegRefundFee() {
		return regRefundFee;
	}

	/**
	 * @param regRefundFee
	 *            the regRefundFee to set
	 */

	public void setRegRefundFee(Double regRefundFee) {
		this.regRefundFee = regRefundFee;
	}

	public OrderCount() {
		super();
	}

	public OrderCount(String date, String hospitalId, String branchId, Integer count, Integer reservationCount, Integer dutyCount,
			Integer reservationNoPayment, Integer reservationPayment, Integer reservationRefund, Integer dutyNoPayment,
			Integer dutyPayment, Integer dutyRefund, Double regPayFee, Double regRefundFee) {
		super();
		this.date = date;
		this.hospitalId = hospitalId;
		this.branchId = branchId;
		this.count = count;
		this.reservationCount = reservationCount;
		this.dutyCount = dutyCount;
		this.reservationNoPayment = reservationNoPayment;
		this.reservationPayment = reservationPayment;
		this.reservationRefund = reservationRefund;
		this.dutyNoPayment = dutyNoPayment;
		this.dutyPayment = dutyPayment;
		this.dutyRefund = dutyRefund;
		this.regPayFee = regPayFee;
		this.regRefundFee = regRefundFee;
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

	public Integer getReservationCount() {
		return reservationCount;
	}

	public void setReservationCount(Integer reservationCount) {
		this.reservationCount = reservationCount;
	}

	public Integer getDutyCount() {
		return dutyCount;
	}

	public void setDutyCount(Integer dutyCount) {
		this.dutyCount = dutyCount;
	}

	public Integer getReservationNoPayment() {
		return reservationNoPayment;
	}

	public void setReservationNoPayment(Integer reservationNoPayment) {
		this.reservationNoPayment = reservationNoPayment;
	}

	public Integer getReservationPayment() {
		return reservationPayment;
	}

	public void setReservationPayment(Integer reservationPayment) {
		this.reservationPayment = reservationPayment;
	}

	public Integer getReservationRefund() {
		return reservationRefund;
	}

	public void setReservationRefund(Integer reservationRefund) {
		this.reservationRefund = reservationRefund;
	}

	public Integer getDutyNoPayment() {
		return dutyNoPayment;
	}

	public void setDutyNoPayment(Integer dutyNoPayment) {
		this.dutyNoPayment = dutyNoPayment;
	}

	public Integer getDutyPayment() {
		return dutyPayment;
	}

	public void setDutyPayment(Integer dutyPayment) {
		this.dutyPayment = dutyPayment;
	}

	public Integer getDutyRefund() {
		return dutyRefund;
	}

	public void setDutyRefund(Integer dutyRefund) {
		this.dutyRefund = dutyRefund;
	}

	public Integer getRegType() {
		return regType;
	}

	public void setRegType(Integer regType) {
		this.regType = regType;
	}

	public Integer getClinicPayment() {
		return clinicPayment;
	}

	public void setClinicPayment(Integer clinicPayment) {
		this.clinicPayment = clinicPayment;
	}

	public Integer getClinicRefund() {
		return clinicRefund;
	}

	public void setClinicRefund(Integer clinicRefund) {
		this.clinicRefund = clinicRefund;
	}

	/**
	 * @return the reservationCountByWechat
	 */

	public Integer getReservationCountByWechat() {
		return reservationCountByWechat;
	}

	/**
	 * @param reservationCountByWechat
	 *            the reservationCountByWechat to set
	 */

	public void setReservationCountByWechat(Integer reservationCountByWechat) {
		this.reservationCountByWechat = reservationCountByWechat;
	}

	/**
	 * @return the reservationCountByAlipay
	 */

	public Integer getReservationCountByAlipay() {
		return reservationCountByAlipay;
	}

	/**
	 * @param reservationCountByAlipay
	 *            the reservationCountByAlipay to set
	 */

	public void setReservationCountByAlipay(Integer reservationCountByAlipay) {
		this.reservationCountByAlipay = reservationCountByAlipay;
	}

	/**
	 * @return the dutyCountByWechat
	 */

	public Integer getDutyCountByWechat() {
		return dutyCountByWechat;
	}

	/**
	 * @param dutyCountByWechat
	 *            the dutyCountByWechat to set
	 */

	public void setDutyCountByWechat(Integer dutyCountByWechat) {
		this.dutyCountByWechat = dutyCountByWechat;
	}

	/**
	 * @return the dutyCountByAlipay
	 */

	public Integer getDutyCountByAlipay() {
		return dutyCountByAlipay;
	}

	/**
	 * @param dutyCountByAlipay
	 *            the dutyCountByAlipay to set
	 */

	public void setDutyCountByAlipay(Integer dutyCountByAlipay) {
		this.dutyCountByAlipay = dutyCountByAlipay;
	}

	/**
	 * @return the reservationNoPaymentByWechat
	 */

	public Integer getReservationNoPaymentByWechat() {
		return reservationNoPaymentByWechat;
	}

	/**
	 * @param reservationNoPaymentByWechat
	 *            the reservationNoPaymentByWechat to set
	 */

	public void setReservationNoPaymentByWechat(Integer reservationNoPaymentByWechat) {
		this.reservationNoPaymentByWechat = reservationNoPaymentByWechat;
	}

	/**
	 * @return the reservationNoPaymentByAlipay
	 */

	public Integer getReservationNoPaymentByAlipay() {
		return reservationNoPaymentByAlipay;
	}

	/**
	 * @param reservationNoPaymentByAlipay
	 *            the reservationNoPaymentByAlipay to set
	 */

	public void setReservationNoPaymentByAlipay(Integer reservationNoPaymentByAlipay) {
		this.reservationNoPaymentByAlipay = reservationNoPaymentByAlipay;
	}

	/**
	 * @return the reservationPaymentByWehchat
	 */

	public Integer getReservationPaymentByWechat() {
		return reservationPaymentByWechat;
	}

	/**
	 * @param reservationPaymentByWehchat
	 *            the reservationPaymentByWehchat to set
	 */

	public void setReservationPaymentByWechat(Integer reservationPaymentByWechat) {
		this.reservationPaymentByWechat = reservationPaymentByWechat;
	}

	/**
	 * @return the reservationPaymentByAlipay
	 */

	public Integer getReservationPaymentByAlipay() {
		return reservationPaymentByAlipay;
	}

	/**
	 * @param reservationPaymentByAlipay
	 *            the reservationPaymentByAlipay to set
	 */

	public void setReservationPaymentByAlipay(Integer reservationPaymentByAlipay) {
		this.reservationPaymentByAlipay = reservationPaymentByAlipay;
	}

	/**
	 * @return the reservationRefundByWechat
	 */

	public Integer getReservationRefundByWechat() {
		return reservationRefundByWechat;
	}

	/**
	 * @param reservationRefundByWechat
	 *            the reservationRefundByWechat to set
	 */

	public void setReservationRefundByWechat(Integer reservationRefundByWechat) {
		this.reservationRefundByWechat = reservationRefundByWechat;
	}

	/**
	 * @return the reservationRefundByAlipay
	 */

	public Integer getReservationRefundByAlipay() {
		return reservationRefundByAlipay;
	}

	/**
	 * @param reservationRefundByAlipay
	 *            the reservationRefundByAlipay to set
	 */

	public void setReservationRefundByAlipay(Integer reservationRefundByAlipay) {
		this.reservationRefundByAlipay = reservationRefundByAlipay;
	}

	/**
	 * @return the dutyNoPaymentByWechat
	 */

	public Integer getDutyNoPaymentByWechat() {
		return dutyNoPaymentByWechat;
	}

	/**
	 * @param dutyNoPaymentByWechat
	 *            the dutyNoPaymentByWechat to set
	 */

	public void setDutyNoPaymentByWechat(Integer dutyNoPaymentByWechat) {
		this.dutyNoPaymentByWechat = dutyNoPaymentByWechat;
	}

	/**
	 * @return the dutyNoPaymentByAlipay
	 */

	public Integer getDutyNoPaymentByAlipay() {
		return dutyNoPaymentByAlipay;
	}

	/**
	 * @param dutyNoPaymentByAlipay
	 *            the dutyNoPaymentByAlipay to set
	 */

	public void setDutyNoPaymentByAlipay(Integer dutyNoPaymentByAlipay) {
		this.dutyNoPaymentByAlipay = dutyNoPaymentByAlipay;
	}

	/**
	 * @return the dutyPaymentByWechat
	 */

	public Integer getDutyPaymentByWechat() {
		return dutyPaymentByWechat;
	}

	/**
	 * @param dutyPaymentByWechat
	 *            the dutyPaymentByWechat to set
	 */

	public void setDutyPaymentByWechat(Integer dutyPaymentByWechat) {
		this.dutyPaymentByWechat = dutyPaymentByWechat;
	}

	/**
	 * @return the dutyPaymentByAlipay
	 */

	public Integer getDutyPaymentByAlipay() {
		return dutyPaymentByAlipay;
	}

	/**
	 * @param dutyPaymentByAlipay
	 *            the dutyPaymentByAlipay to set
	 */

	public void setDutyPaymentByAlipay(Integer dutyPaymentByAlipay) {
		this.dutyPaymentByAlipay = dutyPaymentByAlipay;
	}

	/**
	 * @return the dutyRefundByWechat
	 */

	public Integer getDutyRefundByWechat() {
		return dutyRefundByWechat;
	}

	/**
	 * @param dutyRefundByWechat
	 *            the dutyRefundByWechat to set
	 */

	public void setDutyRefundByWechat(Integer dutyRefundByWechat) {
		this.dutyRefundByWechat = dutyRefundByWechat;
	}

	/**
	 * @return the dutyRefundByAlipay
	 */

	public Integer getDutyRefundByAlipay() {
		return dutyRefundByAlipay;
	}

	/**
	 * @param dutyRefundByAlipay
	 *            the dutyRefundByAlipay to set
	 */

	public void setDutyRefundByAlipay(Integer dutyRefundByAlipay) {
		this.dutyRefundByAlipay = dutyRefundByAlipay;
	}

	/**
	 * @return the clinicPaymentByWechat
	 */

	public Integer getClinicPaymentByWechat() {
		return clinicPaymentByWechat;
	}

	/**
	 * @param clinicPaymentByWechat
	 *            the clinicPaymentByWechat to set
	 */

	public void setClinicPaymentByWechat(Integer clinicPaymentByWechat) {
		this.clinicPaymentByWechat = clinicPaymentByWechat;
	}

	/**
	 * @return the clinicPaymentByAlipay
	 */

	public Integer getClinicPaymentByAlipay() {
		return clinicPaymentByAlipay;
	}

	/**
	 * @param clinicPaymentByAlipay
	 *            the clinicPaymentByAlipay to set
	 */

	public void setClinicPaymentByAlipay(Integer clinicPaymentByAlipay) {
		this.clinicPaymentByAlipay = clinicPaymentByAlipay;
	}

	/**
	 * @return the clinicRefundByWechat
	 */

	public Integer getClinicRefundByWechat() {
		return clinicRefundByWechat;
	}

	/**
	 * @param clinicRefundByWechat
	 *            the clinicRefundByWechat to set
	 */

	public void setClinicRefundByWechat(Integer clinicRefundByWechat) {
		this.clinicRefundByWechat = clinicRefundByWechat;
	}

	/**
	 * @return the clinicRefundByAlipay
	 */

	public Integer getClinicRefundByAlipay() {
		return clinicRefundByAlipay;
	}

	/**
	 * @param clinicRefundByAlipay
	 *            the clinicRefundByAlipay to set
	 */

	public void setClinicRefundByAlipay(Integer clinicRefundByAlipay) {
		this.clinicRefundByAlipay = clinicRefundByAlipay;
	}

	/**
	 * @return the regPayFeeByWechat
	 */

	public Double getRegPayFeeByWechat() {
		return regPayFeeByWechat;
	}

	/**
	 * @param regPayFeeByWechat
	 *            the regPayFeeByWechat to set
	 */

	public void setRegPayFeeByWechat(Double regPayFeeByWechat) {
		this.regPayFeeByWechat = regPayFeeByWechat;
	}

	/**
	 * @return the regPayFeeByAlipay
	 */

	public Double getRegPayFeeByAlipay() {
		return regPayFeeByAlipay;
	}

	/**
	 * @param regPayFeeByAlipay
	 *            the regPayFeeByAlipay to set
	 */

	public void setRegPayFeeByAlipay(Double regPayFeeByAlipay) {
		this.regPayFeeByAlipay = regPayFeeByAlipay;
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
	 * @return the regPayTotalFeeByWechat
	 */

	public Double getRegPayTotalFeeByWechat() {
		return regPayTotalFeeByWechat;
	}

	/**
	 * @param regPayTotalFeeByWechat
	 *            the regPayTotalFeeByWechat to set
	 */

	public void setRegPayTotalFeeByWechat(Double regPayTotalFeeByWechat) {
		this.regPayTotalFeeByWechat = regPayTotalFeeByWechat;
	}

	/**
	 * @return the regPayTotalFeeByAlipay
	 */

	public Double getRegPayTotalFeeByAlipay() {
		return regPayTotalFeeByAlipay;
	}

	/**
	 * @param regPayTotalFeeByAlipay
	 *            the regPayTotalFeeByAlipay to set
	 */

	public void setRegPayTotalFeeByAlipay(Double regPayTotalFeeByAlipay) {
		this.regPayTotalFeeByAlipay = regPayTotalFeeByAlipay;
	}

	/**
	 * @return the regRefundTotalFeeByWechat
	 */

	public Double getRegRefundTotalFeeByWechat() {
		return regRefundTotalFeeByWechat;
	}

	/**
	 * @param regRefundTotalFeeByWechat
	 *            the regRefundTotalFeeByWechat to set
	 */

	public void setRegRefundTotalFeeByWechat(Double regRefundTotalFeeByWechat) {
		this.regRefundTotalFeeByWechat = regRefundTotalFeeByWechat;
	}

	/**
	 * @return the regRefundTotalFeeByAlipay
	 */

	public Double getRegRefundTotalFeeByAlipay() {
		return regRefundTotalFeeByAlipay;
	}

	/**
	 * @param regRefundTotalFeeByAlipay
	 *            the regRefundTotalFeeByAlipay to set
	 */

	public void setRegRefundTotalFeeByAlipay(Double regRefundTotalFeeByAlipay) {
		this.regRefundTotalFeeByAlipay = regRefundTotalFeeByAlipay;
	}

	/**
	 * @return the serialversionuid
	 */

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
