var mainStats = {
	// 绑定按钮事件
	bindEvent: function() {
		// KPI event
		$('#kpiTabs li').off('click').on('click', function() {
			if ($(this).is('.active')) {
				// 获取数据-相当于刷新数据
				// 不刷了，懒得刷
			} else {
				// tab页切换
				$('#kpiTabs li').removeClass('active');
				$(this).addClass('active');
				
			}
			
			var kpiType = Number($(this).attr('data-type')) ;
			mainStats.getKPIStats(kpiType);
		});
		
		// 关键指标
		$('#fieldTab .tag_a').off('click').on('click', function() {
			if ($(this).is('.active')) {
				// 获取数据-相当于刷新数据
				// 不刷了，懒得刷
			} else {
				// tab页切换
				$('#fieldTab .tag_a').removeClass('active');
				$(this).addClass('active');
				
				// 获取数据
				var field = $(this).attr('data-field');
				mainStats.selectData(field);
			}
		});
		
		// 日期
		$('#dateTab .g-time').off('click').on('click', function() {
			if ($(this).is('.active')) {
				// 获取数据-相当于刷新数据
				// 不刷了，懒得刷
			} else {
				// tab页切换
				if ($(this).children().length == 0) {
					// 获取数据
					$('#dateTab .g-time').removeClass('active');
					$(this).addClass('active');
					var type = $(this).attr('data-type');
					mainStats.selectDay(type);
				}
			}
		});
		
		// 自定义日期区间
		$('#customDateBtn').off('click').on('click', function() {
			var beginDate = $('#customBegin').val();
			var endDate = $('#customEnd').val();
			
			if (!beginDate) {
				alert('请输入开始时间');
				return false;
			} else if (!endDate) {
				alert('请输入结束时间');
				return false;
			} else {
				if (beginDate > endDate) {
					alert('开始时间不得大于结束时间哦');
					return false;
				} else {
					$('#dateTab .g-time').removeClass('active');
					$(this).parent().addClass('active');
					
					mainStats.customDate(beginDate, endDate);
				}
			}
		});
	},
	// 获取KPI数据
	getKPIStats: function(kpiType) {
		// 0 默认，昨天
		// 1上周
		// 2上月
		// 3上季度
		var beginDate, endDate;
		
		switch (kpiType) {
		case 0:
			beginDate = mainStats.getBetweenDaysDate(-1);
			endDate = beginDate;
			
			// 测试
			beginDate = '2016-05-24';
			endDate = '2016-05-24';
			break;
		case 1:
			beginDate = mainStats.getLastWeekFirstDay();
			endDate = mainStats.getLastWeekLastDay();
			break;
		case 2:	
			beginDate = mainStats.getLastMonthFirstDay();
			endDate = mainStats.getLastMonthLastDay();
			break;
		case 3:
			beginDate = mainStats.getLastQuarterFirstDay();
			endDate = mainStats.getLastQuarterLastDay();
			break;
		default:
			console.log('未知的查询区间');
		}
		
		$('#beginDate').val(beginDate);
		$('#endDate').val(endDate);
		var datas = $('#voForm').serializeArray();
		var url = appPath + "stats/main/getKpiInfos";
		
		$.ajax({
			type : 'POST',  
	       	url : url,  
	       	data : datas,  
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		console.log(data);
	       	}, 
	       	success: function(data) {
	       		if (data.status == 'OK') {
	       			mainStats.formatKPIDatas(data.message);
	       		} else {
	       			console.log(data);
	       		}
	       	}
		});
	},
	// 格式化KPI数据
	formatKPIDatas: function(data) {
		var obj = JSON.parse(data);
		if (obj.resultCode == 0 && obj.result.size > 0) {
			var item = obj.result.items;
			var sHtml = '';
			
			sHtml += '<td>' + (item.netIncome / 100).toFixed(2) + '</td>';
			sHtml += '<td>' + (item.regPaidAmount).toFixed(2) + '</td>';
			sHtml += '<td>' + (item.clinicPaidAmount).toFixed(2) + '</td>';
			sHtml += '<td>' + (item.depositPaidAmount).toFixed(2) + '</td>';
			sHtml += '<td>' + (item.regRefundAmount).toFixed(2) + '</td>';
			sHtml += '<td>' + (item.clinicRefundAmount).toFixed(2) + '</td>';
			sHtml += '<td>' + (item.depositRefundAmount).toFixed(2) + '</td>';
			
			$('#kpiDatas').html('');
			$('#kpiDatas').append(sHtml);
		} 
	},
	// 指定获取图标数据的类型（挂号、门诊、住院及各种相应退费）
	selectData: function(field) {
		$('#fieldTab').attr('data-field', field);
		mainStats.getStatsInfoWithFields(beginDate, endDate);
	},
	// 指定日期区间数据获取
	selectDay: function(type) {
		var	beginDate = mainStats.getBetweenDaysDate(-1 * Number(type));
		var	endDate = mainStats.getBetweenDaysDate(-1);
		
		$('#dateTab').attr('data-begin', beginDate);
		$('#dateTab').attr('data-end', endDate);
		
		mainStats.getStatsInfoWithFields();
	},
	// 用户自定义区间数据获取
	customDate: function(beginDate, endDate) {
		$('#dateTab').attr('data-begin', beginDate);
		$('#dateTab').attr('data-end', endDate);
		
		mainStats.getStatsInfoWithFields();
	},
	// 获取图标数据
	getStatsInfoWithFields: function() {
		// field,beginDate,endDate同时不为空, 则是关键指标详解（某一项图标）
		var beginDate = $('#dateTab').attr('data-begin');
		var endDate = $('#dateTab').attr('data-end');
		var fields = $('#fieldTab').attr('data-field');
		$('#beginDate').val(beginDate);
		$('#endDate').val(endDate);
		$('#fields').val(fields + ',statsDate,hospitalCode');
		var datas = $('#voForm').serializeArray();
		var url = appPath + "stats/main/getStatsInfo";
		
		$.ajax({
			type : 'POST',  
	       	url : url,  
	       	data : datas,  
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		console.log(data);
	       	}, 
	       	success: function(data) {
	       		if (data.status == 'OK') {
	       			mainStats.formatStatsInfoWithFields(data.message, beginDate, endDate, fields);
	       		} else {
	       			
	       		}
	       	}
		});
	},
	// 格式化图标数据并显示
	formatStatsInfoWithFields: function(data, beginDate, endDate, fields) {
		var startTime, endTime;
		var day = mainStats.getDaysBetween(beginDate, endDate);
		option = {
	        tooltip : {
	            trigger: 'axis'
	        },
	        xAxis: [
	            {
	                type: 'category',
	                boundaryGap: false,
	                data: [],
	                axisTick:{
	                }
	            }
	        ],
	        yAxis: [
	            {
	                type: 'value'
	            }
	        ],
	        series: [
	            {
	                name:'金额',
	                type:'line',
	                smooth:true,
	                stack: 'a',
	                areaStyle: {
	                    normal: {}
	                },
	                data: [],
	                label: {
	                    normal: {
	                        show: true,
	                        position: 'top'
	                    }
	                }
	            }
	        ]
	    };
	    var mainChart = echarts.init(document.getElementById('mainChart'));
		
	    option.xAxis[0].data = [];

        for (var i = 0; i <= day; i++) {
            var tempDate = new Date(beginDate);
            tempDate.setDate(tempDate.getDate() + i);
            var formatTime = tempDate.Format('yyyy-MM-dd');
            option.xAxis[0].data.push(formatTime);
        }
        
        // 设置开始时间，结束时间
        startTime = option.xAxis[0].data[0];
        endTime = option.xAxis[0].data[option.xAxis[0].data.length - 1];
        
        //y轴数据
        option.series[0].data=[];

        var items = JSON.parse(data);
        $.each(items.result.items, function(i, item) {
        	var opt = eval('item.' + fields);
        	option.series[0].data.push(opt == '0' ? '0' : (Number(opt) / 100).toFixed(2))
        });

        mainChart.setOption(option,true);
	},
	// 最下方数据表格统计数据展示
	getStatsInfos: function() {
		var pageIndex = $('#pageIndex').val();
		var pageSize = $('#pageSize').val();
		var url = appPath + "stats/main/getStatsInfo";
		
		$.ajax({
			type : 'POST',  
	       	url : url,  
	       	data : {
	       		pageIndex: pageIndex,
	       		pageSize: pageSize,
	       		hospitalCode: $('#hospitalCode').val()
	       	},
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		console.log(data);
	       	}, 
	       	success: function(data) {
	       		console.log(data);
	       		if (data.status == 'OK') {
	       			mainStats.formatStatsInfo(data.message);
	       		} else {
	       			mainStats.showNoDatas($('#statsDetail'), 8);
	       		}
	       	}
		});
	},
	// 格式化表格数据
	formatStatsInfo: function(data) {
		var items = JSON.parse(data);
		
		if (items.result.items && items.result.items.length > 0) {
			var sHtml = '';
			
			// 分页
			var size = items.result.size;
			var maxPage = 0;
			if (size % 20 == 0) {
				maxPage = size / 20;
			} else {
				maxPage = parseInt(size / 20) + 1;
			}
			$('#maxPage').val(maxPage);
			
			if (maxPage > 0) {
				// 分页控件
		        var element = $('#pageUl');//对应下面ul的ID  
		        var options = {  
		            bootstrapMajorVersion: 3,  
		            currentPage: $('#pageIndex').val(),//当前页面  
		            numberOfPages: 5,//一页显示几个按钮（在ul里面生成5个li）  
		            totalPages: maxPage, //总页数  
		            size: "large",
		            alignment: "left"
		        }  
		       element.bootstrapPaginator(options);  
			} else {
				mainStats.showNoDatas($('#statsDetail'), 8);
			}
		
			$.each(items.result.items, function(i, item) {
				sHtml += '<tr>';
				sHtml += '<td>' + item.statsDate + '</td>';
				sHtml += '<td>' + (Number(item.netIncome) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + (Number(item.regPaidAmount) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + (Number(item.clinicPaidAmount) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + (Number(item.depositPaidAmount) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + (Number(item.regRefundAmount) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + (Number(item.clinicRefundAmount) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + (Number(item.depositRefundAmount) / 100).toFixed(2) + '</td>';
				sHtml += '</tr>';
			});
			
			if (sHtml) {
				$('#statsDetail').html('');
				$('#statsDetail').append(sHtml);
			}
		} else {
			mainStats.showNoDatas($('#statsDetail'), 8);
		}
	},
	showNoDatas: function(obj, colspan) {
		var sHtml = '<td colspan="' + colspan + '">找不到数据</td>'
		$(obj).html('');
		$(obj).append(sHtml);
		
		$('#pageUl').html('');
	},
	getDaysBetween: function(date1, date2) {
		var tempDate1 = new Date(date1);
		var tempDate2 = new Date(date2);
		return Math.abs(tempDate1 - tempDate2) / (24 * 1000 * 60 * 60);
	},
	getBetweenDaysDate: function(days, tDate) {
		if (!tDate) {
			var tempDate = new Date();
			tempDate.setDate(tempDate.getDate() + days);
			return tempDate.Format('yyyy-MM-dd');
		} else {
			var tempDate = new Date(tDate);
			tempDate.setDate(tempDate.getDate() + days);
			return tempDate.Format('yyyy-MM-dd');
		}
	},
	getLastWeekFirstDay: function() {
		var tempDate = new Date();
		// 从周一开始算第一天
		tempDate.setDate(tempDate.getDate() - tempDate.getDay() - 6 );
		return tempDate.Format('yyyy-MM-dd');
	},
	getLastWeekLastDay: function() {
		var tempDate = new Date();
		tempDate.setDate(tempDate.getDate() - tempDate.getDay());
		return tempDate.Format('yyyy-MM-dd');
	},
	getLastMonthFirstDay: function() {
		var tempDate = new Date();
		tempDate.setMonth(tempDate.getMonth() - 1);
		tempDate.setDate(1);
		return tempDate.Format('yyyy-MM-dd');
	},
	getLastMonthLastDay: function() {
		var tempDate = new Date();
		tempDate.setDate(1);
		tempDate.setDate(tempDate.getDate() - 1);
		return tempDate.Format('yyyy-MM-dd');
	},
	getLastQuarterFirstDay: function() {
		var tempDate = new Date();
		tempDate.setMonth(mainStats.getLastQuarterStartMonth(tempDate.getMonth()));
		tempDate.setDate(1);
		return tempDate.Format('yyyy-MM-dd');
	},
	getLastQuarterLastDay: function() {
		var tempDate = new Date();
		tempDate.setMonth(mainStats.getLastQuarterStartMonth(tempDate.getMonth()) + 3);
		tempDate.setDate(1);
		tempDate.setDate(tempDate.getDate() - 1);
		return tempDate.Format('yyyy-MM-dd');
	},
	getLastQuarterStartMonth: function(month) {
		var startMonth = 0;
		
		var spring = 0; // 春
        var summer = 3; // 夏
        var fall = 6;   // 秋
        var winter = 9; // 冬
        if (month < 3) {
        	startMonth = winter;
        } else if (month < 6) {
        	startMonth = spring;
        } else if (month < 9) {
        	startMonth = summer;
        } else {
        	startMonth = fall;
        }
        
        return startMonth;
	}
}

function paging(page) {
	var currentPageIndex = $('#pageIndex').val();
	if (currentPageIndex != page) {
		$('#pageIndex').val(page);
		
		mainStats.getStatsInfos();
	}
}

$(function() {
	mainStats.bindEvent();
	// 初始化显示KPI
	mainStats.getKPIStats(0);
	// 初始化显示图标数据
	var beginDate = mainStats.getBetweenDaysDate(-7);
	var endDate = mainStats.getBetweenDaysDate(-1);
	$('#fieldTab').attr('data-field', 'netIncome');
	$('#dateTab').attr('data-begin', beginDate);
	$('#dateTab').attr('data-end', endDate);
	mainStats.getStatsInfoWithFields(beginDate, endDate);
	// 最下方表格数据显示
	$('#pageIndex').val(1);
	$('#pageSize').val(20);
	mainStats.getStatsInfos();
});