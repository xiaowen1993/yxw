/**
 * Created by leakl on 2015-05-04.
 */
(function($){
    function Y(){};
    /**
     * 全局弹窗
     *   var box = new $Y.dialog({
            title:title,
            height:'252px',
            width:'444px',
            mark:false,
            content:'<div class="loading_w"><span class="icon-loading"></span>加载中...</div>',
            callback:function(){}
            });
     * */
    Y.dialog =function(opts){
        var id2 ='dialog_w'+new Date().getTime();
        this.title =opts.title||'提示';
        this.hideHeader =opts.hideHeader||'';
        this.content =opts.content||'';
        this.width =opts.width||'450px';
        this.height =opts.height||'300px';
        this.callback =opts.callback||function(){};
        this.zIndex = $('.dialog_main').css('z-index')||60;
        this.style =opts.style||'';
        this.button = opts.button || false;
        this.mark = opts.mark == false ?opts.mark:true;
        this.id= '#'+id2;
        this.id2= id2;
        this.init(id2);
        this.buttonClick = opts.buttonClick || function(){$Y.dialog.close('#'+this.id+'')};
        //关闭弹窗
        this.close =opts.close||function(){
            var me = this;
            if($('.dialog_main').size()<2){
                $(me.id).closest('.dialog_w').remove();
            }else{
                $(me.id).remove();
            }
        };
        Y.dialog.close =function(id){
            if(id){
                if($('.dialog_main').size()<2){
                    $(id).closest('.dialog_w').remove();
                }else{
                    $(id).remove();
                }
            }

        }
        //修改弹窗内容
        this.content =function(str){
            var me = this;
            $(me.id).find('.dialog_content').html(str);
        };
    };
    //弹窗初始化
    Y.dialog.prototype.init =function(id){
        var me = this;
        var is_shaow = me.mark?'no_shadow':'';
        var Has =$('.dialog_w').size()?true:false;
        this.zIndex ++;
        var html ='',html2;
        html2='<div class="dialog_main animation '+is_shaow+'" id="'+id+'"  style="width: '+this.width+';height: '+this.height+';z-index:'+this.zIndex+';margin:auto;'+this.style+'">';
        if(!me.hideHeader){
            html2 +='<div class="dialog_title_w">';
        }else{
            html2 +='<div class="dialog_title_w" style="display: none">';
        }
        html2+='<div class="title">'+this.title+'</div>'+
            '<a class="btn_close" href="javascript:;" onclick="$Y.dialog.close(\'#'+id+'\')">X</a>'+
            '</div>'+
            '<div class="dialog_content">'+
            this.content +    // 内容
            '</div>';
                if(me.button){
                    html2+='<div style="position: absolute;bottom: 0;left: 0;right: 0" class="controlsBtnBox rowBg center"> <button class="btn-save" id="js-id-'+me.id2+'">保存</button> <div class="spaceW15"></div> <button class="btn-remove" onclick="$Y.dialog.close(\'#'+id+'\')">取消</button> </div>'
                    setTimeout(function(){
                        $('#js-id-'+me.id2).on('click',me.buttonClick);
                    },200)
                }
        html2+='</div>';
        if(!Has){
            html+='<div class="dialog_w">';
            if(this.mark){
                html += '<div class="dialog_bg"></div>'
            }
            html+= html2;
            html+='</div>';
            $('body').append(html);
        }else{
            html = html2;
            $('.dialog_w').append(html);

        }
        setTimeout(function(){
            me.callback(me);

        },100)
    };


    /**
     * 启用自定义滚动条
     * */
    Y.ScrollBar =function(type,Dom){
        if(Dom){
            $(Dom).each(function(){
                $(this).mCustomScrollbar({
                    theme:'minimal-dark',
                    axis:type||'y' // horizontal scrollbar
                });
            })
        }else{
            $(".mCustomScrollbar_u").each(function(){
                $(this).mCustomScrollbar({
                    theme:'minimal-dark',
                    axis:type||'y' // horizontal scrollbar
                });
            })
        }
    };

    /**
     * 名称： 提示信息
     * 例子： $Y.tips(string || string ,time)
     * 返回：无
     */
    Y.tips =  function(str,time){
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
    
    
    

    window.$Y =Y;

    /* $Y.confirm ('这里是内容',function(Confirm){
    alert(33) ;
    Confirm.close();
    })
	* confirmContent {string} 内容
	* [callback] {function} 确定回调
	* */
		
	$Y.confirm = function(confirmContent,callback){
		var confirmContent = confirmContent || '';
		var callback = callback || function(){ $Y.confirm_box.close()};
		$Y.confirm_box = new $Y.dialog({
		title:'提示',
		hideHeader:'hide',
		button:true,
		buttonClick:function(){callback($Y.confirm_box)},
		height:'200px',
		width:'350px',
		mark:false,
		content:'<div style="padding: 50px 20px 20px;text-align: center;">'+confirmContent+'</div>',
	});
	}
}(window.jQuery));
