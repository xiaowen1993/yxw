var inspectDetail = {
	loadData: function() {
		$Y.loading.show('正在为您加载报告详情');
		var url = base.appPath + "app/report/detail/getInspectDetail";
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
				noRecord.addStyle($('#detailInfo'), '查询超时，请稍后重试。', 'yxw.app/images/greenSkin/doll/yx-doll3.png');
			},
			success: function(data) {
				console.log(data);
				$Y.loading.hide();
				if (data.status == 'OK') {
					inspectDetail.formatData(data.message);
				} else {
					noRecord.addStyle($('#detailInfo'), '查询超时，请稍后重试', 'yxw.app/images/greenSkin/doll/yx-doll3.png');
				}
			}
		})
	},
	formatData: function(data) {
		if (data.detail && data.detail.length > 0) {
			var reportStyle = $('#reportStyle').val();
			if (!reportStyle) {
				reportStyle = 0;
			}
			
			if (reportStyle == 0) {
				inspectDetail.formatStyle0(data.detail);
			} else if (reportStyle == 1) {
				inspectDetail.formatStyle1(data.detail);
			} else {
				// ...
			}
		} else {
			noRecord.addStyle($('#detailInfo'), '暂时查不到该报告的详细内容，请稍候再试。', 'yxw.app/images/greenSkin/doll/yx-doll3.png');
		}
	},
	formatStyle0: function(data) {
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
			
			var abnormal = item.abnormal;
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
	},
	formatStyle1: function(data) {
		var sHtml = '';
		$('#detailInfo').html('');
		sHtml += '<div class="section hide active">';
		sHtml += '	<div class="ui-table">';
		sHtml += '		<div class="ui-tabel-head">';
		sHtml += '			<div class="td">项目</div>';
		sHtml += '			<div class="td">结果</div>';
		sHtml += '			<div class="td">参考</div>';
		sHtml += '			<div class="td">评定</div>';
		sHtml += '		</div>';
       
		$.each(data, function(i, item) {
			var abnormal = item.abnormal;
		
			sHtml += '		<div class="ui-tabel-tr">';
			sHtml += '			<div class="td">' + item.itemName + '</div>';
			sHtml += '			<div class="td">' + ((item.result == 'null' || !item.result) ? '&nbsp;' : item.result) + '</div>';
			sHtml += '			<div class="td">' + ((item.refRange == 'null' || !item.refRange) ? '&nbsp;' : item.refRange) + '</div>';
			switch (abnormal) {
				case '1':
					sHtml += '		<div class="td">↑</div>';
					break;
				 case '2':
					sHtml += '		<div class="td">↓</div>';
					break;
				case '4':
					sHtml += '		<div class="td">注意</div>';
					break;
				default:
					sHtml += '		<div class="td"></div>';
			}
			
			sHtml += '		</div>';

		});
		sHtml += '	</div>';
		sHtml += '</div>';
		$('#detailInfo').append(sHtml);
		$('#detailInfo').removeClass();
	}
}

$(function() {
	var captchaEffectiveTime = $('#captchaEffectiveTime')
	if (captchaEffectiveTime && Number(captchaEffectiveTime) > 0) {
		// 不直接加载，先看是否有验证码
		captcha.init(base.appCode, base.openId, 'REPORT', $('#detailInfo'), inspectDetail.loadData);
		captcha.show();
	} else {
		inspectDetail.loadData();
	}
});

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