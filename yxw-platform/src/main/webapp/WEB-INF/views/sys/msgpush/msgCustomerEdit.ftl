<html>
<head>
<#include "/sys/common/common.ftl">
    <title>新增客服消息</title>
    <script type="text/javascript" src="${basePath}js/sys/msgpush/sys.msgCustomerEdit.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title"><span class="msg-t"><i class="line"></i>新增客服消息</span></h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="space20"></div>
            <div class="widget-box">
                <div class="widget-content msg-outer">
                    <div class="row-fluid">
                    <form method="post" name="msgCustomerForm">
                    	<input type="hidden" name="id" value="${entity.id}"/>
	                	<input type="hidden" name="appId" value="${entity.appId}"/>
	                	<input type="hidden" name="type" value="${entity.type}"/>
	                	<input type="hidden" name="hospitalId" value="${entity.hospitalId}"/>
	                	<input type="hidden" name="cp" value="${entity.cp}"/>
                    	<input type="hidden" name="ct" value="${(entity.ct?string('yyyy-MM-dd HH:mm:ss'))!}"/>
                    	<input type="hidden" name="ep" value="${entity.ep}"/>
                    	<input type="hidden" name="et" value="${(entity.et?string('yyyy-MM-dd HH:mm:ss'))!}"/>
                    	<#if entity.id==null>
                        <div class="control-group">
                            <label class="control-label">服务平台</label>
	                            <div class="controls line">
	                                <label class="radio inline check"><input type="radio" name="source" value="1" checked="checked">微信</label>
	                                <label class="radio inline"><input type="radio" name="source" value="2">支付宝</label>
	                            </div>
                        </div>
                        <div class="control-group"><label class="control-label" >标题/Title</label><div class="controls"><input type="text" name="title" class="span5" name="title" value="${entity.title}" maxlength="50"/></div></div>
                        <div class="control-group"><label class="control-label" >模板编码/Code</label><div class="controls"><input type="text" name="code" class="span5" name="code" value="${entity.code}" maxlength="50"/></div></div>
                        <#else>
                        	<input type="hidden" name="source" value="${entity.source}"/>
	                        <#if entity.source==2>
	                            <div class="control-group"><label class="control-label" >服务平台</label><div class="controls"><input type="text" class="span5" name="source" value="支付宝" readonly="readonly"/></div></div>
	                        <#else>
	         		            <div class="control-group"><label class="control-label" >服务平台</label><div class="controls"><input type="text" class="span5" name="source" value="微信" readonly="readonly"/></div></div>
	                        </#if>
	                        <div class="control-group"><label class="control-label" >标题/Title</label><div class="controls"><input type="text" class="span5" name="title" value="${entity.title}" maxlength="50" readonly="readonly"/></div></div>
                        <div class="control-group"><label class="control-label" >模板编码/Code</label><div class="controls"><input type="text" class="span5" name="code" value="${entity.code}" maxlength="50" readonly="readonly"/></div></div>
                        </#if>
                        <div class="control-group"><label class="control-label" >正文</label>
                            <div class="controls" style="margin-right: 200px;">
                                <textarea rows="5" style=" width:380px;height:350px" name="content">${entity.content}</textarea>
                            </div>
                        </div>
                        <div class="space20"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="space20"></div>
        <button type="button" class="btn btn-save w130" onclick="save()">保存</button>
    </div>
</div>
<!--content end-->
</body>
</html>
