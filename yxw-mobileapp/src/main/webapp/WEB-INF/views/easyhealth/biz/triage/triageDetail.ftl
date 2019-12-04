<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js?v=2"></script>
    <title>智能分诊</title>
</head>
<body>
	<div id="body">
	    <div id="triage">
	    	<div class="space15"></div>
	        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>温馨提示：症状自查结果仅供参考，请及时到正规医院就诊。祝您身体健康！</div>
	        <div class="space15"></div>
	       	<ul class="yx-list">
	           	<li class="flex">
	               	<div class="flexItem w100">疾病</div>
	               	<div class="flexItem color666 textRight">${disease.name}</div>
	           	</li>
	       	</ul>
	        <div class="box-list fff accordion js-accordion">
	            <div class="acc-li">
	                <div class="acc-header js-acc-header show">概述</div>
	                <ul class="acc-content show">
	                    <li class="item color666">
	                        ${disease.dummary}
	                    </li>
	                </ul>
	            </div>
	        </div>
	    </div>
	</div>
</body>
<script>
	function doRefresh()
	{
		var freshForm=$("<form></form>");
		freshForm.append($('<input type="hidden" name="id" value="${disease.id}"/>'));
		freshForm.append($('<input type="hidden" name="symptoms" value="${symptoms}"/>'));
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}/smartTriage/triageDetail");
		freshForm.submit();
	}
	
	function doGoBack()
	{
		var freshForm=$("<form></form>");
		freshForm.append($('<input type="hidden" name="symptoms" value="${symptoms}"/>'));
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}smartTriage/trigeList");
		freshForm.submit();
	}
</script>
</html>
