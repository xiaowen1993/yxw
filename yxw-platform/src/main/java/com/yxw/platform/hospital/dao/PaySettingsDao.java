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

import com.yxw.commons.entity.platform.hospital.PaySettings;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * 后台支付方式配置信息管理 Dao
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
public interface PaySettingsDao extends BaseDao<PaySettings, String> {

	/**
	 * 根据医院及支付方式ID查询支付配置
	 * @param hospitalId
	 * @return
	 */
	public List<PaySettings> findByHospitalIdAndModeIds(String hospitalId, String[] payModeIds);

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
	 * @param platformSettingsId
	 * @param payModeId
	 * @return 
	 */

	public PaySettings findByPlatformSettingsIdAndPayModeId(String platformSettingsId, String payModeId);
}
