package com.yxw.commons.entity.platform.payrefund;

/**
 * 银联支付异步回调
 * 
 * @author YangXuewen
 *
 */
public class UnionpayAsynResponse extends PayAsynResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3473288997728768093L;

	/**
	 * 清算日期（MMdd）
	 * settleDate
	 * 
	 * 为银联和入网机构间的交易结算日期。
	 * 一般前一日23点至当天23点为一个清算日。
	 * 也就是23点前的交易，当天23点之后开始结算，23点之后的交易，要第二天23点之后才会结算。
	 * 测试环境为测试需要，23:30左右日切，所以23:30到23:30为一个清算日，当天23:30之后为下个清算日。
	 */
	private String settleDate;

	/**
	 * 系统跟踪号
	 * traceNo
	 * 
	 * 收单机构对账时使用，该域由银联系统产生
	 */
	private String traceNo;

	/**
	 * 交易传输时间（MMddHHmmss）
	 * traceTime
	 * 
	 * 收单机构对账时使用，该域由银联系统产生
	 */
	private String traceTime;

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getTraceTime() {
		return traceTime;
	}

	public void setTraceTime(String traceTime) {
		this.traceTime = traceTime;
	}

	public UnionpayAsynResponse() {
		super();
	}

	public UnionpayAsynResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
