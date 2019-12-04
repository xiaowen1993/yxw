<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>确认订单信息</title>
</head>
<body>
<div id="body">
    <div id="guoHao">
	    <#if isPreferential == "true">
	    <div class="section">
	        <div class="box-tips"> <i class="icon-warn"></i>
	           温馨提示：${regDiscountTip}
	        </div>
	    </div>
	    </#if>
    
        <div class="box-list">
            <ul class="yx-list flex-list">
                <li>
                    <div class="label">医院名称</div>
                    <div class="values color666">${record.hospitalName}</div>
                </li>
                <li>
                    <div class="label">业务名称</div>
                    <div class="values color666">挂号费</div>
                </li>
                <li>
                    <div class="label">科室</div>
                    <div class="values color666">${record.deptName}</div>
                </li>
                <li>
                    <div class="label">医生</div>
                    <div class="values color666">${record.doctorName}
                    <#if !record.doctorTitle ??>
                    (${record.doctorTitle})
                    </#if>
                    </div>
                </li>
                <li>
                    <div class="label">就诊时间</div>
                    
                    <div class="values color666">${record.scheduleDate?string('yyyy-MM-dd')} 
                    <#if record.beginTime?exists && record.endTime?exists >
                        ${record.beginTime?string('HH:mm')}-${record.endTime?string('HH:mm')}
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
                   </div>
                </li>
                <li>
                    <div class="label">患者姓名</div>
                    <div class="values color666">${record.encryptedPatientName}</div>
                    <#--<div class="values color666">${record.patientName}</div>-->
                </li>
                <li>
                    <div class="label" style="width: 3em">卡号</div>
                    <div class="values color666">${record.cardNo}</div>
                </li>
                <li>
                    <div class="label">付款金额</div>
                    <div class="values color666">
                        <span class="price">${(record.realRegFee + record.realTreatFee) / 100}</span> 元
                    </div>
                </li>
            </ul>
        </div>
    </div>

</div>
</body>
<script>
function doRefresh()
{
	$Y.loading.show('订单信息重载中...');
	setTimeout("$Y.loading.hide()",500);
}

function doGoBack()
{
    windowClose();
}

//支付信息页面和支付页面不在统一域名，iframe 没法做到自适应
//如果在一级域名相同，可以把 document.domain 都设置成一级域名，就可以骗过浏览器，从而达到 iframe 自适应的效果
if ("${basePath}".indexOf("yx129.cn") != -1) {
	document.domain = "yx129.cn";
}
</script>
</html>
