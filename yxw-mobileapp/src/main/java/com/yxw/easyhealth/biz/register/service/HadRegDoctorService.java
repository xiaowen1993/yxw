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
package com.yxw.easyhealth.biz.register.service;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.register.HadRegDoctor;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.mobileapp.biz.register.service
 * @ClassName: HadRegDoctorService
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-7-4
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface HadRegDoctorService extends BaseService<HadRegDoctor, String> {
	public List<HadRegDoctor> findAll();

	/**
	 * 添加和删除增挂号医生
	 * @param addDoctor
	 * @param removeDoctor
	 */
	public void saveAndRemoveDoctor(HadRegDoctor addDoctor, HadRegDoctor removeDoctor);
	
	public List<HadRegDoctor> findByOpenId(String openId);
}
