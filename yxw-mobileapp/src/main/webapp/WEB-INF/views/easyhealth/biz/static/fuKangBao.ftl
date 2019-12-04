<!DOCTYPE html>
<html>
<head>
	<#include "/easyhealth/common/common.ftl">
    <title>福康宝</title>
</head>
<body>
<div id="body">
	<div id="myCenter">
 		<div class="space15"></div>
 		<ul class="yx-list listRow">
 			<li class="arrow" onclick="go('${basePath}easyhealth/fuKangBaoBuilding?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',true);"><div class="u-img"><i class="iconfont i-orange">&#xe650;</i></div>返现</li>
 			<li class="arrow" onclick="go('${basePath}easyhealth/fuKangBaoBuilding?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',true);"><div class="u-img"><i class="iconfont i-green">&#xe651;</i></div>积分兑换</li>
 			<li class="arrow" onclick="go('${basePath}easyhealth/fuKangBaoBuilding?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',true);"><div class="u-img"><i class="iconfont i-yellow">&#xe652;</i></div>优惠券</li>
		</ul>
	</div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
