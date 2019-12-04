package com.yxw.commons.entity.platform.rule;

import java.io.Serializable;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.rule.entity
 * @ClassName: RuleEdit
 * @Statement: <p>医院配置规则-->个人中心规则</p>
 * @JDK version used: 1.6
 * @Author: Dfw
 * @Create Date: 2015-5-27
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RuleUserCenter extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -837205562924384298L;

	/**
	 * 医院Id
	 */
	private String hospitalId;

	/**
	 * 首页显示内容,用","隔开
	 * 1、我的家人
	 * 2、挂号记录
	 * 3、缴费记录
	 * 4、我的医生
	 * 5、我的咨询
	 * 6、我的消息
	 * 7、我的服务
	 * 8、健康记录
	 * 9、服务评价
	 */
	private String indexContent;
	private String[] indexContentArray;

	/**
	 * 码制
	 * 1、EAN码 (ean8)
	 * 2、UPC码  (upc)
	 * 3、39码 (code39)
	 * 4、128码 (code128)
	 * 5、库德巴码 (codabar)
	 */
	private String barcodeStyle;

	/**
	 * 个人信息页面显示内容,用","隔开
	 * 1、条形码
	 * 2、证件号
	 * 3、手机号码
	 * 4、卡号
	 * 5、住院号
	 * 6、社保卡号
	 */
	private String userInfoContent;
	private String[] userInfoContentArray;

	public RuleUserCenter() {

	}

	public static RuleUserCenter getDefaultRule(String hospitalId) {
		RuleUserCenter ruleUserCenter = new RuleUserCenter();
		ruleUserCenter.setHospitalId(hospitalId);
		ruleUserCenter.setIndexContent("1,2,3,4");
		ruleUserCenter.setIndexContentArray(new String[] { "1", "2", "3", "4" });
		ruleUserCenter.setUserInfoContent("1,2,3,4");
		ruleUserCenter.setUserInfoContentArray(new String[] { "1", "2", "3", "4" });
		ruleUserCenter.setBarcodeStyle("4");
		return ruleUserCenter;
	}

	public String getUserInfoContent() {
		return userInfoContent;
	}

	public void setUserInfoContent(String userInfoContent) {
		this.userInfoContent = userInfoContent;
	}

	public String getBarcodeStyle() {
		return barcodeStyle;
	}

	public void setBarcodeStyle(String barcodeStyle) {
		this.barcodeStyle = barcodeStyle;
	}

	public String getIndexContent() {
		return indexContent;
	}

	public void setIndexContent(String indexContent) {
		this.indexContent = indexContent;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String[] getIndexContentArray() {
		return indexContentArray;
	}

	public void setIndexContentArray(String[] indexContentArray) {
		this.indexContentArray = indexContentArray;
	}

	public String[] getUserInfoContentArray() {
		return userInfoContentArray;
	}

	public void setUserInfoContentArray(String[] userInfoContentArray) {
		this.userInfoContentArray = userInfoContentArray;
	}

}