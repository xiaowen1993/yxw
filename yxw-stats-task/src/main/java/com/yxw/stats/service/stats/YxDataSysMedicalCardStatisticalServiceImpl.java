package com.yxw.stats.service.stats;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.stats.YxDataSysMedicalCardStatisticalDao;
import com.yxw.stats.entity.stats.YxDataSysMedicalCardStatistical;

@Service(value = "yxDataSysMedicalCardStatisticalService")
public class YxDataSysMedicalCardStatisticalServiceImpl implements YxDataSysMedicalCardStatisticalService {

	private YxDataSysMedicalCardStatisticalDao yxDataSysMedicalCardStatisticalDao = SpringContextHolder
			.getBean("yxDataSysMedicalCardStatisticalDao");

	public String findCurrMedicalCardStatsMaxDate(String hospitalId) {

		return yxDataSysMedicalCardStatisticalDao.findCurrMedicalCardStatsMaxDate(hospitalId);
	}

	public void batchInsertMedicalCardStatsData(List<YxDataSysMedicalCardStatistical> entitys) {
		yxDataSysMedicalCardStatisticalDao.batchInsertMedicalCardStatsData(entitys);
	}
}