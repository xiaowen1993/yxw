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

import com.yxw.commons.entity.mobile.biz.smarttriage.SymptomDisease;
import com.yxw.framework.mvc.dao.BaseDao;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.dao.impl
 * @ClassName: SymptomDiseaseDao
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface SymptomDiseaseDao extends BaseDao<SymptomDisease, String> {
	/**
	 * 根据疾病ID删除关联关系
	 * @param diseaseId
	 */
	public abstract void deleteByDiseaseId(String diseaseId);
}