/**
 * <html>
 * <body>
 *  <P>  Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年3月13日</p>
 *  <p> Created by caiwq</p>
 *  </body>
 * </html>
 */
package com.yxw.framework.common.attach.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.yxw.base.entity.BaseEntity;

/**
 * @author caiwq
 * @version 1.0
 */
@Entity(name = "attach")
public class Attach extends BaseEntity {

	private static final long serialVersionUID = 3223446009461902773L;
	/**
	 * attachId
	 */
	private String attachId;
	/**
	 * 原始名称
	 */
	private String originalName;
	/**
	 * 真实名称
	 */
	private String archiveName;
	/**
	 * 相对路径
	 */
	private String relativePath;
	/**
	 * 绝对路径
	 */
	private String absolutePath;
	/**
	 * 扩展名
	 */
	private String extName;
	/**
	 * 附件大小(以字节为单位)
	 */
	private Long attachSize;
	/**
	 * 上传时间
	 */
	private Date uploadDate;
	/**
	 * 上传人ID
	 */
	private String uploadPersonId;
	/**
	 * 上传人名称
	 */
	private String uploadPersonName;

	/**
	 * 文件类型 0：图片 1：文档 2：密钥 3：其他
	 */
	private Integer attachType;

	public Attach() {
	}

	public Attach(String attachId, String originalName, String archiveName, String relativePath, String absolutePath, String extName,
			Long attachSize, Date uploadDate, String uploadPersonId, String uploadPersonName, Integer attachType) {
		this.attachId = attachId;
		this.originalName = originalName;
		this.archiveName = archiveName;
		this.relativePath = relativePath;
		this.absolutePath = absolutePath;
		this.extName = extName;
		this.attachSize = attachSize;
		this.uploadDate = uploadDate;
		this.uploadPersonId = uploadPersonId;
		this.uploadPersonName = uploadPersonName;
		this.attachType = attachType;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	public String getOriginalName() {
		return this.originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getArchiveName() {
		return this.archiveName;
	}

	public void setArchiveName(String archiveName) {
		this.archiveName = archiveName;
	}

	public String getRelativePath() {
		return this.relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getAbsolutePath() {
		return this.absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	public String getExtName() {
		return this.extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	public Long getAttachSize() {
		return this.attachSize;
	}

	public void setAttachSize(Long attachSize) {
		this.attachSize = attachSize;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadPersonId() {
		return this.uploadPersonId;
	}

	public void setUploadPersonId(String uploadPersonId) {
		this.uploadPersonId = uploadPersonId;
	}

	public String getUploadPersonName() {
		return this.uploadPersonName;
	}

	public void setUploadPersonName(String uploadPersonName) {
		this.uploadPersonName = uploadPersonName;
	}

	public Integer getAttachType() {
		return attachType;
	}

	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}

}