package com.yxw.insurance.biz.service;

import java.util.List;

import com.yxw.insurance.biz.entity.ClaimBlank;

public interface ClaimBlankService {

	
	/**
	 * 查询所有银行
	 * @return
	 */
	public List<ClaimBlank> findAllBlank();
}
