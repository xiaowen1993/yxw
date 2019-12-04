package com.yxw.stats.service.stats;

import org.springframework.stereotype.Service;

import com.yxw.stats.data.common.Dao.impl.BaseDaoImpl;
import com.yxw.stats.entity.stats.YxDataSysPhoneNumberAttributionStatistical;

@Service(value = "yxDataSysPhoneNumberAttributionStatisticalService")
public class YxDataSysPhoneNumberAttributionStatisticalServiceImpl extends BaseDaoImpl<YxDataSysPhoneNumberAttributionStatistical, Long>
		implements YxDataSysPhoneNumberAttributionStatisticalService {
}