/**
 * 客户端管理js
 */
var $app = {}

$app.getBasePath = function() {
	return $("#basePath").val();
}

$app.carrieroperator = {};

$app.carrieroperator.toAdd = function() {
	location.href = "toAdd";
}

$app.carrieroperator.picId = 'starPtic640x960';
$app.carrieroperator.isStoring = false;

$app.carrieroperator.uploadIcon = function(data) {
	var src = $('#uploadFile').val();
	 var img=document.createElement("img");    
	     img.src=src;  
	 var size=img.fileSize;
	

	if (src) {
		// 异步上传到服务器
		$.ajaxFileUpload({
			secureuri : false,
			fileElementId : 'uploadFile',
			dataType : 'json',
			type : 'POST',
			url : $app.getBasePath() + "uploading?method=uploadLogo&picType="+$app.carrieroperator.picId,
			success : function(resp) {
				if (resp.status == 'OK') {
					//alert(resp.message);
					$('#'+$app.carrieroperator.picId).val(resp.message);
				} else if (resp.status == 'ERROR') {
					window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
				}
			}
		});
	}
}
$app.carrieroperator.showMessageBox = function(data) {
	  $Y.tips(data,2000);
}
$app.carrieroperator.checkSorting = function() {
	var sortingNew=$("#sorting").val();
	var sortingBijiao=$("#sortingBijiao").val();
	$app.carrieroperator.isStoring=false;
	var operationPosition = $("input[name='operationPosition']:checked").val();
	var isRepeat=false;
	$.ajax({
		type: "POST",
		url:  "quertSorting",
		data: {
			
			sortingNew: sortingNew,
			sortingBijiao: sortingBijiao,
			operationPosition:operationPosition,
		},
		cache: false, 
		dataType: "json", 
		timeout: 600000,
		type: 'POST',
		error: function(data) {
			$app.carrieroperator.isStoring=true;
		},
		success: function(data) {
			if (data.status == 'OK') {
				$app.carrieroperator.isStoring=true;
				$app.carrieroperator.showMessageBox("您输入的排序号已经存在，请重新输入");
				return true;
			}
		}
	});
}
$app.carrieroperator.save = function() {
	var nullHint = $app.common.checkNull();
	if (nullHint) {
		$app.carrieroperator.showMessageBox(nullHint);
		return;
	}
	editor.sync();
	
	var datas = $("#saveForm").serializeArray();
	
//	var ckbOper1=$("input[name='ckbOper1']:checked").val();
//	var ckbOper2=$("input[name='ckbOper2']:checked").val();
//	var operationPosition = {name: "operationPosition"};
//	operationPosition.value="";
//	if(ckbOper1!=null && ckbOper2!=null ){
//		operationPosition.value="1_2";
//	}else if(ckbOper1!=null && ckbOper2==null){
//		operationPosition.value="1";
//	}else if(ckbOper1==null && ckbOper2!=null){
//		operationPosition.value="2";
//	}else{
//		$app.carrieroperator.showMessageBox("请选择运营位置");
//		return;
//	}
	if($app.carrieroperator.isStoring){
		$app.carrieroperator.showMessageBox("您输入的排序号已经存在，请重新输入");
		return;
	}
	
	var content = {name: "content"};
	var contentType = $("input[name='contentType']:checked").val();
	if ("html" == contentType) {
		content.value = editor.html();
	} else if ("url" == contentType) {
		content.value = $("#url").val();
	}
	datas.push(content);
	$.ajax({
    	url : 'addCarrieroperator',
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
    			location.href = 'queryCarrieroperatorList';
    		} else {
    			alert(data.message);
    		}
    	}
    });
}
