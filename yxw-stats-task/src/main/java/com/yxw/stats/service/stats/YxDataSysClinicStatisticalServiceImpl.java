package com.yxw.stats.service.stats;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.stats.YxDataSysClinicStatisticalDao;
import com.yxw.stats.entity.stats.YxDataSysClinicStatistical;

@Service(value = "yxDataSysClinicStatisticalService")
public class YxDataSysClinicStatisticalServiceImpl implements YxDataSysClinicStatisticalService {

	private YxDataSysClinicStatisticalDao yxDataSysClinicStatisticalDao = SpringContextHolder.getBean("yxDataSysClinicStatisticalDao");

	public String findCurrClinicStatsMaxDate(String hospitalId) {

		return yxDataSysClinicStatisticalDao.findCurrClinicStatsMaxDate(hospitalId);
	}

	public void batchInsertClinicStatsData(List<YxDataSysClinicStatistical> entitys) {
		yxDataSysClinicStatisticalDao.batchInsertClinicStatsData(entitys);
	}
}