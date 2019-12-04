/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.interfaces.vo.hospitalized;

import java.io.Serializable;
import java.util.List;

import com.yxw.interfaces.vo.Reserve;

/**
 * 住院患者服务-->出院带药
 * @Package: com.yxw.interfaces.entity.hospitalized
 * @ClassName: OutMedicine
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月26日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class OutMedicine extends Reserve implements Serializable {

	private static final long serialVersionUID = 423557311117013106L;
	
	/**
	 * 住院科室
	 */
	private String deptName;
	/**
	 * 病床号
	 */
	private String bedNo;
	/**
	 * 其他说明
	 */
	private String otherDesc;
	/**
	 * 药品集合
	 */
	private List<Drug> drugs;
	
	public OutMedicine() {
		super();
	}

	public OutMedicine(String deptName, String bedNo, String otherDesc) {
		super();
		this.deptName = deptName;
		this.bedNo = bedNo;
		this.otherDesc = otherDesc;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getOtherDesc() {
		return otherDesc;
	}

	public void setOtherDesc(String otherDesc) {
		this.otherDesc = otherDesc;
	}

	public List<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(List<Drug> drugs) {
		this.drugs = drugs;
	}
	
}
