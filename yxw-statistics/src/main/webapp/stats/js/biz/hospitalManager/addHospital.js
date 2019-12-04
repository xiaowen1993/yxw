var timeoutVar;
var checkName = false;
var checkCode = false;
var addHospital = {
	init: function() {
		// 下一步
		$('#saveBtn').off('click').on('click', function() {
			addHospital.add();
		});
		
		// 检测名称
		$('#inputName').off('blur').on('blur', function() {
			var hospitalName = $('#inputName').val();
			addHospital.checkNameDuplicate(hospitalName);
		});
		
		// 检测代码
		$('#inputCode').off('blur').on('blur', function() {
			var hospitalCode = $('#inputCode').val();
			addHospital.checkCodeDuplicate(hospitalCode);
		});
		
		// 区域模块的初始化
		addHospital.initArea();
		
		// 状态初始化
		var state = $('#state').val();
		if ($('#state').val()) {
			$('#stateSelect').find('option[value="' + state + '"]').attr('selected', 'selected');
		}
	},
	add: function() {
		
		timeoutVar = setTimeout(function () {
			if (!checkName) {
				$('#checkName').html('<p class="text-error">请输入</p>');
				return false;
			} else if (!checkCode) {
				$('#checkCode').html('<p class="text-error">请输入</p>');
				return false;
			}
			
			// 验证区域信息
			if (!addHospital.checkArea()) {
				$('#checkArea').html('<p class="text-error">请输入</p>');
				return false;
			}
			
//			if (!addHospital.checkChange()) {
//				// 没有更改直接跳到下一步
//				$('#voForm').attr('action', appPath + 'hospital/branch/list');
//				$('#voForm').submit();
//			} else {
			var hospitalName = $('#inputName').val();
			var hospitalCode = $('#inputCode').val();
			
			$('#hospitalName').val(hospitalName);
			$('#hospitalCode').val(hospitalCode);
			
			// 区域
			var areaCode = '/' + $('#area_prov').val();
			var areaName = '/' + $('#area_prov').find("option:selected").text();
			
			if ($('#area_city').val() != '-1') {
				areaCode += '/' + $('#area_city').val();
				areaName += '/' + $('#area_city').find("option:selected").text();;
			}
			
			$('#areaCode').val(areaCode);
			$('#areaName').val(areaName);
			
			// 状态
			var state = $('#stateSelect').val();
			$('#state').val(state);
			
			var datas = $('#voForm').serializeArray();
			$.ajax({
				type: 'POST',
				url: 'add',
				data: datas,
				dataType: 'json',
				timeout: 2000,
				error: function(data) {
					console.log(data);
				},
				success: function(data) {
					if (data.status == 'OK') {
		       			if (data.message && data.message != 'null' && !$('#hospitalId').val()) {
		       				$('#hospitalId').val(data.message);
		       			}
		       			
		       			// 跳转
		       			$('#voForm').attr('action', appPath + 'hospital/branch/list');
						$('#voForm').submit();
		       		} else {
		       			alert(data.message);
		       		}
				}
			});
//			}
		}, 400);
	},
	checkChange: function() {
		var hospitalId = $('#hospitalId').val();
		if (hospitalId) {
			var sourceName = $('#sourceName').val();
			var sourceCode = $('#sourceCode').val();
			var sourceAreaCode = $('#sourceAreaCode').val();
			var sourceState = $('#sourceState').val();
			
			return !($('#inputName').val().trim() == sourceName && $('#inputCode').val().trim() == sourceCode
					&& $('#state').val().trim() == sourceState && $('#sourceAreaCode').val().trim() == sourceAreaCode);
		} else {
			return true;
		}
	},
	checkArea: function() {
		var result = $('#area_prov').val() != '-1';
		
		return result;
	},
	checkNameDuplicate: function(name) {
		if (name) {
			$.ajax({
				type: 'POST',
				url: 'check',
				data: {hospitalName: name},
				dataType: 'json',
				timeout: 20000,
				error: function(data) {
					console.log(data);
				},
				success: function(data) {
					if (data.status == 'OK') {
						$('#checkName').html('<p class="text-success">该医院名称可以使用</p>');
						checkName = true;
		       		} else {
		       			clearTimeout(timeoutVar);
		       			$('#checkName').html('<p class="text-error">该医院名称已被使用</p>');
		       			checkName = false;
		       			return false;
		       		}
				}
			});
		} else {
			clearTimeout(timeoutVar);
			$('#checkName').html('<p class="text-info">请输入</p>');
			checkName = false;
			return false;
		}
	},
	checkCodeDuplicate: function(code) {
		if (code) {
			$.ajax({
				type: 'POST',
				url: 'check',
				data: {hospitalCode: code},
				dataType: 'json',
				timeout: 2000,
				error: function(data) {
					console.log(data);
				},
				success: function(data) {
					if (data.status == 'OK') {
						checkCode = true;
						$('#checkCode').html('<p class="text-success">该医院代码可以使用</p>');
		       		} else {
		       			clearTimeout(timeoutVar);
		       			$('#checkCode').html('<p class="text-error">该医院代码已被使用</p>');
		       			checkCode = false;
		       		}
				}
			});
		} else {
			clearTimeout(timeoutVar);
			$('#checkCode').html('<p class="text-info">请输入</p>');
			checkCode = false;
			return false;
		}
	},
	initArea: function() {
		var areaCode = $('#areaCode').val();
		var province = '';
		var city = '';
		
		if (areaCode.indexOf('/') > -1) {
			var temp = areaCode.split('/');
			province = temp[1];
			city = temp[2];
		} else {
			province = areaCode;
		}
		
		$("#area").citySelect({
			nodata: "none",
			prov: province,
			city: city,
			required: false
		}); 
	}
}

$(function() {
	if ($('#inputName').val()) {
		checkName = true;
	}

	if ($('#inputCode').val()) {
		checkCode = true;
	}
	
	addHospital.init();
})