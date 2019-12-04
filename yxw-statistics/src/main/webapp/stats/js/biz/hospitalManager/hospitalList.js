var hospitalList = {
	init: function() {
		// 新增
		$('.btn-save').off('click').on('click', function() {
			hospitalList.addHospital();
		});
		
		// 搜索
		$('#searchBtn').off('click').on('click', function() {
			var hospitalName = $(this).siblings().val();
			hospitalList.findHospital(hospitalName);
		});
	},
	findHospital: function(hospitalName) {
		$.ajax({
			type: 'POST',
			url: 'findHospital',
			data: {hospitalName: hospitalName},
			dataType: 'json',
			timeout: 2000,
			error: function(data) {
				console.log(data);
			},
			success: function(data) {
				if (data.status == 'OK') {
	       			hospitalList.formatData(data.message);
	       		} else {
	       			alert('加载信息失败！');
	       		}
			}
		});
	},
	formatData: function(message) {
		var sHtml = '';
		var i = 0;
		
		for (var key in message) {
			var sTr = '';
			var sTd = '';
			var hospitalId = key;
			var hospitalName = '';
			var hospitalCode = '';
			var areaCode = '';
			var areaName = '';
			var state = 0;
			
			$.each(message[hospitalId], function(i, item) {
				hospitalName = item.hospitalName;
				hospitalCode = item.hospitalCode;
				areaCode = item.areaCode;
				areaName = item.areaName;
				state = item.state;
				hospitalCode = item.hospitalCode;
				if (item.platformCode && item.platformName) {
					sTd += '<span class="label label-success">' + item.platformName + '</span>';
				}
			});
			
			sTr += '<tr>';
			sTr += '	<td><span class="badge badge-info">' + ++i + '</span></td>';
			sTr += '	<td>' + hospitalName + '</td>';
			sTr += '	<td>' + sTd + '</td>';
			sTr += '	<td hospitalId="' + hospitalId + '" hospitalName="' + hospitalName + '" hospitalCode="' + 
						hospitalCode + '" areaCode="' + areaCode + '" areaName="' + 
						areaName + '" state="' + state + '">';
			sTr += '		<a class="green viewHospital" href="javascript:void(0);">查看</a>';
			sTr += '		<a class="green delHospital" href="javascript:void(0);">删除</a>';
			sTr += '	</td>';
			
			sTr += '</tr>';
			
			sHtml += sTr;
		};
		
		$('tbody').html('').append(sHtml);
		
		hospitalList.bindEvent();
	},
	bindEvent: function() {
		// 查看、编辑
		$('.viewHospital').off('click').on('click', function() {
			var hospitalId = $(this).parent().attr('hospitalId');
			var hospitalName = $(this).parent().attr('hospitalName');
			var hospitalCode = $(this).parent().attr('hospitalCode');
			var areaCode = $(this).parent().attr('areaCode');
			var areaName = $(this).parent().attr('areaName');
			var state = $(this).parent().attr('state');
			hospitalList.viewHospitalDetail(hospitalId, hospitalName, hospitalCode, areaCode, areaName, state);
		});
		
		// 删除
		$('.delHospital').off('click').on('click', function() {
			var hospitalId = $(this).parent().attr('hospitalId');
			hospitalList.deleteHospital(hospitalId);
		});
	},
	addHospital: function() {
		window.location.href = 'addHospital';
	},
	viewHospitalDetail: function(hospitalId, hospitalName, hospitalCode, areaCode, areaName, state) {
		$('#hospitalId').val(hospitalId);
		$('#hospitalName').val(hospitalName);
		$('#hospitalCode').val(hospitalCode);
		$('#areaCode').val(areaCode);
		$('#areaName').val(areaName);
		$('#state').val(state);
		
		$('#voForm').attr('action', 'addHospital');
		$('#voForm').submit();
	},
	deleteHospital: function(hospitalId) {
		$.ajax({
			type: 'POST',
			url: 'deleteHospital',
			data: {hospitalId: hospitalId},
			dataType: 'json',
			timeout: 2000,
			error: function(data) {
				console.log(data);
			},
			success: function(data) {
				if (data.status == 'OK') {
					hospitalList.findHospital('');
	       		} else {
	       			alert('删除信息失败！');
	       		}
			}
		});
	}
	
}

$(function() {
	hospitalList.init();
	hospitalList.findHospital('');
});