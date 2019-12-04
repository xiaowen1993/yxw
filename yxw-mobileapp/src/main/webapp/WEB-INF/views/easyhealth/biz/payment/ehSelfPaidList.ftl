<!DOCTYPE html>
<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
  	<#include "/easyhealth/common/common.ftl">
    <title>处方记录</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div class="screeningBox">
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem">
                    <label>
                    	<span class="text">全部医院</span>
                        <select id="hospitalFilter" name="hospitalFilter" class="select-screen-box" onchange="filterRecord(this)">
                            <option value="0">全部医院</option>
                            <#list entityList as item>
                            	<option value="${item.hospitalCode}">${item.hospitalName}</option>
                            </#list>
                        </select>
                        <i class="iconfont">&#xe66d;</i>
                    </label>
                </div>
                <div class="flexItem">
                    <label>
                    	<span class="text">全部日期</span>
                        <select id="dateFilter" name="dateFilter" class="select-screen-box"  onchange="filterRecord(this)">
                            <option value="0">全部日期</option>
                            <option value="1">今天</option>
                            <option value="2">近3天</option>
                            <option value="3">近7天</option>
                            <option value="4">近30天</option>
                        </select>
                        <i class="iconfont">&#xe66d;</i>
                    </label>
                </div>
            </li>
        </ul>
    </div>
    <div class="space15"></div>
    <div id="payRecord">

    </div>
</div>
<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
	<input type="hidden" id="appCode" name="appCode" value="${appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="">
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="maxMonths" name="maxMonths" value="" />
	<input type="hidden" id="clinicStatus" name="clinicStatus" value="" />
	<input type="hidden" id="cardType" name="cardType" value="" />
	<input type="hidden" id="cardNo" name="cardNo" value="" />
	<input type="hidden" id="patientName" name="patientName" value="" />
	<input type="hidden" id="mzFeeId" name="mzFeeId" value="" />
	<input type="hidden" id="totalFee" name="totalFee" value="" />
	<input type="hidden" id="medicareFee" name="medicareFee" value="" />
	<input type="hidden" id="payFee" name="payFee" value="" />
	<input type="hidden" id="medicareType" name="medicareType" value="" />
	<input type="hidden" id="hisOrdNum" name="hisOrdNum" value="" />
	<input type="hidden" id="receiptNum" name="receiptNum" value="" />
	<input type="hidden" id="hisMessage" name="hisMessage" value="" />
	<input type="hidden" id="barcode" name="barcode" value="" />
	<input type="hidden" id="orderNo" name="orderNo" value="" />
</form>
<input type="hidden" id="paymentRecordQueryType" name="paymentRecordQueryType" value="" />
<script type="text/javascript">
	var sourceData = {};
	$(function() {
		loadData();
	});
	
	function loadData(){
		var url = '${basePath}easyhealth/healthlist/payment/getSelfClinicPaidListForEasyHealth';
		var datas = $("#voForm").serializeArray();
		console.log(datas);
		$Y.loading.show('正在为您加载数据');
		$.ajax({
           	type: 'POST',  
           	url: url,  
           	data: datas,  
           	dataType: 'json',  
           	timeout: 120000,
           	error: function(data) {
           		$Y.loading.hide();
           		showNoData();
           	},
           	success : function(data) {
           		console.log(data);
           		$Y.loading.hide();
           		if (data.status == 'OK' && data.message && data.message.list.length > 0) {
           			sourceData = data.message.list;
           			formatData(data.message.list);
           		} else {
           			showNoData();
           		}
           	}
    	})
	}
	
	function formatData(data) {
		var sHtml = '';
		
		sHtml += '<ul class="yx-list">';
		$.each(data, function(i, item) {
			sHtml += '<li class="detail-item ';
			if (item.statusLabel == '缴费成功' || item.statusLabel == '部分退费') {
				sHtml += 'arrow';
			}
			sHtml += ' boxTable flex" data-id="' + item.mzFeeId + 
				           '" data-total="' + item.totalFee + 
				           '" data-hospitalId="' + item.hospitalId + 
				           '" data-hospitalCode="' + item.hospitalCode + 
				           '" data-hospitalName="' + item.hospitalName + 
				           '" data-cardType="' + item.cardType + 
				           '" data-cardNo="' + item.cardNo + 
				           '" data-patientName="' + item.patientName + 
				           '" data-medicare="' + item.medicareFee + 
				           '" data-pay="' + item.payFee + 
				           '" data-type="' + item.medicareType + 
				           '" data-hisOrdNum="' + item.hisOrdNo +
				           '" data-receiptNum="' + item.receiptNum + 
				           '" data-branchHospitalCode="' + item.branchHospitalCode + 
				           '" data-barcode="' + item.barcode + 
				           '" data-status="' + item.clinicStatus + 
				           '" data-orderNo="' + item.orderNo + 
				           '" data-cardNo="' + item.cardNo + 
				           '" data-cardType="' + item.cardType + 
				           '" data-patientName="' + item.patientName + 
				           '" data-hisMessage="' + encodeURIComponent(item.hisMessage) + '">';
            sHtml += '	<div class="flexItem">';
            sHtml += '		<div class="name fontSize120">' + item.recordTitle + '－' + item.patientName + '</div>';
            sHtml += '		<div class="mate">' + item.hospitalName + '</div>';
            sHtml += '		<div class="mate">' + Number(item.payFee / 100).toFixed(2) + '元</div>';
            sHtml += '		<div class="time color999">' + item.payDate + '</div>';
            sHtml += '	</div>';
            sHtml += '	<div class="color999 flexItem w100 textRight vertical">';
            if (item.statusLabel == '缴费成功' || item.statusLabel == '部分退费') {
             	sHtml += '	<div class="status color666">' + item.statusLabel;
            } else {
            	if (item.statusLabel == '缴费失败') {
            		sHtml += '	<div class="status red">' + item.statusLabel;
            	} else {
            		sHtml += '	<div class="status color666">' + item.statusLabel;
            	}
            }
            sHtml += '	</div>';
            sHtml += '</li>'
		});
		sHtml += '</ul>';
		
		$('#payRecord').html('');
		$('#payRecord').append(sHtml);
		$('#payRecord').show();
		
		bindClinicEvent();
	}
	
	function bindClinicEvent() {
		$('.detail-item').off('click').on('click', function(event) {
			event.stopPropagation();
	        event.preventDefault();
	        
	        showPaidDetail(this);
		});
	}
	
	function showPaidDetail(obj) {
		if (obj && ($(obj).attr('data-status') == '1' || $(obj).attr('data-status') == '30')) {
			$('#hospitalId').val(getValue($(obj).attr('data-hospitalId')));
			$('#hospitalCode').val(getValue($(obj).attr('data-hospitalCode')));
			$('#hospitalName').val(getValue($(obj).attr('data-hospitalName')));
			$('#cardNo').val(getValue($(obj).attr('data-cardNo')));
			$('#cardType').val(getValue($(obj).attr('data-cardType')));
			$('#patientName').val(getValue($(obj).attr('data-patientName')));
			$('#mzFeeId').val(getValue($(obj).attr('data-id')));
			$('#payFee').val(getValue($(obj).attr('data-pay')));
			$('#totalFee').val(getValue($(obj).attr('data-total')));
			$('#medicareFee').val(getValue($(obj).attr('data-medicare')));
			$('#medicareType').val(getValue($(obj).attr('data-type')));
			$('#hisOrdNum').val(getValue($(obj).attr('data-hisOrdNum')));
			$('#orderNo').val(getValue($(obj).attr('data-orderNo')));
			$('#clinicStatus').val(getValue($(obj).attr('data-status')));
			$('#receiptNum').val(getValue($(obj).attr('data-receiptNum')));
			$('#hisMessage').val(getValue($(obj).attr('data-hisMessage')));
			$('#barcode').val(getValue($(obj).attr('data-barcode')));
			$('#branchHospitalCode').val(getValue($(obj).attr('data-branchHospitalCode')));
			$('#voForm').attr('action', '${basePath}mobileApp/clinic/paidDetail');
			$('#voForm').submit();
		}
	}
	
	function showNoData() {
		var sHtml = '';
		
		sHtml += '<div class="noRecord">';
        sHtml += '	<div id="success">';
        sHtml += '		<div class="noticeEmpty"></div>';
        sHtml += '      <div class="p color666">没有任何处方记录</div>';
        sHtml += '  </div>';
        sHtml += '</div>';
        
		$('#payRecord').html('');
		$('#payRecord').append(sHtml);
	}
	
	function getValue(data) {
		if (!data || data == 'null') {
			data = '';
		}
		return data;
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
				showNoData();
			} 
		} else {
			showNoData();
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
					var regDate = new Date(item.createTime).Format('yyyy-MM-dd');
					if (regDate >= beginDate && regDate <= endDate &&　item.hospitalCode == hospVal) {
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
			var regDate = new Date(item.createTime).Format('yyyy-MM-dd');
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
</script>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>