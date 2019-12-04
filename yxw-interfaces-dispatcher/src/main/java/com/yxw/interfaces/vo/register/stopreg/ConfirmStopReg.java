/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.stopreg;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->停诊-->医生停诊订单处理确认信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class ConfirmStopReg extends Reserve implements Serializable {

	private static final long serialVersionUID = 5749176749000396316L;

	/**
	 * 预约挂号退费流水号,要求唯一，能标识单独的一笔退费挂号订单
	 */
	private String hisRefOrdNum;

	public ConfirmStopReg() {
		super();
	}

	/**
	 * @param hisRefOrdNum
	 */

	public ConfirmStopReg(String hisRefOrdNum) {
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
	 * @param hisRefOrdNum
	 *            the hisRefOrdNum to set
	 */

	public void setHisRefOrdNum(String hisRefOrdNum) {
		this.hisRefOrdNum = hisRefOrdNum;
	}

}
