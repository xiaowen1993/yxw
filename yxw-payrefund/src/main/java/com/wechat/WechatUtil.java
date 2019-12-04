package com.wechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.common.CommonConstant;
import com.common.WechatConstant;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.entity.platform.payrefund.WechatPayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.payrefund.utils.DateUtil;
import com.yxw.payrefund.utils.OrderUtil;
import com.yxw.payrefund.utils.httpClient.HttpProtocolHandler;
import com.yxw.payrefund.utils.httpClient.HttpRequest;
import com.yxw.payrefund.utils.httpClient.HttpResponse;
import com.yxw.payrefund.utils.httpClient.HttpResultType;

/**
 * @author homer.yang
 * @version 1.0
 * @date 2015年7月2日
 */
public class WechatUtil {

	private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

	/**
	 * 获取微信授权跳转地址（snsapi_base）
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String redirectUrl) throws UnsupportedEncodingException {
		return getOAuth2(appId, redirectUrl, WechatConstant.WECHAT_OAUTH2_SCOPE_SNSAPI_BASE, null, null);
	}

	/**
	 * 获取微信授权跳转地址（snsapi_base）
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getComponentOAuth2(String appId, String componentAppid, String redirectUrl) throws UnsupportedEncodingException {
		return getOAuth2(appId, redirectUrl, WechatConstant.WECHAT_OAUTH2_SCOPE_SNSAPI_BASE, WechatConstant.WECHAT_COMPONENT_OAUTH2_STATE,
				componentAppid);
	}

	/**
	 * 获取微信授权跳转地址
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @param scope
	 *            应用授权作用域
	 *            snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）；
	 *            snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String redirectUrl, String scope) throws UnsupportedEncodingException {
		return getOAuth2(appId, redirectUrl, scope, null, null);
	}

	/**
	 * 获取微信授权跳转地址
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @param scope
	 *            应用授权作用域
	 *            snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）；
	 *            snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * @param state
	 *            重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
	 * @param componentAppid
	 * 			  第三方代授权 appId
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String redirectUrl, String scope, String state, String componentAppid)
			throws UnsupportedEncodingException {
		StringBuilder oauth2Url = new StringBuilder();

		oauth2Url.append(WechatConstant.WECHAT_OPEN_AUTH_GATEWAY).append("/connect/oauth2/authorize?response_type=code");
		oauth2Url.append("&scope=").append(scope).append("&state=").append(OrderUtil.ifBlank(state, "getOpenId"));
		oauth2Url.append("&appid=").append(appId);
		oauth2Url.append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "utf-8"));
		if (StringUtils.isNotBlank(componentAppid)) {
			oauth2Url.append("&component_appid=").append(componentAppid);
		}
		oauth2Url.append("#wechat_redirect");

		return oauth2Url.toString();
	}

	public static String getComponentLoginPage(String componentAppId, String preAuthCode, String redirectUrl)
			throws UnsupportedEncodingException {
		return getComponentLoginPage(componentAppId, preAuthCode, redirectUrl, null);
	}

	public static String getComponentLoginPage(String componentAppId, String preAuthCode, String redirectUrl, String authType)
			throws UnsupportedEncodingException {
		StringBuilder oauth2Url = new StringBuilder();

		oauth2Url.append(WechatConstant.WECHAT_MP_GATEWAY).append("/cgi-bin/componentloginpage?component_appid=".concat(componentAppId));
		oauth2Url.append("&pre_auth_code=").append(preAuthCode);
		oauth2Url.append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "utf-8"));
		//要授权的帐号类型， 
		//1则商户扫码后，手机端仅展示公众号、
		//2表示仅展示小程序，
		//3表示公众号和小程序都展示。
		//如果为未制定，则默认小程序和公众号都展示。第三方平台开发者可以使用本字段来控制授权的帐号类型。
		if (StringUtils.isNotBlank(authType)) {
			oauth2Url.append("&auth_type=").append(authType);
		}

		return oauth2Url.toString();
	}

	/**
	 * 微信隐式获取OpenId
	 * 
	 * @param appId
	 * @param appSecret
	 *            微信密匙
	 * @param code
	 *            授权跳转后用request.getParameter("code")取到
	 * @return
	 * {
	 *   "scope":"snsapi_base",
	 *   "openid":"o9ymntzFKpxrueMiYC81NAzTKg_8",
	 *   "expires_in":7200,
	 *   "refresh_token":"I6bpMScRgFdej7mrtQOQNfirCaptraRoSo6E5IQ38bRIQXA4mwteWFw1iFMSOHW29GI5ey7JFelB4tjkQGQMtXMp4HaOKjcKts7WqvkeVCM",
	 *   "access_token":"bATcaZQFshdVnoRNtqhdJVtJP3JD26BmtC6VEgKR0CZPJGPfu824_2tTrJhx2CpBRxVuZ8SS6NEYzMiqLS07il-jXGGyLIfr1aodHUxIPJA"
	 * }
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String getOpenId(String appId, String appSecret, String code) throws HttpException, IOException {
		String openId = "";

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", WechatConstant.WECHAT_OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE);
		params.put("appid", appId);
		params.put("secret", appSecret);
		params.put("code", code);
		logger.info("getOpenId.params: {}", params);
		request.setParameters(params);
		request.setUrl(WechatConstant.WECHAT_API_GATEWAY.concat("/sns/oauth2/access_token"));

		HttpResponse response = httpProtocolHandler.execute(request, null);

		if (response != null) {
			JSONObject jo = JSONObject.parseObject(response.getStringResult());
			logger.info("getOpenId.response: {}", jo);

			openId = jo.getString("openid");

			//unionid 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
			//openId = jo.getString("unionid");
		}

		return openId;
	}

	/**
	 * 微信第三方带授权隐式获取OpenId
	 * 
	 * @param appId
	 * @param code
	 *            授权跳转后用request.getParameter("code")取到
	 * @return
	 * {
	 *   "scope":"snsapi_base",
	 *   "openid":"o9ymntzFKpxrueMiYC81NAzTKg_8",
	 *   "expires_in":7200,
	 *   "refresh_token":"I6bpMScRgFdej7mrtQOQNfirCaptraRoSo6E5IQ38bRIQXA4mwteWFw1iFMSOHW29GI5ey7JFelB4tjkQGQMtXMp4HaOKjcKts7WqvkeVCM",
	 *   "access_token":"bATcaZQFshdVnoRNtqhdJVtJP3JD26BmtC6VEgKR0CZPJGPfu824_2tTrJhx2CpBRxVuZ8SS6NEYzMiqLS07il-jXGGyLIfr1aodHUxIPJA"
	 * }
	 * @throws IOException
	 * @throws HttpException
	 */
	public static String getOpenIdByComponent(String appId, String code, String componentAppId, String componentAccessToken)
			throws HttpException, IOException {
		String openId = "";

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", WechatConstant.WECHAT_OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE);
		params.put("appid", appId);
		params.put("component_appid", componentAppId);
		params.put("code", code);
		params.put("component_access_token", componentAccessToken);
		logger.info("getOpenId.params: {}", params);
		request.setParameters(params);
		request.setUrl(WechatConstant.WECHAT_API_GATEWAY.concat("/sns/oauth2/component/access_token"));

		HttpResponse response = httpProtocolHandler.execute(request, null);

		if (response != null) {
			JSONObject jo = JSONObject.parseObject(response.getStringResult());
			logger.info("getOpenIdByComponent.response: {}", jo);

			openId = jo.getString("openid");

			//unionid 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
			//openId = jo.getString("unionid");
		}

		return openId;
	}

	public static String getUserInfoItem(String openId, String accessToken, String itemKey) throws HttpException, IOException {
		JSONObject jo = getUserInfo(openId, accessToken);
		if (jo != null) {
			return jo.getString(itemKey);
		} else {
			return null;
		}
	}

	public static JSONObject getUserInfo(String openId, String accessToken) throws HttpException, IOException {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken);
		params.put("openid", openId);

		request.setParameters(params);
		request.setUrl(WechatConstant.WECHAT_API_GATEWAY.concat("/cgi-bin/user/info"));

		HttpResponse response = httpProtocolHandler.execute(request, null);

		if (response != null) {
			/*
			{
			    "city":"广州",
			    "country":"中国",
			    "groupid":0,
			    "headimgurl":"http://wx.qlogo.cn/mmopen/Q3auHgzwzM48gy19Q7ojPWftT1nXwgyKhAkqISKKpk5CAJOBDJiaApKwSVwM3xGB0ovuxWmG3NQyh4jJFPfpZ09sic8UibBMViaRRFVVjIsdD4o/0",
			    "language":"zh_CN",
			    "nickname":"homer.yang",
			    "openid":"olMH9tuNZUtz32ObtBFQM2ypEmsk",
			    "province":"广东",
			    "remark":"",
			    "sex":1,
			    "subscribe":1,
			    "subscribe_time":1438677289
			}
			 */
			JSONObject jo = JSONObject.parseObject(response.getStringResult());
			return jo;
		} else {
			return null;
		}
	}

	public static synchronized String getAccessToken(String appId, String appSecret) throws HttpException, IOException {
		String accessToken = "";

		// {"access_token":"ACCESS_TOKEN","expires_in":7200, "getTime": "133333333333"}
		JSONObject atJson = WechatConstant.wechatAccessTokenMap.get(appId);
		if (atJson != null) {
			// 判断超时否
			long getTime = atJson.getLongValue("getTime");
			long expiresIn = 1000l * ( atJson.getIntValue("expires_in") / 2 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				accessToken = getAccessTokenByWechat(appId, appSecret);
			} else {
				// 没超时
				accessToken = atJson.getString("access_token");
			}
		} else {
			accessToken = getAccessTokenByWechat(appId, appSecret);
		}

		return accessToken;
	}

	private static String getAccessTokenByWechat(String appId, String appSecret) throws HttpException, IOException {
		String accessToken = "";

		// {"errcode":40013,"errmsg":"invalid appid"}
		JSONObject retJson = null;

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "client_credential");
		params.put("appid", appId);
		params.put("secret", appSecret);

		request.setParameters(params);
		request.setUrl(WechatConstant.WECHAT_API_GATEWAY.concat("/cgi-bin/token"));

		HttpResponse response = httpProtocolHandler.execute(request, null);

		if (response != null) {
			retJson = JSONObject.parseObject(response.getStringResult());
			if (retJson.containsKey("access_token")) {
				accessToken = retJson.getString("access_token");
				retJson.put("getTime", System.currentTimeMillis());

				WechatConstant.wechatAccessTokenMap.put(appId, retJson);
			} else {
				logger.error(response.getStringResult());
			}
		} else {
			logger.error("response is null");
		}
		logger.debug("reget wx access token : {}", accessToken);
		return accessToken;
	}

	public static synchronized String
			getComponentAccessToken(String componentAppId, String componentAppSecret, String componentVerifyTicket) throws HttpException,
					IOException {
		String componentAccessToken = "";

		// {"component_access_token":"ACCESS_TOKEN","expires_in":7200, "getTime": "133333333333"}
		JSONObject catJson = WechatConstant.wechatComponentAccessTokenMap.get(componentAppId);
		if (catJson != null) {
			// 判断超时否
			long getTime = catJson.getLongValue("getTime");
			long expiresIn = 1000l * ( catJson.getIntValue("expires_in") / 2 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				componentAccessToken = getComponentAccessTokenByWechat(componentAppId, componentAppSecret, componentVerifyTicket);
			} else {
				// 没超时
				componentAccessToken = catJson.getString("component_access_token");
			}
		} else {
			componentAccessToken = getComponentAccessTokenByWechat(componentAppId, componentAppSecret, componentVerifyTicket);
		}

		return componentAccessToken;
	}

	private static String getComponentAccessTokenByWechat(String componentAppId, String componentAppSecret, String componentVerifyTicket)
			throws HttpException, IOException {
		String componentAccessToken = "";

		JSONObject retJson = null;

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		JSONObject params = new JSONObject();
		//出于安全考虑，在第三方平台创建审核通过后，微信服务器 每隔10分钟会向第三方的消息接收地址推送一次component_verify_ticket，用于获取第三方平台接口调用凭据
		params.put("component_verify_ticket", componentVerifyTicket);
		params.put("component_appid", componentAppId);
		params.put("component_appsecret", componentAppSecret);

		request.setUrl(WechatConstant.WECHAT_API_GATEWAY.concat("/cgi-bin/component/api_component_token"));

		HttpResponse response = httpProtocolHandler.execute(request, params.toJSONString());

		if (response != null) {
			retJson = JSONObject.parseObject(response.getStringResult());
			if (retJson.containsKey("component_access_token")) {
				componentAccessToken = retJson.getString("component_access_token");
				retJson.put("getTime", System.currentTimeMillis());

				WechatConstant.wechatComponentAccessTokenMap.put(componentAppId, retJson);
			} else {
				logger.error(response.getStringResult());
			}
		} else {
			logger.error("response is null");
		}
		logger.debug("reget wx component access token : {}", componentAccessToken);
		return componentAccessToken;
	}

	public static String getComponentPreAuthCode(String componentAppId, String componentAccessToken) throws HttpException, IOException {
		String componentPreAuthCode = "";

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		JSONObject params = new JSONObject();
		params.put("component_appid", componentAppId);
		logger.info("getComponentPreAuthCode.params: {}", params);
		request.setUrl(WechatConstant.WECHAT_API_GATEWAY.concat("/cgi-bin/component/api_create_preauthcode?component_access_token="
				.concat(componentAccessToken)));

		HttpResponse response = httpProtocolHandler.execute(request, params.toJSONString());

		if (response != null) {
			JSONObject jo = JSONObject.parseObject(response.getStringResult());
			logger.info("getComponentPreAuthCode.response: {}", jo);

			componentPreAuthCode = jo.getString("pre_auth_code");
		}

		return componentPreAuthCode;
	}

	/**
	 * 调用公共获取 AccessToken 的接口
	 * @param appId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getAccessTokenByPublicInterface(String appId) throws HttpException, IOException {

		String accessToken = "";

		// {"errcode":40013,"errmsg":"invalid appid"}
		JSONObject retJson = null;

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.INPUT_STREAM);
		// 设置编码集
		request.setCharset("utf-8");

		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("operation", "get");
		//logger.info(params.toString());
		request.setParameters(params);
		request.setUrl(WechatConstant.WECHAT_GET_ACCESS_TOKEN_URL);

		HttpResponse response = httpProtocolHandler.execute(request, null);

		if (response != null) {
			retJson = JSONObject.parseObject(response.getStringResult());
			accessToken = retJson.getString("accesstoken");
		} else {
			logger.error("response is null");
		}
		logger.debug("get wx access token by public interface: {}", accessToken);
		return accessToken;
	}

	/**
	 * 去内存获取JSTicket，有超时机制，有synchronized
	 * 
	 * @param appId
	 * @param appSecret
	 * @return JSTicket
	 * @throws IOException 
	 * @throws HttpException 
	 */
	public static String getJSTicket(String appId, String appSecret) throws HttpException, IOException {
		String ticket = "";

		// {"ticket":"ticket","expires_in":7200, "getTime": "133333333333"}
		JSONObject jt = WechatConstant.wechatJSTicketMap.get(appId);
		if (jt != null) {
			// 判断超时否
			long getTime = jt.getLongValue("getTime");
			long expiresIn = 1000l * ( jt.getIntValue("expires_in") / 2 );
			if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
				// 超时 - 重拿
				ticket = getJSTicketByWechat(appId, appSecret);
			} else {
				// 没超时
				ticket = jt.getString("ticket");
			}
		} else {
			ticket = getJSTicketByWechat(appId, appSecret);
		}

		return ticket;
	}

	/**
	 * 去微信服务器获取JSTicket
	 * 
	 * @param appId
	 * @param appSecret
	 * @return JSTicket
	 * @throws IOException 
	 * @throws HttpException 
	 */
	private static String getJSTicketByWechat(String appId, String appSecret) throws HttpException, IOException {
		String ticket = "";
		// {"errcode":40013,"errmsg":"invalid appid"}
		StringBuffer url = new StringBuffer();
		url.append(WechatConstant.WECHAT_API_GATEWAY).append("/cgi-bin/ticket/getticket?type=jsapi").append("&access_token=")
				.append(getAccessTokenByWechat(appId, appSecret));
		//        url.append(WechatConstant.WECHAT_API_GATEWAY).append("/cgi-bin/ticket/getticket?type=jsapi")
		//        .append("&access_token=").append(getAccessTokenByPublicInterface(appId));

		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");
		request.setUrl(url.toString());

		HttpResponse response = httpProtocolHandler.execute(request, null);

		if (response != null) {
			JSONObject jo = JSONObject.parseObject(response.getStringResult());
			if (jo.containsKey("ticket")) {
				ticket = jo.getString("ticket");
				jo.put("getTime", System.currentTimeMillis());
				WechatConstant.wechatJSTicketMap.put(appId, jo);
			} else {
				logger.error("getJSTicketByWechat.HttpStatus: {}, response: {}", response.getHttpStatus(), response.getStringResult());
			}
		} else {
			logger.error("getJSTicketByWechat.response is null");
		}

		logger.debug("reget wechat JSTicket : {}", ticket);
		return ticket;
	}

	/**
	 * 获取 Wechat JSTicket 签名
	 * 
	 */
	public static String getJSTicketSign(String url, String ticket, String timestamp, String noncestr) {
		return getJSSDKParams(null, url, ticket, timestamp, noncestr).get("signature");
	}

	public static Map<String, String> getJSSDKParams(String appId, String url, String ticket) {
		return getJSSDKParams(appId, url, ticket, null, null);
	}

	/**
	 * 获取 Wechat JSSDK config 所需要的参数, 返回值 key 全小写
	 * 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
	 * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
	 * 这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
	 */
	public static Map<String, String> getJSSDKParams(String appId, String url, String ticket, String timestamp, String noncestr) {
		Map<String, String> params = new HashMap<>();
		params.put("url", url);
		params.put("jsapi_ticket", ticket);

		if (StringUtils.isBlank(timestamp)) {
			timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		}
		if (StringUtils.isBlank(noncestr)) {
			noncestr = OrderUtil.genGUID("@guid32");
		}

		params.put("timestamp", timestamp);
		params.put("noncestr", noncestr);

		StringBuilder sign = new StringBuilder();

		String[] keys = new String[params.size()];
		params.keySet().toArray(keys);

		Arrays.sort(keys);

		for (String k : keys) {
			String value = params.get(k);
			if (StringUtils.isNotBlank(value) && !"sign".equals(k)) {
				sign.append(k).append("=").append(params.get(k)).append("&");
			}
		}

		params.put("signature", DigestUtils.shaHex(StringUtils.stripEnd(sign.toString(), "&")));
		if (StringUtils.isNotBlank(appId)) {
			params.put("appid", appId);
		}

		return params;
	}

	public static String map2Xml(Map<String, String> params) {
		Element xml = DocumentHelper.createElement("xml");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			xml.addElement(entry.getKey()).addCDATA(entry.getValue());
		}

		return xml.asXML();
	}

	public static Map<String, String> xml2Map(Element root) {
		if (root == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		List<Element> elsments = root.elements();
		for (Element element : elsments) {
			map.put(element.getName(), element.getText());
		}

		return map;
	}

	public static Map<String, String> xml2Map(String xml) throws DocumentException {
		return xml2Map(DocumentHelper.parseText(xml).getRootElement());
	}

	/**
	 * 生成签名
	 * 签名生成的通用步骤如下：
		第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
		使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
		特别注意以下重要规则：
		◆ 参数名ASCII码从小到大排序（字典序）；
		◆ 如果参数的值为空不参与签名；
		◆ 参数名区分大小写；
		◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
		◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
		第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
	 * 
	 * @param params
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getSign(Map<String, String> params, String key) {
		StringBuilder sign = new StringBuilder();

		String[] keys = new String[params.size()];
		params.keySet().toArray(keys);

		Arrays.sort(keys);

		for (String k : keys) {
			String value = params.get(k);
			if (StringUtils.isNotBlank(value) && !"sign".equals(k)) {
				sign.append(k).append("=").append(params.get(k)).append("&");
			}
		}
		if (StringUtils.isNotBlank(key)) {
			sign.append("key=").append(key);
		}
		logger.info("sign: {}", sign);

		return DigestUtils.md5Hex(sign.toString()).toUpperCase();
	}

	public static String getSha1Sign(String... params) {
		StringBuffer sb = new StringBuffer();
		// 字符串排序
		Arrays.sort(params);
		//		for (int i = 0; i < params.length; i++) {
		//			sb.append(params[i]);
		//		}
		sb.append(StringUtils.join(params));

		return DigestUtils.shaHex(sb.toString());
	}

	public static boolean checkSign(Map<String, String> params, String key) {
		return params.get("sign").equals(getSign(params, key));
	}

	/**
	 * 商户订单号（每个订单号必须唯一） 组成： mch_id+yyyymmdd+10位一天内不能重复的数字。
	 */
	public static String getMchBillno(String mchId) {
		// String dateString = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date());
		String dateString = DateUtil.formatDate(new Date(), "yyyyMMdd");

		return mchId + dateString + OrderUtil.getFixLenthString(10);
	}

	public static String mapToQueryString(Map<String, String> params) {
		return mapToQueryString(params, null);
	}

	public static String mapToQueryString(Map<String, String> params, String urlCharset) {
		StringBuilder queryString = new StringBuilder();
		if (StringUtils.isBlank(urlCharset)) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			}
		} else {
			try {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					queryString.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), urlCharset)).append("&");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		//return (queryString.length() == 0 ? queryString : queryString.deleteCharAt(queryString.length() - 1)).toString();
		return StringUtils.stripEnd(queryString.toString(), "&");
	}

	public static Map<String, String> genPayParams(WechatPay pay, HospitalCodeAndAppVo vo, String tradeType, String ip, JSONObject attach) {
		return genPayParams(pay, vo.getAppId(), vo.getMchId(), vo.getSubMchId(), vo.getPaykey(), tradeType, ip, attach);
	}

	public static Map<String, String> genPayParams(WechatPay pay, String appId, String mchId, String subMchId, String key,
			String tradeType, String ip, JSONObject attach) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("mch_id", mchId); // 微信支付分配的商户号
		if (StringUtils.isNotBlank(subMchId)) {// 如果存在子商户Id，则为特约商户支付
			params.put("sub_mch_id", subMchId); // 微信支付分配的子商户号
		}
		if (StringUtils.isNotBlank(pay.getOpenId())) {
			//trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
			//子商户支付时，应该传入父商户 appId 对应的 openId
			params.put("openid", pay.getOpenId());
		}

		params.put("nonce_str", OrderUtil.genGUID("@guid32")); // 随机字符串，不长于32位
		params.put("body", pay.getBody());// 商品描述
		params.put("out_trade_no", pay.getOrderNo()); // 商户系统内部的订单号,32个字符内、可包含字母,确保在商户系统唯一
		params.put("total_fee", pay.getTotalFee());
		//APP和网页支付(JSAPI)提交用户端IP，NATIVE支付填调用微信支付API的机器IP。
		params.put("spbill_create_ip", ip);

		params.put("notify_url", WechatConstant.WECHAT_NOTIFY_URL);// 异步接收微信支付成功通知URL			

		//JSAPI，NATIVE，APP
		params.put("trade_type", tradeType);

		//交易起始时间
		//订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
		params.put("time_start", DateUtil.formatDate(new Date(), "yyyyMMddHHmmss"));
		//交易结束时间
		//订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
		//注意：最短失效时间间隔必须大于5分钟
		Long agtTimeout = Long.valueOf(OrderUtil.ifBlank(pay.getAgtTimeout(), WechatConstant.WECHAT_ORDER_TIMEOUT));

		params.put("time_expire", DateUtil.formatDate(System.currentTimeMillis() + agtTimeout * 1000, "yyyyMMddHHmmss"));

		params.put("attach", attach.toJSONString()); // 附加数据，原样返回
		String sign = getSign(params, key);
		logger.debug("订单号:{},统一下单签名(sign)：{}", new Object[] { pay.getOrderNo(), sign });
		params.put("sign", sign);
		return params;
	}

	public static String unifiedOrder(Map<String, String> params) throws HttpException, IOException {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		request.setUrl(WechatConstant.WECHAT_API_MCH_GATEWAY.concat("/pay/unifiedorder"));

		String requestXmlData = map2Xml(params);

		HttpResponse response = httpProtocolHandler.execute(request, requestXmlData);

		if (response == null) {
			return null;
		}

		return response.getStringResult();
	}

	public static Map<String, String> genMicroPayParams(WechatPay pay, HospitalCodeAndAppVo vo, String ip, JSONObject attach) {
		return genMicroPayParams(pay, vo.getAppId(), vo.getMchId(), vo.getSubMchId(), vo.getPaykey(), ip, attach);
	}

	public static Map<String, String> genMicroPayParams(WechatPay pay, String appId, String mchId, String subMchId, String key, String ip,
			JSONObject attach) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("mch_id", mchId); // 微信支付分配的商户号
		if (StringUtils.isNotBlank(subMchId)) {// 如果存在子商户Id，则为特约商户支付
			params.put("sub_mch_id", subMchId); // 微信支付分配的子商户号
		}

		params.put("nonce_str", OrderUtil.genGUID("@guid32")); // 随机字符串，不长于32位
		params.put("body", pay.getBody());// 商品描述
		params.put("out_trade_no", pay.getOrderNo()); // 商户系统内部的订单号,32个字符内、可包含字母,确保在商户系统唯一
		params.put("total_fee", pay.getTotalFee());
		//APP和网页支付(JSAPI)提交用户端IP，NATIVE支付填调用微信支付API的机器IP。
		params.put("spbill_create_ip", ip);

		params.put("attach", attach.toJSONString()); // 附加数据，原样返回
		params.put("auth_code", pay.getAuthCode());

		String sign = getSign(params, key);
		logger.debug("订单号:{},统一下单签名(sign)：{}", new Object[] { pay.getOrderNo(), sign });
		params.put("sign", sign);
		return params;
	}

	public static String microPay(Map<String, String> params) throws HttpException, IOException {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		request.setUrl(WechatConstant.WECHAT_API_MCH_GATEWAY.concat("/pay/micropay"));

		String requestXmlData = map2Xml(params);

		HttpResponse response = httpProtocolHandler.execute(request, requestXmlData);

		if (response == null) {
			return null;
		}

		return response.getStringResult();
	}

	public static Map<String, String> genRefundParams(HospitalCodeAndAppVo vo, WechatPayRefund refund) {
		return genRefundParams(vo.getAppId(), vo.getMchId(), refund.getAgtOrderNo(), refund.getOrderNo(), refund.getRefundOrderNo(),
				refund.getTotalFee(), refund.getRefundFee(), refund.getRefundDesc(), vo.getPaykey());
	}

	public static Map<String, String> genRefundParams(String appId, String mchId, String agtOrderNo, String orderNo, String refundOrderNo,
			String totalFee, String refundFee, String refundDesc, String key) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", appId);
		params.put("mch_id", mchId); // 微信支付分配的商户号
		params.put("nonce_str", OrderUtil.genGUID("@guid32")); // 随机字符串，不长于32位

		//agtOrderNo | orderNo 二选一
		if (StringUtils.isNotBlank(agtOrderNo)) {
			params.put("transaction_id", agtOrderNo);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			params.put("out_trade_no", orderNo);
		}

		params.put("out_refund_no", refundOrderNo);
		params.put("total_fee", totalFee);
		params.put("refund_fee", refundFee);
		if (StringUtils.isNotBlank(refundDesc)) {
			params.put("refund_desc", refundDesc);
		}

		String sign = getSign(params, key);
		logger.debug("订单号:{}, 退费签名(sign)：{}", new Object[] { orderNo, sign });
		params.put("sign", sign);

		return params;
	}

	public static String refund(Map<String, String> params) throws Exception {
		String mchId = params.get("mch_id");
		String appId = params.get("appid");
		return refund(mchId, appId, params, null);
	}

	public static String refund(Map<String, String> params, File cert) throws Exception {
		String mchId = params.get("mch_id");
		String appId = params.get("appid");
		return refund(mchId, appId, params, cert);
	}

	private static String refund(String mchId, String appId, Map<String, String> params, File cert) throws Exception {
		String res = "";

		String charset = "utf-8";
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		if (cert == null) {
			String certPath = CommonConstant.CERTS_PATH.concat(String.valueOf(File.separatorChar)).concat(appId).concat(".p12");
			logger.info("加载微信退费证书：{}", certPath);
			cert = new File(certPath);
		}
		FileInputStream instream = new FileInputStream(cert);
		try {
			keyStore.load(instream, mchId.toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf =
				new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
						SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {

			HttpPost httppost = new HttpPost(WechatConstant.WECHAT_API_MCH_GATEWAY.concat("/secapi/pay/refund"));

			String reqXML = map2Xml(params);

			httppost.setEntity(new StringEntity(reqXML, charset));

			logger.info("executing request : {}", httppost.getRequestLine());

			CloseableHttpResponse response = httpclient.execute(httppost);

			try {
				HttpEntity entity = response.getEntity();

				logger.info("----------------- refund start -----------------------");
				logger.info("response.getStatusLine: {}", response.getStatusLine());
				if (entity != null) {
					logger.info("Response content length: {}", entity.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

					String tempText;
					while ( ( tempText = bufferedReader.readLine() ) != null) {
						res += tempText;
						logger.info(tempText);
					}
				}
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}

			logger.info("----------------- refund end -----------------------");
			logger.info("refund.res: {}", res);
		} finally {
			httpclient.close();
		}

		return res;
	}

	public static WechatPayRefundResponse map2WechatPayRefundResponse(Map<String, String> refundResponseMap) {
		WechatPayRefundResponse response = new WechatPayRefundResponse();

		String returnCode = refundResponseMap.get("return_code");
		String returnMsg = refundResponseMap.get("return_msg");

		String resultCode = refundResponseMap.get("result_code");
		String errCode = refundResponseMap.get("err_code");
		String errCodeDes = refundResponseMap.get("err_code_des");
		if ("SUCCESS".equalsIgnoreCase(returnCode)) {
			if ("SUCCESS".equals(resultCode)) {
				response.setResultCode(BizConstant.METHOD_INVOKE_SUCCESS);
				response.setResultMsg(returnMsg);

				response.setAgtRefundOrderNo(refundResponseMap.get("refund_id"));
				response.setRefundSuccessTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			} else {
				response.setResultCode(BizConstant.METHOD_INVOKE_FAILURE);
				response.setResultMsg(errCode.concat(" : ").concat(errCodeDes));
			}
		} else {
			response.setResultCode(BizConstant.METHOD_INVOKE_FAILURE);
			response.setResultMsg(returnMsg);
		}

		return response;
	}

	public static Map<String, String> genOrderQueryParams(HospitalCodeAndAppVo vo, WechatPayOrderQuery orderQuery) {
		return genOrderQueryParams(vo.getAppId(), vo.getMchId(), orderQuery.getAgtOrderNo(), orderQuery.getOrderNo(), vo.getPaykey());
	}

	public static Map<String, String> genOrderQueryParams(String appId, String mchId, String agtOrderNo, String orderNo, String key) {
		Map<String, String> params = new HashMap<>();
		params.put("appid", appId);
		params.put("mch_id", mchId);
		if (StringUtils.isNotBlank(agtOrderNo)) {
			params.put("transaction_id", agtOrderNo);
		}
		if (StringUtils.isNotBlank(orderNo)) {
			params.put("out_trade_no", orderNo);
		}
		params.put("nonce_str", OrderUtil.genGUID("@guid32")); // 随机字符串，不长于32位
		params.put("sign", getSign(params, key));

		return params;
	}

	public static String orderQuery(Map<String, String> params) throws HttpException, IOException {
		HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();

		HttpRequest request = new HttpRequest(HttpResultType.BYTES);
		// 设置编码集
		request.setCharset("utf-8");

		request.setUrl(WechatConstant.WECHAT_API_MCH_GATEWAY.concat("/pay/orderquery"));

		String requestXmlData = map2Xml(params);

		HttpResponse response = httpProtocolHandler.execute(request, requestXmlData);

		if (response == null) {
			return null;
		}

		return response.getStringResult();

	}

	public static WechatPayOrderQueryResponse map2WechatPayOrderQueryResponse(Map<String, String> orderQueryResponseMap)
			throws ParseException {
		WechatPayOrderQueryResponse response = new WechatPayOrderQueryResponse();

		String returnCode = orderQueryResponseMap.get("return_code");
		String returnMsg = orderQueryResponseMap.get("return_msg");

		String resultCode = orderQueryResponseMap.get("result_code");
		String errCode = orderQueryResponseMap.get("err_code");
		String errCodeDes = orderQueryResponseMap.get("err_code_des");
		if ("SUCCESS".equalsIgnoreCase(returnCode)) {
			if ("SUCCESS".equals(resultCode)) {
				response.setResultCode(BizConstant.METHOD_INVOKE_SUCCESS);
				response.setResultMsg(returnMsg);

				response.setOpenId(orderQueryResponseMap.get("openid"));
				response.setTradeType(orderQueryResponseMap.get("trade_type"));
				response.setTradeState(wechatTradeState2TradeState(orderQueryResponseMap.get("trade_state")));
				response.setBankType(orderQueryResponseMap.get("bank_type"));
				response.setTotalFee(orderQueryResponseMap.get("total_fee"));
				response.setAgtOrderNo(orderQueryResponseMap.get("transaction_id"));
				response.setOrderNo(orderQueryResponseMap.get("out_trade_no"));
				response.setAttach(orderQueryResponseMap.get("attach"));

				String timeEnd = orderQueryResponseMap.get("time_end");
				if (StringUtils.isNotBlank(timeEnd)) {
					response.setTradeTime(DateUtil.formatDate(timeEnd, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));
				}
			} else {
				response.setResultCode(BizConstant.METHOD_INVOKE_FAILURE);
				response.setResultMsg(errCode.concat(" : ").concat(errCodeDes));
			}
		} else {
			response.setResultCode(BizConstant.METHOD_INVOKE_FAILURE);
			response.setResultMsg(returnMsg);
		}

		return response;
	}

	public static WechatPayAsynResponse map2WechatPayAsynResponse(Map<String, String> asynResponseMap) throws ParseException {
		WechatPayAsynResponse response = new WechatPayAsynResponse();

		String returnCode = asynResponseMap.get("return_code");
		String returnMsg = asynResponseMap.get("return_msg");

		String resultCode = asynResponseMap.get("result_code");
		String errCode = asynResponseMap.get("err_code");
		String errCodeDes = asynResponseMap.get("err_code_des");
		if ("SUCCESS".equalsIgnoreCase(returnCode)) {
			if ("SUCCESS".equals(resultCode)) {
				response.setResultCode(BizConstant.METHOD_INVOKE_SUCCESS);
				response.setResultMsg(returnMsg);

				response.setTradeState(TradeConstant.TRADE_STATE_SUCCESS);
				response.setAttach(asynResponseMap.get("attach"));
				response.setMchId(asynResponseMap.get("mch_id"));
				response.setOrderNo(asynResponseMap.get("out_trade_no"));
				response.setAgtOrderNo(asynResponseMap.get("transaction_id"));
				response.setTotalFee(asynResponseMap.get("total_fee"));
				response.setTradeTime(DateUtil.formatDate(asynResponseMap.get("time_end"), "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss"));

				response.setAppId(asynResponseMap.get("appid"));
				response.setOpenId(asynResponseMap.get("openid"));
				response.setTradeType(asynResponseMap.get("trade_type"));
			} else {
				response.setResultCode(BizConstant.METHOD_INVOKE_FAILURE);
				response.setResultMsg(errCode.concat(" : ").concat(errCodeDes));
			}
		} else {
			response.setResultCode(BizConstant.METHOD_INVOKE_FAILURE);
			response.setResultMsg(returnMsg);

		}

		return response;
	}

	public static String wechatTradeState2TradeState(String tradeState) {
		//SUCCESS—支付成功
		//REFUND—转入退款
		//NOTPAY—未支付
		//CLOSED—已关闭
		//REVOKED—已撤销（刷卡支付）
		//USERPAYING--用户支付中
		//PAYERROR--支付失败(其他原因，如银行返回失败)

		String returnTradeState = "";

		if ("SUCCESS".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_SUCCESS;
		} else if ("REFUND".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_REFUND;
		} else if ("NOTPAY".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_NOTPAY;
		} else if ("CLOSED".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_CLOSED;
		} else if ("REVOKED".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_CLOSED;
		} else if ("USERPAYING".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_NOTPAY;
		} else if ("PAYERROR".equals(tradeState)) {
			returnTradeState = TradeConstant.TRADE_STATE_NOTPAY;
		} else {
			returnTradeState = TradeConstant.TRADE_STATE_EXCEPTION;
		}

		return returnTradeState;
	}

}
