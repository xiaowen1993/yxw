/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015-6-15</p>
 *  <p> Created by Yuce</p>
 *  </body>
 * </html>
 */
package com.yxw.easyhealth.biz.register.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.easyhealth.biz.register.dao.RegisterOrderDao;
import com.yxw.easyhealth.biz.register.entity.RegisterOrder;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * @Package: com.yxw.mobileapp.biz.register.dao.impl
 * @ClassName: RegisterOrderImpl
 * @Statement: <p>挂号订单</p>
 * @JDK version used: 1.6
 * @Author: Yuce
 * @Create Date: 2015-6-15
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository(value = "registerOrderDao")
public class RegisterOrderDaoImpl extends BaseDaoImpl<RegisterOrder, String> implements RegisterOrderDao {
	private Logger logger = LoggerFactory.getLogger(RegisterOrderDaoImpl.class);
	private final static String SQLNAME_FIND_BY_ORDERNO = "findByOrderNo";
	private final static String SQLNAME_DEL_BY_ORDERNO = "deleteByOrderNo";
	private final static String SQLNAME_UPDATE_BY_ORDERNO = "updateByOrderNo";
	private final static String SQLNAME_UPDATE_REFUND_ORDER_NO = "updateRefundOrderNo";

	@Override
	public RegisterOrder findByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(orderNo);
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			return sqlSession.selectOne(getSqlName(SQLNAME_FIND_BY_ORDERNO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("根据订单编号查询订单信息出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
			throw new SystemException(String.format("根据订单编号查询订单信息出错!语句：%s", getSqlName(SQLNAME_FIND_BY_ORDERNO)), e);
		}
	}

	@Override
	public void deleteByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(orderNo);
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			sqlSession.delete(getSqlName(SQLNAME_DEL_BY_ORDERNO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("根据订单编号删除订单信息出错!语句：%s", getSqlName(SQLNAME_DEL_BY_ORDERNO)), e);
			throw new SystemException(String.format("根据订单编号删除订单信息出错!语句：%s", getSqlName(SQLNAME_DEL_BY_ORDERNO)), e);
		}
	}

	@Override
	public RegisterOrder updateByOrderNo(RegisterOrder order) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(order);
			if (order.getOrderNoHashVal() == null) {
				Integer orderNoHashVal = Math.abs(order.getOrderNo().hashCode());
				order.setOrderNoHashVal(orderNoHashVal);
			}
			sqlSession.update(getSqlName(SQLNAME_UPDATE_BY_ORDERNO), order);
		} catch (Exception e) {
			logger.error(String.format("根据订单编号删除订单信息出错!语句：%s", getSqlName(SQLNAME_DEL_BY_ORDERNO)), e);
			throw new SystemException(String.format("根据订单编号删除订单信息出错!语句：%s", getSqlName(SQLNAME_DEL_BY_ORDERNO)), e);
		}
		return order;
	}

	@Override
	public void updateRefundOrderNo(String orderNo, String relativeOrderNo) {
		// TODO Auto-generated method stub
		try {
			Assert.notNull(orderNo, "saveRelativeOrderNo orderNo is null!");
			Assert.notNull(relativeOrderNo, "saveRelativeOrderNo relativeOrderNo is null!");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Integer orderNoHashVal = Math.abs(orderNo.hashCode());
			paramMap.put("orderNo", orderNo);
			paramMap.put("orderNoHashVal", orderNoHashVal);
			paramMap.put("relativeOrderNo", relativeOrderNo);
			sqlSession.update(getSqlName(SQLNAME_UPDATE_REFUND_ORDER_NO), paramMap);
		} catch (Exception e) {
			logger.error(String.format("保存订单的退费号出错!语句：%s", getSqlName(SQLNAME_UPDATE_REFUND_ORDER_NO)), e);
			throw new SystemException(String.format("保存订单的退费号出错!语句：%s", getSqlName(SQLNAME_UPDATE_REFUND_ORDER_NO)), e);
		}
	}
}
