var smartSearch = {

	init: function() {
		var searchType = $('#searchType').val();
		if (searchType == "hospital") {
			$('#searchList').addClass('recent-search');
		} else if (searchType == "dept") {
			$('#searchList').addClass('recent-search');
		} else if (searchType == "doctor") {
			$('#searchList').addClass('search-doctor-result');
		}

		$('#search_btn').click(function() {
			var searchStr = $("#searchKeyword").val();
			smartSearch.doSearch(searchStr);
		});
	},

	doSearch: function(searchStr) {
		if (searchStr == "") {
			return;
		} else {
			var reg = /^[0-9]+|[\u4E00-\u9FA5]+$/;
			if (!reg.test(searchStr)) {
				return false;
			}
			$("#searchStr").val(searchStr);
			var searchType = $('#searchType').val();
			var url = base.appPath + "easyhealth/search/doSearch";
			var datas = $("#paramsForm").serializeArray();
			$Y.loading.show();
			jQuery.ajax({
				type: 'POST',
				url: url,
				data: datas,
				dataType: 'json',
				timeout: 120000,
				success: function(data) {
					$Y.loading.hide();
					var listDom = $("#searchList");
					var htmlStr;
					if (searchType == "hospital") {
						htmlStr = smartSearch.showHospitals(data);
					} else if (searchType == "dept") {
						htmlStr = smartSearch.showDepts(data);
					} else if (searchType == "doctor") {
						htmlStr = smartSearch.showDoctors(data);
					}
					$(listDom).html(htmlStr);

					smartSearch.bindEevent();
				},
				error: function(data) {
					$Y.loading.hide();
				}
			});
		}
	},

	bindEevent: function() {
		if ($('#searchList ul li')) {
			$('#searchList ul li').click(function() {
				var searchType = $('#searchType').val();
				if (searchType == "hospital") {
					smartSearch.toHospitalInfo(this);
				} else if (searchType == "dept") {
					smartSearch.goSearchDeptNext(this);
				} else if (searchType == "doctor") {
					smartSearch.goDoctorRegInfo(this);
				}
			});
		}
	},

	showHospitals: function(data) {
		var htmlStr = "";
		var entityList = data.message.entityList;
		if (entityList && entityList.length > 0) {
			htmlStr += '<ul class="yx-list result-hospital-list" >';
			for (var i = 0; i < entityList.length; i++) {
				//li节点模版
				/*<li class="arrow" data-href=''>
                <div class="keshi-info">
                    <p class="hospital-name">呼吸内科</p>
                    <p class="hospital-address">深圳市人民医院</p>
	                </div>
	            </li> */

				/*htmlStr += "<li onclick='toHospitalInfo(\"" + entityList[i].appId + "\",\"" + entityList[i].appCode + "\",\"" + entityList[i].areaCode + "\",\"" + entityList[i].hospCode + "\",\"" + entityList[i].hospId + "\",\"" + entityList[i].hospName + "\")'>";
				htmlStr += "<span class='title'>" + entityList[i].hospName + "</span>&nbsp"htmlStr += "</li>";*/
				var liDom = ['<li data-appId="' + entityList[i].appId + '" data-appCode="' + entityList[i].appCode + '"  data-hospCode="' + entityList[i].hospCode + '" data-hospId="' + entityList[i].hospId + '" data-hospName="' + entityList[i].hospName + '" class="arrow">', '<div class="keshi-info">', '<p class="hospital-address">' + entityList[i].hospName + '</p>', '</div>', '</li>'];

				htmlStr += liDom.join("");
			}
			htmlStr = htmlStr + '</ul>';
		} else {
			htmlStr += '<div class="recent-search-empty">没有搜到该医院,请重新输入搜索值</div>';
		}
		return htmlStr;
	},

	showDepts: function(data) {
		var htmlStr = "";
		var entityList = data.message.entityList;
		if (entityList && entityList.length > 0) {
			htmlStr += '<ul class="yx-list result-hospital-list" >';
			for (var i = 0; i < entityList.length; i++) {
				//li节点模版
				/*<li data-href=''>
                <div class="keshi-info">
                    <p class="hospital-name">呼吸内科</p>
                    <p class="hospital-address">深圳市人民医院</p>
                </div>
                <div class="search-appointment">
                    <span class="dangban">当班</span>
                </div>
                </li>*/

				/*htmlStr += "<li onclick='goSearchDeptNext(\"" + entityList[i] + "\")'>";
				htmlStr += "<span class='title'>" + entityList[i] + "</span>"htmlStr += "</li>";*/
				var liDom = ['<li class="arrow" row-data=' + entityList[i] + '>', '<div class="keshi-info">', '<p class="hospital-name">' + entityList[i] + '</p>', '</div>', '</li>'];

				htmlStr += liDom.join("");
			}
			htmlStr = htmlStr + '</ul>';
		} else {
			htmlStr += '<div class="recent-search-empty">没有搜到该科室,请重新输入搜索值</div>';
		}
		return htmlStr;
	},

	showDoctors: function(data) {
		var htmlStr = "";
		var entityList = data.message.entityList;
		if (entityList && entityList.length > 0) {
			htmlStr += '<ul class="yx-list result-hospital-list" >';
			for (var i = 0; i < entityList.length; i++) {
				/*<li data-href=''>
				<div class="keshi-info">
				<p class="hospital-name">刘存</p>
				<p class="hospital-address">消化内科</p>
				<p class="hospital-address">深圳市人民医院</p>
				</div>
				</li>*/

				/*var doctorArray = entityList[i].split("#");
				htmlStr += "<li onclick='goDoctorRegInfo(\"" + entityList[i] + "\")'>";
				htmlStr += "<span class='title'>" + doctorArray[0] + "</span>&nbsp"
				if (doctorArray[1] != null && doctorArray[1] != "null") {
					htmlStr += "<span class='label skinBgColor'>" + doctorArray[1] + "</span>"
				}
				htmlStr += "</li>";*/

				var doctorArray = entityList[i].split("#");
				var liDom = ['<li row-data="' + entityList[i] + '">', '<div class="keshi-info">', '<p class="hospital-name">' + doctorArray[0] + '</p>', '<p class="hospital-address">' + doctorArray[1].split('-')[1] + '</p>', '<p class="hospital-address">' + doctorArray[1].split('-')[0] + '</p>', '</div>', '</li>'];

				htmlStr += liDom.join("");
			}
			htmlStr = htmlStr + '</ul>';
		} else {
			htmlStr += '<div class="recent-search-empty">没有搜到该医生,请重新输入搜索值</div>';
		}
		return htmlStr;
	},

	toHospitalInfo: function(obj) {
		var appId = $(obj).attr('data-appId'),
		appCode = $(obj).attr('data-appCode'),
		areaCode = $(obj).attr('data-areaCode'),
		openId = $('#openId').val(),
		hospitalCode = $(obj).attr('data-hospCode'),
		hospitalId = $(obj).attr('data-hospId');
		var url = base.appPath + "easyhealth/register/index";
		var params = "?appId=" + appId + "&appCode=" + appCode + "&areaCode=" + areaCode + "&regType=2" + "&openId=" + openId + "&hospitalCode=" + hospitalCode + "&hospitalId=" + hospitalId;
		
		window.location = url + params;
	},

	goSearchDeptNext: function(obj) {
		$("#deptNameKey").val($(obj).attr('row-data'));
		var datas = $("#paramsForm").serializeArray();
		$Y.loading.show();
		jQuery.ajax({
			type: 'POST',
			url: base.appPath + "easyhealth/register/doctor/deptInfo",
			data: datas,
			dataType: 'json',
			timeout: 120000,
			success: function(data) {
				$Y.loading.hide();
				if (data.message.dept) {
					var dept = data.message.dept;
					if (data.message.isGoRegInfo) {
						$("#paramsForm").formEdit(dept);
						$("#paramsForm").attr("action", base.appPath + "easyhealth/register/doctor/index");
						$("#paramsForm").submit();
					} else {
						$("#paramsForm").formEdit(dept);
						$("#parentDeptCode").val(dept.deptCode);
						$("#parentDeptName").val(dept.deptName);
						$("#paramsForm").attr("action", base.appPath + "easyhealth/search/querySubDepts");
						$("#paramsForm").submit();
					}
				}
			},
			error: function(data) {
				$Y.loading.hide();
			}
		});
	},

	goDoctorRegInfo: function(obj) {
		$("#doctorNameKey").val($(obj).attr('row-data'));
		
		var doctorNameKeyArray = $(obj).attr('row-data').split("#");
		console.log(doctorNameKeyArray);
		//0：医生姓名，1-0：医院名称，1-1：科室名称，2：医院ID，3：医院Code，4：分院Code，5：医生ID，6：科室ID
		$('#deptCode').val(doctorNameKeyArray[6]);$('#deptName').val(doctorNameKeyArray[1].split('-')[0]);$('#doctorCode').val(doctorNameKeyArray[5]);$('#hospitalId').val(doctorNameKeyArray[2]);$('#hospitalCode').val(doctorNameKeyArray[3]);$('#branchHospitalCode').val(doctorNameKeyArray[4]);
		var datas = $("#paramsForm").serializeArray();
		$Y.loading.show();
		jQuery.ajax({
			type: 'POST',
			url: base.appPath + "easyhealth/search/doctorInfo",
			data: datas,
			dataType: 'json',
			timeout: 120000,
			success: function(data) {
				$Y.loading.hide();
				var doctor = data.message.doctor;
				$("#paramsForm").formEdit(doctor);
				$("#paramsForm").attr("action", base.appPath + "easyhealth/register/doctor/index");
				$("#paramsForm").submit();
			},
			error: function(data) {
				$Y.loading.hide();
			}
		});
	}
};

$(function() {
	smartSearch.init();
});

$.fn.formEdit = function(data) {
	return this.each(function() {
		var elementDom;
		var elementDomName;
		if (data == null) {
			this.reset();
			return;
		}
		for (var i = 0; i < this.length; i++) {
			elementDom = this.elements[i];
			elementDomName = elementDom.name;
			if (data[elementDomName] == undefined) {
				continue;
			}
			elementDom.value = data[elementDomName];
		}
	});
};