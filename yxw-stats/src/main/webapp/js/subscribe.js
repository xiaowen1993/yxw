var subscribe = {
	init: function() {
		subscribe.loadInitData();
		subscribe.bindEvent();
	},
	loadInitData: function() {
		$.get('/common/getInitData', function(data) {
			if (data.status == 'OK' && data.message) {
				subscribe.formatToolBar(data.message);

				subscribe.getDatas({
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
		$('#areaList').append('<li data-value="0"><a href="javascript: void(0);">各区域</a></li>');
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

		subscribe.bindConditionEvent();
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

		subscribe.bindConditionEvent();
	},
	bindConditionEvent: function() {
		$('li>a').off('click').on('click', function() {
			var value = $(this).parent().attr('data-value');
			$(this).parent().parent().attr('data-value', value).parent().find('span.text').text($(this).text());

			if ($(this).parent().parent().attr('id') == 'areaList') {
				subscribe.initHospitals($(this).text());
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

			subscribe.getDatas();
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

			$.post('/subscribe/getDatas', data, function(data) {
				if (data.status == 'OK') {
					subscribe.formatData(contentId, data.message);
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

			switch (resultType) {
			case 1:
				var types = [ 1, 1, 1, 2, 1, 2 ];
				var titles = [ '累计关注数概要图', '各月份关注数概要图', '累计关注数图', '累计关注数表', '各月份关注数图', '各月份关注数表' ];
				var clazzs = [ 'tCumulateCharts', 'tMonthCharts', 'cumulateCharts', 'cumulateTable', 'monthCharts',
						'monthTable' ];
				$('#' + objId).html('').append(commonTabs.addContent(types, titles, clazzs));
				subscribe.formatJustAreaData(objId, data);
				break;
			case 2:
				var types = [ 1, 2, 1, 2 ];
				var titles = [ '累计关注数图', '累计关注数表', '各月份关注数图', '各月份关注数表' ];
				var clazzs = [ 'cumulateCharts', 'cumulateTable', 'monthCharts', 'monthTable' ];
				$('#' + objId).html('').append(commonTabs.addContent(types, titles, clazzs));
				subscribe.formatAllAreaAndHospitalsData(objId, data);
				break;
			case 3:
				var types = [ 1, 2, 1, 2 ];
				var titles = [ '累计关注数图', '累计关注数表', '各月份关注数图', '各月份关注数表' ];
				var clazzs = [ 'cumulateCharts', 'cumulateTable', 'monthCharts', 'monthTable' ];
				$('#' + objId).html('').append(commonTabs.addContent(types, titles, clazzs));
				subscribe.formatOneAreaAndHospitalsData(objId, data);
				break;
			case 4:
				var types = [ 1, 2, 1, 2 ];
				var titles = [ '累计关注数图', '累计关注数表', '各月份关注数图', '各月份关注数表' ];
				var clazzs = [ 'cumulateCharts', 'cumulateTable', 'monthCharts', 'monthTable' ];
				$('#' + objId).html('').append(commonTabs.addContent(types, titles, clazzs));
				subscribe.formatOneHospitalData(objId, data);
				break;
			}

			subscribe.bindDownloadEvent();
		} else {
			console.log('没有tab页面.');
		}
	},
	formatJustAreaData: function(objId, data) {
		console.log(data);
		/**
		 * -------------------------------- 图形如下
		 * --------------------------------------- *
		 */
		// 累计关注数概要图
		var xData = data.monthList;
		var tLegenData = [ '总区域' ];
		var tCumulateYData = [];

		// 各月份关注数概要图
		var tMonthYData = [];

		// 累计关注数图
		var legendData = data.areaList;
		var cumulateYData = []; // series

		// 各月份关注数图
		var monthYData = []; // series

		/**
		 * -------------------------------- 表格如下
		 * --------------------------------------- *
		 */
		var sHead = '';
		var sMonth = '';
		var sCumulateBody = '';
		var sCumulateSubHead = '<tr><td></td>';
		var monthSize = 0;

		var sMonthHead = '';
		var sMonthBody = '';

		sHead += '<tr>';
		sHead += '	<th style="width: 15%;">地区</th>';

		sMonthHead = sHead;

		$.each(data.datas, function(areaKey, areaValue) {
			var cumulateData = [];
			var monthData = [];
			// 下面的几个都是对应每个区域的每个月的值
			monthSize = Object.keys(areaValue).length;
			sMonth = '';

			sCumulateBody += '<tr><td>' + areaKey + '</td>';
			sMonthBody += '<tr><td>' + areaKey + '</td>';

			$.each(areaValue, function(month, value) {
				sMonth += '<th colspan="2">' + month + '</th>';
				sCumulateBody += '<td>' + value.wxCumulateCountTillThisMonth + '</td>';
				sCumulateBody += '<td>' + value.aliCumulateCountTillThisMonth + '</td>';
				sMonthBody += '<td>' + value.thisMonthWxIncCount + '</td>';
				sMonthBody += '<td>' + value.thisMonthAliIncCount + '</td>';

				cumulateData.push(value.wxCumulateCountTillThisMonth + value.aliCumulateCountTillThisMonth);
				monthData.push(value.thisMonthWxIncCount + value.thisMonthAliIncCount);
			});

			cumulateYData.push({
				name: areaKey,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: cumulateData
			});
			monthYData.push({
				name: areaKey,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: monthData
			});

			sCumulateBody += '</tr>';
			sMonthBody += '</tr>';
		})

		for (var i = 0; i < monthSize; i++)
			sCumulateSubHead += '<td>微信</td><td>支付宝</td>';
		sCumulateSubHead += '</tr>';

		sHead += sMonth + '</tr>';
		sCumulateBody = sCumulateSubHead + sCumulateBody;
		sMonthBody = sCumulateSubHead + sMonthBody;

		for (var i = 0; i < xData.length; i++) {
			var monthCount = 0;
			var cumulateCount = 0;
			for (var j = 0; j < legendData.length; j++) {
				var obj = data.datas[legendData[j]][xData[i]];
				monthCount += obj.thisMonthWxIncCount + obj.thisMonthAliIncCount;
				cumulateCount += obj.wxCumulateCountTillThisMonth + obj.aliCumulateCountTillThisMonth;
			}

			tCumulateYData.push(cumulateCount);
			tMonthYData.push(monthCount);
		}

		// 总累计图
		var tCumulateCharts = echarts.init($('#' + objId).find('.tCumulateCharts')[0]);
		tCumulateCharts.setOption(option.line);
		tCumulateCharts.setOption({
			legend: {
				data: tLegenData
			},
			xAxis: [ {
				data: xData
			} ],
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '累计关注数概要'
					}
				}
			},
			series: [ {
				name: '总区域',
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: tCumulateYData
			} ]
		});
		// 总月份图
		var tMonthCharts = echarts.init($('#' + objId).find('.tMonthCharts')[0]);
		tMonthCharts.setOption(option.line);
		tMonthCharts.setOption({
			legend: {
				data: tLegenData
			},
			xAxis: [ {
				data: xData
			} ],
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '各月份关注数概要'
					}
				}
			},
			series: [ {
				name: '总区域',
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: tMonthYData
			} ]
		});
		// 累计图
		var cumulateCharts = echarts.init($('#' + objId).find('.cumulateCharts')[0]);
		cumulateCharts.setOption(option.line);
		cumulateCharts.setOption({
			legend: {
				data: legendData
			},
			xAxis: [ {
				data: xData
			} ],
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '累计关注数'
					}
				}
			},
			series: cumulateYData
		});

		// 月份图
		var monthCharts = echarts.init($('#' + objId).find('.monthCharts')[0]);
		monthCharts.setOption(option.line);
		monthCharts.setOption({
			legend: {
				data: legendData
			},
			xAxis: [ {
				data: xData
			} ],
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '各月份关注数'
					}
				}
			},
			series: monthYData
		});

		// 累计表
		$('#' + objId).find('.cumulateTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.cumulateTable>table>tbody').html('').append(sCumulateBody);
		// 每月表
		$('#' + objId).find('.monthTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.monthTable>table>tbody').html('').append(sMonthBody);
	},
	formatAllAreaAndHospitalsData: function(objId, data) {
		console.log(data);

		var xData = data.monthList;
		var legendData = data.hospitalList;
		var cumulateYData = [];
		var monthYData = [];

		/**
		 * ------------- 表格如下 --------------- *
		 */
		var sHead = '';
		var sMonth = '';
		var sCumulateBody = '';
		var sCumulateSubHead = '<tr><td></td>';
		var monthSize = 0;

		var sMonthHead = '';
		var sMonthBody = '';

		sHead += '<tr>';
		sHead += '	<th style="width: 15%;">医院</th>';

		sMonthHead = sHead;

		$.each(data.datas, function(areaKey, areaValue) {
			$.each(areaValue, function(hospitalkey, hospitalValue) {
				var cumulateData = [];
				var monthData = [];
				monthSize = Object.keys(hospitalValue).length;
				sMonth = '';

				sCumulateBody += '<tr><td>' + hospitalkey + '</td>';
				sMonthBody += '<tr><td>' + hospitalkey + '</td>';

				$.each(hospitalValue, function(month, value) {
					sMonth += '<th colspan="2">' + month + '</th>';
					sCumulateBody += '<td>' + value.wxCumulateCountTillThisMonth + '</td>';
					sCumulateBody += '<td>' + value.aliCumulateCountTillThisMonth + '</td>';
					sMonthBody += '<td>' + value.thisMonthWxIncCount + '</td>';
					sMonthBody += '<td>' + value.thisMonthAliIncCount + '</td>';
					cumulateData.push(value.wxCumulateCountTillThisMonth + value.aliCumulateCountTillThisMonth);
					monthData.push(value.thisMonthWxIncCount + value.thisMonthAliIncCount);
				});

				cumulateYData.push({
					name: hospitalkey,
					type: 'line',
					itemStyle: {
						normal: {
							label: {
								show: true
							}
						}
					},
					data: cumulateData
				});
				monthYData.push({
					name: hospitalkey,
					type: 'line',
					itemStyle: {
						normal: {
							label: {
								show: true
							}
						}
					},
					data: monthData
				});
				
				sCumulateBody += '</tr>';
				sMonthBody += '</tr>';
			});
		})

		for (var i = 0; i < monthSize; i++)
			sCumulateSubHead += '<td>微信</td><td>支付宝</td>';
		sCumulateSubHead += '</tr>';

		sHead += sMonth + '</tr>';
		sCumulateBody = sCumulateSubHead + sCumulateBody;
		sMonthBody = sCumulateSubHead + sMonthBody;

		// 累计图
		var cumulateCharts = echarts.init($('#' + objId).find('.cumulateCharts')[0]);
		cumulateCharts.setOption(option.line);
		cumulateCharts.setOption({
			legend: {
				data: legendData
			},
			xAxis: [ {
				data: xData
			} ],
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '累计关注数'
					}
				}
			},
			series: cumulateYData
		});

		// 月份图
		var monthCharts = echarts.init($('#' + objId).find('.monthCharts')[0]);
		monthCharts.setOption(option.line);
		monthCharts.setOption({
			legend: {
				data: legendData
			},
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '各月份关注数'
					}
				}
			},
			xAxis: [ {
				data: xData
			} ],
			series: monthYData
		});

		$('#' + objId).find('.cumulateTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.cumulateTable>table>tbody').html('').append(sCumulateBody);

		$('#' + objId).find('.monthTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.monthTable>table>tbody').html('').append(sMonthBody);
	},
	formatOneAreaAndHospitalsData: function(objId, data) {
		console.log(data);

		var xData = data.monthList;
		var legendData = data.hospitalList;
		var cumulateYData = [];
		var monthYData = [];

		/**
		 * -------------------------------- 表格如下
		 * --------------------------------------- *
		 */
		var sHead = '';
		var sMonth = '';
		var sCumulateBody = '';
		var sCumulateSubHead = '<tr><td></td>';
		var monthSize = 0;

		var sMonthHead = '';
		var sMonthBody = '';

		sHead += '<tr>';
		sHead += '	<th style="width: 15%;">医院</th>';

		sMonthHead = sHead;

		$.each(data.datas, function(hospitalkey, hospitalValue) {
			var cumulateData = [];
			var monthData = [];
			monthSize = Object.keys(hospitalValue).length;
			sMonth = '';

			sCumulateBody += '<tr><td>' + hospitalkey + '</td>';
			sMonthBody += '<tr><td>' + hospitalkey + '</td>';

			$.each(hospitalValue, function(month, value) {
				sMonth += '<th colspan="2">' + month + '</th>';
				sCumulateBody += '<td>' + value.wxCumulateCountTillThisMonth + '</td>';
				sCumulateBody += '<td>' + value.aliCumulateCountTillThisMonth + '</td>';
				sMonthBody += '<td>' + value.thisMonthWxIncCount + '</td>';
				sMonthBody += '<td>' + value.thisMonthAliIncCount + '</td>';

				cumulateData.push(value.wxCumulateCountTillThisMonth + value.aliCumulateCountTillThisMonth);
				monthData.push(value.thisMonthWxIncCount + value.thisMonthAliIncCount);
			});

			cumulateYData.push({
				name: hospitalkey,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: cumulateData
			});
			monthYData.push({
				name: hospitalkey,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: monthData
			});
			
			sCumulateBody += '</tr>';
			sMonthBody += '</tr>';
		});


		for (var i = 0; i < monthSize; i++)
			sCumulateSubHead += '<td>微信</td><td>支付宝</td>';
		sCumulateSubHead += '</tr>';

		sHead += sMonth + '</tr>';
		sCumulateBody = sCumulateSubHead + sCumulateBody;
		sMonthBody = sCumulateSubHead + sMonthBody;

		// 累计图
		var cumulateCharts = echarts.init($('#' + objId).find('.cumulateCharts')[0]);
		cumulateCharts.setOption(option.line);
		cumulateCharts.setOption({
			legend: {
				data: legendData
			},
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '累计关注数'
					}
				}
			},
			xAxis: [ {
				data: xData
			} ],
			series: cumulateYData
		});

		// 月份图
		var monthCharts = echarts.init($('#' + objId).find('.monthCharts')[0]);
		monthCharts.setOption(option.line);
		monthCharts.setOption({
			legend: {
				data: legendData
			},
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '各月份关注数'
					}
				}
			},
			xAxis: [ {
				data: xData
			} ],
			series: monthYData
		});

		$('#' + objId).find('.cumulateTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.cumulateTable>table>tbody').html('').append(sCumulateBody);

		$('#' + objId).find('.monthTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.monthTable>table>tbody').html('').append(sMonthBody);
	},
	formatOneHospitalData: function(objId, data) {
		console.log(data);

		var xData = data.monthList;
		var legendData = data.hospitalList;
		var cumulateYData = [];
		var monthYData = [];

		/**
		 * -------------------------------- 表格如下
		 * --------------------------------------- *
		 */
		var sHead = '';
		var sMonth = '';
		var sCumulateBody = '';
		var sCumulateSubHead = '<tr><td></td>';
		var monthSize = 0;

		var sMonthHead = '';
		var sMonthBody = '';

		sHead += '<tr>';
		sHead += '	<th style="width: 15%;">医院</th>';

		sMonthHead = sHead;

		$.each(data.datas, function(hospitalkey, hospitalValue) {
			var cumulateData = [];
			var monthData = [];
			monthSize = Object.keys(hospitalValue).length;
			sMonth = '';

			sCumulateBody += '<tr><td>' + hospitalkey + '</td>';
			sMonthBody += '<tr><td>' + hospitalkey + '</td>';

			$.each(hospitalValue, function(month, value) {
				sMonth += '<th colspan="2">' + month + '</th>';
				sCumulateBody += '<td>' + value.wxCumulateCountTillThisMonth + '</td>';
				sCumulateBody += '<td>' + value.aliCumulateCountTillThisMonth + '</td>';
				sMonthBody += '<td>' + value.thisMonthWxIncCount + '</td>';
				sMonthBody += '<td>' + value.thisMonthAliIncCount + '</td>';
				cumulateData.push(value.wxCumulateCountTillThisMonth + value.aliCumulateCountTillThisMonth);
				monthData.push(value.thisMonthWxIncCount + value.thisMonthAliIncCount);
			});

			cumulateYData.push({
				name: hospitalkey,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: cumulateData
			});
			monthYData.push({
				name: hospitalkey,
				type: 'line',
				itemStyle: {
					normal: {
						label: {
							show: true
						}
					}
				},
				data: monthData
			});
			
			sCumulateBody += '</tr>';
			sMonthBody += '</tr>';
		});

		for (var i = 0; i < monthSize; i++)
			sCumulateSubHead += '<td>微信</td><td>支付宝</td>';
		sCumulateSubHead += '</tr>';

		sHead += sMonth + '</tr>';
		sCumulateBody = sCumulateSubHead + sCumulateBody;
		sMonthBody = sCumulateSubHead + sMonthBody;

		// 累计图
		var cumulateCharts = echarts.init($('#' + objId).find('.cumulateCharts')[0]);
		cumulateCharts.setOption(option.line);
		cumulateCharts.setOption({
			legend: {
				data: legendData
			},
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '累计关注数'
					}
				}
			},
			xAxis: [ {
				data: xData
			} ],
			series: cumulateYData
		});

		// 月份图
		var monthCharts = echarts.init($('#' + objId).find('.monthCharts')[0]);
		monthCharts.setOption(option.line);
		monthCharts.setOption({
			legend: {
				data: legendData
			},
			toolbox: {
				feature: {
					saveAsImage: {
						title: '保存',
						name: '各月份关注数'
					}
				}
			},
			xAxis: [ {
				data: xData
			} ],
			series: monthYData
		});

		$('#' + objId).find('.cumulateTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.cumulateTable>table>tbody').html('').append(sCumulateBody);

		$('#' + objId).find('.monthTable>table>thead').html('').append(sHead);
		$('#' + objId).find('.monthTable>table>tbody').html('').append(sMonthBody);
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
	subscribe.init();

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
	})/*.on('changeDate',function(e){  
		    var endTime = e.date.format("yyyy-MM-dd");
		    $('#startDate').datetimepicker('setEndDate',endTime);  
		})*/;

});