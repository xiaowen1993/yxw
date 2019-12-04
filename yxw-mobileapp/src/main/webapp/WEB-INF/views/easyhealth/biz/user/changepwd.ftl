<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>修改密码</title>
</head>
<body  style="background-color: #eeeeee;">
<div id="body">
	<form id="paramsForm" method="post" action="">
		<input type="hidden" id="cardNo" name="cardNo" value="${sessionUser.cardNo}">
		<section class="register-wrap mod-forgetpwd">
		    <article class="register-list">
		        <ul>
		            <li>
		                <input type="password" placeholder="请输入原密码" id="initPwd" name="initPwd">
		            </li>
		            <li>
		                <input type="password" placeholder="请输入新密码" id="passWord" name="passWord">
		            </li>
		            <li>
		                <input type="password" placeholder="重新输入新密码" id="confirmPassWord">
		            </li>
		        </ul>
		    </article>
		    <articel class="register-btn">
		        <span class="" onclick="modifyPwd(this, true);">确定</span>
		    </articel>
		    <!--
		    <div class="btn-w forget-pwd color999" style="text-align:center; font-size: 14px;">
            <span onclick="loginGoView('${basePath}easyhealth/user/toForgetpwdSendcode');">忘记密码？</span>
        </div>
        -->
		</section>
	</form>
</div>
</body>
</html>
<script src="${basePath}yxw.app/js/biz/user/eh.register.js" type="text/javascript"></script>
