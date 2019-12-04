<html>
<head>
<#include "/sys/common/common.ftl">
<title>白名单设置</title>
</head>
<style type="text/css">
    .table th, .table td {
        padding: 5px 2px;
        font-size:14px;
    }
</style>
<body>
	<div id="content">
	 	<div id="content-top">
        	<div class="container-fluid">
            	<div class="box">
                	<div id="settings">
                    <div class="set-msg dropdown">
                        <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
                            <span class="text">当前账户：${loginedUser.userName}</span>
                            <i class="icons_settings icons-set"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${request.contextPath}/user?method=toModifyPwd"><span class="text">修改密码</span><i class="icons_settings icons-password"></i></a></li>
                            <li><a onclick="$user.logout();"><span class="text">退出登录</span><i class="icons_settings icons-loginout"></i></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
	
	
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">推广管理</h3></div>
    </div>
    
   		<div class="container-fluid" style="padding-left:0;padding-right:0;">
   			<div class="space10"></div>
   			<div class="row-fluid">
	            <div class="pull-left">
	                <ul class="tabs">
	                    <li id="wechat"><a href="javascript:changeMode(this,'wechat')" <#if vo.appCode == 'wechat'> class="select" </#if>>微信</a></li>
	                    <li id="alipay"><a href="javascript:changeMode(this,'alipay')" <#if vo.appCode == 'alipay'> class="select" </#if>>支付宝</a></li>
	                </ul>
	            </div>
	        </div>
			<div class="row-fluid">
        		<div class="space10"></div>
        		<div class="widget-box bangKa tab_content">
                	<div class="row-fluid">
                   		<form class="form-horizontal evenBg" id="extensionForm">
                   			<div class="control-box">
                        		<div class="control-group w162" style="padding: 2px 0">
                            		<div class="spaceW25 fr"></div>
                            			<button class="btn btn-save fr" type="button" onclick="addRWM()">添加用户</button>
                            			<div class="line" style="margin-right: 200px;height:3em;line-height:3em;overflow: hidden;">
                                			<span style="text-align:left;padding: 0 25 0 25;color:#40505f;font-weight:bold;width:160px;line-height:30px;font-size:16px;">设置白名单用户</span>
                                			标识：<input type="text" id="district" name="district" class="span5 input33" style="width:450px"/>
                                			<input type="hidden" id="appId" name="appId" value="${vo.appId}"/>
                                			<input type="hidden" id="appCode" name="appCode" value="${vo.appCode}"/>
                                			<input type="hidden" id="hospitalId" name="hospitalId" value="${hospitalId}"/>
                            			</div>
                         			</div>
                   				</div>
                   			</div>
                   		</form>  
                		<div class="space20"></div>
                			<div class="row-fluid">
                    			<table class="table table-bordered table-textCenter table-striped table-hover" style="padding:0;width:98%;" id="detailInfos" >
			                        <thead>
				                        <tr>
				                        	<th width="10"></th>
				                            <th width="120">标识</th>
				                            <th width="120">sceneid</th>
				                            <th width="200">二维码外部地址</th>
				                            <th width="200">二维码内部地址</th>
				                            <th width="50">统计</th>
				                            <th width="100">查看二维码</th>
				                        </tr>
			                        </thead>
			                        <tbody>
				                        <#if extensions?exists>
				                            <#list extensions as extension>
				                                <tr id="tr_${extension.id}">
				                                	<td>${extension_index + 1}</td>
				                                    <td>${extension.district}</td>
				                                    <td>${extension.sceneid}</td>
				                                    <td>${extension.outsideUrl}</td>
				                                    <td>${extension.insideUrl}</td>
				                                    <td>${extension.count}</td>
				                                    <td><a href="${extension.outsideUrl}">查看</a></td>
				                                </tr>
				                            </#list>
				                        <#else>
				                            <tr id="noData">
				                                <td colspan="6">未设置推广</td>
				                            </tr>
				                        </#if>
			                        </tbody>
                    			</table>
              				</div>
              			</div>
              		</div>
</body>
</html>
<script type="text/javascript">
    function addRWM(){
    	if('${vo.appId}' == null || '${vo.appId}' == '' ){
    		alert("未检索到接入平台");
    		return;
    	}
    	var district = jQuery.trim(jQuery("#district").val());
    	if(district != null && district != ''){
    		var reqUrl =  "${basePath}sys/extension/getEWM";
	        var datas = $("#extensionForm").serializeArray(); 
	        $.ajax({  
	            type : 'POST',  
	            url  : reqUrl,  
	            data : datas,  
	            dataType : 'json',  
	            timeout  : 120000,
	            success  : function(data) {
	            	if(data.status == 'OK'){
	            		window.location.reload();
	            	}else{
	            		alert(data.message);
	            	}
	             },error : function(data) {  
	               alert("保存失败!");
	             }  
	        });        
    	}else{
    		alert("标识不能为空");
    		return;
    	}
    }
    function changeMode(obj,mode){
    	var hospitalId = '${hospitalId}';
    	var url  = "${basePath}sys/extension/index?hospitalId=" + hospitalId + "&appCode=" + mode;
		window.location = url;
    }
</script>