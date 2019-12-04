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
			 		<a href="${basePath}smartTriage/symptomBack" class="sidebar-item1"><i></i>症状库</a>
			 	</li>
			 	<li class="sidebar-nav-item">
			 		<a href="${basePath}smartTriage/diseaseBack" class="sidebar-item2"><i></i>疾病库</a>
			 	</li>
			 </ul> 
		</div>
	</div>
	<div class="header-infobar">
		<div class="item-name">
			<a href="">症状</a>
		</div>
	</div>
	<div class="main">
		<form id="symptomForm" method="post">
			<div class="form-wrap">
				<input type="hidden" name="id" value="${symptom.id}" />
				<div class="form-group">
					<label for="">症状名称</label>
					<input type="text" name="name" value="${symptom.name}">
				</div>
				<div class="form-group">
					<label for="">部位</label>
					<select name="part">
						<#if partData?exists>
			                <#list partData?keys as key> 
			                   	<#if key == symptom.part>
			                   		<option value="${key}" selected="selected">${partData[key]}</option>
			                   	<#else>
			                   		<option value="${key}">${partData[key]}</option>
			                   	</#if>
			                </#list>
			            </#if>
					<select>
				</div>
				<div class="form-group-inline">
					<label for="">是否显示</label>
					<#if symptom.isHide == 1>
						<input type="checkbox" name="isHide" value="1" checked="checked" onclick="isHiden(this);">
					<#else>
						<input type="checkbox" name="isHide" value="0" onclick="isHiden(this);">
					</#if>
					
				</div>
				<div>
					<input type="button" onclick="save()" value="保存">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
