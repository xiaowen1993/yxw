/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-7-20</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.mobileapp.biz.usercenter.thread;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.usercenter.dao.MedicalCardDao;

/**
 * 
 * @Package: com.yxw.mobileapp.biz.medicalcard.thread
 * @ClassName: QueryHashTableCallable
 * @Statement: <p>根据参数查询绑卡</p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年9月28日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class QueryCardForParamsCallable implements Callable<List<MedicalCard>> {
	private MedicalCardDao medicalCardDao = SpringContextHolder.getBean(MedicalCardDao.class);

	private Map<String, Object> params;

	public QueryCardForParamsCallable(Map<String, Object> params) {
		super();
		this.params = params;
	}

	@Override
	public List<MedicalCard> call() throws Exception {
		List<MedicalCard> medicalCards = medicalCardDao.findCardForParams(params);
		return medicalCards;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
