<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>忘记密码</title>
</head>
<body  style="background-color: #eeeeee;">
<div id="body">
	<form id="paramsForm" method="post" action="">
		<input type="hidden" id="account" name="account" value="${account}">
		<input type="hidden" id="verifyCode" name="verifyCode" value="${verifyCode}">
		<section class="register-wrap mod-forgetpwd">
		    <article class="register-list">
		        <ul>
		            <li>
		                <input type="password" placeholder="请输入新密码" id="passWord" name="passWord">
		            </li>
		            <li>
		                <input type="password" placeholder="重新输入新密码" id="confirmPassWord">
		            </li>
		        </ul>
		    </article>
		    <articel class="register-btn">
		        <span class="" onclick="modifyPwd(this, false);">确定</span>
		    </articel>
		</section>
	</form>
</div>
</body>
</html>
<script src="${basePath}yxw.app/js/biz/user/eh.register.js" type="text/javascript"></script>
