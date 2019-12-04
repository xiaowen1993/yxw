<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/meterial/sys.meterial.js"></script>
<script type="text/javascript" src="${basePath}js/sys/user/sys.user.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">素材管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-right" id="search">
                    <form method="post" action="">
            			<input type="hidden" name="pageNum" value="${hospitals.pageNum}" />
            			<input type="hidden" name="pageSize" value="${hospitals.pageSize}" />
            			<input type="hidden" name="pages" value="${hospitals.pages}" />
            			<input type="hidden" name="total" value="${hospitals.total}" />
	                    <input type="text" name="search" value="${search}" placeholder="请输入医院名称"/>
	                    <button class="tip-bottom" onclick="$meterial.search();">
	                        <i class="icon-search icon-white"></i>
	                    </button>
                	</form>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th>医院全称</th>
                                <th width="200" >上次操作时间</th>
                                <th>最后操作者</th>
                                <th width="100">管理</th>
                            </tr>
                            </thead>
                            <#if (hospitals.list?size>0)>
	                            <tbody>
	                            	<#list hospitals.list as hospital>
		                                <tr>
		                                    <td>${hospital.hospitalname}</td>
		                                    <td><#if (hospital.meterialList?size>0)>${hospital.meterialList[0].et}</#if></td>
		                                    <td><#if (hospital.meterialList?size>0)>${hospital.meterialList[0].ep}</#if></td>
		                                    <td>
		                                    	<@p.hasPermission value="meterialEdit">
		                                        	<a href="${basePath}message/meterial?method=listOfPic&hospitalId=${hospital.hospitalid}">编辑</a>
												</@p.hasPermission>
		                                    </td>
		                                </tr>
	                                </#list>
	                            </tbody>
                            </#if>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="pagination pagination-centered">
            <ul>
                <li><a href="javascript:;" onclick="$meterial.changePage(${hospitals.prePage});">上一页</a></li>
                <#if hospitals.pages != 0>
	                <#list 1..hospitals.pages as p>
	                	<#if hospitals.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$meterial.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$meterial.changePage(${hospitals.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$meterial.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
