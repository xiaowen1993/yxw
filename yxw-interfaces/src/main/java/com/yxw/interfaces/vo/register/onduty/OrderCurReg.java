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

package com.yxw.interfaces.vo.register.onduty;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->当天挂号-->当天挂号信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class OrderCurReg extends Reserve implements Serializable {

	private static final long serialVersionUID = 8265773074911116233L;
	/**
	 * 医院预约流水号,要求唯一，能标识单独的一笔预约挂号订单
	 */
	private String hisOrdNum;
	/**
	 * 挂号费,单位：分,根据患者身份，返回真实的挂号费用
	 */
	private String regFee;

	/**
	 * 诊疗费, 单位：分,根据患者身份，返回真实的挂号费用
	 */
	private String treatFee;
	/**
	 * 费用说明
	 */
	private String desc;
	
	/**
	 * 个帐金额
	 * */
	private String personalAccountFee;
	/**
	 * 统筹金额
	 * */
	private String overallPlanFee;

	public OrderCurReg() {
		super();
	}

	/**
	 * @param hisOrdNum
	 * @param regFee
	 * @param treatFee
	 * @param desc
	 */

	public OrderCurReg(String hisOrdNum, String regFee, String treatFee, String desc) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.regFee = regFee;
		this.treatFee = treatFee;
		this.desc = desc;
	}

	/**
	 * @return the hisOrdNum
	 */

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	/**
	 * @param hisOrdNum
	 *            the hisOrdNum to set
	 */

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
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

	public String getPersonalAccountFee() {
		return personalAccountFee;
	}

	public void setPersonalAccountFee(String personalAccountFee) {
		this.personalAccountFee = personalAccountFee;
	}

	public String getOverallPlanFee() {
		return overallPlanFee;
	}

	public void setOverallPlanFee(String overallPlanFee) {
		this.overallPlanFee = overallPlanFee;
	}
}
