<html>
<head>
	
	<#include "/sys/statistics/statisticsCommon.ftl">
	<script type="text/javascript" src="${basePath}js/sys/statistics/sys.statistics.js"></script>
	<script type="text/javascript" src="${basePath}js/dialog.js"></script>
    <title>社康中心</title>
</head>
<style>
.orderBox .form-horizontal .control-group{ width:320px; vertical-align:top;}
	.orderBox .form-horizontal .control-label{ width:90px; overflow:hidden; float:left;}
	.orderBox .form-horizontal .controls{ width:200px;}
	.orderBox .form-horizontal .controls{ margin-left：120px；}
</style>
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
	

	function suppyOrganziation(id,name) {
		$("#organizationId").val(id);
		$("#organizationName").val(name);
		$("#administrativeRegionQuert").val($("#administrativeRegion").val());
		$("#organizationNameQuert2").val($("#organizationNameQuert").val());
        $("#schedulingRegionFrom").submit();
	   
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
	<#include "/easyhealth/biz/community/bsmag/communityStatisticsMenu.ftl">
	<div id="content" style="margin-top: -125px;">
	<div id="content-top">
        <div class="container-fluid">
            <div class="box">
                <!--settings str-->
                <div id="settings">
                    <div class="set-msg dropdown">
                        <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
                            <span class="text">系统设置</span>
                            <i class="icons_settings icons-set"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><span class="text">修改密码</span><i class="icons_settings icons-password"></i></a></li>
                            <li><a href="#"><span class="text">退出登录</span><i class="icons_settings icons-loginout"></i></a></li>
                        </ul>
                    </div>
                </div>
                <!--settings end-->
            </div>
        </div>
    </div>
	    <div id="content-header">
	    	<div class="widget-title">
	    		
	    	</div>
	        <div class="widget-title"></div>
	    </div>
	    <div class="container-fluid">
	        <div class="space10"></div>
	        <div class="row-fluid">
	            <div class="space40"></div>
	            
	            <div class="widget-box orderBox">
	                <div class="space10"></div>
	                <div class="widget-content">
	            	<form id="medicalCardListForm" class="form-horizontal form-check" method="post" action="${basePath}sysSchmag/communitycenter/organizationSchmag/queryCcommunityHealth" accept-charset="utf-8">
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
	                        	<label class="control-label" >区域</label>
	                            <div class="controls" >
                            	<div class="my_select ">
                                	<select id="administrativeRegion" name="administrativeRegion" class="span10">
	                    				<option value="" selected="selected">全部</option>
	                    				<#if administrativeRegionList?exists>
                                			<#list administrativeRegionList as administrative>
                                			   <option value="${administrative.administrativeRegion}" <#if administrativeRegion == administrative.administrativeRegion>selected="selected"</#if>>
											      ${administrative.administrativeRegion}
											   </option>
                                			</#list>
                                		</#if>
	                    			</select>
								</div>　
							</div>
	               			</div>
	               			
	               			<div class="control-group">
	                        	<label class="control-label">社康中心名称</label>
	                            <div class="controls">
	                            	<input type="text" name="organizationNameQuert" id="organizationNameQuert" class="span10 input39" value="${organizationNameQuert}" />
	                            </div>
	               			</div>
	               			
						</div>

	
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
	                                	<th>社康中心名称</th>
	                                	<th>所属地区</th>
	                                	<th>排班管理</th>

	                            	</tr>
	                    		</thead>
        					    <tbody>
	        						<#if pager?exists>
                                		<#list pager.list as community>
                                		  <tr >
                                		    <td> 
                                		     	${community_index + 1}
                                		    </td>
                                			
			                                <td>${community.organizationNameSub}</td>
			                                <td>${community.administrativeRegion}</td>
			             
			                                <td>
	                                         	<a href="javascript:void(0);" onclick="suppyOrganziation('${community.id}','${community.organizationName}');">编辑排班信息</a>

			                                </td>
			                               </tr>
			                             </#list>
									<#else>
										<tr><td colspan="10">暂无社康中心信息</td></tr>
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
<form id="schedulingRegionFrom" method="post" action="${basePath}sysSchmag/communitycenter/organizationSchmag/getOrganizationSchmagByCommunitId" accept-charset="utf-8">
  <input type="hidden" id="organizationId" name="organizationId"/>
  <input type="hidden" id="organizationName" name="organizationName"/>
  <input type="hidden" id="administrativeRegionQuert" name="administrativeRegionQuert"/>
  <input type="hidden" id="organizationNameQuert2" name="organizationNameQuert"/>
</form>
<#include "./sys/common/footer.ftl">
</body>
</html>

