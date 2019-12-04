<html>
<#include "/easyhealth/common/common.ftl">
<head>
<title>智能搜索</title>
</head>
<body>
<div id="body">
<#-- 
<div style="height:15px;background-color: #eee;"></div>
    <div id="ks-search" class="flex-wrap row-flex">
        <div class="iconBox"><i class="icon-search"></i></div>
        <div class="search_input flexWidth1">
            <input type="text" class="yx-input" id="searchKeyword" oninput="doSearch(this.value)" placeholder="请输入关键字" autocomplete="off"/>
        </div>
        <div id="search_btn" onclick="search()"><#if commonParams.searchType == "hospital">医院搜索</#if><#if commonParams.searchType == "dept">科室搜索</#if><#if commonParams.searchType == "doctor">医生搜索</#if></div>
    </div>
    <div class="section" style="overflow: visible">
        <ul class="search-list" id="searchList">
        </ul>
    </div>
</div>
-->

<section class="mod-search">
      <div class="search-view">
          <div class="search-input"><input type="text" id="searchKeyword" oninput="smartSearch.doSearch(this.value)" placeholder="请输入关键字" autocomplete="off"> </div>
          <div class="search-btn-warp"><button class="ui-btn ui-btn-seaech" type="button" id="search_btn" ><#if commonParams.searchType == "hospital">医院搜索</#if><#if commonParams.searchType == "dept">科室搜索</#if><#if commonParams.searchType == "doctor">医生搜索</#if></button></div>
      </div>
        <section class="search-main">
            <article id="searchList" class="seach-result">
            </article>
            
            <!-- 搜索记录为空的样式 -->
            <!-- <article class="seach-result recent-search">
                <div class="recent-search-empty">
                    	没有医生记录
                </div>
            </article> -->
        </section>
    </section>
    
<form id="paramsForm" method="post" action="${basePath}easyhealth/search/doSearch">
    <input type="hidden" id="searchStr"  name="searchStr"  value="${commonParams.searchStr}"/>
    <input type="hidden" id="appCode"    name="appCode"    value="${commonParams.appCode}"/>
    <input type="hidden" id="areaCode"   name="areaCode"   value="${commonParams.areaCode}"/>    
    <input type="hidden" id="searchType" name="searchType" value="${commonParams.searchType}"/> 
    <input type="hidden" id="regType"    name="regType"    value="${commonParams.regType}"/>
    <input type="hidden" id="openId" name="openId" value="${sessionUser.id}">
    
    <input type="hidden" id="appId"  name="appId">
    <input type="hidden" id="hospitalId" name="hospitalId" />
    <input type="hidden" id="hospitalCode" name="hospitalCode"  />
    <input type="hidden" id="hospitalName" name="hospitalName"  />
    <input type="hidden" id="branchHospitalId" name="branchHospitalId"  />
    <input type="hidden" id="branchHospitalCode" name="branchHospitalCode"  />
    <input type="hidden" id="branchHospitalName" name="branchHospitalName"  />
    <input type="hidden" id="deptCode" name="deptCode" />
    <input type="hidden" id="deptName" name="deptName"  />
    <input type="hidden" id="doctorCode" name="doctorCode" />
    
    
    <input type="hidden" id="deptNameKey" name="deptNameKey" />
    <input type="hidden" id="doctorNameKey" name="doctorNameKey" />
    <input type="hidden" id="parentDeptCode" name="parentDeptCode" />
    <input type="hidden" id="parentDeptName" name="parentDeptName" />
    <input type="hidden" id="isSearchDoctor" name="isSearchDoctor" value="1"/>
    <!--从搜索界面跳转到医生界面的标志，用于刷新返回-->
    <input type="hidden" id="viaFlag" name="viaFlag" value="searchDoctor"/>
</form>
<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/biz/register/app.smart.search.js" type="text/javascript"></script>
</body>
</html>
<script type="text/javascript">
function doRefresh()
{
	var freshForm=$("<form></form>");
	freshForm.append($('<input type="hidden" name="openId" value="${commonParams.openId}"/>'));
	freshForm.append($('<input type="hidden" name="appCode" value="${commonParams.appCode}"/>'));
	freshForm.append($('<input type="hidden" name="areaCode" value="${commonParams.areaCode}"/>'));
	freshForm.append($('<input type="hidden"  name="searchType" value="${commonParams.searchType}"/>'));
	freshForm.appendTo("body");
	freshForm.css('display','none');
	freshForm.attr("method","post");
	freshForm.attr("action","${basePath}easyhealth/search/index");
	freshForm.submit();
}
</script>