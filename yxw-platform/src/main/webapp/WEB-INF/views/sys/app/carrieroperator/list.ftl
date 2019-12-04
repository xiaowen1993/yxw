<html>
<head>
<#include "/sys/common/common.ftl">
</head>
<body>
<div id="content">
	<!--content str-->
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">运营管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button type="button" class="btn btn-ok" onclick="window.location.href='${basePath}sys/app/carrieroperator/getAddCarrieroperator'">新增</button>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th width="200" >内容标题</th>
                                <th>运营位置</th>
                                <th >排序</th>
                                <th >状态</th>
                                <th >管理</th>
                            </tr>
                            </thead>
                            <#if (pager.list?size>0)>
	                            <tbody>
	                            	<#list pager.list as carrieroperator>
		                                <tr>
		                                	<td>${carrieroperator_index + 1}</td>
		                                    <td>${carrieroperator.title}</td>
		                                    <td>
												<#if carrieroperator.operationPosition == "1">启动页
												<#elseif carrieroperator.operationPosition == "2">首页轮播
												<#else>启动页；首页轮播</#if>
											</td>
		                                    <td>${carrieroperator.sorting}</td>
		                                    <td>
		                                    	<#if carrieroperator.status == "0">发布
												<#else><span style="color:red;">未发布</span></#if>
		                                    </td>
		                                    
		                                    <td>
		                                        <a href="${basePath}sys/app/carrieroperator/getCarrieroperatorById?id=${carrieroperator.id}">编辑</a>
		                                    </td>
		                                </tr>
	                                </#list>
	                            </tbody>
	                        <#else>
									<tr><td colspan="10">暂无运营数据</td></tr>
                            </#if>
                        </table>
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
</div>
<!--content end-->

<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
