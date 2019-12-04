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
package com.yxw.platform.sdk.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.Extension;
import com.yxw.commons.entity.platform.hospital.ExtensionDetail;
import com.yxw.commons.entity.platform.hospital.Menu;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.entity.platform.message.Reply;
import com.yxw.commons.entity.platform.message.Rule;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.platform.hospital.service.ExtensionDetailService;
import com.yxw.platform.hospital.service.ExtensionService;
import com.yxw.platform.hospital.service.MenuService;
import com.yxw.platform.hospital.service.WhiteListDetailService;
import com.yxw.platform.message.MessageConstant;
import com.yxw.platform.message.service.MixedMeterialService;
import com.yxw.platform.message.service.ReplyService;
import com.yxw.platform.sdk.SDKPublicArgs;
import com.yxw.platform.sdk.wechat.WechatSDK;
import com.yxw.platform.sdk.wechat.constant.WechatConstant;
import com.yxw.platform.sdk.wechat.entity.req.ImageMessage;
import com.yxw.platform.sdk.wechat.entity.resp.Article;
import com.yxw.platform.sdk.wechat.entity.resp.Image;
import com.yxw.platform.sdk.wechat.entity.resp.NewsMessage;
import com.yxw.platform.sdk.wechat.entity.resp.TextMessage;
import com.yxw.platform.sdk.wechat.util.MessageUtil;
import com.yxw.platform.sdk.wechat.util.MeterialUploadUtil;

/**
 * 微信service controller
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年3月16日
 */

@Controller
@RequestMapping("/sdk/wechat")
public class WechatSDKController {
	private String debugStr = "/sdk/wechat";
	private String QRSCENE_STR = "qrscene_";

	private static Logger logger = LoggerFactory.getLogger(WechatSDKController.class);

	// http://serverName:port/
	public String basePath = "";

	private static String DISK = null;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private WhiteListDetailService whiteListDetailService;

	@Autowired
	private MixedMeterialService mixedMeterialService;

//	@Autowired
//	private WhiteListCache whiteListCache;
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	// 根据appId获取医院信息VO
	public static Map<String, HospIdAndAppSecretVo> hospitalAppSecretMap = new HashMap<String, HospIdAndAppSecretVo>();

	static {
		String osName = System.getProperties().getProperty("os.name");
		// logger.info("osName:" + osName);
		if (osName.toLowerCase().contains("linux")) {
			// /usr/local/
			DISK = SystemConfig.getStringValue("linux_disk");// 保存的目录名
		} else if (osName.toLowerCase().contains("win")) {
			// E:\\
			DISK = SystemConfig.getStringValue("win_disk");// 保存的目录名
		}
	}

	/**
	 * 根据appId获取医院信息VO
	 * 
	 * @param appId
	 * */
	public HospIdAndAppSecretVo obtainByAppId(String appId) {
		// 取缓存
		if (hospitalAppSecretMap.get(appId) != null) {
			return hospitalAppSecretMap.get(appId);
		} else {
			/*
			 * Map<String, Object> params = new HashMap<String, Object>();
			 * params.put("appId", appId); params.put("platformType",
			 * WechatConstant.WECHAT);
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("appId:{} , platformType:{}", new Object[] { appId, WechatConstant.WECHAT });
			}
			// 根据appId获取医院ID和app密钥
			// HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
			// HospIdAndAppSecretVo hospitalAndAppSecret = hospitalCache.findAppSecretByAppId(appId, WechatConstant.WECHAT);
			HospIdAndAppSecretVo vo = null;
			List<Object> params = new ArrayList<Object>();
			params.add(appId);
			params.add(WechatConstant.WECHAT);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", params);
			if (CollectionUtils.isNotEmpty(results)) {
				vo = (HospIdAndAppSecretVo) results.get(0);
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("hospitalAndAppSecret  is  " + (vo != null ? "not null" : "null"));
				logger.debug("hospitalAndAppSecret  hospid " + (vo != null ? vo.getHospId() : "null"));
				logger.debug("hospitalAndAppSecret  appsercet " + (vo != null ? vo.getAppSecret() : "null"));
			}
			hospitalAppSecretMap.put(appId, vo);
			return vo;
		}
	}

	/**
	 * JS-SDK 获取ticket
	 * 
	 * @param appId
	 * @param url
	 * */
	@RequestMapping(value = "/jsConfig",
			method = RequestMethod.POST)
	@ResponseBody
	public Object jsConfig(HttpServletRequest request, HttpServletResponse response, String appId, String url) {
		if (SDKPublicArgs.logger.isDebugEnabled()) {
			SDKPublicArgs.logger.debug("{}/jsConfig.appId: {}", debugStr, appId);
			SDKPublicArgs.logger.debug("{}/jsConfig.url: {}", debugStr, url);
		}
		HospIdAndAppSecretVo hospIdAndAppSecretVo = obtainByAppId(appId);
		if (hospIdAndAppSecretVo != null) {
			// 获取JSTicket
			String ticket = WechatSDK.getJSTicket(appId, hospIdAndAppSecretVo.getAppSecret());
			if (SDKPublicArgs.logger.isDebugEnabled()) {
				SDKPublicArgs.logger.debug("{}/jsConfig.ticket: {}", debugStr, ticket);
			}
			// 获取配置微信js的信息
			// jsConfigMap所包含的key: url,jsapi_ticket,nonceStr,timestamp,signature
			Map<String, String> jsConfigMap = WechatSDK.getJSTicketSign(ticket, url);
			if (SDKPublicArgs.logger.isDebugEnabled()) {
				SDKPublicArgs.logger.debug("{}/jsConfig.jsConfigMap: {}", debugStr, jsConfigMap);
			}
			return jsConfigMap;
		} else {
			SDKPublicArgs.logger.debug("hospIdAndAppSecretVo is null");
			return null;
		}
	}

	/**
	 * 处理微信发来的请求及事件推送
	 * 
	 * @throws IOException
	 * */
	@RequestMapping(value = "/main",
			method = RequestMethod.POST)
	public void main(HttpServletRequest request, HttpServletResponse response) {
		if (basePath == null || "".equals(basePath)) {
			basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
		}
		String respMsg = processRequest(request, response);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(respMsg);
			out.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 处理请求
	 * */
	public String processRequest(HttpServletRequest request, HttpServletResponse response) {
		String respMessage = null;
		try {
			// appId参数 用于区分是哪家医院
			String appId = request.getParameter("appId");
			if (logger.isDebugEnabled()) {
				logger.debug("WechatSDKController.processRequest:appId--------------" + appId);
			}
			String hospId = null;
			String appSecret = null;
			HospIdAndAppSecretVo hospital = obtainByAppId(appId);
			if (hospital != null) {
				hospId = hospital.getHospId();
				appSecret = hospital.getAppSecret();
			}
			if (logger.isDebugEnabled()) {
				if (hospId == null || appSecret == null) {
					logger.debug("WechatSDKController.processRequest:无法获取完整的医院信息，hospId或appSecret为空");
				}
				logger.debug("WechatSDKController:appSecret--------------" + appSecret);
				logger.debug("WechatSDKController:hospId--------------" + hospId);
			}
			// 默认返回的文本消息内容
			String respContent = WechatConstant.WECHAT_EXCEPTION_TIP;
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 文本消息内容
			String content = requestMap.get("Content");
			if (logger.isDebugEnabled()) {
				logger.debug("WechatSDKController content------" + content);
				logger.debug("WechatSDKController:fromUserName----" + fromUserName);
				logger.debug("WechatSDKController toUserName-----" + toUserName);
				logger.debug("WechatSDKController msgType------" + msgType);
			}
			// 回复异常提示消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				if (content != null && content.startsWith("SQ")) {
					String phone = content.substring(2, content.length());
					Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
					Matcher matcher = pattern.matcher(phone);
					if (matcher.matches()) {
						// 判断是否appId对应的医院是否开始白名单设置
						// boolean isOpen = whiteListCache.isOpenWhiteList(appId, WechatConstant.WECHAT);
						boolean isOpen = false;
						List<Object> params = new ArrayList<Object>();
						params.add(appId);
						params.add(WechatConstant.WECHAT);
						List<Object> results = serveComm.get(CacheType.WHITE_LIST_CACHE, "isOpenWhiteList", params);
						if (CollectionUtils.isNotEmpty(results)) {
							isOpen = (boolean) results.get(0);
						}
						
						if (isOpen) {
							// 判断申请的手机号是否加入登记
							// boolean isAddWhiteFlag = whiteListCache.isValidWhiteOpenIdOrPhone(appId, WechatConstant.WECHAT, phone);
							boolean isAddWhiteFlag = false;
							params.add(phone);
							results = serveComm.get(CacheType.WHITE_LIST_CACHE, "isValidWhiteOpenIdOrPhone", params);
							if (CollectionUtils.isNotEmpty(results)) {
								isAddWhiteFlag = (boolean) results.get(0);
							}
							
							// 有登记 换取openid 并更新数据库和缓存
							if (isAddWhiteFlag) {
								// 有对应的手机号 换取openId
								whiteListDetailService.updateWhiteListOpenId(appId, WechatConstant.WECHAT, phone, fromUserName);
								respContent = "已加入白名单，欢迎参加内测！";
							} else {
								respContent = "您的手机号码未登记,不能参加内测！如有意参加测试,请联系管理员登记.";
							}
						}
					} else {
						respContent = "您输入的手机号码格式不正确,请正确输入您的手机号码！";
					}
				} else {
					// 关键字回复
					String result = doKeywordReply(fromUserName, toUserName, request, content, hospId, appId, appSecret);
					if (result != null) {
						logger.info("关键字回复:" + result);
						return result;
					} else {
						// 什么也不回复
						return "";
					}
				}
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "本公众号暂不支持对图片消息作出回复！";
			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "本公众号暂不支持对地理位置消息作出回复！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "本公众号暂不支持对链接消息作出回复！";
			}
			// 音频消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
				respContent = "本公众号暂不支持对音频消息作出回复！";
			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					String eventKey = requestMap.get("EventKey");
					logger.info("eventKey:" + eventKey);
					if (!StringUtils.isBlank(eventKey)) {
						addExtensionCount(appId, fromUserName, eventKey);
					}
					// 关注后先将用户的openId保存到cookie中
					if (logger.isDebugEnabled()) {
						logger.debug("关注后将openId存入session,openId的值是:" + fromUserName);
					}
					// openId 存入 Cookie
					// openId 在 Cookie 里面的有效天数，默认为30天
					int openIdCookieMaxAge = Integer.valueOf(StringUtils.isBlank(SDKPublicArgs.OPENID_COOKIE_MAX_AGE) ? "30"
							: SDKPublicArgs.OPENID_COOKIE_MAX_AGE);
					Cookie cookie = new Cookie(appId + "-openId", fromUserName);
					cookie.setDomain(request.getServerName()); // 请用自己的域
					cookie.setMaxAge(24 * 60 * 60 * openIdCookieMaxAge); // cookie的有效期
					cookie.setPath("/");
					response.addCookie(cookie);
					// 关注成功自动回复
					String result = doFocusedReply(fromUserName, toUserName, request, hospId, appId, appSecret);
					if (result != null) {
						return result;
					} else {
						// 使用默认的被关注回复
						respContent = WechatConstant.WECHAT_FOCUS_TIP;
					}
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				} else if ("enter".equals(eventType) || "ENTER".equals(eventType)) {
					respContent = "欢迎进入！";
				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if ((appId + "-" + BizConstant.OPENID).equalsIgnoreCase(cookie.getName())) {
								cookie.setMaxAge(0);
								break;
							}
						}
					}
					/************************************** 点击时更新openId *************************************/
					// openId 存入 Cookie
					// openId 在 Cookie 里面的有效天数，默认为30天
					int openIdCookieMaxAge = Integer.valueOf(StringUtils.isBlank(SDKPublicArgs.OPENID_COOKIE_MAX_AGE) ? "30"
							: SDKPublicArgs.OPENID_COOKIE_MAX_AGE);
					Cookie cookie = new Cookie(appId + "-openId", fromUserName);
					cookie.setDomain(request.getServerName()); // 请用自己的域
					cookie.setMaxAge(24 * 60 * 60 * openIdCookieMaxAge); // cookie的有效期
					cookie.setPath("/");
					response.addCookie(cookie);
					/************************************** 点击时更新openId *************************************/
					String eventKey = requestMap.get("EventKey");
					Menu menu = menuService.findById(eventKey);
					if (menu != null) {
						List<MixedMeterial> mixedMeterialList = mixedMeterialService.findMixedMeterialsByIds(new String[] { menu.getGrapicsId() });
						if (mixedMeterialList != null && mixedMeterialList.size() > 0) {
							return sendMulti(appId, appSecret, fromUserName, toUserName, mixedMeterialList.get(0));
						}
					}

				} else if (eventType.equals(MessageUtil.EVENT_TYPE_VIEW)) {
					Cookie[] cookies = request.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if ((appId + "-" + BizConstant.OPENID).equalsIgnoreCase(cookie.getName())) {
								cookie.setMaxAge(0);
								break;
							}
						}
					}
					/************************************** 点击时更新openId *************************************/
					// openId 存入 Cookie
					// openId 在 Cookie 里面的有效天数，默认为30天
					int openIdCookieMaxAge = Integer.valueOf(StringUtils.isBlank(SDKPublicArgs.OPENID_COOKIE_MAX_AGE) ? "30"
							: SDKPublicArgs.OPENID_COOKIE_MAX_AGE);
					Cookie cookie = new Cookie(appId + "-" + BizConstant.OPENID, fromUserName);
					cookie.setDomain(request.getServerName()); // 请用自己的域
					cookie.setMaxAge(24 * 60 * 60 * openIdCookieMaxAge); // cookie的有效期
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
			if (logger.isDebugEnabled()) {
				logger.debug("WechatSDKController respMessage------" + respMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

	/**
	 * 被关注回复
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param hospId
	 * @param appSecret
	 * @param appId
	 * */
	public String doFocusedReply(String fromUserName, String toUserName, HttpServletRequest request, String hospId, String appId, String appSecret)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospId", hospId);
		params.put("thirdType", MessageConstant.THIRD_WECHAT_STR);
		Reply reply = replyService.getFocusedReply(params);
		if (reply != null) {
			if (reply.getContentType() == MessageConstant.TEXT) {
				// 回复文本
				return sendText(fromUserName, toUserName, reply.getContent());
			} else if (reply.getContentType() == MessageConstant.IMAGE) {
				// 回复图片
				return sendImg(fromUserName, toUserName, appId, appSecret, DISK + reply.getPicPaths());
			} else if (reply.getContentType() == MessageConstant.IMAGETEXT) {
				// 回复图文
				MixedMeterial meterial = mixedMeterialService.getMeterialByReplyId(reply.getId());
				return sendMulti(appId, appSecret, fromUserName, toUserName, meterial);
			}
		}
		return null;
	}

	/**
	 * 关键字回复
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param keyword
	 * @param hospId
	 * @param appId
	 * @param appSecret
	 * */
	public String doKeywordReply(String fromUserName, String toUserName, HttpServletRequest request, String keyword, String hospId, String appId,
			String appSecret) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hospId", hospId);
		params.put("keyword", keyword);
		params.put("thirdType", MessageConstant.THIRD_WECHAT_STR);
		List<Rule> ruleList = replyService.getKeywordRule(params);
		// 获取随机规则的随机回复
		if (ruleList != null && ruleList.size() > 0) {
			Rule rule = null;// 随机选择规则
			int n = 0;// 随机-规则
			if (ruleList.size() > 1) {
				n = generateRandom(ruleList.size());
			}
			rule = ruleList.get(n);
			List<Reply> replyList = rule.getReplyList();
			if (replyList == null || replyList.size() == 0) {
				return null;
			}
			// ------------------------随机选取回复-----------------------------------------
			if (replyList.size() > 1) {
				n = generateRandom(replyList.size());
			}
			Reply reply = replyList.get(n);
			if (reply != null) {
				if (reply.getContentType() == MessageConstant.TEXT) {
					// 回复文本
					return sendText(fromUserName, toUserName, reply.getContent());
				} else if (reply.getContentType() == MessageConstant.IMAGE) {
					// 回复图片
					return sendImg(fromUserName, toUserName, appId, appSecret, DISK + reply.getPicPaths());
				} else if (reply.getContentType() == MessageConstant.IMAGETEXT) {
					List<MixedMeterial> meterials = reply.getMixedMeterialList();
					if (meterials != null && meterials.size() > 0) {
						return sendMulti(appId, appSecret, fromUserName, toUserName, meterials.get(0));
					}
				}
			}
		}
		return null;
	}

	/**
	 * 回复图文
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param meterial
	 *            图文
	 */
	private String sendMulti(String appId, String appSecret, String fromUserName, String toUserName, MixedMeterial meterial) throws Exception {
		Article respArticle = null;
		String respMessage = null;
		int count = 0;
		if (meterial != null) {
			List<Article> articleList = new ArrayList<Article>();
			// 设置第一图文
			respArticle = new Article();
			respArticle.setTitle(meterial.getTitle());
			if (meterial.getWechatPicPath() != null && !"".equals(meterial.getWechatPicPath())) {
				respArticle.setPicUrl(meterial.getWechatPicPath());
			} else {
				String wechatPicPath = MeterialUploadUtil.uploadWechatPic(appId, appSecret, DISK + meterial.getCoverPicPath());
				meterial.setWechatPicPath(wechatPicPath);
				mixedMeterialService.update(meterial);
				respArticle.setPicUrl(wechatPicPath);
			}
			if (logger.isDebugEnabled()) {
				logger.debug("path---------" + meterial.getCoverPicPath() + "/n");
			}
			respArticle.setUrl(meterial.getLink());
			respArticle.setDescription(meterial.getDescription());
			articleList.add(respArticle);
			count += 1;
			List<MixedMeterial> subList = meterial.getSubMixedMeterialList();
			if (subList != null && subList.size() > 0) {
				for (MixedMeterial m : subList) {
					respArticle = new Article();
					respArticle.setTitle(m.getTitle());
					if (m.getWechatPicPath() != null && !"".equals(m.getWechatPicPath())) {
						// respArticle.setPicUrl(basePath +
						// m.getCoverPicPath());
						respArticle.setPicUrl(m.getWechatPicPath());
					} else {
						String subWechatPicPath = MeterialUploadUtil.uploadWechatPic(appId, appSecret, DISK + m.getCoverPicPath());
						m.setWechatPicPath(subWechatPicPath);
						mixedMeterialService.update(m);
						respArticle.setPicUrl(subWechatPicPath);
					}
					if (logger.isDebugEnabled()) {
						logger.debug("path---------" + meterial.getCoverPicPath() + "/n");
					}
					respArticle.setUrl(m.getLink());
					respArticle.setDescription(m.getDescription());
					articleList.add(respArticle);
					count += 1;
				}
			}
			NewsMessage newsResp = new NewsMessage();
			newsResp.setCreateTime(new Date().getTime());
			newsResp.setFromUserName(toUserName);
			newsResp.setToUserName(fromUserName);
			newsResp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
			newsResp.setArticleCount(count);
			newsResp.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsResp);
		}
		return respMessage;
	}

	/**
	 * 回复图片
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param appId
	 * @param appSecret
	 * @param imgUrl
	 *            image全路径
	 */
	private String sendImg(String fromUserName, String toUserName, String appId, String appSecret, String imgUrl) throws Exception {
		String media_id = MeterialUploadUtil.multiUpload(appId, appSecret, imgUrl);
		Image image = new Image();
		image.setMediaId(media_id);
		ImageMessage resp = new ImageMessage();
		resp.setCreateTime(new Date().getTime());
		resp.setFromUserName(toUserName);
		resp.setToUserName(fromUserName);
		resp.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
		resp.setImage(image);
		return MessageUtil.imageMessageToXml(resp);
	}

	/**
	 * 回复文本
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 *            文本内容
	 */
	private String sendText(String fromUserName, String toUserName, String content) {
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(content);
		return MessageUtil.textMessageToXml(textMessage);
	}

	/**
	 * 网址接入 确认请求来自微信服务器
	 * 
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * */
	@RequestMapping(value = "/main",
			method = RequestMethod.GET)
	public void accessURL(HttpServletRequest request, HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");

		String appId = request.getParameter("appId");

		String hospId = null;
		String token = null;
		HospIdAndAppSecretVo hospital = obtainByAppId(appId);
		if (hospital != null) {
			hospId = hospital.getHospId();
			token = hospital.getToken();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("the value of token is:" + token);
			logger.debug("the value of hospId is:" + hospId);
		}
		String[] strSet = new String[] { token, timestamp, nonce };
		java.util.Arrays.sort(strSet);
		String total = "";
		for (String string : strSet)
			total = total + string;
		// SHA-1加密实例
		MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
		sha1.update(total.getBytes());
		byte[] codedBytes = sha1.digest();
		String codedString = new BigInteger(1, codedBytes).toString(16);// 将加密后的字节数组转换成字符串。参见http://hi.baidu.com/aotori/item/c94813c4f15caa60f6c95d4a
		if (codedString.equals(signature)) // 将加密的结果与请求参数中的signature比对，如果相同，原样返回echostr参数内容
		{
			PrintWriter out = response.getWriter();
			out.println(echostr);
			out.close();
		}
	}

	public int generateRandom(int b) {
		int temp = 0;
		try {
			if (0 > b) {
				temp = new Random().nextInt(0 - b);
				return temp + b;
			} else {
				temp = new Random().nextInt(b - 0);
				return temp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 是否是白名单用户
	 * 
	 * @param request
	 * @param response
	 * @param appId
	 * @param appCode
	 * @param openId
	 * @return
	 */
//	private Boolean checkIsPassWhite(String appId, String appCode, String openId) {
//		boolean isPassWhite = true;
//		// 是否通过白名单
//		if (StringUtils.isNotBlank(appCode) && StringUtils.isNotBlank(appId)) {
//			// 1.医院未启用白名单则返回true
//			// 2.用户已加入白名单，返回true
//			isPassWhite = whiteListCache.isValidWhiteOpenIdOrPhone(appId, appCode, openId);
//			logger.info("appId : {} ,openId:{} , checked PassWhite:{}", appId, openId, isPassWhite);
//		}
//		return isPassWhite;
//	}

	/**
	 * 添加来源关注人
	 * @param appId
	 * @param openId
	 * @param actionParam
	 */
	public void addExtensionCount(String appId, String openId, String eventKey) {
		String sceneId = eventKey.replace(QRSCENE_STR, "");
		logger.info("appId:{},openId:{},sceneId:{}.", new Object[] { appId, openId, sceneId });
		if (!StringUtils.isBlank(sceneId)) {//获取到sceneId,表示由二维码关注 记录关注人
			ExtensionService extensionService = SpringContextHolder.getBean(ExtensionService.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("appId", appId);
			params.put("sceneid", sceneId);
			logger.info("查推广二维码参数params:{}", new Object[] { JSON.toJSONString(params) });
			Extension extension = extensionService.findExtensionByAppIdAndSceneid(params);
			logger.info("查推广二维码结果:{}", new Object[] { JSON.toJSONString(extension) });
			if (extension != null) {//属于系统中保存的推广二维码则添加关注人
				params.clear();
				ExtensionDetailService detailService = SpringContextHolder.getBean(ExtensionDetailService.class);
				params.put("openId", openId);
				params.put("extensionId", extension.getId());
				logger.info("查是否已经关注参数params:{}", new Object[] { JSON.toJSONString(params) });
				ExtensionDetail detail = detailService.findExtensionDetailByExtensionIdAndOpenId(params);
				logger.info("查是否已经关注结果:{}", new Object[] { JSON.toJSONString(detail) });
				if (detail == null) {
					detail = new ExtensionDetail(extension.getId(), new Date().getTime(), openId);
					logger.info("保存关注人:{}", new Object[] { JSON.toJSONString(detail) });
					extension.setCount(extension.getCount() + 1);
					logger.info("关注统计数加1:{}", new Object[] { extension.getCount() });
					detailService.add(detail);
					extensionService.update(extension);
				}
			}
		}
	}
}
