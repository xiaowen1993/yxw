<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>个人信息</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
    		<div class="space15"></div>
            <ul class="yx-list">
                <!-- <li class="user_pic flex arrow"> 去掉箭头，需要恢复箭头需要去掉img里的style -->
                <li class="user_pic flex">
                    <div class="label flexItem w100">头像</div>
                    <div class="values flexItem textRight">
                    <#if sessionUser.sex == 1>
	                		<img src="${basePath}/easyhealth/images/man-def.png" width="40" height="40" style="right:5px;"/>
	                	<#elseif sessionUser.sex == 2>
	                		<img src="${basePath}/easyhealth/images/girl-def.png" width="40" height="40" style="right:5px;"/>
	                	<#else>
	                		<img src="${basePath}/easyhealth/images/doctor_thumb_100.png" width="40" height="40" style="right:5px;"/>
	                	</#if>
                    </div>
                </li>
                <li class="flex">
                    <div class="label flexItem w100">姓名</div>
                    <div class="values flexItem color666 textRight">${sessionUser.encryptedName}</div>
                </li>
                <li class="flex">
                    <div class="label flexItem w100">性别</div>
                    <div class="values color666 flexItem textRight">
                    	<#if sessionUser.sex == 1>
	                		男
	                	<#elseif sessionUser.sex == 2>
	                		女
	                	<#else>
	                		未知
	                	</#if>
                    </div>
                </li>
            </ul>
        <div class="box-list">
            <ul class="yx-list">
                <li class="flex">
                    <div class="label flexItem w100">证件号</div>
                    <!-- <div class="values color666 flexItem textRight">${sessionUser.encryptedCardNo}</div> -->
                    <div class="values color666 flexItem textRight">${sessionUser.cardNo}</div>
                </li>
                <li class="flex">
                    <div class="label flexItem w100">手机号</div>
                    <div class="values color666 flexItem textRight">${sessionUser.encryptedMobile}</div>
                </li>
            </ul>
        </div>
    </div>

		<!--
    <div class="btn-w">
			<div class="btn btn-ok btn-block" onclick="toModifyPwd();">修改密码</div>
    </div>
    <div class="btn-w" style="padding-top:0;">
			<div class="btn btn-ok btn-block" onclick="toModifyMobile();">修改手机号</div>
    </div>
    <div class="btn-w" style="padding-top:0;">
			<div class="btn btn-ok btn-block" onclick="loginOut();">退出</div>
    </div>
    -->
</div>

<#include "/easyhealth/common/footer.ftl">

<script src="${basePath}yxw.app/js/biz/user/eh.login.js" type="text/javascript"></script>
</body>
</html>