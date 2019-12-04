
<!DOCTYPE html>
<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
    <#include "/easyhealth/common/common.ftl">
	<script type="text/javascript">
	    var appPath = '${basePath}';
	</script>
    <title>社康中心</title>
</head>
<body>

<div id="body">
    <div id="myCenter">

        <div class="search-view" onclick="goCommunitySoushuo();"><i class="iconfont">&#xe61a;</i>请输入地区名称<span class="btn-seaech">搜索</span></div>
        <div class="space15"></div>
        <ul class="yx-list listRow">
           <li class="arrow"  onclick="goToCommunityHealth('福田区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>福田区</li>
           <li class="arrow"  onclick="goToCommunityHealth('罗湖区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>罗湖区</li>
           <li class="arrow"  onclick="goToCommunityHealth('南山区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>南山区</li>
           <li class="arrow"  onclick="goToCommunityHealth('盐田区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>盐田区</li>
           <li class="arrow"  onclick="goToCommunityHealth('宝安区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>宝安区</li>
           <li class="arrow"  onclick="goToCommunityHealth('龙岗区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>龙岗区</li>
           <li class="arrow"  onclick="goToCommunityHealth('光明新区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>光明新区</li>
           <li class="arrow"  onclick="goToCommunityHealth('坪山新区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>坪山新区</li>
           <li class="arrow"  onclick="goToCommunityHealth('龙华新区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>龙华新区</li>
           <li class="arrow"  onclick="goToCommunityHealth('大鹏新区');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>大鹏新区</li>
       	   <!--  
       	   <#if administrativeRegionList?exists>
            	<#list administrativeRegionList as administrativeRegion>
                <li class="arrow"  onclick="goToCommunityHealth('${administrativeRegion.administrativeRegion }');"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div>${administrativeRegion.administrativeRegion } </li>
                </#list>
			<#else>
				<li>暂无数据</li>
			</#if> 
		   -->
        </ul>
    </div>
</div>

<div id="copyright">  </div>
<form id="administrativeRegionFrom" method="post" action="${basePath}easyhealth/communitycenter/communityHealth/getCommunityHealthByAR" accept-charset="utf-8">
 <input type="hidden" id="administrativeRegion" name="administrativeRegion"/>
 <input type="hidden" id="organizationName" name="organizationName"/>
</form>

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
	function goToCommunityHealth(administrativeRegion) {
		$("#administrativeRegion").val(administrativeRegion);
		$("#organizationName").val($("#organizationNameQuery").val());
		$('#administrativeRegionFrom').submit();
	   
	};
	
	function goCommunitySoushuo(){
	   window.location.href="${basePath}easyhealth/communitycenter/communityHealth/getCommunitySoushuo";
	}
	
	function doGoBack()
	{
		windowClose();
	}
</script>