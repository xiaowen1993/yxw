package com.yxw.easyhealth.common.service;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.framework.mvc.service.BaseService;

public interface CommonCardService extends BaseService<MedicalCard, String> {
	/**
	 * 根据OpenId，医院Code找卡
	 * @param medicalCard
	 * @return
	 */
	public List<MedicalCard> getAllCards(MedicalCard medicalCard);

	/**
	 * 得到所有的绑卡信息
	 * @return
	 */
	public List<SimpleMedicalCard> findAllForCache();

}
