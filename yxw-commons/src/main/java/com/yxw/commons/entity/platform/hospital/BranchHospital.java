/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月14日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.commons.entity.platform.hospital;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * 分院实体类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月14日
 */
@Entity(name = "branchHospital")
public class BranchHospital extends BaseEntity implements Comparable<BranchHospital> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 139744036948716043L;

	/**
	 * 分院代码
	 */
	private String code;

	/**
	 * 接口ID
	 */
	private String interfaceId;

	/**
	 * 分院名称
	 */
	private String name;

	/**
	 * 分院地址
	 */
	private String address;

	/**
	 * 分院电话
	 */
	private String tel;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	private String hospitalCode;
	/**
	 * 经度
	 */
	private String longitude;
	/**
	 * 纬度
	 */
	private String latitude;

	/**
	 * @return 分院代码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            分院代码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return 接口ID
	 */
	public String getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId
	 *            接口ID
	 */
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return 分院名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            分院名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 分院地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            分院地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return 分院电话
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel
	 *            分院电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the hospitalId
	 */
	public String getHospitalId() {
		return hospitalId;
	}

	/**
	 * @param hospitalId
	 *            the hospitalId to set
	 */
	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public int compareTo(BranchHospital compareObj) {
		// TODO Auto-generated method stub
		if (compareObj != null && compareObj.getCt() != null) {
			long compareObjCt = compareObj.getCt().getTime();
			long thisObjCt = this.ct.getTime();
			if (compareObjCt < thisObjCt) {
				return 1;
			} else if (compareObjCt > thisObjCt) {
				return -1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}
}
