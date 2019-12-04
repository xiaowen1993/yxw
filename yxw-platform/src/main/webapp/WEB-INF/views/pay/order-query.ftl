<html>
<head>
    <#include "/sys/common/common.ftl">
	<title>支付等待</title>
	<link rel="stylesheet" type="text/css" href="${basePath }css/style_green.css">
	<link rel="stylesheet" type="text/css" href="${basePath }css/yxw_wechat.css">
	<script type="text/javascript">
		var sh;
		var payType = 0;
		var t;
		var h;
		var failUrl = '${pay.failUrl}?${pay.payRemark}';
		var dealWithUrl = '${pay.dealWithUrl}?${pay.payRemark}';
		var tradeNo = '${tradeNo}';
		$(function(){
			// 每隔三秒查询一下订单状态
			sh = setInterval(queryPayType, 3000);
			
			// 十秒后显示按钮
			t = setTimeout(function(){$(".bnt.bnt_1").fadeIn("slow");}, 10000);
			
			// 二十秒后关闭页面
			h = setTimeout(function(){
				clearInterval(sh); // 停止查询订单状态
				clearTimeout(t);   // 停止显示按钮
				
				$(".bnt.bnt_1").fadeOut("slow");
				$("span:eq(0)").text("订单交易延迟");
				$("span:eq(1)").text("三秒后关闭页面，请稍候...");
				console.dir("订单交易延迟,三秒后关闭页面，请稍候...");
				setTimeout(function(){ 
					console.log("支付失败，转后台查询");
					//setExceptionCache();
					location.href = failUrl;
					closeWindow(); 
				}, 3000);
			}, 20000);
			
			// [ 我知道了 ] 按钮的关闭事件
			$("#close").click(function(){
				clearInterval(sh); // 停止查询订单状态
				clearTimeout(h);   // 停止显示按钮
				if('${pay.payState}' == 1){
					console.log("支付成功，转支付成功页面");
					var url = dealWithUrl + '&' + tradeNo + '=${pay.transactionId}';
					location.href = url;
				}else{
					console.log("支付失败，转后台查询");
					//setExceptionCache();
					location.href = failUrl;
					closeWindow(); 
				}
			});
		});
	
		//查询订单	
		function queryPayType(){
			$.ajax({
				url: '${basePath }mobileApp/pay/orderQueryJson',
				data: {
					${payPrefixName}: '${pay.payPrefix}',
					${fieldName}: '${pay.field}'
				},
				dataType: 'json',
				timeout: 30000,
				type: 'POST',
				error: function(XMLHQ, errorMsg) {}, 
				success: function(data){
					console.log(data.payState);
					if(data.payState == 1){
						//支付成功
						clearInterval(sh); // 停止查询订单状态
						clearTimeout(t);   // 停止显示按钮
						
						$(".bnt.bnt_1").fadeOut("slow");// 按钮隐藏
						$("span:eq(0)").text("订单交易成功");
						$("span:eq(1)").text("三秒后自动跳转，请稍候...");
						console.dir("订单交易成功,三秒后自动跳转，请稍候...");
						setTimeout(function(){
							var url = dealWithUrl + '&' + tradeNo + '=' + data.transactionId;
							location.href = url;
						}, 3000);
					}else if(data.payState != 1 && data.payState != 8){
						clearInterval(sh); // 停止查询订单状态
						clearTimeout(t);   // 停止显示按钮
						
						$(".bnt.bnt_1").fadeOut("slow"); // 按钮隐藏
						$("span:eq(0)").text("订单交易失败");
						$("span:eq(1)").text("三秒后关闭页面，请稍候...");
						console.dir("订单交易失败,三秒后关闭页面，请稍候...");
						/*三秒号关闭页面*/
						setTimeout(function(){ 
							location.href = data.failUrl + "?" + data.payRemark;
							closeWindow();
						}, 3000);
					}
				}
			});
		}
		
		function setExceptionCache(){
			$.ajax({
				url: '${basePath }mobileApp/pay/setExceptionCache',
				data: 
				{
					${payPrefixName}: '${pay.payPrefix}',
					${fieldName}: '${pay.field}'
				},
				dataType: 'json',
				timeout: 30000,
				type: 'POST',
				error: function(XMLHQ, errorMsg) {}, 
				success: function(data){
					console.log(data);
					alert("后台执行");
					closeWindow();
				}
			});
		}
		
		/*关闭窗体*/
		function closeWindow(){
			WeixinJSBridge.invoke('closeWindow',{},function(res){});
		}
	</script>
</head>
<body>
	<div id="main">
		<center>
	  		<img src="${basePath}images/pay/pay_wait.png" width="60%" style="margin-top: 60px;"><br><br>
	  		<span>订单正在处理中</span><br>
	  		<span>可能会有延迟，请耐心等待</span><br><br>
	  		<div class="bnt bnt_1" style="display: none;"><a href="javascript:void(0);" id="close">我知道了</a></div>
		</center>
	</div>
</body>
</html>

