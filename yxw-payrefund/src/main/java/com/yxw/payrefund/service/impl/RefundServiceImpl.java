package com.yxw.payrefund.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.AlipayUtil;
import com.common.AlipayConstant;
import com.common.CommonConstant;
import com.common.UnionPayConstant;
import com.common.WechatConstant;
import com.unionpay.acp.sdk.UnionPayUtil;
import com.wechat.WechatUtil;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.utils.CacheUtil;

@Service(value = "refundService")
public class RefundServiceImpl implements RefundService {

	private static Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

	@Override
	public WechatPayRefundResponse wechatPayRefund(WechatPayRefund refund) {
		if (WechatConstant.WECHAT_TEST) {
			refund.setTotalFee(WechatConstant.WECHAT_TEST_MONEY);
			refund.setRefundFee(WechatConstant.WECHAT_TEST_MONEY);
		}

		WechatPayRefundResponse response = new WechatPayRefundResponse();

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(refund.getCode(), refund.getTradeMode());
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		if (StringUtils.equals(vo.getAppId(), "wx7245e798af225271")) {//临时测试
			refund.setTotalFee(WechatConstant.WECHAT_TEST_MONEY);
			refund.setRefundFee(WechatConstant.WECHAT_TEST_MONEY);
		}

		try {
			Map<String, String> refundParams = WechatUtil.genRefundParams(vo, refund);

			String refundResponse = WechatUtil.refund(refundParams);
			if (StringUtils.isNotBlank(refundResponse)) {
				Map<String, String> refundResponseMap = WechatUtil.xml2Map(refundResponse);

				response = WechatUtil.map2WechatPayRefundResponse(refundResponseMap);
			} else {
				response = new WechatPayRefundResponse(BizConstant.METHOD_INVOKE_FAILURE, "退费失败，WechatRefundResponse is null");
			}

		} catch (Exception e) {
			response = new WechatPayRefundResponse(BizConstant.METHOD_INVOKE_EXCEPTION, "退费异常：".concat(e.getMessage()));
		}

		return response;
	}

	@Override
	public AlipayRefundResponse alipayRefund(AlipayRefund refund) {

		if (AlipayConstant.ALIPAY_TEST) {
			refund.setTotalFee(AlipayConstant.ALIPAY_TEST_MONEY);
			refund.setRefundFee(AlipayConstant.ALIPAY_TEST_MONEY);
		}

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(refund.getCode(), refund.getTradeMode());
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		Map<String, String> refundParams = AlipayUtil.genRefundParams(refund);

		AlipayRefundResponse response = AlipayUtil.tradeRefund(vo.getAppId(), vo.getAliPrivateKey(), refundParams);

		return response;
	}

	@Override
	public UnionpayRefundResponse unionpayRefund(UnionpayRefund refund) {
		if (UnionPayConstant.UNIONPAY_TEST) {
			refund.setTotalFee(UnionPayConstant.UNIONPAY_TEST_MONEY);
			refund.setRefundFee(UnionPayConstant.UNIONPAY_TEST_MONEY);
		}

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(refund.getCode(), refund.getTradeMode());
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		JSONObject attach = new JSONObject();
		attach.put("hospitalCode", refund.getCode());
		attach.put("tradeMode", refund.getTradeMode());

		UnionpayRefundResponse response;
		if (UnionPayConstant.UNIONPAY_TEST) {
			response =
					UnionPayUtil.refund(vo.getMchId(), refund.getRefundOrderNo(), refund.getRefundFee(), refund.getAgtOrderNo(), attach,
							null, null);
		} else {
			// /opt/new_platform/certs/acp303440180110054.pfx
			String certPath =
					CommonConstant.CERTS_PATH.concat(String.valueOf(java.io.File.separatorChar)).concat("acp").concat(vo.getMchId())
							.concat(".pfx");
			response =
					UnionPayUtil.refund(vo.getMchId(), refund.getRefundOrderNo(), refund.getRefundFee(), refund.getAgtOrderNo(), attach,
							certPath, vo.getCertificatePwd());
		}

		return response;
	}

}
