<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<meta name="format-detection" content="telephone=no"/>
<meta name="viewport" content="width=device-width,  initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0,  user-scalable=no" />
<#include "/common/common.ftl">
<title>ftl测试</title>
<script>
$(function(){
$("#test").text("test");

$.ajax({
		type: "POST",
		url: "http://localhost:8088/easyhealth/register/confirm/regOrderInfo",
		data: {
			openId: 'a89f3b785148445b8b7f99474a8b378a',
			orderNo: 'Y220160808161410151'
		},
		cache: false,
		dataType: "jsonp",
		jsonp: "callbackparam",
		jsonpCallback:"success_jsonpCallback",
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("系统繁忙，请稍候再试");
		},
		success: function(data, textStatus) {
		console.log(data);
		}
	});

});
</script>
</head>
<body>
	<div id="body">
		
	</div>
<div id="test"></div>
<#include "/common/copyright_footer.ftl">
</body>
</html>
