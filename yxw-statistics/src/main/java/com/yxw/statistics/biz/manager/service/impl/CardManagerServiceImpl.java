package com.yxw.statistics.biz.manager.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.outside.service.YxwCardService;
import com.yxw.solr.vo.medicalcard.CardInfoRequest;
import com.yxw.statistics.biz.manager.service.CardManagerService;
import com.yxw.statistics.biz.vo.CardManagerVo;

@Service("cardManagerService")
public class CardManagerServiceImpl implements CardManagerService {
	
	private YxwCardService service = SpringContextHolder.getBean(YxwCardService.class);

	@Override
	public String getCards(CardManagerVo vo) {
		CardInfoRequest request = new CardInfoRequest();
		BeanUtils.copyProperties(vo, request);
		return service.findMedicalcards(request);
	}

}
