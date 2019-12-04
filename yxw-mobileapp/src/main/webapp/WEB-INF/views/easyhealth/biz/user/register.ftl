<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}yxw.app/js/biz/usercenter/idCardUtils.js"></script>
    <style>
	    body{
            background: #fff;
	    }
    </style>
    <title>注册</title>
</head>
<body>
<div id="body">
	<form id="paramsForm" method="post">
		<div class="phone-login">
                <div class="form-item">
                        <i class="lipei icon-shouji skinColor"></i><input type="tel"  maxlength="11" id="account" name="account" placeholder="手机号">
                </div>
                <div class="form-item">
                        <i class="lipei icon-yanzhengma skinColor"></i><input   type="tel" id="verifyCode" name="verifyCode" placeholder="验证码"> <button type="button"  class="btn btn-ok btn-small btn-skin" onclick="sendVerifyCode(this, 'register');">发送验证码</button>
                </div>
                <div class="form-item">
                        <i class="lipei icon-mm skinColor"></i><input type="password" id="passWord" name="passWord" placeholder="新密码"> <i class="IconEye lipei icon-bukejian "></i>
                </div>
                <div class="form-item">
                        <i class="lipei icon-mm skinColor"></i><input type="password" id="confirmPassWord" placeholder="确认新密码"> <i class="IconEye lipei icon-bukejian"></i>
                </div>
                <div style="height: 20px"></div>
                <a href="javascript:register();" class="btn btn-block btn-ok">确定</a>
        </div>
	
	</form>
</div>
<script src="${basePath}yxw.app/js/biz/user/eh.register.js" type="text/javascript"></script>
</body>
</html>
