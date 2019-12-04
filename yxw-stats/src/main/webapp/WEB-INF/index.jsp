<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!--     <link rel="icon" href="../../favicon.ico"> -->

<title>数据中心</title>

<!-- Bootstrap core CSS -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/font-awesome.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/dashboard.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">
					医享网数据中心
				</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">用户名：${userName}</a></li>
					<li id="logout"><a href="javascript:void(0);">安全退出</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1 col-md-1 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="/index"><i class="fa fa-flag fa-lg"></i>&nbsp;&nbsp;数据中心</a></li>
					<li><a href="/hospital"><i class="fa fa-hospital-o fa-lg"></i>&nbsp;&nbsp;医院数据</a></li>
					<li><a href="/subscribe"><i class="fa fa-heart fa-lg"></i>&nbsp;&nbsp;关注数据</a></li>
					<li><a href="/numberAttr"><i class="fa fa-phone fa-lg"></i>&nbsp;&nbsp;号码归属地</a></li>
					<li><a href="/card"><i class="fa fa-address-card fa-lg"></i>&nbsp;&nbsp;绑卡数据</a></li>
					<li><a href="/orderCount"><i class="fa fa-area-chart fa-lg"></i>&nbsp;&nbsp;订单量</a></li>
					<li><a href="/orderAmount"><i class="fa fa-dollar fa-lg"></i>&nbsp;&nbsp;订单金额</a></li>
					<li><a href="/gender"><i class="fa fa-male fa-sm"></i><i class="fa fa-female fa-sm"></i>&nbsp;&nbsp;性别数据</a></li>
					<li><a href="/ageGroup"><i class="fa fa-tasks fa-lg"></i>&nbsp;&nbsp;年龄段数据</a></li>
				</ul>
			</div>
			<div class="col-sm-11 col-sm-offset-1 col-md-11 col-md-offset-1 main">
				<h1 class="page-header">关键数据</h1>

				<div class="row placeholders">
					<div class="col-xs-6 col-sm-4 placeholder">
						<p class="bg-danger dashboard">
							总数<strong id="hospitalCount">...</strong>
						</p>
						<!-- <h4>医院数目</h4> -->
						<span class="text-muted">上线医院总数</span>
					</div>
					<div class="col-xs-6 col-sm-4 placeholder">
						<p class="bg-success dashboard">
							微信<strong id="wxSubscribe">...</strong>支付宝<strong id="aliSubscribe">...</strong>
						</p>
						<!-- <h4>关注数</h4> -->
						<span class="text-muted">微信关注数、支付宝关注数（累计）</span>
					</div>
					<div class="col-xs-6 col-sm-4 placeholder">
						<p class="bg-info dashboard">
							总数<strong id="cardCount">...</strong>
						</p>
						<!-- <h4>绑卡数</h4> -->
						<span class="text-muted">总绑卡数（累计）</span>
					</div>
					<div class="col-xs-6 col-sm-6 placeholder">
						<p class="bg-warning dashboard">
							微信<strong id="wxOrderCount">...</strong>支付宝<strong id="aliOrderCount">...</strong>
						</p>
						<!-- <h4>订单数</h4> -->
						<span class="text-muted">总订单数（累计）</span>
					</div>
					<div class="col-xs-6 col-sm-6 placeholder">
						<p class="bg-primary dashboard">
							总交易金额<strong id="orderAmount">...</strong>
						</p>
						<!--  <h4>交易金额</h4> -->
						<span class="text-muted">总交易金额（累计）</span>
					</div>
				</div>

				<h2 class="sub-header">数据概况</h2>
				<div class="row placeholders">
					<div class="col-md-12 placeholder">
						<div id="hospitalResume">区域医院概述如下</div>
						<div id="hospital" style="width: 100%; height: 600px;"></div>
					</div>
					<hr />
					<div class="col-md-12 placeholder">
						<div id="cardResume">绑卡信息概述如下</div>
						<div id="card" style="width: 100%; height: 600px;"></div>
					</div>
					<hr />
					<div class="col-md-12 placeholder">
						<div id="orderResume">订单数量概述如下</div>
						<div class="hints">各医院挂号、门诊、押金订单分布（含支付、未支付）</div>
						<div id="order" style="width: 100%; height: 600px;"></div>
					</div>
					<!-- 
					<div class="col-md-12 placeholder">
						<div id="amountResume">订单总额概述如下</div>
						<div id="amount" style="width: 100%; height: 600px;"></div>
					</div>
					 -->
					<hr />
					<div class="col-md-12 placeholder">
						<div id="subscribeResume">医院关注数概述如下</div>
						<div class="hints">微信公众号的关注数（支付宝服务窗的关注数暂不提供）</div>
						<div id="subscribe" style="width: 100%; height: 600px;"></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="js/jQuery/jquery.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="js/echarts/echarts.min.js"></script>
	<script src="js/yxw-util.js"></script>
	<script src="js/index.js"></script>
	<script src="js/login.js"></script>

	<script src="assets/js/docs.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="assets/js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>
