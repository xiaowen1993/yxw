package com.yxw.mobileapp.biz.insure.dao;

import com.yxw.commons.dto.inside.PaidMZDetailCommParams;
import com.yxw.commons.dto.outside.PaidMZDetailReuslt;
import com.yxw.framework.mvc.dao.BaseDao;

public interface InsureDataDao extends BaseDao<PaidMZDetailReuslt, String> {

	/**
	 * 查询缴费明细
	 * @param params
	 * @return
	 */
	public PaidMZDetailReuslt getPaidMZDetail(PaidMZDetailCommParams params);
}
