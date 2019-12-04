/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月15日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->门诊待缴费记录明细(含代煎配送及支付限制)
 * @Package: com.yxw.interfaces.vo.clinicpay
 * @ClassName: MZFeeDetail_FDP
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月15日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MZFeeDetail_FDP extends Reserve implements Serializable {

	private static final long serialVersionUID = 3071565155608622896L;
	/**
	 * 是否能代煎
	 * @see com.yxw.interfaces.constants.FriedType
	 */
	private String canFried;
	/**
	 * 是否能配送
	 * @see com.yxw.interfaces.constants.DeliveryType
	 */
	private String canDeliver;
	/**
	 * 是否代煎
	 * @see com.yxw.interfaces.constants.FriedType
	 */
	private String fried;
	/**
	 * 是否配送
	 * @see com.yxw.interfaces.constants.DeliveryType
	 */
	private String delivered;
	/**
	 * 是否允许支付
	 * @see com.yxw.interfaces.constants.PayType
	 */
	public String canPay;
	/**
	 * 支付限制信息
	 */
	public String constraint;
	/**
	 * 处方集合
	 * @see com.yxw.interfaces.vo.clinicpay.Recipe
	 */
	private List<Recipe> recipes;

	public MZFeeDetail_FDP() {
		super();
	}

	public MZFeeDetail_FDP(String canFried, String canDeliver, String fried,
			String delivered, String canPay, String constraint, 
			List<Recipe> recipes) {
		super();
		this.canFried = canFried;
		this.canDeliver = canDeliver;
		this.fried = fried;
		this.delivered = delivered;
		this.recipes = recipes;
		this.canPay = canPay;
		this.constraint = constraint;
	}

	public String getCanFried() {
		return canFried;
	}

	public void setCanFried(String canFried) {
		this.canFried = canFried;
	}

	public String getCanDeliver() {
		return canDeliver;
	}

	public void setCanDeliver(String canDeliver) {
		this.canDeliver = canDeliver;
	}

	public String getFried() {
		return fried;
	}

	public void setFried(String fried) {
		this.fried = fried;
	}

	public String getDelivered() {
		return delivered;
	}

	public void setDelivered(String delivered) {
		this.delivered = delivered;
	}


	public List<Recipe> getRecipes() {
		return recipes;
	}


	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public String getCanPay() {
		return canPay;
	}

	public void setCanPay(String canPay) {
		this.canPay = canPay;
	}

	public String getConstraint() {
		return constraint;
	}

	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}
	
	
	
}
