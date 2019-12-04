<html>
<#include "/easyhealth/common/common.ftl">
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
    <title>确认挂号信息</title>
</head>
<body ontouchstart="">
<div id="body">
    <div id="guoHao">
    	<#if preRegisterWarmTip?exists && preRegisterWarmTip != "">
	    <div class="space15"></div>
        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>温馨提示：${preRegisterWarmTip}</div>
        </#if>
        <ul class="yx-list ui-list-flex">
            <li>
                <div class="label">
                <#if commonParams.isViewRegFee == 1>
                        <#if regFeeAlias?exists && regFeeAlias != ''>
                    		${regFeeAlias}
                    	<#else>
                    		挂号费
                    	</#if>|诊查费
                    <#else>
                        <#if regFeeAlias?exists && regFeeAlias != ''>
                    		${regFeeAlias}
                    	<#else>
                    		挂号费
                    	</#if>
                    </#if>        
                </div>
                <div class="master textRight">
                    <span class="price">${(commonParams.regFee + commonParams.treatFee) / 100}</span> 元
                </div>
            </li>
            <li>
                <div class="label">科室</div>
                <div class="master textRight">${commonParams.deptName}</div>
            </li>
            <li>
                <div class="label">医生</div>
                <div class="master textRight">${commonParams.doctorName}
                <#if !commonParams.doctorTitle ??>
                (${commonParams.doctorTitle})
                </#if>
                </div>
            </li>
            <li>
                <div class="label">就诊时间</div>
                <div class="master textRight">
                ${commonParams.selectRegDate?substring(0,10)}                  
                    <#if commonParams.doctorBeginTime?exists && commonParams.doctorEndTime?exists >
                        ${commonParams.doctorBeginTime}-${commonParams.doctorEndTime}
                    <#else>
                        <#if commonParams.timeFlag == '1'>
                                                                        上午
                        </#if>
                        <#if commonParams.timeFlag == '2'>
                                                                        下午
                        </#if>
                        <#if commonParams.timeFlag == '3'>
                                                                        晚上
                        </#if>
                        <#if commonParams.timeFlag == '4'>
                                                                        全天
                        </#if>
                    </#if>
                </div>
            </li>
        </ul>
        
	   <#include "/easyhealth/biz/register/commonCard.ftl">
		        
       <#if commonParams.isViewDiseaseDesc == 1> 
		<div class="page-title">病情描述</div>
		<div style="padding:10px; background-color:#fff">
			<textarea name="name" id="diseaseDesc_input" class="ui-textarea" placeholder="病情描述"></textarea>
		</div>
  
        </#if>
        
        <#if commonParams.onlinePaymentControl == 3>
        <div class="space15"></div>
        <div class="switch">
            <ul class="yx-list ui-list-flex">
                 <li>
                    <div>是否支付挂号费</div>
                    <div class="master"><label><input class="mui-switch" type="checkbox" id="isPayFlag"></label> </div>
                </li>

            </ul>
        </div>
        </#if>
        
    <#include "/easyhealth/common/commonTrade.ftl">
        
    <#if onlinePaymentControl == 3>
    	<div class="box-tips"> <i class="iconfont"></i> ${pausePayMentTip}</div>
    </#if>
    <div class="btn-w">
        <button class="btn btn-block btn-ok" id="confirmRegister">确定挂号</button>
    </div>
</div>
<form id="paramsForm" method="post">
<input type="hidden" name="token" id="token" value="${token}">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}"/>
<input type="hidden" id="showDays" name="showDays" value="${showDays}" />
<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}"/>
<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}" />
<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${commonParams.branchHospitalName}" />
<input type="hidden" id="deptCode" name="deptCode" value="${commonParams.deptCode}" />
<input type="hidden" id="doctorCode" name="doctorCode" value="${commonParams.doctorCode}" />
<input type="hidden" id="selectRegDate" name="selectRegDate" value="${commonParams.selectRegDate}" />
<input type="hidden" id="category" name="category" value="${commonParams.category}" />
<input type="hidden" id="deptName" name="deptName" value="${commonParams.deptName}" />
<input type="hidden" id="doctorName" name="doctorName" value="${commonParams.doctorName}" />
<input type="hidden" id="doctorTitle" name="doctorTitle" value="${commonParams.doctorTitle}" />
<input type="hidden" id="doctorBeginTime" name="doctorBeginTime" value="${commonParams.doctorBeginTime}" />
<input type="hidden" id="doctorEndTime" name="doctorEndTime" value="${commonParams.doctorEndTime}" />
<input type="hidden" id="regFee" name="regFee" value="${commonParams.regFee}" />
<input type="hidden" id="treatFee" name="treatFee" value="${commonParams.treatFee}" />
<input type="hidden" id="regType" name="regType" value="${commonParams.regType}"/>
<input type="hidden" id="workId" name="workId" value="${commonParams.workId}"/>
<input type="hidden" id="timeFlag" name="timeFlag" value="${commonParams.timeFlag}"/>
<input type="hidden" id="tradeMode" name="tradeMode"/>
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />

<input type="hidden" id="isViewDiseaseDesc" name="isViewDiseaseDesc" value="${commonParams.isViewDiseaseDesc}"/>
<input type="hidden" id="onlinePaymentControl" name="onlinePaymentControl" value="${commonParams.onlinePaymentControl}"/>

<input type="hidden" id="cardNo" name="cardNo" value=""/>
<input type="hidden" id="cardType" name="cardType" value=""/>
<input type="hidden" id="regPersonType" name="regPersonType" value=""/>
<input type="hidden" id="patientName" name="patientName" value=""/>
<input type="hidden" id="patientSex" name="patientSex" value=""/>
<input type="hidden" id="patientMobile" name="patientMobile" value=""/>
<input type="hidden" id="idType" name="idType" value=""/>
<input type="hidden" id="idNo" name="idNo" value=""/>

<input type="hidden" id="isPay" name="isPay" />
<input type="hidden" id="diseaseDesc" name="diseaseDesc" value="${commonParams.diseaseDesc}"/>
<input type="hidden" id="familyId" name="familyId" value="" />
<input type="hidden" id="syncType" name="syncType" value="" />
</form>

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
    
    <input type="hidden" id="frameHeight" name="frameHeight" value=""/>
    
    <input type="hidden" id="componentOauth2" name="componentOauth2" value=""/>
    <input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
    <input type="hidden" id="openid" name="openid" value="${commonParams.openId}" />
</form>

<#include "/easyhealth/common/footer.ftl">
<script type="text/javascript" src="${basePath}yxw.app/js/biz/register/app.confirmRegisterInfo.js?v=2.2"></script>
</body>
</html>