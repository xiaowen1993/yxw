package com.yxw.easyhealth.biz.community.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.community.CommunityHealthCenter;
import com.yxw.framework.mvc.service.BaseService;

public interface CommunityHealthCenterService extends BaseService<CommunityHealthCenter, String> {

	public CommunityHealthCenter findCommunityHealthCenterById(Map<String, Object> params);

	public List<CommunityHealthCenter> findCommunityHealthCenterAll();

	public List<String> findGroupByAdministrativeRegion();

	public List<CommunityHealthCenter> findByAdministrativeRegion(Map<String, Object> params);

}
