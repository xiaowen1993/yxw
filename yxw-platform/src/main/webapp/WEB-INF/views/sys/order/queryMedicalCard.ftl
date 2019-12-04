<html>
<head>
	<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/order/sys.order.js"></script>
    <title>订单管理</title>
</head>
<body>
	<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title"><a href="${basePath}order/orderList?hospitalId=${hospitalId}">订单管理</a>&nbsp;&nbsp;绑卡管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box orderBox">
                <div class="space10"></div>
                <div class="widget-content">
            	<form class="form-horizontal" method="post" action="${basePath}order/queryMedicalCard" accept-charset="utf-8">
        			<input type="hidden" name="hospitalId" value="${hospitalId}" />
            		<div class="row-fluid">
                        <div class="control-group">
                            <label class="control-label">姓名</label>
                            <div class="controls">
                            	<input type="text" name="name"  value="${name}" class="span10 input39" /> 
                            </div>
                        </div>
						<div class="control-group">
							<label class="control-label">卡号</label>
							<div class="controls">
                          		<input type="text" name="cardNo"  value="${cardNo}" class="span10 input39" /> 
							</div>
						</div>
						<div class="control-group">
                            <label class="control-label">绑卡证件号</label>
                            <div class="controls">
                             	<input type="text" name="idNo"  value="${idNo}" class="span10 input39" /> 
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">手机号</label>
                            <div class="controls">
                             	<input type="text" name="mobile"  value="${mobile}" class="span10 input39" /> 
                            </div>
                        </div>
					</div>
                    <div class="space20"></div>
                    <div class="btn-box">
                      <button class="btn btn btn-save w330">查询</button>
                        <div class="space20"></div>
                    </div>
				</form>
             	<div class="space10"></div>
                <div class="row-fluid">
                	<div class="orderTable">
    					<table class="table table-bordered table-textCenter table-striped table-hover" style="width: 1480px;">
                    		<thead>
                        		<tr>
                                	<th width="50">序号</th>
	                                <th width="100">卡类型</th>
	                                <th width="100">卡号</th>
	                                <th width="50">证件类型</th>
	                                <th width="100">证件号码</th>
	                                <th width="50">患者姓名</th>
	                                <th width="50">患者性别</th>
	                                <th width="50">患者手机</th>
	                                <th width="50">出生日期</th>
	                                <th width="150">地址</th>
	                                <th width="50">绑卡状态</th>
	                                <th width="50">操作</th>
                            	</tr>
                        	</thead>
        					<tbody>
        						<#if medicalCards?exists>
                                	<#list medicalCards as mc>
	                                	<tr onclick="clickHit(this)">
		                                    <td>${mc_index + 1} </td>
		                                    <td>
		                                    	<#switch mc.cardType>
		                                    		<#case 1>诊疗卡<#break>
		                                    		<#case 2>社保卡<#break>
		                                    		<#case 3>医保卡<#break>
		                                    		<#case 4>健康卡<#break>
		                                    		<#case 5>身份证<#break>
		                                    		<#case 6>市民卡<#break>
		                                    		<#case 7>患者唯一标识<#break>
		                                    		<#case 8>临时诊疗卡<#break>
		                                    		<#case 9>医疗证<#break>
		                                    		<#case 10>银联卡<#break>
		                                    		<#default>
		                                    	</#switch>
		                                    </td>
		                                   	<td>${mc.cardNo}</td>
		                                   	<td>
		                                   		<#switch mc.idType>
		                                   			<#case 1>二代身份证<#break>
		                                   			<#case 2>港澳居民身份证<#break>
		                                   			<#case 3>台湾居民身份证<#break>
		                                   			<#case 4>护照<#break>
		                                   			<#case 5>士官证<#break>
		                                   			<#default>
		                                   		</#switch>
		                                   	</td>
		                                   	<td>${mc.idNo}</td>
		                                   	<td>${mc.name}</td>
		                                   	<td>
		                                   		<#if mc.sex == 1>男<#else>女</#if>
		                                   	</td>
		                                   	<td>${mc.mobile}</td>
		                                   	<td>${mc.birth}</td>
		                                   	<td>${mc.address}</td>
		                                   	<td><#if mc.state == 1>已绑定<#else>已解绑</#if></td>
		                                    <td>
		                                    	<#if mc.state == 1>
		                                         	<a href="javascript:unbindMedicalCard('${mc.id}','${mc.openId}','${mc.hospitalCode}','${mc.branchCode}');" >解绑</a>
		                                        </#if>
		                                    </td>
		                                </tr>
									</#list>
								<#else>
									<#if check?exists>
										<td colspan="10">未检索到绑卡数据</td>
									<#else>
										<td colspan="10">暂无绑卡数据，请输入条件查询</td>
									</#if>
								</#if>
        					</tbody>
        				</table>
					</div>
                </div>
            </div>
        </div>
	</div>
</div>
<form id="unbindMedicalCardForm" method="post" accept-charset="utf-8">
	<input type="hidden" id="id" name="id" />
	<input type="hidden" id="openId" name="openId" />
	<input type="hidden" id="hospitalCode" name="hospitalCode" />
	<input type="hidden" id="branchCode" name="branchCode" />
</form>
<#include "./sys/common/footer.ftl">
<script type="text/javascript">
	var basePath = '${basePath}';
	/*选中 变色*/
	function clickHit(obj){
		var bl = jQuery(obj).attr("class");
		jQuery(obj).parent().find("tr").removeClass("hit-class");
		if(bl){
			jQuery(obj).removeClass('hit-class');
		}else{
			jQuery(obj).addClass('hit-class');
		}
	}
	
	function unbindMedicalCard(id, openId, hospitalCode, branchCode){
		jQuery("#id").val(id);
		jQuery("#openId").val(openId);
		jQuery("#hospitalCode").val(hospitalCode);
		jQuery("#branchCode").val(branchCode);
		var url = basePath + "order/unbindMedicalCard";
		var datas = $("#unbindMedicalCardForm").serializeArray();
		$.ajax({  
			type : 'POST',  
		    url : url,  
		    data : datas,  
		    dataType : 'json',  
		    timeout:120000,
		    success : function(data) { 
		    	if(data.isSuccess == "success"){
		    		alert("解绑成功.");
		    		window.location.reload();
		    	}else{
		    		alert("解绑失败.");
		    	}
		    }
		}); 
	}
</script>
</body>
</html>

