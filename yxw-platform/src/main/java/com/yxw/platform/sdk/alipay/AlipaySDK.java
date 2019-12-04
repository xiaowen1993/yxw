/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年4月30日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.sdk.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayMobilePublicQrcodeCreateRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipayMobilePublicQrcodeCreateResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplateContent;
import com.yxw.platform.sdk.SDKPublicArgs;
import com.yxw.platform.sdk.alipay.entity.MenuAlipay;
import com.yxw.platform.sdk.alipay.utils.AlipayMenuUtil;
import com.yxw.platform.sdk.alipay.utils.AlipayPushMsgUtil;

/**
 * 支付宝SDK
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年4月30日
 */
public class AlipaySDK {

	private static String debugStr = "com.yxw.platform.sdk.alipay.AlipaySDK";

	private static Logger Alipaylogger = LoggerFactory.getLogger(AlipaySDK.class);

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
		oauth2Url += SDKPublicArgs.ALIPAY_OPENAUTH_GATEWAY + "/oauth2/publicAppAuthorize.htm?auth_skip=false&scope=auth_base";
		oauth2Url += "&app_id=" + appId;
		oauth2Url += "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8");
		return oauth2Url;
	}

	public static String getOAuth2(String appId, String redirectUrl, String scope) throws UnsupportedEncodingException {
		String oauth2Url = "";
		if (StringUtils.isBlank(scope)) {
			scope = "auth_base";
		}
		oauth2Url += SDKPublicArgs.ALIPAY_OPENAUTH_GATEWAY + "/oauth2/publicAppAuthorize.htm?auth_skip=false&scope=" + scope;
		oauth2Url += "&app_id=" + appId;
		oauth2Url += "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8");
		return oauth2Url;
	}

	/**
	 * 获取支付宝接口调用客户端
	 * 
	 * @param appId
	 * @param privateKey
	 *            支付宝私匙(和公匙是一对，公匙要配置到商户平台。私匙和公匙的生成请看支付宝的API)
	 * @return
	 */
	private static AlipayClient getAlipayClient(String appId, String privateKey) {
		AlipayClient alipayClient = SDKPublicArgs.alipayClientMap.get(appId);
		if (null == alipayClient) {
			alipayClient = new DefaultAlipayClient(SDKPublicArgs.ALIPAY_GATEWAY + "/gateway.do", appId, privateKey, "json", SDKPublicArgs.CHARSET);
			SDKPublicArgs.alipayClientMap.put(appId, alipayClient);
		}
		return alipayClient;
	}

	/**
	 * Alipay 授权跳转后获取账户信息
	 * 
	 * @param privateKey
	 *            支付宝私匙(和公匙是一对，公匙要配置到商户平台。私匙和公匙的生成请看支付宝的API)
	 * @param authCode
	 *            授权跳转后用request.getParameter("auth_code")取到
	 * @return AlipayUserUserinfoShareResponse
	 */
	public static AlipayUserUserinfoShareResponse getUserInfo(String appId, String privateKey, String authCode) {
		SDKPublicArgs.logger.debug("{}.getUserInfo.authCode: {}", debugStr, authCode);
		AlipayUserUserinfoShareResponse userinfoShareResponse = null;
		try {
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType(SDKPublicArgs.GRANT_TYPE);
			AlipayClient alipayClient = getAlipayClient(appId, privateKey);
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			// System.out.println(oauthTokenResponse.getBody());
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				SDKPublicArgs.logger.debug(debugStr + ".getUserInfo.AccessToken: " + oauthTokenResponse.getAccessToken());
				// 4. 利用authToken获取用户信息
				AlipayUserUserinfoShareRequest userinfoShareRequest = new AlipayUserUserinfoShareRequest();
				userinfoShareResponse = alipayClient.execute(userinfoShareRequest, oauthTokenResponse.getAccessToken());
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
		return userinfoShareResponse;
	}

	/**
	 * Alipay 授权跳转后获取OpenId
	 * 
	 * @param privateKey
	 *            支付宝私匙(和公匙是一对，公匙要配置到商户平台。私匙和公匙的生成请看支付宝的API)
	 * @param authCode
	 *            授权跳转后用request.getParameter("auth_code")取到
	 * @return alipay_system_oauth_token_response.alipay_user_id
	 */
	public static String getOpenId(String appId, String privateKey, String authCode) {
		SDKPublicArgs.logger.debug("{}.getOpenId.authCode: {}", debugStr, authCode);
		String openId = "";
		try {
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(authCode);
			oauthTokenRequest.setGrantType(SDKPublicArgs.GRANT_TYPE);
			AlipayClient alipayClient = getAlipayClient(appId, privateKey);
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			Alipaylogger.debug("支付宝body:{}", oauthTokenResponse.getBody());
			SDKPublicArgs.logger.debug("{}.getOpenId.oauthTokenResponse.body: {}", debugStr, oauthTokenResponse.getBody());
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				openId = oauthTokenResponse.getUserId();
			}
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
		SDKPublicArgs.logger.debug("{}.getOpenId.return: {}", debugStr, openId);
		return openId;
	}

	/**
	 * 发送模版消息
	 * 
	 * @param appid
	 * @param privateKey
	 * @param toUser
	 * @param data
	 * @param url
	 * @param topcolor
	 * @param template_id
	 * @param remark
	 * */
	public static com.alibaba.fastjson.JSONObject pushTemplateMsg(String appid, String privateKey, String toUser,
			List<MsgTemplateContent> msgTemplateContents, String url, String topcolor, String template_id) {
		return AlipayPushMsgUtil.pushModelMsgAlipay(appid, privateKey, toUser, msgTemplateContents, url, topcolor, template_id);
	}

	/**
	 * 发送模版消息
	 * 
	 * @param appid
	 * @param privateKey
	 * @param toUser
	 * @param data
	 * @param url
	 * @param topcolor
	 * @param template_id
	 * @param remark
	 * */
	public static com.alibaba.fastjson.JSONObject pushTemplateMsg(String appid, String privateKey, String toUser, String data, String url,
			String topcolor, String template_id) {
		return AlipayPushMsgUtil.pushModelMsgAlipay(appid, privateKey, toUser, data, url, topcolor, template_id);
	}

	/**
	 * 发送客服消息
	 * 
	 * @param appid
	 * @param privateKey
	 * @param toUser
	 * @param data
	 * @param url
	 * @param topcolor
	 * @param template_id
	 * @param remark
	 * */
	public static com.alibaba.fastjson.JSONObject pushCustomerServiceMsg(String appid, String privateKey, String toUser, MsgCustomer msgCustomer) {
		return AlipayPushMsgUtil.pushNoticeMsgAlipay(appid, privateKey, toUser, msgCustomer);
	}

	/**
	 * 菜单创建
	 * 
	 * @param menu
	 * @param appId
	 * @param privateKey
	 * */
	public static JSONObject createAlipayMenu(MenuAlipay menu, String appId, String privateKey) {
		return AlipayMenuUtil.createMenu(menu, appId, privateKey);
	}

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @param appId
	 * @param privateKey
	 * */
	public static JSONObject updateAlipayMenu(MenuAlipay menu, String appId, String privateKey) {
		return AlipayMenuUtil.updateMenu(menu, appId, privateKey);
	}

	/**
	 * 获取菜单
	 * 
	 * @param appId
	 * @param privateKey
	 * */
	public static String getAlipayMenu(String appId, String privateKey) {
		return AlipayMenuUtil.getMenu(appId, privateKey);
	}

	/**
	 * 获取二维码
	 * 
	 * @param appId
	 * @param privateKey
	 *            周鉴斌 2015年9月16日 16:08:15 添加
	 * @return
	 */
	public static AlipayMobilePublicQrcodeCreateResponse getRWM(String appId, String privateKey, String json) {
		AlipayMobilePublicQrcodeCreateResponse response = null;
		try {
			AlipayMobilePublicQrcodeCreateRequest request = new AlipayMobilePublicQrcodeCreateRequest();
			request.setBizContent(json);
			AlipayClient alipayClient = getAlipayClient(appId, privateKey);
			response = alipayClient.execute(request);
		} catch (AlipayApiException alipayApiException) {
			// 自行处理异常
			alipayApiException.printStackTrace();
		}
		return response;
	}
}
