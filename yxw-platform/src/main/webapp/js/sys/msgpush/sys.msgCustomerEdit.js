/**
 *保存
 */
function save() {
	var hospitalId = $("form input[name='hospitalId']");
	var source = $("form input[name='source']");
	var id = $("form input[name='id']");

	if (!$.trim(id.val())) {
		var title = $("form input[name='title']");
		var code = $("form input[name='code']");
		var content = $("form textarea[name='content']");
		if (!$.trim(title.val())) {
			alert("标题不能为空!");
			return false;
		}
		if (!$.trim(code.val())) {
			alert("客服消息编码不能为空!");
			return false;
		}
		if (!$.trim(content.val())) {
			alert("消息内容不能为空!");
			return false;
		}

		$.ajax({
			type : "POST",
			url : $("#basePath").val() + "/msgpush/msgCustomer/check",
			data : {
				"code" : code.val(),
				"source" : source.val(),
				"hospitalId" : hospitalId.val()
			},
			dataType : "json",
			error : function(data) {
				alert("系统内部错误,请联系管理员。");
			},
			success : function(data) {
				if ("OK" == data.status) {
					//var content = $("textarea[name='content']");
					//content.val();
					//提交
					$.ajax({
						type : "POST",
						url : $("#basePath").val() + "/msgpush/msgCustomer/save",
						data : $("form[name='msgCustomerForm']").serializeArray(),
						dataType : "json",
						error : function(data) {
							alert("系统内部错误,请联系管理员。");
						},
						success : function(data) {
							if ("OK" == data.status) {
								alert("保存成功!");
								window.location.href = $("#basePath").val() + "/msgpush/msgTemplate/listViewByHospital?hospitalId=" + hospitalId.val();
							} else {
								alert("保存出错!");
							}
						}
					});

				} else {
					alert("客服消息编码已存在!");
				}
			}
		});

	} else {
		var content = $("form textarea[name='content']");
		if (!$.trim(content.val())) {
			alert("消息内容不能为空!");
			return false;
		}
		//提交
		$.ajax({
			type : "POST",
			url : $("#basePath").val() + "/msgpush/msgCustomer/save",
			data : $("form[name='msgCustomerForm']").serializeArray(),
			dataType : "json",
			error : function(data) {
				alert("系统内部错误,请联系管理员。");
			},
			success : function(data) {
				if ("OK" == data.status) {
					alert("保存成功!");
					window.location.href = $("#basePath").val() + "/msgpush/msgTemplate/listViewByHospital?hospitalId=" + hospitalId.val();
				} else {
					alert("保存出错!");
				}
			}
		});
	}

}