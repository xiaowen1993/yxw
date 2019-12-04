/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-30</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.invoke.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.payrefund.PayAsynResponse;
import com.yxw.commons.generator.OrderNoGenerator;
import com.yxw.mobileapp.invoke.InsideInvokeService;
import com.yxw.mobileapp.invoke.payment.clinic.ClinicPaymentInvoker;
import com.yxw.mobileapp.invoke.payment.register.RegisterPaymentInvoker;

/**
 * @Package: com.yxw.mobileapp.invoke.impl
 * @ClassName: InsideInvokeServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-30
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class InsideInvokeServiceImpl implements InsideInvokeService {
	private static Logger logger = LoggerFactory.getLogger(InsideInvokeService.class);

	/**
	 * 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月19日 
	 * @param payAsynResponse
	 */
	@Override
	public void payMent(Object payAsynResponse) {

		PayAsynResponse payAsynRsp = (PayAsynResponse) payAsynResponse;

		if (StringUtils.equals(payAsynRsp.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
			logger.info("【挂号支付回调】payMent success info", JSONObject.toJSONString(payAsynRsp));

			if (Integer.valueOf(OrderNoGenerator.getBizType(payAsynRsp.getOrderNo())).intValue() == BizConstant.BIZ_TYPE_REGISTER) {//挂号
				RegisterPaymentInvoker.regPayment(payAsynResponse);
			} else if (Integer.valueOf(OrderNoGenerator.getBizType(payAsynRsp.getOrderNo())).intValue() == BizConstant.BIZ_TYPE_CLINIC) {//门诊
				ClinicPaymentInvoker.clinicPayment(payAsynResponse);
			} else if (Integer.valueOf(OrderNoGenerator.getBizType(payAsynRsp.getOrderNo())).intValue() == BizConstant.BIZ_TYPE_DEPOSIT) {//押金

			}

		} else if (StringUtils.equals(payAsynRsp.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
			logger.info("【挂号支付回调】payMent failure info", JSONObject.toJSONString(payAsynRsp));
		}
	}
}
