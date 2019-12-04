<html>
<head>
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
    <title>门诊订单统计</title>
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
        <div class="widget-title"><h3 class="title">住院押金订单统计.${hospital.name}</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form id="depositOrderForm" class="form-horizontal" method="post" action="${basePath}statistics/depositOrderList" accept-charset="utf-8">
            		<#if depositOrderCounts?exists>
                		<input type="hidden" name="pageNum" value="${depositOrderCounts.pageNum}" />
            			<input type="hidden" name="pageSize" value="${depositOrderCounts.pageSize}" />
            			<input type="hidden" name="pages" value="${depositOrderCounts.pages}" />
            			<input type="hidden" name="total" value="${depositOrderCounts.total}" />
        			</#if>
        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
        			<input type="hidden" name="type" value="${type}" />
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
	                                <th width="100">时间</th>
	                                <th width="100">订单总数</th>
	                                <th width="100">已支付总数</th>
	                                <th width="100">已退费总数</th>
	                                <th width="100">已支付总金额</th>
	                                <th width="100">已退费总金额</th>
                            	</tr>
                        	</thead>
        					<tbody>
        						<#if depositOrderCounts?exists>
                                	<#list depositOrderCounts.list as order>
	                                	<tr>
		                                    <td>${order_index + 1} </td>
		                                    <td>${order.date}</td>
		                                    <td>${order.payment + order.refund+order.noPayment}</td>
		                                    <td>${order.payment}</td>
		                                    <td>${order.refund}</td>
		                                    <td>${order.depositPayFee}</td>
		                                    <td>${order.depositRefundFee}</td>
		                                </tr>
									</#list>
									<#if (depositOrderCounts.list?size > 0) >
										<tr>
											<td></td>
											<td></td>
		                                    <td colspan="5">订单总数(包含未交费订单)：${depositOrderCount.count}&nbsp;&nbsp;&nbsp;支付订单总数：${depositOrderCount.payCount}&nbsp;&nbsp;&nbsp;退费订单总数：${depositOrderCount.refundCount}&nbsp;&nbsp;&nbsp;支付总金额(单位:分)：${depositOrderCount.depositPayTotalFee}&nbsp;&nbsp;&nbsp;退费总金额(单位:分)：${depositOrderCount.depositRefundTotalFee}&nbsp;&nbsp;&nbsp; </td>
		                                </tr>
	                                </#if>
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
    	<#if depositOrderCounts?exists>
        <ul>
            <li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.prePage},'depositOrderForm');">上一页</a></li>
            <#if depositOrderCounts.pages != 0>
            	<#if (depositOrderCounts.pages <= 5)>
	                <#list 1..depositOrderCounts.pages as p>
	                	<#if pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p},'depositOrderForm');">${p}</a></li>
	                  	</#if>
	                </#list>
                 <#else>
	            	<#if depositOrderCounts.pageNum <= 2 >
	            		<#if depositOrderCounts.pageNum == 1>
	            			<li class="disabled"><span>${depositOrderCounts.pageNum}</span></li>
	            			<#list 1..4 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1},'depositOrderForm');">${p + 1}</a></li>
			                </#list>
	            		<#elseif depositOrderCounts.pageNum == 2>
	            			<li><a href="javascript:void(0);" onclick="order.changePage(1,'depositOrderForm');">1</a></li>
	            			<li class="disabled"><span>${depositOrderCounts.pageNum}</span></li>
	            			<#list 1..3 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2},'depositOrderForm');">${p + 2}</a></li>
			                </#list>
	            		</#if>
	            	<#elseif depositOrderCounts.pages == depositOrderCounts.pageNum>
            			<#list 4..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pageNum - p},'depositOrderForm');">${depositOrderCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${depositOrderCounts.pageNum}</span></li>
	            	<#elseif (depositOrderCounts.pages - depositOrderCounts.pageNum) == 1>
            			<#list 3..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pageNum - p},'depositOrderForm');">${depositOrderCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${depositOrderCounts.pageNum}</span></li>
            			<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pages},'depositOrderForm');">${depositOrderCounts.pages}</a></li>
	            	<#else>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pageNum - 2},'depositOrderForm');">${depositOrderCounts.pageNum - 2}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pageNum - 1},'depositOrderForm');">${depositOrderCounts.pageNum - 1}</a></li>
	            		<li class="disabled"><span>${depositOrderCounts.pageNum}</span></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pageNum + 1},'depositOrderForm');">${depositOrderCounts.pageNum + 1}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${depositOrderCounts.pageNum + 2},'depositOrderForm');">${depositOrderCounts.pageNum + 2}</a></li>
	            	</#if>
                </#if>
            </#if>
            <li><a href="javascript:;" onclick="order.changePage(${depositOrderCounts.nextPage},'depositOrderForm');">下一页</a></li>
        </ul>
        <div class="pageGoto">
            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val(),'depositOrderForm');">跳转</a>
            <span>共计 ${depositOrderCounts.pages} 页</span>
        </div>
        </#if>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>

