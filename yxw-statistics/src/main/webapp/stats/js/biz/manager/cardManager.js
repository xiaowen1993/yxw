var cardManager = {
	// 事件绑定
	bindEvent: function() {
		// 查询
		$('#btnSearch').off('click').on('click', function() {
			cardManager.searchOrders();
		});
	},
	searchOrders: function() {
		var url = appPath + "manager/card/getCards";
		$('#platform').val($('#pPlatform').attr('data-value'));
		$('#state').val($('#pState').attr('data-value'));
		$('#patientName').val($('#pName').val());
		$('#cardNo').val($('#pCardNo').val());
		$('#mobileNo').val($('#pMobile').val());
		$('#idNo').val($('#pIdNo').val());
		
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
	       			cardManager.formatDatas(data.message);
	       		} else {
	       			cardManager.showNoDatas();
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
					cardManager.showNoDatas();
				}
				
				$.each(itemData.result.items, function(i, item) {
					sHtml += '<tr>'
					sHtml += '	<td data-id="' + item.id + '" tableName="' + item.tableName +'"><label class="checkboxTwo inline"><input type="checkbox" name="one"/></label></td>';
					sHtml += '	<td>' + getValue(item.name) + '</td>';
					sHtml += '	<td>' + (item.sex == '1' ? '男' : '女') + '</td>';
					sHtml += '	<td>' + item.birth + '</td>';
					sHtml += '	<td>' + item.idNo + '</td>';
					sHtml += '	<td>' + item.cardNo + '</td>';
					sHtml += '	<td>' + item.mobile + '</td>';
					sHtml += '	<td>' + (item.state == '1' ? '已绑定' : '未绑定') + '</td>';
					sHtml += '	<td>' + cardManager.getUserType(Number(item.ownership)) + '</td>';
					sHtml += '	<td>' + new Date(item.updateTime).Format('yyyy-MM-dd hh:mm:ss') + '</td>';
					sHtml += '	<td>' + cardManager.getPlatform(Number(item.platform)) + '</td>';
					sHtml += '	<td>' + item.openId + '</td>';
					sHtml += '</tr>'
				});
				
				if (sHtml.length > 0) {
					$('#managerDetail').html('').append(sHtml);
				} else {
					cardManager.showNoDatas();
				}
			} else {
				cardManager.showNoDatas();
			}
		} else {
			cardManager.showNoDatas();
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
	if (data && data.length > 0) {
		return data;
	} else {
		return '';
	}
}

function paging(page) {
	var currentPageIndex = $('#pageIndex').val();
	if (currentPageIndex != page) {
		$('#pageIndex').val(page);
		
		cardManager.searchOrders();
	}
}

$(function() {
	// 事件绑定
	cardManager.bindEvent();
	// 默认显示页数
	$('#pageIndex').val('1');
});	