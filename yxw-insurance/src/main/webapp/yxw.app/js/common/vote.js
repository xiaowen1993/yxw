/**
 * Created by yuki on 2015-10-07.
 */

$(function(){
    $(".evel_box ul li").click(function(index){
        var index = $(this).index(),
            elemt = $(this).parents('.evel_box').find('li');
        elemt.removeClass('active');
        elemt.slice(0,index+1).addClass('active');
    });

})
function toggleEvent(){
    this.showF = function(){
        $('.evel_box ul li').removeClass('active');
        $('.vote-w').show();
        $('.js_know').show();
        $('.js_tijiao').hide();
        $('.frm-box').show()

    }
    this.showS = function(){
        $('.evel_box ul li').removeClass('active');
        $('.frm-box').show();
        $('.vote-w').show();
        $('.js_tijiao').show();
        $('.js_know').hide();
    }

}
var myChoose = new toggleEvent();
//选择评价
function chooseVote(obj){
    var attr  = $(obj).attr('data-selected');
    $(obj).addClass('active').siblings().removeClass('active');
    if(attr == 1){
        myChoose.showF();
    }else{
        myChoose.showS();
    }
}
//赞--提交评价
function votePrise(){
    myBox  =  new $Y.confirm({
        content:'<div style="text-align: center;">谢谢评价，您的肯定是我们最大的鼓励</div>',
        ok:{
            title:"我知道了",
            click:function(){
                myBox.close();
                $('.vote-mask').fadeIn();
                $Y.animationIndexGif ('voteBootImg');
                setTimeout(function(){
                    $('.vote-mask').fadeOut();
                    goBackIndex();
                },4500);
            }
        }
    })
}
//踩--提交评价
function voteTips(){
    myBox2  =  new $Y.confirm({
        content:'<div style="text-align: center;">谢谢评价，您的建议是我们改进的动力</div>',
        ok:{
            title:"我知道了",
            click:function(){
                myBox2.close();
                $('.vote-mask').fadeIn();
                $Y.animationIndexGif ('voteBootImg');
                setTimeout(function(){
                    $('.vote-mask').fadeOut();
                    goBackIndex();
                },4500);
            }
        }
    })
}