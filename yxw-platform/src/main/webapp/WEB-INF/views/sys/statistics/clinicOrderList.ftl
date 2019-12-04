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
        <div class="widget-title"><h3 class="title">门诊订单统计.${hospital.name}</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form id="clinicOrderForm" class="form-horizontal" method="post" action="${basePath}statistics/clinicOrderList" accept-charset="utf-8">
            		<#if clinicOrderCounts?exists>
                		<input type="hidden" name="pageNum" value="${clinicOrderCounts.pageNum}" />
            			<input type="hidden" name="pageSize" value="${clinicOrderCounts.pageSize}" />
            			<input type="hidden" name="pages" value="${clinicOrderCounts.pages}" />
            			<input type="hidden" name="total" value="${clinicOrderCounts.total}" />
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
                        <div class="control-group">
                            <label class="control-label">渠道</label>
                            <div class="controls">
                            <div class="my_select">
	                       		<select id="bizMode" name="bizMode" class="span10">
	                				<option value="" selected="selected">全部</option>
	                				<option value="1">微信</option>
	                				<option value="2">支付宝</option>
	                			</select>
                			</div>
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
        						<#if clinicOrderCounts?exists>
                                	<#list clinicOrderCounts.list as order>
	                                	<tr>
		                                    <td>${order_index + 1} </td>
		                                    <td>${order.date}</td>
		                                    <td>${order.payment + order.refund+order.noPayment}</td>
		                                    <td>${order.payment}</td>
		                                    <td>${order.refund}</td>
		                                    <td>${order.clinicPayFee}</td>
		                                    <td>${order.clinicRefundFee+order.partRefund}</td>
		                                </tr>
									</#list>
									<#if (clinicOrderCounts.list?size > 0) >
										<tr>
											<td></td>
											<td></td>
		                                    <td colspan="5">订单总数(包含未交费订单)：${clinicOrderCount.count}&nbsp;&nbsp;&nbsp;支付订单总数：${clinicOrderCount.payCount}&nbsp;&nbsp;&nbsp;退费订单总数：${clinicOrderCount.refundCount}&nbsp;&nbsp;&nbsp;支付总金额(单位:分)：${clinicOrderCount.clinicPayTotalFee}&nbsp;&nbsp;&nbsp;退费总金额(单位:分)：${clinicOrderCount.clinicRefundTotalFee+clinicOrderCount.partTotalRefund}&nbsp;&nbsp;&nbsp; </td>
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
    	<#if clinicOrderCounts?exists>
        <ul>
            <li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.prePage},'clinicOrderForm');">上一页</a></li>
            <#if clinicOrderCounts.pages != 0>
            	<#if (clinicOrderCounts.pages <= 5)>
	                <#list 1..clinicOrderCounts.pages as p>
	                	<#if pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p},'clinicOrderForm');">${p}</a></li>
	                  	</#if>
	                </#list>
                 <#else>
	            	<#if clinicOrderCounts.pageNum <= 2 >
	            		<#if clinicOrderCounts.pageNum == 1>
	            			<li class="disabled"><span>${clinicOrderCounts.pageNum}</span></li>
	            			<#list 1..4 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1},'clinicOrderForm');">${p + 1}</a></li>
			                </#list>
	            		<#elseif clinicOrderCounts.pageNum == 2>
	            			<li><a href="javascript:void(0);" onclick="order.changePage(1,'clinicOrderForm');">1</a></li>
	            			<li class="disabled"><span>${clinicOrderCounts.pageNum}</span></li>
	            			<#list 1..3 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2},'clinicOrderForm');">${p + 2}</a></li>
			                </#list>
	            		</#if>
	            	<#elseif clinicOrderCounts.pages == clinicOrderCounts.pageNum>
            			<#list 4..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pageNum - p},'clinicOrderForm');">${clinicOrderCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${clinicOrderCounts.pageNum}</span></li>
	            	<#elseif (clinicOrderCounts.pages - clinicOrderCounts.pageNum) == 1>
            			<#list 3..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pageNum - p},'clinicOrderForm');">${clinicOrderCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${clinicOrderCounts.pageNum}</span></li>
            			<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pages},'clinicOrderForm');">${clinicOrderCounts.pages}</a></li>
	            	<#else>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pageNum - 2},'clinicOrderForm');">${clinicOrderCounts.pageNum - 2}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pageNum - 1},'clinicOrderForm');">${clinicOrderCounts.pageNum - 1}</a></li>
	            		<li class="disabled"><span>${clinicOrderCounts.pageNum}</span></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pageNum + 1},'clinicOrderForm');">${clinicOrderCounts.pageNum + 1}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${clinicOrderCounts.pageNum + 2},'clinicOrderForm');">${clinicOrderCounts.pageNum + 2}</a></li>
	            	</#if>
                </#if>
            </#if>
            <li><a href="javascript:;" onclick="order.changePage(${clinicOrderCounts.nextPage},'clinicOrderForm');">下一页</a></li>
        </ul>
        <div class="pageGoto">
            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val(),'clinicOrderForm');">跳转</a>
            <span>共计 ${clinicOrderCounts.pages} 页</span>
        </div>
        </#if>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>

