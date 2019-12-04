package com.yxw.platform.sdk.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;
import com.yxw.platform.sdk.wechat.entity.MenuWechat;

public class WechatMenuUtil {

	private static Logger logger = LoggerFactory.getLogger(WechatMenuUtil.class);

	public static JSONObject createMenuStr(MenuWechat menu, String appId, String appSecret) {

		String jsonMenu = JSONObject.toJSONString(menu);
		// 获取accessToken
		String accessToken = WechatSDK.getAccessToken(appId, appSecret);
		if (logger.isDebugEnabled()) {
			logger.debug("accessToken-------" + accessToken);
		}
		// 拼装创建菜单的url
		String url = WechatConstant.MENU_CRAETE_URL + accessToken;
		// 将菜单对象转换成json字符串
		// String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		//		HttpResponse response = new HttpClientUtil().post(url, jsonMenu, HttpConstants.JSON_TYPE, HttpConstants.CHARACTER_ENCODING_UTF8);

		String responseString = HttpClientUtil.getInstance().post(url, jsonMenu);
		return responseString != null ? JSONObject.parseObject(responseString) : null;
	}

	/**
	 * 获取菜单
	 */
	public static JSONObject getMenu(String appId, String appSecret) {

		// 获取accessToken
		String accessToken = WechatSDK.getAccessToken(appId, appSecret);
		String url = WechatConstant.MENU_GET_URL + accessToken;
		// 调用接口创建菜单
		String responseString = HttpClientUtil.getInstance().get(url, null);
		return responseString != null ? JSONObject.parseObject(responseString) : null;
	}

	/**
	 * 删除菜单
	 */
	public static JSONObject deleteMenu(String appId, String appSecret) {
		// 获取accessToken
		String accessToken = WechatSDK.getAccessToken(appId, appSecret);
		String url = WechatConstant.MENU_DELETE_URL + accessToken;
		// 调用接口创建菜单
		//		HttpResponse response = new HttpClientUtil().get(url);
		//		return response.asJSONObject();
		String responseString = HttpClientUtil.getInstance().get(url, null);
		return responseString != null ? JSONObject.parseObject(responseString) : null;
	}

}
