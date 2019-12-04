/**
 * 客户端管理js
 */
var $app = {}

$app.push = {};

$app.push.toAdd = function() {
	location.href = "toAdd";
}

$app.push.save = function() {
	var nullHint = $app.common.checkNull();
	if (nullHint) {
		alert(nullHint);
		return;
	}
	
	editor.sync();
	
	var datas = $("#saveForm").serializeArray();
	
	var content = {name: "content"};
	var contentType = $("input[name='contentType']:checked").val();
	if ("html" == contentType) {
		content.value = editor.html();
	} else if ("url" == contentType) {
		content.value = $("#url").val();
	}
	datas.push(content);
	
	//console.dir(datas);
	
	$.ajax({
    	url : 'savePush',
//    	url : 'test',
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
