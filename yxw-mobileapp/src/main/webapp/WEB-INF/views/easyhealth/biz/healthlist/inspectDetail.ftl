<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>报告详情</title>
    <script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
</head>
<body>
<div id="body">
    <div class="box-list fff">
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem label w160">姓名</div>
                <div class="flexItem values color666 textRight">*${commonParams.patCardName?substring(1)}</div>
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
        <div class="box-tips"> <i class="iconfont"></i> 提示：该报告结果仅供临床参考。请以医院打印的纸质报告单为准。</div>
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
		<input type="hidden" id="reportType" name="reportType" value="${commonParams.reportType}" />
		<input type="hidden" id="patCardType" name="patCardType" value="${commonParams.patCardType}" />
		<input type="hidden" id="patCardNo" name="patCardNo" value="${commonParams.patCardNo}" />
		<input type="hidden" id="reportId" name="reportId" value="${commonParams.reportId}" />
		<input type="hidden" id="admissionNo" name="admissionNo" value="" />
		<input type="hidden" id="detailSource" name="detailSource" value="${commonParams.detailSource}" />
		<input type="hidden" id="patCardName" name="patCardName" value="${commonParams.patCardName}" />
		<input type="hidden" id="doctorName" name="doctorName" value="${commonParams.doctorName}" />
		<input type="hidden" id="reportTime" name="reportTime" value="${commonParams.reportTime}" />
		<input type="hidden" name="idNo" value="${sessionUser.cardNo}" />
	</form>
	<script type="text/javascript">
    	var basePath = '${basePath}';
    	$(function() {
    		$Y.loading.show('正在为您加载报告详情');
    		var url =  basePath + "easyhealth/healthlist/reports/findReportDetail";
    		var params = $("#voForm").serializeArray();
    		console.dir(params);
    		$.ajax({
	           	type: 'POST',  
	           	url: url,  
	           	data: params,  
	           	dataType: 'json',  
	           	timeout: 120000,
	           	error: function(data) {
	           		$Y.loading.hide();
	           		showNoData('查询超时，请稍后重试');
	           	},
	           	success : function(data) {
	           		console.log(data);
	           		$Y.loading.hide();
	           		if (data.status == 'OK' && data.message && data.message.list && data.message.list.length > 0) {
	           			formatData(data.message.list);
	           		} else {
	           			showNoData();
	           		}
	           	}
	    	})
    	})
    	
    	function showNoData() {
    		var sHtml = '';
    		$('#detailInfo').html('');

    		sHtml += '<div class="noRecord">';
            sHtml += '	<div id="success">';
            sHtml += '  	<div class="noticeEmpty"></div>';
            sHtml += '		<div class="p color666">没有查询到您的报告详情。</div>';
            sHtml += '	</div>';
            sHtml += '</div>';
    		
    		$('#detailInfo').append(sHtml);
    	}
    	
    	function formatData(data) {
			var sHtml = '';
			$('#detailInfo').html('');
			
			$.each(data, function(i, item) {
				var abnormal = item.abnormal;
				if (abnormal) {
					if (abnormal == '1' || abnormal == '2' || abnormal == '4') {
						sHtml += '<div class="acc-li highlight">';
					} else {
						sHtml += '<div class="acc-li">';
					}
				} else {
					sHtml += '<div class="acc-li">';
				}
				
				sHtml += '	<div class="acc-header js-acc-header">' + item.itemName + '</div>';
				sHtml += '	<ul class="acc-content">';
				sHtml += '		<li class="item">';
				sHtml += '			 <div class="label">结果</div>';
				sHtml += '			 <div class="values">' + ((item.result == 'null' || !item.result) ? '&nbsp;' : item.result) + '</div>';
				sHtml += '		</li>';
				sHtml += '		<li class="item">';
				sHtml += '			 <div class="label">参考</div>';
				sHtml += '			 <div class="values">' + ((item.refRange == 'null' || !item.refRange) ? '&nbsp;' : item.refRange) + '</div>';
				sHtml += '		</li>';

    			switch (abnormal) {
    				case '1':
    					sHtml += '	<li class="item">';
    					sHtml += '		<div class="label">评定</div>';
    					sHtml += '		<div class="values">↑</div>';
    					sHtml += '	</li>';
    					break;
    				 case '2':
    				 	sHtml += '	<li class="item">';
    					sHtml += '		<div class="label">评定</div>';
    					sHtml += '		<div class="values">↓</div>';
    					sHtml += '	</li>';
    					break;
    				case '4':
    				 	sHtml += '	<li class="item">';
    					sHtml += '		<div class="label">评定</div>';
    					sHtml += '		<div class="values">注意</div>';
    					sHtml += '	</li>';
    					break;
    				default:
    					// 不做操作 (0[正常]不显示。3[未知]不显示。中文，也不显示。)
    			}
    			
				sHtml += '	</ul>';
				sHtml += '</div>';
			});
			
			$('#detailInfo').append(sHtml);
    	}
    	
    	function doRefresh() {
			$('#voForm').attr("action","${basePath}easyhealth/healthlist/reports/ehReportDetail");
			$('#voForm').submit();
		}
		
		function doGoBack() {
			var detailSource = $('#detailSource').val();
			if (detailSource == '1') {
				window.location.href="${basePath}easyhealth/healthlist/reports/ehReportIndex?openId=${commonParams.openId}&appCode=${appCode}&areaCode=${areaCode}";
			}
			else
			{
				$('#voForm').attr("action", "${basePath}easyhealth/healthlist/reports/search");
				$('#voForm').submit();
			}
		}
	</script>
<#include "/easyhealth/common/footer.ftl">

</body>
</html>