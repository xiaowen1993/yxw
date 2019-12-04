var hospitalList = {
	init: function() {
		// 新增
		$('.btnAdd').off('click').on('click', function() {
			hospitalList.addHospital();
		});

		// 搜索
		$('.btnSearch').off('click').on('click', function() {
			var hospitalName = $(this).parent().siblings().val();
			hospitalList.findHospital(hospitalName);
		});
	},
	findHospital: function(hospitalName) {
		$.ajax({
			type: 'POST',
			url: 'findHospital',
			data: {
				hospitalName: hospitalName
			},
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
		var regExp = new RegExp(/(\/)/g);

		for ( var key in message) {
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
				areaName = item.areaName.replace(regExp, "");
				state = item.state;
				hospitalCode = item.hospitalCode;
				if (item.platformCode && item.platformName) {
					sTd += '<span class="label label-success">' + item.platformName + '</span> &nbsp;';
				}
			});

			sTr += '<tr>';
			sTr += '	<td><span class="badge badge-info">' + ++i + '</span></td>';
			sTr += '	<td>' + hospitalName + '</td>';
			sTr += '	<td>' + areaName + '</td>';
			sTr += '	<td>' + sTd + '</td>';
			sTr += '	<td hospitalId="' + hospitalId + '" hospitalName="' + hospitalName + '" hospitalCode="'
					+ hospitalCode + '" areaCode="' + areaCode + '" areaName="' + areaName + '" state="' + state
					+ '" style="text-align: center; margin: 0 auto;">';
			sTr += '		<div class="btn-group">';
			sTr += '			<div class="btn btn-primary btn-sm viewHospital">查看</div>';
			sTr += '			<div class="btn btn-danger btn-sm delHospital">删除</div>';
			sTr += '		</div>';
			sTr += '	</td>';

			sTr += '</tr>';

			sHtml += sTr;
		}
		;

		$('tbody').html('').append(sHtml);

		hospitalList.bindEvent();
	},
	bindEvent: function() {
		// 查看、编辑
		$('.viewHospital').off('click').on('click', function() {
			var hospitalId = $(this).parent().parent().attr('hospitalId');
			var hospitalName = $(this).parent().parent().attr('hospitalName');
			var hospitalCode = $(this).parent().parent().attr('hospitalCode');
			var areaCode = $(this).parent().parent().attr('areaCode');
			var areaName = $(this).parent().parent().attr('areaName');
			var state = $(this).parent().parent().attr('state');
			hospitalList.viewHospitalDetail(hospitalId, hospitalName, hospitalCode, areaCode, areaName, state);
		});

		// 删除
		$('.delHospital').off('click').on('click', function() {
			var hospitalId = $(this).parent().parent().attr('hospitalId');
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

		$('#voForm').attr('action', '/sys/hospital/addHospital');
		$('#voForm').submit();
	},
	deleteHospital: function(hospitalId) {
		$.ajax({
			type: 'POST',
			url: 'deleteHospital',
			data: {
				hospitalId: hospitalId
			},
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