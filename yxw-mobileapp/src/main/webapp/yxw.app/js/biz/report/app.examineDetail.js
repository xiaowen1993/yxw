var examineDetail = {
	loadData: function() {
		$Y.loading.show('正在为您加载报告详情');
		var url =  base.appPath + "app/report/detail/getExamineDetail";
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
           		noRecord.addStyle($('#detailInfo'), '查询超时，请稍后重试', 'yxw.app/images/greenSkin/doll/yx-doll3.png');
           	},
           	success : function(data) {
           		console.log(data);
           		$Y.loading.hide();
           		if (data.status == 'OK') {
           			examineDetail.formatData(data.message);
           		} else {
           			noRecord.addStyle($('#detailInfo'), '查询超时，请稍后重试', 'yxw.app/images/greenSkin/doll/yx-doll3.png');
           		}
           	}
    	})
	},
	formatData: function(data) {
		var detail = data.detail;
		if (detail) {
			var sHtml = '';
			$('#detailInfo').html('');
			
			sHtml += '<div class="acc-li">';
			sHtml += '	<div class="acc-title">';
            sHtml += '		<div class="label">检查部位</div>';
            sHtml += '		<div class="values">' + ((!detail.checkPart || detail.checkPart == 'null') ? '&nbsp;' : detail.checkPart) + '</div>';
        	sHtml += '	</div>';
        	sHtml += '	<div class="acc-header js-acc-header">检查所见</div>';
        	sHtml += '	<div class="acc-content">';
            sHtml += '		<div class="p">' + ((!detail.checkSituation || detail.checkSituation == 'null') ? '&nbsp;' : detail.checkSituation) + '</div>';
        	sHtml += '	</div>';
        	sHtml += '</div>';
        	sHtml += '<div class="acc-li">';
        	sHtml += '	<div class="acc-header js-acc-header">检查诊断</div>';
        	sHtml += '	<div class="acc-content">';
            sHtml += '		<div class="p">' + ((!detail.option || detail.option == 'null') ? '&nbsp;' : detail.option) + '</div>';
        	sHtml += '	</div>';
			sHtml += '</div>';
			
			$('#detailInfo').append(sHtml);
		} else {
			noRecord.addStyle($('#detailInfo'), '暂时查不到该报告的详细内容，请稍候再试。', 'yxw.app/images/greenSkin/doll/yx-doll3.png');
		}
	}
}

$(function() {
	examineDetail.loadData();
});

function doRefresh()
{
	var form=$("<form></form>");
	form.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
	form.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
	form.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
	form.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
	form.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
	form.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
	form.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
	form.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
	form.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
	form.append($('<input type="hidden" name="checkType" value="${commonParams.checkType}" />'));
	form.append($('<input type="hidden" name="reportType" value="${commonParams.reportType}" />'));
	form.append($('<input type="hidden" name="patCardType" value="${commonParams.patCardType}" />'));
	form.append($('<input type="hidden" name="patCardNo" value="${commonParams.patCardNo}" />'));
	form.append($('<input type="hidden" name="detailId" value="${commonParams.detailId}" />'));
	form.append($('<input type="hidden"  name="patCardName" value="${commonParams.patCardName}" />'));
	form.append($('<input type="hidden" name="doctorName" value="${commonParams.doctorName}" />'));
	form.append($('<input type="hidden" name="reportTime" value="${commonParams.reportTime}" />'));
	form.appendTo("body");
	form.css('display','none');
	form.attr("method","post");
	form.attr("action","${basePath}app/report/detail/index");
	form.submit();
}