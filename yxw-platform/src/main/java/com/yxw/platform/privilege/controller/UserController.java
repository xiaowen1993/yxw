package com.yxw.platform.privilege.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.platform.privilege.UserVo;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.service.BaseService;
import com.yxw.platform.common.controller.BizBaseController;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.privilege.entity.Organization;
import com.yxw.platform.privilege.service.OrganizationService;
import com.yxw.platform.privilege.service.RoleService;
import com.yxw.platform.privilege.service.UserRoleService;
import com.yxw.platform.privilege.service.UserService;
import com.yxw.utils.CookieUtils;

/***
 * 账户管理控制器类
 * 
 * @author luob
 * 
 * modify by huangzy
 * date 2015/09/11 10:16:33
 * */
@Controller
@RequestMapping("/sys/user")
public class UserController extends BizBaseController<User, String> {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private OrganizationService organizationService;

	@Override
	protected BaseService<User, String> getService() {
		return userService;
	}

	/**
	 * 获取所有账户列表
	 * */
	@RequestMapping("list")
	public ModelAndView list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, @RequestParam(
			value = "pageSize", required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(value = "search", defaultValue = "") String search) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 设置搜索条件
		params.put("search", "%" + search + "%");
		PageInfo<UserVo> userList = userService.findListVoByPage(params, new Page<UserVo>(pageNum, pageSize));

		ModelAndView view = new ModelAndView("/sys/privilege/user/list");
		view.addObject("userList", userList);
		view.addObject("search", search);
		return view;
	}

	/**
	 * 批量启用/禁用 账户
	 * 启用1或禁用0
	 * */
	@ResponseBody
	@RequestMapping("batchAvaiable")
	public RespBody batchAvaiable(String ids, Integer available, HttpServletRequest request) {
		try {
			if (available == null || ! ( available == 0 || available == 1 )) {
				return new RespBody(Status.ERROR, "用户状态不正确,更新用户状态失败!");
			}

			userService.batchAvaiable(ids, available, super.getLoginUser(request).getId());

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "启用账户失败，数据更新异常！");
		}
	}

	/**
	 * 批量删除账户
	 * */
	@ResponseBody
	@RequestMapping("batchDelete")
	public RespBody batchDelete(String ids) {
		try {
			String[] idArr = ids.split(",");

			userService.batchDelete(Arrays.asList(idArr));

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "批量删除用户失败，数据更新异常！");
		}
	}

	/**
	 * 新增账户
	 * */
	@ResponseBody
	@RequestMapping("add")
	public RespBody add(String userName, String password, String rePassword, String fullName, String email, String memo, @RequestParam(
			value = "available", defaultValue = "0") Integer available, String organizationId, String roleIds, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(rePassword)) {
				return new RespBody(Status.ERROR, "用户名/密码不能为空");
			}
			if (password.length() < 6) {
				return new RespBody(Status.ERROR, "密码长度不合法");
			}
			if (!password.equals(rePassword)) {
				return new RespBody(Status.ERROR, "两次输入的密码不相同");
			}

			if (StringUtils.isBlank(fullName)) {
				return new RespBody(Status.ERROR, "用户姓名不能为空");
			}

			if (StringUtils.isBlank(organizationId)) {
				return new RespBody(Status.ERROR, "组织/机构/公司不能为空");
			}

			RespBody respBody =
					userService.add(userName, password, rePassword, fullName, email, memo, available, organizationId, roleIds,
							super.getLoginUser(request).getId());

			return respBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "新增账户失败，数据插入异常！");
		}

	}

	/**
	 * 编辑账户
	 * */
	@ResponseBody
	@RequestMapping("update")
	public RespBody update(String id, String userName, String fullName, String email, String memo, @RequestParam(value = "available",
			defaultValue = "0") Integer available, String organizationId, String roleIds, String isResetPwd, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(userName)) {
				return new RespBody(Status.ERROR, "用户名不能为空");
			}

			if (StringUtils.isBlank(fullName)) {
				return new RespBody(Status.ERROR, "用户姓名不能为空");
			}

			if (StringUtils.isBlank(organizationId)) {
				return new RespBody(Status.ERROR, "组织/机构/公司不能为空");
			}

			RespBody respBody =
					userService.update(id, userName, fullName, email, memo, available, organizationId, roleIds, isResetPwd,
							super.getLoginUser(request).getId());

			return respBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "编辑账户失败，数据插入异常！");
		}

	}

	/**
	 * 修改密码
	 * */
	@ResponseBody
	@RequestMapping("modifyPwd")
	public RespBody modifyPwd(String oldpwd, String newpwd, String renewpwd, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 判断用户名是否合法
			if (StringUtils.isBlank(oldpwd)) {
				return new RespBody(Status.ERROR, "原密码不能为空");
			}
			if (StringUtils.isBlank(newpwd) || StringUtils.isBlank(renewpwd)) {
				return new RespBody(Status.ERROR, "新密码不能为空");
			}
			if (!newpwd.equals(renewpwd)) {
				return new RespBody(Status.ERROR, "两次输入的密码不一致");
			}

			User currentUser = (User) request.getSession().getAttribute(UserConstant.LOGINED_USER);

			RespBody respBody = userService.modifyPwd(currentUser, oldpwd, newpwd, renewpwd, super.getLoginUser(request).getId());

			if (respBody.getStatus() == Status.OK) {
				request.getSession().removeAttribute(UserConstant.LOGINED_USER);
				CookieUtils.delCookie(request, response, UserConstant.IS_REMEMBER_ME_KEY);
			}

			return respBody;
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR, "编辑账户失败，数据插入异常！");
		}
	}

	/**
	 * 主页面跳转
	 * */
	@RequestMapping("main")
	public String main() {
		return "/sys/main";
	}

	/**
	 * 新增账户页面跳转
	 * */
	@RequestMapping("toAdd")
	public ModelAndView toAdd(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/sys/privilege/user/add");

		List<Role> roleList = roleService.findAllAvailable();
		view.addObject("roleList", roleList);

		List<Organization> organizationList = organizationService.findAll();
		view.addObject("organizationList", organizationList);

		List<Hospital> allHospitalList = hospitalService.findAll();
		view.addObject("allHospitalList", allHospitalList);

		return view;
	}

	/**
	 * 
	 * toedit账户
	 * */
	@RequestMapping("toEdit")
	public ModelAndView toEdit(String id, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/sys/privilege/user/edit");

		User user = userService.findById(id);
		view.addObject("user", user);

		List<Organization> organizationList = organizationService.findAll();
		view.addObject("organizationList", organizationList);

		List<Role> roleList = roleService.findAllAvailable();
		view.addObject("roleList", roleList);

		List<Hospital> allHospitalList = hospitalService.findAll();
		view.addObject("allHospitalList", allHospitalList);

		List<Role> checkRoleList = userRoleService.findRoleByUserId(id);
		//转成set
		Set<String> checkRoleIdSet = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(checkRoleList)) {
			for (Role role : checkRoleList) {
				checkRoleIdSet.add(role.getId());
			}
		}
		view.addObject("checkRoleIdSet", checkRoleIdSet);
		view.addObject("defaultPasswd", UserConstant.USER_DEFAULT_PASSWD);

		return view;
	}

	/**
	 * TO修改密码
	 * */
	@RequestMapping("toModifyPwd")
	public ModelAndView toModifyPwd() {
		ModelAndView view = new ModelAndView("/sys/privilege/user/password");

		return view;
	}

	/**
	 * 复制账户
	 * */
	@RequestMapping("toCopy")
	public ModelAndView toCopy(String id, ModelMap modelMap, HttpServletRequest request) {
		ModelAndView view = new ModelAndView("/sys/privilege/user/copy");

		User user = userService.findById(id);
		view.addObject("user", user);

		List<Organization> organizationList = organizationService.findAll();
		view.addObject("organizationList", organizationList);

		List<Role> roleList = roleService.findAllAvailable();
		view.addObject("roleList", roleList);

		List<Hospital> allHospitalList = hospitalService.findAll();
		view.addObject("allHospitalList", allHospitalList);

		List<Role> checkRoleList = userRoleService.findRoleByUserId(id);
		//转成set
		Set<String> checkRoleIdSet = new HashSet<String>();
		if (CollectionUtils.isNotEmpty(checkRoleList)) {
			for (Role role : checkRoleList) {
				checkRoleIdSet.add(role.getId());
			}
		}
		view.addObject("checkRoleIdSet", checkRoleIdSet);

		return view;
	}
}
