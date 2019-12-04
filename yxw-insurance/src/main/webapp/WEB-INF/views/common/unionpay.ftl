<#if unionPayUPSDKParams?exists>
	<script src="${basePath}js/unionpay/zepto.min.js"></script>
	<#--<script src="${basePath}js/unionpay/require.js"></script>-->
	<script src="https://open.95516.com/s/open/js/upsdk.js"></script>
	<script type="text/javascript">
	    /**
		 * 银联钱包 UPSDK
		 */
		
		// 你应用后台签名返回的数据, 样本如下
		var resp = {
		    "appId":"${unionPayUPSDKParams.appid}",
		    "nonceStr":"${unionPayUPSDKParams.noncestr}",
		    "timestamp":"${unionPayUPSDKParams.timestamp}",
		    "url":"${unionPayUPSDKParams.url}",
		    "signature":"${unionPayUPSDKParams.signature}"
		};
		
		// 开发阶段可打开此调试标记, 生产版本请关掉。
		resp.debug = false;
		
		var yxUPSDK = {};
		// sdk is ready.
		window.upsdk.ready(function(){
			yxUPSDK.toast = function(msg) {
		    	window.upsdk.showFlashInfo({ msg: msg });
		    }
		    
		    yxUPSDK.getLocationCity = function() {
			    window.upsdk.getLocationCity({
			    	success: function(cityCd){
						getCityCode(cityCd);
					}
				});
			}
			
			yxUPSDK.scanQRCode = function() {
				window.upsdk.scanQRCode({
					scanType: ["qrCode","barCode"],
					success: function(result){
						scanQRCode(result);
					}
				});
			}
			
			
			//设置标题栏右边按钮接口
			//支持文字&图片
			//title/image 可任选其一, 同时有的话title优先
			yxUPSDK.setNavigationBarRightButton = function() {
				window.upsdk.setNavigationBarRightButton({
					title: '刷新',
					//image: '',
					handler: function() {
						//用户点击按钮后的回调函数
						//window.upsdk.closeWebApp();
						try {
							doRefresh();
						} catch (e) {
							//yxUPSDK.toast("刷新方法未定义");
							location.reload();
						}
					}
				});
			}
		
		    yxUPSDK.pay = function(tn) {
		    	window.upsdk.pay({
		            tn: tn,  // tn: Ticket Number 是银联在线后台返回
		            success: function() {
		            	//支付成功, 开发者执行后续操作
		            	paySuccess();
		            }, 
		            fail: function(err) {
		            	//支付失败, err.msg 是失败原因描述, 比如 TN 号不合法, 或者用户取消了交易等等。
		            	yxUPSDK.toast(err.msg);
		            }
		        });
		    }
		    
		    //##################
			yxUPSDK.setNavigationBarRightButton();
		});
		
		window.upsdk.error(function(err){
		    //alert(err.msg);
		});
		
		window.upsdk.config(resp);
	    
	</script>
</#if>