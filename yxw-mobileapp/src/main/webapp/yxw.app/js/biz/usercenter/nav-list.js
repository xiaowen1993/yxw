/**
 * Created by leakl on 2015-06-09.
 */

//全站下拉式菜单  用于记录查询

$(function(){
    $('#js-navList').on('click','li',function(){
        var o = $(this);
        if(o.hasClass('js-item')){
            o.addClass('hover');
            setTimeout(function(){
                o.removeClass('hover');
                $('#js-navView').show();
                $('#js-navList').hide();
                $('body').css({'background-color':'#eee'})
                $('#js-navList li').removeClass('active');
                var val = o.find('.js-title').html();
                var userId = o.attr('userId');
                $('#js-navView .js-value').html(val).attr('userId',userId);
                $Y.loading.show();
                setTimeout(function(){
                    $Y.loading.hide();
                    $('#js-navContent').show();

                },500)
            },300);
            
            /** ------------选择就诊人部分用到-------------- **/ 
            var cardNo = o.attr('data-no');
            var cardType = o.attr('data-type');
            var patName = o.attr('data-name');
            var idNo = o.attr('data-idNo');
            var branchCode = o.attr('data-branchCode');
            var branchId = o.attr('data-branchId');
            var mobile = o.attr('data-mobile');
            var hospitalCode = o.attr('data-hospitalCode');
    		var hospitalId = o.attr('data-hospitalId');
            if (cardNo && cardType) {
            	/*$('#js-navView .js-value').html(val).attr('data-no',cardNo);
            	$('#js-navView .js-value').html(val).attr('data-type',cardType);
            	$('#js-navView .js-value').html(val).attr('data-name',patName);*/
            	$('#selectedCard').attr('data-no', cardNo);
            	$('#selectedCard').attr('data-type', cardType);
            	$('#selectedCard').attr('data-name', patName);
            	$('#selectedCard').attr('data-idNo', idNo);
            	$('#selectedCard').attr('data-branchCode', branchCode);
            	$('#selectedCard').attr('data-branchId', branchId);
            	$('#selectedCard').attr('data-mobile', mobile);
            	$('#selectedCard').attr('data-hospitalCode', hospitalCode);
            	$('#selectedCard').attr('data-hospitalId', hospitalId);
            }
            
            if (typeof loadData === 'function' ) {
            	loadData();
            }
            
            if (typeof $.cookie == 'function') {
            	var openId = $('#openId').val();
            	var appCode = $('#appCode').val();
            	// 设置默认卡 -- dc:default-card --> appCode_openId_dc=XXX
            	if (appCode && appCode != 'null' && openId && openId != 'null') {
            		$.cookie(appCode + '_' + openId + '_dc', o.attr('userId'), {expires: 365, path: '/'});
            	}
            	/*
            	if (appCode == 'easyHealth') {
            		var areaCode = $('#areaCode').val();
            		if (areaCode && areaCode != null) {
            			$.cookie($('#areaCode').val() + '_' + $('#openId').val() + '_defaultCard', o.attr('userId'), {expires: 365, path: '/'}); 
            		}
            	} else {
	            	if (appId && openId && appId != 'null' && openId != 'null' && o.attr('userId')) {
	            		$.cookie($('#appId').val() + '_' + $('#openId').val() + '_defaultCard', o.attr('userId'), {expires: 365, path: '/'}); 
	            	}
            	}
            	*/
            }
        }else{
            $('#js-navView').show();
            $('#js-navList').hide();
            $('#js-navContent').show();
            $('body').css({'background-color':'#eee'})
        }

    })
})

function showNavList(){
    var userId = $('#js-navView .js-value').attr('userId');
    $('#js-navList li[userId='+userId+']').addClass('active');
    $('#js-navView').hide();
    $('#js-navList').show();
    $('#js-navContent').hide();
    $('body').css({'background-color':'#fff'})
}

//tag切换
$(document).on('click','.js-tag .tag ',function(){
    var o  = $(this),index = o.index();;
    o.addClass('active').siblings().removeClass('active');
    var tagContent = $('.js-tagContent');
    tagContent.hide().eq(index).show();

})

//tag 周边生活圈子
$(document).on('click','.js-item-tag li ',function(){
    var o  = $(this),index = o.index();;
    o.addClass('active').siblings().removeClass('active');
    var tagContent = o.parents('#life-main').find('.js-tagContent');
    tagContent.hide().eq(index).show();

})

//手风琴
$(document).on('click','.js-accordion .js-acc-header ',function(){
    $(this).toggleClass('show');
    $(this).next().toggleClass('show');
})

//金额选择
$(function(){
   $('.js_former').click(function(){
        var text = $(this).find('.former').text();
       var input =  $(this).parents('.yx-list').find('.c-input');
       $(this).addClass('active').siblings().removeClass('active');
       input.val(text);
       input.focus(function(){
           $('.js_former').removeClass('active')
       })
    });
})


