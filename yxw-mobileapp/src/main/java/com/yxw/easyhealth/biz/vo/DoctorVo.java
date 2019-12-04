package com.yxw.easyhealth.biz.vo;

import java.io.Serializable;
import com.yxw.interfaces.vo.register.Doctor;

/**
 * 挂号-->号源缓存格式化-->医生VO信息
 * 
 * @version 1.0
 * @since 2015年5月15日
 */
public class DoctorVo extends Doctor implements Serializable, Comparable<DoctorVo>{
	
	private static final long serialVersionUID = -4998481198021141484L;

	private int titleNo;
	
	public DoctorVo() {
		super();
	}

	public DoctorVo(int titleNo) {
		super();
		this.titleNo = titleNo;
	}

	public int getTitleNo() {
		return titleNo;
	}

	public void setTitleNo(int titleNo) {
		this.titleNo = titleNo;
	}

	@Override
	public int compareTo(DoctorVo o) {
		return this.getTitleNo() < o.getTitleNo()?-1:(this.getTitleNo() > o.getTitleNo()?1:0); 
	}

}
