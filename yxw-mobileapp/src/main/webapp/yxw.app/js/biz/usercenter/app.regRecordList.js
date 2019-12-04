var regRecordList = {
	init: function() {
		pushHistory();
		window.addEventListener("popstate", function(e) {
			
			var referrer = document.referrer;
			if (referrer.indexOf("register/infos/showOrderInfo") > 0 || referrer.indexOf("app/usercenter/regRecords/list") > 0) {
				skipPages.forward('userCenterIndex');
			}else {
				window.location.href = referrer;
			}
			
		}, false);
		function pushHistory() {
			var state = {
				title : "title",
				url : "#"
			}; 
			window.history.pushState(state, "title", "#");
		}
		
	},
	formatData: function(data) {
		var sHtml = '';
		var statusLabel = '';
		
		sHtml += '<ul class="yx-list recordList">';
		
		$.each(data, function(i, item) {
			// 标签
			if(item.regStatus == 0){
				sHtml += '<li class="lock'; 
			}else if(item.regStatus == 1 ){
				sHtml += '<li class="ok'; 
			}else if(item.regStatus == 3 ){
				sHtml += '<li class="timeOut';
			}else if(item.regStatus == 2 || item.regStatus == 7){
				sHtml += '<li class="cancel';
			}else if(item.regStatus == 5 || item.regStatus == 6 || item.regStatus == 8 || item.regStatus == 9 ){
				sHtml += '<li class="fail';
			}else{
				sHtml += '<li class="fail';
			}
			sHtml += ' flex arrow boxTable" onclick="showOrderInfo(\'' + item.orderNo + '\')">';
			
			// 状态
			if(item.regStatus == 0 && item.payStatus == 1){
				statusLabel = "预约中<br/>(待缴费)";
			}else if(item.regStatus == 1){
				if(item.payStatus == 1 ){
					statusLabel = "预约成功<br/>(未缴费)";
				}else if(item.payStatus == 2){
					statusLabel = "预约成功<br/>(已缴费)";
				}
			}else if(item.regStatus == 3){
				statusLabel = "取消预约<br/>(缴费超时)";
			}else if(item.regStatus == 2){
				if(item.payStatus == 3){
					statusLabel = "取消预约<br/>(已退费)";
				}else if(item.payStatus == 2  || item.payStatus == 5){
					statusLabel = "取消预约<br/>(处理中)";
				}else{
					statusLabel = "取消预约<br/>(未缴费)";
				}
			}else if(item.regStatus == 4){
				if(item.payStatus == 3){
					statusLabel = "停诊<br/>(已退费)";
				}else if(item.payStatus == 2  || item.payStatus == 5){
					statusLabel = "停诊<br/>(处理中)";
				}else{
					statusLabel = "停诊<br/>(未缴费)";
				}
			}else if (item.regStatus == 5 ){
				statusLabel = "网络异常<br/>(处理中)";
			}else if(item.regStatus == 6 ){ 
				statusLabel = "网络异常<br/>(处理中)";
			}else if(item.regStatus == 7 || item.regStatus == 10){
				statusLabel = "网络异常<br/>(处理中)";
			}else if(item.regStatus == 8){
				if(item.payStatus == 3){
					statusLabel = "网络异常<br/>(已退费)";
				}else if(item.payStatus == 2  || item.payStatus == 5){
					statusLabel = "网络异常<br/>(处理中)";
				}else{
					statusLabel = "网络异常<br/>(未缴费)";
				}
			}else if(item.regStatus == 9){
				statusLabel = "预约成功<br/>(已缴费)";
			}else if(item.regStatus == 11){
				statusLabel = "网络异常<br/>(已退费)";
			}else if(item.regStatus == 12){
				if(item.payStatus == 3){
					statusLabel = "挂号失败<br/>(已退费)";
				}else if(item.payStatus == 2  || item.payStatus == 5){
					statusLabel = "挂号失败<br/>(处理中)";
				}else{
					statusLabel = "挂号失败<br/>(未缴费)";
				}
			}else if(item.regStatus == 13){
			   if(item.payStatus == 3){
				   statusLabel = "网络异常<br/>(已退费)";
			   }else if(item.payStatus == 2  || item.payStatus == 5){
				   statusLabel = "网络异常<br/>(处理中)";
			   }else{
				   statusLabel = "网络异常<br/>(未缴费)";
			   }
			}else if(item.regStatus == 14){
				if(item.payStatus == 3){
					statusLabel = "网络异常<br/>(已退费)";
				}else if(item.payStatus == 2  || item.payStatus == 5){
					statusLabel = "网络异常<br/>(处理中)";
				}else{
					statusLabel = "网络异常<br/>(未缴费)";
				}
			}else if(item.regStatus == 15){
				if(item.payStatus == 3){
					statusLabel = "网络异常<br/>(已退费)";
				}else if(item.payStatus == 2  || item.payStatus == 5){
					statusLabel = "网络异常<br/>(处理中)";
				}else{
					statusLabel = "网络异常<br/>(未缴费)";
				}
			}else if(item.regStatus == 22 && item.payStatus == 3 ){
				statusLabel = "用户取消中<br/>(已退费)";
			}else if(item.regStatus == 20){
				statusLabel = "窗口退费<br/>(已退费)";
			}else if(records[i].regStatus == 21){
			    html += "窗口异常(退费中)";
		    }else if(records[i].regStatus == 23){
			    html += "后台取消(已退费)";
		    }else if(records[i].regStatus == 24){
			    html += "后台取消(退费失败)";
		    }
			
	        sHtml += '	<div class="flexItem">';
	        sHtml += '		<div class="name fontSize110">' + item.hospitalName + '</div>';
	        sHtml += '		<div class="mate">' + item.encryptedPatientName + '(' + item.cardNo +') </div>';
	        // sHtml += '		<div class="mate">' + item.hospitalName +'</div>';
	        // sHtml += '		<div class="mate">' + Number((item.realRegFee + item.realTreatFee) / 100).toFixed(2) + '元</div>';
	        sHtml += '		<div class="mate color999">' + ((item.registerDate.length > 10) ? item.registerDate.substring(0, 10) : item.registerDate) + '</div>';
	        sHtml += '	</div>';
	        sHtml += '	<div class="flexItem w120 textRight vertical states">' + statusLabel + '</div>';
	        sHtml += '</li>';
		});
		
		sHtml += '</ul>';
		
		$('#payRecord').html('');
		$('#payRecord').append(sHtml);
		$('.yxw-data').show();
	}
}

$(function() {
	regRecordList.init();
});

function loadData() {
	$('div.yxw-data').hide();
	var url = base.appPath + 'app/usercenter/regRecords/getData';
	$Y.loading.show('正在为您加载数据');
	$.ajax({
		type: 'POST',
		url: url,
		data: {
			openId: $('#openId').val(),
			appCode: $('#appCode').val(),
			areaCode: $('#areaCode').val(),
			hospitalCode: $('#hospitalFilter').val(),
			dateType: $('#dateFilter').val()
		},
		dataType: 'json',
		timeout: 120000,
		error: function(data) {
			console.log(data);
			$Y.loading.hide();
			recordQuery.showNoRecrod();	
		},
		success: function(data) {
			console.log(data);
			$Y.loading.hide();
			if (data.status == 'OK' && data.message && data.message.entityList.length > 0) {
				regRecordList.formatData(data.message.entityList);
			} else {
				recordQuery.showNoRecord();
			}
		}
	})
}

function showOrderInfo(orderNo){
	$("#orderNo").val(orderNo);
	$("#hospitalName").val($('#hospitalFilter').find("option:selected").text());
	$("#voForm").attr("action" , base.appPath + "easyhealth/register/infos/showOrderInfo");
	$("#voForm").submit();
}

function doRefresh() {
	$('#voForm').attr('action', base.appPath + 'app/usercenter/regRecords/list');
	$('#voForm').submit();
}