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
  		<h1>支付失败</h1>
		<p>支付状态：${pay.payState }</p>
		<p>支付单号(商户)：${pay.outTradeNo }</p>
		<p>appId：${pay.appId }</p>
		<p>金额：${pay.totalFee }</p>
		<p>商户号：${pay.mchId }</p>
		<p>商户key：${pay.key }</p>
		<p>回调结果：${pay.payFlag }</p>
		<p>失败原因：${pay.returnMsg }</p>
		<p>缓存前缀：${pay.payPrefix }</p>
		<p>缓存ID：${pay.field }</p>
  </body>
</html>
