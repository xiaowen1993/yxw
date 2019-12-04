package com.yxw.app.biz.nih.dao;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.nih.NihRecord;
import com.yxw.framework.mvc.dao.BaseDao;

public interface NihRecordDao extends BaseDao<NihRecord, String> {
	public List<NihRecord> findByUserIdAndAppCode(String userId, String appCode, String tableName);

	public List<NihRecord> findByAppCode(String appCode, String tableName);

	public List<NihRecord> findByUserId(String userId, String tableName);
}
