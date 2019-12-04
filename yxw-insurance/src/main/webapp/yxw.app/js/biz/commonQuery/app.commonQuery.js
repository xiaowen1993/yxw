function getValue(data) {
	if (data && data != 'null') {
		return data;
	} else {
		return "";
	}
}

function syncCard() {
	// console.log('关联...');
	/*
	$('#voForm').attr('action', basePath + 'easyhealth/usercenter/syncCard/index');
	$('#voForm').submit();
	 */
	// 需要调用壳的方法，使用goUrl的方式来打开。
	go(appPath + 'easyhealth/usercenter/syncCard/index?openId=' + $('#openId').val() + '&appCode='
			+ $('#appCode').val() + '&areaCode=' + $('#areaCode').val() + '&syncType=2&familyId='
			+ $('#familyId').val(), true);
}

var cards = {};
var selectCards = [];

$(function() {
	$.each($('select'), function(i, item) {
		var sText = $(item).find("option:selected").text();
		var obj = $(item).parent().find('.text');
		$(obj).text(sText);
		$(obj).attr('data-id', $(item).val());

		if ($(obj).attr('id') == 'family') {
			$('#noCardTips')
					.text(sText + '还没有关联医院就诊卡信息，无法查询' + $('#moduleName').val() + '信息，请先去关联医院就诊卡信息，或者切换其他就诊人查询。');
			$('#familyId').val($(item).val());
		}
	});

	$('#reportType').val(1);
	getAllCards();
});

function selectScreening(obj) {
	var option = obj.children[obj.selectedIndex];
	var html = option.innerHTML;
	obj.previousElementSibling.innerHTML = html;
	$(obj.previousElementSibling).attr('data-id', $(option).val());

	if ($(obj).attr('name') == 'familyFilter') {
		$('#noCardTips').text(html + '还没有关联医院就诊卡信息，无法查询' + $('#moduleName').val() + '信息，请先去关联医院就诊卡信息，或者切换其他就诊人查询。');
		$('#familyId').val($(option).val());
	}
}

function getAllCards() {
	$Y.loading.show('正在初始化就诊卡信息...');
	$.ajax({
		type: "POST",
		url: appPath + "mobileApp/common/findCardsForEasyHealth",
		data: {
			openId: $('#openId').val(),
			areaCode: $('#areaCode').val()
		},
		cache: false,
		dataType: "json",
		timeout: 600000,
		error: function(data) {
			$Y.loading.hide();
			// console.log('获取就诊卡异常');
			showHasNoCard();
		},
		success: function(data) {
			$Y.loading.hide();
			// console.log(data);
			if (data.status == 'OK' && data.message && data.message.length > 0) {
				cards = data.message;
				// 过滤数据
				filterCardByCardNoAndHospitalCode();

				// 加载数据
				var hospitalCode = $('#hospital').attr('data-id');
				var familyId = $('#family').attr('data-id');
				selectCards = findCards(familyId, hospitalCode);
				/*
				if (selectCards && selectCards.length > 0) {
					// console.log(selectCards);
					loadingData();
					loadData();
				} else {
					showHasNoCard();
				}
				*/
			} else {
				showHasNoCard();
			}
		}
	})
}

function filterCardByCardNoAndHospitalCode() {
	var cardNo = $('#cardNo').val();
	var hospitalCode = $('#hospitalCode').val();
	if (cardNo && hospitalCode) {
		// 需要指定医院指定到卡
		$.each(cards, function(i, item) {
			if (item.cardNo == cardNo && item.hospitalCode == hospitalCode) {
				// 家人
				var familyId = item.familyId;
				var familyOption = $('#familyFilter option[value="' + familyId + '"]');
				// familyOption.attr("selected", true);
				$('#familyFilter').val(familyId);
				$('#family').text(item.name);
				$('#family').attr('data-id', familyId);

				// 医院
				var hospitalOption = $('#hospitalFilter option[value="' + hospitalCode + '"]');
				// hospitalOption.attr("selected", true);
				$('#hospitalFilter').val(hospitalCode);
				$('#hospital').text(item.hospitalName);
				$('#hospital').attr('data-id', hospitalCode);
				return;
			}
		});
	} else {
		return;
	}
}

function findCards(familyId, hospitalCode) {
	var resultCards = [];
	if (hospitalCode == '0') {
		$.each(cards, function(i, item) {
			if (item.familyId == familyId) {
				// 还需要加一个判断，这个卡所在的医院是否支持这个操作... 			----2015-12-30
				var option = $('#hospitalFilter option[value="' + item.hospitalCode + '"]');
				if (option && option.length > 0) {
					resultCards.push(item);
				}
			}
		});
	} else {
		$.each(cards, function(i, item) {
			if (item.hospitalCode == hospitalCode && item.familyId == familyId) {
				resultCards.push(item);
			}
		});
	}

	return resultCards;
}

function changeHospital(obj) {
	var hospitalCode = $(obj).val();
	if (hospitalCode && hospitalCode != '0') {
		var familyId = $('#family').attr('data-id');

		selectCards = findCards(familyId, hospitalCode);

		if (selectCards && selectCards.length > 0) {
			loadingData();
			// 加载数据
			loadData();
		} else {
			showHasNoCard();
		}
	} else {
		return false;
	}
}

function changeFamily(obj) {
	var familyId = $(obj).val()
	var hospitalCode = $('#hospital').attr('data-id');

	if (hospitalCode && hospitalCode != '0') {
		selectCards = findCards(familyId, hospitalCode);
	
		if (selectCards && selectCards.length > 0) {
			loadingData();
			// 加载数据
			loadData();
		} else {
			showHasNoCard();
		}
	}
}