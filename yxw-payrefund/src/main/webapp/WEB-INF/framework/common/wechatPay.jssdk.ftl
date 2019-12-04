<#if params.wechatJSSDKParams?exists>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript">
		// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
		var jsApiList = ["chooseWXPay", "hideAllNonBaseMenuItem", "scanQRCode", "closeWindow"];
	
		wx.config({
			// 开启调试模式,调用的所有api的返回值会在客户端alert出来，
			// 若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    debug: false, 
		    appId: '${params.wechatJSSDKParams.appid}', // 必填，公众号的唯一标识
		    timestamp: '${params.wechatJSSDKParams.timestamp}', // 必填，生成签名的时间戳
		    nonceStr: '${params.wechatJSSDKParams.noncestr}', // 必填，生成签名的随机串
		    signature: '${params.wechatJSSDKParams.signature}',// 必填，签名，见附录1
		    jsApiList: jsApiList
		});
		
		wx.ready(function(){
		    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
		    // config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
		    // 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		    
		    
		    // 隐藏所有非基础按钮接口
		    wx.hideAllNonBaseMenuItem();
		});
		
		wx.error(function(res){
		    // config信息验证失败会执行error函数，如签名过期导致验证失败，
		    // 具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		    
		    //alert(JSON.stringify(res));
		});
		
	</script>
</#if>

<#if params.wechatPayJSParams?exists>
	<script type="text/javascript">
		function pay() {
			wx.chooseWXPay({
				// 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。
				// 但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
				"timestamp": "${params.wechatPayJSParams.timeStamp}",
	            "nonceStr": "${params.wechatPayJSParams.nonceStr}",
	            "package": "${params.wechatPayJSParams.package}",
	            "signType": "${params.wechatPayJSParams.signType}",
	            "paySign": "${params.wechatPayJSParams.paySign}",
			    success: function (res) {
			        // 支付成功后的回调函数
			        console.log(res);
			        // 判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		    	    if(res.errMsg == "chooseWXPay:ok" ) {
		    	       paySuccess();
		    	    } else if (res.errMsg == "chooseWXPay:cancel") {
		    	       //支付过程中用户取消
		    	    } else if (res.errMsg == "chooseWXPay:fail") {
		    	       //支付失败
		    	    } else {
			    	   alert(res.errMsg);
		    	    }
			    }
			});
		}
	
	
		<#--
	    function pay() {
			 WeixinJSBridge.invoke(
		       'getBrandWCPayRequest', {
		           "appId": "${params.wechatPayJSParams.appId}",
		           "timeStamp": "${params.wechatPayJSParams.timeStamp}",
		           "nonceStr": "${params.wechatPayJSParams.nonceStr}",
		           "package": "${params.wechatPayJSParams.package}",
		           "signType": "${params.wechatPayJSParams.signType}",
		           "paySign": "${params.wechatPayJSParams.paySign}"
		       },
		       function(res){
		    	   console.log(res);
		    	   
		    	   // 判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
		    	   if(res.err_msg == "get_brand_wcpay_request:ok" ) {
		    	       paySuccess();
		    	   } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
		    	       //支付过程中用户取消
		    	   } else if (res.err_msg == "get_brand_wcpay_request:fail") {
		    	       //支付失败
		    	   } else {
			    	   alert(res.err_msg);
		    	   }
		    	   
		       }
		   );
		}
		-->

		<#--
		if (typeof WeixinJSBridge == "undefined"){
		   if( document.addEventListener ){
		       document.addEventListener('WeixinJSBridgeReady', pay, false);
		   }else if (document.attachEvent){
		       document.attachEvent('WeixinJSBridgeReady', pay); 
		       document.attachEvent('onWeixinJSBridgeReady', pay);
		   }
		}else{
		   //pay();
		}
		-->
		
	</script>
</#if>