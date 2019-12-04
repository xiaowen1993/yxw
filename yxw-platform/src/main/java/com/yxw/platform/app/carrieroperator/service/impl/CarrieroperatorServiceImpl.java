package com.yxw.platform.app.carrieroperator.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.app.carrieroperator.dao.CarrieroperatorDao;
import com.yxw.platform.app.carrieroperator.service.CarrieroperatorService;

@Service(value = "carrieroperatorService")
public class CarrieroperatorServiceImpl extends BaseServiceImpl<Carrieroperator, Long> implements CarrieroperatorService {

	@Autowired
	private CarrieroperatorDao carrieroperatorDao;

	@Override
	protected BaseDao<Carrieroperator, Long> getDao() {
		return carrieroperatorDao;
	}

	@Override
	public List<String> findSorting(Map<String, Object> params) {
		return carrieroperatorDao.findSorting(params);
	}

	@Override
	public Carrieroperator findCarrieroperatorById(Map<String, Object> params) {
		return carrieroperatorDao.findCarrieroperatorById(params);
	}

	/**
	 * 根据运营位置查找
	 */
	@Override
	public List<Carrieroperator> findByOperationPosition(String operationPosition) {
		return carrieroperatorDao.findByOperationPosition(operationPosition);
	}

}
