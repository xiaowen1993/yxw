<html>
<head>
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
    <title>订单统计</title>
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
        <div class="widget-title"><h3 class="title">订单统计.${hospital.name}</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form id="allOrderForm" class="form-horizontal" method="post" action="${basePath}statistics/orderList" accept-charset="utf-8">
            		<#if orderCounts?exists>
                		<input type="hidden" name="pageNum" value="${orderCounts.pageNum}" />
            			<input type="hidden" name="pageSize" value="${orderCounts.pageSize}" />
            			<input type="hidden" name="pages" value="${orderCounts.pages}" />
            			<input type="hidden" name="total" value="${orderCounts.total}" />
        			</#if>
        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
        			<input type="hidden" name="type" value="${type}" />
            		<input type="hidden" name="meunType" value="orderList" />
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
                        <div class="control-group">
                            <label class="control-label">订单类型</label>
                            <div class="controls">
	                            <div class="my_select">
		                       		<select id="orderType" name="orderType" class="span10">
		                				<option value="all" selected="selected">全部</option>
		                				<option value="reg" <#if orderType == 'reg'>selected="selected"</#if>>挂号缴费</option>
		                				<option value="clinic" <#if orderType == 'clinic'>selected="selected"</#if>>门诊缴费</option>
		  								<option value="deposit" <#if orderType == 'deposit'>selected="selected"</#if>>住院押金补缴</option>
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
        						<#if orderCounts?exists>
                                	<#list orderCounts.list as order>
	                                	<tr>
		                                    <td>${order_index + 1} </td>
		                                    <td>${order.date}</td>
		                                    <#if orderType == 'all'>
		                                    	<td>${order.reservationNoPayment+order.dutyNoPayment+order.reservationPayment + order.dutyPayment + order.reservationRefund + order.dutyRefund + order.clinicPayment + order.clinicRefund}</td>
			                                    <td>${order.reservationPayment + order.dutyPayment + order.clinicPayment}</td>
			                                    <td>${order.reservationRefund + order.dutyRefund + order.clinicRefund}</td>
		                                    <#elseif  orderType == 'reg'>
		                                    	<td>${order.reservationPayment + order.dutyPayment + order.reservationRefund + order.dutyRefund }</td>
			                                    <td>${order.reservationPayment + order.dutyPayment}</td>
			                                    <td>${order.reservationRefund + order.dutyRefund}</td>
		                                    <#elseif  orderType == 'clinic'>
		                                    	<td>${order.payment + order.refund }</td>
			                                    <td>${order.payment}</td>
			                                    <td>${order.refund}</td>
		                                    </#if>
		                                    <td>${order.regPayFee+order.clinicPayFee}</td>
		                                    <td>${order.regRefundFee+order.clinicRefundFee+order.partRefund}</td>
		                                </tr>
									</#list>
									<#if (orderCounts.list?size > 0) >
										<tr>
		                                    <td>&nbsp;</td>
		                                    <td>&nbsp;</td>
		                                    <td colspan="5">订单总计：
			                                    <#if orderType == 'all'>
			                                    	${orderCount.count}
			                                    <#elseif  orderType == 'reg'>
			                                    	${orderCount.count }
			                                    <#elseif  orderType == 'clinic'>
			                                    	${clinicOrderCount.count}
			                                    </#if>
		                                    </td>
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
    	<#if orderCounts?exists>
        <ul>
            <li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.prePage},'allOrderForm');">上一页</a></li>
            <#if orderCounts.pages != 0>
            	<#if (orderCounts.pages <= 5)>
	                <#list 1..orderCounts.pages as p>
	                	<#if pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p},'allOrderForm');">${p}</a></li>
	                  	</#if>
	                </#list>
                 <#else>
	            	<#if orderCounts.pageNum <= 2 >
	            		<#if orderCounts.pageNum == 1>
	            			<li class="disabled"><span>${orderCounts.pageNum}</span></li>
	            			<#list 1..4 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1},'allOrderForm');">${p + 1}</a></li>
			                </#list>
	            		<#elseif orderCounts.pageNum == 2>
	            			<li><a href="javascript:void(0);" onclick="order.changePage(1,'allOrderForm');">1</a></li>
	            			<li class="disabled"><span>${orderCounts.pageNum}</span></li>
	            			<#list 1..3 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2},'allOrderForm');">${p + 2}</a></li>
			                </#list>
	            		</#if>
	            	<#elseif orderCounts.pages == orderCounts.pageNum>
            			<#list 4..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pageNum - p},'allOrderForm');">${orderCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${orderCounts.pageNum}</span></li>
	            	<#elseif (orderCounts.pages - orderCounts.pageNum) == 1>
            			<#list 3..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pageNum - p},'allOrderForm');">${orderCounts.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${orderCounts.pageNum}</span></li>
            			<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pages},'allOrderForm');">${orderCounts.pages}</a></li>
	            	<#else>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pageNum - 2},'allOrderForm');">${orderCounts.pageNum - 2}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pageNum - 1},'allOrderForm');">${orderCounts.pageNum - 1}</a></li>
	            		<li class="disabled"><span>${orderCounts.pageNum}</span></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pageNum + 1},'allOrderForm');">${orderCounts.pageNum + 1}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderCounts.pageNum + 2},'allOrderForm');">${orderCounts.pageNum + 2}</a></li>
	            	</#if>
                </#if>
            </#if>
            <li><a href="javascript:;" onclick="order.changePage(${orderCounts.nextPage},'allOrderForm');">下一页</a></li>
        </ul>
        <div class="pageGoto">
            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val(),'allOrderForm');">跳转</a>
            <span>共计 ${orderCounts.pages} 页</span>
        </div>
        </#if>
    </div>
</div>
<#include "./sys/common/footer.ftl">
</body>
</html>

