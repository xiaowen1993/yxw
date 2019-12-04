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
package com.yxw.interfaces.vo.mycenter;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 个人中心-->首诊患者建档
 * @Package: com.yxw.interfaces.entity.microsite
 * @ClassName: Card
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Card extends Reserve implements Serializable {

	private static final long serialVersionUID = 7958353418047249585L;
	/**
	 * 患者唯一标识
	 */
	private String patId;
	/**
	 * 诊疗卡类型,见CardType
	 * @see com.yxw.interfaces.constants.CardType
	 */
	private String patCardType;
	/**
	 * 诊疗卡号码
	 */
	private String patCardNo;

	public Card() {
		super();
	}

	/**
	 * @param patId
	 * @param patCardType
	 * @param patCardNo
	 */
	public Card(String patId, String patCardType, String patCardNo) {
		super();
		this.patId = patId;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
	}

	/**
	 * @return the patId
	 */
	public String getPatId() {
		return patId;
	}

	/**
	 * @param patId the patId to set
	 */
	public void setPatId(String patId) {
		this.patId = patId;
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

}
