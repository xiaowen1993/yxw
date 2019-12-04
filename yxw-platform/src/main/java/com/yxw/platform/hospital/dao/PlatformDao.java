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

import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台接入平台管理 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PlatformDao extends BaseDao<Platform, String> {

	public Map<String, Platform> findMapAll(String mapKey);

	public List<PlatformPaymentVo> findAllPlatformPayModes();

	public List<PlatformPaymentVo> findAllPlatformPayModesByHospitalId(String hospitalId);

	public List<PlatformPaymentVo> findAllByHospitalId(String hospitalId);
	
	public List<PlatformMsgModeVo> findAllPlatformMsgModes();

	public List<PlatformMsgModeVo> findAllPlatformMsgModesByHospitalId(String hospitalId);

	public List<PlatformMsgModeVo> findAllMsgModesByHospitalId(String hospitalId);

}
