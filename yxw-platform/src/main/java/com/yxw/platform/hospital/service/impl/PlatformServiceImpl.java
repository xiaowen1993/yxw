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
package com.yxw.platform.hospital.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.hospital.Platform;
import com.yxw.commons.vo.platform.PlatformMsgModeVo;
import com.yxw.commons.vo.platform.PlatformPaymentVo;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.PlatformDao;
import com.yxw.platform.hospital.service.PlatformService;

/**
 * 后台接入平台管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "platformService")
public class PlatformServiceImpl extends BaseServiceImpl<Platform, String> implements PlatformService {

	@Autowired
	private PlatformDao platformDao;

	@Override
	protected BaseDao<Platform, String> getDao() {
		return platformDao;
	}

	@Override
	public Map<String, Platform> findMapAll(String mapKey) {
		return platformDao.findMapAll(mapKey);
	}

	@Override
	public List<PlatformPaymentVo> findAllPlatformPayModes() {
		return platformDao.findAllPlatformPayModes();
	}

	@Override
	public List<PlatformPaymentVo> findAllPlatformPayModesByHospitalId(String hospitalId) {
		return platformDao.findAllPlatformPayModesByHospitalId(hospitalId);
	}

	@Override
	public List<PlatformPaymentVo> findByHospitalId(String hospitalId) {
		return platformDao.findAllByHospitalId(hospitalId);
	}

	@Override
	public List<PlatformMsgModeVo> findAllPlatformMsgModes() {
		return platformDao.findAllPlatformMsgModes();
	}

	@Override
	public List<PlatformMsgModeVo> findAllPlatformMsgModesByHospitalId(String hospitalId) {
		return platformDao.findAllPlatformMsgModesByHospitalId(hospitalId);
	}

	@Override
	public List<PlatformMsgModeVo> findMsgModesByHospitalId(String hospitalId) {
		return platformDao.findAllMsgModesByHospitalId(hospitalId);
	}
}
