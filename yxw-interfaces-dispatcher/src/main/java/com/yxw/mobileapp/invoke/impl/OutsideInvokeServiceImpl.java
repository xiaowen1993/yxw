package com.yxw.mobileapp.invoke.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.invoke.OutsideConstants;
import com.yxw.mobileapp.invoke.OutsideInvokeService;
import com.yxw.mobileapp.invoke.dto.Request;
import com.yxw.mobileapp.invoke.dto.Response;
import com.yxw.utils.OrderUitl;

public class OutsideInvokeServiceImpl implements OutsideInvokeService {

	public static Logger logger = LoggerFactory.getLogger(OutsideInvokeServiceImpl.class);

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

			// 查询类的 订单查询(ordersQuery)/挂号订单查询(regOrdersQuery)
			if (methodCode.endsWith("Query")) {
				//String appId = paramsRootEle.valueOf("appId");
				//String branchCode = paramsRootEle.valueOf("branchCode");
				//支付类型 tradeMode 0 : 所有 1 : 微信 2 : 支付宝 3:健康易
				//0 需要调用所有渠道的订单查询接口, 并汇总, 比较耗时, 暂屏蔽
				//String tradeMode = paramsRootEle.valueOf("tradeMode");

				if (OutsideConstants.METHOD_CODE_ORDERS_QUERY.equals(methodCode)) {
					//String orderMode = paramsRootEle.valueOf("orderMode");
					//String startTime = paramsRootEle.valueOf("startTime");
					//String endTime = paramsRootEle.valueOf("endTime");

				} else if (OutsideConstants.METHOD_CODE_REG_ORDERS_QUERY.equals(methodCode)) {
					//String regType = paramsRootEle.valueOf("regType");
					//String startDate = paramsRootEle.valueOf("startDate");
					//String endDate = paramsRootEle.valueOf("endDate");

				}

				//查询类的直接调用标准平台
				String serviceId = OutsideConstants.SERVICEID_PREFIX_STANDARD + OutsideConstants.SERVICEID_SUFFIX;
				OutsideInvokeService outsideInvokeService = SpringContextHolder.getBean(serviceId);
				response = outsideInvokeService.openService(request);
			} else {
				// 非查询类的

				String serviceId = OutsideConstants.SERVICEID_PREFIX_STANDARD + OutsideConstants.SERVICEID_SUFFIX;
				// 通过修改 serviceId 直连 标准平台/统一平台 的 dubbo 服务

				// 原渠道退费(refundGeneral)
				if (OutsideConstants.METHOD_CODE_REFUND_GENERAL.equalsIgnoreCase(methodCode)) {
					String psOrdNum = paramsRootEle.valueOf("psOrdNum");
					String type = "";
					String refundType = paramsRootEle.valueOf("refundType");
					//1 挂号退费
					//2 门诊退费
					//3住院押金退费 
					if ("1".equals(refundType)) {
						type = "register";
					} else if ("2".equals(refundType)) {
						type = "clinic";
					} else if ("3".equals(refundType)) {
						type = "deposit";
					}
					//serviceId = OrderUitl.getPlatformByOrderNo(psOrdNum) + OutsideConstants.SERVICEID_SUFFIX;
					String platformMode = OrderUitl.getPlatformByOrderNo(type, psOrdNum);
					if (StringUtils.isNotBlank(platformMode)) {
						serviceId = OrderUitl.getPlatformByOrderNo(type, psOrdNum) + OutsideConstants.SERVICEID_SUFFIX;
					} else {
						throw new RuntimeException("orderNo is not exit.");
					}
				}
				// 模板消息推送(templateMsgPush)
				else if (OutsideConstants.METHOD_CODE_TEMPLATE_MSG_PUSH.equalsIgnoreCase(methodCode)) {
					String userType = paramsRootEle.valueOf("userType");
					if ("3".equals(userType) || "4".equals(userType) || "5".equals(userType)) {
						String toUser = paramsRootEle.valueOf("toUser");
						serviceId = OrderUitl.getPlatformByOrderNo(toUser) + OutsideConstants.SERVICEID_SUFFIX;
					} else {
						OutsideInvokeService outsideInvokeService =
								SpringContextHolder.getBean(OutsideConstants.SERVICEID_PREFIX_PT + OutsideConstants.SERVICEID_SUFFIX);
						if (outsideInvokeService != null) {
							response = outsideInvokeService.openService(request);
						}
					}

				}
				// 客服消息推送(templateMsgPush)
				else if (OutsideConstants.METHOD_CODE_CUSTOMER_MSG_PUSH.equalsIgnoreCase(methodCode)) {
					String userType = paramsRootEle.valueOf("userType");
					if ("3".equals(userType) || "4".equals(userType) || "5".equals(userType)) {
						String toUser = paramsRootEle.valueOf("toUser");
						serviceId = OrderUitl.getPlatformByOrderNo(toUser) + OutsideConstants.SERVICEID_SUFFIX;
					} else {
						OutsideInvokeService outsideInvokeService =
								SpringContextHolder.getBean(OutsideConstants.SERVICEID_PREFIX_PT + OutsideConstants.SERVICEID_SUFFIX);
						if (outsideInvokeService != null) {
							response = outsideInvokeService.openService(request);
						}
					}

				}
				// 医生停诊(stopReg)
				else if (OutsideConstants.METHOD_CODE_STOP_REG.equalsIgnoreCase(methodCode)) {
					String orderList = paramsRootEle.valueOf("orderList");
					//Map<String, List<String>> map = OrderUitl.orderNosSplitByPlatform(orderList);
					Map<String, List<String>> map = OrderUitl.orderNosSplitByPlatform("register", orderList);

					if (map.containsKey(OutsideConstants.SERVICEID_PREFIX_PT)) {
						OutsideInvokeService outsideInvokeService =
								SpringContextHolder.getBean(OutsideConstants.SERVICEID_PREFIX_PT + OutsideConstants.SERVICEID_SUFFIX);
						Element orderListEle = paramsRootEle.element("orderList");

						if (outsideInvokeService != null) {
							orderListEle.setText(StringUtils.join(map.get(OutsideConstants.SERVICEID_PREFIX_PT), ","));

							params = paramsRootEle.asXML().replace("<xml>", "").replace("</xml>", "");
							request.setParams(params);

							response = outsideInvokeService.openService(request);
						}

						// 修改调用标准平台的参数
						if (map.containsKey(OutsideConstants.SERVICEID_PREFIX_STANDARD)) {
							orderListEle.setText(StringUtils.join(map.get(OutsideConstants.SERVICEID_PREFIX_STANDARD), ","));
						} else {
							orderListEle.setText("");
						}
						params = paramsRootEle.asXML().replace("<xml>", "").replace("</xml>", "");
						request.setParams(params);
					}
				}
				// 门诊缴费支付二维码(qrPay)
				else if (OutsideConstants.METHOD_CODE_QR_PAY.equalsIgnoreCase(methodCode)) {
					// 新平台暂时没有此接口
				}

				OutsideInvokeService outsideInvokeService = SpringContextHolder.getBean(serviceId);
				if (outsideInvokeService != null) {
					// 所有平台都调用的业务, 只要有一个成功, 就返回成功
					if (response != null && "1".equals(response.getResultCode())) {
						outsideInvokeService.openService(request);
					} else {
						response = outsideInvokeService.openService(request);
					}
				} else {
					//请求接口异常
					response = new Response("-1", "exception");
					logger.error("outsideInvokeService is null, openService:methodCode=" + request.getMethodCode() + ",params="
							+ request.getParams());
				}
				if (response == null) {
					//请求接口异常
					response = new Response("-1", "exception");
					logger.error("response is null, openService:methodCode=" + request.getMethodCode() + ",params=" + request.getParams());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("openService exception:methodCode=" + request.getMethodCode() + ",params=" + request.getParams(), e);
			//请求接口异常
			response = new Response("-1", "exception");
		}

		logger.info("Response info:" + JSONObject.toJSONString(response));
		return response;
	}

}
