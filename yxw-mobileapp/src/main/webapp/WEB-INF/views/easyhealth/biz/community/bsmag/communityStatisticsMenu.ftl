<div id="header">
    <h1>
        <a href="javascript:void(0);">深圳市医管中心管理后台</a>
    </h1>
</div>
<div id="sidebar">
    <ul>
        <li id="communityHealth"><a href="javascript:linkClick('${basePath}sysSchmag/communitycenter/organizationSchmag/queryCcommunityHealth','','communityHealth','');"><i class="icons-siderbar icons-access"></i><i class="caret"></i><span>社康中心</span></a></li>
    </ul>
</div>
<script type="text/javascript">
	$(document).ready(function(){
	  	$('#sidebar ul li.active').removeClass('active');
	  	if('${type}' != ''){
	  		$('#${type}').addClass('active');
	  	}else{
	  		$('#medicalCard').addClass('active');
	  	}
	});
	function linkClick(url,hospitalId,orderType){
		$("#orderType").val(orderType);
		$("#notQuery").val("true");
		$("#hospitalId").val(hospitalId);
		$("#linkClickForm").attr("action",url);
        $("#linkClickForm").submit();
	}
</script>
<form id="linkClickForm" method="post" accept-charset="utf-8">
	<input type="hidden" id="notQuery" name="notQuery" />
	<input type="hidden" id="orderType" name="orderType" />
	<input type="hidden" id="type" name="type" />
</form>