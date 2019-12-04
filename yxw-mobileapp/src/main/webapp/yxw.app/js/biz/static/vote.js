/**
 * Created by yuki on 2015-10-07.
 */

$(function(){
    $("#serviceLevelDiv ul li").click(function(index){
        var index = $(this).index(),
            elemt = $(this).parents('.evel_box').find('li');
        elemt.removeClass('active');
        elemt.slice(0,index+1).addClass('active');
        chooseServiceLevel(index + 1)
    });
    
    $("#skillLevelDiv ul li").click(function(index){
        var index = $(this).index(),
        elemt = $(this).parents('.evel_box').find('li');
        elemt.removeClass('active');
        elemt.slice(0,index+1).addClass('active');
        chooseSkillLevel(index + 1);
    });
})
function toggleEvent(){
    this.showF = function(){
        $('.evel_box ul li').removeClass('active');
    }
    this.showS = function(){
        $('.evel_box ul li').removeClass('active');
    }

}
var myChoose = new toggleEvent();
//选择评价
function chooseVote(obj){
    var attr  = $(obj).attr('data-selected');
    $(obj).addClass('active').siblings().removeClass('active');
    /*
    if(attr == 1){
        myChoose.showF();
    }else{
        myChoose.showS();
    }
    */
    $("#appraiseLevel").val(attr);
    /*
    chooseSkillLevel("");
    chooseServiceLevel("")
    */
}



//赞--提交评价
function votePrise(){
	var appraiseLevel = $("#appraiseLevel").val();
	var serviceLevel = $("#serviceLevel").val();
	var msgBox;
	
	if(appraiseLevel == ""){
		msgBox = new $Y.confirm({
            ok: {title:'确定'},
            content: '您还未对本次就诊进行评价，请选择踩或者赞。'
        });
		return;
	}
	
	if(serviceLevel == "" || serviceLevel == 0){
		msgBox = new $Y.confirm({
            ok: {title:'确定'},
            content: '您还未对本次就诊的服务态度打分'
        });
		return;
	}
	var skillLevel = $("#skillLevel").val();
	if(skillLevel == "" || skillLevel == 0){
		msgBox = new $Y.confirm({
            ok: {title:'确定'},
            content: '您还未对本次就诊的技术专业打分'
        });
		return;
	}
	
	$Y.loading.show('评价提交中...');
	var url = appPath + "easyhealth/vote/saveVoteInfo";
	var datas = $("#voteForm").serializeArray();
	
	$.ajax({
       	type : 'POST',  
       	url : url,  
       	data : datas,  
       	dataType : 'json',  
       	timeout:120000,
       	error: function(data) {
       		$Y.loading.hide();
       		myError = new $Y.confirm({
	            ok: {title:'确定'},
	            content: '网络异常,评价提交失败,请稍后再试'
	        });
       	},
       	success : function(data) {
       		$Y.loading.hide();
       		if (data.status == 'OK') {
       			mySuccess = new $Y.confirm({
	       	        content:'<div style="text-align: center;">谢谢评价，您的肯定是我们最大的鼓励</div>',
	       	        ok:{
	       	            title:"我知道了",
	       	            click:function(){
	       	            	mySuccess.close();
	       	                $('.vote-mask').fadeIn();
	       	                $Y.animationIndexGif ('voteBootImg');
	       	                setTimeout(function(){
	       	                    $('.vote-mask').fadeOut();
	       	                    // goBackIndex();
		       	                if (typeof windowClose == 'function') {
			       	          		windowClose();
			       	          	}
	       	                },4500);
	       	            }
	       	        }
	       	    })
       		} else {
       			myFailure = new $Y.confirm({
		            ok: {title:'确定'},
		            content: '网络繁忙,评价提交失败,请稍后再试'
		        });
       		}
       	}
	});
}

function chooseSkillLevel(val){
	$("#skillLevel").val(val);
}

function chooseServiceLevel(val){
	$("#serviceLevel").val(val);
}