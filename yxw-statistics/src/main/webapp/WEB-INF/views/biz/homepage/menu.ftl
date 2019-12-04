<html>
<head>
	<#include "./common/common.ftl">
    <title>掌上医院全流程管理后台</title>
</head>
<body>
<div id="sidebar">
	<ul id="ul-sideNav">
		<li class="active">
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>数据统计</span></div>
            <ul class="subNav">
		    	<li class="subNavItem subNavItem-active"><a href="${basePath}sys/hospital/list?bizType=1" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>整体统计</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?bizType=2" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>绑卡统计</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?bizType=3" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>订单统计</span></a></li>
            </ul>
        </li>
        <li class="active">
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>数据管理</span></div>
            <ul class="subNav">
		       	<li class="subNavItem"><a href="${basePath}sys/hospital/list?bizType=11" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>订单管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?bizType=12" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>绑卡管理</span></a></li>
				<li class="subNavItem"><a href="${basePath}sys/hospital/list?bizType=13" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>挂号管理</span></a></li>
            </ul>
        </li>
        <li class="active">
            <div class="parentNavItem"><i class="icons-siderbar"></i><span>医院管理</span></div>
            <ul class="subNav">
		       	<li class="subNavItem"><a href="${basePath}hospital/index?bizType=11" target="rightBottomFrame"><i class="icons-siderbar icons-role"></i><i class="caret"></i><span>医院配置</span></a></li>
            </ul>
        </li>
	</ul>
</div>
</body>

</html>