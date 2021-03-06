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
package com.yxw.platform.hospital.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.vo.cache.CodeAndInterfaceVo;
import com.yxw.commons.vo.cache.HospitalInfoByEasyHealthVo;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeInterfaceAndAppVo;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 后台医院管理 Service
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface HospitalService extends BaseService<Hospital, String> {

	public void updateStatus(Hospital hospital);

	public Hospital findHospitalByCode(String code);

	public List<Hospital> findHospitalByCodes(List<String> codes);

	public Hospital findHospitalByName(String name);

	public boolean isUniqueCode(Hospital hospital);

	public boolean isUniqueName(Hospital hospital);

	public Hospital findHospitalByCodeAndID(Hospital hospital);

	public Hospital findHospitalByNameAndID(Hospital hospital);

	/**
	 * 更新规则的最后修改时间
	 * 
	 * @param hospital
	 */
	public void updateForRuleEditTime(Hospital hospital);

	/**
	 * 有分院配置的医院列表
	 * 
	 * @param params
	 * @param page
	 * @return
	 */
	public PageInfo<Hospital> findHadBranchByPage(Map<String, Object> params, Page<Hospital> page);

	/**
	 * 发布医院的规则配置
	 * 
	 * @param hospital
	 */
	public void publishRule(Hospital hospital);

	/**
	 * 查询所有医院与App的对应关系
	 * 
	 * @return
	 */
	public List<HospitalCodeAndAppVo> queryCodeAndApp();

	/**
	 * 查询医院与App的对应关系,增加接口ID及分院code
	 * 
	 * @return
	 */
	public List<HospitalCodeInterfaceAndAppVo> queryCodeAndInterfaceIdAndApp();

	/**
	 * 根据appId查询医院与App的对应关系,增加接口ID及分院code
	 * 
	 * @param AppId
	 * @return
	 */
	public List<HospitalCodeInterfaceAndAppVo> queryCodeAndInterfaceIdAndAppByAppId(String appId);

	/**
	 * 查询hospitalId 对应的医院与App对应关系
	 * 
	 * @param hospitalId
	 * @return
	 */
	public List<HospitalCodeAndAppVo> queryCodeAndAppByHospitalId(String hospitalId);

	/**
	 * 根据医院ID数组查询医院集合
	 * 
	 * @param hospitalIds
	 * @return
	 */
	public List<Hospital> findByHospitalIds(String[] hospitalIds);

	/**
	 * 根据appId查询医院Secret tonken
	 * */
	public HospIdAndAppSecretVo findAppSecretByAppId(Map<String, Object> params);

	/**
	 * 根据所有医院Secret tonken
	 * */
	public List<HospIdAndAppSecretVo> findAllAppSecret();

	/**
	 * 根据hospitalId 查询Secret tonken
	 * */
	public List<HospIdAndAppSecretVo> findAppSecretByHospitalId(String hospitalId);

	/**
	 * 
	 * 根据医院ID查询APPID
	 * */
	public String findAppIdByHospitalId(Map<String, Object> params);

	/**
	 * 查询指定医院与接口的配置
	 * 
	 * @param hospitalId
	 * @return
	 */
	public List<CodeAndInterfaceVo> queryCodeAndInterfaceById(String hospitalId);

	/**
	 * 查询系统所有的医院与接口的配置
	 * 
	 * @return
	 */
	public List<CodeAndInterfaceVo> queryAllCodeAndInterfaceIds();

	/**
	 * 医院是否可能启用
	 * 
	 * @param hospitalId
	 * @return
	 */
	public Map<String, Object> isValidActivated(String hospitalId);

	/**
	 * 根据code查询医院 保证医院及分院code唯一的前置条件查询
	 * 
	 * @param code
	 *            查询条件来源于分院code
	 * @return
	 */
	public Hospital findHospitalByBranchHospitalCode(String code);

	public void updateRuleModifyTime(Hospital entity);

	public List<Hospital> getHospitalByAreaCode(String areaCode);

	/**
	 * 根据appCode及功能code查询医院信息
	 * 
	 * @param params
	 * @return
	 */
	public List<HospitalInfoByEasyHealthVo> queryHospitalAndOptionByAppCodeAndBizCode(Map<String, Object> params);

	/**
	 * 根据地区代码获取医院列表
	 * 
	 * @param params
	 * @return
	 */
	public List<Hospital> getAvailableHospitalByAreaCode(String areaCode);
	
	/**
	 * 查询所有医院列表
	 * @return
	 */
	public List<Hospital> findAllByStatus();
}
