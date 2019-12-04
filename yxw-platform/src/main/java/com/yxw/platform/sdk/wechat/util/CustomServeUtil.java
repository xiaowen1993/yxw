package com.yxw.platform.sdk.wechat.util;

import com.alibaba.fastjson.JSONObject;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;

/**
 * 客服管理
 * 
 * @author luob
 * @date 2015年6月8日
 * */
public class CustomServeUtil {

	/**
	 * 添加客服账户
	 * 
	 * @param kf_account
	 * @param password
	 * @param nickname
	 * */
	public String[] addCustomAccount(String kf_account, String nickname, String password, String appId, String appSecret) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.ADD_KFACCOUNT + tocken;
		JSONObject reqStr = new JSONObject();
		reqStr.put("kf_account", kf_account);
		reqStr.put("password", password);
		reqStr.put("nickname", nickname);
		String[] result = HTTPClient.http_post(postURL, null, reqStr.toJSONString(), "utf-8");
		return result;
	}

	/**
	 * 修改客服账户
	 * 
	 * @param kf_account
	 * @param password
	 * @param nickname
	 * */
	public String[] editCustomAccount(String kf_account, String nickname, String password, String appId, String appSecret) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.EDIT_KFACCOUNT + tocken;
		JSONObject reqStr = new JSONObject();
		reqStr.put("kf_account", kf_account);
		reqStr.put("password", password);
		reqStr.put("nickname", nickname);
		String[] result = HTTPClient.http_post(postURL, null, reqStr.toJSONString(), "utf-8");
		return result;
	}

	/**
	 * 删除客服账户
	 * 
	 * @param kf_account
	 * @param password
	 * @param nickname
	 * */
	public String[] deleteCustomAccount(String kf_account, String nickname, String password, String appId, String appSecret) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.DELETE_KFACCOUNT + tocken + "&kf_account=" + kf_account + "&nickname=" + nickname + "&password=" + password;
		String[] result = HTTPClient.http_get(postURL, null, "utf-8", null);
		return result;
	}
}
