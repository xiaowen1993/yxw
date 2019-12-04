
<!DOCTYPE html>
<html>
<head>
	<meta HTTP-EQUIV="pragma" CONTENT="no-cache"> 
	<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"> 
	<meta HTTP-EQUIV="expires" CONTENT="0"> 
	<#include "/easyhealth/common/common.ftl">
    <title>社康中心</title>
    <style>
    	.jky li{
		    display: -webkit-box;
		    display: -ms-flexbox;
		    display: -webkit-flex;
		    display: flex;
    	}


    </style>
</head>
<body>

<div id="body">
	<div class="box-list fff ">
        <ul class="yx-list jky">
            <li class="flex-wrap row-flex">
                <div class="title flexWidth5">地区</div>
                <div class="title flexWidth5 textRight color999">${administrativeRegion}</div>
            </li>
        </ul>

    </div>

    <ul class="yx-list">
    	<#if communityHealthCenterList?exists>
            	<#list communityHealthCenterList as communityHealthCenter>
                	 <li class="arrow" onclick="goToCommunityHealth('${communityHealthCenter.id}')">
			            <div class="title">${communityHealthCenter.organizationNameSub}</div>
			            <div class="mate color999">${communityHealthCenter.organizationAddress}</div>
			            <div class="mate color999"></div>
			        </li>
                </#list>
		<#else>
			<li>暂无数据</li>
		</#if>
       
 
    </ul>
 

</div>

<div id="copyright">  </div>
<form id="administrativeRegionFrom" method="post" action="${basePath}easyhealth/communitycenter/communityHealth/getCommunityHealthOnlyOneById" accept-charset="utf-8">
 <input type="hidden" id="communityId" name="communityId"/>
</form>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
	function goToCommunityHealth(id) {
		$("#communityId").val(id);
		$('#administrativeRegionFrom').submit();
	   
	};
	
	function doRefresh()
	{
		window.location.href="${basePath}/easyhealth/communitycenter/communityHealth/getCommunityHealthByAR?administrativeRegion=${administrativeRegion }";
	}
	
	function doGoBack()
	{
		window.location.href="${basePath}easyhealth/communitycenter/communityHealth/getAdministrativeRegion?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}";
	}
</script>