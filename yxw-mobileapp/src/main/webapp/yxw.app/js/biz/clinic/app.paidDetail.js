var paidDetail = {
	loadData: function() {
		// 测试 - 把hospitalCode, branchCode换成省二的
//		$('#hospitalCode').val('gdsdermyy');
//		$('#branchHospitalCode').val('gdsdermyy');
		var datas = $('#voForm').serializeArray();
		var timeTemp = new Date().getTime();

		$('.noRecord').hide();
		$Y.loading.show('正在为您加载数据');
		$.ajax({
			type: 'POST',
			url: base.appPath + 'app/clinic/paid/detail/getDatas?timeTemp=' + timeTemp,
			data: datas,
			dataType: 'json',
			timeout: 120000,
			error: function(data) {
				$Y.loading.hide();
				$('.noRecord').show();
			},
			success: function(data) {
				console.log(data);
				$Y.loading.hide();
				if (data.status == 'OK' && data.message) {
					paidDetail.formatData(data.message);
				} else {
					$('.noRecord').show();
				}
			}
		})
	},
	formatData: function(data) {
		if (data.list.length > 0) {
			var sHtml = '';

			sHtml += '<div class="box-list fff accordion js-accordion">';
			// 添加明细项
			$.each(data.list, function(i, item) {
				$.each(item.details, function(i, subItem) {
					sHtml += '<div class="acc-li" data-id="' + subItem.itemId + '">';
					sHtml += '	<div class="acc-header js-acc-header">' + subItem.itemName;

					if (subItem.itemTotalFee) {
						sHtml += '		(<span class="yellow">' + Number(subItem.itemTotalFee / 100).toFixed(2)
								+ '</span>元)';
					}

					sHtml += '	</div>';
					sHtml += '	<ul class="acc-content">';
					sHtml += '		<li class="item">';
					sHtml += '		    <div class="label">规格</div>';
					sHtml += '  		<div class="values">' + (subItem.itemSpec ? subItem.itemSpec : '---') + '</div>';
					sHtml += '		</li>';
					sHtml += '		<li class="item">';
					sHtml += '		    <div class="label">单位</div>';
					sHtml += '			<div class="values">' + (subItem.itemUnit ? subItem.itemUnit : '---') + '</div>';
					sHtml += '		</li>';
					sHtml += '		<li class="item">';
					sHtml += '		    <div class="label">单价</div>';
					sHtml += '			<div class="values">'
							+ (subItem.itemPrice ? (Number(subItem.itemPrice / 100).toFixed(2) + '元') : '---')
							+ '</div>';
					sHtml += '		</li>';
					sHtml += '		<li class="item">';
					sHtml += '			<div class="label">数量</div>';
					sHtml += '			<div class="values">' + (subItem.itemNumber ? subItem.itemNumber : '---') + '</div>';
					sHtml += '		</li>';
					sHtml += '	</ul>';
					sHtml += '</div>';
				})
			});
			sHtml += '</div>';

			$('#paid-detail').html('');
			$('#paid-detail').append(sHtml);
			
			$('.js-accordion .js-acc-header ').click(function(){
				if($(this).hasClass('show')){
					$(this).removeClass('show')
				}else{
					$(this).addClass('show')
				}

			});
			
		} else {
			$('.noRecord').show();
		}
	},
	initBarcode: function() {
		var show = $('#isShowBarcode').val();
		if (!show || show != 1) {
			return false;
		}

		var value = $('#barcodeData').val();
		if (!value || (value != 1 && value != 2)) {
			return false;
		}

		value = value == 1 ? $('#barcode').val() : $('#cardNo').val();

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
			output: renderer,
			bgColor: '#FFFFFF',
			color: '#000000',
			barWidth: 2,
			barHeight: 50,
			moduleSize: 5,
			posX: 10,
			posY: 20,
			addQuietZone: false
		};

		$("#barcodeTarget").html("").show().barcode(value, btype, settings);
	}
}

$(function() {
	
	$('.js-accordion .js-acc-header ').click(function(){
		if($(this).hasClass('show')){
			$(this).removeClass('show')
		}else{
			$(this).addClass('show')
		}

	});
	
//	pushHistory();
	window.addEventListener("popstate", function(e) {
		
		var referrer = document.referrer;
		if (referrer.indexOf("app/clinic/paid/index") > 0) {
			window.location.href = referrer;
		}else {
			skipPages.forward('userCenterIndex');
		}
		
	}, false);
	
	paidDetail.initBarcode();
	paidDetail.loadData();
});

function pushHistory() {
	var state = {
		title : "title",
		url : "#"
	};
	window.history.pushState(state, "title", "#");
}

function doRefresh() {
	var form = $("<form></form>");
	form.append($('<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}"/>'));
	form.append($('<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>'));
	form.append($('<input type="hidden" name="hospitalName" value="${commonParams.hospitalName}"/>'));
	form.append($('<input type="hidden"  name="cardNo" value="${commonParams.cardNo}">'));
	form.append($('<input type="hidden"  name="cardType" value="${commonParams.cardType}">'));
	form.append($('<input type="hidden"  name="patientName" value="${commonParams.patientName}">'));
	form.append($('<input type="hidden" name="orderNo" value="${commonParams.orderNo}"/>'));
	form.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}"/>'));
	form.appendTo("body");
	form.css('display', 'none');
	form.attr("method", "post");
	form.attr("action", base.appPath + "app/clinic/paid/detail/index");
	form.submit();
}