<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    
    <title>银联测试-Demo</title>
</head>
<body>

	<div id="body" style="padding: 10px;">
		<br>
		<button onclick="yxUPSDK.toast('我是弹出框');">弹出框</button>
		<button onclick="yxUPSDK.getLocationCity();">获取城市</button>
		<button onclick="yxUPSDK.scanQRCode();">扫一扫</button>
		
		<br><br>
		<#--<button onclick="yxUPSDK.setNavigationBarRightButton();">设置右边按钮</button>-->
		<button onclick="window.upsdk.closeWebApp();">关闭WebView</button>
		
		<br><br>
		TN: <input type="text" id="unionPayTN" value="9032422455662288">
		<button onclick="yxUPSDK.pay($('#unionPayTN').val());">支付</button>
		
		<br><br>
		<input type="text" id="custUrl" value="">
		<button onclick="location.href=$('#custUrl').val();">跳转页面</button>
		
		<textarea style="width: 95%; height: 300px;">${userInfoJson}</textarea>
		
	</div>
	
	<#include "/easyhealth/common/footer.ftl">
	
	<script type="text/javascript">
		function getCityCode(cityCd) {
			alert("cityCd = " + cityCd);
		}
		
		function scanQRCode(result) {
			alert('Scan result = ' + result);
		}
		
		function doRefresh() {
			//alert("请自定义刷新逻辑");
			location.reload();
		}
	    
	    
	</script>
	
</body>
</html>