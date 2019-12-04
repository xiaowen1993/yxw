var numberAttr = {
	init: function() {
		$.get('js/map/china.json', function(chinaJson) {
			echarts.registerMap('china', chinaJson);
			chart = echarts.init(document.getElementById('numberAttrChart'));
			chart.setOption({
				backgroundColor: '#404a59',
				geo: {
					map: 'china',
					roam: true, // 滚动，缩放
					label: {
						normal: {
							show: true
						},
						emphasis: {
							show: true
						}
					},
					itemStyle: {
						normal: {
							areaColor: '#323c48',
							borderColor: '#111'
						},
						emphasis: {
							areaColor: '#2a333d'
						}
					}
				},
				toolbox: {
					feature: {
						saveAsImage: {
							title: '保存',
							name: '手机号码归属地',
							pixelRatio: 2
						}
					}
				},
				tooltip: {
					trigger: 'item',
					formatter: function(data) {
						return data.name + ': ' + data.value[2];
					},
					padding: [5, 10, 5, 10]
				}
			});
		});

		numberAttr.getDatas();
	},
	getDatas: function() {
		$.get('/numberAttr/getDatas', function(data) {
			if (data.status == 'OK' && data.message) {
				numberAttr.formatData(data.message.result.items.attribution);
				numberAttr.bindDownloadEvent();
			}
		});
	},
	formatData: function(data) {
		var sHtml = '';
		var dataMap = [];

		$.each(data, function(i, item) {
			// 表格
			sHtml += '<tr>';
			sHtml += '	<td>' + item.province + '</td>';
			sHtml += '	<td>' + item.city + '</td>';
			sHtml += '	<td>' + item.cumulateCount + '</td>';
			sHtml += '</tr>';

			// if (i < 20) {
			// 地图
			var tempData = {};
			var tempValues = [];
			tempValues.push(item.longitude);
			tempValues.push(item.latitude);
			tempValues.push(item.cumulateCount);
			// tempData.name = item.areaName;
			tempData.name = item.city;
			tempData.value = tempValues;
			dataMap.push(tempData);
			//}
		});

		$('table>tbody').html('').append(sHtml);
		chart.setOption({
			series: [ {
				type: 'scatter',
				coordinateSystem: 'geo',
				data: dataMap,
				symbolSize: function(val) {
					var i = val[2];
					if (i > 100000) {
						i = 12;
					} else if (i > 50000) {
						i = 10;
					} else if (i > 10000) {
						i = 8;
					} else if (i > 5000) {
						i = 6;
					} else if (i > 500) {
						i = 4;
					} else {
						i = 2;
					}
					// return val[2] / 10000;
					return i;
				},
				label: {
					normal: {
						formatter: '{b}',
						position: 'right',
						show: false
					},
					emphasis: {
						show: true
					}
				},
				itemStyle: {
					normal: {
						color: '#2894F9'
					}
				}
			} ]
		});
	},
	bindDownloadEvent: function() {
		$('.btnDownload').off('click').on('click', function() {
			var title = $(this).siblings('span').text();
			title = title.substring(0, title.length - 1);
			var table = $(this).parent().siblings('div').find('table')[0];
			var hideObj = $('a.hideObj')[0];
			exports.exportTableAsExcel(table, title, hideObj);
		});
	}
}

var chart;

$(function() {
	numberAttr.init();
})