package com.yxw.platform.vo;

import java.util.List;

/**
 * 医院-消息VO
 * */
public class MeterialHospitalVO {

	private String hospitalid;
	private String hospitalname;
	private List<MeterialVO> meterialList;

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

	public List<MeterialVO> getMeterialList() {
		return meterialList;
	}

	public void setMeterialList(List<MeterialVO> meterialList) {
		this.meterialList = meterialList;
	}
}
