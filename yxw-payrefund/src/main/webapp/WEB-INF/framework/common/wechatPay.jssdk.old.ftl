<#if params.wechatPayJSParams?exists>
	<script type="text/javascript">
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

		if (typeof WeixinJSBridge == "undefined"){
		   if( document.addEventListener ){
		       document.addEventListener('WeixinJSBridgeReady', pay, false);
		   }else if (document.attachEvent){
		       document.attachEvent('WeixinJSBridgeReady', pay); 
		       document.attachEvent('onWeixinJSBridgeReady', pay);
		   }
		}else{
		   //pay();
		   WeixinJSBridge.call('hideOptionMenu');//隐藏分享按钮，需要显示请把hideOptionMenu换成showOptionMenu
		}
		
	</script>
</#if>