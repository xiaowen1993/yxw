package com.yxw.stats.service.project.alipay;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.SysMedicalCardStatisticalDao;
import com.yxw.stats.entity.project.SysMedicalCardStatistical;
import com.yxw.stats.service.project.SysMedicalCardStatisticalService;

@Service(value = "alipaySysMedicalCardStatisticalService")
public class SysMedicalCardStatisticalServiceImpl implements SysMedicalCardStatisticalService {

	private SysMedicalCardStatisticalDao sysMedicalCardStatisticalDao = SpringContextHolder.getBean("alipaySysMedicalCardStatisticalDao");

	public String findCurrMedicalCardStatsMaxDate(String hospitalId) {

		return sysMedicalCardStatisticalDao.findCurrMedicalCardStatsMaxDate(hospitalId);
	}

	public List<SysMedicalCardStatistical> findMedicalCardStatsData(Map<String, Object> params) {
		return sysMedicalCardStatisticalDao.findMedicalCardStatsData(params);
	}

	public void batchInsertMedicalCardStatsData(List<SysMedicalCardStatistical> entitys) {
		sysMedicalCardStatisticalDao.batchInsertMedicalCardStatsData(entitys);
	}
}