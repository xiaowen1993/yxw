<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/organization/sys.organization.js"></script>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">组织管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button type="button" class="btn btn-ok" onclick="$organization.toAdd()">新增</button>
                    <button type="button" class="btn btn-ok" onclick="$organization.batchDel()">删除</button>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="" id="form" >
                		<input type="hidden" name="pageNum" value="${organizationList.pageNum}" />
            			<input type="hidden" name="pageSize" value="${organizationList.pageSize}" />
            			<input type="hidden" name="pages" value="${organizationList.pages}" />
            			<input type="hidden" name="total" value="${organizationList.total}" />
                	
	                    <input type="text" name="search" value="${search}" placeholder="请输入组织名称"/>
	                    <button class="tip-bottom" onclick="$organization.search();">
	                        <i class="icon-search icon-white"></i>
	                    </button>
                	</form>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content form-check" >
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                               <th width="50">
                                    <label class="checkboxTwoAll inline">
                                        <input type="checkbox" name="all">
                                    </label>
                                </th>
                                <th>组织名称</th>
                                <th>简介</th>
                                <th>编码</th>
                                <th>父组织名</th>
                                <th>备注</th>
                                <th>创建时间</th>
                                <th>管理</th>
                            </tr> 
                            </thead>
                            <#if (organizationList.list?size>0)>
	                            <tbody>
			                            <#list organizationList.list as organization>
		                                <tr>
		                                    <td>
		                                    	<label class="checkboxTwo inline">
		                                    		<input type="checkbox" name="id" value="${organization.id}">
												</label>
											</td>
		                                    <td>${organization.name}</td>
		                                    <td>${organization.introduction}</td>
		                                    <td>${organization.code}</td>
											<td>${organization.parentId}</td>
											<td>${organization.memo}</td>
		                                    <td>
		                                    	 <#if organization.ct??>
		                                              ${organization.ct?string('yyyy-MM-dd HH:mm:ss')}
		                                         </#if>
		                                    </td>
		                                    <td>
		                                    	<a href="javascript:void(0)" onclick="$organization.toEdit('${organization.id}')">编辑</a>|
		                                    	<a href="javascript:void(0)" onclick="$organization.del('${organization.id}')">删除</a>
											</td>
		                                </tr>
		                                </#list>
	                                
	                            </tbody>
                            </#if>
                            <#if (organizationList?size=0)>
                                <tbody>	
                               		<tr><td colspan="9"> 未查询到任何相关内容  <td></tr>
                                </tbody>
                            </#if>
                        </table>
                    </div>
                </div>
            </div>
        </div>
		
        <div class="pagination pagination-centered">
            <ul>
                <li><a href="javascript:;" onclick="$organization.changePage(${organizationList.prePage});">上一页</a></li>
                
                <#if organizationList.pages != 0>
	                <#list 1..organizationList.pages as p>
	                	<#if organizationList.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$organization.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$organization.changePage(${organizationList.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$organization.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
        
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
