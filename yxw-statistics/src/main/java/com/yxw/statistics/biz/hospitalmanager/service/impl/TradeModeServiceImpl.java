package com.yxw.statistics.biz.hospitalmanager.service.impl;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.statistics.biz.hospitalmanager.dao.TradeModeDao;
import com.yxw.statistics.biz.hospitalmanager.entity.TradeMode;
import com.yxw.statistics.biz.hospitalmanager.service.TradeModeService;

@Service(value="tradeModeService")
public class TradeModeServiceImpl extends BaseServiceImpl<TradeMode, String> implements TradeModeService {

	private TradeModeDao dao = SpringContextHolder.getBean(TradeModeDao.class);
	
	@Override
	protected BaseDao<TradeMode, String> getDao() {
		return dao;
	}

}
