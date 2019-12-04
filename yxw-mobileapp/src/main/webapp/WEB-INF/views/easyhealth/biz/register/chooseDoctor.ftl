<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>${commonParams.deptName}</title>
</head>
<body>
<div id="body">
    <div id="select-date"></div>
    <#if isHasDutyReg == 1>
	<div class="box-tips"> <i class="icon-warn"></i> 温馨提示：${onDutyRegTipContent}</div>
    </#if>
    <div class="doctor-list">
        <ul id="doctorList">
        </ul>
    </div>
</div>
<form id="paramsForm" method="post">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
<input type="hidden" id="showDays" name="showDays" value="${showDays}" >
<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}">
<input type="hidden" id="appId" name="appId" value="${commonParams.appId}">
<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}" />
<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${commonParams.branchHospitalName}" />
<input type="hidden" id="deptCode" name="deptCode" value="${commonParams.deptCode}">
<input type="hidden" id="deptName" name="deptName" value="${commonParams.deptName}">
<input type="hidden" id="doctorCode" name="doctorCode" value="${commonParams.doctorCode}">
<input type="hidden" id="selectRegDate" name="selectRegDate" value="${commonParams.selectRegDate}">
<input type="hidden" id="category" name="category" value="${commonParams.category}">
<input type="hidden" id="isHasDutyReg" name="isHasDutyReg" value="${isHasDutyReg}" />
<input type="hidden" id="nextDay" name="nextDay" value="${nextDay}" />
<input type="hidden" id="isSearchDoctor" name="isSearchDoctor" value="${commonParams.isSearchDoctor}" />
<input type="hidden" id="isHasAppointmentReg" name="isHasAppointmentReg" value="${isHasAppointmentReg}" />
<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
<input type="hidden" id="viaFlag" name="viaFlag" value="${viaFlag}" />
<!--<button type="button" onclick="doRefresh()">doRefresh</button>
<button type="button" onclick="doGoBack()">doGoBack</button> -->
</form>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/register/app.chooseDoctor.js" type="text/javascript"></script>
<script>
	var viaFlag='${viaFlag}';
	
	function doRefresh()
	{
		var freshForm=$("<form></form>");
		freshForm.append($('<input type="hidden" name="branchHospitalId" value="${commonParams.branchHospitalId}"/>'));
		freshForm.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}"/>'));
		freshForm.append($('<input type="hidden" name="branchHospitalName" value="${commonParams.branchHospitalName}"/>'));
		freshForm.append($('<input type="hidden"  name="deptCode" value="${commonParams.deptCode}">'));
		freshForm.append($('<input type="hidden"  name="deptName" value="${commonParams.deptName}">'));
		freshForm.append($('<input type="hidden"  name="doctorCode" value="${commonParams.doctorCode}">'));
		freshForm.append($('<input type="hidden" name="appId" value="${commonParams.appId}"/>'));
		freshForm.append($('<input type="hidden" name="openId" value="${commonParams.openId}"/>'));
		freshForm.append($('<input type="hidden" name="appCode" value="${commonParams.appCode}"/>'));
		freshForm.append($('<input type="hidden" name="areaCode" value="${commonParams.areaCode}"/>'));
		freshForm.append($('<input type="hidden" name="regType" value="${commonParams.regType}"/>'));
		freshForm.append($('<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}"/>'));
		freshForm.append($('<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>'));
		freshForm.append($('<input type="hidden" name="hospitalName" value="${commonParams.hospitalName}"/>'));
		freshForm.append($('<input type="hidden" name="viaFlag" value="${viaFlag}"/>'));
		freshForm.append($('<input type="hidden"  name="searchType" value="${searchType}"/>'));
		freshForm.append($('<input type="hidden"  name="searchStr" value="${searchStr}"/>'));
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action", base.appPath + "easyhealth/register/doctor/index");
		freshForm.submit();
	}
</script>
