package com.yxw.payrefund.thread;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.constants.biz.TradeConstant;
import com.yxw.commons.entity.platform.payrefund.AlipayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.PayAsynResponse;
import com.yxw.commons.entity.platform.payrefund.WechatPayAsynResponse;
import com.yxw.framework.common.http.HttpClientUtil;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.invoke.InsideInvokeService;
import com.yxw.mobileapp.invoke.PayNoticeService;
import com.yxw.mobileapp.invoke.dto.PayNotice;

/**
 * 分线程执行支付回调后的业务
 */
public class PayAsynResponseRunnable implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(PayAsynResponseRunnable.class);

	private PayAsynResponse payAsynResponse;

	public PayAsynResponse getPayAsynResponse() {
		return payAsynResponse;
	}

	public void setPayAsynResponse(PayAsynResponse payAsynResponse) {
		this.payAsynResponse = payAsynResponse;
	}

	public PayAsynResponseRunnable() {
		super();
	}

	public PayAsynResponseRunnable(PayAsynResponse payAsynResponse) {
		super();
		this.payAsynResponse = payAsynResponse;
	}

	@Override
	public void run() {
		Long startMillis = System.currentTimeMillis();
		logger.info("payAsynResponse: {}", JSONObject.toJSONString(payAsynResponse));

		try {
			JSONObject attach = JSONObject.parseObject(payAsynResponse.getAttach());
			String code = attach.getString("code");
			String tradeMode = attach.getString("tradeMode");
			Boolean isRestful = attach.getBoolean("isRestful");
			String customAttach = attach.getString("custom");
			logger.info("code: {}, tradeMode: {}", code, tradeMode);

			if (String.valueOf(TradeConstant.TRADE_MODE_WECHATNATIVE_VAL).equals(tradeMode) // 自助机（微信扫码付）
					|| String.valueOf(TradeConstant.TRADE_MODE_ALIPAYNATIVE_VAL).equals(tradeMode) // 自助机（支付宝扫码付）
					|| String.valueOf(TradeConstant.TRADE_MODE_UNIONPAYNATIVE_VAL).equals(tradeMode) // 自助机（银联钱包扫码付）
			) {
				PayNotice payNotice = new PayNotice();
				payNotice.setAgtOrdNum(payAsynResponse.getAgtOrderNo());
				payNotice.setOrderNo(payAsynResponse.getOrderNo());
				payNotice.setTerminalId(customAttach);
				if (payAsynResponse instanceof WechatPayAsynResponse) {
					payNotice.setOpenId( ( (WechatPayAsynResponse) payAsynResponse ).getOpenId());
				} else if (payAsynResponse instanceof AlipayAsynResponse) {
					payNotice.setOpenId( ( (AlipayAsynResponse) payAsynResponse ).getOpenId());
				}

				payNotice.setPayTime(payAsynResponse.getTradeTime());

				PayNoticeService payNoticeService = SpringContextHolder.getBean(PayNoticeService.class);
				if (payNoticeService != null) {
					payNoticeService.push(payNotice);
					logger.info("PayNoticeService.push.end...");
				} else {
					logger.info("PayNoticeService is null...");
				}
			} else if (String.valueOf(TradeConstant.TRADE_MODE_WECHAT_VAL).equals(tradeMode)
					|| String.valueOf(TradeConstant.TRADE_MODE_APP_UNIONPAY_VAL).equals(tradeMode)
					|| String.valueOf(TradeConstant.TRADE_MODE_APP_ALIPAY_VAL).equals(tradeMode)
					|| String.valueOf(TradeConstant.TRADE_MODE_APP_WECHAT_VAL).equals(tradeMode)
					|| String.valueOf(TradeConstant.TRADE_MODE_ALIPAY_MOBILE_WEB_VAL).equals(tradeMode)) {

				if (isRestful) {
					String url = attach.getString("notifyUrl");

					HttpClientUtil.getInstance().post(url, BeanUtils.describe(payAsynResponse));
				} else {
					if (StringUtils.equals(payAsynResponse.getMchId(), "1227059102")) {//临时
						InsideInvokeService insideInvokeService = SpringContextHolder.getBean("insideInvokeServiceTest");
						if (insideInvokeService != null) {
							insideInvokeService.payMent(payAsynResponse);
							logger.info("InsideInvokeService.payMent.end...");
						} else {
							logger.info("InsideInvokeService is null...");
						}
					} else {
						InsideInvokeService insideInvokeService = SpringContextHolder.getBean(InsideInvokeService.class);
						if (insideInvokeService != null) {
							insideInvokeService.payMent(payAsynResponse);
							logger.info("InsideInvokeService.payMent.end...");
						} else {
							logger.info("InsideInvokeService is null...");
						}
					}
				}

			} else {
				logger.warn("未知 tradeMode: {}", tradeMode);
			}
		} catch (Exception e) {
			logger.error("dubbo调用支付后业务逻辑异常：{}", e.getMessage());
		}

		logger.info("dubbo调用支付后业务逻辑: {} 毫秒", ( System.currentTimeMillis() - startMillis ));
	}

}
