package com.yxw.easyhealth.biz.community.service;

import java.util.List;
import java.util.Map;

import com.yxw.commons.entity.mobile.biz.community.CommunityOrganizationSchmag;
import com.yxw.framework.mvc.service.BaseService;

public interface CommunityOrganizationSchmagService extends BaseService<CommunityOrganizationSchmag, String> {

	Map<String, Object> deleteOrganizaion(Map<String, Object> params);

	Map<String, Object> updateOrganizaion(CommunityOrganizationSchmag organizationSchmag);

	Map<String, Object> addOrganizaion(CommunityOrganizationSchmag organizationSchmag);

	List<CommunityOrganizationSchmag> findOrganizaionByCondition(Map<String, Object> params);

	List<CommunityOrganizationSchmag> findCommunityOrganizationSchmagAll();

	CommunityOrganizationSchmag findCommunityOrganizationSchmagById(Map<String, Object> params);

}
