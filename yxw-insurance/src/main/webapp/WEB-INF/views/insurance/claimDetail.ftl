<!DOCTYPE html>
<html>
<head>
  	<#include "/common/common.ftl">
    <title>申请详情</title>
</head>
<body>
<div id="body">
	<div class="padding-all-14">
        <ul class="lipei-des-list">
                <li>
                        <span class="label">客户姓名</span> <span>${order.patientName}</span>
                </li>
                <li>
                        <span class="label">出险原因</span> <span>
                        <#if (order.accidentCause=="1")>
                         疾病
                        <#elseif (order.accidentCause=="2")>
                         意外
                        </#if>
                       </span>
                </li>
                <li>
                        <span class="label">保险公司</span> <span>${order.insurer}</span>
                </li>
                <li>
                        <span class="label">通迅地址</span> <span>${order.address}</span>
                </li>
        </ul>
        <div style="height: 20px"></div>

        <div class="lipei-card-status">
             	<#if (order.claimStatus=="1")>
             	 <div class="status">理赔中</div>
                 <div class="des">尊敬的客户，您的理赔申请，已经提交给保险公司，正在处理中，请耐心等候！如有疑问请联系保险公司，客服电话：95519</div>
               <#elseif (order.claimStatus=="2")>
                <div class="status">申请成功</div>
                <div class="des">尊敬的客户，您的理赔申请，已经提交给中国人寿！如有疑问或希望查询理赔进度请联系中国人寿，客服电话：95519</div>
               <#elseif (order.claimStatus=="3")>
                <div class="status">无法在线理赔</div>
                <div class="des">尊敬的客户，我们抱歉地通知，您无法在线申请理赔，请您：亲临中国人寿柜面，或致电95519，或咨询您的保单销售人员。</div>
                
               </#if>
               <div class="footer">
                        <span>请刷新查看最新理赔进度</span>
                </div>
        </div>

        <div style="height: 20px"></div>
        <div class="lipei-card-status">
                <div class="status" style="font-size: 1em; color: #333">理赔详情</div>
                <div class="des" style="padding-bottom: .1em">
                        <div class="lipei-list">

                                	<a href="${basePath}api/toPaidDetail?openId=${order.openId}&orderNo=${order.claimProjectOrderNo}&hospitalCode=${order.hospitalCode}">
                                        <div class="info">
                                                <div>项目：${order.claimProject}</div>
                                                <div>金额：<span class="cost" id="totalAmout">${((order.claimFee?number) / 100)?string('0.00')}</span> 元</div>
                                        </div>
                                        <div class="r"><i class="arrow"></i></div>
                                </a>
                        </div>
                </div>
                <div class="footer" style="text-align: left">
                        <span id="clinicTime">${order.claimTime}</span>
                </div>
        </div>


</div>
<script type="text/javascript">

	/* $(function(){
	$.ajax({
		type: "POST",
		url: "${basePath}api/getMzFeeData?openId="+encodeURIComponent("${order.openId}")+"&mzFeeId=${order.claimOrderNo}",
		cache: false, 
		dataType: "json", 
		timeout: 600000,
		success: function(data) {
				if(data!=null){
					$("#clinicTime").text(data.clinicTime);
					$("#totalAmout").text(returnFloat(data.totalAmout/100));
				}
			}
		})
	});
	
	function returnFloat(value){
		 var value=Math.round(parseFloat(value)*100)/100;
		 var xsd=value.toString().split(".");
		 if(xsd.length==1){
		 value=value.toString()+".00";
			 return value;
		 }
		 if(xsd.length>1){
			 if(xsd[1].length<2){
			 value=value.toString()+"0";
		 }
			 return value;
		 }
	} */

</script>
<#include "/common/footer.ftl">
</body>
</html>