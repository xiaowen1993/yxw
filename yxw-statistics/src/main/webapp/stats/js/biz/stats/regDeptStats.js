var regDeptStats = {
	bindEvent: function() {
		$('.tab').off('click').on('click', function() {
			if (!$(this).is('.active')) {
				$('.tab').removeClass('active');
				$(this).addClass('active');
				
				regDeptStats.showOtherStats($(this));
			}
		});
		
		$('#btnSearch').off('click').on('click', function() {
			var beginDate = $('#dateBegin').val();
			var endDate = $('#dateEnd').val();
			
			if (beginDate && endDate && beginDate > endDate) {
				alert('开始时间不得大于结束时间...');
				return false;
			}
			
			// 重置分页数据
			regDeptStats.resetPageInfos();
			
			regDeptStats.getStatsInfos();
		});
		
		$('#btnDownload').off('click').on('click', function() {
			alert('暂未开通此功能，请联系乔帮主!');
		});
	},
	// 跳转其他业务统计
	showOtherStats: function(obj) {
		var url = $(obj).attr('data-url');
		url = appPath + url + '?bizType=3&hospitalCode=' + $('#hospitalCode').val() + '&hospitalName=' + encodeURIComponent($('#hospitalName').val()); 
		// 跳转，参数等等等等
		console.log(url);
		window.location.href = url;
	},
	getStatsInfos: function() {
		$('#platform').val($('#pPlatform').attr('data-value'));
		$('#beginDate').val($('#dateBegin').val());
		$('#endDate').val($('#dateEnd').val());
		var url = appPath + "stats/regDept/getStatsInfo";
		
		$.ajax({
			type : 'POST',  
	       	url : url,  
	       	data : {
	       		hospitalCode: $('#hospitalCode').val(),
	       		platform: $('#platform').val(),
	       		deptName: $('#deptName').val(),
	       		pageSize: $('#pageSize').val(),
	       		pageIndex: $('#pageIndex').val()
	       	},
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		console.log(data);
	       	}, 
	       	success: function(data) {
	       		console.log(data);
	       		if (data.status == 'OK') {
	       			regDeptStats.formatStatsInfo(data.message);
	       		} else {
	       			regDeptStats.showNoDatas($('#statsDetail'), 6);
	       		}
	       	}
		});
	},
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
				regDeptStats.showNoDatas($('#statsDetail'), 6);
			}
		
			$.each(items.result.items, function(i, item) {
				sHtml += '<tr>';
				sHtml += '<td>' + item.deptName + '</td>';
				sHtml += '<td>' + item.yesterdayQuantity + '</td>';
				sHtml += '<td>' + item.lastWeekQuantity + '</td>';
				sHtml += '<td>' + item.thisMonthQuantity + '</td>';
				sHtml += '<td>' + item.lastMonthQuantity + '</td>';
				sHtml += '<td>' + item.thisYearQuantity + '</td>';
				sHtml += '<td>' + item.totalQuantity + '</td>';
				sHtml += '</tr>';
			});
			
			if (sHtml) {
				$('#statsDetail').html('');
				$('#statsDetail').append(sHtml);
			}
		} else {
			regDeptStats.showNoDatas($('#statsDetail'), 6);
		}
	}, 
	showNoDatas: function(obj, colspan) {
		var sHtml = '<td colspan="' + colspan + '">找不到数据</td>'
		$(obj).html('');
		$(obj).append(sHtml);
		
		$('#pageUl').html('');
	},
	resetPageInfos: function() {
		$('#pageIndex').val(1);
		$('#pageSize').val(20);
	}
}

function paging(page) {
	var currentPageIndex = $('#pageIndex').val();
	if (currentPageIndex != page) {
		$('#pageIndex').val(page);
		
		regDeptStats.getStatsInfos();
	}
}

$(function() {
	regDeptStats.bindEvent();
	
	//　初始化数据
	$('#platform').val('-1');
	$('#branchCode').val('-1');
	
	// 表格数据
	$('#pageIndex').val(1);
	$('#pageSize').val(20);
	regDeptStats.getStatsInfos();
});	