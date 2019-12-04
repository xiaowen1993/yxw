<html>
<head>
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
    <title>绑卡统计</title>
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
	        <div class="widget-title"><h3 class="title">绑卡统计.${hospital.name}</h3></div>
	    </div>
	    <div class="container-fluid">
	        <div class="space10"></div>
	        <div class="row-fluid">
	            <div class="space10"></div>
	            <div class="widget-box orderBox">
	                <div class="space10"></div>
	                <div class="widget-content">
	            	<form id="medicalCardListForm" class="form-horizontal" method="post" action="${basePath}statistics/medicalCardList" accept-charset="utf-8">
	            		<#if medicalCardCounts?exists>
	                		<input type="hidden" name="pageNum" value="${medicalCardCounts.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${medicalCardCounts.pageSize}" />
	            			<input type="hidden" name="pages" value="${medicalCardCounts.pages}" />
	            			<input type="hidden" name="total" value="${medicalCardCounts.total}" />
	        			</#if>
	        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
	            	
	            		<div class="row-fluid">
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
		                                <th width="100">时间</th>
		                                <th width="100">数量</th>
	                            	</tr>
	                        	</thead>
	        					<tbody>
	        						<#if medicalCardCounts?exists>
	                                	<#list medicalCardCounts.list as medicalCardCount>
		                                	<tr>
			                                    <td>${medicalCardCount_index + 1} </td>
			                                    <td>${medicalCardCount.date}</td>
			                                    <td>${medicalCardCount.count}</td>
			                                </tr>
										</#list>
										<#if (medicalCardCounts.list?size > 0) >
											<tr>
			                                    <td>&nbsp;</td>
			                                    <td>&nbsp;</td>
			                                    <td colspan="3">订单总计：${medicalCardCount.count}</td>
			                                </tr>
		                                </#if>
									<#else>
										<tr><td colspan="10">暂无绑卡统计数据</td></tr>
									</#if>
	        					</tbody>
	        				</table>
						</div>
	                </div>
	            </div>
	        </div>
		</div>
	    <div class="pagination pagination-centered">
	    	<#if medicalCardCounts?exists>
        		<ul>
            		<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.prePage},'medicalCardListForm');">上一页</a></li>
            			<#if medicalCardCounts.pages != 0>
			            	<#if (medicalCardCounts.pages <= 5)>
				                <#list 1..medicalCardCounts.pages as p>
				                	<#if pageNum == p>
				                		<li class="disabled"><span>${p}</span></li>
				                	<#else>
				                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p},'medicalCardListForm');">${p}</a></li>
				                  	</#if>
				                </#list>
			                 <#else>
				            	<#if medicalCardCounts.pageNum <= 2 >
				            		<#if medicalCardCounts.pageNum == 1>
				            			<li class="disabled"><span>${medicalCardCounts.pageNum}</span></li>
				            			<#list 1..4 as p>
						                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1},'medicalCardListForm');">${p + 1}</a></li>
						                </#list>
				            		<#elseif medicalCardCounts.pageNum == 2>
				            			<li><a href="javascript:void(0);" onclick="order.changePage(1,'medicalCardListForm');">1</a></li>
				            			<li class="disabled"><span>${medicalCardCounts.pageNum}</span></li>
				            			<#list 1..3 as p>
						                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2},'medicalCardListForm');">${p + 2}</a></li>
						                </#list>
				            		</#if>
				            	<#elseif medicalCardCounts.pages == medicalCardCounts.pageNum>
			            			<#list 4..1 as p>
					                	<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pageNum - p},'medicalCardListForm');">${medicalCardCounts.pageNum - p}</a></li>
					                </#list>
			            			<li class="disabled"><span>${medicalCardCounts.pageNum}</span></li>
	            				<#elseif (medicalCardCounts.pages - medicalCardCounts.pageNum) == 1>
			            			<#list 3..1 as p>
					                	<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pageNum - p},'medicalCardListForm');">${medicalCardCounts.pageNum - p}</a></li>
					                </#list>
			            			<li class="disabled"><span>${medicalCardCounts.pageNum}</span></li>
			            			<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pages},'medicalCardListForm');">${medicalCardCounts.pages}</a></li>
				            	<#else>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pageNum - 2},'medicalCardListForm');">${medicalCardCounts.pageNum - 2}</a></li>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pageNum - 1},'medicalCardListForm');">${medicalCardCounts.pageNum - 1}</a></li>
			            		<li class="disabled"><span>${medicalCardCounts.pageNum}</span></li>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pageNum + 1},'medicalCardListForm');">${medicalCardCounts.pageNum + 1}</a></li>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${medicalCardCounts.pageNum + 2},'medicalCardListForm');">${medicalCardCounts.pageNum + 2}</a></li>
			            	</#if>
		                </#if>
		            </#if>
            		<li><a href="javascript:;" onclick="order.changePage(${medicalCardCounts.nextPage},'medicalCardListForm');">下一页</a></li>
            	</ul>
		        <div class="pageGoto">
		            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
		            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val(),'deptOrderForm');">跳转</a>
		            <span>共计 ${medicalCardCounts.pages} 页</span>
		        </div>
			</#if>
		</div>
	</div>
<#include "./sys/common/footer.ftl">
</body>
</html>

