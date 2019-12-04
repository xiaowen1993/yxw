package com.yxw.task.callable;

import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.yxw.biz.attribution.service.AttributionService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.vo.attribution.AttributionStatsRequest;
import com.yxw.vo.attribution.CityVo;

public class StatsAttributionCallable implements Callable<String> {

	private Logger logger = LoggerFactory.getLogger(StatsAttributionCallable.class);

	private AttributionService service = SpringContextHolder.getBean(AttributionService.class);

	private CityVo vo;

	private String statsMonth;

	public StatsAttributionCallable(CityVo vo, String statsMonth) {
		super();
		this.setVo(vo);
		this.setStatsMonth(statsMonth);
	}

	@Override
	public String call() throws Exception {
		AttributionStatsRequest request = new AttributionStatsRequest();
		BeanUtils.copyProperties(vo, request);

		if (StringUtils.isBlank(statsMonth)) {
			logger.info("[号码归属地统计]统计日期为空，默认为初始化统计所有日期");
			return service.statsInfos(request);
		} else {
			logger.info("[号码归属地统计]统计日期为{}", statsMonth);
			request.setStatsMonth(statsMonth);
			request.setBeginDate(statsMonth);
			return service.statsInfoForMonth(request);
		}

	}

	public String getStatsMonth() {
		return statsMonth;
	}

	public void setStatsMonth(String statsMonth) {
		this.statsMonth = statsMonth;
	}

	public CityVo getVo() {
		return vo;
	}

	public void setVo(CityVo vo) {
		this.vo = vo;
	}

}
