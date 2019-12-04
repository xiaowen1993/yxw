/**
 * 客户端管理js
 */
var $app = {}

$app.optional = {};

$app.optional.toAdd = function() {
	location.href = "toAdd";
}

$app.optional.uploadIcon = function() {
	var src = $('#uploadFile').val();
	if (src) {
		// 异步上传到服务器
		$.ajaxFileUpload({
			secureuri : false,
			fileElementId : 'uploadFile',
			dataType : 'json',
			type : 'POST',
			url : $app.getBasePath() + "uploading?method=uploadLogo",
			success : function(resp) {
				if (resp.status == 'OK') {
					$('#showIcon').attr('src', resp.message);
					$('#icon').val(resp.message);
				} else if (resp.status == 'ERROR') {
					window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
				}
			}
		});
	}
}

$app.optional.save = function() {
	var nullHint = $app.common.checkNull();
	if (nullHint) {
		alert(nullHint);
		return;
	}
	
	var datas = $("#saveForm").serializeArray();
	
	$.ajax({
    	url : 'saveOptional',
    	data : datas,
    	dataType : 'json',
    	type : 'POST',
    	error : function(XMLHQ, errorMsg) {
    		console.log(errorMsg);
    		alert(errorMsg);
    	},
    	success : function(data) {
    		console.dir(data);
    		if (data.status == 'OK') {
    			location.href = 'list';
    		} else {
    			alert(data.message);
    		}
    	}
    });
}
