package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.register.RegDoctor;
import com.yxw.interfaces.vo.register.RegDoctorRequest;
import com.yxw.task.vo.HospitalBaseInfo;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HospitalBaseInfoCollectCall
 * @Statement: <p>
 *             医院基本信息 科室/医生信息轮询线程
 *             </p>
 * @JDK version used:
 * @Author: Administrator
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class HospitalDoctorInfoCollectCall implements Callable<HospitalBaseInfo> {
	public static Logger logger = LoggerFactory.getLogger(HospitalDoctorInfoCollectCall.class);

	/**
	 * 
	 */
	private String hospitalId;

	/**
	 * 医院接口 对应的spring id
	 */
	private String interfaceId;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	private String hospitalName;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	private String areaCode;

	/**
	 * 医院远程调用数据接口
	 */
	//private YxwService yxwService;
	private YxwCommService yxwCommService;

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public HospitalDoctorInfoCollectCall(String hospitalCode, String interfaceId) {
		super();
		this.hospitalCode = hospitalCode;
		this.interfaceId = interfaceId;
		//yxwService = SpringContextHolder.getBean(interfaceId);
		yxwCommService = SpringContextHolder.getBean("yxwCommService");
	}

	public HospitalDoctorInfoCollectCall(String areaCode, String hospitalId, String hospitalName, String hospitalCode, String branchHospitalCode,
			String interfaceId) {
		super();
		this.areaCode = areaCode;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.interfaceId = interfaceId;
		this.hospitalCode = hospitalCode;
		this.branchHospitalCode = branchHospitalCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public HospitalBaseInfo call() {
		HospitalBaseInfo info = new HospitalBaseInfo();
		info.setInterfaceId(interfaceId);
		info.setHospitalCode(hospitalCode);
		info.setBranchHospitalCode(branchHospitalCode);
		info.setHospitalId(hospitalId);

		try {
			//yxwService = SpringContextHolder.getBean(interfaceId);
			yxwCommService = SpringContextHolder.getBean("yxwCommService");
			//if (yxwService != null) {
			if (yxwCommService != null) {
				RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);

				info = doctorCacheHandle(info, ruleHisData);

			} else {
				//logger.warn("interface:{}, poxy yxwService is error.yxwService is null.", interfaceId);
				logger.warn("interface:{}, poxy yxwCommService is error.yxwCommService is null.", interfaceId);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Network exception interrupt , the HospitalDoctorInfoTask is  canceled.");
			return info;
		}

		return info;
	}

	/** 
	 * 处理医生信息缓存
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年4月15日 
	 * @param info
	 * @param ruleHisData
	 * @return 
	 */

	private HospitalBaseInfo doctorCacheHandle(HospitalBaseInfo info, RuleHisData ruleHisData) {

		try {
			Integer isSameDoctorData = null;
			if (ruleHisData != null) {
				isSameDoctorData = ruleHisData.getIsSameDoctorData();
			}
			if (isSameDoctorData != null && isSameDoctorData.intValue() == RuleConstant.IS_SAME_DOCTOR_DATA_NO) {

				info = dealWithDifferent(info, ruleHisData);
			} else {

				info = dealWithSame(info, ruleHisData);
			}
		} catch (Exception e) {
			logger.error("hospitalName:{} ,interfaceId :{} , Network exception interrupt , the HospitalDoctorInfoTask is  canceled.", hospitalName,
					interfaceId);
			info.setCollectCallMsg("happened exception , the HospitalDoctorInfoTask is  canceled.reason:" + e.getMessage());
			return info;
		}

		return info;
	}

	private HospitalBaseInfo dealWithDifferent(HospitalBaseInfo info, RuleHisData ruleHisData) throws Exception {
		List<RegDoctor> hospitalDoctors = null;
		StringBuffer msgSb = new StringBuffer();

		msgSb.append("当班可挂号医生总数：");
		hospitalDoctors = getHisDoctors(info, String.valueOf(RegisterConstant.REG_TYPE_CUR));
		if (!CollectionUtils.isEmpty(hospitalDoctors)) {
			setDoctorsToCache(hospitalDoctors, info, String.valueOf(RegisterConstant.REG_TYPE_CUR));
			msgSb.append(hospitalDoctors.size());
		} else {
			msgSb.append(0);
		}

		msgSb.append(";");

		msgSb.append("预约可挂号医生总数：");
		hospitalDoctors = getHisDoctors(info, String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT));
		if (!CollectionUtils.isEmpty(hospitalDoctors)) {
			setDoctorsToCache(hospitalDoctors, info, String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT));
			msgSb.append(hospitalDoctors.size());
		} else {
			msgSb.append(0);
		}

		info.setCollectCallMsg(msgSb.toString());
		return info;
	}

	private HospitalBaseInfo dealWithSame(HospitalBaseInfo info, RuleHisData ruleHisData) throws Exception {
		StringBuffer msgSb = new StringBuffer();
		List<RegDoctor> hospitalDoctors = getHisDoctors(info, null);
		if (!CollectionUtils.isEmpty(hospitalDoctors)) {
			msgSb.append("可挂号医生总数:").append(hospitalDoctors.size());
			setDoctorsToCache(hospitalDoctors, info, null);
		} else {
			msgSb.append("可挂号医生总数:0");
		}

		info.setCollectCallMsg(msgSb.toString());
		return info;
	}

	@SuppressWarnings({ "unchecked" })
	public List<RegDoctor> getHisDoctors(HospitalBaseInfo info, String regType) throws Exception {
		Response doctorResp = null;
		List<RegDoctor> regDoctors = null;
		if (StringUtils.isNotEmpty(info.getInterfaceId())) {
			try {
				RegDoctorRequest regDoctorRequest = new RegDoctorRequest();
				regDoctorRequest.setBranchCode(branchHospitalCode);
				regDoctorRequest.setReservedFieldOne(regType);

				Request request = new Request();
				request.setServiceId(info.getInterfaceId());
				request.setMethodName("getRegDoctors");
				request.setInnerRequest(regDoctorRequest);

				//doctorResp = yxwService.getRegDoctors(regDoctorRequest);
				doctorResp = yxwCommService.invoke(request);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("hospitalName:{},interfaceId :{} , exec getRegDepts is  error.msg:{},case:{}", new Object[] { hospitalName,
						interfaceId,
						e.getMessage(),
						e.getCause() });
				throw new Exception(e.getMessage() + ";caseBy:" + e.getCause());
			}
			if (doctorResp == null) {
				return null;
			}
			regDoctors = (List<RegDoctor>) doctorResp.getResult();
		}
		return regDoctors;

	}

	private HospitalBaseInfo setDoctorsToCache(List<RegDoctor> regDoctors, HospitalBaseInfo info, String regType) {
		List<Object> params = new ArrayList<Object>();

		// 医院的所有医生
		List<RegDoctor> doctors = new ArrayList<RegDoctor>();
		Map<String, List<RegDoctor>> noDeptNameDoctorMap = new HashMap<String, List<RegDoctor>>();
		List<RegDoctor> noCodeDoctors = new ArrayList<RegDoctor>();

		if (StringUtils.isBlank(regType)) {
			regType = "";
		}

		if (!CollectionUtils.isEmpty(regDoctors)) {
			for (RegDoctor regDoctor : regDoctors) {
				if (regDoctor == null) {
					continue;
				}

				// 去除无DoctorCode的无用数据
				if (StringUtils.isEmpty(regDoctor.getDoctorCode())) {
					noCodeDoctors.add(regDoctor);
					continue;
				}
				regDoctor.setBranchCode(branchHospitalCode);

				if (StringUtils.isBlank(regDoctor.getDeptName())) {
					if (StringUtils.isNotBlank(regDoctor.getDeptCode())) {
						List<RegDoctor> noNameDoctors = noDeptNameDoctorMap.get(regDoctor.getDeptCode());
						boolean isNeedPut = false;
						if (noNameDoctors == null) {
							noNameDoctors = new ArrayList<RegDoctor>();
							isNeedPut = true;
						}
						noNameDoctors.add(regDoctor);
						if (isNeedPut) {
							noDeptNameDoctorMap.put(regDoctor.getDeptCode(), noNameDoctors);
						}
						continue;
					}
				}
				doctors.add(regDoctor);
			}

			// 将无部门名称的医生 在科室缓存中查找科室名称
			if (!noDeptNameDoctorMap.isEmpty()) {
				addDoctorDeptName(hospitalCode, branchHospitalCode, regType, noDeptNameDoctorMap);

				for (List<RegDoctor> noNameDoctors : noDeptNameDoctorMap.values()) {
					doctors.addAll(noNameDoctors);
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("interface:{}, doctors : {} ,no code doctors : {}",
						new Object[] { interfaceId, regDoctors.size(), noCodeDoctors.size(), });
			}
			info.setDoctors(doctors);

			if (!CollectionUtils.isEmpty(doctors)) {
				params.add(hospitalCode);
				params.add(branchHospitalCode);
				params.add(regType);
				params.add(doctors);
				serveComm.set(CacheType.DOCTOR_CACHE, "setDoctors", params);
			}

		} else {
			logger.warn("interface:{}, doctors :0", interfaceId);
		}

		return info;
	}

	private void addDoctorDeptName(String hospitalCode, String branchHospitalCode, String regType, Map<String, List<RegDoctor>> noDeptNameDoctorMap) {

		List<Dept> levOneDepts = null;
		List<Dept> levTwoDepts = null;
		List<Object> params = new ArrayList<Object>();

		RuleRegister ruleRegister = ruleConfigManager.getRuleRegisterByHospitalCode(hospitalCode);
		if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_NO) {

			params.add(hospitalCode);
			params.add(branchHospitalCode);
			params.add(regType);
			List<Object> levOneResults = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelOneDepts", params);
			if (!CollectionUtils.isEmpty(levOneResults)) {
				String source = JSON.toJSONString(levOneResults);
				levOneDepts = JSON.parseArray(source, Dept.class);

				for (Dept dept : levOneDepts) {
					List<RegDoctor> doctors = noDeptNameDoctorMap.get(dept.getDeptCode());
					if (!CollectionUtils.isEmpty(doctors)) {
						for (RegDoctor doctor : doctors) {
							doctor.setDeptName(dept.getDeptName());
						}
					}
				}
			}

		} else if (ruleRegister.getIsHasGradeTwoDept() == RuleConstant.IS_HAS_TWO_DEPT_YES) {
			params.clear();
			params.add(hospitalCode);
			params.add(branchHospitalCode);
			params.add(regType);
			List<Object> levTwoResults = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelTwoDepts", params);
			if (!CollectionUtils.isEmpty(levTwoResults)) {
				String source = JSON.toJSONString(levTwoResults);
				levTwoDepts = JSON.parseArray(source, Dept.class);

				for (Dept dept : levTwoDepts) {
					List<RegDoctor> doctors = noDeptNameDoctorMap.get(dept.getDeptCode());
					if (!CollectionUtils.isEmpty(doctors)) {
						for (RegDoctor doctor : doctors) {
							doctor.setDeptName(dept.getDeptName());
						}
					}
				}
			}
		}

		/*//一级科室查找
		List<Dept> levOneDepts = null;
		List<Dept> levTwoDepts = null;
		List<Object> params = new ArrayList<Object>();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		params.add(regType);
		List<Object> levOneResults = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelOneDepts", params);
		if (!CollectionUtils.isEmpty(levOneResults)) {
			String source = JSON.toJSONString(levOneResults);
			levOneDepts = JSON.parseArray(source, Dept.class);

			for (Dept dept : levOneDepts) {
				List<RegDoctor> doctors = noDeptNameDoctorMap.get(dept.getDeptCode());
				if (!CollectionUtils.isEmpty(doctors)) {
					for (RegDoctor doctor : doctors) {
						doctor.setDeptName(dept.getDeptName());
					}
				}
			}
		}

		//二级科室查找
		params.clear();
		params.add(hospitalCode);
		params.add(branchHospitalCode);
		params.add(regType);
		List<Object> levTwoResults = serveComm.get(CacheType.DEPT_CACHE, "getAppLevelTwoDepts", params);
		if (!CollectionUtils.isEmpty(levTwoResults)) {
			String source = JSON.toJSONString(levTwoResults);
			levTwoDepts = JSON.parseArray(source, Dept.class);

			for (Dept dept : levTwoDepts) {
				List<RegDoctor> doctors = noDeptNameDoctorMap.get(dept.getDeptCode());
				if (!CollectionUtils.isEmpty(doctors)) {
					for (RegDoctor doctor : doctors) {
						doctor.setDeptName(dept.getDeptName());
					}
				}
			}
		}*/
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getBranchHospitalCode() {
		return branchHospitalCode;
	}

	public void setBranchHospitalCode(String branchHospitalCode) {
		this.branchHospitalCode = branchHospitalCode;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
