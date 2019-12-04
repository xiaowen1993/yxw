var branchList = {
	init : function() {
		// 删除
		$('#delBranch').off('click').on('click', function() {
			var obj = $('.branchActive');
			if (obj && obj.length == 1) {
				branchList.deleteBranch($(obj).parent().attr('branchId'));
			}
		});

		// 保存
		$('#saveBranch').off('click').on('click', function() {
			var branchName = $('#inputName').val();
			var branchCode = $('#inputCode').val();

			if (!branchName.trim()) {
				alert('请输入分院名称');
				return false;
			}

			if (!branchCode.trim()) {
				alert('请输入分院代码');
				return false;
			}
			
			if (branchName == $('#branchName').val() && branchCode == $('#branchCode').val()) {
				console.log('没有更改');
				return false;
			}
			
			var obj = $('.branchActive');
			if (obj && obj.length == 1) {
				var branchId = $(obj).parent().attr('branchId');
				if (branchId) {
					branchList.updateBranch(branchId, branchName, branchCode);
					return false;
				}
			}

			branchList.addBranch(branchName, branchCode);
		});

		// 下一步
		$('#nextStep').off('click').on('click', function() {
			// 妈的，不存了，直接下一步，爱存不存
			$('#voForm').attr('action', appPath + 'hospital/setting/index');
			$('#voForm').submit();
		});

		// 新增
		$('#newBranch').off('click').on('click', function() {
			$('#branchList>li').find('a').removeClass('branchActive');
			
			$('#inputName').val('');
			$('#inputCode').val('');
			
			$('#branchName').val('');
			$('#branchCode').val('');
			$('#branchId').val('');
		});
	},
	getBranches : function() {
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				hospitalId : $('#hospitalId').val()
			},
			timeout : 20000,
			url : 'getBranches',
			error : function(data) {
				console.log(data);
			},
			success : function(data) {
				if (data.status == 'OK') {
					branchList.formatData(data.message);
				} else {
					alert('挂了。');
				}
			}
		});
	},
	formatData : function(data) {
		if (data && data.length > 0) {
			var sHtml = '';

			$.each(data, function(i, item) {
				sHtml += '<li class="addList" branchId="' + item.id
						+ '" branchName="' + item.name + '" branchCode="'
						+ item.code + '"><a href="javascript: void(0);">'
						+ item.name + '</a></li>';
			});

			$('#branchList').html('').append(sHtml);
			branchList.bindEvent();
			if ($('#branchList>li').length > 0) {
				$('#branchList>li').eq(0).trigger('click');
			}
		} else {
			$('#branchList').html('');
			// 复原
			$('#inputName').val('');
			$('#inputCode').val('');
			
			// 清空
			$('#branchName').val('');
			$('#branchCode').val('');
			$('#branchId').val('');
		}
	},
	bindEvent : function() {
		$('#branchList>li').off('click').on('click', function() {
			$('#branchList>li').find('a').removeClass('branchActive');
			$(this).find('a').addClass('branchActive');
			$('#inputName').val($(this).attr('branchName'));
			$('#inputCode').val($(this).attr('branchCode'));
			
			$('#branchId').val($(this).attr('branchId'));
			$('#branchName').val($(this).attr('branchName'));
			$('#branchCode').val($(this).attr('branchCode'));
		});
	},
	deleteBranch : function(branchId) {
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				id : branchId
			},
			timeout : 20000,
			url : 'deleteBranch',
			error : function(data) {
				console.log(data);
			},
			success : function(data) {
				if (data.status == 'OK') {
					branchList.getBranches();
				} else {
					alert('删除失败,死啦死啊了第。');
				}
			}
		});
	},
	updateBranch : function(branchId, branchName, branchCode) {
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				hospitalId : $('#hospitalId').val(),
				name : branchName,
				code : branchCode,
				id : branchId
			},
			timeout : 20000,
			url : 'updateBranch',
			error : function(data) {
				console.log(data);
			},
			success : function(data) {
				if (data.status == 'OK') {
					branchList.getBranches();
				} else {
					alert(data.message);
				}
			}
		});
	},
	addBranch : function(branchName, branchCode) {
		$.ajax({
			type : 'POST',
			dataType : 'json',
			data : {
				hospitalId : $('#hospitalId').val(),
				name : branchName,
				code : branchCode
			},
			timeout : 20000,
			url : 'addBranch',
			error : function(data) {
				console.log(data);
			},
			success : function(data) {
				if (data.status == 'OK') {
					branchList.getBranches();
				} else {
					alert(data.message);
				}
			}
		});
	}
}

$(function() {
	branchList.init();
	branchList.getBranches();
});