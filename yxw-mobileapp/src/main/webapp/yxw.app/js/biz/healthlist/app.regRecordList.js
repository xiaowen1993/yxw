function loadData(){
		var url = appPath + "easyhealth/healthlist/regrecord/getRecords";
		var datas = $("#voForm").serializeArray();
		console.log(datas);
		$Y.loading.show('正在为您加载数据');
		$.ajax({
           	type : 'POST',  
           	url : url,  
           	data : datas,  
           	dataType : 'json',  
           	timeout:120000,
           	error: function(data) {
           		$Y.loading.hide();
           		showNoRecord();
           	},
           	success : function(data) {
           		console.log(data);
           		$Y.loading.hide();
           		if (data.status == 'OK' && data.message && data.message.length > 0) {
           			sourceData = data.message;
           			formatData(data.message);
           		} else {
           			showNoRecord();
           		}
           	}
    	})
	}

function formatData(data) {
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
		}else if(item.regStatus == 21){
			statusLabel = "窗口异常<br/>(退费中)";
		}
		
        sHtml += '	<div class="flexItem">';
        sHtml += '		<div class="name fontSize110">' + item.deptName + '－' + item.doctorName + '</div>';
        sHtml += '		<div class="mate">' + item.hospitalName +'</div>';
        sHtml += '		<div class="mate">' + Number((item.realRegFee + item.realTreatFee) / 100).toFixed(2) + '元</div>';
        sHtml += '		<div class="mate color999">' + ((item.registerDate.length > 10) ? item.registerDate.substring(0, 10) : item.registerDate) + '</div>';
        sHtml += '	</div>';
        sHtml += '	<div class="flexItem w120 textRight vertical states">' + statusLabel + '</div>';
        sHtml += '</li>';
	});
	
	sHtml += '</ul>';
	
	$('#records').html('');
	$('#records').append(sHtml);
}

var sourceData = {};

function showOrderInfo(orderNo){
	$("#orderNo").val(orderNo);
	$("#voForm").attr("action" , appPath + "mobileApp/register/infos/showOrderInfo");
	$("#voForm").submit();
}

function showNoRecord() {
	var sHtml = '';
	sHtml += '<div id="success">';
    sHtml += '	<div class="noticeEmpty"></div>';
    sHtml += '	<div class="p color666">找不到您的挂号记录</div>';
    sHtml += '</div>';
	$('#records').html('');
	$('#records').html(sHtml);
}

function filterRecord(obj) {
	$(obj).siblings('.text').text($(obj).find("option:selected").text());
	
	if (sourceData.length > 0) {
		var hospitalValue = $('#hospitalFilter').val();
		var dateValue = $('#dateFilter').val();
		var datas = filterData(hospitalValue, dateValue);
		if (datas.length > 0) {
			formatData(datas);
		} else {
			showNoRecord();
		} 
	} else {
		showNoRecord();
	}
}

function filterData(hospVal, dateVal) {
	var resultData = [];
	
	if (hospVal == '0' && dateVal == '0') {
		resultData = sourceData;
	} else {
		if (hospVal == '0') {
			resultData = filterByDate(dateVal);
		} else if (dateVal == '0') {
			resultData = filterByHosp(hospVal);
		} else {
			var tempDate = new Date();
			var endDate = tempDate.Format('yyyy-MM-dd');
			var beginDate = getBeginDate(tempDate, dateVal);
			
			$.each(sourceData, function(i, item) {
				var regDate = new Date(item.registerTime).Format('yyyy-MM-dd');
				if (regDate >= beginDate && regDate <= endDate && item.hospitalCode == hospVal) {
					resultData.push(item);
				}
			});
		}
	}
	
	return resultData;
}

function filterByHosp(value) {
	var resultData = [];
	$.each(sourceData, function(i, item) {
		if (item.hospitalCode == value) {
			resultData.push(item);
		}
	});
	
	return resultData;
}

function filterByDate(value) {
	var resultData = [];
	var tempDate = new Date();
	var endDate = tempDate.Format('yyyy-MM-dd');
	var beginDate = getBeginDate(tempDate, value);
	$.each(sourceData, function(i, item) {
		var regDate = new Date(item.registerTime).Format('yyyy-MM-dd');
		if (regDate >= beginDate && regDate <= endDate) {
			resultData.push(item);
		}
	});
	
	return resultData;
}

function getBeginDate(tempDate, dateType) {
	var beginDate = '';
	var tDate = new Date(tempDate);
    /*
	1今天
	2近3天
	3近7天
	4近30天
    */
    if (dateType == 1) {
    	beginDate = tDate.Format('yyyy-MM-dd');
    } else if (dateType == 2) {
    	tempDate = tDate.setDate(tempDate.getDate() - 2);
    	beginDate = tDate.Format('yyyy-MM-dd');
    } else if (dateType == 3) {
    	tempDate = tDate.setDate(tempDate.getDate() - 7);
    	beginDate = tDate.Format('yyyy-MM-dd');
    } else {
    	tempDate = tDate.setMonth(tempDate.getMonth() - 1);
    	beginDate = tDate.Format('yyyy-MM-dd');
    }
	return beginDate;
}

Date.prototype.Format = function(fmt) { //author: meizz 
		var o = { 
		"M+" : this.getMonth()+1,                 //月份 
		"d+" : this.getDate(),                    //日 
		"h+" : this.getHours(),                   //小时 
		"m+" : this.getMinutes(),                 //分 
		"s+" : this.getSeconds(),                 //秒 
		"q+" : Math.floor((this.getMonth()+3)/3), //季度 
		"S"  : this.getMilliseconds()             //毫秒 
		}; 
		
		if(/(y+)/.test(fmt)) 
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 

		for(var k in o) 
		if(new RegExp("("+ k +")").test(fmt)) 
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
		
		return fmt; 
}

$(function() {
	loadData();
});