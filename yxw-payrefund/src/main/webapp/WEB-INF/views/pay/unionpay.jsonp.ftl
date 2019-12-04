<html>
<head>
	<#include "/common/common.ftl">
	<#include "/common/unionpay.jssdk.ftl">
	<title>云闪付支付</title>
</head>
<body>
	<div id="body">
	
		<div id="jsonp"></div>
		
		<input type="hidden" id="unionPayTN" value="${params.tn}">

		<#--<div style="height:0.8em;"></div>-->
		
		<#include "/common/pay.timer.ftl">
		
		<div class="section btn-w">
	       <div id="payBtn" class="btn btn-ok btn-block">确定支付</div>
	    </div>
	</div>
	
	<#include "/common/copyright_footer.ftl">
	<script>
		function jsonpCallback(data) {
			$("#jsonp").html(data.message);
		}
	</script>
	
	<script type="text/javascript"src="${params.pay.infoUrl}?callback=jsonpCallback&orderNo=${params.pay.orderNo}"></script>
	
	<script>
		$("#payBtn").click(function(){
			yxUPSDK.pay($("#unionPayTN").val());
		});
		
		function paySuccess() {
			location.href = "${params.pay.paySuccessPageUrl}?orderNo=${params.pay.orderNo}";
		}
		
	</script>
	
	
</body>
</html>