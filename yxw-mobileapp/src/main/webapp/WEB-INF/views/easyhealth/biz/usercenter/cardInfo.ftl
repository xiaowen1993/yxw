<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>医院就诊卡信息</title>
</head>
<body>
	<div id="body">
		<div class="qrcode">
            <div class="barCode">
        		<div id="barcodeTarget" class="barcodeTarget" style="margin: 0 auto;"></div>
        	</div>
	    </div>
	    <div class="space15"></div>
	    <ul class="yx-list">
	        <li class="flex">
	            <div class="flexItem">医院</div>
	            <div class="flexItem color666 textRight">${entity.hospitalName}</div>
	        </li>
	        <li class="flex">
	            <div class="flexItem">姓名</div>
	            <div class="flexItem color666 textRight">${entity.encryptedPatientName}</div>
	        </li>
	        <li class="flex">
	            <div class="flexItem">卡类型</div>
	            <div class="flexItem color666 textRight">
	            	<#switch entity.cardType>
                		<#case 1>就诊卡号<#break>
                		<#case 2>社保卡号<#break>
                		<#case 3>医保卡号<#break>
                		<#case 4>健康卡号<#break>
                		<#case 5>身份证号<#break>
                		<#case 6>市民卡号<#break>
                		<#case 7>病历号<#break>	<#-- <#case 7>患者唯一标识<#break> -->
                		<#case 8>临时诊疗卡号<#break>
                		<#default>
                	</#switch>
	            </div>
	        </li>
	        <li class="flex">
	            <div class="flexItem">卡号码</div>
	            <div class="flexItem color666 textRight">${entity.cardNo}</div>
	        </li>
	        <#if entity.ownership == 3>
            <li class="flex">
                <div class="flexItem">出生日期</div>
                <div class="flexItem color666 textRight">${entity.birth}</div>
            </li>
            </#if>
	    </ul>
	    <div class="space15"></div>
	    <#if entity.ownership != 3>
	    <ul class="yx-list">
	    	<li class="flex">
	            <div class="flexItem">证件类型</div>
	            <div class="flexItem color666 textRight">
	            	<#switch entity.idType>
	            		<#case 1>二代身份证<#break>
	            		<#case 2>港澳居民身份证<#break>
	            		<#case 3>台湾居民身份证<#break>
	            		<#case 4>护照<#break>
	            		<#default>
	            	</#switch>
	            </div>
	        </li>
	        <li class="flex">
	            <div class="flexItem">证件号码</div>
	            <div class="flexItem color666 textRight">${entity.encryptedIdNo}</div>
	        </li>
	        <li class="flex">
	            <div class="flexItem">手机号码</div>
	            <div class="flexItem color666 textRight">${entity.encryptedMobile}</div>
	        </li>
	    </ul>
	    <#else>
	    <ul class="yx-list">
        	<li class="flex">
                <div class="flexItem">监护人姓名</div>
                <div class="flexItem color666 textRight">${entity.encryptedGuardName}</div>
            </li>
        	<li class="flex">
                <div class="flexItem">监护人证件类型</div>
                <div class="flexItem color666 textRight">
                	<#switch entity.guardIdType>
	            		<#case 1>二代身份证<#break>
	            		<#case 2>港澳居民身份证<#break>
	            		<#case 3>台湾居民身份证<#break>
	            		<#case 4>护照<#break>
	            		<#default>
	            	</#switch>
                </div>
            </li>
            <li class="flex">
                <div class="flexItem">监护人证件号码</div>
                <div class="flexItem color666 textRight">${entity.encryptedGuardMobile}</div>
            </li>
            <li class="flex">
                <div class="flexItem">监护人手机号码</div>
                <div class="flexItem color666 textRight">${entity.encryptedGuardNo}</div>
            </li>
        </ul>
	    </#if>
	    
	    <div class="section btn-w">
	    	<div id="unbind" class="btn btn-ok btn-block">移除就诊卡</div>
	    </div>
	</div>    
	<form id="voForm" method="post" action="">
		<input type="hidden" id="openId" name="openId" value="${entity.openId}" />
		<input type="hidden" id="appCode" name="appCode" value="${entity.appCode}" />
		<input type="hidden" id="appId" name="appId" value="${entity.appId}" />
		<input type="hidden" id="hospitalCode" name="hospitalCode" value="${entity.hospitalCode}" />
		<input type="hidden" id="hospitalId" name="hospitalId" value="${entity.hospitalId}" />
		<input type="hidden" id="hospitalName" name="hospitalName" value="${entity.hospitalName}" />
		<input type="hidden" id="syncType" name="syncType" value="${commonParams.syncType}" />
		<input type="hidden" id="familyId" name="familyId" value="${entity.familyId}">
		<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
		<input type="hidden" id="medicalcardId" name="medicalcardId" value="${commonParams.medicalcardId}">
		<input type="hidden" id="forward" name="forward" value="${commonParams.forward}">
		<input type="hidden" id="id" name="id" value="${entity.id}">
	</form>
	
	<input type="hidden" id="cardNo" value="${entity.cardNo}">
	<input type="hidden" id="barcodeStyle" value="${rule.barcodeStyle}">
<#include "/easyhealth/common/footer.ftl">
</body>
<script type="text/javascript" src="${basePath}yxw.app/js/common/jquery-barcode.min.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/usercenter/app.cardInfo.js"></script>
</html>