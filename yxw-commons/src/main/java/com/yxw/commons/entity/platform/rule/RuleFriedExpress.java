/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-11-11</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleFried
 * @Statement: <p>医院配置规则-->代煎配送规则</p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015-11-11
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleFriedExpress extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2068820557278900778L;

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 门诊缴费是否接入代煎配送;1是,0否
	 */
	private Integer isAccessClinic;

	/**
	 * 缴费记录是否接入代煎配送;1是,0否
	 */
	private Integer isAccessPayed;

	/**
	 * 用户是否可关闭代煎配送;1是,0否
	 */
	private Integer isCanOffByUser;

	/**
	 * 是否只设置配送信息;1是,0否
	 */
	private Integer isOnlyExpress;

	/**
	 * 是否要对不同的处方单设置代煎配送;1是,0否
	 */
	private Integer isSplitRecipe;

	/**
	 * 代煎配置是否要查询接口;1是,0否
	 */
	private Integer isFriedQuery;

	/**
	 * 配送配置是否要查询接口;1是,0否
	 */
	private Integer isExpressQuery;

	/**
	 * 代煎与配送的关系;1不限制,2代煎必须配送,3代煎可以不配送
	 */
	private Integer friedExpressRelation;

	/**
	 * 代煎配送提示语
	 */
	private String tipContent;

	public static RuleFriedExpress getDefaultRule(String hospitalId) {
		RuleFriedExpress ruleFriedExpress = new RuleFriedExpress();
		ruleFriedExpress.setHospitalId(hospitalId);

		ruleFriedExpress.setIsAccessClinic(0);
		ruleFriedExpress.setIsAccessPayed(0);
		ruleFriedExpress.setIsCanOffByUser(1);
		ruleFriedExpress.setIsOnlyExpress(1);
		ruleFriedExpress.setIsSplitRecipe(0);
		ruleFriedExpress.setIsFriedQuery(0);
		ruleFriedExpress.setIsExpressQuery(0);

		return ruleFriedExpress;
	}

	/**
	 * 
	 */
	public RuleFriedExpress() {
		super();
	}

	/**
	 * @param hospitalId
	 * @param isAccessClinic
	 * @param isAccessPayed
	 * @param isCanOffByUser
	 * @param isOnlyExpress
	 * @param isSplitRecipe
	 * @param isFriedQuery
	 * @param isExpressQuery
	 * @param friedExpressRelation
	 * @param tipContent
	 */
	public RuleFriedExpress(String hospitalId, Integer isAccessClinic, Integer isAccessPayed, Integer isCanOffByUser, Integer isOnlyExpress,
			Integer isSplitRecipe, Integer isFriedQuery, Integer isExpressQuery, Integer friedExpressRelation, String tipContent) {
		super();
		this.hospitalId = hospitalId;
		this.isAccessClinic = isAccessClinic;
		this.isAccessPayed = isAccessPayed;
		this.isCanOffByUser = isCanOffByUser;
		this.isOnlyExpress = isOnlyExpress;
		this.isSplitRecipe = isSplitRecipe;
		this.isFriedQuery = isFriedQuery;
		this.isExpressQuery = isExpressQuery;
		this.friedExpressRelation = friedExpressRelation;
		this.tipContent = tipContent;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	/**
	 * @return the isAccessClinic
	 */
	public Integer getIsAccessClinic() {
		return isAccessClinic;
	}

	/**
	 * @param isAccessClinic the isAccessClinic to set
	 */
	public void setIsAccessClinic(Integer isAccessClinic) {
		this.isAccessClinic = isAccessClinic;
	}

	/**
	 * @return the isAccessPayed
	 */
	public Integer getIsAccessPayed() {
		return isAccessPayed;
	}

	/**
	 * @param isAccessPayed the isAccessPayed to set
	 */
	public void setIsAccessPayed(Integer isAccessPayed) {
		this.isAccessPayed = isAccessPayed;
	}

	/**
	 * @return the isCanOffByUser
	 */
	public Integer getIsCanOffByUser() {
		return isCanOffByUser;
	}

	/**
	 * @param isCanOffByUser the isCanOffByUser to set
	 */
	public void setIsCanOffByUser(Integer isCanOffByUser) {
		this.isCanOffByUser = isCanOffByUser;
	}

	/**
	 * @return the isOnlyExpress
	 */
	public Integer getIsOnlyExpress() {
		return isOnlyExpress;
	}

	/**
	 * @param isOnlyExpress the isOnlyExpress to set
	 */
	public void setIsOnlyExpress(Integer isOnlyExpress) {
		this.isOnlyExpress = isOnlyExpress;
	}

	/**
	 * @return the isSplitRecipe
	 */
	public Integer getIsSplitRecipe() {
		return isSplitRecipe;
	}

	/**
	 * @param isSplitRecipe the isSplitRecipe to set
	 */
	public void setIsSplitRecipe(Integer isSplitRecipe) {
		this.isSplitRecipe = isSplitRecipe;
	}

	/**
	 * @return the isFriedQuery
	 */
	public Integer getIsFriedQuery() {
		return isFriedQuery;
	}

	/**
	 * @param isFriedQuery the isFriedQuery to set
	 */
	public void setIsFriedQuery(Integer isFriedQuery) {
		this.isFriedQuery = isFriedQuery;
	}

	/**
	 * @return the isExpressQuery
	 */
	public Integer getIsExpressQuery() {
		return isExpressQuery;
	}

	/**
	 * @param isExpressQuery the isExpressQuery to set
	 */
	public void setIsExpressQuery(Integer isExpressQuery) {
		this.isExpressQuery = isExpressQuery;
	}

	/**
	 * @return the friedExpressRelation
	 */
	public Integer getFriedExpressRelation() {
		return friedExpressRelation;
	}

	/**
	 * @param friedExpressRelation the friedExpressRelation to set
	 */
	public void setFriedExpressRelation(Integer friedExpressRelation) {
		this.friedExpressRelation = friedExpressRelation;
	}

	/**
	 * @return the tipContent
	 */
	public String getTipContent() {
		return tipContent;
	}

	/**
	 * @param tipContent the tipContent to set
	 */
	public void setTipContent(String tipContent) {
		this.tipContent = tipContent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RuleFriedExpress [hospitalId=" + hospitalId + ", isAccessClinic=" + isAccessClinic + ", isAccessPayed=" + isAccessPayed
				+ ", isCanOffByUser=" + isCanOffByUser + ", isOnlyExpress=" + isOnlyExpress + ", isSplitRecipe=" + isSplitRecipe + ", isFriedQuery="
				+ isFriedQuery + ", isExpressQuery=" + isExpressQuery + ", friedExpressRelation=" + friedExpressRelation + ", tipContent="
				+ tipContent + "]";
	}

}
