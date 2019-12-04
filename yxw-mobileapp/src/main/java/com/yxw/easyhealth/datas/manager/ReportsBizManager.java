/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by Administrator</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.datas.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.service.YxwService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.inspection.Examine;
import com.yxw.interfaces.vo.inspection.ExamineDetail;
import com.yxw.interfaces.vo.inspection.ExamineDetailRequest;
import com.yxw.interfaces.vo.inspection.ExamineRequest;
import com.yxw.interfaces.vo.inspection.Inspect;
import com.yxw.interfaces.vo.inspection.InspectDetail;
import com.yxw.interfaces.vo.inspection.InspectDetailRequest;
import com.yxw.interfaces.vo.inspection.InspectRequest;

/**
 * @Package: com.yxw.platform.cache
 * @ClassName: BizDatasCacheManager
 * @Statement: <p>检验检查提交报告查询</p>
 * @JDK version used: 1.6
 * @Author: 周鉴斌
 * @Create Date: 2015-06-18
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@SuppressWarnings("unchecked")
public class ReportsBizManager {
	private static Logger logger = LoggerFactory.getLogger(ReportsBizManager.class);
	// private HospitalCache hospitalCache = SpringContextHolder.getBean(HospitalCache.class);
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 检验报告列表查询
	 * @param params
	 * @return
	 */
	public List<Inspect> getInspectList(Map<String, Object> params) {
		List<Inspect> inspects = new ArrayList<Inspect>();
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			InspectRequest ir = new InspectRequest();
			ir.setPatCardType(params.get("patCardType").toString());
			ir.setPatCardNo(params.get("patCardNo").toString());
			//ir.setAdmissionNo(params.get("admissionNo").toString());
			ir.setBranchCode(params.get("branchHospitalCode").toString());
			ir.setBeginDate(params.get("beginDate").toString());
			ir.setEndDate(params.get("endDate").toString());
			
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getInspectList");
			request.setInnerRequest(ir);
			
			//Response response = yxwService.getInspectList(ir);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				inspects = (List<Inspect>) response.getResult();
				if (CollectionUtils.isEmpty(inspects)) {
					inspects = new ArrayList<Inspect>();
				}
			}
		} else {
			logger.error("getInspectList error. reason: interfaceId is null");
		}
		return inspects;
	}

	public Map<String, Object> getInspectListEx(MedicalCard medicalCard, String beginDate, String endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Inspect> inspects = new ArrayList<Inspect>();
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(medicalCard.getHospitalCode());
		paramsList.add(medicalCard.getBranchCode());
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			InspectRequest ir = new InspectRequest();
			ir.setPatCardType(medicalCard.getCardType() + "");
			ir.setPatCardNo(medicalCard.getCardNo());
			//ir.setAdmissionNo(params.get("admissionNo").toString());
			ir.setBranchCode(medicalCard.getBranchCode());
			ir.setBeginDate(beginDate);
			ir.setEndDate(endDate);
			
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getInspectList");
			request.setInnerRequest(ir);
			
//			Response response = yxwService.getInspectList(ir);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				inspects = (List<Inspect>) response.getResult();
				if (CollectionUtils.isEmpty(inspects)) {
					inspects = new ArrayList<Inspect>();
				}
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
			resultMap.put(BizConstant.COMMON_ENTITY_LIST_KEY, inspects);
		} else {
			logger.error("getInspectListEx error. reason: interfaceId is null");
		}

		return resultMap;
	}

	/**
	 * 检验报告详细查询
	 * @param params
	 * @return
	 */
	public List<InspectDetail> getInspectDetail(Map<String, Object> params) {
		List<InspectDetail> detail = new ArrayList<InspectDetail>();
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			InspectDetailRequest idr = new InspectDetailRequest();
			idr.setBranchCode(params.get("branchHospitalCode").toString());
			idr.setInspectId(params.get("inspectId").toString());
			
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getInspectDetail");
			request.setInnerRequest(idr);
			
//			Response response = yxwService.getInspectDetail(idr);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				detail = (List<InspectDetail>) response.getResult();
				if (detail == null) {
					detail = new ArrayList<InspectDetail>();
				}
			}
		} else {
			logger.error("getInspectDetail error. reason: interfaceId is null");
		}
		return detail;
	}

	/**
	 * 检查报告列表查询
	 * @param params
	 * @return
	 */
	public List<Examine> getExamineList(Map<String, Object> params) {
		List<Examine> examines = new ArrayList<Examine>();
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			ExamineRequest er = new ExamineRequest();
			er.setPatCardType(params.get("patCardType").toString());
			er.setPatCardNo(params.get("patCardNo").toString());
			//er.setAdmissionNo(params.get("admissionNo").toString());
			er.setBranchCode(params.get("branchHospitalCode").toString());
			er.setBeginDate(params.get("beginDate").toString());
			er.setEndDate(params.get("endDate").toString());
			
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getExamineList");
			request.setInnerRequest(er);
			
//			Response response = yxwService.getExamineList(er);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				examines = (List<Examine>) response.getResult();
				if (CollectionUtils.isEmpty(examines)) {
					examines = new ArrayList<Examine>();
				}
			}
		} else {
			logger.error("getExamineList error. reason: interfaceId is null");
		}
		return examines;
	}

	public Map<String, Object> getExamineListEx(MedicalCard medicalCard, String beginDate, String endDate) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Examine> examines = new ArrayList<Examine>();
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(medicalCard.getHospitalCode());
		paramsList.add(medicalCard.getBranchCode());
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			ExamineRequest ir = new ExamineRequest();
			ir.setPatCardType(medicalCard.getCardType() + "");
			ir.setPatCardNo(medicalCard.getCardNo());
			//ir.setAdmissionNo(params.get("admissionNo").toString());
			ir.setBranchCode(medicalCard.getBranchCode());
			ir.setBeginDate(beginDate);
			ir.setEndDate(endDate);
			
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getExamineList");
			request.setInnerRequest(ir);
			
//			Response response = yxwService.getExamineList(ir);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				examines = (List<Examine>) response.getResult();
				if (CollectionUtils.isEmpty(examines)) {
					examines = new ArrayList<Examine>();
				}
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
			resultMap.put(BizConstant.COMMON_ENTITY_LIST_KEY, examines);
		} else {
			logger.error("getExamineListEx error. reason: interfaceId is null");
		}
		return resultMap;
	}

	/**
	 * 检查报告详细查询
	 * @param params
	 * @return
	 */
	public ExamineDetail getExamineDetail(Map<String, Object> params) {
		ExamineDetail detail = new ExamineDetail();
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(params.get("hospitalCode"));
		paramsList.add(params.get("branchHospitalCode"));
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);
		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
//			YxwService yxwService = SpringContextHolder.getBean(interfaceId);
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
			ExamineDetailRequest examineDetailRequest = new ExamineDetailRequest();
			examineDetailRequest.setBranchCode(params.get("branchHospitalCode").toString());
			examineDetailRequest.setCheckId(params.get("checkId").toString());
			examineDetailRequest.setCheckType(params.get("checkType").toString());
			
			Request request = new Request();
			request.setServiceId(interfaceId);
			request.setMethodName("getExamineDetail");
			request.setInnerRequest(examineDetailRequest);
			
			//Response response = yxwService.getExamineDetail(examineDetailRequest);
			Response response = yxwCommService.invoke(request);
			if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
				detail = (ExamineDetail) response.getResult();
				if (detail == null) {
					detail = new ExamineDetail();
				}
			}
		} else {
			logger.error("getExamineDetail error. reason: interfaceId is null");
		}
		return detail;
	}
}
