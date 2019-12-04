var cardInfo = {
	init: function() {
		// 生成二维码
		cardInfo.generateBarcode($('#cardNo').val());
		
		// 绑定事件
		$('#unbind').click(function(event) {
			event.stopPropagation();
			event.preventDefault();
			cardInfo.unbindCard();
		});
	},
	unbindCard: function() {
		$Y.loading.show('正在为您解绑就诊卡...');
		$.ajax({
			type: "POST",
			url: base.appPath + "app/usercenter/cardInfo/unbindCard",
			data: {
				id: $('#id').val(),
				appId: $('#appId').val(),
				openId: $('#openId').val()
			},
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			error: function(data) {
				$Y.loading.hide();
				cardInfo.showMessageBox('解绑失败，请稍后重试。');
			},
			success: function(data) {
				$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK') {
					if (data.message.isSuccess == 'success') {
						var syncType = '${commonParams.syncType}';
						if (syncType) {
							// 回到就诊卡列表页面
							$('#voForm').attr('action', base.appPath + 'app/usercenter/familyInfo/index');
							$('#voForm').submit();
						} else {
							// 没有这个东西，就认为他是从消息里面过来的，直接关闭即可。
							// 直接跳回消息列表
							$('#voForm').attr('action', base.appPath + 'app/msgcenter/msgCenterListView');
							$('#voForm').submit();
						}
					} else {
						cardInfo.showMessageBox('解绑失败，请稍后重试。');
					}
				} else {
					cardInfo.showMessageBox('解绑失败，请稍后重试。');
				}
			}
		})
	},
	generateBarcode: function(cardNo) {
		// 码制（看医院）
		var codeStyle = $('#barcodeStyle').val();
		var btype = '';
		switch (codeStyle) {
			case '1': 
				btype = 'ean8';
				break;
			case '2':
				btype = 'upc';
				break;
			case '3':
				btype = 'code39';
				break;
			case '4':
				btype = 'code128';
				break;
			case '5':
				btype = 'codabar';
				break;
			default:
				btype = 'code128';
		}
        
        // 类型（固定）
        var renderer = 'css';
		
        var settings = {
          output:renderer,
          bgColor: '#FFFFFF',
          color: '#000000',
          barWidth: 2,
          barHeight: 50,
          moduleSize: 5,
          posX: 10,
          posY: 20,
          addQuietZone: false
        };
        
        $('#barcodeTarget').html('').show().barcode(cardNo, btype, settings);
	},
	showMessageBox: function(data) {
		new $Y.confirm({
	        ok:{title:'确定'},
	        content: data
	    });
	}
}

$(function() {
	cardInfo.init();
});

// 刷新方法
function doRefresh() {
	$('#voForm').attr('action', base.appPath + "app/usercenter/cardInfo/toView");
	$('#voForm').submit();
}