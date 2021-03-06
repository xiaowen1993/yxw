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
package com.yxw.easyhealth.biz.smarttriage.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.smarttriage.Symptom;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.dao
 * @ClassName: SymptomDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface SymptomDao extends BaseDao<Symptom, String> {
	/**
	 * 根据症状名称查询症状
	 * @param params
	 * @return
	 */
	public abstract Symptom findSymptomByName(Map<String, Object> params);

	/**
	 * 根据疾病ID查询其关联的症状
	 * @param diseaseId
	 * @return
	 */
	public abstract List<Symptom> findSymptomByDiseaseId(String diseaseId);

	/**
	 * 查询是否显示的症状
	 * @param params
	 * @return
	 */
	public abstract List<Symptom> findAllByIsHide(Map<String, Object> params);
}
