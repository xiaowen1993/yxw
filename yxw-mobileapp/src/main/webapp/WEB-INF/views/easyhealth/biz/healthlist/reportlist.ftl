<!DOCTYPE html>
<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
  	<#include "/easyhealth/common/common.ftl">
    <title>报告记录</title>
</head>
<body>
<div class="search-zoom-btn fixed" onclick="toSearch()"><i class="iconfont">&#xe67a;</i></div>
<div id="body">
    <div id="payRecord">
    	<div class="screeningBox">
            <ul class="yx-list">
                <li class="flex">
                    <div class="flexItem">
                        <label>
	                        <span id="loadReportType" class="text" data-value="1">检验</span>
	                        <select name="reportType" class="select-screen-box" onchange="changeType(this)" value="1">
	                            <option value="1">检验</option>
	                            <option value="2">检查</option>
	                            <option value="3">体检</option>
	                        </select>
                            <i class="iconfont"></i>
                        </label>
                    </div>
                    <div class="flexItem">
                        <label>
	                        <span class="text" data-value="0">全部医院</span>
	                        <select name="hospitalFilter" id="hospitalFilter" class="select-screen-box" onchange="filterRecord(this)">
	                            <option value="0">全部医院</option>
	                            <#list entityList as item>
	                            <option value="${item.hospitalCode}">${item.hospitalName}</option>
	                            </#list>
	                        </select>
                            <i class="iconfont"></i>
                        </label>
                    </div>
                    <div class="flexItem">
                        <label>
	                        <span class="text" data-value="0">全部日期</span>
	                        <select name="dateFilter" id="dateFilter" class="select-screen-box" onchange="filterRecord(this)">
	                            <option value="0">全部日期</option>
	                            <option value="1">今天</option>
	                            <option value="2">近3天</option>
	                            <option value="3">近7天</option>
	                            <option value="4">近30天</option>
	                        </select>
                            <i class="iconfont"></i>
                        </label>
                    </div>
                </li>
            </ul>
        </div>
        <div class="space15"></div>
        <div id="report-list">
	    </div>
    	<div class="noRecord">
            <div id="success">
                <div class="noticeEmpty"></div>
                <div class="p color666">没有查询到您的报告记录。</div>
            </div>
        </div>
    </div>
</div>
<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${sessionUser.id}" />
	<input type="hidden" id="appCode" name="appCode" value="${appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="idNo" name="idNo" value="${sessionUser.cardNo}" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="">
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}" />
	<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="" />
	<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="" />
	<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="" />
	<input type="hidden" id="reportType" name="reportType" value="" />
	<input type="hidden" id="patCardNo" name="patCardNo" value="" />
	<input type="hidden" id="doctorName" name="doctorName" value="" />
	<input type="hidden" id="deptName" name="deptName" value="" />
	<input type="hidden" id="reportTime" name="reportTime" value="" />
	<input type="hidden" id="executeTime" name="executeTime" value="" />
	<input type="hidden" id="patCardName" name="patCardName" value="${sessionUser.name}" />
	<input type="hidden" id="checkType" name="checkType" value="" />
	<input type="hidden" id="reportId" name="reportId" value="" />
	<input type="hidden" id="detailSource" name="detailSource" value="1" />
</form>
	<script type="text/javascript">
		var dataSource = [];
		$(function() {
			dataSource = [];
			// 复原类型
			$('select[name="reportType"]').get(0).selectedIndex=0;
			$('select[name="reportType"]').siblings('.text').text($('select[name="reportType"]').find("option:selected").text());
			$('select[name="reportType"]').siblings('.text').attr('data-value', $('select[name="reportType"]').val())
			
			// 复原医院
			$('#hospitalFilter').get(0).selectedIndex=0;
			$('#hospitalFilter').siblings('.text').text($('#hospitalFilter').find("option:selected").text());
			$('#hospitalFilter').siblings('.text').attr('data-value', $('#hospitalFilter').val())
			
			// 复原日期
			$('#dateFilter').get(0).selectedIndex=0;
			$('#dateFilter').siblings('.text').text($('#dateFilter').find("option:selected").text());
			$('#dateFilter').siblings('.text').attr('data-value', $('#dateFilter').val())
			
			$('#reportType').val($('#loadReportType').attr('data-value'));
			loadData();
		});
		
		function changeType(obj) {
			$(obj).siblings('.text').text($(obj).find("option:selected").text());
			$(obj).siblings('.text').attr('data-value', $(obj).val())
			$('#reportType').val($(obj).val());
			
			// 复原医院
			$('#hospitalFilter').get(0).selectedIndex=0;
			$('#hospitalFilter').siblings('.text').text($('#hospitalFilter').find("option:selected").text());
			$('#hospitalFilter').siblings('.text').attr('data-value', $('#hospitalFilter').val())
			
			// 复原日期
			$('#dateFilter').get(0).selectedIndex=0;
			$('#dateFilter').siblings('.text').text($('#dateFilter').find("option:selected").text());
			$('#dateFilter').siblings('.text').attr('data-value', $('#dateFilter').val())
			
			loadData();
		}
		
    	function loadData(){
    		hideNoData();
    		    		
    		var url = "${basePath}/easyhealth/healthlist/reports/findReportList";
    		var datas = $("#voForm").serializeArray();
    		console.log(datas);
    		$Y.loading.show('正在为您加载数据');
    		$.ajax({
	           	type : 'POST',  
	           	url : url,  
	           	data : datas,  
	           	dataType : 'json',  
	           	timeout:120000,
	           	error: function(data) {
	           		$Y.loading.hide();
	           		showNoData();
	           	},
	           	success : function(data) {
	           		console.log(data);
	           		$Y.loading.hide();
	           		
	           		if (data.status == 'OK' && data.message && data.message.list && data.message.list.length > 0) {
	           			dataSource = data.message.list;
	           			formatData(dataSource);
	           		} else {
	           			showNoData();
	           		}
	           	}
	    	})
    	}
    	
    	function formatData(data) {
    		var reportType = $('#reportType').val();
    		if (reportType == '1') {
    			formatInspectData(data);
    		} else if (reportType == '2') {
    			formatExamineData(data);
    		} else {
    			formatCheckupData(data);
    		}
    	}
    	
    	function filterRecord(obj) {
			$(obj).siblings('.text').text($(obj).find("option:selected").text());
			$(obj).siblings('.text').attr('data-value', $(obj).val())
			
			if (dataSource.length > 0) {
				var hospitalValue = $('#hospitalFilter').val();
				var dateValue = $('#dateFilter').val();
				var datas = filterData(hospitalValue, dateValue);
				if (datas.length > 0) {
					hideNoData();
					formatData(datas);
				} else {
					showNoData();
				} 
			} else {
				showNoData();
			}
		}
	
		function filterData(hospVal, dateVal) {
			var resultData = [];
			
			if (hospVal == '0' && dateVal == '0') {
				resultData = dataSource;
			} else {
				if (hospVal == '0') {
					resultData = filterByDate(dateVal);
				} else if (dateVal == '0') {
					resultData = filterByHosp(hospVal);
				} else {
					var tempDate = new Date();
					var endDate = tempDate.Format('yyyy-MM-dd');
					var beginDate = getBeginDate(tempDate, dateVal);
					
					$.each(dataSource, function(i, item) {
						var regDate = new Date(item.reportTime).Format('yyyy-MM-dd');
						if (regDate >= beginDate && regDate <= endDate &&　item.hospitalCode == hospVal) {
							resultData.push(item);
						}
					});
				}
			}
			
			return resultData;
		}
		
		function filterByHosp(value) {
			var resultData = [];
			$.each(dataSource, function(i, item) {
				if (item.hospitalCode == value) {
					resultData.push(item);
				}
			});
			
			return resultData;
		}
		
		function filterByDate(value) {
			var resultData = [];
			var tempDate = new Date();
			var endDate = tempDate.Format('yyyy-MM-dd');
			var beginDate = getBeginDate(tempDate, value);
			$.each(dataSource, function(i, item) {
				var regDate = new Date(item.reportTime).Format('yyyy-MM-dd');
				if (regDate >= beginDate && regDate <= endDate) {
					resultData.push(item);
				}
			});
			
			return resultData;
		}
    	
    	/*数据查询方式跳转*/
    	function showDataDetail(obj) {
    		$("#doctorName").val($(obj).attr('doctor'));
    		$("#reportTime").val($(obj).attr('reportTime'));
    		$("#reportId").val($(obj).attr('id'));
    		$("#deptName").val($(obj).attr('dept'));
    		$("#executeTime").val($(obj).attr('executeTime'));
    		$("#checkType").val($(obj).attr('checkType'));
    		$("#hospitalCode").val($(obj).attr('hospitalCode'));
    		$("#patCardNo").val($(obj).attr('cardNo'));
    		
    		$("#voForm").attr("action" , '${basePath}' + 'easyhealth/healthlist/reports/ehReportDetail');
   		 	$("#voForm").submit();
    	}
    	
    	function toSearch() {
    		$('#voForm').attr('action', '${basePath}' + 'easyhealth/healthlist/reports/search');
    		$('#voForm').submit();
    	}
    	
    	/*检查列表*/
    	function formatExamineData(data) {
    		console.log('检查');
    		if (data.length > 0) {
	    		var sHtml = '';
	    		sHtml += '<ul class="yx-list">';
	    		$.each(data, function(i, item) {
	    			sHtml += '<li class="arrow" ' + 
	    						'  id="' + item.id + 
	    						'" dept="' + getValue(item.deptName) + 
	    						'" doctor="' + getValue(item.doctorName) + 
	    						'" executeTime="' + getValue(item.checkTime) + 
	    						'" graphAddress="' + getValue(item.fileAddress) + 
	    						'" hospitalCode="' + item.hospitalCode + 
	    						'" reportTime="' + getTime(item.reportTime) + 
	    						'" cardNo="' + item.cardNo + 
	    						'" checkType="' + getValue(item.checkType) + '" onclick="showDataDetail(this);">';
	    			sHtml += '	<div class="flexItem">';
                    sHtml += '		<div class="name fontSize110">' + item.checkName + '</div>';
                    sHtml += '		<div class="mate color999">' + getTime(item.reportTime) + '</div>';
                    sHtml += '		<div class="mate color999">' + item.hospitalName + '</div>';
                	sHtml += '	</div>';
	    			sHtml += '	</div>';
	    			sHtml += '</li>';
	    		}); 
	    		sHtml += '</ul>';
	    		
	    		$('#report-list').html('');
	    		$('#report-list').append(sHtml);
	    	} else {
	    		showNoData();
	    	}
	    	
    	} 
    	
    	/*检验列表*/
    	function formatInspectData(data) {
    		console.log('检验');
    		if (data.length > 0) {
	    		var sHtml = '';
	    		
	    		sHtml += '<ul class="yx-list">';
	    		$.each(data, function(i, item) {
	    			sHtml += '<li class="arrow boxTable flex" ' + 
	    						'  id="' + item.id + 
	    						'" dept="' + getValue(item.deptName) + 
	    						'" doctor="' + getValue(item.inspectDoctor) + 
	    						'" executeTime="' + getValue(item.provingTime) + 
	    						'" hospitalCode="' + item.hospitalCode + 
	    						'" cardNo="' + item.cardNo + 
	    						'" reportTime="' + getTime(item.reportTime) + 
	    						'" graphAddress="' + getValue(item.fileAddress) + '" onclick="showDataDetail(this);">';
	    			sHtml += '	<div class="flexItem">';
                    sHtml += '		<div class="name fontSize110">' + item.inspectName + '</div>';
                    sHtml += '		<div class="mate color999">' + getTime(item.reportTime) + '</div>';
                    sHtml += '		<div class="mate color999">' + item.hospitalName + '</div>';
                	sHtml += '	</div>';
	    			sHtml += '</li>';
	    		}); 
	    		sHtml += '</ul>';
	    		
	    		$('#report-list').html('');
	    		$('#report-list').append(sHtml);
	    	} else {
	    		showNoData();
	    	}
	    	
    	}
    	
    	/*体检列表*/
    	function formatCheckupData(data) {
    		showNoData();
    	}
    	
    	function showNoData() {
    		$('#report-list').html('');
    		$('.noRecord').show();
    	}
    	
    	function hideNoData() {
    		$('.noRecord').hide();
    	}
    	
    	function getValue(data) {
    		if (data && data != 'null') {
    			return data;
    		} else {
    		 	return "";
    		}
    	}
    	
    	function getBeginDate(tempDate, dateType) {
			var beginDate = '';
			var tDate = new Date(tempDate);
		    /*
			1今天
			2近3天
			3近7天
			4近30天
		    */
		    if (dateType == 1) {
		    	beginDate = tDate.Format('yyyy-MM-dd');
		    } else if (dateType == 2) {
		    	tempDate = tDate.setDate(tempDate.getDate() - 2);
		    	beginDate = tDate.Format('yyyy-MM-dd');
		    } else if (dateType == 3) {
		    	tempDate = tDate.setDate(tempDate.getDate() - 7);
		    	beginDate = tDate.Format('yyyy-MM-dd');
		    } else {
		    	tempDate = tDate.setMonth(tempDate.getMonth() - 1);
		    	beginDate = tDate.Format('yyyy-MM-dd');
		    }
			return beginDate;
		}
    	
    	function getTime(mTime) {
    		if (mTime && mTime.length >= 10) {
    			return mTime.substring(0, 10);
    		} else {
    			return "";
    		}
    	}
    	
    	function doRefresh()
		{
			var freshFrom=$("<form></form>");
			freshFrom.append($('<input type="hidden"  name="openId" value="${commonParams.openId}" />'));
			freshFrom.append($('<input type="hidden"  name="appCode" value="${appCode}" />'));
			freshFrom.append($('<input type="hidden"  name="areaCode" value="ShenZheng" />'));
			freshFrom.append($('<input type="hidden"  name="appId" value="${commonParams.appId}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalCode" value="${commonParams.hospitalCode}" />'));
			freshFrom.append($('<input type="hidden"  name="hospitalId" value="${commonParams.hospitalId}">'));
			freshFrom.append($('<input type="hidden"  name="hospitalName" value="${commonParams.hospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalName" value="${commonParams.branchHospitalName}" />'));
			freshFrom.append($('<input type="hidden"  name="branchHospitalId" value="${commonParams.branchHospitalId}" />'));
			freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />'));
			freshFrom.append($('<input type="hidden" name="checkType" value="${commonParams.checkType}" />'));
			freshFrom.append($('<input type="hidden" name="reportType" value="${commonParams.reportType}" />'));
			freshFrom.append($('<input type="hidden" name="patCardType" value="${commonParams.patCardType}" />'));
			freshFrom.append($('<input type="hidden" name="patCardNo" value="${commonParams.patCardNo}" />'));
			freshFrom.append($('<input type="hidden" name="detailId" value="${commonParams.detailId}" />'));
			freshFrom.append($('<input type="hidden"  name="patCardName" value="${commonParams.patCardName}" />'));
			freshFrom.append($('<input type="hidden" name="doctorName" value="${commonParams.doctorName}" />'));
			freshFrom.append($('<input type="hidden" name="reportTime" value="${commonParams.reportTime}" />'));
			freshFrom.appendTo("body");
			freshFrom.css('display','none');
			freshFrom.attr("method","post");
			freshFrom.attr("action","${basePath}easyhealth/healthlist/reports/ehReportIndex");
			freshFrom.submit();
		}
		
		function doGoBack()
		{
			windowClose();
		}
		
		Date.prototype.Format = function(fmt) { //author: meizz 
			var o = { 
			"M+" : this.getMonth()+1,                 //月份 
			"d+" : this.getDate(),                    //日 
			"h+" : this.getHours(),                   //小时 
			"m+" : this.getMinutes(),                 //分 
			"s+" : this.getSeconds(),                 //秒 
			"q+" : Math.floor((this.getMonth()+3)/3), //季度 
			"S"  : this.getMilliseconds()             //毫秒 
			}; 
			
			if(/(y+)/.test(fmt)) 
			fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	
			for(var k in o) 
			if(new RegExp("("+ k +")").test(fmt)) 
					fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length))); 
			
			return fmt; 
		}
    </script>
    
<#include "/easyhealth/common/footer.ftl">
</body>
</html>