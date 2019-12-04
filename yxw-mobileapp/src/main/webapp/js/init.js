
window.$Y = $Y  = function(){};
$Y.isWeiXin = /MicroMessenger/.test(navigator.userAgent); //是否是微信
$Y.isAlipay = /Alipay/.test(navigator.userAgent) //是否是支付宝
$Y.isMacOS = /Mac\s+OS/.test(navigator.userAgent) //是否是支付宝

//定义要运行的组件
$Y.run =function(){

    $Y.UI();
  //  $Y.scroll();
 //   $Y.lazyLoadImage();
  // $Y.imagePreview();
};

/**
 * 名称：初始化全局变量
 * */
$Y.init = function() {
    this.height = $(window).outerHeight();
    this.tips_timer = '';
};

/**
 * 名称：UI初始化
 * 例子：无
 * 返回：无
 * */
$Y.UI=function(){
    this.init();
    var p_bottom = parseInt($('#body').css('padding-bottom').replace(/\D/g,''))+1;
    if($('#body').outerHeight()< $Y.height){
        if( $('#Copyright').length>0){
            var h = $Y.height-60-p_bottom;
            $('#body').css('min-height',h+'px') //定义body的最小高度
        }else{
            var h = $Y.height-p_bottom;
            $('#body').css('min-height',h+'px') //定义body的最小高度
        }

    }
    setTimeout(function(){
        $('#Copyright').show();  //显示版权
    },500)

};

/**
 * 名称： 滚动回调，目的：能同时多个方式绑定到滚动事件上
 * 例子： $Y.scrollAction.push(Function)
 * 返回： 无
 * */
$Y.scroll =function(){
    $Y.scrollAction = [];
    var t = null;
    //绑定滚动事件
    $(window).on('scroll',function(){

        clearTimeout(t);
        t = setTimeout(function(){
            //console.log('滚动完成，开始执行事件',$Y.scrollAction)
            if($Y.scrollAction.length > 0){
                for(var i in $Y.scrollAction){
                    if(typeof  $Y.scrollAction[i] == "function"){
                        $Y.scrollAction[i].call();

                    }
                }
            }
        },500)
    });
};

/**
 * 名称：图片延迟加载
 * 例子：<img lazy-src="xxx.jpg">
 * 返回：无
 */
$Y.lazyLoadImage = function(){
    $Y.lazyLoadImage.timer= null;
    $Y.lazyLoadImage.getOffset = function(img){
        var top = img.offset().top;
        if(top - window.scrollY<$Y.height){
            return true
        }else{
            return false;
        }
    }
    $Y.lazyLoadImage.load(200);
    //绑定到滚动事件
    $Y.scrollAction.push($Y.lazyLoadImage.load);
};

$Y.lazyLoadImage.load =function(time){
    var time = time|| 100;
    clearTimeout($Y.lazyLoadImage.timer);
    $Y.lazyLoadImage.timer= setTimeout(function(){
        $('img[lazy-src]').each(function(){
            if($Y.lazyLoadImage.getOffset($(this))){
                var src = $(this).attr('lazy-src');
                $(this).attr('src',src).removeAttr('lazy-src')
            }
        })
        clearTimeout($Y.lazyLoadImage.timer);
    },time)
}



/**
 * 名称： 提示信息
 * 例子： $Y.tips(string || string ,time)
 * 返回：无
 */
$Y.tips =  function(str,time){
    var time = time || 1500;
    clearTimeout($Y.tips_timer);
    if(str){
        var style ='<style type="text/css" id="y_tips_style">#y_tips_warp{position: fixed; bottom: 50%; width: 100%; z-index: 980; left: 0; text-align: center;}' +
            '#y_tips_body{padding: 6px 10px; background-color: #676767; color: #F9F9F9; font-size: 13px; line-height: 1.5; border-radius: 4px; display: inline-block; max-width: 210px; box-shadow: 0 2px 10px #8C8C8C; opacity: .9;}</style>'
        var html = '<div id="y_tips_warp">' +
            '<span id="y_tips_body">'+str+'</span>' +
            '</div>'
        if($('#y_tips_style').length<1){
            $('head').append(style);
        }
        if($('#y_tips_body').length<1){
            $('body').append(html);
        }else{
            $('#y_tips_body').html(str);
        }

        $Y.tips_timer = setTimeout(function(){
            $('#y_tips_warp').remove();
        },time)
    }
};


$Y.pics =  function(str){
    if(str){
        var style ='<style type="text/css" id="y_tips_style">#y_tips_warp{position: fixed; bottom: 50%; width: 100%; z-index: 980; left: 0; text-align: center;}' +
            '#y_tips_body{padding: 6px 10px; background-color: #676767; color: #F9F9F9; font-size: 13px; line-height: 1.5; border-radius: 4px; display: inline-block; max-width: 210px; box-shadow: 0 2px 10px #8C8C8C; opacity: .9;}</style>'
        var html = '<div id="y_tips_warp">' +
            '<span id="y_tips_body">'+str+'</span>' +
            '</div>'
        if($('#y_tips_style').length<1){
            $('head').append(style);
        }
        if($('#y_tips_body').length<1){
            $('body').append(html);
        }else{
            $('#y_tips_body').html(str);
        }
    }
};

/**
 * 名称： 在微信中查看大图
 * 例子： <img data-index="0" binSrc="xxx.jpg">
 * 返回： 无
 * */
$Y.imagePreview = function() {
    var _fn = function (curSrc, srcList) {
        //curSrc 【srcList 图片数组索引】
        //srcList 图片数组
        WeixinJSBridge.invoke('imagePreview', {
            'current': curSrc,
            'urls': srcList
        });
    }

    //绑定点击事件
    $(document).on('tap','[big-Pic]',function () {
        var isDisable = $(this).attr('big-Pic-disable');
        if(isDisable == 'true'){
            return false;
        }
        if (typeof WeixinJSBridge != "object") {
            $Y.tips('只能在微信浏览器内才能查看大图');
            return;
        }
        var index = '';
        var thisSrc = $(this).attr('big-Pic');
        var onlyone = $(this).attr('onlyone');
        var srcList = [];
        if(onlyone){
            srcList.push(thisSrc);
        }else{
            $('[big-Pic]').each(function (i) {
                var bigPic = $(this).attr('big-Pic');
                if (bigPic) {
                    if(thisSrc == bigPic ){
                        index = i;
                    }
                    srcList.push(bigPic)
                }
            });

        }

        if (!srcList || srcList.length == 0) {
            $Y.tips('图片数据不正确');
            return
        }
        _fn(srcList[index], srcList);
    })
};

/**
 * 名称：实例化运行 $Y
 * 例子：无
 * 返回：无
 * 备注：兼容微信
 * */
$(function(){
    if($Y.isWeiXin){
        //微信
        document.addEventListener("WeixinJSBridgeReady", function(){
            $Y.run();
        });
    }else{
        $Y.run();
    }
});
