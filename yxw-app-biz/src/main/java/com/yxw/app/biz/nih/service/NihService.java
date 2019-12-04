package com.yxw.app.biz.nih.service;

import java.util.List;

import com.yxw.commons.entity.mobile.biz.nih.NihRecord;
import com.yxw.framework.mvc.service.BaseService;

public interface NihService extends BaseService<NihRecord, String> {
	public List<NihRecord> findByUserIdAndAppCode(String userId, String appCode);
	
	public List<NihRecord> findByAppCode(String appCode);
	
	public List<NihRecord> findByUserId(String userId);
}
