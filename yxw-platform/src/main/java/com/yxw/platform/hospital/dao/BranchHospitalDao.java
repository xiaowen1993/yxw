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
package com.yxw.platform.hospital.dao;

import java.util.List;

import com.yxw.commons.entity.platform.hospital.BranchHospital;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台分院管理 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface BranchHospitalDao extends BaseDao<BranchHospital, String> {

	public BranchHospital findBranchHospitalByCode(String code);

	public List<BranchHospital> selectBranchHospitalsByHospitalId(String hospitalId);

	/**
	 * 根据code及医院id查询分院
	 * @param code
	 * @return
	 */
	public BranchHospital findHospitalByCodeForHospitalId(BranchHospital branchHospital);

	/**
	 * 根据name及医院id查询分院 
	 * @param code
	 * @return
	 */
	public BranchHospital findHospitalByNameForHospitalId(BranchHospital branchHospital);

	/**
	 * 根据interfaceId查询分院
	 * @param code
	 * @return
	 */
	public BranchHospital findHospitalByInterfaceId(BranchHospital branchHospital);

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
