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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.WhiteList;
import com.yxw.commons.entity.platform.hospital.WhiteListDetail;
import com.yxw.commons.vo.cache.WhiteListVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.hospital.dao.WhiteListDao;
import com.yxw.platform.hospital.dao.WhiteListDetailDao;
import com.yxw.platform.hospital.service.WhiteListDetailService;

/**
 * @Package: com.yxw.platform.hospital.service.impl
 * @ClassName: WhiteListServiceDetailImpl
 * @Statement: <p>
 *             </p>
 * @JDK version used:
 * @Author: 黄忠英
 * @Create Date: 2015年8月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "whiteListDetailService")
public class WhiteListServiceDetailImpl extends BaseServiceImpl<WhiteListDetail, String> implements WhiteListDetailService {

	private static Logger logger = LoggerFactory.getLogger(WhiteListServiceDetailImpl.class);

	@Autowired
	private WhiteListDetailDao whiteListDetailDao;

	@Autowired
	private WhiteListDao whiteListDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yxw.platform.hospital.service.WhiteListDetailService#
	 * findByWhiteListIdAndPhone(java.lang.String, java.lang.String)
	 */
	@Override
	public WhiteListDetail findByWhiteListIdAndPhone(String whiteListId, String phone) {
		return whiteListDetailDao.findByWhiteListIdAndPhone(whiteListId, phone);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#getDao()
	 */
	@Override
	protected BaseDao<WhiteListDetail, String> getDao() {
		return whiteListDetailDao;
	}

	/***
	 * 根据手机号码判断是否加入白名单
	 * 
	 * @param appId
	 * @param phone
	 * @param platformType
	 */
	@Override
	public boolean whetherAddWhiteList(String appId, String phone, String appCode) {
		WhiteList whiteList = whiteListDao.findByAppIdAndCode(appId, appCode);
		if (whiteList != null && whiteList.getIsOpen().equals("1")) {
			WhiteListDetail whiteListDetail = findByWhiteListIdAndPhone(whiteList.getId(), phone);
			// 判断该手机号是否对应的openId
			if (whiteListDetail != null && whiteListDetail.getId() != null && whiteListDetail.getOpenId() != null
					&& !"".equals(whiteListDetail.getOpenId())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/***
	 * 更新白名单上某手机号码对应的OpenId
	 * 
	 * @param appId
	 * @param platformType
	 * @param phone
	 * @param openId
	 */
	@Override
	public Boolean updateWhiteListOpenId(String appId, String platformType, String phone, String openId) {
		Boolean isSuccessBoolean = true;
		try {
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("openId", openId);
			paraMap.put("appId", appId);
			paraMap.put("appCode", platformType);
			paraMap.put("phone", phone);
			whiteListDetailDao.updateOpenIdByPhone(paraMap);

			// WhiteListCache whiteListCache = SpringContextHolder.getBean(WhiteListCache.class);
			WhiteListVo vo = new WhiteListVo();
			vo.setAppCode(platformType);
			vo.setAppId(appId);
			vo.setOpenId(openId);
			// whiteListCache.addWhiteInfo(vo);
			ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
			List<Object> params = new ArrayList<Object>();
			params.add(vo);
			serveComm.set(CacheType.WHITE_LIST_CACHE, "addWhiteInfo", params);
		} catch (Exception e) {
			logger.error("出错！更新白名单上某手机号码对应的OpenId---updateWhiteListOpenId");
			e.printStackTrace();
			isSuccessBoolean = false;
		}
		return isSuccessBoolean;
	}

	@Override
	public List<WhiteListDetail> findByWhiteListId(String whiteListId) {
		// TODO Auto-generated method stub
		List<WhiteListDetail> details = whiteListDetailDao.findByWhiteListId(whiteListId);
		if (details == null) {
			details = new ArrayList<WhiteListDetail>();
		}
		return details;
	}

	@Override
	public Boolean isCheckUniquePhone(String whiteListId, String phone) {
		// TODO Auto-generated method stub
		WhiteListDetail detail = whiteListDetailDao.findByWhiteListIdAndPhone(whiteListId, phone);
		if (detail == null) {
			return true;
		} else {
			return false;
		}
	}

}
