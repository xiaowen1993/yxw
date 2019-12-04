<html>
<head>
		<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/json_utils.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>功能选择</title>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">功能配置</h3></div>
    </div>
    <div class="container-fluid">
    	<div class="space20"></div>
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
        <div class="space10"></div>
        <#list platforms as platform>
        	<#if platform.state == 1>
            <div class="widget-box bangKa tab_content" id="tab${platform_index + 1}">
                <div class="space10"></div>
                <form class="form-horizontal evenBg platformOptionForm" id="form_${platform.platformCode}">
                    <input type="hidden" id="platformCode" name="platformCode" value="${platform.platformCode}"/>
                    <input type="hidden" id="appId" name="appId" value="${platform.appId}"/>
                    <input type="hidden" id="platformSettingsId" name="platformSettingsId" value="${platform.platformSettingsId}"/>
                    <div class="widget-content guaHao">
                    	<div class="row-fluid">
                    		<span>${platform.platformName}-平台功能设置</span>
                    		<div class="space20"></div>
							<div class="f-Info">
								<div class="f-name">
								   <div class="optionsF">可选功能</div>
									<ul class="module-list">
										<#list optionals as optional>
											<li><a href="javascript:void(0);" onclick="$hospital.optional.addItem(this, '${platform.platformCode}');" data-id="${platform.platformCode}_${optional.id}" data-conntroller="${optional.controllerPath}"><span class="text">${optional.name}</span><i class="icons-plus"></i></a></li>
										</#list>
									</ul>
								</div>
								<div class="f-content">
								   <div class="optionsS"><span>已选择功能</span></div>
									<ul class="optionsS-list">
										<#list selOptionalsMap["${platform.platformCode}"].optionals as optional>
											<script type="text/javascript">$('.module-list li a[data-id="${platform.platformCode}_${optional.id}"]').attr('sel', 'true').parent().hide();</script>
											<li><span class="text">${optional.name}</span><a class="green pull-right" data-id="${platform.platformCode}_${optional.id}" data-controller="${optional.controllerPath}" href="javascript:void(0);" onclick="$hospital.optional.delItem(this);">取消</a></li>
										</#list>
									</ul>
								</div>
							</div>
						</div>
                    </div>
                </form>
           	</div>
        	</#if>
    	</#list>
        <div class="footer-tool">
        	<div style="padding:1px 18px 30px;">
            	<hr />
                <h5 class="title">温馨提示</h5>
                <p>注意：本功能配置是按照平台来进行配置的。</p>
            </div>
            <div class="row-fluid">
                <button class="btn btn-save" onclick="$hospital.optional.save();">保存</button>
            </div>
        </div>
    </div>
</div>
<!--content end-->

</body>
</html>
<script type="text/javascript">
	$('.tabs li a').click(function(){
    	$(this).addClass('select');
    	$(this).parent().siblings().children().removeClass('select');
    });
    
	$hospital.id = '${hospitalId}';
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