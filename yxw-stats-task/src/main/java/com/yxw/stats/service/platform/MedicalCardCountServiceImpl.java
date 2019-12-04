package com.yxw.stats.service.platform;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.stats.dao.platform.MedicalCardCountDao;
import com.yxw.stats.entity.platform.MedicalCardCount;

@Service(value = "medicalCardCountService")
public class MedicalCardCountServiceImpl extends BaseServiceImpl<MedicalCardCount, String> implements MedicalCardCountService {
	public static Logger logger = LoggerFactory.getLogger(MedicalCardCountServiceImpl.class);

	@Autowired
	private MedicalCardCountDao medicalCardCountDao;

	@Override
	protected BaseDao<MedicalCardCount, String> getDao() {
		// TODO Auto-generated method stub
		return medicalCardCountDao;
	}

	public List<MedicalCardCount> findMedicalCardCountByDate(Map map) {
		return medicalCardCountDao.findMedicalCardCountByDate(map);
	}
}
