<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>${commonParams.hospitalName}</title>
</head>
<body>
<div id="body">
   
    <div class="search-view">
        <div class="search-select">
            <select name="searchCode" id="searchCode">
                <#if isHasSearChDoctor == 1> 
					<option value="1" selected="selected">医生</option>               
                <#elseif isHasSearChDoctor == 2 && commonParams.regType == 2>
                	<option value="1" selected="selected">医生</option>
            	<#elseif isHasSearChDoctor == 3 && commonParams.regType == 1> 
            	<option value="1" selected="selected">医生</option>
                <#else> 
                </#if>
                <option value="2" >科室</option>
            </select>
        </div>
        <div id="search" class="search-input"><i class="iconfont">&#xe61a;</i>请输入关键字<span class="btn-seaech">搜索</span></div>
    </div>
    

       <div class="ui-ceGuaHao">
		   <div class="ui-ceGuaHao-title">
		       曾挂号医生：
		   </div>
		    <div id="hadRegDoctors" class="ui-ceGuaHao-centent">
			    <#list hadRegDoctors?reverse as hadRegDoctor>
			    	<a href="#" id="${hadRegDoctor.deptCode}:${hadRegDoctor.doctorCode}" class="ui-ceGuaHao-lebel">${hadRegDoctor.doctorName}</a>
		        </#list>
		    </div>
		</div>
	
    <div id="ks-list" >
        <div class="ks-list-right onlyOne">
            <div id="right-list-main">
                <div class="scroller">
                   <ul style="display: block">
                       <#list depts as domain>
	                       <li id="${domain.deptCode}" name="${domain.deptName}">${domain.deptName}</li>
	                   </#list>
                    </ul>
                </div>
            </div>
            <div id="right-ks_arrow"><i class="iconfont">&#xe600;</i></div>
        </div>
    </div>
</div>

<form id="paramsForm" method="post">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
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
<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
<input type="hidden" id="searchType" name="searchType"/>
</form>
<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/biz/register/app.chooseNoSubDept.js?v=1.0" type="text/javascript"></script>
</body>
</html>
<script type="text/javascript">

    function doRefresh()
	{
		window.location.href="${basePath}easyhealth/register/refreshDepts?branchHospitalId=${commonParams.branchHospitalId}&branchHospitalCode=${commonParams.branchHospitalCode}"+
		"&appId=${commonParams.appId}&openId=${commonParams.openId}&appCode=${commonParams.appCode}&areaCode=${commonParams.appCode}"+
		"&regType=${commonParams.regType}&hospitalId=${commonParams.hospitalId}&hospitalCode=${commonParams.hospitalCode}";
	}
</script>