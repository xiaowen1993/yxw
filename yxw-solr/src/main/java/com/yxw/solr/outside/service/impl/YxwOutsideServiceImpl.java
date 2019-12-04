package com.yxw.solr.outside.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.common.service.HospitalInfoService;
import com.yxw.solr.biz.order.service.OrderService;
import com.yxw.solr.biz.register.service.RegisterService;
import com.yxw.solr.constants.OutsideResultConstant;
import com.yxw.solr.outside.service.YxwOutsideService;
import com.yxw.solr.vo.HospitalInfosVo;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.YxwResult;
import com.yxw.solr.vo.order.OrderInfoRequest;
import com.yxw.solr.vo.outside.OrderParams;
import com.yxw.solr.vo.outside.RegOrderParams;
import com.yxw.solr.vo.register.RegInfoRequest;
import com.yxw.utils.DateUtils;

public class YxwOutsideServiceImpl implements YxwOutsideService {

	private Logger logger = LoggerFactory.getLogger(YxwOutsideService.class);

	private HospitalInfoService hospitalInfoService = SpringContextHolder.getBean(HospitalInfoService.class);

	private OrderService orderService = SpringContextHolder.getBean(OrderService.class);

	private RegisterService registerService = SpringContextHolder.getBean(RegisterService.class);

	@Override
	public YxwResponse queryOrders(OrderParams orderParams) {
		YxwResponse response = new YxwResponse();

		Map<String, Object> checkResultMap = checkOrderParams(orderParams);
		int resultCode = (int) checkResultMap.get("resultCode");
		if (resultCode == OutsideResultConstant.RESULT_CODE_SUCCESS) {
			@SuppressWarnings("unchecked")
			List<OrderInfoRequest> infoRequests = (List<OrderInfoRequest>) checkResultMap.get("entity");
			if (CollectionUtils.isNotEmpty(infoRequests)) {
				// 调用service提供的查询方法啦
				if (logger.isInfoEnabled()) {
					logger.info("查询参数: {}", JSON.toJSONString(infoRequests));
				}
				YxwResult result = orderService.orderQuery(infoRequests);
				// response.setResult(JSON.toJSONString(result));
				response.setResult(result);

			} else {
				response.setResultMessage("No data was found!");
			}

			response.setResultCode(resultCode);
		} else {
			response.setResultCode(resultCode);
			response.setResultMessage((String) checkResultMap.get("resultMessage"));
		}

		return response;
	}

	@Override
	public YxwResponse queryRegOrders(RegOrderParams regOrderParams) {
		YxwResponse response = new YxwResponse();

		Map<String, Object> checkResultMap = checkRegOrderParams(regOrderParams);
		int resultCode = (int) checkResultMap.get("resultCode");
		if (resultCode == OutsideResultConstant.RESULT_CODE_SUCCESS) {
			@SuppressWarnings("unchecked")
			List<RegInfoRequest> regInfoRequests = (List<RegInfoRequest>) checkResultMap.get("entity");
			if (CollectionUtils.isNotEmpty(regInfoRequests)) {
				// 调用service提供的查询方法啦
				if (logger.isInfoEnabled()) {
					logger.info("查询参数: {}", JSON.toJSONString(regInfoRequests));
				}
				YxwResult result = registerService.orderQuery(regInfoRequests);
				// response.setResult(JSON.toJSONString(result));
				response.setResult(result);

			} else {
				response.setResultMessage("No data was found!");
			}

			response.setResultCode(resultCode);
		} else {
			response.setResultCode(resultCode);
			response.setResultMessage((String) checkResultMap.get("resultMessage"));
		}

		return response;
	}

	/**
	 * 这份代码有局限性。
	 * @param requestMap
	 * @return
	 */
	private Map<String, Object> checkOrderParams(OrderParams orderParams) {
		Map<String, Object> resultMap = null;

		/** ------------------------------- 入参非空校验 -----------------------------------**/
		// appId -- 此处的appId只是作为一个医院。通过appId找医院
		if (StringUtils.isBlank(orderParams.getAppId())) {
			return failParamsNone("appId");
		}

		// orderMode -- 后面还需要验证在 0,1,2,3 内
		if (orderParams.getOrderMode() == null) {
			return failParamsNone("orderMode");
		}

		int orderMode = orderParams.getOrderMode();
		if (orderMode == 0) {
			orderMode = -1; // 转为solr查询所需要值。-1即为查询所有类型的订单
		} else if (orderMode < 0 || orderMode > 3) {
			return failParamsFormatError("orderMode");
		}

		// tradeMode -- 后面还需要验证在0,1,2,3内. 不验证了。
		if (orderParams.getTradeMode() == null) {
			return failParamsNone("tradeMode");
		}

		// branchCode，如果没有则默认查询所有。不需要单独判断

		// startTime
		if (StringUtils.isBlank(orderParams.getStartTime())) {
			return failParamsNone("startTime");
		}

		// endTime
		if (StringUtils.isBlank(orderParams.getEndTime())) {
			return failParamsNone("endTime");
		}

		// isQiaoQiaoShang -- 这个根本不会为空的，一起约定好的。
		if (orderParams.getIsQiaoQiaoShang() == null) {
			return failParamsNone("Error! Error! Error! Bom, Bom, Bom~~");
		}

		/** ------------------------------- 入参格式校验 -----------------------------------**/
		Date beginDate = DateUtils.StringToDate(orderParams.getStartTime());
		long beginTime = 0L;
		if (beginDate != null) {
			beginTime = beginDate.getTime();
		} else {
			return failParamsFormatError("startTime");
		}

		Date endDate = DateUtils.StringToDate(orderParams.getEndTime());
		long endTime = 0L;
		if (endDate != null) {
			endTime = endDate.getTime();
		} else {
			return failParamsFormatError("endTime");
		}

		if (beginTime > endTime) {
			return failParamsFormatError("startTime, endTime");
		}

		/** ------------------------------- 入参数据校验 -----------------------------------**/

		List<HospitalInfosVo> hospitalInfosVos = hospitalInfoService.getInfosByAppId(orderParams.getAppId());
		if (CollectionUtils.isEmpty(hospitalInfosVos)) {
			return failParamsNotExists("appId");
		}

		// 和appId匹配的数据
		HospitalInfosVo matchVo = null;
		for (HospitalInfosVo vo : hospitalInfosVos) {
			if (vo.getAppId().equals(orderParams.getAppId())) {
				matchVo = vo;
				break;
			}
		}

		// ------------ 查询的条件 ---------------
		List<OrderInfoRequest> orderInfoRequests = new ArrayList<>();

		int tradeMode = orderParams.getTradeMode();
		if (orderParams.getIsQiaoQiaoShang() == 1) {
			if (tradeMode < 0 || tradeMode > 3) {
				return failParamsNotExists("tradeMode");
			} else {
				// 查找全部的，这个代码上有点问题，目前是没问题的。
				// 但是，假如有一家医院（红会），红会在标准平台上，也在新平台上，还在其他平台也上了，现在通过标准平台的查询，应该是查询到标准平台和新平台的数据，而不会查询到其他平台上的数据...
				if (tradeMode == 0) {
					for (HospitalInfosVo vo : hospitalInfosVos) {
						OrderInfoRequest infoRequest = new OrderInfoRequest();
						infoRequest.setBeginTimestamp(beginTime);
						infoRequest.setEndTimestamp(endTime);
						infoRequest.setPlatform(Integer.valueOf(vo.getPlatformCode()));
						infoRequest.setHospitalCode(matchVo.getHospitalCode());
						infoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
						infoRequest.setBizType(orderMode);
						orderInfoRequests.add(infoRequest);
					}
				} else {
					// 找category一样的。
					for (HospitalInfosVo vo : hospitalInfosVos) {
						// if (vo.getCategory() == matchVo.getCategory()) {		// 这里找的就是不应该是按appId来。 按tradeMode来找。！！！1
						if (vo.getCategory() == tradeMode) {
							OrderInfoRequest infoRequest = new OrderInfoRequest();
							infoRequest.setBeginTimestamp(beginTime);
							infoRequest.setEndTimestamp(endTime);
							infoRequest.setPlatform(Integer.valueOf(vo.getPlatformCode()));
							infoRequest.setHospitalCode(matchVo.getHospitalCode());
							infoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
							infoRequest.setBizType(orderMode);
							orderInfoRequests.add(infoRequest);
						}
					}
				}
			}
		} else {
			// **** 这里其实查询的都是单个平台的了。
			if (tradeMode < 0 || tradeMode > 12) {
				return failParamsNotExists("tradeMode");
			} else {
				// 非悄悄上，如果tradeMode = 0 则全平台，否则单独的平台  
				if (tradeMode == 0) {
					for (HospitalInfosVo vo : hospitalInfosVos) {
						if (vo.getPlatformCode().equals(matchVo.getPlatformCode())) {
							OrderInfoRequest infoRequest = new OrderInfoRequest();
							infoRequest.setBeginTimestamp(beginTime);
							infoRequest.setEndTimestamp(endTime);
							infoRequest.setPlatform(Integer.valueOf(vo.getPlatformCode()));
							infoRequest.setHospitalCode(matchVo.getHospitalCode());
							infoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
							infoRequest.setBizType(orderMode);
							orderInfoRequests.add(infoRequest);
						}
					}
				} else {
					OrderInfoRequest infoRequest = new OrderInfoRequest();
					infoRequest.setBeginTimestamp(beginTime);
					infoRequest.setEndTimestamp(endTime);
					// ------------------- 这里的platform 与 tradeMode一致，但是！！后期要求His改成我们的.
					infoRequest.setPlatform(tradeMode);
					infoRequest.setHospitalCode(matchVo.getHospitalCode());
					infoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
					infoRequest.setBizType(orderMode);
					orderInfoRequests.add(infoRequest);
				}
			}
		}

		resultMap = new HashMap<>();
		resultMap.put("resultCode", OutsideResultConstant.RESULT_CODE_SUCCESS);
		resultMap.put("entity", orderInfoRequests);

		return resultMap;
	}
	
	private Map<String, Object> checkRegOrderParams(RegOrderParams regOrderParams) {
		Map<String, Object> resultMap = null;

		/** ------------------------------- 入参非空校验 -----------------------------------**/
		// appId -- 此处的appId只是作为一个医院。通过appId找医院
		if (StringUtils.isBlank(regOrderParams.getAppId())) {
			return failParamsNone("appId");
		}

		// orderMode -- 后面还需要验证在 0,1,2,3 内
		if (regOrderParams.getRegType() == null) {
			return failParamsNone("regType");
		}

		int regType = regOrderParams.getRegType();
		if (regType == 0) {
			regType = -1; // 转为solr查询所需要值。-1即为查询所有类型的订单
		} else if (regType < 0 || regType > 2) {
			return failParamsFormatError("regType");
		}

		// tradeMode -- 后面还需要验证在0,1,2,3内. 不验证了。
		if (regOrderParams.getTradeMode() == null) {
			return failParamsNone("tradeMode");
		}

		// branchCode，如果没有则默认查询所有。不需要单独判断

		// startDate
		if (StringUtils.isBlank(regOrderParams.getStartDate())) {
			return failParamsNone("startDate");
		}

		// endTime
		if (StringUtils.isBlank(regOrderParams.getEndDate())) {
			return failParamsNone("endDate");
		}

		// isQiaoQiaoShang -- 这个根本不会为空的，一起约定好的。
		if (regOrderParams.getIsQiaoQiaoShang() == null) {
			return failParamsNone("Error! Error! Error! Bom, Bom, Bom~~");
		}

		/** ------------------------------- 入参格式校验 -----------------------------------**/
		Date beginDate = DateUtils.StringToDate(regOrderParams.getStartDate());
		long beginTime = 0L;
		if (beginDate != null) {
			beginTime = beginDate.getTime();
		} else {
			return failParamsFormatError("startDate");
		}

		Date endDate = DateUtils.StringToDate(regOrderParams.getEndDate());
		long endTime = 0L;
		if (endDate != null) {
			endTime = endDate.getTime();
		} else {
			return failParamsFormatError("endDate");
		}

		if (beginTime > endTime) {
			return failParamsFormatError("startDate, endDate");
		}

		/** ------------------------------- 入参数据校验 -----------------------------------**/

		List<HospitalInfosVo> hospitalInfosVos = hospitalInfoService.getInfosByAppId(regOrderParams.getAppId());
		if (CollectionUtils.isEmpty(hospitalInfosVos)) {
			return failParamsNotExists("appId");
		}

		// 和appId匹配的数据
		HospitalInfosVo matchVo = null;
		for (HospitalInfosVo vo : hospitalInfosVos) {
			if (vo.getAppId().equals(regOrderParams.getAppId())) {
				matchVo = vo;
				break;
			}
		}

		// ------------ 查询的条件 ---------------
		List<RegInfoRequest> regInfoRequests = new ArrayList<>();

		int tradeMode = regOrderParams.getTradeMode();
		if (regOrderParams.getIsQiaoQiaoShang() == 1) {
			if (tradeMode < 0 || tradeMode > 3) {
				return failParamsNotExists("tradeMode");
			} else {
				// 查找全部的，这个代码上有点问题，目前是没问题的。
				// 但是，假如有一家医院（红会），红会在标准平台上，也在新平台上，还在其他平台也上了，现在通过标准平台的查询，应该是查询到标准平台和新平台的数据，而不会查询到其他平台上的数据...
				if (tradeMode == 0) {
					for (HospitalInfosVo vo : hospitalInfosVos) {
						RegInfoRequest regInfoRequest = new RegInfoRequest();
						regInfoRequest.setBeginTimestamp(beginTime);
						regInfoRequest.setEndTimestamp(endTime);
						regInfoRequest.setPlatform(Integer.valueOf(vo.getPlatformCode()));
						regInfoRequest.setHospitalCode(matchVo.getHospitalCode());
						regInfoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
						regInfoRequest.setRegType(regType);
						regInfoRequests.add(regInfoRequest);
					}
				} else {
					// 找category一样的。
					for (HospitalInfosVo vo : hospitalInfosVos) {
						// if (vo.getCategory() == matchVo.getCategory()) {		// 这里找的就是不应该是按appId来。 按tradeMode来找。！！！1
						if (vo.getCategory() == tradeMode) {
							RegInfoRequest regInfoRequest = new RegInfoRequest();
							regInfoRequest.setBeginTimestamp(beginTime);
							regInfoRequest.setEndTimestamp(endTime);
							regInfoRequest.setPlatform(Integer.valueOf(vo.getPlatformCode()));
							regInfoRequest.setHospitalCode(matchVo.getHospitalCode());
							regInfoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
							regInfoRequest.setRegType(regType);
							regInfoRequests.add(regInfoRequest);
						}
					}
				}
			}
		} else {
			// **** 这里其实查询的都是单个平台的了。
			if (tradeMode < 0 || tradeMode > 12) {
				return failParamsNotExists("tradeMode");
			} else {
				// 非悄悄上，如果tradeMode = 0 则全平台，否则单独的平台  
				if (tradeMode == 0) {
					for (HospitalInfosVo vo : hospitalInfosVos) {
						if (vo.getPlatformCode().equals(matchVo.getPlatformCode())) {
							RegInfoRequest regInfoRequest = new RegInfoRequest();
							regInfoRequest.setBeginTimestamp(beginTime);
							regInfoRequest.setEndTimestamp(endTime);
							regInfoRequest.setPlatform(Integer.valueOf(vo.getPlatformCode()));
							regInfoRequest.setHospitalCode(matchVo.getHospitalCode());
							regInfoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
							regInfoRequest.setRegType(regType);
							regInfoRequests.add(regInfoRequest);
						}
					}
				} else {
					RegInfoRequest regInfoRequest = new RegInfoRequest();
					regInfoRequest.setBeginTimestamp(beginTime);
					regInfoRequest.setEndTimestamp(endTime);
					// ------------------- 这里的platform 与 tradeMode一致，但是！！后期要求His改成我们的.
					regInfoRequest.setPlatform(tradeMode);
					regInfoRequest.setHospitalCode(matchVo.getHospitalCode());
					regInfoRequest.setTradeMode(tradeMode); // tradeMode用传进来的，这个在指定tradeMode时候都不需要改
					regInfoRequest.setRegType(regType);
					regInfoRequests.add(regInfoRequest);
				}
			}
		}

		resultMap = new HashMap<>();
		resultMap.put("resultCode", OutsideResultConstant.RESULT_CODE_SUCCESS);
		resultMap.put("entity", regInfoRequests);

		return resultMap;
	}

	private Map<String, Object> failParamsNone(String field) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode", OutsideResultConstant.RESULT_CODE_PARAMS_NONE);
		resultMap.put("resultMessage", field.concat(" cannot be none."));
		return resultMap;
	}

	private Map<String, Object> failParamsNotExists(String field) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode", OutsideResultConstant.RESULT_CODE_PARAMS_NOT_EXITST);
		resultMap.put("resultMessage", field.concat(" does not exists."));
		return resultMap;
	}

	private Map<String, Object> failParamsFormatError(String field) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultCode", OutsideResultConstant.RESULT_CODE_PARAMS_NOT_EXITST);
		resultMap.put("resultMessage", field.concat(" format error."));
		return resultMap;
	}

}
