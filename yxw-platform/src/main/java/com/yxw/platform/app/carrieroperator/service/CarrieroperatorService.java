package com.yxw.platform.app.carrieroperator.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.platform.app.carrieroperator.Carrieroperator;
import com.yxw.framework.mvc.service.BaseService;

public interface CarrieroperatorService extends BaseService<Carrieroperator, Long> {

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

	/**
	 * 根据运营位置查找
	 */
	public List<Carrieroperator> findByOperationPosition(String operationPosition);

}
