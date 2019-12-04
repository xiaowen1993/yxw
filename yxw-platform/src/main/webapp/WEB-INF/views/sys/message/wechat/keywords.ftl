<html>
<head>
<#include "./sys/common/common.ftl">
<!--ajax上传组件 -->
<script src="${basePath}js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basePath}js/sys/message/sys.msg.js"></script>
<!--content str-->
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">微信消息管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                     <button class="btn btn-ok w165 " onclick="$msg.toFocusedReply();">被添加自动回复</button>
                    <button class="btn btn-ok w165 select" onclick="$msg.toKeywordReply();">关键词自动回复</button>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content" style="padding: 0;">
                    <div class="row-fluid">
                        <a class="btn-add pull-right" href="javascript:void(0);" onclick="addRule(this);"><i class="caret"></i><i class="icons-plus"></i>添加</a>
                    </div>
                </div>
                <div class="space10"></div>
	                <div class="rule-new clearfix">
	                <!--规则集合-->
		        		<#if (rules?size>0)>
		        			<#list rules as rule>
		        				<div class="widget-content">
		        					<form>
		        						<input name="hospitalId" type="hidden" value="${hospitalId}"><input name="thirdType" type="hidden" value="${thirdType}">
		        						<div class="row-fluid">
				                    		<div class="space20"></div>
						                    	<div class="rule-msg">
						                            <div class="r-title ruleBg"><div class="options"><a onclick="toEditRule('${rule.id}',this)">修改</a><a onclick="deleteRule('${rule.id}',this)" >删除</a></div><h4 class="title">${rule.ruleName}</h4></div>
						                            <div class="r-rule">
						                                <div class="innerw">
						                                    <!-- 关键词 父 str-->
						                                    <div class="r-keywords clearfix">
						                                        <!-- 关键词 str-->
					                                        	<#list rule.keywords as keyword>
							                                        <div class="control-group">
							                                            <label class="control-label">关键字</label>
							                                            <div class="controls clearfix">
							                                               	<div class="span5 text-defaults">${keyword.content}</div>
							                                               	<span class="textTis">
							                                               	<#if (keyword.type)==1>（完全匹配）<#else>（不完全匹配）</#if></span>
							                                            </div>
							                                        </div>
					                                            </#list>
						                                        <!-- 关键词 end-->
						                                        <!--回复 str-->
						                                        <div class="control-group">
						                                            <label class="control-label">回复</label>
						                                            <div class="controls">
						                                                <h5 class="title">${rule.totalNum}条<small>（${rule.textNum}条文字， ${rule.imgNum}条图片，${rule.imageTextNum}条图文）</small></h5>
						                                            </div>
						                                        </div>
						                                        <!--回复 end-->
						                                    </div>
						                                    <!-- 关键词 父 end-->
						                                </div>
						                            </div> 
						                        </div>
				                        	<div class="space10"></div>
				                    	</div>
		        					</form>
		        				</div>
		        				<div class="space20"></div>
		        			</#list>
		                </#if>
	                <!--规则集合-->
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
<script type="text/javascript">
	path='${request.contextPath}';
	var hospitalId='${hospitalId}';//保存当前操作的医院Id
	var thirdType='${thirdType}';//保存当前操作的医院Id
	var ruleNum=0;//记录规则数 每添加一个规则+1
	//添加规则 弹出一个空规则的div
	function addRule(){
		var ruleUnitNum = $('.ruleUnit').length;
		if(ruleUnitNum>0)
		{
			alert('请先保存当前编辑的规则');
			return;
		}
	    var html='';
	    html+='<div class="widget-content ruleUnit"><form><input name="hospitalId" type="hidden" value="${hospitalId}"><input name="thirdType" type="hidden" value="${thirdType}"> <div class="row-fluid">';
	    html+='  <div class="rule-msg">';
	    html+=' <div class="r-title ruleBg"> <div class="options" style="display: none;"><a href="#">修改</a><a href="#">删除</a></div><h4 class="title">新建规则</h4> </div>';
	    html+='<div class="r-rule rule_parent">  <div class="innerw">';
	    html+='  <div class="control-group"><label class="control-label">规则名</label> <div class="controls"><input maxlength="60" class="span5"  name="ruleName" type="text" placeholder="输入规则名称"/>规则名最多60个字</div></div>';
	    html+='<div class="r-keywords clearfix"><div class="control-group">';
	    html+='<label class="control-label">关键词</label><div class="controls">';
	    html+=' <input class="span5" name="keyword" maxlength="30"  type="text" placeholder="输入关键词"/>';
	    html+=' <div class="my_select"> <select name="keywordType" class="span2"><option value="1">完全匹配</option><option value="2">不完全匹配</option></select></div>';
	    html+='<div class="operation" onclick="addKeyword(this);"><i class="icons-rule icons-plus"></i>每个关键字少于30个字符</div></div> </div>';
	 //   html+='<div class="control-group"> <label class="control-label">关键词</label><div class="controls">';
	 //   html+=' <input class="span5" name="keyword"  type="text" placeholder="输入关键词"/>';
	 //   html+='  <div class="my_select"> <select name="keywordType" class="span2"> <option value="1">完全匹配</option><option value="2">不完全匹配</option></select></div>';
	//    html+='<div class="operation" onclick="delKeyWord(this);"><i class="icons-rule icons-del-grey"></i></div></div></div>';
	    html+='</div><div class="control-group">';
	    html+=' <label class="control-label">回复</label><div class="controls">';
	    html+='<div class="operation grey" onclick="addWord(this);"><i class="icons-rule icons-word"></i>文字</div>';
	    html+='<div class="operation grey" onclick="addPicKeyword('+ruleNum+');"><i class="icons-rule icons-pic"></i>图片</div>';
	    html+='<div class="operation grey" onclick="addImageTextKeyword('+ruleNum+');"><i class="icons-rule icons-imagetext"></i>图文信息</div>';
	    html+='<div class="replyAll"></div>';
//	    html+='<div class="replyAll"><label class="checkboxTwo inline check"><input type="checkbox" checked name="ruleReplyType" value="2">回复全部</label></div>';
	    html+='</div></div></div>';
	    html+='<div class="r-reply clearfix"></div>';
	    html+='</div></div>';
	    html+='<div class="space20"></div><div class="clearfix">';
	    html+=' <button type="button" class="btn btn-save" onclick="saveRule('+ruleNum+')">保存</button>';
	    html+='<div class="spaceW15"></div>';
	    html+='<button type="button" class="btn btn-remove" onclick="removeNew('+ruleNum+')" >取消</button>';
	    html+='</div><div class="space10"></div></div></div><div class="space10"></div></form></div>';
	    $('.rule-new').append(html);
	    ruleNum++;
	}
	
	//编辑规则 弹出一个填充原有规则的div
	//参数：规则ID 规则名 规则类型 关键字LIST 回复LIST
	function editRule(ruleId,ruleName,ruleType,kwList,replyList){
	    var html='';
	    html+='<div class="widget-content ruleUnit"><form id="'+ruleId+'"><input name="hospitalId" type="hidden" value="${hospitalId}"><input name="thirdType" type="hidden" value="${thirdType}"> <div class="row-fluid">';
	    html+='  <div class="rule-msg">';
	    html+=' <div class="r-title ruleBg"> <div class="options" style="display: none;"><a href="#">修改</a><a href="#">删除</a></div><h4 class="title">编辑规则</h4> </div>';
	    html+='<div class="r-rule rule_parent">  <div class="innerw">';
	    html+='  <div class="control-group"><label class="control-label">规则名</label> <div class="controls"><input type="hidden" name="ruleId" value="'+ruleId+'"><input class="span5" maxlength="60"  name="ruleName" type="text" placeholder="输入规则名称" value="'+ruleName+'"/>规则名最多60个字</div></div>';
	    html+='<div class="r-keywords clearfix">';
	    //拼接关键字
	    for(var i=0;i<kwList.length;i++)
	    {
		    html+='<div class="control-group">';
		    html+='<label class="control-label">关键词</label><div class="controls"><input name="keywordId" type="hidden" value="'+kwList[i].id+'"> ';
		    html+=' <input class="span5" name="keyword" maxlength="30" type="text" placeholder="输入关键词" value="'+kwList[i].content+'"/>';
		    html+=' <div class="my_select"> <select name="keywordType" class="span2"><option value="1" <#if '+kwList[i].type+'==1>selected</#if>>完全匹配</option><option value="2"  <#if '+kwList[i].type+'==2>selected</#if>>不完全匹配</option></select></div>';
		    html+='<div class="operation" onclick="addKeyword(this);"><i class="icons-rule icons-plus"></i>每个关键字少于30个字符</div></div> </div>';
	    }
	    html+='<div class="control-group">';
	    html+=' <label class="control-label">回复</label><div class="controls">';
	    html+='<div class="operation grey" onclick="addWord(this);"><i class="icons-rule icons-word"></i>文字</div>';
	    html+='<div class="operation grey" onclick="addPicKeyword('+ruleNum+');"><i class="icons-rule icons-pic"></i>图片</div>';
	    html+='<div class="operation grey" onclick="addImageTextKeyword('+ruleNum+');"><i class="icons-rule icons-imagetext"></i>图文信息</div>';
	  //  if(ruleType==2)
	 //   {
	 //   	 html+='<div class="replyAll"><label class="checkboxTwo inline check"><input type="checkbox" checked name="ruleReplyType" value="2">回复全部</label></div>';
	 //   }else
	 //   {
	//    	html+='<div class="replyAll"><label class="checkboxTwo inline"><input type="checkbox" name="ruleReplyType" value="2">回复全部</label></div>';
	//    }
	    html+='</div></div></div>';
	    html+='<div class="r-reply clearfix">';
	    //拼接回复
	    for(var i=0;i<replyList.length;i++)
	    {
	    	if(replyList[i].contentType==1)
	    	{
	    		 html+='<div class="t-words t-tap clearfix"><div class="innerw clearfix">';
		         html+='<div class="operation" onclick="delReply(this,\''+replyList[i].id+'\',\''+ruleId+'\');"><i class="icons-rule icons-del"></i></div>';
		         html+='<div class="text">'+replyList[i].content+'</div>';
		         html+='</div></div>';
	    	}
	    	else if(replyList[i].contentType==2)
	    	{
	    		 html+='<div class="t-pic t-tap clearfix"><div class="innerw clearfix">';
		         html+='<div class="operation" onclick="delReply(this,\''+replyList[i].id+'\',\''+ruleId+'\');"><i class="icons-rule icons-del"></i></div>';
		         html+='<img class="pic-thumb"  src="'+replyList[i].picPaths+'" width="100" height="100"/>';
		         html+='</div></div>';
	    	}
	    	else if(replyList[i].contentType==3)
	    	{
	    		var mixList=replyList[i].mixedMeterialList;
	    		for(var j=0;j<mixList.length;j++)
	    		{
	    			if(mixList[j].type==1)
	    			{
	    				html+='<div class="t-imagetext t-tap clearfix"><div class="innerw clearfix">';
				        html+='<div class="operation" onclick="delReply(this,\''+replyList[i].id+'\',\''+ruleId+'\');"><i class="icons-rule icons-del"></i></div>';
				        html+='<div class="t-msg">   <div class="inner">   <div class="as-thumb">';
				        html+='<h4 class="msg-title js_msgTitle">'+mixList[j].title+'</h4>';
				        //html+='<div class="msg-info">2015-5-20  17:08:29</div>';
				        html+='<div class="msg-thumb-wrap">';
				        html+='<img class="msg-thumb " style="display:block"   src="'+mixList[j].coverPicPath+'"/>';
				        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
				        html+='</div>';
				        html+='<div class="msg-desc js_msgDesc">'+mixList[j].description+'</div>';
				        html+='</div></div></div> </div></div>';
	    			}
	    			else if(mixList[j].type==2)
	    			{
	    				html+='<div class="t-imagetext t-tap clearfix"><div class="innerw clearfix">';
				        html+='<div class="operation" onclick="delReply(this,\''+replyList[i].id+'\',\''+ruleId+'\');"><i class="icons-rule icons-del"></i></div>';
				        html+='<div class="t-msg"> <div class="inner"> <div class="multi-thumb">';
				        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+mixList[j].title+'</a></h4>';
				        html+='<div class="msg-thumb-wrap">';
				        html+='<img class="msg-thumb" style="display:block"  src="'+mixList[j].coverPicPath+'"/>';
				        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
				        html+='</div>';
				        html+='<div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>';
				        html+='</div></div>';
				        var subMixedMeterialList=mixList[j].subMixedMeterialList;
				        for(var k=0;k<subMixedMeterialList.length;k++){
					        html+='<div class="msg-out clearfix">  <div class="inner msg-item">';
					        html+='<img class="msg-thumb" style="display:block"  src="'+subMixedMeterialList[k].coverPicPath+'"/>';
					        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
					        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+subMixedMeterialList[k].title+'</a></h4>';
					        html+='<div class="msg-edit-mask">';
					        html+='<a class="icons_edit edit-white" href="javascript:void(0)"></a>';
					        html+='<div class="spaceW9"></div>';
					        html+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>';
					        html+='</div></div></div>';
				        }
				        html+='</div></div></div>';
	    			}
	    		}
	    	}
	    }
	    html+='</div>';
	    html+='<div class="space20"></div><div class="clearfix">';
	    html+=' <button type="button" class="btn btn-save" onclick="updateRule(\''+ruleId+'\');">保存</button>';
	    html+='<div class="spaceW15"></div>';
	    html+='<button type="button" class="btn btn-remove" onclick="cancelEdit(\''+ruleId+'\')">取消</button>';
	    html+='</div><div class="space10"></div></div></div><div class="space20"></div></form></div><div class="space10"></div>';
	    $('.rule-new').prepend(html); 
	}
	
	//保存规则后 显示的规则div样式
	function showRule(ruleId,ruleName,keywordList,textNum,imageNum,imageTextNum,totalNum)
	{
			var html='';
	    	html+='<div class="widget-content"> <div class="row-fluid">';
			html+='<div class="rule-msg">';
			html+='<div class="r-title ruleBg"><div class="options"><a onclick="toEditRule(\''+ruleId+'\',this);" >修改</a><a onclick="deleteRule(\''+ruleId+'\',this)">删除</a></div><h4 class="title">'+ruleName+'</h4></div>';
			html+='<div class="r-rule">';
			html+=' <div class="innerw">';
			html+='<div class="r-keywords clearfix">';
			for(var i=0;i<keywordList.length;i++)
			{
				html+=' <div class="control-group">';
				html+='<label class="control-label">关键字</label>';
				html+=' <div class="controls clearfix">';
				html+='<div class="span5 text-defaults">'+keywordList[i].content+'</div>';
				if(keywordList[i].type==1)
				{
					html+='<span class="textTis">（完全匹配）</span>';
				}else
				{
				html+='<span class="textTis">（不完全匹配）</span>';
				}
				html+='</div>';
			}
			html+='  <div class="control-group">';
			html+=' <label class="control-label">回复</label>';
			html+='<div class="controls">';
			html+=' <h5 class="title">'+totalNum+'条<small>（'+textNum+'条文字， '+imageNum+'条图片，'+imageTextNum+'条图文）</small>';
		//	if(ruleType==1)
		//	{
		//		html+='    随机选择一条回复</h5>';
		//	}else
		//	{
		//		html+='    回复全部</h5>';
		//	}
			html+='</div></div></div></div></div></div></div></div></div>';
			$('.rule-new').prepend(html);
	}
	
	//保存新规则
	function saveRule(index)
	{
		var form = $('.ruleUnit:eq('+index+') form');
		var ruleName=$(form).find('input[name="ruleName"]').val();
		if(ruleName=='')
		{
			window.wxc.xcConfirm('必须填写规则名', window.wxc.xcConfirm.typeEnum.info);
			return;
		}
        $.ajax({  
            type: "post",  
            url: path+"/message/reply?method=saveKeywordReply",       
            data: $(form).serialize(),      
            success: function(resp) {  
        		if(resp.status=='OK')
        		{
        			//显示保存后的规则样式
        			showRule(resp.ruleId,resp.ruleName,resp.keywordList,resp.textNum,resp.imageNum,resp.imageTextNum,resp.replyNum);
        			//移除保存规则前的规则样式
        			$('.ruleUnit:eq('+index+')').remove();
        			ruleNum--;//当前新增待保存的规则数-1
        			
        		}else
        		{
        			window.wxc.xcConfirm('规则保存失败', window.wxc.xcConfirm.typeEnum.error);
        		}
            }
   		});  
	}
	
	//移除新增规则样式
	function removeNew(index)
	{
		$('.ruleUnit:eq('+index+')').remove();
		ruleNum--;//当前新增待保存的规则数-1
	}
	
	//保存编辑规则
	function updateRule(ruleId)
	{
		var form  = $('#'+ruleId);
		var isPass=true;
		$('input[name=ruleName]').each(function()
		{
			if($(this).val().length>60)
			{
				window.wxc.xcConfirm('规则名过长（小于60个字）', window.wxc.xcConfirm.typeEnum.info);
				isPass=false;
			}
		});
		$('input[name=keyword]').each(function()
		{
			if($(this).val().length>30)
			{
				window.wxc.xcConfirm('关键字过长（小于30个字）', window.wxc.xcConfirm.typeEnum.info);
				isPass=false;
			}
		});
		if(!isPass)
		{
			return false;
		}
		$.ajax({  
	            type: "post",  
	            url: path+"/message/reply?method=updateKeywordReply",       
	            data: $(form).serialize(),      
	            success: function(resp) {  
	        		if(resp.status=='OK')
	        		{
	        			//显示保存后的规则样式
	        			showRule(resp.ruleId,resp.ruleName,resp.keywordList,resp.textNum,resp.imageNum,resp.imageTextNum,resp.replyNum);
						//移除保存规则前的规则样式
						$(form).parent().remove();
	        		}else
	        		{
	        			if(resp.message!='')
	        			{
		        			window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
	        			}
	        			else
	        			{
	        				window.wxc.xcConfirm('规则修改失败', window.wxc.xcConfirm.typeEnum.error);
	        			}
	        		}
	            }
	   		});  
	}
	
		
	
	//获取编辑规则并填充出一个规则样式
	function toEditRule(ruleId,obj)
	{
		var ruleUnitNum = $('.ruleUnit').length;
		if(ruleUnitNum>0)
		{
			alert('请先保存当前编辑的规则');
			return;
		}
		$.ajax({  
            type: "post",  
            url: path+"/message/reply?method=editKeywordReply",       
            data:{ruleId:ruleId},      
            success: function(resp) {  
        		if(resp.status=='OK')
        		{
        			var rule=resp.rule;//规则obj
        			var kwList=resp.keywordList;//关键字LIst
        			var replyList=resp.replies;//回复list
        			//显示一个规则编辑样式   
					editRule(rule.id,rule.ruleName,rule.type,kwList,replyList);
					$(obj).parents('.widget-content').remove();
        		}
        		else
        		{
        			window.wxc.xcConfirm('规则保存失败', window.wxc.xcConfirm.typeEnum.error);
        		}
            }
   		});  
	}
	
	
	//删除规则
	function deleteRule(ruleId,obj)
	{
		if(!confirm('确定删除该规则吗？'))
		{
			return;
		}
		$.ajax({  
            type: "post",  
            url: path+"/message/reply?method=deleteRule",       
            data:{ruleId:ruleId},      
            success: function(resp) {  
        		if(resp.status=='OK')
        		{
					$(obj).parents('.widget-content').remove();
        		}
        		else
        		{
        			window.wxc.xcConfirm('规则删除失败', window.wxc.xcConfirm.typeEnum.error);
        		}
            }
   		});  
	}

    //添加关键字
    function addKeyword(obj){
        var parent = $(obj).parents('.r-keywords');
        var html='';
        html+='<div class="control-group"> <label class="control-label">关键字</label> <div class="controls">';
        html+='<input class="span5" name="keyword" maxlength="30" type="text" placeholder="输入关键词"/> ';
        html+='<div class="my_select"><select name="keywordType" class="span2">  <option value="1">完全匹配</option> <option value="2">不完全匹配</option>';
        html+='</select></div> ';
        html+='<div class="operation" onclick="delKeyWord(this);"><i class="icons-rule icons-del-grey"></i></div>';
        html+='</div></div>';
        $(parent).append(html);
    }
    
    
    //删除关键字
    function delKeyWord(obj){
        $(obj).parents('.control-group').remove();
    }
    
    
    //删除规则下的回复 回复与规则解绑
    function delReply(obj,replyId,ruleId){
       
        if(replyId!=null)
        {
        	if(!confirm('确定删除该回复吗？'))
        	{
        		return;
        	}
    		$.ajax({  
            type: "post",  
            url: path+"/message/reply?method=deleteRuleReply",       
            data:{ruleId:ruleId,replyId:replyId},      
            success: function(resp) {  
	        		if(resp.status=='OK')
	        		{
						 $(obj).parents('.t-tap').remove();
	        		}
	        		else
	        		{
	        			window.wxc.xcConfirm('规则保存失败', window.wxc.xcConfirm.typeEnum.error);
	        		}
	            }
			});  
        }
        else
        {
           $(obj).parents('.t-tap').remove();
        }
    }
    
    //取消编辑
    function cancelEdit(ruleId)
    {
    	var form  = $('#'+ruleId);
    	$.ajax({  
	            type: "post",  
	            url: path+"/message/reply?method=cancelEditKeywordReply",       
	            data: {ruleId:ruleId},      
	            success: function(resp) {  
	        		if(resp.status=='OK')
	        		{
	        			//显示保存后的规则样式
	        			showRule(resp.ruleId,resp.ruleName,resp.keywordList,resp.textNum,resp.imageNum,resp.imageTextNum,resp.replyNum);
						//移除保存规则前的规则样式
						$(form).parent().remove();
	        		}else
	        		{
	        			window.wxc.xcConfirm('规则修改失败', window.wxc.xcConfirm.typeEnum.error);
	        		}
	            }
	   		});  
    }

    //弹出添加文字框
    function addWord(obj){
       new $Y.dialog({
            title:'回复文字',
            width:'535px',
            height:'290px',
            content:'',
            callback:function(boxword){
                $.ajax({
                    url:path+'/message/reply?method=toDialogWords',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        boxword.content(html);
                        $Y.ScrollBar();
                        $(boxword.id).on('click','.btn-save',function(){
                        var textContent = $(boxword.id).find('.textContent').val();
                        //console.log(textContent);
                          boxword.close();
                            addReplyWord(obj,textContent);
                              
                        });
                        $(boxword.id).on('click','.btn-remove',function(){
                            boxword.close();
                        });
                    }
                })
            }
        });
    }

    //添加 回复--文字
    function addReplyWord(obj,textContent){
        var parent = $(obj).parents('.rule_parent').find('.r-reply');
        var html='';
        html+='<div class="t-words t-tap clearfix"><div class="innerw clearfix">';
        html+='<div class="operation" onclick="delReply(this);"><i class="icons-rule icons-del"></i></div>';
        html+='<div class="text">'+textContent+'</div><input type="hidden" value="'+textContent+'" name="replyText">';
        html+='</div></div>';
        $(parent).append(html);
    }
    
    //添加 回复--图片
    function addReplyImg(index,imgSrc){
        var parent = $('.rule_parent:eq('+index+')').find('.r-reply');
        var html='';
        html+='<div class="t-pic t-tap clearfix"><div class="innerw clearfix">';
        html+='<div class="operation" onclick="delReply(this);"><i class="icons-rule icons-del"></i></div>';
        html+='<img class="pic-thumb"  src="'+imgSrc+'" width="100" height="100"/><input type="hidden" name="replyImgSrc" value="'+imgSrc+'">';
        html+='</div></div>';
        $(parent).append(html);
    }
    

    //弹出选择添加图片框
    function addPicKeyword(index){
        boxPic=new $Y.dialog({
            title:'回复图片',
            width:'400px',
            height:'220px',
            content:'',
            callback:function(){
                $.ajax({
                    url:path+'/message/reply?method=toDialogPic&hospitalId=${hospitalId}',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        boxPic.content(html);
                        $(boxPic.id).find('#uploadFile').bind('change',function(){
                        	var src=$('#uploadFile').val();
							if(src!='')
							{
								//异步上传到服务器
								$.ajaxFileUpload(  
										{
											secureuri:false,  
											fileElementId:'uploadFile',  			
											dataType: 'json',	  
											type:'POST',
											url:path+"/uploading?method=uploadMeterial",
											success:function(resp)
											{
					 							if(resp.status=='OK')
												{
													addReplyImg(index,resp.message);
													boxPic.close();
												}
												else if(resp.status=='ERROR')
												{
													window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
												}
											}                                                                                                              
										});
							}
                        });
                        $(boxPic.id).find('#metarialPath').bind('change',function()
	                    {
	                    	addReplyImg(index,$(this).val());
	                    	boxPic.close();
	                    });
                        $Y.ScrollBar();
//                      boxPic.close();
                    }
                })
            }
        });
    }


    //弹出回复图文选择框
    function addImageTextKeyword(index){
       boxImageText =  new $Y.dialog({
            title:'选择图文',
            width:'400px',
            height:'300px',
            content:'',
            callback:function(){
                $.ajax({
                    url:path+'/message/reply?method=toDialogImageText&thirdType=${thirdType}&hospitalId=${hospitalId}&type=2',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        boxImageText.content(html);
						var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
						var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
                    	$(boxImageText.id).find('#singleBtn').bind('click',function(){
							window.open("${request.contextPath}/message/reply?method=toSingleText&hospitalId="+hospitalId+"&index="+index+"&type=2",
							"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
                    		
                    	});
                    	$(boxImageText.id).find('#multiBtn').bind('click',function(){
                    		window.open("${request.contextPath}/message/reply?method=toMulti&hospitalId="+hospitalId+"&index="+index+"&type=2",
							"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
                    	});
                    	$(boxImageText.id).find('#metarialId').bind('change',function()
	                    {
	                    	$.ajax({
						        url: "${request.contextPath}/message/mixedMeterial?method=getMeterialById",
						        type:"POST",
						        dataType:"JSON",
						        data:{id:$(this).val()},
						        success:function(resp){
						            if(resp.status == 'OK')
						            {
						            	if(resp.message.type==1)
						            	{
						            		addSingleUnit(index,resp.message.id,resp.message.title,resp.message.content,resp.message.coverPicPath);
						            	}
						            	else
						            	{
						            		addMultiUnitObj(index,resp.message);
						            	}
						            	boxImageText.close();
						            }
						            else
						            {
						            	window.wxc.xcConfirm("添加失败", window.wxc.xcConfirm.typeEnum.error);
						                boxImageText.close();
						            }
						        }
						    });
	                    });
                        $Y.ScrollBar();
//                      boxImageText.close();
                    }
                })
            }
        });
    }

    //显示一个单图文样式
    function addSingleUnit(index,id,title,content,imgsrc){
        var parent = $('.rule_parent:eq('+index+')').find('.r-reply');
        var html='';
        html+='<div class="t-imagetext t-tap clearfix"><div class="innerw clearfix">';
        html+='<div class="operation" onclick="delReply(this);"><i class="icons-rule icons-del"></i></div>';
        html+='<div class="t-msg">   <div class="inner">   <div class="as-thumb">';
        html+='<h4 class="msg-title js_msgTitle">'+title+'</h4>';
      //  html+='<div class="msg-info">2015-5-20  17:08:29</div>';
        html+='<div class="msg-thumb-wrap">';
        html+='<img class="msg-thumb " style="display:block"   src="'+imgsrc+'"/><input type="hidden" name="singleIds" value="'+id+'">';
        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
        html+='</div>';
        html+='<div class="msg-desc js_msgDesc">'+content+'</div>';
        html+='</div></div></div> </div></div>';
        $(parent).append(html);
    }

    //显示一个多图文样式
    function addMultiUnit(index,ids,objArray){
        var parent = $('.rule_parent:eq('+index+')').find('.r-reply');
        if(objArray.length>0){
	        var html='';
	        html+='<div class="t-imagetext t-tap clearfix"><div class="innerw clearfix">';
	        html+='<div class="operation" onclick="delReply(this);"><i class="icons-rule icons-del"></i></div>';
	        html+='<div class="t-msg"> <div class="inner"> <div class="multi-thumb">';
	        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+objArray[0].title+'</a></h4>';
	        html+='<div class="msg-thumb-wrap">';
	        html+='<img class="msg-thumb" style="display:block"  src="'+objArray[0].coverPicPath+'"/>';
	        html+='<input type="hidden" name="multiIds" value="'+ids+'"><i class="msg-thumb-size default" style="display:none" ></i>';
	        html+='</div>';
	        html+='<div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>';
	        html+='</div></div>';
	        for(var i=1;i<objArray.length;i++){
		        html+='<div class="msg-out clearfix">  <div class="inner msg-item">';
		        html+='<img class="msg-thumb" style="display:block"  src="'+objArray[i].coverPicPath+'"/>';
		        html+='<i class="msg-thumb-size default" style="display:none" ></i>';
		        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+objArray[i].title+'</a></h4>';
		        html+='<div class="msg-edit-mask">';
		        html+='<a class="icons_edit edit-white" href="javascript:void(0)"></a>';
		        html+='<div class="spaceW9"></div>';
		        html+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>';
		        html+='</div></div></div>';
	        }
	        html+='</div></div></div>';
        }
        $(parent).append(html);
    }
    //显示一个多图文样式
    function addMultiUnitObj(index,obj){
        var parent = $('.rule_parent:eq('+index+')').find('.r-reply');
        if(obj){
	        var html='';
	        var subList = obj.subMixedMeterialList;
	        var fragment='';
	        for(var i=0;i<subList.length;i++){
		        fragment+='<div class="msg-out clearfix">  <div class="inner msg-item">';
		        fragment+='<img class="msg-thumb" style="display:block"  src="'+subList[i].coverPicPath+'"/>';
		        fragment+='<i class="msg-thumb-size default" style="display:none" ></i>';
		        fragment+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+subList[i].title+'</a></h4>';
		        fragment+='<div class="msg-edit-mask">';
		        fragment+='<a class="icons_edit edit-white" href="javascript:void(0)"></a>';
		        fragment+='<div class="spaceW9"></div>';
		        fragment+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>';
		        fragment+='</div></div></div>';
	        }
	        html+='<div class="t-imagetext t-tap clearfix"><div class="innerw clearfix">';
	        html+='<div class="operation" onclick="delReply(this);"><i class="icons-rule icons-del"></i></div>';
	        html+='<div class="t-msg"> <div class="inner"> <div class="multi-thumb">';
	        html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">'+obj.title+'</a></h4>';
	        html+='<div class="msg-thumb-wrap">';
	        html+='<img class="msg-thumb" style="display:block"  src="'+obj.coverPicPath+'"/>';
	        html+='<input type="hidden" name="multiIds" value="'+obj.id+'"><i class="msg-thumb-size default" style="display:none" ></i>';
	        html+='</div>';
	        html+='<div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>';
	        html+='</div></div>';
	        html+=fragment;
	        html+='</div></div></div>';
        }
        $(parent).append(html);
    } 
</script>