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

import com.yxw.commons.entity.platform.hospital.PlatformOptional;
import com.yxw.commons.vo.PlatformOptionalVo;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 后台医院已选功能管理 Service
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PlatformOptionalService extends BaseService<PlatformOptional, String> {

	/**
	 * 单个平台
	 * @param platformSettingsId
	 * @return
	 */
	public PlatformOptional findByPlatformSettingsId(String platformSettingsId);
	
	/**
	 * 通过医院，查询多个平台下的功能信息
	 * @param hospitalId
	 * @return
	 */
	public List<PlatformOptional> findByHospital(String hospitalId);
	
	public List<PlatformOptionalVo> findAllByHospitalId(String hospitalId);

	public void deleteByPlatformSettingsId(String platformSettingsId);

	public void batchSaveHospitalOptional(Map<String, List<PlatformOptional>> map);

}
