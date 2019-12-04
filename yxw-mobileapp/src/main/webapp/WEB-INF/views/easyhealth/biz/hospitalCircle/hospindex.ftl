<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>三公里就医圈</title>
</head>
<body>
<div id="body" class="threemRange">
    <div class="space15"></div>
    <ul class="yx-list">
        <li id="threeKmAdd" class="">
            <div class="title">当前位置：<span class="threeKmAdd_text" id="location"></span></div>
            <div class="btn3kmRefresh" onclick="relocation();"><i class="iconfont green ">&#xe61d;</i></div>
        </li>
    </ul>

    <nav class="threemRange-nav" id="threemRange-menu" style="display:none;">
        <div class="threemRange-nav-item" onclick="go('${basePath}easyhealth/cloudHospital/toSZCloudHospital',true);"><i>&#xe686;</i><span>医院</span></div>
        <div class="threemRange-nav-item"  onclick="go('${basePath}easyhealth/hospitalCircle/toOtherIndex?type=1')"><i>&#xe685;</i><span>药店</span></div>
        <div class="threemRange-nav-item"  onclick="go('${basePath}easyhealth/communitycenter/communityHealth/getAdministrativeRegion?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',1)" ><i>&#xe654;</i><span>社康</span></div>
        <div class="threemRange-nav-item"  onclick="go('${basePath}easyhealth/hospitalCircle/toOtherIndex?type=3')"><i>&#xe687;</i><span>诊所</span></div>
    </nav>
    
    <div class="pageTitle" id="discoverDiv">发现</div>
    <ul class="yx-list" id="threemRange-discoveryList">
      <#--  <li class="" onclick="go('detail.html')">
            <div class="threemRange-discoveryList-info">
                <div class="title">深圳第二人民医院</div>
                <div class="mate color999">您曾经在该院就诊</div>
                <div class="mate color999">距离您 <span class="skinColor">10m</span></div>
            </div>
            <div class="hospital-appointment">
                <span class="guahao">去挂号</span>
            </div>
        </li> -->
    </ul>

    <div class="pageTitle">附近的医疗机构</div>
    <ul class="yx-list" id="hospUl">
       <#-- <li class="arrow" onclick="go('detail.html')">
            <div class="title">深圳第二人民医院</div>
            <div class="mate color999">深圳福田区XXX路1056888115号</div>
            <div class="mate color999">距离您 <span class="skinColor">10m</span></div>
        </li>
        <li class="arrow" onclick="go('detail.html')">
            <div class="title">深圳第二人民医院</div>
            <div class="mate color999">深圳福田区XXX路1056888115号</div>
            <div class="mate color999">距离您 <span class="skinColor">320m</span></div>
        </li>
        <li class="arrow" onclick="go('detail.html')">
            <div class="title">深圳第二人民医院</div>
            <div class="mate color999">深圳福田区XXX路1056888115号</div>
            <div class="mate color999">距离您 <span class="skinColor">600m</span></div>
        </li>
        <li class="arrow" onclick="go('detail.html')">
            <div class="title">深圳第二人民医院</div>
            <div class="mate color999">深圳福田区XXX路1056888115号</div>
            <div class="mate color999">距离您 <span class="skinColor">4.5km</span></div>
        </li>

        <li class="noDataText">
            <div class="title">附近没有医院</div>
        </li> -->
    </ul>
    <div class="space15"></div>
</div>
<div id="copyright"> 医享网出品 </div>
</body>
<script src="${basePath}js/json_utils.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/base64.js"></script>
<script type="text/javascript">
	var base64=new Base64();
	var locationStr=base64.decode('${locationStr}');
	//版本功能分界
	var newVersion=114;
	//目前线上版本号
	var version ="${version}";
	$(function(){
		var body = $(document.body);
		body.on('click','.btn3kmRefresh',function(){
			$(this).removeClass('rotate').addClass('rotate');
		})
		$('.btn3kmRefresh').on('webkitAnimationEnd',function(){
			$(this).removeClass('rotate');
		});
		if(locationStr=='')
		{
			//appLocation();
			easyHealthData('{\"type\":\"hospital\",\"hospitals\":[{\"name\":\"深圳市妇幼保健院测试\",\"distance\":1432,\"addr\":\"深圳市福田路55454号\",\"tel\":\"1353343444\"},{\"name\":\"深圳市第三人民医院测试\",\"distance\":1012,\"addr\":\"深圳市福田路2号\",\"tel\":\"深圳市福田路2号\"},{\"name\":\"北京大学深圳医院测试\",\"distance\":1325,\"addr\":\"深圳市福田路2号\",\"tel\":\"12342424\"},{\"name\":\"滨江街社区卫生服务中心\",\"distance\":5200,\"addr\":\"深圳市福田路2号\",\"tel\":\"1434224\"},{\"name\":\"滨江街社区卫生服务中心(预防保健部)\",\"distance\":5227,\"addr\":\"深圳市福田路2号\",\"tel\":\"154234323\"},{\"name\":\"龙潭社区卫生服务站(龙潭中约大街)\",\"distance\":5228},{\"name\":\"北京街社区卫生服务中心服务网点\",\"distance\":5229},{\"name\":\"海珠区江海街台涌社区卫生服务站(石榴岗路)\",\"distance\":5296},{\"name\":\"天河区中心医院\",\"distance\":5305},{\"name\":\"天河区天园街社区卫生服务中心(棠石路)\",\"distance\":5332},{\"name\":\"昌岗街社区卫生服务中心\",\"distance\":5346},{\"name\":\"光塔街社区卫生服务中心\",\"distance\":5349},{\"name\":\"天元健康服务站\",\"distance\":5381}],\"location\":\"广东省广州市天河区广州大道中靠近珠控国际中心\",\"code\":0}');
		}
		else
		{
			easyHealthData(locationStr);
		}
		
	});
	
	function appLocation()
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
			        		setTimeout("appStartLocate('医院',parseInt('3000'))",1000);
		        		}
		        	}
		        }
		        catch (e) {}}
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
		        		window.yx129.appStartLocate('医院',parseInt('3000'));
		        	}
		        } catch (e) {}
		    }
	}
	
	function relocation()
	{
		$('#threeKmAdd .btn3kmRefresh').addClass('rotate');
		$('#hospUl').empty();
		$('#threemRange-discoveryList').empty();
		$Y.loading.show('重新定位中...');
		setTimeout("$Y.loading.hide();$('#threeKmAdd .btn3kmRefresh').removeClass('rotate');appLocation();",2000);
	} 
	
	function easyHealthData(dataObj)
	{
		//var myBox = new $Y.confirm({ title:"", content:$json.toJSONString(dataObj), ok:{title:"DEBUG", click:function(){ myBox.close(); } } });
	
		if(locationStr=='')
		{
			locationStr=JSON.stringify(dataObj);
		}
		if(version!=""&&parseInt(version.replace(new RegExp("[a-zA-Z_.]","gm"),""))>=newVersion)
		{
				$('#threemRange-menu').show();
				$.ajax({
	        	url:'${basePath}/easyhealth/hospitalCircle/locateHospital',
	        	type:'POST',
	        	data:{jsonStr:$json.toJSONString(dataObj)},
	        	success:function(resp)
	        	{
	        		if(resp.status=='OK')
	        		{
		        		$Y.tips('定位成功<br/>您当前位于'+resp.message.location,1500);
		    			$('#location').text(resp.message.location);
	        			//发现的医院
	        			if(resp.message.discoverHospitals.length>0)
	        			{
	        				$.each(resp.message.discoverHospitals, function(i, item) {
	        					 var hadRegfragment='';
	        					 var distancefragment='';
	        					 if(item.hadReg=='1')
	        					 {
		        					 hadRegfragment='<div class="mate color999">您曾经在该院就诊</div>';
	        					 }
	        					 else
	        					 {
	        					 	hadRegfragment='<div class="mate color999">'+(item.address!=null?item.address:"")+'</div>';
	        					 }
	        					 if(item.distance=='0')
	        					 {
	        					 	distancefragment='<div class="mate color999"><span class="skinColor">在您附近</span></div></div>';
	        					 }
	        					 else
	        					 {
	        					 	 distancefragment='<div class="mate color999">距离您 <span class="skinColor">'+item.distance+'</span> 米</div></div>';
	        					 }
	        					 var $hosp =$('<li onclick="toHospDetail(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\',\''+item.longitude+'\',\''+item.latitude+'\')"><div class="threemRange-discoveryList-info"><div class="title">'+item.hospName+'</div>'
	        					 + hadRegfragment
	        					 +distancefragment
	                			 +'<div class="hospital-appointment"><span class="guahao"  >去挂号</span></div></li>');
	        					 $('#threemRange-discoveryList').append($hosp);
					        });
	        			}
	        			else
	        			{
	        				$('#threemRange-discoveryList').hide();
	        				$('#discoverDiv').hide();
	        			}
	        			//附近的医院
	        			if(resp.message.nearbyHospitals.length>0)
	        			{
			        		$.each(resp.message.nearbyHospitals, function(i, item) {
			        		   if(item.hospId)
			        		   {
				        		   var $hosp = $('<li class="arrow" onclick="toHospDetail(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\',\''+item.longitude+'\',\''+item.latitude+'\')">'+
				        		   			   '<div class="title">'+item.hospName+'</div>'+
				        		   			   '<div class="mate color999">'+item.address+'</div>'+
				        		   			   '<div class="mate color999">距离您 <span class="skinColor">'+item.distance+'</span> 米</div>'+
				        		   			   '</li>');
			        		   }
			        		   else
			        		   {
			        		   		var $hosp = $('<li class="arrow" onclick="toHospDetail(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\',\''+item.longitude+'\',\''+item.latitude+'\')">'+
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
	        				$('#threemRange-discoveryList').hide();
	        				$('#discoverDiv').hide();
	        				var nodata = $('<li class="noDataText"><div class="title">附近没有医院</div></li>');
							$('#hospUl').empty();
							$('#hospUl').append(nodata);
	        			}
	        		}
	        		else
	        		{
	        			$('#threemRange-discoveryList').hide();
	        			$('#discoverDiv').hide();
	        			var $hosp = $('<li class="noDataText"><div class="title">三公里范围内未发现任何医院</div></li>');
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
	        		var errmsg = $('<li class="noDataText"><div class="title">三公里范围内未发现任何医院</div></li>');
					$('#hospUl').empty();
					$('#hospUl').append(errmsg);
	        	}
	        });
		}
		else
		{
			$.ajax({
        	url:'${basePath}/easyhealth/hospitalCircle/filterHospital',
        	type:'POST',
        	data:{jsonStr:$json.toJSONString(dataObj)},
        	success:function(resp)
        	{
        		$('#threemRange-discoveryList').hide();
        		$('#threemRange-menu').hide();
	        	$('#discoverDiv').hide();
    			$('#location').text(resp.message.location);
        		if(resp.status=='OK')
        		{
        			if(resp.message.hospitalList.length>0)
        			{
		        		$.each(resp.message.hospitalList, function(i, item) {
		        		   if(item.hospId)
		        		   {
			        		   var $hosp = $('<li class="arrow" onclick="toHospDetailLowVersion(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\')">'+
			        		   			   '<div class="title">'+item.hospName+'</div>'+
			        		   			   '<div class="mate color999">'+item.address+'</div>'+
			        		   			   '<div class="mate color999">距离您 <span class="skinColor">'+item.distance+'</span> 米</div>'+
			        		   			   '</li>');
		        		   }
		        		   else
		        		   {
		        		   		var $hosp = $('<li class="arrow" onclick="toHospDetailLowVersion(\''+item.hospId+'\',\''+item.tel+'\',\''+item.address+'\',\''+item.hospName+'\',\''+item.appId+'\',\''+item.appCode+'\',\''+item.hospCode+'\')">'+
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
		
	}
	
	function toHospDetailLowVersion(hospId,telphone,address,hospName,appId,appCode,hospCode)
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
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}easyhealth/hospitalCircle/toHospInfoLowVersion");
		freshForm.submit();
	}
	
	
	function toHospDetail(hospId,telphone,address,hospName,appId,appCode,hospCode,longitude,latitude)
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
		freshForm.append($('<input type="hidden" name="longitude" value="'+longitude+'"/>'));
		freshForm.append($('<input type="hidden" name="latitude" value="'+latitude+'"/>'));
		freshForm.append($('<input type="hidden" name="version" value="'+version+'"/>'));
		freshForm.appendTo("body");
		freshForm.css('display','none');
		freshForm.attr("method","post");
		freshForm.attr("action","${basePath}/easyhealth/hospitalCircle/toHospInfo");
		freshForm.submit();
	}
	
	function doGoBack()
	{
		windowClose();
	}
	
	
</script>
</html>
