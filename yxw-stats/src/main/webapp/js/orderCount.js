var order = {
	init: function() {
		order.loadInitData();
		order.bindEvent();
	},
	loadInitData: function() {
		$.get('/common/getInitData', function(data) {
			if (data.status == 'OK' && data.message) {
				order.formatToolBar(data.message);

				order.getDatas({
					areaCode: '-1',
					hospitalCode: '-1',
					statsType: '1',
					dateCode: '4'
				});
			}
		});
	},
	formatToolBar: function(data) {
		areaData = data;
		$('#areaList').html('');

		$('#areaList').append('<li data-value="-1"><a href="javascript: void(0);">总区域</a></li>');
		/*
		 * $('#areaList').append('<li data-value="0"><a href="javascript:
		 * void(0);">各区域</a></li>');
		 */
		$.each(data, function(key, value) {
			if (value.length > 0) {
				$('#areaList').append(
						'<li data-value="' + value[0].areaCode + '"><a href="javascript: void(0);">' + key
								+ '</a></li>');
			}
		});

		if ($('#hospitalList>li').length == 0) {
			$('#hospitalList').append('<li data-value="-1"><a href="javascript: void(0);">请先选择区域</a></li>');
		}

		order.bindConditionEvent();
	},
	initHospitals: function(areaName) {
		$('#hospitalList').html('');
		$.each(areaData, function(key, value) {
			if (key == areaName) {
				$.each(value, function(i, item) {
					$('#hospitalList').append(
							'<li data-value="' + item.code + '"><a href="javascript: void(0);">' + item.name
									+ '</a></li>');
				})
			}
		});

		// set hospitalList to default value
		$('#hospitalList').attr('data-value', '-1').parent().find('span.text').text('选择医院');

		if ($('#hospitalList>li').length == 0) {
			$('#hospitalList').append('<li data-value="-1"><a href="javascript: void(0);">请先选择区域</a></li>');
		}

		order.bindConditionEvent();
	},
	bindConditionEvent: function() {
		$('li>a').off('click').on('click', function() {
			var value = $(this).parent().attr('data-value');
			$(this).parent().parent().attr('data-value', value).parent().find('span.text').text($(this).text());

			if ($(this).parent().parent().attr('id') == 'areaList') {
				order.initHospitals($(this).text());
			}

			if ($(this).parent().parent().attr('id') == 'dateList') {
				if (value == 5) {
					$('#startDate, #endDate').show();
				} else {
					$('#startDate, #endDate').hide();
					$('#startDate, #endDate').find('input').val('');
				}
			}

		});
	},
	bindEvent: function() {
		$('#stats').off('click').on('click', function() {
			$('#startDate input').tooltip("destroy");
			$('#endDate input').tooltip("destroy");
			if (!$('#startDate').is(":hidden") && !$('#endDate').is(":hidden")) {
				if ($('#startDate input').val() == '' || $('#endDate input').val() == '') {
					if ($('#startDate input').val() == '') {
						$('#startDate input').tooltip("show");
					}

					if ($('#endDate input').val() == '') {
						$('#endDate input').tooltip("show");
					}
					return;
				}
			}

			order.getDatas();
		});
	},
	getDatas: function(options) {
		if (!options) {
			var options = {};
			options.areaCode = $('#areaList').attr('data-value'); // 0
			options.hospitalCode = $('#hospitalList').attr('data-value'); // -1表示不选择医院
			options.dateCode = $('#dateList').attr('data-value'); // 后台去做
			options.statsType = $('#statsTypeList').attr('data-value'); // 后台去做
		}

		if (options.hospitalCode != '-1') {
			options.title = $('#hospitalList>li[data-value="' + options.hospitalCode + '"]>a').text();
			options.title += $('#dateList>li[data-value="' + options.dateCode + '"]>a').text();

		} else {
			options.title = $('#areaList>li[data-value="' + options.areaCode + '"]>a').text() + '所有医院'
					+ $('#dateList>li[data-value="' + options.dateCode + '"]>a').text();
		}

		if ($('#startDate input').val() && $('#endDate input').val()) {
			options.title += $('#startDate input').val() + "--" + $('#endDate input').val();
			options.title += $('#statsTypeList>li[data-value="' + options.statsType + '"]>a').text() + '统计';
		} else {
			options.title += $('#statsTypeList>li[data-value="' + options.statsType + '"]>a').text();
			options.title += '统计';
		}

		var data = {
			areaCode: options.areaCode,
			hospitalCode: options.hospitalCode,
			dateCode: options.dateCode,
			statsType: options.statsType,
			statsField: 'count',
			startDate: $('.startDate').val(),
			endDate: $('.endDate').val()
		}

		var reg = /\//g;
		var tabId = options.areaCode.replace(reg, "") + "_" + options.hospitalCode + "_" + options.statsType + "_"
				+ options.dateCode;

		if ($('#startDate input').val() && $('#endDate input').val()) {
			tabId += $('#startDate input').val() + "_" + $('#endDate input').val();
		}

		if ($('#tab_' + tabId).length == 0) {
			var option = {
				id: tabId,
				title: options.title,
				// defaultTitle : '统计',
				close: true,
				content: '',
			}
			var contentId = commonTabs.addTabs(option);

			$.post('/order/getDatas', data, function(data) {
				if (data.status == 'OK') {
					order.addTab(contentId, data.message);
				}
			}, 'json');

		} else {
			console.log('[' + tabId + ']已存在');
			// 切换到该页面
			$('#mainContent').find('.nav-tabs>li').removeClass('active');
			$('#mainContent').find('.tab-content>.tab-pane').removeClass('active');

			$('#tab_tab_' + tabId).find('a').trigger('click');
		}
	},
	addTab: function(objId, data) {
		console.log(data);
		// 初始化
		order.addInit(objId);
		// 订单 -- 折线
		order.addByOrder(objId, data.order);
		// 各类型订单 -- 折线
		order.addByOrderType(objId, data.orderType);
		// 各类型订单占比 -- 饼图
		order.addByOrderTypeScale(objId, data.orderTypeScale);
		// 各渠道订单 -- 折线
		order.addByOrderPlatform(objId, data.orderPlatform);
		// 各渠道订单占比 -- 饼图
		order.addByOrderPlatformScale(objId, data.orderPlatformScale);
		// 挂号类型订单 -- 折线
		order.addByRegType(objId, data.regType);
		// 挂号类型订单占比 -- 饼图
		order.addByRegTypeScale(objId, data.regTypeScale);
		// 支付订单类型 -- 折线
		order.addByPayment(objId, data.orderPayment);
		// 支付订单类型占比 -- 饼图
		order.addByPaymentScale(objId, data.orderPaymentScale);

		order.bindDownloadEvent();
	},
	addInit: function(objId) {
		var types = [ 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2 ];
		var titles = [ '订单数量概要图', '订单数量概要表', '各类型订单数量图', '各类型订单数量表', '各类型订单数量占比图', '各类型订单数量占比表', '各渠道订单数量图',
				'各渠道订单数量表', '各渠道订单数量占比图', '各渠道订单数量占比表', '挂号类型订单数量图', '挂号类型订单数量表', '挂号类型订单数量占比图', '挂号类型订单数量占比表',
				'支付类型订单数量图', '支付订单数量类型表', '支付类型订单数量占比图', '支付类型订单数量占比表' ];
		var clazzs = [ 'orderChart', 'orderTable', 'orderTypeChart', 'orderTypeTable', 'orderTypeScaleChart',
				'orderTypeScaleTable', 'orderPlatformChart', 'orderPlatformTable', 'orderPlatformScaleChart',
				'orderPlatformScaleTable', 'regTypeChart', 'regTypeTable', 'regTypeScaleChart', 'regTypeScaleTable',
				'paymentChart', 'paymentTable', 'paymentScaleChart', 'paymentScaleTable' ];
		var hints = [ '所有挂号、门诊、押金业务的订单数量总和（含已支付、未支付）', '所有挂号、门诊、押金业务的订单数量总和（含已支付、未支付）', '挂号、门诊、押金业务的订单数量（含已支付、未支付）',
				'挂号、门诊、押金业务的订单数量（含已支付、未支付）', '挂号、门诊、押金业务的订单数量占比（含已支付、未支付）', '挂号、门诊、押金业务的订单数量占比（含已支付、未支付）',
				'微信、支付宝平台所有订单数量（含已支付、未支付）', '微信、支付宝平台所有订单数量（含已支付、未支付）', '微信、支付宝平台所有订单数量占比（含已支付、未支付）',
				'微信、支付宝平台所有订单数量占比（含已支付、未支付）', '当班挂号、预约挂号的订单数量（含已支付、未支付）', '当班挂号、预约挂号的订单数量（含已支付、未支付）',
				'当班挂号、预约挂号的订单数量占比（含已支付、未支付）', '当班挂号、预约挂号的订单数量占比（含已支付、未支付）', '所有支付、未支付的订单数量', '所有支付、未支付的订单数量',
				'所有支付、未支付的订单数量占比', '所有支付、未支付的订单数量占比' ];
		$('#' + objId).html('').append(commonTabs.addContent(types, titles, clazzs, hints));
	},
	addByOrder: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.orderChart')[0]);
		chart.setOption(option.line);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>区域|医院</th>';
		$.each(data.xData, function(i, item) {
			sHead += '<th>' + item + '</th>';
		});
		sHead += '</tr>';

		var yData = [];
		$.each(data.yData, function(key, value) {
			sBody += '<tr><td>' + key + '</td>';
			$.each(value, function(itemKey, itemValue) {
				sBody += '<td>' + itemValue + '</td>';
			});

			sBody += '</tr>';

			yData.push({
				name: key,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: value
			});
		});

		chart.setOption({
			legend: {
				data: data.legend
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '订单数量概要',
						title: '保存'
					}
				}
			},
			xAxis: [ {
				data: data.xData
			} ],
			series: yData
		});

		$('#' + objId).find('.orderTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.orderTable>table>tbody').html('').append(sBody);
	},
	addByOrderType: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.orderTypeChart')[0]);
		chart.setOption(option.line);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>订单类型</th>';
		$.each(data.xData, function(i, item) {
			sHead += '<th>' + item + '</th>';
		});
		sHead += '</tr>';

		var yData = [];
		$.each(data.yData, function(key, value) {

			sBody += '<tr><td>' + key + '</td>';
			$.each(value, function(itemKey, itemValue) {
				sBody += '<td>' + itemValue + '</td>';
			});

			sBody += '</tr>';

			yData.push({
				name: key,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: value
			});
		});

		chart.setOption({
			legend: {
				data: data.legend
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '各类型订单数量',
						title: '保存'
					}
				}
			},
			xAxis: [ {
				data: data.xData
			} ],
			series: yData
		});

		$('#' + objId).find('.orderTypeTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.orderTypeTable>table>tbody').html('').append(sBody);
	},
	addByOrderTypeScale: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.orderTypeScaleChart')[0]);
		chart.setOption(option.pie);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>订单类型</th><th>订单数</th></tr>';

		var seriesDatas = [];
		var legendDatas = [];
		for (var i = 0; i < data.legend.length; i++) {
			sBody += '<tr>';
			sBody += '	<td>' + data.legend[i] + '</td>';
			sBody += '	<td>' + data.values[i] + '</td>';
			sBody += '</tr>';

			legendDatas.push(data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单");
			seriesDatas.push({
				'name': data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单",
				'value': data.values[i]
			});
		}

		chart.setOption({
			legend: {
				orient: 'vertical',
				left: 'left',
				data: legendDatas,
				formatter: '{name}'
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '各类型订单数量占比',
						title: '保存'
					}
				}
			},
			series: [ {
				data: seriesDatas
			} ]
		});

		$('#' + objId).find('.orderTypeScaleTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.orderTypeScaleTable>table>tbody').html('').append(sBody);

	},
	addByOrderPlatform: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.orderPlatformChart')[0]);
		chart.setOption(option.line);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>订单平台</th>';
		$.each(data.xData, function(i, item) {
			sHead += '<th>' + item + '</th>';
		});
		sHead += '</tr>';

		var yData = [];
		$.each(data.yData, function(key, value) {

			sBody += '<tr><td>' + key + '</td>';
			$.each(value, function(itemKey, itemValue) {
				sBody += '<td>' + itemValue + '</td>';
			});

			sBody += '</tr>';

			yData.push({
				name: key,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: value
			});
		});

		chart.setOption({
			legend: {
				data: data.legend
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '各平台订单数量',
						title: '保存'
					}
				}
			},
			xAxis: [ {
				data: data.xData
			} ],
			series: yData
		});

		$('#' + objId).find('.orderPlatformTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.orderPlatformTable>table>tbody').html('').append(sBody);

	},
	addByOrderPlatformScale: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.orderPlatformScaleChart')[0]);
		chart.setOption(option.pie);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>平台类别</th><th>订单数</th></tr>';

		var seriesDatas = [];
		var legendDatas = [];
		for (var i = 0; i < data.legend.length; i++) {
			sBody += '<tr>';
			sBody += '	<td rowspan="1">' + data.legend[i] + '</td>';
			sBody += '	<td rowspan="1">' + data.values[i] + '</td>';
			sBody += '</tr>';

			legendDatas.push(data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单");
			seriesDatas.push({
				'name': data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单",
				'value': data.values[i]
			});
		}

		chart.setOption({
			legend: {
				orient: 'vertical',
				left: 'left',
				data: legendDatas,
				formatter: '{name}'
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '各平台订单数量占比',
						title: '保存'
					}
				}
			},
			series: [ {
				data: seriesDatas
			} ]
		});

		$('#' + objId).find('.orderPlatformScaleTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.orderPlatformScaleTable>table>tbody').html('').append(sBody);
	},
	addByRegType: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.regTypeChart')[0]);
		chart.setOption(option.line);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th></th>';
		$.each(data.xData, function(i, item) {
			sHead += '<th>' + item + '</th>';
		});
		sHead += '</tr>';

		var yData = [];
		$.each(data.yData, function(key, value) {

			sBody += '<tr><td>' + key + '</td>';
			$.each(value, function(itemKey, itemValue) {
				sBody += '<td>' + itemValue + '</td>';
			});

			sBody += '</tr>';

			yData.push({
				name: key,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: value
			});
		});

		chart.setOption({
			legend: {
				data: data.legend
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '挂号订单数量',
						title: '保存'
					}
				}
			},
			xAxis: [ {
				data: data.xData
			} ],
			series: yData
		});

		$('#' + objId).find('.regTypeTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.regTypeTable>table>tbody').html('').append(sBody);
	},
	addByRegTypeScale: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.regTypeScaleChart')[0]);
		chart.setOption(option.pie);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>挂号类别</th><th>订单数</th></tr>';

		var seriesDatas = [];
		var legendDatas = [];
		for (var i = 0; i < data.legend.length; i++) {
			sBody += '<tr>';
			sBody += '	<td rowspan="1">' + data.legend[i] + '</td>';
			sBody += '	<td rowspan="1">' + data.values[i] + '</td>';
			sBody += '</tr>';

			legendDatas.push(data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单");
			seriesDatas.push({
				'name': data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单",
				'value': data.values[i]
			});
		}

		chart.setOption({
			legend: {
				orient: 'vertical',
				left: 'left',
				data: legendDatas,
				formatter: '{name}'
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '挂号订单数量占比',
						title: '保存'
					}
				}
			},
			series: [ {
				data: seriesDatas
			} ]
		});

		$('#' + objId).find('.regTypeScaleTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.regTypeScaleTable>table>tbody').html('').append(sBody);
	},
	addByPayment: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.paymentChart')[0]);
		chart.setOption(option.line);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th></th>';
		$.each(data.xData, function(i, item) {
			sHead += '<th>' + item + '</th>';
		});
		sHead += '</tr>';

		var yData = [];
		$.each(data.yData, function(key, value) {

			sBody += '<tr><td>' + key + '</td>';
			$.each(value, function(itemKey, itemValue) {
				sBody += '<td>' + itemValue + '</td>';
			});

			sBody += '</tr>';

			yData.push({
				name: key,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: value
			});
		});

		chart.setOption({
			legend: {
				data: data.legend
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '支付订单数量',
						title: '保存'
					}
				}
			},
			xAxis: [ {
				data: data.xData
			} ],
			series: yData
		});

		$('#' + objId).find('.paymentTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.paymentTable>table>tbody').html('').append(sBody);
	},
	addByPaymentScale: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.paymentScaleChart')[0]);
		chart.setOption(option.pie);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th>支付类别</th><th>订单数</th></tr>';

		var seriesDatas = [];
		var legendDatas = [];
		for (var i = 0; i < data.legend.length; i++) {
			sBody += '<tr>';
			sBody += '	<td rowspan="1">' + data.legend[i] + '</td>';
			sBody += '	<td rowspan="1">' + data.values[i] + '</td>';
			sBody += '</tr>';

			legendDatas.push(data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单");
			seriesDatas.push({
				'name': data.legend[i] + "：" + yxw.number_format(data.values[i]) + "笔订单",
				'value': data.values[i]
			});
		}

		chart.setOption({
			legend: {
				orient: 'vertical',
				left: 'left',
				data: legendDatas,
				formatter: '{name}'
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '支付订单占比概要',
						title: '保存'
					}
				}
			},
			series: [ {
				data: seriesDatas
			} ]
		});

		$('#' + objId).find('.paymentScaleTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.paymentScaleTable>table>tbody').html('').append(sBody);
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

var areaData = {};

$(function() {
	order.init();

	// 加一个看不见的A标签
	$('.main').append('<a class="hideObj" style="display: none;"></a>');

	$('#startDate').datetimepicker({
		language: 'zh-CN',
		autoclose: false,
		startView: 3,
		minView: 3,
		endDate: new Date(),
		format: 'yyyy-mm'
	}).on('changeDate', function(e) {
		var startTime = e.date.format("yyyy-MM-dd");
		$('#endDate').datetimepicker('setStartDate', startTime);
	});

	$('#endDate').datetimepicker({
		language: 'zh-CN',
		autoclose: false,
		startView: 3,
		minView: 3,
		endDate: new Date(),
		format: 'yyyy-mm'
	})/*
		 * .on('changeDate',function(e){ var endTime =
		 * e.date.format("yyyy-MM-dd");
		 * $('#startDate').datetimepicker('setEndDate',endTime); })
		 */;
});
