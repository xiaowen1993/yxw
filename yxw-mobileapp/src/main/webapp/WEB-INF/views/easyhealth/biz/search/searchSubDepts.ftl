<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>选二级科室</title>
</head>
<body>
<div id="body">
    <section class="mod-search">
        <section class="search-main">
            <div class="pageTitle">${commonParams.parentDeptName}</div>
            <article class="seach-result recent-search">
                <ul class="yx-list search-all">
                    <#list entityList as entity>
                        <li class="arrow" onclick="goRegInfo('${entity.deptCode}')">${entity.deptName}</li>
                    </#list>
                </ul>
            </article>
        </section>
    </section>
</div>
<form id="paramsForm" method="post" action="${basePath}mobileApp/register/doctor/index">
    <input type="hidden" id="searchStr"  name="searchStr"  value="${commonParams.searchStr}"/>
    <input type="hidden" id="appCode"    name="appCode"    value="${commonParams.appCode}"/>
    <input type="hidden" id="areaCode"   name="areaCode"   value="${commonParams.areaCode}"/>    
    <input type="hidden" id="searchType" name="searchType" value="${commonParams.searchType}"/> 
    <input type="hidden" id="regType"    name="regType"    value="${commonParams.regType}"/>
    <input type="hidden" id="openId"     name="openId"     value="${commonParams.openId}">
    
    <input type="hidden" id="appId"  name="appId" value="${commonParams.appId}">
    <input type="hidden" id="hospitalId" name="hospitalId"  value="${commonParams.hospitalId}"/>
    <input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
    <input type="hidden" id="hospitalName" name="hospitalName"  value="${commonParams.hospitalName}"/>
    <input type="hidden" id="branchHospitalId" name="branchHospitalId"  value="${commonParams.branchHospitalId}"/>
    <input type="hidden" id="branchHospitalCode" name="branchHospitalCode"  value="${commonParams.branchHospitalCode}" />
    <input type="hidden" id="branchHospitalName" name="branchHospitalName"  value="${commonParams.branchHospitalName}" />
    <input type="hidden" id="deptCode" name="deptCode" />
    <input type="hidden" id="deptName" name="deptName"  />
    <input type="hidden" id="doctorCode" name="doctorCode" />
    
    
    <input type="hidden" id="deptKey" name="deptKey" />
        <!--从搜索界面跳转到医生界面的标志，用于刷新返回-->
    <input type="hidden" id="viaFlag" name="viaFlag" value="searchDoctor"/>

</form>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
function goRegInfo(deptCode , deptName){
   $("#deptCode").val(deptCode);
   $("#deptName").val(deptName);
   $("#paramsForm").attr("action" , base.appPath + "easyhealth/register/doctor/index");
   $("#paramsForm").submit(); 
}
</script>