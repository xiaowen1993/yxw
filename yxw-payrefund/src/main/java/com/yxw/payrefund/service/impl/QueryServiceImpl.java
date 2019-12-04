package com.yxw.payrefund.service.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alipay.AlipayUtil;
import com.common.CommonConstant;
import com.common.UnionPayConstant;
import com.unionpay.acp.sdk.UnionPayUtil;
import com.wechat.WechatUtil;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQueryResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQueryResponse;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.utils.CacheUtil;

public class QueryServiceImpl implements QueryService {

	private Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

	@Override
	public WechatPayOrderQueryResponse wechatPayOrderQuery(WechatPayOrderQuery orderQuery) {
		WechatPayOrderQueryResponse response = new WechatPayOrderQueryResponse();

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(orderQuery.getCode(), orderQuery.getTradeMode());
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		try {
			Map<String, String> orderQueryParams = WechatUtil.genOrderQueryParams(vo, orderQuery);

			String orderQueryResponse = WechatUtil.orderQuery(orderQueryParams);
			if (StringUtils.isNotBlank(orderQueryResponse)) {
				Map<String, String> orderQueryResponseMap = WechatUtil.xml2Map(orderQueryResponse);

				response = WechatUtil.map2WechatPayOrderQueryResponse(orderQueryResponseMap);
			} else {
				response = new WechatPayOrderQueryResponse(BizConstant.METHOD_INVOKE_FAILURE, "查询失败，WechatOrderQueryResponse is null");
			}

		} catch (Exception e) {
			response = new WechatPayOrderQueryResponse(BizConstant.METHOD_INVOKE_EXCEPTION, "查询异常：".concat(e.getMessage()));
		}

		return response;

	}

	@Override
	public AlipayOrderQueryResponse alipayOrderQuery(AlipayOrderQuery orderQuery) {
		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(orderQuery.getCode(), orderQuery.getTradeMode());
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		AlipayOrderQueryResponse response =
				AlipayUtil.tradeQuery(vo.getAppId(), vo.getAliPrivateKey(), orderQuery.getAgtOrderNo(), orderQuery.getOrderNo());

		return response;
	}

	@Override
	public UnionpayOrderQueryResponse unionpayOrderQuery(UnionpayOrderQuery orderQuery) {
		UnionpayOrderQueryResponse response = null;

		HospitalCodeAndAppVo vo = CacheUtil.getPayInfoByCache(orderQuery.getCode(), orderQuery.getTradeMode());
		logger.info("HospitalCodeAndAppVo：{} ", JSONObject.toJSONString(vo));

		try {
			if (UnionPayConstant.UNIONPAY_TEST) {
				response = UnionPayUtil.orderQuery(orderQuery, vo.getMchId(), null, null);
			} else {
				// /opt/new_platform/certs/acp303440180110054.pfx
				String certPath =
						CommonConstant.CERTS_PATH.concat(String.valueOf(java.io.File.separatorChar)).concat("acp").concat(vo.getMchId())
								.concat(".pfx");
				response = UnionPayUtil.orderQuery(orderQuery, vo.getMchId(), certPath, vo.getCertificatePwd());
			}
		} catch (Exception e) {
			response = new UnionpayOrderQueryResponse(BizConstant.METHOD_INVOKE_EXCEPTION, "查询异常：".concat(e.getMessage()));
		}

		return response;
	}

}
