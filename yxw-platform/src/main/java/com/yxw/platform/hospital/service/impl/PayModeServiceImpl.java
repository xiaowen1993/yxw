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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.hospital.PayMode;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.PayModeDao;
import com.yxw.platform.hospital.service.PayModeService;

/**
 * 后台支付方式配置信息管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "payModeService")
public class PayModeServiceImpl extends BaseServiceImpl<PayMode, String> implements PayModeService {

	@Autowired
    private PayModeDao payModeDao;
	
	@Override
	protected BaseDao<PayMode, String> getDao() {
		return payModeDao;
	}

    
}
