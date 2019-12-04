/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-5-22</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.base.datas.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.hospital.PayMode;
import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.exception.SystemException;
import com.yxw.interfaces.vo.register.Doctor;
import com.yxw.interfaces.vo.register.RegDoctor;

/**
 * @Package: com.yxw.web.common
 * @ClassName: HospitalBaseInfoManager
 * @Statement: <p>
 *             基础数据缓存管理
 *             </p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-5-22
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class BaseDatasManager {

	/**
	 * 请求成功代码
	 */
	public static final String RES_SUCCESS_CODE = "0";

	/**
	 * 请求成功但没有查询到数据
	 */
	public static final String RES_SUCCESS_NO_DATA_CODE = "1";

	/**
	 * 请求失败代码
	 */
	public static final String RES_FAILURE_CODE = "-1";

	/**
	 * 
	 */
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 根据医院id 查询 医院Code
	 * 
	 * @param hospitalId
	 * @return
	 */
	public String getHospitalCodeById(String hospitalId) {
		String hospitalCode = null;
		if (StringUtils.isNotEmpty(hospitalId)) {
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalId);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getHospitalCodeById", params);
			if (!CollectionUtils.isEmpty(results)) {
				hospitalCode = (String) results.get(0);
			}
		}

		return hospitalCode;
	}

	/**
	 * 根据医院CODE获取接入平台信息
	 * 
	 * @param hospitalCode
	 * @param appCode
	 *            平台类型<br>
	 *            CacheConstant.CACHE_PAY_TYPE_WEIXIN = "wechat" 微信
	 *            CacheConstant.CACHE_PAY_TYPE_ALIPAY = "alipay" 支付宝
	 * @return
	 */
	/*public HospitalCodeAndAppVo getAppInfoByHospitalCode(String hospitalCode, String appCode) {
		HospitalCodeAndAppVo vo = null;
		if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(appCode)) {
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalCode);
			params.add(appCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAppInfoByHospitalCode", params);
			if (CollectionUtils.isNotEmpty(results)) {
				vo = (HospitalCodeAndAppVo) results.get(0);
			}
		}
		return vo;
	}*/

	public List<PayMode> getPayModesByPlatformCode(String platformCode) throws SystemException {
		List<PayMode> payModes = null;
		List<Object> params = new ArrayList<>();
		params.add(platformCode);
		List<Object> objs = serveComm.get(CacheType.PLATFORM_CACHE, "getPayModesByPlatformCode", params);
		if (!CollectionUtils.isEmpty(objs)) {
			String source = JSON.toJSONString(objs);
			payModes = JSON.parseArray(source, PayMode.class);
		}

		params.clear();
		params = null;

		if (CollectionUtils.isEmpty(payModes)) {
			throw new SystemException("无效的code.没有对应的支付方式.code=" + platformCode);
		}

		return payModes;
	}

	/**
	 * 新平台专用方法。
	 * @param appId
	 * @param appCode
	 * @return
	 */
	public HospitalCodeAndAppVo queryAppInfoByAppIdAndAppCodeForApp(String appId, String appCode, String payModeCode) {
		HospitalCodeAndAppVo vo = null;
		if (StringUtils.isNoneBlank(appId, appCode, payModeCode)) {
			List<Object> params = new ArrayList<Object>();
			params.add(appId);
			params.add(appCode);
			params.add(payModeCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAppInfoByAppIdAndAppCodeForApp", params);
			if (!CollectionUtils.isEmpty(results)) {
				vo = (HospitalCodeAndAppVo) results.get(0);
			}
		}
		return vo;
	}

	/**
	 * 根据医院ID获取接入平台信息
	 * 
	 * @param hospitalId
	 * @param appCode
	 *            平台类型<br>
	 *            CacheConstant.CACHE_PAY_TYPE_WEIXIN = "wechat" 微信
	 *            CacheConstant.CACHE_PAY_TYPE_ALIPAY = "alipay" 支付宝
	 * @return
	 */
	public HospitalCodeAndAppVo getAppInfoByHospitalId(String hospitalId, String appCode) {
		HospitalCodeAndAppVo vo = null;
		if (StringUtils.isNotBlank(hospitalId) && StringUtils.isNotBlank(appCode)) {
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalId);
			params.add(appCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryAppInfoByHospitalId", params);
			if (CollectionUtils.isNotEmpty(results)) {
				vo = (HospitalCodeAndAppVo) results.get(0);
			}
		}
		return vo;
	}

	/**
	 * 根据医院code 查询 医院id
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public String getHospitalIdByCode(String hospitalCode) {
		String hospitalId = null;
		if (StringUtils.isNotEmpty(hospitalCode)) {
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getHospitalIdByCode", params);
			if (CollectionUtils.isNotEmpty(results)) {
				hospitalId = (String) results.get(0);
			}
		}

		return hospitalId;
	}

	/**
	 * 查询医院-分院对应的 interfaceId
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public String getInterfaceId(String hospitalCode, String branchHospitalCode) throws SystemException {
		String interfaceId = null;
		if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(branchHospitalCode)) {
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalCode);
			params.add(branchHospitalCode);
			List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getInterfaceId", params);
			if (CollectionUtils.isNotEmpty(results)) {
				interfaceId = (String) results.get(0);
			}
		}

		return interfaceId;
	}

	/**
	 * 医院code与interfaceid的对应关系
	 * 
	 * @return
	 * @throws SystemException
	 */
	public List<CodeAndInterfaceVo> getCodeAndInterfaceMap() throws SystemException {
		List<CodeAndInterfaceVo> resultList = null;
		List<Object> params = new ArrayList<Object>();
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getCodeAndInterfaceMap", params);
		if (CollectionUtils.isNotEmpty(results)) {
			resultList = new ArrayList<CodeAndInterfaceVo>(results.size());
			String sourceJson = JSON.toJSONString(results);
			resultList.addAll(JSON.parseArray(sourceJson, CodeAndInterfaceVo.class));
		}

		return resultList != null ? resultList : new ArrayList<CodeAndInterfaceVo>();
	}

	/**
	 * 根据app信息获取医院code 商户 id 等
	 * 
	 * @param appCode
	 * @param appId
	 * @return HospitalCodeAndAppVo app与医院关系的一个传值对象
	 */
	public HospitalCodeAndAppVo queryHospitalCodeByApp(String appCode, String appId) throws SystemException {
		HospitalCodeAndAppVo vo = null;
		List<Object> params = new ArrayList<Object>();
		params.add(appCode);
		params.add(appId);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryHospitalCodeByApp", params);
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospitalCodeAndAppVo) results.get(0);
		}

		return vo;
	}

	/**
	 * 根据接入的平台信息 查询医院的分院信息
	 * 
	 * @param appCode
	 * @param appId
	 * @return
	 */
	public List<BranchHospital> queryBranchsByHospitalCode(String appCode, String appId) {
		List<BranchHospital> branches = null;
		List<Object> params = new ArrayList<Object>();
		params.add(appCode);
		params.add(appId);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryBranchsByHospitalCode", params);
		// 暂时用fastjson进行转换
		String sourceJson = JSON.toJSONString(results);
		branches = JSON.parseArray(sourceJson, BranchHospital.class);
		return branches != null ? branches : new ArrayList<BranchHospital>();
	}

	/**
	 * 根据医院code 查询所有分院信息
	 * 
	 * @param hospitalCode
	 * @return
	 */
	public List<BranchHospital> queryBranchsByHospitalCode(String hospitalCode) {
		List<BranchHospital> branches = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "queryBranchsByHospitalCode", params);
		// 暂时用fastjson进行转换
		String source = JSON.toJSONString(results);
		branches = JSON.parseArray(source, BranchHospital.class);
		return branches != null ? branches : new ArrayList<BranchHospital>();
	}

	/**
	 * (微信公众号/支付宝服务窗)获得指定医院代码 分院代码的所有一级科室
	 * 
	 * @param hospitalCode
	 *            医院代码
	 * @param childHospitalCode
	 *            分院代码
	 * @return
	 */
	public List<Dept> getLevelOneDepts(String hospitalCode, String branchHospitalCode, String regType) throws SystemException {
		List<Dept> depts = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getLevelOneDepts", params);
		String source = JSON.toJSONString(results);
		depts = JSON.parseArray(source, Dept.class);

		for (Dept dept : depts) {
			dept.setSubDepts(null);
		}
		return depts != null ? depts : new ArrayList<Dept>();
	}

	/**
	 * (微信公众号/支付宝服务窗)获取2级科室列表
	 * 
	 * @param hospitalCode
	 * @param childHospitalCode
	 * @param parentDeptCode
	 * @return
	 */
	public List<Dept> getLevelTwoDepts(String hospitalCode, String branchHospitalCode, String parentDeptCode) throws SystemException {
		List<Dept> depts = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		params.add(parentDeptCode);
		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getLevelTwoDepts", params);
		String source = JSON.toJSONString(results);
		depts = JSON.parseArray(source, Dept.class);
		return depts != null ? depts : new ArrayList<Dept>();
	}

	/**
	 * (app)获得指定医院代码 分院代码的所有一级科室
	 * 
	 * @param hospitalCode
	 *            医院代码
	 * @param childHospitalCode
	 *            分院代码
	 * @param regType
	 *            挂号类型 当班/预约
	 * @return
	 */
	public List<Dept> getAppLevelOneDepts(RuleHisData ruleHisData, String hospitalCode, String branchHospitalCode, String regType)
			throws SystemException {

		List<Dept> depts = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);

		Integer isSameDeptData = null;
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			params.add(regType);
		} else {
			params.add("");
		}

		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelOneDepts", params);
		String source = JSON.toJSONString(results);
		depts = JSON.parseArray(source, Dept.class);
		return depts != null ? depts : new ArrayList<Dept>();

	}

	/**
	 * (app)获取2级科室列表
	 * 
	 * @param hospitalCode
	 * @param childHospitalCode
	 * @param parentDeptCode
	 * @return
	 */
	public List<Dept> getAppLevelTwoDepts(RuleHisData ruleHisData, String hospitalCode, String branchHospitalCode, String regType,
			String parentDeptCode) throws SystemException {

		List<Dept> depts = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);

		Integer isSameDeptData = null;
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			params.add(regType);
		} else {
			params.add("");
		}
		params.add(parentDeptCode);

		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelTwoDepts", params);
		String source = JSON.toJSONString(results);
		depts = JSON.parseArray(source, Dept.class);
		return depts != null ? depts : new ArrayList<Dept>();
	}

	/**
	 * 得到openId医院所有的绑卡
	 * 
	 * @param openId
	 * @param hospitalCode
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardsByOpenId(String openId, String hospitalCode) {
		List<SimpleMedicalCard> medicalCards = null;
		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		params.add(hospitalCode);
		List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardsByOpenId", params);
		String source = JSON.toJSONString(results);
		medicalCards = JSON.parseArray(source, SimpleMedicalCard.class);
		return medicalCards != null ? medicalCards : new ArrayList<SimpleMedicalCard>();
	}

	/**
	 * 根据appCode 得到同一appCode所有的医院信息(简单信息,只包含医院的名称,id,code)
	 * 
	 * @param appCode
	 * @param areaCode
	 *            区域代码
	 * @return
	 */
	public List<HospIdAndAppSecretVo> getHospitalListByAppCode(String appCode, String areaCode) {
		List<HospIdAndAppSecretVo> vos = null;
		List<Object> params = new ArrayList<Object>();
		params.add(appCode);
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findByAppSecretByAppCode", params);
		String source = JSON.toJSONString(results);
		vos = JSON.parseArray(source, HospIdAndAppSecretVo.class);
		return vos != null ? vos : new ArrayList<HospIdAndAppSecretVo>();
	}

	public List<HospIdAndAppSecretVo> getHospitalListByAppCode(String appCode) {
		List<HospIdAndAppSecretVo> vos = null;
		List<Object> params = new ArrayList<Object>();
		params.add(appCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findByAppSecretByAppCode", params);
		String source = JSON.toJSONString(results);
		vos = JSON.parseArray(source, HospIdAndAppSecretVo.class);
		return vos != null ? vos : new ArrayList<HospIdAndAppSecretVo>();
	}

	/**
	 * 
	 * @param hospital
	 */
	public void updateHospital(Hospital hospital, String updateType) throws SystemException {
		/*** 保存 医院时 不会变更分院信息 故不需要去更新缓存 **/
		List<Object> params = new ArrayList<Object>();
		params.add(hospital);
		params.add(updateType);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateHospitalCache", params);
	}

	/**
	 * @param hospital
	 */
	public void deleteHospital(Hospital hospital) throws SystemException {
		List<Object> params = new ArrayList<Object>();
		params.add(hospital);
		serveComm.delete(CacheType.HOSPITAL_CACHE, "delHospital", params);
	}

	public void deleteAllHospital() throws SystemException {
		List<Object> params = new ArrayList<Object>();
		serveComm.delete(CacheType.HOSPITAL_CACHE, "delAllHospital", params);
	}

	/**
	 * 更新医院的状态
	 * 
	 * @param code
	 * @param status
	 * @throws SystemException
	 */
	public void updateHospitalStatus(String code, Integer status) throws SystemException {
		List<Object> params = new ArrayList<Object>();
		params.add(code);
		params.add(status);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateHospitalStatus", params);
	}

	/**
	 * 更新医院与平台的关系
	 * 
	 * @param vo
	 */
	public void updateAppCache(HospitalCodeAndAppVo vo) {
		List<Object> params = new ArrayList<Object>();
		params.add(vo);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateAppCache", params);
	}

	/**
	 * 搜索医院范围下的医生 在缓存中的匹配的fields
	 * 
	 * @param searchName
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public List<Object> getMatchHospitalDoctors(String searchName, String hospitalCode, String branchHospitalCode, String regType) {
		List<Object> matchDoctorFields = null;
		if (StringUtils.isNotBlank(searchName)) {
			List<Object> params = new ArrayList<Object>();
			params.add(searchName);
			params.add(hospitalCode);
			params.add(branchHospitalCode);
			if (StringUtils.isNotEmpty(regType)) {
				params.add(regType);
			} else {
				params.add("");
			}
			List<Object> results = serveComm.get(CacheType.DOCTOR_CACHE, "getMatchHospitalDoctors", params);//改为返回RegDoctor对象
			if (CollectionUtils.isNotEmpty(results)) {
				matchDoctorFields = new ArrayList<Object>(results.size());
				matchDoctorFields.addAll(results);
			} else {
				matchDoctorFields = new ArrayList<Object>(0);
			}
		}
		return matchDoctorFields;
	}

	/*	*//** 
			* 描述: TODO 
			* 搜索医院医生,匹配具体某个field(用于补全信息的操作)
			* @author Caiwq
			* @date 2017年4月23日  
			*/
	/*

	public List<Object> getMatchHospitalDoctorsForField(String hospitalCode, String branchHospitalCode, String regType, String field) {
	List<Object> matchDoctor = null;
	if (StringUtils.isNotBlank(field)) {
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		if (StringUtils.isNotEmpty(regType)) {
			params.add(regType);
		} else {
			params.add("");
		}
		params.add(field);
		List<Object> results = serveComm.get(CacheType.DOCTOR_CACHE, "getMatchHospitalDoctorsForField", params);//改为返回RegDoctor对象
		if (CollectionUtils.isNotEmpty(results)) {
			matchDoctor = new ArrayList<Object>(results.size());
			matchDoctor.addAll(results);
		} else {
			matchDoctor = new ArrayList<Object>(0);
		}
	}
	return matchDoctor;

	}
	*/
	/**
	 * 搜索区域医生
	 * 
	 * @param searchName
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param areaCode
	 * @return
	 */
	public List<String> getMatchAreaDoctors(String searchName, /*String hospitalCode, String branchHospitalCode,*/String areaCode) {
		List<String> matchDoctorFields = new ArrayList<String>();
		if (StringUtils.isNotBlank(searchName)) {
			List<Object> params = new ArrayList<Object>();
			params.add(searchName);
			/*			params.add(hospitalCode);
						params.add(branchHospitalCode);*/
			params.add(areaCode);
			List<Object> results = serveComm.get(CacheType.REGISTER_SOURCE_CACHE, "getMatchAreaDoctors", params);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				matchDoctorFields = JSON.parseArray(source, String.class);
			}
		}
		return matchDoctorFields;
	}

	/**
	 * 根据医生姓名缓存的field查找区域内的医生
	 * 
	 * @param doctorNameKey
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param areaCode
	 * @return
	 */
	public Doctor getAreaDoctorByField(String doctorNameKey, String hospitalCode, String branchHospitalCode, String areaCode) {
		Doctor doctor = null;
		List<Object> params = new ArrayList<Object>();
		params.add(doctorNameKey);
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.REGISTER_SOURCE_CACHE, "getAreaDoctorByField", params);
		if (CollectionUtils.isNotEmpty(results)) {
			doctor = (Doctor) results.get(0);
		}
		return doctor;
	}

	/**
	 * 根据医生姓名缓存的field,获取医院内的医生信息
	 * 
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public Doctor getHospitalDoctorByField(String doctorNameKey, String hospitalCode, String branchHospitalCode) {
		Doctor doctor = null;
		List<Object> params = new ArrayList<Object>();
		params.add(doctorNameKey);
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.REGISTER_SOURCE_CACHE, "getHospitalDoctorByField", params);
		if (CollectionUtils.isNotEmpty(results)) {
			doctor = (Doctor) results.get(0);
		}
		return doctor;
	}

	/**
	 * 根据医生的编码列表 获取医生信息列表
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param doctorCodes
	 * @return
	 * @throws SystemException
	 */
	public List<RegDoctor> searchDoctors(String hospitalCode, String branchHospitalCode, String deptCode, String[] doctorCodes,
			Integer regType) throws SystemException {
		List<RegDoctor> searchDoctors = null;
		if (doctorCodes != null && doctorCodes.length > 0) {
			List<Object> params = new ArrayList<Object>();
			params.add(hospitalCode);
			params.add(branchHospitalCode);
			params.add(regType != null ? String.valueOf(regType) : "");
			params.add(deptCode);
			params.add(doctorCodes);
			List<Object> results = serveComm.get(CacheType.DOCTOR_CACHE, "getDoctors", params);
			if (CollectionUtils.isNotEmpty(results)) {
				String source = JSON.toJSONString(results);
				searchDoctors = JSON.parseArray(source, RegDoctor.class);
			}
		}
		return searchDoctors != null ? searchDoctors : new ArrayList<RegDoctor>();
	}

	/**
	 * 根据科室code 查询科室下的医生信息
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param doctorCodes
	 * @return
	 * @throws SystemException
	 */
	public List<RegDoctor> searchDoctors(String hospitalCode, String branchHospitalCode, String deptCode) throws SystemException {
		List<RegDoctor> searchDoctors = null;

		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		params.add(deptCode);
		List<Object> results = serveComm.get(CacheType.DOCTOR_CACHE, "getDoctors", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			searchDoctors = JSON.parseArray(source, RegDoctor.class);
		}
		return searchDoctors != null ? searchDoctors : new ArrayList<RegDoctor>();
	}

	/**
	 * 根据医生的编码 获取可医生详细信息
	 * 
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @param doctorCode
	 * @return
	 * @throws SystemException
	 */
	public RegDoctor searchDoctor(String hospitalCode, String branchHospitalCode, String deptCode, String doctorCode)
			throws SystemException {
		RegDoctor regDoctor = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		params.add(deptCode);
		params.add(Arrays.asList(doctorCode));
		List<Object> results = serveComm.get(CacheType.DOCTOR_CACHE, "getDoctor", params);
		if (CollectionUtils.isNotEmpty(results)) {
			regDoctor = (RegDoctor) results.get(0);
		}
		return regDoctor;
	}

	/**
	 * 更新分院信息
	 * 
	 * @param branchHospitals
	 */
	public void updateHospitalByBranch(List<BranchHospital> branchHospitals) {
		List<Object> params = new ArrayList<Object>();
		params.add(branchHospitals);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateHospitalByBranch", params);
	}

	/**
	 * 更新分院信息
	 * 
	 * @param branchHospitals
	 */
	public void updateHospitalForBranch(List<CodeAndInterfaceVo> vos, Hospital hospital) {
		List<Object> params = new ArrayList<Object>();
		params.add(vos);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateHospitalForBranch", params);

		params.clear();
		params.add(hospital);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateHospBaseInfo", params);
	}

	/**
	 * 更新医疗卡缓存信息
	 * 
	 * @param medicalCard
	 * @param opType
	 *            操作类型 可选参数如下:<br>
	 *            CacheConstant.UPDATE_OP_TYPE_ADD 新增<br>
	 *            CacheConstant.UPDATE_OP_TYPE_UPDATE 更新<br>
	 *            CacheConstant.UPDATE_OP_TYPE_DEL 删除<br>
	 */
	public void setMedicalCardToCache(MedicalCard medicalCard, String opType) {
		List<Object> params = new ArrayList<Object>();
		params.add(medicalCard);
		params.add(opType);
		serveComm.set(CacheType.MEDICAL_CARD_CACHE, "setMedicalCardToCache", params);
	}

	/**
	 * 将新拿到的就诊卡信息放入缓存 （异步获取就诊卡信息时，从缓存中没有查到就诊卡，则去数据去查，如果数据库有数据，则重新放入缓存）
	 * 
	 * @param medicalCards
	 */
	public void addMedicalcardsToCache(List<MedicalCard> medicalCards) {
		List<Object> params = new ArrayList<Object>();
		params.add(medicalCards);
		serveComm.set(CacheType.MEDICAL_CARD_CACHE, "addMedicalcardsToCache", params);
	}

	/**
	 * 得到openId医院分院所有的绑卡
	 * 
	 * @param openId
	 * @param hospitalCode
	 * @param branchCode
	 * @return
	 */
	public List<SimpleMedicalCard> getMedicalCardsByOpenId(String openId, String hospitalCode, String branchCode) {
		List<SimpleMedicalCard> medicalCards = null;

		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardsByOpenId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			medicalCards = JSON.parseArray(source, SimpleMedicalCard.class);
		}

		return medicalCards != null ? medicalCards : new ArrayList<SimpleMedicalCard>();
	}

	public List<SimpleMedicalCard> getMedicalCardsByOpenId(String openId) {
		List<SimpleMedicalCard> medicalCards = null;

		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardsByOpenId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			medicalCards = JSON.parseArray(source, SimpleMedicalCard.class);
		}

		return medicalCards != null ? medicalCards : new ArrayList<SimpleMedicalCard>();
	}

	/**
	 * 得到openId所有的曾挂号医生
	 * 
	 * @param openId
	 * @param hospitalCode
	 * @param branchCode
	 * @return
	 */
	public List<HadRegDoctor> getHadRegDoctorsByOpenId(String openId) {
		List<HadRegDoctor> doctors = null;

		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		List<Object> results = serveComm.get(CacheType.HAD_REG_DOCTOR_CACHE, "getHadRegDoctorsByOpenId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			doctors = JSON.parseArray(source, HadRegDoctor.class);
		}

		return doctors != null ? doctors : new ArrayList<HadRegDoctor>();
	}

	/**
	 * 得到openId所有的曾挂号医生
	 * 
	 * @param openId
	 * @param hospitalCode
	 * @param branchCode
	 * @return
	 */
	public List<HadRegDoctor> getHadRegDoctorsByOpenId(String openId, String hospitalCode, String branchHospitalCode) {
		List<HadRegDoctor> doctors = null;

		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.HAD_REG_DOCTOR_CACHE, "getHadRegDoctorsByOpenId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			doctors = JSON.parseArray(source, HadRegDoctor.class);
		}

		return doctors != null ? doctors : new ArrayList<HadRegDoctor>();
	}

	/**
	 * 获取hash结构的缓存信息
	 * 
	 * @param payCacheKey
	 * @param payCacheField
	 * @return
	 */
	//	public <T> T getHashCache(String cacheKey, String cacheField, Class<T> clazz) {
	//		return commonCache.getHashCache(cacheKey, cacheField, clazz);
	//	}

	/**
	 * 删除hash结构的缓存信息
	 * 
	 * @param cacheKey
	 * @param cacheField
	 */
	public void delHashCache(String cacheKey, String cacheField) {
		List<Object> params = new ArrayList<Object>();
		params.add(cacheKey);
		params.add(cacheField);
		serveComm.delete(CacheType.COMMON_CACHE, "delHashCache", params);
	}

	/**
	 * 保存hash结构到缓存
	 * 
	 * @param pay
	 */
	public void setHashCache(String cacheKey, String cacheField, Object obj) {
		List<Object> params = new ArrayList<Object>();
		params.add(cacheKey);
		params.add(cacheField);
		params.add(obj);
		serveComm.set(CacheType.COMMON_CACHE, "setHashCache", params);
	}

	/**
	 * @param appId
	 * @param appCode
	 * @param modeType
	 *            BizConstant.MODE_TYPE_WEIXIN = "wechat" 微信
	 *            BizConstant.MODE_TYPE_ALIPAY = "alipay" 支付宝
	 * 
	 * @return
	 */
	/*public HospitalCodeAndAppVo queryEasyHealthTradeInfo(String appId, String appCode, String modeType) {
		HospitalCodeAndAppVo appInfo = null;
		HospIdAndAppSecretVo vo = null;

		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(appCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", params);

		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospIdAndAppSecretVo) results.get(0);
			String hospitalCode = vo.getHospCode();
			appInfo = getAppInfoByHospitalCode(hospitalCode, modeType);
		}
		return appInfo;
	}*/

	/**
	 * @param hospitalCode
	 * @param branchHospitalCode
	 * @return
	 */
	public CodeAndInterfaceVo getCodeAndInterfaceVo(String hospitalCode, String branchHospitalCode) {
		CodeAndInterfaceVo vo = null;

		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getCodeAndInterfaceVo", params);
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (CodeAndInterfaceVo) results.get(0);
		}

		return vo;
	}

	/**
	 * 获取医院的所有接入平台信息
	 * 
	 * @param hospitalId
	 * @return
	 */
	public HospIdAndAppSecretVo getHospitalEasyHealthAppInfo(String hospitalId, String appCode) {
		HospIdAndAppSecretVo vo = null;
		PlatformSettings settings = null;

		List<Object> params = new ArrayList<Object>();
		if (appCode == null) {
			appCode = "";
		}

		String appId = "";

		params.add(hospitalId);
		params.add(appCode);
		List<Object> hospitalPlatforms = serveComm.get(CacheType.HOSPITAL_CACHE, "findHospitalPlatform", params);
		if (CollectionUtils.isNotEmpty(hospitalPlatforms)) {
			settings = (PlatformSettings) hospitalPlatforms.get(0);

			if (StringUtils.isNotBlank(settings.getAppId())) {
				appId = settings.getAppId();
			}
		}

		params.clear();
		params.add(hospitalId);
		params.add(appId);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "getEasyHealthAppByHospitalId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospIdAndAppSecretVo) results.get(0);
		}

		return vo;
	}

	public HospIdAndAppSecretVo findAppSecretByAppId(String appId, String appCode) {
		HospIdAndAppSecretVo vo = null;

		List<Object> params = new ArrayList<Object>();
		params.add(appId);
		params.add(appCode);
		List<Object> results = serveComm.get(CacheType.HOSPITAL_CACHE, "findAppSecretByAppId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			vo = (HospIdAndAppSecretVo) results.get(0);
		}

		return vo;
	}

	/**
	 * 查询当班和预约可挂号科室
	 * 
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public List<String> searchTwoInterfaceDeptNames(String deptNameStr, String appCode, String areaCode) {
		List<String> deptNames = null;

		List<Object> params = new ArrayList<Object>();
		params.add(deptNameStr);
		params.add(appCode);
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "searchTwoInterfaceDeptNames", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			deptNames = JSON.parseArray(source, String.class);
		}

		return deptNames != null ? deptNames : new ArrayList<String>();
	}

	/**
	 * 查询当班/预约挂号科室,或者查询所有,根据regType来判断
	 * 
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public List<String> searchDeptNames(String deptNameStr, String regType, /*String appCode,*/String areaCode) {
		List<String> deptNames = null;

		List<Object> params = new ArrayList<Object>();
		params.add(deptNameStr);
		if (StringUtils.isNotEmpty(regType)) {
			params.add(regType);
		} else {
			params.add("");
		}
		//		params.add(appCode);
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "searchDeptNames", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			deptNames = JSON.parseArray(source, String.class);
		}

		return deptNames != null ? deptNames : new ArrayList<String>();
	}

	/**
	 * 根据(app)部门的缓存field,获取部门
	 * 
	 * @param deptNameStr
	 * @param areaCode
	 * @return
	 */
	public Dept getDeptByField(RuleHisData ruleHisData, String deptNameKey, String regType, /*String appCode,*/String areaCode) {
		Dept dept = null;
		Integer isSameDeptData = null;

		List<Object> params = new ArrayList<Object>();
		params.add(deptNameKey);
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			params.add(regType);
		} else {
			params.add("");
		}
		//		params.add(appCode);
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getDeptByField", params);
		if (CollectionUtils.isNotEmpty(results)) {
			dept = (Dept) results.get(0);
		}

		return dept;
	}

	/**
	 * 更新医院的状态后更新其缓存
	 * 
	 * @param 维护
	 *            hospital.baseInfo
	 * 
	 * @param hospitalId
	 * @return
	 */
	public void updateHospStatus(Hospital hospital) {
		List<Object> params = new ArrayList<Object>();
		params.add(hospital);
		serveComm.set(CacheType.HOSPITAL_CACHE, "updateHospBaseInfo", params);
	}

	/**
	 * (app)获取2级科室列表
	 * 
	 * @param hospitalCode
	 * @param childHospitalCode
	 * @return
	 */
	public List<Dept> getAppLevelTwoDepts(RuleHisData ruleHisData, String hospitalCode, String branchHospitalCode, String regType)
			throws SystemException {
		List<Dept> depts = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);

		Integer isSameDeptData = null;
		if (ruleHisData != null) {
			isSameDeptData = ruleHisData.getIsSameDeptData();
		}
		if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
			params.add(regType);
		} else {
			params.add("");
		}

		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelTwoDepts", params);
		String source = JSON.toJSONString(results);
		depts = JSON.parseArray(source, Dept.class);
		return depts != null ? depts : new ArrayList<Dept>();
	}

}
