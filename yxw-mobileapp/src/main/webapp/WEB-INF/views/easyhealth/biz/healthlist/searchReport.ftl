<!DOCTYPE html>
<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>报告搜索</title>
    <script type="text/javascript" src="${basePath}yxw.app/js/common/nav-list.js"></script>
</head>
<body>
<div id="body">
	<section class="mod-search">
        <section class="search-select">
            <div class="search-option-box"> <span class="skinColor">　报告</span></div>
            <input type="text" placeholder="请输入关键词" class="search-select-input" id="searchStr">
            <div class="search-select-btn" onclick="toSearch()" id="search_btn">搜索</div>
        </section>
        <div style="height: 15px"></div>
        <section class="search-main">
            <!-- 搜索记录为空的样式 -->
            <article class="seach-result recent-search" id="noData" style="display: none;">
                <div class="recent-search-empty">
                  	  没有查询到您的报告记录
                </div>
            </article>
            <!-- 搜索结果页 -->
            <article class="seach-result recent-search" id="dataList">
            </article>
        </section>
    </section>
</div>
<form id="voForm" method="post">
	<input type="hidden" id="openId" name="openId" value="${commonParams.openId}" />
	<input type="hidden" id="appCode" name="appCode" value="${appCode}" />
	<input type="hidden" id="appId" name="appId" value="${commonParams.appId}" />
	<input type="hidden" id="areaCode" name="areaCode" value="${areaCode}" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
	<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}">
	<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
	<input type="hidden" id="idNo" name="idNo" value="${commonParams.idNo}" />
	<input type="hidden" id="patCardName" name="patCardName" value="${sessionUser.name}" />
	<input type="hidden" id="patCardType" name="patCardType" value="" />
	<input type="hidden" id="patCardNo" name="patCardNo" value="" />
	<input type="hidden" id="reportTime" name="reportTime" value="" />
	<input type="hidden" id="reportId" name="reportId" value="" />
	<input type="hidden" id="doctorName" name="doctorName" value="" />
	<input type="hidden" id="deptName" name="deptName" value="" />
	<input type="hidden" id="reportType" name="reportType" value="" />
	<input type="hidden" id="detailSource" name="detailSource" value="2" />
</form>
<script type="text/javascript">
    var dataSource = [];	
    var hadSearch = false;
    
    $(function() {
	    dataSource = [];	
	    hadSearch = false;
    });
    
    function toSearch() {
    	console.log($('#searchStr').val());
    	var searchStr = $("#searchStr").val();
	    $('#search_btn').css('color','#000');
	    setTimeout(function(){
	    	 $('#search_btn').removeAttr('style');
	    },300)
	    
	    var reg = /^[0-9a-zA-Z]+|[\u4E00-\u9FA5]+$/; 
	    
	    if (searchStr && reg.test(searchStr)) {
	    	if (hadSearch) {
	    		initData(searchStr);
	    	} else {
		        var url = "${basePath}easyhealth/healthlist/reports/findReportsByIdNo";
		        var datas = $("#voForm").serializeArray(); 
		        $Y.loading.show(); 
		        $.ajax({  
		           type : 'POST',  
		           url : url,  
		           data : datas,  
		           dataType : 'json',  
		           timeout : 120000,
		           success : function(data) { 
		           		console.log(data);
		                $Y.loading.hide();
		                if (data.status == 'OK') {
		                	hadSearch = true;
		                	dataSource = data.message.entityList;
		                	initData(searchStr);
		                }
		           },  
		           error : function(data) {  
		           		hadSearch = false;
		                $Y.loading.hide();
		           }  
		        }); 
	    	}
	    }
    }
    
    function initData(str) {
    	if (dataSource.length > 0) {
    		$('#noData').hide();
    		
    		var sHtml = '';
			$.each(dataSource, function(i, item) {
				if (item.reportName.indexOf(str) > -1) {
					sHtml += '<li class="arrow" hospitalCode="' + item.hospitalCode +'"' + 
								' hospitalId="' + item.hospitalId + '"' + 
								' hospitalName="' + item.hospitalName + '"' + 
								' patCardType="' + item.patCardType + '"' + 
								' patCardNo="' + item.patCardNo + '"' + 
								' reportId="' + item.reportId + '"' + 
								' reportTime="' + getTime(item.reportTime) + '"' + 
								' fileAddress="' + item.fileAddress + '"' + 
								' doctorName="' + item.doctorName + '"' + 
								' deptName="' + item.deptName + '"' + 
								' reportType="' + item.reportType + '"' + 
								' onclick="showDetail(this);">';
                    sHtml += '	<div class="keshi-info">';
                    sHtml += '		<p class="hospital-name">' + item.reportName + '</p>';
                    sHtml += '		<p class="hospital-address">' + item.hospitalName + '</p>';
                    sHtml += '		<p class="hospital-address">' + getTime(item.reportTime) + '</p>';
                    sHtml += '	</div>';
                    sHtml += '</li>';
				}
			});
			
			if (!sHtml) {
				$('#noData').show();
    			$('#dataList').html('');
			} else {
				sHtml = '<ul class="yx-list result-hospital-list">' + sHtml + '</ul>';
				$('#noData').hide();
				$('#dataList').html('');
				$('#dataList').html(sHtml);
			}
			
    	} else {
    		$('#noData').show();
    		$('#dataList').html('');
    	}
    }	
    
    function getTime(mTime) {
		if (mTime && mTime.length >= 10) {
			return mTime.substring(0, 10);
		} else {
			return "";
		}
	}
	
	function showDetail(obj) {
		$('#hospitalCode').val($(obj).attr('hospitalCode'));
		$('#hospitalId').val($(obj).attr('hospitalId'));
		$('#hospitalName').val($(obj).attr('hospitalName'));
		$('#patCardType').val($(obj).attr('patCardType'));
		$('#patCardNo').val($(obj).attr('patCardNo'));
		$('#reportTime').val($(obj).attr('reportTime'));
		$('#reportId').val($(obj).attr('reportId'));
		$('#doctorName').val($(obj).attr('doctorName'));
		$('#deptName').val($(obj).attr('deptName'));
		$('#fileAddress').val($(obj).attr('fileAddress'));
		$('#reportType').val($(obj).attr('reportType'));
		
		$("#voForm").attr("action" , '${basePath}' + 'easyhealth/healthlist/reports/ehReportDetail');
   		 $("#voForm").submit();
	}
    	
	function doRefresh() {
		$('#voForm').attr('action', '${basePath}' + 'easyhealth/healthlist/reports/search');
		$('#voForm').submit();
	}
		
	function doGoBack() {
		$('#voForm').attr('action', '${basePath}' + 'easyhealth/healthlist/reports/ehReportIndex');
		$('#voForm').submit();
	}
</script>
<#include "/easyhealth/common/footer.ftl">

</body>
</html>