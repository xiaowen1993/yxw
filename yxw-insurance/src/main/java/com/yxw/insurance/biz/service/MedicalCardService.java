package com.yxw.insurance.biz.service;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.mvc.service.BaseService;

public interface MedicalCardService extends BaseService<MedicalCard, String> {

	/**
	 * 找所有的家人（包括自己）
	 * @param openId
	 * @return
	 */
	public List<MedicalCard> findAllMedicalCards(String openId);

}
