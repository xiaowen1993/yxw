package com.yxw.commons.entity.mobile.biz.vote;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.MsgPushEntity;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.hash.SimpleHashTableNameGenerator;
import com.yxw.commons.vo.MessagePushParamsVo;

public class VoteInfo extends MsgPushEntity implements Serializable, Comparable<VoteInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2188859426581689805L;

	/**
	 * 医院主键ID
	 */
	private String hospitalId;

	/**
	 * 医院编码
	 */
	private String hospitalCode;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 医院对应的appId
	 */
	private String appId;

	/**
	 * 医院对应的appCode
	 */
	private String appCode;

	/**
	 * 
	 */
	private String openId;

	private ClinicRecord record;

	/**
	 * 就诊卡号
	 */
	private String cardNo;

	/**
	 * 就诊人
	 */
	private String patientName;

	/**
	 * 业务编码 
	 * 业务编号(2位  不足2位的补0)  挂号:01    门诊:02    住院:03<br>
	 */
	private String bizCode;

	/**
	 * 评价级别   0  踩    1 赞
	 */
	private Short appraiseLevel;

	/**
	 * 服务态度
	 */
	private Short serviceLevel;

	/**
	 * 技术专业
	 */
	private Short skillLevel;

	/**
	 * 建议或意见
	 */
	private String suggestion;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 
	 */
	private Integer orderNoHashVal;

	/**
	 * 被评价事物的编码  eg:医生的编码
	 */
	private String raterCode;

	/**
	 * 是否已评价  
	 * 		BizConstant.VOTE_IS_HAD_YES = 1  已评价
	 *      BizConstant.VOTE_IS_HAD_NO = 0 未评价
	 */
	private Integer isHadVote;

	/**
	 * 标题
	 */
	private String voteTitle;

	/**
	 * 实体类对应的hash子表
	 * 该属性只在数据库操作时定位tableName 不入库
	 */
	protected String hashTableName;

	/**
	 * 评价完后的跳转地址  不入库 不需要跳转设置为null
	 */
	protected String fowardUrl;

	/**
	 * 创建时间
	 */
	protected Long createTime;

	protected String createTimeStr;

	/**
	 * 评价时间
	 */
	protected Long updateTime;
	protected String updateTimeStr;

	public VoteInfo() {
		super();
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId == null ? null : hospitalId.trim();
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName == null ? null : hospitalName.trim();
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId == null ? null : appId.trim();
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode == null ? null : appCode.trim();
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo == null ? null : cardNo.trim();
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode == null ? null : bizCode.trim();
	}

	public Short getAppraiseLevel() {
		return appraiseLevel;
	}

	public void setAppraiseLevel(Short appraiseLevel) {
		this.appraiseLevel = appraiseLevel;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion == null ? null : suggestion.trim();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Integer getOrderNoHashVal() {
		return orderNoHashVal;
	}

	public void setOrderNoHashVal(Integer orderNoHashVal) {
		this.orderNoHashVal = orderNoHashVal;
	}

	public String getRaterCode() {
		return raterCode;
	}

	public void setRaterCode(String raterCode) {
		this.raterCode = raterCode == null ? null : raterCode.trim();
	}

	public String getHashTableName() {
		if (StringUtils.isBlank(hashTableName)) {
			hashTableName = SimpleHashTableNameGenerator.getVoteHashTable(openId, true);
		}
		return hashTableName;
	}

	@SuppressWarnings("unused")
	private void setHashTableName(String hashTableName) {
		this.hashTableName = hashTableName;
	}

	public String getFowardUrl() {
		return fowardUrl;
	}

	public void setFowardUrl(String fowardUrl) {
		this.fowardUrl = fowardUrl;
	}

	public Integer getIsHadVote() {
		return isHadVote;
	}

	public void setIsHadVote(Integer isHadVote) {
		this.isHadVote = isHadVote;
	}

	public String getVoteTitle() {
		return voteTitle;
	}

	public void setVoteTitle(String voteTitle) {
		this.voteTitle = voteTitle;
	}

	public Short getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(Short serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Short getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(Short skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getCreateTimeStr() {
		if (createTime != null) {
			Date date = new Date(createTime);
			createTimeStr = BizConstant.YYYYMMDDHHMM.format(date);
		}
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MessagePushParamsVo convertMessagePushParams() {
		// TODO Auto-generated method stub
		MessagePushParamsVo params = new MessagePushParamsVo();
		params.setHospitalId(this.hospitalId);
		params.setAppId(this.appId);

		params.setPlatformType(appCode);

		params.setOpenId(this.openId.replaceAll(" ", "+"));

		Map<String, Serializable> dataMap = JSON.parseObject(JSON.toJSONString(this), Map.class);
		dataMap.putAll(JSON.parseObject(JSON.toJSONString(this.record), Map.class));

		//
		String urlParms =
				BizConstant.URL_PARAM_CHAR_FIRST.concat(BizConstant.URL_PARAM_ORDER_NO).concat(BizConstant.URL_PARAM_CHAR_ASSGIN)
						.concat(this.orderNo).concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_OPEN_ID)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.openId).concat(BizConstant.URL_PARAM_CHAR_CONCAT)
						.concat(BizConstant.URL_PARAM_APPID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.appId)
						.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_APPCODE)
						.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(this.appCode).concat("&bizCode=2&viewType=noVote");
		dataMap.put(BizConstant.MSG_PUSH_URL_PARAMS_KEY, urlParms);
		params.setParamMap(dataMap);
		return params;
	}

	public ClinicRecord getRecord() {
		return record;
	}

	public void setRecord(ClinicRecord record) {
		this.record = record;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeStr() {
		if (updateTime != null) {
			Date date = new Date(updateTime);
			updateTimeStr = BizConstant.YYYYMMDDHHMM.format(date);
		}
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	@Override
	public int compareTo(VoteInfo o) {
		// TODO Auto-generated method stub
		if (o == null) {
			return 1;
		} else {
			if (updateTime == null && createTime != null) {
				if (this.createTime > o.getCreateTime()) {
					return -1;
				} else if (this.createTime == o.getCreateTime()) {
					return 0;
				} else {
					return 1;
				}
			} else if (updateTime != null && createTime != null) {
				if (this.updateTime > o.getUpdateTime()) {
					return -1;
				} else if (this.updateTime == o.getUpdateTime()) {
					return 0;
				} else {
					return 1;
				}
			} else {
				return 0;
			}
		}
	}
}