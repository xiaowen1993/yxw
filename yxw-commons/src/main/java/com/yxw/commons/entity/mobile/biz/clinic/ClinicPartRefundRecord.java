package com.yxw.commons.entity.mobile.biz.clinic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.register.Record;
import com.yxw.commons.vo.MessagePushParamsVo;

public class ClinicPartRefundRecord extends Record {

	private static final long serialVersionUID = 2585538960867452216L;

	/**
	 * 退费类型 ClinicConstant.MODE_TYPE_WEIXIN_VAL = 1;
	 * ClinicConstant.MODE_TYPE_ALIPAY_VAL = 2;
	 */
	private Integer refundMode;

	/**
	 * 总金额
	 */
	private Integer totalFee;

	/**
	 * 退费金额
	 */
	private Integer refundFee;

	private String refundFeeLabel;

	/**
	 * 是否发生异常
	 */
	private Integer isException;

	/**
	 * 是否处理成功
	 */
	private Integer isHandleSuccess;

	/**
	 * 处理次数
	 */
	private Integer handleCount;

	/**
	 * 处理日志
	 */
	private String handleLog;

	/**
	 * 订单创建时间
	 */
	private Long createTime;

	/**
	 * 退费状态
	 * 
	 * @see com.yxw.mobileapp.biz.clinic.ClinicConstant
	 */
	private Integer refundStatus;

	private Integer refundOrderNoHashVal;

	public ClinicPartRefundRecord() {
		super();
	}

	public ClinicPartRefundRecord(ClinicRecord record) {
		super();

		this.setHospitalId(record.getHospitalId());
		this.setHospitalCode(record.getHospitalCode());
		this.setHospitalName(record.getHospitalName());
		this.setBranchHospitalId(record.getBranchHospitalId());
		this.setBranchHospitalCode(record.getBranchHospitalCode());
		this.setBranchHospitalName(record.getBranchHospitalName());
		this.setAppId(record.getAppId());
		this.setOpenId(record.getOpenId());
		this.setCardType(record.getCardType());
		this.setCardNo(record.getCardNo());
		this.setPatientName(record.getPatientName());
		this.setPatientMobile(record.getPatientMobile());
		this.setPatientSex(record.getPatientSex());
		this.setRefundMode(record.getPlatformMode());
		this.setOrderNo(record.getOrderNo());
		this.setRefundOrderNo(record.getRefundOrderNo());
		this.setAgtOrdNum(record.getAgtOrdNum());
		this.setHisOrdNo(record.getHisOrdNo());
		this.setTotalFee(Integer.valueOf(record.getTotalFee()));
		this.setPayTime(record.getPayTime());
		this.setOrderNoHashVal(record.getOrderNoHashVal());
	}

	@Override
	public Integer getPayTotalFee() {
		return null;
	}

	public Integer getRefundMode() {
		return refundMode;
	}

	public void setRefundMode(Integer refundMode) {
		this.refundMode = refundMode;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public Integer getIsException() {
		return isException;
	}

	public void setIsException(Integer isException) {
		this.isException = isException;
	}

	public Integer getIsHandleSuccess() {
		return isHandleSuccess;
	}

	public void setIsHandleSuccess(Integer isHandleSuccess) {
		this.isHandleSuccess = isHandleSuccess;
	}

	public Integer getHandleCount() {
		return handleCount;
	}

	public void setHandleCount(Integer handleCount) {
		this.handleCount = handleCount;
	}

	public String getHandleLog() {
		return handleLog;
	}

	public void setHandleLog(String handleLog) {
		this.handleLog = handleLog;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}

	@Override
	public Integer getRefundTotalFee() {
		return refundFee;
	}

	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		MessagePushParamsVo params = new MessagePushParamsVo();
		params.setHospitalId(this.hospitalId);
		params.setAppId(this.appId);
		params.setOpenId(this.openId);

		if (this.refundMode != null) {
			if (this.refundMode == BizConstant.MODE_TYPE_WECHAT_VAL) {
				params.setPlatformType(BizConstant.MODE_TYPE_WECHAT);
			} else if (this.refundMode == BizConstant.MODE_TYPE_ALIPAY_VAL) {
				params.setPlatformType(BizConstant.MODE_TYPE_ALIPAY);
			} else if (this.refundMode == BizConstant.MODE_TYPE_APP_VAL) {
				params.setPlatformType(BizConstant.MODE_TYPE_APP);
			} else if (refundMode == BizConstant.MODE_TYPE_INNER_UNIONPAY_VAL) {//嵌在银联钱包平台
				params.setPlatformType(BizConstant.MODE_TYPE_INNER_UNIONPAY);
			} else if (refundMode == BizConstant.MODE_TYPE_INNER_WECHAT_VAL) {//嵌在微信平台
				params.setPlatformType(BizConstant.MODE_TYPE_INNER_WECHAT);
			} else if (refundMode == BizConstant.MODE_TYPE_INNER_ALIPAY_VAL) {//嵌在支付宝平台
				params.setPlatformType(BizConstant.MODE_TYPE_INNER_ALIPAY);
			}
		} else {
			params.setPlatformType(BizConstant.MODE_TYPE_WECHAT);
		}

		@SuppressWarnings("unchecked")
		Map<String, Serializable> dataMap = JSON.parseObject(JSON.toJSONString(this), Map.class);
		String urlParms =
				BizConstant.URL_PARAM_CHAR_FIRST.concat(BizConstant.ORDERNO_KEY).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.orderNo)
						.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.OPENID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.openId);

		dataMap.put(BizConstant.MSG_PUSH_URL_PARAMS_KEY, urlParms);
		params.setParamMap(dataMap);

		return params;
	}

	public Integer getRefundOrderNoHashVal() {
		if (StringUtils.isNotBlank(refundOrderNo)) {
			refundOrderNoHashVal = Math.abs(refundOrderNo.hashCode());
		} else {
			refundOrderNoHashVal = 0;
		}
		return refundOrderNoHashVal;
	}

	public void setRefundOrderNoHashVal(Integer refundOrderNoHashVal) {
		this.refundOrderNoHashVal = refundOrderNoHashVal;
	}

	public String getRefundFeeLabel() {
		if (refundFee != null) {
			refundFeeLabel = new BigDecimal(refundFee).divide(new BigDecimal(100)).toString();
		} else {
			refundFeeLabel = "0";
		}
		return refundFeeLabel;
	}

	public void setRefundFeeLabel(String refundFeeLabel) {
		this.refundFeeLabel = refundFeeLabel;
	}

}
