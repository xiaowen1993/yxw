<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title><#if commonParams.syncType==1>关联本人医院信息<#else>关联医院信息</#if></title>
<style>
	.syncedTips {
	    display: initial;
	    line-height: 2em;
	    text-align: center;
	    padding: 0 1.3em;
	    margin: 0;
    }
</style>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div class="userCardBox">
       <div class="box-tips icontips">
        	<i class="iconfont">&#xe60d;</i>
           	 使用医院服务（如挂号、缴费、查报告）之前，需要先关联在医院的就诊卡信息。关联完成之后您可以在个人资料页查看已关联的医院信息。关联医院信息需要花费1-3分钟，建议在网络良好的环境下进行。
        </div>
        <div class="page-title">选择要同步的医院信息</div>
        <ul class="yx-list">
        	<#if !commonParams.hospitalCode?exists || commonParams.hospitalCode == ''>
            	<#list entityMap?keys as key>
	            	<li class="flex">
		                <div class="flexItem fontSize1	">${entityMap["${key}"].hospital.hospitalName}</div>
		                <div class="flexItem textRight" appId="${entityMap["${key}"].hospital.appId}" hospitalName="${entityMap["${key}"].hospital.hospitalName}" hospitalId="${entityMap["${key}"].hospital.hospitalId}" hospitalCode="${entityMap["${key}"].hospital.hospitalCode}">
		                	<#if entityMap[key].card == 'false'>
		                	<span class="color999 syncTips" style="display: none;">正在关联...</span>
		                	<div class="btn-async asyncCard">关联</div>
		                	<div class="btn-async manualBind" style="display: none;">手动绑卡</div>
							<#else>
							<span class="skinColor syncedTips">已关联</span>
							</#if>
		                </div>
		            </li>
            	</#list>
			<#else>
		    	<#list entityMap?keys as key>
		    		<#if commonParams.hospitalCode == key>
		            	<li class="flex">
			                <div class="flexItem fontSize1	">${entityMap["${key}"].hospital.hospitalName}</div>
			                <div class="flexItem textRight" appId="${entityMap["${key}"].hospital.appId}" hospitalName="${entityMap["${key}"].hospital.hospitalName}" hospitalId="${entityMap["${key}"].hospital.hospitalId}" hospitalCode="${entityMap["${key}"].hospital.hospitalCode}">
			                	<#if entityMap[key].card == 'false'>
			                	<span class="color999 syncTips" style="display: none;">正在关联...</span>
			                	<div class="btn-async asyncCard">关联</div>
			                	<div class="btn-async manualBind" style="display: none;">手动绑卡</div>
								<#else>
								<span class="skinColor syncedTips">已关联</span>
								</#if>
			                </div>
			            </li>
		            </#if>
            	</#list>	    	
		    </#if>
        </ul>
        <div class="btn-w" id="complete">
            <div class="btn btn-ok btn-block">完成</div>
        </div>
        <#--
        <#if commonParams.syncType==1>
        <div class="btn-w">
            <div class="btn btn-ok btn-block"">查看我的就诊卡</div>
        </div>
        </#if>
         -->
    </div>
</div>
<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="syncType" name="syncType" value="${commonParams.syncType}">
	<input type="hidden" id="hospitalId" name="hospitalId" value="">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}">
	<input type="hidden" id="hospitalName" name="hospitalName" value="">
	<input type="hidden" id="familyId" name="familyId" value="${commonParams.familyId}">
	<input type="hidden" id="forward" name="forward" value="${commonParams.forward}">
</form>

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/usercenter/app.asyncCard.js" type="text/javascript"></script>