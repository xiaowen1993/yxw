<html>
<head>
    <#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/json_utils.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>接入平台</title>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">接入平台</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row-fluid">
                <div class="space10"></div>
                <div class="myStep s3">
                    <div class="myStepClick">
                       	<a class="aStepClick a-s1" href="${basePath}sys/hospital/toEdit?id=${hospitalId}"></a>
                        <a class="aStepClick a-s2" href="${basePath}sys/branchHospital/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s3" href="${basePath}sys/platformSettings/toView?hospitalId=${hospitalId}"></a>
                        <#-- <a class="aStepClick a-s4" href="${basePath}sys/paySettings/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s5" href="${basePath}sys/optional/toView?hospitalId=${hospitalId}"></a>
                        <a class="aStepClick a-s6" href="${basePath}sys/customerMenu/toView?hospitalId=${hospitalId}"></a> -->
                    </div>
                </div>
            </div>
            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <!--内容-->
                        <div class="h-Info">
                            <div class="h-name">
                                <ul class="name-list textleft">
                                	<#list platformsMap?keys as key>
                                		<li <#if platformSettingsMap["${key}"].id?exists>class="active"</#if> value="${platformsMap['${key}'].id}" platformCode="${key}" app="true" onclick="$hospital.platform.switchPlatFormTab(this);"><a href="javascript:void(0);"><label ><i class="checkbox-opt"></i><span class="text">${platformsMap["${key}"].name}</span></label></a></li>
                                	</#list>
                                	<#--
                                	<li <#if platformSettingsMap['app'].id?exists>class="active"</#if> value="${platformSettingsMap['app'].pfId}" code="app" app="true" onclick="$hospital.platform.switchTab(this);"><a href="javascript:void(0);"><label ><i class="checkbox-opt"></i><span class="text">APP</span></label></a></li>
                                	-->
                                </ul>
                            </div>
                            
                            <div class="h-content">
                                <form class="form-horizontal">
                                    <div class="space30"></div>
                                    
                                    <#list platformsMap?keys as key>
                                    	<#if key == "app">
                                    		<div id="app_input" <#if !platformSettingsMap["app"].id?exists>style="display: none;"</#if>>
			                                    <input type="hidden" field="id" value="${platformSettingsMap['app'].id}" />
			                                    <input type="hidden" field="platformId" value="${platformsMap['app'].id}" />
			                                    <input type="hidden" field="hospitalPlatformSettingsId" value="${platformSettingsMap['app'].hpsId}" />
			                                    <div class="control-group">
			                                    	<label class="control-label" >app-AppId</label>
			                                    	<div class="controls">
			                                    		<input type="text" field="appId" readonly="readonly" value="${platformSettingsMap['app'].appId}" class="span11"/>
			                                    	</div>
			                                    </div>
			                                    <div class="control-group">
			                                    	<label class="control-label" >&nbsp;</label>
			                                    	<div class="controls">
			                                    		<a class="btn" onclick="$hospital.platform.genAppId('app')">自动生成</a>
			                                    	</div>
			                                    </div>
		                                    </div>
                                    	</#if>
                                    	<#if key == "innerUnionPay">
                                    		<div id="innerUnionPay_input" <#if !platformSettingsMap["innerUnionPay"].id?exists>style="display: none;"</#if>>
                                    			<input type="hidden" field="id" value="${platformSettingsMap['innerUnionPay'].id}" />
			                                    <input type="hidden" field="platformId" value="${platformsMap['innerUnionPay'].id}" />
			                                    <input type="hidden" field="hospitalPlatformSettingsId" value="${platformSettingsMap['innerUnionPay'].hpsId}" />
			                                    <div class="control-group">
			                                    	<label class="control-label" >innerUnionPay-AppId</label>
			                                    	<div class="controls">
			                                    		<input type="text" field="appId" readonly="readonly" value="${platformSettingsMap['innerUnionPay'].appId}" class="span11"/>
			                                    	</div>
			                                    </div>
			                                    <div class="control-group">
			                                    	<label class="control-label" >&nbsp;</label>
			                                    	<div class="controls">
			                                    		<a class="btn" onclick="$hospital.platform.genAppId('innerUnionPay')">自动生成</a>
			                                    	</div>
			                                    </div>
		                                    </div>
                                    	</#if>
										<#if key == "innerWechat">
                                    		<div id="innerWechat_input" <#if !platformSettingsMap["innerWechat"].id?exists>style="display: none;"</#if>>
			                                    <input type="hidden" field="id" value="${platformSettingsMap['innerWechat'].id}" />
			                                    <input type="hidden" field="platformId" value="${platformsMap['innerWechat'].id}" />
			                                    <input type="hidden" field="hospitalPlatformSettingsId" value="${platformSettingsMap['innerWechat'].hpsId}" />
			                                    <div class="control-group">
			                                    	<label class="control-label" >innerWechat-AppId</label>
			                                    	<div class="controls">
			                                    		<input type="text" field="appId" readonly="readonly" value="${platformSettingsMap['innerWechat'].appId}" class="span11"/>
			                                    	</div>
			                                    </div>
			                                    <div class="control-group">
			                                    	<label class="control-label" >&nbsp;</label>
			                                    	<div class="controls">
			                                    		<a class="btn" onclick="$hospital.platform.genAppId('innerWechat')">自动生成</a>
			                                    	</div>
			                                    </div>
		                                    </div>
                                    	</#if>
                                    	<#if key == "innerAlipay">
                                    		<div id="innerAlipay_input" <#if !platformSettingsMap["innerAlipay"].id?exists>style="display: none;"</#if>>
                                    			<input type="hidden" field="id" value="${platformSettingsMap['innerAlipay'].id}" />
			                                    <input type="hidden" field="platformId" value="${platformsMap['innerAlipay'].id}" />
			                                    <input type="hidden" field="hospitalPlatformSettingsId" value="${platformSettingsMap['innerAlipay'].hpsId}" />
			                                    <div class="control-group">
			                                    	<label class="control-label" >innerAlipay-AppId</label>
			                                    	<div class="controls">
			                                    		<input type="text" field="appId" readonly="readonly" value="${platformSettingsMap['innerAlipay'].appId}" class="span11"/>
			                                    	</div>
			                                    </div>
			                                    <div class="control-group">
			                                    	<label class="control-label" >&nbsp;</label>
			                                    	<div class="controls">
			                                    		<a class="btn" onclick="$hospital.platform.genAppId('innerAlipay')">自动生成</a>
			                                    	</div>
			                                    </div>
		                                    </div>
                                    	</#if>
                                    	<#if key == "autoService">
                                    		<div id="autoService_input" <#if !platformSettingsMap["autoService"].id?exists>style="display: none;"</#if>>
                                    			<input type="hidden" field="id" value="${platformSettingsMap['autoService'].id}" />
			                                    <input type="hidden" field="platformId" value="${platformsMap['autoService'].id}" />
			                                    <input type="hidden" field="hospitalPlatformSettingsId" value="${platformSettingsMap['autoService'].hpsId}" />
			                                    <div class="control-group">
			                                    	<label class="control-label" >autoService-AppId</label>
			                                    	<div class="controls">
			                                    		<input type="text" field="appId" readonly="readonly" value="${platformSettingsMap['autoService'].appId}" class="span11"/>
			                                    	</div>
			                                    </div>
			                                    <div class="control-group">
			                                    	<label class="control-label" >&nbsp;</label>
			                                    	<div class="controls">
			                                    		<a class="btn" onclick="$hospital.platform.genAppId('autoService')">自动生成</a>
			                                    	</div>
			                                    </div>
		                                    </div>
                                    	</#if>
                                    	<#if key == "innerChinaLife">
                                    		<div id="innerChinaLife_input" <#if !platformSettingsMap["innerChinaLife"].id?exists>style="display: none;"</#if>>
                                    			<input type="hidden" field="id" value="${platformSettingsMap['innerChinaLife'].id}" />
			                                    <input type="hidden" field="platformId" value="${platformsMap['innerChinaLife'].id}" />
			                                    <input type="hidden" field="hospitalPlatformSettingsId" value="${platformSettingsMap['innerChinaLife'].hpsId}" />
			                                    <div class="control-group">
			                                    	<label class="control-label" >innerChinaLife-AppId</label>
			                                    	<div class="controls">
			                                    		<input type="text" field="appId" readonly="readonly" value="${platformSettingsMap['innerChinaLife'].appId}" class="span11"/>
			                                    	</div>
			                                    </div>
			                                    <div class="control-group">
			                                    	<label class="control-label" >&nbsp;</label>
			                                    	<div class="controls">
			                                    		<a class="btn" onclick="$hospital.platform.genAppId('innerChinaLife')">自动生成</a>
			                                    	</div>
			                                    </div>
		                                    </div>
                                    	</#if>
                                    </#list>
                                    <div class="space30"></div>
                                </form>
                                <div class="caption">
                                    <div id="app_wxts" style="padding:1px 18px 30px; display: none;">
                                    	<hr />
                                        <h5 class="title">${platformsMap["app"].name}温馨提示</h5>
                                        <p>注意：平台appid用于对平台授权、接口授权，一旦生成保存，不可修改。</p>
                                    </div>
                                    <div id="innerUnionPay_wxts" style="padding:1px 18px 30px; display: none;">
                                    	<hr />
                                        <h5 class="title">${platformsMap["innerUnionPay"].name}温馨提示</h5>
                                        <p>注意：平台appid用于对平台授权、接口授权，一旦生成保存，不可修改。</p>
                                    </div>
                                    <div id="innerWechat_wxts" style="padding:1px 18px 30px; display: none;">
                                    	<hr />
                                        <h5 class="title">${platformsMap["innerWechat"].name}温馨提示</h5>
                                        <p>注意：平台appid用于对平台授权、接口授权，一旦生成保存，不可修改。</p>
                                    </div>
                                    <div id="innerAlipay_wxts" style="padding:1px 18px 30px; display: none;">
                                    	<hr />	 
                                        <h5 class="title">${platformsMap["innerAlipay"].name}温馨提示</h5>
                                        <p>注意：平台appid用于对平台授权、接口授权，一旦生成保存，不可修改。</p>
                                    </div>
                                    <div id="autoService_wxts" style="padding:1px 18px 30px; display: none;">
                                    	<hr />	 
                                        <h5 class="title">${platformsMap["autoService"].name}温馨提示</h5>
                                        <p>注意：平台appid用于对平台授权、接口授权，一旦生成保存，不可修改。</p>
                                    </div>
                                    <div id="innerChinaLife_wxts" style="padding:1px 18px 30px; display: none;">
                                    	<hr />	 
                                        <h5 class="title">${platformsMap["innerChinaLife"].name}温馨提示</h5>
                                        <p>注意：平台appid用于对平台授权、接口授权，一旦生成保存，不可修改。</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-remove" onclick="$hospital.back('${basePath}sys/branchHospital/toView?hospitalId=${hospitalId}');">上一步</button>
                    <button class="btn btn-save" onclick="$hospital.platform.savePlatformSettings();">保存</button>
                </div>
            </div>

        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>
<script type="text/javascript">
	$hospital.id = '${hospitalId}';
	var checkPlatforms = $('.name-list li.active');
	if(checkPlatforms.length > 0){
		checkPlatforms.each(function(i, item) {
	    	var code = $(item).attr('platformCode');
		    if($('#' + code + '_input').find(":text[field=appId]").val() != ''){
		    	$('#' + code + '_input').show();
		    	$('#' + code + '_wxts').show();
		    }else{
		    	$('#' + code + '_input').hide();
		    	$('#' + code + '_wxts').hide();
		    	$(item).removeClass("active");
		    }
	  	});
	}else{
		// $('.name-list li').eq(0).addClass("active");
		$('.name-list li').eq(0).trigger('click');
		var code = $('.name-list li').eq(0).attr("code")
		$('#' + code + '_wxts').show();
	}
</script>
