/**
 * Created by leakl on 2015-05-22.
 */

var IS = {};
//IS.isWeiXin = /MicroMessenger/.test(navigator.userAgent); //是否是微信
//IS.isAlipay = /Alipay/.test(navigator.userAgent); //是否是支付宝
IS.isMacOS = /yx-ios/.test(navigator.userAgent); //是否是IOS
IS.isAndroid = /yx-android/.test(navigator.userAgent); //是否是安卓
//IS.isMiui = /Miui|XiaoMi/.test(navigator.userAgent); //是否是小米手机

//加载皮肤控制JS
$.getScript(window.location.origin+'/yxw.app/js/common/setSkinColor.js',function () {
    var href = $('#yxColor').attr('href')
    if(href){
		 href= href.match(/yxColor\S+/)
   // console.info('主色调:',href)

		 if(href && href[0]){
			    var value = href[0].split('=')
				if(value[1]){
					//console.info('主色调:',value[1])
					setSkinColor(value[1])
				}
		 }
     
    }else{
        console.info('没有找到自定义主色调，使用默认主色调')
    }

})



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
/** go(url,[Enabled])
 * @param {string|function}  跳转url或执行方法
 * @param {Boolean}  说明：当为真是会调用app的方法执行跳转
 * @return null
 * */
function go(url,Enabled){
    if(url){
        if(typeof url =='function'){
            go();
            return false;
        }
        if(IS.isMacOS && Enabled){
            try
            {
                appGotoView(url);
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
                appLoginGoView(url);
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
//获取当前城市
function getCity() {
    if(IS.isMacOS){
        try
        {
            return appGetCity();

        } catch (e) {
            // alert('IOS的方法出错');
        }
    }else if(IS.isAndroid){
        try
        {
            return window.yx129.appGetCity();

        } catch (e) {
            //  alert('Android的方法出错');
        }

    }else{
        return {"ret_code":0,"ret_msg":"APP的方法没调起来"}
    }
}

//设置当前城市
function setCity(city) {
    if(IS.isMacOS){
        try
        {
            appSetCity(city);

        } catch (e) {
        }
    }else if(IS.isAndroid){
        try
        {
            window.yx129.appSetCity(city);

        } catch (e) {
        }

    }
}


(function(){
    window.$Y = function(){};
    //初始化
    $Y.init =function(){
        var me = this;

        //调用原生app的方法改变title
        me.setTitle = function(){
            var title = $.trim($('title').html());
            //console.log(title);
            if(title){
                if(IS.isMacOS){
                    try
                    {
                        appSetTitle(title);
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

        //版权位置渲染
        me.setCopyrightPosition = function(){
            var wHeight = window.innerHeight;
            var bodyHeight = $('body').outerHeight();
            var copyrightHeight = 44;
            if(bodyHeight < wHeight){
                var h = wHeight-copyrightHeight;
                $('#body').css({'min-height':h});
            }
            $('#copyright').show();
        };
        // me.setCopyrightPosition();   不显示版权


        //下拉初始化
        $('.select_box').each(function(){
            var o = $(this);
            var index = o.find('select')[0].selectedIndex;
            var text = o.find('select option').eq(index).html();
            o.find('span.text').html(text)
        })


        //绑定全局单选

        $(document).on('click','.radio-list li',function(){
            var radio = $(this).not(".disable").find('.icon-radio');
            if(radio.size()>0){
                $(this).addClass('active').siblings();
                $(this).siblings().each(function(){
                    var r = $(this).find('.icon-radio');
                    if(r.size()>0){
                        $(this).removeClass('active');
                    }
                })
            }

        });
        //绑定全局复选框
        //$('.check-list').on('click','li',function(){
        //        if( $(this).hasClass('active')){
        //            $(this).removeClass('active');
        //        }else{
        //            $(this).addClass('active')
        //        }
        //
        //
        //});

        //绑定全局 是否按钮
        $(document).on('click','.icon-switch',function(){
            if($(this).hasClass('icon-switch-right')){
                $(this).removeClass('icon-switch-right')
            }else{
                $(this).addClass('icon-switch-right')
            }
        })

//        $Y.hover();
    }



    /**
     *  loading 加载菊花
     * 例子 ：$Y.loading.show();
     $Y.loading.show('加载中');
     $Y.loading.hide();

     */
    $Y.loading = {
        setHTML: function (str) {
            var htmltext = '<div class="am-loading am-loading-show" id="loading_warp">'+
                '<div class="am-loading-mark"></div><div class="am-loading-text">'+
                '<span class="am-icon-loading"></span><b id="am-loading_txt" style="font-weight: normal">'+
                str+
                '</b></div></div>';
            $('#body').append(htmltext);
        },
        show:function(str){
            var str = str || '';
            if($('#loading_warp').length>0){
                $('#am-loading_txt').html(str);
                $('#loading_warp').show();
            }else{
                this.setHTML(str);
            }
        },
        hide:function(){
            $('#loading_warp').hide();
            $('#am-loading_txt').html('加载中');
        }
    }


    $Y.mask =function(){};
    $Y.mask.show =function(){
        var html = '<div class="myMask" style="position: absolute;z-index: 9999;height: 100%;width: 100%;top: 0;right: 0;bottom: 0;left: 0;box-sizing: border-box;background: transparent"></div>';
        $('#body').append(html);
    }
    $Y.mask.remove = function(){
        $('.myMask').remove();
    }



    /*dialog 弹窗*/

    /**
     * 例子1：  没有按钮，没有标题只有内容的弹窗
     *
     var  myBox  =  new $Y.confirm({
	ok:{
	title:"确定",
	click:function(){        //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
			alert('这是确定')  
		}
	},
	cancel:{                   
	title:"取消",
		click:function(){       //参数可为空, 当为空时默认方法关闭窗体
			alert('这是取消') 
		}
	}
	,callback:function(){ 
			//窗体显示后的回调
			}
	)
	
// ok 和   cancel 可以单独存在（只有一个按钮），或同时存(两个按钮)

     */


    $Y.confirm = function(opts){
        if(typeof opts != "object"){
            return false;
        }
        var d = new Date().getTime();
        this.id = '#dialog'+d;

        var me  = $Y.confirm;
        var op = {};
        op.btnSize = 0;
        op.id =  'dialog'+d;
        me.prototype.init =function () {
            var that = this;
            op.title = opts.title || null;
            op.toolBtn = opts.toolBtn || null;
            op.style = opts.style || '';

            op.ok = opts.ok || {};

            if(op.ok.title){
                op.okTitle = opts.ok.title || '提示';
                op.okEvent = opts.ok.click || this.close;
                op.btnSize++
            }

            op.cancel = opts.cancel || {};
            if(op.cancel.title){
                op.cancelTitle = opts.cancel.title || '取消';
                op.cancelEvent = opts.cancel.click || this.close;
                op.btnSize++
            }

            op.content = opts.content || "";
            op.callback = opts.callback || $.noop();

            var html ="";
            html += '<div id="dialogOpt">';
            html+=  '<div class="dialogOpt_bg"></div>';
            html+=  '<div class="dialogOptCon" id="'+op.id+'" style="'+op.style+'">';
            if(op.title){
                html+= '<div class="optTit">'+ op.title +'</div>';
            }
            html+=  '<div class="optTic">'+ op.content +'</div>';
            if(op.btnSize){
                html+=  '<div class="optBtn">';
                var btnBlock = op.btnSize<2?' btn2-block':'';
                if(op.okTitle) {
                    html += '<div class="btn2 btn2-ok'+btnBlock+'">' + op.okTitle + '</div>';
                }

                if(op.cancelTitle){
                    html+= '<div class="btn2 btn2-cancel'+btnBlock+'">'+op.cancelTitle+'</div>';
                }
            }
            html+=   '</div>';
            html+=    ' </div>';
            html+=   ' </div>';
            $("#body").append(html);
            setTimeout(function(){
                that.bind();
            },250)
        };
        me.prototype.bind  =function(){
            var that = this;
            var okClick = function(){
                if(typeof  op.okEvent =="function"){
                    op.okEvent(that);
                }
            };
            var cancelClick = function(){
                if(typeof  op.cancelEvent =="function"){
                    op.cancelEvent(that);
                }
            };

            $(that.id).find('.btn2-ok').on('click',function(that){okClick(that)})
            $(that.id).find('.btn2-cancel').on('click',function(that){cancelClick(that)})
        }

        me.prototype.close =function(){
            var that = this;
            if($('.dialogOptCon').length<2){
                $('#dialogOpt').remove();
            }else{
                $(that.id).remove();
            }
        }
        me.prototype.content =function(str){
            var that = this;
            $(that.id).find('.optTic').html(str)

        }

        //初始化
        this.init();
    }


    $Y.tips =function(str,time,className){
        var t = null,className = className || '';
        var time = time || 1500;
        if(str){
            var html = '<div id="tips">' +
                '<div class="tips_bg"></div>' +
                '<div class="tips_content '+className+'">' +
                '<div class="tips_content_txt">'+str+'</div></div>' +
                '</div>'
            if($('#tips').size()>0){
                $('#tips .tips_content_txt').html(str);
            }else{
                $('body').append(html)
            }
            setTimeout(function(){
                $('#tips').remove();
            },time)
        }


    }



    /**
     * 名称：增加点击交互效果
     * */
    $Y.hover = function(){
        var me = $Y.hover;
        var obj = null,t=null,t2;
        me.ClassName = 'hover';
        me.init =function(){
            $('.touch,.btn')
                .on("touchstart",$Y.hover.TouchStart)
                .on("touchend",$Y.hover.TouchOut)
        }
        me.TouchStart =function(){
            obj = $(this);
            //me.ClassName =obj.data('hover') || me.ClassName;
            //obj.addClass(me.ClassName);
            me.ClassName =obj.data('hover') || me.ClassName;
            t = setTimeout(function(){
                obj.addClass(me.ClassName);
            },0)

        }
        me.TouchOut =function(){
            if(!obj) return false;
            me.removeTouchClass();

        }
        me.TouchMove =function(e){
            if(!obj) return false;
            clearTimeout(t);
            me.removeTouchClass();
        }
        me.removeTouchClass = function(){
            t2 = setTimeout(function(){
                obj.removeClass(me.ClassName);
                clearTimeout(t);
                clearTimeout(t2);
            },150)

        };
        $('body').attr('ontouchmove','$Y.hover.TouchMove()');
        //$('body').attr({'ontouchmove':'$Y.hover.TouchMove(event)','ontouchstart':'$Y.pushLoad.touchstart(event)','ontouchend':'$Y.pushLoad.touchend(event)'} );
        setTimeout(function(){
            $Y.hover.init();
        },200)
    }
    $Y.go = function(url){
        if(url){
            window.location = url;
        }
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


//    //引导动画-首页
//    $Y.animationIndexGif = AnimateGif = function(url){
//        var body = $('body'),
//            serverGif = $('.serverGif'),
//            t = new Date().getTime(),
////            url = url?url + 'images/animation/bootImage.gif?' +t: 'images/animation/bootImage.gif?'+t;
//            url = url +'?'+t;
//
//        var html ='';
//        html +='<div class="animationBox">'+
//            '<div class="bootImage removeOut"><img src="'+url+'" width="200" height="297"/></div>'+
//            '</div>';
//        body.append(html);
//        body.addClass('gif');
//        serverGif.attr('onclick','');
//        setTimeout(function(){
//            $('.animationBox ').remove();
//            body.removeClass('gif' );
//            serverGif.attr('onclick','$Y.animationGif();');
//        },5000);
//    }

    //引导动画 -- 通知中心
    //$Y.animationGif = AnimateGif = function(url){
    //    var body = $('body'),
    //        serverGif = $('.serverGif');
    //    t = new Date().getTime(),
    //        // url = url?url + 'images/animation/bootImage.gif?' +t+type: 'images/animation/bootImage.gif?'+t;
    //        url = url +'?'+t;
    //
    //    var html ='';
    //    html +='<div class="animationBox">'+
    //        '<div class="bootImage removeOut changeIamges"><img src="'+url+'" width="150" height="223"/></div>'+
    //        '</div>';
    //    body.append(html);
    //    body.addClass('gif');
    //    serverGif.attr('onclick','');
    //}

}(window.jQuery))
$(function(){
    $Y.init();
    if(IS.isMacOS){
        FastClick.attach(document.body);
    }
})



