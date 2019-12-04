<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>报告查询</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div id="payRecord">
		<#include "/easyhealth/common/commonBizQueryHead.ftl">
		
		<#if families??&& families?size gt 0>
        <div class="section" id="reportContent">
           <div style="padding: 0 10px">
               <div class="js-tag flex">
                   <div class="tag flexItem active reports" reportType="1">检验</div>
                   <div class="tag flexItem reports" reportType="2">检查</div>
                   <!--
                   <div class="tag flexItem reports" reportType="3">体检</div>
                   -->
               </div>
           	</div>
			
			<div class="yxw-data">
	            <ul class="yx-list js-tagContent show noBorder" id="reportList">
	               
	            </ul>
	            
			</div>
        </div>
        </#if>
        
	    <div id="commonTips" style="display: none;">
	    </div>
	    <#--
	    <div class="btn-w">
	    	<div class="btn btn-ok btn-block" onclick="doGoBack();">返回</div>
	    	<div class="btn btn-ok btn-block" onclick="doRefresh();">刷新</div>
	    </div>
	    --> 
    </div>
</div>
<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}">
	<input type="hidden" id="familyId" name="familyId" value="">
	<input type="hidden" id="reportType" name="reportType" value="1">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="patCardType" name="patCardType" value="" />
	<input type="hidden" id="patCardNo" name="patCardNo" value="" />
	<input type="hidden" id="encryptedPatientName" name="encryptedPatientName" value="" />
	<input type="hidden" id="detailId" name="detailId" value="" />
	<input type="hidden" id="admissionNo" name="admissionNo" value="" />
	<input type="hidden" id="doctorName" name="doctorName" value="" />
	<input type="hidden" id="deptName" name="deptName" value="" />
	<input type="hidden" id="reportTime" name="reportTime" value="" />
	<input type="hidden" id="executeTime" name="executeTime" value="" />
	<input type="hidden" id="patCardName" name="patCardName" value="" />
	<input type="hidden" id="checkType" name="checkType" value="" />
	<input type="hidden" id="fileAddress" name="fileAddress" value="" />
	<input type="hidden" id="forward" value="app/report/index" />
</form>
	<input type="hidden" id="moduleName" value="报告" />

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/common/stringUtils.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/common/nav-list.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/report/app.reportIndex.js?v=1.30" type="text/javascript"></script>