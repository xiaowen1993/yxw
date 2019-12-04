package com.yxw.interfaces.vo.charge;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 自助机-->充值记录查询
 * @Package: com.yxw.interfaces.entity.charge
 * @ClassName: ChargeRecordRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ChargeRecordRequest extends Reserve implements Serializable{

	private static final long serialVersionUID = -6884557288481546502L;
	/**
	 * 第三方支付订单号
	 */
	private String agtOrdNum;
	
	public ChargeRecordRequest(){}
	
	

	public ChargeRecordRequest(String agtOrdNum) {
		super();
		this.agtOrdNum = agtOrdNum;
	}



	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}
	
}
