<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>选择医院</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
        <div class="space15"></div>
        <ul class="yx-list listRow">
        <#list entityList as hospital>
            <li class="arrow" onclick="goNext('${hospital.appId}','${hospital.appCode}','${hospital.hospitalId}','${hospital.hospitalCode}','${hospital.hospitalName}')">
                <div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>${hospital.hospitalName}
            </li>
        </#list>
        <!--
        <#if rmyy_data?exists>
	        <li class="arrow" onclick="gotoRMYY();">
                <div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>深圳市人民医院
            </li>
        </#if>
        -->
        </ul>
        <div class="space15"></div>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
<form id="paramsForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
	<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
	<input type="hidden" id="regMode" name="regMode" value="${commonParams.regMode}" />
	<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
	<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
</form>

<!--
<#if rmyy_data?exists>
	<form id="rmyyForm" method="post" action="http://www.169jk.com:8082/Proxy/Jump">
		<input type="hidden" id="data" name="data" value="${rmyy_data}" />
		<input type="hidden" id="signature" name="signature" value="${rmyy_signature}" />
		<input type="hidden" id="nonce" name="nonce" value="${rmyy_nonce}" />
	</form>
</#if>
-->

<!--<button type="button" onclick="doRefresh()">doRefresh</button>
<button type="button" onclick="doGoBack()">doGoBack</button>-->
</body>
</html>
<script type="text/javascript">
function goNext(appId ,appCode,hospitalId,hospitalCode,hospitalName){
    $("#appCode").val(appCode);
    $("#appId").val(appId);
    $("#hospitalId").val(hospitalId);
    $("#hospitalCode").val(hospitalCode);
    $("#hospitalName").val(hospitalName);
    $("#paramsForm").attr("action" ,"${basePath}${nextUrl}");
    $("#paramsForm").submit();
}

function gotoRMYY() {
	console.log('goto rmyy...');
	
	<#if rmyy_data?exists>
	$('#rmyyForm').submit();
	</#if>
}

var urlType= '${nextUrl}';
function doRefresh()
{
	if(urlType=='mobileApp/register/index')
	{
		window.location.href="${basePath}mobileApp/register/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=${commonParams.regType}";
	}
	else if(urlType=='mobileApp/queue/index')
	{
		window.location.href="${basePath}mobileApp/queue/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
	else if(urlType=='mobileApp/clinic/payIndex')
	{
		window.location.href="${basePath}mobileApp/clinic/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
	else if(urlType=='mobileApp/reports/index')
	{
		window.location.href="${basePath}mobileApp/reports/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
	else if(urlType=='easyhealth/healthlist/reports/index')
	{
		window.location.href="${basePath}easyhealth/healthlist/reports/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
	else if(urlType=='mobileApp/medicalcard/family/toView')
	{
		window.location.href="${basePath}mobileApp/medicalcard/family/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
}
function doGoBack()
{
	windowClose();
}
</script>