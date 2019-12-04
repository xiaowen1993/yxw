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
		                            温馨提示：请在<span id="leftSecondSpan" style="color: red;"></span>分钟内完成支付，超时不支付挂号将不成功，需要重新挂号。
		        </div>
		    </div>
		</#if>
		<div class="section btn-w">
	        <div id="payBtn" class="btn btn-ok btn-block" onclick="pay(this);">确定支付</div>
	    </div>
		
	</div>
	<script type="text/javascript">
		jQuery(function(){
			if(IS.isMacOS){
	        try{ 
	        	setTimeout("appHideNavRefresh();",1000);
		        } catch (e) {}}
		    else if(IS.isAndroid){
		        try{window.yx129.appHideNavRefresh();
		        } catch (e) {}
		    }
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
					appPay();
				}else{
					console.log("支付发起失败");
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
		
		function appPay(){
        	var appId = '${pay.appId}';
			var prepayId = '${pay.prepayId}';
			var mchId = '${pay.mchId}';
			var nonceStr = '${pay.nonceStr}';
			var timeStamp = '${pay.timeStamp}';
			var package = "Sign=WXPay";
			var paySign = '${pay.paySign}';
			var successUrl = '${pay.successUrl}?${pay.payRemark}';
			/*var failMsg = new $Y.confirm({
				ok:{title:'确定',click:function(){
					failMsg.close();
					closeWindow();
				}},
				content:'<div style="text-align: center">' + appId + ', ' + prepayId + ', ' + mchId + ', ' + nonceStr + ', ' + timeStamp + ', ' + package + ', ' + paySign + ', ' + successUrl + '</div>'
			})*/
        	if(IS.isMacOS){
            	appWXPay(appId,prepayId,mchId,nonceStr,timeStamp,package,paySign,successUrl);
        	}else if(IS.isAndroid){
				window.yx129.appWXPay(appId,prepayId,mchId,nonceStr,timeStamp,package,paySign,successUrl);
        	}
		}
	</script>
</body>
<#include "/mobileApp/common/yxw_footer.ftl">
</html>