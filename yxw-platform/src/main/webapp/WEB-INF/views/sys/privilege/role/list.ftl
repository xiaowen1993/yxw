<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/role/sys.role.js"></script>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">角色管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button type="button" class="btn btn-ok" onclick="$role.toAdd()">新增</button>
                    <button type="button" class="btn btn-ok" onclick="$role.batchDel()">删除</button>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="" id="form" >
                		<input type="hidden" name="pageNum" value="${roleList.pageNum}" />
            			<input type="hidden" name="pageSize" value="${roleList.pageSize}" />
            			<input type="hidden" name="pages" value="${roleList.pages}" />
            			<input type="hidden" name="total" value="${roleList.total}" />
                	
	                    <input type="text" name="search" value="${search}" placeholder="请输入角色名称"/>
	                    <button class="tip-bottom" onclick="$role.search();">
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
                                        <input type="checkbox" name="all" />
                                    </label>
                                </th>
                                <th>名称</th>
                                <th>编码</th>
                                <th>是否可用</th>
                                <th>备注</th>
                                <th>创建时间</th>
                                <th>管理</th>
                            </tr> 
                            </thead>
                            <#if (roleList.list?size>0)>
	                            <tbody>
			                            <#list roleList.list as role>
		                                <tr>
		                                    <td>
		                                    	<label class="checkboxTwo inline">
		                                    		<input type="checkbox" name="id" value="${role.id}"/>
												</label>
											</td>
		                                    <td>${role.name}</td>
		                                    <td>${role.code}</td>
											<td>
												<#if role.available==0>
													禁用
													<#elseif role.available==1>
													可用
												</#if>
											</td>
											<td>${role.memo}</td>
		                                    <td>
		                                    	 <#if role.ct??>
		                                              ${role.ct?string('yyyy-MM-dd HH:mm:ss')}
		                                         </#if>
		                                    </td>
		                                    <td>
		                                    	<a href="javascript:void(0)" onclick="$role.toEdit('${role.id}')">编辑</a>|
		                                    	<a href="javascript:void(0)" onclick="$role.del('${role.id}')">删除</a>
											</td>
		                                </tr>
		                                </#list>
	                                
	                            </tbody>
                            </#if>
                            <#if (roleList?size=0)>
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
                <li><a href="javascript:;" onclick="$role.changePage(${roleList.prePage});">上一页</a></li>
                
                <#if roleList.pages != 0>
	                <#list 1..roleList.pages as p>
	                	<#if roleList.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$role.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$role.changePage(${roleList.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$role.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
        
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
