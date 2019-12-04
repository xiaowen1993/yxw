var hospital = {
	init: function() {
		$.ajax({
			type: 'POST',
			url: '/hospital/getDatas',
			data: '',
			dataType: 'JSON',
			timeout: 10000,
			error: function(data) {
				console.log(data);
			},
			success: function(data) {
				if (data.status == 'OK' && data.message.result.size > 0) {
					hospital.formatData(data.message.result.items);
				}
			}
		});
	},
	formatData: function(data) {
		var reg = /\//g;
		var sHtml = '';

		var onlineCounts = 0;
		var signCounts = 0;

		console.log(data);

		$.each(data, function(areaIndex, area) {
			var sArea = '';
			var areaName = area.areaName.replace(reg, "");
			var hospitalCount = area.size;
			var onlineCount = area.onlineItems.length;

			onlineCounts += onlineCount;
			signCounts += area.signItems.length;

			// sArea += '<td rowspan=' + areaName + '>' + + '</td>';

			if (onlineCount > 0) {
				$.each(area.onlineItems, function(onlineIndex, online) {
					if (onlineIndex == 0) {
						sHtml += '<tr><td rowspan="' + hospitalCount + '">' + areaName + '</td>';
						sHtml += '<td>' + online.hospitalName + '</td>';
						sHtml += '<td rowspan="' + onlineCount + '">已上线</td>';
						sHtml += '<td rowspan="' + hospitalCount + '">' + onlineCount + '</td>';
						sHtml += '<td rowspan="' + hospitalCount + '">' + hospitalCount + '</td></tr>';
					} else {
						sHtml += '<tr><td>' + online.hospitalName + '</td></tr>';
					}
				});
			}

			if (area.signItems.length > 0) {
				$.each(area.signItems, function(signIndex, sign) {
					if (signIndex == 0 && onlineCount == 0) {
						sHtml += '<tr><td rowspan="' + hospitalCount + '">' + areaName + '</td>';
						sHtml += '<td>' + sign.hospitalName + '</td>';
						sHtml += '<td rowspan="' + area.signItems.length + '">已签约</td>';
						sHtml += '<td rowspan="' + hospitalCount + '">' + onlineCount + '</td>';
						sHtml += '<td rowspan="' + hospitalCount + '">' + hospitalCount + '</td></tr>';
					} else if (signIndex == 0) {
						sHtml += '<tr><td>' + sign.hospitalName + '</td>';
						sHtml += '<td rowspan="' + area.signItems.length + '">已签约</td></tr>';
					} else {
						sHtml += '<tr><td>' + sign.hospitalName + '</td></tr>';
					}
				});
			}
		});

		var chart = echarts.init($('.hospitalChart')[0]);
		chart.setOption(option.pie);
		var legendData = [ "上线数：" + onlineCounts + "家", "签约数：" + signCounts + "家" ];
		var seriesData = [ {
			'name': "上线数：" + onlineCounts + "家",
			'value': onlineCounts
		}, {
			'name': "签约数：" + signCounts + "家",
			'value': signCounts
		} ];

		chart.setOption({
			legend: {
				orient: 'vertical',
				left: 'left',
				data: legendData,
				formatter: '{name}'
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '医院上线情况'
					}
				}
			},
			series: [ {
				data: seriesData
			} ]
		});

		$('tbody').html('').append(sHtml);

		hospital.bindExportEvent($('table:first')[0], '医院上线情况', $('#exportExcel')[0]);
	},
	bindExportEvent: function(obj, fileName, hideObj) {
		// var fileName = $(obj).parent().siblings('div').find('span.title').text();
		// 去掉最后的图/表
		// fileName = fileName.substring(0, fileName.length - 1);
		
		$('.btnDownload').off('click').on('click', function() {
			exports.exportTableAsExcel(obj, fileName, hideObj);
		})
	}
}

$(function() {
	hospital.init();
});