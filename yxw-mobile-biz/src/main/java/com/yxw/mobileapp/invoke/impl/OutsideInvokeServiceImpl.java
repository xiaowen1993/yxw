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
package com.yxw.mobileapp.invoke.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.ClinicConstant;
import com.yxw.commons.constants.biz.MsgTemplateCodeConstant;
import com.yxw.commons.constants.biz.OrderConstant;
import com.yxw.commons.constants.biz.OutsideConstant;
import com.yxw.commons.constants.biz.ReceiveConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.dto.outside.OrdersQueryResult;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.msgpush.MsgTemplate;
import com.yxw.commons.entity.platform.register.Record;
import com.yxw.commons.entity.platform.rule.RuleEdit;
import com.yxw.commons.entity.platform.rule.RulePush;
import com.yxw.commons.utils.biz.ModeTypeUtil;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.mobileapp.biz.clinic.service.ClinicService;
import com.yxw.mobileapp.biz.msgpush.service.CommonMsgPushService;
import com.yxw.mobileapp.biz.msgpush.thread.OutsideMessagePushRunnable;
import com.yxw.mobileapp.biz.msgpush.thread.msgpool.MessagePushThreadPool;
import com.yxw.mobileapp.biz.outside.service.InterfaceService;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;
import com.yxw.mobileapp.biz.register.service.RegisterService;
import com.yxw.mobileapp.biz.register.service.StopRegisterRecordService;
import com.yxw.mobileapp.biz.register.thread.StopRegRunnable;
import com.yxw.mobileapp.biz.usercenter.service.MedicalCardService;
import com.yxw.mobileapp.common.websocket.YxwMessage;
import com.yxw.mobileapp.common.websocket.YxwMessage.MessageType;
import com.yxw.mobileapp.common.websocket.YxwSocketHandler;
import com.yxw.mobileapp.datas.manager.MsgTempManager;
import com.yxw.mobileapp.invoke.OutsideInvokeService;
import com.yxw.mobileapp.invoke.dto.Request;
import com.yxw.mobileapp.invoke.dto.Response;
import com.yxw.mobileapp.invoke.dto.inside.OrdersParams;
import com.yxw.mobileapp.invoke.dto.inside.RegOrdersParams;
import com.yxw.mobileapp.invoke.dto.inside.RgParams;
import com.yxw.mobileapp.invoke.dto.inside.StopRegister;
import com.yxw.mobileapp.invoke.dto.inside.TemplateMsgPushParams;
import com.yxw.mobileapp.invoke.dto.outside.RegOrdersQueryResult;
import com.yxw.mobileapp.invoke.utils.Utils;

/**
 * @Package: com.yxw.mobileapp.invoke.impl
 * @ClassName: OutsideInvokeServiceImpl
 * @Statement: <p>
 *             对外提供的接口服务实现
 *             </p>
 * @JDK version used:
 * @Author: 周鉴斌
 * @Create Date: 2015年8月5日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0		
 */
public class OutsideInvokeServiceImpl implements OutsideInvokeService {

	public static Logger logger = LoggerFactory.getLogger(OutsideInvokeServiceImpl.class);
	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
	private ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);
	private BaseDatasManager baseDatasManager = SpringContextHolder.getBean(BaseDatasManager.class);
	private MsgTempManager msgManager = SpringContextHolder.getBean(MsgTempManager.class);
	private InterfaceService interFaceService = SpringContextHolder.getBean(InterfaceService.class);
	//	private StopRegisterExceptionCache stopRegisterExceptionCache = SpringContextHolder.getBean(StopRegisterExceptionCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
	private CommonMsgPushService commonMsgPushSvc = SpringContextHolder.getBean(CommonMsgPushService.class);

	public static final int EXECUTOR_COUNT = 2;

	private StopRegisterRecordService stopRegisterRecordService = SpringContextHolder.getBean(StopRegisterRecordService.class);

	/**
	 * 模板消息推送
	 * 
	 * @param msgPushParams
	 * @param responseType
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public Response templateMsgPush(TemplateMsgPushParams msgPushParams, boolean responseType) {
		try {
			int pushStatus = 1;
			if (StringUtils.isBlank(msgPushParams.getAppId())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
			}
			if (StringUtils.isBlank(msgPushParams.getPlatformType())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "platformType cannot be null");
			}
			if (StringUtils.isBlank(msgPushParams.getTemplateCode())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "templateCode cannot be null");
			}
			if (StringUtils.isBlank(msgPushParams.getUserType())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "userType cannot be null");
			}
			if (StringUtils.isBlank(msgPushParams.getToUser())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "toUser cannot be null");
			}
			if (StringUtils.isBlank(msgPushParams.getMsgContent())) {
				return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "msgContent cannot be null");
			}
			if (StringUtils.isBlank(msgPushParams.getPlatformType())) {
				return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "platformType does not exist");
			}

			HospitalCodeAndAppVo hospitalCodeAndAppVo =
					baseDatasManager.queryHospitalCodeByApp(msgPushParams.getPlatformType(), msgPushParams.getAppId());

			if (hospitalCodeAndAppVo == null) {
				return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "hospital does not exist");
			}
			RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalId(hospitalCodeAndAppVo.getHospitalId());
			int ifOpentemplateMsgPush = ruleEdit.getOpentemplateMsgPush();
			ruleEdit = null;
			if (ifOpentemplateMsgPush != RuleConstant.IS_OPEN_YES) {
				return new Response(OutsideConstant.OUTSIDE_SUCCESS,
						"the message-pushing function has been closed caused by frequent operations.");
			}

			// 获取openId和详情链接url
			Response response =
					getMsgInfo(msgPushParams.getAppId(), msgPushParams.getPlatformType(), hospitalCodeAndAppVo.getHospitalId(),
							hospitalCodeAndAppVo.getHospitalCode(), Integer.valueOf(msgPushParams.getUserType()),
							msgPushParams.getToUser(), msgPushParams.getTemplateCode());

			if (!OutsideConstant.OUTSIDE_SUCCESS.equals(response.getResultCode())) {
				return response;
			}
			Map<String, String> map = JSON.parseObject(response.getResult(), HashMap.class);
			String openId = map.get("openId");

			if (StringUtils.isBlank(openId)) {
				return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "openId is null");
			}

			String platformMode = String.valueOf(ModeTypeUtil.getPlatformModeType(msgPushParams.getPlatformType()));
			String PlatformType = msgPushParams.getPlatformType();
			RulePush rulePush = ruleConfigManager.getRulePushByHospitalId(hospitalCodeAndAppVo.getHospitalId(), PlatformType);
			String[] pushArray = rulePush.getModeArray();

			boolean state = true;

			for (int i = 0; i < pushArray.length; i++) {
				if (!pushArray[i].equals("0")) {
					MsgTemplate msgTemplate =
							msgManager
									.getMsgTemplate(msgPushParams.getAppId(), msgPushParams.getTemplateCode(), platformMode, pushArray[i]);
					if (msgTemplate != null) {
						Map<String, Serializable> paramMap = JSON.parseObject(msgPushParams.getMsgContent(), Map.class);

						MessagePushThreadPool.outsideMsgThreadPool.execute(new OutsideMessagePushRunnable(msgPushParams.getAppId(),
								msgPushParams.getTemplateCode(), msgPushParams.getPlatformType(), openId, msgTemplate, paramMap));

						/**发消息 start*/
						YxwSocketHandler handler = SpringContextHolder.getBean(YxwSocketHandler.class);
						Map<String, Object> contentMap = new HashMap<>();
						contentMap.put("hasUnread", true);
						contentMap.put("unreadCount", 1);

						YxwMessage unreadMsg = new YxwMessage();
						unreadMsg.setMessageType(MessageType.MSGPUSH);
						unreadMsg.setToUser(openId);
						unreadMsg.setFromUser("hospital-system");
						unreadMsg.setContent(JSON.toJSONString(contentMap));
						unreadMsg.setPushTime(System.currentTimeMillis());
						handler.sendMessage(unreadMsg);
						/**发消息 end*/

					} else {
						state = false;
						logger.debug("appId: {} ,TemplateCode:{} , platformMode: {} , msgTarget: {}",
								new Object[] { msgPushParams.getAppId(), msgPushParams.getTemplateCode(), platformMode, pushArray[i] });
					}

				}
			}

			if (state) {
				return new Response(OutsideConstant.OUTSIDE_SUCCESS, "message push success!");
			} else {
				return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "template does not exist");
			}

		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("异常信息:method=templateMsgPush").append(",params=").append(JSONObject.toJSONString(msgPushParams));
			logger.error(stringBuilder.toString(), e);
		}
		return new Response(OutsideConstant.OUTSIDE_ERROR, "message push failure!");
	}

	/**
	 * 客服消息推送
	 * 
	 * @param msgPushParams
	 * @param responseType
	 * @return
	 */
	/*
	public Response customerMsgPush(CustomerMsgPushParams msgPushParams, boolean responseType) {
	try {
		if (StringUtils.isBlank(msgPushParams.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		}
		if (StringUtils.isBlank(msgPushParams.getPlatformType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "platformType cannot be null");
		}
		if (StringUtils.isBlank(msgPushParams.getUserType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "userType cannot be null");
		}
		if (StringUtils.isBlank(msgPushParams.getToUser())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "toUser cannot be null");
		}
		if (StringUtils.isBlank(msgPushParams.getMsgContent())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "msgContent cannot be null");
		}

		if (StringUtils.isBlank(msgPushParams.getPlatformType())) {
			return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "platformType does not exist");
		}

		HospitalCodeAndAppVo hospitalCodeAndAppVo =
				baseDatasManager.queryHospitalCodeByApp(msgPushParams.getPlatformType(), msgPushParams.getAppId());
		if (hospitalCodeAndAppVo == null) {
			return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "hospital does not exist");
		}

		// 获取openId
		// 获取openId和详情链接url
		Response response =
				getMsgInfo(msgPushParams.getAppId(), msgPushParams.getPlatformType(), hospitalCodeAndAppVo.getHospitalId(),
						hospitalCodeAndAppVo.getHospitalCode(), Integer.valueOf(msgPushParams.getUserType()),
						msgPushParams.getToUser(), null);
		if (!OutsideConstant.OUTSIDE_SUCCESS.equals(response.getResultCode())) {
			return response;
		}
		String openId = response.getResult();

		*//**
		* 把消息推送改为使用线程池推送 add by 范建明, at 2016-06-07
		*/
	/*
	MsgCustomer msgCustomer =
		new MsgCustomer(null, null, msgPushParams.getMsgContent(), BizConstant.MODE_TYPE_WEIXIN.equals(msgPushParams
				.getPlatformType()) ? 1 : 2, 1, msgPushParams.getAppId(), hospitalCodeAndAppVo.getHospitalId());

	MessagePushThreadPool.outsideMsgThreadPool.execute(new OutsideMessagePushRunnable(msgPushParams.getAppId(), null, msgPushParams
		.getPlatformType(), openId, msgCustomer));

	return new Response(OutsideConstant.OUTSIDE_SUCCESS, "message push success!");

	// String jsonStr = com.alibaba.dubbo.common.json.JSON.json(msgCustomer);
	// // 如果失败,则继续推送,三次都失败,放弃
	// JSONObject jsonObject = null;
	// int pushFailedNum = 0;
	// for (int i = 0; i < 3; i++) {
	// jsonObject = YXW.pushCustomerServiceMsg(msgPushParams.getPlatformType(), msgPushParams.getAppId(),
	// hospitalCodeAndAppVo.getPrivateKey(), openId, msgCustomer);
	// pushFailedNum = i + 1;
	// // jsonObject不为空则表示http请求成功,不再继续推送,不管推送是否成功
	// if (jsonObject != null) {
	// if (0 == jsonObject.getInteger("errcode")) {
	// // 成功
	// pushStatus = 1;
	// break;
	// } else {
	// // 失败
	// pushStatus = 2;
	// }
	//
	// } else {
	// // http请求失败
	// pushStatus = 3;
	// }
	// }
	// MsgLog msgLog = new MsgLog(msgCustomer.getCode(), jsonObject.getString("errcode"),
	// jsonObject.getString("errmsg"), msgPushParams.getAppId(), msgCustomer.getHospitalId(), openId,
	// BizConstant.MODE_TYPE_WEIXIN.equals(msgPushParams.getPlatformType()) ? 1 : 2, pushStatus,
	// pushFailedNum, jsonStr, new Date());
	// msgLogDao.add(msgLog);
	// if (pushStatus == 1) {
	// return new Response(OutsideConstant.OUTSIDE_SUCCESS, "message push success!");
	// } else {
	// return new Response(OutsideConstant.OUTSIDE_ERROR, "message push failure!");
	// }
	} catch (Exception e) {
	e.printStackTrace();
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append("异常信息:method=customerMsgPush").append(",params=").append(JSONObject.toJSONString(msgPushParams));
	logger.error(stringBuilder.toString(), e);
	}
	return new Response(OutsideConstant.OUTSIDE_ERROR, "message push failure!");
	}*/

	/**
	 * 门诊缴费扫码支付（旧版）
	 * 
	 * @param qrPayParams
	 * @param qrPayParams
	 *            true ? "xml" : "json"
	 * @return
	 */
	/*
	public Response qrPay(QrPayOldParams qrPayOldParams, boolean responseType) {
	return qrPay(qrPayOldParams.toQrPayParams(), responseType);
	}*/

	/**
	 * 门诊缴费扫码支付
	 * 
	 * @param qrPayParams
	 * @param qrPayParams
	 *            true ? "xml" : "json"
	 * @return
	 */
	/*public Response qrPay(QrPayParams qrPayParams, boolean responseType) {

		if (StringUtils.isBlank(qrPayParams.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getAgencyType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "agencyType cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getDeptName())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "deptName cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getDoctorName())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "doctorName cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getPatCardType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "patCardType cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getPatCardNo())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "patCardNo cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getPatName())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "patName cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getMzFeeId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "mzFeeId cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getTotalFee())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "totalFee cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getPayFee())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "payFee cannot be null");
		} else if (StringUtils.isBlank(qrPayParams.getPayFee())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "payFee cannot be null");
		}

		try {
			QrPayResult payResponse = new QrPayResult();

			// 模式1：原生扫码支付
			if ("0".equals(qrPayParams.getNeedPrepare())) {

				// 业务逻辑
				// 插入订单
				Map<String, Object> saveOrderRes = clinicQrService.saveQrOrderRecord(qrPayParams);
				if (saveOrderRes == null) {
					return new Response(OutsideConstant.OUTSIDE_ERROR, "生成订单失败");
				} else if (!"0".equals(saveOrderRes.get("resultCode").toString())) {
					return new Response(OutsideConstant.OUTSIDE_ERROR, "生成订单失败：".concat(String.valueOf(saveOrderRes.get("resultMessage"))));
				}

				String orderNo = saveOrderRes.get("orderNo").toString();

				ClinicQrRecord clinicQrRecord = (ClinicQrRecord) saveOrderRes.get(BizConstant.COMMON_ENTITY_KEY);

				if ("1".equals(qrPayParams.getAgencyType())) { // wechat
					PayWechat payWechat = clinicQrService.buildWechatPayInfo(clinicQrRecord);

					// String accessToken = WechatSDK.getAccessToken(payWechat.getAppId(), payWechat.getSecret());
					String url = WechatSDK.getQrPayUrl(payWechat.getAppId(), payWechat.getMchId(), orderNo, payWechat.getKey());
					if (StringUtils.isNotBlank(url)) {
						payResponse.setUrl(url);
					} else {
						return new Response(OutsideConstant.OUTSIDE_ERROR, "生成微信扫码支付失败");
					}
				} else if ("2".equals(qrPayParams.getAgencyType())) { // alipay
					PayAli payAli = clinicQrService.buildAliPayInfo(clinicQrRecord);

					HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
					HospIdAndAppSecretVo hospitalAndAppSecret =
							hospitalCache.findAppSecretByAppId(payAli.getAppId(), AlipayConstant.ALIPAY);

					// String privateKey = payAli.getPrivateKey();
					String privateKey = hospitalAndAppSecret.getAppSecret();

					String totalFee = String.valueOf(Integer.valueOf(payAli.getTotalFee()) / 100.0);

					JSONArray goods_detail_ja = new JSONArray();

					JSONObject goods_detail1 = new JSONObject();
					goods_detail1.put("goods_id", "mz001");
					goods_detail1.put("goods_name", "门诊缴费");
					goods_detail1.put("quantity", "1");
					goods_detail1.put("price", totalFee);
					goods_detail1.put("body",
							"门诊缴费（".concat(clinicQrRecord.getDeptName()).concat("-").concat(clinicQrRecord.getDoctorName()).concat("）"));
					goods_detail_ja.add(goods_detail1);

					String notifyUrl = PayUtils.genertorNotifyURLQR(payAli); // yxw_trade_api.jar.2.0的方法

					JSONObject jsonRes =
							AlipaySDK.getQrPayUrl(payAli.getAppId(), privateKey, orderNo, totalFee, "门诊缴费", "门诊缴费", goods_detail_ja,
									clinicQrRecord.getCardNo(), notifyUrl);

					if (jsonRes.getBoolean("flag")) {
						payResponse.setUrl(jsonRes.getString("url"));
					} else {
						return new Response(OutsideConstant.OUTSIDE_ERROR, jsonRes.getString("msg"));
					}
				}
			}
			// 模式二：跳转页面支付
			else if ("1".equals(qrPayParams.getNeedPrepare())) {
				String url = SystemConfig.getStringValue(ReceiveConstant.QR_PAY_FORWARD_URL);
				if (StringUtils.isNotBlank(url)) {
					String shortUrl = "";

					// 需要转换短链接
					if ("1".equals(qrPayParams.getAgencyType())) { // wechat
						// 根据appId获取医院ID和app密钥
						// HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
						// HospIdAndAppSecretVo hospitalAndAppSecret =
						// hospitalCache.findAppSecretByAppId(qrPayParams.getAppId(), WechatConstant.WECHAT);

						// String accessToken = WechatSDK.getAccessToken(hospitalAndAppSecret.getAppId(),
						// hospitalAndAppSecret.getAppSecret());

						// shortUrl = WechatUrlUtil.getShortUrl(accessToken, url + "?" +
						// qrPayParams.toQueryString("utf-8"));

						// 微信生成短链接有每日1000次限额
						shortUrl = YxwUrlUtil.getShortUrl(url.concat("?").concat(qrPayParams.toQueryString("utf-8")));
					} else if ("2".equals(qrPayParams.getAgencyType())) { // alipay
						// return new Response(OutsideConstant.OUTSIDE_ERROR, "支付宝不支持预处理扫码支付");

						shortUrl = YxwUrlUtil.getShortUrl(url.concat("?").concat(qrPayParams.toQueryString("utf-8")));
					}

					if (StringUtils.isNotBlank(shortUrl)) {
						payResponse.setUrl(shortUrl);
					} else {
						return new Response(OutsideConstant.OUTSIDE_ERROR, "转换短链接失败：".concat("1".equals(qrPayParams.getAgencyType())
								? "wechat" : "alipay"));
					}

				} else {
					return new Response(OutsideConstant.OUTSIDE_ERROR, "支付页面未配置");
				}
			} else {
				return new Response(OutsideConstant.OUTSIDE_ERROR, "needPrepare value error!");
			}

			String result = "";
			if (responseType) {
				XStream stream = new XStream();
				stream.alias("qrPayResult", QrPayResult.class);
				result = stream.toXML(payResponse);
			} else {
				result = com.alibaba.fastjson.JSON.toJSONString(payResponse);
			}
			// logger.info(result);
			return new Response(OutsideConstant.OUTSIDE_SUCCESS, "", result);

		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("异常信息:method=qrPay").append(",params=").append(JSONObject.toJSONString(qrPayParams));
			logger.error(stringBuilder.toString(), e);
		}
		return new Response(OutsideConstant.OUTSIDE_ERROR, "Get qr pay url failure!");
	}
	*/
	/**
	 * 接口判断
	 */
	@Override
	public Response openService(Request request) {
		if (StringUtils.isBlank(request.getMethodCode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "methodCode cannot be null");
		}

		if (StringUtils.isBlank(request.getParams())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "params cannot be null");
		}

		boolean responseType = true;
		if (StringUtils.isNotBlank(request.getResponseType()) && request.getResponseType().equals("1")) {
			responseType = false;
		}

		logger.info("methodCode: {} ,responseType:{} , params(): {}.", new Object[] { request.getMethodCode(),
				responseType ? "xml" : "json",
				request.getParams() });
		Response response = new Response();

		if (!ReceiveConstant.methodCodeParams.contains(request.getMethodCode().trim())) {
			response = new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "methodCode does not exist");
		} else {
			if (request.getMethodCode().trim().equals(ReceiveConstant.METHOD_STOP_REG)) {
				StopRegister stopRegister = (StopRegister) Utils.xmlToObject(request.getParams(), StopRegister.class);
				if (stopRegister != null) {
					response = stopReg(stopRegister);
				} else {
					response = new Response(OutsideConstant.PARAMETER_CONVERSION_FAILURE, "params conversion failure");
				}
			}
			if (request.getMethodCode().trim().equals(ReceiveConstant.METHOD_REFUND_GENERAL)) {
				RgParams rgParams = (RgParams) Utils.xmlToObject(request.getParams(), RgParams.class);
				if (rgParams != null) {
					response = refundGeneral(rgParams);
				} else {
					response = new Response(OutsideConstant.PARAMETER_CONVERSION_FAILURE, "params conversion failure");
				}
			}
			if (request.getMethodCode().trim().equals(ReceiveConstant.METHOD_ORDERS_QUERY)) {
				OrdersParams ordersParams = (OrdersParams) Utils.xmlToObject(request.getParams(), OrdersParams.class);
				if (ordersParams != null) {
					response = ordersQuery(ordersParams, responseType);
				} else {
					response = new Response(OutsideConstant.PARAMETER_CONVERSION_FAILURE, "params conversion failure");
				}
			}
			if (request.getMethodCode().trim().equals(ReceiveConstant.METHOD_TEMPLATE_MSG_PUSH)) {
				TemplateMsgPushParams msgPushParams = Utils.xmlToObject(request.getParams(), TemplateMsgPushParams.class);
				if (msgPushParams != null) {
					response = templateMsgPush(msgPushParams, responseType);
				} else {
					response = new Response(OutsideConstant.PARAMETER_CONVERSION_FAILURE, "params conversion failure");
				}
			}
			if (request.getMethodCode().trim().equals(ReceiveConstant.METHOD_CUSTOMER_MSG_PUSH)) {
				// 暂不支持客服消息
				response = new Response(OutsideConstant.OUTSIDE_OPERATED, "new app does not support customMsg push.");
			}

			if (request.getMethodCode().trim().equals(ReceiveConstant.METHOD_REG_ORDERS_QUERY)) {
				RegOrdersParams regOrdersParams = Utils.xmlToObject(request.getParams(), RegOrdersParams.class);
				if (regOrdersParams != null) {
					response = regOrdersQuery(regOrdersParams, responseType);
				} else {
					response = new Response(OutsideConstant.PARAMETER_CONVERSION_FAILURE, "params conversion failure");
				}
			}
		}
		return response;
	}

	/**
	 * 停诊
	 * 
	 * @param sr
	 * @return
	 */
	public Response stopReg(StopRegister sr) {
		if (StringUtils.isBlank(sr.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		}
		Response result = null;
		//		List<HospitalCodeInterfaceAndAppVo> vos = hospitalService.queryCodeAndInterfaceIdAndAppByAppId(sr.getAppId());
		List<HospIdAndAppSecretVo> vos = null;
		List<Object> cacheParams = new ArrayList<Object>();
		cacheParams.add(sr.getAppId());
		List<Object> cacheResults = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", cacheParams);
		if (!CollectionUtils.isEmpty(cacheResults)) {
			String source = JSON.toJSONString(cacheResults);
			vos = JSON.parseArray(source, HospIdAndAppSecretVo.class);
		}

		if (CollectionUtils.isEmpty(vos)) {
			return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "appId does not exist");
		}

		String hospitalCode = vos.get(0).getHospCode();
		RuleEdit ruleEdit = ruleConfigManager.getRuleEditByHospitalCode(hospitalCode);
		if (ruleEdit == null || ruleEdit.getAcceptStopInfoType().intValue() == RuleConstant.IS_ACCEPT_STOP_INFO_TYPE_NO) {
			return new Response(OutsideConstant.OUTSIDE_ERROR, "the rules do not permit");
		}
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(sr.getOrderList())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "orderlist cannot be null");
		} else {
			String[] item = sr.getOrderList().split(",");
			Set<String> orderSet = new HashSet<String>(item.length);
			for (String i : item) {
				orderSet.add(i);
			}
			for (String string : orderSet) {
				sb.append("\"").append(string).append("\",");
			}
		}
		if (StringUtils.isBlank(sb.toString())) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "orderlist format error");
		}

		String condition = sb.substring(0, sb.length() - 1);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("condition", condition);
		paramsMap.put("searchType", "1");
		List<RegisterRecord> records = registerService.findStopListByProcedure(paramsMap);

		//		Map<String, String> stopRegOrders = stopRegisterExceptionCache.getStopRegOrders();
		Map<String, String> stopRegOrders = null;
		cacheParams.clear();
		cacheResults = serveComm.get(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "getStopRegOrders", cacheParams);
		if (!CollectionUtils.isEmpty(cacheResults)) {
			stopRegOrders = (Map<String, String>) cacheResults.get(0);
		} else {
			stopRegOrders = new HashMap<String, String>(0);
		}

		logger.info("stopRegOrders已有订单:{}.", JSON.toJSONString(stopRegOrders));
		logger.info("停诊挂号记录，appId:{} , orderList : {} , records size : {}.",
				new Object[] { sr.getAppId(), sr.getOrderList(), records.size() });
		if (CollectionUtils.isEmpty(records)) {
			paramsMap.put("condition", condition);
			paramsMap.put("searchType", "2");
			List<RegisterRecord> stoptList = registerService.findStopListByProcedure(paramsMap);
			if (!CollectionUtils.isEmpty(stoptList) && stoptList.size() == sr.getOrderList().split(",").length) {
				result = new Response(OutsideConstant.OUTSIDE_OPERATED, "停诊已经受理过");
			} else {
				result = new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "stop registration is null");
			}
		} else {
			List<RegisterRecord> dealRecords = new ArrayList<RegisterRecord>();
			for (RegisterRecord record : records) {// 在停诊业务处理之前将停诊订单号添加到处理map的集合中，如果存在则不添加
				boolean isFlag = !stopRegOrders.containsKey(record.getOrderNo());
				logger.info("stopRegOrder:{}, 是否处理:{}", record.getOrderNo(), isFlag);
				if (isFlag) {
					stopRegOrders.put(record.getOrderNo(), String.valueOf(System.currentTimeMillis()));
					//					stopRegisterExceptionCache.addStopRegOrders(stopRegOrders);
					List<Object> params = new ArrayList<Object>();
					params.add(stopRegOrders);
					serveComm.set(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "addStopRegOrders", params);

					dealRecords.add(record);
				}
			}

			if (dealRecords.size() > 0) {
				// 停诊单入库
				StopRegisterRecord stopRegisterRecord = null;
				List<StopRegisterRecord> stopRegisterRecords = new ArrayList<>(dealRecords.size());
				for (RegisterRecord record : dealRecords) {
					stopRegisterRecord = new StopRegisterRecord();
					BeanUtils.copyProperties(record, stopRegisterRecord);
					stopRegisterRecord.setBranchId(record.getBranchHospitalId());
					stopRegisterRecord.setLaunchTime(System.currentTimeMillis());
					if (record.getPayStatus() == OrderConstant.STATE_PAYMENT) {
						stopRegisterRecord.setHasTrade((byte) 1);
					}
					stopRegisterRecords.add(stopRegisterRecord);
				}
				stopRegisterRecordService.batchInsert(stopRegisterRecords);

				Thread thread = new Thread(new StopRegRunnable(dealRecords));
				thread.start();
			}

			result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "停诊已受理");
		}

		List<Object> params = new ArrayList<Object>();
		List<Object> stopRegisterOrders = serveComm.get(CacheType.STOP_REGISTER_EXCEPTION_CACHE, "getStopRegOrders", params);

		logger.info("stopRegisterOrders新增后订单:{}.", JSON.toJSONString(stopRegisterOrders));
		return result;
	}

	/**
	 * 退费
	 * 
	 * @param rg
	 * @return
	 */
	public Response refundGeneral(RgParams rg) {
		// 检查参数是否为空
		Response result = checkRefundGeneral(rg);
		if (result != null) {
			return result;
		} else {

			if (rg.getRefundType().equals(String.valueOf(BizConstant.BIZ_TYPE_REGISTER))) {
				RegisterRecord record = registerService.findRecordByOrderNo(rg.getPsOrdNum());
				if (record != null) {
					if ( ( record.getIsException() != null && record.getIsException().equals(BizConstant.IS_HAPPEN_EXCEPTION_YES) )
							|| ( record.getIsHandleSuccess() != null && record.getIsHandleSuccess().equals(BizConstant.HANDLED_FAILURE) )) {
						result = new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, "订单异常，请联系相关人员");
					} else {
						if (!record.getAppId().equals(rg.getAppId())) {
							result = new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, "订单appId不匹配");
						} else {
							result = interFaceService.refundRegisterOriginalChannel(record, rg);
						}
					}

				} else {
					result = new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "psOrdNum does not exist");
				}
			} else if (rg.getRefundType().equals(String.valueOf(BizConstant.BIZ_TYPE_CLINIC))) {

				ClinicRecord record = clinicService.findRecordByOrderNo(rg.getPsOrdNum());
				if (record != null) {
					if ( ( record.getIsException() != null && record.getIsException().equals(BizConstant.IS_HAPPEN_EXCEPTION_YES) )
							|| ( record.getIsHandleSuccess() != null && record.getIsHandleSuccess().equals(BizConstant.HANDLED_FAILURE) )) {
						result = new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, "订单异常，请联系相关人员");
					} else {
						if (!record.getAppId().equals(rg.getAppId())) {
							result = new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, "订单appId不匹配");
						} else {
							if (!StringUtils.isBlank(rg.getPartialOrAllrefund()) && rg.getPartialOrAllrefund().equals("2")) {// 门诊部分退费
								result = interFaceService.refundClinicOriginalChannelPart(record, rg);
							} else {
								result = interFaceService.refundClinicOriginalChannel(record, rg);
							}
						}
					}

				} else {
					result = new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "psOrdNum does not exist");
				}

			} else if (rg.getRefundType().equals(String.valueOf(BizConstant.BIZ_TYPE_DEPOSIT))) {
				//暂时没有住院押金
			}

			return result;
		}
	}

	/**
	 * 订单查询 根据支付时间
	 * 
	 * @param op
	 * @param responseType
	 * @return
	 */
	public Response ordersQuery(OrdersParams op, boolean responseType) {

		try {
			logger.info("OrdersParams:{}.", com.alibaba.fastjson.JSON.toJSONString(op));
			// 检查参数是否为空
			Response result = checkOrdersParams(op);
			if (result != null) {
				return result;
			} else {
				//				List<HospitalCodeInterfaceAndAppVo> vos = hospitalService.queryCodeAndInterfaceIdAndAppByAppId(op.getAppId());
				List<HospIdAndAppSecretVo> vos = null;
				List<Object> cacheParams = new ArrayList<Object>();
				cacheParams.add(op.getAppId());
				List<Object> cacheResults = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", cacheParams);
				if (!CollectionUtils.isEmpty(cacheResults)) {
					String source = JSON.toJSONString(cacheResults);
					vos = JSON.parseArray(source, HospIdAndAppSecretVo.class);
				}

				if (CollectionUtils.isEmpty(vos)) {
					return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "appId does not exist");
				}

				String hospitalId = vos.get(0).getHospId();

				if (StringUtils.isBlank(op.getScope())) {
					op.setScope("0");
				}

				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("hospitalId", hospitalId);
				paramMap.put("branchCode", op.getBranchCode());
				paramMap.put("tradeMode", op.getTradeMode());
				paramMap.put("startTime", op.getStartTime());
				paramMap.put("endTime", op.getEndTime());
				paramMap.put("scope", op.getScope());

				List<OrdersQueryResult> item = new ArrayList<OrdersQueryResult>();
				if (!op.getOrderMode().equals(String.valueOf(ReceiveConstant.QUERY_ALL_TYPE))) {
					if (op.getOrderMode().equals(String.valueOf(BizConstant.BIZ_TYPE_REGISTER))) {
						// 挂号订单查询
						List<RegisterRecord> registerRecords = registerService.findListByProcedure(paramMap);
						logger.info("【对外接口调用】ordersQuery查询挂号订单,存储过程查询到订单总数:{}", registerRecords != null ? registerRecords.size()
								: "registerRecords is null");
						item.addAll(listVoConvertForRegisterRecord(registerRecords, op.getOrderMode()));
					}
					if (op.getOrderMode().equals(String.valueOf(BizConstant.BIZ_TYPE_CLINIC))) {
						// 门诊订单查询
						List<ClinicRecord> clinicRecords = clinicService.findListByProcedure(paramMap);
						logger.info("【对外接口调用】ordersQuery查询门诊订单,存储过程查询到订单总数:{}", clinicRecords != null ? clinicRecords.size()
								: "clinicRecords is null");
						item.addAll(listVoConvertForClinicRecord(clinicRecords, op.getOrderMode()));
					}
					if (op.getOrderMode().equals(String.valueOf(BizConstant.BIZ_TYPE_DEPOSIT))) {
						/*// 住院押金补缴订单查询
						List<DepositRecord> depositRecords = depositRecordService.findListByProcedure(paramMap);
						logger.info("【对外接口调用】ordersQuery查询住院订单,存储过程查询到订单总数:{}", depositRecords != null ? depositRecords.size()
								: "depositRecords is null");
						item.addAll(listVoConvertForDepositRecord(depositRecords, op.getOrderMode()));*/
					}
				} else {
					// 挂号订单查询
					List<RegisterRecord> registerRecords = registerService.findListByProcedure(paramMap);
					// 门诊订单查询
					List<ClinicRecord> clinicRecords = clinicService.findListByProcedure(paramMap);
					// 住院押金补缴订单查询
					//					List<DepositRecord> depositRecords = depositRecordService.findListByProcedure(paramMap);

					logger.info("【对外接口调用】ordersQuery查询挂号订单,存储过程查询到订单总数:{}", registerRecords != null ? registerRecords.size()
							: "registerRecords is null");
					logger.info("【对外接口调用】ordersQuery查询门诊订单,存储过程查询到订单总数:{}", clinicRecords != null ? clinicRecords.size()
							: "clinicRecords is null");
					//					logger.info("【对外接口调用】ordersQuery查询住院订单,存储过程查询到订单总数:{}", depositRecords != null ? depositRecords.size()
					//							: "depositRecords is null");

					item.addAll(listVoConvertForRegisterRecord(registerRecords, String.valueOf(BizConstant.BIZ_TYPE_REGISTER)));
					item.addAll(listVoConvertForClinicRecord(clinicRecords, String.valueOf(BizConstant.BIZ_TYPE_CLINIC)));
					//					item.addAll(listVoConvertForDepositRecord(depositRecords, String.valueOf(BizConstant.BIZ_TYPE_DEPOSIT)));
				}
				logger.info("【对外接口调用】ordersQuery查询订单item总数:{}", item.size());
				if (item.size() > 0) {
					XStream stream = new XStream();
					stream.alias("ordersQueryResult", OrdersQueryResult.class);
					stream.alias("item", List.class);
					if (responseType) {
						result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(item));
					} else {
						result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", com.alibaba.fastjson.JSON.toJSONString(item));
					}

				} else {
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "data not found");
				}
			}

			logger.info("OrderQuery.getResultCode:{},getResultMessage:{},getResult:{}", result.getResultCode(), result.getResultMessage(),
					result.getResult() == null ? "result is null" : "result is not null");
			return result;

		} catch (Exception e) {
			logger.error("ordersQuery方法异常：", e.getMessage());
			return new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, e.getMessage());
		}
	}

	/**
	 * 检查参数 订单查询
	 * 
	 * @param op
	 * @return
	 */
	private Response checkOrdersParams(OrdersParams op) {

		if (StringUtils.isBlank(op.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		}

		if (StringUtils.isBlank(op.getOrderMode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "orderMode cannot be null");
		}

		if (StringUtils.isBlank(op.getTradeMode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "tradeMode cannot be null");
		}

		if (StringUtils.isBlank(op.getStartTime())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "startTime cannot be null");
		}

		if (StringUtils.isBlank(op.getEndTime())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "endTime cannot be null");
		}

		if (!Utils.validDate(op.getStartTime(), Utils.YYYYMMDDHHMMSS)) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "startTime format error");
		}

		if (!Utils.validDate(op.getEndTime(), Utils.YYYYMMDDHHMMSS)) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "endTime format error");
		}

		return null;
	}

	/**
	 * 挂号订单查询 根据挂号时间
	 * 
	 * @param op
	 * @param responseType
	 * @return
	 */
	public Response regOrdersQuery(RegOrdersParams op, boolean responseType) {
		logger.info("RegOrdersParams:{}.", com.alibaba.fastjson.JSON.toJSONString(op));
		// 检查参数是否为空
		Response result = checkRegOrdersParams(op);
		if (result != null) {
			return result;
		} else {
			//			List<HospitalCodeInterfaceAndAppVo> vos = hospitalService.queryCodeAndInterfaceIdAndAppByAppId(op.getAppId());

			List<HospIdAndAppSecretVo> vos = null;
			List<Object> cacheParams = new ArrayList<Object>();
			cacheParams.add(op.getAppId());
			List<Object> cacheResults = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", cacheParams);
			if (!CollectionUtils.isEmpty(cacheResults)) {
				String source = JSON.toJSONString(cacheResults);
				vos = JSON.parseArray(source, HospIdAndAppSecretVo.class);
			}

			if (CollectionUtils.isEmpty(vos)) {
				return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "appId does not exist");
			}

			String hospitalId = vos.get(0).getHospId();
			String hashTableName = (String) ReceiveConstant.tradeTypeParams.get(String.valueOf(BizConstant.BIZ_TYPE_REGISTER));
			List<RegOrdersQueryResult> item =
					interFaceService.queryRegisterRecordByScheduleDate(hospitalId, op.getBranchCode(), op.getTradeMode(),
							op.getStartDate(), op.getEndDate(), hashTableName, op.getRegType());

			if (item.size() > 0) {
				XStream stream = new XStream();
				stream.alias("ordersQueryResult", RegOrdersQueryResult.class);
				stream.alias("item", List.class);
				if (responseType) {
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", stream.toXML(item));
				} else {
					result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "", com.alibaba.fastjson.JSON.toJSONString(item));
				}

			} else {
				result = new Response(OutsideConstant.OUTSIDE_SUCCESS, "data not found");
			}
		}
		return result;
	}

	/**
	 * 检查参数 挂号订单查询
	 * 
	 * @param op
	 * @return
	 */
	private Response checkRegOrdersParams(RegOrdersParams op) {

		if (StringUtils.isBlank(op.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		}

		if (StringUtils.isBlank(op.getRegType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "regType cannot be null");
		}

		if (StringUtils.isBlank(op.getTradeMode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "tradeMode cannot be null");
		}

		if (StringUtils.isBlank(op.getStartDate())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "startDate cannot be null");
		}

		if (StringUtils.isBlank(op.getEndDate())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "endDate cannot be null");
		}

		if (!Utils.validDate(op.getStartDate(), Utils.YYYYMMDD)) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "startDate format error");
		}

		if (!Utils.validDate(op.getEndDate(), Utils.YYYYMMDD)) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "endDate format error");
		}

		return null;
	}

	/**
	 * 检查参数 退费
	 * 
	 * @return
	 */
	private Response checkRefundGeneral(RgParams rg) {

		if (StringUtils.isBlank(rg.getAppId())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "appId cannot be null");
		}

		if (StringUtils.isBlank(rg.getTradeMode())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "trademode cannot be null");
		}

		if (StringUtils.isBlank(rg.getRefundType())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "refundType cannot be null");
		}

		if (StringUtils.isBlank(rg.getHisNewOrdNum())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "hisNewOrdNum cannot be null");
		}

		if (Utils.getStrLength(rg.getHisNewOrdNum()) > 50) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "hisNewOrdNum length greater than 50 characters");
		}

		if (StringUtils.isBlank(rg.getPsOrdNum())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "psOrdNum cannot be null");
		}

		if (StringUtils.isBlank(rg.getRefundAmout())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "refundAmout cannot be null");
		}

		if (!Utils.isNumeric(rg.getRefundAmout())) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "refundAmout must be a positive integer");
		}

		if (StringUtils.isBlank(rg.getRefundTime())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "refundTime cannot be null");
		}

		if (!Utils.validDate(rg.getRefundTime(), Utils.YYYYMMDDHHMMSS)) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "refundTime format error");
		}

		if (StringUtils.isBlank(rg.getRefundReason())) {
			return new Response(OutsideConstant.PARAMS_CANNOT_BE_NULL, "refundReason cannot be null");
		}

		if (Utils.getStrLength(rg.getRefundReason()) > 200) {
			return new Response(OutsideConstant.PARAMS_FORMAT_ERROR, "refundReason length greater than 50 characters");
		}

		if (StringUtils.isBlank(rg.getTradeMode()) && StringUtils.equals(ReceiveConstant.HIS_TRADE_MODE, rg.getTradeMode())) {
			return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "tradeMode does not exist");
		}

		if (!ReceiveConstant.tradeTypeParams.containsKey(rg.getRefundType())) {
			return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "refundType does not exist");
		}

		return null;
	}

	private Response getMsgInfo(String appId, String appCode, String hospitalId, String hospitalCode, int userType, String toUser,
			String templateCode) {
		Response response = new Response(OutsideConstant.OUTSIDE_SUCCESS, null);
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 获取openId
			Record record = null;
			switch (userType) {
			case 1:
				// 就诊卡号 -- 根据appId, appCode, 就诊卡号 找到这个人的绑卡信息。拿到openid
				MedicalCardService medicalCardService = SpringContextHolder.getBean(MedicalCardService.class);
				List<MedicalCard> medicalCards =
						medicalCardService.findCardsByCardNoAndAppCodeAndHospitalCode(toUser, appCode, hospitalCode);

				if (!CollectionUtils.isEmpty(medicalCards)) {
					record = new ClinicRecord();
					record.setAppId(appId);
					record.setAppCode(appCode);
					record.setCardNo(toUser);
					record.setOpenId(medicalCards.get(0).getOpenId());//只有一条记录
				} else {
					response.setResultCode(OutsideConstant.NOT_RETRIEVED_RESULTS);
					response.setResultMessage("cardNo does not exist");
				}

				break;
			case 2:
				// openId -- 直接返回.
				record = new ClinicRecord();
				record.setAppId(appId);
				record.setAppCode(appCode);
				record.setOpenId(toUser);
				break;
			case 3:
				// 挂号订单号 -- 通过挂号提供的方法去找这个订单
				RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);
				record = registerService.findRecordByOrderNo(toUser);
				if (record == null) {
					response.setResultCode(OutsideConstant.NOT_RETRIEVED_RESULTS);
					response.setResultMessage("orderNo does not exist");
				}
				break;
			case 4:
				// 门诊订单号 -- 通过门诊提供的方法去找这个订单
				ClinicService clinicService = SpringContextHolder.getBean(ClinicService.class);
				record = clinicService.findRecordByOrderNo(toUser);
				if (record == null) {
					return new Response(OutsideConstant.NOT_RETRIEVED_RESULTS, "orderNo does not exist");
				}
				break;
			case 5:
				logger.error("暂不支持的userType:{}", userType);
				response.setResultCode(OutsideConstant.NOT_RETRIEVED_RESULTS);
				response.setResultMessage("无效的userType");
				break;

			default:
				logger.error("无效的userType:{}", userType);
				response.setResultCode(OutsideConstant.NOT_RETRIEVED_RESULTS);
				response.setResultMessage("无效的userType");
				break;
			}

			if (!OutsideConstant.OUTSIDE_SUCCESS.equals(response.getResultCode())) {
				return response;
			}

			map.put("openId", record.getOpenId());

			if (StringUtils.isBlank(templateCode)) {
				response.setResultCode(OutsideConstant.OUTSIDE_SUCCESS);
				response.setResult(record.getOpenId());
				return response;
			}
			// 门诊待缴费提醒
			if (MsgTemplateCodeConstant.wechat_template_code_12000.equals(templateCode)
					|| MsgTemplateCodeConstant.alipay_template_code_22000.equals(templateCode)) {
				// 门诊缴费待缴费通知
				String hostUrl = SystemConfig.getStringValue("url_prefix");
				String url =
						hostUrl.concat(ClinicConstant.CLINIC_URL_NEED_TO_PAY).concat(BizConstant.URL_PARAM_CHAR_FIRST)
								.concat(BizConstant.URL_PARAM_APPID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getAppId())
								.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_APPCODE)
								.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getAppCode())
								.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_OPEN_ID)
								.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getOpenId());
				if (StringUtils.isNotBlank(record.getCardNo())) {
					url =
							url.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_CARD_NO)
									.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getCardNo());
				}

				if (logger.isDebugEnabled()) {
					logger.debug("待缴费通知URL：{}", url);
				}
				map.put("url", url);
			} else if (MsgTemplateCodeConstant.wechat_template_code_41001.equals(templateCode)
					|| MsgTemplateCodeConstant.alipay_template_code_41001.equals(templateCode)) {
				// 待缴费推送
			} else if (MsgTemplateCodeConstant.wechat_template_code_13000.equals(templateCode)
					|| MsgTemplateCodeConstant.alipay_template_code_23000.equals(templateCode)) {
				// 住院清单提醒

				/*String hostUrl = SystemConfig.getStringValue("url_prefix");
				String url =
						hostUrl.concat(InpatientConstant.INPATIENT_URL_NEED_TO_DAYLIST).concat(BizConstant.URL_PARAM_CHAR_FIRST)
								.concat(BizConstant.URL_PARAM_APPID).concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getAppId())
								.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_APPCODE)
								.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getAppCode())
								.concat(BizConstant.URL_PARAM_CHAR_CONCAT).concat(BizConstant.URL_PARAM_OPEN_ID)
								.concat(BizConstant.URL_PARAM_CHAR_ASSGIN).concat(record.getOpenId());
				if (logger.isDebugEnabled()) {
					logger.debug("住院清单提醒URL：{}", url);
				}
				map.put("url", url);*/

			} else {
				response.setResult(null);
				response.setResultCode(OutsideConstant.NOT_RETRIEVED_RESULTS);
				response.setResultMessage("无效的templateCode");
				return response;
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("异常信息:method=getMsgInfo").append(",params=[userType:").append(userType).append(",toUser:").append(toUser)
					.append(",appCode:").append(appCode).append("]");
			logger.error(stringBuilder.toString(), e);
			response.setResult(null);
			response.setResult(OutsideConstant.OUTSIDE_REQUEST_ERROR);
			response.setResultMessage("exception");
		}
		response.setResult(JSON.toJSONString(map));
		return response;
	}

	/**
	 * 挂号转换
	 * 
	 * @param list
	 * @return
	 */
	private List<OrdersQueryResult> listVoConvertForRegisterRecord(List<RegisterRecord> list, String orderMode) {
		List<OrdersQueryResult> queryResults = new ArrayList<OrdersQueryResult>();
		if (list != null && list.size() > 0) {
			for (RegisterRecord record : list) {
				queryResults.add(voConvert(record.getTradeTime(), record.getTradeType(), String.valueOf(record.getTradeMode()),
						record.getAgtOrdNum(), record.getAgtRefundOrdNum(), record.getHisOrdNo(), record.getRefundHisOrdNo(),
						record.getOrderNo(), record.getRefundOrderNo(), String.valueOf(record.getRealRegFee() + record.getRealTreatFee()),
						String.valueOf(record.getRefundTotalFee()), orderMode));
			}
		}
		return queryResults;
	}

	/**
	 * 门诊转换
	 * 
	 * @param list
	 * @return
	 */
	private List<OrdersQueryResult> listVoConvertForClinicRecord(List<ClinicRecord> list, String orderMode) {
		List<OrdersQueryResult> queryResults = new ArrayList<OrdersQueryResult>();
		if (list != null && list.size() > 0) {
			for (ClinicRecord record : list) {
				queryResults.add(voConvert(record.getTradeTime(), record.getTradeType(), String.valueOf(record.getTradeMode()),
						record.getAgtOrdNum(), record.getAgtRefundOrdNum(), record.getHisOrdNo(), record.getRefundHisOrdNo(),
						record.getOrderNo(), record.getRefundOrderNo(), String.valueOf(record.getPayFee()),
						String.valueOf(record.getRefundTotalFee()), orderMode));
			}
		}
		return queryResults;
	}

	/**
	 * 住院押金补缴转换
	 * 
	 * @param list
	 * @return
	 */
	/*	private List<OrdersQueryResult> listVoConvertForDepositRecord(List<DepositRecord> list, String orderMode) {
			List<OrdersQueryResult> queryResults = new ArrayList<OrdersQueryResult>();
			if (list != null && list.size() > 0) {
				for (DepositRecord record : list) {
					queryResults.add(voConvert(record.getTradeTime(), record.getTradeType(), String.valueOf(record.getPayMode()),
							record.getAgtOrdNum(), record.getAgtRefundOrdNum(), record.getHisOrdNo(), record.getRefundHisOrdNo(),
							record.getOrderNo(), record.getRefundOrderNo(), String.valueOf(record.getPayFee()),
							String.valueOf(record.getRefundTotalFee()), orderMode));
				}
			}
			return queryResults;
		}*/

	/**
	 * 参数写入
	 * 
	 * @param tradeTime
	 * @param tradeType
	 * @param tradeMode
	 * @param agtOrdNum
	 * @param agtRefundOrdNum
	 * @param hisOrdNo
	 * @param refundHisOrdNo
	 * @param orderNo
	 * @param refundOrderNo
	 * @param payFee
	 * @param refundTotalFee
	 * @return
	 */
	private OrdersQueryResult voConvert(String tradeTime, String tradeType, String tradeMode, String agtOrdNum, String agtRefundOrdNum,
			String hisOrdNo, String refundHisOrdNo, String orderNo, String refundOrderNo, String payFee, String refundTotalFee,
			String orderMode) {
		OrdersQueryResult payQr = new OrdersQueryResult();
		payQr.setTradeTime(tradeTime);
		payQr.setTradeType(tradeType);
		payQr.setOrderMode(String.valueOf(orderMode));
		payQr.setTradeMode(Integer.valueOf(tradeMode));
		payQr.setAgtPayOrdNum(setParams(agtOrdNum));
		payQr.setHisPayOrdNum(setParams(hisOrdNo));
		payQr.setPsPayOrdNum(setParams(orderNo));
		if (tradeType.equals("1")) {
			payQr.setPayTotalFee(payFee);
			payQr.setRefundTotalFee("");
		} else {
			payQr.setAgtRefundOrdNum(setParams(agtRefundOrdNum));
			payQr.setHisRefundOrdNum(setParams(refundHisOrdNo));
			payQr.setPsRefundOrdNum(setParams(refundOrderNo));
			payQr.setRefundTotalFee(refundTotalFee);
			payQr.setPayTotalFee("");
		}
		return payQr;
	}

	private String setParams(String params) {
		if (StringUtils.isBlank(params)) {
			return "";
		} else {
			return params;
		}
	}

}
