var bizQuery = {
	init: function() {
		bizQuery.bindEvent();
		bizQuery.showInitTips();

		$('#familyFilter').val(0);
		$('#hospitalFilter').val(0);
		
		var cookieHospital = getCookie("hospitalFilterVal");
		if (cookieHospital) {
			var selectedHospital = $("#hospitalFilter").find("option[value='"+cookieHospital+"']");
			if (selectedHospital) {
				$('#hospitalFilter').val(cookieHospital);
				
				$('#hospital').attr('data-id', cookieHospital)
				$('#hospital').text(selectedHospital.text());
				
				bizQuery.selectScreen(document.getElementById("hospital"));
			}
		}
		
		var cookieFamily = getCookie("familyFilterVal");
		if (cookieFamily) {
			var selectedFilter = $("#familyFilter").find("option[value='"+cookieFamily+"']");
			if (selectedFilter) {
				$('#familyFilter').val(cookieFamily);
				
				$('#family').attr('data-id', cookieFamily)
				$('#family').text(selectedFilter.text());
				
				bizQuery.selectScreen(document.getElementById("familyFilter"));
			}
		}
	},
	showInitTips: function() {
		$('.yxw-data').hide();
		var sHtml = '';

		sHtml += '<div class="noFamily">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData">';
		// 暂不确实是使用哪个图标
		sHtml += '			<img src="' + base.appPath + 'yxw.app/images/greenSkin/doll/yx-doll2.png" width="220">';
		sHtml += '		</div>';
		sHtml += '		<div class="p color666" id="noCardTips">请选择医院后查看' + $('#moduleName').val() + '</div>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$('#commonTips').html('').append(sHtml);
		$('#commonTips').show();
	},
	bindEvent: function() {
		$('#familyFilter').off('change').on('change', function() {
			bizQuery.selectScreen(this);
			setCookie("familyFilterVal", $(this).val());
		});

		$('#hospitalFilter').off('change').on('change', function() {
			bizQuery.selectScreen(this);
			setCookie("hospitalFilterVal", $(this).val());
		});
	},
	bindEventAfterComplete: function() {
		$('#sync').off('click').on('click', function() {
			bizQuery.assignCard();
		});
	},
	selectScreen: function(obj) {
		$('.btn-w').hide();
		// 隐藏提示
		if ($('#preTips')) {
			$('#preTips').hide();
		}
		
		if ($(obj).hasClass('select-screen-box')) {
			var option = obj.children[obj.selectedIndex];
			var html = option.innerHTML;
			// 下面的是家人部分只显示姓名，不显示证件号码
			// html = html.substring(0, html.indexOf('('));
			obj.previousElementSibling.innerHTML = html;
			$(obj.previousElementSibling).attr('data-id', $(option).val());
			$('#appId').val( $(option).attr("data-appId") );
		}

		// 判断获取卡信息等等
		var familyId = $('#family').attr('data-id');
		var hospitalCode = $('#hospital').attr('data-id');
		if (hospitalCode != '0') {
			bizQuery.getCard(familyId, hospitalCode);
		} else {
			bizQuery.showInitTips();
		}
		
		var appId = $('#appId').attr('data-id');
	},
	getCard: function(familyId, hospitalCode) {
		$('#commonTips').hide();
		$('.yxw-data').hide();
		$Y.loading.show('正在加载就诊卡信息...');
		$.ajax({
			url: base.appPath + 'app/common/getFamilyCard',
			type: 'POST',
			datatype: 'json',
			timeout: 20000,
			data: {
				openId: $('#openId').val(),
				areaCode: $('#areaCode').val(),
				familyId: familyId,
				hospitalCode: hospitalCode
			},
			error: function(data) {
				console.log(data);
				$Y.loading.hide();
				$('.yxw-data').hide();
				$('#commonTips').hide();
			},
			success: function(data) {
				$Y.loading.hide();
				$('.yxw-data').hide();
				$('#commonTips').hide();
				if (data.status == "OK") {
					if (data.message && data.message != 'null') {
						bizQuery.loadData(data.message);
					} else {
						// 没卡 - 搞那个绑卡的东西
						bizQuery.showAssignCard();
					}
				} else {
					// 数据异常
					console.log('参数异常.');
				}
			}
		});
	},
	loadData: function(data) {
		$('#hospitalCode').val(data.hospitalCode);
		$('#hospitalId').val(data.hospitalId);
		$('#branchHospitalCode').val(data.branchCode);
		$('#branchHospitalId').val(data.branchId);
		$('#patCardType').val(data.cardType);
		$('#patCardNo').val(data.cardNo);
		$('#cardType').val(data.cardType);
		$('#cardNo').val(data.cardNo);
		$('#admissionNo').val(data.admissionNo);
		$('#familyId').val(data.familyId);
		$('#encryptedPatientName').val(data.encryptedPatientName);
		$('#hospitalName').val($('#hospital').text());

		// 调用loadData
		if (typeof loadData == 'function') {
			loadData();
		}
	},
	showAssignCard: function() {
		$('#familyId').val($('#family').attr('data-id'));

		var sHtml = '';

		sHtml += '<div class="noFamily">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData"><img src="' + base.appPath + 'yxw.app/images/greenSkin/doll/yx-doll2.png" width="220"> </div>';
		sHtml += '		<div class="p color666">还没有关联医院就诊卡信息，无法查询' + $('#moduleName').val() + '信息，请先去关联医院就诊卡信息，或者切换其他就诊人查询。</div>';
		sHtml += '	</div>';
		sHtml += '	<div class="btn-w">';
		sHtml += '		<div class="btn btn-ok btn-block" id="sync">去关联</div>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$('#commonTips').html('').append(sHtml);
		$('#commonTips').show();

		bizQuery.bindEventAfterComplete();
	},
	assignCard: function() {
		// 可选参数asyncType 1本人，2家人，3...
		// 跳回来的时候需要手动加上appCode,openId,areaCode, 也就是forward中不需要带了
		var forward = $('#forward').val() + '?familyId=' + $('#familyId').val();
		var ownership = $('#familyFilter option:selected').attr('ownership');
		var hospitalCode = $('#hospital').attr('data-id');
		window.location.href = base.appPath + 'app/usercenter/syncCard/index?openId=' + $('#openId').val() + '&appCode=' + $('#appCode').val() + '&areaCode='
				+ $('#areaCode').val() + '&familyId=' + $('#familyId').val() + '&hospitalCode=' + hospitalCode + '&syncType=' + ownership + '&forward='
				+ encodeURIComponent(forward);
	},
	showNoRecord: function() {
		var sHtml = '';

		sHtml += '<div class="noRecord">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData"><img src="' + base.appPath + 'yxw.app/images/greenSkin/doll/yx-doll3.png" width="220"> </div>';
		sHtml += '		<div class="p color666">已关联的医院中，没有查询到您的任何' + $('#moduleName').val() + '项目</div>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$('#commonTips').html('').append(sHtml);
		$('#commonTips').show();
	}
}

$(function() {
	bizQuery.init();
});

/**
 * 设置Cookie
 * 
 * @param c_name
 * @param value
 * @param expiredays
 *            过期天数
 */
function setCookie(c_name, value, expiredays) {
  expiredays = expiredays ? expiredays : 30;// 默认30天
  var exdate = new Date();
  exdate.setDate(exdate.getDate() + expiredays);
  document.cookie = c_name + "=" + escape(value) + ";expires=" + exdate.toGMTString() + ";path=/";
}

/**
 * 获取Cookie
 * 
 * @param c_name
 * @returns
 */
function getCookie(c_name) {
  if (document.cookie.length > 0) {
    c_start = document.cookie.indexOf(c_name + "=");
    if (c_start != -1) {
      c_start = c_start + c_name.length + 1;
      c_end = document.cookie.indexOf(";", c_start);
      if (c_end == -1) {
        c_end = document.cookie.length;
      }
      return unescape(document.cookie.substring(c_start, c_end));
    }
  }
  return "";
}
