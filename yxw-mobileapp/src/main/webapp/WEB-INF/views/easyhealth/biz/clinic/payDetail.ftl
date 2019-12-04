<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>待缴费明细</title>
<style>
.box-list{
	margin:0;
}
.accordion .acc-li .acc-header {
    position: relative;
    padding: .9em;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    padding-right: 35px;
    background: #f3f3f3;
}

.accordion .acc-li .acc-content {
    background-color: #f7f7f7;
}
.accordion .acc-li .acc-content .item {
    padding: 0.4em 1em;
    position: relative;
    margin: 0;
    overflow: hidden;
    background: #e2e2e2;
    font-size: 14px;
}

.page-title {
    padding: 0.8em .8em .6em;
    font-size: 1.6rem;
    color: #666;
    margin: 1em 0 0 !important;
    background: #fff;
    border-bottom: 1px solid #ddd;
}
</style>
</head>

<body ontouchmove="$Y.hover.TouchMove(event)">
<div id="body">
    <div id="payRecord">
    	<#if ruleConfig.clinicPrePayWarmTip?exists && ruleConfig.clinicPrePayWarmTip != "">
	    <div class="section box-tips icontips">
	    	<i class="iconfont">&#xe60d;</i>
	    	 温馨提示：${ruleConfig.clinicPrePayWarmTip}
	    </div>
	    </#if>
        <ul class="yx-list">
			<li class="flex">
                <div class="flexItem w120">就诊人</div>
                <div class="flexItem color666 textRight">${commonParams.encryptedPatientName}</div>
            </li>
            <li class="flex">
                <div class="flexItem w120">卡号</div>
                <div class="flexItem color666 textRight">${commonParams.cardNo}</div>
            </li>
            <li class="flex">
                <div class="flexItem w120">流水号</div>
                <div class="flexItem color666 textRight">${commonParams.omitMzFeeId}</div>
            </li class="flex">
            <li class="flex">
                <div class="flexItem w120">总金额</div>
                <div class="flexItem color666 textRight"><span class="yellow fontSize140">${(commonParams.totalFee / 100)?string('0.00')}</span> 元</div>
            </li>
        </ul>
    </div>
	
    <div id="pay-detail"></div>
    <div id="pay-medicare"></div>
    
    <#include "/easyhealth/common/commonTrade.ftl">
    <div id="pay-infos"></div>
</div>
<form id="voForm" method="post">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}" />
<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />
<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${commonParams.branchHospitalName}" />
<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
<input type="hidden" id="cardType" name="cardType" value="${commonParams.cardType}" />
<input type="hidden" id="cardNo" name="cardNo" value="${commonParams.cardNo}" />
<input type="hidden" id="patientName" name="patientName" value="${commonParams.patientName}" />
<input type="hidden" id="mzFeeId" name="mzFeeId" value="${commonParams.mzFeeId}" />
<input type="hidden" id="patientMobile" name="patientMobile" value="${commonParams.patientMobile}" />
<input type="hidden" id="payIds" name="payIds" value="" />
<input type="hidden" id="totalFee" name="totalFee" value="${commonParams.totalFee}" />
<input type="hidden" id="medicareFee" name="medicareFee" value="${commonParams.medicareFee}" />
<input type="hidden" id="payFee" name="payFee" value="${commonParams.payFee}" />
<input type="hidden" id="platformMode" name="platformMode"/>
<input type="hidden" id="tradeMode" name="tradeMode"/>
<input type="hidden" id="medicareType" name="medicareType" value="${commonParams.medicareType}" />
<input type="hidden" id="doctorName" name="doctorName" value="${commonParams.doctorName}" />
<input type="hidden" id="deptName" name="deptName" value="${commonParams.deptName}" />
<input type="hidden" id="visitWay" name="visitWay" value="${commonParams.visitWay}" />
<input type="hidden" id="supportMedicare" name="supportMedicare" value="${ruleClinic.isSupportHealthPayments}" />
<input type="hidden" id="isSupportForbiddenPayment" name="isSupportForbiddenPayment" value="${ruleClinic.isSupportForbiddenPayment}" />
<input type="hidden" id="showClinicPayDetailStyle" name="showClinicPayDetailStyle" value="${ruleClinic.showClinicPayDetailStyle}" />
<input type="hidden" id="isAccessClinic" name="isAccessClinic" value="${ruleFriedExpress.isAccessClinic}" />
<input type="hidden" id="isSplitRecipe" name="isSplitRecipe" value="${ruleFriedExpress.isSplitRecipe}" />
<input type="hidden" id="isShowTradeMode" name="isShowTradeMode" value="${commonParams.isShowTradeMode}" />
</form>

<#-- 如果需要走医保结算流程，返回错误或者没有医保等，都直接以自费进行结算 -->
<#-- 是否需要进行额外的医保结算流程 -->
<input type="hidden" id="needCalcMedicare" name="needCalcMedicare" value="0" />
<#-- 需要走医保计算流程，是否已经完成了医保结算 -->
<input type="hidden" id="hadCalcMedicare" name="hadCalcMedicare" value="0" />

<form id="payForm" method="post" action="">
    <input type="hidden" id="body" name="body" value=""/>
    <input type="hidden" id="code" name="code" value=""/> 
    <input type="hidden" id="infoUrl" name="infoUrl" value=""/>
    <input type="hidden" id="orderNo" name="orderNo" value=""/> 
    <input type="hidden" id="paySuccessPageUrl" name="paySuccessPageUrl" value=""/>
    <input type="hidden" id="viewType" name="viewType" value=""/>
    <input type="hidden" id="merchantUrl" name="merchantUrl" value=""/>
    <input type="hidden" id="timeout" name="timeout" value=""/> 
    <input type="hidden" id="totalFee" name="totalFee" value=""/>
    <input type="hidden" id="tradeMode" name="tradeMode" value=""/> 
    <input type="hidden" id="agtTimeout" name="agtTimeout" value=""/> 
    
    <input type="hidden" id="componentOauth2" name="componentOauth2" value=""/>
</form>

<#include "/easyhealth/common/footer.ftl">
<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/clinic/app.payDetail.js"></script>
<script type="text/javascript">
	
<#--	$(function()
	{
		if(IS.isMacOS){
	        try{
	        	setTimeout("appHideNavRefresh();",1000);
		        } catch (e) {}}
		    else if(IS.isAndroid){
		        try{window.yx129.appHideNavRefresh();
		        } catch (e) {}
		    }
	}); -->
	
	function doGoBack()
	{
		var freshFrom=$("<form></form>");
		freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
		freshFrom.append($('<input type="hidden"  name="appCode" value="${commonParams.appCode}" />'));
		freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
		freshFrom.append($('<input type="hidden"  name="areaCode" value="${commonParams.areaCode}" />'));
		freshFrom.append($('<input type="hidden"  name="cardType" value="${commonParams.cardType}" />'));
		freshFrom.append($('<input type="hidden"  name="cardNo" value="${commonParams.cardNo}" />'));
		freshFrom.append($('<input type="hidden"  name="visitWay" value="${commonParams.visitWay}" />'));
		freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
		freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
		freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
		freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
		freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
		freshFrom.append($('<input type="hidden"  name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
		freshFrom.appendTo("body");
		freshFrom.css('display','none');
		freshFrom.attr("method","post");
		var url = "${basePath}easyhealth/clinic/payIndex";
		if ($('#visitWay').val() == '1') {
			url = "${basePath}easyhealth/clinic/payIndexFromMsg";
		}
		freshFrom.attr("action", url);
		freshFrom.submit();
			
	}
</script>
</body>
</html>