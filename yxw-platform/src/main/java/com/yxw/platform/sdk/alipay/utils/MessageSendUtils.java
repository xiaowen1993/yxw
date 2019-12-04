package com.yxw.platform.sdk.alipay.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayMobilePublicMessageCustomSendRequest;
import com.alipay.api.response.AlipayMobilePublicMessageCustomSendResponse;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.message.MixedMeterial;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.platform.sdk.alipay.constant.AlipayConstant;
import com.yxw.platform.sdk.alipay.factory.AlipayAPIClientFactory;

/**
 * 支付宝消息异步发送工具类
 * 
 * @author luob
 * 
 * */
public class MessageSendUtils {

	/** 线程池 */
	private static ExecutorService executors = Executors.newSingleThreadExecutor();

	private static Logger logger = LoggerFactory.getLogger(MessageSendUtils.class);

	public static HospIdAndAppSecretVo obtainByAppId(String appId) {
		if (logger.isDebugEnabled()) {
			logger.debug("appId:{} , platformType:{}", new Object[] { appId, AlipayConstant.ALIPAY });
		}
		// 根据appId获取医院ID和app密钥
		// HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
		// HospIdAndAppSecretVo hospitalAndAppSecret = hospitalCache.findAppSecretByAppId(appId, AlipayConstant.ALIPAY);
		HospIdAndAppSecretVo vo = null;
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(AlipayConstant.ALIPAY);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", params);
		
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospIdAndAppSecretVo) results.get(0);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("MessageSendUtils.obtainByAppId.hospitalAndAppSecret  is  null " + (vo != null ? "true" : "false"));
			logger.debug("MessageSendUtils.obtainByAppId.hospitalAndAppSecret  hospid "
					+ (vo != null ? vo.getHospId() : "null"));
			logger.debug("MessageSendUtils.obtainByAppId.hospitalAndAppSecret  appsercet "
					+ (vo != null ? vo.getAppSecret() : "null"));
		}
		return vo;
	}

	/**
	 * 回复文字
	 * 
	 * @param fromUserId
	 * @param textContent
	 * @param appSecret
	 * */
	public static void sendText(final String fromUserId, final String textContent, final String appSecret, final String appId) {

		executors.execute(new Runnable() {
			public void run() {
				String requestMsg = AlipayMsgBuildUtil.buildSingleTextMsg(fromUserId, textContent);
				AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, appSecret, "json", AlipayConstant.ALIPAY_CHAR_SET);
				AlipayMobilePublicMessageCustomSendRequest request = new AlipayMobilePublicMessageCustomSendRequest();
				request.setBizContent(requestMsg);
				// 2.2 使用SDK接口类发送响应
				AlipayMobilePublicMessageCustomSendResponse response;
				try {
					response = alipayClient.execute(request);
					if (null != response && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
						if (logger.isDebugEnabled()) {
							logger.debug("MessageSendUtils类sendText方法，异步发送成功，结果为：" + response.getBody());
						}
					} else {
						logger.error("MessageSendUtils类sendText方法，异步发送失败 code=" + response.getCode() + "msg：" + response.getMsg());
					}
				} catch (AlipayApiException e) {
					e.printStackTrace();
					logger.error("MessageSendUtils——>sendText方法：回复文本消息失败");
				}
			}
		});
	}

	/**
	 * 回复单图文
	 * 
	 * @param fromUserId
	 * @param appSecret
	 * @param appId
	 * @param desc
	 * @param imgsrc
	 * @param title
	 * @param url
	 * */
	public static void sendSingleImageText(final String fromUserId, final String desc, final String appSecret, final String appId,
			final String imgsrc, final String title, final String url) {
		executors.execute(new Runnable() {
			public void run() {
				String requestMsg = AlipayMsgBuildUtil.buildSingleImgTextMsg(fromUserId, desc, imgsrc, title, url);
				AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, appSecret, "json", AlipayConstant.ALIPAY_CHAR_SET);
				AlipayMobilePublicMessageCustomSendRequest request = new AlipayMobilePublicMessageCustomSendRequest();
				request.setBizContent(requestMsg);
				// 2.2 使用SDK接口类发送响应
				AlipayMobilePublicMessageCustomSendResponse response;
				try {
					response = alipayClient.execute(request);
					if (null != response && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
						if (logger.isDebugEnabled()) {
							logger.debug("MessageSendUtils类sendSingleImageText方法，异步发送成功，结果为：" + response.getBody());
						}
					} else {
						logger.error("MessageSendUtils类sendSingleImageText方法，异步发送失败 code=" + response.getCode() + "msg：" + response.getMsg());
					}
				} catch (AlipayApiException e) {
					e.printStackTrace();
					logger.error("MessageSendUtils——>sendSingleImageText方法：回复单图文消息失败");
				}
			}
		});
	}

	/**
	 * 回复多图文
	 * 
	 * @param fromUserId
	 * @param appSecret
	 * @param meterial
	 * @param appId
	 * */
	public static void sendMultiImageText(final String fromUserId, final String appSecret, final MixedMeterial meterial, final String appId) {
		executors.execute(new Runnable() {
			public void run() {
				String requestMsg = AlipayMsgBuildUtil.buildMultiImgTextMsg(fromUserId, meterial);
				AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient(appId, appSecret, "json", AlipayConstant.ALIPAY_CHAR_SET);
				AlipayMobilePublicMessageCustomSendRequest request = new AlipayMobilePublicMessageCustomSendRequest();
				request.setBizContent(requestMsg);
				// 2.2 使用SDK接口类发送响应
				AlipayMobilePublicMessageCustomSendResponse response;
				try {
					response = alipayClient.execute(request);
					if (null != response && response.isSuccess() && AlipayConstant.ALIPAY_SUCCESS_STR.equals(response.getCode())) {
						if (logger.isDebugEnabled()) {
							logger.debug("MessageSendUtils类sendMultiImageText方法，异步发送成功，结果为：" + response.getBody());
						}
					} else {
						logger.error("MessageSendUtils类sendMultiImageText方法，异步发送失败 code=" + response.getCode() + "msg：" + response.getMsg());
					}
				} catch (AlipayApiException e) {
					e.printStackTrace();
					logger.error("MessageSendUtils——>sendMultiImageText方法：回复多图文消息失败");
				}
			}
		});
	}
}
