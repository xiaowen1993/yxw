<html>
<head>
	<#include "./common/common.ftl">
	<script type="text/javascript" src="${basePath}js/echarts.min.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/datePlugin.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/biz/stats/regDeptStats.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/hospitalList.js"></script>
	<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.js"></script>
    <title>挂号订单</title>
    <style>
    	.btn-save {
    		height: 20px;
    	}
    </style>
</head>
<body>
<div id="content">
	<div id="content-top">
		<div class="container-fluid">
			<div class="box">
	            <div id="user-nav" class="navbar">
	            	<div id="select-msg" class="dropdown">
	                	<a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown">
	                    	<span class="text">${commonVo.hospitalName}</span>
	                        <i class="caret"></i>
	                    </a>
	                    <ul class="dropdown-menu">
	                    	<#list commonEntity as hospital>
	                    		<#if hospital.code != commonVo.hospitalCode>
	                    			<li><a href="javascript:void(0);" onclick="hospitalList.showDetail('${hospital.name}', '${hospital.code}', ${commonVo.bizType})">${hospital.name}</a></li>
	                    		</#if>
	                    	</#list>
	                    </ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	
    <div id="content-header">
    	<div class="widget-title">
	        <h3 class="title">
	            <a href="javascript: void(0);" class="tab" data-url="stats/order/index">全部订单</a> |
	            <a href="javascript: void(0);" class="tab" data-url="stats/reg/index">挂号订单</a> |
	            <a href="javascript: void(0);" class="tab" data-url="stats/clinic/index">门诊订单</a> |
	            <a href="javascript: void(0);" class="tab" data-url="stats/deposit/index">住院订单</a> |
	            <a href="javascript: void(0);" class="tab active" data-url="stats/regDept/index">科室订单</a>
	        </h3>
        </div>
    </div>
	
	<div class="container-fluid">
        <div class="space20"></div>
			<div class="row-fluid">
		            <div class="space10"></div>
		            <div class="widget-box orderBox">
		                <div class="form-horizontal">
		                    <div class="row-fluid">
		                        <div class="control-group">
		                            <label class="control-label">科室名称</label>
		                            <div class="controls"><input type="text" placeholder="" class="span5 input10" id="deptName" name="deptName" style="min-width: 200px;"/></div>
								</div>
							</div>
							<div class="row-fluid">
								<div class="control-group">
		                            <label class="control-label">选择渠道</label>
		                            <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="全部" readOnly id="pPlatform" data-value="-1">
									    <ul class="ui-select-list">
									    	<li data-value="-1">全部</li>
									    	<#list platforms as item>
									    	<li data-value="${item.code}">${item.name}</li>
									    	</#list>
									    </ul>
									</div>
		                        </div>
							</div>
		                    <div class="space20"></div>
		                    <div class="row-fluid">
		                        <div class="btn-box textleft">
		                            <div class="btn btn-save w80" id="btnSearch">查询</div>
		                            <div class="spaceW15"></div>
		                            <div class="btn btn-save w80" id="btnDownload">下载</div>
		                        </div>
		
		                        <div class="space20"></div>
		                    </div>
		                </div>
		                <div class="space10"></div>
		                <div class="widget-content">
		                    <table class="table table-bordered table-textCenter table-striped table-hover">
		                        <thead>
			                        <tr>
			                            <th>科室名称</th>
			                            <th>昨日订单数</th>
			                            <th>上周订单数</th>
			                            <th>本月累计订单数</th>
			                            <th>上月累计订单数</th>
			                            <th>本年累计订单数</th>
			                            <th>历史累计订单数</th>
			                        </tr>
		                        </thead>
		                        <tbody id="statsDetail">
									
		                        </tbody>
		                    </table>
		                </div>
		            </div>
		        </div>
			
			<div class="row-fluid">
		    	<div class="pagination pagination-centered">
		        	<ul id="pageUl">
		        	</ul>
		       	</div>
		   	</div>
		</div>		   	
	</div>		   	
</div>
</body>

<form id="voForm" method="post">
	<input type="hidden" name="hospitalCode" id="hospitalCode" value="${commonVo.hospitalCode}" />
	<input type="hidden" name="hospitalName" id="hospitalName" value="${commonVo.hospitalName}" />
	<input type="hidden" name="platform" id="platform" value="-1" />
	<input type="hidden" name="fields" id="fields" value="" />
	<input type="hidden" name="branchCode" id="branchCode" value="" />
	<input type="hidden" name="beginDate" id="beginDate" value="" />
	<input type="hidden" name="endDate" id="endDate" value="" />
	<input type="hidden" name="statsDate" id="statsDate" value="" />
	<input type="hidden" name="pageSize" id="pageSize" value="" />
	<input type="hidden" name="pageIndex" id="pageIndex" value="" />
</form>
	<input type="hidden" name="maxPage" id="maxPage" value="" />

</html>