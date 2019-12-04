<html>
<head>
    <#include "/sys/common/common.ftl">
    <title>系统配色</title>
    <style>
    </style>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title"><#if entity?exists>编辑系统配色<#else>新增系统配色</#if></h3></div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">

            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <!--内容-->
                        <div class="row-layout">
                            <form class="form-horizontal" id="dataForm" method="post">
                            	<input type="hidden" name="id" id="id" value="${entity.id}"/>
                                
                                <div class="space30"></div>
                                <div class="control-group">
                                    <label class="control-label" >版本名称</label>
                                    <div class="controls">
                                        <input type="text" class="span5" id="appName" name="appName" value="${entity.appName}" readonly/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" >版本代码</label>
                                    <div class="controls">
                                        <input type="text" class="span5" id="appCode" name="appCode" value="${entity.appCode}" readonly/>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" >颜色值</label>
                                    <div class="controls">
                                    	<div class="input-group">
										  <input type="text" class="span5" id="color" name="color" placeholder="请输入颜色值" value="${entity.color}" maxlength="7"/>
										  <small class="red" style="display: none;">#开头的6位颜色值</small>
										</div>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" >样例</label>
                                    <div class="controls">
                                    	<div class="input-group">
                                    		<input class="span5" id="colorInput" type="color" style="height: 39px;border: 1px solid #dce0e4;border-radius: 2px;" />
										</div>
                                    </div>
                                </div>
                                
                                <div class="control-group">
                                    <label class="control-label">支付信息显示方式</label>
                                    <div class="controls" style="padding-top: 8px;">
	                                        <label class="radio inline <#if entity?exists><#if entity.payInfoViewType == "iframe">check</#if><#else>check</#if>"> <input type="radio" <#if entity?exists><#if entity.payInfoViewType == "iframe">checked</#if><#else>checked</#if> value="iframe" name="payInfoViewType">iframe</label>
	                                        <label class="radio inline <#if entity?exists><#if entity.payInfoViewType == "jsonp">check</#if></#if>"> <input type="radio" <#if entity?exists><#if entity.payInfoViewType == "jsonp">checked</#if></#if> value="jsonp" name="payInfoViewType">jsonp</label>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
        	<div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-save">保存</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
<script src="${basePath}js/sys/app/color/sys.app.color.js"></script>
</body>
</html>