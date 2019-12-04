package com.yxw.commons.entity.platform.message;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.yxw.base.entity.BaseEntity;

/**
 * 混合素材实体类
 * */
public class MixedMeterial extends BaseEntity {

	private static final long serialVersionUID = 5521903788702475698L;

	/**
	 * 标题
	 * */
	private String title;
	/**
	 * 作者
	 * */
	private String author;
	/**
	 * 摘要
	 * */
	private String description;
	/**
	 * 正文
	 * */
	private String content;
	/**
	 * 链接
	 * */
	private String link;
	/**
	 * 封面图片
	 * */
	private String coverPicPath;
	/**
	 * 微信图片
	 * */
	private String wechatPicPath;
	/**
	 * 图文类型 1 单图文 2多图文
	 * */
	private int type;

	/**
	 * 删除状态（1未删除 0已删除）
	 * */
	private int state;
	/**
	 * 是否是父素材
	 * */
	private int isParent;

	/**
	 * 父素材ID
	 * */
	private String parentId;
	/**
	 * 医院ID
	 * */
	private String hospitalId;

	private List<MixedMeterial> subMixedMeterialList = new ArrayList<MixedMeterial>();

	public MixedMeterial() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCoverPicPath() {
		return coverPicPath;
	}

	public void setCoverPicPath(String coverPicPath) {
		this.coverPicPath = coverPicPath;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<MixedMeterial> getSubMixedMeterialList() {
		return subMixedMeterialList;
	}

	public void setSubMixedMeterialList(List<MixedMeterial> subMixedMeterialList) {
		this.subMixedMeterialList = subMixedMeterialList;
	}

	@Override
	public String toString() {
		try {
			Base64 base64 = new Base64();
			StringBuffer substr = new StringBuffer();
			if (subMixedMeterialList != null && subMixedMeterialList.size() > 0) {
				for (MixedMeterial m : subMixedMeterialList) {

					substr.append("{\"id\":\"" + m.getId() + "\",\"title\":\"" + base64.encodeAsString(m.getTitle().getBytes("utf-8"))
							+ "\", \"author\":\"" + base64.encodeAsString(m.getAuthor().getBytes("utf-8")) + "\", \"description\":\""
							+ base64.encodeAsString(m.getDescription().getBytes("utf-8")) + "\", \"content\":\""
							+ base64.encodeAsString(m.getContent().getBytes("utf-8")) + "\", \"link\":\"" + m.getLink() + "\", \"coverPicPath\":\""
							+ m.getCoverPicPath() + "\", \"type\":\"" + m.getType() + "\", \"state\":\"" + m.getState() + "\", \"isParent\":\""
							+ m.getIsParent() + "\", \"parentId\":\"" + m.getParentId() + "\"},");

				}
			}
			return "{\"id\":\"" + id + "\",\"title\":\"" + base64.encodeAsString(title.getBytes("utf-8")) + "\", \"author\":\""
					+ base64.encodeAsString(author.getBytes("utf-8")) + "\", \"description\":\""
					+ base64.encodeAsString(description.getBytes("utf-8")) + "\", \"content\":\"" + base64.encodeAsString(content.getBytes("utf-8"))
					+ "\", \"link\":\"" + link + "\", \"coverPicPath\":\"" + coverPicPath + "\", \"type\":\"" + type + "\", \"state\":\"" + state
					+ "\", \"isParent\":\"" + isParent + "\", \"parentId\":\"" + parentId + "\", \"subMixedMeterialList\":["
					+ (substr.length() == 0 ? "" : substr.substring(0, substr.length() - 1)) + "]}";
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * @return the wechatPicPath
	 */
	public String getWechatPicPath() {
		return wechatPicPath;
	}

	/**
	 * @param wechatPicPath
	 *            the wechatPicPath to set
	 */
	public void setWechatPicPath(String wechatPicPath) {
		this.wechatPicPath = wechatPicPath;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

}
