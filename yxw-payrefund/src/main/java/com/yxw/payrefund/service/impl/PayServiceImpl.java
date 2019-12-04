package com.yxw.payrefund.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.AlipayUtil;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.common.CommonConstant;
import com.common.UnionPayConstant;
import com.common.WechatConstant;
import com.unionpay.acp.sdk.AcpService;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.UnionPayUtil;
import com.wechat.WechatUtil;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.Unionpay;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.payrefund.service.PayService;
import com.yxw.payrefund.utils.CacheUtil;

@Service(value = "payService")
public class PayServiceImpl implements PayService {

	private static Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

	@Override
	public JSONObject wechatNativeService(WechatPay pay) throws Exception {
		return wechatService(pay, WechatConstant.WECHAT_TRADE_TYPE_NATIVE);
	}

	private JSONObject wechatService(WechatPay pay, String tradeType) throws Exception {
		JSONObject res = new JSONObject();

		// 支付测试标志
		logger.info("订单号:{}, 是否测试：{}, 微信支付 start", pay.getOrderNo(), WechatConstant.WECHAT_TEST);
		if (WechatConstant.WECHAT_TEST) {
			logger.info("pay_test_wechat_money: {}", WechatConstant.WECHAT_TEST_MONEY);
			pay.setTotalFee(WechatConstant.WECHAT_TEST_MONEY);
		}

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(pay.getCode(), pay.getTradeMode());

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (vo != null) {
			JSONObject attach = new JSONObject();
			attach.put("hospitalCode", vo.getHospitalCode());
			attach.put("tradeMode", vo.getTradeMode());
			if (StringUtils.isNotBlank(pay.getCustomAttach())) {
				attach.put("custom", pay.getCustomAttach());
			}

			//Map<String, String> payParams = WechatUtil.genPayParams(pay, vo.getAppId(), vo.getMchId(), vo.getSubMchId(), vo.getPaykey(),
			//		tradeType, CommonConstant.SERVER_IP, attach);

			Map<String, String> payParams = WechatUtil.genPayParams(pay, vo, tradeType, CommonConstant.SERVER_IP, attach);

			logger.info("订单号:{},统一下单请求数据：{}", new Object[] { pay.getOrderNo(), payParams });

			String unifiedOrderRes = WechatUtil.unifiedOrder(payParams);
			logger.info("订单号:{},微信统一支付接口返回:{}", new Object[] { pay.getOrderNo(), unifiedOrderRes });

			if (StringUtils.isNotBlank(unifiedOrderRes)) {
				Map<String, String> checkParams = WechatUtil.xml2Map(unifiedOrderRes);
				boolean checkSign = WechatUtil.checkSign(checkParams, vo.getPaykey());

				if (checkSign) {
					String returnCode = checkParams.get("return_code");
					String resultCode = checkParams.get("result_code");
					if ("SUCCESS".equalsIgnoreCase(returnCode)) {
						if ("SUCCESS".equalsIgnoreCase(resultCode)) {
							res.put("prepayId", checkParams.get("prepay_id"));// 支付prepay_id需在支付签名生成前set
							res.put("codeUrl", checkParams.get("code_url"));//扫码支付时用到
						} else {
							res.put("resultMsg", checkParams.get("err_code").concat(" : ").concat(checkParams.get("err_code_des")));
							logger.error("订单号:{},接口调用失败，返回结果:{}", pay.getOrderNo(), res.getString("resultMsg"));
						}
					} else {

					}
				} else {
					res.put("resultMsg", "验证签名失败");
					logger.error("订单号:{},验证签名失败:{}", pay.getOrderNo(), checkSign);
				}
			} else {
				res.put("resultMsg", "微信统一支付接口返回null");
			}
		} else {
			res.put("resultMsg", "没有对应的支付配置");
		}
		return res;
	}

	@Override
	public JSONObject alipayNativeService(Alipay pay) throws Exception {

		JSONObject res = new JSONObject();

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(pay.getCode(), pay.getTradeMode());

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (vo != null) {
			JSONObject attach = new JSONObject();
			attach.put("hospitalCode", vo.getHospitalCode());
			attach.put("tradeMode", vo.getTradeMode());
			if (StringUtils.isNotBlank(pay.getCustomAttach())) {
				attach.put("custom", pay.getCustomAttach());
			}

			AlipayTradePrecreateResponse response = AlipayUtil.tradePrecreate(vo.getAppId(), vo.getAliPrivateKey(), attach, pay);

			if (null != response) {
				if (response.isSuccess()) {
					if ("10000".equals(response.getCode())) {
						res.put("codeUrl", response.getQrCode());//扫码支付时用到
					} else {
						res.put("resultMsg", response.getSubCode().concat(" : ").concat(response.getSubMsg()));
					}
				} else {
					res.put("resultMsg", response.getCode().concat(" : ").concat(response.getMsg()));
				}
			} else {
				res.put("resultMsg", "AlipayTradePrecreateResponse is null");
			}
		} else {
			res.put("resultMsg", "没有对应的支付配置");
		}

		return res;
	}

	@Override
	public JSONObject unionpayNativeService(Unionpay pay) throws Exception {
		JSONObject res = new JSONObject();

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(pay.getCode(), pay.getTradeMode());

		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (vo != null) {
			JSONObject attach = new JSONObject();
			attach.put("hospitalCode", vo.getHospitalCode());
			attach.put("tradeMode", vo.getTradeMode());
			if (StringUtils.isNotBlank(pay.getCustomAttach())) {
				attach.put("custom", pay.getCustomAttach());
			}

			Map<String, String> response;
			if (UnionPayConstant.UNIONPAY_TEST) {
				response = UnionPayUtil.qrPay(pay, UnionPayConstant.UNIONPAY_TEST_MERID, attach, null, null);
			} else {
				// /opt/new_platform/certs/acp303440180110054.pfx
				String certPath =
						CommonConstant.CERTS_PATH.concat(String.valueOf(java.io.File.separatorChar)).concat("acp").concat(vo.getMchId())
								.concat(".pfx");
				response = UnionPayUtil.qrPay(pay, vo.getMchId(), attach, certPath, vo.getCertificatePwd());
			}

			if (null != response && !response.isEmpty()) {
				if (AcpService.validate(response, UnionPayConstant.encoding)) {
					LogUtil.writeLog("验证签名成功");
					String respCode = response.get("respCode");
					if ( ( "00" ).equals(respCode)) {
						res.put("codeUrl", response.get("qrCode"));//扫码支付时用到
					} else {
						//其他应答码为失败请排查原因或做失败处理
						res.put("resultMsg", respCode.concat(" : ").concat(response.get("respMsg")));
					}
				} else {
					LogUtil.writeErrorLog("验证签名失败");
					res.put("resultMsg", "验证签名失败");
				}
			} else {
				res.put("resultMsg", "UnionpayQrCode response is null");
			}
		} else {
			res.put("resultMsg", "没有对应的支付配置");
		}

		return res;
	}

}
