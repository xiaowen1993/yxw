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

import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 后台分院管理 Service
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface BranchHospitalService extends BaseService<BranchHospital, String> {

	public BranchHospital findBranchHospitalByCode(String code);

	public List<BranchHospital> selectBranchHospitalsByHospitalId(String hospitalId);

	public void batchSaveBranchHospital(List<BranchHospital> branchHospitals);

	/**
	 * 根据code及医院id查询分院
	 * 
	 * @param code
	 * @return
	 */
	public BranchHospital findHospitalByCodeForHospitalId(BranchHospital branchHospital);

	/**
	 * 根据name及医院id查询分院
	 * 
	 * @param code
	 * @return
	 */
	public BranchHospital findHospitalByNameForHospitalId(BranchHospital branchHospital);

	/**
	 * 根据interfaceId查询分院
	 * 
	 * @param code
	 * @return
	 */
	public BranchHospital findHospitalByInterfaceId(BranchHospital branchHospital);

	/**
	 * 检查分院code在当前医院中是否存在
	 * 
	 * @param branchHospital
	 * @return
	 */
	public boolean isUniqueCodeForHospitalId(BranchHospital branchHospital);

	/**
	 * 检查分院name在当前医院中是否存在
	 * 
	 * @param branchHospital
	 * @return
	 */
	public boolean isUniqueNameForHospitalId(BranchHospital branchHospital);

	/**
	 * 检查分院interfaceId是否存在 全局检查，检查所有医院，必须保证唯一
	 * 
	 * @param branchHospital
	 * @return
	 */
	public boolean isUniqueInterFace(BranchHospital branchHospital);

	/**
	 * 根据医院id 查询分院列表
	 * @param hospitalId
	 * @return
	 */
	public List<BranchHospital> findByHospitalId(String hospitalId);

	/**
	 * 根据code查询分院  保证医院及分院code唯一的前置条件查询
	 * @param code 查询条件来源于医院code
	 * @return
	 */
	public BranchHospital findBranchHospitalByHospitalCode(String code);
}
