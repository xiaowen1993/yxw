var ageGroup = {
	init: function() {
		ageGroup.loadInitData();
		ageGroup.bindEvent();
	},
	loadInitData: function() {
		$.get('/common/getInitData', function(data) {
			if (data.status == 'OK' && data.message) {
				ageGroup.formatToolBar(data.message);

				ageGroup.getDatas({
					areaCode: '-1',
					hospitalCode: '-1',
					dateCode: '4'
				});
			}
		});
	},
	formatToolBar: function(data) {
		areaData = data;
		$('#areaList').html('');

		$('#areaList').append('<li data-value="-1"><a href="javascript: void(0);">总区域</a></li>');
		// $('#areaList').append('<li data-value="0"><a href="javascript: void(0);">各区域</a></li>');
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

		ageGroup.bindConditionEvent();
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

		ageGroup.bindConditionEvent();
	},
	bindConditionEvent: function() {
		$('li>a').off('click').on('click', function() {
			var value = $(this).parent().attr('data-value');
			$(this).parent().parent().attr('data-value', value).parent().find('span.text').text($(this).text());

			if ($(this).parent().parent().attr('id') == 'areaList') {
				ageGroup.initHospitals($(this).text());
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

			ageGroup.getDatas();
		});
	},
	getDatas: function(options) {
		if (!options) {
			var options = {};
			options.areaCode = $('#areaList').attr('data-value'); // 0
			// 表示查询所有区域
			options.hospitalCode = $('#hospitalList').attr('data-value'); // -1表示不选择医院
			options.dateCode = $('#dateList').attr('data-value'); // 后台去做
		}

		if (options.hospitalCode != '-1') {
			options.title = $('#hospitalList>li[data-value="' + options.hospitalCode + '"]>a').text();
			options.title += $('#dateList>li[data-value="' + options.dateCode + '"]>a').text();
		} else {
			options.title = $('#areaList>li[data-value="' + options.areaCode + '"]>a').text() + '所有医院'
					+ $('#dateList>li[data-value="' + options.dateCode + '"]>a').text();
		}

		if ($('#startDate input').val() && $('#endDate input').val()) {
			options.title += $('#startDate input').val() + "--" + $('#endDate input').val() + '统计';
		} else {
			options.title += '统计';
		}

		var data = {
			areaCode: options.areaCode,
			hospitalCode: options.hospitalCode,
			dateCode: options.dateCode,
			startDate: $('.startDate').val(),
			endDate: $('.endDate').val()
		}

		var reg = /\//g;
		var tabId = options.areaCode.replace(reg, "") + "_" + options.hospitalCode + "_" + options.dateCode;

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

			$.post('/ageGroup/getDatas', data, function(data) {
				if (data.status == 'OK') {
					console.log(data);
					ageGroup.formatData(contentId, data.message);
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
	formatData: function(objId, data) {
		if ($('#' + objId).length != 0) {
			var resultType = Number(data.resultType);

			var types = [ 1, 2, 1, 2, 1, 2 ];
			var titles = [ '全平台年龄段概要图', '全平台年龄段概要表', '各平台年龄段概要图', '各平台年龄段概要表', '各月份年龄段统计图', '各月份年龄段统计表' ];
			var clazzs = [ 'cumulateAgeGroupCharts', 'tCumulateAgeGroup', 'platformAgeGroupCharts',
					'tPlatformAgeGroup', 'monthCharts', 'tMonth' ];
			$('#' + objId).html('').append(commonTabs.addContent(types, titles, clazzs));

			// 性别 饼图&&表
			ageGroup.addByageGroupScale(objId, data.ageGroupScale);

			// 平台性别 柱状图&&表
			ageGroup.addByPlatformAgeGroup(objId, data.platformAgeGroup);

			// 每月性别明细 柱状图&&表
			ageGroup.addByMonthAgeGroup(objId, data.monthAgeGroup);

			ageGroup.bindDownloadEvent();
		} else {
			console.log('没有tab页面.');
		}
	},
	addByageGroupScale: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.cumulateAgeGroupCharts')[0]);
		chart.setOption(option.pie);

		var sHead = '';
		var sBody = '';

		sHead += '<tr><th width="20%">年龄段</th><th>累计数量</th></tr>';

		var seriesDatas = [];
		var legendDatas = [];
		for (var i = 0; i < data.legend.length; i++) {
			sBody += '<tr>';
			sBody += '	<td>' + data.legend[i] + '</td>';
			sBody += '	<td>' + yxw.number_format(data.values[i]) + '</td>';
			sBody += '</tr>';

			legendDatas.push(data.legend[i] + "：" + yxw.number_format(data.values[i]) + "位");
			seriesDatas.push({
				'name': data.legend[i] + "：" + yxw.number_format(data.values[i]) + "位",
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
						name: '全平台年龄段概要图',
						title: '保存'
					}
				}
			},
			series: [ {
				name: '年龄段统计',
				data: seriesDatas
			} ]
		});

		$('#' + objId).find('.tCumulateAgeGroup>table>thead').html('').append(sHead);
		$('#' + objId).find('.tCumulateAgeGroup>table>tbody').html('').append(sBody);
	},
	addByPlatformAgeGroup: function(objId, data) {
		var chart = echarts.init($('#' + objId).find('.platformAgeGroupCharts')[0]);
		chart.setOption(option.bargraph);

		chart.setOption({
			legend: {
				data: data.legend,
				left : 150,
				right : 150
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '各平台年龄段统计',
						title: '保存'
					}
				}
			},
			xAxis: {
				data: data.platforms,
				name: '平台',
				type: 'category'
			},
			yAxis: {
				name: '年龄段'
			},
			series: [ {
				data: data.ageGroup0,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[0]
			}, {
				data: data.ageGroup1,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[1]
			}, {
				data: data.ageGroup2,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[2]
			}, {
				data: data.ageGroup3,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[3]
			}, {
				data: data.ageGroup4,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[4]
			}, {
				data: data.ageGroup5,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[5]
			}, {
				data: data.ageGroup6,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[6]
			} ]
		});

		// 下面是表格的问题
		var sHead = '';
		var sMonth = '';
		var sCumulateBody = '';

		sHead += '<tr>';
		sHead += '	<th style="width: 15%;">平台类型</th>';
		$.each(data.legend, function(i, item) {
			sHead += '	<th style="width: 10%;">' + item + '</th>';
		});
		sHead += '</tr>';

		$.each(data.platforms, function(rowIndex, item) {
			sCumulateBody += '<tr>';
			sCumulateBody += ' <td>' + item + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup0[rowIndex] + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup1[rowIndex] + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup2[rowIndex] + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup3[rowIndex] + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup4[rowIndex] + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup5[rowIndex] + '</td>';
			sCumulateBody += ' <td>' + data.ageGroup6[rowIndex] + '</td>';
			sCumulateBody += '</tr>';
		})

		$('#' + objId).find('.tPlatformAgeGroup>table>thead').html('').append(sHead);
		$('#' + objId).find('.tPlatformAgeGroup>table>tbody').html('').append(sCumulateBody);
	},
	addByMonthAgeGroup: function(objId, data) {
		/**
		 * 图
		 */
		var chart = echarts.init($('#' + objId).find('.monthCharts')[0]);
		chart.setOption(option.bargraph);

		chart.setOption({
			legend: {
				data: data.legend,
				orient : 'horizontal',
				left : 150,
				right : 150
			},
			toolbox: {
				feature: {
					saveAsImage: {
						name: '各月份年龄段统计',
						title: '保存'
					}
				}
			},
			xAxis: {
				data: data.xData,
				name: '月份'
			},
			yAxis: {
				name: '性别'
			},
			series: [ {
				data: data.monthAgeGroup0,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[0]
			}, {
				data: data.monthAgeGroup1,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[1]
			}, {
				data: data.monthAgeGroup2,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[2]
			}, {
				data: data.monthAgeGroup3,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[3]
			}, {
				data: data.monthAgeGroup4,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[4]
			}, {
				data: data.monthAgeGroup5,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[5]
			}, {
				data: data.monthAgeGroup6,
				type: 'bar',
				barMaxWidth: 60,
				stack: '年龄段',
				name: data.legend[6]
			} ]
		});

		/**
		 * 表
		 */
		// 表头
		var sHead = '';
		sHead += '<tr>';
		sHead += ' <th style="width: 15%;">月份</th>';
		$.each(data.legend, function(i, item) {
			sHead += '<th>' + item + '</th>';
		});
		sHead += '</tr>';
		
		var sCumulateBody = '';
		$.each(data.xData, function(i, item) {
			sCumulateBody += '<tr>';
			sCumulateBody += '<td><span style="font-weight: bold;">' + item + '</span></td>';
			sCumulateBody += '<td>' + data.monthAgeGroup0[i] + yxw.percentageEx(data.monthAgeGroup0[i], data.monthTotal[i]) + '</td>';
			sCumulateBody += '<td>' + data.monthAgeGroup1[i] + yxw.percentageEx(data.monthAgeGroup1[i], data.monthTotal[i]) +'</td>';
			sCumulateBody += '<td>' + data.monthAgeGroup2[i] + yxw.percentageEx(data.monthAgeGroup2[i], data.monthTotal[i]) +'</td>';
			sCumulateBody += '<td>' + data.monthAgeGroup3[i] + yxw.percentageEx(data.monthAgeGroup3[i], data.monthTotal[i]) +'</td>';
			sCumulateBody += '<td>' + data.monthAgeGroup4[i] + yxw.percentageEx(data.monthAgeGroup4[i], data.monthTotal[i]) +'</td>';
			sCumulateBody += '<td>' + data.monthAgeGroup5[i] + yxw.percentageEx(data.monthAgeGroup5[i], data.monthTotal[i]) +'</td>';
			sCumulateBody += '<td>' + data.monthAgeGroup6[i] + yxw.percentageEx(data.monthAgeGroup6[i], data.monthTotal[i]) +'</td>';
			sCumulateBody += '</tr>';
		});

		var sMonth = '';
		var monthSize = data.xData.length;

		$('#' + objId).find('.tMonth>table>thead').html('').append(sHead);
		$('#' + objId).find('.tMonth>table>tbody').html('').append(sCumulateBody);
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
	ageGroup.init();

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