<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/user/sys.user.js"></script>
</head>
<body>
<!--content str-->
	<#include "./sys/common/hospital_setting.ftl">
    <!--医院及系统设置 end-->
    <div id="content-header">
        <div class="widget-title"><h3 class="title">账户管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button type="button" class="btn btn-ok" onclick="$user.toAdd()">新增</button>
                    <button type="button" class="btn btn-ok"  onclick="$user.batchAvaiable('1')">启用</button>
                    <button type="button" class="btn btn-ok" onclick="$user.batchAvaiable('0')">禁用</button>
                    <button type="button" class="btn btn-ok" onclick="$user.batchDel()">删除</button>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="">
            			<input type="hidden" name="pageNum" value="${userList.pageNum}" />
            			<input type="hidden" name="pageSize" value="${userList.pageSize}" />
            			<input type="hidden" name="pages" value="${userList.pages}" />
            			<input type="hidden" name="total" value="${userList.total}" />
	                    <input type="text" name="search" value="${search}" placeholder="请输入登录账号"/>
	                    <button class="tip-bottom" onclick="$user.search();">
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
                               <th width="3%">
                                    <label class="checkboxTwoAll inline">
                                        <input type="checkbox" name="all">
                                    </label>
                                </th>
                                <th width="10%">用户名称</th>
                                <th width="10%">登录账号</th>
                                <th width="15%">email</th>
								<th width="7%">状态</th>
 								<th width="10%">角色</th>
								<th width="15%">备注</th>
                                <th width="12%">最后登录时间</th>
								<th width="13%">操作</th>
                            </tr>
                            </thead>
                            <#if (userList.list?size>0)>
	                            <tbody>
			                            <#list userList.list as user>
		                                <tr>
		                                    <td><label class="checkboxTwo inline"><input type="checkbox" name="id" value="${user.id}"></label></td>
		                                    <td>${user.fullName}</td>
		                                    <td>${user.userName}</td>
											<td>${user.email}</td>
		                                    <td>
												<#if user.available==0>
													禁用
													<#elseif user.available==1>
													启用
												</#if>
											</td>
											<td>${user.roleNames}</td>
											<td>${user.memo}</td>
		                                    <td>
		                                          <#if user.lastLoginTime??>
		                                              ${user.lastLoginTime?string('yyyy-MM-dd HH:mm:ss')}
		                                          </#if>
		                                    </td>
		                                    <td>
		                                        <a  onclick="$user.toEdit('${user.id}')">编辑</a>|
		                                        <#if user.available==1><a onclick="$user.updateAvaiable('${user.id}',0);">禁用</a>|</#if>
		                                        <#if user.available==0><a onclick="$user.updateAvaiable('${user.id}',1);">启用</a>|</#if>
		                                        <a onclick="$user.del('${user.id}');">删除</a>|
		                                        <a onclick="$user.toCopy('${user.id}')">复制</a>
		                                    </td>
		                                </tr>
		                                </#list>
	                                
	                            </tbody>
                            </#if>
                            <#if (userList?size=0)>
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
                <li><a href="javascript:;" onclick="$user.changePage(${userList.prePage});">上一页</a></li>
                
                <#if userList.pages != 0>
	                <#list 1..userList.pages as p>
	                	<#if userList.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$user.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$user.changePage(${userList.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$user.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>

