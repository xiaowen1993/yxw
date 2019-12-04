<html>
<head>
	<#include "/common/common.ftl">
	<title>JSONP</title>
</head>
<body>
	<div id="body">
	
		<div id="jsonp"></div>
		
		<div class="section btn-w">
	       <div id="payBtn" class="btn btn-ok btn-block" onclick="#">确定支付</div>
	    </div>
	
	</div>
	
	<#include "/common/copyright_footer.ftl">
	
	<script>
		function jsonpCallback(data) {
			$("#jsonp").html(data.message);
		}
	</script>
	
	<script type="text/javascript" src="${basePath}pay/test/jsonpServer?callback=jsonpCallback&orderNo=Y220160808161410151&openId=a89f3b785148445b8b7f99474a8b378a"></script>
	
</body>
</html>