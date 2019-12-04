/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->门诊待缴费记录明细查询请求参数
 * @Package: com.yxw.interfaces.entity.clinicpay
 * @ClassName: MZFeeDetailRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MZFeeDetailRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 4098954637795923636L;
	/**
	 * 医院代码,医院没有分院，则值为空字符串；医院有分院，则值不允许为空字符串
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
	 * 缴费项唯一标识,门诊流水号，就诊登记号等，并非处方号,用来唯一标识一笔缴费(包含1..n个处方或检查单)
	 */
	private String mzFeeId;
	/**
	 * 渠道类型,见PayPlatformType
	 * @see com.yxw.interfaces.constants.PayPlatformType
	 */
	private String channelType;

	public MZFeeDetailRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param patCardType
	 * @param patCardNo
	 * @param mzFeeId
	 * @param channelType
	 */
	public MZFeeDetailRequest(String branchCode, String patCardType, String patCardNo, String mzFeeId, String channelType) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.mzFeeId = mzFeeId;
		this.channelType = channelType;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the patCardType
	 */
	public String getPatCardType() {
		return patCardType;
	}

	/**
	 * @param patCardType the patCardType to set
	 */
	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	/**
	 * @return the patCardNo
	 */
	public String getPatCardNo() {
		return patCardNo;
	}

	/**
	 * @param patCardNo the patCardNo to set
	 */
	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	/**
	 * @return the mzFeeId
	 */
	public String getMzFeeId() {
		return mzFeeId;
	}

	/**
	 * @param mzFeeId the mzFeeId to set
	 */
	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	/**
	 * @return the channelType
	 */
	public String getChannelType() {
		return channelType;
	}

	/**
	 * @param channelType the channelType to set
	 */
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

}
