<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>绑定就诊卡</title>
</head>
<style>
	.bindMask {
		position: fixed;
		left: 0px;
		top: 0px;
		right: 0px;
		bottom: 0px;
		background: #fff;
		opacity: 0;
	}
</style>
<body>
<div id="body">
	<div class="familyInfo">
        <div class="box-tips icontips"><i class="iconfont"></i>为了方便使用医院服务，请先完善资料。</div>
        <div class="space15"></div>
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem">姓名</div>
                <div class="flexItem textRight ">${entity.encryptedName}</div>
            </li>
            <#if entity.ownership != 3>
            <li class="flex">
                <div class="flexItem ">证件类型</div>
                <div id="yx-select-card-id ">
                    <span class="view">&nbsp;&nbsp;${entity.idTypeLabel}&nbsp;&nbsp;</span>
                </div>
            </li>
            <li class="flex">
                <div class="flexItem ">证件号码</div>
                <div class="flexItem textRight ">${entity.encryptedIdNo}</div>
            </li>
            </#if>
            <#if entity.ownership == 3>
            <li class="flex">
                <div class="flexItem ">出生日期</div>
                <div class="flexItem textRight ">${entity.birth}</div>
            </li>
            <li class="flex">
                <div class="flexItem ">性别</div>
                <div class="flexItem textRight ">${entity.sexLabel}</div>
            </li>
            </#if>
            <#if entity.ownership != 3>
            <li class="flex">
                <div class="flexItem ">手机号码</div>
                <div class="flexItem textRight">
                	<input type="text" class="yx-input" id="edtMobile" placeholder="请输入" value="${entity.encryptedMobile}">
                </div>
            </li>
            </#if>
            <li class="flex">
                <div class="flexItem ">地址</div>
                <div class="flexItem textRight ">${entity.address}</div>
            </li>
            
            <#if entity.ownership == 3>
            <li class="flex">
                <div class="flexItem ">监护人姓名</div>
                <div class="flexItem  textRight">${entity.encryptedGuardName}</div>
            </li>
        	<li class="flex">
                <div class="flexItem ">监护人证件类型</div>
                <div class="flexItem  textRight">${entity.guardIdTypeLabel}</div>
            </li>
            <li class="flex">
                <div class="flexItem ">监护人证件号码</div>
                <div class="flexItem  textRight">${entity.encryptedGuardIdNo}</div>
            </li>
            <li class="flex">
                <div class="flexItem ">监护人手机号码</div>
                <div class="flexItem  textRight">${entity.encryptedGuardMobile}</div>
            </li> 
            </#if>
            
            <li class="flex">
                <div class="flexItem">就诊卡类型</div>
                <div id="yx-select-card-id" class="patCardType" >
                    <span class="view"><i class="iconfont">&#xe600;</i></span>
                    <select name="" id="yx-select-card-id-val">
                        <#list rule.cardType?split(",") as item>
                        <option value="${item}">
                        	<#switch item>
                        		<#case 1>就诊卡<#break>
                        		<#case 2>社保卡<#break>
                        		<#case 3>医保卡<#break>
                        		<#case 4>健康卡<#break>
                        		<#case 5>身份证<#break>
                        		<#case 6>市民卡<#break>
                        		<#case 7>患者唯一标识<#break>
                        		<#case 8>临时诊疗卡<#break>
                        		<#default>
                        	</#switch>
                        </option>
                        </#list>>
                    </select>
                </div>
            </li>
            <li class="flex">
                <div class="">就诊卡号</div>
                <div class="flexItem color666 textRight">
                	<input type="text" class="yx-input" id="patCardNo" placeholder="请输入">
                </div>
            </li>
        </ul>
        <div class="btn-w">
            <div class="btn btn-ok btn-block">绑定</div>
        </div>
    </div>
</div>
<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="syncType" name="syncType" value="${commonParams.syncType}">
	<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}">
	<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}">
	<input type="hidden" id="branchCode" name="branchCode" value="${commonParams.branchHospitalCode}">
	<input type="hidden" id="branchId" name="branchId" value="${commonParams.branchHospitalId}">
	<input type="hidden" id="branchName" name="branchName" value="${commonParams.branchHospitalName}">
	<input type="hidden" id="familyId" name="familyId" value="${commonParams.familyId}">
	<input type="hidden" id="cardNo" name="cardNo" value="">
	<input type="hidden" id="cardType" name="cardType" value="">
	<#-- 考虑到一个用户在每个医院所留的电话号码不一定一样，所以此处的电话号码应该可以被修改 -->
	<input type="hidden" id="mobile" name="mobile" />
</form>

<input type="hidden" id="forward" name="forward" value="${commonParams.forward}">

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/usercenter/app.bindCard.js?v=1.0" type="text/javascript"></script>