package com.yxw.task.callable;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.base.datas.manager.RuleConfigManager;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.RegisterConstant;
import com.yxw.commons.constants.biz.RuleConstant;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.rule.RuleHisData;
import com.yxw.commons.vo.platform.Dept;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.interfaces.service.YxwCommService;
import com.yxw.interfaces.vo.Request;
import com.yxw.interfaces.vo.Response;
import com.yxw.interfaces.vo.register.RegDept;
import com.yxw.interfaces.vo.register.RegDeptRequest;
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
public class HospitalDeptInfoCollectCall implements Callable<HospitalBaseInfo> {
	public static Logger logger = LoggerFactory.getLogger(HospitalDeptInfoCollectCall.class);
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

	/**
	 * 医院Name
	 */
	private String hospitalName;

	/**
	 * 分院code
	 */
	private String branchHospitalCode;

	/**
	 * 区域代码
	 */
	private String areaCode;

	public static final Charset COLLECT_CHARSET = Charset.forName("UTF-8");

	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	public HospitalDeptInfoCollectCall(String hospitalId, String hospitalCode, String hospitalName, String branchHospitalCode, String interfaceId,
			String areaCode) {
		super();
		this.hospitalId = hospitalId;
		this.hospitalCode = hospitalCode;
		this.hospitalName = hospitalName;
		this.interfaceId = interfaceId;
		this.branchHospitalCode = branchHospitalCode;
		this.areaCode = areaCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.concurrent.Callable#call()
	 */
	public HospitalBaseInfo call() {

		HospitalBaseInfo info = new HospitalBaseInfo();
		info.setInterfaceId(interfaceId);
		info.setHospitalId(hospitalId);
		info.setHospitalName(hospitalName);
		info.setHospitalCode(hospitalCode);
		info.setBranchHospitalCode(branchHospitalCode);
		if (StringUtils.isNotBlank(interfaceId)) {
			try {
				RuleHisData ruleHisData = ruleConfigManager.getRuleHisDataByHospitalCode(hospitalCode);
				Integer isSameDeptData = null;
				if (ruleHisData != null) {
					isSameDeptData = ruleHisData.getIsSameDeptData();
				}
				if (isSameDeptData != null && isSameDeptData.intValue() == RuleConstant.IS_SAME_DEPT_DATA_NO) {
					info = dealWithDifferent(info, ruleHisData);
				} else {
					info = dealWithSame(info, ruleHisData);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("hospitalName:{} ,interfaceId :{} , Network exception interrupt , the HospitalDeptInfoTask is  canceled.", hospitalName,
						interfaceId);
				info.setCollectCallMsg("happened exception , the HospitalDeptInfoTask is  canceled.reason:" + e.getMessage());
				return info;
			}
		} else {
			info.setCollectCallMsg("interfaceId  is null,the HospitalDeptInfoTask is  canceled.");
			logger.error("hospitalName:{},interfaceId  is null!", hospitalName);
		}
		return info;

	}

	/**
	 * 处理区分当班/预约可挂号科室不同数据
	 * @param info
	 */
	private HospitalBaseInfo dealWithDifferent(HospitalBaseInfo info, RuleHisData ruleHisData) throws Exception {
		List<RegDept> hospitalDepts = null;
		StringBuffer msgSb = new StringBuffer();

		//当天可挂号科室
		msgSb.append("当班可挂号科室总数：");
		hospitalDepts = getHisDepts(info, String.valueOf(RegisterConstant.REG_TYPE_CUR));
		if (!CollectionUtils.isEmpty(hospitalDepts)) {
			setDeptsToCache(hospitalDepts, info, String.valueOf(RegisterConstant.REG_TYPE_CUR));
			msgSb.append(hospitalDepts.size());
		} else {
			msgSb.append(0);
		}

		msgSb.append(";");

		msgSb.append("预约可挂号科室总数：");
		hospitalDepts = getHisDepts(info, String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT));
		if (!CollectionUtils.isEmpty(hospitalDepts)) {
			setDeptsToCache(hospitalDepts, info, String.valueOf(RegisterConstant.REG_TYPE_APPOINTMENT));
			msgSb.append(hospitalDepts.size());
		} else {
			msgSb.append(0);
		}

		info.setCollectCallMsg(msgSb.toString());
		return info;
	}

	/**
	 * 处理不区分当班/预约可挂号科室不同数据
	 * @param info
	 * @return
	 */
	private HospitalBaseInfo dealWithSame(HospitalBaseInfo info, RuleHisData ruleHisData) throws Exception {
		StringBuffer msgSb = new StringBuffer();
		List<RegDept> hospitalDepts = getHisDepts(info, null);
		if (!CollectionUtils.isEmpty(hospitalDepts)) {
			msgSb.append("可挂号科室总数:").append(hospitalDepts.size());
			setDeptsToCache(hospitalDepts, info, null);
		} else {
			msgSb.append("可挂号科室总数:0");
		}

		info.setCollectCallMsg(msgSb.toString());
		return info;
	}

	/**
	 * 获取接入医院的当天\预约可挂号科室列表  
	 * @param regType<br>
	 *    REG_TYPE_CUR = 2 当天<br>
	 *    REG_TYPE_APPOINTMENT = 1 预约<br>
	 *    不区分当天/预约：传入null
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<RegDept> getHisDepts(HospitalBaseInfo info, String regType) throws Exception {
		Response deptResp = null;
		List<RegDept> regDepts = null;
		if (StringUtils.isNotEmpty(info.getInterfaceId())) {
			try {
				//YxwService yxwService = SpringContextHolder.getBean(info.getInterfaceId());
				YxwCommService yxwCommService = SpringContextHolder.getBean("yxwCommService");
				RegDeptRequest deptRequest = new RegDeptRequest();
				deptRequest.setBranchCode(info.getBranchHospitalCode());
				deptRequest.setParentDeptCode("");
				if (StringUtils.isNotBlank(regType)) {
					deptRequest.setReservedFieldOne(regType);
				}

				Request request = new Request();
				request.setServiceId(info.getInterfaceId());
				request.setMethodName("getRegDepts");
				request.setInnerRequest(deptRequest);

				//deptResp = yxwService.getRegDepts(deptRequest);
				deptResp = yxwCommService.invoke(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("hospitalName:{},interfaceId :{} , exec getRegDepts is  error.msg:{},case:{}", new Object[] { hospitalName,
						interfaceId,
						e.getMessage(),
						e.getCause() });
				throw new Exception(e.getMessage() + ";caseBy:" + e.getCause());
			}
			if (deptResp == null) {
				return null;
			}
			regDepts = (List<RegDept>) deptResp.getResult();
		}
		return regDepts;
	}

	/**
	 * 存储科室信息列表
	 * @param regDepts
	 * @param info
	 * @param regType    当天/预约  不区分传null
	 * @return
	 */
	private HospitalBaseInfo setDeptsToCache(List<RegDept> regDepts, HospitalBaseInfo info, String regType) {
		// 主科室 与 子科室封装 目前只考虑2级
		List<Dept> parentDepts = new ArrayList<Dept>();
		ConcurrentHashMap<String, List<Dept>> subDepts = new ConcurrentHashMap<String, List<Dept>>();

		// 存储2级科室时的Field前缀   医院code:分院code:当班/预约:一级科室code
		String levelTwoFiedNamePrefix = info.getHospitalCode().concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(info.getBranchHospitalCode());
		if (StringUtils.isNotEmpty(regType)) {
			levelTwoFiedNamePrefix = levelTwoFiedNamePrefix.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(regType);
		}

		//医院所有科室名称的缓存,以实现科室搜索功能
		String nameCacheKey = "";
		// app所有医院科室名称的缓存,以实现科室搜索功能 固定常量 + 区域code + 当班/预约.
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(regType)) {
			params.add(regType);
		} else {
			params.add("");
		}
		//		params.add(BizConstant.MODE_TYPE_APP);//去掉appCode，所有平台都用一份
		params.add(areaCode);
		List<Object> results = serveComm.get(CacheType.DEPT_CACHE, "getTwoInterfaceDeptNameCacheKey", params);

		if (!CollectionUtils.isEmpty(results)) {
			nameCacheKey = (String) results.get(0);
		}

		StringBuilder nameFieldPrefixSb = new StringBuilder();
		nameFieldPrefixSb.append("(").append(info.getHospitalName());
		if (String.valueOf(RegisterConstant.REG_TYPE_CUR).equalsIgnoreCase(regType)) {
			nameFieldPrefixSb.append("[" + RegisterConstant.REG_TYPE_CUR_CHINESE + "]");
		} else {
			nameFieldPrefixSb.append("[" + RegisterConstant.REG_TYPE_APPOINTMENT_CHINESE + "]");
		}
		nameFieldPrefixSb.append(")");
		String nameFieldPrefix = nameFieldPrefixSb.toString();

		Map<String, String> deptNameJsonMap = new HashMap<String, String>();

		if (!CollectionUtils.isEmpty(regDepts)) {
			for (RegDept regDept : regDepts) {
				// 去除无科室编码的无用数据
				if (StringUtils.isEmpty(regDept.getDeptCode())) {
					continue;
				}
				Dept dept = new Dept(regDept);
				dept.setHospitalId(info.getHospitalId());
				dept.setHospitalCode(info.getHospitalCode());
				dept.setHospitalName(info.getHospitalName());
				dept.setBranchHospitalCode(info.getBranchHospitalCode());

				// parentDeptCode 为 null 或者为 0 为一级科室
				boolean isParent = CacheConstant.CACHE_PARENT_DEPT_CODE.equalsIgnoreCase(regDept.getParentDeptCode());
				if (StringUtils.isEmpty(regDept.getParentDeptCode()) || isParent) {
					parentDepts.add(dept);
				} else {
					String subDeptsFileName =
							levelTwoFiedNamePrefix.concat(CacheConstant.CACHE_KEY_SPLIT_CHAR).concat(regDept.getParentDeptCode().trim());
					List<Dept> depts = subDepts.get(subDeptsFileName);
					boolean isNeedPut = false;
					if (CollectionUtils.isEmpty(depts)) {
						depts = new ArrayList<Dept>();
						isNeedPut = true;
					}
					depts.add(dept);
					if (isNeedPut) {
						subDepts.put(subDeptsFileName, depts);
					}
				}

				deptNameJsonMap.put(dept.getDeptName().concat(nameFieldPrefix), JSON.toJSONString(dept));
			}

			if (parentDepts.size() > 0) {
				//				deptCache.setLevelOneDepts(info.getHospitalCode(), info.getBranchHospitalCode(), regType, parentDepts);
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(info.getHospitalCode());
				parameters.add(info.getBranchHospitalCode());
				if (StringUtils.isNotBlank(regType)) {
					parameters.add(regType);
				} else {
					parameters.add("");
				}
				parameters.add(parentDepts);
				serveComm.set(CacheType.DEPT_CACHE, "setAppLevelOneDepts", parameters);
			}
			if (subDepts.size() > 0) {
				//				deptCache.setLevelTwoDepts(info.getHospitalCode(), info.getBranchHospitalCode(), subDepts);
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(info.getHospitalCode());
				parameters.add(info.getBranchHospitalCode());
				parameters.add(subDepts);
				serveComm.set(CacheType.DEPT_CACHE, "setAppLevelTwoDepts", parameters);
			}

			if (!CollectionUtils.isEmpty(deptNameJsonMap)) {
				//				deptCache.setDeptNameCache(deptNameJsonMap);
				List<Object> parameters = new ArrayList<Object>();
				parameters.add(nameCacheKey.replace("*", ""));//去掉通配符
				parameters.add(deptNameJsonMap);
				serveComm.set(CacheType.DEPT_CACHE, "setAppDeptNames", parameters);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("interfaceId:{},LevelOnedepts.size:{},LevelTwodepts.size:{}", new Object[] { info.getInterfaceId(),
						parentDepts.size(),
						subDepts.size() });
			}
		} else {
			logger.error("interfaceId:{},hospitalCode{}  deptResp.getResult() is null!",
					new Object[] { info.getInterfaceId(), info.getHospitalCode() });
		}
		return info;
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
