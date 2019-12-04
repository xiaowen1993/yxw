<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>挂号详情</title>
</head>
<body>
<div id="body">
    <div id="guoHao">
    	    <!--跨平台取号 (未缴费)-->
		    <#if record.regStatus == 1 && record.payStatus == 1 && record.regAgency?exists>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                        跨平台取号,支付后不可退费.
		    </div>
		     <!--跨平台取号 (未缴费) end -->
		    
		    <!--跨平台取号 (已缴费)-->
		    <#elseif record.regStatus == 1 && record.payStatus == 2 && record.regAgency?exists>
		     <!--跨平台取号 (已缴费) end-->
		    
		    <!-- 当班挂号 ,不支持退费 start -->
			<#elseif isSupportOndutyRefund == 0 && record.payStatus == 2 && record.regType == 1>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		            <#if ruleConfig.regSuccessOnDutyTip ??>
		                ${ruleConfig.regSuccessOnDutyTip}
		            </#if>
		    </div>
		    <!-- 当班挂号 ,不支持退费 end -->
		    
		    <!-- 预约挂号 ,不支持退费 -->
		    <#elseif isSupportAppointmentRefund == 0 && record.payStatus == 2 && record.regType == 1>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		            <#if ruleConfig.regSuccessHadPayTip ??>
		                ${ruleConfig.regSuccessHadPayTip}
		            </#if>
		    </div>
		    <!-- 预约挂号 ,不支持退费end -->
		    
		    
		    <!--挂号成功（已缴费）-->
		    <#elseif record.regStatus == 1 && record.payStatus == 2>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		            <#if record.regType == 1>
		                ${ruleConfig.regSuccessHadPayTip}
		            <#else>
		                ${ruleConfig.regSuccessOnDutyTip}
		            </#if>
		    </div>
		    <!--挂号成功（已缴费） end-->
		
		    <!--挂号成功 （未缴费）-->
		    <#elseif record.regStatus == 1 && record.payStatus == 1 && record.onlinePaymentType == 3>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		        <#if ruleConfig.regSuccessNoPayTip ??>
		            ${ruleConfig.regSuccessNoPayTip}
		        <#else>
		                                      请在就诊时间段开始前30分钟在医院窗口或在手机上完成支付,否则号源将会作废,并视为爽约.
		        </#if>
		    </div>
		    <!--挂号成功 （未缴费） end-->
		
		    <!--挂号成功 (暂不支付) -->
		    <#elseif record.regStatus == 1 && record.payStatus == 1 && record.onlinePaymentType == 2>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		            <#if ruleConfig.regSuccessNoPayTip ??>
		                ${ruleConfig.regSuccessNoPayTip}
		            <#else>
		                                          请在就诊时间段开始前30分钟在医院窗口或在手机上完成支付,否则号源将会作废,并视为爽约.
		            </#if>
		    </div>
		    <!--挂号成功(暂不支付)  end-->
		
		    <!--挂号失败(已退费) -->
		    <#elseif record.regStatus == 10 && record.payStatus == 3>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                                因网络异常,该挂号失败,费用已退回,请注意查收,如需挂号请重新选择.
		    </div>
		    <!--挂号失败（已退费） end-->
		    
		    <!--挂号支付超时 -->
		    <#elseif record.regStatus == 3 && record.payStatus == 1>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		            <#if (ruleConfig.regPayTimeOutTip) ??>
		                ${ruleConfig.regPayTimeOutTip}
		            <#else>
		                                  该挂号在规定时间内没支付,已被取消.
		            </#if>
		    </div>
		    <!--挂号超时 end-->
		
		    <!--取消挂号(停诊退费） -->
		    <#elseif record.regStatus == 4>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                                    因医生临时停诊,该挂号已被取消,费用已被退回,请选择其它医生挂号.
		    </div>
		    <!--取消挂号(停诊退费）end -->
		    
		    <!--取消挂号(已退费） -->
		    <#elseif record.regStatus == 2 && record.payStatus == 3>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                            该挂号已被取消,费用已被退回,请注意查收.
		    </div>
		    <!--取消挂号(已退费）end -->
		
		    <!--取消挂号 -->
		    <#elseif record.regStatus == 2 && record.payStatus == 1>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                            该挂号已被取消,如需挂号,请重新预约.
		    </div>
		    <!--取消挂号end -->
		
		    <!--挂号（锁号中） -->
		    <#elseif record.regStatus == 0 && record.payStatus == 1>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		        <input type="hidden" id="leftSecond" value="${leftSecond}">
		                            温馨提示：请在<span id="leftSecondSpan" style="color: red;"></span>分钟内完成支付，逾期号源将会作废，需要重新挂号。
		    </div>
		    <!--挂号（锁号中）end -->
		    
		    <#elseif record.regStatus == 6 || record.regStatus == 9 >
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                            因网络原因支付异常,系统核实中..
		    </div>
		
		    <#elseif record.regStatus == 7 >
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                            因网络原因挂号异常,系统处理中..
		    </div>
		    
		    <#elseif record.regStatus == 8>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                            因网络原因挂号异常,系统处理中..
		    </div>
		    
		    <#elseif record.regStatus == 11>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                           业务异常,系统无法自动处理,请联系客服人工处理,对您造成不便,深感抱歉.
		    </div>
		    
		    <#elseif record.regStatus == 12>
		    <div class="guahao-tips">
		        <i class="icon-triangle-dark"></i>
		        <i class="icons-square"></i>
		                            业务异常,系统无法自动处理,请到医院窗口处理,对您造成不便,深感抱歉.
		    </div>
		    
		    <#else>
		     
		    </#if>


    	
    	<div class="space15"></div>
    	
        <ul class="yx-list">
        	<li class="flex">
                <div class="flexItem w120">医院</div>
                <div class="color666 flexItem textRight">${record.hospitalName}</div>
            </li>
        	<li class="flex">
                <div class="flexItem w120">就诊地点</div>
                <div class="color666 flexItem textRight">${record.visitLocation}</div>
            </li>
        
            <li class="flex">
                <div class="flexItem w120">科室</div>
                <div class="color666 flexItem textRight">${record.deptName}</div>
            </li>
            <li class="flex">
                <div class="flexItem w120">医生</div>
                <div class="color666 flexItem textRight">${record.doctorName} 
                <#if !record.doctorTitle ??>
                (${record.doctorTitle})
                </#if>
                </div>
            </li>
            
            <li class="flex">
                <div class="flexItem w120">排队号</div>
                <div class="color666 flexItem textRight">
                    <#if record.serialNum?length gt 5>
                    ${record.serialNum}
                    <#else>
                    <span class="skinColor fontSize140 strong" style="line-height: 1">${record.serialNum}</span> 号
                    </#if>
                </div>
            </li>
            
            <li class="flex">
                <div class="flexItem w120">就诊时间</div>
                <div class="color666 flexItem textRight" style="-webkit-box-flex: 2;flex: 2;">${record.scheduleDate?string('yyyy-MM-dd')}  
                <#if showRegPeriod == 0 && record.regType == 2>	
                <#else>
                	<#if record.beginTime?exists && record.endTime?exists >
                        ${record.beginTime?string('HH:mm')}-${record.endTime?string('HH:mm')}
                    <#elseif record.beginTime?exists && !record.endTime?exists>
                        ${record.beginTime?string('HH:mm')}
                    <#else>
                        <#if record.timeFlag == '1'>
                                                                        上午
                        </#if>
                        <#if record.timeFlag == '2'>
                                                                        下午
                        </#if>
                        <#if record.timeFlag == '3'>
                                                                        晚上
                        </#if>
                        <#if record.timeFlag == '4'>
                                                                        全天
                        </#if>
                    </#if>
                </#if>
               </div>
            </li>
        </ul>
        
        <div class="space15"></div>
            
        <ul class="yx-list">
            <li class="flex">
            	<div class="flexItem w120">就诊人</div>
            	<#--<div class="color666 flexItem textRight">${record.patientName}(${record.cardNo})</div>-->
                <div class="color666 flexItem textRight">${record.encryptedPatientName}(${record.cardNo})</div>
            </li>
            <#if record.onlinePaymentType != 2>
	            <li class="flex">
	                <div class="flexItem w120">
	                	<#if regFeeAlias?exists && regFeeAlias != ''>
                    		${regFeeAlias}
                    	<#else>
                    		挂号费
                    	</#if>
	                </div>
	                <div class="color666 flexItem textRight">
	                    <span class="price">
                        <#if (record.realRegFee + record.realTreatFee) gt 0 || (record.realRegFee + record.realTreatFee) == 0  >
                            ${((record.realRegFee + record.realTreatFee) / 100)?string("0.##")}
                        </#if>
                        </span> 元 
                        <span class="priceColor">
                            <#if     record.payStatus == 1 >
                                (未缴费)
                            <#elseif record.payStatus == 2>
                                (已缴费)
                            <#elseif record.payStatus == 3>
                                (已退费)
                            <#elseif record.payStatus == 4>
                                (处理中)
                            <#elseif record.payStatus == 5 >
                                (处理中)
                            <#else>
                                (网络异常,系统处理中)
                            </#if>
                        </span>
	                </div>
	            </li>
	       </#if>
        </ul>
    </div>
    
    
    <!--跨平台取号 (未缴费)-->
    <#if record.regStatus == 1 && record.payStatus == 1 && record.regAgency?exists>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" id="payBtn" onclick="payMentReg(this)">支付取号</div>
    </div>
     <!--跨平台取号 (未缴费) end -->
    
    <!--跨平台取号 (已缴费)-->
    <#elseif record.regStatus == 1 && record.payStatus == 2 && record.regAgency?exists>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</button>
    </div>
     <!--跨平台取号 (已缴费) end-->
    
    <!-- 当班挂号 ,不支持退费 start -->
	<#elseif isSupportOndutyRefund == 0 && record.payStatus == 2 && record.regStatus == 1 && record.regType == 2>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!-- 当班挂号 ,不支持退费 end -->
    
    <!-- 预约挂号 ,不支持退费 -->
    <#elseif isSupportAppointmentRefund == 0 && record.payStatus == 2  && record.regStatus == 1 && record.regType == 1>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!-- 预约挂号 ,不支持退费end -->
    
    
    <!--挂号成功（已缴费）-->
    <#elseif record.regStatus == 1 && record.payStatus == 2>
    <div class=" btn-w btn-inline">
        <div class="btn btn-cancel" id="refundBtn" onclick="refundConfirm(this)">取消挂号</div>
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--挂号成功（已缴费） end-->

    <!--挂号成功 （未缴费）-->
    <#elseif record.regStatus == 1 && record.payStatus == 1 && record.onlinePaymentType == 3>
    <div class=" btn-w btn-inline">
        <div class="btn btn-cancel" onclick="cancelRegister(this)">取消挂号</div>
        <div class="btn btn-ok" id="payBtn" onclick="payMentReg(this)">支付取号</div>
    </div>
    <!--挂号成功 （未缴费） end-->

    <!--挂号成功 (暂不支付) -->
    <#elseif record.regStatus == 1 && record.payStatus == 1 && record.onlinePaymentType == 2>
    <div class=" btn-w btn-inline">
        <div class="btn btn-cancel" onclick="cancelRegister(this)">取消挂号</div>
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--挂号成功(暂不支付)  end-->

    <!--挂号失败(已退费) -->
    <#elseif record.regStatus == 10 && record.payStatus == 3>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--挂号失败（已退费） end-->
    
    <!--挂号支付超时 -->
    <#elseif record.regStatus == 3 && record.payStatus == 1>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--挂号超时 end-->

    <!--取消挂号(停诊退费） -->
    <#elseif record.regStatus == 4>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--取消挂号(停诊退费）end -->
    
    <!--取消挂号(已退费） -->
    <#elseif record.regStatus == 2 && record.payStatus == 3>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--取消挂号(已退费）end -->

    <!--取消挂号 -->
    <#elseif record.regStatus == 2 && record.payStatus == 1>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    <!--取消挂号end -->

    <!--挂号（锁号中） -->
    <#elseif record.regStatus == 0 && record.payStatus == 1>
    <div class=" btn-w btn-inline">
        <div class="btn btn-cancel" onclick="cancelRegister(this)">取消挂号</div>
        <div class="btn btn-ok" id="payBtn" onclick="payMentReg(this)">支付取号</div>
    </div>
    <!--挂号（锁号中）end -->
    
    <#elseif record.regStatus == 6 || record.regStatus == 9 >
    	<!--<#if isExceptionRefundOrder==1>
    		<button type="button" class="btn btn-cancel btn-block" id="refundBtn" onclick="refundExecptionConfirm(this)">取消异常</button>
    	</#if>-->
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    
    
    <#elseif record.regStatus == 7 >
    	<!--<#if isExceptionRefundOrder==1>
    		<button type="button" class="btn btn-cancel btn-block" id="refundBtn" onclick="refundExecptionConfirm(this)">取消异常</button>
    	</#if>-->
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    
    <#elseif record.regStatus == 8>
    	<!--<#if isExceptionRefundOrder==1>
    		<button type="button" class="btn btn-cancel btn-block" id="refundBtn" onclick="refundExecptionConfirm(this)">取消异常</button>
    	</#if>-->
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>

    <#elseif record.regStatus == 11>
    	<!--<#if isExceptionRefundOrder==1>
    		<button type="button" class="btn btn-cancel btn-block" id="refundBtn" onclick="refundExecptionConfirm(this)">取消异常</button>
    	</#if>-->
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    
    <#elseif record.regStatus == 12>
    	<!--<#if isExceptionRefundOrder==1>
    		<button type="button" class="btn btn-cancel btn-block" id="refundBtn" onclick="refundExecptionConfirm(this)">取消异常</button>
    	</#if>-->
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    
    <#else>
    <div class=" btn-w btn-inline">
        <div class="btn btn-ok" onclick="goRegInfoList()">确定</div>
    </div>
    </#if>
</div>
<form id="paramsForm" method="post">
	<!-- 支付类型 -->
    <input type="hidden" id="tradeMode" name="tradeMode" value="${payParams.tradeMode}"/>
    <input type="hidden" id="appId" name="appId" value="${record.appId}" />
    <input type="hidden" id="appCode" name="appCode" value="${record.appCode}" />
    <input type="hidden" id="hospitalId" name="hospitalId" value="${record.hospitalId}" />
    <input type="hidden" id="hospitalCode" name="hospitalCode" value="${record.hospitalCode}" />
    <input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${record.branchHospitalId}" />
    <input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${record.branchHospitalCode}" />
    <input type="hidden" id="hospitalName" name="hospitalName" value="${record.hospitalName}" />
    <input type="hidden" id="openId" name="openId"  value="${record.openId}"/>
    <input type="hidden" id="patientName" name="patientName"  value="${record.patientName}"/>
    <input type="hidden" id="cardNo" name="cardNo"  value="${record.cardNo}"/>
    <input type="hidden" id="deptName" name="deptName"  value="${record.deptName}"/>
    <input type="hidden" id="doctorName" name="doctorName"  value="${record.doctorName}"/>
    <input type="hidden" id="doctorTitle" name="doctorTitle"  value="${record.doctorTitle}"/>
    <input type="hidden" id="realRegFee" name="realRegFee"  value="${record.realRegFee}"/>
    <input type="hidden" id="realTreatFee" name="realTreatFee"  value="${record.realTreatFee}"/>
    <input type="hidden" id="visitLocation" name="visitLocation"  value="${record.visitLocation}"/>
    <input type="hidden" id="scheduleDate" name="scheduleDate"  value="${record.scheduleDate?string('yyyy-MM-dd')}"/>
    <#if record.beginTime??>
    <input type="hidden" id="beginTime" name="beginTime"  value="${record.beginTime?string('HH:mm')}"/>
    </#if>
    <#if record.endTime??>
    <input type="hidden" id="endTime" name="endTime"  value="${record.endTime?string('HH:mm')}"/>
    </#if>
    <input type="hidden" id="serialNum" name="serialNum"  value="${record.serialNum}"/> 
    <input type="hidden" id="regStatus" name="regStatus"  value="${record.regStatus}"/>
    <input type="hidden" id="payStatus" name="payStatus"  value="${record.payStatus}"/>
    <input type="hidden" id="orderNo" name="orderNo"  value="${record.orderNo}"/>
    <input type="hidden" id="hisOrdNo" name="hisOrdNo"  value="${record.hisOrdNo}"/>
    <input type="hidden" id="regMode" name="regMode"  value="${record.regMode}"/>
    <input type="hidden" id="regType" name="regType"  value="${record.regType}"/>
    <input type="hidden" id="agtOrdNum" name="agtOrdNum" value="${record.agtOrdNum}" />
    <input type="hidden" id="recordTitle" name="recordTitle" value="${record.recordTitle}" />
    
    <input type="hidden" id="failMsg" name="failMsg" />
</form>


<#if record.payStatus == 1 && isCanPayMent>
<form id="payForm" method="post" action="">
    <input type="hidden" id="body" name="body" value=""/>
    <input type="hidden" id="code" name="code" value=""/> 
    <input type="hidden" id="infoUrl" name="infoUrl" value=""/>
    <input type="hidden" id="orderNo" name="orderNo" value=""/> 
    <input type="hidden" id="paySuccessPageUrl" name="paySuccessPageUrl" value=""/>
    <input type="hidden" id="viewType" name="viewType" value=""/>
    <input type="hidden" id="merchantUrl" name="merchantUrl" value=""/>
    <input type="hidden" id="timeout" name="timeout" value=""/> 
    <input type="hidden" id="totalFee" name="totalFee" value=""/>
    <input type="hidden" id="tradeMode" name="tradeMode" value=""/> 
    <input type="hidden" id="agtTimeout" name="agtTimeout" value=""/> 
    
    <input type="hidden" id="frameHeight" name="frameHeight" value=""/>
    
    <input type="hidden" id="componentOauth2" name="componentOauth2" value=""/>
</form>
</#if>

<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
var msgInfo = '${msgInfo}';
if(msgInfo != ""){
  var msgInfoBox = new $Y.confirm({
        content:'<div>' + msgInfo + '</div>',
        ok:{
            title: '确定',
            click:function(){ //参数可为空，没有默认方法,不会自动关闭窗体，可用  myBox.close()来关闭  
                msgInfoBox.close();
            }
        }
  }); 
}

var isCanPayMent = '${isCanPayMent}';
</script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/register/app.regRecordInfo.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/common/app.skipPages.js"></script>