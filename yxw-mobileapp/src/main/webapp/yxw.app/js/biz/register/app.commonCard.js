var commonCard = {
	cardInfos: {}, 	
	init: function() {
		commonCard.bindEvent();
		commonCard.loadCard();
	},
	bindEvent: function() {
		$('#addFamily').off('click').on('click', function() {
			commonCard.addFamily();
		});
	},
	loadCard: function() {
		$Y.loading.show()
		// 参数
		var datas = {};
		datas.openId = base.openId;
		datas.appCode = base.appCode;
		datas.areaCode = base.areaCode;
		datas.hospitalCode = $('#hospitalCode').val();
		datas.branchHospitalCode = $('#branchHospitalCode').val();

		$.ajax({
			type: 'POST',
			url: base.appPath + 'app/common/getRegCards',
			data: datas,
			dataType: 'json',
			timeout: 120000,
			success: function(data) {
				$Y.loading.hide()
				if (data.status == "OK") {
					commonCard.formatData(data.message.entities);
					commonCard.cardInfos = data.message.entities;
					$('#appFamilyLimit').val(data.message.appFamilyLimit);
					commonCard.bindEventAfterComplete();
				} else {
					// 加载不了？后续处理？ 处理毛线

				}
			},
			error: function(data) {
				$Y.loading.hide();
			}
		});
	},
	bindEventAfterComplete: function() {
		$('ul.ui-list-radio').off('click').on('click', ' li', function() {
			var li = $(this)
			li.addClass('active').siblings().removeClass('active')
		});

		$('div.asyncCard').off('click').on('click', function() {
			var familyId = $(this).parent().parent().attr('familyId');
			commonCard.asyncCard(familyId, $(this));
		});

		$('div.manualBind').off('click').on('click', function() {
			commonCard.toBindCard($(this));
		});

		$('ul.ui-list-radio>li:first').trigger('click');
	},
	formatData: function(data) {
		var sCard = '';
		var sNoCard = '';
		var curFamilyCount = 0;

		$.each(data, function(key, value) {
			++curFamilyCount;
			if (value.card) {
				sCard += '<li familyId="' + value.family.id + '">';
				sCard += '	<div class=""><i class="icon-radio"></i>' + value.card.encryptedPatientName + '</div>';
				sCard += '	<div class="master">' + value.card.cardNo + '</div>';
				sCard += '</li>';
			} else {
				sNoCard += '<li familyId="' + value.family.id + '">';
				sNoCard += '	<div class="info">';
				sNoCard += '		<div>' + value.family.encryptedName + '</div>';
				sNoCard += '		<div class="ui-text-des">(没有关联本院信息)</div>';
				sNoCard += '	</div>';
				sNoCard += '	<div class="master"> ';
				sNoCard += '		<span class="color999 syncTips" style="display: none;">正在关联...</span>';
				sNoCard += '		<div class="btn btn-ok btn-small btn-async asyncCard">一键绑卡</div>';
				sNoCard += '		<div class="btn-async manualBind" style="display: none;">手动绑卡</div>';
				sNoCard += '	</div>';
				sNoCard += '</li>';
			}
		});

		$('#curFamilyCount').val(curFamilyCount);
		$('#hasCard').html('').append(sCard);
		$('#noCard').html('').append(sNoCard);
		
		var forward = $('#cardForm input[name="forward"]').val();
		
		//新增判断，方便用户清楚的知道是完善自己的信息。
		var sessionIsFamilySelfInfo = $('#sessionIsFamilySelfInfo').val();
		if (sessionIsFamilySelfInfo=='') {
			var sNoUserInfo = '';
			sNoUserInfo += '<li>';
			sNoUserInfo += '	<div class="info">';
			sNoUserInfo += '		<div style="font-size:16px">本人信息未完善</div>';
			sNoUserInfo += '		<div class="ui-text-des">卡号：</div>';
			sNoUserInfo += '	</div>';
			sNoUserInfo += '	<div class="master"> ';
			sNoUserInfo += '		<div class="btn btn-ok btn-small btn-asyncs perfectInfo" >一键完善</div>';
			sNoUserInfo += '	</div>';
			sNoUserInfo += '</li>';
			
			$('#noUserInfo').html(sNoUserInfo);
			
			$('#noUserInfo li').click(function(){
				 var myBox = new $Y.confirm({
				    	content:'<div>请先完善个人信息</div>',
				    	title:"温馨提示",
						ok:{
							title:"确定",
							click:function(){ //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
								myBox.close();
								window.location.href = base.appPath + "easyhealth/user/toPerfectUserInfo?forward=" + encodeURIComponent(encodeURIComponent(forward));
							}
						},
						cancel:{                   
							title:"取消",
							click:function(){       //参数可为空, 当为空时默认方法关闭窗体
								myBox.close();
							}
						},
						callback:function(){ 
						//窗体显示后的回调
						}
				     });
			});
		}
	},
	toBindCard: function(obj) {
		$(obj).attr('disabled', true);
		$(obj).hide();
		var tipsElement = $(obj).parent().find('.syncTips');
		tipsElement.show();
		tipsElement.removeClass('red');
		tipsElement.addClass('color999');

		$(obj).attr('disabled', true);
		$(obj).hide();
		var tipsElement = $(obj).parent().find('.syncTips');
		tipsElement.show();
		tipsElement.removeClass('red');
		tipsElement.addClass('color999');

		// 完成后的跳转页面
//		var forward = $('#forward').val();
		var familyId = $(obj).parent().parent().attr('familyId');

		// 使用goUrl的方式过去，绑定后，跳回这个页面，并自动刷新。
/*		var url = base.appPath + 'app/usercenter/medicalcard/index' + '?appId=' + $('#appId').val() + '&appCode=' + base.appCode + '&openId=' + base.openId
				+ '&areaCode=' + base.areaCode + '&familyId=' + familyId + '&hospitalId=' + $('#hospitalId').val() + '&hospitalCode='
				+ $('#hospitalCode').val();

		if (forward && forward.length > 0) {
			url += '&forward=' + encodeURIComponent(forward);
		}
		window.location.href = url;*/
		$('#cardForm input[name="familyId"]').val(familyId);
		$('#cardForm').attr("action", base.appPath + "app/usercenter/medicalcard/index");
		$('#cardForm').submit();
		
	},
	asyncCard: function(familyId, obj) {
		// 参数
		var datas = {};
		datas.openId = base.openId;
		datas.appCode = base.appCode;
		datas.areaCode = base.areaCode;
		datas.hospitalCode = $('#hospitalCode').val();
		datas.hospitalId = $('#hospitalId').val();
		datas.branchHospitalCode = $('#branchHospitalCode').val();
		datas.familyId = familyId;

		$Y.loading.show();
		$(obj).hide();

		var tipsElement = $(obj).siblings('.syncTips');
		tipsElement.show();
		tipsElement.removeClass('red');
		tipsElement.addClass('color999');

		$.ajax({
			type: 'POST',
			url: base.appPath + 'app/usercenter/syncCard/syncMedicalcard',
			data: datas,
			dataType: 'json',
			timeout: 120000,
			success: function(data) {
				$Y.loading.hide();
				if (data.status == 'OK' && data.message && data.message.isSuccess == "success") {
					commonCard.init();
				} else if (data.status == "PROMPT") {
					// 失败。且有提示 仅在卡已被绑定的情况下出现。则显示不能关联
					tipsElement.removeClass('color999');
					tipsElement.addClass('red');
					tipsElement.text('已被关联');
					$(obj).hide();
					
					// 弹框提示
					var myBox = new $Y.confirm({
						content: '在' + $('#hospitalName').val() + '的卡已被其他账号绑定（同一个就诊卡只能被一个账号绑定）。',
						ok: {
							title: "确定",
							click: function() {
								myBox.close();
							}
						}
					});
				} else {
					tipsElement.removeClass('color999');
					tipsElement.addClass('red');
					tipsElement.text('关联失败');
					$(obj).hide();
					$(obj).siblings('.btn-async').show();
				}
			},
			error: function(data) {
				$Y.loading.hide();
				tipsElement.removeClass('color999');
				tipsElement.addClass('red');
				tipsElement.text('关联失败');
				$(obj).hide();
				$(obj).siblings('.btn-async').show();
			}
		});
	},
	addFamily: function() {
		var familyLimit = Number($('#appFamilyLimit').val());
		var curFamilyCount = Number($('#curFamilyCount').val());
		// 验证绑卡上限。
		if (curFamilyCount >= (familyLimit + 1)) {
			var myBox = new $Y.confirm({
				content: '最多可添加<span class="red">' + (familyLimit + 1) + '</span>位家人(含本人)。',
				ok: {
					title: "确定",
					click: function() {
						// 关闭界面
						console.log('关闭界面');
						myBox.close();
					}
				}
			});
			return false;
		} 

		/*var forward = $('#forward').val();
		var url = base.appPath + 'app/usercenter/myFamily/addMyFamily';
		url = url + '?openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode
		       	  + '&familyNumbers=' + $('#appFamilyLimit').val() + '&syncType=2'
		          + '&forward=' + forward;
		window.location.href = url;*/
		$('#cardForm input[name="familyNumbers"]').val($('#appFamilyLimit').val());
		$('#cardForm input[name="syncType"]').val(2);
		$('#cardForm').attr("action", base.appPath + "app/usercenter/myFamily/addMyFamily");
		$('#cardForm').submit();
	}
}

$(function() {
	commonCard.init();
});