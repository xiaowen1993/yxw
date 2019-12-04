package com.yxw.platform.privilege.dao;

import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.vo.platform.privilege.UserVo;
import com.yxw.framework.mvc.dao.BaseDao;

public interface UserDao extends BaseDao<User, String> {

	/**
	 * 是否可用(0禁用;1可用)
	 * @param user
	 */
	public void updateAvailable(User user);

	public User findByUserName(String userName);

	/**
	 * 自定义分页
	 * @param params
	 * @param page
	 * @return
	 */
	public PageInfo<UserVo> findListVoByPage(Map<String, Object> params, Page<UserVo> page);
}
