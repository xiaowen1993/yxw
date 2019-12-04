<#assign p=JspTaglibs["/WEB-INF/tlds/privilege.tld"]>
<div id="header">
    <h1>
        <a href="javascript:void(0);">掌上医院统计管理后台 </a>
    </h1>
</div>
<div id="sidebar">
    <ul>
    	<@p.hasPermission value="statistics-medicalCard">
       	 <li id="medicalCard" class="active"><a href="javascript:linkClick('${basePath}statistics/medicalCardList','${hospital.id}','');"><i class="icons-siderbar icons-account"></i><i class="caret"></i><span>绑卡统计</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-order">
        <li id="all"><a href="javascript:linkClick('${basePath}statistics/orderList','${hospital.id}','all');"><i class="icons-siderbar icons-behavior"></i><i class="caret"></i><span>订单统计</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-reg">
        <li id="reg"><a href="javascript:linkClick('${basePath}statistics/regOrderList','${hospital.id}','reg');"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>挂号订单统计</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-clinic">
        <li id="clinic"><a href="javascript:linkClick('${basePath}statistics/clinicOrderList','${hospital.id}','clinic');"><i class="icons-siderbar icons-access"></i><i class="caret"></i><span>门诊订单统计</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-depose">
        <li id="deposit"><a href="javascript:linkClick('${basePath}statistics/depositOrderList','${hospital.id}','deposit','');"><i class="icons-siderbar icons-access"></i><i class="caret"></i><span>住院押金订单统计</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-dept">
        <li id="dept"><a href="javascript:linkClick('${basePath}statistics/deptList','${hospital.id}','dept','');"><i class="icons-siderbar icons-access"></i><i class="caret"></i><span>科室订单统计</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-orderManagement">
        <li id="order"><a href="javascript:linkClick('${basePath}order/orderList','${hospital.id}','order','');"><i class="icons-siderbar icons-access"></i><i class="caret"></i><span>订单管理</span></a></li>
        </@p.hasPermission>
        
        <@p.hasPermission value="statistics-medicalPeople">
        <li id="medicalCardSupervise"><a href="javascript:linkClick('${basePath}statistics/medicalCardSupervise','${hospital.id}','medicalCardSupervise','');"><i class="icons-siderbar icons-access"></i><i class="caret"></i><span>就诊人管理</span></a></li>
        </@p.hasPermission>
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
		$("#type").val(orderType);
		$("#hospitalId").val(hospitalId);
		$("#linkClickForm").attr("action",url);
        $("#linkClickForm").submit();
	}
</script>
<form id="linkClickForm" method="post" accept-charset="utf-8">
	<input type="hidden" id="hospitalId" name="hospitalId" />
	<input type="hidden" id="orderType" name="orderType" />
	<input type="hidden" id="type" name="type" />
</form>