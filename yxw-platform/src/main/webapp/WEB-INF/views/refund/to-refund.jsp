<%@ page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
	<title>支付</title>
	<script src="js/jquery-1.11.2.min.js"></script>
</head>
<body style="font: 13px Verdana; background: #eee; color: #333">
	<h1>退款结果页面</h1>
	<p>退款结果：${refund.refundFlag }</p>
	<p>退款单号(微信)：${refund.refundId }</p>
	<p>支付单号(微信)：${refund.transactionId }</p>
	<p>退款单号(商户)：${refund.outRefundNo }</p>
	<p>支付单号(商户)：${refund.outTradeNo }</p>
	<p>appId：${refund.appId }</p>
	<p>总金额：${refund.totalFee }</p>
	<p>退款金额：${refund.refundFee }</p>
	<p>商户号：${refund.mchId }</p>
	<p>商户key：${refund.key }</p>
</body>
</html>

