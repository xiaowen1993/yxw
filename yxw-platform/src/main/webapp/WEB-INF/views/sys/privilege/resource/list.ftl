<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/resource/sys.resource.js"></script>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">资源管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button type="button" class="btn btn-ok" onclick="$resource.toAdd()">新增</button>
                    <button type="button" class="btn btn-ok" onclick="$resource.batchDel()">删除</button>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="" id="form" >
                		<input type="hidden" name="pageNum" value="${resourceList.pageNum}" />
            			<input type="hidden" name="pageSize" value="${resourceList.pageSize}" />
            			<input type="hidden" name="pages" value="${resourceList.pages}" />
            			<input type="hidden" name="total" value="${resourceList.total}" />
                	
	                    <input type="text" name="search" value="${search}" placeholder="请输入资源名称"/>
	                    <button class="tip-bottom" onclick="$resource.search();">
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
                                <th nowrap>资源名称</th>
                                <th nowrap>编码</th>
                                <th nowrap>资源</th>
                                <th nowrap>资源类型</th>
                                <th>备注</th>
                                <th nowrap>创建时间</th>
                                <th nowrap>管理</th>
                            </tr> 
                            </thead>
                            <#if (resourceList.list?size>0)>
	                            <tbody>
			                            <#list resourceList.list as resource>
		                                <tr>
		                                    <td>
		                                    	<label class="checkboxTwo inline">
		                                    		<input type="checkbox" name="id" value="${resource.id}"/>
												</label>
											</td>
		                                    <td nowrap>${resource.name}</td>
		                                    <td nowrap>${resource.code}</td>
		                                    <td nowrap>${resource.abstr}</td>
											<td>
												<#if resource.type==1>
													菜单
													<#elseif resource.type==2>
													按钮
													<#elseif resource.type==3>
													普通链接
													<#elseif resource.type==4>
													医院
												</#if>
											</td>
											<td>${resource.memo}</td>
		                                    <td nowrap>
		                                    	 <#if resource.ct??>
		                                              ${resource.ct?string('yyyy-MM-dd HH:mm:ss')}
		                                         </#if>
		                                    </td>
		                                    <td nowrap>
		                                    	<a href="javascript:void(0)" onclick="$resource.toEdit('${resource.id}')">编辑</a>|
		                                    	<a href="javascript:void(0)" onclick="$resource.del('${resource.id}')">删除</a>
											</td>
		                                </tr>
		                                </#list>
	                                
	                            </tbody>
                            </#if>
                            <#if (resourceList?size=0)>
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
                <li><a href="javascript:;" onclick="$resource.changePage(${resourceList.prePage});">上一页</a></li>
                
                <#if resourceList.pages != 0>
	                <#list 1..resourceList.pages as p>
	                	<#if resourceList.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$resource.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$resource.changePage(${resourceList.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$resource.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
        
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
