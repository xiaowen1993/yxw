<html>
<head>
	<#include "/common/common.ftl">
	<title>微信支付</title>
</head>
<body>
	<div id="body">
		<iframe src="${params.pay.infoUrl}?orderNo=${params.pay.orderNo}" 
		        width="100%" height="61.8%" id="payFrame" name="payFrame" frameborder="0" scrolling="no"></iframe>
		
		<#--<div style="height:0.8em;"></div>-->
		
		<#include "/common/pay.timer.ftl">
		
		<div class="section btn-w">
	       <div id="payBtn" class="btn btn-ok btn-block">确定支付</div>
	    </div>
	</div>
	
	<#include "/common/copyright_footer.ftl">
	
	<script>
		$("#payBtn").click(function(){
			pay();
		});
		
		function paySuccess() {
			location.href = "${params.pay.paySuccessPageUrl}?orderNo=${params.pay.orderNo}";
		}
	</script>
	<#include "/common/wechatPay.jssdk.ftl">
	
	<#include "/common/iframe.ftl">
	<#-- <#include "/common/history.ftl"> -->
	
</body>
</html>