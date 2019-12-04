<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>选时间</title>
    <#-- 日期插件样式 -->
    <link rel="stylesheet" href="${basePath}yxw.app/plugin/mobiscroll/css/mobiscroll.custom-3.0.0-beta2.min.css">
    <#-- 日期插件JS-->
    <script src="${basePath}yxw.app/plugin/mobiscroll/mobiscroll.custom-3.0.0-beta2.min.js"></script>
</head>
<body>
<div id="body">
    <div class="doctor-list">
        <ul>
            <li class="doctorCart">
            	<div class="cartMain">
	                <div class="pic">
	                    <#if commonParams.doctorPic == "">
	                        <img src="${basePath}yxw.app/images/touxiang.png"  width="65" height="65"/>
	                    <#else>
	                        <img src="${commonParams.doctorPic}"  width="65" height="65"/>
	                    </#if>
	                </div>
	                <div class="info">
	                    <div class="title">${commonParams.doctorName}</div>
	                    <div class="mate">${commonParams.doctorTitle}</div>
	                </div>
	                <div class="li-right">
	                    <#if commonParams.category == "1">
	                    <span class="tag skinBgColor">专家号</span>
	                    </#if>
	                </div>
                </div>
                 
                <#if commonParams.doctorIntrodution ??>
                <div class="des">
                    <#if commonParams.doctorIntrodution?length < 50 >
                    ${commonParams.doctorIntrodution}
                    <#else>
                    <span id="showInfo"  class="more skinColor" style="display:''">${commonParams.doctorIntrodution?substring(0,30)}...</span>
                    <span id="showLable" class="more skinColor" style="display:''" onclick="doctorCart_show(this)">展开</span>
                    <span id="hiddenInfo" class="more skinColor" style="display:none">${commonParams.doctorIntrodution}</span><span id="hideLable"  style="display:none" class="more skinColor" onclick="doctorCart_show(this)">收起</span>
                    </#if>
                </div>
                </#if>
            </li>
        </ul>
    </div>

    <div class="space15"></div>
    
	<div class="time-list">
        <ul>
            <li class="t-header-w">
                <input type="text" class="js-date" value="" readonly="" />
                <#if commonParams.regType == 1 >
                	<div id="dateView" class="dateView">切换日期<i class="iconfont">&#xe61d;</i></div>
                </#if>
            </li>
         </ul>
         
         <ul id="doctimes_header">
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
<input type="hidden" id="doctorName" name="doctorName" value="${commonParams.doctorName}">
<input type="hidden" id="selectRegDate" name="selectRegDate" value="${commonParams.selectRegDate}">
<input type="hidden" id="category" name="category" value="${commonParams.category}">
<input type="hidden" id="doctorTitle" name="doctorTitle" value="${commonParams.doctorTitle}">
<input type="hidden" id="doctorBeginTime" name="doctorBeginTime" value="${commonParams.doctorBeginTime}">
<input type="hidden" id="doctorEndTime" name="doctorEndTime" value="${commonParams.doctorEndTime}">
<input type="hidden" id="regFee" name="regFee" value="${commonParams.regFee}">
<input type="hidden" id="treatFee" name="treatFee" value="${commonParams.treatFee}" />
<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
<input type="hidden" id="workId" name="workId" value="${commonParams.workId}" />
<input type="hidden" id="timeFlag" name="timeFlag" value="${commonParams.timeFlag}"/>
<input type="hidden" id="viaFlag" name="viaFlag" value="${viaFlag}" />


<!--<button type="button" onclick="doRefresh()">doRefresh</button>
<button type="button" onclick="doGoBack()">doGoBack</button>-->
</form>

<input type="hidden" id="selectedIndex" name="selectedIndex" value="${selectedIndex}" />
<input type="hidden" id="indexCount" name="indexCount" value="${indexCount}" />
<input type="hidden" id="optionalDates" name="optionalDates" value="${optionalDates}" />

<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/common/crypto-js.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/common/stringUtils.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/register/app.chooseDoctorTime.js?v=1.1" type="text/javascript"></script>
</body>
</html>
<script type="text/javascript">

function doRefresh()
{
	var now = new Date();
	var timeTemp = now.getTime();
	var selectDate=$("#selectDate").text().substring(0,10);
	if(viaFlag.indexOf('history')!=-1||viaFlag.indexOf('search')!=-1||viaFlag.indexOf('regDoctor')!=-1)
	{
		window.location.href="${basePath}easyhealth/register/doctorTime/index?timeTemp=" + timeTemp+"&branchHospitalId=${commonParams.branchHospitalId}&branchHospitalCode=${commonParams.branchHospitalCode}"
		+"&branchHospitalName=${commonParams.branchHospitalName}&selectRegDate="+selectDate+"&category=${commonParams.category}&isHasDutyReg=${isHasDutyReg}&nextDay=${nextDay}"
		+"&isSearchDoctor=${commonParams.isSearchDoctor}&isHasAppointmentReg=${isHasAppointmentReg}&showDays=${showDays}&deptCode=${commonParams.deptCode}&deptName=${commonParams.deptName}"
		+"&doctorCode=${commonParams.doctorCode}&appId=${commonParams.appId}&openId=${commonParams.openId}&appCode=${commonParams.appCode}&areaCode=${commonParams.areaCode}"
		+"&regType=${commonParams.regType}&hospitalId=${commonParams.hospitalId}&hospitalCode=${commonParams.hospitalCode}&hospitalName=${commonParams.hospitalName}&viaFlag=${viaFlag}";
	}
	else
	{
		window.location.href="${basePath}easyhealth/register/doctorTime/index?timeTemp=" + timeTemp+"&branchHospitalId=${commonParams.branchHospitalId}&branchHospitalCode=${commonParams.branchHospitalCode}"
		+"&branchHospitalName=${commonParams.branchHospitalName}&selectRegDate="+selectDate+"&category=${commonParams.category}&isHasDutyReg=${isHasDutyReg}&nextDay=${nextDay}"
		+"&isSearchDoctor=${commonParams.isSearchDoctor}&isHasAppointmentReg=${isHasAppointmentReg}&showDays=${showDays}&deptCode=${commonParams.deptCode}&deptName=${commonParams.deptName}"
		+"&doctorCode=${commonParams.doctorCode}&appId=${commonParams.appId}&openId=${commonParams.openId}&appCode=${commonParams.appCode}&areaCode=${commonParams.areaCode}"
		+"&regType=${commonParams.regType}&hospitalId=${commonParams.hospitalId}&hospitalCode=${commonParams.hospitalCode}&hospitalName=${hospitalName}";
	}
}

function doGoBack()
{
	if(viaFlag.indexOf('history')!=-1)
	{
		window.location.href="${basePath}/easyhealth/usercenter/historyRegDoctorIndex?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
	else if(viaFlag.indexOf('search')!=-1)
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
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}easyhealth/register/doctor/index");
		freshForm.submit();
	}
	else if(viaFlag.indexOf('regDoctor')!=-1)
	{
		window.location.href=appPath+"easyhealth/healthlist/regDoctor/index?openId=${commonParams.openId}&appCode=${commonParams.appCode}&areaCode=${commonParams.areaCode}";
	}
	else
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
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}easyhealth/register/doctor/index");
		freshForm.submit();
	}
	
	
}
</script>