<div id="content">
	<!--医院及系统设置-->
	 <div id="content-top">
        <div class="container-fluid">
            <div class="box">
                <!--top-Header-menu str -->
                <!-- <div id="user-nav" class="navbar">
                    <div id="select-msg" class="dropdown" >
                    	<#if hospitalList?exists>
                    		<#list hospitalList as hospitals>
                    			<#if hospitals.id == hospitalId>
	                    			<a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
	                            		<span id="hospitals_name" class="text">${hospitals.name}</span>
	                            		<i class="caret"></i>
	                        		</a>
                        		</#if>
                    		</#list>
                        </#if>
                        <ul class="dropdown-menu">
                        	<#if hospitalList?exists>
                        		<#list hospitalList as hospitals>
                        			<li><a href="#" onclick="swith('${hospitals.id}');">${hospitals.name}</a></li>
                        		</#list>
                            </#if>
                        </ul>
                    </div>
                </div>-->
                <!--top-Header-menu end -->
                <!--settings str-->
                <div id="settings">
                    <div class="set-msg dropdown">
                        <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
                            <span class="text">当前账户：${loginedUser.userName}</span>
                            <i class="icons_settings icons-set"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a onclick="toModifyPwd()"><span class="text">修改密码</span><i class="icons_settings icons-password"></i></a></li>
                            <li><a onclick="logout();"><span class="text">退出登录</span><i class="icons_settings icons-loginout"></i></a></li>
                        </ul>
                    </div>
                </div>
                <!--settings end-->
            </div>
        </div>
    </div>
    <script>
    	function swith(hospitalId){
    		var url = window.location.href;
    		console.log(url);
    		url = url.substring(0,url.indexOf("=")) + "=" + hospitalId;
    		console.log(url);
    		window.location.href = url;
    	}
    </script>