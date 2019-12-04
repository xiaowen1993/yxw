package com.yxw.platform.vo;

import java.util.List;

/**
 * 医院-消息VO
 * */
public class MsgHospitalVO {

	private String hospitalid;
	private String hospitalname;
	private List<MsgReplyVO> replyList;

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

	public List<MsgReplyVO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<MsgReplyVO> replyList) {
		this.replyList = replyList;
	}

}
