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

import com.yxw.commons.entity.platform.hospital.Optional;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.OptionalDao;
import com.yxw.platform.hospital.service.OptionalService;

/**
 * 后台功能选择管理 Service 实现类
 * 
 * @author homer.yang
 * @version 1.0
 * @date 2015年5月15日
 */
@Service(value = "optionalService")
public class OptionalServiceImpl extends BaseServiceImpl<Optional, String> implements OptionalService {

	@Autowired
	private OptionalDao optionalDao;

	@Override
	protected BaseDao<Optional, String> getDao() {
		return optionalDao;
	}

}
