package com.yxw.commons.entity.platform.payrefund;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class AlipayRefund extends Refund implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6106096080251536196L;

	/**
	 * 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
	 */
	private String requestNo;

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		if (StringUtils.isBlank(requestNo)) {
			this.requestNo = String.valueOf(System.nanoTime());
		} else {
			this.requestNo = requestNo;
		}
	}

}
