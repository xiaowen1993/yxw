/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年4月28日</p>
 *  <p> Created by 周鉴斌</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.smarttriage.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.smarttriage.Disease;
import com.yxw.framework.mvc.service.BaseService;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.service
 * @ClassName: DiseaseService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface DiseaseService extends BaseService<Disease, String> {
	/**
	 * 根据疾病名称查询疾病
	 * @param params
	 * @return
	 */
	public abstract Disease findDiseaseByName(Map<String, Object> params);

	/**
	 * 检查疾病名称是否存在
	 * @param disease
	 * @return
	 */
	public abstract boolean checkDisease(Disease disease);

	/**
	 * 保存疾病
	 * @param disease
	 */
	public abstract void save(Disease disease, String symptoms);

	/**
	 * 根据症状ID集合查询疾病
	 * @param params
	 * @return
	 */
	public abstract List<Disease> findDiseaseBySymptomIds(Map<String, Object> params);
}
