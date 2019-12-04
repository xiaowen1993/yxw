package com.yxw.hospitalmanager.service.impl;

import org.springframework.stereotype.Service;

import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.hospitalmanager.dao.TradeModeDao;
import com.yxw.hospitalmanager.entity.TradeMode;
import com.yxw.hospitalmanager.service.TradeModeService;

@Service(value="tradeModeService")
public class TradeModeServiceImpl extends BaseServiceImpl<TradeMode, String> implements TradeModeService {

	private TradeModeDao dao = SpringContextHolder.getBean(TradeModeDao.class);
	
	@Override
	protected BaseDao<TradeMode, String> getDao() {
		return dao;
	}

}
