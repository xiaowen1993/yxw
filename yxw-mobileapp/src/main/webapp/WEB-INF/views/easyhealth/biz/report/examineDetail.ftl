<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>检查报告详情</title>
</head>
<body>
<div id="body">
    <div class="box-list fff">
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem label w160">姓名</div>
                <div class="flexItem values color666 textRight">${commonParams.encryptedPatientName}</div>
            </li>
            <li class="flex">
                <div class="flexItem label w160">就诊卡</div>
                <div class="flexItem values color666 textRight">${commonParams.patCardNo}</div>
            </li>
            <li class="flex">
                <div class="flexItem label w160">报告医生</div>
                <div class="flexItem values color666 textRight"><#if vo.doctorName != 'null' >${commonParams.doctorName}<#else>---</#if></div>
            </li>
            <li class="flex">
                <div class="flexItem label w160">报告时间</div>
                <div class="flexItem values color666 textRight"><#if vo.reportTime != 'null' >${commonParams.reportTime}<#else>---</#if></div>
            </li>
        </ul>
    </div>
    <div id="detailInfo" class="box-list fff accordion js-accordion">
    </div>
    <div class="box-list">
        <div class="box-tips"> <i class="iconfont">&#xe60d;</i> 提示：该报告结果仅供临床参考。请以医院打印的纸质报告单为准。</div>
    </div>
</div>
	<form id="voForm" method="post">
		<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
		<input type="hidden" id="appCode" name="appCode" value="${appCode}" />
		<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}" />
		<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
		<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
		<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
		<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
		<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${commonParams.branchHospitalName}" />
		<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
		<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
		<input type="hidden" id="reportType" name="reportType" value="${commonParams.reportType}" />
		<input type="hidden" id="patCardType" name="patCardType" value="${commonParams.patCardType}" />
		<input type="hidden" id="patCardNo" name="patCardNo" value="${commonParams.patCardNo}" />
		<input type="hidden" id="detailId" name="detailId" value="${commonParams.detailId}" />
		<input type="hidden" id="admissionNo" name="admissionNo" value="${commonParams.admissionNo}" />
		<input type="hidden" id="checkType" name="checkType" value="${commonParams.checkType}" />
	</form>
</body>
</html>

<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.noRecord.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/report/app.examineDetail.js"></script>