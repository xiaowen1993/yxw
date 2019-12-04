<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>排队候诊</title>
</head>
<body>
<div id="body">
	<div id="queue">
        <#include "/easyhealth/common/commonBizQueryHead.ftl">
        
        <#if families??&& families?size gt 0>
        <div class="section" id="reportContent">
           <div style="padding: 0 10px">
               <div class="js-tag flex">
                   <div class="tag flexItem active queues" queueType="1">候诊</div>
                   <div class="tag flexItem queues" queueType="3">取药</div>
                   <div class="tag flexItem queues" queueType="2">检查</div>
               </div>
           	</div>
			
			<div class="yxw-data">

			</div>
        </div>
        </#if>
        
        <div class="btn-w" style="display: none;">	
				<div class="btn btn-ok btn-block">刷新排队进度</div>
		</div>
	    <div id="commonTips" style="display: none;">
	    </div>
    </div>
</div>
<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="familyId" name="familyId" value="">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="">
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="patCardType" name="patCardType" value="" />
	<input type="hidden" id="patCardName" name="patCardName" value="" />
	<input type="hidden" id="patCardNo" name="patCardNo" value="" />
	<input type="hidden" id="queueType" name="queueType" value="" />
	<input type="hidden" id="patientMobile" name="patientMobile" value="" />
	<input type="hidden" id="forward" value="app/queue/index" />
</form>
	<input type="hidden" id="moduleName" name="moduleName" value="排队" />

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/common/stringUtils.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/queue/app.queueIndex.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
