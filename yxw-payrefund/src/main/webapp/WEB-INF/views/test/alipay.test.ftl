<html>
<head>
	<#include "/common/common.ftl">
	<title>支付宝-测试</title>
</head>
<body>
	<div id="body">
		<br>
		
		<div class="section btn-w">
	       <div class="btn btn-ok btn-block" 
	            onclick="location.href='alipay.iframe?totalFee=${totalFee}'">确定支付(iframe)</div>
	       
	       <#--<div class="btn btn-ok btn-block" 
	            onclick="location.href='alipay.jsonp?totalFee=${totalFee}'">确定支付(jsonp)</div>-->
	    </div>
	
	</div>
	
	<#include "/common/copyright_footer.ftl">
	
	<script>
	</script>
	
</body>
</html>