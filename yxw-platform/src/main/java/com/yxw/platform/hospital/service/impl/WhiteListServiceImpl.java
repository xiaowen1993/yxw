/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.hospital.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.constants.biz.BizConstant;
import com.yxw.commons.entity.platform.hospital.WhiteList;
import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.WhiteListDao;
import com.yxw.platform.hospital.dao.WhiteListDetailDao;
import com.yxw.platform.hospital.service.WhiteListService;

/**
 * @Package: com.yxw.platform.hospital.service.impl
 * @ClassName: WhiteListServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "whiteListService")
public class WhiteListServiceImpl extends BaseServiceImpl<WhiteList, String> implements WhiteListService {
	@Autowired
	private WhiteListDao whiteListDao;
	@Autowired
	private WhiteListDetailDao whiteListDetailDao;

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.WhiteListService#findByHospitalIdAndPlatformType(java.lang.String, java.lang.String)
	 */
	@Override
	public WhiteList findByAppIdAndCode(String appId, String appCode) {
		return whiteListDao.findByAppIdAndCode(appId, appCode);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.WhiteListService#isWhiteList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isCantVisit(String appId, String appCode, String openId) {
		boolean isWhiteList = true;

		WhiteList whiteList = null;
		whiteList = this.findByAppIdAndCode(appId, appCode);
		if (whiteList != null && BizConstant.WHITE_LIST_IS_OPEN_YES == whiteList.getIsOpen()) {
			//未查到用户,该用户不能访问.
			WhiteListDetail whiteListDetail = whiteListDetailDao.findByWhiteListIdAndOpenId(whiteList.getId(), openId);
			if (whiteListDetail == null) {
				isWhiteList = false;
			}

		}

		return isWhiteList;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.hospital.service.WhiteListService#findHospitalId(java.lang.String, java.lang.String)
	 */
	@Override
	public WhiteList findHospitalId(String hospitalId, String platformType) {
		return whiteListDao.findHospitalId(hospitalId, platformType);
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<WhiteList, String> getDao() {
		return whiteListDao;
	}

}
