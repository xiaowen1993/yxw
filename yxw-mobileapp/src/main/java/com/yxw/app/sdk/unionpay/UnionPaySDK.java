/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年3月16日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.app.sdk.unionpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.yxw.app.sdk.SDKPublicArgs;

/**
 * 银联钱包SDK
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2017年3月7日
 */
public class UnionPaySDK {

	private static String GRANT_TYPE = "authorization_code";

	/**
	 * 获取授权跳转地址
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String scope, String redirectUrl) throws UnsupportedEncodingException {
		String oauth2Url = "";

		oauth2Url += SDKPublicArgs.UNIONPAY_OPENAUTH_GATEWAY + "?responseType=code";
		//oauth2Url += "&scope=upapi_userinfo&state=getUserInfo";
		//		oauth2Url += "&scope=upapi_quick&state=getUserInfo";
		oauth2Url += "&scope=" + scope;
		oauth2Url += "&state=getUserInfo";
		oauth2Url += "&appId=" + appId;
		oauth2Url += "&redirectUri=" + URLEncoder.encode(redirectUrl, "utf-8");

		return oauth2Url;
	}

	/**
	 * 去内存获取FrontToken，有超时机制
	 * 
	 * @param appId
	 * @param secret
	 * @return FrontToken
	 */
	public static String getFrontToken(String appId, String secret) {
		String frontToken = "";
		JSONObject btJson = SDKPublicArgs.unionPayFrontTokenMap.get(appId);
		if (btJson != null) {
			// 判断超时否
			long getTime = btJson.getLongValue("getTime");
			long expiresIn = 1000L * ( (Integer) JSONPath.eval(btJson, "$.params.expiresIn") - 1000 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				frontToken = getFrontTokenByUnionPay(appId, secret);
			} else {
				// 没超时
				frontToken = (String) JSONPath.eval(btJson, "$.params.frontToken");
			}
		} else {
			frontToken = getFrontTokenByUnionPay(appId, secret);
		}
		return frontToken;
	}

	/**
	 * 去银联服务器获取FrontToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return FrontToken
	 */
	public static String getFrontTokenByUnionPay(String appId, String secret) {
		String frontToken = "";

		String url = SDKPublicArgs.UNIONPAY_GATEWAY + "/open/access/1.0/frontToken";

		JSONObject params = new JSONObject();
		params.put("appId", appId);
		params.put("secret", secret);

		SDKPublicArgs.logger.info("getFrontTokenByUnionPay.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = SDKPublicArgs.httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = SDKPublicArgs.httpClient.post(url, params.toJSONString());

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {
				frontToken = (String) JSONPath.eval(jo, "$.params.frontToken");

				jo.put("getTime", System.currentTimeMillis() + "");

				SDKPublicArgs.unionPayFrontTokenMap.put(appId, jo);
			}
		}

		SDKPublicArgs.logger.debug("reget unionpay front token : {}", frontToken);
		return frontToken;
	}

	/**
	 * 去内存获取BackendToken，有超时机制
	 * 
	 * @param appId
	 * @param secret
	 * @return BackendToken
	 */
	public static String getBackendToken(String appId, String secret) {
		String backendToken = "";
		JSONObject btJson = SDKPublicArgs.unionPayBackendTokenMap.get(appId);
		if (btJson != null) {
			// 判断超时否
			long getTime = btJson.getLongValue("getTime");
			long expiresIn = 1000L * ( (Integer) JSONPath.eval(btJson, "$.params.expiresIn") - 1000 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				backendToken = getBackendTokenByUnionPay(appId, secret);
			} else {
				// 没超时
				backendToken = (String) JSONPath.eval(btJson, "$.params.backendToken");
			}
		} else {
			backendToken = getBackendTokenByUnionPay(appId, secret);
		}
		return backendToken;
	}

	/**
	 * 去银联服务器获取BackendToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return BackendToken
	 */
	public static String getBackendTokenByUnionPay(String appId, String secret) {
		String backendToken = "";

		String url = SDKPublicArgs.UNIONPAY_GATEWAY + "/open/access/1.0/backendToken";

		JSONObject params = new JSONObject();
		params.put("appId", appId);
		params.put("secret", secret);

		SDKPublicArgs.logger.info("getBackendTokenByUnionPay.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = SDKPublicArgs.httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = SDKPublicArgs.httpClient.post(url, params.toJSONString());

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {
				backendToken = (String) JSONPath.eval(jo, "$.params.backendToken");

				jo.put("getTime", System.currentTimeMillis() + "");

				SDKPublicArgs.unionPayBackendTokenMap.put(appId, jo);
			}
		}

		SDKPublicArgs.logger.debug("reget unionpay backend token : {}", backendToken);
		return backendToken;
	}

	/**
	 * 去银联服务器获取AccessToken
	 * 
	 * @param appId
	 * @param backendToken
	 * @param code - request.getParameter("code"); 
	 * 注意: 返回的code是通过encodeURIComponent经过URL编码的，接入方需要通过函数 decodeURIComponent 解码去获取 code.
	 * @return JSON 
	 * {
		"accessToken":"ACCESSTOKEN", 
		"expiresIn":"7200", 
		"refreshToken":"REFRESHTOKEN", 
		"openId":"OPENID", 
		"scope":"SCOPE"
	   }
	 */
	public static JSONObject getAccessTokenAndOpenIdByUnionPay(String appId, String backendToken, String code) {
		String url = SDKPublicArgs.UNIONPAY_GATEWAY + "/open/access/1.0/token";

		JSONObject params = new JSONObject();
		params.put("appId", appId);
		params.put("backendToken", backendToken);
		params.put("code", code);
		params.put("grantType", GRANT_TYPE);

		SDKPublicArgs.logger.info("getAccessTokenAndOpenIdByUnionPay.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = SDKPublicArgs.httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = SDKPublicArgs.httpClient.post(url, params.toJSONString());

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			return jo.getJSONObject("params");
		}

		return null;
	}

	/**
	 * 银联钱包获取用户信息
	 * 
	 * @param appId
	 * @param openId
	 * @param accessToken
	 * @return JSON
	 */
	public static JSONObject getUserInfo(String appId, String openId, String accessToken) {
		String url = SDKPublicArgs.UNIONPAY_GATEWAY + "/open/access/1.0/oauth.userInfo";

		JSONObject params = new JSONObject();
		params.put("accessToken", accessToken);
		params.put("openId", openId);
		params.put("appId", appId);

		SDKPublicArgs.logger.info("getUserInfo.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = SDKPublicArgs.httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = SDKPublicArgs.httpClient.post(url, params.toJSONString());

		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {

			}

			return jo.getJSONObject("params");
		}
		return null;
	}

	/**
	 * 银联钱包获取用户信息
	 * 
	 * @param appId
	 * @param openId
	 * @param accessToken
	 * @return JSON
	 */
	public static JSONObject getUserInfo(String appId, String openId, String accessToken, String backendToken) {
		String url = SDKPublicArgs.UNIONPAY_GATEWAY + "/open/access/1.0/oauth.userAuthInfo";

		JSONObject params = new JSONObject();
		params.put("accessToken", accessToken);
		params.put("openId", openId);
		params.put("appId", appId);
		params.put("backendToken", backendToken);

		SDKPublicArgs.logger.info("getUserInfo.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = SDKPublicArgs.httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = SDKPublicArgs.httpClient.post(url, params.toJSONString());
		SDKPublicArgs.logger.info("getUserInfo.res: {}", rs);
		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {

			}

			return jo.getJSONObject("params");
		}
		return null;
	}

	/**
	 * 银联钱包获取用户信息-手机号码
	 * 
	 * @param appId
	 * @param openId
	 * @param accessToken
	 * @return JSON
	 */
	public static JSONObject getUserInfoForMobile(String appId, String openId, String accessToken, String backendToken) {
		String url = SDKPublicArgs.UNIONPAY_GATEWAY + "/open/access/1.0/user.mobile";

		JSONObject params = new JSONObject();
		params.put("accessToken", accessToken);
		params.put("openId", openId);
		params.put("appId", appId);
		params.put("backendToken", backendToken);

		SDKPublicArgs.logger.info("getUserInfo.url: {}, params: {}", url, params.toJSONString());

		//		HttpResponse rs = SDKPublicArgs.httpClient.post(url, params.toJSONString(), HttpConstants.HTML_TYPE, "utf-8");
		String rs = SDKPublicArgs.httpClient.post(url, params.toJSONString());
		SDKPublicArgs.logger.info("getUserInfo.res: {}", rs);
		if (rs != null) {
			JSONObject jo = JSONObject.parseObject(rs);
			if (jo.containsKey("resp") && "00".equals(jo.getString("resp"))) {

			}

			return jo.getJSONObject("params");
		}
		return null;
	}

	/**
	 * UNION PAY UPSDK 签名算法
	 * @param params
	 * 
	 * @return Signature
	 */
	public static String getUPSDKSignature(Map<String, String> params) {
		String signature = "";
		StringBuilder signatureBefore = new StringBuilder();

		String[] keys = new String[params.size()];
		params.keySet().toArray(keys);

		Arrays.sort(keys);

		for (String k : keys) {
			String value = params.get(k);
			if (StringUtils.isNotBlank(value)) {
				signatureBefore.append(k).append("=").append(params.get(k)).append("&");
			}
		}

		System.out.println(StringUtils.stripEnd(signatureBefore.toString(), "&"));

		signature = DigestUtils.sha256Hex(StringUtils.stripEnd(signatureBefore.toString(), "&"));

		return signature;
	}

	public static void main(String[] args) {
		//		Map<String, String> unionPayUPSDKParams = new HashMap<String, String>();
		//
		//		unionPayUPSDKParams.put("fronttoken", "Oa017intQJS1K6D1G13BWA==");
		//		unionPayUPSDKParams.put("noncestr", "FQYT3TcSsEHKy5Vq");
		//		unionPayUPSDKParams.put("timestamp", "1489547372");
		//		unionPayUPSDKParams.put("url", "http://pt.yx129.cn/easyhealth/unionpay/demo/test");
		//
		//		System.out.println(getUPSDKSignature(unionPayUPSDKParams));

		String appId = "1e4adaa4e8164aa894c115ac84b6c718";
		String secret = "2f06f4a9715149fcb34ef25fd9842474";
		System.out.println(getBackendTokenByUnionPay(appId, secret));
	}

}
