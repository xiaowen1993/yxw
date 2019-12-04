package com.yxw.insurance.biz.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.insurance.biz.entity.ClaimOrder;

public interface ClaimOrderDao extends BaseDao<ClaimOrder, String> {

	
	/**
	 * 保存理赔申请记录
	 * @param order
	 */
	public void saveClaimOrder(ClaimOrder order);
	
	
	/**
	 * 修改理赔申请记录
	 * @param order
	 */
	public void updateClaimOrder(ClaimOrder order);
	
	/**
	 * 查询理赔申请列表
	 * @param openId
	 * @return
	 */
	public List<ClaimOrder> findClaimOrderList(String openId);
	
	/**
	 * 查询理赔详情
	 * @param openId
	 * @param claimProjectOrderNo 理赔单号
	 * @return
	 */
	public ClaimOrder findClaimOrder(String openId,String claimProjectOrderNo);
	
	
	/**
	 * 根据id查询理赔详情
	 * @param id
	 * @return
	 */
	public ClaimOrder findClaimOrderById(String id);
}
