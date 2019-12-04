package com.yxw.solr.task.callable;

import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.solr.biz.register.service.RegDeptService;
import com.yxw.solr.vo.register.RegDeptStatsRequest;

public class StatsRegDeptCallable implements Callable<String> {

private Logger logger = LoggerFactory.getLogger(StatsRegDeptCallable.class);
	
	private RegDeptService regDeptService = SpringContextHolder.getBean(RegDeptService.class);

	private Integer platform;
	
	private String hospitalCode;
	
	private String branchCode;
	
	private String statsDate;
	
	
	public StatsRegDeptCallable(Integer platform, String hospitalCode, String branchCode, String statsDate) {
		super();
		this.platform = platform;
		this.hospitalCode = hospitalCode;
		this.branchCode = branchCode;
		this.statsDate = statsDate;
	}


	public Integer getPlatform() {
		return platform;
	}


	public void setPlatform(Integer platform) {
		this.platform = platform;
	}


	public String getHospitalCode() {
		return hospitalCode;
	}


	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}


	public String getBranchCode() {
		return branchCode;
	}


	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}


	public String getStatsDate() {
		return statsDate;
	}


	public void setStatsDate(String statsDate) {
		this.statsDate = statsDate;
	}
	
	@Override
	public String call() throws Exception {
		RegDeptStatsRequest request = new RegDeptStatsRequest(); 
		request.setHospitalCode(hospitalCode);
		request.setBranchCode(branchCode);
		request.setPlatform(platform);
		
		if (platform != -1) {
			if (StringUtils.isBlank(statsDate)) {
				logger.info("[特定平台统计]统计日期为空，默认为初始化统计所有日期");
				return regDeptService.statsInfosWithinPlatform(request);
			} else {
				logger.info("[特定平台统计]统计日期为{}. 单独开启一天的统计.", statsDate);
				request.setStatsDate(statsDate);
				return regDeptService.statsInfoForDayWithinPlatform(request);
			}
		} else {
			if (StringUtils.isBlank(statsDate)) {
				logger.info("[全平台统计]统计日期为空，默认为初始化统计所有日期");
				return regDeptService.statsInfosWithoutPlatform(request);
			} else {
				logger.info("[全平台统计]统计日期为{}. 单独开启一天的统计.", statsDate);
				request.setStatsDate(statsDate);
				return regDeptService.statsInfoForDayWithoutPlatform(request);
			}
		}
	}

}
