var nih = {
	init: function() {
		$('div.btn-ok').off('click').on('click', function() {
			var name = $('#name').val();
			var mobile = $('#mobileNo').val();
			var desc = $('#desc').val();

			if (nih.valid(name, mobile)) {
				$(this).off('click');
				nih.add(name, mobile, desc);
			}
		});
	},
	valid: function(name, mobile) {
		if (!name || name.trim().length == 0) {
			new $Y.confirm({
				ok: {
					title: '错误'
				},
				content: '请输入联系人称呼。'
			});
			return false;
		}
		
		var textMobile = /^[0-9]{11}$/;
		if (!textMobile.test(mobile)) {
			new $Y.confirm({
				ok: {
					title: '错误'
				},
				content: '请正确输入联系人的联系号码。'
			});
			return false;
		}
		
		return true;
	},
	add: function(name, mobile, desc) {
		$.ajax({
			data: {
				name: name,
				mobileNo: mobile,
				desc: desc,
				appCode: base.appCode,
				userId: base.openId
			},
			type: "POST",
			datatype: "json",
			timeout: 20000,
			url: base.appPath + "app/nih/add",
			error: function(data) {
				new $Y.confirm({
					ok: {
						title: '错误'
					},
					content: data
				});
				
				nih.init();
				return false;
			},
			success: function(data) {
				console.log(data);
				if (data.status == "OK") {
					nih.showOKStatus();
				}
			}
		});
	},
	showOKStatus: function() {
		$('div.btn-ok').html('预约成功');
		/*
		$('div.btn-ok').off('click').on('click', function() {
			new $Y.confirm({
				ok: {
					title: '确定'
				},
				content: '点击退出。'
			});
		});
		*/
		
		new $Y.confirm({
			ok: {
				title: '确定',
				click: function() {
					window.location.href = base.appPath + 'app/nih/list';
				}
			},
			content: '预约成功，点击进入预约记录。'
		});
	}
}

$(function() {
	nih.init();
});