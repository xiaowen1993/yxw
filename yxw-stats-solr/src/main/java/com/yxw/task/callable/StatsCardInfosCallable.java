package com.yxw.task.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.yxw.biz.medicalcard.service.CardInfoService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.vo.StatsHospitalInfosVo;
import com.yxw.vo.medicalcard.CardInfoStatsRequest;

public class StatsCardInfosCallable implements Callable<String> {

	private Logger logger = LoggerFactory.getLogger(StatsCardInfosCallable.class);

	private CardInfoService service = SpringContextHolder.getBean(CardInfoService.class);

	private StatsHospitalInfosVo infosVo;

	private boolean statsArea;
	
	private String statsMonth;

	public StatsCardInfosCallable(StatsHospitalInfosVo infosVo, boolean statsArea, String statsMonth) {
		super();
		this.setInfosVo(infosVo);
		this.statsArea = statsArea;
		this.setStatsMonth(statsMonth);
	}

	@Override
	public String call() throws Exception {
		CardInfoStatsRequest request = new CardInfoStatsRequest();
		BeanUtils.copyProperties(infosVo, request);
		request.setPlatform(infosVo.getPlatformType());

		if (statsArea) {
			logger.info("[年龄段&&性别统计]统计日期为空，区域不为空，默认为初始化统计所有日期");
			return service.statsAreaInfos(request);
		} else {
			logger.info("[年龄段&&性别统计]统计日期为空，区域为空，默认为初始化统计所有日期");
			return service.statsInfos(request);
		}

	}

	public boolean isStatsArea() {
		return statsArea;
	}

	public void setStatsArea(boolean statsArea) {
		this.statsArea = statsArea;
	}

	public StatsHospitalInfosVo getInfosVo() {
		return infosVo;
	}

	public void setInfosVo(StatsHospitalInfosVo infosVo) {
		this.infosVo = infosVo;
	}
	
	public String getStatsMonth() {
		return statsMonth;
	}

	public void setStatsMonth(String statsMonth) {
		this.statsMonth = statsMonth;
	}

}
