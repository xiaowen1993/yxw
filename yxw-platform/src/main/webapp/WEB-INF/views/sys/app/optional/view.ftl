<html>
<head>
    <#include "/sys/common/common.ftl">
    <title><#if appOptional?exists>编辑<#else>新增</#if>功能</title>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title"><#if appOptional?exists>编辑<#else>新增</#if>功能</h3></div>
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
                            	<input type="hidden" name="id" value="${appOptional.id}">
                                <div class="space30"></div>
                                <div class="control-group">
                                    <label class="control-label" >功能编码</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="code" ext-null-hint="请输入功能编码" value="${appOptional.code}"/>
                                        <small class="grey">(用于程序内容识别所用，保存后不可修改)</small>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">功能名称</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="name" ext-null-hint="请输入功能名称" value="${appOptional.name}"/>
                                        <small class="grey">(用于前端APP展示所用)</small>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">归属版块</label>
                                    <div class="controls">
                                    	<select class="span5" name="appOptionalModule.id" ext-null-hint="请选择归属板块">
                            				<option value="" selected>未选择</option>
                            				<#if appOptionalModules?exists>
		                						<#list appOptionalModules as appOptionalModule>
		                							<#if appOptional.appOptionalModule.id == appOptionalModule.id>
		                            					<option value="${appOptionalModule.id}" selected>${appOptionalModule.name}</option>
		                            				<#else>
		                            					<option value="${appOptionalModule.id}">${appOptionalModule.name}</option>
		                            				</#if>
		                            			</#list>
		                            		</#if>
                            			</select>
                                        
                                        <small class="grey">(用于判断该功能在APP中哪个区域)</small>
                                    </div>
                                </div>
                                <#-- <div class="control-group">
                                    <label class="control-label">功能图标</label>
                                	<div style="display: none;"><input type="file" id="uploadFile" name="uploadFile" style="" onchange="$app.optional.uploadIcon();"/></div>
                                    <div class="controls">
                                        <div class="imgBox">
                                        	<img id="showIcon" src="<#if appOptional?exists>${appOptional.icon}<#else>${basePath}images/demo-defalt.jpg</#if>" width="100" height="100"/>
                                        </div>
                                        <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(用于前端APPZ展示所用)</small>
                                        <input type="hidden" id="icon" name="icon" value="${appOptional.icon}">
                                    </div>
                                </div> -->
								<div class="control-group">
                                    <label class="control-label">功能图标样式</label>
                                    <div class="controls">
                                        <input type="text" class="span5" name="iconCss" ext-null-hint="请输入功能图标样式" value="${appOptional.iconCss}"/>
                                        <small class="grey">&nbsp;</small>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">功能排序</label>
                                    <div class="controls">
                                        <input type="number" class="span5" name="showSort" ext-null-hint="请输入功能排序" value="${appOptional.showSort}">
                                        <small class="grey">(用于控制该功能在APP对应模块中的排序，数字越小排序越考前)</small>
                                    </div>
                                </div>
                                <div class="control-group"><label class="control-label">是否发布</label>
                                    <div class="controls" style="padding-top: 8px;">
                                        <label class="radio inline <#if appOptional?exists><#if appOptional.visible == 1>check</#if></#if>"> <input type="radio" <#if appOptional?exists><#if appOptional.visible == 1>checked</#if></#if> value="1" name="visible">发布</label>
                                        <label class="radio inline <#if appOptional?exists><#if appOptional.visible == 0>check</#if><#else>check</#if>"> <input type="radio" <#if appOptional?exists><#if appOptional.visible == 0>checked</#if><#else>checked</#if> value="0" name="visible">不发布</label>
                                    </div>
                                </div>
                                <div class="control-group"><label class="control-label">功能地址</label>

                                    <div class="controls"><input type="text" class="span8" name="url" ext-null-hint="请输入功能地址" value="${appOptional.url}"/></div>
                                </div
                                <div class="control-group"><label class="control-label">功能简介</label>

                                    <div class="controls"><textarea class="span8" rows="5" name="description">${appOptional.description}</textarea></div>
                                </div>
                            </form>
                        </div>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
            <div class="footer-tool">

                <div class="row-fluid">
                    <button class="btn btn-save" onclick="$app.optional.save();">保存</button>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

<script src="${basePath}js/sys/app/optional/sys.app.optional.js"></script>
<script src="${basePath}js/sys/app/sys.app.common.js"></script>
<script src="${basePath}js/ajaxfileupload.js"></script>

</body>
</html>