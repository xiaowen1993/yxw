package com.yxw.platform.sdk.wechat.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;

/**
 * 
 * wechat消息推送
 * 
 * */
public class ExtensionUtil {

	private static Logger logger = LoggerFactory.getLogger(ExtensionUtil.class);

	/**
	 * 获取二维码
	 * @param appId
	 * @param appSecret
	 * @param qcJson
	 * @return
	 */
	public static JSONObject qrcodeCreate(String appId, String appSecret, String qcJson) {
		String tocken = WechatSDK.getAccessToken(appId, appSecret);
		String postURL = WechatConstant.QRCODE_CREATE + tocken;
		//		HttpResponse response = new HttpClientUtil().post(postURL, qcJson, HttpConstants.JSON_TYPE, HttpConstants.CHARACTER_ENCODING_UTF8);
		String responseString = HttpClientUtil.getInstance().post(postURL, qcJson);
		if (StringUtils.isNotBlank(responseString)) {
			return JSONObject.parseObject(responseString);
		} else {
			return null;
		}

	}

}
