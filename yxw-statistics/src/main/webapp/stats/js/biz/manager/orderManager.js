var orderManager = {
	// 事件绑定
	bindEvent: function() {
		// 业务类型
		$(document).on('click','.js-tabOpt', function(){
            var index = $(this).index();
            $(this).addClass('active').siblings().removeClass('active');
            $('.js-tabOptCon').hide().eq(index).show();
            // 切换移除选中的li
            /*
            var obj = $('.js-tabOptCon').find('tr.warning');
            if (obj) {
            	$(obj).removeClass('warning');
            	$(obj).find('label.check').removeClass('check');
            }
            */
        });
		
		// 查询
		$('#btnSearch').off('click').on('click', function() {
			var beginDate = $('#pBeginTime').val();
			var endDate = $('#pEndTime').val();
			
			if (beginDate && endDate && beginDate > endDate) {
				alert('开始时间不得大于结束时间...');
				return false;
			}
			
			orderManager.searchOrders();
		});
	},
	searchOrders: function(data) {
		var datas = {};
		var url = appPath + "manager/order/getOrders";
		
		if (data) {
			datas = data;
		} else {
			$('#platform').val($('#pPlatform').attr('data-value'));
			$('#bizType').val($('#pBizType').attr('data-value'));
			$('#payStatus').val($('#pPayStatus').attr('data-value'));
			$('#bizStatus').val($('#pBizStatus').attr('data-value'));
			$('#patientName').val($('#pName').val());
			$('#cardNo').val($('#pCardNo').val());
			$('#patientMobile').val($('#pMobile').val());
			$('#orderNo').val($('#pOrderNo').val());
			$('#hisOrderNo').val($('#pHisOrderNo').val());
			$('#beginTime').val($('#pBeginTime').val());
			$('#endTime').val($('#pEndTime').val());
			
			datas = {
				hospitalCode: $('#hospitalCode').val(),
				hospitalName: $('#hospitalCode').val(),
				platform: $('#platform').val(),
				branchCode: $('#branchCode').val(),
				beginTime: $('#beginTime').val(),
				endTime: $('#endTime').val(),
				pageSize: $('#pageSize').val(),
				pageIndex: $('#pageIndex').val(),
				bizType: $('#bizType').val(),
				bizStatus: $('#bizStatus').val(),
				payStatus: $('#payStatus').val(),
				patientName: $('#patientName').val(),
				cardNo: $('#cardNo').val(),
				patientMobile: $('#patientMobile').val(),
				orderNo: $('#orderNo').val(),
				hisOrderNo: $('#hisOrderNo').val()
			};
		}
		
		if (!datas.patientName && !datas.cardNo && !datas.patientMobile && !datas.orderNo && !datas.hisOrderNo) {
			var box = new $Y.dialog({
	            title:'',
	            width:'450px',
	            height:'200px',
	            content:'<div class="orderRefundContent"><div class="orderRefundTips">请至少输入一个查询条件（红色选项）</div> <div class="controlsBtnBox rowBg center"><button class="btn-save" onclick="$Y.dialog.close($(this));">确定</button></div></div>'
	        });
			
			return false;
		}
		
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
	       			orderManager.formatDatas(data.message);
	       		} else {
	       			orderManager.showNoDatas();
	       		}
	       	}
		});
	},
	formatDatas: function(data) {
		if (data) {
			var itemData = JSON.parse(data);
			if (itemData.result && itemData.result.items && itemData.result.items.length > 0) {
				var sPage = '';
				var sTabs = '';
				
				$('#detailTabs').html('');
				$('#detailPages').html('');
				
				$.each(itemData.result.items, function(i, item) {
					if (item.beans && item.beans.length > 0) {
						var bizName = orderManager.getBizName(Number(item.bizType));
						var platformName = orderManager.getOrderPlatform(Number(item.platform));
						var tabName = bizName + '(' + platformName + ')';
						sTabs = '<div class="opt-li js-tabOpt" style="padding: 10px 10px;">' + tabName + '</div>';
						
						sPage = '<div class="js-tabOptCon" style="display: none;">';
						sPage += '	<div class="orderTable">';
						sPage += '		<table class="table table-bordered table-textCenter table-hover">';
						sPage += '			<thead>';
						sPage += '				<tr>';
						sPage += '					<th>管理</th>';
						sPage += '					<th>订单生成时间</th>';
						sPage += '					<th>订单退费时间</th>';
						sPage += '					<th>订单类型</th>';
						sPage += '					<th>订单渠道</th>';
						sPage += '					<th>HIS订单号</th>';
						sPage += '					<th>收单机构订单号</th>';
						sPage += '					<th>平台支付订单号</th>';
						sPage += '					<th>平台退费订单号</th>';
						sPage += '					<th>业务状态</th>';
						sPage += '					<th>支付状态</th>';
						sPage += '					<th>患者姓名</th>';
						sPage += '					<th>患者卡号</th>';
						sPage += '					<th>患者手机</th>';
						sPage += '					<th>医院收据号</th>';
						sPage += '					<th>订单金额</th>';
						sPage += '					<th>备注</th>';
						sPage += '				</tr>';
						sPage += '			</thead>';
						sPage += '			<tbody class="form-check managerDetail">';

						$.each(item.beans, function(j, detail) {
							sPage += '<tr>';
							sPage += '	<td data-id="' + detail.id + '" tableName="' + detail.tableName +'"><label class="checkboxTwo inline"><input type="checkbox" name="one"/></label></td>';
							sPage += '	<td>' + new Date(detail.createTime ? detail.createTime : detail.registerTime).Format('yyyy-MM-dd hh:mm:ss') + '</td>';
							sPage += '	<td>' + (detail.refundTime ? new Date(detail.refundTime).Format('yyyy-MM-dd hh:mm:ss') : '') + '</td>';
							sPage += '	<td>' + bizName + '</td>';
							sPage += '	<td>' + platformName + '</td>';
							sPage += '	<td>' + getValue(detail.hisOrdNo) + '</td>';
							sPage += '	<td>' + getValue(detail.agtOrdNum) + '</td>';
							sPage += '	<td>' + detail.orderNo + '</td>';
							sPage += '	<td>' + getValue(detail.refundOrderNo) + '</td>';
							sPage += '	<td>' + orderManager.getBizStatus(Math.abs(Number(item.bizType)), detail.bizStatus) + '</td>';
							sPage += '	<td>' + $('#pPayStatus').next().find('li[data-value="' + detail.payStatus + '"]').text() + '</td>';
							sPage += '	<td>' + detail.patientName +'</td>';
							sPage += '	<td>' + detail.cardNo + '</td>';
							sPage += '	<td>' + detail.patientMobile + '</td>';
							sPage += '	<td>' + getValue(detail.receiptNum) + '</td>';
							sPage += '	<td>' + (Number(detail.totalFee) / 100).toFixed(2) + '</td>';
							sPage += '	<td></td>'; 			// 这个备注，就不要算了，懒得加
							sPage += '</tr>';
						})
						
						// 分页
						var size = item.size;
						// 分页大小 -- 需要按照查询的表来
						var interval = 20;
						
						var maxPage = 0;
						if (size % 30 == 0) {
							maxPage = size / 30;
						} else {
							maxPage = parseInt(size / 30) + 1;
						}
						
						sPage += '			</tbody>';
						sPage += '		</table>';
						sPage += '	</div>';
						sPage += '	<div class="row-fluid">';
						sPage += '		<div class="pagination pagination-centered">';
						sPage += '			<ul class="pageUl" id="page_' + i + '" data-index="1" data-max="' + maxPage + '" bizType="' + item.bizType + '" platform="' + item.platform + '">';			//-- 用data-index来记录当前页数
						sPage += '			</ul>';
						sPage += '		</div>';
						sPage += '	</div>';
						sPage += '</div>';
						
						$('#detailTabs').append(sTabs);
						$('#detailPages').append(sPage);
						
						// 分页控件
				        var element = $('#detailPages').find('.pageUl').last();//对应下面ul的ID  
				        var options = {  
				            bootstrapMajorVersion: 3,  
				            currentPage: $(element).attr('data-index'),//当前页面  
				            numberOfPages: 5,//一页显示几个按钮（在ul里面生成5个li）  
				            totalPages: $(element).attr('data-max'), //总页数  
				            size: "large",
				            alignment: "left"
				        }  
				        element.bootstrapPaginator(options);  
					}
				});
				
				if (sTabs && sTabs.length > 0) {
					$('#detailTabs').show();
					$('#detailTabs').find('.opt-li').eq('0').trigger("click");
				} else {
					orderManager.showNoDatas();
				}
			} else {
				orderManager.showNoDatas();
			}
		} else {
			orderManager.showNoDatas();
		}
	},
	searchOrdersByPages: function(data) {
		var url = appPath + "manager/order/getOrders";
		$.ajax({
			type : 'POST',  
	       	url : url,  
	       	data : data,
	       	dataType : 'json',  
	       	timeout:120000,
	       	error: function(data) {
	       		console.log(data);
	       	}, 
	       	success: function(data) {
	       		console.log(data);
	       		if (data.status == 'OK') {
	       			orderManager.formatDatasForPages(data.message);
	       		} else {
	       			alert('查询异常');
	       		}
	       	}
		});
	},
	formatDatasForPages: function(data) {
		if (data) {
			var itemData = JSON.parse(data);
			if (itemData.result && itemData.result.items && itemData.result.items.length > 0) {
				var sHtml = '';
				
				var index = $('.js-tabOpt.active').index();
			    var showPage = $('.js-tabOptCon').eq(index);
				
				$.each(itemData.result.items, function(i, item) {
					if (item.beans && item.beans.length > 0) {
						var bizName = orderManager.getBizName(Number(item.bizType));
						var platformName = orderManager.getOrderPlatform(Number(item.platform));
						
						$.each(item.beans, function(j, detail) {
							sHtml += '<tr>';
							sHtml += '	<td data-id="' + detail.id + '" tableName="' + detail.tableName +'"><label class="checkboxTwo inline"><input type="checkbox" name="one"/></label></td>';
							sHtml += '	<td>' + new Date(detail.createTime ? detail.createTime : detail.registerTime).Format('yyyy-MM-dd hh:mm:ss') + '</td>';
							sHtml += '	<td>' + (detail.refundTime ? new Date(detail.refundTime).Format('yyyy-MM-dd hh:mm:ss') : '') + '</td>';
							sHtml += '	<td>' + bizName + '</td>';
							sHtml += '	<td>' + platformName + '</td>';
							sHtml += '	<td>' + getValue(detail.hisOrdNo) + '</td>';
							sHtml += '	<td>' + getValue(detail.agtOrdNum) + '</td>';
							sHtml += '	<td>' + detail.orderNo + '</td>';
							sHtml += '	<td>' + getValue(detail.refundOrderNo) + '</td>';
							sHtml += '	<td>' + orderManager.getBizStatus(Math.abs(Number(item.bizType)), detail.bizStatus) + '</td>';
							sHtml += '	<td>' + $('#pPayStatus').next().find('li[data-value="' + detail.payStatus + '"]').text() + '</td>';
							sHtml += '	<td>' + detail.patientName +'</td>';
							sHtml += '	<td>' + detail.cardNo + '</td>';
							sHtml += '	<td>' + detail.patientMobile + '</td>';
							sHtml += '	<td>' + getValue(detail.receiptNum) + '</td>';
							sHtml += '	<td>' + (Number(detail.totalFee) / 100).toFixed(2) + '</td>';
							sHtml += '	<td></td>'; 			// 这个备注，就不要算了，懒得加
							sHtml += '</tr>';
						})
						
						// 分页
						var size = item.size;
						// 分页大小 -- 需要按照查询的表来
						var interval = 20;
						
						var maxPage = 0;
						if (size % 30 == 0) {
							maxPage = size / 30;
						} else {
							maxPage = parseInt(size / 30) + 1;
						}
						
						$(showPage).find('tbody').html('').append(sHtml);
						
						// 分页控件
				        var element = $(showPage).find('.pageUl');//对应下面ul的ID  
				        var options = {  
				            bootstrapMajorVersion: 3,  
				            currentPage: $(element).attr('data-index'),//当前页面  
				            numberOfPages: 5,//一页显示几个按钮（在ul里面生成5个li）  
				            totalPages: $(element).attr('data-max'), //总页数  
				            size: "large",
				            alignment: "left"
				        }  
				        element.bootstrapPaginator(options);  
					}
				});
				
			} else {
				orderManager.showNoDatas();
			}
		} else {
			orderManager.showNoDatas();
		}
	},
	getOrderPlatform: function(platformCode) {
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
	getBizName: function(bizType) {
		var bizName = '未知订单类型';
		switch (bizType) {
		case 1:
			bizName = '挂号订单';
			break;
		case 2:
			bizName = '门诊订单';
			break;
		case 3: 
			bizName = '押金订单';
			break;
		case -2: 
			bizName = '门诊退费订单';
			break;
		case -3:
			bizName = '押金退费订单';
			break;
		default:
			
		}
		
		return bizName;
	},
	getBizStatus: function(bizType, statusCode) {
		var tempType = bizType;
		if (tempType == 3) {
			tempType = 2;
		}
		return $('li[data-type="' + tempType + '"][data-value="' + statusCode + '"]').text();;
	},
	showNoDatas: function(dataMessage) {
		var sHtml = '';
		sHtml += '<div class="js-tabOptCon">';
		sHtml += '	<div class="orderTable">';
		sHtml += '		<table class="table table-bordered table-textCenter table-hover">';
		sHtml += '			<thead>';
		sHtml += '				<tr>';
		sHtml += '					<th>管理</th>';
		sHtml += '					<th>订单生成时间</th>';
		sHtml += '					<th>订单退费时间</th>';
		sHtml += '					<th>订单类型</th>';
		sHtml += '					<th>订单渠道</th>';
		sHtml += '					<th>HIS订单号</th>';
		sHtml += '					<th>收单机构订单号</th>';
		sHtml += '					<th>平台支付订单号</th>';
		sHtml += '					<th>平台退费订单号</th>';
		sHtml += '					<th>业务状态</th>';
		sHtml += '					<th>支付状态</th>';
		sHtml += '					<th>患者姓名</th>';
		sHtml += '					<th>患者卡号</th>';
		sHtml += '					<th>患者手机</th>';
		sHtml += '					<th>医院收据号</th>';
		sHtml += '					<th>订单金额</th>';
		sHtml += '					<th>备注</th>';
		sHtml += '				</tr>';
		sHtml += '			</thead>';
		sHtml += '			<tbody class="form-check managerDetail">';
		sHtml += '				<tr>';
		sHtml += '					<td colspan=17>' + (dataMessage ? dataMessage : '找不到数据...') + '</td>';
		sHtml += '				</tr>';
		sHtml += '			</tbody>';
		sHtml += '		</table>';
		sHtml += '	</div>';
		sHtml += '</div>';
		
		$('#detailPages').html('').append(sHtml);
		$('#detailTabs').hide();
	},
	setDefaultBizStatus: function() {
		// 切换
		$('#pBizStatus').next().find('li[data-type="-1"]').show();
		$('#pBizStatus').next().find('li[data-type!="-1"]').hide();
		
		// 切换后默认选择全部
		$('#pBizStatus').attr('data-value', '-1');
		$('#pBizStatus').val('全部');
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
	var index = $('.js-tabOpt.active').index();
    var showPage = $('.js-tabOptCon').eq(index);
	var tempUl = $(showPage).find('ul.pagination-lg');
	if (tempUl) {
		var currentPageIndex = $(tempUl).attr('data-index');
		if (currentPageIndex != page) {
			$(tempUl).attr('data-index', page);
			
			var datas = {
				hospitalCode: $('#hospitalCode').val(),
				hospitalName: $('#hospitalCode').val(),
				platform: $(tempUl).attr('platform'),
				branchCode: $('#branchCode').val(),
				beginTime: $('#beginTime').val(),
				endTime: $('#endTime').val(),
				pageSize: $('#pageSize').val(),
				pageIndex: page,
				bizType: $(tempUl).attr('bizType'),
				bizStatus: $('#bizStatus').val(),
				payStatus: $('#payStatus').val(),
				patientName: $('#patientName').val(),
				cardNo: $('#cardNo').val(),
				patientMobile: $('#patientMobile').val(),
				orderNo: $('#orderNo').val(),
				hisOrderNo: $('#hisOrderNo').val()
			};
			orderManager.searchOrdersByPages(datas);
		}
	}
}

function liClickCallback(obj) {
	var obj1 = obj.parent().prev();
	if (obj1 && $(obj1).attr('id') == 'pBizType') {
		var bizType = Number($(obj).attr('data-value'));
		if (bizType == 3) {
			bizType = 2;
		}
		
		// 切换
		$('#pBizStatus').next().find('li[data-type="' + bizType + '"]').show();
		$('#pBizStatus').next().find('li[data-type!="' + bizType + '"]').hide();
		
		// 切换后默认选择全部
		$('#pBizStatus').attr('data-value', '-1');
		$('#pBizStatus').val('全部');
	}
}

$(function() {
	// 事件绑定
	orderManager.bindEvent();
	// 设置默认业务状态
	orderManager.setDefaultBizStatus();
	// 初始化表格
	orderManager.showNoDatas('请输入查询条件进行搜索...');
	// 默认显示页数
	$('#pageIndex').val('1');
});	