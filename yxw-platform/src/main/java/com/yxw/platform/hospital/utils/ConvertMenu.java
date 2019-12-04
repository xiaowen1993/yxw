/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.hospital.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.commons.entity.platform.hospital.Optional;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.framework.config.SystemConfig;
import com.yxw.platform.sdk.alipay.entity.ButtonAlipay;
import com.yxw.platform.sdk.alipay.entity.ClickButtonAlipay;
import com.yxw.platform.sdk.alipay.entity.ComplexButtonAlipay;
import com.yxw.platform.sdk.alipay.entity.MenuAlipay;
import com.yxw.platform.sdk.alipay.entity.ViewButtonAlipay;
import com.yxw.platform.sdk.wechat.entity.Button;
import com.yxw.platform.sdk.wechat.entity.ClickButton;
import com.yxw.platform.sdk.wechat.entity.ComplexButton;
import com.yxw.platform.sdk.wechat.entity.MenuWechat;
import com.yxw.platform.sdk.wechat.entity.ViewButton;

/**
 * @Package: com.yxw.platform.hospital.utils
 * @ClassName: MenuForJSON
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年6月27日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class ConvertMenu {
	private static final String URL_PREFIX = SystemConfig.getStringValue("url_prefix");
	/**
	 * 是否特约商户 0：非特约商户 1：特约商户
	 */
	private static final String IS_PARENT = "isParent";

	/**
	 * 组装微信菜单
	 * 
	 * @param menuList
	 * @param optionalMap
	 * @param platformSettings
	 * @return
	 */
	public static MenuWechat
			wechatConvert(List<Menu> menuList, Map<String, Optional> optionalMap, PlatformSettings platformSettings, Integer isParent) {
		Button[] buttons = new Button[menuList.size()];
		for (int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if (menu.getChildMenus().size() > 0) {
				ComplexButton complexButton = new ComplexButton();
				Button[] sub_button = new Button[menu.getChildMenus().size()];
				for (int j = 0; j < menu.getChildMenus().size(); j++) {
					Menu childMenu = menu.getChildMenus().get(j);
					if (childMenu.getMeunType().equals(0)) {
						ViewButton btn = new ViewButton();
						btn.setType("view");
						btn.setName(childMenu.getName());
						// btn.setKey(childMenu.getId());
						Optional op = null;
						if (StringUtils.isNotEmpty(childMenu.getOptionalId())) {
							op = optionalMap.get(childMenu.getOptionalId());
						}

						if (op == null) {
							btn.setUrl(getLinkParams(URL_PREFIX + childMenu.getControllerPath(), platformSettings));
						} else {
							btn.setUrl(getLinkParams(URL_PREFIX + op.getControllerPath(), platformSettings) + "&bizCode=" + op.getBizCode() + "&"
									+ IS_PARENT + "=" + isParent);
						}

						sub_button[j] = btn;
					} else if (childMenu.getMeunType().equals(1)) {
						ViewButton btn = new ViewButton();
						btn.setType("view");
						btn.setUrl(addParamsForContent(childMenu.getContent(), platformSettings));
						btn.setName(childMenu.getName());
						sub_button[j] = btn;
					} else {
						ClickButton btn = new ClickButton();
						btn.setType("click");
						btn.setName(childMenu.getName());
						btn.setKey(childMenu.getId());
						sub_button[j] = btn;
					}
				}
				complexButton.setName(menu.getName());
				complexButton.setSub_button(sub_button);
				buttons[i] = complexButton;
			} else {
				if (menu.getMeunType().equals(0)) {
					ViewButton btn = new ViewButton();
					btn.setType("view");
					btn.setName(menu.getName());
					Optional op = null;
					if (StringUtils.isNotEmpty(menu.getOptionalId())) {
						op = optionalMap.get(menu.getOptionalId());
					}
					if (op == null) {
						btn.setUrl(getLinkParams(URL_PREFIX + menu.getControllerPath(), platformSettings));
					} else {
						btn.setUrl(getLinkParams(URL_PREFIX + op.getControllerPath(), platformSettings) + "&bizCode=" + op.getBizCode() + "&"
								+ IS_PARENT + "=" + isParent);
					}
					buttons[i] = btn;
				} else if (menu.getMeunType().equals(1)) {
					ViewButton btn = new ViewButton();
					btn.setType("view");
					btn.setName(menu.getName());
					btn.setUrl(addParamsForContent(menu.getContent(), platformSettings));
					buttons[i] = btn;
				} else {
					ClickButton btn = new ClickButton();
					btn.setType("click");
					btn.setName(menu.getName());
					btn.setKey(menu.getId());
					buttons[i] = btn;
				}
			}
		}
		MenuWechat wechat = new MenuWechat();
		wechat.setButton(buttons);
		return wechat;
	}

	/**
	 * 组装支付宝菜单
	 * 
	 * @param menuList
	 * @param optionalMap
	 * @param platformSettings
	 * @return
	 */
	public static MenuAlipay alipayConvert(List<Menu> menuList, Map<String, Optional> optionalMap, PlatformSettings platformSettings) {
		ButtonAlipay[] buttons = new ButtonAlipay[menuList.size()];
		for (int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			if (menu.getChildMenus().size() > 0) {
				ComplexButtonAlipay complexButton = new ComplexButtonAlipay();
				ButtonAlipay[] sub_button = new ButtonAlipay[menu.getChildMenus().size()];
				for (int j = 0; j < menu.getChildMenus().size(); j++) {
					Menu childMenu = menu.getChildMenus().get(j);
					if (childMenu.getMeunType().equals(0)) {
						ViewButtonAlipay btn = new ViewButtonAlipay();
						btn.setActionType("link");
						btn.setName(childMenu.getName());
						// btn.setKey(childMenu.getId());
						Optional op = null;
						if (StringUtils.isNotEmpty(childMenu.getOptionalId())) {
							op = optionalMap.get(childMenu.getOptionalId());
						}

						if (op == null) {
							btn.setActionParam(getLinkParams(URL_PREFIX + childMenu.getControllerPath(), platformSettings));
						} else {
							btn.setActionParam(getLinkParams(URL_PREFIX + op.getControllerPath(), platformSettings) + "&bizCode=" + op.getBizCode());
						}

						sub_button[j] = btn;
					} else if (childMenu.getMeunType().equals(1)) {
						ViewButtonAlipay btn = new ViewButtonAlipay();
						btn.setActionType("link");
						btn.setActionParam(addParamsForContent(childMenu.getContent(), platformSettings));
						btn.setName(childMenu.getName());
						sub_button[j] = btn;
					} else {
						ClickButtonAlipay btn = new ClickButtonAlipay();
						btn.setActionType("out");
						btn.setName(childMenu.getName());
						btn.setActionParam(childMenu.getId());
						sub_button[j] = btn;
					}
				}
				complexButton.setName(menu.getName());
				complexButton.setSubButton(sub_button);
				buttons[i] = complexButton;
			} else {
				if (menu.getMeunType().equals(0)) {
					ViewButtonAlipay btn = new ViewButtonAlipay();
					btn.setActionType("link");
					btn.setName(menu.getName());
					Optional op = null;
					if (StringUtils.isNotEmpty(menu.getOptionalId())) {
						op = optionalMap.get(menu.getOptionalId());
					}
					if (op == null) {
						btn.setActionParam(getLinkParams(URL_PREFIX + menu.getControllerPath(), platformSettings));
					} else {
						btn.setActionParam(getLinkParams(URL_PREFIX + op.getControllerPath(), platformSettings) + "&bizCode=" + op.getBizCode());
					}
					buttons[i] = btn;
				} else if (menu.getMeunType().equals(1)) {
					ViewButtonAlipay btn = new ViewButtonAlipay();
					btn.setActionType("link");
					btn.setName(menu.getName());
					btn.setActionParam(addParamsForContent(menu.getContent(), platformSettings));
					buttons[i] = btn;
				} else {
					ClickButtonAlipay btn = new ClickButtonAlipay();
					btn.setActionType("out");
					btn.setName(menu.getName());
					btn.setActionParam(menu.getId());
					buttons[i] = btn;
				}
			}
		}
		MenuAlipay alipay = new MenuAlipay();
		alipay.setButton(buttons);
		return alipay;
	}

	/**
	 * 拼接请求地址参数
	 * 
	 * @param platformSettings
	 * @return
	 */
	private static String getLinkParams(String url, PlatformSettings platformSettings) {
		int index = url.indexOf("?");
		String urlParams = "1=1";
		if (platformSettings != null) {
			urlParams = "appCode=" + platformSettings.getCode() + "&appId=" + platformSettings.getAppId();
		}
		if (index != -1) {
			return url + "&" + urlParams;
		} else {
			return url + "?" + urlParams;
		}
	}

	private static String addParamsForContent(String content, PlatformSettings platformSettings) {
		Map<String, String> params = URLRequest(content);
		int index = content.indexOf("?");
		if (index != -1) {
			content = content.substring(0, content.indexOf("?"));
		}
		if (platformSettings != null) {
			content += "?appCode=" + platformSettings.getCode() + "&appId=" + platformSettings.getAppId();
		} else {
			content += "?1=1";
		}
		if (!params.isEmpty()) {
			if (params.containsKey("appCode")) {
				params.remove("appCode");
			}
			if (params.containsKey("appId")) {
				params.remove("appId");
			}
			for (Map.Entry<String, String> entry : params.entrySet()) {
				content += "&" + entry.getKey() + "=" + entry.getValue();
			}
		}
		return content;
	}

	/**
	* 去掉url中的路径，留下请求参数部分
	* @param strURL url地址
	* @return url请求参数部分
	*/
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;
		strURL = strURL.trim();
		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对
	 * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * @param URL  url地址
	 * @return  url请求参数部分
	 */
	public static Map<String, String> URLRequest(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();
		String[] arrSplit = null;
		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}

		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			//解析出键值
			if (arrSplitEqual.length > 1) {
				//正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					//只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
