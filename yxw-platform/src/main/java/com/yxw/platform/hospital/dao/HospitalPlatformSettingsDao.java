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
import java.util.Map;

import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台医院接入平台配置信息中间表 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface HospitalPlatformSettingsDao extends BaseDao<HospitalPlatformSettings, String> {

	public List<HospitalPlatformSettings> findByHospital(String hospitalId);

	public <K, V> Map<K, HospitalPlatformSettings> findMapByHospital(String hospitalId, String mapKey);

	/**
	 * 根据医院Id删除接入平台中间表信息
	 * @param hospitalId
	 */
	public void deleteByHospitalId(String hospitalId);

}
