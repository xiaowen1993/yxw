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

package com.yxw.task.vo;

import java.io.Serializable;

/**
 * 
 * @Package: com.yxw.task.vo
 * @ClassName: StopReg
 * @Statement: <p>停诊号源实体类(附带分院code)</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年8月5日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class StopRegTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6090723371551219020L;
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
	 * 分院code
	 */
	private String branchHospitalCode;

	public StopRegTask() {
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
	 */
	public StopRegTask(String hisOrdNum, String psOrdNum, String deptCode, String deptName, String doctorCode, String doctorName, String reason) {
		super();
		this.hisOrdNum = hisOrdNum;
		this.psOrdNum = psOrdNum;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.reason = reason;
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

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

}
