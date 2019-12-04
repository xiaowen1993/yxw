/**
 * 医院管理后台js
 */

var $attach = {};


$attach.uploader;

/**
 * 请求前缀
 */
$attach.basePath = basePath;

/**
 * 选择文件元素ID
 */
$attach.browse_button = 'browse';


/**
 * 上传
 */
$attach.uploadFile = function(obj,showId,attachId,attachType){
	var fileElementId = $(obj).attr("id");
	var fileName = $(obj).val();
	if(fileName != ''){
		var hz = /\.[^\.]+/.exec(fileName);
		if(hz == '.p12'){
			$.ajaxFileUpload({
				secureuri:false,
				fileElementId: fileElementId,  			
				dataType: 'json',	  
				type:'POST',
				url: basePath + "attach/uploadFile?attachId=" + jQuery("#" + attachId).val() + "&attachType=" + attachType,
				success:function(resp){
					console.log(resp);
					if(resp.status){
						//alert(resp.attach);
						jQuery("#" + attachId).val(resp.attach.attachId);
						jQuery("#" + showId).text(resp.attach.originalName);
						//jQuery(obj).attr("value",resp.attach.originalName)
						//alert(jQuery(obj).attr("value"));
					}else{
						alert('上传失败');
					}
				}                                                                                                              
			});
		}else{
			alert("请上传正确的密钥文件.");
		}
	}else{
		console.log("src不能为空");
	}
}


