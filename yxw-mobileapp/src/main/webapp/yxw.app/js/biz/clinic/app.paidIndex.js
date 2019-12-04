var paidIndex = {
	formatData: function(data) {
		var sHtml = '';

		sHtml += '<ul class="yx-list">';
		$.each(data, function(i, item) {
			sHtml += '<li class="lock f_vertical ';
			if (item.statusLabel == '缴费成功' || item.statusLabel == '部分退费') {
				sHtml += 'arrow';
			}
			sHtml += ' boxTable flex" data-orderNo="' + item.orderNo + '" data-hospitalId="' + item.hospitalId + 
					 '" data-hospitalCode="' + item.hospitalCode + '" data-status="' + item.clinicStatus +  '">';
			sHtml += '	<div class="flexItem">';
			sHtml += '		<div class="name fontSize120">' + item.recordTitle + '－' + item.patientName + '</div>';
			sHtml += '		<div class="mate">' + item.hospitalName + '</div>';
			sHtml += '		<div class="mate">' + Number(item.payFee / 100).toFixed(2) + '元</div>';
			sHtml += '		<div class="time color999">' + item.payDate + '</div>';
			sHtml += '	</div>';
			sHtml += '	<div class="color999 flexItem w100 textRight vertical">';
			if (item.statusLabel == '缴费成功' || item.statusLabel == '部分退费') {
				sHtml += '	<div class="status color666">' + item.statusLabel;
			} else {
				if (item.statusLabel == '缴费失败') {
					sHtml += '	<div class="status red">' + item.statusLabel;
				} else {
					sHtml += '	<div class="status color666">' + item.statusLabel;
				}
			}
			sHtml += '	</div>';
			sHtml += '</li>'
		});
		sHtml += '</ul>';

		$('#payRecord').html('').append(sHtml);
		$('.yxw-data').show();
		paidIndex.bindEventAfterComplete();
	},
	bindEventAfterComplete: function() {
		$('ul.yx-list>li').off('click').on('click', function(event) {
			event.stopPropagation();
			event.preventDefault();

			paidIndex.showPaidDetail(this);
		});
	},
	showPaidDetail: function(obj) {
		if (obj && ($(obj).attr('data-status') == '1' || $(obj).attr('data-status') == '30')) {
			$('#hospitalId').val(stringUtils.getValue($(obj).attr('data-hospitalId')));
			$('#hospitalCode').val(stringUtils.getValue($(obj).attr('data-hospitalCode')));
			$('#hospitalName').val(stringUtils.getValue($(obj).attr('data-hospitalName')));
			$('#orderNo').val(stringUtils.getValue($(obj).attr('data-orderNo')));
			$('#voForm').attr('action', base.appPath + 'app/clinic/paid/detail/index');
			$('#voForm').submit();
		}
	}
}

function loadData() {
	$('div.yxw-data').hide();
	
	var url = base.appPath + 'app/clinic/paid/getDatas';
	$Y.loading.show('正在为您加载数据');
	$.ajax({
		type: 'POST',
		url: url,
		data: {
			openId: $('#openId').val(),
			appCode: $('#appCode').val(),
			areaCode: $('#areaCode').val(),
			hospitalCode: $('#hospitalFilter').val(),
			dateType: $('#dateFilter').val()
		},
		dataType: 'json',
		timeout: 120000,
		error: function(data) {
			console.log(data);
			$Y.loading.hide();
			recordQuery.showNoRecrod();	
		},
		success: function(data) {
			console.log(data);
			$Y.loading.hide();
			if (data.status == 'OK' && data.message && data.message.list.length > 0) {
				paidIndex.formatData(data.message.list);
			} else {
				recordQuery.showNoRecord();
			}
		}
	})
}

$(function() {
	pushHistory();
	window.addEventListener("popstate", function(e) {

		var referrer = document.referrer;
		if (referrer.indexOf("app/clinic/paid/detail/index") > 0 || referrer.indexOf("app/clinic/paid/index") > 0) {
			skipPages.forward('userCenterIndex');
		} else {
			window.location.href = referrer;
		}

	}, false);
	function pushHistory() {
		var state = {
			title : "title",
			url : "#"
		};
		window.history.pushState(state, "title", "#");
	}
});