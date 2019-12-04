<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>我的家人</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div class="familyInfo">
        <div class="box-tips icontips"><i class="iconfont"></i>温馨提示：最多关联${familyNumbers}位家人，请谨慎添加。</div>
        <div class="space15"></div>
        <div id="families">
	        <ul id="familyList" class="yx-list">
	           
	        </ul>
	        <div id="commonTips" style="display: none;">
	    </div>
	    </div>
        <div class="btn-w">
            <div class="btn btn-ok btn-block" id="btnAddFamily">添加</div>
        </div>
    </div>
    
</div>

<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="familyNumbers" name="familyNumbers" value="${familyNumbers}">
	<input type="hidden" id="familyId" name="familyId" value="">
	<input type="hidden" id="syncType" name="syncType" value="2">
</form>

<input type="hidden" id="maxCard" value="${familyNumbers}">
<input type="hidden" id="cardNum" value="${familyNumbers}">

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/usercenter/app.familyList.js" type="text/javascript"></script>