<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>挂号记录</title>
</head>
<body>
<div id="body">
    <#include "/easyhealth/common/commonRecordQueryHead.ftl">
	<div class="yxw-data">
		<div id="payRecord"></div>
    </div>
    <div id="commonTips" style="display: none;">
    </div>
</div>

<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}">
	<input type="hidden" id="hospitalId" name="hospitalId" value="" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="cardNo" name="cardNo" value="" />
	<input type="hidden" id="orderNo" name="orderNo" value="" />
</form>

<input type="hidden" id="moduleName" value="挂号记录" />
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/common/stringUtils.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/usercenter/app.regRecordList.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.skipPages.js"></script>