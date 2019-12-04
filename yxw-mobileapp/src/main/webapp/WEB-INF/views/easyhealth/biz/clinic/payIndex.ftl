<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>门诊缴费</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div id="">
		<#include "/easyhealth/common/commonBizQueryHead.ftl">
		<div id="preTips" class="section box-tips icontips" style="display: none;">
	        <i class="iconfont">&#xe60d;</i>
	       	
	    </div>
		<div class="section" id="reportContent">
			<div class="yxw-data">
		        <ul class="yx-list fourList" id="paymentList">
		               
		        </ul>
		    </div>
		</div>
        <div id="commonTips" style="display: none;">
	    </div>
    </div>
</div>
<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}">
	<input type="hidden" id="familyId" name="familyId" value="">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
	<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="cardType" name="cardType" value="${commonParams.cardType}" />
	<input type="hidden" id="cardNo" name="cardNo" value="${commonParams.cardNo}" />
	<input type="hidden" id="patientName" name="patientName" value="" />
	<input type="hidden" id="mzFeeId" name="mzFeeId" value="" />
	<input type="hidden" id="totalFee" name="totalFee" value="" />
	<input type="hidden" id="payFee" name="payFee" value="" />
	<input type="hidden" id="medicareFee" name="medicareFee" value="" />
	<input type="hidden" id="medicareType" name="medicareType" value="" />
	<input type="hidden" id="deptName" name="deptName" value="" />
	<input type="hidden" id="doctorName" name="doctorName" value="" />
	<input type="hidden" id="patientMobile" name="patientMobile" value="" />
	<input type="hidden" id="visitWay" name="visitWay" value="${commonParams.visitWay}" />
	<input type="hidden" id="forward" value="app/clinic/pay/index" />
	<input type="hidden" id="isScanCode" name="isScanCode" value="${isScanCode}" /> <!--扫码入口标示-->
</form>
	<input type="hidden" id="moduleName" name="moduleName" value="待缴费" />

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
<script src="${basePath}yxw.app/js/biz/common/stringUtils.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/biz/clinic/app.payIndex.js?v=1.31" type="text/javascript"></script>