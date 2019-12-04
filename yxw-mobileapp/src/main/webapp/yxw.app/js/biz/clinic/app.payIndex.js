var clinicIndex = {
	init: function() {
		/*if (!$('#visitWay').val()) {
			var defaultCardNo = $('#cardNo'.val());
			if (defaultCardNo) {
				clinicIndex.visitWay = 1;
			} 
			
			$('#visitWay').val(clinicIndex.visitWay);
		}*/
		var appId = $("#hospitalFilter option:selected").attr("data-appid");
		if (appId) {
			$('#appId').val( appId );
		}
	},
	formatData: function(data) {
		console.log(data);
    	var sHtml = '';
    	$.each(data, function(i, item) {
			sHtml += '<li' + 
					 ' data-branchCode="' + stringUtils.getValue(item.branchCode) + '"' +
					 ' data-doctor="' + stringUtils.getValue(item.doctorName) + '"' +
					 ' data-time="' + stringUtils.getValue(item.time) + '"' +
					 ' data-id="' + stringUtils.getValue(item.mzFeeId) + '"' +
					 ' data-dept="' + stringUtils.getValue(item.deptName) + '"' +
					 ' data-pay="' + stringUtils.getValue(item.payAmout) + '"' +
					 ' data-total="' + stringUtils.getValue(item.totalAmout) + '"' +
					 ' data-medicare="' + stringUtils.getValue(item.medicareAmout) + '"' +
					 ' data-type="' + stringUtils.getValue(item.payType) + '">';
            sHtml += '	<div class="boxTable flex arrow-s">';
            sHtml += '		<div class="flexItem">';
            if (item.deptName && item.doctorName) {
            	sHtml += '			<div class="name fontSize110">' + item.deptName + '-' + item.doctorName + '</div>';
            } else {
            	sHtml += '			<div class="name fontSize110">门诊缴费</div>';
            }
            // sHtml += '			<div class="mate">' + item.hospitalName + '</div>';
            sHtml += '			<div class="mate">' + Number(item.totalAmout / 100).toFixed(2) + '元</div>';
            sHtml += '			<div class="mate color999">' + stringUtils.getValue(item.time) + '</div>';
            sHtml += '		</div>';
            sHtml += '		<div class="color999 flexItem w120 textRight vertical">&nbsp;</div>';
            sHtml += '	</div>';
            sHtml += '	<div class="space5"></div>';
            sHtml += '	<div class="opt-view">去缴费</div>';
            sHtml += '</li>';
    	});
    	
    	$('#paymentList').html('').append(sHtml);
    	$('.yxw-data').show();
    	
    	clinicIndex.bindEventAfterComplete();
    },
    bindEventAfterComplete: function() {
    	$('div.yxw-data>ul.yx-list>li').off('click').on('click', function() {
    		clinicIndex.showDetail($(this));
    	});
    },
    showDetail: function(obj) {
     	// 具体的就诊卡信息
    	$("#hospitalCode").val($('#hospital').attr('data-id'));
    	$("#hospitalName").val($('#hospital').text());
    	$("#branchHospitalCode").val($(obj).attr('data-branchCode'));
    	
    	// 展示信息
    	$("#doctorName").val($(obj).attr('data-doctor'));
		$("#mzFeeId").val($(obj).attr('data-id'));
		$("#deptName").val($(obj).attr('data-dept'));
		$("#totalFee").val(Number($(obj).attr('data-total')));
		$("#payFee").val(Number($(obj).attr('data-pay')));
		$("#medicareFee").val(Number($(obj).attr('data-medicare')));
		$("#medicareType").val($(obj).attr('data-type'));
		
		// mobileApp/clinic/payDetail -- pre href
		$("#voForm").attr("action" , base.appPath + 'app/clinic/detail/index');
   		$("#voForm").submit();
    },
    initTips: function(tips) {
    	$('#preTips').html('').append('<i class="iconfont">&#xe60d;</i>' + tips).show();
    }
}

$(function(){
	clinicIndex.init();
});

function loadData() {
	$('.yxw-data').hide();
	$('#preTips').hide();
	
	$Y.loading.show('正在加载数据...');
	$.ajax({
		type: "POST",
		url: base.appPath + "app/clinic/pay/getDatas",
		data: $('#voForm').serializeArray(),
		cache: false, 
		dataType: "json", 
		timeout: 600000,
		error: function(data) {
			$Y.loading.hide();
			console.log('加载待缴费数据异常');
			bizQuery.showNoRecord();
		},
		success: function(data) {
			$Y.loading.hide();
			if (data.status == 'OK' && data.message) {
				if (data.message.entities && data.message.entities.length > 0) {
					clinicIndex.formatData(data.message.entities);
				} else {
					bizQuery.showNoRecord();
				}
				
				if (data.message.ruleConfig) {
					clinicIndex.initTips(data.message.ruleConfig);
				}
			} else {
				bizQuery.showNoRecord();
			}
		}
	})
}

function doRefresh() {
	var url = base.appPath + "app/clinic/pay/index";
	if (visitWay == 1) {
		url = base.appPath + "app/clinic/pay/msgIndex";
	} 
	
	$('#voForm').attr('action', url);
	$('#voForm').submit();
}