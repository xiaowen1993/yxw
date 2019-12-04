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

package com.yxw.interfaces.vo.register;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 挂号-->号源信息-->医生信息
 * 
 * @author 申午武
 * @version 1.0
 * @since 2015年5月15日
 */

public class Doctor extends Reserve implements Serializable {

	private static final long serialVersionUID = -4998481198021141484L;
	/**
	 * 类别 ,见DoctorType
	 * @see com.yxw.interfaces.constants.DoctorType
	 */
	private String category;
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
	 * 医生/专科职称 ,若是专科，此字段返回"专科号"
	 */
	private String doctorTitle;
	/**
	 * 医生/专科简介
	 */
	private String doctorIntrodution;
	/**
	 * 医生性别,若是专科,此字段返回空字符串
	 */
	private String doctorSex;
	/**
	 * 出生日期,若是专科,此字段返回空字符串
	 */
	private String doctorBirth;
	/**
	 * 医生擅长,若是专科,此字段返回空字符串
	 */
	private String doctorSkill;
	/**
	 * 照片,可供外网访问的url路径
	 */
	private String picture;

	/**
	 * 剩余可预约号源总数
	 */
	private String leftTotalNum;
	/**
	 * 明细信息集合
	 */
	private List<Detail> details;

	public Doctor() {
		super();
	}

	/**
	 * @param category
	 * @param deptCode
	 * @param deptName
	 * @param doctorCode
	 * @param doctorName
	 * @param doctorTitle
	 * @param doctorIntrodution
	 * @param doctorSex
	 * @param doctorBirth
	 * @param doctorSkill
	 * @param picture
	 * @param leftTotalNum
	 * @param details
	 */
	public Doctor(String category, String deptCode, String deptName, String doctorCode, String doctorName, String doctorTitle,
			String doctorIntrodution, String doctorSex, String doctorBirth, String doctorSkill, String picture, String leftTotalNum,
			List<Detail> details) {
		super();
		this.category = category;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.doctorTitle = doctorTitle;
		this.doctorIntrodution = doctorIntrodution;
		this.doctorSex = doctorSex;
		this.doctorBirth = doctorBirth;
		this.doctorSkill = doctorSkill;
		this.picture = picture;
		this.leftTotalNum = leftTotalNum;
		this.details = details;
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
	 * @return the category
	 */

	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */

	public void setCategory(String category) {
		this.category = category;
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

	/**
	 * @return the leftTotalNum
	 */
	public String getLeftTotalNum() {
		return leftTotalNum;
	}

	/**
	 * @param leftTotalNum the leftTotalNum to set
	 */
	public void setLeftTotalNum(String leftTotalNum) {
		this.leftTotalNum = leftTotalNum;
	}

	/**
	 * @return the details
	 */

	public List<Detail> getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */

	public void setDetails(List<Detail> details) {
		this.details = details;
	}

}
