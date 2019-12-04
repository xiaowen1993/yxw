<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script src="${basePath}yxw.app/js/biz/triage/eh.triage.js"></script>
    <title>智能分诊</title>
</head>
<body>
	<div id="body">
	    <div id="triage">
	        <div class="space15"></div>
	        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>温馨提示：症状自查结果仅供参考，请及时到正规医院就诊。祝您身体健康！</div>
	        <div class="space15"></div>
	        <div class="triage-view">
	        	<#if (diseases?size > 0)>
              		<#list diseases as disease>
              			<div class="view-row">
			                <div class="title">${disease.name}</div>
			                <div class="mate color999">
			                	<#if disease.dummary?length gt 20>
			                		${disease.dummary?substring(0,20)}
			                	<#else>
			                		${disease.dummary}
			                	</#if>
			                	
			                </div>
			                <div class="btn-box btn-w">
			                    <button type="button" class="btn btn-ok" onclick="diseaseDetail('${disease.id}','${symptoms}');">了解疾病</button>
			                    <button type="button" class="btn btn-ok" onclick="go('${basePath}mobileApp/register/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=2',true);">去挂号</button>
			                </div>
			            </div>
              		</#list>
              	<#else>
              		<div id="success">
			            <div class="noticeEmpty"></div>
			            <div class="p color666" >没有匹配到可能的疾病，<br/>建议您到医院就诊检查。<br/>祝您身体健康。<br/></div>
			        </div>
				</#if>
	        </div>
	    </div>
	</div>
	<form id="triageListForm" method="post">
		<input type="hidden" id="id" name="id" />
	</form>
</body>
<script>
	function doRefresh()
	{
		window.location.href="${basePath}/smartTriage/trigeList?symptoms=${symptoms}";
	}
	
	function doGoBack()
	{
		window.location.href="${basePath}/smartTriage/triageIndex";
	}
</script>
</html>
