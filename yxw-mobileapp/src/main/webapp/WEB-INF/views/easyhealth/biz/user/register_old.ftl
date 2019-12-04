<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}mobileApp/js/biz/medicalcard/idCardUtils.js"></script>
    <title>注册</title>
</head>
<body  style="background-color: #eeeeee;">
<div id="body">
	<form id="paramsForm" method="post">
		<section class="register-wrap">
			<article class="register-list">
		    	<ul>
        		<!-- <li><input type="text" id="name" name="name" placeholder="姓名"></li>
            <li>
                <input type="text" id="cardNo" name="cardNo" placeholder="身份证" maxlength="18">
                <input type="hidden" id="cardType" name="cardType" value="1">
                <!-- 
                <select id="cardType" name="cardType">
                	<option value="1">二代身份证</option>
                  <option value="2">港澳居民身份证</option>
                  <option value="3">台湾居民身份证</option>
                  <option value="4">护照</option>
                  <option value="">取消</option>
                </select>
                -- >
            </li> -->
            <li>
               <input type="tel" id="account" name="account" placeholder="手机" maxlength="11">
               <span class="code-btn" onclick="sendVerifyCode(this, 'register');">发送验证码</span>
            </li>
            <li>
               <input type="tel" id="verifyCode" name="verifyCode" placeholder="验证码">
            </li>
            <li>
               <input type="password" id="passWord" name="passWord" placeholder="密码">
            </li>
            <li>
               <input type="password" id="confirmPassWord" placeholder="确认密码">
            </li>
		    	</ul>
		    </article>
		    
		    <article class="space15"></article>
		    <!--<article class="box-tips icontips">
		        <i class="iconfont">&#xe60d;</i>温馨提示：身份证号码作为您在深圳公立医院的统一就诊识别码，全市通用。
		    </article>
		    <article class="space15"></article>-->
		    <articel class="register-btn">
		        <span class="" onclick="register();">立即注册</span>
		    </articel>
		    
		</section>
	</form>
</div>
<script src="${basePath}yxw.app/js/biz/user/eh.register.js" type="text/javascript"></script>
</body>
</html>
