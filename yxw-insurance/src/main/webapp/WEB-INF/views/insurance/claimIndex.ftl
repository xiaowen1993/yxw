<!DOCTYPE html>
<html>
<head>
  	<#include "/common/common.ftl">
    <title>理赔申请列表</title>
</head>
<body>
<div id="body">
<div class="padding-all-14">
        <div class="lipei-list">
        	<#if orders?? && (orders?size > 0) >
 				<#list orders as order>
 						<#if (order.claimStatus=="1")>
 						<a href="${basePath}api/account?id=${order.id}">
 						<#else>
                        <a href="${basePath}api/claimDetail?id=${order.id}">
                        </#if>
                                <div class="info">
                                        <div>客户姓名：${order.patientName}</div>
				                        <div>出险原因：<#if (order.accidentCause=="1")>疾病<#elseif (order.accidentCause=="2")>意外</#if>
				                        </div>
				                        <div>保险公司：${order.insurer}</div>
                                </div>
                                <#if (order.claimStatus=="1")>
		                   		 <div class="r">申请中<i class="arrow"></i> <div class="edit">请填写理赔资料</div></div>
				                <#elseif (order.claimStatus=="2")>
				                   <div class="r">已申请<i class="arrow"></i></div>
				                 <#else>
				                   <div class="r">无法在线理赔<i class="arrow"></i></div>
				                 </#if>
                              
                        </a>
 				</#list>
 				<#else>
 				<div class="nodata">
				  	    <img src="${basePath}yxw.app/images/lipei/icon-nodata2.png">
						<div>您还没有理赔申请单……</div>
				</div>
 				</#if>
	 			
        	
        </div>
        <a href="${basePath}api/selectUser?openId=${openId}&appCode=${appCode}&areaCode=${areaCode}" class="btn btn-ok btn-block">查看就诊订单</a>
</div>

<#include "/common/footer.ftl">
</body>
</html>