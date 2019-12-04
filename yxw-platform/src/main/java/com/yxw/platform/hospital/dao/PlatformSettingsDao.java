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

import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台接入平台配置信息管理 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PlatformSettingsDao extends BaseDao<PlatformSettings, String> {

	/**
	 * 根据医院及接入平台配置ID查询平台配置 集合
	 * @param hospitalId
	 * @return
	 */
	public List<PlatformSettings> findByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds);

	/**
	 * 根据医院及接入平台配置ID查询平台配置 确定接入平台
	 * @param hospitalId
	 * @return
	 */
	public PlatformSettings findByHospitalIdAndPlatformId(String hospitalId, String platformId);

	/**
	 * 根据医院及接入平台配置ID查询平台配置 确定接入平台
	 * @param hospitalId
	 * @return
	 */
	public PlatformSettings findByHospitalIdAndAppCode(String hospitalId, String appCode);

	/**
	 * 根据apId查询平台配置
	 * @param hospitalId
	 * @return
	 */
	public PlatformSettings findByAppId(String appId);

	/**
	 * 根据privateKey查询平台配置
	 * @param hospitalId
	 * @return
	 */
	public PlatformSettings findByPrivateKey(String privateKey);

	/**
	 * 根据publicKey查询平台配置
	 * @param hospitalId
	 * @return
	 */
	public PlatformSettings findByPublicKey(String publicKey);

	/** 
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年3月27日 
	 * @return 
	 */

	public List<PlatformSettings> findAllPlatformRelations();
}
