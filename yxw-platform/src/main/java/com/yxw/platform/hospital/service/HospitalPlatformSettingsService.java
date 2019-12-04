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

import com.yxw.commons.entity.platform.hospital.HospitalPlatformSettings;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 后台接入平台配置信息管理 Service
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface HospitalPlatformSettingsService extends BaseService<HospitalPlatformSettings, String> {

	public List<HospitalPlatformSettings> findByHospital(String hospitalId);

	public <K, V> Map<K, HospitalPlatformSettings> findMapByHospital(String hospitalId, String mapKey);

}
