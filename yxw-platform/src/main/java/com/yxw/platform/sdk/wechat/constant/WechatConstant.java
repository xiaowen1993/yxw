package com.yxw.platform.sdk.wechat.constant;

/**
 * 微信SDK常量
 * 
 * @author luob
 * @date 2015年6月8日
 * */
public class WechatConstant {

	public static String PARENT_APPID = "wx80067f3a459f679e";

	public static String PARENT_APPSECRET = "6ff0b66d4e5dbe8e3e4e32498a1e0096";

	public static String WECHAT = "wechat";

	/**
	 * 异常提示信息
	 * */
	public static String WECHAT_EXCEPTION_TIP = "请求处理异常，请稍候尝试！";

	/**
	 * 默认关键字回复
	 * */
	// public static String WECHAT_DEFAULT_TIP = "您发送的是无效指令！";

	/**
	 * 默认被关注回复
	 * */
	public static String WECHAT_FOCUS_TIP = "非常感谢您的关注！";

	/*********************************** URL *************************************************/
	/*********************************** 菜单 *************************************************/
	/**
	 * 创建自定义菜单
	 * */
	public static String MENU_CRAETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	/**
	 * 获取自定义菜单
	 * */
	public static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=";
	/**
	 * 删除自定义菜单
	 * */
	public static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";

	/*********************************** 模版消息 *************************************************/
	/**
	 * 发送模版消息
	 * */
	public static String SEND_TEMPLATE_MSG = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
	/**
	 * 获取模版Id
	 * */
	public static String GET_TEMPLATE_ID = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=";

	/*********************************** 客服消息 *************************************************/
	/**
	 * 发送客服消息
	 * */
	public static String SEND_KF_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	/**
	 * 获取添加客服账户
	 * */
	public static String ADD_KFACCOUNT = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=";
	/**
	 * 修改客服账户
	 * */
	public static String EDIT_KFACCOUNT = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=";
	/**
	 * 删除客服账户
	 * */
	public static String DELETE_KFACCOUNT = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=";

	/*********************************** 素材 *************************************************/
	/**
	 * 上传临时素材
	 * */
	public static String UPLOAD_TEMP_METERIAL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
	/**
	 * 上传微信图文图片
	 * */
	public static String UPLOAD_MixedMETERIAL_PIC = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	/**
	 * 上传永久素材
	 * */
	public static String UPLOAD_PARMANENT_METERIAL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=";

	/*********************************** 素材 *************************************************/
	/**
	 * 生成推广二维码
	 * */
	public static String QRCODE_CREATE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";

	/**
	 * 获取推广二维码
	 * */
	public static String SHOW_CREATE = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";

	/***********************************************************************************************/
}
