package com.yxw.insurance.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.dao.ClaimBlankDao;
import com.yxw.insurance.biz.entity.ClaimBlank;
import com.yxw.insurance.biz.service.ClaimBlankService;

@Service
public class ClaimBlankServiceImpl implements ClaimBlankService {

	ClaimBlankDao claimBlankDao = SpringContextHolder.getBean(ClaimBlankDao.class);
	
	@Override
	public List<ClaimBlank> findAllBlank() {
		return claimBlankDao.findAll();
	}

}
