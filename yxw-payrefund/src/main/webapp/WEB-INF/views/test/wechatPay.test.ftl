<html>
<head>
	<#include "/common/common.ftl">
	<title>微信支付-测试</title>
	<link rel="stylesheet" href="${basePath}payrefund/css/weui.min.css" type="text/css" />
	<link rel="stylesheet" href="${basePath}payrefund/css/jquery-weui.min.css" type="text/css" />
	<script type="text/javascript" src="${basePath}payrefund/js/common/jquery-weui.min.js"></script>
</head>
<body>
	<div id="body">
		<br>
		
		<div class="section btn-w">
		    	<div class="btn btn-ok btn-block" 
		            onclick="location.href='wechatPay.iframe?totalFee=${params.totalFee}'">确定支付(iframe)</div>
		       
		    	<#--<div class="btn btn-ok btn-block" 
		            onclick="location.href='wechatPay.jsonp?totalFee=${params.totalFee}'">确定支付(jsonp)</div>-->
	
	
			<div class="btn btn-ok btn-block" 
		            onclick="location.href='wechatPay.iframe.component?totalFee=${params.totalFee}'">确定支付(iframe)(第三方代授权)</div>
			
			<div class="btn btn-ok btn-block" onclick="scanQRCode()">扫码支付</div>
	            
	    </div>
	
		<table align="center" width="98%" border="0"><tr><td>
		<textarea style="width: 100%; min-height: 200px; border: 0px; background:transparent; border-style:none;"></textarea>
		</td></tr></table>
		
	</div>
	
	<#include "/common/wechatPay.jssdk.ftl">
	
	<#include "/common/copyright_footer.ftl">
	
	<script type="text/javascript" src="${basePath}payrefund/js/biz/test/wechatPay.test.js?v=1.0"></script>
	
</body>
</html>