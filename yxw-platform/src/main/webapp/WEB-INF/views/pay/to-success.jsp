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
  		<h1>支付成功</h1>
		<form id="refund" action="<%=basePath%>refund/info/" method="post">
			<p>appId：<input type="hidden" name="appId" value="${pay.appId }" /></p><!--  -->
			<p>openId：<input type="hidden" name="openId" value="${pay.openId }" /></p>
			<p>mchId：<input type="hidden" name="mchId" value="${pay.mchId }" /></p>
			<p>subMchId：<input type="hidden" name="subMchId" value="" /></p>
			<p>key：<input type="hidden" name="key" value="${pay.key }" /></p>
			<p>totalFee：<input type="hidden" name="totalFee" value="1" /></p>
			<p>dealWithUrl：<input type="hidden" name="dealWithUrl" value="http%3A%2F%2Fhapi8.yx129.com%2Fyxw_framework%2Fpay%2FdealWith" /></p>
			<p>successUrl：<input type="hidden" name="successUrl" value="http%3A%2F%2Fwww.baidu.com" /></p>
			<p>failUrl：<input type="hidden" name="failUrl" value="http%3A%2F%2Fwww.qq.com" /></p>
			<p>payRemark：<input type="hidden" name="payRemark" value="payRemark" /></p>
			<p>attach：<input type="hidden" name="attach" value="attach" /></p>
			<p>attach：<input type="hidden" name="refundFee" value="1" /></p>
			<p>outTradeNo：<input type="text" name="outTradeNo" value="${pay.outTradeNo }" /></p>
			<p>transactionId：<input type="text" name="transactionId" value="${pay.transactionId }" /></p>
			<input type="submit" value="退款" />
    	</form>
  </body>
</html>
