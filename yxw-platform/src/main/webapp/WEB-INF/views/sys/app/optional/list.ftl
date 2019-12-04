<html>
<head>
	<#include "/sys/common/common.ftl">
    
    <title>功能配置</title>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">功能配置</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid"> 
                <div class="pull-left">
                	<button type="button" class="btn btn-ok" onclick="$app.optional.toAdd();">新增功能</button>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="" accept-charset="utf-8">
                		<#if appOptionals?exists>
	                		<input type="hidden" name="pageNum" value="${appOptionals.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${appOptionals.pageSize}" />
	            			<input type="hidden" name="pages" value="${appOptionals.pages}" />
	            			<input type="hidden" name="total" value="${appOptionals.total}" />
            			</#if>
	                    <input type="text" name="search"  value="${search}" placeholder="请输入功能名称查询功能"/>
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
	                                <th width="100" nowrap>功能名称</th>
	                                <th>归属板块</th>
	                                <#-- <th>功能图标</th> -->
	                                <th>功能图标样式</th>
	                                <th nowrap>功能排序</th>
	                                <th width="50">状态</th>
	                                <th>管理</th>
	                            </tr>
                            </thead>
                            <tbody>
                            	<#if appOptionals?exists>
	                                <#list appOptionals.list as appOptional>
		                                <tr>
		                                    <td>
		                                    	${appOptional_index + 1}
		                                    </td>
		                                    <td style="display: none;">${appOptional.id}</td>
		                                    <td >${appOptional.name}</td>
		                                    <td >${appOptional.appOptionalModule.name}</td>
		                                    <#-- <td>${appOptional.icon}</td> -->
		                                    <#-- 
		                                    <td><img id="showIcon" src="<#if appOptional.icon?exists>${appOptional.icon}<#else>${basePath}images/demo-defalt.jpg</#if>" onerror="this.src='${basePath}images/demo-defalt.jpg'" height="30"/></td>
		                                    -->
											<td>${appOptional.iconCss}</td>
		                                    <td>${appOptional.showSort}</td>
		                                    
		                                    <#--是否发布 0：不发布；1：发布-->
		                                    <td>
		                                    	<#if appOptional.visible == 1>已发布<#else><span style="color: red;">未发布</span></#if>
		                                    </td>
		                                    
		                                    <td nowrap>
	                                    		<a href="javascript:void(0);" onclick="location.href='toEdit?id=${appOptional.id}'">编辑</a>
		                                    </td>
		                                </tr>
									</#list>
								<#else>
									<td colspan="7">暂无功能配置数据</td>
								</#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="pagination pagination-centered">
            <#if appOptionals?exists>
            <ul>
                <li><a href="javascript:;" onclick="$app.common.changePage(${appOptionals.prePage});">上一页</a></li>
                <#if appOptionals.pages != 0>
	                <#list 1..appOptionals.pages as p>
	                	<#if appOptionals.pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:;" onclick="$app.common.changePage(${p});">${p}</a></li>
	                  	</#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$app.common.changePage(${appOptionals.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$app.common.changePage($('#skipPage').val());">跳转</a>
            </div>
            </#if>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

<script src="${basePath}js/sys/app/optional/sys.app.optional.js"></script>
<script src="${basePath}js/sys/app/sys.app.common.js"></script>
</body>
</html>

