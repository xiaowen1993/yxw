var chooseDoctorTime = {

	isChangeDate: false,

	init: function() {
		/**计算开始日期start*/
		var dateArray = $('#optionalDates').val().indexOf(",")>0?$('#optionalDates').val().split(","):$('#optionalDates').val();
		var tempDateString = dateArray[1].split(" ")[0]; //当班的后一天开始，所有数组下标是1
		var tempDateArray = tempDateString.split('-');
		var minDate = new Date();
		minDate.setFullYear(parseInt(tempDateArray[0]), parseInt(tempDateArray[1]) - 1, parseInt(tempDateArray[2]));
		/**计算开始日期end*/

		/**计算结束日期start*/
		var dateArray = $('#optionalDates').val().split(",");
		var tempDateString = dateArray[dateArray.length - 1].split(" ")[0];
		var tempDateArray = tempDateString.split('-');
		var maxDate = new Date();
		maxDate.setFullYear(parseInt(tempDateArray[0]), parseInt(tempDateArray[1]) - 1, parseInt(tempDateArray[2]));
		/**计算结束日期end*/

		/**计算当前选择日期start*/
		var selectedIndex = $('#selectedIndex').val();
		$('.js-date').val(dateArray[selectedIndex]); //设置默认值
		/**计算当前选择日期end*/

		var currYear = (new Date()).getFullYear();
		if ($('#regType').val() == '1') {
			$('.js-date').mobiscroll().calendar({
				theme: 'mobiscroll',
				lang: 'zh',
				mode: 'Basic usage',
				formatValue: function(data) {
					var date = data[0] + "-" + (data[1] + 1) + "-" + data[2];
					var dateFormatYear = data[0];
					var dateFormatMounth = (((data[1] + 1) < 10) ? ('0' + (data[1] + 1)) : (data[1] + 1));
					var dateFormatDay = (data[2] < 10) ? ('0' + (data[2])) : data[2];
					var weekDay = new Date().getWeekDay(date);
					return dateFormatYear + "-" + dateFormatMounth + "-" + dateFormatDay + " " + weekDay;
				},
				display: 'center',
				//显示方式
				startYear: currYear,
				//开始年份
				endYear: currYear,
				//结束年份,
				max: maxDate,
				// 根据后台值来设定   //最大日期
				min: minDate,
				//最小日期,
				onBeforeClose: function(event, inst) {
					if (event.valueText != inst._value) {
						chooseDoctorTime.isChangeDate = true;
					}
				},

				onClose: function(event, inst) {
					if (chooseDoctorTime.isChangeDate) {
						$("#selectRegDate").val($('.js-date').val());
						chooseDoctorTime.loadDoctorTimes();
					}
					chooseDoctorTime.isChangeDate = false;
				}
			});
		}

		chooseDoctorTime.loadDoctorTimes();
	},

	loadDoctorTimes: function() {
		var doctimesDom = $("#doctimes_header");
		if (doctimesDom) {
			$Y.loading.show();
			doctimesDom.html("");
			$.ajax({
				type: 'POST',
				url: base.appPath + "easyhealth/register/doctorTime/loadDoctorTime",
				data: $("#paramsForm").serializeArray(),
				dataType: 'json',
				timeout: 120000,
				success: function(data) {
					$Y.loading.hide();
					var doctorTimes = data.message.doctorTimes;
					var curTime = data.message.curTime;
					var curDate = data.message.curDate;
					var selectDate = $("#selectRegDate").val().substring(0, 10);

					if (doctorTimes) {
						var htmlStr = "";
						if (doctorTimes.length > 0) {
							for (var i = 0; i < doctorTimes.length; i++) {
								var workId = doctorTimes[i].workId,
								beginTime = doctorTimes[i].beginTime,
								endTime = doctorTimes[i].endTime,
								regFee = doctorTimes[i].regFee,
								treatFee = doctorTimes[i].treatFee == "" ? 0 : doctorTimes[i].treatFee,
								timeFlag = doctorTimes[i].timeFlag;

								if (stringUtils.isNull(endTime) || (doctorTimes[i].leftNum > 0 && (endTime > curTime || curDate < selectDate))) {
									htmlStr += "<li class='t-item arrow' workId='" + workId + "' beginTime='" + beginTime + "' endTime='" + endTime + "' regFee='" + regFee + "' treatFee='" + treatFee + "' timeFlag='" + timeFlag + "' >";
								} else {
									htmlStr += "<li class='t-item noNum'>";
								}

								htmlStr += "    <div class='li-left'>"
								if (!stringUtils.isNull(endTime)) {
									htmlStr += "         <span class='time skinBgColor'>" + beginTime + " - " + endTime + "</span>";
								} else {
									if (timeFlag == '1') {
										htmlStr += "         <span class='time skinBgColor'>上午</span>";
									} else if (timeFlag == '2') {
										htmlStr += "         <span class='time skinBgColor'>下午</span>";
									} else if (timeFlag == '3') {
										htmlStr += "         <span class='time skinBgColor'>晚上</span>";
									} else {
										htmlStr += "         <span class='time skinBgColor'>全天</span>";
									}
								}

								htmlStr += "    </div>";
								htmlStr += "	   <span class='price'>" + ((parseInt(regFee) + parseInt(treatFee)) / 100) + "</span><span class='des'>元</span>";
								htmlStr += "    <div class='li-right'>";
								//alert(curDate  + ":" + selectDate + "   " +(curDate < selectDate))
								if (doctorTimes[i].leftNum > 0 && (!stringUtils.isNull(endTime) && (endTime > curTime || curDate < selectDate))) {
									htmlStr += "可挂号 ";
								} else if (doctorTimes[i].leftNum == 0) {
									htmlStr += "满号";
								} else {
									if (!stringUtils.isNull(endTime) && curTime > endTime && curDate == selectDate) {
										htmlStr += "过期";
									} else if (stringUtils.isNull(endTime) && !stringUtils.isNull(beginTime) && (curDate < selectDate || (beginTime >= curTime && curDate == selectDate))) {
										htmlStr += "可挂号";
									} else if (stringUtils.isNull(endTime) && stringUtils.isNull(beginTime)) {
										//显示上下午时间格式的判断
										htmlStr += "可挂号";
									} else if (stringUtils.isNull(endTime) && !stringUtils.isNull(beginTime) && beginTime >= curTime && curDate == selectDate) {
										//时间点过期的判断
										htmlStr += "过期";
									}
								}

								htmlStr += "	    </div>";
								htmlStr += "</li>";

							}
						} else {
							htmlStr = '<li class=t-item noDataText">无分时号源信息,请选择<br/>其它时间或其它医生进行挂号</li>';

						}
						doctimesDom.html(htmlStr)

						chooseDoctorTime.bindEvent();
					} else {
						new $Y.confirm({
							content: '<div>网络异常,请保持您的网络通畅,稍后再试.</div>',
							ok: {
								title: '确定'
							}
						})
					}
				},
				error: function(data) {
					$("#paramsForm").attr("action", base.appPath + "easyhealth/register/doctorTime/index");
					$("#paramsForm").submit();
				}
			});
		}
	},

	bindEvent: function() {
		$("#doctimes_header li.arrow").click(function() {
			var workId = $(this).attr('workId'),
			beginTime = $(this).attr('beginTime'),
			endTime = $(this).attr('endTime'),
			regFee = $(this).attr('regFee'),
			treatFee = $(this).attr('treatFee'),
			timeFlag = $(this).attr('timeFlag');
			chooseDoctorTime.confirmRegisterInfo(workId, beginTime, endTime, regFee, treatFee, timeFlag);
		});
	},

	confirmRegisterInfo: function(workId, beginTime, endTime, regFee, treatFee, timeFlag) {
		$("#workId").val(workId);
		if (!stringUtils.isNull(endTime)) {
			$("#doctorEndTime").val(endTime);
		}
		if (!stringUtils.isNull(beginTime)) {
			$("#doctorBeginTime").val(beginTime);
		}
		if (!stringUtils.isNull(timeFlag)) {
			$("#timeFlag").val(timeFlag);
		}
		$("#regFee").val(chooseDoctorTime.convertInt(regFee));
		$("#treatFee").val(chooseDoctorTime.convertInt(treatFee));
		
		/*var forward = $("#paramsForm input:not([name='forward'])").serialize();
		
		var str = CryptoJS.enc.Utf8.parse(forward);
		var base64string = CryptoJS.enc.Base64.stringify(str);
		alert(base64string.length);
		$('#forward').val(base64string);*/
		
		$("#paramsForm").attr("action", base.appPath + "easyhealth/register/confirm/registerInfo");
		$("#paramsForm").submit();
	},

	convertInt: function(fee) {
		if (fee) {
			var index = fee.indexOf(".");
			if (index > -1) {
				fee = fee.substring(0, index);
			}
		}
		console.log(fee);
		return fee;
	}
};

$(function() {
	chooseDoctorTime.init();
});

Date.prototype.getWeekDay = function(dateStr) {
	var weekArr = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	var StringDate = dateStr.split("-");
	var tempDate = new Date();
	tempDate.setFullYear(parseInt(StringDate[0]), parseInt(StringDate[1]) - 1, parseInt(StringDate[2]));

	var week = tempDate.getDay();
	return weekArr[week];
};