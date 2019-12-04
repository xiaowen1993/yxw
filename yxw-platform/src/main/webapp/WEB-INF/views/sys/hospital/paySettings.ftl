<html>
<head>
    <#include "/sys/common/common.ftl">
    <title>支付方式</title>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">支付配置</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            
            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
	                    <div class="space30"></div>
			            <div class="row-fluid">
			            	<div class="pull-left">
				                <ul id="platform" class="tabs"></ul>
				            </div>
			            </div>		
			             <div class="space30"></div>																								
                        <!--内容-->
                        <div class="h-Info">
                            <div class="h-name">
                                <ul id="payment" class="name-list textleft"></ul>
                            </div>
                            <div class="h-content">
                                <form id="paySettings" class="form-horizontal">
                                   
                                </form>
                            </div>
                        </div>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
            <div class="footer-tool">
                <div class="row-fluid">
                	<#--
                    <button class="btn btn-remove" onclick="$hospital.back('${basePath}sys/platformSettings/toView?hospitalId=${hospitalId}');">上一步</button>
                    -->
                    <button id="start_upload" class="btn btn-save"  onclick="$hospital.pay.savePaySettings();">保存</button>
                </div>
            </div>

        </div>
    </div>
</div>
<!--content end-->
</body>

    <script type="text/javascript" src="${basePath}js/json_utils.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <script type="text/javascript" src="${basePath}js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="${basePath}js/attach/sys.attach.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/paysettings/sys.paySettings.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/paysettings/sys.formatPaySettings.js"></script>
    
</html>
<script type="text/javascript">
	$hospital.id = '${hospitalId}';
	var basePath = '${basePath}';
</script>
