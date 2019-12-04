<#if params.pay.timeout?exists && params.pay.timeout?? && params.pay.timeout!="">
	<div class="section">
        <div class="box-tips">
        	<input type="hidden" id="leftSecond" value="${params.pay.timeout}">
			<i class="icon-warn"></i>
            温馨提示：请在
            <span id="leftSecondSpan" style="color: red;"></span>
            分钟内完成支付。
        </div>
    </div>
    
    <script>
    	//倒计时
		function payTimer() {
			var leftSecond = $('#leftSecond').val();
			if (leftSecond > 0) {
				$("#leftSecondSpan").text(secondToMin(leftSecond));
				$("#leftSecond").val(--leftSecond);
				setTimeout(payTimer, 1000);
			} else {
				$("#leftSecondSpan").text("00:00");
				$("#payBtn").unbind("click");//禁用
				grayscale($("#payBtn"));
			}
		}
		
		function secondToMin(leftSecond) {
			var min = 0;
			var second = 0;
			if(leftSecond > 60){
				var min = parseInt(leftSecond / 60);
				var second = parseInt(leftSecond % 60);
				if(min < 10){
					min = "0" + min;
				}
				if(second < 10){
					second = "0" + second;
				}
			}else{
				second = leftSecond;
				if(second < 10){
					second = "0" + second;
				}
				min = "00";
			}
			return min + ":" + second;
		}
		
		$(function(){
			payTimer()
		});
    </script>
</#if>