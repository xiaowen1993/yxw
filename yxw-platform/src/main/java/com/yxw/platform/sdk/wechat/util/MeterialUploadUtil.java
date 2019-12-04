package com.yxw.platform.sdk.wechat.util;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;

/**
 * 素材管理
 * 
 * @author luob
 * @date 2015年6月8日
 * */
public class MeterialUploadUtil {

	private static Logger logger = LoggerFactory.getLogger(MeterialUploadUtil.class);

	public static String multiUpload(String appId, String secret, String ImgUrl) throws Exception {
		String id = null;
		String accessToken = WechatSDK.getAccessToken(appId, secret);
		if (accessToken != null) {
			File file = new File(ImgUrl);
			if ((file != null) && (file.exists())) {
				String type = "image";
				if (file.getName().toLowerCase().endsWith(".mp4")) {
					type = "video";
				}
				if (file.getName().toLowerCase().endsWith(".mp3")) {
					type = "voice";
				}
				if (file.getName().toLowerCase().endsWith(".amr")) {
					type = "voice";
				}
				id = upload(WechatConstant.UPLOAD_TEMP_METERIAL, accessToken, type, file);
			}
		}
		return id;
	}

	/**
	 * 上传微信图文专用图片
	 * */
	public static String uploadWechatPic(String appId, String secret, String ImgUrl) {
		HttpClient client = new HttpClient();
		String accessToken = WechatSDK.getAccessToken(appId, secret);
		String uploadurl = String.format("%s?access_token=%s", new Object[] { WechatConstant.UPLOAD_MixedMETERIAL_PIC, accessToken });
		PostMethod post = new PostMethod(uploadurl);
		post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		post.setRequestHeader("Host", "file.api.weixin.qq.com");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		File file = new File(ImgUrl);
		String result = null;
		try {
			if ((file != null) && (file.exists())) {
				FilePart filepart = new FilePart("media", file, "image/jpeg", "UTF-8");
				Part[] parts = { filepart };
				MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
				post.setRequestEntity(entity);
				int status = client.executeMethod(post);
				if (status == 200) {
					String responseContent = post.getResponseBodyAsString();
					// JsonParser jsonparer = new JsonParser();
					// JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
					JSONObject json = JSONObject.parseObject(responseContent);
					if (json != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("上传微信图文专用图片uploadWechatPic方法，入参ImgUrl：{}", ImgUrl);
							logger.debug("上传微信图文专用图片uploadWechatPic方法，返回json：{}", json.toString());
						}
						if (json.get("url") != null) {
							result = json.get("url").toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传临时素材
	 * */
	public static String upload(String url, String access_token, String type, File file) {
		HttpClient client = new HttpClient();
		String uploadurl = String.format("%s?access_token=%s&type=%s", new Object[] { url, access_token, type });
		PostMethod post = new PostMethod(uploadurl);
		post.setRequestHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:30.0) Gecko/20100101 Firefox/30.0");
		post.setRequestHeader("Host", "file.api.weixin.qq.com");
		post.setRequestHeader("Connection", "Keep-Alive");
		post.setRequestHeader("Cache-Control", "no-cache");
		String result = null;
		try {
			if ((file != null) && (file.exists())) {
				FilePart filepart = new FilePart("media", file, "image/jpeg", "UTF-8");
				if (file.getName().toLowerCase().endsWith(".mp4")) {
					filepart = new FilePart("media", file, "video/mpeg4", "UTF-8");
				}
				if (file.getName().toLowerCase().endsWith(".mp3")) {
					filepart = new FilePart("media", file, "audio/mp3", "UTF-8");
				}
				if (file.getName().toLowerCase().endsWith(".amr")) {
					filepart = new FilePart("media", file, "audio/AMR", "UTF-8");
				}
				Part[] parts = { filepart };
				MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
				post.setRequestEntity(entity);
				int status = client.executeMethod(post);
				if (status == 200) {
					String responseContent = post.getResponseBodyAsString();
					// JsonParser jsonparer = new JsonParser();
					// JsonObject json = jsonparer.parse(responseContent).getAsJsonObject();
					JSONObject json = JSONObject.parseObject(responseContent);
					if (json.get("errcode") == null) {
						if (type.equals("thumb")) {
							result = json.get("thumb_media_id").toString();
						} else {
							result = json.get("media_id").toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 上传永久素材 返回MEDIA_ID
	 * */
	public String uploadPermanentMeterial(String jsonMeterial, String appId, String appSecret) {
		String accessToken = WechatSDK.getAccessToken(appId, appSecret);
		// 拼装创建菜单的url
		String url = WechatConstant.UPLOAD_PARMANENT_METERIAL + accessToken;
		String[] result = HTTPClient.http_post(url, null, jsonMeterial, "utf-8");
		return result[0];
	}

}
