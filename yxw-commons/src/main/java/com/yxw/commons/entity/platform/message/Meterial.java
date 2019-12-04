package com.yxw.commons.entity.platform.message;

import com.yxw.base.entity.BaseEntity;

/**
 * 单一素材实体类
 * */
public class Meterial extends BaseEntity {

	private static final long serialVersionUID = -2292200414348375120L;

	/**
	 * 素材分组ID
	 * */
	private String meterialGroupId;

	/**
	 * 素材资源名称
	 * */
	private String name;

	/**
	 * 素材资源路径
	 * */
	private String path;

	/**
	 * 素材资源大小
	 * */
	private String size;
	/**
	 * 素材资源时长
	 * */
	private String duration;
	/**
	 * 视频封面
	 * */
	private String coverPicPath;
	/**
	 * 类型（1.图片 2.语音 3.视频）
	 * */
	private int type;
	/**
	 * 删除状态（1未删除 0已删除）
	 * */
	private int state;

	/**
	 * 医院ID
	 * */
	private String hospitalId;

	public Meterial() {
	}

	public String getMeterialGroupId() {
		return meterialGroupId;
	}

	public void setMeterialGroupId(String meterialGroupId) {
		this.meterialGroupId = meterialGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getCoverPicPath() {
		return coverPicPath;
	}

	public void setCoverPicPath(String coverPicPath) {
		this.coverPicPath = coverPicPath;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

}
