<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>三公里就医圈</title>
</head>
<body>
<div id="body">
    <!-- <div class="box-list">
        <div class="box-tips"> <i class="iconfont">&#xe60d;</i> 温馨提示：如果您找不到，可能该<#if locateType=1>药店<#elseif locateType=2>社康中心<#else>诊所</#if>还未收录入库。</div>
    </div>-->
    <ul class="yx-list" id="hospUl">
    </ul>
</div>
<div id="copyright"> 医享网出品 </div>
</body>
<script src="${basePath}/js/json_utils.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/base64.js"></script>
<script>
	var base64=new Base64();
	var locationStr=base64.decode('${locationStr}');
	var locateType='${locateType}';
	var institutionType='';
	//版本功能分界
	var newVersion=114;
	//默认版本号
	var version ="1.1.4";
	$(function(){
		if(locateType==1)
		{
			institutionType='药店';
		}
		else if(locateType==2)
		{
			institutionType='社康中心';
		}
		else{
			institutionType='诊所';
		}
		
	    if(locationStr=='')
		{
			if(IS.isMacOS){
		        try{
		        	setTimeout("version=appGetVersion()",1000);
		        	if(version=="")
		        	{
		        		setTimeout("appStartLocate(parseInt('3000'))",1000);
		        	}
		        	else
		        	{
		        		var versionInt=parseInt(version.replace(new RegExp("[a-zA-Z_.]","gm"),"")); 
		        		if(versionInt<newVersion)
		        		{
		        			setTimeout("appStartLocate(parseInt('3000'))",1000);
		        		}
		        		else
		        		{
			        		setTimeout("appStartLocate(institutionType,parseInt('3000'))",1000);
		        		}
		        	}
		        } catch (e) {}}
		    else if(IS.isAndroid)
		    {
		        try{
		        	version =window.yx129.appGetVersion();
		        	var versionInt=parseInt(version.replace(new RegExp("[a-zA-Z_.]","gm"),"")); 
		        	if(versionInt<newVersion)
		        	{
		        		window.yx129.appStartLocate(parseInt('3000'));
		        	}
		        	else
		        	{
		        		window.yx129.appStartLocate(institutionType,parseInt('3000'));
		        	}
		        } catch (e) {}
		    }
		}
		else
		{
			easyHealthData(locationStr);
		}
		
		
		/*if(locationStr!='')
		{
			easyHealthData(locationStr);
		}else
		{
			easyHealthData('{\"type\":\"hospital\",\"hospitals\":[{\"name\":\"深圳市妇幼保健院测试\",\"distance\":1432,\"addr\":\"深圳市福田路55454号\",\"tel\":\"1353343444\"},{\"name\":\"深圳市第三人民医院测试\",\"distance\":1012,\"addr\":\"深圳市福田路2号\",\"tel\":\"深圳市福田路2号\"},{\"name\":\"北京大学深圳医院测试\",\"distance\":1325,\"addr\":\"深圳市福田路2号\",\"tel\":\"12342424\"},{\"name\":\"滨江街社区卫生服务中心\",\"distance\":5200,\"addr\":\"深圳市福田路2号\",\"tel\":\"1434224\"},{\"name\":\"滨江街社区卫生服务中心(预防保健部)\",\"distance\":5227,\"addr\":\"深圳市福田路2号\",\"tel\":\"154234323\"},{\"name\":\"龙潭社区卫生服务站(龙潭中约大街)\",\"distance\":5228},{\"name\":\"北京街社区卫生服务中心服务网点\",\"distance\":5229},{\"name\":\"海珠区江海街台涌社区卫生服务站(石榴岗路)\",\"distance\":5296},{\"name\":\"天河区中心医院\",\"distance\":5305},{\"name\":\"天河区天园街社区卫生服务中心(棠石路)\",\"distance\":5332},{\"name\":\"昌岗街社区卫生服务中心\",\"distance\":5346},{\"name\":\"光塔街社区卫生服务中心\",\"distance\":5349},{\"name\":\"天元健康服务站\",\"distance\":5381}],\"location\":\"广东省广州市天河区广州大道中靠近珠控国际中心\",\"code\":0}');
		}*/
	});
	
	
	function easyHealthData(dataObj)
	{
		if(locationStr=='')
		{
			locationStr=JSON.stringify(dataObj);
		}
		$.ajax({
        	url:'${basePath}/easyhealth/hospitalCircle/locateOther',
        	type:'POST',
        	data:{jsonStr:$json.toJSONString(dataObj)},
        	success:function(resp)
        	{
        		if(resp.status=='OK')
        		{
        			if(resp.message.nearbyHospitals.length>0)
        			{
		        		$.each(resp.message.nearbyHospitals, function(i, item) {
		        		   var $hosp = $('<li class="arrow" onclick="toOtherDetail(\''+item.hospId+'\',\''+
		        		   		item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\',\''+item.longitude+'\',\''+item.latitude+'\')">'+
			        		   			   '<div class="title">'+item.hospName+'</div>'+
			        		   			   '<div class="mate color999">'+(item.address!=null?item.address:"")+'</div>'+
			        		   			   '<div class="mate color999">距离您 <span class="skinColor">'+item.distance+'</span> 米</div>'+
			        		   			   '</li>');
					       $('#hospUl').append($hosp);
				        });
        			}
        			else
        			{
        				var nodata = $('<li class="noDataText"><div class="title">附近没有<#if locateType==1>药店<#elseif locateType==2>社康中心<#else>诊所</#if></div></li>');
						$('#hospUl').empty();
						$('#hospUl').append(nodata);
        			}
        		}
        		else
        		{
        			var $hosp = $('<li class="noDataText"><div class="title">三公里范围内未发现任何<#if locateType==1>药店<#elseif locateType==2>社康中心<#else>诊所</#if></div></li>');
					$('#hospUl').empty();
					$('#hospUl').append($hosp);
        		}
        	},
        	error:function(resp)
        	{	
        		new $Y.confirm({
			            content:"<div style='text-align:center'>定位失败<br/>可能是您忘了开启手机定位服务</div>",
			            ok:{
			                title:'我知道了'
			            }
			    });
        		var errmsg = $('<li class="noDataText"><div class="title">三公里范围内未发现任何<#if locateType==1>药店<#elseif locateType==2>社康中心<#else>诊所</#if></div></li>');
				$('#hospUl').empty();
				$('#hospUl').append(errmsg);
        	}
        });
	}
	
	
	function toOtherDetail(hospId,telphone,address,hospName,appId,appCode,hospCode,longitude,latitude)
	{
		var freshForm=$("<form></form>");
		freshForm.append($('<input type="hidden" name="hospId" value="'+hospId+'"/>'));
		freshForm.append($('<input type="hidden" name="telphone" value="'+telphone+'"/>'));
		freshForm.append($('<input type="hidden" name="address" value="'+address+'"/>'));
		freshForm.append($('<input type="hidden" name="hospName" value="'+hospName+'"/>'));
		freshForm.append($('<input type="hidden" name="appId" value="'+appId+'"/>'));
		freshForm.append($('<input type="hidden" name="appCode" value="'+appCode+'"/>'));
		freshForm.append($('<input type="hidden" name="hospCode" value="'+hospCode+'"/>'));
		freshForm.append($('<input type="hidden" name="locationStr" value="'+base64.encode(locationStr)+'"/>'));
		freshForm.append($('<input type="hidden" name="locateType" value="'+locateType+'"/>'));
		freshForm.append($('<input type="hidden" name="longitude" value="'+longitude+'"/>'));
		freshForm.append($('<input type="hidden" name="latitude" value="'+latitude+'"/>'));
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}/easyhealth/hospitalCircle/toOtherInfo");
		freshForm.submit();
	}
	
	function doGoBack()
	{
		window.location.href="${basePath}easyhealth/hospitalCircle/index";
	}
	
	function doRefresh()
	{
		window.location.href="${basePath}/easyhealth/hospitalCircle/toOtherIndex?type=${locateType}";
	}
	
</script>
</html>
