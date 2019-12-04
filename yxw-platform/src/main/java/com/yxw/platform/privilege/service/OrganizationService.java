package com.yxw.platform.privilege.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.privilege.entity.Organization;
import com.yxw.platform.privilege.vo.OrganizationVo;

/**
 * @Package: com.yxw.platform.privilege.service
 * @ClassName: OrganizationService
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年8月31日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public interface OrganizationService extends BaseService<Organization, String> {
	public List<Organization> findByName(String name);

	public Organization findByCode(String code);

	/**
	 * 自定义分页
	 * @param parms
	 * @param page
	 * @return
	 */
	public PageInfo<OrganizationVo> findListVoByPage(Map<String, Object> parms, Page<OrganizationVo> page);
}
