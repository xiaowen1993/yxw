<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/sdk/yxw.js?v=1.0"></script>
<title>js-sdk-test</title>

<style type="text/css">
	#main { display: none; }
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
			<textarea id="showOpenId"></textarea>
		</div>
		
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
    }, '${pageContext.request.contextPath}');
    
    function test() {
      
    }
  </script>
</body>
</html>