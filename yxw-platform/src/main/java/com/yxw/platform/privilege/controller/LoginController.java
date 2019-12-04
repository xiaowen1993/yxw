package com.yxw.platform.privilege.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.hospital.Hospital;
import com.yxw.commons.entity.platform.privilege.Resource;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.platform.hospital.service.HospitalService;
import com.yxw.platform.privilege.service.UserService;
import com.yxw.platform.privilege.utils.AESUtil;
import com.yxw.platform.privilege.utils.PasswdUtil;
import com.yxw.platform.privilege.vo.AuthorizationVo;
import com.yxw.utils.CookieUtils;
import com.yxw.utils.SCaptcha;

/**
 * 登录控制类
 * 
 * @author luob
 * 
 * modify by huangzy
 * date 2015/08/31 18:19:33
 * */
@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserService userService;

	@Autowired
	private HospitalService hospitalService;

	/**
	 * 跳转到登录页面
	 * */
	@RequestMapping("user_tologin")
	public ModelAndView tologin(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String toUrl = "/login";
		//自动登录
		String enAutoLoginStr = CookieUtils.getCookieVal(request, UserConstant.IS_REMEMBER_ME_KEY);
		if (StringUtils.isNotBlank(enAutoLoginStr)) {
			try {
				String deAutoLoginStr = AESUtil.desEncrypt(enAutoLoginStr);
				String[] strArr = deAutoLoginStr.split(",");
				if (strArr.length == 3) {
					String userName = strArr[0];
					String password = strArr[1];

					//用户密码登录时的IP
					String autoLoginIp = strArr[2];
					if (StringUtils.equals(autoLoginIp, getRemoteAddr(request))) {
						RespBody retRespBody = logining(userName, password, true, request);
						if (retRespBody.getStatus() == Status.OK) {
							toUrl = "redirect:/sys/user/main";
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		ModelAndView view = new ModelAndView(toUrl);
		return view;
	}

	/***
	 * 退出登录
	 * */
	@ResponseBody
	@RequestMapping("user_logout")
	public RespBody logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getSession().removeAttribute(UserConstant.LOGINED_USER);
			CookieUtils.delCookie(request, response, UserConstant.IS_REMEMBER_ME_KEY);

			return new RespBody(Status.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 登录
	 * */
	@ResponseBody
	@RequestMapping("user_login")
	public RespBody login(String username, String password, Integer isRememberMe, String validCode, HttpServletRequest request,
			HttpServletResponse response) {

		if (!checkCode(validCode, request)) {
			return new RespBody(Status.ERROR, "验证码错误，请重新输入");
		}
		RespBody retRespBody = logining(username, password, false, request);
		if (retRespBody.getStatus() == Status.OK) {
			// 设置cookies
			if (isRememberMe != null && isRememberMe == UserConstant.IS_REMEMBER_ME) {
				try {
					//添加自动登录时IP
					String autoLoginAESStr = username + "," + password + "," + getRemoteAddr(request);
					autoLoginAESStr = AESUtil.encrypt(autoLoginAESStr);
					CookieUtils.setCookie(response, UserConstant.IS_REMEMBER_ME_KEY, autoLoginAESStr, UserConstant.AUTOLOGIN_TIMEOUT, null, "/");
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("login", e);
				}

			} else {
				CookieUtils.delCookie(request, response, UserConstant.IS_REMEMBER_ME_KEY);
			}
		}

		return retRespBody;
	}

	/**
	 * 生成验证码
	 * */
	@RequestMapping("getValidCode")
	public void getValidCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		SCaptcha instance = new SCaptcha();
		request.getSession().setAttribute("randCheckCode", instance.getCode());
		instance.write(response.getOutputStream());
	}

	/**
	 * 验证码校验
	 * */
	private boolean checkCode(String validCode, HttpServletRequest request) {
		String randCheckCode = (String) request.getSession().getAttribute("randCheckCode");
		if (validCode != null && validCode.toLowerCase().equals(randCheckCode.toLowerCase())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检查用户名和密码,如果都正确,则返回 new RespBody(Status.OK).
	 * 并做一些登录成功后的数据设置(如设置user到session)
	 * @param userName
	 * @param password
	 * @Param byAutoLogin 是否通过自动登录  登录的
	 * @param request
	 * @return
	 */
	private RespBody logining(String userName, String password, boolean byAutoLogin, HttpServletRequest request) {
		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
			return new RespBody(Status.ERROR, "账户名或密码不能为空");
		}
		User user = userService.findByUserName(userName);
		if (user == null) {
			return new RespBody(Status.ERROR, "用户名或密码错误");
		}

		if (user.getAvailable() == UserConstant.AVAILABLE_NO) {
			return new RespBody(Status.ERROR, "账户已禁用，请联系管理员协助");
		}

		if (!PasswdUtil.checkPasswd(user.getPassword(), password, user.getSalt())) {
			return new RespBody(Status.ERROR, "用户名或密码错误");
		}
		user.setLastLoginIp(getRemoteAddr(request));
		user.setLastLoginTime(new Date());
		userService.update(user);

		request.getSession().setAttribute(UserConstant.LOGINED_USER, user);
		//缓存授权信息
		AuthorizationVo authorizationVo = initAuthorizationVo(user, byAutoLogin);
		request.getSession().setAttribute(UserConstant.LOGINED_USER_AUTHORIZATION, authorizationVo);

		afterSuccessLogin(user, authorizationVo, request);

		return new RespBody(Status.OK);
	}

	/**
	 * 登录成功后的一些额外动作
	 * @param user
	 * @param request
	 */
	private void afterSuccessLogin(User user, AuthorizationVo authorizationVo, HttpServletRequest request) {
		// 设置当前帐号可以管理的医院 2015年6月10日 周鉴斌
		List<Hospital> hospitals = null;
		logger.info(JSON.toJSONString(authorizationVo.getRoleMap()));
		Collection<Resource> resourceList = authorizationVo.getResourceMap().values();
		List<String> codes = new ArrayList<String>();
		for (Resource resource : resourceList) {
			if (resource.getType() == UserConstant.RESOURCE_TYPE_HOSPITAL) {
				codes.add(resource.getAbstr());
			}
		}

		if (CollectionUtils.isNotEmpty(codes)) {
			hospitals = hospitalService.findHospitalByCodes(codes);
		}

		hospitals = hospitals == null ? new ArrayList<Hospital>() : hospitals;

		request.getSession().setAttribute(UserConstant.HOSPITAL_LIST, hospitals);
	}

	/**
	 * 
	 * @param user
	 * @param byAutoLogin 是否通过自动登录登录的
	 * @return
	 */
	private AuthorizationVo initAuthorizationVo(User user, boolean byAutoLogin) {
		AuthorizationVo authorizationVo = new AuthorizationVo(user.getUserName(), byAutoLogin);

		// List<Role> roleList = userRoleCache.queryRoleByUserId(user.getId());
		List<Role> roleList = null;
		ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);
		List<Object> params = new ArrayList<Object>();
		params.add(user.getId());
		List<Object> results = serveComm.get(CacheType.USER_ROLE_CACHE, "queryRoleByUserId", params);
		
		if (CollectionUtils.isNotEmpty(results)) {
			String source = JSON.toJSONString(results);
			roleList = JSON.parseArray(source, Role.class);
			
			Map<String, Role> roleMap = new HashMap<String, Role>();

			for (Role role : roleList) {
				//跳过停用角色
				if (role.getAvailable() == UserConstant.AVAILABLE_NO) {
					continue;
				}
				roleMap.put(role.getCode(), role);
			}
			authorizationVo.setRoleMap(roleMap);
		}

		return authorizationVo;
	}

	public String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (ip != null && ip.indexOf(",") > 0) {
			ip = ip.split(",")[0];
		}
		return ip;
	}
}
