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
			 		<a href="${basePath}smartTriage/diseaseBack" class="sidebar-item2"><i></i>疾病库</a>
			 	</li>
			 </ul> 
		</div>
	</div>
	<div class="header-infobar">
		<div class="item-name">
			<a href="">疾病</a>
		</div>
	</div>
	<div class="main">
		<form id="diseaseForm" method="post">
			<input type="hidden" name="id" value="${disease.id}">
			<div class="form-wrap">
				<div class="form-group">
					<label for="">症状名称</label>
					<input type="text" name="name" value="${disease.name}">
				</div>
				<div class="form-group">
					<label for="">概述</label>
					<textarea name="dummary" id="dummary">${disease.dummary}</textarea>
				</div>
				<div class="form-group">
					<label for="">关联症状  <span>按Ctrl可多选</span></label>
					<div class="guanLian">
						<div class="jiBing-select">
							<p>可选关联症状</p>
							<select name="" id="uncheckedSelect" multiple='multiple'>
	              				<#if symptomsUnCheck?exists>
	              					<#list symptomsUnCheck as symptom>
	              						<option value="${symptom.id}">${symptom.name}</option>
	              					</#list>
	              				</#if>
							</select>
						</div>
						<div class="jiBing-select-btn">
							<span class="" onclick="selectAdd()">添加>></span>
							<span class="" onclick="selectRemove()"><<移除</span>
						</div>
						<div class="jiBing-select">
							<p>已选关联症状</p>
							<select name="" id="checkedSelect" multiple='multiple'>
								<#if symptomsCheck?exists>
	              					<#list symptomsCheck as symptom>
	              						<option value="${symptom.id}">${symptom.name}</option>
	              					</#list>
	              				</#if>
							</select>
						</div>
					</div>
				</div>
				<div>
					<input type="button" onclick="save()" value="保存">
				</div>
			</div>
		</form>
	</div>
</body>
</html>
