<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>${(family.ownership==1)?string("本人信息", "家人信息")}</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div class="familyInfo">
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem">关系</div>
                <div class="flexItem color666 textRight">${family.ownershipLabel}</div>
            </li>
            <li class="flex">
                <div class="flexItem">姓名</div>
                <div class="flexItem color666 textRight">${family.encryptedName}</div>
            </li>
            <#if family.ownership == 3>
            <li class="flex">
                <div class="flexItem">出生日期</div>
                <div class="flexItem color666 textRight">${family.birth}</div>
            </li>
            </#if>
            <li class="flex">
                <div class="flexItem">性别</div>
                <div class="flexItem color666 textRight">${family.sexLabel}</div>
            </li>
            <li class="flex">
                <div class="flexItem">地址</div>
                <div class="flexItem color666 textRight">${family.address}</div>
            </li>
        </ul>
        <div class="space15"></div>
        <#if family.ownership != 3>
        <ul class="yx-list">
        	<li class="flex">
                <div class="flexItem">证件类型</div>
                <div class="flexItem color666 textRight">${family.idTypeLabel}</div>
            </li>
            <li class="flex">
                <div class="flexItem">证件号码</div>
                <div class="flexItem color666 textRight">${family.encryptedIdNo}</div>
            </li>
            <li class="flex">
                <div class="flexItem">手机号码</div>
                <div class="flexItem color666 textRight">${family.encryptedMobile}</div>
            </li>
        </ul>
        <#else>
        <ul class="yx-list">
        	<li class="flex">
                <div class="flexItem">监护人姓名</div>
                <div class="flexItem color666 textRight">${family.encryptedGuardName}</div>
            </li>
        	<li class="flex">
                <div class="flexItem">监护人证件类型</div>
                <div class="flexItem color666 textRight">${family.guardIdTypeLabel}</div>
            </li>
            <li class="flex">
                <div class="flexItem">监护人证件号码</div>
                <div class="flexItem color666 textRight">${family.encryptedGuardIdNo}</div>
            </li>
            <li class="flex">
                <div class="flexItem">监护人手机号码</div>
                <div class="flexItem color666 textRight">${family.encryptedGuardMobile}</div>
            </li>
        </ul>
        </#if>

        <div class="add-jiuZhenRen">
            <div class="page-title">医院信息管理</div>
            <div class="radio-list">
                <ul class="yx-list" id="cardList">

                </ul>
                <ul class="yx-list">
                    <li class="addPeople" id="syncCard">
                        <div class="skinColor"><i class="iconfont"></i>去关联</div>
                    </li>
                    <#--<li class="addPeople" onclick="go(base.appPath+'easyhealth/userCenterIndex', true)">
                        <div class="skinColor"><i class="iconfont icon-wode1"></i>个人中心</div>
                    </li>-->
                </ul>
            </div>
            <div class="space15"></div>
            
            <#if commonParams.syncType != 1>
            <div class="btn-w">
                <div class="btn btn-ok btn-block" id="removeFamily">移除家人</div>
            </div>
            </#if>
        </div>
    </div>
</div>
<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="familyId" name="familyId" value="${family.id}">
	<input type="hidden" id="syncType" name="syncType" value="${commonParams.syncType}">
	<input type="hidden" id="medicalcardId" name="medicalcardId" value="">
	<input type="hidden" id="forward" name="forward" value="${commonParams.forward}">
</form>

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/usercenter/app.familyInfo.js" type="text/javascript"></script>