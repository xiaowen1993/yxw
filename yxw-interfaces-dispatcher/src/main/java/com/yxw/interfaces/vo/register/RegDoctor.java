/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年5月14日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */

package com.yxw.interfaces.vo.register;

import java.io.Serializable;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->可挂号医生
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月14日
 */

public class RegDoctor extends Reserve implements Serializable {

	private static final long serialVersionUID = -4929853033455055656L;
	/**
	 * 医院代码,医院没有分院则返回空字符串
	 */
	private String branchCode;
	/**
	 * 医院名称,医院没有分院则返回空字符串
	 */
	private String branchName;
	/**
	 * 科室代码
	 */
	private String deptCode;
	/**
	 * 科室名称
	 */
	private String deptName;
	/**
	 * 医生代码,若只存在医生代码或只存在医生工号，则两者返回相同的值
	 */
	private String doctorCode;
	/**
	 * 医生工号
	 */
	private String doctorNo;
	/**
	 * 医生名称
	 */
	private String doctorName;
	/**
	 * 医生性别
	 */
	private String doctorSex;
	/**
	 * 出生日期
	 */
	private String doctorBirth;
	/**
	 * 医生电话
	 */
	private String doctorTelephone;
	/**
	 * 医生擅长
	 */
	private String doctorSkill;
	/**
	 * 医生简介
	 */
	private String doctorIntrodution;
	/**
	 * 医生职称
	 */
	private String doctorTitle;
	/**
	 * 照片,可供外网访问的url路径(要jpg，200X200px以上大小，最好是生活照（医生自己觉得自己最满意的照片）。如果没有，工作照也可以)
	 */
	private String picture;

	public RegDoctor() {
		super();
	}

	/**
	 * @param branchCode
	 * @param branchName
	 * @param deptCode
	 * @param deptName
	 * @param doctorCode
	 * @param doctorNo
	 * @param doctorName
	 * @param doctorSex
	 * @param doctorBirth
	 * @param doctorTelephone
	 * @param doctorSkill
	 * @param doctorIntrodution
	 * @param doctorTitle
	 * @param picture
	 */

	public RegDoctor(String branchCode, String branchName, String deptCode, String deptName, String doctorCode, String doctorNo, String doctorName,
			String doctorSex, String doctorBirth, String doctorTelephone, String doctorSkill, String doctorIntrodution, String doctorTitle,
			String picture) {
		super();
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorNo = doctorNo;
		this.doctorName = doctorName;
		this.doctorSex = doctorSex;
		this.doctorBirth = doctorBirth;
		this.doctorTelephone = doctorTelephone;
		this.doctorSkill = doctorSkill;
		this.doctorIntrodution = doctorIntrodution;
		this.doctorTitle = doctorTitle;
		this.picture = picture;
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
	 * @return the branchName
	 */

	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 *            the branchName to set
	 */

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	 * @return the doctorNo
	 */

	public String getDoctorNo() {
		return doctorNo;
	}

	/**
	 * @param doctorNo
	 *            the doctorNo to set
	 */

	public void setDoctorNo(String doctorNo) {
		this.doctorNo = doctorNo;
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
	 * @return the doctorSex
	 */

	public String getDoctorSex() {
		return doctorSex;
	}

	/**
	 * @param doctorSex
	 *            the doctorSex to set
	 */

	public void setDoctorSex(String doctorSex) {
		this.doctorSex = doctorSex;
	}

	/**
	 * @return the doctorBirth
	 */

	public String getDoctorBirth() {
		return doctorBirth;
	}

	/**
	 * @param doctorBirth
	 *            the doctorBirth to set
	 */

	public void setDoctorBirth(String doctorBirth) {
		this.doctorBirth = doctorBirth;
	}

	/**
	 * @return the doctorTelephone
	 */

	public String getDoctorTelephone() {
		return doctorTelephone;
	}

	/**
	 * @param doctorTelephone
	 *            the doctorTelephone to set
	 */

	public void setDoctorTelephone(String doctorTelephone) {
		this.doctorTelephone = doctorTelephone;
	}

	/**
	 * @return the doctorSkill
	 */

	public String getDoctorSkill() {
		return doctorSkill;
	}

	/**
	 * @param doctorSkill
	 *            the doctorSkill to set
	 */

	public void setDoctorSkill(String doctorSkill) {
		this.doctorSkill = doctorSkill;
	}

	/**
	 * @return the doctorIntrodution
	 */

	public String getDoctorIntrodution() {
		return doctorIntrodution;
	}

	/**
	 * @param doctorIntrodution
	 *            the doctorIntrodution to set
	 */

	public void setDoctorIntrodution(String doctorIntrodution) {
		this.doctorIntrodution = doctorIntrodution;
	}

	/**
	 * @return the doctorTitle
	 */

	public String getDoctorTitle() {
		return doctorTitle;
	}

	/**
	 * @param doctorTitle
	 *            the doctorTitle to set
	 */

	public void setDoctorTitle(String doctorTitle) {
		this.doctorTitle = doctorTitle;
	}

	/**
	 * @return the picture
	 */

	public String getPicture() {
		return picture;
	}

	/**
	 * @param picture
	 *            the picture to set
	 */

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( deptCode == null ) ? 0 : deptCode.hashCode() );
		result = prime * result + ( ( doctorCode == null ) ? 0 : doctorCode.hashCode() );
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegDoctor other = (RegDoctor) obj;
		if (deptCode == null) {
			if (other.deptCode != null)
				return false;
		} else if (!deptCode.equals(other.deptCode))
			return false;
		if (doctorCode == null) {
			if (other.doctorCode != null)
				return false;
		} else if (!doctorCode.equals(other.doctorCode))
			return false;
		return true;
	}

}
