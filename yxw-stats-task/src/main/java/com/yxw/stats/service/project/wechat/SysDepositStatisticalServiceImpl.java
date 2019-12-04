package com.yxw.stats.service.project.wechat;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.stats.dao.project.SysDepositStatisticalDao;
import com.yxw.stats.entity.project.SysDepositStatistical;
import com.yxw.stats.service.project.SysDepositStatisticalService;

@Service(value = "sysDepositStatisticalService")
public class SysDepositStatisticalServiceImpl implements SysDepositStatisticalService {

	private SysDepositStatisticalDao sysDepositStatisticalDao = SpringContextHolder.getBean("wechatSysDepositStatisticalDao");

	public String findCurrDepositStatsMaxDate(String hospitalId) {

		return sysDepositStatisticalDao.findCurrDepositStatsMaxDate(hospitalId);
	}

	public List<SysDepositStatistical> findDepositStatsData(Map<String, Object> params) {
		return sysDepositStatisticalDao.findDepositStatsData(params);
	}

	public void batchInsertDepositStatsData(List<SysDepositStatistical> entitys) {
		sysDepositStatisticalDao.batchInsertDepositStatsData(entitys);
	}
}