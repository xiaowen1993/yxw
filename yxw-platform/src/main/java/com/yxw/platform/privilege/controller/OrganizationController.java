/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月21日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.privilege.entity.Organization;
import com.yxw.platform.privilege.service.OrganizationService;
import com.yxw.platform.privilege.vo.OrganizationVo;

/**
 * @Package: com.yxw.platform.privilege.controller
 * @ClassName: OrganizationController
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月21日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */

@Controller
@RequestMapping("/sys/organization")
public class OrganizationController extends BizBaseController<Organization, String> {
	@Autowired
	private OrganizationService organizationService;

	/**
	 * 获取所有组织列表
	 * */
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(value = "search", defaultValue = "") String search) {

		Map<String, Object> params = new HashMap<String, Object>();
		// 设置搜索条件
		params.put("search", "%" + search + "%");
		PageInfo<OrganizationVo> organizationList = organizationService.findListVoByPage(params, new Page<OrganizationVo>(pageNum, pageSize));

		ModelAndView view = new ModelAndView("/sys/privilege/organization/list");
		view.addObject("organizationList", organizationList);
		view.addObject("search", search);
		return view;
	}

	@RequestMapping("toAdd")
	public ModelAndView toAdd() {
		List<Organization> organizationList = organizationService.findAll();
		ModelAndView view = new ModelAndView("/sys/privilege/organization/add");
		view.addObject("organizationList", organizationList);
		return view;
	}

	@RequestMapping("toEdit")
	public ModelAndView toEdit(String id) {
		Organization organization = organizationService.findById(id);
		List<Organization> organizationList = organizationService.findAll();

		ModelAndView view = new ModelAndView("/sys/privilege/organization/edit");
		view.addObject("organization", organization);
		view.addObject("organizationList", organizationList);
		return view;
	}

	@ResponseBody
	@RequestMapping("add")
	public RespBody add(String name, String introduction, String parentId, String code, String memo, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(name)) {
				return new RespBody(Status.ERROR, "名称不能为空");
			}
			if (StringUtils.isEmpty(code)) {
				return new RespBody(Status.ERROR, "编码不能为空");
			}
			List<Organization> tmpOrganizationList = organizationService.findByName(name);
			if (CollectionUtils.isNotEmpty(tmpOrganizationList)) {
				return new RespBody(Status.ERROR, name + " 名称已存在");
			}

			Organization tmpOrganization = organizationService.findByCode(code);
			if (tmpOrganization != null) {
				return new RespBody(Status.ERROR, code + " 编码已存在");
			}

			Organization organization = new Organization(name, introduction, parentId, code, memo);
			organization.setCp(super.getLoginUser(request).getId());
			organization.setCt(new Date());

			organizationService.add(organization);

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "新增失败，数据插入异常！");
		}
	}

	@ResponseBody
	@RequestMapping("update")
	public RespBody update(String id, String name, String introduction, String parentId, String code, String memo, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(name)) {
				return new RespBody(Status.ERROR, "名称不能为空");
			}
			if (StringUtils.isEmpty(code)) {
				return new RespBody(Status.ERROR, "编码不能为空");
			}
			Organization organization = organizationService.findById(id);
			if (organization == null) {
				return new RespBody(Status.ERROR, "更新失败,找不到id=" + id);
			}
			List<Organization> tmpOrganizationList = organizationService.findByName(name);
			if (tmpOrganizationList != null) {
				Organization tmpOrganization = tmpOrganizationList.get(0);
				if (!StringUtils.equals(tmpOrganization.getId(), id)) {
					return new RespBody(Status.ERROR, "更新失败 " + name + " 名称已存在");
				}
			}

			Organization tmpOrganization = organizationService.findByCode(code);
			if (tmpOrganization != null && !StringUtils.equals(tmpOrganization.getId(), id)) {
				return new RespBody(Status.ERROR, "更新失败 " + code + " 编码已存在");
			}

			organization.setName(name);
			organization.setIntroduction(introduction);
			organization.setParentId(parentId);
			organization.setCode(code);
			organization.setMemo(memo);
			organization.setEp(super.getLoginUser(request).getId());
			organization.setEt(new Date());

			organizationService.update(organization);

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "更新失败，数据更新异常！");
		}
	}

	@ResponseBody
	@RequestMapping("batchDelete")
	public RespBody batchDelete(String[] ids) {
		try {
			organizationService.batchDelete(Arrays.asList(ids));
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "批量删除失败，数据更新异常！");
		}
	}

	@Override
	protected BaseService<Organization, String> getService() {
		return organizationService;
	}

}
