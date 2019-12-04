package com.yxw.platform.datas.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.vo.cache.SimpleMedicalCard;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.mobile.biz.medicalcard.MedicalCard;
import com.yxw.framework.common.spring.ext.SpringContextHolder;

public class CommonBizManager {
	// private MedicalCardCache medicalCardCache = SpringContextHolder.getBean(MedicalCardCache.class);
	
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	/**
	 * 更新医疗卡缓存信息
	 * @param medicalCard
	 * @param opType  操作类型  可选参数如下:<br>
	 * 		  CacheConstant.UPDATE_OP_TYPE_ADD  新增<br>
	 *		  CacheConstant.UPDATE_OP_TYPE_UPDATE 更新<br>
	 *        CacheConstant.UPDATE_OP_TYPE_DEL  删除<br>
	 */
	public void updateMedicalCardToCache(MedicalCard medicalCard, String opType) {
		List<Object> params = new ArrayList<Object>();
		params.add(medicalCard);
		params.add(opType);
		serveComm.set(CacheType.MEDICAL_CARD_CACHE, "setMedicalCardToCache", params);
		// medicalCardCache.setMedicalCardToCache(medicalCard, opType);
	}

	/**
	 * 得到openId所有的有效绑卡
	 * @param openId
	 * @param hospitalCode
	 * @param branchCode
	 * @return
	 */
	public List<SimpleMedicalCard> getOpenIdMedicalCards(String openId, String hospitalCode, String branchCode) {
		List<SimpleMedicalCard> cards = null;
		
		// return medicalCardCache.getMedicalCardsByOpenId(openId, hospitalCode, branchCode);
		List<Object> params = new ArrayList<Object>();
		params.add(openId);
		params.add(hospitalCode);
		params.add(branchCode);
		List<Object> results = serveComm.get(CacheType.MEDICAL_CARD_CACHE, "getMedicalCardsByOpenId", params);
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			cards = JSON.parseArray(source, SimpleMedicalCard.class);
		}
		
		return cards != null ? cards : new ArrayList<SimpleMedicalCard>();
	}

}
