<html>
<head>
	<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/order/sys.order.js"></script>
    <title>订单管理</title>
</head>
<body>
	<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">订单管理&nbsp;&nbsp;<a href="${basePath}order/queryMedicalCard?hospitalId=${hospitalId}">绑卡管理</a></h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form class="form-horizontal" method="post" action="${basePath}order/orderList" accept-charset="utf-8">
            		<#if orderViews?exists>
                		<input type="hidden" name="pageNum" value="${orderViews.pageNum}" />
            			<input type="hidden" name="pageSize" value="${orderViews.pageSize}" />
            			<input type="hidden" name="pages" value="${orderViews.pages}" />
            			<input type="hidden" name="total" value="${orderViews.total}" />
        			</#if>
        			<input type="hidden" name="hospitalId" value="${hospitalId}" />
            	
            		<div class="row-fluid">
                        <div class="control-group">
                            <label class="control-label">订单时间</label>
                            <div class="controls">
                            	<div class="my_select">
                                	<select id="dayType" name="dayType" class="span10">
							        	<option value="" selected="selected">全部</option>
							        	<option value="0" <#if dayType == 0>selected="selected"</#if>>今天</option>
							          	<option value="-1" <#if dayType == -1>selected="selected"</#if>>昨天</option>
							          	<option value="-3" <#if dayType == -3>selected="selected"</#if>>近三天</option>
							          	<option value="-7" <#if dayType == -7>selected="selected"</#if>>近一周</option>
							          	<option value="-30" <#if dayType == -30>selected="selected"</#if>>近一个月</option>
							    	</select>
								</div>
                            </div>
                        </div>
                        <div class="control-group">
                        	<label class="control-label">订单类型</label>
                            <div class="controls">
                            	<div class="my_select">
                                	<select id="orderType" name="orderType" class="span10" onchange="orderTypeChange(this)">
	                    				<option value="" selected="selected">全部</option>
	                    				<option value="1" <#if orderType == 1>selected="selected"</#if>>挂号缴费</option>
	                    				<option value="2" <#if orderType == 2>selected="selected"</#if>>门诊缴费</option>
          								<option value="3" <#if orderType == 3>selected="selected"</#if>>住院押金补缴</option>
	                    			</select>
                              	</div>　
							</div>
						</div>
						<div id="bizStuatsDiv" class="control-group" <#if orderType == null || orderType == ''>style="display: none;"</#if>>
							<label class="control-label">业务状态</label>
							<div class="controls">
                          		<div class="my_select">
                          			<select id="bizStatus" name="bizStatus" class="span10">
	                    				<option value="" selected="selected">全部</option>
	                    				<#if orderType == 1>
		                    				<#if registerStuats?exists>
								                <#list registerStuats?keys as key>
								                	<#if bizStatus == key>
								                		<option value="${key}" <#if bizStatus == key>selected="selected"</#if>>${registerStuats[key]}</option>
								                	<#else>
								                		<option value="${key}" >${registerStuats[key]}</option>
								                	</#if>
								                </#list>
								            </#if>
                          				</#if>
                          				<#if orderType == 2>
		                    				<#if clinicStuats?exists>
								                <#list clinicStuats?keys as key>
								                	<#if bizStatus == key>
								                		<option value="${key}" <#if bizStatus == key>selected="selected"</#if>>${clinicStuats[key]}</option>
								                	<#else>
								                		<option value="${key}" >${clinicStuats[key]}</option>
								                	</#if>
								                </#list>
								            </#if>
                          				</#if>
                          				<#if orderType == 3>
                          				
                          				</#if>
									</select>
								</div>　
							</div>
						</div>
						<div class="control-group">
							<label class="control-label">支付状态</label>
							<div class="controls">
                          		<div class="my_select">
                                	<select id="payStatus" name="payStatus" class="span10">
	                    				<option value="" selected="selected">全部</option>
								        <option value="1" <#if payStatus == 1>selected="selected"</#if>>未支付</option>
								        <option value="4" <#if payStatus == 4>selected="selected"</#if>>支付中</option>
								        <option value="2" <#if payStatus == 2>selected="selected"</#if>>已支付</option>
								        <option value="5" <#if payStatus == 5>selected="selected"</#if>>退费中</option>
								        <option value="3" <#if payStatus == 3>selected="selected"</#if>>已退费</option>
									</select>
								</div>　
							</div>
						</div>
					</div>
                   	<div class="row-fluid  ">
                   		<div class="control-group">
                        	<label class="control-label">来源渠道</label>
                            <div class="controls" style="margin-left: 70px;">
                            	<div class="my_select ">
                                	<select id="payMode" name="payMode" class="span10">
	                    				<option value="" selected="selected">全部</option>
	                    				<option value="1" <#if payMode == 1>selected="selected"</#if>>微信</option>
	                    				<option value="2" <#if payMode == 2>selected="selected"</#if>>支付宝</option>
	                    			</select>
								</div>　
							</div>
						</div>
                    	<div class="control-group">
                        	<label class="control-label">患者卡号</label>
                            <div class="controls">
                            	<input type="text" name="cardNo" class="span10 input39" value="${cardNo}" />
                            </div>
               			</div>
                        <div class="control-group">
                        	<label class="control-label">患者姓名</label>
                            <div class="controls">
	                    		<input type="text" name="patientName"  value="${patientName}" class="span10 input39" />
                            </div>
						</div>
						<div class="control-group">
							<label class="control-label">患者手机</label>
							<div class="controls">
								<input type="text" name="patientMobile"  value="${patientMobile}" class="span10 input39" />
							</div>
						</div>
					</div>
					<div class="row-fluid  ">
						<div class="control-group">
							<label class="control-label">订单号</label>
							<div class="controls">
								<input type="text" name="orderNo"  value="${orderNo}" class="span10 input39" /> 
							</div>
						</div>
                        <div class="control-group">
                            <label class="control-label">HIS订单号</label>
                            <div class="controls">
                             	<input type="text" name="hisOrdNo"  value="${hisOrdNo}" class="span10 input39" /> 
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">收单机构订单号</label>
                            <div class="controls">
                                <input type="text" name="agtOrdNum"  value="${agtOrdNum}" class="span10 input39" />
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
                	<div class="orderTable">
    					<table class="table table-bordered table-textCenter table-striped table-hover">
                    		<thead>
                        		<tr>
                                	<th width="50">序号</th>
	                                <th width="100">订单类型</th>
	                                <th width="100">业务状态</th>
	                                <th width="100">支付状态</th>
	                                <th width="150">订单生成时间</th>
	                                <th width="150">订单退费时间</th>
	                                <th width="150">就诊卡号</th>
	                                <th width="100">患者姓名</th>
	                                <th width="100">患者手机</th>
	                                <th width="180">医院订单号</th>
	                                <th width="180">医院确认收据号</th>
	                                <th width="230">收单机构订单号</th>
	                                <th width="180">订单金额</th>
	                                <th width="180">支付订单号</th>
	                                <th width="180">退费订单号</th>
	                                <th width="180">备注</th>
	                                <th width="200">操作</th>
                            	</tr>
                        	</thead>
        					<tbody>
        						<#if orderViews?exists>
                                	<#list orderViews.list as orderView>
	                                	<tr onclick="clickHit(this)">
		                                    <td>${orderView_index + 1} </td>
		                                    <td>
		                                    	<#if orderView.orderType == 1>挂号缴费</#if>
	                    						<#if orderView.orderType == 2>门诊缴费</#if>
          										<#if orderView.orderType == 3>住院押金补缴</#if>
		                                    </td>
		                                    <td>
		                                    	<#if orderView.orderType == 1>
		                                    		<#if registerStuats?exists>
										                <#list registerStuats?keys as key>
										                	<#if orderView.bizStatus == key>${registerStuats[key]}</#if>
										                </#list>
										            </#if>
		                                    	</#if>
		                                    	<#if orderView.orderType == 2>
		                                    		<#if clinicStuats?exists>
										                <#list clinicStuats?keys as key>
										                	<#if orderView.bizStatus == key>${clinicStuats[key]}</#if>
										                </#list>
										            </#if>
		                                    	</#if>
		                                    </td>
		                                    <td>
		                                    	<#if orderView.payStatus == 1>未支付</#if>
								         		<#if orderView.payStatus == 4>支付中</#if>
								         		<#if orderView.payStatus == 2>已支付</#if>
								         		<#if orderView.payStatus == 5>退费中</#if>
								         		<#if orderView.payStatus == 3>已退费</#if>
		                                    </td>
		                                    <td>${orderView.createTime}</td>
		                                    <td>${orderView.refundTime}</td>
		                                    <td>${orderView.cardNo}</td>
		                                    <td>${orderView.patientName}</td>
		                                    <td>${orderView.patientMobile}</td>
		                                    <td>${orderView.hisOrdNo}</td>
		                                    <td>${orderView.receiptNum}</td>
		                                    <td>${orderView.agtOrdNum}</td>
		                                    <td>${orderView.payFee}</td>
		                                    <td>${orderView.orderNo}</td>
		                                    <td>${orderView.refundOrderNo}</td>
		                                    <td>${orderView.title}</td>
		                                    <td>
		                                         <a href="javascript:void(0);">退费</a>
		                                         <a href="javascript:void(0);">状态修改</a>
		                                         <a href="javascript:queryLog(this,'${orderView.id}','${orderView.orderType}','${orderView.tableName}');">查看</a>
		                                    </td>
		                                </tr>
									</#list>
								<#else>
									<td colspan="10">暂无订单数据</td>
								</#if>
        					</tbody>
        				</table>
					</div>
                </div>
            </div>
        </div>
	</div>
    <div class="pagination pagination-centered">
        <#if orderViews?exists>
        <input type="hidden" name="pageNum" value="${orderViews.pageNum}" />
		<input type="hidden" name="pageSize" value="${orderViews.pageSize}" />
		<input type="hidden" name="pages" value="${orderViews.pages}" />
		<input type="hidden" name="total" value="${orderViews.total}" />
        <ul>
            <li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.prePage});">上一页 </a></li>
            <#if orderViews.pages != 0>
           		<#if (orderViews.pages <= 5)>
	                <#list 1..orderViews.pages as p>
	                	<#if pageNum == p>
	                		<li class="disabled"><span>${p}</span></li>
	                	<#else>
	                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p});">${p}</a></li>
	                  	</#if>
	                </#list>
	            <#else>
	            	<#if orderViews.pageNum <= 2 >
	            		<#if orderViews.pageNum == 1>
	            			<li class="disabled"><span>${orderViews.pageNum}</span></li>
	            			<#list 1..4 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1});">${p + 1}</a></li>
			                </#list>
	            		<#elseif orderViews.pageNum == 2>
	            			<li><a href="javascript:void(0);" onclick="order.changePage(1);">1</a></li>
	            			<li class="disabled"><span>${orderViews.pageNum}</span></li>
	            			<#list 1..3 as p>
			                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2});">${p + 2}</a></li>
			                </#list>
	            		</#if>
	            	<#elseif orderViews.pages == orderViews.pageNum>
            			<#list 4..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pageNum - p});">${orderViews.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${orderViews.pageNum}</span></li>
	            	<#elseif (orderViews.pages - orderViews.pageNum) == 1>
            			<#list 3..1 as p>
		                	<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pageNum - p});">${orderViews.pageNum - p}</a></li>
		                </#list>
            			<li class="disabled"><span>${orderViews.pageNum}</span></li>
            			<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pages});">${orderViews.pages}</a></li>
	            	<#else>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pageNum - 2});">${orderViews.pageNum - 2}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pageNum - 1});">${orderViews.pageNum - 1}</a></li>
	            		<li class="disabled"><span>${orderViews.pageNum}</span></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pageNum + 1});">${orderViews.pageNum + 1}</a></li>
	            		<li><a href="javascript:void(0);" onclick="order.changePage(${orderViews.pageNum + 2});">${orderViews.pageNum + 2}</a></li>
	            	</#if>
                </#if>
            </#if>
            <li><a href="javascript:;" onclick="order.changePage(${orderViews.nextPage});">下一页</a></li>
        </ul>
        <div class="pageGoto">
            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val());">跳转</a>
            <span>共计 ${orderViews.pages} 页</span>
        </div>
        </#if>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
<script type="text/javascript">
	/*选中 变色*/
	function clickHit(obj){
		var bl = jQuery(obj).attr("class");
		jQuery(obj).parent().find("tr").removeClass("hit-class");
		if(bl){
			jQuery(obj).removeClass('hit-class');
		}else{
			jQuery(obj).addClass('hit-class');
		}
	}
	/*查看日志*/
	function queryLog(obj,id,orderType,hashTableName){
       new $Y.dialog({
            title:'订单日志',
            width:'580px',
            height:'250px',
            content:'',
            callback:function(box){
            	$.ajax({
		            url:'orderLog',
		            data:{id:id,orderType:orderType,hashTableName:hashTableName},
		            success:function(html){
		                box.content(html);
		                $Y.ScrollBar();
		            }
		        });
            }
        });
	}
	
	/*订单类型切换*/
	function orderTypeChange(obj){
		var orderType = jQuery(obj).find("option:selected").attr("value");
		if(orderType != null && orderType != ''){
			$.ajax({
	            url:'getStuats',
	            success:function(data){
	            	var html = "<option value=\"\" selected=\"selected\">全部</option>";
	                if(orderType == '1'){
						$.each(data.registerStuats,function(key,values){
	                    	html += "<option value=\"" + key + "\" >" + values + "</option>";
						});  
					}else if(orderType == '2'){
						$.each(data.clinicStuats,function(key,values){
	                    	html += "<option value=\"" + key + "\" >" + values + "</option>";
						});  
					}else{
					
					}
					jQuery("#bizStatus").html(html);
	            }
	        });
			jQuery("#bizStuatsDiv").show();
		}else{
			jQuery("#bizStuatsDiv").hide();
		}
	}
</script>
</body>
</html>

