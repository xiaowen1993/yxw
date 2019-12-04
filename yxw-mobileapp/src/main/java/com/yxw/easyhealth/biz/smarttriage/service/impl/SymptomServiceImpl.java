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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.mobile.biz.smarttriage.Symptom;
import com.yxw.easyhealth.biz.smarttriage.dao.SymptomDao;
import com.yxw.easyhealth.biz.smarttriage.service.SymptomService;
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
@Service(value = "symptomService")
public class SymptomServiceImpl extends BaseServiceImpl<Symptom, String> implements SymptomService {

	@Autowired
	private SymptomDao symptomDao;

	@Override
	protected BaseDao<Symptom, String> getDao() {
		return symptomDao;
	}

	@Override
	public Symptom findSymptomByName(Map<String, Object> params) {
		return symptomDao.findSymptomByName(params);
	}

	@Override
	public void save(Symptom symptom) {
		if (StringUtils.isBlank(symptom.getId())) {
			symptomDao.add(symptom);
		} else {
			symptomDao.update(symptom);
		}
	}

	@Override
	public boolean checkSymtomName(Symptom symptom) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", symptom.getName());
		Symptom entity = symptomDao.findSymptomByName(params);
		if (entity == null) {
			return true;
		} else {
			return symptom.getId().equals(entity.getId());
		}
	}

	@Override
	public List<Symptom> findSymptomByDiseaseId(String diseaseId) {
		return symptomDao.findSymptomByDiseaseId(diseaseId);
	}

	@Override
	public List<Symptom> findAllByIsHide(Map<String, Object> params) {
		return symptomDao.findAllByIsHide(params);
	}
}
