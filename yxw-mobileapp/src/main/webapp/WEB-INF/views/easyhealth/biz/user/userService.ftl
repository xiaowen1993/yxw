<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title><#if type==1>客服中心<#else>APP反馈</#if></title>
</head>
<body>
<div id="body">
    <div id="myCenter">
        <div class="space15"></div>
        <div class="feedback-view">
            <textarea class="frm-textarea js-textarea" rows="3" id="feedBack"  placeholder="请输入您的意见和建议"></textarea>
        </div>
        <div class="btn-w">
            <button type="button" class=" btn btn-ok btn-block" onclick="submitFeedback('${type}');">提交</button>
        </div>
        <div class="page-title line"><span class="word">下面是医院的回复</span></div>
        <ul class="replayList">
        	<#if replyList?exists>
        		<#if replyList?size &gt;0 >
	        		<#list replyList as reply>
			            <li>
			                <div class="title">反馈内容：${reply.feedback}</div>
			                <div class="des">回复：${reply.reply}</div>
			            </li>
		            </#list>
	            <#else>
		            <li class="nodatatext">
		                                         暂无回复，谢谢您的支持
		            </li>
            	</#if>
            </#if>
        </ul>
    </div>
</div>
<div class="vote-mask"></div>
<div id="copyright"> 医享网出品 </div>
</body>
</html>
<script type="text/javascript">
    function submitFeedback(type){
        var inputVal = $.trim($('.js-textarea').val());
        if(inputVal !=""){
        	$.ajax({
    		  type : 'POST',  
		      url  : appPath+'/easyhealth/customService/saveFeedback',  
		      data : {feedback:inputVal,type:type},  
		      dataType : 'json',
		      timeout  : 6000,
		      success  : function(data) {
		          if(data.status == 'OK'){
		          var myBox = new $Y.confirm({
		                content:'<div>非常感谢！您的建议和意见是我们改进的动力</div>',
		                ok:{
		                    title:'我知道了',
		                    click:function(){
		                    	myBox.close();
		                    	if(IS.isMacOS){
							        try
							        {
							            window.appCloseView();
							
							        } catch (e) {
							          //  alert('IOS的方法出错');
							        }
							
							    }else if(IS.isAndroid){
							        try
							        {
							            window.yx129.appCloseView();
							
							        } catch (e) {
							         //   alert('Android的方法出错');
							        }
							
							    }else{
							       go(appPath + 'easyhealth/userCenterIndex');
							    }
		                    }
		                }
		            });
		          }
		       }
        	});
        }else{
            $Y.tips('请填写您的意见或者是建议');
        }
    }
</script>

