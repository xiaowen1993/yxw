//时间插件
var isHadInit = false;

$(function() {
	var Time = {};
	function getToday() {
		var myDate = new Date();
		var Y = myDate.getFullYear();
		var M = myDate.getMonth();
		var D = myDate.getDate();
		var date = Y + '-' + M + '-' + D;
		Time.Y = Y;
		Time.M = M;
		Time.D = D;
		//   console.log(Time);
	}
	getToday();

	//获取月份的天数
	Time.getDaysInMonth = function(year, month) {
		return 32 - new Date(year, month, 32).getDate();
	}

	//获取月份中的第一天是所在星期的第几天
	// month：(0--11) 月份从0开始
	// return (0-6) 0：星期日 1：星期一 ...
	Time.getFirstDayOfMonth = function(year, month, day) {
		var day = day || 1;
		return new Date(year, month, day).getDay();
	};
	window.myDate = Time;

	
	//计算日历显示的高度  add by yuce
	var showDays = 7;
	var isHasDutyReg = 1;
	var showDaysDom = $("#showDays");
	var isHasDutyRegDom = $("#isHasDutyReg");
	var isHasAppointmentReg = $("#isHasAppointmentReg").val();
	
	if(showDaysDom){
		showDays = $(showDaysDom).val();
	}
	
	if(isHasDutyRegDom){
		isHasDutyReg = $(isHasDutyRegDom).val();
	}
	
	
	var height = 90 * ( 1+ Math.ceil((showDays - 4) / 5));
	//alert("showDays :" + showDays +  "     height :" + height )
	
	Time.Run = function() {
		var my_width = $(window).width() / 5;
		my_width++;
		var html = '';
		var p = Time.D;
		var week = Time.getFirstDayOfMonth(Time.Y, Time.M, Time.D);
		for ( var i = 1; i <= showDays; i++) {
			if (p > Time.getDaysInMonth(Time.Y, Time.M)) {
				p = 1;
			}
			if (week > 6) {
				week = 0;
			}
			var w = '';
			switch (week) {
				case 0:
					w += '日';
					break;
				case 1:
					w += '一';
					break;
				case 2:
					w += '二';
					break;
				case 3:
					w += '三';
					break;
				case 4:
					w += '四';
					break;
				case 5:
					w += '五';
					break;
				case 6:
					w += '六';
					break;
			}

			if (p == Time.D) {
				if(isHasDutyReg == 1 ){
					html += '<div class="d-grid goHook active" style="width:'
				}else{
					html += '<div class="d-grid noHook " style="width:'
				}
				
				html += my_width
						+ 'px"> <div class="d-grid-content"> <span class="week">'
						+ w
						+ '</span><span class="date">今</span></div><div class="border"></div><div class="right-border"></div><div class="border-arrow"></div> </div>';
			} else if (i == 5) {
				html += '<div class="d-grid" style="width:'
						+ (my_width - 1)
						+ 'px" onclick="$Time.toggle(this)" > <div class="d-grid-content"><div class="d-title"><i class="icon-arrow-show"></i><br/><span>展开</span></div></div><div class="border"></div><div class="right-border" style="border: none"></div><div class="border-arrow"></div> </div>';
				if(isHasAppointmentReg == 1){
					html += '<div class="d-grid goHook" style="width:';
				}else{
					html += '<div class="d-grid noHook " style="width:'
				}
				
				html += my_width
						+ 'px"> <div class="d-grid-content"><span class="week">'
						+ w
						+ '</span><span class="date">'
						+ p
						+ '</span></div><div class="border"></div><div class="right-border"></div><div class="border-arrow"></div> </div>';
			} else {
				if (i == 9 || i == 14 || i == 19 || i == 24 || i == 29 || i == 34) {
					if(isHasAppointmentReg == 1){
						html += '<div class="d-grid goHook" style="width:';
					}else{
						html += '<div class="d-grid noHook " style="width:'
					}
					html += (my_width - 1)
							+ 'px;"> <div class="d-grid-content"> <span class="week">'
							+ w
							+ '</span><span class="date">'
							+ p
							+ '</span></div><div class="border"></div><div class="right-border" style="border: none"></div><div class="border-arrow"></div> </div>';
				} else {
					if(isHasDutyReg == 0 && i == 2 ){
						html += '<div class="d-grid goHook active" style="width:'
					}else{
						if(isHasAppointmentReg == 1){
							html += '<div class="d-grid goHook" style="width:';
						}else{
							html += '<div class="d-grid noHook " style="width:'
						}
					}
						html += my_width
							+ 'px"> <div class="d-grid-content"> <span class="week">'
							+ w
							+ '</span><span class="date">'
							+ p
							+ '</span></div><div class="border"></div><div class="right-border" ></div><div class="border-arrow"></div> </div>';
				}
			}
			p++;
			week++;
		}
		$('#select-date').html(html);
		Time.toggle = function(obj) {
			var index = $('.d-grid.active').index();
			var obj = $(obj);
			var t = obj.find('.d-title');
			if (t.hasClass('up')) {
				t.removeClass('up');
				t.find('span').html('展开');
				$('#select-date').removeClass('show');
				$('#select-date').css({height:89});

				if (index > 3) {
					$('.d-grid.active .border-arrow').hide();
				}
			} else {
				t.addClass('up');
				t.find('span').html('收起')
				$('#select-date').addClass('show');
				$('#select-date').css({height:height});
				//                    $('#select-date').animate({height:268},200)
				$('.d-grid.active .border-arrow').show();
				 var size = $('.d-grid').size();
				var yu = size%5;
				$('.d-grid').slice(size-yu).find('.border').hide();

			}
		}
		Time.goHook = function() {
			if (!$(this).hasClass('noNum')) {
				$('.d-grid.goHook').removeClass('active');
				$('.d-grid.goHook .border-arrow').removeAttr('style')
				$(this).addClass('active')
				
				var hookDate;
				var hookDays = $(this).find(".date").text();
				//大于今天的日期  可判断为本月的日期
				
				if(hookDays > Time.D){
					hookDate = Time.Y + "-" + completeDateNum(Time.M + 1) + "-" + completeDateNum(hookDays);
				}else if(hookDays < Time.D){
					//小于今天的日期  可判断为下个月的日期
					//判断本月是否为12月 若是 则年要加1
					if(Time.M == 11){
						hookDate = (Time.Y + 1) + "-01-" + completeDateNum(hookDays);
					}else{
						hookDate = Time.Y + "-" + completeDateNum(Time.M + 2) + "-" + completeDateNum(hookDays);
					}
				}else{
					hookDate = Time.Y + "-" + completeDateNum(Time.M + 1) + "-" + completeDateNum(Time.D)
				}
				//alert("hookDate : "  + hookDate)
				loadDoctorList(hookDate);
			}
		}
		$('.goHook').on('click', Time.goHook);
	}

	Time.Run();
	window.$Time = Time;
	
	if(!isHadInit && isHasDutyReg == 1){
		//加载今天的号源信息
		loadDoctorList(Time.Y + "-" + completeDateNum(Time.M + 1) + "-" + completeDateNum(Time.D));
	}
	if(!isHadInit && isHasAppointmentReg == 1){
		var nextDay = $("#nextDay").val()
		loadDoctorList(nextDay);
	}
});


function completeDateNum(num){
	if(num < 10){
		return "0" + num;
	}else{
		return num;
	}
}



/**
 * 加载该科室的号源信息
 * @param hookDate
 */
function loadDoctorList(hookDate){
	if($("#selectRegDate")){
		$("#selectRegDate").val(hookDate);
	}
	//添加时间戳  解决移动段 返回不请求服务端的bug
	var now = new Date();
	var timeTemp = now.getTime();
	
	//加载当天的号源信息
	var url = base.appPath +  "/easyhealth/register/doctor/queryDoctorList?timeTemp=" + timeTemp;
    var datas = $("#paramsForm").serializeArray(); 
    var doctorList = $("#doctorList");	
	if(doctorList){
		$Y.loading.show();
        $.ajax( {  
           type : 'POST',  
           url : url,  
           data : datas,  
           dataType : 'json',  
           timeout:120000,
           success : function(data) { 
               $Y.loading.hide();
               successExe(data);
           },  
           error : function(xmlHttpRequest ,msg ,e) { 
	           //解决3星手机使用物理返回时数据加载
        	   $("#paramsForm").attr("action" ,base.appPath + "easyhealth/register/doctor/index?timeTemp=" + timeTemp);
        	   $("#paramsForm").submit();
           }  
        });  
	}
}

function successExe(data){
    var doctorList = $("#doctorList");	
	var isTimeValid = data.message.isTimeValid;
    var htmlStr = "";
    if(isTimeValid){
        var doctors = data.message.doctors; 
        var isViewSourceNum = data.message.isViewSourceNum;
        var isViewSourceNumReg = data.message.isViewSourceNumReg;
        var branchHospitalCode = data.message.branchHospitalCode; 
        var branchHospitalId = data.message.branchHospitalId; 
        var branchHospitalName = data.message.branchHospitalName; 
        if(branchHospitalCode)
        {
        	$('#branchHospitalCode').val(branchHospitalCode);
        }
        if(branchHospitalName)
        {
        	$('#branchHospitalName').val(branchHospitalName);
        }
        if(branchHospitalId)
        {
        	$('#branchHospitalId').val(branchHospitalId);
        }
        
	    if(doctors){
	    	if(doctors.length > 0){
	    		for(var i = 0 ; i < doctors.length ; i++){
	    			var picUrl = doctors[i].picture;
		            if(picUrl == "null" || picUrl == "" || picUrl == null){
		            	picUrl = base.appPath + "yxw.app/images/touxiang.png"
		            }
		                    	
		            htmlStr += "<li class='arrow' onclick='goDoctorTimeList(\"" + doctors[i].doctorCode + "\",\"" + doctors[i].deptCode + "\",\""  + doctors[i].deptName + "\",\""  + doctors[i].category + "\",\""   + doctors[i].picture  + "\")' >";
		            htmlStr += "    <div class='li-right'>";
		            if(doctors[i].category == "1"){
		            	htmlStr += "    <span class='tag skinBgColor label'>专家号</span>";
		            }
		            htmlStr += "    </div>";
		            htmlStr += "    <div class='pic'><img src='" +  picUrl + "'  width='65' height='65'/></div>";
		            htmlStr += "    <div class='info'>"
		            htmlStr += "         <div class='title'>" + doctors[i].doctorName  +  "</div>"
		            htmlStr += "         <div class='mate'> " + ((doctors[i].doctorTitle == "null" || doctors[i].doctorTitle == null) ? "" : doctors[i].doctorTitle)  + "</div>"
					if(doctors[i].leftTotalNum != 'NaN'){
						if (isViewSourceNum && isViewSourceNum != 0) {
							if (doctors[i].leftTotalNum > 0) {
								htmlStr += " <div class='mate'>剩余<span class='skinColor fontSize120'>" + (doctors[i].leftTotalNum < 0 ? 0 : doctors[i].leftTotalNum) + "</span>个号</div>"
							} else {
								htmlStr += " <div class='mate'><span class='skinColor'>已满</span></div>"
							}
						}
						if (isViewSourceNumReg && isViewSourceNumReg != 0) {
							if (doctors[i].leftTotalNum > 0) {
								htmlStr += " <div class='mate'>剩余<span class='skinColor fontSize120'>" + (doctors[i].leftTotalNum < 0 ? 0 : doctors[i].leftTotalNum) + "</span>个号</div>"
							} else {
								htmlStr += " <div class='mate'><span class='skinColor'>已满</span></div>"
							}
						}
					}
		            htmlStr += "	</div>";
		            htmlStr += "</li>"
		         }
	         }else{
	        	 	htmlStr += "<li class='noDataText'>";
	                htmlStr += data.message.noSourceTipContent;
	                htmlStr += "</li>";
	         }
	    }else{
	    	htmlStr += "<li class='noDataText'>";
            htmlStr += "网络异常,获取号源信息失败,请您重新操作!";
            htmlStr += "</li>";
	    }
    }else{
    	 htmlStr += "<li class='noDataText'>";
      	 htmlStr += data.message.timeValidTip;
         htmlStr += "</li>";
    }
    doctorList.html(htmlStr);
}


/**
 * 分时号源查询
 * 
 * @returns
 */
function goDoctorTimeList(doctorCode , deptCode ,deptName,category , picture){
	$("#doctorCode").val(doctorCode);
	$("#deptCode").val(deptCode);
	if(deptName != '' && deptName != null && deptName != "null"){
		$("#deptName").val(deptName);
	}
	$("#category").val(category);
	//添加时间戳  解决移动段 返回不请求服务端的bug
	var now = new Date();
	var timeTemp = now.getTime();
	$("#paramsForm").attr("action" ,base.appPath + "easyhealth/register/doctorTime/index?timeTemp=" + timeTemp);
    $("#paramsForm").submit();
}

