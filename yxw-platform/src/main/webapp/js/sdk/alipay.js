/**
 * alipay js sdk
 */
yxw.print('alipay.js');

/**
 * 关闭网页
 */
yxw.closeWindow = function() {
  AlipayJSBridge.call('closeWebview');
}

/**
 * 隐藏右上角菜单
 */
yxw.hideOptionMenu = function() {
  AlipayJSBridge.call("hideOptionMenu");
}

/**
 * 显示右上角菜单
 */
yxw.showOptionMenu = function() {
  AlipayJSBridge.call("showOptionMenu");
}

/**
 * 获取网络状态
 * 
 * @res.networkType string 网络类型 'fail': 无网络/网络断开, 'wifi': wifi网络, 'wwan': 非wifi
 */
yxw.getNetworkType = function(func) {
  AlipayJSBridge.call('getNetworkType', function(res) {
    func(res.networkType);
  });
}

yxw.inited = true;
