<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>周边生活圈</title>
</head>
<body>
<div id="body">
    <div class="lookinglist">
        <div id="success">
            <div class="lookingDoll"></div>
            <div class="p color666" >正在为您查找周边信息</div>
        </div>
    </div>
    
    <!-- 
    <div class="seatList">
        <div class="space15"></div>
        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>很遗憾，您不在医院附近(3000米内)，请选择医院，查看医院周边信息</div>
        <div class="space15"></div>
        <ul class="hospital-list">
            <li class="arrow">
                <div class="title fontSize120">深圳市第二人民医院</div>
            </li>
            <li class="arrow">
                <div class="title fontSize120">深圳市第二人民医院</div>
            </li>
            <li class="arrow">
                <div class="title fontSize120">深圳市第二人民医院</div>
            </li>
        </ul>
    </div>
    <div class="locationList">
        <div class="space15"></div>
        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>很遗憾，定位失败，无法自动匹配周边信息。请选择医院，查看医院周边信息</div>
        <div class="space15"></div>
        <ul class="hospital-list">
            <li class="arrow">
                <div class="title fontSize120">深圳市第二人民医院</div>
            </li>
            <li class="arrow">
                <div class="title fontSize120">深圳市第二人民医院</div>
            </li>
            <li class="arrow">
                <div class="title fontSize120">深圳市第二人民医院</div>
            </li>
        </ul>
    </div>
    -->
</div>
<div id="copyright"> 医享网出品 </div>
</body>
<script src="${basePath}/js/json_utils.js"></script>
<script type="text/javascript">
	$(function()
	{
		if(IS.isMacOS){
	        try{
	        	setTimeout("appStartLocate('医院',parseInt('3000'))",1000);
	        }
	        catch (e) {}
	    }
	    else if(IS.isAndroid)
	    {
	        try{
	        	window.yx129.appStartLocate('医院',parseInt('3000'));
	        } catch (e) {}
	    }
		//easyHealthData('{\"longitude\":\"113.322343\",\"latitude\":\"23.134519\",\"location\":\"ggghggggggggggggggggggggg\",\"hospitals\":[{\"name\":\"广州仁爱天河医院\",\"longitude\":\"113.32\",\"latitude\":\"23.13\",\"addr\":\"天河路108号\",\"distance\":\"486\",\"tel\":\"020-22229999\"},{\"name\":\"广东药学院新天生殖医院-放射科\",\"longitude\":\"113.32\",\"latitude\":\"23.13\",\"addr\":\"天河路110号\",\"distance\":\"486\",\"tel\":\"\"}]}');
	});
	
	
	function toLifeCircle(latitude,longitude,hospName)
	{
		window.location.href='http://market.m.taobao.com/market/diandian/yywm.php?longitude='+longitude+'&latitude='+latitude
        				+'&addrname='+hospName;
	}
	
	function easyHealthData(dataObj)
	{
		$.ajax({
        	url:'${basePath}/easyhealth/life/lifeLocate',
        	type:'POST',
        	data:{jsonStr:$json.toJSONString(dataObj)},
        	success:function(resp)
        	{
        		if(resp.status=='OK')
        		{
        			if(resp.message.sign=='FOUND')
        			{
        				window.location.href='http://market.m.taobao.com/market/diandian/yywm.php?longitude='+resp.message.longitude+'&latitude='+resp.message.latitude
        				+'&addrname='+resp.message.hospName;
        			}
        			else 
        			{
        				 var hospsDiv='';
        				 var hospList=resp.message.hospList;
        				 for(var i=0;i<hospList.length;i++)
        				 {
        				 	var hosp=hospList[i];
        				 	hospsDiv='<li class="arrow"><div class="title fontSize120" onclick="toLifeCircle(\''+hosp.latitude+'\',\''+hosp.longitude+'\',\''+hosp.hospName+'\')">'+hosp.hospName+'</div></li>'+hospsDiv;
        				 }
        				 var seatListDiv='<div class="seatList">'
        				 					+'<div class="space15"></div>'
        				 					+'<div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>很遗憾，您不在医院附近(3000米内)，请选择医院，查看医院周边信息</div>'
        				 					+'<div class="space15"></div>'
        				 					+'<ul class="hospital-list">'
        				 						+hospsDiv
        				 					+'</ul>'
        				 				 +'</div>';
        				$('#body').append(seatListDiv);
        			}
        		}
        		else
        		{
        			var locationListDiv='<div class="locationList">'
        				 					+'<div class="space15"></div>'
        				 					+'<div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>很遗憾，定位失败，无法自动匹配周边信息。请稍后再试</div>'
        				 					+'<div class="space15"></div>'
        				 				 +'</div>';
        			$('#body').append(locationListDiv);
        		}
        		$('.lookinglist').hide();
        	},
        	error:function()
        	{	
        	}
        });
	}

</script>
</html>

