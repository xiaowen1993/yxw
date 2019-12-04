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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yxw.commons.entity.platform.hospital.PaySettings;
import com.yxw.commons.entity.platform.hospital.PlatformPaySettings;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.PaySettingsDao;
import com.yxw.platform.hospital.dao.PlatformPaySettingsDao;
import com.yxw.platform.hospital.service.PaySettingsService;

/**
 * 后台支付方式配置信息管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "paySettingsService")
public class PaySettingsServiceImpl extends BaseServiceImpl<PaySettings, String> implements PaySettingsService {

	private static Logger logger = Logger.getLogger(PaySettingsServiceImpl.class);

	@Autowired
	private PaySettingsDao paySettingsDao;

	@Autowired
	private PlatformPaySettingsDao platformPaySettingsDao;

	@Override
	protected BaseDao<PaySettings, String> getDao() {
		return paySettingsDao;
	}

	@Override
	public List<PaySettings> findByHospitalIdAndModeIds(String hospitalId, String[] payModeIds) {
		return paySettingsDao.findByHospitalIdAndModeIds(hospitalId, payModeIds);
	}

	@Override
	public void batchSavePaySettings(List<PaySettings> paySettingsList, LinkedList<Map<String, String>> platformPaySettingslsAdd,
			LinkedList<Map<String, String>> platformPaySettingslsDelete) {
		// 保存接入平台配置信息
		for (int i = 0; i < paySettingsList.size(); i++) {
			String pk = "";

			PaySettings paySettings = paySettingsList.get(i);
			if (paySettings != null && paySettings.getId() != null) {
				PaySettings check = paySettingsDao.findById(paySettings.getId());
				if (check == null) {
					pk = paySettingsDao.add(paySettings);
					logger.info("新增paySettings： " + JSONObject.toJSONString(paySettings));
				} else {
					paySettingsDao.update(paySettings);
					logger.info("更新paySettings： " + JSONObject.toJSONString(paySettings));

				}
			} else {
				pk = paySettingsDao.add(paySettings);
				logger.info("新增paySettings： " + JSONObject.toJSONString(paySettings));
			}

			if (!StringUtils.isBlank(pk)) {
				platformPaySettingslsAdd.get(i).put("paySettingsId", pk);
			}

		}

		// 保存医院和接入平台配置信息中间表
		for (Map<String, String> platformPaySettingsMap : platformPaySettingslsDelete) {
			if (StringUtils.equals(platformPaySettingsMap.get("status"), "0")) {
				if (!StringUtils.isBlank(platformPaySettingsMap.get("paySettingsId"))
						&& !StringUtils.isBlank(platformPaySettingsMap.get("platformSettingsId"))) {
					platformPaySettingsDao.deletePlatformPaySettingsByPlatFormId(platformPaySettingsMap.get("platformSettingsId"),
							platformPaySettingsMap.get("paySettingsId"));
					paySettingsDao.deleteById(platformPaySettingsMap.get("paySettingsId"));
				}
			}
		}

		for (Map<String, String> platformPaySettingsMap : platformPaySettingslsAdd) {

			PlatformPaySettings platformPaySettings = new PlatformPaySettings();
			platformPaySettings.setPlatformSettingsId(platformPaySettingsMap.get("platformSettingsId"));
			platformPaySettings.setPaySettingsId(platformPaySettingsMap.get("paySettingsId"));
			platformPaySettings.setStatus(1);

			PlatformPaySettings platformPaySetting =
					platformPaySettingsDao.findByPlatformSettingsIdAndPaySettingsId(platformPaySettings.getPlatformSettingsId(),
							platformPaySettings.getPaySettingsId());

			if (platformPaySetting == null) {
				logger.info("新增platformPaySettings： " + JSONObject.toJSONString(platformPaySettings));
				platformPaySettingsDao.add(platformPaySettings);
			}
		}
	}

	@Override
	public PaySettings findByHospitalIdAndModeCode(String hospitalId, String code) {
		return paySettingsDao.findByHospitalIdAndModeCode(hospitalId, code);
	}

	@Override
	public PaySettings findByPlatformSettingsIdAndPayModeId(String platformSettingsId, String payModeId) {
		return paySettingsDao.findByPlatformSettingsIdAndPayModeId(platformSettingsId, payModeId);
	}
}
