/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年12月11日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.commons.entity.mobile.biz.vaccinate;

import com.yxw.base.entity.BaseEntity;

/**
 * 接种疫苗实体
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年12月11日
 */
public class Vaccinate extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -528313021151977962L;

	/**
	 * 地区名
	 */
	private String regionName;

	/**
	 * 接种门诊名称
	 */
	private String vaccinateClinicName;

	/**
	 * 接种门诊地址
	 */
	private String vaccinateClinicAddr;

	/**
	 * 接种门诊电话
	 */
	private String vaccinateClinicTel;

	/**
	 * 区域编码
	 */
	private String areaCode;

	/**
	 * 区域名称
	 */
	private String areaName;

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getVaccinateClinicName() {
		return vaccinateClinicName;
	}

	public void setVaccinateClinicName(String vaccinateClinicName) {
		this.vaccinateClinicName = vaccinateClinicName;
	}

	public String getVaccinateClinicAddr() {
		return vaccinateClinicAddr;
	}

	public void setVaccinateClinicAddr(String vaccinateClinicAddr) {
		this.vaccinateClinicAddr = vaccinateClinicAddr;
	}

	public String getVaccinateClinicTel() {
		return vaccinateClinicTel;
	}

	public void setVaccinateClinicTel(String vaccinateClinicTel) {
		this.vaccinateClinicTel = vaccinateClinicTel;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

}
