<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>检验报告详情</title>
<style>
.section.hide {
    display:none
}
.section.hide.active {
    display:block
}
.section.active {
    display:block
}
@-webkit-keyframes fadeInDown {
    0% {
        opacity:0;
        -webkit-transform:translate3d(0, -50%, 0);
        transform:translate3d(0, -50%, 0)
    }
    100% {
        opacity:1;
        -webkit-transform:none;
        transform:none
    }
}
@keyframes fadeInDown {
    0% {
        opacity:0;
        -webkit-transform:translate3d(0, -50%, 0);
        transform:translate3d(0, -50%, 0)
    }
    100% {
        opacity:1;
        -webkit-transform:none;
        transform:none
    }
}
.nodata-img {
    color:#888;
    text-align:center
}
.nodata-img img {
    display:block;
    margin:3em auto 1em;
    width:40%
}
.ui-space {
    height:15px
}
.tips {
    padding:10px 20px;
    background-color:#fefef4;
    color:#666
}
.icon-tips {
    width:1em;
    height:1em
}
.icon-tips:before {
    content:'';
    background-image:url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACMAAAAkCAYAAAF027N3AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyJpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuMy1jMDExIDY2LjE0NTY2MSwgMjAxMi8wMi8wNi0xNDo1NjoyNyAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENTNiAoV2luZG93cykiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OTcxNkQ2OTRERjdGMTFFNjhGNjdBN0Q2QTUxRUY1NzEiIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OTcxNkQ2OTVERjdGMTFFNjhGNjdBN0Q2QTUxRUY1NzEiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0ieG1wLmlpZDo5NzE2RDY5MkRGN0YxMUU2OEY2N0E3RDZBNTFFRjU3MSIgc3RSZWY6ZG9jdW1lbnRJRD0ieG1wLmRpZDo5NzE2RDY5M0RGN0YxMUU2OEY2N0E3RDZBNTFFRjU3MSIvPiA8L3JkZjpEZXNjcmlwdGlvbj4gPC9yZGY6UkRGPiA8L3g6eG1wbWV0YT4gPD94cGFja2V0IGVuZD0iciI/Pj4ClXkAAAPRSURBVHjaYvw/hQEGlID4HojBBBX4z5D9/y6YZmBgZILJIoF/jFDt/5FFYdq/IIkxAgQQI5JFcFX/wTj1A5hmgktdmQnRA9SCYTAjsqEAAYRs6H8GTKADxFdBunhQFGQDmSnv4bbBLN+PoX+FHoaTTTEUsQsg8xgBAgjDo8ghix5EofBAYGC4i8SGKxIH4lVwR2ejePI/TNELBgKACYX35iI2NdNRFV2egk3RW4w4wQIYUeNJ3hvd4f9gscsAVfgPzQRQWCmDGAABhC0wQeAQENvisH4hECdguAnJoHwgnoAiqxKK5LjfQPs3oOu3AOKTIAYLkh8YMex2X4Vg//oINEgAXcUJmPdAgTQRqyHo4PU5XDKgdOUJMigep+Zrc5DidCo+a5JABpXjlEZOEHfX4jOoFGTQTGjOwgTcMgi2sC4uQ/qA+AFyrIGy8GcG0gAjtgzwBSoBwgo4XLkPiPmQ1MEBQADhSpAwIALE0kB8B4i/EpcdIcATKfe/BuILUJeC+D+AWB1dAwsa/yPUybgAOxDfgGJNbIagZmsOoE9kHBH8O6uRZTWgruNBNuQ1hp2CGqhZ4g5GYucG4l1A7MYELa5FGMgDriCHMEEzGCb4/5dYg7YwQZ1FSkbEKAeYcEr9/Um0n5gYqADoaMi353ilQenkMM6CeyojMVbUgDIgyDXY4xO5nsFtILiCAhXgPWQGhzt6UYCvPsIGuoG4DD1g7YC4gEgD7GAGYIsdWLVSh6VZ9AuIU6Dyh5ElAAKMUMmGDkAFUjAQOwOxISivY1HzGJof9wIxqPx4R3TBTcAxoIpvOqjYoDBd/wHiyUBcS2pxDQIl0FR0lwoOgeWPQmjBCmqdWuNShAy8QEUWwWaEpA0DQ9Bh7HKgdspsAXy6QZJHgPglEBsgtzeZ0JpdW4lqz1AHgFrGoPImCt0x84E4jmFgwFIgjoA5JgBbQ5TsAvTbK3IctBiIeUGOySVZ60dguv7xBrvcq1PkJvBMkGOMyArcl6dIEycMDECO+UaW1lencTjmJLmO+cEEbXhSJ2T+A4um12fJdcweUAmsCC3cSMvSzBwMDFI2aGUMsEx7eYIch4Cbq6CEcx+Iw6D1CPFAwpKBwWEmpvhmYLn54SYpJn0HYnvkEngNVOAA0SHEwgVsoithCTE2UhzyHNr+/oReHRyCtvJ3ALETQWO+PGJguDoLSzIkupKuBOIOYmptTiBeAsRBVC5t/0Ad0UNKrf0d2m4BRZk8tN76Q6YDQEFVA8TAeGVgxdeeJLVxhVzJ6UMbW/zQ/haoafAGmjPOk1N+AQC+5vcEoj3tBQAAAABJRU5ErkJggg==");
    display:inline-block;
    width:1em;
    height:1em;
    background-size:100%;
    vertical-align:text-bottom;
    line-height:1;
    -webkit-transform:translateY(-1px);
    -ms-transform:translateY(-1px);
    transform:translateY(-1px)
}
.ui-table {
    width:100%;
    display:table;
    background-color:#fff;
    font-size:14px
}
.ui-table .ui-tabel-head {
    display:table-header-group
}
.ui-table .ui-tabel-head .td {
    position:relative;
    box-sizing:border-box;
    background-color:#F6F6F6;
    padding:10px
}
.ui-table .ui-tabel-head .td:after {
    content:'';
    border-bottom:solid 1px #ddd;
    display:block;
    position:absolute;
    bottom:0;
    left:0;
    width:100%;
    -webkit-transform:scaleY(0.5);
    -ms-transform:scaleY(0.5);
    transform:scaleY(0.5)
}
.ui-table .ui-tabel-tr {
    display:table-row-group
}
.ui-table .td {
    line-height:1.2;
    padding:5px 10px;
    display:table-cell;
    position:relative;
    box-sizing:border-box
}
.ui-table .td:after {
    content:'';
    border-bottom:solid 1px #ddd;
    display:block;
    position:absolute;
    bottom:0;
    left:0;
    width:100%;
    -webkit-transform:scaleY(0.5);
    -ms-transform:scaleY(0.5);
    transform:scaleY(0.5)
}
</style>
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
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}" />
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
	<input type="hidden" id="admissionNo" name="admissionNo" value="" />
	<input type="hidden" id="reportStyle" name="reportStyle" value="${ruleConfig.choicesInspectionReportStyle}" />
	<input type="hidden" id="captchaEffectiveTime" name="captchaEffectiveTime" value="${ruleConfig.captchaEffectiveTime}" />
</form>
<script type="text/javascript" src="${basePath}yxw.app/js/common/captcha.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.noRecord.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/report/app.inspectDetail.js"></script>

</body>
</html>