<html>
<head>
	<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>统计管理</title>
</head>
<body>
	<div id="content">
	<!--医院及系统设置
	 <div id="content-top">
        <div class="container-fluid">
            <div class="box">
               
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
                
            </div>
        </div>
    </div>-->
	
	
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">统计管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-right" id="search">
                	<form method="post" action="" accept-charset="utf-8">
                		<#if hospitals?exists>
	                		<input type="hidden" name="pageNum" value="${hospitals.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${hospitals.pageSize}" />
	            			<input type="hidden" name="pages" value="${hospitals.pages}" />
	            			<input type="hidden" name="total" value="${hospitals.total}" />
            			</#if>
	                    <input type="text" name="search"  value="${search}" placeholder="请输入医院名称、医院编码、联系人"/>
                	</from>
                    <button class="tip-bottom" type="submit">
                        <i class="icon-search icon-white"></i>
                    </button>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content form-check">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            	<tr>
                                	<th width="50"></th>
	                                <th>医院全称</th>
	                                <th width="100">联系人</th>
	                                <th width="120" >联系人电话</th>
	                                <th>上次操作时间</th>
	                                <th>管理</th>
	                            </tr>
                            </thead>
                            <tbody>
                            	<#if hospitals?exists>
	                                <#list hospitals.list as hospital>
		                                <tr>
		                                    <td>${hospital_index + 1} </td>
		                                    <td>${hospital.name}</td>
		                                    <td>${hospital.contactName}</td>
		                                    <td >${hospital.contactTel}</td>
		                                    <td >${hospital.ct?string("yyyy-MM-dd HH:mm:ss")}</td>
		                                    <td>
		                                         <a href="${basePath}statistics/medicalCardList?hospitalId=${hospital.id}">查看</a>
		                                    </td>
		                                </tr>
									</#list>
								<#else>
									<td colspan="10">暂无医院数据</td>
								</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="pagination pagination-centered">
            <#if hospitals?exists>
            <ul>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.prePage});">上一页</a></li>
                <#if hospitals.pages != 0>
	                <#list 1..hospitals.pages as p>
	                	<#if pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:;" onclick="$hospital.changePage(${p});">${p}</a></li>
	                  	</#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$hospital.changePage($('#skipPage').val());">跳转</a>
            </div>
            </#if>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>

