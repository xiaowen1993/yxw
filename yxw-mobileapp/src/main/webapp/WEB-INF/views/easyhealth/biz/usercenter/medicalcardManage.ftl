<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>就诊卡管理</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)" style="background-color: rgb(238, 238, 238);">
<div id="body">
	<div class="screeningBox cardManagement-select">
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem">
                    <label><span class="text" value="0">全部医院</span>
                    <select id="hospitalFilter" name="hospitalFilter" class="select-screen-box" onchange="filterData(1);">
                        <option value="0">全部医院</option>
                        <#list entityList as item>
                        	<option value="${item.hospCode}">${item.hospName}</option>
                        </#list>
                    </select>
                        <i class="iconfont">&#xe66d;</i>
                    </label>
                </div>
                <div class="flexItem">
                    <label><span class="text" value="0" data-name="">全部就诊人</span>
                    <select id="patientFilter" name="patientFilter" class="select-screen-box"  onchange="filterData(2);">
                    	<option value="0">全部就诊人</option>
                    </select>
                        <i class="iconfont">&#xe66d;</i>
                    </label>
                </div>
            </li>
        </ul>
    </div>
    
    <div id="hasCard" style="display: none;">
	    <ul class="yx-list" id="cardManagement-discoveryList" id="bindCardWithHosptial" style="display: none;">
	        <li onclick="bindCardWithHosptial();">
	            <div class="cardManagement-discoveryList-info">
	                <div class="title">添加医院就诊卡</div>
	            </div>
	            <div class="hospital-appointment">
	                <span class="add">去添加</span>
	            </div>
	        </li>
	    </ul>
		
	    <ul class="yx-list cardSelect-list" id="card-list">
	    </ul>
	</div>
	<div id="hasNoCard" style="display: none;">
		<div id="success">
	        <div class="noPeople"></div>
	        <div class="p color666">没有任何就诊卡</div>
		</div>
		<div class="btn-w" id="bindCard">
		    <div class="btn btn-ok btn-block" onclick="bindCard();">添加就诊卡</div>
		</div>
	</div>
</div>

<form id="voForm" method="post" action="">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}">
	<input type="hidden" id="hospitalId" name="hospitalId" value="" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="forward" name="forward" value="${basePath}mobileApp/medicalcard/manage/toView" />
</form>

<input type="hidden" id="defaultHospitalCode" name="defaultHospitalCode" value="" />
<input type="hidden" id="defaultCardId" name="defaultCardId" value="" />
<input type="hidden" id="defaultPatName" name="defaultPatName" value="" />
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script>
	var dataSource = {};
	var hospitalBindCardNums = '${hospitalBindCardNums}';	
	
	$(function() {
		/*
		var datas = $('#voForm').serializeArray();
		var items = [];
		for (var i=0; i<3; i++) {
			var item = {};
			item.hospitalCode = 'hospitalCode_' + i;
			item.branchCode = 'branchCode_' + i;
			items.push(item);
		}
		datas.items = items;
		console.log(datas);
		*/
		loadCards();
	})
	
	function loadCards() {
		$Y.loading.show('正在加载就诊人信息...');
		setTimeout(function() {
			var datas = $('#voForm').serializeArray();
			var url = '${basePath}/mobileApp/medicalcard/manage/getAllCards';
			var now = new Date();
			var timeTemp = now.getTime();
			$.ajax({
				url: url + '?timeTemp=' + timeTemp,
				data: datas,
				type: "post",
				dataType: "json", 
				global: false,
				cache: false,
				timeout: 5000,
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					$Y.loading.hide();
					$('#hasNoCard').show();
				},
				success: function(data) {
					$Y.loading.hide();
					console.log(data);
					if (data.status == 'OK' && data.message.entityList && data.message.entityList.length > 0) {
						dataSource = data.message.entityList;
						formatData(data.message.entityList);
					} else {
						$('#hasNoCard').show();
					}
				}
			});
		}, 200);
	}
	
	function formatData(entityList) {
		var sHtml = '';
		$('#patientFilter').html('');
		$('#patientFilter').append('<option value="0" data-name="">全部就诊人</option>');
		
		$.each(entityList, function(i, item) {
			// 初始化下拉框。
			if ($('#patientFilter').find('option[value="' + item.name + '"]').length == 0) {
				$('#patientFilter').append('<option value="' + item.name + '">' + item.encryptedPatientName + '（证件：' + item.encryptedIdNo + '）' + '</option>');
			}
			
			// 初始化就诊卡数据
			sHtml += '<li class="arrow" onclick="">';
	        sHtml += '	<div class="list-content">';
	        sHtml += '		<div class="title">' + item.encryptedPatientName + '（' + item.cardNo + '）</div>';
	        sHtml += '		<div class="mate color999">' + item.hospitalName + '</div>';
	        sHtml += '	</div>';
	        sHtml += '</li>';
		});
		
		$('#card-list').html('');
		$('#card-list').html(sHtml);
		$('#hasCard').show();
		$('#hasNoCard').hide();
		$('#bindCardWithHosptial').hide();
	}
	
	function filterData(obj) {
		if (obj == '1') {
		
		}
	}
	
	// fillChar 会只拿一位。
	String.prototype.format = function(str, length, fillChar) {
		// 处理填充
		if (fillChar && fillChar.length > 0) {
			fillChar = fillChar.substring(0, 1);
			console.log(fillChar);
		} else {
			fillChar = ' ';
		}
		
		
	}
	
	function bindCard() {
		console.log('没医院。去你妹的。');
	}
	
	function bindCardWithHosptial() {
		console.log('有医院。你大爷的。');
	}
</script>