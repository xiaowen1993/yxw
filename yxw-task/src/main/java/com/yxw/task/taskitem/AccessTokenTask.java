package com.yxw.task.taskitem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.vo.cache.AccessTokenVo;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;

/**
 * @Package: com.yxw.platform.quartz.task
 * @ClassName: AccessTokenTask
 * @Statement: <p>
 *             定时获取微信AccessToken
 *             </p>
 * @Author: 
 * @Create Date: 2015-10-09
 */
public class AccessTokenTask {
	public static Logger logger = LoggerFactory.getLogger(AccessTokenTask.class);

	// 微信API网关
	public static final String WECHAT_API_GATEWAY = "https://api.weixin.qq.com";

	/**
	 * 计数器
	 */
	private final AtomicLong idGen = new AtomicLong();

	public AccessTokenTask() {
		super();
	}

	public void startUp() {
		long count = idGen.incrementAndGet();
		logger.info("第 " + count + " 次定时获取微信AccessToken开始....................");
		Long statrTime = Calendar.getInstance().getTimeInMillis();

		String componentAppId = SystemConfig.getStringValue("component_appid");
		String componentAppSecret = SystemConfig.getStringValue("component_secret");

		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> params = new ArrayList<Object>();
		params.add(componentAppId);
		List<Object> objects = serveComm.get(CacheType.COMPONENT_VERIFY_TICKET_CACHE, "getComponentVerifyTicketByAppId", params);

		String componentVerifyTicket = "";

		if (!CollectionUtils.isEmpty(objects)) {
			componentVerifyTicket = (String) objects.get(0);
		}

		if (!StringUtils.isBlank(componentVerifyTicket)) {
			refreshComponentAccessToken(componentAppId, componentAppSecret, componentVerifyTicket);
		}

		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 次定时获取微信AccessToken结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}

	/*	public static AccessTokenVo getComponentAccessToken(String componentAppId, String componentAppSecret, String componentVerifyTicket)
				throws HttpException, IOException {
			AccessTokenVo accessTokenVo = null;

			// {"component_access_token":"ACCESS_TOKEN","expires_in":7200, "getTime": "133333333333"}
			//		JSONObject catJson = WechatConstant.wechatComponentAccessTokenMap.get(componentAppId);
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

			List<Object> params = new ArrayList<Object>();
			params.add(componentAppId);
			List<Object> objects = serveComm.get(CacheType.ACCESS_TOKEN_CACHE, "getTokenByAppId", params);

			if (!CollectionUtils.isEmpty(objects)) {
				accessTokenVo = (AccessTokenVo) objects.get(0);
			}

			if (accessTokenVo != null) {
				// 判断超时否
				long getTime = accessTokenVo.getGetTime();
				long expiresIn = 1000l * ( new Integer(accessTokenVo.getExpiresTime()).intValue() / 2 );
				if ( ( System.currentTimeMillis() - getTime ) > expiresIn) {
					// 超时 - 重拿
					accessTokenVo = getComponentAccessTokenByWechat(componentAppId, componentAppSecret, componentVerifyTicket);
				}
			} else {
				accessTokenVo = getComponentAccessTokenByWechat(componentAppId, componentAppSecret, componentVerifyTicket);
			}

			return accessTokenVo;
		}*/

	private static AccessTokenVo
			refreshComponentAccessToken(String componentAppId, String componentAppSecret, String componentVerifyTicket) {

		AccessTokenVo accessTokenVo = null;

		try {

			HttpClientUtil httpClient = HttpClientUtil.getInstance();

			JSONObject params = new JSONObject();
			//出于安全考虑，在第三方平台创建审核通过后，微信服务器 每隔10分钟会向第三方的消息接收地址推送一次component_verify_ticket，用于获取第三方平台接口调用凭据
			params.put("component_verify_ticket", componentVerifyTicket);
			params.put("component_appid", componentAppId);
			params.put("component_appsecret", componentAppSecret);

			String url = WECHAT_API_GATEWAY.concat("/cgi-bin/component/api_component_token");

			String res = httpClient.post(url, params.toJSONString());

			if (!StringUtils.isEmpty(res)) {
				JSONObject retJson = JSONObject.parseObject(res);
				if (retJson.containsKey("component_access_token")) {
					String componentAccessToken = retJson.getString("component_access_token");
					String expiresIn = retJson.getString("expires_in");
					accessTokenVo = new AccessTokenVo(System.currentTimeMillis(), expiresIn, componentAccessToken);

					//设置缓存
					ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

					List<Object> cacheParams = new ArrayList<Object>();
					cacheParams.add(componentAppId);
					cacheParams.add(accessTokenVo);
					serveComm.set(CacheType.ACCESS_TOKEN_CACHE, "updateToken", cacheParams);

				} else {
					logger.error("get component_access_token error, response :", retJson.toJSONString());
				}
			} else {
				logger.error("response is null");
			}
		} catch (Exception e) {
			logger.error("request component_access_token error,throws exception:{}", e.getMessage());
		}

		return accessTokenVo;
	}
}
