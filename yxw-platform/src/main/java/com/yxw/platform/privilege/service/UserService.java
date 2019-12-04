package com.yxw.platform.privilege.service;

import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.platform.privilege.UserVo;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.service.BaseService;

public interface UserService extends BaseService<User, String> {
	public void updateAvailable(User user);

	public User findByUserName(String userName);

	/**
	 * 自定义分页
	 * @param parms
	 * @param page
	 * @return
	 */
	public PageInfo<UserVo> findListVoByPage(Map<String, Object> parms, Page<UserVo> page);

	/**
	 * 批量启用或禁用用户
	 * @param ids
	 * @param available
	 * @param createrId
	 */
	public void batchAvaiable(String ids, Integer available, String createrId);

	public RespBody add(String userName, String password, String rePassword, String fullName, String email, String memo, Integer available,
			String organizationId, String roleIds, String createrId);

	public RespBody update(String id, String userName, String fullName, String email, String memo, Integer available, String organizationId,
			String roleIds, String isResetPwd, String createrId);

	public RespBody modifyPwd(User user, String oldpwd, String newpwd, String renewpwd, String createrId);
}
