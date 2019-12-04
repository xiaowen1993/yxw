/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-4</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.easyhealth.biz.register.service.HadRegDoctorService;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.mobileapp.biz.register.service.impl
 * @ClassName: HadRegDoctorServiceImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "hadRegDoctorService")
public class HadRegDoctorServiceImpl extends BaseDaoImpl<HadRegDoctor, String> implements HadRegDoctorService {
	
	private static Logger logger = LoggerFactory.getLogger(HadRegDoctorServiceImpl.class);
	
	private final static String SQLNAME_FIND_BY_OPEN_ID = "findByOpenId";

	@Override
	public void saveAndRemoveDoctor(HadRegDoctor addDoctor, HadRegDoctor removeDoctor) {
		// TODO Auto-generated method stub
		if (removeDoctor != null) {
			deleteById(removeDoctor.getId());
		}
		if (addDoctor != null) {
			add(addDoctor);
		}
	}

	@Override
	public List<HadRegDoctor> findByOpenId(String openId) {
		List<HadRegDoctor> list = null;
		try {
			Assert.notNull(openId, "OpenId不能为空！");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("openId", openId);
			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_OPEN_ID), paramMap);
		} catch (Exception e) {
			logger.error(String.format("通过OpenId查找曾挂号医生！语句：%s", getSqlName(SQLNAME_FIND_BY_OPEN_ID)), e);
			throw new SystemException(String.format("通过OpenId查找曾挂号医生！语句：%s", getSqlName(SQLNAME_FIND_BY_OPEN_ID)), e);
		}
		
		return list;
	}

}
