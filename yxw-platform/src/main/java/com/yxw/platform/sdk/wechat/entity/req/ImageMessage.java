package com.yxw.platform.sdk.wechat.entity.req;

import com.yxw.platform.sdk.wechat.entity.resp.Image;

/**
 * 图片消息
 * 
 */
public class ImageMessage extends BaseMessage {
	// 图片链接
	private String PicUrl;

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
}
