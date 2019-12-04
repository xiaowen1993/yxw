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
package com.yxw.mobileapp.biz.user.entity;

import org.apache.commons.lang.StringUtils;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.MsgPushEntity;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.utils.biz.MedicalCardUtil;
import com.yxw.commons.vo.MessagePushParamsVo;
import com.yxw.utils.IdCardUtils;

/**
 * @Package: com.yxw.easyhealth.biz.user.entity
 * @ClassName: EasyHealthUser
 * @Statement: <p>
 *             健康易注册登录信息
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年10月6日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class EasyHealthUser extends MsgPushEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9151554851072776653L;

	/**
	 * 实体类对应的hash子表 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	/**
	 * 账号
	 */
	private String account;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 证件类型 1：二代身份证， 2：港澳居民身份证， 3：台湾居民身份证， 4：护照
	 */
	private Integer cardType;

	/**
	 * 证件号，既登录帐号
	 */
	private String cardNo;

	/**
	 * 密码
	 */
	private String passWord;

	/**
	 * 登录随机字符
	 */
	private String loginNonce;

	/**
	 * 设备号
	 */
	private String deviceNumber;

	/**
	 * 注册时间
	 */
	private Long registerTime;

	/**
	 * 最后登录时间
	 */
	private Long lastLoginTime;

	/**
	 * 生日
	 */
	private String birthDay;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 支付宝服务窗OpenId
	 */
	private String alipayId;

	/**
	 * 微信公众账号OpenId
	 */
	private String wechatId;

	/**
	 * 银联钱包公众账号OpenId
	 */
	private String unionPayId;

	/**
	 * 脱敏用户名
	 */
	private String encryptedName;

	/**
	 * 脱敏手机号码
	 */
	private String encryptedMobile;

	/**
	 * 脱敏卡号
	 */
	private String encryptedCardNo;

	/**
	 * 脱敏账号
	 */
	private String encryptedAccount;

	/**
	 * 经度
	 * */
	private String longitude;
	/**
	 * 纬度
	 * */
	private String latitude;

	public EasyHealthUser() {
		super();
	}

	public EasyHealthUser(String account, String name, String mobile, Integer cardType, String cardNo, String birthDay, Integer sex,
			String address, String passWord, String loginNonce, String deviceNumber, Long registerTime, Long lastLoginTime,
			String longitude, String latitude) {
		super();
		this.account = account;
		this.name = name;
		this.mobile = mobile;
		this.cardType = cardType;
		this.cardNo = cardNo;
		this.birthDay = birthDay;
		this.sex = sex;
		this.address = address;
		this.passWord = passWord;
		this.loginNonce = loginNonce;
		this.deviceNumber = deviceNumber;
		this.registerTime = registerTime;
		this.lastLoginTime = lastLoginTime;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAlipayId() {
		return alipayId;
	}

	public void setAlipayId(String alipayId) {
		this.alipayId = alipayId;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getUnionPayId() {
		return unionPayId;
	}

	public void setUnionPayId(String unionPayId) {
		this.unionPayId = unionPayId;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLoginNonce() {
		return loginNonce;
	}

	public void setLoginNonce(String loginNonce) {
		this.loginNonce = loginNonce;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public Long getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	public Long getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getEasyHealthUserHashTable(account, true);
		}
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getOpenid() {
		return id;
	}

	public String getBirthDay() {
		if (StringUtils.isBlank(birthDay)) {
			if (this.cardType != null && this.cardType == 1 && cardNo != null) {
				if (this.cardNo.length() == 15 || this.cardNo.length() == 18) {
					//this.birthDay = IdCardUtils.getBirthByIdCard(cardNo);
					this.birthDay = MedicalCardUtil.getBirth(this.cardNo);
				}
			}
		}/* else {
			   this.birthDay = birthDay.replaceAll("-", "");
			}*/
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public Integer getSex() {
		if (sex == null) {
			if (this.cardType != null && this.cardType == 1 && cardNo != null) {
				if (this.cardNo.length() == 15 || this.cardNo.length() == 18) {
					String tempSex = IdCardUtils.getGenderByIdCard(this.cardNo);
					sex = tempSex.equalsIgnoreCase("N") ? 0 : tempSex.equals("M") ? 1 : 2;
				} else {
					sex = 0;
				}
			} else {
				sex = 0;
			}
		}
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getEncryptedName() {
		encryptedName = "";
		if (StringUtils.isNotEmpty(name)) {
			encryptedName = name.replaceFirst("[\u4E00-\u9FA5]", BizConstant.VIEW_SENSITIVE_CHAR);
		}
		return encryptedName;
	}

	public String getEncryptedMobile() {
		encryptedMobile = "";
		if (this.mobile != null && mobile.length() == 11) {
			encryptedMobile =
					mobile.substring(0, 3) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, mobile.length() - 7)
							+ mobile.substring(mobile.length() - 4);
		}
		return encryptedMobile;
	}

	public String getEncryptedCardNo() {
		encryptedCardNo = "";
		if (this.cardNo != null && this.cardNo.length() > 4) {
			encryptedCardNo =
					cardNo.substring(0, 1) + StringUtils.repeat(BizConstant.VIEW_SENSITIVE_CHAR, cardNo.length() - 4)
							+ cardNo.substring(cardNo.length() - 3);
		}
		return encryptedCardNo;
	}

	public String getEncryptedAccount() {
		encryptedAccount = "";

		if (account.matches("^\\d{15}|\\d{18}|\\d{17}(x|X)$")) {
			encryptedAccount = getEncryptedCardNo();
		} else if (account.matches("^\\d{11}$")) {
			encryptedAccount = getEncryptedMobile();
		} else {
			encryptedAccount = getEncryptedCardNo();
		}

		return encryptedAccount;
	}

	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		// TODO Auto-generated method stub
		return null;
	}

}
