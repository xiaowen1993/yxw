package com.yxw.stats.service.project.wechat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.SysRegStatisticalDao;
import com.yxw.stats.entity.project.SysRegStatistical;
import com.yxw.stats.service.project.SysRegStatisticalService;

@Service(value = "sysRegStatisticalService")
public class SysRegStatisticalServiceImpl implements SysRegStatisticalService {

	private SysRegStatisticalDao sysRegStatisticalDao = SpringContextHolder.getBean("wechatSysRegStatisticalDao");

	public String findCurrRegisterStatsMaxDate(String hospitalId) {

		return sysRegStatisticalDao.findCurrRegisterStatsMaxDate(hospitalId);
	}

	public List<SysRegStatistical> findRegisterStatsData(Map<String, Object> params) {
		return sysRegStatisticalDao.findRegisterStatsData(params);
	}

	public void batchInsertRegisterStatsData(List<SysRegStatistical> entitys) {
		sysRegStatisticalDao.batchInsertRegisterStatsData(entitys);
	}

}