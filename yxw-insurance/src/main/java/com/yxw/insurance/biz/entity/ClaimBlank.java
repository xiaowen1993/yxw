package com.yxw.insurance.biz.entity;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * 银行
 * @author Administrator
 *
 */
public class ClaimBlank extends BaseEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1700494341217454049L;

	
	private int type;
	
	private String bankCode;
	
	private String bankName;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
}
