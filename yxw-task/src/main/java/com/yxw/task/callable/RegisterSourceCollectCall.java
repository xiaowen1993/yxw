package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.entity.platform.rule.RuleRegister;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.register.RegInfo;
import com.yxw.interfaces.vo.register.RegInfoRequest;
import com.yxw.task.taskitem.RegisterSourceTask;
import com.yxw.task.vo.RegisterTaskResult;

/**
 * @Package: com.yxw.platform.quartz.callable
 * @ClassName: HospitalBaseInfoCollectCall
 * @Statement: <p>
 *             号源  轮询
 *             </p>
 * @JDK version used:
 * @Author: Yuce
 * @Create Date: 2015-5-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class RegisterSourceCollectCall implements Callable<RegisterTaskResult> {
	//private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public static Logger logger = LoggerFactory.getLogger(RegisterSourceCollectCall.class);

	private RuleConfigManager ruleConfigManager = SpringContextHolder.getBean(RuleConfigManager.class);

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

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	public RegisterSourceCollectCall(String areaCode, String hospitalId, String hospitalName, String hospitalCode, String branchHospitalCode,
			String interfaceId) {
		super();
		this.areaCode = areaCode;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.hospitalCode = hospitalCode;
		this.interfaceId = interfaceId;
		this.branchHospitalCode = branchHospitalCode;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public RegisterTaskResult call() {
		RegisterTaskResult result = new RegisterTaskResult();
		result.setHospitalId(hospitalId);
		result.setHospitalName(hospitalName);
		result.setInterfaceId(interfaceId);
		result.setHospitalCode(hospitalCode);
		result.setBranchHospitalCode(branchHospitalCode);
		StringBuffer collectCallMsg = new StringBuffer();
		if (StringUtils.isNotBlank(hospitalCode) && StringUtils.isNotBlank(interfaceId)) {
			Map<String, Object> buildRegInfoMap = buildRegInfoRequest(result);
			RegInfoRequest regInfoRequest = (RegInfoRequest) buildRegInfoMap.get("regInfoRequest");
			if (StringUtils.isNotBlank(regInfoRequest.getEndDate())) {
				try {
					List<RegInfo> infos = (List<RegInfo>) getHisRegInfos(result, regInfoRequest);
					if (RegisterSourceTask.logger.isDebugEnabled()) {
						RegisterSourceTask.logger.debug("this hospital code:{} , reg source infos size:{}",
								new Object[] { hospitalCode, infos.size() });
					}

					if (!CollectionUtils.isEmpty(infos)) {
						List<Object> params = new ArrayList<Object>();
						params.add(areaCode);
						params.add(hospitalId);
						params.add(hospitalName);
						params.add(hospitalCode);
						params.add(branchHospitalCode);
						params.add((String) buildRegInfoMap.get("regType"));
						params.add(infos);
						serveComm.set(CacheType.REGISTER_SOURCE_CACHE, "setRegSourceToCache", params);

						collectCallMsg.append("collect success.regInfo's num:").append(infos.size());
					} else {
						collectCallMsg.append("collect success.regInfo's num:0");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					collectCallMsg.append("collect failure.原因:").append(e.getMessage());
				}
			} else {
				collectCallMsg.append("collect failure.原因:1、挂号规则中配置缓存天数为0;2、His数据规则中已配置医院不支持当班挂号号源缓存接口,也不支持预约号源缓存接口");
			}
			result.setCollectCallMsg(collectCallMsg.toString());
		}
		return result;

	}

	private Map<String, Object> buildRegInfoRequest(RegisterTaskResult result) {
		Map<String, Object> buildRegInfoMap = new HashMap<String, Object>();
		RegInfoRequest regInfoRequest = new RegInfoRequest();
		regInfoRequest.setBranchCode(branchHospitalCode);
		Calendar start = Calendar.getInstance();
		regInfoRequest.setBeginDate(BizConstant.YYYYMMDD.format(start.getTime()));

		Integer sourceCacheDays = null;
		RuleConfigManager ruleManager = SpringContextHolder.getBean(RuleConfigManager.class);
		RuleRegister ruleRegister = ruleManager.getRuleRegisterByHospitalCode(hospitalCode);
		if (ruleRegister != null) {
			sourceCacheDays = ruleRegister.getSourceCacheDays();
		}
		if (sourceCacheDays == null) {
			sourceCacheDays = 7;
		}

		RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);

		Integer isHadCurRegSourceInterface = null;
		Integer isHadAppointmentRegSourceInterface = null;
		if (ruleHisData != null) {
			//是否有当班号源缓存接口
			isHadCurRegSourceInterface = ruleHisData.getIsHadCurRegSourceInterface();
			if (isHadCurRegSourceInterface == null) {
				isHadCurRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
			}
			//是否有预约号源缓存接口
			isHadAppointmentRegSourceInterface = ruleHisData.getIsHadAppointmentRegSourceInterface();
			if (isHadAppointmentRegSourceInterface == null) {
				isHadAppointmentRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
			}
		} else {
			isHadCurRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
			isHadAppointmentRegSourceInterface = RuleConstant.IS_HAD_INTERFACE_YES;
		}

		if (sourceCacheDays > 0) {
			if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES) {
				//根据缓存天数配置,缓存号源
				Calendar end = Calendar.getInstance();
				end.add(Calendar.DAY_OF_MONTH, sourceCacheDays - 1);
				regInfoRequest.setEndDate(BizConstant.YYYYMMDD.format(end.getTime()));
				buildRegInfoMap.put("regType", String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT));
			} else if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO) {
				//只缓存当班号源
				regInfoRequest.setEndDate(regInfoRequest.getBeginDate());
				buildRegInfoMap.put("regType", String.valueOf(RegisterConstant.REG_TYPE_CUR));
			} else if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_YES) {
				//只缓存预约号源

			} else if (isHadCurRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO
					&& isHadAppointmentRegSourceInterface.intValue() == RuleConstant.IS_HAD_INTERFACE_NO) {
				//不缓存号源
				regInfoRequest.setEndDate(null);
			}
		} else {
			regInfoRequest.setEndDate(null);
		}

		buildRegInfoMap.put("regInfoRequest", regInfoRequest);
		return buildRegInfoMap;
	}

	private List<RegInfo> getHisRegInfos(RegisterTaskResult result, RegInfoRequest regInfoRequest) throws Exception {
		List<RegInfo> infos = null;
		try {
			//YxwService yxwService = SpringContextHolder.getBean(result.getInterfaceId());
			YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");

			//if (yxwService != null) {
			if (yxwCommService != null) {
				logger.info("result.getHospitalName():{},result.getInterfaceId():{}", result.getHospitalName(), result.getInterfaceId());
				//Response response = yxwService.getRegInfo(regInfoRequest);

				Request request = new Request();
				request.setServiceId(result.getInterfaceId());
				request.setMethodName("getRegInfo");
				request.setInnerRequest(regInfoRequest);
				Response response = yxwCommService.invoke(request);
				//logger.info("result.getHospitalName():{},yxwService:{}", result.getHospitalName(), yxwService);
				logger.info("result.getHospitalName():{},yxwCommService:{}", result.getHospitalName(), yxwCommService);
				if (response != null) {
					infos = (List<RegInfo>) response.getResult();
				} else {
					RegisterSourceTask.logger.error("hospitalName : {} ,load regInfos is error. response is null", result.getHospitalName());
					throw new Exception("load regInfos is error.his response is null");
				}
			} else {
				//RegisterSourceTask.logger.warn("interface:{}, poxy yxwService is error.yxwService is null", interfaceId);
				//throw new Exception("poxy yxwService is error.yxwService is null");
				RegisterSourceTask.logger.warn("interface:{}, poxy yxwCommService is error.yxwCommService is null", interfaceId);
				throw new Exception("poxy yxwCommService is error.yxwCommService is null");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			RegisterSourceTask.logger.error("hospitalName : {} , load regInfos is error. msg:{} , case {}", result.getHospitalName(), e.getMessage(),
					e.getCause());
			RegisterSourceTask.logger.error("hospitalName : {} , load regInfos is error. msg:{}", result.getHospitalName(), e);
			e.printStackTrace();
			throw new Exception(e.getMessage() + ";caseBy:" + e.getCause());
		}
		return infos;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
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
}
