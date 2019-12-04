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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.hospital.PaySettings;
import com.yxw.framework.mvc.service.BaseService;

/**
 * 后台支付方式配置信息管理 Service
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PaySettingsService extends BaseService<PaySettings, String> {
	/**
	 * 根据医院查询支付配置
	 * @param hospitalId
	 * @return
	 */
	public List<PaySettings> findByHospitalIdAndModeIds(String hospitalId, String[] payModeIds);

	/**
	 * 保存/更新支付属性对象和关联医院中间表
	 * @param hospitalId
	 * @return
	 */
	public void batchSavePaySettings(List<PaySettings> paySettingsList, LinkedList<Map<String, String>> platformPaySettingsAdd,
			LinkedList<Map<String, String>> platformPaySettingsDelete);

	/**
	 * 根据医院及Code查询是否是特约商户
	 * @param hospitalId
	 * @param code
	 * @return
	 */
	public PaySettings findByHospitalIdAndModeCode(String hospitalId, String code);

	/** 
	 * 根据平台查询支付配置
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年5月26日  
	 */
	public PaySettings findByPlatformSettingsIdAndPayModeId(String platformSettingsId, String payModeId);
}
