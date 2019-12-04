<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<title>关注数据统计</title>

<!-- Bootstrap core CSS -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="bootstrap/css/font-awesome.min.css" rel="stylesheet">
<link href="bootstrap-datepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

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
<style type="text/css">
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th,
	.table>thead>tr>td, .table>thead>tr>th {
	padding: 8px;
	line-height: 1;
	border: 1px solid;
}

.table>caption+thead>tr:first-child>td, .table>caption+thead>tr:first-child>th,
	.table>colgroup+thead>tr:first-child>td, .table>colgroup+thead>tr:first-child>th,
	.table>thead:first-child>tr:first-child>td, .table>thead:first-child>tr:first-child>th
	{
	border-top: 1px solid;
}

.btn {
	margin-right: 20px;
}

.input-group{
	margin-right: 20px;
}

i.glyphicon-remove {
	opacity: .7;
	margin-left: 8px;
}
</style>
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">医享网数据中心</a>
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
					<li><a href="/index"><i class="fa fa-flag fa-lg"></i>&nbsp;&nbsp;数据中心</a></li>
					<li><a href="/hospital"><i class="fa fa-hospital-o fa-lg"></i>&nbsp;&nbsp;医院数据</a></li>
					<li class="active"><a href="/subscribe"><i class="fa fa-heart fa-lg"></i>&nbsp;&nbsp;关注数据</a></li>
					<li><a href="/numberAttr"><i class="fa fa-phone fa-lg"></i>&nbsp;&nbsp;号码归属地</a></li>
					<li><a href="/card"><i class="fa fa-address-card fa-lg"></i>&nbsp;&nbsp;绑卡数据</a></li>
					<li><a href="/orderCount"><i class="fa fa-area-chart fa-lg"></i>&nbsp;&nbsp;订单量</a></li>
					<li><a href="/orderAmount"><i class="fa fa-dollar fa-lg"></i>&nbsp;&nbsp;订单金额</a></li>
					<li><a href="/gender"><i class="fa fa-male fa-sm"></i><i class="fa fa-female fa-sm"></i>&nbsp;&nbsp;性别数据</a></li>
					<li><a href="/ageGroup"><i class="fa fa-tasks fa-lg"></i>&nbsp;&nbsp;年龄段数据</a></li>
				</ul>
			</div>
			<div class="col-sm-11 col-sm-offset-1 col-md-11 col-md-offset-1 main">
				<div class="row">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="panel-title">统计范围</h3>
						</div>
						<div class="panel-body">
						
						<form class="form-inline" role="form">
							<div class="btn-group">
								<div class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<span class="text">总区域</span> <span class="caret"></span>
								</div>
								<ul class="dropdown-menu" role="menu" id="areaList" data-value="-1">

								</ul>
							</div>
							
							<div class="btn-group">
								<div class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<span class="text">选择医院</span> <span class="caret"></span>
								</div>
								<ul class="dropdown-menu" role="menu" id="hospitalList" data-value="-1">

								</ul>
							</div>
							
							<div class="btn-group">
								<div class="btn btn-default dropdown-toggle" data-toggle="dropdown">
									<span class="text">本年度</span> <span class="caret"></span>
								</div>
								<ul class="dropdown-menu" role="menu" id="dateList" data-value="4">
									<li data-value="1"><a href="javascript: void(0);">上月</a></li>
									<li data-value="2"><a href="javascript: void(0);">近3月</a></li>
									<li data-value="3"><a href="javascript: void(0);">近半年</a></li>
									<li data-value="4"><a href="javascript: void(0);">本年度</a></li>
									<li data-value="5"><a href="javascript: void(0);">指定日期范围</a></li>
								</ul>
							</div>
							
							<div class="form-group">
				                <div id="startDate" class="input-group date form_datetime" style="display: none;" >
				                    <div class="input-group-addon">开始年月</div>
				                    <input class="form-control startDate" size=12 type="text" value="" data-toggle="tooltip" data-placement="top" title="请选择开始年月" readonly>
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
				            </div>
            
            				<div class="form-group">
				                <div id="endDate" class="input-group date form_datetime" style="display: none;">
				                    <div class="input-group-addon">结束年月</div>
				                    <input class="form-control endDate" size=12 type="text" data-toggle="tooltip" data-placement="top" title="请选择结束年月" value="" readonly>
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
				            </div>
				            
							<div class="btn btn-success" id="stats">
								<span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;统计
							</div>
						</form>
						</div>
					</div>
				</div>
				<div class="row" id="mainContent">
					<div class="col-xs-12" style="padding-left: 5px;">
						<ul class="nav nav-tabs" role="tablist">
							<!-- <li class="active" id="tab_mainStats"><a href="#mainStats" role="tab" data-toggle="tab">基本统计</a></li> -->
							<!-- <li id="tab_bbb"><a href="#bbb" role="tab" data-toggle="tab">bbb<i class="glyphicon glyphicon-remove" data-close="bbb"></i></a></li> -->
						</ul>
						<div class="tab-content">
							<!-- <div role="tabpanel" class="tab-pane active" id="mainStats">
								
							</div> -->
						</div>
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
	<script src="bootstrap-datepicker/js/bootstrap-datetimepicker.js"></script>
	<script src="bootstrap-datepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	
	<script src="assets/js/docs.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="assets/js/ie10-viewport-bug-workaround.js"></script>
	<script src="js/echarts/echarts.min.js"></script>
	<script src="js/chartOption.js"></script>
	<script src="js/yxw-exports.js"></script>
	<script src="js/commonTabs.js"></script>
	<script src="js/subscribe.js"></script>
	<script src="js/login.js"></script>
</body>
</html>
