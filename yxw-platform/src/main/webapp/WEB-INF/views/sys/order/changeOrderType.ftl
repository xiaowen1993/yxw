		<div id="link-dialog">
			<div class="widget-content">
    			<div class="row-fluid">
		<form id="changeType" class="form-horizontal" method="post" action="${basePath}order/changeType" accept-charset="utf-8">
    	<div class="widget-content">
			<div class="row-fluid">
							<label class="control-label">业务状态</label>
							<div class="controls">
                          		<div class="my_select">
                          			<select id="bizStatus" name="bizStatus" class="span12">
	                    				<option value="" selected="selected">全部</option>
	                    				<#if orderType == 1>
		                    				<#if registerStuats?exists>
								                <#list registerStuats?keys as key>
								                	<#if bizStatus == key>
								                		<option value="${key}" <#if bizStatus == key>selected="selected"</#if>>${registerStuats[key]}</option>
								                	<#else>
								                		<option value="${key}" >${registerStuats[key]}</option>
								                	</#if>
								                </#list>
								            </#if>
                          				</#if>
                          				<#if orderType == 2>
		                    				<#if clinicStuats?exists>
								                <#list clinicStuats?keys as key>
								                	<#if bizStatus == key>
								                		<option value="${key}" <#if bizStatus == key>selected="selected"</#if>>${clinicStuats[key]}</option>
								                	<#else>
								                		<option value="${key}" >${clinicStuats[key]}</option>
								                	</#if>
								                </#list>
								            </#if>
                          				</#if>
                          				<#if orderType == 3>
                          				
                          				</#if>
									</select>
								</div>　
							</div>
							<div class="control-group">
							<label class="control-label">支付状态</label>
							<div class="controls">
                          		<div class="my_select">
                                	<select id="payStatus" name="payStatus" class="span12">
	                    				<option value="" selected="selected">全部</option>
								        <option value="1" <#if payStatus == 1>selected="selected"</#if>>未支付</option>
								        <option value="4" <#if payStatus == 4>selected="selected"</#if>>支付中</option>
								        <option value="2" <#if payStatus == 2>selected="selected"</#if>>已支付</option>
								        <option value="5" <#if payStatus == 5>selected="selected"</#if>>退费中</option>
								        <option value="3" <#if payStatus == 3>selected="selected"</#if>>已退费</option>
									</select>
								</div>　
							</div>
							</div>
				</div>
		</div>
        <input type="hidden" name="orderType" value="${orderType}" />
        <input type="hidden" name="orderNo" value="${orderNo}" />
        <input type="hidden" name="openId" value="${openId}" />
							<div class="controlsBtnBox center">
							    <a href="javascript:void(0);" onclick="submitForm();" class="btn-save" style="padding:10px 20px;">保存</a>
							    <div class="spaceW15"></div>
							    <!--<a href="javascript:void(0);" onclick="closeForm();" id="close1" class="btn-remove" style="padding:10px 20px;">取消</a>-->
							</div>
		</form>
		<script>
		function showMessageBox(data) {
			 $Y.tips(data,2000);
			};
		function submitForm(){
			$.ajax({
			type: "POST",
			url: 'changeType',
			data: $("#changeType").serialize(),
			cache: false, 
			dataType: "json", 
			timeout: 600000,
			error: function(data) {
				//$Y.loading.hide();
				showMessageBox(data.message);
			},
			success: function(data) {
				//$Y.loading.hide();
				console.log(data);
					if (data.message.refundIsSuccess == true) {
						
						setTimeout(function() {$('#orderStatisticsForm').submit();}, 1000);
	  	  				showMessageBox('状态修改成功!');
					} else {
						setTimeout(function() {$('#orderStatisticsForm').submit();}, 1000);
						showMessageBox('状态修改失败!');
					}
			}
			});
		}
		</script>
		</div>
	</div>
</div>