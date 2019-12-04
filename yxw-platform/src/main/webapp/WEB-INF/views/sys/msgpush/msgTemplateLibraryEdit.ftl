<html>
<head>
<#include "/sys/common/common.ftl">
    <title>添加模版</title>
<script type="text/javascript" src="${basePath}js/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}js/sys/msgpush/sys.msgTemplateLibraryEdit.js"></script>
</head>
<style>
	a.btn-add.add-uploads{ width:120px; height:40px; line-height:40px;vertical-align:top; display:inline-block;zoom:1; *display:inline;}
</style>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">添加模版库模板 </h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space10"></div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content temp-outer">
                    <div class="row-fluid">
                    	<form id="frm" method="post" action="${basePath}msgpush/msgTemplateLibrary/saveMsgTemplateLibrary" enctype="multipart/form-data">
                    		<input type="hidden" name="id" value="${entity.id}"/>
                    		<input type="hidden" name="source" value="${entity.source}"/>
                    		<input type="hidden" name="iconPath" value="${entity.iconPath}"/>
                    		<input type="hidden" name="animationPath" value="${entity.animationPath}"/>
                    		<input type="hidden" name="cp" value="${entity.cp}"/>
                    		<input type="hidden" name="ct" value="${(entity.ct?string('yyyy-MM-dd HH:mm:ss'))!}"/>
                    		<input type="hidden" name="ep" value="${entity.ep}"/>
                    		<input type="hidden" name="et" value="${(entity.et?string('yyyy-MM-dd HH:mm:ss'))!}"/>
                        	<div class="control-group"><label class="control-label" >编　　号</label><div class="controls"><input type="text" class="span5" name="templateId" value="${entity.templateId}" maxlength="100"/></div></div>
                        	<div class="control-group"><label class="control-label" >编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label><div class="controls"><input type="text" class="span5" name="templateCode" value="${entity.templateCode}" maxlength="50"/></div></div>
                        	<div class="control-group"><label class="control-label" >标　　题</label><div class="controls"><input type="text" class="span5" name="title" value="${entity.title}" maxlength="100"/></div></div>
                        	<#if (entity.source >= 20)>
                        	<div class="control-group"><label class="control-label" >图　　标</label>
                        	<div class="controls">
                        	<input type="text" class="span5" id="iconName" name="iconName" value="${entity.iconName}" maxlength="500" readonly="readonly"/>
                        	<a class="btn-add add-uploads" href="javascript:void(0);" id="iconUpload" onClick="iconUpload()"><i class="icons-plus"></i>上传</a>
                        	<input type="file" id="iconFile" name="iconFile" onChange="getIconFileName()" style="display: none;"/>
                        	</div></div>
                        	<div class="control-group"><label class="control-label" >动　　画</label>
                        	<div class="controls">
                        	<input type="text" class="span5" id="animationName" name="animationName" value="${entity.animationName}" maxlength="500" readonly="readonly"/>
                        	<a class="btn-add add-uploads" href="javascript:void(0);" id="animationUpload" onClick="animationUpload()"><i class="icons-plus"></i>上传</a>
                        	<input type="file" id="animationFile" name="animationFile" onChange="getAnimationFileName()" style="display: none;"/>
                        	</div></div>
                        	</#if>
                        	<div class="control-group"><label class="control-label" >一级行业</label><div class="controls"><input type="text" class="span5" name="primaryIndustry" value="${entity.primaryIndustry}" maxlength="50"/></div></div>
                        	<div class="control-group"><label class="control-label" >二级行业</label><div class="controls"><input type="text" class="span5" name="secondIndustry" value="${entity.secondIndustry}" maxlength="50"/></div></div>
                        	<#-- <20的，即为微信或支付宝等旧类型。其中的健康易比较坑爹 -->
                        	<#if (entity.source < 20)>
                        	<div class="control-group"><label class="control-label" >顶部颜色</label><div class="controls">
                                <div class="my_select">
                                    <select name="topColor" class="span9">
                                    	<#if entity.topColor=='#008000'>
                                        	<option value="#0000FF">蓝色</option>
                                        	<option value="#ff0000">红色</option>
                                        	<option value="#008000" selected="selected">黑色</option>
                                        <#elseif entity.topColor=='#ff0000'>
                                        	<option value="#0000FF">蓝色</option>
                                        	<option value="#ff0000" selected="selected">红色</option>
                                        	<option value="#008000" >黑色</option>
                                        <#else>
                                         	<option value="#0000FF" selected="selected">蓝色</option>
                                        	<option value="#ff0000">红色</option>
                                        	<option value="#008000">黑色</option>
                                        </#if>
                                    </select>
                                </div>
                            </div>
	                        <div class="control-group"><label class="control-label" >跳链</label><div class="controls"><input type="text" class="span9" name="url" maxlength="500" value="${entity.url}"/></div></div>
	                        </#if>
	                        <div class="space20"></div>
	                        <div class="row-fluid">
                        	<!--配置内容 str-->
	                        <div class="totalList">
	                            <div class="action-row bottom clearfix">
	                                <div class="row-li" style="width:10%;">排序号</div>
	                                <div class="row-li" style="width:15%;">关键字</div>
	                                <div class="row-li" style="width:30%;">值</div>
	                                <div class="row-li" style="width:15%;">节点</div>
	                                <div class="row-li" style="width:15%;">字体颜色</div>
	                                <div class="row-li" style="width:15%;">操作</div>
	                            </div>
	                            <div class="space10"></div>
                            <div class="action_box clearfix">
                            	<#if (entity.msgLibraryContents?size>0)>
	                            	<#list entity.msgLibraryContents as msgTemplateContent>
	                                <div class="msg_library_content action-row clearfix">
	                                    <div class="row-li" style="width:10%;"><div class="sidepadding">
	                                     <#if msgTemplateContent_has_next>
	                                    	<span class="msg_library_content label_id js_label_id">
	                                    		<input type="text" class="span12 center"  name="msgLibraryContents.sort" readonly="readonly" value="${msgTemplateContent.sort}"/>
	                                    	</span>
	                                    <#else>
	                                    	<span class="msg_library_content label_id">
	                                    		<input type="text" class="span12 center"  name="msgLibraryContents.sort" readonly="readonly" value="${msgTemplateContent.sort}"/>
	                                    	</span>
	                                    </#if>
	                                    </div>
	                                </div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.keyword" value="${msgTemplateContent.keyword}"/></div></div>
	                                    <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.value" value="${msgTemplateContent.value}"/></div></div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.node" value="${msgTemplateContent.node}"/></div></div>
	                                    <div class="row-li" style="width:15%;">
	                                        <div class="sidepadding">
	                                            <div class="my_select">
	                                                <select name="msgLibraryContents.fontColor"  class="span12">
	                                                   	<#if msgTemplateContent.fontColor=='#ff0000'>
	                                        				<option value="#0000FF">蓝色</option>
	                                        				<option value="#000000">黑色</option>
	                                                    	<option value="#ff0000" selected="selected">红色</option>
	                                        			<#elseif msgTemplateContent.fontColor=='#000000'>
	                                        				<option value="#0000FF">蓝色</option>
	                                                    	<option value="#000000" selected="selected">黑色</option>
	                                                    	<option value="#ff0000">红色</option>
	                                        			<#else>
	                                         				<option value="#0000FF" selected="selected">蓝色</option>
	                                                    	<option value="#000000">黑色</option>
	                                                    	<option value="#ff0000">红色</option>
	                                        			</#if>
	                                                </select>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <div class="row-li textleft" style="width:15%;">
	                                        <div class="leftpadding">
	                                            <#if msgTemplateContent_has_next>
	                                            	<button class="btn btn-tool" onclick="addMsgTemplateContent(this);" type="button">
	                                                	<i class="icons-plus"></i>
	                                            	</button>
	                                            </#if>
	                                            <#if (msgTemplateContent_has_next && msgTemplateContent_index > 0)>
	                                           	    <button type="button" class="btn btn-tool" onclick="delMsgTemplateContent(this);">
	                                            		<i class="icons-trash"></i>
	                                            	</button>
	                                            </#if>
	                                        </div>
	                                    </div>
	                                </div>
	                                </#list>
	                           <#else>
	                                <!--第一条列表 str-->
	                                <div class="msg_library_content action-row clearfix">
	                                    <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="msg_library_content label_id js_label_id"><input type="text" class="span12 center"  name="msgLibraryContents.sort" readonly="readonly" value="1"/></span></div></div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.keyword" value="first"/></div></div>
	                                    <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.value"/></div></div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.node" value="first"/></div></div>
	                                    <div class="row-li" style="width:15%;">
	                                        <div class="sidepadding">
	                                            <div class="my_select">
	                                                <select name="msgLibraryContents.fontColor"  class="span12">
	                                                    <option value="#000000">黑色</option>
	                                                    <option value="#0000FF">蓝色</option>
	                                                    <option value="#ff0000">红色</option>
	                                                </select>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <div class="row-li textleft" style="width:15%;">
	                                        <div class="leftpadding">
	                                            <button class="btn btn-tool" onclick="addMsgTemplateContent(this);" type="button">
	                                                <i class="icons-plus"></i>
	                                            </button>
	                                        </div>
	                                    </div>
	                                </div>
	                                <!--第一条列表 end-->
	                                <!--最后一条列表 str-->
	                                <div class="msg_library_content action-row clearfix">
	                                    <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="label_id"><input type="text" class="span12 center"  name="msgLibraryContents.sort" readonly="readonly" value="99"/></span></div></div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.keyword" value="remark" /></div></div>
	                                    <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.value" value=""/></div></div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.node" value="remark"/></div></div>
	                                    <div class="row-li" style="width:15%;">
	                                        <div class="sidepadding">
	                                            <div class="my_select">
	                                                <select name="msgLibraryContents.fontColor" class="span12">
	                                                    <option value="#000000">黑色</option>
	                                                    <option value="#0000FF">蓝色</option>
	                                                    <option value="#ff0000">红色</option>
	                                                </select>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <div class="row-li textleft" style="width:15%;"><div class="leftpadding"></div></div>
	                                </div>
	                        </#if>
                        </div>
                    	<!--配置内容 end-->
                    	<#if (entity.source >= 20)>
                    	<!--配置功能 str-->
	                        <div class="totalList">
	                            <div class="action-row bottom clearfix">
	                                <div class="row-li" style="width:10%;">排序号</div>
	                                <div class="row-li" style="width:15%;">功能点名称</div>
	                                <div class="row-li" style="width:30%;">功能点代码</div>
	                                <div class="row-li" style="width:15%;">功能点类型</div>
	                                <div class="row-li addfirstMenu" style="width:15%;"><a href="javascript:void(0);" onclick="addMsgTemplateFunction();"><span class="green"><i class="icons-add"></i>添加功能</span></a></div>
	                            </div>
	                            <div class="space10"></div>
                            <div class="msg_library_function action_box clearfix">
                            	<#if (entity.msgTemplateLibraryFunctions?size>0)>
	                            	<#list entity.msgTemplateLibraryFunctions as msgTemplateLibraryFunction>
	                                <div class="msg_library_function action-row clearfix">
	                                    <div class="row-li" style="width:10%;"><div class="sidepadding">
	                                    	<span class="msg_library_function label_id">
	                                    		<input type="text" class="span12 center"  name="msgTemplateLibraryFunctions.sort" readonly="readonly" value="${msgTemplateLibraryFunction.sort}"/>
	                                    	</span>
	                                    </div>
	                                </div>
	                                    <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateLibraryFunctions.functionName" value="${msgTemplateLibraryFunction.functionName}"/></div></div>
	                                    <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateLibraryFunctions.functionCode" value="${msgTemplateLibraryFunction.functionCode}"/></div></div>
	                                    <div class="row-li" style="width:15%;">
	                                        <div class="sidepadding">
	                                            <div class="my_select">
	                                                <select name="msgTemplateLibraryFunctions.functionType"  class="span12">
	                                                    <#if msgTemplateLibraryFunction.functionType==2>
	                                        				<option value="1">超链接</option>
	                                                    	<option value="2" selected="selected">JS函数名称</option>
	                                        			<#else>
	                                         				<option value="1" selected="selected">超链接</option>
	                                                    	<option value="2">JS函数名称</option>
	                                        			</#if>
	                                                </select>
	                                            </div>
	                                        </div>
	                                    </div>
	                                    <div class="row-li textleft" style="width:15%;">
	                                        <div class="leftpadding">
	                                            	<button class="btn btn-tool" onclick="addMsgTemplateFunction(this);" type="button">
	                                                	<i class="icons-plus"></i>
	                                            	</button>
	                                            	<button type="button" class="btn btn-tool" onclick="delMsgTemplateFunction(this);">
	                                            	<i class="icons-trash"></i>
	                                            	</button>
	                                        </div>
	                                    </div>
	                                </div>
	                                </#list>
	                        </#if>
                        </div>
                        </#if>
                    	<!--配置内容 end-->
            			</div>
                		</div>
                        </form>
                        <div class="space25"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-tool">
            <div class="row-fluid">
                <button class="btn btn-save" onclick="save()">保存</button>
                <button class="btn btn-remove" onclick="window.location.href = '${basePath}msgpush/msgTemplateLibrary/listView';">取消</button>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
