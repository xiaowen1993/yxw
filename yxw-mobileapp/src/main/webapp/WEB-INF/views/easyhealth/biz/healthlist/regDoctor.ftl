<!DOCTYPE html>
<html>
<head>
<#include "/easyhealth/common/common.ftl">
<title>医生记录</title>
</head>
<body ontouchmove="$Y.hover.TouchMove(event)">
	<div id="body">
		<div id="myCenter">
			<div id="doctors">
			</div>
        <div>
	</div>
	
<form id="voForm" method="post" action="${basePath}mobileApp/register/doctorTime/index">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
	<input type="hidden" id="appCode" name="appCode" value="${appCode}">
	<input type="hidden" id="appId" name="appId" value="">
	<input type="hidden" id="hospitalId" name="hospitalId" value="" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="deptCode" name="deptCode" value="">
	<input type="hidden" id="deptName" name="deptName" value="">
	<input type="hidden" id="doctorCode" name="doctorCode" value="">
	<input type="hidden" id="regType" name="regType" value="" />
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}" />
	<input type="hidden" id="selectRegDate" name="selectRegDate" value="" />
	<input type="hidden" id="viaFlag" name="viaFlag" value="regDoctor" />
</form>
<#include "/easyhealth/common/footer.ftl">
</body>
<script type="text/javascript">
	$(function() {
		loadHistoryRegDoctors();
	});
	
	function loadHistoryRegDoctors() {
		$Y.loading.show('正在为您加载数据...');
		var url = '${basePath}easyhealth/healthlist/regDoctor/getDoctors';
		var now = new Date();
		var timeTemp = now.getTime();
		var datas = $('#voForm').serializeArray();
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
				// 加载错误，重新进入加载，后台确保没有异常 try..catch
				$('#voForm').attr('action', '${basePath}easyhealth/healthlist/regDoctor/index?timeTemp=' + timeTemp);
				$('#voForm').submit();
			},
			success: function(data) {
				console.log(data);
				if (data.status == 'OK' && data.message) {
					formatData(data.message);
					$Y.loading.hide();
				} else {
					$Y.loading.hide();
					showNoData();
				}
			}
		})
	}
	
	function formatData(data) {
		if (data.length > 0) {
			var sHtml = '';
			
			sHtml += '<ul class="yx-list userList">';
			$.each(data, function(i, item) {
				 sHtml += '<li class="pic arrow"' + 
				 		  ' data-hospitalCode="' + item.hospitalCode + '"' +
				 		  ' data-hospitalId="' + item.hospitalId + '"' +
				 		  ' data-hospitalName="' + item.hospitalName + '"' +
				 		  ' data-branchHospitalCode="' + item.branchHospitalCode + '"' +
				 		  // ' data-branchHospitalName="' + item.branchHospitalName + '"' +
				 		  ' data-branchHospitalId="' + item.branchHospitalId + '"' +
				 		  ' data-deptName="' + item.deptName + '"' +
				 		  ' data-deptCode="' + item.deptCode + '"' +
				 		  ' data-doctorName="' + item.doctorName + '"' +
				 		  ' data-doctorCode="' + item.doctorCode + '"' +
				 		  ' data-appId="' + item.appId + '"' +
				 		  ' data-regType="' + getValue(item.regType) + '">';
	             sHtml += '	<img class="topVal" src="' + base.appPath + 'yxw.app/images/touxiang.png" width="60" height="60"/>';
	             sHtml += '	<div class="info">';
	             sHtml += '		<div class="title">' + item.doctorName + '</div>';
	             sHtml += '		<div class="mate color999">' + item.deptName + '</div>';
	             sHtml += '		<div class="mate color999">' + item.hospitalName + '</div>';
	             sHtml += '	</div>';
	             sHtml += '	<div class="item-right"></div>';
	             /*
	             if (getValue(item.regType) == '1') {
	             	sHtml += '		<span class="tag skinBgColor">预约</span>';
	             } else {
	             	sHtml += '		<span class="tag skinBgColor blue">当班</span>';
	             }
	             sHtml += '	</div>';
	             */
	             sHtml += '</li>';
			});
			sHtml += '</ul>';
			sHtml += '<div class="space15"></div>';
			
			$('#doctors').html('');
			$('#doctors').append(sHtml);
			
			bindClickEvent();
		} else {
			showNoData();
		}
	}
	
	function bindClickEvent() {
		$('.arrow').off('click').on('click', function() {
			$('#hospitalId').val($(this).attr("data-hospitalId"));
			$('#hospitalCode').val($(this).attr("data-hospitalCode"));
			$('#hospitalName').val($(this).attr("data-hospitalName"));
			$('#branchHospitalId').val($(this).attr("data-branchHospitalId"));
			$('#branchHospitalCode').val($(this).attr("data-branchHospitalCode"));
			$('#deptName').val($(this).attr("data-deptName"));
			$('#deptCode').val($(this).attr("data-deptCode"));
			$('#doctorName').val($(this).attr("data-doctorName"));
			$('#doctorCode').val($(this).attr("data-doctorCode"));
			$('#regType').val($(this).attr("data-regType"));
			$('#appId').val($(this).attr("data-appId"));
			// $('#selectRegDate').val(getRegDate($(this).attr("data-regType")));
			$('#selectRegDate').val('');
			
			$('#voForm').submit();
		});
	};
	
	function showNoData() {
		var sHtml = '';
		
		sHtml += '<div class="noRecord">';
        sHtml += '	<div id="success">';
        sHtml += '		<div class="noticeEmpty"></div>';
        sHtml += '      <div class="p color666">没有找到您的医生记录</div>';
        sHtml += '  </div>';
        sHtml += '</div>';
        
		$('#doctors').html('');
		$('#doctors').append(sHtml);
	}
	
	function getValue(data) {
		if (data && data != 'null') {
			return data;
		} 
		
		return '';
	}
	
	function getRegDate(regType) {
		var regDate = '';
		
		if (!regType || regType == '2') {
			// 当班
			var myDate = new Date();
			var Y = myDate.getFullYear();
			var M = myDate.getMonth();
			var D = myDate.getDate();
			regDate = Y + '-' + M + '-' + D;
		} else {
			// 走预约
			var myDate = new Date();
			myDate.setDate(myDate.getDate() + 1);
			var Y = myDate.getFullYear();
			var M = myDate.getMonth();
			var D = myDate.getDate();
			regDate = Y + '-' + M + '-' + D;
		}
		
		return regDate;
	}
	
	function doRefresh()
	{
		window.location.href="${basePath}easyhealth/healthlist/regDoctor/index?openId=${commonParams.openId}&appCode=${appCode}&areaCode=${areaCode}";
	}
	
	function doGoBack()
	{
		windowClose();
	}
	
</script>
</html>