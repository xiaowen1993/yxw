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
                $('.headerSelectNav').css('height','auto')
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
            },300)
        }else{
            $('#js-navView').show();
            $('#js-navList').hide();
            $('#js-navContent').show();
            $('body').css({'background-color':'#eee'})
            $('.headerSelectNav').css('height','auto')
        }

    })
})

function showNavList(){
    var userId = $('#js-navView .js-value').attr('userId');
    $('#js-navList li[userId='+userId+']').addClass('active');
    $('#js-navView').hide();
    $('#js-navList').show();
    //$('#js-navContent').hide();
    $('body').css({'background-color':'#fff'});
    $('.headerSelectNav').css('height','100%')
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


