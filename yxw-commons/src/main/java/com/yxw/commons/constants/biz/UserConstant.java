package com.yxw.commons.constants.biz;

public class UserConstant {
	public static final String LOGINED_USER = "loginedUser";// session中的登录用户KEY
	public static final String LOGINED_USER_AUTHORIZATION = "loginedUserAuthorization";// session中的登录用户授权信息KEY

	public static final String HOSPITAL_LIST = "hospitalList";// session中的登录用户的管理医院

	/**no 不可用*/
	public static final int AVAILABLE_NO = 0;
	/**yes 可用*/
	public static final int AVAILABLE_YES = 1;

	/**登录页面入口*/
	public static final String LOGIN_URL = "/login/user_tologin";
	/**是否自动登录;0否;1是;*/
	public static final int IS_REMEMBER_ME = 1;
	/**自动登录cookie超时时间*/
	public static final int AUTOLOGIN_TIMEOUT = 7 * 24 * 60 * 60;// cookie保存时间

	/**用户默认密码*/
	public static final String USER_DEFAULT_PASSWD = "yx123456";
	/**用户默认盐值*/
	public static final String USER_DEFAULT_SALT = "yxw129";
	/**盐值默认长度*/
	public static final int SALT_DEFAULT_LENGTH = 6;

	/**自动登录key*/
	public static final String IS_REMEMBER_ME_KEY = "yxw129l";

	/**资源类型1菜单;2;按钮;3普通链接;4医院*/
	public static final int RESOURCE_TYPE_LINK = 3;
	public static final int RESOURCE_TYPE_HOSPITAL = 4;
}
