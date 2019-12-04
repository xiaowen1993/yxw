<html>
<head>
	<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>医院列表</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">消息模板</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                	<@p.hasPermission value="xxmbMsgTemplateLibrary">
                    	<button type="button" class="btn btn-ok" onclick="window.location.href='${basePath}msgpush/msgTemplateLibrary/toListView'">模板库</button>
                	</@p.hasPermission>
                </div>
                <div class="pull-right" id="search">
            		<form method="post" action="" accept-charset="utf-8">
            			<input type="hidden" name="pageNum" value="${hospitals.pageNum}" />
            			<input type="hidden" name="pageSize" value="${hospitals.pageSize}" />
            			<input type="hidden" name="pages" value="${hospitals.pages}" />
            			<input type="hidden" name="total" value="${hospitals.total}" />
                        <input name="search" type="text"  value="${search}" placeholder="请输入医院名称"/>
	                    <button class="tip-bottom" type="submit">
	                        <i class="icon-search icon-white"></i>
	                    </button>
                    </form>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content form-check">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th>医院全称</th>
                                <th width="100">上次修改时间</th>
                                <th width="120">最后操作者</th>
                                <th>管理</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list hospitals.list as hospital>
	                                <tr>
	                                    <td style="display:none">${hospital.hospitalid}</td>
	                                    <td>${hospital.hospitalname}</td>
	                                    <td><#if (hospital.msgPushList?size>0)>${hospital.msgPushList[0].et}</#if></td>
		                                <td><#if (hospital.msgPushList?size>0)>${hospital.msgPushList[0].ep}</#if></td>
	                                    <td>
											<@p.hasPermission value="xxmbEdit">
	                                        	<a href="javascript:void(0);" onclick="window.location.href='${basePath}msgpush/msgTemplate/listViewByHospital?hospitalId=${hospital.hospitalid}'">编辑</a>
	                                    	</@p.hasPermission>
	                                    </td>
	                                </tr>
							     </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
<div class="pagination pagination-centered">
            <ul>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.prePage});">上一页</a></li>
                
                <#if hospitals.pages != 0>
	                <#list 1..hospitals.pages as p>
	                	<#if hospitals.pageNum == p>
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
        </div>

    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>