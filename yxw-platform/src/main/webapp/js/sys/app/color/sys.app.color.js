var appColor = {
	init: function() {
		// 颜色初始化
		var sColor = $('#color').val();
		if (sColor && sColor.length > 0) {
			$('#colorInput').val(sColor);
		} else {
			$('#color').val($('#colorInput').val());
		}
		
		appColor.bindEvent();
	},
	bindEvent: function() {
		// 样例颜色改变则改变颜色值
		$('#colorInput').off('change').on('change',function() {
			console.log($(this).val());
			$('#color').val($(this).val());
		});
		
		// 颜色值改变，输入回车后，进行颜色值得校验，并提示
		$('#color').off('keyup').on('keyup', function(event) {
			// /^#[0-9a-fA-F]{3,6}$/
			if (event.keyCode == 13) {
				var sColor = $(this).val();
				if (colorReg.test(sColor)) {
					$(this).siblings().hide();
					$('#colorInput').val(sColor);
				} else {
					$(this).siblings().show();
				}
			}
		});
		
		// 保存
		$('.btn-save').off('click').on('click', function() {
			appColor.save();
		});
		
		// 删除
		$('.btn-remove').off('click').on('click', function() {
			appColor.remove();
		});
	},
	save: function() {
		// 颜色值是否为空
		var sColor = $('#color').val();
		if (!sColor || sColor.trim() == '') {
			if (colorReg.test(sColor)) {
				$('#color').siblings().hide();
				$('#colorInput').val(sColor);
			} else {
				$('#color').siblings().show();
				return false;
			}
		}
		
		var datas = $('#dataForm').serializeArray();
		console.log(datas);
		$.ajax({
			url: 'save',
			type: 'POST',
			datatype: 'json',
			data: datas,
			success: function(data) {
				if (data.status == "OK") {
					// 成功，那啥去
					console.log('OK');
					window.location.href = 'list';
				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert('保存失败！');
			}
		});
	},
	remove: function() {
		$.ajax({
			url: 'delete',
			type: 'POST',
			datatype: 'json',
			data: {id: $('#id').val()},
			success: function(data) {
				if (data.status == "OK") {
					// 成功，那啥去
					console.log('OK');
					window.location.href = 'list';
				} else {
					alert(data.message);
				}
			},
			error: function() {
				alert('删除失败！');
			}
		});
	}
}

// var colorReg = /^#[0-9a-fA-F]{3,6}$/;
var colorReg = /^#[0-9a-fA-F]{6}$/;

$(function() {
	appColor.init();
})