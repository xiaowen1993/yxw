package com.yxw.solr.vo.medicalcard;

import com.yxw.solr.constants.PlatformConstant;
import com.yxw.solr.vo.BaseRequest;

public class CardInfoRequest extends BaseRequest {

	private static final long serialVersionUID = -3172899179127234800L;
	
	/**
	 * 患者姓名
	 */
	private String patientName;
	
	/**
	 * 卡号
	 */
	private String cardNo;
	
	/**
	 * 手机号码
	 */
	private String mobileNo;
	
	/**
	 * 身份证号码
	 */
	private String idNo;
	
	/**
	 * 绑卡状态：-1全部，0解绑，1绑定
	 */
	private Integer state = -1;
	
	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getCoreName() {
		return platform != -1 ? PlatformConstant.platformCardMap.get(platform) : "";
	}
}
