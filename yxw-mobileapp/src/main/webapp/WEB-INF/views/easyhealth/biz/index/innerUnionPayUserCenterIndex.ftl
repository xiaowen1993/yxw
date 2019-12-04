<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
	<meta charset="UTF-8">
	<meta content="width=device-width ,maximum-scale=1" name="viewport">
	<meta content="yes" name="apple-mobile-web-app-capable">
	<meta content="black" name="apple-mobile-web-app-status-bar-style">
	<link rel="stylesheet" href="${basePath}yxw.app/css/style.css?v=1.0<#if appColor?exists>&yxColor=${appColor}</#if>" id="yxColor"/>
	<script type="text/javascript" src="${basePath}yxw.app/js/common/jquery.min1.9.js"></script>
	<script src="${basePath}yxw.app/js/common/fastclick.js" type="text/javascript"></script>
	<script src="${basePath}yxw.app/js/common/common.js?v=1.0" type="text/javascript"></script>
	<script type="text/javascript">
	    var appPath = '${basePath}';
	    var curuser = {
	    	openId: '${sessionUser.id}'
	    }
	    
	    var base = {
	    	appPath: '${basePath}',
	    	openId: '${sessionUser.id}',
	    	appCode: '${appCode}',
	    	areaCode: '${areaCode}'
	    }
	</script>
	<#include "/easyhealth/common/unionpay.ftl">

    <title>个人中心</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
   		<div class="space15"></div>
		<#if sessionUser.cardNo>
        <ul class="yx-list userList">
            <li class="pic arrow" onclick="go('${basePath}app/usercenter/familyInfo/index?openId=${sessionUser.id}&appCode=${appCode}&syncType=1', true)">
	            	<#if sessionUser.sex == 1>
	            		<img src="${basePath}yxw.app/images/man-def.png" width="60" height="60"/>
	            	<#else>
	            		<img src="${basePath}yxw.app/images/girl-def.png" width="60" height="60"/>
	            	</#if>
                <div class="info">
                    <div class="title">${sessionUser.encryptedName}
                    	<#if sessionUser.sex == 1>
                    		<i class="iconfont icon-man">&#xe63a;</i>
                    		<#else>
            						<i class="iconfont icon-girl">&#xe639;</i>
            					</#if>
                    </div>
                    <!-- <div class="name color999">${sessionUser.encryptedCardNo}</div> -->
                    <div class="name color999">${sessionUser.encryptedAccount}</div>
                </div>
            </li>
        </ul>
   			<#else>
   			<ul class="yx-list userList">
            <li class="pic arrow" onclick="go('${basePath}easyhealth/user/toPerfectUserInfo', true)">
	            	<#if sessionUser.sex == 2>
	            		<img src="${basePath}yxw.app/images/girl-def.png" width="60" height="60"/>
	            	<#else>
	            		<img src="${basePath}yxw.app/images/man-def.png" width="60" height="60"/>
	            	</#if>
                <div class="info">请先完善资料</div>
            </li>
        </ul>
		</#if>
        <div class="space15"></div>
		<ul class="yx-list listRow" >
 	 		<li class="arrow" onclick="go('${basePath}app/usercenter/syncCard/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&forward=easyhealth/userCenterIndex', true);"><div class="u-img"><i class="iconfont i-orange">&#xe684;</i></div>同步本人医院信息</li>
 	 		<li class="arrow" onclick="go('${basePath}app/usercenter/myFamily/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);"><div class="u-img"><i class="iconfont i-orange">&#xe632;</i></div>我的家人</li>
            <li class="arrow" onclick="go('${basePath}app/usercenter/historyRegDoctorIndex?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-blue">&#xe630;</i></div>我的医生</li>
            <li class="arrow" onclick="go('${basePath}app/usercenter/regRecords/list?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-yellow">&#xe637;</i></div>挂号记录</li>
            <li class="arrow" onclick="go('${basePath}app/clinic/paid/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-green">&#xe636;</i></div>缴费记录</li>
            <li class="arrow" onclick="go('${basePath}app/nih/list?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-green">&#xe636;</i></div>预约记录</li>
		</ul>
		
		<div class="btn-w">
		 <a href="tel:4008-933-934" class="btn btn-block" style="color:#fff;display:block">客服电话：4008-933-934</a>
		</div>
</div>
<#include "/easyhealth/common/menu_footer.ftl">
</body>
</html>

