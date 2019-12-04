<html>
<head>
	<#include "./common/common.ftl">
	<script type="text/javascript" src="${basePath}js/echarts.min.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/datePlugin.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/biz/stats/mainStats.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/hospitalList.js"></script>
	<script type="text/javascript" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${basePath}js/bootstrap-paginator.js"></script>
    <title>整体统计</title>
</head>
<style>
	.tongjiTab.first span {
		width: 12.5%;
	}
</style>
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
        <div class="widget-title"><h3 class="title">整体统计</h3></div>
    </div>

    <div class="container-fluid">
		<div class="space10"></div>
			<div class="row-fluid">
                <div class="widget-box orderBox">
            		<div class="space10"></div>
                    <div class="widget-content">
                        <ul class="typeTab" id="kpiTabs">
                            <li class="active" data-type="0">昨日关键指标</li>
                            <li data-type="1">上周关键指标</li>
                            <li data-type="2">上个月关键指标</li>
                            <li data-type="3">上个季度关键指标</li>
                        </ul>
                        <div class="typeContent">
                        	<table class="table table-bordered table-textCenter table-striped table-hover">
                            	<thead>
                           	 		<tr>
		                                <th>总缴费</th>
		                                <th>挂号总缴费</th>
		                                <th>门诊总缴费</th>
		                                <th>住院总缴费</th>
		                                <th>挂号总退费</th>
		                                <th>门诊总退费</th>
		                                <th>住院总退费</th>
                            		</tr>
                            	</thead>
	                            <tbody>
		                            <tr id="kpiDatas">
		                                <td colspan="7">毛都查不出来...</td>
		                            </tr>
	                            </tbody>
                        </table>
                        </div>
                        <div class="space40"></div>

                        <div class="row-fluid">
                            <div class="tongjiTab first" id="fieldTab" data-field="">
                            	<span>关键指标详解：</span>
                                <span class="tag_a active" data-field="netIncome">总收入</span>
                                <span class="tag_a" data-field="regPaidAmount">挂号总缴费</span>
                                <span class="tag_a" data-field="clinicPaidAmount">门诊总缴费</span>
                                <span class="tag_a" data-field="depositPaidAmount">住院总缴费</span>
                                <span class="tag_a" data-field="regRefundAmount">挂号总退费</span>
                                <span class="tag_a" data-field="clinicRefundAmount">门诊总退费</span>
                                <span class="tag_a" data-field="depositRefundAmount">住院总退费</span>
                            </div>
                            <div class="tongjiTab" style="margin-top: -1px" id="dateTab" data-begin="" data-end="">
                                <span>时间：</span>
                                <span class="g-time active" data-type="7">7天</span>
                                <span class="g-time" data-type="14">14天</span>
                                <span class="g-time" data-type="30">30天</span>
                				<span class="g-time" style="width: 33.33%;padding: 0;padding-top: 10px;">
                       				<input type="text" placeholder="" class="span5 input-small" name="customBegin" id="customBegin"  onclick="WdatePicker()"> -
                       				<input type="text" placeholder="" class="span5 input-small" name="customEnd" id="customEnd" onclick="WdatePicker()">
                    				<div class="btn btn-small" style="margin-top: -10px" id="customDateBtn">确定</div>
                				</span>
                            </div>
                        </div>
                        <div class="space10"></div>

                        <div id="mainChart" style="height: 400px; -webkit-tap-highlight-color: transparent; -webkit-user-select: none; position: relative; background-color: transparent;" _echarts_instance_="ec_1465890460498"><div style="position: relative; overflow: hidden; width: 1476px; height: 400px; cursor: default;"><canvas width="1476" height="400" data-zr-dom-id="zr_0" style="position: absolute; left: 0px; top: 0px; width: 1476px; height: 400px; -webkit-user-select: none; -webkit-tap-highlight-color: rgba(0, 0, 0, 0);"></canvas></div><div style="position: absolute; display: none; border: 0px solid rgb(51, 51, 51); white-space: nowrap; transition: left 0.4s cubic-bezier(0.23, 1, 0.32, 1), top 0.4s cubic-bezier(0.23, 1, 0.32, 1); border-radius: 4px; color: rgb(255, 255, 255); font-style: normal; font-variant: normal; font-weight: normal; font-stretch: normal; font-size: 14px; font-family: 'Microsoft YaHei'; line-height: 21px; padding: 5px; left: 696.924px; top: 147px; background-color: rgba(50, 50, 50, 0.701961);"></div>
              		</div>
                    <table class="table table-bordered table-textCenter table-striped table-hover">
                        <thead>
                        <tr>
                            <th>时间</th>
                            <th>总缴费</th>
                            <th>挂号总缴费</th>
                            <th>门诊总缴费</th>
                            <th>住院总缴费</th>
                            <th>挂号总退费</th>
                            <th>门诊总退费</th>
                            <th>住院总退费</th>
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
</body>

<form id="voForm" method="post">
	<input type="hidden" name="hospitalCode" id="hospitalCode" value="${commonVo.hospitalCode}" />
	<input type="hidden" name="platform" id="platform" value="" />
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