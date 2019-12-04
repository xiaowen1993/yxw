/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.dto.outside;

import java.io.Serializable;

/**
 * @Package: com.yxw.mobileapp.invoke.dto
 * @ClassName: RequestReturnGeneralResult
 * @Statement: <p>窗口退费返回擦参数</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月12日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ReturnGeneralResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187702748215168402L;

	/**
	 * 平台退款订单号
	 */
	private String psNewOrdNum;

	/**
	 * 第三方退款订单号
	 */
	private String agtRefOrdNum;

	public ReturnGeneralResult() {
		super();
	}

	public ReturnGeneralResult(String psNewOrdNum, String agtRefOrdNum) {
		super();
		this.psNewOrdNum = psNewOrdNum;
		this.agtRefOrdNum = agtRefOrdNum;
	}

	public String getPsNewOrdNum() {
		return psNewOrdNum;
	}

	public void setPsNewOrdNum(String psNewOrdNum) {
		this.psNewOrdNum = psNewOrdNum;
	}

	public String getAgtRefOrdNum() {
		return agtRefOrdNum;
	}

	public void setAgtRefOrdNum(String agtRefOrdNum) {
		this.agtRefOrdNum = agtRefOrdNum;
	}

}
