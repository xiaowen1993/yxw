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
package com.yxw.interfaces.vo.register.onduty;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->当天挂号-->当天挂号退费
 * @Package: com.yxw.interfaces.entity.register.onduty
 * @ClassName: RefundCurReg
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RefundCurReg extends Reserve implements Serializable {

	private static final long serialVersionUID = 7465733413364533420L;
	/**
	 * 当班挂号退费流水号,要求唯一，能标识单独的一笔退费预约挂号订单
	 */
	private String hisRefOrdNum;

	public RefundCurReg() {
		super();
	}

	/**
	 * @param hisRefOrdNum
	 */
	public RefundCurReg(String hisRefOrdNum) {
		super();
		this.hisRefOrdNum = hisRefOrdNum;
	}

	/**
	 * @return the hisRefOrdNum
	 */
	public String getHisRefOrdNum() {
		return hisRefOrdNum;
	}

	/**
	 * @param hisRefOrdNum the hisRefOrdNum to set
	 */
	public void setHisRefOrdNum(String hisRefOrdNum) {
		this.hisRefOrdNum = hisRefOrdNum;
	}

}
