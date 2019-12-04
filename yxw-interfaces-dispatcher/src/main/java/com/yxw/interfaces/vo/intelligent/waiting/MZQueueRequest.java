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
package com.yxw.interfaces.vo.intelligent.waiting;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 智能查询-->候诊排队查询-->门诊候诊信息查询请求参数封装
 * @Package: com.yxw.interfaces.entity.intelligent.waiting
 * @ClassName: MZQueueRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MZQueueRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 6972979143838997140L;

	/**
	 * 医院代码,医院没有分院，则值为空字符串,医院有分院，则值不允许为空字符串
	 */
	private String branchCode;
	
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	
	/**
	 * 诊疗卡号码
	 */
	private String patCardNo;
	
	
	public MZQueueRequest() {
		super();
	}

	public MZQueueRequest(String branchCode, String patCardType, String patCardNo) {
		super();
		this.branchCode = branchCode;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
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
}
