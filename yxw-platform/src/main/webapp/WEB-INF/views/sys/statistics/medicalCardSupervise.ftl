<html>
<head>
	
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
	<script type="text/javascript" src="${basePath}js/dialog.js"></script>
    <title>就诊人管理</title>
</head>

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
	};
	

	function updateMedcalState(id,state,openId,updateState) {
	
	    if(updateState==1 && updateState==state){
	       showMessageBox('已是绑定状态，无需重复操作。');
	       return;
	    }
	    if(updateState==0 && updateState==state){
	       showMessageBox('已是解除绑定状态，无需重复操作。');
	       return;
	    }
		//$Y.loading.show('正在为您解绑就诊卡...');
		$.ajax({
			type: "POST",
			url: '${basePath}'+"statistics/updateMedicalCardSta",  
			data: {
				id: id,
				appId: '${appId}',
				openId: openId,
				state:updateState,
			},
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			type: 'POST',
			error: function(data) {
				//$Y.loading.hide();
				showMessageBox('解绑失败，请稍后重试。');
			},
			success: function(data) {
				//$Y.loading.hide();
				console.log(data);
				if (data.status == 'OK') {
					if (data.message.isSuccess == 'success') {
						
						setTimeout(function() {
	  	  					$('#medicalCardListForm').submit();
	  	  				}, 1000);
	  	  				showMessageBox('操作成功。');
						
					} else {
						showMessageBox('解绑失败，请稍后重试。');
					}
				} else {
					showMessageBox('解绑失败，请稍后重试。');
				}
			}
		})
	};
	
	function showMessageBox(data) {
		
	  $Y.tips(data,2000);
	};
	
	function showTiShiBox(data) {
		new $Y.dialog({
            title:"提示",
            height:'252px',
            width:'444px',
            mark:false,
            content:'<div class="loading_w"><span class="icon-loading"></span>加载中...</div>',
            callback:function(){}
       });
	};
	
	
</script>
<body>
	<#include "/sys/statistics/statisticsMenu.ftl">
	<div id="content" style="margin-top: -125px;">
	    <div id="content-header">
	    	<div class="widget-title">
	    		<h3 class="title">
	    			<a href="${basePath}statistics/index">返回医院列表</a>
	    		</h3>
	    	</div>
	        <div class="widget-title"><h3 class="title">就诊人管理.${hospital.name}</h3></div>
	    </div>
	    <div class="container-fluid">
	        <div class="space10"></div>
	        <div class="row-fluid">
	            <div class="space10"></div>
	            <div class="widget-box orderBox">
	                <div class="space10"></div>
	                <div class="widget-content">
	            	<form id="medicalCardListForm" class="form-horizontal" method="post" action="${basePath}statistics/medicalCardSupervise" accept-charset="utf-8">
	            		<#if pager?exists>
	                		<input type="hidden" name="pageNum" value="${pager.pageNum}" />
	            			<input type="hidden" name="pageSize" value="${pager.pageSize}" />
	            			<input type="hidden" name="pages" value="${pager.pages}" />
	            			<input type="hidden" name="total" value="${pager.total}" />
	            			<input type="hidden" name="type" value="${type}" />
	        			</#if>
	        			<input type="hidden" name="hospitalId" value="${hospital.id}" />
	            	
	            		<div class="row-fluid">
	                        
	                        <div class="control-group">
	                        	<label class="control-label">状态</label>
	                            <div class="controls" style="margin-left: 70px;">
                            	<div class="my_select ">
                                	<select id="state" name="state" class="span10">
	                    				<option value="" selected="selected">全部</option>
	                    				<option value="1" <#if state == 1>selected="selected"</#if>>绑定</option>
	                    				<option value="0" <#if state == 0>selected="selected"</#if>>未绑定</option>
	                    			</select>
								</div>　
							</div>
	               			</div>
	               			<div class="control-group">
	                        	<label class="control-label">姓名</label>
	                            <div class="controls">
	                            	<input type="text" name="name" class="span10 input39" value="${name}" />
	                            </div>
	               			</div>
	               			<div class="control-group">
	                        	<label class="control-label">手机号码</label>
	                            <div class="controls">
	                            	<input type="text" name="mobile" class="span10 input39" value="${mobile}" />
	                            </div>
	               			</div>
	               			<div class="control-group">
	                        	<label class="control-label">患者卡号</label>
	                            <div class="controls">
	                            	<input type="text" name="cardNo" class="span10 input39" value="${cardNo}" />
	                            </div>
	               			</div>
						</div>
						<div class="row-fluid">
	       
	               			<div class="control-group">
	                        	<label class="control-label">身份证号</label>
	                            <div class="controls">
	                            	<input type="text" name="idNo" class="span10 input39" value="${idNo}" />
	                            </div>
	               			</div>
	               			<div class="control-group">
	                        	<label class="control-label">OpenId</label>
	                            <div class="controls">
	                            	<input type="text" name="openId" class="span10 input39" value="${openId}" />
	                            </div>
	               			</div>
	               			<div class="control-group" style="width:40%;">
	                            <label class="control-label">查询时间</label>
	                            <div class="controls">
	                            	<input type="date" name="startDate" class="span4 input39" value="${startDate}" />
	                            	　>>　
	                            	<input type="date" name="endDate" class="span4 input39" value="${endDate}" />
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
	                        		<tr">
	                        		    <th>序号</th>
	                                	<th>姓名</th>
	                                	<th>性别</th>
	                                	<th>生日</th>
	                                	<th>手机号码</th>
	                                	
	                                	<th>就诊卡号</th>
	                                	<th>身份证号码</th>
	                                	<th>状态</th>
	                                	<th>关系</th>
	                                	<th>绑卡时间</th>
	                                	<th>openId</th>
	                                	
	      
	                            	</tr>
	                    		</thead>
        					    <tbody>
	        						<#if pager?exists>
                                		<#list pager.list as medical>
                                		  <tr >
                                		    <td> 
                                		     	${medical_index + 1}
                                		    </td>
                                			<td>${medical.name}</td>
											<td>
			                                    <#if medical.sex == 1>男</#if>
	                    						<#if medical.sex == 2>女</#if>
			                                </td>
			                                <td>${medical.birth}</td>
			                                <td>${medical.mobile}</td>
			                                <td>${medical.cardNo}</td>
			                                <td>${medical.idNo}</td>
			                                <td>
												<#if medical.state == 1>绑定</#if>
	                    						<#if medical.state != 1>未绑定</#if>
											</td>
			                                <td>${medical.ownership}</td>
			                                <td>
			                                    ${medical.bangkaTime}
			                                </td>
			                                <td>${medical.openId}</td>
			                                <td>
			                                	<@p.hasPermission value="statistics-bindMedicalCard"> 
	                                         	<a href="javascript:void(0);" onclick="updateMedcalState('${medical.id}','${medical.state}','${medical.openId}',1);">绑定</a>
	                      						</@p.hasPermission>
	                      						
	                      						
	                      						<@p.hasPermission value="statistics-unbindMedicalCard"> 
	                                         	<a href="javascript:void(0);" onclick="updateMedcalState('${medical.id}','${medical.state}','${medical.openId}',0);">解除绑定</a>
												</@p.hasPermission>
			                                </td>
			                               </tr>
			                             </#list>
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
	    	<#if pager?exists>
        		<ul>
            		<li><a href="javascript:void(0);" onclick="order.changePage(${pager.prePage},'medicalCardListForm');">上一页</a></li>
            			<#if pager.pages != 0>
			            	<#if (pager.pages <= 5)>
			            	    
				                <#list 1..pager.pages as p>
				                	<#if pager.pageNum == p>
				                		<li class="disabled"><span>${p}</span></li>
				                	<#else>
				                    	<li><a href="javascript:void(0);" onclick="order.changePage(${p},'medicalCardListForm');">${p}</a></li>
				                  	</#if>
				                </#list>
			                 <#else>
				            	<#if pager.pageNum <= 2 >
				            		<#if pager.pageNum == 1>
				            			<li class="disabled"><span>${pager.pageNum}</span></li>
				            			<#list 1..4 as p>
						                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 1},'medicalCardListForm');">${p + 1}</a></li>
						                </#list>
				            		<#elseif pager.pageNum == 2>
				            			<li><a href="javascript:void(0);" onclick="order.changePage(1,'medicalCardListForm');">1</a></li>
				            			<li class="disabled"><span>${pager.pageNum}</span></li>
				            			<#list 1..3 as p>
						                	<li><a href="javascript:void(0);" onclick="order.changePage(${p + 2},'medicalCardListForm');">${p + 2}</a></li>
						                </#list>
				            		</#if>
				            	<#elseif pager.pages == pager.pageNum>
			            			<#list 4..1 as p>
					                	<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pageNum - p},'medicalCardListForm');">${pager.pageNum - p}</a></li>
					                </#list>
			            			<li class="disabled"><span>${pager.pageNum}</span></li>
	            				<#elseif (pager.pages - pager.pageNum) == 1>
			            			<#list 3..1 as p>
					                	<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pageNum - p},'medicalCardListForm');">${pager.pageNum - p}</a></li>
					                </#list>
			            			<li class="disabled"><span>${pager.pageNum}</span></li>
			            			<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pages},'medicalCardListForm');">${pager.pages}</a></li>
				            	<#else>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pageNum - 2},'medicalCardListForm');">${pager.pageNum - 2}</a></li>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pageNum - 1},'medicalCardListForm');">${pager.pageNum - 1}</a></li>
			            		<li class="disabled"><span>${pager.pageNum}</span></li>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pageNum + 1},'medicalCardListForm');">${pager.pageNum + 1}</a></li>
			            		<li><a href="javascript:void(0);" onclick="order.changePage(${pager.pageNum + 2},'medicalCardListForm');">${pager.pageNum + 2}</a></li>
			            	</#if>
		                </#if>
		            </#if>
            		<li><a href="javascript:;" onclick="order.changePage(${pager.nextPage},'medicalCardListForm');">下一页</a></li>
            	</ul>
		        <div class="pageGoto">
		            <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
		            <a href="javascript:void(0);" class="goto" onclick="order.changePage($('#skipPage').val(),'medicalCardListForm');">跳转</a>
		            <span>共计 ${pager.pages} 页</span>
		        </div>
			</#if>
		</div>
	</div>
<#include "./sys/common/footer.ftl">
</body>
</html>

