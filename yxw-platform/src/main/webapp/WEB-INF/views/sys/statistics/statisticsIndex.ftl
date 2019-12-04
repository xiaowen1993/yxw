<html>
<head>
	<#include "/common/common.ftl">
    <title>手动统计</title>
</head>
<body>
	<div>
		<p>绑卡统计</p>
		<table>
			<tr>
				<td style="text-align: right;">时间：</td>
				<td><input type="text" name="startDate" />&nbsp;至&nbsp;</td>
				<td><input type="text" name="endDate" /></td>
				<td>&nbsp;&nbsp;<button onclick="mcStatistics(this)">统计</button></td>
			</tr>
		</table>
		<hr>
	</div>
	
	<div>
		<p>挂号统计</p>
		<table>
			<tr>
				<td style="text-align: right;">时间：</td>
				<td><input type="text" name="startDate" />&nbsp;至&nbsp;</td>
				<td><input type="text" name="endDate" /></td>
				<td>&nbsp;&nbsp;<button onclick="regStatistics(this)">统计</button></td>
			</tr>
		</table>
		<hr>
	</div>
	
	<div>
		<p>门诊统计</p>
		<table>
			<tr>
				<td style="text-align: right;">时间：</td>
				<td><input type="text" name="startDate" />&nbsp;至&nbsp;</td>
				<td><input type="text" name="endDate" /></td>
				<td>&nbsp;&nbsp;<button onclick="clinicStatistics(this)">统计</button></td>
			</tr>
		</table>
		<hr>
	</div>
	
	<div>
		<p>住院押金统计</p>
		<table>
			<tr>
				<td style="text-align: right;">时间：</td>
				<td><input type="text" name="startDate" />&nbsp;至&nbsp;</td>
				<td><input type="text" name="endDate" /></td>
				<td>&nbsp;&nbsp;<button onclick="depositStatistics(this)">统计</button></td>
			</tr>
		</table>
		<hr>
	</div>
	<form id="statisticsForm" method="post" accept-charset="utf-8">
		<input type="hidden" id="startDate" name="startDate" />
		<input type="hidden" id="endDate" name="endDate" />
	</form>
	<script type="text/javascript">
		var basePath = '${basePath}';
		function mcStatistics(obj){
			var startDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=startDate]").val());
			var endDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=endDate]").val());
			jQuery("#startDate").val(startDate);
			jQuery("#endDate").val(endDate);
			var url = basePath +  "/statistics/medicalCardListTest";
		    var datas = $("#statisticsForm").serializeArray(); 
		    jQuery.ajax( {  
		    	type : 'POST',  
		    	url : url,  
		        data : datas,  
		        dataType : 'json',  
	           	success : function(data) { 
	              	alert(data.message);
	           },error : function(xmlHttpRequest ,msg ,e) {  	
	        	   alert(msg);
	           }  
			});  
		}
		
		function regStatistics(obj){
			var startDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=startDate]").val());
			var endDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=endDate]").val());
			jQuery("#startDate").val(startDate);
			jQuery("#endDate").val(endDate);
			var url = basePath +  "/statistics/orderListTest";
		    var datas = $("#statisticsForm").serializeArray(); 
		    jQuery.ajax( {  
		    	type : 'POST',  
		    	url : url,  
		        data : datas,  
		        dataType : 'json',  
	           	success : function(data) { 
	              	alert(data.message);
	           },error : function(xmlHttpRequest ,msg ,e) {  	
	        	   alert(msg);
	           }  
			});  
		}
	
		function clinicStatistics(obj){
			var startDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=startDate]").val());
			var endDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=endDate]").val());
			jQuery("#startDate").val(startDate);
			jQuery("#endDate").val(endDate);
			var url = basePath +  "/statistics/clinicOrderListTest";
		    var datas = $("#statisticsForm").serializeArray(); 
		    jQuery.ajax( {  
		    	type : 'POST',  
		    	url : url,  
		        data : datas,  
		        dataType : 'json',  
	           	success : function(data) { 
	              	alert(data.message);
	           },error : function(xmlHttpRequest ,msg ,e) {  	
	        	   alert(msg);
	           }  
			});  
		}
		
		function depositStatistics(obj){
			var startDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=startDate]").val());
			var endDate = jQuery.trim(jQuery(obj).parent().parent().find(":text[name=endDate]").val());
			jQuery("#startDate").val(startDate);
			jQuery("#endDate").val(endDate);
			var url = basePath +  "/statistics/depositOrderListTest";
		    var datas = $("#statisticsForm").serializeArray(); 
		    jQuery.ajax( {  
		    	type : 'POST',  
		    	url : url,  
		        data : datas,  
		        dataType : 'json',  
	           	success : function(data) { 
	              	alert(data.message);
	           },error : function(xmlHttpRequest ,msg ,e) {  	
	        	   alert(msg);
	           }  
			});  
		}
	</script>
</body>
</html>

