// 性别选中
// $('input[name="sex"]:checked').val()

var perfect = {
	init: function() {

		// 设置默认性别
		// $('div.sex-bar').find('')

		// 选择时间 出现时间控件
		new yxCalendar({
			dom: '.js-date'
		});

		perfect.getIndex($('#yx-select-card-id-val'));

		perfect.loadProvince($("select[name='province']"));
	},
	bindEvent: function() {
		$('#yx-select-card-id-val').off('change').on('change', function() {
			perfect.getIndex($(this))
		});

		$('#btnPerfect').off('click').on('click', function() {
			perfect.perfectUserInfo();
		});

		// 省份选择 -- 初始化城市， 清空区
		$('#province').off('change').on('change', function() {
			perfect.selectScreening($(this)[0]);
			perfect.loadCity($(this));
		});

		// 城市选择 -- 初始化区
		$('#city').off('change').on('change', function() {
			perfect.selectScreening($(this)[0]);
			perfect.loadArea($(this));
		});

		// 区域
		$('#area').off('change').on('change', function() {
			perfect.selectScreening($(this)[0]);
		});

		// 名字脱敏
		$('#edtName').off('blur').on('blur', function() {
			var name = $(this).val();
			var reg = /^[a-zA-Z0-9\u4e00-\u9fa5]{2,30}$/; // 30位大写，小写，数字，中文

			if (name) {
				if (reg.exec(name)) {
					// 脱敏
					$('#name').val(name);
					$(this).val('*' + name.substring(1));
				} else {
					perfect.showIncorrectTip('姓名');
					$(this).val('');
					$('#name').val('');
				}
			} else {
				$(this).val('');
				$('#name').val('');
			}
		});

		$('#edtName').off('focus').on('focus', function() {
			$(this).val('');
		});

		// 手机号码
		$('#edtMobile').off('blur').on('blur', function() {
			var mobile = $(this).val();

			if (mobile) {
				$('#mobile').val(mobile);
			} else {
				$(this).val('');
				$('#mobile').val('');
			}
		});

		$('#edtMobile').off('focus').on('focus', function() {
			$(this).val('');
		});
		
		// 卡信息脱敏
		$('#edtCardNo').off('blur').on('blur', function() {
			var cardNo = $(this).val();
			var reg = /^[a-zA-Z0-9]{7,30}$/; // 至少7位大写，小写，数字，

			if (cardNo) {
				if (reg.exec(cardNo)) {
					$('#cardNo').val(cardNo);
					$(this).val(cardNo.substring(0, 1) + perfect.getEncrypt(cardNo) + cardNo.substring(cardNo.length - 1, cardNo.length));
				} else {
					perfect.showIncorrectTip('卡信息');
					$(this).val('');
					$('#cardNo').val('');
				}
			} else {
				$(this).val('');
				$('#cardNo').val('');
			}
		});

		$('#edtCardNo').off('focus').on('focus', function() {
			$(this).val('');
		});
	},
	getIndex: function(obj) {
		var selectIndex = $(obj)[0].selectedIndex;
		perfect.selectedShow(obj, selectIndex)

		if (selectIndex == 0) {
			$('.yx-list li[needHideIndex="0"]').hide();
		} else {
			$('.yx-list li[needHideIndex="0"]').show();
		}
	},
	getEncrypt: function(data) {
		if (data.length > 2) {
			var str = "";
			for (var i = 0; i < data.length - 2; i++) {
				str += '*';
			}
			return str;
		} else {
			return "*";
		}
	},
	showIncorrectTip: function(data) {
		$Y.loading.hide();
		var tip = '您输入的%replace%格式不正确';
		$Y.tips(tip.replace('%replace%', data));
	},
	selectedShow: function(dom, selectIndex) {
		var mySelect = $(dom);
		var mySelectHtml = mySelect.find('option').eq(selectIndex).html();
		var mySelectVal = mySelect.find('option').eq(selectIndex).attr('value');
		$('#yx-select-card-id .view').html(mySelectHtml + '<i class="iconfont">&#xe600;</i>')
	},
	loadProvince: function(sel) {
		var provinces = dsy.get('0');
		var selector = $(sel);

		// 把广东放第一位。
		for (var i = 0; i < provinces.length; i++) {
			// console.log(provinces[i]);
			if ("广东省" == provinces[i]) {
				selector.append('<option value="' + i + '" selected>' + provinces[i] + '</option>');
			} else {
				selector.append('<option value="' + i + '">' + provinces[i] + '</option>');
			}
		}

		perfect.selectScreening(selector[0]);
		perfect.loadCity($('#province'));
	},
	loadCity: function(sel) {
		var citys = dsy.get('0_' + $(sel).val());
		var selector = $('select[name="city"]');
		selector.html('');
		for (var i = 0; i < citys.length; i++) {
			// console.log(citys[i]);

			// 暂时写死， 后面再拦截器中
			if ("广州市" == citys[i]) {
				selector.append('<option value="' + i + '" selected>' + citys[i] + '</option>');
			} else {
				selector.append('<option value="' + i + '">' + citys[i] + '</option>');
			}
		}

		if (!sel.selectedIndex) {
			sel.selectedIndex = 0;
		}

		perfect.selectScreening(selector[0]);
		perfect.loadArea($('#city'));
	},
	loadArea: function(sel) {
		var areas = dsy.get('0_' + $("select[name='province']").val() + '_' + $(sel).val());
		var selector = $('select[name="area"]');
		selector.html('');

		for (var i = 0; i < areas.length; i++) {
			// console.log(areas[i]);
			selector.append('<option value="' + i + '">' + areas[i] + '</option>');
		}

		if (!sel.selectedIndex) {
			sel.selectedIndex = 0;
		}

		perfect.selectScreening(selector[0]);
	},
	selectScreening: function(obj) {
		var option = obj.children[obj.selectedIndex];
		if (option) {
			var html = option.innerHTML;
			obj.previousElementSibling.innerHTML = html
		}
	},
	perfectUserInfo: function() {
		// 姓名非空
		var name = $.trim($("#name").val());
		if (!perfect.checkNull(name, '姓名')) {
			return;
		}

		// 姓名规则
		if (!/^[\u0391-\uFFE5]{2,}$/.test(name)) {
			perfect.showMessageBox("请输入至少两个字的中文姓名!");
			return;
		}

		// 手机号码规则
		var mobile = jQuery.trim(jQuery("#mobile").val());
		if (mobile) {
			var re = /^1\d{10}$/;
			if(!re.test(mobile)){
				perfect.showMessageBox("请输入正确的手机号!");
			    return;
			}
		}
		
		var cardType = $("#yx-select-card-id-val").val();
		var cardNo = $.trim($("#cardNo").val());

		// 证件非空
		if (!cardNo || cardNo == '') {
			perfect.showMessageBox("请输入证件号码!");
			return;
		}

		// 若为身份证则验证
		if (cardType == 1) {
			if (!idCardUtils.validateIdNo(cardNo)) {
				perfect.showMessageBox("请输入正确的证件号码!");
				return;
			}
		} else {
			// 非身份证 验证出生日期
			var birth = $.trim($('#birthDay').val());
			if (!birth || birth == '') {
				perfect.showMessageBox("请输入出生日期!");
				return;
			}

			// 非身份证 验证性别
			var gender = $('input[name="sex"]:checked').val();
			if (!gender) {
				perfect.showMessageBox("请输入性别!");
				return;
			}
		}

		var datas = {};
		datas.name = name;
		if (mobile) {
			datas.mobile = mobile;
		}
		datas.cardType = cardType;
		datas.cardNo = cardNo;

		if (cardType != 1) {
			datas.birthDay = $.trim($('#birthDay').val());
			datas.sex = gender;
		}

		var province = $('select[name="province"]').val();
		var city = $('select[name="city"]').val();
		var area = $('select[name="area"]').val();
		datas.address = dsy.get(0)[province] + dsy.get("0_" + province)[city] + dsy.get("0_" + province + "_" + city)[area];
		$Y.loading.show();
		
		console.log(datas);
		
		$.ajax({
			type: 'POST',
			url: base.appPath + "easyhealth/user/perfectUserInfo",
			data: datas,
			dataType: 'json',
			timeout: 120000,
			success: function(data) {
				$Y.loading.hide();
				if (data.status == 'OK') {
					myBox = new $Y.confirm({
						title: "",
						content: "<div style='text-align: center;'>完善资料成功！</div>",
						ok: {
							title: "确定",
							click: function() {
								myBox.close();
								var forward = $("#forward").val();
								 
								if (forward) {
									go(appPath + decodeURIComponent(decodeURIComponent(forward)), false);
								} else {
									if (IS.isMacOS) {
										try {
											window.appCloseView(true);
										} catch (e) {
										}

									} else if (IS.isAndroid) {
										try {
											window.yx129.appCloseView(true);
										} catch (e) {
										}

									} else {
										go(appPath + 'easyhealth/userCenterIndex');
									}

								}
							}
						}
					});
				} else {
					perfect.showMessageBox(data.message);
				}
			},
			error: function(data) {
				perfect.showMessageBox('网络异常,请保持您的网络通畅,稍后再试.');
			}
		});
	},
	checkNull: function(value, typeName) {
		if (!value || value == '') {
			perfect.showMessageBox("请输入" + typeName + "!");
			return false;
		} else {
			return true;
		}
	},
	showMessageBox: function(msg) {
		var myBox = new $Y.confirm({
			title: "",
			content: "<div style='text-align: center;'>" + msg + "</div>",
			ok: {
				title: "确定",
				click: function() {
					myBox.close();
				}
			}
		});
	}
}

$(function() {
	perfect.init();
	perfect.bindEvent();
})

function doRefresh() {
	$('#paramsForm').attr('action', base.appPath + 'easyhealth/user/toPerfectUserInfo');
	$('#paramsForm').submit();
}