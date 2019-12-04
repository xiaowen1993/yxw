/**
 * 健康易   依赖common.js
 */

var IS = {};
//IS.isWeiXin = /MicroMessenger/.test(navigator.userAgent); //是否是微信
//IS.isAlipay = /Alipay/.test(navigator.userAgent); //是否是支付宝
IS.isMacOS = /yx-ios/.test(navigator.userAgent); //是否是IOS
IS.isAndroid = /yx-android/.test(navigator.userAgent); //是否是安卓
//IS.isMiui = /Miui|XiaoMi/.test(navigator.userAgent); //是否是小米手机
//下拉选择
function select_box(obj){
    var option = obj.children[obj.selectedIndex];
    var html = option.innerHTML;
    obj.previousElementSibling.querySelector('.text').innerHTML = html;
}



//性别切换
function switchSex(obj){
    $(obj).addClass('active').siblings().removeClass('active')
}

//跳转
/** go(url,Enabled)
 * 参数1 url 必须  说明：跳转url
 * 参数2 Enabled 可选  说明：当为真是会调用app的方法执行跳转
 * 参数3 type 可选  说明：详见app文档
* */
function go(url,Enabled){
    if(url){
        if(IS.isMacOS && Enabled){
            try
            {
                window.appGotoView(url);
            } catch (e) {
              //  alert('IOS的方法出错');
            }

        }else if(IS.isAndroid && Enabled){
            try
            {
                window.yx129.appGotoView(url);
            } catch (e) {
             //   alert('Android的方法出错');
            }
        }else{
            window.location = url;
        }
    }
}

//注册跳转，忘记密码跳转
/** loginGoView(url)
 * 参数1 url 必须  说明：跳转url
* */
function loginGoView(url){
    if(url){
        if(IS.isMacOS){
            try
            {
                window.appLoginGoView(url);
            } catch (e) {
              //  alert('IOS的方法出错');
            }

        }else if(IS.isAndroid){
            try
            {
                window.yx129.appLoginGoView(url);
            } catch (e) {
             //   alert('Android的方法出错');
            }
        }else{
            window.location.href = url;
        }
    }
}

//关闭此界面返回上一层 --调用壳方法
function goBackIndex(){
    if(IS.isMacOS){
        try
        {
            closeThisViewFromJs();

        } catch (e) {
           // alert('IOS的方法出错');
        }
    }else if(IS.isAndroid ){
        try
        {
            window.yx129.closeThisViewFromJs();

        } catch (e) {
           // alert('Android的方法出错');
        }

    }else{
           go('../index.html');
    }
}

//回到登录 --调用壳方法
function goBackLogin(){
    if(IS.isMacOS){
        try
        {
            backToLogin();

        } catch (e) {
           // alert('IOS的方法出错');
        }
    }else if(IS.isAndroid ){
        try
        {
            window.yx129.backToLogin();

        } catch (e) {
          //  alert('Android的方法出错');
        }

    }else{
        go('login_index.html');
    }
}

(function($,$Y){
    //初始化
    $Y.ease_init =function(){
        var me = this;

        //调用原生app的方法改变title
        me.setTitle = function(){
            var title = $('title').html();
            //console.log(title);
            if(title){
                if(IS.isMacOS){
                    try
                    {
                        window.appSetTitle(title);
                    } catch (e) {
                      //  alert('IOS的setTitleFromJs方法出错');
                    }
                }else if(IS.isAndroid){
                    try
                    {
                        window.yx129.appSetTitle(title);

                    } catch (e) {
                      //  alert('Android的setTitleFromJs方法出错');
                    }

                }
            }
        };
        setTimeout(function(){me.setTitle()},1000);
    }

    //引导动画-首页、个人中心动画、登录页
    $Y.animationIndexGif = AnimateGif = function(attrName){
        var body = $('body'),
            serverGif = $('.serverGif');
        var html ='';
        html +='<div class="animationBox">'+
            '<div class="bootImage removeOut '+ attrName +'"></div>'+
            '</div>';
        body.append(html);
        body.addClass('gif');
        $(".bg-mask").show();
        setTimeout(function(){
            $('.animationBox ').remove();
            body.removeClass('gif' );
            $(".bg-mask").hide();
        },4500);
    }

    //引导动画 -- 通知中心
    $Y.animationGif = AnimateGif = function(url){
        var body = $('body'),
            serverGif = $('.serverGif');
        t = new Date().getTime(),
            url = url +'?'+t;

        var html ='';
        html +='<div class="animationBox">'+
            '<div class="bootImage removeOut changeIamges"><img src="'+url+'" width="150" height="223"/></div>'+
            '</div>';
        body.append(html);
        body.addClass('gif');
        serverGif.attr('onclick','');
//        setTimeout(function(){
//            $('.animationBox ').remove();
//            body.removeClass('gif' );
//            serverGif.attr('onclick','$Y.animationGif();');
//        },5000);
    }


}(window.jQuery,$Y))
$(function(){
    $Y.ease_init();
})




