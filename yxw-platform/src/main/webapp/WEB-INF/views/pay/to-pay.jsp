<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>去支付</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  		<h1>支付测试</h1>
    	<form action="<%=basePath%>pay/info/" method="post">
    		<p>appId：<input type="text" name="appId" value="wxc3678a507117e457" /></p><!--  -->
    		<p>openId：<input type="text" name="openId" value="o4bbdt53DGOE9Bi0KJms8Sg6jo0s" /></p>
    		<p>mchId：<input type="text" name="mchId" value="123" /></p>
    		<p>subMchId：<input type="text" name="subMchId" value="" /></p>
    		<p>key：<input type="text" name="key" value="key123456" /></p>
    		<p>totalFee：<input type="text" name="totalFee" value="1" /></p>
    		<p>outTradeNo：<input type="text" name="outTradeNo" value="1430731013662" /></p>
    		<p>body：<input type="text" name="body" value="%E6%B5%8B%E8%AF%95%E5%95%86%E5%93%81" /></p>
    		<p>spbillCreateIp：<input type="text" name="spbillCreateIp" value="127.0.0.1" /></p>
    		<p>nonceStr：<input type="text" name="nonceStr" value="A28DC2D7E0DB95B8" /></p>
    		<p>successUrl：<input type="text" name="dealWithUrl" value="http%3A%2F%2F127.0.0.1%2Fyxw_framework%2Fpay%2FdealWith" /></p>
    		<p>successUrl：<input type="text" name="successUrl" value="http%3A%2F%2F127.0.0.1%2Fyxw_framework%2Fpay%2FpayResult%3Fflag%3D1" /></p>
    		<p>failUrl：<input type="text" name="failUrl" value="http%3A%2F%2F127.0.0.1%2Fyxw_framework%2Fpay%2FpayResult%3Fflag%3D0" /></p>
    		<p>infoUrl：<input type="text" name="infoUrl" value="http%3A%2F%2Fhapi7.yx129.com%2Fsharewx%2Fshare%2FpayInfo" /></p>
    		<p>afterPayUrl：<input type="text" name="afterPayUrl" value="http%3A%2F%2Fwww.sina.com.cn" /></p>
    		<p>payRemark：<input type="type" name="payRemark" value="payRemark" /></p>
    		<p>attach：<input type="type" name="attach" value="attach" /></p>
    		<input type="submit" value="支付" />
    	</form>
  </body>
</html>
