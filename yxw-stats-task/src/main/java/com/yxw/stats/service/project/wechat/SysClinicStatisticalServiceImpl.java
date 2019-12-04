package com.yxw.stats.service.project.wechat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.SysClinicStatisticalDao;
import com.yxw.stats.entity.project.SysClinicStatistical;
import com.yxw.stats.service.project.SysClinicStatisticalService;

@Service(value = "sysClinicStatisticalService")
public class SysClinicStatisticalServiceImpl implements SysClinicStatisticalService {

	private SysClinicStatisticalDao sysClinicStatisticalDao = SpringContextHolder.getBean("wechatSysClinicStatisticalDao");

	public String findCurrClinicStatsMaxDate(String hospitalId) {

		return sysClinicStatisticalDao.findCurrClinicStatsMaxDate(hospitalId);
	}

	public List<SysClinicStatistical> findClinicStatsData(Map<String, Object> params) {
		return sysClinicStatisticalDao.findClinicStatsData(params);
	}

	public void batchInsertClinicStatsData(List<SysClinicStatistical> entitys) {
		sysClinicStatisticalDao.batchInsertClinicStatsData(entitys);
	}
}