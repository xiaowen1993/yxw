<html>
<head>
	<#include "./common/common.ftl">
	<script type="text/javascript" src="${basePath}js/echarts.min.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/datePlugin.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/hospitalList.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/biz/manager/registerManager.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/download.js"></script>
	<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.js"></script>
    <title>挂号管理</title>
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
    	<div class="widget-title"><h3 class="title">挂号管理</h3></div>
    </div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="space10"></div>
	        <div class="row-fluid">
	            <div class="widget-box orderBox">
	                <div class="form-horizontal">
	                    <div class="row-fluid">
	                        <div class="control-group">
	                            <label class="control-label">挂号渠道</label>
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
	                            <label class="control-label">挂号类型</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="所有状态" readOnly id="pRegType" data-value="-1">
									    <ul class="ui-select-list">
									      <li data-value="-1">所有类型</li>
									      <li data-value="1">预约挂号</li>
									      <li data-value="2">当班挂号</li>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">挂号状态</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="所有状态" readOnly id="pBizStatus" data-value="-1">
									    <ul class="ui-select-list">
									    	<li data-value="-1">所有状态</li>
									    	<li data-value="0" >预约中</li>
											<li data-value="1" >已预约</li>
										 	<li data-value="2" >已取消-用户取消</li>
										 	<li data-value="3" >已取消-支付超过规定时长</li>
										 	<li data-value="4" >已取消-停诊取消</li>
										 	<li data-value="5" >预约异常(his锁号异常)</li>
										 	<li data-value="6" >第3方支付异常</li>
										 	<li data-value="7" >第3方支付成功后 his确认异常</li>
										 	<li data-value="8" >取消挂号异常</li>
										 	<li data-value="9" >第3方退费前 调用his退费确认 出现异常</li>
										 	<li data-value="10" >第3方退费异常</li>
										 	<li data-value="11" >第3方退费成功后提交his确认异常</li>
										 	<li data-value="12" >挂号失败</li>
										 	<li data-value="13" >异常导致的挂号取消 后续轮询处理 处理成功的最终状态</li>
										 	<li data-value="14" >业务异常 需要人工处理</li>
										 	<li data-value="15" >业务异常 需要到医院窗口处理</li>
										 	<li data-value="16" >停诊取消异常 未支付</li>
										 	<li data-value="17" >hia预约退费确认异常 停诊</li>
										 	<li data-value="18" >第3方退费异常 停诊</li>
										 	<li data-value="19" >第3方退费成功后提交his确认异常 停诊</li>
										 	<li data-value="20" >窗口退费成功</li>
										 	<li data-value="21" >窗口退费异常</li>
										 	<li data-value="22" >用户取消预约中</li>
										 	<li data-value="23" >后台退费:成功</li>
										 	<li data-value="24" >后台退费:失败</li>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                        <div class="control-group" style="display: none;">
	                            <label class="control-label">支付状态</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="全部状态" readOnly id="pPayStatus" data-value="-1">
									    <ul class="ui-select-list">
									    	<li data-value="-1">全部状态</li>
									    	<li data-value="1">未支付</li>
									    	<li data-value="2">已支付</li>
									    	<li data-value="3">已退费</li>
									    	<li data-value="4">支付中</li>
									    	<li data-value="5">退费中</li>
									    	<li data-value="6">已关闭</li>
									    	<li data-value="7">未退费</li>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="row-fluid">
	                    	<div class="control-group">
	                            <label class="control-label">挂号院区</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="所有院区" readOnly id="pBranch" data-value="-1">
									    <ul class="ui-select-list">
									    	<li data-value="-1">所有院区</li>
									       	<#list branches as item>
	                                       	<li data-value="${item.code}">${item.name}</li>
	                                       	</#list>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">挂号科室</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pDeptName">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">挂号医生</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pDoctorName">
	                            </div>
	                        </div>
	                    </div>
	                    <div class="row-fluid">
	                        <div class="control-group">
	                            <label class="control-label">患者名称</label>
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
	                        <div class="control-group">
	                            <label class="control-label">患者手机</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pMobile">
	                            </div>
	                        </div>
	                    </div>
	                    <div class="row-fluid">
	                        <div class="control-group" style="width: 40%;">
	                            <label class="control-label">挂号时间</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span5 input39" onclick="WdatePicker()" id="pRegBeginTime"> -
	                                <input type="text" placeholder="" class="span5 input39" onclick="WdatePicker()" id="pRegEndTime">
	                            </div>
	                        </div>
	                    </div>
	                     <div class="row-fluid">
	                        <div class="control-group" style="width: 40%;">
	                            <label class="control-label">就诊时间</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span5 input39" onclick="WdatePicker()" id="pVisitBeginTime"> -
	                                <input type="text" placeholder="" class="span5 input39" onclick="WdatePicker()" id="pVisitEndTime">
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
	                    <div class="row-fluid">
	                    <div class="orderTable">
	                    <!-- table-striped -->
	                    <table class="table table-bordered table-textCenter table-hover">
	                        <thead>
		                        <tr>
                                    <th>挂号日期</th>
                                    <th>挂号患者</th>
                                    <th>患者卡号</th>
                                    <th>患者手机</th>
                                    <th>挂号渠道</th>
                                    <th>挂号类型</th>
                                    <th>挂号院区</th>
                                    <th>挂号科室</th>
                                    <th>挂号医生</th>
                                    <th>总费用</th><!-- 挂号费是不对的，实际挂号费=挂号费+诊疗费 -->
                                    <th>挂号时间</th>
                                    <th>就诊日期</th>
                                    <th>就诊分时</th>
                                    <th>挂号状态</th>
                                    <th>支付状态</th>
                                    <th>his订单号</th>
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
	<input type="hidden" name="branchCode" id="branchCode" value="" />
	<input type="hidden" name="deptName" id="deptName" value="" />
	<input type="hidden" name="doctorName" id="doctorName" value="" />
	<input type="hidden" name="pageSize" id="pageSize" value="" />
	<input type="hidden" name="pageIndex" id="pageIndex" value="" />
	<input type="hidden" name="patientName" id="patientName" value="" />
	<input type="hidden" name="cardNo" id="cardNo" value="" />
	<input type="hidden" name="patientMobile" id="patientMobile" value="" />
	<input type="hidden" name="regType" id="regType" value="" />
	<input type="hidden" name="bizStatus" id="bizStatus" value="" />
	<input type="hidden" name="regBeginTime" id="regBeginTime" value="" />
	<input type="hidden" name="regEndTime" id="regEndTime" value="" />
	<input type="hidden" name="visitBeginTime" id="visitBeginTime" value="" />
	<input type="hidden" name="visitEndTime" id="visitEndTime" value="" />
</form>
	<input type="hidden" name="maxPage" id="maxPage" value="" />

</html>