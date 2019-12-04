<html>
<head>
	<#include "/sys/common/common.ftl">
    <title>登录页面</title>
</head>
<style>
    body{ background: url("./images/login_bg.jpg") no-repeat scroll 0 0  #37b494;}
</style>
<body>
<div class="w1000">
    <div id="headbox">
        <div class="space25"></div>
        <div class="title"><span class="line"></span>
            <span class="mate">掌上医院全流程管理后台</span>
            <span class="made pull-right">官方网站</span>
        </div>
    </div>
    <div id="loginbox">
        <form class="loginform">
            <div class="control-group normal_text">
                <h3>登录系统<small>system login</small></h3>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box">
                        <span class="add-on"> <i class="caret"></i><i class="icons-user"></i></span>
                        <input type="text" class="text-input" placeholder="请输入账户名" id="username" value="admin"/>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box">
                        <span class="add-on"> <i class="caret"></i><i class="icons-lock"></i></span>
                        <input type="password" class="text-input" placeholder="请输入密码" id="password" value="123456"/>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box textleft">
                        <input type="checkbox" class="checkbox" id="isRememberMe"/>
                        <span class="text">自动登录</span>
                    </div>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box textleft">
                        <input type="text" class="password-input" placeholder="请输入验证码" id="validCode" >
                        <div class="yzmBox pull-right">
                             <img id="ValidImg"  width="182" height="44" alt="点击刷新验证码" style="cursor:pointer;" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="space20"></div>
            <div class="control-group">
                <div class="controls">
                    <div class="main_input_box ">
                       <button type="button" class="btn-save btn-large" onclick="login();" id="loginBtn">登录</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="space80"></div>
    <div id="Copyright">粤ICP备12045426号-4   Copyright©2014 医享网 AII Rights Reserved </div>
</div>
<script>

//登录
function login()
{
	var username=$('#username').val();
	var password =$('#password').val();
	var validCode= $('#validCode').val();
	
	var isRememberMe= $('#isRememberMe').is(':checked')?'1':'0';
	if(username==''){
		window.wxc.xcConfirm("请输入账户名", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	if(password==''){
		window.wxc.xcConfirm("请输入密码", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	if(validCode=='')
	{
		window.wxc.xcConfirm("请输入验证码", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	$.ajax({
    		url:"${request.contextPath}/login/user_login",
    		data:{username:username,password:password,isRememberMe:isRememberMe,validCode:validCode},
    		dataType:'json',
    		type:'POST',
    		success:function(resp)
    		{
    			if(resp.status=='ERROR')
    			{
    				changeValidateCode();
    				window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.info);
    			}
    			else if(resp.status=='OK')
    			{
    				window.top.location="${request.contextPath}/sys/user/main";
    			}
    		}
    });
}

function changeValidateCode(){
	var timeNow = new Date().getTime();
	var imgUrl = "${request.contextPath}/login/getValidCode?time="+timeNow;
	$('#ValidImg').attr('src',imgUrl);
}

$(function()
{
	$('#ValidImg').click(function(){
		changeValidateCode();
	});
	
	$('#ValidImg').click();
    document.onkeydown = function(event) 
    { 
		var e = event || window.event || arguments.callee.caller.arguments[0]; 
		if (e && e.keyCode == 13) 
		{ 
			$('#loginBtn').click();
		} 
	}; 
});
</script>
</body>
</html>
