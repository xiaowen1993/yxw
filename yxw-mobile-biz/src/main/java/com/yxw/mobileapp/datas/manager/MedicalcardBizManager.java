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
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.constants.GenderType;
import com.yxw.interfaces.constants.PatientType;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.mycenter.Card;
import com.yxw.interfaces.vo.mycenter.CardRequest;
import com.yxw.interfaces.vo.mycenter.MZPatient;
import com.yxw.interfaces.vo.mycenter.MZPatientRequest;

/**
 * @Package: com.yxw.platform.datas.manager
 * @ClassName: ClinicBizManager
 * @Statement: <p>绑卡的接口</p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-28
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class MedicalcardBizManager {

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private Logger logger = LoggerFactory.getLogger(MedicalcardBizManager.class);

	/**
	 * 个人中心-->患者信息查询
	 * @param hospitalCode
	 * @param branchCode
	 * @param patCardNo  卡号
	 * @param patCardType 卡类型
	 * @param patName   持卡人姓名
	 * @return
	 */
	public Map<String, Object> queryMZPatient(String hospitalCode, String branchCode, String patCardNo, String patCardType, String patName) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MZPatient patient = null;

		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(hospitalCode);
		paramsList.add(branchCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);

		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			if (StringUtils.isNotEmpty(interfaceId)) {
//				YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				MZPatientRequest patientRequest = new MZPatientRequest();
				patientRequest.setPatCardNo(patCardNo);
				patientRequest.setPatCardType(patCardType);
				patientRequest.setPatName(patName);
				patientRequest.setBranchCode(branchCode);
				
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getMZPatient");
				request.setInnerRequest(patientRequest);
				
//				Response response = yxwService.getMZPatient(patientRequest);
				Response response = yxwCommService.invoke(request);
				dataMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
					patient = (MZPatient) response.getResult();
					logger.info("create card... result: {}", JSON.toJSONString(patient));
					dataMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, patient);
				} else {
					dataMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				}
			}
		} else {
			logger.error("could not find the interfaceId. hosptalCode: {}. branchCode: {}.", new Object[] { hospitalCode, branchCode });
		}

		return dataMap;
	}

	/**
	 * 查询病人信息，多传入了一些额外参数，供接口保存使用
	 * @param hospitalCode
	 * @param branchCode
	 * @param patCardNo
	 * @param patCardType
	 * @param patName
	 * @return
	 */
	public Map<String, Object> queryMZPatient(String hospitalCode, String branchCode, MedicalCard medicalCard) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		MZPatient patient = null;
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(hospitalCode);
		paramsList.add(branchCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);

		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			if (StringUtils.isNotEmpty(interfaceId)) {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");

				MZPatientRequest patientRequest = new MZPatientRequest();
				patientRequest.setBranchCode(branchCode);
				patientRequest.setGuardAddress("");
				patientRequest.setGuardIdNo(medicalCard.getGuardIdNo());
				patientRequest.setGuardIdType(medicalCard.getGuardIdType() == null ? "" : medicalCard.getGuardIdType().toString());
				patientRequest.setGuardMobile(medicalCard.getGuardMobile());
				patientRequest.setGuardName(medicalCard.getGuardName());
				patientRequest.setPatAddress(medicalCard.getAddress());
				patientRequest.setPatBirth(medicalCard.getBirth());
				patientRequest.setPatCardNo(medicalCard.getCardNo());
				patientRequest.setPatCardType(medicalCard.getCardType() == null ? "" : medicalCard.getCardType().toString());
				patientRequest.setPatIdNo(medicalCard.getIdNo());
				patientRequest.setPatIdType(medicalCard.getIdType() == null ? "" : medicalCard.getIdType().toString());
				patientRequest.setPatMobile(medicalCard.getMobile());
				patientRequest.setPatName(medicalCard.getName());

				// 性别
				if (medicalCard.getSex() == null || medicalCard.getSex().intValue() == 2) {
					patientRequest.setPatSex(GenderType.GENDER_F);
				} else {
					patientRequest.setPatSex(GenderType.GENDER_M);
				}
				
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("getMZPatient");
				request.setInnerRequest(patientRequest);

//				Response response = yxwService.getMZPatient(patientRequest);
				Response response = yxwCommService.invoke(request);
				dataMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
					patient = (MZPatient) response.getResult();
					logger.info("get card... result: {}", JSON.toJSONString(patient));
					dataMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, patient);
				} else {
					dataMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				}
			}
		} else {
			logger.error("could not find the interfaceId. hospitalCode: {}. branchCode: {}.", new Object[] { hospitalCode, branchCode });
		}
		return dataMap;
	}

	/**
	 * @param hospitalCode
	 * @param branchCode
	 * @param medicalCard
	 * @return
	 */
	public Map<String, Object> createMZPatient(String hospitalCode, String branchCode, MedicalCard medicalCard) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Card card = null;
		List<Object> paramsList = new ArrayList<Object>();
		paramsList.add(hospitalCode);
		paramsList.add(branchCode);
		List<Object> objects = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", paramsList);

		if (CollectionUtils.isNotEmpty(objects)) {
			String interfaceId = (String) objects.get(0);
			if (StringUtils.isNotEmpty(interfaceId)) {
				//YxwService yxwService = SpringContextHolder.getBean(interfaceId);
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				CardRequest cardRequest = new CardRequest();
				// 分院代码
				cardRequest.setBranchCode(medicalCard.getBranchCode());
				// 建档类型
				if (StringUtils.isNotBlank(medicalCard.getGuardName())) {
					cardRequest.setPatType(PatientType.CHILDREN);
				} else {
					cardRequest.setPatType(PatientType.ADULT);
				}
				// 监护人联系地址
				cardRequest.setGuardAddress("");
				// 监护人证件号码
				cardRequest.setGuardIdNo(medicalCard.getGuardIdNo());
				// 监护人证件类型
				cardRequest.setGuardIdType(medicalCard.getGuardIdType() == null ? "" : medicalCard.getGuardIdType().toString());
				// 监护人电话号码
				cardRequest.setGuardMobile(medicalCard.getGuardMobile());
				// 监护人姓名
				cardRequest.setGuardName(medicalCard.getGuardName());
				// 地址
				cardRequest.setPatAddress(medicalCard.getAddress());
				// 出生日期
				cardRequest.setPatBirth(medicalCard.getBirth());
				// 证件号码
				cardRequest.setPatIdNo(medicalCard.getIdNo());
				// 证件类型
				cardRequest.setPatIdType(medicalCard.getIdType() == null ? "" : medicalCard.getIdType().toString());
				// 联系电话
				cardRequest.setPatMobile(medicalCard.getMobile());
				// 姓名
				cardRequest.setPatName(medicalCard.getName());
				// 性别
				if (medicalCard.getSex() == null || medicalCard.getSex().intValue() == 2) {
					cardRequest.setPatSex(GenderType.GENDER_F);
				} else {
					cardRequest.setPatSex(GenderType.GENDER_M);
				}
				
				Request request = new Request();
				request.setServiceId(interfaceId);
				request.setMethodName("createCard");
				request.setInnerRequest(cardRequest);

				//Response response = yxwService.createCard(cardRequest);
				Response response = yxwCommService.invoke(request);
				dataMap.put(BizConstant.INTERFACE_MAP_CODE_KEY, response.getResultCode());
				if (BizConstant.INTERFACE_RES_SUCCESS_CODE.equalsIgnoreCase(response.getResultCode())) {
					card = (Card) response.getResult();
					logger.info("create card... result: {}", JSON.toJSONString(card));
					dataMap.put(BizConstant.INTERFACE_MAP_DATA_KEY, card);
				} else {
					dataMap.put(BizConstant.INTERFACE_MAP_MSG_KEY, response.getResultMessage());
				}
			}
		} else {
			logger.error("could not find the interfaceId. hospitalCode: {}. branchCode: {}.", new Object[] { hospitalCode, branchCode });
		}

		return dataMap;
	}
}
