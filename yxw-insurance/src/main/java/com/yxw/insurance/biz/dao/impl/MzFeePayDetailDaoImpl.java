package com.yxw.insurance.biz.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;
import com.yxw.insurance.biz.dao.MzFeePayDetailDao;
import com.yxw.insurance.biz.entity.MzFeePayDetail;

@Repository
public class MzFeePayDetailDaoImpl extends BaseDaoImpl<MzFeePayDetail, String> implements MzFeePayDetailDao {

	private Logger logger = LoggerFactory.getLogger(MzFeeDataDaoImpl.class);
	private final static String SQLNAME_SELECT_DETAIL = "findMzPayDetail";

	/**
	 * 根据门诊缴费编号查询缴费明细
	 * @param mzFeeId
	 * @return
	 */
	@Override
	public List<MzFeePayDetail> findMzFeePayDetail(String mzFeeId) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("mzFeeId", mzFeeId);
			List<MzFeePayDetail> list = sqlSession.selectList(getSqlName(SQLNAME_SELECT_DETAIL), map);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			logger.error(String.format("查询代缴费出错!语句：%s", getSqlName(SQLNAME_SELECT_DETAIL)), e);
			throw new SystemException(String.format("查询代缴费出错!语句：%s", getSqlName(SQLNAME_SELECT_DETAIL)), e);
		}
		return null;
	}

}
