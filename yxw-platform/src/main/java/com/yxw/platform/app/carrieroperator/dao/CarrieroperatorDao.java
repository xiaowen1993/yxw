package com.yxw.platform.app.carrieroperator.dao;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.framework.mvc.dao.BaseDao;

public interface CarrieroperatorDao extends BaseDao<Carrieroperator, Long> {

	/**
	 * 查询运营消息 所有排序数字
	 * @return
	 */
	public List<String> findSorting(Map<String, Object> params);

	/**
	 * 根据id查询运营消息
	 * @return
	 */
	public Carrieroperator findCarrieroperatorById(Map<String, Object> params);
	
	public List<Carrieroperator> findByOperationPosition(String operationPosition);

}
