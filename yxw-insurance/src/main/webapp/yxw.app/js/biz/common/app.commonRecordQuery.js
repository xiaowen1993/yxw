var recordQuery = {
	init: function() {
		recordQuery.bindEvent();
		recordQuery.showInitTips();
		
		$('#dateFilter').val(0);
		$('#hospitalFilter').val(0);
		
		var cookieHospital = getCookie("hospitalFilterVal");
		if (cookieHospital) {
			var selectedHospital = $("#hospitalFilter").find("option[value='"+cookieHospital+"']");
			
			if (selectedHospital) {
				$('#hospitalFilter').val(cookieHospital);
				
				//$('#hospital').attr('data-id', cookieHospital)
				//$('#hospital').text(selectedHospital.text());
				
				recordQuery.selectScreen(document.getElementById("hospitalFilter"));
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
		$('#dateFilter').off('change').on('change', function() {
			recordQuery.selectScreen(this);
		});

		$('#hospitalFilter').off('change').on('change', function() {
			recordQuery.selectScreen(this);
			setCookie("hospitalFilterVal", $(this).val());
		});
	},
	selectScreen: function(obj) {
		if ($(obj).hasClass('select-screen-box')) {
			var option = obj.children[obj.selectedIndex];
			var html = option.innerHTML;
			// 下面的是家人部分只显示姓名，不显示证件号码
			// html = html.substring(0, html.indexOf('('));
			obj.previousElementSibling.innerHTML = html;
			$(obj.previousElementSibling).attr('data-id', $(option).val());
		}

		// 判断获取卡信息等等
		var hospitalCode = $('#hospital').attr('data-id');
		if (hospitalCode != '0') {
			recordQuery.loadData();
		} else {
			recordQuery.showInitTips();
		}
	},
	loadData: function() {
		$('#commonTips').hide();
		// 调用loadData
		if (typeof loadData == 'function') {
			loadData();
		}
	},
	showNoRecord: function() {
		var sHtml = '';

		sHtml += '<div class="noRecord">';
		sHtml += '	<div id="success">';
		sHtml += '		<div class="noData"><img src="' + base.appPath
				+ 'yxw.app/images/greenSkin/doll/yx-doll3.png" width="220"> </div>';
		sHtml += '		<div class="p color666">已关联的医院中，没有查询到您的任何' + $('#moduleName').val() + '项目</div>';
		sHtml += '	</div>';
		sHtml += '</div>';

		$('#commonTips').html('').append(sHtml);
		$('#commonTips').show();
	}
}

$(function() {
	recordQuery.init();
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
