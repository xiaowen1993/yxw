<html>
<head>
	<#include "./common/common.ftl">
	<script type="text/javascript" src="${basePath}js/echarts.min.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/datePlugin.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/hospitalList.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/download.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/biz/manager/cardManager.js"></script>
	<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.js"></script>
    <title>全部订单</title>
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
    	<div class="widget-title"><h3 class="title">绑卡管理</h3></div>
    </div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="space10"></div>
	        <div class="row-fluid">
	            <div class="widget-box orderBox">
	                <div class="form-horizontal">
	                    <div class="row-fluid">
	                        <div class="control-group">
	                            <label class="control-label">绑卡渠道</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="所有渠道" readOnly id="pPlatform" data-value="-1">
									    <ul class="ui-select-list">
									    	<li data-value="-1">所有渠道</li>
									       	<#list platforms as item>
	                                       	<li data-value="${item.code}">${item.name}</li>
	                                       	</#list>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">绑定状态</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="所有状态" readOnly id="pState" data-value="-1">
									    <ul class="ui-select-list">
									      <li data-value="-1">所有状态</li>
									      <li data-value="1">已绑定</li>
									      <li data-value="0">未绑定</li>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="row-fluid">
	                        <div class="control-group">
	                            <label class="control-label">患者姓名</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pName">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">患者卡号</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pCardNo">
	                            </div>
	                        </div>
	                    </div>
	                    <div class="row-fluid">
	                        <div class="control-group">
	                            <label class="control-label">患者手机</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pMobile">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">证件号码</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pIdNo">
	                            </div>
	                        </div>
	                    </div>
	                    <div class="space20"></div>
	                    <div class="row-fluid">
	                        <div class="btn-box textleft">
	                            <button class="btn btn-save w80" id="btnSearch">查询</button>
	                            <div class="spaceW15"></div>
	                            <button class="btn btn-save w80" id="btnDownload">下载</button>
	                        </div>
	
	                        <div class="space20"></div>
	                    </div>
	                </div>
	                <div class="space10"></div>
	                <div class="widget-content">
	                    <div class="box">
	                        <button class="btn btn-ok" id="unbindCard">解绑</button>
	                        <button class="btn btn-ok" id="bindCard">绑定</button>
	                    </div>
	                    <div class="space20"></div>
	                    <div class="row-fluid">
	                    <div class="orderTable">
	                    <!-- table-striped -->
	                    <table class="table table-bordered table-textCenter table-hover table-striped">
	                        <thead>
		                        <tr>
		                            <th>管理</th>
		                            <th>患者姓名</th>
		                            <th>性别</th>
		                            <th>出生日期</th>
		                            <th>身份证号</th>
		                            <th>患者卡号</th>
		                            <th>患者手机</th>
		                            <th>绑定状态</th>
		                            <th>就诊人类型</th>
		                            <th>绑卡时间</th>
		                            <th>绑卡渠道</th>
		                            <th>openid</th>
		                        </tr>
	                        </thead>
	                        <tbody id="managerDetail" class="form-check">
	                        	<td colspan="17">请输入条件进行订单检索</td>
	                        </tbody>
	                    </table>
	                </div>
	                </div>
	                </div>
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
</body>

<form id="voForm" method="post">
	<input type="hidden" name="hospitalCode" id="hospitalCode" value="${commonVo.hospitalCode}" />
	<input type="hidden" name="hospitalName" id="hospitalName" value="${commonVo.hospitalName}" />
	<input type="hidden" name="platform" id="platform" value="" />
	<input type="hidden" name="pageSize" id="pageSize" value="" />
	<input type="hidden" name="pageIndex" id="pageIndex" value="" />
	<input type="hidden" name="state" id="state" value="" />
	<input type="hidden" name="idNo" id="idNo" value="" />
	<input type="hidden" name="patientName" id="patientName" value="" />
	<input type="hidden" name="cardNo" id="cardNo" value="" />
	<input type="hidden" name="mobileNo" id="mobileNo" value="" />
</form>
	<input type="hidden" name="maxPage" id="maxPage" value="" />

</html>