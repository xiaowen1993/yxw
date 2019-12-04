<html>
<head>
	<#include "/symptom/common/common.ftl">
	<script src="${basePath}symptom/js/jiBing.js"></script>
    <title>疾病库</title>
</head>
<body>
	<div class="sidebar">
		<div class="sidebar-nav">
			<ul class="sidebar-nav-ul">
			 	<li class="sidebar-nav-item ">
			 		<a href="${basePath}smartTriage/symptomBack" class="sidebar-item1"><i></i>症状库</a>
			 	</li>
			 	<li class="sidebar-nav-item sidebar-item-hover">
			 		<a href="#" class="sidebar-item2"><i></i>疾病库</a>
			 	</li>
			 </ul> 
		</div>
	</div>
	<div class="header-infobar">
		<div class="item-name">
			<a href="">疾病库</a>
		</div>
	</div>
	<div class="main">
		<div class="main-wrap">
			<div class="btn-group-right">
				<span class="btn-add" id="add-jiBing" onclick="addDisease();">新增疾病</span>
			</div>
			<div class="table-wrap">
				<table class="table">
					<tr>
						<th width="10%">序号</th>
						<th width="70%">疾病</th>
						<th>操作</th>
					</tr>
					<#if diseases?exists>
	              		<#list diseases as disease>
	              			<tr>
								<td>${disease_index + 1}</td>
								<td>${disease.name}</td>
								<td>
									<span class="btn-edit" onclick="editDisease('${disease.id}')">编辑</span>
									<span class="btn-del">删除</span>
								</td>
							</tr>
	              		</#list>
					<#else>
						<tr><td colspan="3">暂无数据</td></tr>
					</#if>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
