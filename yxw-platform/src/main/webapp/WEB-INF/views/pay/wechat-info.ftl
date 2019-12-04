<html>
<head>
	<#include "/mobileApp/common/common.ftl">
	<title>支付</title>
	<script type="text/javascript" src="${basePath}mobileApp/js/common/grayscale.js"></script>
</head>
<body>
	<div id="body">
		<iframe src="${pay.infoUrl}?${pay.payRemark}" width="100%" align="center" height="300" 
		id="win" name="win" onload="Javascript:SetWinHeight(this)" frameborder="0" scrolling="no"></iframe>
		<#if pay.onlinePaymentControl == 3>
            <div style="height:0.8em;"></div>
        </#if>
		<#if leftSecond?exists>
			<div class="section">
	        <div class="box-tips"> <i class="icon-warn"></i>
	        	<input type="hidden" id="leftSecond" value="${leftSecond}">
	                            温馨提示：请在<span id="leftSecondSpan" style="color: red;"></span>分钟内完成支付，逾期号源将会作废，需要重新挂号。
	        </div>
	    </div>
		</#if>
		<div class="section btn-w">
	        <div id="payBtn" class="btn btn-ok btn-block" onclick="pay(this);">确定支付</div>
	    </div>
		
	</div>
	<script type="text/javascript">
		jQuery(function(){
			if('${leftSecond}'){
				if('${leftSecond}' <= 0){
					$('#leftSecond').val(0);
					$('#leftSecondSpan').text("00:00");
					$('#payBtn').unbind("click");//禁用
					grayscale($('#payBtn'));
				}else{
					$('#leftSecondSpan').text(secondToMin(${leftSecond}));
					setTimeout(djs, 1000);
				}
			}
		});
	
		//倒计时
		function djs() {
			var leftSecond = $('#leftSecond').val() - 1;
			if (leftSecond > 0) {
				$('#leftSecond').val(leftSecond);
				$('#leftSecondSpan').text(secondToMin(leftSecond));
				setTimeout(djs, 1000);
			} else {
				$('#leftSecond').val(0);
				$('#leftSecondSpan').text("00:00");
				$('#payBtn').unbind("click");//禁用
				grayscale($('#payBtn'));
			}
		}
		
		function secondToMin(leftSecond){
			var min = 0;
			var second = 0;
			if(leftSecond > 60){
				var min = parseInt(leftSecond/60);
				var second = parseInt(leftSecond%60);
				if(min < 10){
					min = "0" + min;
				}
				if(second < 10){
					second = "0" + second;
				}
			}else{
				var second = leftSecond%60;
				if(second < 10){
					second = "0" + second;
				}
				min = "00";
			}
			return min + ":" + second;
		}
		
		function pay(obj) {
			var paySign = "${pay.paySign}";
			console.log("${pay.appId}" + "--${pay.paySign}" + "-${pay.prepayId}");
			var leftSecond = $('#leftSecond').val() - 1;
  			if(leftSecond > 0 || !'${leftSecond}'){
  				if (paySign) {
					WeixinJSBridge.invoke('getBrandWCPayRequest', {
						"appId": "${pay.appId}",
						"timeStamp": "${pay.timeStamp}",//时间戳
						"nonceStr": "${pay.nonceStr}",//随机字符串,String(32)
						"package": "prepay_id=${pay.prepayId}",  //订单详情扩展字符串,统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***
						"signType": "MD5",//签名方式
						"paySign": paySign//签名
						},function(res){
							/**
								err_msg
								get_brand_wcpay_request:ok 支付成功
								get_brand_wcpay_request:cancel 支付过程中用户取消
								get_brand_wcpay_request:fail 支付失败
							**/
							//alert(res.err_msg);
							WeixinJSBridge.log(res.err_msg); 
							//alert(res.err_msg);
							
							if (res.err_msg == 'get_brand_wcpay_request:ok') {
								//location.href = '${basePath }mobileApp/pay/orderQuery?${payPrefixName}=${pay.payPrefix}' + '&${fieldName}=${pay.field}';
								location.href = '${pay.successUrl}?${pay.payRemark}';
							}else if(res.err_msg == 'get_brand_wcpay_request:cancel'){
								console.log("取消支付");
							}else{
								//location.href = '${pay.failUrl}?${pay.payRemark}';
								var failNoMsg = new $Y.confirm({
	         						ok:{title:'确定',click:function(){
	         							failNoMsg.close();
	         							closeWindow();
	         						}},
	         						content:'<div style="text-align: center">支付失败！</div>'
	     						})
							}
					});
				}else{
					console.log("支付发起失败");
					//location.href = '${pay.failUrl}?${pay.payRemark}';
					var failMsg = new $Y.confirm({
	         			ok:{title:'确定',click:function(){
	         				failMsg.close();
	         				closeWindow();
	         			}},
	         			content:'<div style="text-align: center">支付失败，${pay.returnMsg}！</div>'
	     			})
				}
  			}
		}
		
		/*暂不支付*/
		function afterPay(obj){
			location.href = '${pay.afterPayUrl}?${pay.payRemark}';
		}
		
		/*iframe 自适应*/
		function SetWinHeight(obj) { 
			var win=obj; 
			if (document.getElementById) { 
				if (win && !window.opera) { 
					if (win.contentDocument && win.contentDocument.body.offsetHeight) {
						win.height = win.contentDocument.body.offsetHeight; 
					}else if(win.Document && win.Document.body.scrollHeight){ 
						win.height = win.Document.body.scrollHeight; 
					}
				} 
			} 
		}
		
		/*关闭窗体*/
		function closeWindow(){
			WeixinJSBridge.invoke('closeWindow',{},function(res){});
		}
	</script>
</body>
<#include "/mobileApp/common/yxw_footer.ftl">
</html>