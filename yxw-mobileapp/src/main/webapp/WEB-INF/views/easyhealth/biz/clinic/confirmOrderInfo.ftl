<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>确认订单信息</title>
</head>
<body>
	<div id="body">
	    <div id="guoHao">
	        <div class="box-list">
	            <ul class="yx-list">
	                <li class="flex">
	                    <div class="flexItem w120">医院名称</div>
	                    <div class="flexItem color666 textRight">${record.hospitalName}</div>
	                </li>
	                <li class="flex">
	                    <div class="flexItem w120">业务名称</div>
	                    <div class="flexItem color666 textRight">门诊缴费</div>
	                </li>
	                <li class="flex">
	                    <div class="flexItem w120">患者姓名</div>
	                    <#--
	                    <div class="flexItem color666 textRight">${record.patientName}</div>
	                    -->
	                    <div class="flexItem color666 textRight">${record.encryptedPatientName}</div>
	                </li>
	                <li class="flex">
	                    <div class="label" style="width: 3em">卡号</div>
	                    <div class="flexItem color666 textRight">${record.cardNo}</div>
	                </li>
	                <#if record.medicareType == "自费">
	                <li class="flex">
	                    <div class="flexItem w120">付款金额</div>
	                    <div class="flexItem color666 textRight">
	                        <span class="price">${(record.payFee?number / 100)?string('0.00')}</span> 元
	                    </div>
	                </li>
	                <#else>
	                <li class="flex">
	                    <div class="flexItem w120">总金额</div>
	                    <div class="flexItem color666 textRight"><span class="yellow">${(record.totalFee?number / 100)?string('0.00')}</span>元</div>
	                </li>
	            <#-- <li class="flex">
	                    <div class="flexItem w120">医保报销金额</div>
	                    <div class="flexItem color666 textRight"><span class="yellow">${(record.medicareFee?number / 100)?string('0.00')}</span>元</div>
	                </li>
	                 -->
	                <li class="flex">
	                    <div class="flexItem w120">实付金额</div>
	                    <div class="flexItem color666 textRight"><span class="yellow fontSize140">${(record.payFee?number / 100)?string('0.00')}</span> 元</div>
	                </li>
	                </#if>
	            </ul>
	        </div>
	    </div>

        <div class="section box-tips icontips">
			<#if record.medicareType != '自费'>
				<i class="iconfont">&#xe60d;</i>${rule.supportHealthPaymentsTip}
			<#else>
				<i class="iconfont">&#xe60d;</i>${rule.notSupportHealthPaymentsTip}
			</#if>
        </div>
	</div>
	
	<script>
		//支付信息页面和支付页面不在统一域名，iframe 没法做到自适应
		//如果在一级域名相同，可以把 document.domain 都设置成一级域名，就可以骗过浏览器，从而达到 iframe 自适应的效果
		if ("${basePath}".indexOf("yx129.cn") != -1) {
			document.domain = "yx129.cn";
		}
	</script>
</body>
</html>