package com.yxw.mobileapp.biz.clinic.vo;

import org.apache.commons.lang3.StringUtils;

import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.cache.CommonParamsVo;

/**
 * 查询条件  -- 基本除了OpenId、appCode 其他都是非必要，看业务选择
 * 		  -- 对于数据打通的问题，如果有多个平台，是不是需要使用appCode作为一个条件？？
 * 功能概要：
 * @author Administrator
 * @date 2017年3月29日
 */
public class ClinicQueryVo extends CommonParamsVo {

	private static final long serialVersionUID = -8401161971418146259L;

	/**
	 * 可选条件 --支付状态
	 */
	private Integer payStatus;

	/**
	 * 可选条件 -- 业务状态
	 */
	private Integer clinicStatus;

	/**
	 * 可选条件 -- 记录ID
	 */
	private String id;

	/**
	 * 可选条件 -- 订单号
	 */
	private String orderNo;

	/**
	 * 可选条件 -- his业务编号
	 */
	private String mzFeeId;

	/**
	 * 可选条件 -- 开始时间
	 */
	private Long beginTime;

	/**
	 * 可选条件 -- 结束时间
	 */
	private Long endTime;

	private String hashTableName;

	/**
	 * 诊疗卡号
	 */
	private String cardNo;

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getClinicStatus() {
		return clinicStatus;
	}

	public void setClinicStatus(Integer clinicStatus) {
		this.clinicStatus = clinicStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getClinicRecordHashTable(openId, true);
		}
		return hashTableName;
	}

	public void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
