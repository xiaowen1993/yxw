package com.yxw.insurance.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.ClaimOrderDao;
import com.yxw.insurance.biz.entity.ClaimOrder;

@Repository
public class ClaimOrderDaoImpl  extends BaseDaoImpl<ClaimOrder, String> implements ClaimOrderDao {

	private Logger logger = LoggerFactory.getLogger(ClaimOrderDaoImpl.class);

	private final static String SQLNAME_INSERT="add"; 
	private final static String SQLNAME_SELECT_LIST="findClaimOrderList";
	private final static String SQLNAME_SELECT_DETAIL="findClaimOrder";
	private final static String SQLNAME_UPDATE="update"; 
	
	/**
	 * 保存理赔申请记录
	 * @param order
	 */
	@Override
	public void saveClaimOrder(ClaimOrder order) {
		try {
			sqlSession.insert(getSqlName(SQLNAME_INSERT), order);
		} catch (Exception e) {
			logger.error(String.format("保存理赔申请记录出错!语句：%s", getSqlName(SQLNAME_INSERT)), e);
			throw new SystemException(String.format("保存理赔申请记录出错!语句：%s", getSqlName(SQLNAME_INSERT)), e);
		}
	}

	/**
	 * 查询理赔申请列表
	 * @param openId
	 * @param patientCardNo 诊疗卡号
	 * @return
	 */
	@Override
	public List<ClaimOrder> findClaimOrderList(String openId) {
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("openId", openId);
			List<ClaimOrder> list=sqlSession.selectList(getSqlName(SQLNAME_SELECT_LIST), map);
			if(list!=null&&list.size()>0){
				return list;
			}
		} catch (Exception e) {
			logger.error(String.format("查询理赔申请列表出错!语句：%s", getSqlName(SQLNAME_SELECT_LIST)), e);
			throw new SystemException(String.format("查询理赔申请列表出错!语句：%s", getSqlName(SQLNAME_SELECT_LIST)), e);
		}	
		return null;
	}

	
	/**
	 * 查询理赔详情
	 * @param openId
	 * @param claimProjectOrderNo 理赔单号
	 * @return
	 */
	@Override
	public ClaimOrder findClaimOrder(String openId, String claimProjectOrderNo) {
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("openId", openId);
			map.put("claimProjectOrderNo", claimProjectOrderNo);
			ClaimOrder order=sqlSession.selectOne(getSqlName(SQLNAME_SELECT_DETAIL), map);
			if(order!=null){
				return order;
			}
		} catch (Exception e) {
			logger.error(String.format("查询理赔详情出错!语句：%s", getSqlName(SQLNAME_SELECT_DETAIL)), e);
			throw new SystemException(String.format("查询理赔详情出错!语句：%s", getSqlName(SQLNAME_SELECT_DETAIL)), e);
		}
		return null;
	}

	/**
	 * 修改理赔申请记录
	 * @param order
	 */
	@Override
	public void updateClaimOrder(ClaimOrder order) {
		try {
			sqlSession.update(getSqlName(SQLNAME_UPDATE), order);
		} catch (Exception e) {
			logger.error(String.format("更新理赔申请记录出错!语句：%s", getSqlName(SQLNAME_UPDATE)), e);
			throw new SystemException(String.format("更新理赔申请记录出错!语句：%s", getSqlName(SQLNAME_UPDATE)), e);
		}
		
	}

	@Override
	public ClaimOrder findClaimOrderById(String id) {
		return super.findById(id);
	}


}
