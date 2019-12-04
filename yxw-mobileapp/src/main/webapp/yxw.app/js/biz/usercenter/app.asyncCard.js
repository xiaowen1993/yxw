var asyncCard = {
	enableShellButtonEvent: true,
	bindEvent: function() {
		$('.asyncCard').off('click').on('click', function() {
			asyncCard.syncCard($(this));
		});

		$('.manualBind').off('click').on('click', function() {
			asyncCard.manualBind($(this));
		});

		$('#complete').off('click').on('click', function() {
			asyncCard.complete();
		});
	},
	init: function() {
		asyncCard.bindEvent();
	},
	syncCard: function(obj) {
		asyncCard.enableShellButtonEvent = false;
		$(obj).attr('disabled', true);
		$(obj).hide();
		var tipsElement = $(obj).parent().find('.syncTips');
		tipsElement.show();
		tipsElement.removeClass('red');
		tipsElement.addClass('color999');

		var parentObj = $(obj).parent();
		$('#hospitalId').val($(parentObj).attr('hospitalId'));
		$('#hospitalCode').val($(parentObj).attr('hospitalCode'));
		$('#hospitalName').val($(parentObj).attr('hospitalName'));
		$('#appId').val($(parentObj).attr('appId'));

		var datas = $('#voForm').serializeArray();
		console.log(datas);

		$.ajax({
			type: "POST",
			url: base.appPath + "app/usercenter/syncCard/syncMedicalcard",
			data: datas,
			cache: false,
			dataType: "json",
			timeout: 30000,
			error: function(data) {
				asyncCard.enableShellButtonEvent = true;
				new $Y.confirm({
					ok: {
						title: '确定'
					},
					content: '网络超时，请稍后在我的家人查看绑定结果。'
				});
				$(obj).attr('disabled', false);

				tipsElement.removeClass('color999');
				tipsElement.addClass('red');
				tipsElement.text('关联失败');
				$(obj).hide();
				$(obj).siblings('.btn-async').show();
				// $(obj).show();
			},
			success: function(data) {
				console.log(data);
				asyncCard.enableShellButtonEvent = true;
				if (data.status == 'OK') {
					var isSuccess = data.message.isSuccess;
					if (isSuccess == 'success') {
						tipsElement.removeClass('color999');
						tipsElement.addClass('skinColor');
						tipsElement.addClass('syncedTips');
						tipsElement.text('已关联');
					} else {
						// 失败
						tipsElement.removeClass('color999');
						tipsElement.addClass('red');
						tipsElement.text('关联失败');
						$(obj).hide();
						$(obj).siblings('.btn-async').show();
						// $(obj).show();
					}
				} else if (data.status == 'PROMPT') {
					// 失败。且有提示 仅在卡已被绑定的情况下出现。则显示不能关联
					tipsElement.removeClass('color999');
					tipsElement.addClass('red');
					tipsElement.text('已被关联');
					$(obj).hide();
					
					// 弹框提示
					var myBox = new $Y.confirm({
						content: '在' + $('#hospitalName').val() + '的卡已被其他账号绑定（同一个就诊卡只能被一个账号绑定）。',
						ok: {
							title: "确定",
							click: function() {
								myBox.close();
							}
						}
					});
				} else {
					// 失败
					tipsElement.removeClass('color999');
					tipsElement.addClass('red');
					tipsElement.text('关联失败');
					$(obj).hide();
					$(obj).siblings('.btn-async').show();
					// $(obj).show();
				}
			}
		});
	},
	manualBind: function(obj) {
		$(obj).attr('disabled', true);
		$(obj).hide();
		var tipsElement = $(obj).parent().find('.syncTips');
		tipsElement.show();
		tipsElement.removeClass('red');
		tipsElement.addClass('color999');

		var parentObj = $(obj).parent();
		$('#hospitalId').val($(parentObj).attr('hospitalId'));
		$('#hospitalCode').val($(parentObj).attr('hospitalCode'));
		$('#hospitalName').val($(parentObj).attr('hospitalName'));
		$('#appId').val($(parentObj).attr('appId'));

		// 使用goUrl的方式过去，绑定后，跳回这个页面，并自动刷新。
//		var url = base.appPath + 'app/usercenter/medicalcard/index' + '?appId=' + $('#appId').val() + '&appCode='
//				+ $('#appCode').val() + '&openId=' + $('#openId').val() + '&areaCode=' + $('#areaCode').val()
//				+ '&familyId=' + $('#familyId').val() + '&hospitalId=' + $('#hospitalId').val() + '&syncType='
//				+ $('#syncType').val() + '&hospitalCode=' + $('#hospitalCode').val();
//		window.location.href = url;
		
		$('#voForm').attr('action', base.appPath + 'app/usercenter/medicalcard/index');
		$('#voForm').submit();
	},
	viewMyCards: function() {
		console.log('查看本人就诊卡');
		$('#voForm').attr('action', base.appPath + 'app/usercenter/familyInfo/index');
		$('#voForm').submit();
	},
	complete: function() {
		var callbackUrl = $('#forward').val();
		if (!callbackUrl || callbackUrl == 'null') {
			$('#voForm').attr('action', base.appPath + 'app/usercenter/familyInfo/index');
			$('#voForm').submit();
		} else {
			var w_index =  callbackUrl.indexOf("?");
			if (w_index != -1) {
				callbackUrl = callbackUrl.substring(0, w_index);
			}
			$('#voForm').attr('action', base.appPath + callbackUrl);
			$('#voForm').submit();
		}
	}
}

$(function() {
	asyncCard.init();
});

function doRefresh() {
	if (asyncCard.enableShellButtonEvent) {
		$('#voForm').attr('action', base.appPath + 'app/usercenter/syncCard/index');
		$('#voForm').submit();
	}
}