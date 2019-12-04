package com.yxw.platform.sdk.alipay.utils;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMenuAddRequest;
import com.alipay.api.request.AlipayMobilePublicMenuGetRequest;
import com.alipay.api.request.AlipayMobilePublicMenuUpdateRequest;
import com.alipay.api.response.AlipayMobilePublicMenuAddResponse;
import com.alipay.api.response.AlipayMobilePublicMenuGetResponse;
import com.alipay.api.response.AlipayMobilePublicMenuUpdateResponse;
import com.yxw.platform.sdk.alipay.constant.AlipayConstant;
import com.yxw.platform.sdk.alipay.entity.MenuAlipay;
import com.yxw.platform.sdk.alipay.factory.AlipayAPIClientFactory;

/**
 * 支付宝 菜单service
 * 
 * @author luob
 * */
public class AlipayMenuUtil {

	private static Logger logger = LoggerFactory.getLogger(MessageSendUtils.class);

	// 响应状态
	public static final int SUCCESS = 0;
	public static final int FAILURE = -1;

	/**
	 * 菜单创建
	 * 
	 * @param menu
	 *            菜单类
	 * @param appId
	 * @param privateKey
	 *            密钥
	 * */
	public static JSONObject createMenu(MenuAlipay menu, String appId, String privateKey) {
		String requestMsg = JSONObject.fromObject(menu).toString();
		JSONObject result = new JSONObject();
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey, "json", AlipayConstant.ALIPAY_CHAR_SET);
		AlipayMobilePublicMenuAddRequest request = new AlipayMobilePublicMenuAddRequest();
		request.setBizContent(requestMsg);
		// logger.info("privateKey  ----------------" + privateKey);
		// 2.2 使用SDK接口类发送响应
		AlipayMobilePublicMenuAddResponse response;
		try {
			response = alipayClient.execute(request);
			if (null != response) {
				result.put("flag", response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode()) ? true : false);
				result.put("errcode", response.getCode());
				result.put("msg", response.getMsg());
				return result;
			} else {
				result.put("flag", false);
				result.put("errcode", "-1");
				result.put("msg", "支付宝服务器异常");
				logger.error("AlipayMenuService——>createMenu-response==null方法出现异常");
				return result;
			}

		} catch (AlipayApiException e) {
			e.printStackTrace();
			logger.error("AlipayMenuService——>createMenu方法出现异常");
			result.put("errcode", "-1");
			result.put("msg", "支付宝服务器异常");
			result.put("flag", false);
			return result;
		}
	}

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 *            菜单类
	 * @param appId
	 * @param privateKey
	 *            密钥
	 * */
	public static JSONObject updateMenu(MenuAlipay menu, String appId, String privateKey) {
		String requestMsg = JSONObject.fromObject(menu).toString();
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey, "json", AlipayConstant.ALIPAY_CHAR_SET);
		AlipayMobilePublicMenuUpdateRequest request = new AlipayMobilePublicMenuUpdateRequest();
		request.setBizContent(requestMsg);
		JSONObject result = new JSONObject();
		// 2.2 使用SDK接口类发送响应
		AlipayMobilePublicMenuUpdateResponse response;
		try {
			response = alipayClient.execute(request);
			if (null != response) {
				result.put("flag", response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode()) ? true : false);
				result.put("errcode", response.getCode());
				result.put("msg", response.getMsg());
				if (logger.isDebugEnabled()) {
					logger.debug("msg ----" + response.getMsg());
				}
				return result;
			} else {
				result.put("flag", false);
				result.put("errcode", "-1");
				result.put("msg", "支付宝服务器异常");
				logger.error("AlipayMenuService——>createMenu-response==null方法出现异常");
				return result;
			}

		} catch (AlipayApiException e) {
			e.printStackTrace();
			logger.error("AlipayMenuService——>updateMenu方法出现异常");
			result.put("errcode", "-1");
			result.put("msg", "支付宝服务器异常");
			result.put("flag", false);
			return result;
		}
	}

	/**
	 * 获取菜单
	 * 
	 * @param appId
	 * @param privateKey
	 * */
	public static String getMenu(String appId, String privateKey) {
		AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, privateKey, "json", AlipayConstant.ALIPAY_CHAR_SET);
		AlipayMobilePublicMenuGetRequest request = new AlipayMobilePublicMenuGetRequest();
		// 2.2 使用SDK接口类发送响应
		AlipayMobilePublicMenuGetResponse response;
		try {
			response = alipayClient.execute(request);
			if (null != response && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
				return response.getMenuContent();
			} else {
				logger.error("异步发送失败 code=" + response.getCode() + "msg：" + response.getMsg());
				return null;
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			logger.error("AlipayMenuService——>getMenu方法出现异常");
			return null;
		}
	}
}
