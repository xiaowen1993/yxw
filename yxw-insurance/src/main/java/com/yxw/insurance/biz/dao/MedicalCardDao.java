package com.yxw.insurance.biz.dao;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.mvc.dao.BaseDao;

public interface MedicalCardDao extends BaseDao<MedicalCard, String> {

	/**
	 * 找到所有的家人
	 * @param openId
	 * @param hashTableName
	 * @param state
	 * @return
	 */
	public List<MedicalCard> findAllMedicalCards(String openId, int state);
}
