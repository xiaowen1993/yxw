var familyList = {
	init: function() {
		familyList.bindEvent();
		familyList.loadData();

		var cardNum = $('#cardNum').val();
		if (!cardNum) {
			$('#cardNum').val(0)
		}
		var maxCard = $('#maxCard').val();
		if (!maxCard) {
			$('#maxCard').val(0)
		}
	},
	bindEvent: function() {
		$('#btnAddFamily').off('click').on('click', function() {
			familyList.addFamilies();
		});
	},
	bindEventAfterComplete: function() {
		$('ul.yx-list>li').off('click').on('click', function() {
			familyList.showDetail(this);
		});
	},
	loadData: function() {
		$Y.loading.show('正在为您加载家人数据');
		$.ajax({
			type: 'POST',
			url: base.appPath + 'app/usercenter/myFamily/getFamilies',
			data: {
				appCode: $('#appCode').val(),
				areaCode: $('#areaCode').val(),
				openId: $('#openId').val()
			},
			dataType: 'json',
			timeout: 120000,
			error: function(data) {
				$Y.loading.hide();
				var myBox = new $Y.confirm({
					content: "加载家人信息异常，请稍后重试...",
					ok: {
						title: "确定",
						click: function() {
							// 关闭界面
							console.log('关闭界面');
							myBox.close();
						}
					}
				})
			},
			success: function(data) {
				console.log(data);
				$Y.loading.hide();
				if (data.status == 'OK' && data.message && data.message.entityList) {
					$('#cardNum').val(data.message.entityList.length);
					if (data.message.entityList.length > 0) {
						$('#familyList').hide();
						$('#commonTips').hide();
						familyList.formatData(data.message.entityList);
						familyList.bindEventAfterComplete();
					} else {
						$('#familyList').hide();
						familyList.showNoData();
					}
				} else {
					$('#familyList').hide();
					familyList.showNoData();
				}
			}
		});
	},
	formatData: function(data) {
		var sHtml = '';

		$.each(data, function(i, item) {
			sHtml += '<li class="arrow" familyId="' + item.id + '">';
			sHtml += '	<div class="title fontSize120">' + item.encryptedName + '（' + item.ownershipLabel + '）</div>';
			// sHtml += '	<div class="des color999">' + item.encryptedIdNo + '</div>';
			sHtml += '</li>';
		});

		$('#familyList').html('');
		$('#familyList').append(sHtml);
		$('#familyList').show();
	},
	showDetail: function(obj) {
		var familyId = $(obj).attr('familyId');
		if (familyId) {
			$('#familyId').val(familyId);
			$('#voForm').attr('action', base.appPath + 'app/usercenter/familyInfo/index');
			$('#voForm').submit();
		}
	},
	addFamilies: function() {
		var cardNum = $('#cardNum').val();
		var maxCard = $('#maxCard').val();
		// 检测还能不能添加家人 
		if (cardNum >= maxCard) {
			// 最多只能绑定那么多个
			new $Y.confirm({
				content: '<div>每个用户最多可以绑定' + maxCard + '个家人。</div>',
				ok: {
					title: '确定'
				}
			});

			return false;
		} else {
			$('#voForm').attr('action', base.appPath + 'app/usercenter/myFamily/addMyFamily');
			$('#voForm').submit();
		}
	},
	showNoData: function() {
		var sHtml = '';

		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData">';
		sHtml += '			<img src="' + base.appPath + 'yxw.app/images/greenSkin/doll/yx-doll2.png" width="220">';
		sHtml += '		</div>';
		sHtml += '		<div class="p color666">还没有添加家人</div>';
		sHtml += '	</div>';

		$('#commonTips').html('').append(sHtml);
		$('#commonTips').show();
	}
}

$(function() {
	familyList.init();
});

function doRefresh() {
	$('#voForm').attr('action', base.appPath + 'app/usercenter/myFamily/index');
	$('#voForm').submit();
}