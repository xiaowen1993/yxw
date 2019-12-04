package com.yxw.commons.vo;

import java.util.List;

/**
 * 医院-消息VO
 * */
public class MsgPushHospitalVo {

	private String hospitalid;
	private String hospitalname;
	private List<MsgPushVo> msgPushList;

	public String getHospitalid() {
		return hospitalid;
	}

	public void setHospitalid(String hospitalid) {
		this.hospitalid = hospitalid;
	}

	public String getHospitalname() {
		return hospitalname;
	}

	public void setHospitalname(String hospitalname) {
		this.hospitalname = hospitalname;
	}

	public List<MsgPushVo> getMsgPushList() {
		return msgPushList;
	}

	public void setMsgPushList(List<MsgPushVo> msgPushList) {
		this.msgPushList = msgPushList;
	}

}
