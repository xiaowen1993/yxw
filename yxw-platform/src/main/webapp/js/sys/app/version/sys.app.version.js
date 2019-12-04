/**
 * 客户端管理js
 */
var $app = {};

$app.version = {};

$app.version.toAdd = function(osName) {
	if ("Android" == osName) {
		location.href = "toAndroidAdd";
	} else if ("IOS" == osName) {
		location.href = "toIOSAdd";
	}
}

$app.version.attachType = 0; // 0 图片 1 文档

/**
 * 上传
 */
$app.version.uploadFile = function(){
	var src = $('#uploadFile').val();
	if(src){
		var postUrl = "";
		if ($app.version.attachType == 0) {
			postUrl = $app.getBasePath() + "uploading?method=uploadLogo";
		} else if ($app.version.attachType == 1) {
			var fileSuffixRegex = /[.apk|.APK|.ipa|IPA]$/;
			if(fileSuffixRegex.test(src)) {
				var attachId = src.substring(src.replace(/\\/g, "/").lastIndexOf("/") + 1, src.length - 4);
				postUrl = $app.getBasePath() + "attach/uploadFile?attachId=" + attachId + "&attachType=" + $app.version.attachType;
			} else {
				alert("请上传正确的APP安装包");
				return;
			}

		}


		if (postUrl) {
			$.ajaxFileUpload({
				secureuri:false,
				fileElementId: 'uploadFile',
				dataType: 'json',
				type:'POST',
				url: postUrl,
				success:function(resp){
					console.dir(resp);

					if ($app.version.attachType == 0) {
						if (resp.status == 'OK') {
							$('#showLogo').attr('src', resp.message);
							$("#saveForm input[name='appLogo']").val(resp.message);
						} else if (resp.status == 'ERROR') {
							window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
						}
					} else if ($app.version.attachType == 1) {
						if(resp.status){
							//$("#saveForm input[name='appSetupFile']").val(resp.attach.originalName);
							$("#saveForm input[name='appSetupFile']").val(resp.attach.relativePath);
						}else{
							alert('上传APP安装包失败');
						}
					}
				}
			});
		}
	}
}

$app.version.save = function() {
	var nullHint = $app.common.checkNull();
	if (nullHint) {
		alert(nullHint);
		return;
	}
	
	var datas = $("#saveForm").serializeArray();
	
	$.ajax({
    	url : 'saveVersion',
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
