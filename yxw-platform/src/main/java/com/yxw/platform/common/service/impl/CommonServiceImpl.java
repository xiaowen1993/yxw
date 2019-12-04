/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-8-12</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.common.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.platform.common.service.CommonService;
import com.yxw.platform.hospital.dao.WhiteListDetailDao;

/**
 * @Package: com.yxw.platform.common.service.impl
 * @ClassName: CommonServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-8-12
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value="commonService")
public class CommonServiceImpl implements CommonService {
	private WhiteListDetailDao whiteListDetailDao = SpringContextHolder.getBean(WhiteListDetailDao.class);

	public List<WhiteListVo> findAllWhiteDetails() {
		return whiteListDetailDao.findAllWhiteInfo();
	}

	@Override
	public List<WhiteListVo> findWhiteDetailsByApp(String appId, String appCode) {
		// TODO Auto-generated method stub
		return whiteListDetailDao.findWhiteDetailsByApp(appId, appCode);
	}

}
