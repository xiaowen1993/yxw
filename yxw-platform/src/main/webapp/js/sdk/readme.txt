yxw.js使用说明书

1.导入此js之前，必须导入JQuery js
2.导入此js的页面，链接必须带appId参数 如：http://www.yx129.com?appId=xxx

--------------------------------------------------------------

属性：
yxw.appId                       当前页面的 appId
yxw.ua                          当前浏览器的 UA
yxw.inApp                       当前页面是否在 微信/支付宝 的 APP 里面打开 (value : true/false)

方法：
yxw.init(func)																yxw.js初始化(func(success) : 回调函数：当success返回true时,才代表yxw.js完全加载完成)
yxw.getRootPath(projectName)                  获取项目跟路径 (projectName: 项目名，因为从脚本获取的项目名称不可靠)
yxw.loadjs(filename)                          加载 js
yxw.print(s)                                  输出带时间的信息到浏览器控制台,用于调试
yxw.printerr(s)                               输出带时间的错误信息到浏览器控制台,用于调试
yxw.printobj(o)                               输出对象到浏览器控制台
yxw.param(name)                               通过 js 获取在 url 后面的参数
yxw.setCookie(c_name, value, expiredays)      设置 Cookie (expiredays: Cookie 的过期天数，不传默认30天)
yxw.getCookie(c_name)                         获取 Cookie

yxw.closeWindow()                             关闭网页
yxw.hideOptionMenu()                          隐藏右上角菜单
yxw.showOptionMenu()                          显示右上角菜单
yxw.getNetworkType(func)                      获取网络状态 (func(networkType) : 回调函数：networkType : 网络类型('fail': 无网络/网络断开,'wifi': wifi网络,'wwan': 非wifi))


--------------------------------------------------------------

ps：
如果需要页面初始化完成之后自动调用 yxw 的方法，建议先调用 yxw.init()
实例：
yxw.init(function(data) {
  if (data.success) {
    yxw.hideOptionMenu();
  }
},'${pageContext.request.contextPath}');

此处如果项目是部署在ROOT目录第二个参数可不传
