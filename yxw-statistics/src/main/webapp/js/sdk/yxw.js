/**
 * 微信公众账号&支付宝服务窗 JS SDK - 医享网(http://www.yx129.com)
 * 需基于JQuery,请在导入次JS文件之前导入JQuery
 * 
 * @version 1.0
 * @author IT-JAVA homer.yang
 * @since 2015-03-16
 */
// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).format("yyyy-M-d h:m:s.S") ==> 2006-7-2 8:9:4.18
Date.prototype.format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1)
							? (o[k])
							: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/**
 * don't tell you
 */
var yxw = {};

/**
 * 获取项目根路径
 */
yxw.getRootPath = function(projectName) {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPath = curWwwPath.substring(0, pos);
	// var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') +
	// 1);
	projectName = projectName ? projectName : '';
	return (localhostPath + projectName);
}

/**
 * 加载js
 */
yxw.loadjs = function(filename) {
	var fileref = document.createElement('script');
	fileref.setAttribute("type", "text/javascript");
	fileref.setAttribute("src", filename);
	if (typeof fileref != "undefined")
		document.getElementsByTagName("head")[0].appendChild(fileref);
}

/**
 * 输出带时间的信息到浏览器控制台,用于调试
 */
yxw.print = function(s) {
	console.log('[%s] ' + s, new Date().format('yyyy-MM-dd hh:mm:ss.S'));
}

/**
 * 输出带时间的错误信息到浏览器控制台,用于调试
 */
yxw.printerr = function(s) {
	console.error('[%s] ' + s, new Date().format('yyyy-MM-dd hh:mm:ss.S'));
}

/**
 * 输出对象到浏览器控制台
 */
yxw.printobj = function(o) {
	console.dir(o);
}

/**
 * 通过js获取在url后面的参数
 */
yxw.param = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 通过js获取在url 问号后面的一串
 */
yxw.queryString = function() {
	return location.search.slice(1);
}

/**
 * 设置Cookie
 * 
 * @param c_name
 * @param value
 * @param expiredays
 *            过期天数
 */
yxw.setCookie = function(c_name, value, expiredays) {
	expiredays = expiredays ? expiredays : 30;// 默认30天
	var exdate = new Date();
	exdate.setDate(exdate.getDate() + expiredays);
	document.cookie = c_name + "=" + escape(value) + ";expires="
			+ exdate.toGMTString() + ";path=/";
}

/**
 * 获取Cookie
 * 
 * @param c_name
 * @returns
 */
yxw.getCookie = function(c_name) {
	if (document.cookie.length > 0) {
		c_start = document.cookie.indexOf(c_name + "=");
		if (c_start != -1) {
			c_start = c_start + c_name.length + 1;
			c_end = document.cookie.indexOf(";", c_start);
			if (c_end == -1) {
				c_end = document.cookie.length;
			}
			return unescape(document.cookie.substring(c_start, c_end));
		}
	}
	return "";
}

yxw.appId = yxw.param('appId');

/**
 * 浏览器判断模块
 * 
 * @微信样例 Mozilla/5.0 (iPhone; CPU iPhone OS 8_1 like Mac OS X)
 *       AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B411
 *       MicroMessenger/6.0 NetType/WIFI
 * @支付宝样例 Mozilla/5.0 (iPhone; CPU iPhone OS 8_1_3 like Mac OS X)
 *        AppleWebKit/600.1.4 (KHTML, like Gecko) Mobile/12B466 PSDType(1)
 *        AliApp(AP/8.5.3.012402) AlipayClient/8.5.3.012402
 */
yxw.ua = navigator.userAgent.toLowerCase();
yxw.print(yxw.ua);

yxw.inApp = false; // 是否在微信或者支付宝的App内打开的

yxw.inited = false; // 用来标记动态导入的js是否加载完毕
yxw.init = function(func, projectName) {
	yxw.rootPath = yxw.getRootPath(projectName);
	var res = {};
	try {
		if (yxw.ua.indexOf('alipayclient') > 0) {
			yxw.inApp = true;
			// 导入支付宝js
			yxw.loadjs(yxw.rootPath + "/js/sdk/alipay.js");
		} else if (yxw.ua.indexOf('micromessenger') > 0) {
			yxw.inApp = true;
			// 导入微信js
			yxw.loadjs(yxw.rootPath + "/js/sdk/wechat.js");
		} else {
			yxw.loadjs(yxw.rootPath + "/js/sdk/other.js");
		}

		var tempFunc = function() {
			if (yxw.inited) {
				res.success = true;
				try {
					func(res);
				} catch (e) {
				}
			} else {
				setTimeout(tempFunc, 200);
			}
		}
		tempFunc();
	} catch (e) {
		res.success = false;
		res.error = '动态导入脚本出错';
		func(res, e);
	}
}

yxw.printobj(yxw);
