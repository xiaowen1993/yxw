<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>设置</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
        <div class="space15"></div>
        <ul class="yx-list listRow">
            <li class="arrow" onclick="go('${basePath}easyhealth/customService/toFeedback?openId=${sessionUser.id}&type=2',true)"><div class="u-img"><i class="iconfont i-green">&#xe63f;</i></div>APP反馈</li>
            <li class="arrow" onclick="go('${basePath}easyhealth/user/toModifyPwd',true);"><div class="u-img"><i class="iconfont i-yellow">&#xe66b;</i></div>修改密码</li>
            
            <!-- 不是用手机号为账号的，才能修改手机 -->
            <#if sessionUser.account != sessionUser.mobile>
                <li class="arrow" onclick="go('${basePath}easyhealth/user/toModifyMobile',true);"><div class="u-img"><i class="iconfont i-orange">&#xe66c;</i></div>修改手机号</li>
            </#if>
            
            <!-- 只有android有这个功能 -->
            <li id="appUpdate" class="arrow" onclick="appUpdate();" style="display: none;"><div class="u-img"><i class="iconfont i-green">&#xe667;</i></div>检查新版本</li>
        </ul>
        <div class="btn-w">
            <button type="button" class="btn btn-ok btn-block" onclick="loginOut();">退出登录</button>
        </div>
        <div class="space15"></div>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
<script src="${basePath}yxw.app/js/biz/user/eh.login.js" type="text/javascript"></script>
<script>
	var appVersion = '';
	
	$(function () {
		setTimeout(function(){
			appVersion = getVersion();
			var newVersion = '1.1.6_t';
			if(IS.isAndroid && appVersion.localeCompare(newVersion) != -1){
				$('#appUpdate').show();
			}
		}, 500);
	});
	
	function getVersion() {
		//android函数原型	window.yx129.appGetVersion();
		//ios 函数原型	appGetVersion();
		var appVersion = "未知";
		if(IS.isMacOS) {
	    	appVersion = appGetVersion();
	    } else if(IS.isAndroid) {
	      appVersion = window.yx129.appGetVersion();
	    }
		
		return appVersion;
	}
	
	function appUpdate() {
		if(IS.isAndroid){
			try {
              window.yx129.appUpdate();
          	} catch (e) {
          		
          	}
		}
	}
</script>
</html>
