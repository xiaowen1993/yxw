package com.yxw.solr.vo.rebuild;

import java.io.Serializable;

public class RebuildResponse implements Serializable {

	private static final long serialVersionUID = -1140484795815160241L;

	private Integer resultCode;
	
	private String resultMessage;

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
