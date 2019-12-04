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
package com.yxw.platform.sdk.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.platform.sdk.SDKPublicArgs;
import com.yxw.platform.sdk.wechat.entity.MenuWechat;
import com.yxw.platform.sdk.wechat.util.PushMsgUtil;
import com.yxw.platform.sdk.wechat.util.WechatMenuUtil;

/**
 * 微信SDK
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年3月16日
 */
public class WechatSDK {

	private static Logger logger = LoggerFactory.getLogger(WechatSDK.class);

	/**
	 * 获取授权跳转地址
	 * 
	 * @param appId
	 * @param redirectUrl
	 *            授权回调地址
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String redirectUrl) throws UnsupportedEncodingException {
		String oauth2Url = "";

		oauth2Url += SDKPublicArgs.WECHAT_OPENAUTH_GATEWAY + "/connect/oauth2/authorize?response_type=code";
		oauth2Url += "&scope=snsapi_base&state=getOpenId";
		oauth2Url += "&appid=" + appId;
		oauth2Url += "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8");
		oauth2Url += "#wechat_redirect";

		return oauth2Url;
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
	 */
	public static String getOpenId(String appId, String appSecret, String code) {
		String openid = "";

		String url = SDKPublicArgs.WECHAT_GATEWAY + "/sns/oauth2/access_token?appid=" + appId;
		url += "&secret=" + appSecret;
		url += "&code=" + code;
		url += "&grant_type=authorization_code";
		System.out.println("url=" + url);
		/**
		 * { "access_token":"ACCESS_TOKEN", "expires_in":7200,
		 * "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE" }
		 **/
		String responseString = SDKPublicArgs.httpClient.get(url, null);
		if (StringUtils.isNotBlank(responseString)) {
			JSONObject jo = JSONObject.parseObject(responseString);

			openid = jo.getString("openid");
		}

		return openid;
	}

	/**
	 * 去内存获取AccessToken，有超时机制
	 * 
	 * @param appId
	 * @param appSecret
	 * @return AccessToken
	 */
	public static String getAccessToken(String appId, String appSecret) {
		String accessToken = "";
		JSONObject at = SDKPublicArgs.wechatAccessTokenMap.get(appId);
		if (at != null) {
			// 判断超时否
			long get_time = at.getLongValue("get_time");
			long expires_in = 1000L * ( at.getLongValue("expires_in") - 1000l );
			if ( ( System.currentTimeMillis() - get_time ) > expires_in) {
				// 超时 - 重拿
				accessToken = getAccessTokenByWechat(appId, appSecret);
			} else {
				// 没超时
				accessToken = at.getString("access_token");
			}
		} else {
			accessToken = getAccessTokenByWechat(appId, appSecret);
		}
		return accessToken;
	}

	/*
	 * public static String getAccessToken(String appId, String appSecret) {
	 * String accessToken = ""; JSONObject at =
	 * SDKPublicArgs.wechatAccessTokenMap.get(appId); if (at != null) { // 判断超时否
	 * long get_time = at.getLongValue("get_time"); // long expires_in = 1000L *
	 * at.getLongValue("expires_in"); long expires_in = 1000L *
	 * (at.getLongValue("expires_in") - 1000l);
	 * 
	 * if ((System.currentTimeMillis() - get_time) > expires_in) { // 超时 - 重拿
	 * accessToken = getAccessTokenByWechat(appId, appSecret); } else { // 没超时
	 * accessToken = at.getString("access_token"); } } else { accessToken =
	 * getAccessTokenByWechat(appId, appSecret); }
	 * 
	 * return accessToken; } *
	 */

	/**
	 * 去微信服务器获取AccessToken
	 * 
	 * @param appId
	 * @param appSecret
	 * @return AccessToken
	 */
	public static String getAccessTokenByWechat(String appId, String appSecret) {
		String accessToken = "";

		// {"errcode":40013,"errmsg":"invalid appid"}
		String url = SDKPublicArgs.WECHAT_GATEWAY + "/cgi-bin/token?grant_type=client_credential";
		url += "&appid=" + appId;
		url += "&secret=" + appSecret;
		logger.info("getAccessTokenByWechat.url:" + url);
		String responseString = SDKPublicArgs.httpClient.get(url, null);
		if (StringUtils.isNotBlank(responseString)) {
			JSONObject jo = JSONObject.parseObject(responseString);
			if (jo.containsKey("access_token")) {
				accessToken = jo.getString("access_token");
				jo.put("get_time", System.currentTimeMillis() + "");

				SDKPublicArgs.wechatAccessTokenMap.put(appId, jo);
			}
		}

		SDKPublicArgs.logger.debug("reget wx access token : {}", accessToken);

		return accessToken;
	}

	/**
	 * 去内存获取JSTicket，有超时机制，有synchronized
	 * 
	 * @param appId
	 * @param appSecret
	 * @return JSTicket
	 */
	public static String getJSTicket(String appId, String appSecret) {
		String ticket = "";

		// {"ticket":"ticket","expires_in":7200, "get_time": "133333333333"}
		JSONObject jt = SDKPublicArgs.wechatJSTicketMap.get(appId);
		if (jt != null) {
			// 判断超时否
			long get_time = jt.getLongValue("get_time");
			long expires_in = 1000l * jt.getLongValue("expires_in");

			if ( ( System.currentTimeMillis() - get_time ) > expires_in) {
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
	 */
	private static String getJSTicketByWechat(String appId, String appSecret) {
		String ticket = "";

		// {"errcode":40013,"errmsg":"invalid appid"}
		String url = SDKPublicArgs.WECHAT_GATEWAY + "/cgi-bin/ticket/getticket?type=jsapi";
		url += "&access_token=" + getAccessToken(appId, appSecret);
		String responseString = SDKPublicArgs.httpClient.get(url, null);
		if (StringUtils.isNotBlank(responseString)) {
			JSONObject jo = JSONObject.parseObject(responseString);
			if (jo.containsKey("ticket")) {
				ticket = jo.getString("ticket");
				jo.put("get_time", System.currentTimeMillis() + "");

				SDKPublicArgs.wechatJSTicketMap.put(appId, jo);
			}
		}

		SDKPublicArgs.logger.debug("reget wx access token : {}", ticket);

		return ticket;
	}

	/**
	 * WECHAT JSSDK 生成JS-SDK权限验证的签名
	 * 生成签名之前必须先了解一下jsapi_ticket，jsapi_ticket是公众号用于调用微信JS接口的临时票据
	 * 。正常情况下，jsapi_ticket的有效期为7200秒
	 * ，通过access_token来获取。由于获取jsapi_ticket的api调用次数非常有限
	 * ，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket 。
	 * 
	 * @1.获取access_token
	 * @2.采用http GET方式请求获得jsapi_ticket
	 *           https://api.weixin.qq.com/cgi-bin/ticket/getticket
	 *           ?access_token=ACCESS_TOKEN&type=jsapi
	 * @成功返回如下JSON： { "errcode":0, "errmsg":"ok", "ticket":
	 *              "bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA"
	 *              , "expires_in":7200 }
	 * @3.获得jsapi_ticket之后，就可以生成JS-SDK权限验证的签名了。
	 * 
	 * @param ticket
	 * @param url
	 * @return Map<String, String>
	 *         (url,jsapi_ticket,nonceStr,timestamp,signature)
	 */
	public static Map<String, String> getJSTicketSign(String ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = createNonceStr();
		String timestamp = createTimestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		SDKPublicArgs.logger.debug(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String createNonceStr() {
		return UUID.randomUUID().toString();
	}

	private static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 获取模版ID
	 * 
	 * @param appId
	 * @param appSecret
	 * @param templateShortId
	 *            模版ID
	 * */
	public static JSONObject getTemplateId(String appId, String appSecret, String templateShortId) {
		return PushMsgUtil.getTemplate(templateShortId, appId, appSecret);
	}

	/**
	 * 发送模版消息接口
	 * 
	 * @param touser
	 * @param appId
	 * @param appSecret
	 * @param template_id
	 * @param url
	 * @param topcolor
	 * @param data
	 * */
	public static JSONObject pushTemplateMsg(String touser, String appId, String appSecret, String template_id, String url, String topcolor,
			List<MsgTemplateContent> msgTemplateContents) {
		return PushMsgUtil.pushWXTemplate(touser, appId, appSecret, template_id, url, topcolor, msgTemplateContents);
	}

	/**
	 * 发送模版消息接口
	 * 
	 * @param touser
	 * @param appId
	 * @param appSecret
	 * @param template_id
	 * @param url
	 * @param topcolor
	 * @param data
	 * */
	public static JSONObject pushTemplateMsg(String touser, String appId, String appSecret, String template_id, String url, String topcolor,
			String data) {
		return PushMsgUtil.pushWXTemplate(touser, appId, appSecret, template_id, url, topcolor, data);
	}

	/**
	 * 发送客服消息接口
	 * 
	 * @param touser
	 * @param appId
	 * @param appSecret
	 * @param msgtype
	 * @param data
	 * */
	public static JSONObject pushCustomerServiceMsg(String touser, String appId, String appSecret, MsgCustomer msgCustomer) {
		return PushMsgUtil.pushServiceNotice(touser, appId, appSecret, msgCustomer);
	}

	/**
	 * 创建菜单
	 * 
	 * @param appId
	 * @param menu
	 * @param appSecret
	 * */
	public static net.sf.json.JSONObject createWechatMenu(MenuWechat menu, String appId, String appSecret) {
		JSONObject result = WechatMenuUtil.createMenuStr(menu, appId, appSecret);
		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
		jsonObject.put("errcode", result.getString("errcode"));
		jsonObject.put("msg", result.getString("errmsg"));
		jsonObject.put("flag", "0".equals(result.getString("errcode")));
		if (logger.isDebugEnabled()) {
			logger.debug("errmsg----------" + result.getString("errmsg"));
			logger.debug("errcode---------" + result.getString("errcode"));
		}
		return jsonObject;
	}

	/**
	 * 获取菜单
	 * 
	 * @param appId
	 * @param appSecret
	 * */
	public static JSONObject getWechatMenu(String appId, String appSecret) {
		return WechatMenuUtil.getMenu(appId, appSecret);
	}

	/**
	 * 删除菜单
	 * 
	 * @param appId
	 * @param appSecret
	 * */
	public static JSONObject deleteWechatMenu(String appId, String appSecret) {
		return WechatMenuUtil.deleteMenu(appId, appSecret);
	}

	public static void main(String[] args) {
		String jsapi_ticket = "jsapi_ticket";

		// 注意 URL 一定要动态获取，不能 hardcode
		String url = "http://example.com";
		Map<String, String> ret = getJSTicketSign(jsapi_ticket, url);
		for (Map.Entry<String, String> entry : ret.entrySet()) {
			System.out.println(entry.getKey() + ", " + entry.getValue());
		}
	}
}
