<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>搜索医生</title>
</head>
<body style="background-color: #fff">
<div id="body">
   <#--  <div id="ks-search" class="flex-wrap row-flex">
       <div class="iconBox"><i class="icon-search"></i></div>
        <div class="search_input flexWidth1">
            <input type="text" class="yx-input" id="searchKeyword" oninput="search.forDoctors(this.value)" placeholder="请输入关键字" autocomplete="off"/>
        </div>
        <div id="search_btn"  onclick="searchDoctorsButton()">医生搜索</div>
    </div>
    <div class="section" style="overflow: visible">
        <ul class="search-list" id="searchList">
        </ul>
    </div> -->
    
    <section class="mod-search">
        <section class="search-select">
            <input type="text" placeholder="请输入医生关键字" class="search-select-input" id="searchKeyword" oninput="search.forDoctors(this.value)" autocomplete="off">
            <button id="search_btn" row-data="doctor" class="search-select-btn">搜索</button>
        </section>
        <section class="search-main" >
            <#-- 搜索记录为空的样式 
            <article class="seach-result recent-search">
                <div class="recent-search-empty">
                    没有最近搜索记录
                </div>
            </article>-->
            <#-- 搜索结果页 -->
            <article class="seach-result recent-search" id="searchList"></article>
        </section>
    </section>
</div>
<form id="paramsForm" method="post">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}">
<input type="hidden" id="appId" name="appId" value="${commonParams.appId}">
<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}">
<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />


<input type="hidden" id="searchStr" name="searchStr">
<input type="hidden" id="deptCode" name="deptCode">
<input type="hidden" id="doctorCode" name="doctorCode">

<input type="hidden" id="isSearchDoctor" name="isSearchDoctor" value="1" />

<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
</form>
<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/biz/register/app.search.js" type="text/javascript"></script>
</body>
</html>