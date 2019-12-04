$(function() {
	$("textarea").each(function() {
		var obj = $("#" + this.id.substring(0, this.id.indexOf("_temp")));
		if (obj) {
			var val = $(obj).val();
			$(this).val(val);
		}
	});

	ruleJS.init();

});

var ruleJS = {
	//初始化
	init: function() {
		var ruleType = $('#ruleType').val();
		if (ruleType == 'ruleTiedCard') {
			ruleJS.initRuleTiedCard();
		}
	},
	//初始化绑卡配置页面
	initRuleTiedCard: function() {
		var cardTypeCheckboxs = $('input[name="cardTypeArray"]');
		var inputCardNoTip = $('input[name="inputCardNoTip"]').val().split(',');
		var inputCardTypeRemarkArray = $('input[name="inputCardTypeRemark"]').val().split(',');
		var inputCardNoTip_html = '';
		var inputCardTypeRemark_html = '';
		var maxIndex = $(cardTypeCheckboxs[cardTypeCheckboxs.length - 1]).val();
		var index = 0;
		for (var i = 0; i < maxIndex; i++) {
			inputCardNoTip_html += '<div class="line"';
			inputCardTypeRemark_html += '<div class="line"';
			if (i + 1 == $(cardTypeCheckboxs[index]).val()) {
				var tip = inputCardNoTip[i];
				var inputCardTypeRemark = inputCardTypeRemarkArray[i];
				if (!tip) {
					tip = "";
				}
				if (!inputCardTypeRemark) {
					inputCardTypeRemark = "";
				}
				inputCardNoTip_html += '>' + $(cardTypeCheckboxs[index]).parent().text().trim() + '输入框提示内容　<input type="text" placeholder="点击输入提示语" class="span5 input33" name="inputCardNoTipArray" value="' + tip + '"';
				inputCardTypeRemark_html += '>' + $(cardTypeCheckboxs[index]).parent().text().trim() + '名称备注　<input type="text" placeholder="点击输入备注名" class="span5 input33" name="inputCardTypeRemarkArray" value="' + inputCardTypeRemark + '"';
				index++;
			} else {
				inputCardNoTip_html += ' style="display:none">输入框提示内容　<input type="text" placeholder="点击输入提示语" class="span5 input33" name="inputCardNoTipArray" value=""';
				inputCardTypeRemark_html += ' style="display:none">卡类型名称备注　<input type="text" placeholder="点击输入备注名" class="span5 input33" name="inputCardTypeRemarkArray" value=""';
			}
			inputCardNoTip_html += ' /></div>';
			inputCardTypeRemark_html += ' /></div>';
		}
		$('.inputCardNoTip').html(inputCardNoTip_html);
		$('.inputCardTypeRemark').html(inputCardTypeRemark_html + '<div class="line" style="color:red;">（当卡类型对应的备注名不为空时，优先显示备注名）</div>');
	},
	//发布规则
	publishRule: function(hospitalId) {
		if (confirm("确定要发布该医院的规则配置吗?")) {
			var url = $("#basePath").val() + "sys/ruleIndex/publishRule?hospitalId=" + hospitalId;
			jQuery.ajax({
				type: 'POST',
				url: url,
				dataType: 'json',
				timeout: 120000,
				success: function(data) {
					alert("发布成功!");
					$("#" + hospitalId + "_publishTime").html(data.message.publishTime);
					$("#" + hospitalId + "_publishstatus").html(data.message.publishstatus);
					$("#" + hospitalId + "_lastHandler").html(data.message.lastHandler);
				},
				error: function(data) {
					alert("发布失败!");
				}
			});
		}
	},
	//输入校验
	validate: function(ruleType) {
		//挂号规则输入校验
		if (ruleType == 'RuleRegister') {
			return ruleJS.validateRuleRegister();
		} else {
			//其他规则不需要校验
			return true;
		}
	},
	//挂号规则输入校验
	validateRuleRegister: function() {
		var regMaximumSameDoctor = parseInt($('#regMaximumSameDoctor').val() != '' ? $('#regMaximumSameDoctor').val() : "0");
		var regMaximumInDay = parseInt($('#regMaximumInDay').val() != '' ? $('#regMaximumInDay').val() : "0");
		var regMaximumInWeek = parseInt($('#regMaximumInWeek').val() != '' ? $('#regMaximumInWeek').val() : "0");
		var regMaximumInMonth = parseInt($('#regMaximumInMonth').val() != '' ? $('#regMaximumInMonth').val() : "0");
		if (regMaximumInMonth >= regMaximumInWeek && regMaximumInWeek >= regMaximumInDay && regMaximumInDay >= regMaximumSameDoctor) {
			return true;
		} else {
			return false;
		}
	},
	/*
	 * 规则编辑
	 */
	ruleEdit: function(hospitalId, ruleType) {
		if (!ruleType) {
			ruleType = "ruleEdit";
		}

		if ($("#ruleSelect")) {
			$("#ruleSelect option[value='" + ruleType + "']").attr("selected", "selected");
		}

		window.location = $("#basePath").val() + "sys/ruleIndex/toEdit?hospitalId=" + hospitalId + "&ruleType=" + ruleType;
	},

	//保存规则
	saveRule: function(formId, ruleType) {
		//console.info($('input[name="bindCardSuc"]').val());
		//console.info($('#' + formId).serializeArray());
		// 挂号规则特殊处理一下
		if (formId == 'ruleRegisterForm') {
			if ($('input[name="dutySupportSearchDoc"]').get(0).checked && $('input[name="appointmentSupportSearchDoc"]').get(0).checked) {
				$('#isHasSearChDoctor').val(1);
			} else if ($('input[name="dutySupportSearchDoc"]').get(0).checked) {
				$('#isHasSearChDoctor').val(2);
			} else if ($('input[name="appointmentSupportSearchDoc"]').get(0).checked) {
				$('#isHasSearChDoctor').val(3);
			} else {
				$('#isHasSearChDoctor').val(0);
			}
		} 
		
		// 推送规则的pushModes需要特殊处理
		if ($('#' + formId).is('.rulePush')) {
			
		}

		if (ruleType) {
			var isPass = ruleJS.validate(ruleType);
			if (!isPass) {
				alert("请确保：每人每月挂号次数>=每人每周挂号次数>=每人每天允许挂号次数>=每人每天允许挂同一医生次数");
				return false;
			}
			$("#" + formId).find("textarea").each(function() {
				console.log(this.id);
				$("#" + this.id.substring(0, this.id.indexOf("_temp"))).val($(this).val());
			});
			var savePath = ruleType.substring(0, 1).toLowerCase();
			savePath += ruleType.substring(1, ruleType.length);
			var url = $("#basePath").val() + "sys/" + savePath + "/save" + ruleType;
			var datas = $('#' + formId).serializeArray();
			jQuery.ajax({
				type: 'POST',
				url: url,
				data: datas,
				dataType: 'json',
				timeout: 120000,
				success: function(data) {
					if (data.status == 'OK') {
						alert("保存成功!");
						$("#id").val(data.message.entityId);
					} else {
						alert(data.message);
					}
				},
				error: function(data) {
					alert("保存失败!");
				}
			});
		} else {
			alert("缺少参数:ruleType!");
		}
	}
};