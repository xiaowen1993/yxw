/**
 * <html>
 *   <body>
 *     <p>Copyright(C)版权所有 - 2015 广州医享网络科技发展有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2015年5月15日</p>
 *     <p>Created by homer.yang</p>
 *   </body>
 * </html>
 */
package com.yxw.platform.hospital.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeInterfaceAndAppVo;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.platform.hospital.dao.HospitalDao;

/**
 * 后台医院管理 Dao 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Repository
public class HospitalDaoImpl extends BaseDaoImpl<Hospital, String> implements HospitalDao {

	private static Logger logger = LoggerFactory.getLogger(HospitalDaoImpl.class);

	private final static String SQLNAME_FIND_ALL_BY_STATUS = "findAllByStatus";
	private final static String SQLNAME_UPDATE_STATUS = "updateStatus";
	private final static String SQLNAME_FIND_HOSPITAL_BY_CODE = "findHospitalByCode";
	private final static String SQLNAME_FIND_HOSPITAL_BY_NAME = "findHospitalByName";
	private final static String SQLNAME_FIND_HOSPITAL_BY_CODE_AND_ID = "findHospitalByCodeAndID";
	private final static String SQLNAME_FIND_HOSPITAL_BY_NAME_AND_ID = "findHospitalByNameAndID";

	private final static String SQLNAME_UPDATE_PUBLISH_RULE_INFO = "updatePublishRuleInfo";

	private final static String SQLNAME_QUERY_CODE_AND_APP = "queryCodeAndApp";

	private final static String SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP = "queryCodeAndInterfaceIdAndApp";

	private final static String SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP_BY_APP_ID = "queryCodeAndInterfaceIdAndAppByAppId";

	private final static String SQLNAME_FIND_HAD_BRANCH_BY_PAGE = "findHadBranchByPage";

	private final static String SQLNAME_FIND_BY_HOSPITALIDS = "findByHospitalIds";
	private final static String SQLNAME_FIND_APPSECRET_BY_APPID = "findAppSecretByAppId";
	private final static String SQLNAME_FIND_APPSECRET_BY_HOSPITALID = "findAppSecretByHospitalId";
	private final static String SQLNAME_FIND_All_APPSECRET = "findAllAppSecret";
	private final static String SQLNAME_FIND_APPID_BY_HOSPID = "findAppIdByHospitalId";
	private final static String SQLNAME_QUERY_CODE_AND_INTERFACE_BY_ID = "queryCodeAndInterfaceById";
	private final static String SQLNAME_QUERY_ALL_CODE_AND_INTERFACES = "queryAllCodeAndInterfaceIds";
	private final static String SQLNAME_QUERY_HAD_CONFIG_RULE = "queryHadConfigRule";
	private final static String SQLNAME_FIND_HOSPITAL_BY_BRANCH_HOSPITAL_CODE = "findHospitalByBranchHospitalCode";
	private final static String SQLNAME_QUERY_CODE_AND_APP_BY_HOSPITAL_ID = "queryCodeAndAppByHospitalId";
	private final static String SQLNAME_UPDATE_FOR_RULE_EDIT_TIME = "updateForRuleEditTime";

	private final static String SQLNAME_FIND_HOSPITAL_BY_CODES = "findHospitalByCodes";
	private final static String SQLNAME_SELECT_HOSP_BY_AREACODE = "findHospsByAreaCode";
	private final static String SQLNAME_SELECT_AVAILABLE_HOSP_BY_AREACODE = "findAvailableHospsByAreaCode";

	private final static String SQLNAME_QUERY_HOSPITAL_AND_OPTION_BY_APPCODE_AND_BIZCODE = "queryHospitalAndOptionByAppCodeAndBizCode";

	@Override
	public void updateStatus(Hospital hospital) {
		try {
			Assert.notNull(hospital);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_STATUS), hospital);
		} catch (Exception e) {
			logger.error(String.format("更新医院出错！语句：%s", getSqlName(SQLNAME_UPDATE_STATUS)), e);
			throw new SystemException(String.format("更新医院出错！语句：%s", getSqlName(SQLNAME_UPDATE_STATUS)), e);
		}
	}

	@Override
	public Hospital findHospitalByCode(String code) {
		try {
			Assert.notNull(code);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODE), code);
		} catch (Exception e) {
			logger.error(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODE)), e);
			throw new SystemException(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODE)), e);
		}
	}

	@Override
	public List<Hospital> findHospitalByCodes(List<String> codes) {
		try {
			Assert.notNull(codes);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODES), codes);
		} catch (Exception e) {
			logger.error(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODES)), e);
			throw new SystemException(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODES)), e);
		}
	}

	@Override
	public Hospital findHospitalByName(String name) {
		try {
			Assert.notNull(name);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME), name);
		} catch (Exception e) {
			logger.error(String.format("根据name查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME)), e);
			throw new SystemException(String.format("根据name查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME)), e);
		}
	}

	@Override
	public Hospital findHospitalByCodeAndID(Hospital hospital) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODE_AND_ID), hospital);
		} catch (Exception e) {
			logger.error(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODE_AND_ID)), e);
			throw new SystemException(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_CODE_AND_ID)), e);
		}
	}

	@Override
	public Hospital findHospitalByNameAndID(Hospital hospital) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME_AND_ID), hospital);
		} catch (Exception e) {
			logger.error(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME_AND_ID)), e);
			throw new SystemException(String.format("根据code查询医院出错！语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME_AND_ID)), e);
		}
	}

	@Override
	public void updatePublishRuleInfo(Hospital hospital) {
		// TODO Auto-generated method stub
		try {
			sqlSession.update(getSqlName(SQLNAME_UPDATE_PUBLISH_RULE_INFO), hospital);
		} catch (Exception e) {
			logger.error(String.format("更新医院规则发布信息出错!语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME_AND_ID)), e);
			throw new SystemException(String.format("更新医院规则发布信息出错!语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_NAME_AND_ID)), e);
		}
	}

	@Override
	public List<HospitalCodeInterfaceAndAppVo> queryCodeAndInterfaceIdAndApp() {
		// TODO Auto-generated method stub
		List<HospitalCodeInterfaceAndAppVo> vos = null;
		try {
			vos = sqlSession.selectList(getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP));
		} catch (Exception e) {
			logger.error(String.format("查询医院与App的对应关系及接口ID及分院code信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP)), e);
			throw new SystemException(String.format("查询医院与App的对应关系及接口ID及分院code信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP)),
					e);
		}
		return vos;
	}

	@Override
	public List<HospitalCodeInterfaceAndAppVo> queryCodeAndInterfaceIdAndAppByAppId(String appId) {
		List<HospitalCodeInterfaceAndAppVo> list = null;
		try {
			Assert.notNull(appId);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("appId", appId);
			list = sqlSession.selectList(getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP_BY_APP_ID), map);
		} catch (Exception e) {
			logger.error(
					String.format("根据appId查询医院与App的对应关系,增加接口ID及分院code信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP_BY_APP_ID)),
					e);
			throw new SystemException(String.format("根据appId查询医院与App的对应关系,增加接口ID及分院code信息出错!语句：%s",
					getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_ID_AND_APP_BY_APP_ID)), e);
		}
		return list;
	}

	@Override
	public List<HospitalCodeAndAppVo> queryCodeAndApp() {
		List<HospitalCodeAndAppVo> vos = null;
		try {
			vos = sqlSession.selectList(getSqlName(SQLNAME_QUERY_CODE_AND_APP));
		} catch (Exception e) {
			logger.error(String.format("查询医院与接入平台信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_APP)), e);
			throw new SystemException(String.format("查询医院与接入平台信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_APP)), e);
		}
		return vos;
	}

	@Override
	public PageInfo<Hospital> findHadBranchByPage(Map<String, Object> params, Page<Hospital> page) {
		try {

			PageHelper.startPage(page.getPageNum(), page.getPageSize());
			List<Hospital> list = sqlSession.selectList(getSqlName(SQLNAME_FIND_HAD_BRANCH_BY_PAGE), params);
			return new PageInfo<Hospital>(list);
		} catch (Exception e) {
			logger.error(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_HAD_BRANCH_BY_PAGE)), e);
			throw new SystemException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SQLNAME_FIND_HAD_BRANCH_BY_PAGE)), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yxw.platform.hospital.dao.HospitalDao#findByHospitalIds(java.lang
	 * .String[])
	 */
	@Override
	public List<Hospital> findByHospitalIds(String[] hospitalIds) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hospitalIds", hospitalIds);
			return sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_HOSPITALIDS), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITALIDS)), e);
			throw new SystemException(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_BY_HOSPITALIDS)), e);
		}
	}

	@Override
	public HospIdAndAppSecretVo findAppSecretByAppId(Map<String, Object> params) {
		try {
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_APPSECRET_BY_APPID), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_APPSECRET_BY_APPID)), e);
			throw new SystemException(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_APPSECRET_BY_APPID)), e);
		}
	}

	public List<HospIdAndAppSecretVo> findAppSecretByHospitalId(String hospitalId) {
		List<HospIdAndAppSecretVo> list = null;
		try {
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_APPSECRET_BY_HOSPITALID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_APPSECRET_BY_HOSPITALID)), e);
			throw new SystemException(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_APPSECRET_BY_HOSPITALID)), e);
		}
		return list;
	}

	public List<HospIdAndAppSecretVo> findAllAppSecret() {
		List<HospIdAndAppSecretVo> list = null;
		try {
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_All_APPSECRET));
		} catch (Exception e) {
			logger.error(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_All_APPSECRET)), e);
			throw new SystemException(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_All_APPSECRET)), e);
		}
		return list;
	}

	@Override
	public String findAppIdByHospitalId(Map<String, Object> params) {
		try {
			logger.info((String) params.get("hospitalId"));
			logger.info((String) params.get("thirdType"));
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_APPID_BY_HOSPID), params);
		} catch (Exception e) {
			logger.error(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_APPID_BY_HOSPID)), e);
			throw new SystemException(String.format("根据医院ID数组查询医院集合信息出错!语句：%s", getSqlName(SQLNAME_FIND_APPID_BY_HOSPID)), e);
		}
	}

	@Override
	public List<CodeAndInterfaceVo> queryCodeAndInterfaceById(String hospitalId) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(hospitalId);
			return sqlSession.selectList(getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_BY_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("根据医院code与interfaceId信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_BY_ID)), e);
			throw new SystemException(String.format("根据医院code与interfaceId信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_INTERFACE_BY_ID)), e);
		}
	}

	public List<CodeAndInterfaceVo> queryAllCodeAndInterfaceIds() {
		try {
			return sqlSession.selectList(getSqlName(SQLNAME_QUERY_ALL_CODE_AND_INTERFACES));
		} catch (Exception e) {
			logger.error(String.format("根据医院code与interfaceId信息出错!语句：%s", getSqlName(SQLNAME_QUERY_ALL_CODE_AND_INTERFACES)), e);
			throw new SystemException(String.format("根据医院code与interfaceId信息出错!语句：%s", getSqlName(SQLNAME_QUERY_ALL_CODE_AND_INTERFACES)), e);
		}
	}

	@Override
	public Map<String, Object> isValidActivated(String hospitalId) {
		// TODO Auto-generated method stub
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			boolean isValid = true;
			Assert.notNull(hospitalId);

			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("hospitalId", "'" + hospitalId + "'");
			for (String tableName : BizConstant.ruleTableMap.keySet()) {
				paramMap.put("tableName", tableName);
				int count = sqlSession.selectOne(getSqlName(SQLNAME_QUERY_HAD_CONFIG_RULE), paramMap);
				if (count == 0) {
					isValid = false;
					resMap.put(BizConstant.METHOD_INVOKE_RES_MSG_KEY, "规则配置【" + BizConstant.ruleTableMap.get(tableName).concat("】未配置,该医院不能启用!"));
				}
				break;
			}
			resMap.put(BizConstant.METHOD_INVOKE_RES_ISSUCCESS_KEY, isValid);

		} catch (Exception e) {
			logger.error(String.format("根据医院code与interfaceId信息出错!语句：%s", getSqlName(SQLNAME_QUERY_HAD_CONFIG_RULE)), e);
			throw new SystemException(String.format("根据医院code与interfaceId信息出错!语句：%s", getSqlName(SQLNAME_QUERY_HAD_CONFIG_RULE)), e);
		}
		return resMap;
	}

	@Override
	public Hospital findHospitalByBranchHospitalCode(String code) {
		try {
			Assert.notNull(code);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_HOSPITAL_BY_BRANCH_HOSPITAL_CODE), code);
		} catch (Exception e) {
			logger.error(String.format("根据code查询医院  保证医院及分院code唯一的前置条件查询出错!语句：%s", getSqlName(SQLNAME_FIND_HOSPITAL_BY_BRANCH_HOSPITAL_CODE)), e);
			throw new SystemException(String.format("根据code查询医院  保证医院及分院code唯一的前置条件查询出错!语句：%s",
					getSqlName(SQLNAME_FIND_HOSPITAL_BY_BRANCH_HOSPITAL_CODE)), e);
		}
	}

	@Override
	public List<HospitalCodeAndAppVo> queryCodeAndAppByHospitalId(String hospitalId) {
		// TODO Auto-generated method stub
		List<HospitalCodeAndAppVo> vos = null;
		try {
			vos = sqlSession.selectList(getSqlName(SQLNAME_QUERY_CODE_AND_APP_BY_HOSPITAL_ID), hospitalId);
		} catch (Exception e) {
			logger.error(String.format("查询医院与接入平台信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_APP_BY_HOSPITAL_ID)), e);
			throw new SystemException(String.format("查询医院与接入平台信息出错!语句：%s", getSqlName(SQLNAME_QUERY_CODE_AND_APP_BY_HOSPITAL_ID)), e);
		}
		return vos;
	}

	@Override
	public void updateForRuleEditTime(Hospital hospital) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(hospital.getId());
			Assert.notNull(hospital.getRuleLastEditTime());
			sqlSession.update(getSqlName(SQLNAME_UPDATE_FOR_RULE_EDIT_TIME), hospital);
		} catch (Exception e) {
			logger.error(String.format("根据医院id更新规则最后修改时间出错!语句：%s", getSqlName(SQLNAME_UPDATE_FOR_RULE_EDIT_TIME)), e);
			throw new SystemException(String.format("根据code查询医院  保证医院及分院code唯一的前置条件查询出错!语句：%s",
					getSqlName(SQLNAME_FIND_HOSPITAL_BY_BRANCH_HOSPITAL_CODE)), e);
		}
	}

	/***
	 * 根据地区代码获取医院列表
	 */
	@Override
	public List<Hospital> getHospitalByAreaCode(String areaCode) {
		List<Hospital> hosps = null;
		try {
			hosps = sqlSession.selectList(getSqlName(SQLNAME_SELECT_HOSP_BY_AREACODE), areaCode);
		} catch (Exception e) {
			logger.error(String.format("根据地区代码获取医院列表出错!语句：%s", getSqlName(SQLNAME_SELECT_HOSP_BY_AREACODE)), e);
			throw new SystemException(String.format("根据地区代码获取医院列表出错!语句：%s", getSqlName(SQLNAME_SELECT_HOSP_BY_AREACODE)), e);
		}
		return hosps;
	}

	/***
	 * 根据地区代码获取启用的医院列表
	 */
	@Override
	public List<Hospital> getAvailableHospitalByAreaCode(String areaCode) {
		List<Hospital> hosps = null;
		try {
			hosps = sqlSession.selectList(getSqlName(SQLNAME_SELECT_AVAILABLE_HOSP_BY_AREACODE), areaCode);
		} catch (Exception e) {
			logger.error(String.format("根据地区代码获取医院列表出错!语句：%s", getSqlName(SQLNAME_SELECT_AVAILABLE_HOSP_BY_AREACODE)), e);
			throw new SystemException(String.format("根据地区代码获取医院列表出错!语句：%s", getSqlName(SQLNAME_SELECT_AVAILABLE_HOSP_BY_AREACODE)), e);
		}
		return hosps;
	}

	@Override
	public List<HospitalInfoByEasyHealthVo> queryHospitalAndOptionByAppCodeAndBizCode(Map<String, Object> params) {
		List<HospitalInfoByEasyHealthVo> list = null;
		try {
			list = sqlSession.selectList(getSqlName(SQLNAME_QUERY_HOSPITAL_AND_OPTION_BY_APPCODE_AND_BIZCODE), params);
		} catch (Exception e) {
			logger.error(String.format("根据appCode及功能code查询医院信息出错!语句：%s", getSqlName(SQLNAME_QUERY_HOSPITAL_AND_OPTION_BY_APPCODE_AND_BIZCODE)), e);
			throw new SystemException(String.format("根据appCode及功能code查询医院信息出错!语句：%s",
					getSqlName(SQLNAME_QUERY_HOSPITAL_AND_OPTION_BY_APPCODE_AND_BIZCODE)), e);
		}
		return list;
	}

	@Override
	public List<Hospital> findAllByStatus() {
		List<Hospital> hosps = null;
		try {
			hosps = sqlSession.selectList(getSqlName(SQLNAME_FIND_ALL_BY_STATUS), null);
		} catch (Exception e) {
			logger.error(String.format("查询所有医院列表出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_STATUS)), e);
			throw new SystemException(String.format("查询所有医院列表出错!语句：%s", getSqlName(SQLNAME_FIND_ALL_BY_STATUS)), e);
		}
		return hosps;
	}
}
