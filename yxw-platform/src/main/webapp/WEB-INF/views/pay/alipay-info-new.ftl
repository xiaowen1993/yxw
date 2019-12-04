<html>
<head>
	<#include "/mobileApp/common/common.ftl">
	<title>支付</title>
	<script type="text/javascript" src="${basePath}mobileApp/js/common/grayscale.js"></script>
</head>
<body>
	<div id="body">
		<iframe src="${pay.infoUrl}?${pay.payRemark}" width="100%" align="center" height="250" id="win" name="win" onload="Javascript:SetWinHeight(this)" frameborder="0" scrolling="no"></iframe>
	 	<!--<form id="alipaysubmit" name="alipaysubmit" action="${ALIPAY_GATEWAY_NEW}_input_charset=${INPUT_CHARSET}" method="get">
			<input type="hidden" name="subject" value="${pay.subject }"/>
			<input type="hidden" name="sign_type" value="RSA"/>
			<input type="hidden" name="notify_url" value="${pay.notifyUrl }"/>
			<input type="hidden" name="out_trade_no" value="${pay.orderNo }"/>
			<input type="hidden" name="return_url" value="${pay.successUrl }?${pay.payRemark}"/>
			<input type="hidden" name="sign" value="${sign}"/>
			<input type="hidden" name="_input_charset" value="utf-8"/>
			<input type="hidden" name="total_fee" value="0.01"/>
			<input type="hidden" name="service" value="alipay.wap.create.direct.pay.by.user"/>
			<input type="hidden" name="partner" value="${pay.mchId }"/>
			<input type="hidden" name="seller_id" value="${pay.mchId }"/>
			<input type="hidden" name="payment_type" value="1"/>
  		</form>-->
  		${sHtmlText}
  		<#if leftSecond?exists>
	  		<div class="section">
		        <div class="box-tips"> <i class="icon-warn"></i>
		        	<input type="hidden" id="leftSecond" value="${leftSecond}">
		                            温馨提示：请在<span id="leftSecondSpan" style="color: red;"></span>分钟内完成支付，逾期号源将会作废，需要重新挂号。
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
	  				jQuery("#alipaysubmit").submit();
	  			}
	  		} else {
	  			jQuery("#alipaysubmit").submit();
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
	</script>
</body>
<#include "/mobileApp/common/yxw_footer.ftl">
</html>