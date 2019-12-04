/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月20日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->患者类型查询请求参数
 * @Package: com.yxw.interfaces.vo.register
 * @ClassName: PatientTypeRequest
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月20日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PatientTypeRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 1218941252260594956L;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号码
	 */
	private String patCardNo;
	
	public PatientTypeRequest() {
		super();
	}

	public PatientTypeRequest(String patCardType, String patCardNo) {
		super();
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
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
