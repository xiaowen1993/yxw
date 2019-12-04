var reportIndex = {
	initData: function() {
		reportIndex.bindEvent();
	},
	bindEvent: function() {
		$('div.reports').off('click').on('click', function() {
			reportIndex.showReport(this);
		});
	},
	bindEventAfterComplete: function() {
		$('div.yxw-data>ul.yx-list>li').off('click').on('click', function() {
			reportIndex.showDetail($(this));
		});
	},
	showReport: function(obj) {
		var hospitalCode = $('#hospitalFilter').val();
		if (hospitalCode && hospitalCode != '0') {
			$('#reportType').val($(obj).attr('reportType'));
			bizQuery.selectScreen(obj);
		}
	},
	showDetail: function(obj) {
		// 展示信息
		$("#doctorName").val($(obj).attr('data-doctor'));
		$("#reportTime").val($(obj).attr('data-reportTime'));
		$("#detailId").val($(obj).attr('data-id'));
		$("#deptName").val($(obj).attr('data-dept'));
		$("#executeTime").val($(obj).attr('data-executeTime'));
		$("#checkType").val($(obj).attr('data-checkType'));
		$("#fileAddress").val($(obj).attr('data-fileAddress'));

		$("#voForm").attr("action", base.appPath + 'app/report/detail/index');
		$("#voForm").submit();
	},
	formatInspectData: function(data) {
		$('#reportList').html('')
		var sHtml = '';

		$.each(data, function(i, item) {
			sHtml += '<li class="arrow"  data-doctor="'
					+ stringUtils.getValue(item.doctorName) + '"' + ' data-reportTime="'
					+ stringUtils.getValue(item.reportTime) + '"' + ' data-id="' + stringUtils.getValue(item.inspectId)
					+ '"' + ' data-dept="' + stringUtils.getValue(item.deptName) + '"' + ' data-fileAddress="'
					+ stringUtils.getValue(item.fileAddress) + '"' + ' data-executeTime="'
					+ stringUtils.getValue(item.provingTime) + '">';
			sHtml += '	<div class="title">' + stringUtils.getValue(item.inspectName) + '</div>';
			sHtml += '	<div class="mate color999">' + item.reportTime.substring(0, 10) + '</div>';
			sHtml += '	<div class="mate color999">' + $('#hospitalName').val() + '</div>';
			sHtml += '</li>';
		});

		$('#reportList').append(sHtml);
		$('#reportList').show();
		$('.yxw-data').show();
		reportIndex.bindEventAfterComplete();
	},
	formatExamineData: function(data) {
		var sHtml = '';
		$('#reportList').html('')
		$.each(data, function(i, item) {
			sHtml += '<li class="arrow"  data-doctor="'
					+ stringUtils.getValue(item.doctorName) + '"' + ' data-reportTime="'
					+ stringUtils.getValue(item.reportTime) + '"' + ' data-id="' + stringUtils.getValue(item.checkId)
					+ '"' + ' data-dept="' + stringUtils.getValue(item.deptName) + '"' + ' data-executeTime="'
					+ stringUtils.getValue(item.checkTime) + '"' + ' data-fileAddress="'
					+ stringUtils.getValue(item.fileAddress) + '"' + ' data-checkType="'
					+ stringUtils.getValue(item.checkType) + '">';
			sHtml += '	<div class="title">' + stringUtils.getValue(item.checkName) + '</div>';
			sHtml += '	<div class="mate color999">' + item.reportTime.substring(0, 10) + '</div>';
			sHtml += '	<div class="mate color999">' + $('#hospitalName').val() + '</div>';
			sHtml += '</li>';
		});

		$('#reportList').append(sHtml);
		$('#reportList').show();
		$('.yxw-data').show();
		reportIndex.bindEventAfterComplete();
	},
	formatPhysicalExamineData: function(data) {
		// 体检，暂时没有
	}
}

$(function() {
	reportIndex.initData();
});

//刷新方法
function doRefresh() {
	$('#voForm').attr('action', base.appPath + 'app/report/index');
	$('#voForm').submit();
}

function loadData() {
	$('.yxw-data').hide();
	$('#reportType').val($('div.reports.active').attr('reportType'));
	var datas = $('#voForm').serializeArray();
	$Y.loading.show('正在加载数据...');
	$.ajax({
		type: "POST",
		url: base.appPath + "app/report/getDatas",
		data: datas,
		cache: false,
		dataType: "json",
		timeout: 600000,
		error: function(data) {
			$Y.loading.hide();
			console.log('加载报告数据异常');
			bizQuery.showNoRecord();
		},
		success: function(data) {
			$Y.loading.hide();
			console.log(data);
			if (data.status == 'OK' && data.message && data.message.entityList && data.message.entityList.length > 0) {
				var reportType = $('#reportType').val();
				if (reportType == '1') {
					reportIndex.formatInspectData(data.message.entityList);
				} else if (reportType == '2') {
					reportIndex.formatExamineData(data.message.entityList);
				} else {
					// 暂时不管
					// reportIndex.formatPhysicalExamineData(data.message.entityList);
				}
			} else {
				bizQuery.showNoRecord();
			}
		}
	})
}
