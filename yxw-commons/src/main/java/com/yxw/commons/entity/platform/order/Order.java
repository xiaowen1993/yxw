/**
 * 
 */
package com.yxw.commons.entity.platform.order;

import java.io.Serializable;
import java.util.Date;

import com.yxw.base.entity.BaseEntity;

/**
 * @Package: com.yxw.platform.order.entity
 * @ClassName: Order
 * @Statement: <p>订单基类</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class Order extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -7709093962602160547L;

	/**
	 * 订单编号
	 */
	protected String orderNo;

	/**
	 * 订单编号的hash值  用于提高查询性能
	 */
	protected Integer orderNoHashVal;

	/**
	 * 商户appId
	 */
	protected String appId;

	/**
	 * 商户接入的平台code
	 */
	protected String appCode;

	/**
	 * 分院ID(关联分院ID)
	 */
	protected String branchId;

	/**
	 * 医院ID
	 */
	protected String hospitalId;

	/**
	 * 用户openId
	 */
	protected String openId;

	/**
	 * 子商户时需要的openId
	 */
	private String mchOpenId;

	/**
	 * 商户mchId
	 */
	private String mchId;

	/**
	 * 商户secret
	 */
	private String mchSecret;

	/**
	 * 子商户mchId
	 */
	private String subMchId;

	/**
	 * 是否有子商户   1 有   0 没有
	 */
	private Integer isSubPay;

	/**
	 * 商户key
	 */
	private String mchKey;

	/**
	 * 支付配置ID 用户查询支付或者退款密钥
	 */
	private String paySettingId;

	/**
	 * 子商户ID
	 */
	protected String subAppId;

	/**
	 * 关联订单号 (此订单对应的退费等业务订单)
	 */
	protected String relativeOrderNo;

	/**
	 * 订单抬头
	 */
	protected String orderTitle;

	/**
	 * 医院订单号
	 */
	protected String hisOrdNum;

	/**
	 * 医院退费单号
	 */
	protected String hisRefOrdNum;

	/**
	 * 医院订单支付确认收据号
	 */
	protected String mzOrdNum;

	/**
	 * 交易机构支付单号
	 */
	protected String agtOrdNum;

	/**
	 * 交易机构退费单号 
	 */
	protected String agtRefOrdNum;

	/**
	 * 交易机构代码 ,财付通账号、银行卡账号等
	 */
	protected String agtCode;

	/**
	 * 支付方式,1：微信公众号,2：支付宝服务窗,3：其他
	 * 
	 */
	protected Integer payMode;

	/**
	 * 支付方式,1：微信公众号,2：支付宝服务窗,3：其他
	 * 
	 */
	protected Integer refundMode;

	/**
	 * 支付控制类型  1：必须在线支付    2：不用在线支付     3：支持暂不支付
	 */
	protected Integer onlinePaymentType;

	/**
	 * 支付金额,单位：分
	 */
	protected Integer payAmout;

	/**
	 * 支付时间  yyyy-MM-dd HH:mm:ss
	 */
	protected Date payTime;

	/**
	 * 支付说明
	 */
	protected String payDesc;

	/**
	 * 退费金额,单位：分
	 */
	protected Integer refundAmout;

	/**
	 * 退费时间  yyyy-MM-dd HH:mm:ss
	 */
	protected Date refundTime;

	/**
	 * 退费说明
	 */
	protected String refundDesc;

	/**
	 * 取消原因
	 */
	protected String cancelReason;

	/**
	 * 订单状态<br>
	 * 1：未支付（0:预约中, 4:已取消-支付超过规定时长, 5:预约异常(his锁号异常), 6:支付异常(公共支付), 7:取消挂号异常, 10:停诊取消）<br>
	 * 2：已支付（1:已预约, 8:退费异常(公共退费), 9:支付后提交his异常）<br>
	 * 3：已退费（2:已取消-用户取消, 3:已取消-网络异常系统自动取消, 10:停诊取消）
	 */
	protected Integer state;

	/**
	 * 订单描述
	 */
	protected String orderDesc;

	/**
	 * 业务编码
	 */
	protected Integer businessCode;

	/**
	 * 业务名称
	 */
	protected String businessName;

	/**
	 * 订单生成时间
	 */
	protected Date orderTime;

	/**
	 * 等待支付时间(分钟)
	 */
	protected Integer waitPayTime;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String orderNo, Integer orderNoHashVal, String appId, String appCode, String branchId, String hospitalId, String openId,
			String mchOpenId, String mchId, String mchSecret, String subMchId, Integer isSubPay, String mchKey, String relativeOrderNo,
			String orderTitle, String hisOrdNum, String hisRefOrdNum, String mzOrdNum, String agtOrdNum, String agtRefOrdNum, String agtCode,
			Integer payMode, Integer refundMode, Integer onlinePaymentType, Integer payAmout, Date payTime, String payDesc, Integer refundAmout,
			Date refundTime, String refundDesc, String cancelReason, Integer state, String orderDesc, Integer businessCode, String businessName,
			Date orderTime, Integer waitPayTime, String subAppId) {
		super();
		this.orderNo = orderNo;
		this.orderNoHashVal = orderNoHashVal;
		this.appId = appId;
		this.appCode = appCode;
		this.branchId = branchId;
		this.hospitalId = hospitalId;
		this.openId = openId;
		this.mchOpenId = mchOpenId;
		this.mchId = mchId;
		this.mchSecret = mchSecret;
		this.subMchId = subMchId;
		this.isSubPay = isSubPay;
		this.mchKey = mchKey;
		this.relativeOrderNo = relativeOrderNo;
		this.orderTitle = orderTitle;
		this.hisOrdNum = hisOrdNum;
		this.hisRefOrdNum = hisRefOrdNum;
		this.mzOrdNum = mzOrdNum;
		this.agtOrdNum = agtOrdNum;
		this.agtRefOrdNum = agtRefOrdNum;
		this.agtCode = agtCode;
		this.payMode = payMode;
		this.refundMode = refundMode;
		this.onlinePaymentType = onlinePaymentType;
		this.payAmout = payAmout;
		this.payTime = payTime;
		this.payDesc = payDesc;
		this.refundAmout = refundAmout;
		this.refundTime = refundTime;
		this.refundDesc = refundDesc;
		this.cancelReason = cancelReason;
		this.state = state;
		this.orderDesc = orderDesc;
		this.businessCode = businessCode;
		this.businessName = businessName;
		this.orderTime = orderTime;
		this.waitPayTime = waitPayTime;
		this.subAppId = subAppId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getOrderNoHashVal() {
		return orderNoHashVal;
	}

	public void setOrderNoHashVal(Integer orderNoHashVal) {
		this.orderNoHashVal = orderNoHashVal;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMchOpenId() {
		return mchOpenId;
	}

	public void setMchOpenId(String mchOpenId) {
		this.mchOpenId = mchOpenId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getMchSecret() {
		return mchSecret;
	}

	public void setMchSecret(String mchSecret) {
		this.mchSecret = mchSecret;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public Integer getIsSubPay() {
		return isSubPay;
	}

	public void setIsSubPay(Integer isSubPay) {
		this.isSubPay = isSubPay;
	}

	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}

	public String getRelativeOrderNo() {
		return relativeOrderNo;
	}

	public void setRelativeOrderNo(String relativeOrderNo) {
		this.relativeOrderNo = relativeOrderNo;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getHisOrdNum() {
		return hisOrdNum;
	}

	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	public String getHisRefOrdNum() {
		return hisRefOrdNum;
	}

	public void setHisRefOrdNum(String hisRefOrdNum) {
		this.hisRefOrdNum = hisRefOrdNum;
	}

	public String getMzOrdNum() {
		return mzOrdNum;
	}

	public void setMzOrdNum(String mzOrdNum) {
		this.mzOrdNum = mzOrdNum;
	}

	public String getAgtOrdNum() {
		return agtOrdNum;
	}

	public void setAgtOrdNum(String agtOrdNum) {
		this.agtOrdNum = agtOrdNum;
	}

	public String getAgtRefOrdNum() {
		return agtRefOrdNum;
	}

	public void setAgtRefOrdNum(String agtRefOrdNum) {
		this.agtRefOrdNum = agtRefOrdNum;
	}

	public String getAgtCode() {
		return agtCode;
	}

	public void setAgtCode(String agtCode) {
		this.agtCode = agtCode;
	}

	public Integer getOnlinePaymentType() {
		return onlinePaymentType;
	}

	public void setOnlinePaymentType(Integer onlinePaymentType) {
		this.onlinePaymentType = onlinePaymentType;
	}

	public Integer getPayAmout() {
		return payAmout;
	}

	public void setPayAmout(Integer payAmout) {
		this.payAmout = payAmout;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public Integer getRefundAmout() {
		return refundAmout;
	}

	public void setRefundAmout(Integer refundAmout) {
		this.refundAmout = refundAmout;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Integer getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(Integer businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Integer getWaitPayTime() {
		return waitPayTime;
	}

	public void setWaitPayTime(Integer waitPayTime) {
		this.waitPayTime = waitPayTime;
	}

	public String getSubAppId() {
		return subAppId;
	}

	public void setSubAppId(String subAppId) {
		this.subAppId = subAppId;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}

	public Integer getRefundMode() {
		return refundMode;
	}

	public void setRefundMode(Integer refundMode) {
		this.refundMode = refundMode;
	}

	public String getPaySettingId() {
		return paySettingId;
	}

	public void setPaySettingId(String paySettingId) {
		this.paySettingId = paySettingId;
	}
}
