/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月24日</p>
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.privilege.service.ResourceService;

/**
 * @Package: com.yxw.platform.privilege.controller
 * @ClassName: ResourceController
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月24日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Controller
@RequestMapping("/sys/resource")
public class ResourceController extends BizBaseController<Resource, String> {
	@Autowired
	private ResourceService resourceService;

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize, @RequestParam(value = "search", required = false,
			defaultValue = "") String search, ModelMap modelMap) {

		Map<String, Object> params = new HashMap<String, Object>();
		// 设置搜索条件
		params.put("search", "%" + search + "%");
		PageInfo<Resource> resourceList = resourceService.findListByPage(params, new Page<Resource>(pageNum, pageSize));

		ModelAndView view = new ModelAndView("/sys/privilege/resource/list");
		view.addObject("resourceList", resourceList);
		view.addObject("search", search);

		return view;
	}

	@ResponseBody
	@RequestMapping("add")
	public RespBody add(String name, String code, String abstr, String type, String memo, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(name)) {
				return new RespBody(Status.ERROR, "名称不能为空");
			}
			if (StringUtils.isEmpty(code)) {
				return new RespBody(Status.ERROR, "编码不能为空");
			}
			if (StringUtils.isEmpty(abstr)) {
				return new RespBody(Status.ERROR, "资源不能为空");
			}

			List<Resource> tmpResourceList = resourceService.findByName(name);
			if (CollectionUtils.isNotEmpty(tmpResourceList)) {
				return new RespBody(Status.ERROR, name + " 名称已存在");
			}

			Resource tmpResource = resourceService.findByCode(code);
			if (tmpResource != null) {
				return new RespBody(Status.ERROR, code + " 编码已存在");
			}

			tmpResource = resourceService.findByAbstr(abstr);
			if (tmpResource != null) {
				return new RespBody(Status.ERROR, "更新失败 " + abstr + " 资源抽象已存在");
			}

			Resource resource = new Resource(name, code, abstr, Integer.parseInt(type), memo);

			resource.setCp(super.getLoginUser(request).getId());
			resource.setCt(new Date());

			resourceService.add(resource);

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "新增失败，数据插入异常！");
		}
	}

	@ResponseBody
	@RequestMapping("update")
	public RespBody update(String id, String name, String code, String abstr, String type, String memo, HttpServletRequest request) {
		try {
			if (StringUtils.isEmpty(name)) {
				return new RespBody(Status.ERROR, "名称不能为空");
			}
			if (StringUtils.isEmpty(code)) {
				return new RespBody(Status.ERROR, "编码不能为空");
			}
			if (StringUtils.isEmpty(abstr)) {
				return new RespBody(Status.ERROR, "资源不能为空");
			}

			Resource tmpResource = null;
			List<Resource> tmpResourceList = resourceService.findByName(name);
			if (CollectionUtils.isNotEmpty(tmpResourceList)) {
				tmpResource = tmpResourceList.get(0);
				if (!StringUtils.equals(tmpResource.getId(), id)) {
					return new RespBody(Status.ERROR, "更新失败 " + name + " 名称已存在");
				}
			}

			tmpResource = resourceService.findByCode(code);
			if (tmpResource != null && !StringUtils.equals(tmpResource.getId(), id)) {
				return new RespBody(Status.ERROR, "更新失败 " + code + " 编码已存在");
			}

			tmpResource = resourceService.findByAbstr(abstr);
			if (tmpResource != null && !StringUtils.equals(tmpResource.getId(), id)) {
				return new RespBody(Status.ERROR, "更新失败 " + abstr + " 资源抽象已存在");
			}

			Resource resource = resourceService.findById(id);
			if (resource == null) {
				return new RespBody(Status.ERROR, "更新失败,找不到id=" + id);
			}
			resource.setName(name);
			resource.setCode(code);
			resource.setAbstr(abstr);
			resource.setType(Integer.parseInt(type));
			resource.setMemo(memo);
			resource.setEp(super.getLoginUser(request).getId());
			resource.setEt(new Date());

			resourceService.update(resource);

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
			resourceService.batchDelete(Arrays.asList(ids));
			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "批量删除失败，数据更新异常！");
		}
	}

	@RequestMapping("toAdd")
	public ModelAndView toAdd() {
		ModelAndView view = new ModelAndView("/sys/privilege/resource/add");
		return view;
	}

	@RequestMapping("toEdit")
	public ModelAndView toEdit(String id) {
		Resource resource = resourceService.findById(id);

		ModelAndView view = new ModelAndView("/sys/privilege/resource/edit");
		view.addObject("resource", resource);
		return view;
	}

	@Override
	protected BaseService<Resource, String> getService() {
		return resourceService;
	}

}
