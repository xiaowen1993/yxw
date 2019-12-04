<html>
<head>
	<#include "/mobileApp/common/common.ftl">
	<title>支付</title>
	<script type="text/javascript" src="${basePath}mobileApp/js/common/grayscale.js"></script>
</head>
<body>
	<div id="body">
		<iframe src="${pay.infoUrl}?${pay.payRemark}" width="100%" align="center" height="250" id="win" name="win" onload="Javascript:SetWinHeight(this)" frameborder="0" scrolling="no"></iframe>
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
	
		<!--<input type="button" value="暂不支付" onclick="afterPay(this);" style="background-color: green; height: 60px; width: 100px;">-->
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
	
		function pay(obj){
			if('${leftSecond}'){
				var leftSecond = $('#leftSecond').val() - 1;
	  			if(leftSecond > 0){
	  				appPay();
	  			}
	  		} else {
	  			appPay();
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
		
		function appPay(){
        	if(IS.isMacOS){
            	appAliPay(
            		'${pay.mchId}',			// 签约合作者身份ID
            		'${pay.mchAccount}',	// 签约卖家支付宝账号
            		'${pay.privateKey}',	// 商户私钥，pkcs8格式
            		'${pay.orderNo}',		// 商户网站唯一订单号
            		'${pay.subject}',		// 商品名称
            		'${pay.subject}',		// 商品详情
            		'${pay.totalFee}',		// 商品金额 
            		'${pay.notifyUrl}',		// 服务器异步通知页面路径
            		'${pay.successUrl}'		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径
				);
        	}else if(IS.isAndroid){
	        	window.yx129.appAliPay(
            		'${pay.mchId}',			// 签约合作者身份ID
            		'${pay.mchAccount}',	// 签约卖家支付宝账号
            		'${pay.privateKey}',	// 商户私钥，pkcs8格式
            		'${pay.orderNo}',		// 商户网站唯一订单号
            		'${pay.subject}',		// 商品名称
            		'${pay.subject}',		// 商品详情
            		'${pay.totalFee}',		// 商品金额 
            		'${pay.notifyUrl}',		// 服务器异步通知页面路径
            		'${pay.successUrl}'		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径
				);
        	}
		}
	</script>
</body>
<#include "/mobileApp/common/yxw_footer.ftl">
</html>