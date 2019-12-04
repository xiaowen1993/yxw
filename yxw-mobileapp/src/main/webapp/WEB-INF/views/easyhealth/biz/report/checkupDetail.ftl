<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>检查报告</title>
    <script type="text/javascript" src="${basePath}mobileApp/js/biz/inspection/nav-list.js"></script>
</head>
<body>
<div id="body">
	<div class="box-list">
    	<ul class="yx-list">
       		<li>
                <div class="label">姓名</div>
                <div class="values color666">*${vo.patCardName?substring(1)}</div>
            </li>
            <li>
                <div class="label">就诊卡</div>
                <div class="values color666">${vo.patCardNo}</div>
            </li>
            <li>
                <div class="label">报告医生</div>
                <div class="values color666"><#if vo.doctorName != 'null' >${vo.doctorName}<#else>---</#if></div>
            </li>
            <li>
                <div class="label">报告时间</div>
                <div class="values color666"><#if vo.reportTime != 'null' >${vo.reportTime}<#else>---</#if></div>
            </li>
        </ul>
    </div>
    
    <div id="examineDetailInfo" class="box-list accordion js-accordion">
        
    </div>
    
    <div class="section">
        <div class="box-tips"> <i class="icon-warn"></i> 提示：该报告结果仅供临床参考。请以医院打印的纸质报告单为准。</div>
    </div>
</div>
	<form id="voForm" method="post">
		<input type="hidden" id="openId" name="openId" value="${vo.openId}" />
		<input type="hidden" id="appCode" name="appCode" value="${vo.appCode}" />
		<input type="hidden" id="appId" name="appId" value="${vo.appId}" />
		<input type="hidden" id="hospitalCode" name="hospitalCode" value="${vo.hospitalCode}" />
		<input type="hidden" id="hospitalId" name="hospitalId" value="${vo.hospitalId}">
		<input type="hidden" id="hospitalName" name="hospitalName" value="${vo.hospitalName}" />
		<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${vo.branchHospitalName}" />
		<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${vo.branchHospitalId}" />
		<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${vo.branchHospitalCode}" />
		<input type="hidden" id="checkType" name="checkType" value="${vo.checkType}" />
		<input type="hidden" id="patCardType" name="patCardType" value="" />
		<input type="hidden" id="patCardNo" name="patCardNo" value="" />
		<input type="hidden" id="checkId" name="checkId" value="${vo.checkId}" />
		<input type="hidden" id="admissionNo" name="admissionNo" value="" />
	</form>
	<script type="text/javascript">
    	var basePath = '${basePath}';
    	(function($) {
    		var url =  basePath + "mobileApp/inspection/getDetailJson";
    		var datas = $("#voForm").serializeArray();
    		console.dir(datas);
    		jQuery.ajax({
	           	type : 'POST',  
	           	url : url,  
	           	data : datas,  
	           	dataType : 'json',  
	           	timeout:120000,
	           	success : function(data) {
	           		console.dir(data.details);
	           		var details = data.details;
	           		var html = "";
	           		if(details){
	           			html += '<div class="acc-li"><div class="acc-title"><div class="label">检查部位</div><div class="values">'
	           				+ (details.checkPart == null ? "---" : details.checkPart)
	           				+ '</div></div>'
            				+ '<div class="acc-header js-acc-header">检查所见</div><div class="acc-content"><div class="p">'
                    		+ (details.checkSituation == null ? "&nbsp;" : details.checkSituation)
                			+ '</div></div></div>'
        					+ '<div class="acc-li"><div class="acc-header js-acc-header">检查诊断</div><div class="acc-content"><div class="p">'
                    		+ (details.option == null ? "---" : details.option)
                			+ '</div></div></div>'
                			+ '<div class="acc-li"><div class="acc-header js-acc-header">检查方法</div><div class="acc-content"><div class="p">'
                    		+ (details.checkMethod == null ? "---" : details.checkMethod)
                			+ '</div></div></div>'
                			+ '<div class="acc-li"><div class="acc-header js-acc-header">医嘱项</div><div class="acc-content"><div class="p">'
                    		+ (details.advice == null ? "---" : details.advice)
                			+ '</div></div></div>';
	           		}else{
	           		
	           		}
	           		jQuery("#examineDetailInfo").html(html);
	           	}
	    	})
    	}(window.jQuery))
    	
    	
    	function doRefresh()
		{
			var freshFrom=$("<form></form>");
			freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
			freshFrom.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
			freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
			freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
			freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
			freshFrom.append($('<input type="hidden" name="checkType" value="${commonParams.checkType}" />'));
			freshFrom.append($('<input type="hidden" name="reportType" value="${commonParams.reportType}" />'));
			freshFrom.append($('<input type="hidden" name="patCardType" value="${commonParams.patCardType}" />'));
			freshFrom.append($('<input type="hidden" name="patCardNo" value="${commonParams.patCardNo}" />'));
			freshFrom.append($('<input type="hidden" name="detailId" value="${commonParams.detailId}" />'));
			freshFrom.append($('<input type="hidden"  name="patCardName" value="${commonParams.patCardName}" />'));
			freshFrom.append($('<input type="hidden" name="doctorName" value="${commonParams.doctorName}" />'));
			freshFrom.append($('<input type="hidden" name="reportTime" value="${commonParams.reportTime}" />'));
			freshFrom.appendTo("body");
			freshFrom.css('display','none');
			freshFrom.attr("method","post");
			freshFrom.attr("action","${basePath}mobileApp/reports/reportDetail");
			freshFrom.submit();
		}
		
		function doGoBack()
		{
			var freshFrom=$("<form></form>");
			freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
			freshFrom.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
			freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
			freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
			freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="reportType" value="${commonParams.checkType}" />'));
			freshFrom.appendTo("body");
			freshFrom.css('display','none');
			freshFrom.attr("method","post");
			freshFrom.attr("action","${basePath}mobileApp/reports/index");
			freshFrom.submit();
		}
	</script>
<#include "/easyhealth/common/footer.ftl">

</body>
</html>