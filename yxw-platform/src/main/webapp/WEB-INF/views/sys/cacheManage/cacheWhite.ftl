<html>
<head>
	<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>缓存管理</title>
    <style>
    	.textarea-white{width:100%;height:200px;}
    </style>
</head>
<body>
	<div id="content">
	<!--医院及系统设置-->
	 <div id="content-top">
        <div class="container-fluid">
            <div class="box">
                <!--settings str-->
                <div id="settings">
                    <div class="set-msg dropdown">
                        <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
                            <span class="text">当前账户：${loginedUser.userName}</span>
                            <i class="icons_settings icons-set"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="${request.contextPath}/user?method=toModifyPwd"><span class="text">修改密码</span><i class="icons_settings icons-password"></i></a></li>
                            <li><a onclick="$user.logout();"><span class="text">退出登录</span><i class="icons_settings icons-loginout"></i></a></li>
                        </ul>
                    </div>
                </div>
                <!--settings end-->
            </div>
        </div>
    </div>
	
	
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">缓存管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content form-check">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <tbody>
                                <tr>
                                    <td width="10%">微信白名单缓存</td>
                                    <td width="80%">
                                		<#if whiteWechatCache?exists>
							                <#list whiteWechatCache?keys as key>
							                	<input type="hidden" name="key" value="${key}" />
							                   <textarea class="textarea-white" >${whiteWechatCache[key]}</textarea>
							                </#list>
							            </#if>
                                    </td>
                                </tr>
                                <tr>
                                    <td width="10%">支付宝白名单缓存</td>
                                    <td>
                                    	<#if whiteAlipayCache?exists>
							                <#list whiteAlipayCache?keys as key>
							                	<input type="hidden" name="key" value="${key}" />
							                   <textarea class="textarea-white" >${whiteAlipayCache[key]}</textarea>
							                </#list>
							            </#if>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>

