<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>就诊记录</title>
</head>
<body>
<div id="body">
    <div id="guaHao">
    	<div class="screeningBox">
            <ul class="yx-list">
                <li class="flex">
                    <div class="flexItem">
                        <label>
                        	<span class="text">全部医院</span>
                            <select id="hospitalFilter" name="hospitalFilter" class="select-screen-box" onchange="filterRecord(this)">
                                <option value="0">全部医院</option>
                                <#list entityList as item>
                                	<option value="${item.hospitalCode}">${item.hospitalName}</option>
                                </#list>
                            </select>
                            <i class="iconfont">&#xe66d;</i>
                        </label>
                    </div>
                    <div class="flexItem">
                        <label>
                        	<span class="text">全部日期</span>
                            <select id="dateFilter" name="dateFilter" class="select-screen-box"  onchange="filterRecord(this)">
                                <option value="0">全部日期</option>
                                <option value="1">今天</option>
                                <option value="2">近3天</option>
                                <option value="3">近7天</option>
                                <option value="4">近30天</option>
                            </select>
                            <i class="iconfont">&#xe66d;</i>
                        </label>
                    </div>
                </li>
            </ul>
        </div>
        <div class="space15"></div>
        <div id="records" >
        	
        </div>
    </div>
</div>
<form id="voForm" method="post">
    <input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
    <input type="hidden" id="appCode" name="appCode" value="${appCode}" />
    <input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
    <input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
    <input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
    <input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
    <input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${commonParams.branchHospitalName}" />
    <input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
    <input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
    <input type="hidden" id="cardNo" name="cardNo"    />
    <input type="hidden" id="orderNo" name="orderNo"  />
    <input type="hidden" id="regStatus" name="regStatus"  />
</form>
<#include "/easyhealth/common/footer.ftl">
<script type="text/javascript" src="${basePath}yxw.app/js/biz/healthlist/app.regRecordList.js"></script>
</body>
</html>