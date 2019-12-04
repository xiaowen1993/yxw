/**
 * wechat js sdk
 */
yxw.print('wechat.js');

/**
 * 关闭网页
 */
yxw.closeWindow = function() {
  WeixinJSBridge.invoke('closeWindow', {}, function(res) {
  });
}

/**
 * 隐藏右上角菜单
 */
yxw.hideOptionMenu = function() {
  WeixinJSBridge.call('hideOptionMenu');
}

/**
 * 显示右上角菜单
 */
yxw.showOptionMenu = function() {
  WeixinJSBridge.call('showOptionMenu');
}

yxw.print('load wechat sdk jweixin-1.0.0.js');
yxw.loadjs('http://res.wx.qq.com/open/js/jweixin-1.0.0.js');

/**
 * wechat js sdk 版本1.0.0接口
 */
var jsApiList = 
  [ 
    "onMenuShareTimeline",    "onMenuShareAppMessage",   "onMenuShareQQ",   "onMenuShareWeibo", "startRecord", 
    "stopRecord",             "onVoiceRecordEnd",        "playVoice",       "pauseVoice",       "stopVoice", 
    "onVoicePlayEnd",         "uploadVoice",             "downloadVoice",   "chooseImage",      "previewImage", 
    "uploadImage",            "downloadImage",           "translateVoice",  "getNetworkType",   "openLocation", 
    "getLocation",            "hideOptionMenu",          "showOptionMenu",  "hideMenuItems",    "showMenuItems", 
    "hideAllNonBaseMenuItem", "showAllNonBaseMenuItem",  "closeWindow",     "scanQRCode",       "chooseWXPay", 
    "openProductSpecificView","addCard",                 "chooseCard",      "openCard" 
  ];

// sdk/wechat/jsConfig
$.ajax({
  url : yxw.rootPath + '/sdk/wechat/jsConfig',
  data : {
    appId : yxw.appId,
    url : location.href
  },
  async : true,
  dataType : 'json',
  timeout : 5000,
  type : 'POST',
  error : function(XMLHQ, errorMsg) {
    yxw.printerr(errorMsg);
  },
  success : function(data) {
    yxw.printobj(data);

    wx.config({
      debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
      appId : yxw.appId, // 必填，公众号的唯一标识
      timestamp : data.timestamp, // 必填，生成签名的时间戳
      nonceStr : data.nonceStr, // 必填，生成签名的随机串
      signature : data.signature,// 必填，签名，见附录1
      jsApiList : jsApiList
    // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    });

    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    wx.ready(function() {
      yxw.print('wx.ready');
      /**
       * 关闭网页
       */
      yxw.closeWindow = function() {
        wx.closeWindow();
      }

      /**
       * 隐藏右上角菜单
       */
      yxw.hideOptionMenu = function() {
        wx.hideOptionMenu();
      }

      /**
       * 显示右上角菜单
       */
      yxw.showOptionMenu = function() {
        wx.showOptionMenu();
      }

      /**
       * 获取网络状态
       */
      yxw.getNetworkType = function(func) {
        wx.getNetworkType({
          success : function(res) { // 返回网络类型2g，3g，4g，wifi
            if ('2g' == res.networkType || '3g' == res.networkType || '3g+' == res.networkType || '4g' == res.networkType) {
              func('wwan');
            } else {
              func(res.networkType);
            }
          },
          fail : function(res) {
            func('fail');
          }
        });
      }

      yxw.inited = true;
    });

    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
    wx.error(function(res) {
      yxw.print('wx.error');
      yxw.printobj(res);
    });
  }
});
