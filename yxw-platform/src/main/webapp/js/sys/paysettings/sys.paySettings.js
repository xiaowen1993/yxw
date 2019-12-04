var paySettings = {};

paySettings.platformPayment = {};

paySettings.init = function() {
	paySettings.loadPlatForm();
};

paySettings.loadPlatForm = function() {
	$.ajax({
		type: "POST",
		url: basePath + "sys/paySettings/loadPlatForm",
		data: {
			"hospitalId": $hospital.id
		},
		cache: false,
		dataType: "json",
		asycn: false,
		error: function(XMLHQ, errorMsg) {
			console.log(errorMsg);
			alert(errorMsg);
		},
		success: function(data, textStatus) {
			if (data.status == 'OK') {
				console.log(data.message.platformPayment);
				paySettings.platformPayment = data.message.platformPayment;
				paySettings.formatDataPlatformAndPayment(paySettings.platformPayment, 0);
			}
		}

	});
};

paySettings.formatDataPlatformAndPayment = function(data, n) {
	console.log(data);
	if (data != null && data.length == 0) {
		alert("平台支付方式缺失，请检查！");
		return;
	}
	var platformTarget = '';
	$.each(data, function(i, val) {
		var platformLi = [
			'<li><a href="javascript:void(0);" id="' + i + '" class="' + (i == n ? "select" : "") + '" platformSettingsId="' + val.platformSettingsId + '" style="width: 150px;">' + val.platformName + '</a></li>'
		];
		platformTarget += platformLi.join("");

		if (i == n) {
			if (val.payModes != null && val.payModes.length > 0) {
				var paymentTarget = '';
				$.each(val.payModes, function(k, v) {
					var paymentLi = [
						'<li index="'+k+'" platformSettingsId="'+val.platformSettingsId+'" payModeId="' + v.id + '" platformCode="'+val.platformCode+'" payModeCode="'+v.code+'" >',
						'<a href="javascript:void(0);">',
						'<label>',
						'<i class="checkbox-opt"></i>',
						'<span class="text">' + v.name + '</span>',
						'</label>',
						'</a>',
						'</li>'
					];
					paymentTarget += paymentLi.join("");
				});

				$('#payment').html(paymentTarget);
				
			}
		}

	});
	$('#platform').html(platformTarget);

	paySettings.bindEvent();
	
	$('#paySettings').html("");
	
	paySettings.formatPaySettings();
	
	$('#payment li').trigger("click");
	
	setTimeout('paySettings.initFormatPaySettings()', 200);
};

paySettings.bindEvent = function() {
	$('#platform li a').click(function(event) {
		event.stopPropagation();
		event.preventDefault();
		var id = $(this).attr('id');
		if (!$(this).is('.select')) {
			paySettings.formatDataPlatformAndPayment(paySettings.platformPayment, id);
		}
	});

	$('#payment li').click(function(event){
		event.stopPropagation();
		event.preventDefault();
		
		$hospital.platform.switchPlatFormPaySettingsTab(this);
	});
	
};

paySettings.initFormatPaySettings = function(){
	$.each($('#paySettings div[index]'), function(i, v) {
		var index = $(v).attr("index");
		$(v).hide();
		$('#payment li[index="' + index + '"]').removeClass("active");
		if ($(v).find('div.controls input:first').val()) {
			$(v).show();
			$('#payment li[index="' + index + '"]').addClass("active");
		}
	});
};

paySettings.formatPaySettings = function(){
	$.each($('#payment li'), function(i, val){
		var index = $(val).attr("index");
		var payModeId = $(val).attr('payModeId'), platformSettingsId = $(val).attr("platformSettingsId"), payModeCode = $(val).attr('payModeCode'), platformCode = $(val).attr('platformCode');
		
		var paySettingsHtml = [
		                       	'<div class="space30"></div>',
								'<div index="'+index+'" id="'+platformCode+'_'+payModeCode+'_input" style="display: none;">',
								'</div>'
								];
		
		$('#paySettings').append(paySettingsHtml.join(""));
	});
};

paySettings.loadPlatformPaySettings = function(platformSettingsId, payModeId, platformCode, payModeCode){
	$.ajax({
		type: "POST",
		url: basePath + "sys/paySettings/loadPlatformPaySettings",
		data: {
			"platformSettingsId": platformSettingsId,
			"payModeId": payModeId
		},
		cache: false,
		dataType: "json",
		asycn: false,
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			console.log(errorMsg);
			alert(errorMsg);
		},
		success: function(data, textStatus) {
			if (data.status == 'OK') {
				var params = {"platformSettingsId": platformSettingsId,"payModeId": payModeId, "platformCode":platformCode, "payModeCode":payModeCode};
				paySettings.formatDataPaySettings(params, data.message.paySettings);
			}
		}

	});
};

paySettings.formatDataPaySettings = function(params, data){
	if (params.platformCode == 'innerUnionPay' && params.payModeCode == 'alipay') {//暂时测试
		formatPaySettings.format1(params, data);
	}else if (params.platformCode == 'innerUnionPay' && params.payModeCode == 'wechat') {//暂时测试
		formatPaySettings.format2(params, data);
	}else if (params.platformCode == 'innerUnionPay' && params.payModeCode == 'unionpay') {//银联支付（内嵌银联钱包app）
		formatPaySettings.format3(params, data);
	}else if (params.platformCode == 'innerUnionPay' && params.payModeCode == 'insurance') {//商保支付
		formatPaySettings.format4(params, data);
	}else if (params.platformCode == 'app' && params.payModeCode == 'unionpay') {//暂时测试
		formatPaySettings.format5(params, data);
	}else if (params.platformCode == 'autoService' && params.payModeCode == 'wechatnative') {//自助机（微信扫码付）
		formatPaySettings.format6(params, data);
	}else if (params.platformCode == 'autoService' && params.payModeCode == 'alipaynative') {//自助机（支付宝扫码付）
		formatPaySettings.format7(params, data);
	}else if (params.platformCode == 'autoService' && params.payModeCode == 'wechatmicro') {//自助机（微信条码付）
		formatPaySettings.format8(params, data);
	}else if (params.platformCode == 'autoService' && params.payModeCode == 'alipaymicro') {//自助机（支付宝条码付）
		formatPaySettings.format9(params, data);
	}else if (params.platformCode == 'autoService' && params.payModeCode == 'unionpaynative') {//自助机（银联钱包扫码付）
		formatPaySettings.format3(params, data);
	}else if (params.platformCode == 'innerWechat' && params.payModeCode == 'wechat') {//微信支付（内嵌微信）
		formatPaySettings.format10(params, data);
	}else if (params.platformCode == 'innerAlipay' && params.payModeCode == 'alipay') {//微信支付（内嵌支付宝）
		formatPaySettings.format7(params, data);
	}else if (params.platformCode == 'innerChinaLife' && params.payModeCode == 'alipaymobileweb') {//微信支付（内嵌微信）
		formatPaySettings.format11(params, data);
	}else if (params.platformCode == 'innerChinaLife' && params.payModeCode == 'wechat') {//微信支付（内嵌微信）
		formatPaySettings.format10(params, data);
	}
	
};

$(function() {
	paySettings.init();
});

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};