package com.yxw.commons.entity.platform.rule;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.entity.BaseEntity;

/**
 * 支付方式配置
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年4月27日
 */
public class RulePayment extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2710794117598338027L;

	/**
	 * 关联医院ID
	 */
	private String hospitalId;

	/**
	 * 是否显示微信支付
	 */
//	private Integer isViewWeiXin;
//	private Integer isUseWeiXinTrade;

	/**
	 * 支付宝
	 */
//	private Integer isViewAlipay;
//	private Integer isUseAlipayTrade;

	/**
	 * 健康卡
	 */
//	private Integer isViewHealthCard;
//	private Integer isUseHealthCardTrade;

	/**
	 * 医保卡
	 */
//	private Integer isViewInsuranceHealthCard;
//	private Integer isUseInsuranceHealthCardTrade;

	/**
	 * 银联
	 */
//	private Integer isViewUnionpay;
//	private Integer isUseUnionpayTrade;

	/**
	 * 中信银行
	 */
//	private Integer isViewCITICBank;
//	private Integer isUseCITICBankTrade;

	/**
	 * 0,1,0,1 
	 */
//	@JSONField(serialize = false)
//	private String useArray;
//	@JSONField(serialize = false)
//	private String viewArray;
//
//	@JSONField(serialize = false)
//	private String tradeModeTargetArray;
//	@JSONField(serialize = false)
//	private String tradeModeTargetValArray;

	/**
	 * 支付方式：map, key为appCode, value为支付方式的数组
	 */
	private String tradeTypes;
	
	/**
	 * 默认支付方式   值见BizConstant.TRADE_MODE_XXXXXX
	 * 修改：和平台挂钩
	 */
	private String defaultTradeTypes;
	
	/**
	 * 显示视图的类型：iframe/jsonp
	 */
	private String viewType = "iframe";

	public RulePayment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RulePayment(String hospitalId) {
		super();
		this.hospitalId = hospitalId;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getTradeTypesMap() {
		Map<String, String> map = null;
		if (StringUtils.isNotBlank(this.getTradeTypes())) {
			map = JSON.parseObject(this.getTradeTypes(), Map.class);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getDefaultTradeTypesMap() {
		Map<String, String> map = null;
		if (StringUtils.isNotBlank(this.getDefaultTradeTypes())) {
			map = JSON.parseObject(this.getDefaultTradeTypes(), Map.class);
		}
		return map;
	}

	public RulePayment(String hospitalId, String tradeTypes, String defaultTradeTypes ) {
		super();
		this.hospitalId = hospitalId;
		this.tradeTypes = tradeTypes;
		this.defaultTradeTypes = defaultTradeTypes;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	/**
	 * 得到默认的缴费规则
	 * @param hospitalId
	 * @return
	 */
	public static RulePayment getDefaultRule(String hospitalId) {
		RulePayment payment = new RulePayment(hospitalId);
		
		// 默认的规则信息
		
		return payment;
	}

	public String getTradeTypes() {
		return tradeTypes;
	}

	public void setTradeTypes(String tradeTypes) {
		this.tradeTypes = tradeTypes;
	}

	public String getDefaultTradeTypes() {
		return defaultTradeTypes;
	}

	public void setDefaultTradeTypes(String defaultTradeTypes) {
		this.defaultTradeTypes = defaultTradeTypes;
	}

	public String getViewType() {
		return viewType;
	}

	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	
}