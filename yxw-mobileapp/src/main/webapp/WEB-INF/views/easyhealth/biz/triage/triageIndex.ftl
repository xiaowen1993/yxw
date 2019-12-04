<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script src="${basePath}yxw.app/js/biz/triage/eh.triage.js"></script>
    <title>智能分诊</title>
</head>
<body>
	<div id="body">
	    <div id="triage">
	        <div class="triageList">
	        	<#--
	            <div class="search-nav">
	                <ul class="search-nav-ul">
	                    <li class="icon-hospital" onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=hospital',1);">搜医院</li>
	                    <li class="icon-kesi" onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=dept',1);">搜科室</li>
	                    <li class="icon-doctor" onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=doctor',1);">搜医生</li>
	                    <li class="icon-zhengzhuang" onclick="go('${basePath}easyhealth/building',1);">搜症状</li>
	                </ul>
	            </div>-->
	            <div class="space15"></div>
	            <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>温馨提示：症状自查结果仅供参考，请及时到正规医院就诊。祝您身体健康！</div>
	            <div class="page-title">您是否有以下不适症状</div>
	            <div class="check-list">
		            <ul id="symptomsUl" class="yx-list">
		            	<#if symptoms?exists>
		              		<#list symptoms as symptom>
		              			<li>
				                    <label class="check-label flex">
				                        <div class="flexItem ">${symptom.name}</div>
				                        <div class="color666 flexItem textRight w120"><input type="checkbox" class="checkBoxMask" value="${symptom.id}"/></div>
				                    </label>
				                </li>
			                </#list>
						</#if>
		            </ul>
		      	</div>
	        </div>
	        <div class="btn-w fixed">
				<div class="bg-waitMask"></div>
				<button type="button" class="btn btn-ok btn-block" onclick="goTrigeList();">确定</button>
			</div>
	    	</div>
		</div>
	<form id="triageIndexForm" method="post">
		<input type="hidden" id="symptoms" name="symptoms" />
	</form>
</body>
<script>
	function doRefresh()
	{
		window.location.href="${basePath}/smartTriage/triageIndex";
	}
	
	function doGoBack()
	{
		windowClose();
	}
</script>
</html>
