package com.yxw.solr.outside.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.medicalcard.service.MedicalcardService;
import com.yxw.solr.constants.ResultConstant;
import com.yxw.solr.outside.service.YxwCardService;
import com.yxw.solr.vo.YxwResponse;
import com.yxw.solr.vo.medicalcard.CardInfoRequest;
import com.yxw.solr.vo.medicalcard.CardStatsRequest;

public class YxwCardServiceImpl implements YxwCardService {

	private MedicalcardService service = SpringContextHolder.getBean(MedicalcardService.class);

	@Override
	public String getStatsInfos(CardStatsRequest request) {
		YxwResponse vo = service.getStatInfos(request);
		return JSON.toJSONString(vo);
	}

	@Override
	public String findMedicalcards(CardInfoRequest request) {
		YxwResponse vo = new YxwResponse();
		if (StringUtils.isBlank(request.getHospitalCode())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_HOSPITALCODE);
		} else {
			vo = service.findCards(request);
		}
		return JSON.toJSONString(vo);
	}

	@Override
	public String statsDailyInfo(CardStatsRequest request) {
		return service.statsInfoForDayWithinPlatform(request);
	}

	@Override
	public String searchCards(CardInfoRequest request) {
		YxwResponse vo = new YxwResponse();
		if (StringUtils.isBlank(request.getHospitalCode())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_HOSPITALCODE);
		} else if (request.getPlatform() == -1) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_PLATFORM);
		} else if (StringUtils.isBlank(request.getCardNo())) {
			vo.setResultCode(ResultConstant.RESULT_CODE_FAIL);
			vo.setResultMessage(ResultConstant.NEED_CARD_NO);
		} else {
			vo = service.searchCards(request);
		}
		return JSON.toJSONString(vo);
	}

}
