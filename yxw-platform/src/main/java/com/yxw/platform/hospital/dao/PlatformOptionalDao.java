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

import com.yxw.commons.entity.platform.hospital.PlatformOptional;
import com.yxw.commons.vo.PlatformOptionalVo;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台医院已选功能中间表 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PlatformOptionalDao extends BaseDao<PlatformOptional, String> {

	public List<PlatformOptional> findByHospital(String hospitalId);

	public void deleteByPlatformSettingsId(String platformSettingsId);
	
	public PlatformOptional findByPlatformSettingsId(String platformSettingsId);

	public List<PlatformOptionalVo> findAllByHospitalId(String hospitalId);
}
