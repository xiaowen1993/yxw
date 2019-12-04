<html>
<head>
	<#include "/symptom/common/common.ftl">
	<script src="${basePath}symptom/js/zhengZhuang.js"></script>
    <title>疾病库</title>
</head>
<body>
	<div class="sidebar">
		<div class="sidebar-nav">
			<ul class="sidebar-nav-ul">
			 	<li class="sidebar-nav-item sidebar-item-hover">
			 		<a href="#" class="sidebar-item1"><i></i>症状库</a>
			 	</li>
			 	<li class="sidebar-nav-item">
			 		<a href="${basePath}smartTriage/diseaseBack" class="sidebar-item2"><i></i>疾病库</a>
			 	</li>
			 </ul> 
		</div>
	</div>
	<div class="header-infobar">
		<div class="item-name">
			<a href="">症状库</a>
		</div>
	</div>
	<div class="main">
		<div class="main-wrap">
			<div class="btn-group-right">
				<span class="btn-add" onclick="addSymptom();">新增症状</span>
			</div>
			<div class="table-wrap">
				<table class="table">
					<tr>
						<th width="10%">序号</th>
						<th width="30%">症状</th>
						<th width="30%">部位</th>
						<th width="10%">展示</th>
						<th>操作</th>
					</tr>
					<#if symptoms?exists>
	              		<#list symptoms as symptom>
							<tr>
								<td>${symptom_index + 1}</td>
								<td>${symptom.name}</td>
								<td>
									<#if partData?exists>
						                <#list partData?keys as key> 
						                   	<#if key == symptom.part>
						                   		${partData[key]}
						                   	</#if>
						                </#list>
						            </#if>
								</td>
								<td>
									<#if symptom.isHide == 1>
										是
									<#else>
										否
									</#if>
								</td>
								<td>
									<span class="btn-edit" onclick="editSymptom('${symptom.id}')">编辑</span>
									<span class="btn-del" onclick="showHideSwitch('${symptom.id}','${symptom.isHide}')">
										<#if symptom.isHide == 1>
											隐藏
										<#else>
											显示
										</#if>
									</span>
								</td>
							</tr>
						</#list>
					<#else>
						<tr><td colspan="5">暂无数据</td></tr>
					</#if>
				</table>
			</div>
		</div>
	</div>
	<form id="symptomForm" method="post">
	<form>
</body>
</html>
