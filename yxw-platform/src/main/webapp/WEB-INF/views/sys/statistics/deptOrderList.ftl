<html>
<head>
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
    <title>科室订单统计</title>
</head>
<body>
	<#include "/sys/statistics/statisticsMenu.ftl">
	<div id="content" style="margin-top: -125px;">
    <div id="content-header">
    	<div class="widget-title">
    		<h3 class="title">
    			<a href="${basePath}statistics/index">返回医院列表</a>
    		</h3>
    	</div>
        <div class="widget-title"><h3 class="title">科室订单统计.${hospital.name}</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form id="deptOrderForm" class="form-horizontal" method="post" action="${basePath}statistics/deptList" accept-charset="utf-8">
            		<#if deptCounts?exists>
                		<input type="hidden" name="pageNum" value="${deptCounts.pageNum}" />
            			<input type="hidden" name="pageSize" value="${deptCounts.pageSize}" />
            			<input type="hidden" name="pages" value="${deptCounts.pages}" />
            			<input type="hidden" name="total" value="${deptCounts.total}" />
        			</#if>
        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
            		<input type="hidden" name="type" value="dept" />
					<div class="row-fluid">
                        <div class="control-group">
                            <label class="control-label">分院</label>
                            <div class="controls">
                            <div class="my_select">
	                       		<select id="branchId" name="branchId" class="span10">
	                				<option value="" selected="selected">全部</option>
	                				<#if branchHospitals?exists>
                                		<#list branchHospitals as branch>
                                			<option value="${branch.id}" <#if branchId == branch.id>selected="selected"</#if>>${branch.name}</option>
                                		</#list>
                                	</#if>
	                			</select>
                			</div>
                			</div>
                        </div>
                        <div class="control-group" style="width:50%;">
                            <label class="control-label">查询时间</label>
                            <div class="controls">
                            	<input type="text" name="startDate" class="span4 input39" value="${startDate}" />
                            	--
                            	<input type="text" name="endDate" class="span4 input39" value="${endDate}" />
                            </div>
                        </div>
					</div>
                    <div class="space20"></div>
                    <div class="btn-box">
                      <button class="btn btn btn-save w330">查询</button>
                        <div class="space20"></div>
                    </div>
				</form>
             	<div class="space10"></div>
                <div class="row-fluid">
                	<div class="orderTable" style="overflow-x: auto;">
    					<table class="table table-bordered table-textCenter table-striped table-hover" style="width: 100%;">
                    		<thead>
                        		<tr>
                                	<th width="50">序号</th>
	                                <th width="100">科室代码</th>
	                                <th width="100">科室名称</th>
	                                <th width="100">订单总数</th>
                            	</tr>
                        	</thead>
        					<tbody>
        						<#if deptCounts.list?exists>
                                	<#list deptCounts.list as dept>
	                                	<tr>
		                                    <td>${dept_index + 1} </td>
		                                    <td>${dept.deptCode}</td>
		                                    <td>${dept.deptName}</td>
		                                    <td>${dept.count}</td>
		                                </tr>
									</#list>
									<tr>
	                                    <td></td>
	                                    <td></td>
	                                    <td></td>
	                                    <td>总计：${allCount}</td>
	                                </tr>
								<#else>
									<tr><td colspan="10">暂无订单统计数据</td></tr>
								</#if>
        					</tbody>
        				</table>
					</div>
                </div>
            </div>
        </div>
	</div>
    <div class="pagination pagination-centered">
        <#if deptCounts?exists>
        <ul>
            <li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.prePage},'deptOrderForm');">上一页</a></li>
            <#if deptCounts.pages != 0>
            	<#if (deptCounts.pages <= 5)>
	                <#list 1..deptCounts.pages as p>
	                	<#if pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p},'deptOrderForm');">${p}</a></li>
	                  	</#if>
	                </#list>
                 <#else>
	            	<#if deptCounts.pageNum <= 2 >
	            		<#if deptCounts.pageNum == 1>
	            			<li class="disabled"><span>${deptCounts.pageNum}</span></li>
	            			<#list 1..4 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1},'deptOrderForm');">${p + 1}</a></li>
			                </#list>
	            		<#elseif deptCounts.pageNum == 2>
	            			<li><a href="javascript:void(0);" onclick="order.changePage(1,'deptOrderForm');">1</a></li>
	            			<li class="disabled"><span>${deptCounts.pageNum}</span></li>
	            			<#list 1..3 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2},'deptOrderForm');">${p + 2}</a></li>
			                </#list>
	            		</#if>
	            	<#elseif deptCounts.pages == deptCounts.pageNum>
            			<#list 4..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pageNum - p},'deptOrderForm');">${deptCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${deptCounts.pageNum}</span></li>
	            	<#elseif (deptCounts.pages - deptCounts.pageNum) == 1>
            			<#list 3..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pageNum - p},'deptOrderForm');">${deptCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${deptCounts.pageNum}</span></li>
            			<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pages},'deptOrderForm');">${deptCounts.pages}</a></li>
	            	<#else>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pageNum - 2},'deptOrderForm');">${deptCounts.pageNum - 2}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pageNum - 1},'deptOrderForm');">${deptCounts.pageNum - 1}</a></li>
	            		<li class="disabled"><span>${deptCounts.pageNum}</span></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pageNum + 1},'deptOrderForm');">${deptCounts.pageNum + 1}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${deptCounts.pageNum + 2},'deptOrderForm');">${deptCounts.pageNum + 2}</a></li>
	            	</#if>
                </#if>
            </#if>
            <li><a href="javascript:;" onclick="order.changePage(${deptCounts.nextPage},'deptOrderForm');">下一页</a></li>
        </ul>
        <div class="pageGoto">
            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val(),'deptOrderForm');">跳转</a>
            <span>共计 ${deptCounts.pages} 页</span>
        </div>
        </#if>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>

