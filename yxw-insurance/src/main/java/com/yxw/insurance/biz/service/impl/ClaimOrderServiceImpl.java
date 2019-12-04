package com.yxw.insurance.biz.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.insurance.biz.dao.ClaimOrderDao;
import com.yxw.insurance.biz.entity.ClaimOrder;
import com.yxw.insurance.biz.service.ClaimOrderService;

@Service
public class ClaimOrderServiceImpl implements ClaimOrderService {

	ClaimOrderDao claimOrderDao = SpringContextHolder.getBean(ClaimOrderDao.class);
	
	@Override
	public void saveClaimOrder(ClaimOrder order) {
		claimOrderDao.saveClaimOrder(order);
	}

	@Override
	public List<ClaimOrder> findClaimOrderList(String openId) {
		return claimOrderDao.findClaimOrderList(openId);
	}

	@Override
	public ClaimOrder findClaimOrder(String openId, String claimProjectOrderNo) {
		return claimOrderDao.findClaimOrder(openId, claimProjectOrderNo);
	}

	@Override
	public void udpateClaimOrder(ClaimOrder order) {
		claimOrderDao.update(order);
	}

	@Override
	public ClaimOrder findClaimOrder(String id) {
		return claimOrderDao.findClaimOrderById(id);
	}

}
