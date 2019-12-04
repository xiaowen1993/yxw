/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年12月30日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->报到取号请求参数
 * 
 * @author 范建明
 * @version 1.0
 * @since 2015年12月30日
 */
public class TakeNoRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 4935024796884559235L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院不存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号
	 */
	private String patCardNo;
	/**
	 * 预约挂号流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	
	private String patName;
	
	private String idCardNo;
	
	private String channelType;
	
	private String receiptNum;
	
	public TakeNoRequest() {
		super();
	}

	public TakeNoRequest(String branchCode, String patCardType,
			String patCardNo, String hisOrdNum, String patName ,String idCardNo,
			String channelType, String receiptNum) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.hisOrdNum = hisOrdNum;
		this.patName = patName;
		this.idCardNo = idCardNo;
		this.channelType = channelType;
		this.receiptNum = receiptNum;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getPatCardType() {
		return patCardType;
	}

	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	public String getPatCardNo() {
		return patCardNo;
	}

	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getPatName() {
		return patName;
	}

	public void setPatName(String patName) {
		this.patName = patName;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getReceiptNum() {
		return receiptNum;
	}

	public void setReceiptNum(String receiptNum) {
		this.receiptNum = receiptNum;
	}
	
}
