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
<title>配置信息</title>
<!-- Bootstrap core CSS -->
<link href="${basePath}bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="${basePath}bootstrap/css/font-awesome.min.css" rel="stylesheet">
<link href="${basePath}css/hospital.css" rel="stylesheet">
</head>
<body>
	<div id="content">
		<div class="container-fluid">
			<div class="inner">
				<div class="row-fluid">
					<div class="views">
						<div class="stepBox">
							<ul class="steps">
								<li class="step1">
									<a href="#">
										<span class="step">医院</span> <span class="line"></span>
									</a>
								</li>
								<li class="step2">
									<a href="#">
										<span class="step">分院</span> <span class="line"></span>
									</a>
								</li>
								<li class="step3 active">
									<a href="#">
										<span class="step">平台</span> <span class="line"></span>
									</a>
								</li>
							</ul>
						</div>
						<div class="termBox">
							<div class="ui-select-wrap">
								<span> 平台：</span>
								<select class="ui-form-select" id="platformSelector">
									<option value="-1" data-code="-1">请选择平台类型</option>
									<c:forEach items="${platforms }" var="platform">
										<option value="${platform.id}" data-code="${platform.code}">${platform.name}</option>
									</c:forEach>
								</select>
							</div>
							<div class="ui-select-wrap">
								<span> 支付方式：</span>
								<select class="ui-form-select" id="tradeModeSelector">
									<option value="-1">请选择支付方式</option>
									<%-- <c:forEach items="${tradeModes }" var="tradeMode">
										<option value="${tradeMode.id}">${tradeMode.name}</option>
									</c:forEach> --%>
								</select>
							</div>
							<div class="ui-input-wrap">
								<span>appId：</span>
								<input class="ui-form-input" type="text" value="" id="inputAppId" placeholder="请输入appId">
							</div>
							<button class="ui-btn ui-btn-lg" id="saveSetting">保存</button>
						</div>
						<div class="divTable">
							<table>
								<thead>
									<tr>
										<th width="30%">平台</th>
										<th width="30%">支付方式</th>
										<th width="30%">APPID</th>
										<th>管理</th>
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
	</div>
</body>
<form id="voForm" name="voForm" method="post" action="">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode }" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName }" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId }" />
	<input type="hidden" id="areaName" name="areaName" value="${commonParams.areaName }" />
	<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode }" />
	<input type="hidden" id="state" name="state" value="${commonParams.state }" />
</form>

<input type="hidden" name="sourceId" id="sourceId" value="${commonParams.hospitalId}" />
<input type="hidden" name="sourceCode" id="sourceCode" value="${commonParams.hospitalCode}" />
<input type="hidden" name="sourceName" id="sourceName" value="${commonParams.hospitalName}" />
<input type="hidden" name="sourceAreaCode" id="sourceAreaCode" value="${commonParams.areaCode}" />
<input type="hidden" name="sourceState" id="sourceState" value="${commonParams.state}" />
<input type="hidden" name="appPath" id="appPath" value="${basePath}" />
<input type="hidden" name="tradeModes" id="tradeModes" value="${tradeModes}" />

<script src="${basePath}js/jQuery/jquery.min.js"></script>
<script src="${basePath}bootstrap/js/bootstrap.min.js"></script>
<script src="${basePath}assets/js/docs.min.js"></script>
<script src="${basePath}js/hospital/setting.js"></script>
</html>
