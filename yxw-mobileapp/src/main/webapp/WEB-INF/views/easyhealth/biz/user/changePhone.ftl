<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}mobileApp/js/biz/medicalcard/idCardUtils.js"></script>
    <title>更换手机</title>
</head>
<body style="background-color: #eeeeee;">
<div id="body">
	<form id="paramsForm" method="post" action="">
		<input type="hidden" id="cardNo" name="cardNo" value="${sessionUser.cardNo}">
		<section class="register-wrap mod-changePhone">
		    <article class="box-tips icontips">
		        <i class="iconfont">&#xe60d;</i>
		            当前登录手机：${sessionUser.encryptedMobile}。为了保障您的信息安全，更换手机号码前请先填写登录密码。
		    </article>
		    <article class="register-list">
		        <ul>
		            <li>
		                <input type="password" placeholder="输入登录密码" id="passWord" name="passWord">
		            </li>
		            <li>
		                <input type="tel" placeholder="输入新手机号" id="mobile" name="mobile">
		                <span class="code-btn" onclick="sendVerifyCode(this, 'modifyPhone', $('#mobile').val());">发送验证码</span>
		            </li>
		            <li>
		                <input type="tel" placeholder="验证码" id="verifyCode" name="verifyCode">
		            </li>
		        </ul>
		    </article>
		    <articel class="register-btn">
		        <span class="" onclick="modifyMobile(this);">确认修改</span>
		    </articel>
		</section>
	</form>
</div>
<script src="${basePath}yxw.app/js/biz/user/eh.register.js" type="text/javascript"></script>
</body>
</html>
