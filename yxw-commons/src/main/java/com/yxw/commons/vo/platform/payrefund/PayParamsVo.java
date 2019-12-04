/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-2</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.commons.vo.platform.payrefund;

import org.springframework.beans.BeanUtils;

import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.WechatPay;

/**
 * @Package: com.yxw.platform.payrefund.remote.vo
 * @ClassName: PayParamVo
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-2
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PayParamsVo {
	/**
	 * 支付类型
	 * BizConstant.MODE_TYPE_WEIXIN_VAL = 1
	 * BizConstant.MODE_TYPE_ALIPAY_VAL = 2 
	 */
	private Integer tradeMode;

	/**
	 * 支付金额 单位：分
	 */
	protected String totalFee;

	/**
	 * 掌上医院标准平台支付订单号
	 */
	private String orderNo;

	private String appId;

	private String appCode;

	/**
	 * 父商户appId
	 */
	private String parentAppId;

	/**
	 * 父商户appId
	 */
	private String parentSecret;

	/**
	 * 商户Id
	 */
	private String mchId;

	/**
	 * 商户key
	 */
	private String key;

	/**
	 * 支付openId（支付宝支付实际未应用到此参数）
	 */
	private String openId;

	/**
	 * 支付成功链接地址
	 */
	private String successUrl;

	/**
	 * 支付显示内容链接地址
	 */
	private String infoUrl;

	/**
	 * 异步返回地址
	 */
	private String notifyUrl;

	/**
	 * 暂不支付链接地址
	 */
	private String afterPayUrl;

	/**
	 * 在线支付控制  :1：必须在线支付    2：不用在线支付     3：支持暂不支付
	 * BizConstant.PAYMENT_TYPE_MUST = 1
	 * BizConstant.PAYMENT_TYPE_NOT_NEED = 2
	 * BizConstant.PAYMENT_TYPE_SUPPORT_TEMPORARILY_NOT = 3
	 */
	private Integer onlinePaymentControl;

	/**
	 * 支付超时时间
	 */
	private Long payTimeoutTime;

	/**
	 * 商品信息
	 */
	private String subject;

	/**
	 * 支付备注
	 */
	private String payRemark;

	/************** PayAli  特有参数 start ****************/
	/**
	 * 支付公钥
	 */
	private String publicKey;
	/**
	 * 支付私钥
	 */
	private String privateKey;

	/**
	 * 卖家支付宝账号
	 */
	private String mchAccount;

	/**
	 * 中断返回地址
	 */
	private String merchantUrl;
	/**************  PayAli  特有参数 end *****************/

	/**************  PayWechat  特有参数 end *****************/
	/**
	 * 特约商户支付openId
	 */
	private String mchOpenId;

	/**
	 * 子商户mchId
	 */
	private String subMchId;

	/**
	 * 支付订单生成ip
	 */
	private String spbillCreateIp;

	/**
	 * 商户secret
	 */
	private String secret;

	/**
	 * 支付prepayId
	 */
	private String prepayId;
	/**
	 * 支付签名
	 */
	private String paySign;
	/**
	 * 附加参数 
	 */
	private String attach;

	/**
	 * 支付随机数
	 */
	private String nonceStr;

	/**
	 * 回调执行业务函数名称
	 */
	private String notifyMethodName;

	/**
	 * 解决跨域不能自适应  高度由业务传值决定
	 */
	protected Integer orderInfoHeight;

	public PayParamsVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getTradeMode() {
		return tradeMode;
	}

	public void setTradeMode(Integer tradeMode) {
		this.tradeMode = tradeMode;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getParentAppId() {
		return parentAppId;
	}

	public void setParentAppId(String parentAppId) {
		this.parentAppId = parentAppId;
	}

	public String getParentSecret() {
		return parentSecret;
	}

	public void setParentSecret(String parentSecret) {
		this.parentSecret = parentSecret;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getAfterPayUrl() {
		return afterPayUrl;
	}

	public void setAfterPayUrl(String afterPayUrl) {
		this.afterPayUrl = afterPayUrl;
	}

	public Integer getOnlinePaymentControl() {
		return onlinePaymentControl;
	}

	public void setOnlinePaymentControl(Integer onlinePaymentControl) {
		this.onlinePaymentControl = onlinePaymentControl;
	}

	public Long getPayTimeoutTime() {
		return payTimeoutTime;
	}

	public void setPayTimeoutTime(Long payTimeoutTime) {
		this.payTimeoutTime = payTimeoutTime;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPayRemark() {
		return payRemark;
	}

	public void setPayRemark(String payRemark) {
		this.payRemark = payRemark;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getMchAccount() {
		return mchAccount;
	}

	public void setMchAccount(String mchAccount) {
		this.mchAccount = mchAccount;
	}

	public String getMerchantUrl() {
		return merchantUrl;
	}

	public void setMerchantUrl(String merchantUrl) {
		this.merchantUrl = merchantUrl;
	}

	public String getMchOpenId() {
		return mchOpenId;
	}

	public void setMchOpenId(String mchOpenId) {
		this.mchOpenId = mchOpenId;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPaySign() {
		return paySign;
	}

	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getNotifyMethodName() {
		return notifyMethodName;
	}

	public void setNotifyMethodName(String notifyMethodName) {
		this.notifyMethodName = notifyMethodName;
	}

	/**
	 * 转化为微信支付信息
	 * @return
	 */
	public WechatPay convertPayWechat() {
		WechatPay pay = new WechatPay();
		BeanUtils.copyProperties(this, pay);
		return pay;
	}

	/**
	 * 转化为支付宝支付信息
	 * @return
	 */
	public Alipay convertPayAli() {
		Alipay pay = new Alipay();
		BeanUtils.copyProperties(this, pay);
		return pay;
	}

	public Integer getOrderInfoHeight() {
		return orderInfoHeight;
	}

	public void setOrderInfoHeight(Integer orderInfoHeight) {
		this.orderInfoHeight = orderInfoHeight;
	}
}
