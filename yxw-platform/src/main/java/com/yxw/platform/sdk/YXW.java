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
package com.yxw.platform.sdk;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.platform.sdk.alipay.AlipaySDK;
import com.yxw.platform.sdk.alipay.constant.AlipayConstant;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.entity.MenuWechat;

/**
 * 医享网 微信&支付宝&银联钱包 java sdk 统一入口
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年4月30日
 */
public class YXW {

	private static Logger logger = LoggerFactory.getLogger(YXW.class);

	/**
	 * 获取授权跳转地址
	 * 
	 * @param appId
	 * @param platformCode
	 *            接入平台Code
	 * @param redirectUrl
	 *            授权回调地址
	 * @return 授权跳转地址
	 * @throws UnsupportedEncodingException
	 */
	public static String getOAuth2(String appId, String platformCode, String redirectUrl) throws UnsupportedEncodingException {
		String oauth2Url = "";

		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			oauth2Url = WechatSDK.getOAuth2(appId, redirectUrl);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			oauth2Url = AlipaySDK.getOAuth2(appId, redirectUrl);
		} /*else if (SDKPublicArgs.PLATFORM_UNIONPAY.equalsIgnoreCase(platformCode)) {
			oauth2Url = UnionPaySDK.getOAuth2(appId, redirectUrl);
			}*/
		return oauth2Url;
	}

	/**
	 * 获取OpenId
	 * 
	 * @param appId
	 * @param privateKey
	 *            私匙
	 * @param platformCode
	 *            接入平台Code
	 * @param code
	 *            授权跳转后取到的code
	 * @return OpenId
	 */
	public static String getOpenId(String appId, String privateKey, String platformCode, String code) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.getOpenId(appId, privateKey, code);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return AlipaySDK.getOpenId(appId, privateKey, code);
		} else {
			return "";
		}
	}

	/**
	 * 获取token
	 * 
	 * @param appId
	 * @param secret
	 * @param platformCode
	 * */
	public static String getAccessToken(String appId, String secret, String platformCode) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.getAccessToken(appId, secret);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return "";
		}/* else if (SDKPublicArgs.PLATFORM_UNIONPAY.equalsIgnoreCase(platformCode)) {
			return UnionPaySDK.getBackendToken(appId, secret);
			}*/else {
			return "";
		}
	}

	/**
	 * 获取模版ID
	 * 
	 * @param appId
	 * @param privateKey
	 * @param platformCode
	 * @param templateShortId
	 *            模版ID
	 * */
	public static String getTemplateId(String appId, String privateKey, String platformCode, String templateShortId) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			com.alibaba.fastjson.JSONObject jsonObject = WechatSDK.getTemplateId(appId, privateKey, templateShortId);
			if (jsonObject != null && jsonObject.getInteger("errcode") == 0) {
				return jsonObject.getString("template_id");
			} else {
				return null;
			}
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return null;
		} else {
			return null;
		}
	}

	/**
	 * 发送模版消息
	 * 
	 * @param platformCode
	 *            平台类型
	 * @param touser
	 *            openId
	 * @param appId
	 * @param privateKey
	 *            密钥
	 * @param template_id
	 *            模版ID
	 * @param url
	 *            模版URL
	 * @param topcolor
	 * @param data
	 * @param remark
	 *            备注
	 * */
	public static com.alibaba.fastjson.JSONObject pushTemplateMsg(String platformCode, String touser, String appId, String privateKey,
			MsgTemplate msgTemplate) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.pushTemplateMsg(touser, appId, privateKey, msgTemplate.getTemplateId(), msgTemplate.getUrl(), msgTemplate.getTopColor(),
					msgTemplate.getMsgTemplateContents());
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return AlipaySDK.pushTemplateMsg(appId, privateKey, touser, msgTemplate.getMsgTemplateContents(), msgTemplate.getUrl(),
					msgTemplate.getTopColor(), msgTemplate.getTemplateId());
		} else {
			return null;
		}
	}

	/**
	 * 发送模版消息
	 * 
	 * @param platformCode
	 *            平台类型
	 * @param touser
	 *            openId
	 * @param appId
	 * @param privateKey
	 *            密钥
	 * @param template_id
	 *            模版ID
	 * @param url
	 *            模版URL
	 * @param topcolor
	 * @param data
	 * @param remark
	 *            备注
	 * */
	public static com.alibaba.fastjson.JSONObject pushTemplateMsg(String platformCode, String touser, String appId, String privateKey,
			MsgTemplate msgTemplate, String data) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.pushTemplateMsg(touser, appId, privateKey, msgTemplate.getTemplateId(), msgTemplate.getUrl(), msgTemplate.getTopColor(),
					data);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return AlipaySDK.pushTemplateMsg(appId, privateKey, touser, data, msgTemplate.getUrl(), msgTemplate.getTopColor(),
					msgTemplate.getTemplateId());
		} else {
			return null;
		}
	}

	/**
	 * 发送客服消息
	 * 
	 * @param platformCode
	 *            平台类型
	 * @param touser
	 *            openId
	 * @param appId
	 * @param privateKey
	 *            密钥
	 * @param data
	 * @param msgtype
	 *            消息类型
	 * */
	public static com.alibaba.fastjson.JSONObject pushCustomerServiceMsg(String platformCode, String appId, String privateKey, String touser,
			MsgCustomer msgCustomer) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.pushCustomerServiceMsg(touser, appId, privateKey, msgCustomer);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return AlipaySDK.pushCustomerServiceMsg(appId, privateKey, touser, msgCustomer);
		} else {
			return null;
		}
	}

	/**
	 * 发送健康易消息
	 * @param platformCode
	 * @param touser
	 * @param appId
	 * @param privateKey
	 * @param msgTemplate
	 * @return
	 */
	/*public static com.alibaba.fastjson.JSONObject msgTemplatePush(EHDeviceInfo ehDeviceInfo, String content, String openType, String url) {
		return EasyHealthSDK.msgTemplatePush(ehDeviceInfo.getDeviceType(), ehDeviceInfo.getDeviceId(), ehDeviceInfo.getChannelId(), content,
				openType, url);
	}*/

	/**
	 * 创建菜单
	 * 
	 * @param platformCode
	 *            平台类型
	 * @param appId
	 * @param privateKey
	 * @param menu
	 *            菜单类 --注：微信和支付宝的菜单类不同
	 * */
	public static JSONObject createMenu(String platformCode, String appId, String privateKey, Object menu) {
		JSONObject jsonObject = null;
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.createWechatMenu((MenuWechat) menu, appId, privateKey);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			jsonObject = AlipaySDK.createAlipayMenu((com.yxw.platform.sdk.alipay.entity.MenuAlipay) menu, appId, privateKey);
			if (jsonObject != null) {
				int code = jsonObject.getInt("errcode");
				if (code == AlipayConstant.ALIPAY_SUCCESS) {
					return jsonObject;
				} else if (code == AlipayConstant.MENU_EVER_CREATED) {
					return AlipaySDK.updateAlipayMenu((com.yxw.platform.sdk.alipay.entity.MenuAlipay) menu, appId, privateKey);
				} else {
					return jsonObject;
				}
			} else {
				jsonObject = new JSONObject();
				jsonObject.put("flag", false);
				jsonObject.put("errcode", "-1");
				jsonObject.put("msg", "微信服务器未返回任何响应");
				return jsonObject;
			}
		} else {
			jsonObject = new JSONObject();
			jsonObject.put("flag", false);
			jsonObject.put("errcode", "-1");
			jsonObject.put("msg", "未找到可支持的平台");
			return jsonObject;
		}
	}

	/**
	 * 更新菜单（目前只有支付宝的更新菜单功能）
	 * 
	 * @param platformCode
	 *            平台类型
	 * @param appId
	 * @param privateKey
	 * @param menu
	 *            菜单类 --注：微信和支付宝的菜单类不同
	 * */
	public static JSONObject updateMenu(String platformCode, String appId, String privateKey, Object menu) {
		if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return AlipaySDK.updateAlipayMenu((com.yxw.platform.sdk.alipay.entity.MenuAlipay) menu, appId, privateKey);
		} else {
			return null;
		}
	}

	/**
	 * 获取菜单
	 * 
	 * @param platformCode
	 * @param appId
	 * @param privateKey
	 * */
	public static com.alibaba.fastjson.JSONObject getMenu(String platformCode, String appId, String privateKey) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.getWechatMenu(appId, privateKey);
		} else if (SDKPublicArgs.PLATFORM_ALIPAY.equalsIgnoreCase(platformCode)) {
			return com.alibaba.fastjson.JSONObject.parseObject(AlipaySDK.getAlipayMenu(appId, privateKey));
		} else {
			return null;
		}
	}

	/**
	 * 删除菜单 （目前只要微信提供删除菜单）
	 * 
	 * @param platformCode
	 * @param appId
	 * @param privateKey
	 * */
	public static com.alibaba.fastjson.JSONObject deleteMenu(String platformCode, String appId, String privateKey) {
		if (SDKPublicArgs.PLATFORM_WECHAT.equalsIgnoreCase(platformCode)) {
			return WechatSDK.deleteWechatMenu(appId, privateKey);
		} else {
			return null;
		}
	}
}
