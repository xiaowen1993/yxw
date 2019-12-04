<script type="text/javascript" src="${basePath}yxw.app/js/biz/register/app.commonCard.js?v=1.8"></script>
<div class="page-title">选择就诊人</div>
<div id="selectCard">
	<ul class="yx-list  ui-list-flex ui-list-radio" id="hasCard">
	
	</ul>
	<ul class="yx-list ui-list-flex" id="noCard">
	</ul>
	<ul class="yx-list ui-list-flex" id="noUserInfo">
	</ul>
</div>
<a href="javascript:;" class="addPeople" id="addFamily"><i class="iconfont"></i>添加就诊人 </a>
<form id="cardForm" method="post">
<input type="hidden" id="appFamilyLimit" name="appFamilyLimit" value="" />
<input type="hidden" id="curFamilyCount" name="curFamilyCount" value="" />

<input type="hidden" name="appId" value="${commonParams.appId}"/>
<input type="hidden" name="openId" value="${commonParams.openId}"/>
<input type="hidden" name="appCode" value="${commonParams.appCode}"/>
<input type="hidden" name="areaCode" value="${commonParams.areaCode}" />
<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}" />
<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>
<input type="hidden" name="familyId" value="${commonParams.familyId}"/>
<input type="hidden" name="forward" value="${commonParams.forward}"/>

<input type="hidden" name="familyNumbers" value="" />
<input type="hidden" name="syncType" value="" />

<input type="hidden" id="sessionIsFamilySelfInfo" name="sessionIsFamilySelfInfo" value="${sessionUser.cardNo}" />
</form>