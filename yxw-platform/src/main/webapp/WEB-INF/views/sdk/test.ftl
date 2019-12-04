<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#include "/common/common.ftl">
<script type="text/javascript" src="${basePath}/js/sdk/yxw.js?v=1.0"></script>
<title>js-sdk-test</title>

<style type="text/css">
	/* #main { display: none; } */
	#main input { width: 90%; height: 30px; margin: 3px; }
	#main p { text-align: left; }
	#main textarea { width: 90%; height: 60px; border: 0px; }
</style>
</head>
<body>
	<center>
		<div id="main">
			<input type="button" value="关闭" onclick="yxw.closeWindow();" />
			<input type="button" value="隐藏右上角菜单" onclick="yxw.hideOptionMenu();" />
			<input type="button" value="显示右上角菜单" onclick="yxw.showOptionMenu();" />
			<input type="button" value="获取网络状态" onclick="yxw.getNetworkType(function(networkType) { alert(networkType); });" />
			
			
			<input type="button" value="test1" onclick="test();" />
			
			<p>Web Browser UA : </p>
			<textarea id="showUA"></textarea>
			<p>Get OpenId Debug : </p>
			<textarea id="showOpenId">${openId}</textarea>
		</div>
		<#include "/common/copyright_footer.ftl">
	</center>
	<script type="text/javascript">
    yxw.init(function(data) {
      if (data.success) {
        $('#showUA').text(yxw.ua);
        
        $('#main').show();
        /*----------------------------*/
        yxw.hideOptionMenu(); //隐藏右上角菜单
        
				
        //test();
      }
    }, '${basePath}');
    
    function test() {
      
    }
  </script>
</body>
</html>