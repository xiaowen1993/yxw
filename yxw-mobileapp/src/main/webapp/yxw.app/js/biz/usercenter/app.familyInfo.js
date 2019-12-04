var familyInfo = {
	init: function() {
		familyInfo.loadData();
		familyInfo.bindEvent();
	},
	bindEvent: function() {
		$('#syncCard').off('click').on('click', function() {
			familyInfo.syncCards();
		});
		
		$('#removeFamily').off('click').on('click', function() {
			familyInfo.removeFamily();
		});
	},
	bindEventAfterComplete: function() {
		$('.cardInfo').off('click').on('click', function() {
			familyInfo.showCardInfo($(this));
		});
	},
	loadData: function() {
		$Y.loading.show('正在加载绑卡信息...');
		var datas = $('#voForm').serializeArray();
		$.ajax({
			type: "POST",
			url: base.appPath + "app/usercenter/familyInfo/getCards",
			data: datas,
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			error: function(data) {
				$Y.loading.hide();
			},
			success: function(data) {
				$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK' && data.message && data.message.entityList && data.message.entityList.length > 0) {
					familyInfo.formatData(data.message.entityList);
				}
			}
		})
	},
	formatData: function(data) {
		var sHtml = '';
		$.each(data, function(i, item) {
			sHtml += '<li class="arrow cardInfo" medicalcardId="' + item.id + '">';
            sHtml += '	<div class="title fontSize120">' + item.encryptedPatientName + '（' + item.cardNo + '）</div>';
            sHtml += '	<div class="des color666" item-code="' + item.hospitalCode + '">' + item.hospitalName + '</div>';
            sHtml += '</li>';
		});
		
		$('#cardList').html('');
		$('#cardList').append(sHtml);
		
		familyInfo.bindEventAfterComplete();
	},
	showCardInfo: function(obj) {
		$('#medicalcardId').val($(obj).attr('medicalcardId'));
		$('#voForm').attr('action', base.appPath + 'app/usercenter/cardInfo/toView');
		$('#voForm').submit();
	},
	showMessageBox: function(data) {
		new $Y.confirm({
	        ok:{title:'确定'},
	        content: data
	    });
	},
	removeFamily: function() {
		// 跳往家人列表
		console.log('我要移除家人了.');
		
		$Y.loading.show('正在移除家人...');
		var datas = $('#voForm').serializeArray();
		$.ajax({
			type: "POST",
			url: base.appPath + 'app/usercenter/familyInfo/unbindFamily',
			data: datas,
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			error: function(data) {
				$Y.loading.hide();
				familyInfo.showMessageBox('移除家人失败，请稍后重试。');
			},
			success: function(data) {
				$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK') {
					var isSuccess = data.message.isSuccess;
					var tips = data.message.msgInfo ? data.message.msgInfo : '移除家人失败，请稍后重试。';
					
					
					if (isSuccess == 'success') {
						// 成功，跳转回列表
						$('#voForm').attr('action', base.appPath + 'app/usercenter/myFamily/index');
						$('#voForm').submit();
					} else {
						familyInfo.showMessageBox(tips);
					}
				} else {
					familyInfo.showMessageBox('移除家人失败，请稍后重试。');
				}
			}
		})
	},
	syncCards: function() {
		$('#voForm').attr('action', base.appPath + 'app/usercenter/syncCard/index');
		$('#voForm').submit();
	}
}

$(function() {
	familyInfo.init();
})

// 刷新方法
	function doRefresh() {
		$('#voForm').attr('action', base.appPath + 'app/usercenter/familyInfo/index');
		$('#voForm').submit();
	}