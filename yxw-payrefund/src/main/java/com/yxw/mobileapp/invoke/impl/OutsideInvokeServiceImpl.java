package com.yxw.mobileapp.invoke.impl;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.AlipayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.Unionpay;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefundResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefundResponse;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.invoke.OutsideConstant;
import com.yxw.mobileapp.invoke.OutsideInvokeService;
import com.yxw.mobileapp.invoke.dto.Request;
import com.yxw.mobileapp.invoke.dto.Response;
import com.yxw.payrefund.service.PayService;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.service.RefundService;
import com.yxw.payrefund.service.impl.PayServiceImpl;
import com.yxw.payrefund.service.impl.QueryServiceImpl;
import com.yxw.payrefund.service.impl.RefundServiceImpl;

public class OutsideInvokeServiceImpl implements OutsideInvokeService {

	public static Logger logger = LoggerFactory.getLogger(OutsideInvokeServiceImpl.class);

	private PayService payService = SpringContextHolder.getBean(PayServiceImpl.class);
	private RefundService refundService = SpringContextHolder.getBean(RefundServiceImpl.class);
	private QueryService queryService = SpringContextHolder.getBean(QueryServiceImpl.class);

	/**
	 * 接口判断
	 */
	@Override
	public Response openService(Request request) {
		String methodCode = request.getMethodCode();
		String params = request.getParams();
		//String responseType = request.getResponseType(); //0：xml 1：json
		logger.info("openService:methodCode=" + methodCode + ",params=" + params);
		Response response = new Response();
		try {
			Document paramsDoc = DocumentHelper.parseText("<xml>" + params + "</xml>");
			Element paramsRootEle = paramsDoc.getRootElement();

			String hospitalCode = paramsRootEle.valueOf("hospitalCode");
			String tradeMode = paramsRootEle.valueOf("tradeMode");
			String totalFee = paramsRootEle.valueOf("totalFee");
			String orderNo = paramsRootEle.valueOf("orderNo");
			String body = paramsRootEle.valueOf("body");
			String attach = paramsRootEle.valueOf("attach");

			//扫码支付
			if ("qrCodePay".equalsIgnoreCase(methodCode)) {
				//if (CommonConstant.TRADE_MODE_WECHAT_NATIVE_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				if (String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = wechatNative(hospitalCode, tradeMode, orderNo, totalFee, body, attach);
					//} else if (CommonConstant.TRADE_MODE_ALIPAY_NATIVE_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = alipayNative(hospitalCode, tradeMode, orderNo, totalFee, body, attach);
				} else if (String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = unionpayNative(hospitalCode, tradeMode, orderNo, totalFee, body, attach);
				}

			}
			//条码支付
			else if ("barCodePay".equals(methodCode)) {
				//if (CommonConstant.TRADE_MODE_WECHAT_MICRO_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				if (String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL).equalsIgnoreCase(tradeMode)) {

					//} else if (CommonConstant.TRADE_MODE_ALIPAY_MICRO_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYMICRO_VAL).equalsIgnoreCase(tradeMode)) {

				} else if (String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYMICRO_VAL).equalsIgnoreCase(tradeMode)) {

				}
			} else if ("refund".equals(methodCode)) {
				String refundOrderNo = paramsRootEle.valueOf("refundOrderNo");
				String agtOrderNo = paramsRootEle.valueOf("agtOrderNo");
				String refundFee = paramsRootEle.valueOf("refundFee");
				String refundDesc = paramsRootEle.valueOf("refundDesc");

				//if (CommonConstant.TRADE_MODE_WECHAT_NATIVE_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				if (String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = wechatRefund(hospitalCode, tradeMode, refundOrderNo, agtOrderNo, orderNo, totalFee, refundFee, refundDesc);
					//} else if (CommonConstant.TRADE_MODE_ALIPAY_NATIVE_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = alipayRefund(hospitalCode, tradeMode, refundOrderNo, agtOrderNo, orderNo, totalFee, refundFee, refundDesc);
					//} else if (CommonConstant.TRADE_MODE_WECHAT_MICRO_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL).equalsIgnoreCase(tradeMode)) {
					response = wechatRefund(hospitalCode, tradeMode, refundOrderNo, agtOrderNo, orderNo, totalFee, refundFee, refundDesc);
					//} else if (CommonConstant.TRADE_MODE_ALIPAY_MICRO_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYMICRO_VAL).equalsIgnoreCase(tradeMode)) {
					response = alipayRefund(hospitalCode, tradeMode, refundOrderNo, agtOrderNo, orderNo, totalFee, refundFee, refundDesc);
				} else if (String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = unionpayRefund(hospitalCode, tradeMode, refundOrderNo, agtOrderNo, orderNo, totalFee, refundFee, refundDesc);
				} else if (String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYMICRO_VAL).equalsIgnoreCase(tradeMode)) {
					response = unionpayRefund(hospitalCode, tradeMode, refundOrderNo, agtOrderNo, orderNo, totalFee, refundFee, refundDesc);
				}

			} else if ("query".equals(methodCode)) {
				String agtOrderNo = paramsRootEle.valueOf("agtOrderNo");
				String refundOrderNo = paramsRootEle.valueOf("refundOrderNo");
				String agtRefundOrderNo = paramsRootEle.valueOf("agtRefundOrderNo");

				//if (CommonConstant.TRADE_MODE_WECHAT_NATIVE_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				if (String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = wechatOrderQuery(hospitalCode, tradeMode, agtOrderNo, orderNo);
					//} else if (CommonConstant.TRADE_MODE_ALIPAY_NATIVE_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = alipayOrderQuery(hospitalCode, tradeMode, agtOrderNo, orderNo);
					//} else if (CommonConstant.TRADE_MODE_WECHAT_MICRO_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_WECHATMICRO_VAL).equalsIgnoreCase(tradeMode)) {
					response = wechatOrderQuery(hospitalCode, tradeMode, agtOrderNo, orderNo);
					//} else if (CommonConstant.TRADE_MODE_ALIPAY_MICRO_AUTO_SERVICE_VAL.equalsIgnoreCase(tradeMode)) {
				} else if (String.valueOf(TradeConstant.TRADE_MODE_ALIPAYMICRO_VAL).equalsIgnoreCase(tradeMode)) {
					response = alipayOrderQuery(hospitalCode, tradeMode, agtOrderNo, orderNo);
				} else if (String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYNATIVE_VAL).equalsIgnoreCase(tradeMode)) {
					response = unionpayOrderQuery(hospitalCode, tradeMode, agtOrderNo, orderNo, agtRefundOrderNo, refundOrderNo);
				} else if (String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYMICRO_VAL).equalsIgnoreCase(tradeMode)) {
					response = unionpayOrderQuery(hospitalCode, tradeMode, agtOrderNo, orderNo, agtRefundOrderNo, refundOrderNo);
				}

			} else {
				response = new Response(OutsideConstant.OUTSIDE_ERROR, methodCode.concat("方法不存在"));
			}

		} catch (Exception e) {
			logger.error("openService exception:methodCode=" + request.getMethodCode() + ",params=" + request.getParams(), e);
			//请求接口异常
			response = new Response(OutsideConstant.OUTSIDE_REQUEST_ERROR, "exception");
		}
		return response;
	}

	private Response wechatOrderQuery(String hospitalCode, String tradeMode, String agtOrderNo, String orderNo) {
		Response response = new Response();

		WechatPayOrderQuery orderQuery = new WechatPayOrderQuery();
		orderQuery.setCode(hospitalCode);
		orderQuery.setTradeMode(tradeMode);
		orderQuery.setAgtOrderNo(agtOrderNo);
		orderQuery.setOrderNo(orderNo);

		WechatPayOrderQueryResponse orderQueryResponse = queryService.wechatPayOrderQuery(orderQuery);
		if (StringUtils.equals(orderQueryResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", JSONObject.toJSONString(orderQueryResponse));
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, orderQueryResponse.getResultMsg());
		}

		return response;
	}

	private Response alipayOrderQuery(String hospitalCode, String tradeMode, String agtOrderNo, String orderNo) {
		Response response = new Response();

		AlipayOrderQuery orderQuery = new AlipayOrderQuery();
		orderQuery.setCode(hospitalCode);
		orderQuery.setTradeMode(tradeMode);
		orderQuery.setAgtOrderNo(agtOrderNo);
		orderQuery.setOrderNo(orderNo);

		AlipayOrderQueryResponse orderQueryResponse = queryService.alipayOrderQuery(orderQuery);
		if (StringUtils.equals(orderQueryResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", JSONObject.toJSONString(orderQueryResponse));
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, orderQueryResponse.getResultMsg());
		}

		return response;
	}

	private Response unionpayOrderQuery(String hospitalCode, String tradeMode, String agtOrderNo, String orderNo, String agtRefundOrderNo,
			String refundOrderNo) {
		Response response = new Response();

		UnionpayOrderQuery orderQuery = new UnionpayOrderQuery();
		orderQuery.setCode(hospitalCode);
		orderQuery.setTradeMode(tradeMode);
		orderQuery.setAgtOrderNo(agtOrderNo);
		orderQuery.setOrderNo(orderNo);
		orderQuery.setAgtRefundOrderNo(agtRefundOrderNo);
		orderQuery.setRefundOrderNo(refundOrderNo);

		UnionpayOrderQueryResponse orderQueryResponse = queryService.unionpayOrderQuery(orderQuery);
		if (StringUtils.equals(orderQueryResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", JSONObject.toJSONString(orderQueryResponse));
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, orderQueryResponse.getResultMsg());
		}

		return response;
	}

	private Response wechatRefund(String hospitalCode, String tradeMode, String refundOrderNo, String agtOrderNo, String orderNo,
			String totalFee, String refundFee, String refundDesc) throws Exception {
		Response response = new Response();

		WechatPayRefund refund = new WechatPayRefund();
		refund.setCode(hospitalCode);
		refund.setTradeMode(tradeMode);
		refund.setRefundOrderNo(refundOrderNo);
		refund.setAgtOrderNo(agtOrderNo);
		refund.setOrderNo(orderNo);
		refund.setTotalFee(totalFee);
		refund.setRefundFee(refundFee);
		refund.setRefundDesc(refundDesc);

		WechatPayRefundResponse refundResponse = refundService.wechatPayRefund(refund);
		if (StringUtils.equals(refundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", JSONObject.toJSONString(refundResponse));
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, refundResponse.getResultMsg());
		}

		return response;
	}

	private Response alipayRefund(String hospitalCode, String tradeMode, String refundOrderNo, String agtOrderNo, String orderNo,
			String totalFee, String refundFee, String refundDesc) throws Exception {
		Response response = new Response();

		AlipayRefund refund = new AlipayRefund();
		refund.setCode(hospitalCode);
		refund.setTradeMode(tradeMode);
		refund.setRefundOrderNo(refundOrderNo);
		refund.setAgtOrderNo(agtOrderNo);
		refund.setOrderNo(orderNo);
		refund.setTotalFee(totalFee);
		refund.setRefundFee(refundFee);
		refund.setRefundDesc(refundDesc);

		AlipayRefundResponse refundResponse = refundService.alipayRefund(refund);
		if (StringUtils.equals(refundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", JSONObject.toJSONString(refundResponse));
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, refundResponse.getResultMsg());
		}

		return response;
	}

	private Response unionpayRefund(String hospitalCode, String tradeMode, String refundOrderNo, String agtOrderNo, String orderNo,
			String totalFee, String refundFee, String refundDesc) throws Exception {
		Response response = new Response();

		UnionpayRefund refund = new UnionpayRefund();
		refund.setCode(hospitalCode);
		refund.setTradeMode(tradeMode);
		refund.setRefundOrderNo(refundOrderNo);
		refund.setAgtOrderNo(agtOrderNo);
		refund.setOrderNo(orderNo);
		refund.setTotalFee(totalFee);
		refund.setRefundFee(refundFee);
		refund.setRefundDesc(refundDesc);

		UnionpayRefundResponse refundResponse = refundService.unionpayRefund(refund);
		if (StringUtils.equals(refundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", JSONObject.toJSONString(refundResponse));
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, refundResponse.getResultMsg());
		}

		return response;
	}

	private Response wechatNative(String hospitalCode, String tradeMode, String orderNo, String totalFee, String body, String attach)
			throws Exception {

		Response response = new Response();

		WechatPay pay = new WechatPay();
		pay.setCode(hospitalCode);
		pay.setTradeMode(tradeMode);
		pay.setTotalFee(totalFee);
		pay.setOrderNo(orderNo);
		pay.setBody(body);
		pay.setCustomAttach(attach);

		JSONObject res = payService.wechatNativeService(pay);
		String codeUrl = res.getString("codeUrl");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(codeUrl)) {

			JSONObject result = new JSONObject();
			result.put("codeUrl", codeUrl);

			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", result.toJSONString());
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, res.getString("resultMsg"));
		}

		return response;
	}

	private Response alipayNative(String hospitalCode, String tradeMode, String orderNo, String totalFee, String body, String attach)
			throws Exception {

		Response response = new Response();

		Alipay pay = new Alipay();
		pay.setCode(hospitalCode);
		pay.setTradeMode(tradeMode);
		pay.setTotalFee(totalFee);
		pay.setOrderNo(orderNo);
		pay.setBody(body);// 商品描述
		pay.setCustomAttach(attach);
		logger.info("支付宝支付参数：{}.", JSONObject.toJSONString(pay));

		JSONObject res = payService.alipayNativeService(pay);

		String codeUrl = res.getString("codeUrl");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(codeUrl)) {

			JSONObject result = new JSONObject();
			result.put("codeUrl", codeUrl);

			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", result.toJSONString());
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, res.getString("resultMsg"));
		}

		return response;
	}

	private Response unionpayNative(String hospitalCode, String tradeMode, String orderNo, String totalFee, String body, String attach)
			throws Exception {

		Response response = new Response();

		Unionpay pay = new Unionpay();
		pay.setCode(hospitalCode);
		pay.setTradeMode(tradeMode);
		pay.setTotalFee(totalFee);
		pay.setOrderNo(orderNo);
		pay.setBody(body);// 商品描述
		pay.setCustomAttach(attach);
		logger.info("银联支付参数：{}.", JSONObject.toJSONString(pay));

		JSONObject res = payService.unionpayNativeService(pay);

		String codeUrl = res.getString("codeUrl");
		if (org.apache.commons.lang3.StringUtils.isNotBlank(codeUrl)) {

			JSONObject result = new JSONObject();
			result.put("codeUrl", codeUrl);

			response = new Response(OutsideConstant.OUTSIDE_SUCCESS, "OK", result.toJSONString());
		} else {
			response = new Response(OutsideConstant.OUTSIDE_ERROR, res.getString("resultMsg"));
		}

		return response;
	}

}
