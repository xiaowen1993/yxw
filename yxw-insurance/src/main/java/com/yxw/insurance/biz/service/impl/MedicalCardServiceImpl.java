package com.yxw.insurance.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.commons.constants.biz.FamilyConstants;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.insurance.biz.dao.MedicalCardDao;
import com.yxw.insurance.biz.service.MedicalCardService;

@Service(value = "medicalCardService")
public class MedicalCardServiceImpl extends BaseServiceImpl<MedicalCard, String> implements MedicalCardService {

	private MedicalCardDao medicalCardDao = SpringContextHolder.getBean(MedicalCardDao.class);

	@Override
	public List<MedicalCard> findAllMedicalCards(String openId) {
		return medicalCardDao.findAllMedicalCards(openId, FamilyConstants.FAMILY_STATE_ENABLE);
	}

	@Override
	protected BaseDao<MedicalCard, String> getDao() {
		return medicalCardDao;
	}

}
