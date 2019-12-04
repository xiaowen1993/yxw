var list = {
	init: function() {
		$('form input[name="pages"]').val("");
		$('form input[name="pageNum"]').val("");
		$('form input[name="pageSize"]').val("");
		$('form input[name="userId"]').val(base.openId);
		$('form input[name="appCode"]').val(base.appCode);
		
		list.getDatas();
	},
	getDatas: function() {
		$Y.loading.show('正在为您加载数据...');
		
		// 暂时先不做分页
		$('form input[name="pageNum"]').val("1");
		$('form input[name="pageSize"]').val("1000");
		
		$.ajax({
			type : "POST",
			url : base.appPath + "app/nih/list/getDatas",
			data : $("form").serializeArray(),
			dataType : "json",
			error : function(data) {
				$Y.loading.hide();
				// 提示不能跳转
				myBox = new $Y.confirm({
					title : "",
					content : "<div style='text-align: center;'>网络异常,请保持您的网络通畅,稍后再试.</div>",
					ok : {
						title : "确定",
						click : function() {
							myBox.close();
						}
					}
				});
			},
			success : function(data) {
				$Y.loading.hide();
				console.log(data);
				
				if (data.status == "OK") {
					list.formatData(data.message.list);
				} else {
					list.showNoData();
				}
			}
		});
	},
	formatData: function(data) {
		if (data.length > 0) {
			var sHtml = '';
			$.each(data, function(i, item) {
				sHtml += '<li data-url="" class="boxTable flex">';
				sHtml += '	<div class="flexItem">';
				sHtml += '		<div class="name fontSize110">' + item.name + '</div>';
				sHtml += '		<div class="mate">' + item.mobileNo + '</div>';
				sHtml += '		<div class="time color999">' + item.createTimeStr + '</div>';
				sHtml += '	</div>';
				// sHtml += '	<div class="color999 w120 flexItem textRight vertical">' + (item.isValid == 0 ? '无效' : '有效') + '</div>';
				sHtml += '</li>';
			});
			
			$('.yx-list').html('').append(sHtml);
		} else {
			list.showNoData();
		}
	},
	showNoData: function() {
		var sHtml = '';
		sHtml += '<div class="noRecord">';
		sHtml += '	<div id="success">';
	    sHtml += '		<div class="noData">';
	    sHtml += '		<img src="' + base.appPath + 'yxw.app/images/greenSkin/doll/yx-doll3.png" width="220" />';
	    sHtml += '		</div>';
	    sHtml += '		<div class="p color666">找不到您的预约记录</div>';
	    sHtml += '	</div>';
	    sHtml += '</div>';
		$('#body').html('');
		$('#body').html(sHtml);
	}
}

$(function() {
	list.init();
});