<script>
	var ifm = $("#payFrame"); 
	<#if params.pay.frameHeight?exists && params.pay.frameHeight?? && params.pay.frameHeight gt 0>
		ifm.height(${params.pay.frameHeight});
		<#else>
		
		/*iframe 自适应*/
		
		//支付信息页面和支付页面不在统一域名，iframe 没法做到自适应
		//如果在一级域名相同，可以把 document.domain 都设置成一级域名，就可以骗过浏览器，从而达到 iframe 自适应的效果
		if ("${basePath}".indexOf("yx129.cn") != -1) {
			document.domain = "yx129.cn";
		}
		
		var finalHeight = 0;
		var forTimes = 0;
		
		function changeFrameHeight(){
		    //console.log(ifm);
		    var frameHeight = ifm.contents().find("body").height() + 5;
		    console.log("frameHeight: " + frameHeight);
		    if (frameHeight != finalHeight) {
		    	ifm.height(frameHeight);
		    	
		    	finalHeight = frameHeight;
		    	changeFrameHeight();
		    } else {
		    	//循环5次保证
		    	forTimes++;
		    	if (forTimes <= 5) {
		    		setTimeout(changeFrameHeight, 1000);
		    	}
		    }
		}
		window.onresize=function(){
		     changeFrameHeight();  
		}
		
		$(function(){
			$("#payFrame").load(function() {
				changeFrameHeight();
			});
		});
	</#if>
</script>