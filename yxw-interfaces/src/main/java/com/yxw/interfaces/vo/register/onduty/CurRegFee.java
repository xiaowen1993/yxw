/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.onduty;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->当天挂号-->当天挂号费用减免查询信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class CurRegFee extends Reserve implements Serializable {

	private static final long serialVersionUID = 112284286791344301L;
	/**
	 * 挂号费,单位：分
	 */
	private String regFee;
	/**
	 * 诊疗费,单位：分
	 */
	private String treatFee;
	/**
	 * 费用说明
	 */
	private String desc;

	public CurRegFee() {
		super();
	}

	/**
	 * @param regFee
	 * @param treatFee
	 * @param desc
	 */

	public CurRegFee(String regFee, String treatFee, String desc) {
		super();
		this.regFee = regFee;
		this.treatFee = treatFee;
		this.desc = desc;
	}

	/**
	 * @return the regFee
	 */

	public String getRegFee() {
		return regFee;
	}

	/**
	 * @param regFee
	 *            the regFee to set
	 */

	public void setRegFee(String regFee) {
		this.regFee = regFee;
	}

	/**
	 * @return the treatFee
	 */

	public String getTreatFee() {
		return treatFee;
	}

	/**
	 * @param treatFee
	 *            the treatFee to set
	 */

	public void setTreatFee(String treatFee) {
		this.treatFee = treatFee;
	}

	/**
	 * @return the desc
	 */

	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc
	 *            the desc to set
	 */

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
