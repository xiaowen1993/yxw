var bindCard = {
	init: function() {
		$('#yx-select-card-id-val').off('change').on('change', function() {
			bindCard.getIndex();
		});
		
		bindCard.getIndex();

		$('.btn-ok').off('click').on('click', function() {
			bindCard.bind();
		});
		
		$('#edtMobile').off('blur').on('blur', function() {
			bindCard.mobileBlur($(this));
		});
		
		$('#edtMobile').off('focus').on('focus', function() {
			$(this).val('');
		});
	},
	mobileBlur: function(obj) {
		var mobile = $(obj).val();

		if (mobile) {
			var reg = /^1\d{10}$/; // 1开头的号码
			if (reg.exec(mobile)) {
				$('#mobile').val(mobile);
			} else {
				// 弹框提示
				$Y.loading.hide();
				$Y.tips('您输入的手机号码不正确。');
				$('#mobile').val('');
			}
		}
	},
	getIndex: function() {
		var selectIndex = $('#yx-select-card-id-val')[0].selectedIndex;
		bindCard.selectedShow($('#yx-select-card-id-val'), selectIndex)

		if (selectIndex == 0) {
			$('.yx-list li[needHideIndex="0"]').hide();
		} else {
			$('.yx-list li[needHideIndex="0"]').show();
		}
	},
	selectedShow: function(obj, selectIndex) {
		var mySelect = $(obj);
		var mySelectHtml = mySelect.find('option').eq(selectIndex).html();
		$('.patCardType span').html(mySelectHtml + '<i class="iconfont">&#xe600;</i>')
	},
	bind: function() {
		// 测试
		// window.location.href = base.appPath + $('#forward').val() + '?openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode;
		// return false;
		
		var patCardNo = $('#patCardNo').val();
		if (!patCardNo) {
			$Y.tips('请输入您的就诊卡号');
			return false;
		}

		$Y.loading.show('正在为您绑定就诊卡...');
		$('#cardNo').val(patCardNo);
		$('#cardType').val($('#yx-select-card-id-val').val());
		var datas = $('#voForm').serializeArray();
		console.log(datas);

		$.ajax({
			type: "POST",
			url: base.appPath + "app/usercenter/medicalcard/bindCard",
			data: datas,
			cache: false,
			dataType: "json",
			timeout: 7900,
			error: function(data) {
				$Y.loading.hide();
				myBox = new $Y.confirm({
					title: "",
					content: "<div style='text-align: center;'>绑卡失败</div>",
					ok: {
						title: "确定",
						click: function() {
							myBox.close();
						}
					}
				});
				return;
			},
			success: function(data) {
				$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK' && data.message && data.message.isSuccess == 'success') {
					var forward = $('#forward').val();
					if (forward) {
						// $('#voForm').attr('action', base.appPath + forward);
						// $('#voForm').submit(); 
						
						// forward使用get请求，参数在里面了，在拼上三件套即可 -- 去绑卡的地方，都有传入familyId，也就是至少有一个参数
//						window.location.href = base.appPath + forward + '&openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode;
						window.location.href = base.appPath + forward;
					} else {
						// 成功， 弹框提示。 完成后调用壳的方法关闭页面，会自动刷新页面
						myBox = new $Y.confirm({
							title: "",
							content: "<div style='text-align: center;'>绑定成功</div>",
							ok: {
								title: "我知道了",
								click: function() {
									// 调用壳的方法关闭
									myBox.close();

									// 延迟1~2秒，跳回同步就诊卡页面
									setTimeout(function() {
										$('#voForm').attr('action', base.appPath + 'app/usercenter/syncCard/index');
										$('#voForm').submit();
									}, 200);
								}
							}
						});
					}

					return;
				} else {
					// 失败，弹框提示
					myBox = new $Y.confirm({
						title: "",
						content: "<div style='text-align: center;'>" + data.message.msgInfo ? data.message.msgInfo
								: "绑卡失败" + "</div>",
						ok: {
							title: "确定",
							click: function() {
								myBox.close();
							}
						}
					});
					return;
				}
			}
		});
	}
}

$(function() {
	bindCard.init();
});

//刷新方法
function doRefresh() {
	$('#voForm').attr('action', base.appPath + 'app/usercenter/medicalcard/index');
	$('#voForm').submit();
}