package com.yxw.commons.entity.platform.payrefund;

/**
 * 
 * @author YangXuewen
 *
 */
public class UnionpayOrderQueryResponse extends OrderQueryResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6552602267749126809L;

	//super.tradeState
	//origRespCode
	//00 交易成功

	/**
	 * 系统跟踪号
	 * 收单机构对账时使用，该域由银联系统产生
	 */
	private String traceNo;

	/**
	 * 被查询交易的查询流水号
	 * 交易流水号|退费流水号
	 */
	//private String queryId;

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	//	public String getQueryId() {
	//		return queryId;
	//	}
	//
	//	public void setQueryId(String queryId) {
	//		this.queryId = queryId;
	//	}

	public UnionpayOrderQueryResponse() {
		super();
	}

	public UnionpayOrderQueryResponse(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}

}
