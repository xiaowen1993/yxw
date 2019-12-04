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

import com.yxw.commons.entity.platform.hospital.PlatformSettings;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 菜单及平台配置信息管理  Service
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PlatformSettingsService extends BaseService<PlatformSettings, String> {

	public void batchSavePlatformSettings(List<PlatformSettings> platformSettings, String hospitalId);

	/**
	 * 根据医院及介入平台ID查询平台配置
	 * @param platformSettings
	 * @return
	 */
	public List<PlatformSettings> findByHospitalIdAndPlatformIds(String hospitalId, String[] platformIds);

	/**
	 * 根据医院及介入平台ID查询平台配置
	 * @param platformSettings
	 * @return
	 */
	public PlatformSettings findByHospitalIdAndPlatformId(String hospitalId, String platformId);

	/**
	 * 初始化使用
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年3月27日 
	 * @return
	 */
	public List<PlatformSettings> findAllPlatformRelations();

	/**
	 * 根据医院及介入平台ID查询平台配置
	 * @param platformSettings
	 * @return
	 */
	public PlatformSettings findByHospitalIdAndAppCode(String hospitalId, String appCode);

	/**
	 * 根据appId查询平台配置
	 * @param platformSettings
	 * @return
	 */
	public PlatformSettings findByAppId(String appId);

	/**
	 * 根据privateKey查询平台配置
	 * @param platformSettings
	 * @return
	 */
	public PlatformSettings findByPrivateKey(String privateKey);

	/**
	 * 根据publicKey查询平台配置
	 * @param platformSettings
	 * @return
	 */
	public PlatformSettings findByPublicKey(String publicKey);

	/**
	 * 检查接入平台appId是否存在
	 * 
	 * @param platformSettings
	 * @return
	 */
	public boolean isUniqueAppIdForPlatformSettings(PlatformSettings platformSettings);

	/**
	 * 检查接入平台PrivateKey是否存在
	 * 
	 * @param platformSettings
	 * @return
	 */
	public boolean isUniquePrivateKeyForPlatformSettings(PlatformSettings platformSettings);

	/**
	 * 检查接入平台PublicKey是否存在
	 * @param platformSettings
	 * @return
	 */
	public boolean isUniquePublicKeyForPlatformSettings(PlatformSettings platformSettings);
}
