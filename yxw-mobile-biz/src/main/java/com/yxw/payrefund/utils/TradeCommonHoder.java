package com.yxw.payrefund.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.clinic.ClinicRecord;
import com.yxw.commons.entity.mobile.biz.register.RegisterRecord;
import com.yxw.commons.entity.platform.app.color.AppColor;
import com.yxw.commons.entity.platform.payrefund.Alipay;
import com.yxw.commons.entity.platform.payrefund.AlipayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.AlipayRefund;
import com.yxw.commons.entity.platform.payrefund.Pay;
import com.yxw.commons.entity.platform.payrefund.Refund;
import com.yxw.commons.entity.platform.payrefund.Unionpay;
import com.yxw.commons.entity.platform.payrefund.UnionpayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.UnionpayRefund;
import com.yxw.commons.entity.platform.payrefund.WechatPay;
import com.yxw.commons.entity.platform.payrefund.WechatPayOrderQuery;
import com.yxw.commons.entity.platform.payrefund.WechatPayRefund;
import com.yxw.commons.entity.platform.register.Record;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.config.SystemConfig;
import com.yxw.payrefund.service.QueryService;
import com.yxw.payrefund.service.RefundService;

/**
 * @Package: com.yxw.platform.payrefund.common
 * @ClassName: TradeCommonHoder
 * @Statement: <p>
 *             交易公共处理类
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-28
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class TradeCommonHoder {
	private static Logger logger = LoggerFactory.getLogger(TradeCommonHoder.class);

	/**
	 * 构建微信支付支付信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static WechatPay buildWechatPayInfo(RegisterRecord record) {
		WechatPay wechatPay = new WechatPay();

		Pay pay = buildPayBaseInfo(record);
		BeanUtils.copyProperties(pay, wechatPay);
		wechatPay.setOpenId(record.getOpenId());//相对于父商户的openId

		if (record.getOnlinePaymentType().intValue() != BizConstant.PAYMENT_TYPE_MUST) {
			wechatPay.setAgtTimeout(null);
		}

		Long timeout = record.getPayTimeoutTime() - record.getRegisterTime();
		wechatPay.setTimeout(String.valueOf(timeout / 1000));

		if (record.getTradeMode().intValue() == TradeConstant.TRADE_MODE_APP_WECHAT_VAL) {
			wechatPay.setComponentOauth2(true);
		}

		return wechatPay;
	}

	/**
	 * 构建微信支付支付信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static WechatPay buildWechatPayInfo(ClinicRecord record) {
		WechatPay wechatPay = new WechatPay();

		Pay pay = buildPayBaseInfo(record);
		BeanUtils.copyProperties(pay, wechatPay);
		wechatPay.setOpenId(record.getOpenId());//相对于父商户的openId

		wechatPay.setAgtTimeout(null);

		if (record.getTradeMode().intValue() == TradeConstant.TRADE_MODE_APP_WECHAT_VAL) {
			wechatPay.setComponentOauth2(true);
		}

		return wechatPay;
	}

	/**
	 * 构建支付宝支付信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static Alipay buildAlipayInfo(RegisterRecord record) {
		Alipay alipay = new Alipay();

		Pay pay = buildPayBaseInfo(record);
		BeanUtils.copyProperties(pay, alipay);
		alipay.setMerchantUrl(alipay.getPaySuccessPageUrl());// TODO 暂时为空

		if (record.getOnlinePaymentType().intValue() != BizConstant.PAYMENT_TYPE_MUST) {
			alipay.setAgtTimeout(null);
		}

		Long timeout = record.getPayTimeoutTime() - record.getRegisterTime();
		alipay.setTimeout(String.valueOf(timeout / 1000));

		return alipay;
	}

	/**
	 * 构建支付宝支付信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static Alipay buildAlipayInfo(ClinicRecord record) {
		Alipay alipay = new Alipay();

		Pay pay = buildPayBaseInfo((Record) record);
		BeanUtils.copyProperties(pay, alipay);
		alipay.setMerchantUrl("");// TODO 暂时为空

		alipay.setAgtTimeout(null);

		return alipay;
	}

	/**
	 * 构建银联支付信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static Unionpay buildUnionpayInfo(RegisterRecord record) {
		Unionpay unionpay = new Unionpay();

		Pay pay = buildPayBaseInfo(record);
		BeanUtils.copyProperties(pay, unionpay);

		if (record.getOnlinePaymentType().intValue() != BizConstant.PAYMENT_TYPE_MUST) {
			unionpay.setAgtTimeout(null);
		}

		Long timeout = record.getPayTimeoutTime() - record.getRegisterTime();
		unionpay.setTimeout(String.valueOf(timeout / 1000));

		return unionpay;
	}

	/**
	 * 构建银联支付信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static Unionpay buildUnionpayInfo(ClinicRecord record) {
		Unionpay unionpay = new Unionpay();

		Pay pay = buildPayBaseInfo(record);
		BeanUtils.copyProperties(pay, unionpay);

		unionpay.setAgtTimeout(null);

		return unionpay;
	}

	/**
	 * 构建支付宝退费信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static AlipayRefund buildAlipayRefundInfo(RegisterRecord record) {
		AlipayRefund alipayRefund = new AlipayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, alipayRefund);

		return alipayRefund;
	}

	/**
	 * 构建支付宝退费信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static AlipayRefund buildAlipayRefundInfo(ClinicRecord record) {
		AlipayRefund alipayRefund = new AlipayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, alipayRefund);

		return alipayRefund;
	}

	/**
	 * 构建微信退费信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static WechatPayRefund buildWechatPayRefundInfo(RegisterRecord record) {
		WechatPayRefund wechatPayRefund = new WechatPayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, wechatPayRefund);

		return wechatPayRefund;
	}

	/**
	 * 构建微信退费信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static WechatPayRefund buildWechatPayRefundInfo(ClinicRecord record) {
		WechatPayRefund wechatPayRefund = new WechatPayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, wechatPayRefund);

		return wechatPayRefund;
	}

	/**
	 * 构建银联退费信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static UnionpayRefund buildUnionpayRefundInfo(RegisterRecord record) {
		UnionpayRefund unionpayRefund = new UnionpayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, unionpayRefund);

		return unionpayRefund;
	}

	/**
	 * 构建银联退费信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param record
	 * @return
	 */
	public static UnionpayRefund buildUnionpayRefundInfo(ClinicRecord record) {
		UnionpayRefund unionpayRefund = new UnionpayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, unionpayRefund);

		return unionpayRefund;
	}

	/**
	 *  构建基础支付信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static Pay buildPayBaseInfo(Object object) {
		Pay pay = new Pay();

		if (object instanceof RegisterRecord) {
			RegisterRecord record = (RegisterRecord) object;
			String payInfoViewType = getPayInfoViewType(record.getAppCode());

			StringBuffer sb = new StringBuffer();
			sb.append("挂号：");
			sb.append(record.getDeptName().concat("-").concat(record.getDoctorName()));

			pay.setCode(record.getHospitalCode());
			pay.setTradeMode(String.valueOf(record.getTradeMode()));
			pay.setOrderNo(record.getOrderNo());
			pay.setTotalFee(String.valueOf(record.getPayTotalFee()));
			pay.setBody(sb.toString());
			pay.setPaySuccessPageUrl(record.getAppUrl() + "/easyhealth/register/confirm/payMentSuccess");

			if ("iframe".equals(payInfoViewType)) {
				pay.setInfoUrl(record.getAppUrl() + "/easyhealth/register/confirm/regOrderInfoByIframe");
				pay.setViewType(payInfoViewType);
			} else {
				pay.setInfoUrl(record.getAppUrl() + "/easyhealth/register/confirm/regOrderInfoByJsonp");
				pay.setViewType(payInfoViewType);
			}
			//			pay.setInfoUrl("http://192.168.8.160:8088/tt.html");

		} else if (object instanceof ClinicRecord) {
			ClinicRecord record = (ClinicRecord) object;
			String payInfoViewType = getPayInfoViewType(record.getAppCode());

			StringBuffer sb = new StringBuffer();
			/*sb.append(BizConstant.URL_PARAM_ORDER_NO + BizConstant.URL_PARAM_CHAR_ASSGIN + record.getOrderNo());
			sb.append(BizConstant.URL_PARAM_CHAR_CONCAT + BizConstant.URL_PARAM_OPEN_ID + BizConstant.URL_PARAM_CHAR_ASSGIN
					+ record.getOpenId());*/
			sb.append("门诊：");
			sb.append(record.getDeptName().concat("-").concat(record.getDoctorName()));

			pay.setCode(record.getHospitalCode());
			pay.setTradeMode(String.valueOf(record.getTradeMode()));
			pay.setOrderNo(record.getOrderNo());
			pay.setTotalFee(String.valueOf(record.getPayTotalFee()));
			pay.setBody(sb.toString());
			pay.setPaySuccessPageUrl(record.getAppUrl() + "/app/clinic/detail/payMentSuccess");

			if ("iframe".equals(payInfoViewType)) {
				pay.setInfoUrl(record.getAppUrl() + "/app/clinic/detail/clinicOrderInfo");
				pay.setViewType(payInfoViewType);
			} else {
				pay.setInfoUrl(record.getAppUrl() + "/app/clinic/detail/clinicOrderInfoByJsonp");
				pay.setViewType(payInfoViewType);
			}
		}

		return pay;
	}

	/**
	 * 构建基础退费信息
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月13日 
	 * @param object
	 * @return
	 */
	public static Refund buildRefundBaseInfo(Object object) {
		Refund refund = new Refund();

		if (object instanceof RegisterRecord) {
			RegisterRecord record = (RegisterRecord) object;

			StringBuffer sb = new StringBuffer();
			sb.append(BizConstant.URL_PARAM_ORDER_NO + BizConstant.URL_PARAM_CHAR_ASSGIN + record.getOrderNo());
			sb.append(BizConstant.URL_PARAM_CHAR_CONCAT + BizConstant.URL_PARAM_OPEN_ID + BizConstant.URL_PARAM_CHAR_ASSGIN
					+ record.getOpenId());

			refund.setCode(record.getHospitalCode());
			refund.setTradeMode(String.valueOf(record.getTradeMode()));
			refund.setOrderNo(record.getOrderNo());
			refund.setAgtOrderNo(record.getAgtOrdNum());
			refund.setRefundOrderNo(String.valueOf(record.getRefundOrderNo()));
			refund.setTotalFee(String.valueOf(record.getPayTotalFee()));
			refund.setRefundFee(String.valueOf(record.getRefundTotalFee()));
			refund.setRefundDesc(sb.toString());
		} else if (object instanceof ClinicRecord) {
			ClinicRecord record = (ClinicRecord) object;

			StringBuffer sb = new StringBuffer();
			sb.append(BizConstant.URL_PARAM_ORDER_NO + BizConstant.URL_PARAM_CHAR_ASSGIN + record.getOrderNo());
			sb.append(BizConstant.URL_PARAM_CHAR_CONCAT + BizConstant.URL_PARAM_OPEN_ID + BizConstant.URL_PARAM_CHAR_ASSGIN
					+ record.getOpenId());

			refund.setCode(record.getHospitalCode());
			refund.setTradeMode(String.valueOf(record.getTradeMode()));
			refund.setOrderNo(record.getOrderNo());
			refund.setAgtOrderNo(record.getAgtOrdNum());
			refund.setRefundOrderNo(String.valueOf(record.getRefundOrderNo()));
			refund.setTotalFee(String.valueOf(record.getPayTotalFee()));
			refund.setRefundFee(String.valueOf(record.getRefundTotalFee()));
			refund.setRefundDesc(sb.toString());
		}

		return refund;
	}

	/** 
	 * 构建微信订单查询信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return 
	 */

	public static WechatPayOrderQuery buildWechatPayOrderQueryInfo(RegisterRecord record) {
		WechatPayOrderQuery wechatPayOrderQuery = new WechatPayOrderQuery();
		wechatPayOrderQuery.setCode(record.getHospitalCode());
		wechatPayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		wechatPayOrderQuery.setOrderNo(record.getOrderNo());
		wechatPayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());

		return wechatPayOrderQuery;
	}

	/** 
	 * 构建支付宝订单查询信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return 
	 */

	public static AlipayOrderQuery buildAlipayOrderQueryInfo(RegisterRecord record) {
		AlipayOrderQuery alipayOrderQuery = new AlipayOrderQuery();
		alipayOrderQuery.setCode(record.getHospitalCode());
		alipayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		alipayOrderQuery.setOrderNo(record.getOrderNo());
		alipayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());

		return alipayOrderQuery;
	}

	/** 
	 * 构建银联订单查询信息-挂号
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return 
	 */

	public static UnionpayOrderQuery buildUnionpayOrderQueryInfo(RegisterRecord record) {
		UnionpayOrderQuery unionpayOrderQuery = new UnionpayOrderQuery();
		unionpayOrderQuery.setCode(record.getHospitalCode());
		unionpayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		unionpayOrderQuery.setOrderNo(record.getOrderNo());
		unionpayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());
		//		unionpayOrderQuery.setAgtRefundOrderNo("201707281518324871068");
		//		unionpayOrderQuery.setRefundOrderNo("Y232320170728151850219924513");

		return unionpayOrderQuery;
	}

	/** 
	 * 构建微信订单查询信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return 
	 */

	public static WechatPayOrderQuery buildWechatPayOrderQueryInfo(ClinicRecord record) {
		WechatPayOrderQuery wechatPayOrderQuery = new WechatPayOrderQuery();
		wechatPayOrderQuery.setCode(record.getHospitalCode());
		wechatPayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		wechatPayOrderQuery.setOrderNo(record.getOrderNo());
		wechatPayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());

		return wechatPayOrderQuery;
	}

	/** 
	 * 构建支付宝订单查询信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return 
	 */

	public static AlipayOrderQuery buildAlipayOrderQueryInfo(ClinicRecord record) {
		AlipayOrderQuery alipayOrderQuery = new AlipayOrderQuery();
		alipayOrderQuery.setCode(record.getHospitalCode());
		alipayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		alipayOrderQuery.setOrderNo(record.getOrderNo());
		alipayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());

		return alipayOrderQuery;
	}

	/** 
	 * 构建银联订单查询信息-门诊
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @param record
	 * @return 
	 */

	public static UnionpayOrderQuery buildUnionpayOrderQueryInfo(ClinicRecord record) {
		UnionpayOrderQuery unionpayOrderQuery = new UnionpayOrderQuery();
		unionpayOrderQuery.setCode(record.getHospitalCode());
		unionpayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		unionpayOrderQuery.setOrderNo(record.getOrderNo());
		unionpayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());

		return unionpayOrderQuery;
	}

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月14日 
	 * @return
	 */
	public static QueryService getInvokeOrderQueryService() {
		QueryService orderQueryService = SpringContextHolder.getBean(QueryService.class);
		return orderQueryService;
	}

	/**
	 * 根据支付调用remote/local payService
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @return
	 */
	public static RefundService getInvokeRefundService() {
		RefundService refundService = SpringContextHolder.getBean(RefundService.class);
		return refundService;
	}

	/**
	 * 获取公共支付方式地址
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @return
	 */
	public static String getTradeUrl() {
		String tradeUrl = null;

		tradeUrl = SystemConfig.getStringValue(BizConstant.TRADE_REMOTE_URL_PROPERTIES_KEY);

		return tradeUrl;
	}

	public static String getPayInfoViewType(String appCode) {
		String payInfoViewType = "iframe";

		List<Object> params = new ArrayList<>();
		params.add(appCode);
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> results = serveComm.get(CacheType.APP_COLOR_CACHE, "findByAppCode", params);
		if (!CollectionUtils.isEmpty(results)) {
			String source = JSON.toJSONString(results.get(0));
			AppColor appColor = JSON.parseObject(source, AppColor.class);
			payInfoViewType = appColor.getPayInfoViewType();
		}

		return payInfoViewType;
	}

}
