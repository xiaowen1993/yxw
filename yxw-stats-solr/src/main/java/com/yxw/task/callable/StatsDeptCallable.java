package com.yxw.task.callable;

import java.util.concurrent.Callable;

import org.springframework.beans.BeanUtils;

import com.yxw.biz.dept.service.DeptService;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.vo.StatsHospitalInfosVo;
import com.yxw.vo.dept.DeptStatsRequest;

public class StatsDeptCallable implements Callable<String> {

	private DeptService service = SpringContextHolder.getBean(DeptService.class);

	private StatsHospitalInfosVo infosVo;

	private boolean statsArea;
	
	private String statsMonth;

	public StatsDeptCallable(StatsHospitalInfosVo infosVo, boolean statsArea, String statsMonth) {
		super();
		this.setInfosVo(infosVo);
		this.statsArea = statsArea;
		this.setStatsMonth(statsMonth);
	}

	@Override
	public String call() throws Exception {
		DeptStatsRequest request = new DeptStatsRequest();
		BeanUtils.copyProperties(infosVo, request);
		request.setPlatform(infosVo.getPlatformType());
		return service.statsInfos(request);

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
