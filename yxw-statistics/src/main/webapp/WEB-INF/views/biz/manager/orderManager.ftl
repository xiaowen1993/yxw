<html>
<head>
	<#include "./common/common.ftl">
	<script type="text/javascript" src="${basePath}js/echarts.min.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/datePlugin.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/hospitalList.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/biz/manager/orderManager.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/download.js"></script>
	<link rel="stylesheet" href="${basePath}js/My97DatePicker/skin/WdatePicker.css"/>
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
    	<div class="widget-title"><h3 class="title">订单管理</h3></div>
    </div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="space10"></div>
	        <div class="row-fluid">
	            <div class="widget-box orderBox">
	                <div class="form-horizontal">
	                    <div class="row-fluid">
	                        <div class="control-group">
	                            <label class="control-label">订单渠道</label>
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
	                            <label class="control-label">业务类型</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="全部订单" readOnly id="pBizType" data-value="-1">
									    <ul class="ui-select-list">
									    	<li data-value="-1">全部订单</li>
									    	<li data-value="1">挂号订单</li>
									    	<li data-value="2">门诊订单</li>
									    	<li data-value="3">押金订单</li>
									    </ul>
									</div>
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label">业务状态</label>
	                            <div class="controls">
	                                <div class="ui-select-warp">
										<input type="text" class="ui-select-input" value="全部" readOnly id="pBizStatus" data-value="-1">
									    <ul class="ui-select-list">
									    	<!-- 默认 -->
								   			<li data-value="-1" data-type="-1">全部</li>
											<!-- 挂号 -->
								   			<li data-value="-1" data-type="1">全部</li>
								   			<li data-value="0" data-type="1">预约中</li>
											<li data-value="1" data-type="1">已预约</li>
											<li data-value="2" data-type="1">已取消-用户取消</li>
											<li data-value="3" data-type="1">已取消-支付超过规定时长</li>
											<li data-value="4" data-type="1">已取消-停诊取消</li>
											<li data-value="5" data-type="1">预约异常(his锁号异常)</li>
											<li data-value="6" data-type="1">第3方支付异常</li>
											<li data-value="7" data-type="1">第3方支付成功后 his确认异常</li>
											<li data-value="8" data-type="1">取消挂号异常</li>
											<li data-value="9" data-type="1">第3方退费前 调用his退费确认 出现异常</li>
											<li data-value="10" data-type="1">第3方退费异常</li>
											<li data-value="11" data-type="1">第3方退费成功后提交his确认异常</li>
											<li data-value="12" data-type="1">挂号失败</li>
											<li data-value="13" data-type="1">异常导致的挂号取消 后续轮询处理 处理成功的最终状态</li>
											<li data-value="14" data-type="1">业务异常 需要人工处理</li>
											<li data-value="15" data-type="1">业务异常 需要到医院窗口处理</li>
											<li data-value="16" data-type="1">停诊取消异常 未支付</li>
											<li data-value="17" data-type="1">hia预约退费确认异常 停诊</li>
											<li data-value="18" data-type="1">第3方退费异常 停诊</li>
											<li data-value="19" data-type="1">第3方退费成功后提交his确认异常 停诊</li>
											<li data-value="20" data-type="1">窗口退费成功</li>
											<li data-value="21" data-type="1">窗口退费异常</li>
											<li data-value="22" data-type="1">用户取消预约中</li>
											<li data-value="23" data-type="1">后台退费:成功</li>
											<li data-value="24" data-type="1">后台退费:失败</li>
								   			<!-- 门诊、押金 -->
								   			<li data-value="-1" data-type="2">全部</li>
								   			<li data-value="0" data-type="2">待缴费</li>
											<li data-value="1" data-type="2">已缴费</li>
											<li data-value="2" data-type="2">第三方缴费失败</li>
											<li data-value="3" data-type="2">写入His异常</li>
											<li data-value="4" data-type="2">缴费关闭</li>
											<li data-value="5" data-type="2">退费异常</li>
											<li data-value="6" data-type="2">写入His失败</li>
											<li data-value="7" data-type="2">业务异常需人工处理</li>
											<li data-value="8" data-type="2">业务异常需窗口处理</li>
											<li data-value="20" data-type="2">窗口退费成功</li>
											<li data-value="21" data-type="2">窗口退费异常</li>
											<li data-value="30" data-type="2">部分退费</li>
											<li data-value="31" data-type="2">部分退费成功</li>
											<li data-value="32" data-type="2">部分退费失败</li>
											<li data-value="33" data-type="2">部分退费异常</li>
											<li data-value="34" data-type="2">后台退费成功</li>
											<li data-value="35" data-type="2">后台退费失败</li>
								   		</ul>
									</div>
	                            </div>
	                        </div>
	                        <div class="control-group">
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
	                            <label class="control-label red">患者姓名</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pName">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label red">患者卡号</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pCardNo">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label red">患者手机</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pMobile">
	                            </div>
	                        </div>
	                        <div class="control-group">
	                            <label class="control-label red">平台订单号</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pOrderNo">
	                            </div>
	                        </div>
	                    </div>
	                    <div class="row-fluid">
	                    	<div class="control-group">
	                            <label class="control-label red">HIS订单号</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span10 input39" id="pHisOrderNo">
	                            </div>
	                        </div>
	                        <div class="control-group" style="width: 40%;">
	                            <label class="control-label">订单时间</label>
	                            <div class="controls">
	                                <input type="text" placeholder="" class="span5 input39" onclick="WdatePicker()" id="pBeginTime"> -
	                                <input type="text" placeholder="" class="span5 input39" onclick="WdatePicker()" id="pEndTime">
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
	                        <button class="btn btn-ok" id="refund">人工退费</button>
	                        <button class="btn btn-ok" id="updateStatus">修改状态</button>
	                        <button class="btn btn-ok" id="viewBizLog">查看业务日志</button>
	                        <button class="btn btn-ok" id="thirdPayStatus">第三方支付状态</button>
	                        <button class="btn btn-ok" id="hisOrderStatus">HIS订单状态</button>
	                    </div>
	                    <div class="space20"></div>
	                    <div class="box optBox" id="detailTabs" style="display: none;">
	                    
	                    </div>
	                    <div class="space20"></div>
	                    <div class="row-fluid" id="detailPages">
	                    	
	                	</div>
	                </div>
	            </div>
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
	<input type="hidden" name="beginTime" id="beginTime" value="" />
	<input type="hidden" name="endTime" id="endTime" value="" />
	<input type="hidden" name="pageSize" id="pageSize" value="" />
	<input type="hidden" name="pageIndex" id="pageIndex" value="" />
	<input type="hidden" name="bizType" id="bizType" value="" />
	<input type="hidden" name="bizStatus" id="bizStatus" value="" />
	<input type="hidden" name="payStatus" id="payStatus" value="" />
	<input type="hidden" name="patientName" id="patientName" value="" />
	<input type="hidden" name="cardNo" id="cardNo" value="" />
	<input type="hidden" name="patientMobile" id="patientMobile" value="" />
	<input type="hidden" name="orderNo" id="orderNo" value="" />
	<input type="hidden" name="hisOrderNo" id="hisOrderNo" value="" />
</form>
	<input type="hidden" name="maxPage" id="maxPage" value="" />

</html>