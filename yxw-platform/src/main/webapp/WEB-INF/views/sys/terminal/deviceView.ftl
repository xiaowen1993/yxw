<html>
<head>
    <#include "/sys/common/common.ftl">
    <title><#if device?exists>编辑<#else>新增</#if>设备</title>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title"><#if device?exists>编辑<#else>新增</#if>设备</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">

            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <!--内容-->
                        <div class="row-layout">
                            <form class="form-horizontal" id="saveForm" method="post">
                            	<input type="hidden" name="hospitalId" value="${hospitalId}">
                            	<input type="hidden" name="id" value="${device.id}">
                                <div class="space30"></div>
                                <div class="control-group">
                                    <label class="control-label" >设备 ID</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="deviceId" ext-null-hint="请输入设备 ID" value="${device.deviceId}"/>
                                        <small class="grey">&nbsp;</small>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" >设备编码</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="code" ext-null-hint="请输入设备编码" value="${device.code}"/>
                                        <small class="grey">&nbsp;</small>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">摆放位置</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="position" ext-null-hint="请输入摆放位置" value="${device.position}"/>
                                        <small class="grey">&nbsp;</small>
                                    </div>
                                </div>
								<div class="control-group">
                                    <label class="control-label">软件版本号</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="appVersion" ext-null-hint="请输入软件版本号" value="${device.appVersion}"/>
                                        <small class="grey">&nbsp;</small>
                                    </div>
                                </div>
                                <div class="control-group"><label class="control-label">状态</label>
                                    <div class="controls" style="padding-top: 8px;">
                                        <label class="radio inline <#if device?exists><#if device.state == 1>check</#if></#if>"> <input type="radio" <#if device?exists><#if device.state == 1>checked</#if></#if> value="1" name="state">启用</label>
                                        <label class="radio inline <#if device?exists><#if device.state == 0>check</#if><#else>check</#if>"> <input type="radio" <#if device?exists><#if device.state == 0>checked</#if><#else>checked</#if> value="0" name="state">停用</label>
                                    </div>
                                </div>
                                <div class="control-group"><label class="control-label">功能简介</label>
                                    <div class="controls"><textarea class="span8" rows="5" name="note">${device.note}</textarea></div>
                                </div>
                            </form>
                        </div>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
            <div class="footer-tool">

                <div class="row-fluid">
                    <button class="btn btn-save" onclick="$terminal.device.save('${device.hospitalId}');">保存</button>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

<script src="${basePath}js/sys/terminal/sys.terminal.js"></script>
</body>
</html>