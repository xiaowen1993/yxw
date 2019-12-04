<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>三公里就医圈</title>
</head>
<body>
<div id="body">
    <div class="space15"></div>
    <ul class="yx-list">
        <li id="threeKmAdd" class="">
            <div class="title">当前位置：<span class="threeKmAdd_text" id="location"></span></div>
            <div class="btn3kmRefresh" onclick="relocation();"><i class="iconfont green ">&#xe61d;</i></div>
        </li>
    </ul>
    <div class="pageTitle">附近的医疗机构</div>
    <ul class="yx-list" id="hospUl">
       <!-- <li class="arrow" onclick="go('detail.html')">
            <div class="title">深圳第二人民医院</div>
            <div class="mate color999">深圳福田区XXX路1056888115号</div>
            <div class="mate color999">距离您 <span class="skinColor">10m</span></div>
        </li>
        <li class="arrow" onclick="go('detail.html')">
            <div class="title">深圳第二人民医院</div>
            <div class="mate color999">深圳福田区XXX路1056888115号</div>
            <div class="mate color999">距离您 <span class="skinColor">320m</span></div>
        </li>
        <li class="noDataText">
            <div class="title">附近没有医院</div>
        </li> -->
    </ul>
    <div class="space15"></div>
   
</div>
<div id="copyright"> 医享网出品 </div>
</body>
<script src="${basePath}/js/json_utils.js"></script>
<script type="text/javascript">
	
	function relocation()
	{
		$('#threeKmAdd .btn3kmRefresh').addClass('rotate');
		$('#hospUl').empty();
		$Y.loading.show('重新定位中...');
		setTimeout("$Y.loading.hide();$('#threeKmAdd .btn3kmRefresh').removeClass('rotate');if(IS.isMacOS){try{setTimeout(\"appStartLocate(parseInt('30000'))\",1000); } catch (e) {}} else if(IS.isAndroid){ try{window.yx129.appStartLocate(parseInt('30000')); } catch (e) {}}",2000);
	} 
	
	$(function()
	{
		if(IS.isMacOS){
	        try{
	        	setTimeout("appStartLocate(parseInt('3000'))",1000);
	        } catch (e) {}}
	    else if(IS.isAndroid){
	        try{window.yx129.appStartLocate(parseInt('3000'));
	        } catch (e) {}
	    }
		//easyHealthData('{"type":"hospital","location":"广东省广州市天河区广州大道中靠近珠控国际中心","code":0,"hospitals":[]}');
	});
	
	function easyHealthData(dataObj)
	{
		$.ajax({
        	url:'${basePath}/easyhealth/hospitalCircle/filterHospital',
        	type:'POST',
        	data:{jsonStr:$json.toJSONString(dataObj)},
        	success:function(resp)
        	{
    			$('#location').text(resp.message.location);
        		if(resp.status=='OK')
        		{
        			if(resp.message.hospitalList.length>0)
        			{
		        		$.each(resp.message.hospitalList, function(i, item) {
		        		   if(item.hospId)
		        		   {
			        		   var $hosp = $('<li class="arrow" onclick="toHospDetail(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\')">'+
			        		   			   '<div class="title">'+item.hospName+'</div>'+
			        		   			   '<div class="mate color999">'+item.address+'</div>'+
			        		   			   '<div class="mate color999">距离您 <span class="skinColor">'+item.distance+'</span> 米</div>'+
			        		   			   '</li>');
		        		   }
		        		   else
		        		   {
		        		   		var $hosp = $('<li class="arrow" onclick="toHospDetail(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\')">'+
			        		   			   '<div class="title">'+item.hospName+'</div>'+
			        		   			   '<div class="mate color999">'+item.address+'</div>'+
			        		   			   '<div class="mate color999">距离您 <span class="skinColor">'+item.distance+'</span> 米</div>'+
			        		   			   '</li>');
		        		   }
					       $('#hospUl').append($hosp);
				        });
        			}
        			else
        			{
        				var nodata = $('<li class="noDataText"><div class="title">附近没有医院</div></li>');
						$('#hospUl').empty();
						$('#hospUl').append(nodata);
        			}
        		}
        		else
        		{
        			var $hosp = $('<li class="noDataText"><div class="title">三公里范围内未发现任何医院</div></li>');
					$('#hospUl').empty();
					$('#hospUl').append($hosp);
        		}
        	},
        	error:function(resp)
        	{	
        		var errmsg = $('<li class="noDataText"><div class="title">三公里范围内未发现任何医院</div></li>');
				$('#hospUl').empty();
				$('#hospUl').append(errmsg);
        	}
        });
	}
	
	function toHospDetail(hospId,telphone,address,hospName,appId,appCode,hospCode)
	{
		var freshForm=$("<form></form>");
		freshForm.append($('<input type="hidden" name="hospId" value="'+hospId+'"/>'));
		freshForm.append($('<input type="hidden" name="telphone" value="'+telphone+'"/>'));
		freshForm.append($('<input type="hidden" name="address" value="'+address+'"/>'));
		freshForm.append($('<input type="hidden" name="hospName" value="'+hospName+'"/>'));
		freshForm.append($('<input type="hidden" name="appId" value="'+appId+'"/>'));
		freshForm.append($('<input type="hidden" name="appCode" value="'+appCode+'"/>'));
		freshForm.append($('<input type="hidden" name="hospCode" value="'+hospCode+'"/>'));
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}/easyhealth/hospitalCircle/toHospInfo");
		freshForm.submit();
	}
	
</script>
</html>
