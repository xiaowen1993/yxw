package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.stats.dao.platform.ClinicOrderCountDao;
import com.yxw.stats.entity.platform.ClinicOrderCount;

@Service(value = "clinicOrderCountService")
public class ClinicOrderCountServiceImpl extends BaseServiceImpl<ClinicOrderCount, String> implements ClinicOrderCountService {
	public static Logger logger = LoggerFactory.getLogger(ClinicOrderCountServiceImpl.class);

	@Autowired
	private ClinicOrderCountDao clinicOrderCountDao;

	@Override
	protected BaseDao<ClinicOrderCount, String> getDao() {
		return clinicOrderCountDao;
	}

	/**
	 * 当天门诊订单统计
	 * @param map
	 * @return
	 */
	public List<ClinicOrderCount> findClinicOrderCountByDate(Map map) {
		return clinicOrderCountDao.findClinicOrderCountByDate(map);
	}
}
