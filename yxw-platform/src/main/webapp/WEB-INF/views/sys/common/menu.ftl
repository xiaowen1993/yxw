<html>
<head>
<#include "./sys/common/common.ftl">
</head>
<body>
<!--sidebar-menu str-->
<div id="sidebar">
    <ul id="ul-sideNav">
    		<li>
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>系统管理</span></div>
            <ul class="subNav">
            	 <@p.hasPermission value="ghgl">
		       		<li class="subNavItem"><a href="${basePath}sys/user/list" target="rightBottomFrame"><i class="icons-siderbar icons-account"></i><i class="caret"></i><span>账户管理</span></a></li>
		        </@p.hasPermission>
				<@p.hasPermission value="jsgl">
					<li class="subNavItem"><a href="${basePath}sys/role/list" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>角色管理</span></a></li>
		        </@p.hasPermission>
				<@p.hasPermission value="zygl">
					<li class="subNavItem"><a href="${basePath}sys/resource/list" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>权限管理<#--资源管理--></span></a></li>
		        </@p.hasPermission>
            </ul>
        </li>
    		<li class="active">
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>医院管理</span></div>
            <ul class="subNav">
            	<@p.hasPermission value="yygl">
		        	<li class="subNavItem subNavItem-active"><a href="${basePath}sys/hospital/list" target="rightBottomFrame"><i class="icons-siderbar icons-hopital"></i><i class="caret"></i><span>医院信息<#--医院管理--></span></a> </li>
		        </@p.hasPermission>
            	 <@p.hasPermission value="gzbz">
					<li class="subNavItem"><a href="${basePath}sys/ruleIndex/hospitalList" target="rightBottomFrame"><i class="icons-siderbar icons-rules"></i><i class="caret"></i><span>规则配置</span></a></li>
		        </@p.hasPermission>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?menuCode=payManager" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>支付管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?menuCode=optionalManager" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>功能管理</span></a></li>
				<@p.hasPermission value="xxmb">
			    	<li class="subNavItem"><a href="${basePath}msgpush/hospital/hospitalList" target="rightBottomFrame"><i class="icons-siderbar icons-temp"></i><i class="caret"></i><span>业务消息<#--消息模板--></span></a></li>
			   	</@p.hasPermission>
				<@p.hasPermission value="customservice">
					<li class="subNavItem"><a href="${basePath}sys/customService/list" target="rightBottomFrame"><i class="icons-siderbar icons-nes"></i><i class="caret"></i><span>就医反馈<#--客服中心--></span></a></li>
				</@p.hasPermission>
				<@p.hasPermission value="cacheManage">
		       		<li class="subNavItem"><a href="${basePath}sys/cacheManage/cacheManageHospitalList" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>缓存管理</span></a></li>
		       	</@p.hasPermission>
			</ul>
        </li>
    		<li>
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>客户端管理</span></div>
            <ul class="subNav">
				<#-- <li class="subNavItem"><a href="${basePath}sys/app/version/list" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>版本管理</span></a></li> -->
				<li class="subNavItem"><a href="${basePath}sys/app/optional/list" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>功能配置</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/app/push/list" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>推送管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/app/location/view" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>定位管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/app/carrieroperator/queryCarrieroperatorList" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>运营管理</span></a></li>
				<@p.hasPermission value="customservice">
					<li class="subNavItem"><a href="${basePath}sys/customService/list" target="rightBottomFrame"><i class="icons-siderbar icons-nes"></i><i class="caret"></i><span>客服消息<#--客服中心--></span></a></li>
				</@p.hasPermission>
				<@p.hasPermission value="bbps">
				<li class="subNavItem"><a href="${basePath}sys/app/color/list" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>版本配色</span></a></li>
				</@p.hasPermission>
			</ul>
        </li>
        
        <#--
    		<li>
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>资讯中心</span></div>
            <ul class="subNav">
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>栏目管理</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>内容管理</span></a></li>
			</ul>
        </li>
    		<li>
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>数据中心</span></div>
            <ul class="subNav">
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>用户列表</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>绑卡列表</span></a></li>
			</ul>
        </li>
    		<li>
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>统计中心</span></div>
            <ul class="subNav">
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>整体统计</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>绑卡统计</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>订单统计</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>挂号管理</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>订单管理</span></a></li>
				<li class="subNavItem"><a href="#" target="rightBottomFrame"><i class="icons-siderbar"></i><i class="caret"></i><span>用户管理</span></a></li>
			</ul>
        </li>
        
        -->
        
        <li>
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>终端管理</span></div>
            <ul class="subNav">
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?menuCode=terminalDevice" target="rightBottomFrame"><i class="icons-siderbar icons-rules"></i><i class="caret"></i><span>设备管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?menuCode=terminalSetup" target="rightBottomFrame"><i class="icons-siderbar icons-rules"></i><i class="caret"></i><span>安装包管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?menuCode=terminalAd" target="rightBottomFrame"><i class="icons-siderbar icons-rules"></i><i class="caret"></i><span>广告管理</span></a></li>
			</ul>
        </li>
	</ul>
	
	<#--
	   	<@p.hasPermission value="xxgl">
			<li class="subNavItem"><a href="${basePath}message/reply?method=list" target="rightBottomFrame"><i class="icons-siderbar icons-nes"></i><i class="caret"></i><span>消息管理</span></a></li>
        </@p.hasPermission>
	   	
		<@p.hasPermission value="gzjggl">
        	<li><a href="${basePath}sys/organization/list" target="rightBottomFrame"><i class="icons-siderbar icons-account"></i><i class="caret"></i><span>组织/机构管理</span></a></li>
	    </@p.hasPermission>
		<@p.hasPermission value="ddgl">
	   		<li><a href="${basePath}order/orderHospitalList" target="rightBottomFrame"><i class="icons-siderbar icons-temp"></i><i class="caret"></i><span>订单管理</span></a></li>
		</@p.hasPermission>
		<@p.hasPermission value="meterial">
       		<li><a href="${basePath}message/meterial?method=list" target="rightBottomFrame"><i class="icons-siderbar icons-nes"></i><i class="caret"></i><span>素材管理</span></a></li>
		</@p.hasPermission>
		<@p.hasPermission value="statistics">
       		<li><a href="${basePath}statistics/index" target="view_window" target="rightBottomFrame"><i class="icons-siderbar icons-nes"></i><i class="caret"></i><span>后台管理</span></a></li>
		</@p.hasPermission>
		
	-->
</div>
<!--sidebar-menu end-->

<script type="text/javascript">
	$(function(){
		$('.subNav li').click(function() {
			$('#sidebar ul li.subNavItem-active').removeClass('subNavItem-active');
			$(this).addClass('subNavItem-active');
		});
		
		$('#ul-sideNav .parentNavItem').click(function(){
	        var o = $(this),li = o.closest('li');
	        if(li.hasClass('active')){
	            li.removeClass('active');
	        }else{
	            li.addClass('active')
	        }
    	})
	});
</script>
</body>
</html>