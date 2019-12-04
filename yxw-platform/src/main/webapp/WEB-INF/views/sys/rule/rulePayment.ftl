<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>支付方式配置</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
   <div id="content-header">
        <div class="widget-title">
            <h3 class="title">支付方式规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
        	<div class="space30"></div>
            <div class="row-fluid">
            	<div class="pull-left">
	                <ul class="tabs">
	                    <#list platforms as platform>
	                    	<#if platform.state == 1>
	                    		<li><a href="#tab${platform_index + 1}" class="<#if platform_index == 0>select</#if>" style="width: 150px;">${platform.platformName}</a></li>
	                    	</#if>
	                    </#list>
	                </ul>
	            </div>
            </div>
            <input type="hidden" id="hospitalId" name="hospitalId" value="${rulePayment.hospitalId}"/>
            <input type="hidden" id="id" name="id" value="${rulePayment.id}"/>
            <div class="space10"></div>
            <#list platforms as platform>
            	<#if platform.state == 1>
	            <div class="widget-box bangKa tab_content" id="tab${platform_index + 1}">
	                <div class="space10"></div>
	                <form class="form-horizontal evenBg" id="${platform.platformCode}">
	                    <input type="hidden" id="platformCode" name="platformCode" value="${platform.platformCode}"/>
	                    <div class="widget-content guaHao">
	                    	<#if platform.payModes?size &gt; 0>
	                    		<#assign tempPayMode=payModes['${platform.platformCode}']?split(',')>
		                    	<#list platform.payModes as payMode>
		                    	<div class="row-fluid">
		                    		<div class="control-group w235">
	                                    <label class="control-label">${payMode.name}</label>
	                                    <div class="controls payMode">
	                                        <label class="radio inline <#if tempPayMode[payMode_index] == 0>check</#if>"><input type="radio" name="${platform.platformCode}_${payMode_index}" value="0" <#if tempPayMode[payMode_index] == 0>checked="checked"</#if>/>不显示</label>
	                                        <label class="radio inline <#if tempPayMode[payMode_index] == 1>check</#if>"><input type="radio" name="${platform.platformCode}_${payMode_index}" value="1" <#if tempPayMode[payMode_index] == 1>checked="checked"</#if>/>显示不使用</label>
	                                        <label class="radio inline <#if tempPayMode[payMode_index] == 2>check</#if>"><input type="radio" name="${platform.platformCode}_${payMode_index}" value="2" <#if tempPayMode[payMode_index] == 2>checked="checked"</#if>/>显示并使用</label>
	                                    </div>
	                                </div>
		                    	</div>
		                    	</#list>
								<#-- 默认支付 -->
								<div class="row-fluid">
		                    		<div class="control-group w235">
	                                    <label class="control-label">默认支付方式</label>
	                                    <div class="controls">
	                                    	<#assign defaultTradeMode=defaultTradeTypes['${platform.platformCode}']>
	                                    	<#list platform.payModes as payMode>
	                                        <label class="radio inline <#if defaultTradeMode == '${payMode.targetId}' >check</#if>"><input type="radio" name="${platform.platformCode}_defaultTradeType" value="${payMode.targetId}" <#if defaultTradeMode == '${payMode.targetId}' >checked="checked"</#if>" />${payMode.name}</label>
	                                        </#list>
	                                    </div>
	                                </div>
		                    	</div>
		                    	
		                    	<div class="row-fluid">
		                    		<div class="control-group w235">
	                                    <label class="control-label">支付详情显示方式</label>
	                                    <div class="controls">
	                                    	<label class="radio inline <#if rulePayment.viewType == 'iframe'>check</#if>"><input type="radio" name="viewType" value="iframe" <#if rulePayment.viewType == 'iframe'>checked="checked"</#if>/>iframe</label>
	                                        <label class="radio inline <#if rulePayment.viewType == 'jsonp'>check</#if>"><input type="radio" name="viewType" value="jsonp" <#if rulePayment.viewType == 'jsonp'>checked="checked"</#if>/>jsonp</label>
	                                    </div>
	                                </div>
		                    	</div>
							<#else>
								没有配置支付信息
							</#if>
	                   	</div>
	                </form>
	            </div>
	            </#if>
            </#list>
            
            <div class="space30"></div>
            <button class="btn btn-save" id="savePaymentRule" onclick="javascript: void(0);">保存</button>
        </div>
    </div>
</div>
</body>
</html>

<script type="text/javascript">
    $('.tabs li a').click(function(){
    	$(this).addClass('select');
    	$(this).parent().siblings().children().removeClass('select');
    });
    
    $('input[type=radio]').off('click').on('click', function() {
    	var p = $(this).parents('label');
        var name = $(this).attr('name');
        $('form input[name='+name+']').parents('label').removeClass('check');
        p.addClass('check');
    });
    
    $('#savePaymentRule').off('click').on('click', function() {
    console.log("sss");
    	var url = "${basePath}sys/rulePayment/saveRulePayment";
		var datas = {};
		datas.hospitalId = $('#hospitalId').val();
		datas.id = $('#id').val();
		datas.viewType = $("input[name='viewType']:checked").val();
		
		// 支付方式 - 单一平台下，不能全为0（存在支付方式）.
		datas.tradeTypes = '{';
		// 默认支付方式 - 单一平台下必须选择一个，如果没有支付方式，则为-1
		datas.defaultTradeTypes = '{';
		
		var forms = $('form');
		for (var i=0; i<forms.length; i++) {
			var platformCode = forms[i].id;
			var payModes = $('form').eq(i).find('div.controls.payMode'); // 每个支付方式
			
			var sPayModes = '';
			for (var j=0; j<payModes.length; j++) {
				sPayModes += $(payModes).eq(j).find('input:checked').val() + ',';
			}
			
			if (sPayModes.length > 0) {
				sPayModes = sPayModes.substring(0, sPayModes.length - 1);
			}
			
			datas.tradeTypes += platformCode + ':"' + sPayModes + '"';
			if (i != forms.length - 1) {
				datas.tradeTypes += ',';
			}
			
			var defaultType = $('input[name="' + platformCode + '_defaultTradeType"]:checked').val();
			if (!defaultType && sPayModes && sPayModes.length > 0) {
				alert("请选择默认的支付方式!"); 
				return false;
			} else {
				if (!sPayModes) {
					datas.defaultTradeTypes += platformCode + ':"-1"';
				} else {
					datas.defaultTradeTypes += platformCode + ':"' + defaultType + '"';
				}
				
				if (i != forms.length - 1) {
					datas.defaultTradeTypes += ',';
				}
			}
		}
		
		datas.tradeTypes += '}';
		datas.defaultTradeTypes += '}';
		
		console.log(datas);
	
    	jQuery.ajax({
				type: 'POST',
				url: url,
				data: datas,
				dataType: 'json',
				timeout: 120000,
				success: function(data) {
					if (data.status == 'OK') {
						alert("保存成功!"); 
						$("#id").val(data.message.entityId);
					} else {
						alert(data.message);
					}
				},
				error: function(data) {
					alert("保存失败!");
				}
			});
    });
    
    $(function() {
    	$( ".tab_content" ).hide();
    	$( ".tab_content:first" ).show();
    	
    	$( "ul.tabs li" ).click( function () {
            $( "ul.tabs li" ).removeClass( "active" );  //Remove any "active" class
            $( this ).addClass( "active" );  //Add "active" class to selected tab
            $( ".tab_content" ).hide();  //Hide all tab content
            var  activeTab = $( this ).find( "a" ).attr( "href" );  //Find the rel attribute value to identify the active tab + content
            $(activeTab).fadeIn();  //Fade in the active content
            return   false ;
        });
        
    });
</script>