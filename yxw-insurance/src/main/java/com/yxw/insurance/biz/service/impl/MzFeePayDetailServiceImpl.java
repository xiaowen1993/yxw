package com.yxw.insurance.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.dao.MzFeePayDetailDao;
import com.yxw.insurance.biz.entity.MzFeePayDetail;
import com.yxw.insurance.biz.service.MzFeePayDetailService;

@Service
public class MzFeePayDetailServiceImpl implements MzFeePayDetailService {

	private MzFeePayDetailDao mzFeePayDetailDao = SpringContextHolder.getBean(MzFeePayDetailDao.class);

	@Override
	public List<MzFeePayDetail> findMzFeePayDetail(String mzFeeId) {
		return mzFeePayDetailDao.findMzFeePayDetail(mzFeeId);
	}

}
