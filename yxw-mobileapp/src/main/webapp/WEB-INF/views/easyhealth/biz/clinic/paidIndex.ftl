<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>缴费记录</title>
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
<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
	<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="">
	<input type="hidden" id="orderNo" name="orderNo" value="" />
</form>
<input type="hidden" id="moduleName" value="已缴费" />

<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/biz/common/stringUtils.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/clinic/app.paidIndex.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.skipPages.js"></script>
</body>
</html>