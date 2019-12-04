package com.yxw.easyhealth.biz.vo;

import com.yxw.commons.vo.cache.CommonParamsVo;

public class QueuesParamsVo extends CommonParamsVo {

	private static final long serialVersionUID = -4719121962108459574L;

	/**
	 * 查询候诊类型 
	 * @see com.yxw.mobileapp.biz.queues.QueueConstants
	 */
	private Integer queueType;
	
	/**
	 * 诊疗卡类型
	 */
	private String patCardType;

	/**
	 * 诊疗卡号
	 */
	private String patCardNo;

	/**
	 * 诊疗人姓名
	 */
	private String patCardName;
	
	private String familyId;
	
	public QueuesParamsVo() {
		super();
	}
	
	public QueuesParamsVo(CommonParamsVo vo) {
		super(vo.getHospitalCode(), vo.getHospitalId(), vo.getHospitalName(), vo.getBranchHospitalCode(), vo.getBranchHospitalId(), vo
				.getBranchHospitalName(), vo.getAppId(), vo.getAppCode(), vo.getBizCode(), vo.getOpenId());
	}

	public QueuesParamsVo(Integer queueType, String patCardType, String patCardNo, String patCardName) {
		super();
		this.queueType = queueType;
		this.patCardType = patCardType;
		this.patCardNo = patCardNo;
		this.patCardName = patCardName;
	}

	public Integer getQueueType() {
		return queueType;
	}

	public void setQueueType(Integer queueType) {
		this.queueType = queueType;
	}

	public String getPatCardType() {
		return patCardType;
	}

	public void setPatCardType(String patCardType) {
		this.patCardType = patCardType;
	}

	public String getPatCardNo() {
		return patCardNo;
	}

	public void setPatCardNo(String patCardNo) {
		this.patCardNo = patCardNo;
	}

	public String getPatCardName() {
		return patCardName;
	}

	public void setPatCardName(String patCardName) {
		this.patCardName = patCardName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

}
