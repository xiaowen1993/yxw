/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 申午武</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.msgpush.service.impl;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.base.datas.manager.BaseDatasManager;
import com.yxw.commons.constants.cache.CacheConstant;
import com.yxw.commons.entity.platform.msgpush.MsgCustomer;
import com.yxw.commons.vo.platform.hospital.HospIdAndAppSecretVo;
import com.yxw.commons.vo.platform.hospital.HospitalCodeAndAppVo;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.datas.manager.MsgTempManager;
import com.yxw.platform.msgpush.dao.MsgCustomerDao;
import com.yxw.platform.msgpush.service.MsgCustomerService;

/**
 * 客服消息service实现类
 * @Package: com.yxw.platform.msgpush.service.impl
 * @ClassName: MsgCustomerServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 申午武
 * @Create Date: 2015年6月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service
public class MsgCustomerServiceImpl extends BaseServiceImpl<MsgCustomer, String> implements MsgCustomerService {

	private static Logger logger = LoggerFactory.getLogger(MsgCustomerService.class);

	@Autowired
	private MsgCustomerDao msgCustomerDao;
	@Autowired
	private BaseDatasManager baseDatasManager;
	@Autowired
	private MsgTempManager msgManager;

	@Override
	protected BaseDao<MsgCustomer, String> getDao() {
		return this.msgCustomerDao;
	}

	@Override
	public String add(MsgCustomer entity) {
		/*HospitalCodeAndAppVo hospitalCodeAndAppVo =
				baseDatasManager.getAppInfoByHospitalId(entity.getHospitalId(), entity.getSource() == 1 ? CacheConstant.CACHE_PAY_TYPE_WEIXIN
						: CacheConstant.CACHE_PAY_TYPE_ALIPAY);*/
		// 新平台 使用APP
		String appCode = "";

		switch (entity.getSource()) {
		case 1:
			appCode = CacheConstant.CACHE_PAY_TYPE_WEIXIN;
			break;
		case 2:
			appCode = CacheConstant.CACHE_PAY_TYPE_ALIPAY;
			break;
		case 3:
			appCode = CacheConstant.CACHE_PAY_TYPE_APP;
			break;

		default:
			logger.error("无效的source. source: {}.", entity.getSource());
			break;
		}

		HospIdAndAppSecretVo vo = baseDatasManager.getHospitalEasyHealthAppInfo(entity.getHospitalId(), appCode);
		entity.setAppId(vo.getAppId());
		entity.setType(1);
		msgCustomerDao.add(entity);
		msgManager.updateMsgCustomer(entity);
		return entity.getId();
	}

	@Override
	public void update(MsgCustomer entity) {
		msgCustomerDao.update(entity);
		msgManager.updateMsgCustomer(entity);
	}

	@Override
	public boolean check(Map<String, Serializable> params) {
		HospitalCodeAndAppVo hospitalCodeAndAppVo =
				baseDatasManager.getAppInfoByHospitalId(String.valueOf(params.get("hospitalId")),
						Integer.valueOf(String.valueOf(params.get("source"))) == 1 ? CacheConstant.CACHE_PAY_TYPE_WEIXIN
								: CacheConstant.CACHE_PAY_TYPE_ALIPAY);
		return msgManager
				.existsMsgCustomer(hospitalCodeAndAppVo.getAppId(), String.valueOf(params.get("code")), String.valueOf(params.get("source")));
		//params.put("appId", hospitalCodeAndAppVo.getAppId());
		//return msgCustomerDao.check(params);
	}

}
