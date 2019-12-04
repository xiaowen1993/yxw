/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月15日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register.stopreg;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->停诊-->医生停诊信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class StopReg extends Reserve implements Serializable {

	private static final long serialVersionUID = 5036561059972134067L;
	/**
	 * 医院预约流水号
	 */
	private String hisOrdNum;
	/**
	 * 公众服务平台订单号
	 */
	private String psOrdNum;
	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 科室名称
	 */
	private String deptName;
	/**
	 * 医生代码
	 */
	private String doctorCode;
	/**
	 * 医生名称
	 */
	private String doctorName;
	/**
	 * 停诊原因
	 */
	private String reason;
	/**
	 * 停诊开始时间
	 */
	private String beginTime;
	/**
	 * 停诊结束时间
	 */
	private String endTime;

	public StopReg() {
		super();
	}

	/**
	 * @param hisOrdNum
	 * @param psOrdNum
	 * @param deptCode
	 * @param deptName
	 * @param doctorCode
	 * @param doctorName
	 * @param reason
	 * @param beginTime
	 * @param endTime
	 */
	public StopReg(String hisOrdNum, String psOrdNum, String deptCode, String deptName, String doctorCode, String doctorName, String reason,
			String beginTime, String endTime) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.reason = reason;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	/**
	 * @return the beginTime
	 */
	public String getBeginTime() {
		return beginTime;
	}

	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	 * @return the deptName
	 */

	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            the deptName to set
	 */

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
	 * @return the doctorName
	 */

	public String getDoctorName() {
		return doctorName;
	}

	/**
	 * @param doctorName
	 *            the doctorName to set
	 */

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	/**
	 * @return the reason
	 */

	public String getReason() {
		return reason;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the hisOrdNum
	 */
	public String getHisOrdNum() {
		return hisOrdNum;
	}

	/**
	 * @param hisOrdNum the hisOrdNum to set
	 */
	public void setHisOrdNum(String hisOrdNum) {
		this.hisOrdNum = hisOrdNum;
	}

	/**
	 * @return the psOrdNum
	 */
	public String getPsOrdNum() {
		return psOrdNum;
	}

	/**
	 * @param psOrdNum the psOrdNum to set
	 */
	public void setPsOrdNum(String psOrdNum) {
		this.psOrdNum = psOrdNum;
	}

}
