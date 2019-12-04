/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年11月19日</p>
 *  <p> Created by 范建明</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.clinicpay;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 诊疗付费-->门诊待缴费记录处方信息
 * @Package: com.yxw.interfaces.vo.clinicpay
 * @ClassName: Recipe
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 范建明
 * @Create Date: 2015年11月19日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Recipe extends Reserve implements Serializable {

	private static final long serialVersionUID = -7688041905431807742L;
	/**
	 * 处方编号
	 */
	private String recipeId;
	/**
	 * 处方费用类型,返回中文名称,如：药费、检查费、材料费
	 */
	private String recipeType;
	/**
	 * 处方费用,单位：分
	 */
	private String recipeFee;
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
	 * 开出科室
	 */
	private String deptName;
	/**
	 * 开药医生代码
	 */
	private String doctorCode;
	/**
	 * 开药医生姓名
	 */
	private String doctorName;
	/**
	 * 处方集合
	 * @see com.yxw.interfaces.vo.clinicpay.RecipeDetail
	 */
	private List<RecipeDetail> recipeDetails;
	
	public Recipe() {
		super();
	}

	public Recipe(String recipeId, String recipeType, String recipeFee,
			String canFried, String canDeliver, String fried, String delivered,
			String deptName, String doctorCode, String doctorName,
			List<RecipeDetail> recipeDetails) {
		super();
		this.recipeId = recipeId;
		this.recipeType = recipeType;
		this.recipeFee = recipeFee;
		this.canFried = canFried;
		this.canDeliver = canDeliver;
		this.fried = fried;
		this.delivered = delivered;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.recipeDetails = recipeDetails;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getRecipeType() {
		return recipeType;
	}

	public void setRecipeType(String recipeType) {
		this.recipeType = recipeType;
	}

	public String getRecipeFee() {
		return recipeFee;
	}

	public void setRecipeFee(String recipeFee) {
		this.recipeFee = recipeFee;
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

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDoctorCode() {
		return doctorCode;
	}

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public List<RecipeDetail> getRecipeDetails() {
		return recipeDetails;
	}

	public void setRecipeDetails(List<RecipeDetail> recipeDetails) {
		this.recipeDetails = recipeDetails;
	}

}
