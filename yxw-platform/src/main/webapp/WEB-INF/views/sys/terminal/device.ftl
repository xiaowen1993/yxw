<html>
<head>
	<#include "/sys/common/common.ftl">
    
    <title>设备管理</title>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">设备管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid"> 
                <div class="pull-left">
                	<button type="button" class="btn btn-ok" onclick="$terminal.device.toAdd('${hospitalId}');">新增设备</button>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="" accept-charset="utf-8">
                		<#if devices?exists>
	                		<input type="hidden" name="pageNum" value="${devices.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${devices.pageSize}" />
	            			<input type="hidden" name="pages" value="${devices.pages}" />
	            			<input type="hidden" name="total" value="${devices.total}" />
            			</#if>
	                    <input type="text" name="search"  value="${search}" placeholder="请输入设备编码查询"/>
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
                                	<th width="50">序号</th>
	                                <th width="100" nowrap>设备ID</th>
	                                <th width="100" nowrap>设备编码</th>
	                                <th>摆放位置</th>
	                                <th>软件版本号</th>
	                                <th width="50">状态</th>
	                                <th>管理</th>
	                            </tr>
                            </thead>
                            <tbody>
                            	<#if devices?exists>
	                                <#list devices.list as device>
		                                <tr>
		                                    <td>
		                                    	${device_index + 1}
		                                    </td>
		                                    <td style="display: none;">${device.id}</td>
		                                    <td >${device.deviceId}</td>
		                                    <td >${device.code}</td>
		                                    <td >${device.position}</td>
											<td>${device.appVersion}</td>
		                                    <#--是否发布 0：不发布；1：发布-->
		                                    <td>
		                                    	<#if device.state == 1>启用<#else><span style="color: red;">停用</span></#if>
		                                    </td>
		                                    
		                                    <td nowrap>
	                                    		<a href="javascript:void(0);" onclick="location.href='toEdit?id=${device.id}'">编辑</a>
		                                    </td>
		                                </tr>
									</#list>
								<#else>
									<#-- <td colspan="7">暂无设备数据</td> -->
								</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="pagination pagination-centered">
            <#if devices?exists>
            <ul>
                <li><a href="javascript:;" onclick="$terminal.changePage(${devices.prePage});">上一页</a></li>
                <#if devices.pages != 0>
	                <#list 1..devices.pages as p>
	                	<#if devices.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:;" onclick="$terminal.changePage(${p});">${p}</a></li>
	                  	</#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$terminal.changePage(${devices.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$terminal.changePage($('#skipPage').val());">跳转</a>
            </div>
            </#if>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

<script src="${basePath}js/sys/terminal/sys.terminal.js"></script>
</body>
</html>

