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
<title>医院信息</title>
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
								<li class="step1 active">
									<a href="#">
										<span class="step">医院</span> <span class="line"></span>
									</a>
								</li>
								<li class="step2">
									<a href="#">
										<span class="step">分院</span> <span class="line"></span>
									</a>
								</li>
								<li class="step3">
									<a href="#">
										<span class="step">平台</span> <span class="line"></span>
									</a>
								</li>
							</ul>
						</div>
						<div class="divTable">
							<table class="optTable">
								<tbody>
									<tr>
										<td width="20%" class="name">
											<div>医院名称</div>
										</td>
										<td width="40%">
											<input id="inputName" class="ui-form-input" type="text" value="${commonParams.hospitalName}" name="" placeholder="请输入医院名称">
										</td>
										<td>
											<div id="checkName">
												<p class="text-info"></p>
											</div>
										</td>
									</tr>
									<tr>
										<td width="20%" class="name">
											<div>医院code</div>
										</td>
										<td width="40%">
											<input id="inputCode" class="ui-form-input" type="text" value="${commonParams.hospitalCode}" name="" placeholder="请输入医院code">
										</td>
										<td>
											<div id="checkCode">
												<p class="text-info"></p>
											</div>
										</td>
									</tr>
									<tr>
										<td width="20%" class="name">
											<div>医院当前状态</div>
										</td>
										<td width="40%">
											<select id="stateSelect">
												<option value="0">已签约</option>
												<option value="1">已上线</option>
											</select>
										</td>
										<td></td>
									</tr>
									<tr>
										<td width="20%" class="name">
											<div>区域</div>
										</td>
										<td width="60%">
											<div id="area">
												<select class="prov" id="area_prov"></select>
												<select class="city" id="area_city"></select>
											</div>
										</td>
										<td>
											<div id="checkArea">
												<p class="text-info"></p>
											</div>
										</td>
									</tr>
									<tr class="optTr">
										<td width="20%" class="name"></td>
										<td width="40%">
											<div class="ui-btn" id="saveBtn">下一步</div>
										</td>
									</tr>
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

<script src="${basePath}js/jQuery/jquery.min.js"></script>
<script src="${basePath}bootstrap/js/bootstrap.min.js"></script>
<script src="${basePath}assets/js/docs.min.js"></script>
<script src="${basePath}js/hospital/jquery.cityselect.js"></script>
<script src="${basePath}js/hospital/hospital.js"></script>
</html>
