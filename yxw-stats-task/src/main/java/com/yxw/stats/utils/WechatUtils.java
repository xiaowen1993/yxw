package com.yxw.stats.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.stats.constants.CommConstants;
import com.yxw.stats.task.platform.collect.StatisticalCollector;

public class WechatUtils {
	public static Logger logger = LoggerFactory.getLogger(StatisticalCollector.class);

	public static Map<String, JSONObject> WECHAT_ACCESSTOKEN_MAP = Collections.synchronizedMap(new HashMap<String, JSONObject>());

	/**
	 * 调用公共获取 AccessToken 的接口（项目医院）
	
	 * @param appId
	 */
	public static String getAccessTokenByPublicInterface(String appId) {

		String accessToken = "";

		// {"errcode":40013,"errmsg":"invalid appid"}

		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("operation", "get");

		String result = HttpClientUtil.getInstance().post("http://hw21.yx129.net/interface/s_yxw/GetAccessToken", params);
		if (!StringUtils.isBlank(result)) {
			JO jo = new JO(result);
			accessToken = jo.getStr("accesstoken", "");
		}

		return accessToken;

	}

	/**
	 * 获取标准平台医院的accesstoken
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static String getAccessTokenByPublicInterface(String appId, String appSecret) {

		String accessToken = "";

		// {"access_token":"access_token","expires_in":7200}，新增属性getTime（获取的时间）
		JSONObject accessTokenJO = WECHAT_ACCESSTOKEN_MAP.get(appId);
		if (accessTokenJO != null) {
			// 判断超时否
			long getTime = accessTokenJO.getLongValue("getTime");
			long expiresIn = 1000l * ( accessTokenJO.getIntValue("expires_in") / 2 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				accessToken = getAccessTokenByWechat(appId, appSecret);
			} else {
				// 没超时
				accessToken = accessTokenJO.getString("access_token");
			}
		} else {
			accessToken = getAccessTokenByWechat(appId, appSecret);
		}

		return accessToken;

	}

	/**
	 * 去微信服务器获取AccessToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return AccessToken
	 */
	public static String getAccessTokenByWechat(String appId, String appSecret) {
		String accessToken = "";

		StringBuffer url = new StringBuffer();
		url.append(CommConstants.WECHAT_API_GATEWAY).append("/cgi-bin/token?grant_type=client_credential").append("&appid=").append(appId)
				.append("&secret=").append(appSecret);

		String result = HttpClientUtil.getInstance().get(url.toString(), new HashMap<String, String>());

		JO jo = new JO(StringUtils.defaultIfBlank(result, "{}"));

		if (jo.containsKey("access_token")) {
			accessToken = jo.getStr("access_token", "");
			jo.put("getTime", System.currentTimeMillis());

			WECHAT_ACCESSTOKEN_MAP.put(appId, jo.getMyJSONObject());
		} else {
			logger.error("appId：{} get accessToken is error,reponse infos：{}", appId, result);
		}

		logger.debug("reget wx access token : {}", accessToken);
		return accessToken;

	}

	public static void main(String[] args) {
		//		String a = getAccessTokenByWechat("wx7245e798af225271", "f1bde17843f944d509469dd72ee8a6d1");
		//		System.out.println(a);
		String accessToken =
				"19_dfIjLRIz8jklvYWFFEcjAohQHj3omT-ENABg5sqovI2JzdxesHg5eBHAr1FYRbM1kNPg40I89uJU9gTg-fscIkccSiiwWfWz6QSXZdaKVRpg1u9ptcmASL0WixQCVKsN0Ge1Mv2dCVHgIRkGKXGgADAYLU";
		String xx = createQRCode(accessToken);
		JSONObject jsonObject = JSONObject.parseObject(xx);
		String ticket = (String) jsonObject.get("ticket");
		System.out.println(ticket);

	}

	public static String createQRCode(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken;
		String params =
				"{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"aihscoaihscoaihscoiahscoihasoch\"}}}";

		String result = HttpClientUtil.getInstance().post(url.toString(), params);
		System.out.println(result);
		return result;
	}

	/**
	 * 获取用户增减数据
	 * 
	 * @param accessToken
	 * @param beginDate
	 * @param endDate
	 */
	public static String getUserSummary(String accessToken, String beginDate, String endDate) {
		//datacube/getusersummary?access_token=ACCESS_TOKEN

		String postURL = CommConstants.WECHAT_API_GATEWAY.concat("/datacube/getusersummary?access_token=").concat(accessToken);

		JO reqStr = new JO();
		reqStr.put("begin_date", beginDate);
		reqStr.put("end_date", endDate);

		String result = HttpClientUtil.getInstance().post(postURL, reqStr.toJsonString());

		return result;
	}

	/**
	 * 获取累计用户数据
	 * 
	 * @param accessToken
	 * @param beginDate
	 * @param endDate
	 */
	public static String getUserCumulate(String accessToken, String beginDate, String endDate) {
		//datacube/getusercumulate?access_token=ACCESS_TOKEN

		String postURL = CommConstants.WECHAT_API_GATEWAY.concat("/datacube/getusercumulate?access_token=").concat(accessToken);

		JO reqStr = new JO();
		reqStr.put("begin_date", beginDate);
		reqStr.put("end_date", endDate);

		String result = HttpClientUtil.getInstance().post(postURL, reqStr.toJsonString());

		return result;
	}

	/**
	 * 获取用户列表
	 * 
	 * @param accessToken
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 */
	public static String getUsers(String accessToken, String nextOpenId) {
		//cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID

		String postURL =
				CommConstants.WECHAT_API_GATEWAY.concat("/cgi-bin/user/get?access_token=").concat(accessToken).concat("&next_openid=")
						.concat(nextOpenId == null ? "" : nextOpenId);

		String result = HttpClientUtil.getInstance().post(postURL, new HashMap<String, String>());

		return result;
	}

	/**
	 * 获取用户总数
	 * 
	 * @param accessToken
	 */
	public static int getUserTotal(String accessToken) {
		String res = getUsers(accessToken, "");
		if (res != null) {
			return new JO(res).getInt("total", -1);
		}
		return -1;
	}
}
