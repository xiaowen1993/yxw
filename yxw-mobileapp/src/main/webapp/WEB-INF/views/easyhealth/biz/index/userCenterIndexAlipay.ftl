<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
    <#include "/easyhealth/common/common.ftl">
    <title>个人中心</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
   		<div class="space15"></div>
				<#if sessionUser.cardNo>
	        <ul class="yx-list userList">
	            <li class="pic arrow" onclick="go('${basePath}easyhealth/usercenter/familyInfo/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&syncType=1&forward=userCenterIndex', true)">
		            	<#if sessionUser.sex == 1>
		            		<img src="${basePath}/easyhealth/images/man-def.png" width="60" height="60"/>
		            	<#else>
		            		<img src="${basePath}/easyhealth/images/girl-def.png" width="60" height="60"/>
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
		            		<img src="${basePath}/easyhealth/images/girl-def.png" width="60" height="60"/>
		            	<#else>
		            		<img src="${basePath}/easyhealth/images/man-def.png" width="60" height="60"/>
		            	</#if>
	                <div class="info">请先完善资料</div>
	            </li>
	        </ul>
				</#if>
		    <div class="space15"></div>
				<ul class="yx-list listRow" >
		 	 		<li class="arrow" onclick="go('${basePath}easyhealth/usercenter/syncCard/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);"><div class="u-img"><i class="iconfont i-orange">&#xe684;</i></div>同步本人医院信息</li>
		 	 		<li class="arrow" onclick="go('${basePath}easyhealth/usercenter/myFamily/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);"><div class="u-img"><i class="iconfont i-orange">&#xe632;</i></div>我的家人</li>
          <li class="arrow" onclick="go('${basePath}easyhealth/usercenter/historyRegDoctorIndex?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-blue">&#xe630;</i></div>我的医生</li>
          <li class="arrow" onclick="go('${basePath}easyhealth/usercenter/toRegRecordList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-yellow">&#xe637;</i></div>挂号记录</li>
          <li class="arrow" onclick="go('${basePath}mobileApp/clinic/paidIndex?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true)"><div class="u-img"><i class="iconfont i-green">&#xe636;</i></div>缴费记录</li>
          <li class="arrow" onclick="go('${basePath}mobileApp/vote/listByOpenId?openId=${sessionUser.id}&appCode=${appCode}&bizCode=2&viewType=allVote', true)"><div class="u-img"><i class="iconfont i-blue">&#xe640;</i></div>就诊评价</li>
          <li class="arrow" class="serverGif" onclick="go('${basePath}mobileApp/customService/toFeedback?openId=${sessionUser.id}&type=1',true)"><div class="u-img"><i class="iconfont i-green">&#xe63f;</i></div>客服中心</li>

				</ul>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/index/eh.index.js" type="text/javascript"></script>
<script type="text/javascript">

</script>
