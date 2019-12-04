var tradeMap = {};

var setting = {
	init: function() {
		tradeMap[-1] = '<option value="-1">请选择支付方式</option>';
		var tradeModes = JSON.parse(decodeURIComponent($('#tradeModes').val()));
		$.each(tradeModes, function(i, item) {
			tradeMap[item.code] = '<option value="' + item.id + '">' + item.name + '</option>';
		});
		
		// 新增
		$('#saveSetting').off('click').on('click', function() {
			if ($('#platformSelector').val() == '-1') {
				alert('请选择平台类型!');
				return false;
			}

			if ($('#tradeModeSelector').val() == '-1') {
				alert('请选择支付方式!');
				return false;
			}

			if (!$('#inputAppId').val()) {
				alert('请输入AppId!');
				return false;
			}

			var hospitalId = $('#hospitalId').val();
			var platformId = $('#platformSelector').val();
			var tradeModeId = $('#tradeModeSelector').val();
			var appId = $('#inputAppId').val();
			setting.addSetting(hospitalId, platformId, tradeModeId, appId);
		});

		$('#platformSelector').off('change').on('change', function() {
			var code = $(this).find('option:selected').attr('data-code');
			if (code == '-1') {
				$('#tradeModeSelector').html('').append(tradeMap[-1]);
			} else {
				setting.initTradeModes(Number(code));
			}

		});
	},
	initTradeModes: function(code) {
		var sHtml = '';
		sHtml += tradeMap[-1];

		switch (code) {
		case 1:
		case 2:
			sHtml += tradeMap[code];
			break;
		case 3:
			// 3.4.5.7.8.9
			sHtml += tradeMap[3] + tradeMap[4] + tradeMap[5] + tradeMap[7] + tradeMap[8] + tradeMap[9];
			break;
		case 4:
			sHtml += tradeMap[10] + tradeMap[11] + tradeMap[12];
			break;
		case 5:
			sHtml += tradeMap[10];
			break;
		case 6:
			sHtml += tradeMap[11];
			break;
		case 7:
			sHtml += tradeMap[1];
			break;
		case 8:
			sHtml += tradeMap[2];
			break;
		default:
			break;
		}

		$('#tradeModeSelector').html('').append(sHtml);

	},
	addSetting: function(hospitalId, platformId, tradeId, appId) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			data: {
				hospitalId: hospitalId,
				platformId: platformId,
				tradeId: tradeId,
				appId: appId
			},
			timeout: 20000,
			url: 'addSetting',
			error: function(data) {
				console.log(data);
			},
			success: function(data) {
				if (data.status == 'OK') {
					setting.findSetting(hospitalId);
				} else {
					alert(data.message);
				}
			}
		});
	},
	findSetting: function(hospitalId) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			data: {
				hospitalId: $('#hospitalId').val()
			},
			timeout: 20000,
			url: 'getSettings',
			error: function(data) {
				console.log(data);
			},
			success: function(data) {
				console.log(data);
				if (data.status == 'OK') {
					if (data.message) {
						setting.formatData(data.message);
					}
				} else {
					alert('挂了。');
				}
			}
		});
	},
	formatData: function(data) {
		var sHtml = '';

		$.each(data, function(i, item) {
			sHtml += '<tr settingId="' + item.id + '">';
			sHtml += '	<td>' + item.platformName + '</td>';
			sHtml += '	<td>' + item.tradeName + '</td>';
			sHtml += '	<td>' + item.appId + '</td>';
			sHtml += '	<td><div class="btn btn-danger delSetting"">删除</div></td>';
			sHtml += '</tr>';
		});

		$('tbody').html('').append(sHtml);
		setting.bindEvent();
	},
	bindEvent: function() {
		$('div.delSetting').off('click').on('click', function() {
			var settingId = $(this).parent().parent().attr('settingId');
			setting.delSetting(settingId);
		});
	},
	delSetting: function(settingId) {
		$.ajax({
			type: 'POST',
			dataType: 'json',
			data: {
				id: settingId
			},
			timeout: 20000,
			url: 'deleteSetting',
			error: function(data) {
				console.log(data);
			},
			success: function(data) {
				if (data.status == 'OK') {
					setting.findSetting($('#hospitalId'));
				} else {
					alert('挂了。');
				}
			}
		});
	}
}

$(function() {
	setting.init();
	setting.findSetting($('#hospitalId').val());
});