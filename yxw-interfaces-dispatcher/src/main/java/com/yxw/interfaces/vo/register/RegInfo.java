/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->号源信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class RegInfo extends Reserve implements Serializable {

	private static final long serialVersionUID = -6058272882806587319L;
	/**
	 * 号源日期
	 */
	private String scheduleDate;
	/**
	 * 医生集合
	 */
	private List<Doctor> doctors;

	public RegInfo() {
		super();
	}

	/**
	 * @param scheduleDate
	 * @param doctors
	 */

	public RegInfo(String scheduleDate, List<Doctor> doctors) {
		super();
		this.scheduleDate = scheduleDate;
		this.doctors = doctors;
	}

	/**
	 * @return the scheduleDate
	 */

	public String getScheduleDate() {
		return scheduleDate;
	}

	/**
	 * @param scheduleDate
	 *            the scheduleDate to set
	 */

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	/**
	 * @return the doctors
	 */

	public List<Doctor> getDoctors() {
		return doctors;
	}

	/**
	 * @param doctors
	 *            the doctors to set
	 */

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

}
