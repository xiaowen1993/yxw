var registerManager = {
	// 事件绑定
	bindEvent: function() {
		// 查询
		$('#btnSearch').off('click').on('click', function() {
			registerManager.searchOrders();
		});
	},
	searchOrders: function() {
		var url = appPath + "manager/register/getOrders";
		$('#platform').val($('#pPlatform').attr('data-value'));
		$('#branchCode').val($('#pBranch').attr('data-value'));
		$('#deptName').val($('#pDeptName').val());
		$('#doctorName').val($('#pDoctorName').val());
		$('#patientName').val($('#pName').val());
		$('#cardNo').val($('#pCardNo').val());
		$('#patientMobile').val($('#pMobile').val());
		$('#regType').val($('#pRegType').attr('data-value'));
		$('#bizStatus').val($('#pBizStatus').attr('data-value'));
		$('#regBeginTime').val($('#pRegBeginTime').val());
		$('#regEndTime').val($('#pRegEndTime').val());
		$('#visitBeginTime').val($('#pVisitBeginTime').val());
		$('#visitEndTime').val($('#pVisitEndTime').val());
		
		var datas = $('#voForm').serializeArray();
		
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
	       		console.log(data);
	       		if (data.status == 'OK') {
	       			registerManager.formatDatas(data.message);
	       		} else {
	       			registerManager.showNoDatas();
	       		}
	       	}
		});
	},
	formatDatas: function(data) {
		if (data) {
			var itemData = JSON.parse(data);
			if (itemData.result && itemData.result.items && itemData.result.items.length > 0) {
				var sHtml = '';
				
				// 分页
				var size = itemData.result.size;
				var maxPage = 0;
				if (size % 30 == 0) {
					maxPage = size / 30;
				} else {
					maxPage = parseInt(size / 30) + 1;
				}
				$('#maxPage').val(maxPage);
				console.log(maxPage);
				
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
					registerManager.showNoDatas();
				}
				
				$.each(itemData.result.items, function(i, item) {
					sHtml += '<tr>'
					sHtml += '	<td data-id="' + item.id + '" tableName="' + item.tableName +'"><label class="checkboxTwo inline"><input type="checkbox" name="one"/></label></td>';
					sHtml += '	<td>' + getValue(item.patientName) + '</td>';
					sHtml += '	<td>' + getValue(item.cardNo) + '</td>';
					sHtml += '	<td>' + getValue(item.patientMobile) + '</td>';
					sHtml += '	<td>' + registerManager.getPlatform(Number(item.platform)) + '</td>';
					sHtml += '	<td>' + (item.regType == '1' ? '预约挂号' : '当班挂号') + '</td>';
					sHtml += '	<td>' + $('#pBranch').next().find('li[data-value=' + item.branchCode + ']').text() + '</td>';
					sHtml += '	<td>' + getValue(item.deptName) + '</td>';
					sHtml += '	<td>' + getValue(item.doctorName) + '</td>';
					sHtml += '	<td>' + (Number(item.totalFee) / 100).toFixed(2) + '</td>';
					sHtml += '	<td>' + new Date(item.registerTime).Format('yyyy-MM-dd hh:mm:ss') + '</td>';
					sHtml += '	<td>' + getValue(item.scheduleDate) + '</td>';
					sHtml += '	<td>' + getValue(item.beginTime) + ' - ' + getValue(item.endTime) + '</td>';
					sHtml += '	<td>' + $('#pBizStatus').next().find('li[data-value=' + item.bizStatus + ']').text() + '</td>';
					sHtml += '	<td>' + $('#pPayStatus').next().find('li[data-value=' + item.payStatus + ']').text() + '</td>';
					sHtml += '	<td>' + getValue(item.hisOrdNo) + '</td>';
					sHtml += '</tr>'
				});
				
				if (sHtml.length > 0) {
					$('#managerDetail').html('').append(sHtml);
				} else {
					registerManager.showNoDatas();
				}
			} else {
				registerManager.showNoDatas();
			}
		} else {
			registerManager.showNoDatas();
		}
	},
	getPlatform: function(platformCode) {
		var platformName = '未知平台类型';
		
		switch (platformCode) {
		case 1:
			platformName = '标准平台-微信';
			break;
		case 2:
			platformName = '标准平台-支付宝';
			break;
		case 3: 
			platformName = '健康易';
			break;
		case 4: 
			platformName = '新平台';
			break;
		case 5:
			platformName = '新平台-微信';
			break;
		case 6:
			platformName = '新平台-支付宝';
			break;
		case 7:
			platformName = '医院项目';
			break;
		default:
			
		}
		
		return platformName;
	},
	getUserType: function(ownership) {
		var ownershipName = '未知用户类型';
		
		switch (ownership) {
		case 1:
			ownershipName = '本人';
			break;
		case 2:
			ownershipName = '他人';
			break;
		case 3: 
			ownershipName = '儿童';
			break;
		default:
			
		}
		
		return ownershipName;
	},
	showNoDatas: function() {
		$('#managerDetail').html('').append('<td colspan="17">没有找到就诊卡信息</td>');
		$('#pageUl').html('');
	}
}

function getValue(data) {
	if (data && data.length > 0 && data != 'null') {
		return data;
	} else {
		return '';
	}
}

function paging(page) {
	var currentPageIndex = $('#pageIndex').val();
	if (currentPageIndex != page) {
		$('#pageIndex').val(page);
		
		registerManager.searchOrders();
	}
}

$(function() {
	// 事件绑定
	registerManager.bindEvent();
	// 默认显示页数
	$('#pageIndex').val('1');
});	