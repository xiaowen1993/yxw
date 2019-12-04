<#assign p=JspTaglibs["/WEB-INF/tlds/privilege.tld"]>

<title>掌上医院全流程管理后台</title>

<!--公共head头-->
<meta name="mobile-agent" content="format=html5;url=${basePath}">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<#include "/common/common.ftl">
<script type="text/javascript" src="${basePath}js/bootstrap_v2/bootstrap.min.js"></script>
<script type="text/javascript" src="${basePath}js/xcConfirm.js"  charset="utf-8"></script>
<script type="text/javascript" src="${basePath}js/dialog.js"></script>
<script type="text/javascript" src="${basePath}js/common.js"></script>
<link rel="stylesheet" href="${basePath}js/bootstrap_v2/bootstrap.min.css"/>
<link rel="stylesheet" href="${basePath}css/yx-style.css"/>
<link rel="stylesheet" href="${basePath}css/yx-media.css"/>

<link rel="stylesheet" href="${basePath}css/yx-style-cover.css"/>
<link rel="stylesheet" href="${basePath}css/xcConfirm.css"/>

<link rel="stylesheet" href="${basePath}css/subNav.css"/>
<link rel="stylesheet" href="${basePath}css/poject20160428.css"/>

<script>
	window.path="${request.contextPath}";
	
	/**
	 * 注销登录
	 * */
	window.logout =function(){
		window.wxc.xcConfirm("您确定退出系统？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
			$.ajax({
			 	url:'${request.contextPath}' +'/login/user_logout',
			 	type:'POST',
			 	dataType:'json',
			 	success:function(resp)
			 	{
			 		if(resp.status=='OK')
			 		{
			 			window.wxc.xcConfirm("您已退出登录！", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
			 				window.parent.location=path+"/login/user_tologin";
			 			}});
			 		}
			 		else
			 		{
			 			window.wxc.xcConfirm("退出登录失败，请重试！", window.wxc.xcConfirm.typeEnum.error);
			 		}
			 	}
			
			});
		}});
	}
	
	/**
	* 修改密码
	* */
	window.toModifyPwd = function(){
		window.location='${request.contextPath}'+'/sys/user/toModifyPwd';
	}
</script>