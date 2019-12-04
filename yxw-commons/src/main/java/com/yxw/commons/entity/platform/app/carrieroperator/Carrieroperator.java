package com.yxw.commons.entity.platform.app.carrieroperator;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * APP 运营管理实体类
 * 
 * @author 刘德华
 *
 */
@Entity(name = "carrieroperator")
public class Carrieroperator extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5176257012929619319L;

	/**
	 * 运营标题
	 */
	private String title;

	/**
	 * 运营位置 1启动页;2首页轮播
	 */
	private String operationPosition;

	//iPhone尺寸
	/**
	 * 运营图片地址
	 */
	private String starPtic640x960;
	/**
	 * 运营图片地址
	 */
	private String starPtic640x1136;
	/**
	 * 运营图片地址
	 */
	private String starPtic750x1334;
	/**
	 * 运营图片地址
	 */
	private String starPtic1242x2208;

	//安卓尺寸
	/**
	 * 运营图片地址
	 */
	private String starPtic320x480;
	/**
	 * 运营图片地址
	 */
	private String starPtic480x800;
	/**
	 * 运营图片地址
	 */
	private String starPtic1080x1920;

	/**
	 * 轮播图片
	 */
	private String shufflingPic;

	/**
	 * 运营内容类型：html/url
	 */
	private String contentType;
	/**
	 * 运营内容
	 */
	private String content;
	/**
	 * 内容排序，不允许重复
	 */
	private String sorting;
	/**
	 * 发布状态 0 发布；1 不发布
	 */
	private String status;

	/**
	 * 备用字段
	 */
	private String aux1;
	/**
	 *
	 */
	private String aux2;

	public Carrieroperator() {
		super();
	}

	public Carrieroperator(String title, String operationPosition, String starPtic640x960, String starPtic640x1136, String starPtic750x1334, String starPtic1242x2208, String starPtic320x480,
	        String starPtic480x800, String starPtic1080x1920, String shufflingPic, String contentType, String content, String sorting, String status, String aux1, String aux2) {
		super();
		this.title = title;
		this.operationPosition = operationPosition;
		this.starPtic640x960 = starPtic640x960;
		this.starPtic640x1136 = starPtic640x1136;
		this.starPtic750x1334 = starPtic750x1334;
		this.starPtic1242x2208 = starPtic1242x2208;
		this.starPtic320x480 = starPtic320x480;
		this.starPtic480x800 = starPtic480x800;
		this.starPtic1080x1920 = starPtic1080x1920;
		this.shufflingPic = shufflingPic;
		this.contentType = contentType;
		this.content = content;
		this.sorting = sorting;
		this.status = status;
		this.aux1 = aux1;
		this.aux2 = aux2;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOperationPosition() {
		return operationPosition;
	}

	public void setOperationPosition(String operationPosition) {
		this.operationPosition = operationPosition;
	}

	public String getStarPtic640x960() {
		return starPtic640x960;
	}

	public void setStarPtic640x960(String starPtic640x960) {
		this.starPtic640x960 = starPtic640x960;
	}

	public String getStarPtic640x1136() {
		return starPtic640x1136;
	}

	public void setStarPtic640x1136(String starPtic640x1136) {
		this.starPtic640x1136 = starPtic640x1136;
	}

	public String getStarPtic750x1334() {
		return starPtic750x1334;
	}

	public void setStarPtic750x1334(String starPtic750x1334) {
		this.starPtic750x1334 = starPtic750x1334;
	}

	public String getStarPtic1242x2208() {
		return starPtic1242x2208;
	}

	public void setStarPtic1242x2208(String starPtic1242x2208) {
		this.starPtic1242x2208 = starPtic1242x2208;
	}

	public String getStarPtic320x480() {
		return starPtic320x480;
	}

	public void setStarPtic320x480(String starPtic320x480) {
		this.starPtic320x480 = starPtic320x480;
	}

	public String getStarPtic480x800() {
		return starPtic480x800;
	}

	public void setStarPtic480x800(String starPtic480x800) {
		this.starPtic480x800 = starPtic480x800;
	}

	public String getStarPtic1080x1920() {
		return starPtic1080x1920;
	}

	public void setStarPtic1080x1920(String starPtic1080x1920) {
		this.starPtic1080x1920 = starPtic1080x1920;
	}

	public String getShufflingPic() {
		return shufflingPic;
	}

	public void setShufflingPic(String shufflingPic) {
		this.shufflingPic = shufflingPic;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSorting() {
		return sorting;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAux1() {
		return aux1;
	}

	public void setAux1(String aux1) {
		this.aux1 = aux1;
	}

	public String getAux2() {
		return aux2;
	}

	public void setAux2(String aux2) {
		this.aux2 = aux2;
	}

}