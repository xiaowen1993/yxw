<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>选择分院</title>
</head>
<body>
<body style="background-color: #fff">
<div id="body">
    <div id="Hospital-list">
        <ul class="hospital-list">
        <#list branchHospitals as branchHospital>
            <li onclick="goNext('${branchHospital.id}','${branchHospital.code}' , '${branchHospital.name}')">
                <div class="pic">
                    <img src="${basePath}yxw.app/images/yiyuan.png"  width="65"  height="65" />
                </div>
                <div class="info">
                    <div class="title skinColor fontSize120">${branchHospital.name}</div>
                    <div class="des">${branchHospital.address}</div>
                </div>
            </li>
        </#list>
        </ul>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
<form id="paramsForm" method="post">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
<input type="hidden" id="branchHospitalName" name="branchHospitalName" />
<input type="hidden" id="branchHospitalId" name="branchHospitalId" />
<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" />
<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
<input type="hidden" id="nextUrl" name="nextUrl" />
</form>
<!--
<button type="button" onclick="doRefresh()">doRefresh</button>
<button type="button" onclick="doGoBack()">doGoBack</button>
-->
</body>
</html>
<script type="text/javascript">
function goNext(branchHospitalId , branchHospitalCode , branchHospitalName){
    $("#branchHospitalId").val(branchHospitalId);
    $("#branchHospitalCode").val(branchHospitalCode);
    $("#branchHospitalName").val(branchHospitalName);
    $("#paramsForm").attr("action" ,"${basePath}${nextUrl}");
    $("#paramsForm").submit();
}

var urlType='${nextUrl}';
function doRefresh()
{
	if(urlType=='/mobileApp/queue/index' ){
		var freshFrom=$("<form></form>");
		freshFrom.append($('<input type="hidden" name="appId" value="${commonParams.appId}"/>'));
		freshFrom.append($('<input type="hidden" name="appCode" value="${appCode}"/>'));
		freshFrom.append($('<input type="hidden" name="openId" value="${commonParams.openId}" />'));
		freshFrom.append($('<input type="hidden" name="areaCode" value="${areaCode}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalName" value="${commonParams.hospitalName}"/>'));
		freshFrom.append($('<input type="hidden" name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
		freshFrom.append($('<input type="hidden" name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
		freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
		freshFrom.appendTo("body");
		freshFrom.css('display','none');
		freshFrom.attr("method","post");
		freshFrom.attr("action","${basePath}${nextUrl}");
		freshFrom.submit();
	}
	else if(urlType =='mobileApp/clinic/payIndex')
	{
		var freshFrom=$("<form></form>");
		freshFrom.append($('<input type="hidden" name="appId" value="${commonParams.appId}"/>'));
		freshFrom.append($('<input type="hidden" name="appCode" value="${appCode}"/>'));
		freshFrom.append($('<input type="hidden" name="openId" value="${commonParams.openId}" />'));
		freshFrom.append($('<input type="hidden" name="areaCode" value="${areaCode}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalName" value="${commonParams.hospitalName}"/>'));
		freshFrom.append($('<input type="hidden" name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
		freshFrom.append($('<input type="hidden" name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
		freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
		freshFrom.appendTo("body");
		freshFrom.css('display','none');
		freshFrom.attr("method","post");
		freshFrom.attr("action","${basePath}${nextUrl}");
		freshFrom.submit();
	}
	else if(urlType=='easyhealth/register/branchDeptes')
	{
		var freshFrom=$("<form></form>");
		freshFrom.append($('<input type="hidden" name="appId" value="${commonParams.appId}"/>'));
		freshFrom.append($('<input type="hidden" name="openId" value="${commonParams.openId}"/>'));
		freshFrom.append($('<input type="hidden" name="appCode" value="${appCode}"/>'));
		freshFrom.append($('<input type="hidden" name="areaCode" value="${areaCode}"/>'));
		freshFrom.append($('<input type="hidden" name="regType" value="${commonParams.regType}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalName" value="${commonParams.hospitalName}"/>'));
		freshFrom.appendTo("body");
		freshFrom.css('display','none');
		freshFrom.attr("method","post");
		freshFrom.attr("action","${basePath}easyhealth/register/index");
		freshFrom.submit();
	}
	else if(urlType=='mobileApp/reports/index')
	{
			var freshFrom=$("<form></form>");
			freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
			freshFrom.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
			freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
			freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
			freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="reportType" value="${commonParams.checkType}" />'));
			freshFrom.appendTo("body");
			freshFrom.css('display','none');
			freshFrom.attr("method","post");
			freshFrom.attr("action","${basePath}mobileApp/reports/index");
			freshFrom.submit();
	}
	else if(urlType=='easyhealth/healthlist/reports/index')
	{
		var freshFrom=$("<form></form>");
			freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
			freshFrom.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
			freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
			freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
			freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="reportType" value="${commonParams.checkType}" />'));
			freshFrom.appendTo("body");
			freshFrom.css('display','none');
			freshFrom.attr("method","post");
			freshFrom.attr("action","${basePath}easyhealth/healthlist/reports/index");
			freshFrom.submit();
	}
	else if(urlType=='mobileApp/medicalcard/family/toView')
	{
			var freshFrom=$("<form></form>");
			freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
			freshFrom.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
			freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
			freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
			freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="reportType" value="${commonParams.checkType}" />'));
			freshFrom.appendTo("body");
			freshFrom.css('display','none');
			freshFrom.attr("method","post");
			freshFrom.attr("action","${basePath}mobileApp/medicalcard/family/toView");
			freshFrom.submit();
	}
}

function doGoBack()
{	if(urlType == '/mobileApp/queue/index')
	{
		window.location.href="${basePath}mobileApp/queue/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
	else if(urlType=='easyhealth/register/branchDeptes')
	{
		window.location.href="${basePath}easyhealth/register/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
	else if(urlType=='mobileApp/reports/index')
	{
		window.location.href="${basePath}mobileApp/reports/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
	else if(urlType=='mobileApp/clinic/payIndex')
	{
		window.location.href="${basePath}mobileApp/clinic/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
	else if(urlType=='easyhealth/healthlist/reports/index')
	{
		window.location.href="${basePath}easyhealth/healthlist/reports/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
	else if(urlType=='mobileApp/medicalcard/family/toView')
	{
		window.location.href="${basePath}mobileApp/medicalcard/family/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
}
</script>