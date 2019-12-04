<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}yxw.app/js/biz/usercenter/idCardUtils.js"></script>
    <title>忘记密码</title>
</head>
<body>
<div id="body">
	<form id="paramsForm" method="post" action="">
		<section class="register-wrap mod-forgetpwd">
		    <article class="register-list">
		        <ul>
		            <li>
		                <input type="text" placeholder="请输入账号" id="account" name="account">
		                <span class="code-btn" onclick="sendVerifyCodeByForgetpwd(this, 'forgetpwd');">发送验证码</span>
		            </li>
		            <li>
		                <input type="text" placeholder="请输入验证码" id="verifyCode" name="verifyCode">
		            </li>
		        </ul>
		    </article>
		    <articel class="register-btn">
		        <span class="" onclick="forgetpwdSendCodeNext(this);">下一步</span>
		    </articel>
		</section>
	</form>
</div>
</body>
</html>
<script src="${basePath}yxw.app/js/biz/user/eh.register.js" type="text/javascript"></script>
