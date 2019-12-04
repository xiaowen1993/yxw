package com.yxw.mobileapp.datas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.constants.PayPlatformType;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.clinicpay.AckPayOrderRequest;
import com.yxw.interfaces.vo.clinicpay.MZFee;
import com.yxw.interfaces.vo.clinicpay.MZFeeDetail;
import com.yxw.interfaces.vo.clinicpay.MZFeeDetailRequest;
import com.yxw.interfaces.vo.clinicpay.MZFeeRequest;
import com.yxw.interfaces.vo.clinicpay.PayFee;
import com.yxw.interfaces.vo.clinicpay.PayFeeDetail;
import com.yxw.interfaces.vo.clinicpay.PayFeeDetailRequest;
import com.yxw.interfaces.vo.clinicpay.PayFeeRequest;

/**
 * @Package: com.yxw.platform.datas.manager
 * @ClassName: ClinicBizManager
 * @Statement:
 * 			<p>
 *             门诊代缴费、门诊已缴费接口
 *             </p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-28
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@SuppressWarnings("unchecked")
public class ClinicBizManager {

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private Logger logger = LoggerFactory.getLogger(ClinicBizManager.class);

	/**
	 * 查询待缴费记录
	 * 
	 * @param params
	 *            // hospitalCode, branchHospitalCode, patCardType, patCardNo,
	 *            patCardName
	 * @return
	 */
	public List<MZFee> getMZFeeList(Map<String, String> params) {
		List<MZFee> list = null;

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			MZFeeRequest mZFeeRequest = new MZFeeRequest();
			// 需要使用分院代码
			mZFeeRequest.setBranchCode(params.get("branchHospitalCode"));
			// 需要使用就诊卡类型
			mZFeeRequest.setPatCardType(params.get("patCardType"));
			// 需要使用就诊卡号
			mZFeeRequest.setPatCardNo(params.get("patCardNo"));
			// 平台类型
			String appCode = params.get("appCode");
			String channelType = PayPlatformType.ALL;
			JSONObject obj = new JSONObject();
			obj.put("branchCode", params.get("branchHospitalCode"));
			obj.put("patCardType", params.get("patCardType"));
			obj.put("patCardNo", params.get("patCardNo"));
			obj.put("appCode", params.get("appCode"));
			obj.put("channelType", channelType);
			System.out.println("--hh--"+obj.toString());
			//默认传0，不固定某一个平台
			/*if (BizConstant.MODE_TYPE_WEIXIN.equals(appCode)) {
				channelType = PayPlatformType.WECHAT;
			} else if (BizConstant.MODE_TYPE_ALIPAY.equals(appCode)) {
				channelType = PayPlatformType.ALIPAY;
			}*/
			mZFeeRequest.setChannelType(channelType);

			//Response response = yxwService.getMZFeeList(mZFeeRequest);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getMZFeeList");
			request.setInnerRequest(mZFeeRequest);
			Response response = yxwCommService.invoke(request);
			// logger.info("getMZFeeList return: {}", JSON.toJSONString(response));

			/*response =
					JSONObject
							.parseObject(
									"{\"result\":[{\"deptName\":\"生殖医学中心\",\"diagnoseFee\":\"3000\",\"doctorName\":\"欧湘红\",\"medicareAmout\":\"0\",\"mzFeeId\":\"201805030653\",\"payAmout\":\"65921\",\"payType\":\"自费\",\"totalAmout\":\"68921\"}],\"resultCode\":\"0\",\"resultMessage\":\"成功\"}",
									Response.class);*/

			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equals(response.getResultCode())) {
				list = (List<MZFee>) response.getResult();
			}
		} else {
			logger.error("could not find the interfaceId. hospitalCode: {}. branchCode: {}.", new Object[] { params.get("hospitalCode"),
					params.get("branchHospitalCode") });
		}

		if (CollectionUtils.isEmpty(list)) {
			list = new ArrayList<MZFee>();
		}

		return list;
	}

	public Map<String, Object> getMZFeeListEx(MedicalCard medicalCard) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<MZFee> list = null;

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(medicalCard.getHospitalCode());
		paramsList.add(medicalCard.getBranchCode());
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			MZFeeRequest mZFeeRequest = new MZFeeRequest();
			// 需要使用分院代码
			mZFeeRequest.setBranchCode(medicalCard.getBranchCode());
			// 需要使用就诊卡类型
			mZFeeRequest.setPatCardType(medicalCard.getCardType() + "");
			// 需要使用就诊卡号
			mZFeeRequest.setPatCardNo(medicalCard.getCardNo());
			// 支付类型 (健康易专用，查询所有)
			mZFeeRequest.setChannelType(PayPlatformType.ALL);

			//Response response = yxwService.getMZFeeList(mZFeeRequest);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getMZFeeList");
			request.setInnerRequest(mZFeeRequest);
			Response response = yxwCommService.invoke(request);

			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equals(response.getResultCode())) {
				list = (List<MZFee>) response.getResult();
			}

			if (CollectionUtils.isEmpty(list)) {
				list = new ArrayList<MZFee>();
			}

			resultMap.put("hospitalCode", medicalCard.getHospitalCode());
			resultMap.put("hospitalId", medicalCard.getHospitalId());
			resultMap.put("hospitalName", medicalCard.getHospitalName());
			resultMap.put("branchId", medicalCard.getBranchId());
			resultMap.put("branchCode", medicalCard.getBranchCode());
			resultMap.put("branchName", medicalCard.getBranchName());
			resultMap.put("patCardType", medicalCard.getCardType());
			resultMap.put("patCardNo", medicalCard.getCardNo());
			resultMap.put("patCardName", medicalCard.getName());
			resultMap.put("idNo", medicalCard.getIdNo());
			resultMap.put("patMobile", medicalCard.getMobile());
			resultMap.put("appId", medicalCard.getAppId());
			resultMap.put("appCode", medicalCard.getAppCode());
			resultMap.put(BizConstant.COMMON_ENTITY_LIST_KEY, list);
		} else {
			logger.error("could not find the interfaceId. hospitalCode: {}. branchCode: {}.", new Object[] { medicalCard.getHospitalCode(),
					medicalCard.getBranchCode() });
		}

		return resultMap;
	}

	/**
	 * 查询待缴费明细
	 * 
	 * @param params
	 * @return
	 */
	public List<MZFeeDetail> getMZFeeDetail(Map<String, String> params) {
		List<MZFeeDetail> list = null;

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			MZFeeDetailRequest mzFeeDetailRequest = new MZFeeDetailRequest();
			// 分院代码
			mzFeeDetailRequest.setBranchCode(params.get("branchHospitalCode"));
			// 门诊编号
			mzFeeDetailRequest.setMzFeeId(params.get("mzFeeId"));
			// 支付类型
			String appCode = params.get("appCode");
			String channelType = PayPlatformType.ALL;
			/*if (BizConstant.MODE_TYPE_WEIXIN.equals(appCode)) {
				channelType = PayPlatformType.WECHAT;
			} else if (BizConstant.MODE_TYPE_ALIPAY.equals(appCode)) {
				channelType = PayPlatformType.ALIPAY;
			}*/
			mzFeeDetailRequest.setChannelType(channelType);
			// 就诊卡类型
			mzFeeDetailRequest.setPatCardType(params.get("cardType"));
			// 就诊卡编号
			mzFeeDetailRequest.setPatCardNo(params.get("cardNo"));

			//Response response = yxwService.getMZFeeDetail(mzFeeDetailRequest);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getMZFeeDetail");
			request.setInnerRequest(mzFeeDetailRequest);
			Response response = yxwCommService.invoke(request);

			/*response =
					JSONObject
							.parseObject(
									"{\"result\":[{\"itemTime\":\"2018-05-03 18:03:13\",\"itemName\":\"超敏C反应蛋白测定\",\"itemType\":\"化验费\",\"itemUnit\":\"项\",\"itemId\":\"2826\",\"itemPrice\":\"4000\",\"itemSpec\":\" \",\"itemNumber\":\"1\",\"itemTotalFee\":\"4000\",\"deptName\":\"急诊科\",\"doctorCode\":\"0990\",\"doctorName\":\"刘丽辉\"},{\"itemTime\":\"2018-05-03 18:03:13\",\"itemName\":\"血常规（五分类）\",\"itemType\":\"化验费\",\"itemUnit\":\"次\",\"itemId\":\"2460\",\"itemPrice\":\"2000\",\"itemSpec\":\" \",\"itemNumber\":\"1\",\"itemTotalFee\":\"2000\",\"deptName\":\"急诊科\",\"doctorCode\":\"0990\",\"doctorName\":\"刘丽辉\"},{\"itemTime\":\"2018-05-03 18:05:26\",\"itemName\":\"△静脉采血\",\"itemType\":\"治疗费\",\"itemUnit\":\"次\",\"itemId\":\"1540\",\"itemPrice\":\"767\",\"itemSpec\":\" \",\"itemNumber\":\"1\",\"itemTotalFee\":\"767\",\"deptName\":\"急诊科\",\"doctorCode\":\"0990\",\"doctorName\":\"刘丽辉\"}],\"resultCode\":\"0\",\"resultMessage\":\"成功\"}",
									Response.class);*/

			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equals(response.getResultCode())) {
				list = (List<MZFeeDetail>) response.getResult();
			}
		} else {
			logger.error("");
		}

		if (CollectionUtils.isEmpty(list)) {
			list = new ArrayList<MZFeeDetail>();
		}

		return list;
	}

	/**
	 * 查询已缴费记录 ，如果时间不传入，则接口默认查询近3天
	 * 
	 * @param params
	 * @return
	 */
	public List<PayFee> getPayList(Map<String, String> params) {
		List<PayFee> list = null;

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			if (StringUtils.isNotBlank(interfaceId)) {
				try {
					//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
					PayFeeRequest payFeeRequest = new PayFeeRequest();
					// 分院代码
					payFeeRequest.setBranchCode(params.get("branchHospitalCode"));
					// 诊疗卡类型
					payFeeRequest.setPatCardType(params.get("patCardType"));
					// 诊疗卡号
					payFeeRequest.setPatCardNo(params.get("patCardNo"));
					// 支付方式
					payFeeRequest.setPayMode(params.get("payMode"));
					// 开始时间
					payFeeRequest.setBeginDate(params.get("beginDate"));
					// 结束时间
					payFeeRequest.setEndDate(params.get("endDate"));
					// 服务平台订单号 (查询列表的时候，可以不使用)
					payFeeRequest.setPsOrdNum(params.get("psOrdNum"));

					//Response response = yxwService.getPayFeeList(payFeeRequest);
					YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
					Request request = new Request();
					request.setServiceId(interfaceId);
					request.setMethodName("getPayFeeList");
					request.setInnerRequest(payFeeRequest);
					Response response = yxwCommService.invoke(request);

					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						list = (List<PayFee>) response.getResult();
					}
				} catch (Exception e) {
					logger.error("getPayList error. errorMessage: {}, cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
				}
			} else {
				logger.error("getPayList error. reason: interfaceId is null. hospitalCode: {}. branchCode: {}.",
						new Object[] { params.get("hospitalCode"), params.get("branchHospitalCode") });
			}
		}

		if (CollectionUtils.isEmpty(list)) {
			list = new ArrayList<PayFee>();
		}

		return list;
	}

	/**
	 * 查询已缴费记录明细
	 * 
	 * @param params
	 * @return
	 */
	public List<PayFeeDetail> getPayDetail(Map<String, String> params) {
		List<PayFeeDetail> list = null;

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				PayFeeDetailRequest payFeeDetailRequest = new PayFeeDetailRequest();
				// 分院代码
				payFeeDetailRequest.setBranchCode(params.get("branchHospitalCode"));
				// 医院的订单号
				if (StringUtils.isNotBlank(params.get("hisOrdNum"))) {
					payFeeDetailRequest.setHisOrdNum(params.get("hisOrdNum"));
				}
				// 收据号
				if (StringUtils.isNotBlank(params.get("receiptNum"))) {
					payFeeDetailRequest.setReceiptNum(params.get("receiptNum"));
				}
				// 门诊号
				if (StringUtils.isNotBlank(params.get("mzFeeId"))) {
					payFeeDetailRequest.setMzFeeId(params.get("mzFeeId"));
				}
				// logger.info("reques json string:{}.", JSON.toJSONString(request));
				//Response response = yxwService.getPayFeeDetail(payFeeDetailRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getPayFeeDetail");
				request.setInnerRequest(payFeeDetailRequest);
				Response response = yxwCommService.invoke(request);

				if (logger.isDebugEnabled()) {
					logger.debug("getPayFeeDetail return: {}", JSON.toJSONString(response));
				}

				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
					list = (List<PayFeeDetail>) response.getResult();
				}

			} catch (Exception e) {
				logger.error("getPayDetail error. errorMessage: {}, cause by: {}.", new Object[] { e.getMessage(), e.getCause() });
			}
		} else {
			logger.error("getPayList error. reason: interfaceId is null. hospitalCode: {}. branchCode: {}.",
					new Object[] { params.get("hospitalCode"), params.get("branchHospitalCode") });
		}

		if (list == null) {
			list = new ArrayList<PayFeeDetail>();
		}

		return list;
	}

	/**
	 * 订单支付
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> payOrder(Map<String, String> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			try {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				AckPayOrderRequest ackPayOrderRequest = new AckPayOrderRequest();
				// 分院代码
				ackPayOrderRequest.setBranchCode(params.get("branchHospitalCode"));
				// 卡类型
				ackPayOrderRequest.setPatCardType(params.get("patCardType"));
				// 卡号
				ackPayOrderRequest.setPatCardNo(params.get("patCardNo"));
				// 缴费项列表
				ackPayOrderRequest.setMzFeeIdList(params.get("mzFeeIdList"));
				// 付款金额
				ackPayOrderRequest.setPayAmout(params.get("payAmout"));
				// 总金额
				ackPayOrderRequest.setTotalAmout(params.get("totalAmout"));
				// 服务平台订单号
				ackPayOrderRequest.setPsOrdNum(params.get("psOrdNum"));
				// 收单机构流水号
				ackPayOrderRequest.setAgtOrdNum(params.get("agtOrdNum"));
				// 首单机构代码
				ackPayOrderRequest.setAgtCode(params.get("agtCode"));
				// 付款方式
				//				ackPayOrderRequest.setPayMode(params.get("payMode"));
				ackPayOrderRequest.setPayMode(BizConstant.HIS_ORDER_MODE_VAL);//默认值传3
				// 付款时间
				ackPayOrderRequest.setPayTime(params.get("payTime"));
				// logger.info("call pay clinic order params=" + params.toString());
				//Response response = yxwService.ackPayOrder(ackPayOrderRequest);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("ackPayOrder");
				request.setInnerRequest(ackPayOrderRequest);
				Response response = yxwCommService.invoke(request);

				if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
					resultMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
					resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
					resultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				} else {
					if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
						resultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
						resultMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
					} else {
						resultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
						resultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					}
					resultMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
					resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
				}
			} catch (Exception e) {
				logger.error("payClinicOrder error. orderNo:{}. reason: {}", new Object[] { params.get("psOrdNum"),
						( e.getMessage() + "   Cause:" + e.getCause() ) });
				resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		} else {
			logger.error("getPayList error. reason: interfaceId is null. hospitalCode: {}. branchCode: {}.",
					new Object[] { params.get("hospitalCode"), params.get("branchHospitalCode") });
		}

		return resultMap;
	}

	/**
	 * 查询订单的状态， 也是调用已缴费订单列表的接口。查询的时间为空，订单号不为空
	 * 
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPayOrderStatus(Map<String, String> params) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			if (StringUtils.isNotBlank(interfaceId)) {
				try {
					//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
					PayFeeRequest payFeeRequest = new PayFeeRequest();
					// 分院代码
					payFeeRequest.setBranchCode(params.get("branchHospitalCode"));
					// 诊疗卡类型
					payFeeRequest.setPatCardType(params.get("patCardType"));
					// 诊疗卡号
					payFeeRequest.setPatCardNo(params.get("patCardNo"));
					// 支付方式
					payFeeRequest.setPayMode(params.get("payMode"));
					// 开始时间
					payFeeRequest.setBeginDate("");
					// 结束时间
					payFeeRequest.setEndDate("");
					// 服务平台订单号 (查询列表的时候，可以不使用)
					payFeeRequest.setPsOrdNum(params.get("psOrdNum"));

					//Response response = yxwService.getPayFeeList(payFeeRequest);
					YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
					Request request = new Request();
					request.setServiceId(interfaceId);
					request.setMethodName("getPayFeeList");
					request.setInnerRequest(payFeeRequest);
					Response response = yxwCommService.invoke(request);

					if (BizConstant.INTERFACE_RES_EXCEPTION_CODE.equalsIgnoreCase(response.getResultCode())) {
						resultMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
						resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
						resultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
					} else {
						if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
							resultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, true);
							resultMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, response.getResult());
						} else {
							resultMap.put(BizConstant.INTERFACE_EXEC_IS_SUCCESS, false);
							resultMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
						}
						resultMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
						resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, false);
					}
				} catch (Exception e) {
					logger.error("payClinicOrder error. orderNo:{}. reason: {}", new Object[] { params.get("psOrdNum"),
							( e.getMessage() + "   Cause:" + e.getCause() ) });
					resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
				}
			} else {
				logger.error("payClinicOrderStatus error. orderNo:{}.reason: interfaceId is null", params.get("psOrdNum"));
				resultMap.put(BizConstant.INTERFACE_EXEC_IS_EXCEPTION, true);
			}
		}

		return resultMap;
	}

}
