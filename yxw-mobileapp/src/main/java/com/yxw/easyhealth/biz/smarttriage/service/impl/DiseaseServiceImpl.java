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
package com.yxw.easyhealth.biz.smarttriage.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.yxw.commons.entity.mobile.biz.smarttriage.Disease;
import com.yxw.commons.entity.mobile.biz.smarttriage.SymptomDisease;
import com.yxw.easyhealth.biz.smarttriage.dao.DiseaseDao;
import com.yxw.easyhealth.biz.smarttriage.dao.SymptomDiseaseDao;
import com.yxw.easyhealth.biz.smarttriage.service.DiseaseService;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;

/**
 * @Package: com.yxw.easyhealth.biz.smarttriage.dao.impl
 * @ClassName: DiseaseDaoImpl
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author:	周鉴斌
 * @Create Date: 2015年10月22日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Service(value = "diseaseService")
public class DiseaseServiceImpl extends BaseServiceImpl<Disease, String> implements DiseaseService {

	private static Logger logger = LoggerFactory.getLogger(DiseaseServiceImpl.class);

	@Autowired
	private DiseaseDao diseaseDao;

	@Autowired
	private SymptomDiseaseDao symptomDiseaseDao;

	@Override
	protected BaseDao<Disease, String> getDao() {
		return diseaseDao;
	}

	@Override
	public Disease findDiseaseByName(Map<String, Object> params) {
		return diseaseDao.findDiseaseByName(params);
	}

	@Override
	public boolean checkDisease(Disease disease) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", disease.getName());
		Disease entity = diseaseDao.findDiseaseByName(params);
		if (entity == null) {
			return true;
		} else {
			return disease.getId().equals(entity.getId());
		}
	}

	@Override
	public void save(Disease disease, String symptoms) {
		List<SymptomDisease> sdList = new ArrayList<SymptomDisease>();
		String diseaseId = "";
		if (StringUtils.isBlank(disease.getId())) {
			diseaseId = diseaseDao.add(disease);
		} else {
			diseaseDao.update(disease);
			diseaseId = disease.getId();
		}

		symptomDiseaseDao.deleteByDiseaseId(diseaseId);

		if (StringUtils.isNotBlank(symptoms)) {
			String[] str = symptoms.split(",");
			for (String string : str) {
				SymptomDisease sd = new SymptomDisease(string, diseaseId);
				sdList.add(sd);
			}
			symptomDiseaseDao.batchInsert(sdList);
		}
		logger.info("disease:{}, sdList:{}", JSON.toJSONString(disease), JSON.toJSONString(sdList));
	}

	@Override
	public List<Disease> findDiseaseBySymptomIds(Map<String, Object> params) {
		return diseaseDao.findDiseaseBySymptomIds(params);
	}
}
