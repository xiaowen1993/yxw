var orderStats = {
	bindEvent: function() {
		$('.tab').off('click').on('click', function() {
			if (!$(this).is('.active')) {
				$('.tab').removeClass('active');
				$(this).addClass('active');
				
				orderStats.showOtherStats($(this));
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
			orderStats.resetPageInfos();
			
			orderStats.getStatsInfos();
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
	// 获取累计信息 -- 就是获取昨天的，不管怎么变都不动。
	getAccumulativeInfos: function() {
		var url = appPath + "stats/order/getAccumulativeInfos";
		var tempDate = new Date();
		tempDate.setDate(tempDate.getDate() - 1);
		var statsDate = tempDate.Format('yyyy-MM-dd');
		
		$.ajax({
			type : 'POST',  
	       	url : url,  
	       	data : {
	       		hospitalCode: $('#hospitalCode').val(),
	       		platform: '-1',
	       		branchCode: '-1',
	       		statsDate: statsDate
	       	},  
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		console.log(data);
	       	}, 
	       	success: function(data) {
	       		console.log(data);
	       		if (data.status == 'OK') {
	       			orderStats.formatAccumulativeInfos(data.message);
	       		} else {
	       			orderStats.showNoDatas($('#accumulativeData'), 5);
	       		}
	       	}
		});
	}, 
	// 格式累计信息
	formatAccumulativeInfos: function(data) {
		var jsonData = JSON.parse(data);
		if (jsonData.result.items && jsonData.result.items.length > 0) {
			var sHtml = '';
			
			$.each(jsonData.result.items, function(i, item) {
				sHtml += '<tr>';
				sHtml += '	<td>' + item.cumulativeQuantity + '</td>';
				sHtml += '	<td>' + item.cumulativePaidQuantity + '</td>';
				sHtml += '	<td>' + item.cumulativeRefundQuantity + '</td>';
				sHtml += '	<td>' + (item.cumulativePaidAmount / 100).toFixed(2) + '</td>';
				sHtml += '	<td>' + (item.cumulativeRefundAmount / 100).toFixed(2) + '</td>';
				sHtml += '</tr>';
			});
			
			$('#accumulativeData').html('');
			$('#accumulativeData').append(sHtml);
		} else {
			orderStats.showNoDatas($('#accumulativeData'), 5);
		}
	},
	getStatsInfos: function() {
		$('#beginDate').val($('#dateBegin').val());
		$('#endDate').val($('#dateEnd').val());
		$('#branchCode').val($('#pBranch').attr('data-value'));
		$('#platform').val($('#pPlatform').attr('data-value'));
		var datas = $('#voForm').serializeArray();
		var url = appPath + "stats/order/getStatsInfo";
		
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
	       			orderStats.formatStatsInfo(data.message);
	       		} else {
	       			orderStats.showNoDatas($('#statsDetail'), 6);
	       			$('#pageUl').html('');
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
				orderStats.showNoDatas($('#statsDetail'), 5);
				$('#pageUl').html('');
			}
		
			$.each(items.result.items, function(i, item) {
				sHtml += '<tr>';
				sHtml += '<td>' + item.statsDate + '</td>';
				sHtml += '<td>' + item.ordersQuantity + '</td>';
				sHtml += '<td>' + item.paidQuantity + '</td>';
				sHtml += '<td>' + (Number(item.paidAmount) / 100).toFixed(2) + '</td>';
				sHtml += '<td>' + item.refundQuantity + '</td>';
				sHtml += '<td>' + (Number(item.refundAmount) / 100).toFixed(2) + '</td>';
				sHtml += '</tr>';
			});
			
			if (sHtml) {
				$('#statsDetail').html('');
				$('#statsDetail').append(sHtml);
			}
		} else {
			orderStats.showNoDatas($('#statsDetail'), 5);
			$('#pageUl').html('');
		}
	}, 
	showNoDatas: function(obj, colspan) {
		var sHtml = '<td colspan="' + colspan + '">找不到数据</td>'
		$(obj).html('');
		$(obj).append(sHtml);
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
		
		orderStats.getStatsInfos();
	}
}

$(function() {
	orderStats.bindEvent();
	
	// 获取初始化统计信息
	$('#platform').val('-1');
	$('#branchCode').val('-1');
	orderStats.getAccumulativeInfos();
	
	// 表格数据
	$('#pageIndex').val(1);
	$('#pageSize').val(20);
	orderStats.getStatsInfos();
});	