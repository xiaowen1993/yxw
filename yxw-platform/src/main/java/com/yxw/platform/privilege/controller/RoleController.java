package com.yxw.platform.privilege.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.privilege.service.ResourceService;
import com.yxw.platform.privilege.service.RoleResourceService;
import com.yxw.platform.privilege.service.RoleService;

@Controller
@RequestMapping("/sys/role")
public class RoleController extends BizBaseController<Role, String> {

	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private RoleResourceService roleResourceService;

	@Override
	protected BaseService<Role, String> getService() {
		return roleService;
	}

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize, @RequestParam(value = "search", required = false,
			defaultValue = "") String search, ModelMap modelMap) {

		Map<String, Object> params = new HashMap<String, Object>();
		// 设置搜索条件
		params.put("search", "%" + search + "%");
		PageInfo<Role> roleList = roleService.findListByPage(params, new Page<Role>(pageNum, pageSize));

		ModelAndView view = new ModelAndView("/sys/privilege/role/list");
		view.addObject("roleList", roleList);
		view.addObject("search", search);

		return view;
	}

	@ResponseBody
	@RequestMapping("add")
	public RespBody add(String name, String code, Integer available, String memo, String resourceIds, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(name)) {
				return new RespBody(Status.ERROR, "名称不能为空");
			}
			if (StringUtils.isEmpty(code)) {
				return new RespBody(Status.ERROR, "编码不能为空");
			}
			if (available == null) {
				return new RespBody(Status.ERROR, "是否可用不能为空");
			}

			RespBody respBody = roleService.add(name, code, available, memo, resourceIds, super.getLoginUser(request).getId());

			return respBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "新增失败，数据插入异常！");
		}
	}

	@ResponseBody
	@RequestMapping("update")
	public RespBody update(String id, String name, String code, Integer available, String memo, String resourceIds, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(name)) {
				return new RespBody(Status.ERROR, "名称不能为空");
			}
			if (StringUtils.isEmpty(code)) {
				return new RespBody(Status.ERROR, "编码不能为空");
			}
			if (available == null) {
				return new RespBody(Status.ERROR, "是否可用不能为空");
			}

			RespBody respBody = roleService.update(id, name, code, available, memo, resourceIds, super.getLoginUser(request).getId());

			return respBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "更新失败，数据更新异常！");
		}
	}

	@RequestMapping("toAdd")
	public ModelAndView toAdd() {
		ModelAndView view = new ModelAndView("/sys/privilege/role/add");

		List<Resource> resourceList = resourceService.findAll();
		view.addObject("resourceList", resourceList);

		return view;
	}

	@ResponseBody
	@RequestMapping("batchDelete")
	public RespBody batchDelete(String[] ids) {
		try {

			List<String> idList = Arrays.asList(ids);

			roleService.batchDelete(idList);

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "批量删除失败，数据更新异常！");
		}
	}

	@RequestMapping("toEdit")
	public ModelAndView toEdit(String id) {
		ModelAndView view = new ModelAndView("/sys/privilege/role/edit");

		List<Resource> checkResourceList = roleResourceService.findResourceByRoleId(id);
		//转成set
		Set<String> checkResourceIdSet = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(checkResourceList)) {
			for (Resource resource : checkResourceList) {
				checkResourceIdSet.add(resource.getId());
			}
		}
		view.addObject("checkResourceIdSet", checkResourceIdSet);

		Role role = roleService.findById(id);
		view.addObject("role", role);

		List<Resource> resourceList = resourceService.findAll();
		view.addObject("resourceList", resourceList);

		return view;
	}

}
