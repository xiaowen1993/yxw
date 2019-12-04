package com.yxw.task.callable;

import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.yxw.biz.deposit.service.DepositService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.vo.StatsHospitalInfosVo;
import com.yxw.vo.deposit.DepositStatsRequest;

public class StatsDepositCallable implements Callable<String> {

	private Logger logger = LoggerFactory.getLogger(StatsDepositCallable.class);

	private DepositService service = SpringContextHolder.getBean(DepositService.class);

	private StatsHospitalInfosVo infosVo;

	private boolean statsArea;
	
	private String statsMonth;

	public StatsDepositCallable(StatsHospitalInfosVo infosVo, boolean statsArea, String statsMonth) {
		super();
		this.setInfosVo(infosVo);
		this.statsArea = statsArea;
		this.setStatsMonth(statsMonth);
	}

	@Override
	public String call() throws Exception {
		DepositStatsRequest request = new DepositStatsRequest();
		BeanUtils.copyProperties(infosVo, request);
		request.setPlatform(infosVo.getPlatformType());

		if (statsArea) {
			if (StringUtils.isBlank(statsMonth)) {
				logger.info("[押金统计]统计日期为空，区域不为空，默认为初始化统计所有日期");
				return service.statsAreaInfos(request);
			} else {
				logger.info("[押金统计]统计日期为{}.区域不为空.", statsMonth);
				request.setStatsMonth(statsMonth);
				request.setBeginDate(statsMonth);
				return service.statsAreaInfoForMonth(request);
			}
		} else {
			if (StringUtils.isBlank(statsMonth)) {
				logger.info("[押金统计]统计日期为空，区域为空，默认为初始化统计所有日期");
				return service.statsInfos(request);
			} else {
				logger.info("[押金统计]统计日期为{}. 区域为空.", statsMonth);
				request.setStatsMonth(statsMonth);
				request.setBeginDate(statsMonth);
				return service.statsInfoForMonth(request);
			}
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
