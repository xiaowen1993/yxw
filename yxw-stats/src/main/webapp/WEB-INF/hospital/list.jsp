<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!--     <link rel="icon" href="../../favicon.ico"> -->
<title>医院管理</title>
<!-- Bootstrap core CSS -->
<link href="${basePath}bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${basePath}bootstrap/css/font-awesome.min.css" rel="stylesheet">
<link href="${basePath}css/hospital.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="space20"></div>
		<div class="row-fluid">
			<div class="pull-right" id="search">
				<div class="input-group" style="width: 400px">
					<input type="text" class="form-control" />
					<div class="input-group-btn">
						<div class="btn btn-primary btnSearch">
							<span class="glyphicon glyphicon-search"></span>&nbsp;&nbsp;查找
						</div>
						<div class="btn btn-success btnAdd">
							<span class="glyphicon glyphicon-plus"></span>&nbsp;&nbsp;添加
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="space20"></div>
		<div class="row-fluid">
			<div class="space20"></div>
			<div class="widget-box">
				<div class="widget-content">
					<div class="row-fluid">
						<table class="table table-bordered table-textCenter table-striped table-hover">
							<thead>
								<tr>
									<th width="75px"></th>
									<th width="23%">医院</th>
									<th width="24%">地区</th>
									<th width="40%">平台</th>
									<th style="min-width: 120px;">操作</th>
								</tr>
							</thead>
							<tbody>

							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="voForm" name="voForm" method="post" action="">
		<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
		<input type="hidden" id="hospitalName" name="hospitalName" value="" />
		<input type="hidden" id="hospitalId" name="hospitalId" value="" />
		<input type="hidden" id="areaName" name="areaName" value="" />
		<input type="hidden" id="areaCode" name="areaCode" value="" />
		<input type="hidden" id="state" name="state" value="" />
	</form>
</body>
<script src="${basePath}js/jQuery/jquery.min.js"></script>
<script src="${basePath}bootstrap/js/bootstrap.min.js"></script>
<script src="${basePath}assets/js/docs.min.js"></script>
<script src="${basePath}js/hospital/list.js"></script>
</html>
