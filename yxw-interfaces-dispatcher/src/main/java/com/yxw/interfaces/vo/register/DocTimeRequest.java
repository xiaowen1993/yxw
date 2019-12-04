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

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->医生分时查询信息请求参数
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class DocTimeRequest extends Reserve implements Serializable {

	private static final long serialVersionUID = 5605351394025597470L;
	/**
	 * 医院代码,医院没有分院则传入空字符串；医院存在分院时不允许为空
	 */
	private String branchCode;
	/**
	 * 号源日期,格式：YYYY-MM-DD
	 */
	private String scheduleDate;
	/**
	 * 科室代码,该字段不允许为空
	 */
	private String deptCode;
	/**
	 * 医生/专科代码
	 */
	private String doctorCode;
	/**
	 * 时段,见TimeType
	 * @see com.yxw.interfaces.constants.TimeType
	 */
	private String timeFlag;

	public DocTimeRequest() {
		super();
	}

	/**
	 * @param branchCode
	 * @param scheduleDate
	 * @param deptCode
	 * @param doctorCode
	 * @param timeFlag
	 */

	public DocTimeRequest(String branchCode, String scheduleDate, String deptCode, String doctorCode, String timeFlag) {
		super();
		this.branchCode = branchCode;
		this.scheduleDate = scheduleDate;
		this.deptCode = deptCode;
		this.doctorCode = doctorCode;
		this.timeFlag = timeFlag;
	}

	/**
	 * @return the branchCode
	 */

	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode
	 *            the branchCode to set
	 */

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	 * @return the deptCode
	 */

	public String getDeptCode() {
		return deptCode;
	}

	/**
	 * @param deptCode
	 *            the deptCode to set
	 */

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * @return the doctorCode
	 */

	public String getDoctorCode() {
		return doctorCode;
	}

	/**
	 * @param doctorCode
	 *            the doctorCode to set
	 */

	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}

	/**
	 * @return the timeFlag
	 */

	public String getTimeFlag() {
		return timeFlag;
	}

	/**
	 * @param timeFlag
	 *            the timeFlag to set
	 */

	public void setTimeFlag(String timeFlag) {
		this.timeFlag = timeFlag;
	}

}
