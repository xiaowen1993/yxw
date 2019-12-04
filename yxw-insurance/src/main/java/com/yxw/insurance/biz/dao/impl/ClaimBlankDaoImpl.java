package com.yxw.insurance.biz.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.ClaimBlankDao;
import com.yxw.insurance.biz.entity.ClaimBlank;
@Repository
public class ClaimBlankDaoImpl   extends BaseDaoImpl<ClaimBlank, String> implements ClaimBlankDao {
	
	private Logger logger = LoggerFactory.getLogger(ClaimBlankDaoImpl.class);

	
	/**
	 * 查询所有银行
	 * @return
	 */
	@Override
	public List<ClaimBlank> findAllBlank() {
		try {
			List<ClaimBlank> list=super.findAll();
			if(list!=null&&list.size()>0){
				return list;
			}
		} catch (Exception e) {
			logger.error("查询所有银行出错!语句：",  e);
			throw new SystemException("查询所有银行!语句：",  e);
		}
		return null;
	}


}
