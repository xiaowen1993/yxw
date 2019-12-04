<#assign p=JspTaglibs["/WEB-INF/tlds/privilege.tld"]>
<html>
<head>
	<#include "/sys/statistics/statisticsCommon.ftl">
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
        <div class="widget-title"><h3 class="title">订单管理.${hospital.name}</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form id="orderStatisticsForm" class="form-horizontal" method="post" action="${basePath}order/orderList" accept-charset="utf-8">
            		<#if orderViews?exists>
                		<input type="hidden" name="pageNum" value="${orderViews.pageNum}" />
            			<input type="hidden" name="pageSize" value="${orderViews.pageSize}" />
            			<input type="hidden" name="pages" value="${orderViews.pages}" />
            			<input type="hidden" name="total" value="${orderViews.total}" />
        			</#if>
        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
            		<input type="hidden" name="type" value="${type}" />
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
				<!--<div class="space12">
					
                      <a class="" style="padding:20px;">退费</a>
                      <a class="" style="padding:20px;">状态修改</a>
                      <a class="" style="padding:20px;">查看</a>
                      <a class="" style="padding:20px;">查看第三方支付状态</a>
                      <a class="" style="padding:20px;">查询his订单状态</a>
				</div>-->
             	<div class="space10"></div>
                <div class="row-fluid">
                	<div class="orderTable">
    					<table class="table table-bordered table-textCenter table-striped table-hover">
                    		<thead>
                        		<tr>
                                	<th width="50">序号</th>
                                	<th width="200">操作</th>
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
	                                
                            	</tr>
                        	</thead>
        					<tbody>
        						<#if orderViews?exists>
                                	<#list orderViews.list as orderView>
	                                	<tr onclick="clickHit(this)">
		                                    <td>${orderView_index + 1} </td>
		                                    <td>
		                                    	 <@p.hasPermission value="statistics-refund">
		                                         <a href="javascript:refund('${orderView.orderType}','${orderView.payStatus}','${orderView.id}','${orderView.appCode}','${orderView.orderNo}','${orderView.openId}','${orderView.bizMode}');">退费</a>
		                                         </@p.hasPermission>
		                                         
		                                         
		                                         <@p.hasPermission value="statistics-changeType">                                   
		                                         <a href="javascript:changeOrderType(this,'${orderView.orderType}','${orderView.payStatus}','${orderView.id}','${orderView.appCode}','${orderView.orderNo}','${orderView.openId}','${orderView.bizStatus}');">状态修改</a>
		                                         </@p.hasPermission>
		                                         
		                                         <a href="javascript:queryLog(this,'${orderView.id}','${orderView.orderType}','${orderView.tableName}');">查看</a>
		                                         <a href="javascript:queryQuery(this,'${orderView.orderNo}','${orderView.bizMode}','${orderView.hospitalCode}','${orderView.agtOrdNum}','${orderView.payStatus}');">查看第三方支付状态</a>
		                                         <a href="javascript:queryHisRecords(this,'${orderView.orderNo}','${orderView.orderType}','${orderView.openId}');">查询his订单状态</a>
		                                    </td>
		                                    <!--<td> 
		                                    	 <input type="radio" value="male" checked="checked" name="sex"/>
		                                    	 <input type="hidden" value="${orderView.orderType}" name="orderType"/>
		                                    	 <input type="hidden" value="${orderView.payStatus}" name="payStatus"/>
		                                    	 <input type="hidden" value="${orderView.id}" name="id"/>
		                                    	 <input type="hidden" value="${orderView.appCode}" name="appCode"/>
		                                    	 <input type="hidden" value="${orderView.orderNo}" name="orderNo"/>
		                                    	 <input type="hidden" value="${orderView.openId}" name="openId"/>
		                                    	 <input type="hidden" value="${orderView.tableName}" name="tableName"/>
		                                    	 <input type="hidden" value="${orderView.hospitalCode}" name="hospitalCode"/>
		                                    	 <input type="hidden" value="${orderView.agtOrdNum}" name="agtOrdNum"/>
		                                    </td>-->
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
            content:'11111',
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
	
	/*查看第三方交易状态*/
	function queryQuery(obj,orderNo,orderType,hospitalCode,agtOrdNum,payStatus){
       new $Y.dialog({
            title:'查看第三方交易状态',
            width:'780px',
            height:'800px',
            content:'',
            callback:function(box){
            	$.ajax({
		            url:'orderQuery',
		            data:{orderType:orderType,orderNo:orderNo,hospitalCode:hospitalCode,agtOrdNum:agtOrdNum,payStatus:payStatus},
		            success:function(html){
		                box.content(html);
		                $Y.ScrollBar();
		            }
		        });
            }
        });
	}
	
	/*查看his订单状态*/
	function queryHisRecords(obj,orderNo,orderType,openId){
    	var confirm='queryRegisterRecords';
    	if(orderType==2){
    		typeName='是否进行门诊退费';
    		confirm='queryClinicRecords';
    	}
       	new $Y.dialog({
            title:'his订单状态',
            width:'780px',
            height:'800px',
            content:'',
            callback:function(box){
            	$.ajax({
		            url:confirm,
		            data:{orderType:orderType,orderNo:orderNo,openId:openId},
		            success:function(html){
		                box.content(html);
		                $Y.ScrollBar();
		            }
		        });
            }
        });
	}
	
	function refundFee(obj,id,orderType,hashTableName){
       new $Y.dialog({
            
        });
	}
	function showMessageBox(data) {
		
	 $Y.tips(data,2000);
	};
	
	function changeOrderType(obj,orderType,payStatus,id,appCode,orderNo,openId,bizStatus) {
	  var subWindow =	
	  	new $Y.dialog({
            title:'修改状态',
            width:'580px',
            height:'300px',
            content:'',
            callback:function(box){
            	$.ajax({
		            url:'changeOrderType',
		            data:{orderType:orderType,orderNo:orderNo,openId:openId,payStatus:payStatus,bizStatus:bizStatus},
		            success:function(html){
		                box.content(html);
		                $Y.ScrollBar();
		            }
		        });
            }
        });
	 		
	};
	
	//调起退费
    function refund(orderType,payStatus,id,appCode,orderNo,openId,bizMode){
    	var typeName='是否进行挂号退费';
    	var confirm='refundRegisterConfirm';
    	if(orderType==2){
    		typeName='是否进行门诊退费';
    		confirm='refundClinitConfirm';
    	}
    	 if(payStatus!=2){
	       showMessageBox('退费必须是已支付状态');
	       return;
	    }
    	window.wxc.xcConfirm(typeName, window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
    		$.ajax({
			type: "POST",
			url: confirm,
			data: {
				id: id,
				appId:'${appId}',
				openId: openId,
				orderNo:orderNo,
				appCode:appCode,
				bizMode:bizMode
			},
			cache: false, 
			dataType: "json", 
			timeout: 600000,                              
			type: 'POST',
			error: function(data) {
				//$Y.loading.hide();
				showMessageBox(data.message);
			},
			success: function(data) {
				//$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK') {
					if (data.message.refundIsSuccess == true) {
						
						setTimeout(function() {$('#orderStatisticsForm').submit();}, 1000);
	  	  				showMessageBox('第3方平台退费成功');
						
					} else {
						setTimeout(function() {$('#orderStatisticsForm').submit();}, 1000);
						showMessageBox('第3方平台退费失败:'+data.message.failMsg);
					}
				}
			}
		})
    		
    	}});
        /*new $Y.dialog({
            title:'回复内容',
            width:'252px',
            height:'444px',
            content:'<div class="loading_w"><span class="icon-loading"></span>加载中...</div>',
            callback:function(box){
                $.ajax({
                    url:'showDialog',
                    dataType:'html',
                    cache:false,
                    success:function(html){
                        box.content(html);
                        $Y.ScrollBar();
                        $(box.id).on('click','.btn-save',function(){
                           alert(orderType);
                           alert(payStatus);
                        });
                        $(box.id).on('click','.btn-remove',function(){
                            box.close();
                        });
                    }
                })
            }
        });*/
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
	
	var order = {};

	order.search = function() {
	  jQuery("#orderStatisticsForm").submit();
	}

	order.changePage = function(pageNum, pageSize) {
	  if (pageNum) {
	    var pages = $('form input[name="pages"]').val();
	    var pageNumInput = $('form input[name="pageNum"]');
	
	    // 如果输入的页数是非数字，则还是跳到当前页
	    if (isNaN(pageNum)) {
	      pageNum = pageNumInput.val();
	    }
	
	    // 如果页数大于总页数，则跳至最后一页，如页数小于最小页数，则跳至第一页
	    pageNum = pageNum > pages ? pages : pageNum;
	    pageNum = pageNum < 1 ? 1 : pageNum;
	
	    pageNumInput.val(pageNum);
	
	    // 如果修改了每页显示的数量
	    if (pageSize) {
	      $('form input[name="pageSize"]').val(pageSize);
	    }
	    order.search();
	  }
	}
	
</script>
</body>
</html>

