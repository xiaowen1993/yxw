package com.yxw.platform.privilege.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.cache.core.serve.ServeComm;
import com.yxw.commons.constants.biz.UserConstant;
import com.yxw.commons.constants.cache.CacheType;
import com.yxw.commons.entity.platform.privilege.Role;
import com.yxw.commons.entity.platform.privilege.User;
import com.yxw.commons.entity.platform.privilege.UserRole;
import com.yxw.commons.vo.platform.privilege.UserVo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.framework.mvc.controller.RespBody;
import com.yxw.framework.mvc.controller.RespBody.Status;
import com.yxw.framework.mvc.dao.BaseDao;
import com.yxw.framework.mvc.service.impl.BaseServiceImpl;
import com.yxw.platform.privilege.dao.RoleDao;
import com.yxw.platform.privilege.dao.UserDao;
import com.yxw.platform.privilege.dao.UserRoleDao;
import com.yxw.platform.privilege.service.UserService;
import com.yxw.platform.privilege.utils.PasswdUtil;
import com.yxw.platform.privilege.utils.RandomUtil;
import com.yxw.utils.StringUtils;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private RoleDao roleDao;

//	@Autowired
//	private UserRoleCache userRoleCache;
	private ServeComm serveComm = SpringContextHolder.getBean(ServeComm.class);

	@Override
	protected BaseDao<User, String> getDao() {
		return userDao;
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#updateAvailable(com.yxw.platform.privilege.entity.User)
	 */
	@Override
	public void updateAvailable(User user) {
		userDao.updateAvailable(user);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#findByUserName(java.lang.String)
	 */
	@Override
	public User findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#findListVoByPage(java.util.Map, com.github.pagehelper.Page)
	 */
	@Override
	public PageInfo<UserVo> findListVoByPage(Map<String, Object> parms, Page<UserVo> page) {
		return userDao.findListVoByPage(parms, page);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#batchAvaiable(java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public void batchAvaiable(String ids, Integer available, String createrId) {
		User user = null;
		for (String id : ids.split(",")) {
			user = new User();
			user.setId(id);
			user.setAvailable(available);
			user.setEp(createrId);
			user.setEt(new Date());

			this.updateAvailable(user);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.framework.mvc.service.impl.BaseServiceImpl#batchDelete(java.util.List)
	 */
	@Override
	public void batchDelete(List<String> ids) {
		for (String id : ids) {
			userRoleDao.deleteByUserId(id);
			this.deleteById(id);

			//删除用户角色缓存
			// userRoleCache.delUserRole(id);
			List<Object> params = new ArrayList<Object>();
			
			params.add(new String[] {id});
			serveComm.delete(CacheType.USER_ROLE_CACHE, "delUserRole", params);
		}
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#add(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RespBody add(String userName, String password, String rePassword, String fullName, String email, String memo, Integer available,
			String organizationId, String roleIds, String createrId) {

		User result = this.findByUserName(userName);

		if (result != null && result.getId() != null) {
			return new RespBody(Status.ERROR, "用户名已使用");
		}

		//添加用戶
		String salt = RandomUtil.RandomString(UserConstant.SALT_DEFAULT_LENGTH);
		String enPasswd = PasswdUtil.getPwd(password, salt);
		User user = new User();
		user.setUserName(userName);
		user.setSalt(salt);
		user.setPassword(enPasswd);
		user.setFullName(fullName);
		user.setEmail(email);
		user.setAvailable(available);
		user.setMemo(memo);
		user.setOrganizationId(organizationId);
		user.setCp(createrId);
		user.setCt(new Date());
		super.add(user);

		String userId = user.getId();

		if (org.apache.commons.lang3.StringUtils.isNotBlank(roleIds)) {
			//添加用戶角色
			Set<String> roleIdSet = StringUtils.strToSet(roleIds);
			List<UserRole> userRoleList = new ArrayList<UserRole>();

			for (String roleId : roleIdSet) {
				UserRole userRole = new UserRole(userId, roleId);
				userRoleList.add(userRole);
			}
			userRoleDao.batchInsert(userRoleList);

			//添加到緩存
			List<String> roleIdList = Arrays.asList(roleIdSet.toArray(new String[roleIdSet.size()]));
			List<Role> roleList = roleDao.findByIds(roleIdList);
			List<Object> params = new ArrayList<Object>();
			params.add(userId);
			params.add(roleList);
			serveComm.set(CacheType.USER_ROLE_CACHE, "updateUserRole", params);
			// userRoleCache.updateUserRole(userId, roleList);
		}

		return new RespBody(Status.OK);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#update(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RespBody update(String id, String userName, String fullName, String email, String memo, Integer available, String organizationId,
			String roleIds, String isResetPwd, String createrId) {
		User user = this.findById(id);

		User tmpUser = this.findByUserName(userName);
		if (tmpUser != null && !StringUtils.equals(tmpUser.getId(), user.getId())) {
			return new RespBody(Status.ERROR, "用户名已使用");
		}

		//重置密码
		if (org.apache.commons.lang3.StringUtils.isNotBlank(isResetPwd)) {
			String salt = RandomUtil.RandomString(UserConstant.SALT_DEFAULT_LENGTH);
			String enPasswd = PasswdUtil.getPwd(UserConstant.USER_DEFAULT_PASSWD, salt);
			user.setSalt(salt);
			user.setPassword(enPasswd);
		}

		user.setUserName(userName);
		user.setFullName(fullName);
		user.setEmail(email);
		user.setAvailable(available);
		user.setMemo(memo);
		user.setOrganizationId(organizationId);
		user.setEp(createrId);
		user.setEt(new Date());
		super.update(user);

		//先删除数据
		String userId = user.getId();
		userRoleDao.deleteByUserId(userId);
		// userRoleCache.delUserRole(userId);
		List<Object> params = new ArrayList<Object>();
		params.add(new String[] {userId});
		serveComm.delete(CacheType.USER_ROLE_CACHE, "delUserRole", params);
		
		if (org.apache.commons.lang3.StringUtils.isNotBlank(roleIds)) {
			//添加用戶角色
			Set<String> roleIdSet = StringUtils.strToSet(roleIds);
			List<UserRole> userRoleList = new ArrayList<UserRole>();

			for (String roleId : roleIdSet) {
				UserRole userRole = new UserRole(userId, roleId);
				userRoleList.add(userRole);
			}
			userRoleDao.batchInsert(userRoleList);

			//添加到緩存
			List<String> roleIdList = Arrays.asList(roleIdSet.toArray(new String[roleIdSet.size()]));
			List<Role> roleList = roleDao.findByIds(roleIdList);
			// userRoleCache.updateUserRole(userId, roleList);
			params.clear();
			params.add(userId);
			params.add(roleList);
			serveComm.set(CacheType.USER_ROLE_CACHE, "updateUserRole", params);
		}

		return new RespBody(Status.OK);
	}

	/* (non-Javadoc)
	 * @see com.yxw.platform.privilege.service.UserService#modifyPwd(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public RespBody modifyPwd(User user, String oldpwd, String newpwd, String renewpwd, String createrId) {

		if (!PasswdUtil.checkPasswd(user.getPassword(), oldpwd, user.getSalt())) {
			return new RespBody(Status.ERROR, "原密码不正确");
		}
		String salt = RandomUtil.RandomString(UserConstant.SALT_DEFAULT_LENGTH);
		String md5Pwd = PasswdUtil.getPwd(newpwd, salt);

		user.setSalt(salt);
		user.setPassword(md5Pwd);
		user.setEp(user.getId());
		user.setEt(new Date());
		super.update(user);

		return new RespBody(Status.OK);
	}

}
