package com.yxw.insurance.biz.dao;

import java.util.List;

import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.insurance.biz.entity.ClaimBlank;

public interface ClaimBlankDao extends BaseDao<ClaimBlank, String> {

	
	/**
	 * 查询所有银行
	 * @return
	 */
	public List<ClaimBlank> findAllBlank();
}
