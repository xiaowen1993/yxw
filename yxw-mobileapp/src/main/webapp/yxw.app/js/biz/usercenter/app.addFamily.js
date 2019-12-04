var addFamily = {
	codeType: "addFamily",
	init: function() {
		// 日历初始化
		new yxCalendar({
			dom: '.js-date'
		});

		// 关系
		$('.select-relationship').off('change').on('change', function() {
			addFamily.selectScreen(this);
			addFamily.selectPage(this);
		});

		// 证件类型
		$('.select-idType').off('change').on('change', function() {
			addFamily.selectScreen(this);
			addFamily.changeBirth(this);
		});

		// 性别
		$('.select-sex').off('change').on('change', function() {
			addFamily.selectScreen(this);
		});

		addFamily.selectPage($('.select-screen-box').eq(0))

		// 姓名脱敏
		$('.patName').blur(function() {
			addFamily.patNameBlur($(this));
		});

		// 证件号码脱敏
		$('.patIdNo').blur(function() {
			addFamily.idNoBlur($(this));
		});

		// 手机号码脱敏
		$('.patMobile').blur(function() {
			addFamily.mobileBlur($(this));
		});

		// 获取焦点时清空姓名
		$('.patName').focus(function() {
			$(this).val('');
		});

		// 获取焦点时清空证件号码
		$('.patIdNo').focus(function() {
			$(this).val('');
		});

		// 获取焦点时清空手机号码
		$('.patMobile').focus(function() {
			$(this).val('');
		});

		// 按钮绑定事件
		$('#addParent').off('click').on('click', function() {
			addFamily.bindParent($(this));
		});

		$('#addPartner').off('click').on('click', function() {
			addFamily.bindPartner($(this));
		});

		$('#addChild').off('click').on('click', function() {
			addFamily.bindChild($(this));
		});

		$('#addSibling').off('click').on('click', function() {
			addFamily.bindSibling($(this));
		});

		$('#addOther').off('click').on('click', function() {
			addFamily.bindOther($(this));
		});
	},
	selectScreen: function(obj) {
		var option = obj.children[obj.selectedIndex];
		var html = option.innerHTML;
		obj.previousElementSibling.innerHTML = html;
	},
	selectPage: function(obj) {
		var index = $(obj)[0].selectedIndex || 0;
		$('.familyBox').hide().eq(index).show();
	},
	changeBirth: function(obj) {
		var index = $(obj)[0].selectedIndex;
		if (index == 0) {
			console.log(index);
			$(obj).closest('ul').eq(0).find('li.birth').hide();
			$(obj).closest('ul').eq(0).find('li.sex_w').hide();
		} else {
			$(obj).closest('ul').eq(0).find('li.birth').show();
			$(obj).closest('ul').eq(0).find('li.sex_w').show();
		}
	},
	patNameBlur: function(obj) {
		var name = $(obj).val();
		var formatName = addFamily.getFormatName(obj);

		if (name) {
			if (addFamily.validateName(name)) {
				$('#r' + formatName).val(name);
			} else {
				showIncorrectTip('姓名');
				$('#r' + formatName).val('');
			}
		}
	},
	mobileBlur: function(obj) {
		var mobile = $(obj).val();
		var formatName = addFamily.getFormatName(obj);

		if (mobile) {
			if (addFamily.validateMobile(mobile)) {
				$('#r' + formatName).val(mobile);
			} else {
				showIncorrectTip('手机号');
				$('#r' + formatName).val('');
			}
		}
	},
	idNoBlur: function(obj) {
		var idNo = $(obj).val();
		var formatName = addFamily.getFormatName(obj);

		if (idNo) {
			if ($(obj).attr('id') == 'childIdNo') {
				// 儿童的身份证（非必填） 不需要带出性别，身份证
				if (addFamily.validateIdNo(idNo, 1)) {
					$('#r' + formatName).val(idNo);
				} else {
					addFamily.showIncorrectTip('证件号码');
					$('#r' + formatName).val('');
				}
			} else {
				// 本人、他人、监护证件类型（有证件类型选择框的）
				var type = $(obj).parent().parent().parent().find('.select-idType').val();
				console.log('idType=' + type);
				if (addFamily.validateIdNo(idNo, Number(type))) {
					$('#r' + formatName).val(idNo);

					// 本人和他人的时候，并且证件类型时1的时候
					if (Number(type) == 1 && $(obj).attr('id') != 'guardIdNo') {
						var sex = Number(idCardUtils.getGender(idNo));
						console.log('性别：' + sex);
						var birth = idCardUtils.getBirth(idNo);
						console.log('生日：' + birth);
						var age = idCardUtils.getAge(birth);
						console.log('年龄：' + age);

						var userType = $('select[name="ownership"]').val();
						if (userType == '4') {
							// 父母 parent
							$('#parentBirth').val(birth);
							var opt = $('#parentSex option[value="' + sex + '"]');
							opt.attr('selected', 'true');
							$('#parentSex').parent().find('.text').text(opt.text());
						} else if (userType == '6') {
							// 伴侣 partner
							$('#partnerBirth').val(birth);
							var opt = $('#partnerSex option[value="' + sex + '"]');
							opt.attr('selected', 'true');
							$('#partnerSex').parent().find('.text').text(opt.text());
						} else if (userType == '5') {
							// 兄弟姐妹 sibling
							$('#siblingBirth').val(birth);
							var opt = $('#siblingSex option[value="' + sex + '"]');
							opt.attr('selected', 'true');
							$('#siblingSex').parent().find('.text').text(opt.text());
						} else if (userType == '2') {
							// 其他 other
							$('#otherBirth').val(birth);
							var opt = $('#otherSex option[value="' + sex + '"]');
							opt.attr('selected', 'true');
							$('#otherSex').parent().find('.text').text(opt.text());
						}
					}
				} else {
					addFamily.showIncorrectTip('证件号码');
					$('#r' + formatName).val('');
				}
			}
		}
	},
	// 转换函数，将对象Id的首字母变成大写
	getFormatName: function(obj, i) {
		var id = $(obj).attr('id');
		return id.substring(0, 1).toUpperCase() + id.substring(1, id.length);
	},
	// 验证姓名
	validateName: function(data) {
		var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]{1,30}$/; // 30位大写，小写，数字，中文
		return reg.exec(data);
	},
	// 验证身份证
	validateIdNo: function(data, type) {
		// 1：二代身份证 2：港澳居民身份证 3：台湾居民身份证 4：护照
		var result = false;
		switch (type) {
		case 1:
			result = idCardUtils.validateIdNo(data);
			break;
		case 2:
			result = true;
			break;
		case 3:
			result = true;
			break;
		case 4:
			result = true;
			break;
		}

		return result;
	},
	// 验证手机号码
	validateMobile: function(data) {
		var reg = /^1\d{10}$/; // 1开头的号码
		return reg.exec(data);
	},
	// 信息不正确提示
	showIncorrectTip: function(data) {
		$Y.loading.hide();
		var tip = $('#inputIncorrectTip').val();
		if (!tip || tip.indexOf('%replace%') < 0) {
			tip = '您输入的%replace%不正确';
		}
		$Y.tips(tip.replace('%replace%', data));
	},
	// 信息不完整提示
	showIncompleteTip: function(data) {
		$Y.loading.hide();
		var tip = $('#inputIncorrectTip').val();
		if (!tip || tip.indexOf('%replace%') < 0) {
			tip = '%replace%不能为空';
		}
		$Y.tips(tip.replace('%replace%', data));
	},
	bindParent: function(obj) {
		// 测试
		// window.location.href = base.appPath + $('#forward').val() + '?openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode;
		// return false;
		
		$(obj).attr('disabled', true);
		$Y.loading.show('正在为您进行绑定家人信息');

		if (!$('input[name="rParentName"]').val()) {
			if ($('#parentName').val()) {
				addFamily.showIncorrectTip('姓名');
			} else {
				addFamily.showIncompleteTip('姓名');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rParentIdNo"]').val()) {
			if ($('#parentIdNo').val()) {
				addFamily.showIncorrectTip('证件号码');
			} else {
				addFamily.showIncompleteTip('证件号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#parentBirth').val()) {
			addFamily.showIncompleteTip('出生日期');
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rParentMobile"]').val()) {
			if ($('#parentMobile').val()) {
				addFamily.showIncorrectTip('手机号码');
			} else {
				addFamily.showIncompleteTip('手机号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#parentAddress').val()) {
			addFamily.showIncompleteTip('地址');
			$(obj).attr('disabled', false);
			return false;
		}

		// 验证码
		var inputCode = $('#parentCode').val();
		if (!inputCode) {
			addFamily.showIncompleteTip('验证码');
		}

		console.log('父母异步绑卡流程...');

		var data = {
			appCode: $('input[name="appCode"]').val(),
			areaCode: $('input[name="areaCode"]').val(),
			openId: $('input[name="openId"]').val(),
			name: $('input[name="rParentName"]').val(),
			mobile: $('input[name="rParentMobile"]').val(),
			idType: $('#parentIdType').val(),
			idNo: $('input[name="rParentIdNo"]').val(),
			birth: $('#parentBirth').val(),
			sex: $('#parentSex').val(),
			address: $('#parentAddress').val(),
			ownership: 4,
			verifyCode: $('#parentCode').val(),
			codeType: addFamily.codeType
		};
		console.log(data);
		addFamily.ajaxBindFamily(obj, data);
	},

	bindPartner: function(obj) {
		$(obj).attr('disabled', true);
		$Y.loading.show('正在为您进行绑定家人信息');

		if (!$('input[name="rPartnerName"]').val()) {
			if ($('#partnerName').val()) {
				addFamily.showIncorrectTip('姓名');
			} else {
				addFamily.showIncompleteTip('姓名');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rPartnerIdNo"]').val()) {
			if ($('#partnerIdNo').val()) {
				addFamily.showIncorrectTip('证件号码');
			} else {
				addFamily.showIncompleteTip('证件号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#partnerBirth').val()) {
			addFamily.showIncompleteTip('出生日期');
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rPartnerMobile"]').val()) {
			if ($('#partnerMobile').val()) {
				addFamily.showIncorrectTip('手机号码');
			} else {
				addFamily.showIncompleteTip('手机号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#partnerAddress').val()) {
			addFamily.showIncompleteTip('地址');
			$(obj).attr('disabled', false);
			return false;
		}

		// 验证码
		var inputCode = $('#partnerCode').val();
		if (!inputCode) {
			addFamily.showIncompleteTip('验证码');
		}

		console.log('伴侣异步绑卡流程...');

		var data = {
			appCode: $('input[name="appCode"]').val(),
			areaCode: $('input[name="areaCode"]').val(),
			openId: $('input[name="openId"]').val(),
			name: $('input[name="rPartnerName"]').val(),
			mobile: $('input[name="rPartnerMobile"]').val(),
			idType: $('#partnerIdType').val(),
			idNo: $('input[name="rPartnerIdNo"]').val(),
			birth: $('#partnerBirth').val(),
			sex: $('#partnerSex').val(),
			address: $('#partnerAddress').val(),
			ownership: 6,
			verifyCode: $('#partnerCode').val(),
			codeType: addFamily.codeType
		};
		console.log(data);
		addFamily.ajaxBindFamily(obj, data);
	},
	bindOther: function(obj) {
		$(obj).attr('disabled', true);
		$Y.loading.show('正在为您进行绑定家人信息');

		if (!$('input[name="rOtherName"]').val()) {
			if ($('#otherName').val()) {
				addFamily.showIncorrectTip('姓名');
			} else {
				addFamily.showIncompleteTip('姓名');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rOtherIdNo"]').val()) {
			if ($('#otherIdNo').val()) {
				addFamily.showIncorrectTip('证件号码');
			} else {
				addFamily.showIncompleteTip('证件号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#otherBirth').val()) {
			addFamily.showIncompleteTip('出生日期');
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rOtherMobile"]').val()) {
			if ($('#otherMobile').val()) {
				addFamily.showIncorrectTip('手机号码');
			} else {
				addFamily.showIncompleteTip('手机号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#otherAddress').val()) {
			addFamily.showIncompleteTip('地址');
			$(obj).attr('disabled', false);
			return false;
		}

		// 验证码
		var inputCode = $('#otherCode').val();
		if (!inputCode) {
			addFamily.showIncompleteTip('验证码');
		}

		console.log('他人异步绑卡流程...');

		var data = {
			appCode: $('input[name="appCode"]').val(),
			areaCode: $('input[name="areaCode"]').val(),
			openId: $('input[name="openId"]').val(),
			name: $('input[name="rOtherName"]').val(),
			mobile: $('input[name="rOtherMobile"]').val(),
			idType: $('#otherIdType').val(),
			idNo: $('input[name="rOtherIdNo"]').val(),
			birth: $('#otherBirth').val(),
			sex: $('#otherSex').val(),
			address: $('#otherAddress').val(),
			ownership: 2,
			verifyCode: $('#otherCode').val(),
			codeType: addFamily.codeType
		};
		console.log(data);
		addFamily.ajaxBindFamily(obj, data);
	},

	bindSibling: function(obj) {
		$(obj).attr('disabled', true);
		$Y.loading.show('正在为您进行绑定家人信息');

		if (!$('input[name="rSiblingName"]').val()) {
			if ($('#siblingName').val()) {
				addFamily.showIncorrectTip('姓名');
			} else {
				addFamily.showIncompleteTip('姓名');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rSiblingIdNo"]').val()) {
			if ($('#siblingIdNo').val()) {
				addFamily.showIncorrectTip('证件号码');
			} else {
				addFamily.showIncompleteTip('证件号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#siblingBirth').val()) {
			addFamily.showIncompleteTip('出生日期');
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rSiblingMobile"]').val()) {
			if ($('#siblingMobile').val()) {
				addFamily.showIncorrectTip('手机号码');
			} else {
				addFamily.showIncompleteTip('手机号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#siblingAddress').val()) {
			addFamily.showIncompleteTip('地址');
			$(obj).attr('disabled', false);
			return false;
		}

		// 验证码
		var inputCode = $('#siblingCode').val();
		if (!inputCode) {
			addFamily.showIncompleteTip('验证码');
		}

		console.log('兄弟姐妹异步绑卡流程...');

		var data = {
			appCode: $('input[name="appCode"]').val(),
			areaCode: $('input[name="areaCode"]').val(),
			openId: $('input[name="openId"]').val(),
			name: $('input[name="rSiblingName"]').val(),
			mobile: $('input[name="rSiblingMobile"]').val(),
			idType: $('#siblingIdType').val(),
			idNo: $('input[name="rSiblingIdNo"]').val(),
			birth: $('#siblingBirth').val(),
			sex: $('#siblingSex').val(),
			address: $('#siblingAddress').val(),
			ownership: 5,
			verifyCode: $('#siblingCode').val(),
			codeType: addFamily.codeType
		};
		console.log(data);
		addFamily.ajaxBindFamily(obj, data);
	},
	bindChild: function(obj) {
		$(obj).attr('disabled', true);
		$Y.loading.show('正在为您进行绑定家人信息');

		if (!$('input[name="rChildName"]').val()) {
			if ($('#childName').val()) {
				addFamily.showIncorrectTip('姓名');
			} else {
				addFamily.showIncompleteTip('姓名');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#childBirth').val()) {
			addFamily.showIncompleteTip('出生日期');
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('#childAddress').val()) {
			addFamily.showIncompleteTip('地址');
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rGuardName"]').val()) {
			if ($('#guardName').val()) {
				addFamily.showIncorrectTip('监护人姓名');
			} else {
				addFamily.showIncompleteTip('监护人姓名');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rGuardIdNo"]').val()) {
			if ($('#guardIdNo').val()) {
				addFamily.showIncorrectTip('监护人证件号码');
			} else {
				addFamily.showIncompleteTip('监护人证件证件号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		if (!$('input[name="rGuardMobile"]').val()) {
			if ($('#guardMobile').val()) {
				addFamily.showIncorrectTip('监护人手机号码');
			} else {
				addFamily.showIncompleteTip('监护人手机号码');
			}
			$(obj).attr('disabled', false);
			return false;
		}

		// 验证码
		var inputCode = $('#childCode').val();
		if (!inputCode) {
			addFamily.showIncompleteTip('验证码');
		}

		// 儿童异步绑卡
		console.log('儿童异步绑卡流程...');

		var data = {
			appId: $('input[name="appId"]').val(),
			appCode: $('input[name="appCode"]').val(),
			areaCode: $('input[name="areaCode"]').val(),
			openId: $('input[name="openId"]').val(),
			name: $('input[name="rChildName"]').val(),
			idType: '1',
			idNo: '',
			birth: $('#childBirth').val(),
			sex: $('#childSex').val(),
			address: $('#childAddress').val(),
			ownership: 3,
			guardName: $('#rGuardName').val(),
			guardIdType: $('#guardIdType').val(),
			guardIdNo: $('#rGuardIdNo').val(),
			guardMobile: $('#rGuardMobile').val(),
			verifyCode: $('#childCode').val(),
			codeType: addFamily.codeType
		};
		console.log(data);
		addFamily.ajaxBindFamily(obj, data);
	},
	ajaxBindFamily: function(obj, data) {
		return $.ajax({
			type: "POST",
			url: base.appPath + "app/usercenter/myFamily/add",
			data: data,
			cache: false,
			dataType: "json",
			timeout: 7900,
			error: function(data) {
				$Y.loading.hide();
				new $Y.confirm({
					ok: {
						title: '确定'
					},
					content: '网络超时，请稍后在我的家人查看绑定结果。'
				});
				$(obj).attr('disabled', false);
			},
			success: function(data) {
				$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK') {
					var isSuccess = data.message.isSuccess;
					if (isSuccess == 'success') {
						// 挂号过来的话，跳回挂号页面
						var forward = $('#forward').val();
						if (forward) {
							// $('#voForm').attr('action', forward);
			/*				if (forward.indexOf('?') == -1) {
								forward += '?'
							} else {
								forward += '&';
							}
							
							forward += 'openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode;*/
							window.location.href = base.appPath + forward;
						} else {
							// 成功返回列表界面
							$('#voForm').attr('action', base.appPath + 'app/usercenter/myFamily/index');
							$('#voForm').submit();
						}


					} else {
						new $Y.confirm({
							ok: {
								title: '确定'
							},
							content: data.message.msgInfo ? data.message.msgInfo : '绑卡失败'
						});
						$(obj).attr('disabled', false);
					}
				}
			}
		});
	}
}

$(function() {
	addFamily.init();
});

function doRefresh() {
	$('#voForm').attr('action', base.appPath + 'app/usercenter/myFamily/addMyFamily');
	$('#voForm').submit();
}