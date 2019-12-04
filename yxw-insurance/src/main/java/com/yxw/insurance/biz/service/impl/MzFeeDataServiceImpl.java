package com.yxw.insurance.biz.service.impl;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.dao.MzFeeDataDao;
import com.yxw.insurance.biz.entity.MzFeeData;
import com.yxw.insurance.biz.service.MzFeeDataService;

@Service
public class MzFeeDataServiceImpl implements MzFeeDataService {

	MzFeeDataDao mzFeeDataDao = SpringContextHolder.getBean(MzFeeDataDao.class);
	
	@Override
	public MzFeeData findMzFeeData(String openId, String mzFeeId) {
		return mzFeeDataDao.findMzFeeData(openId, mzFeeId);
	}

}
