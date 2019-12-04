<html>
<head>
<#include "/sys/common/common.ftl">
<title>编辑消息模板</title>
<!--滚动条-->
<script type="text/javascript" src="${basePath}js/jquery.mCustomScrollbar.concat.min.js"></script>
<link rel="stylesheet" href="${basePath}css/jquery.mCustomScrollbar.css" />
<script type="text/javascript" src="${basePath}js/jquery.form.js"></script>
<script type="text/javascript" src="${basePath}js/sys/msgpush/sys.msgTemplateEdit.js"></script>
<style>
a.btn-add.add-uploads {
	width: 120px;
	height: 40px;
	line-height: 40px;
	vertical-align: top;
	display: inline-block;
	zoom: 1;
	*display: inline;
}
</style>
</head>
<body>
	<!--content str-->
	<#include "./sys/common/hospital_setting.ftl">
	<div id="content-header">
		<div class="widget-title">
			<div class="widget-title">
				<h3 class="title">新增消息模板</h3>
			</div>
		</div>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="space20"></div>
			<div class="widget-box">
				<div class="widget-content msg-outer">
					<form name="msgTemplateForm" id="msgTemplateForm" method="post" action="${basePath}msgpush/msgTemplate/saveMsgTemplate" enctype="multipart/form-data">
						<input type="hidden" name="id" value="${entity.id}" />
						<input type="hidden" name="appId" value="${entity.appId}" />
						<input type="hidden" name="hospitalId" value="${entity.hospitalId}" />
						<input type="hidden" name="iconPath" value="${entity.iconPath}" />
						<input type="hidden" name="animationPath" value="${entity.animationPath}" />
						<input type="hidden" name="cp" value="${entity.cp}" />
						<input type="hidden" name="ct" value="${(entity.ct?string('yyyy-MM-dd HH:mm:ss'))!}" />
						<input type="hidden" name="ep" value="${entity.ep}" />
						<input type="hidden" name="et" value="${(entity.et?string('yyyy-MM-dd HH:mm:ss'))!}" />
						<input type="hidden" name="msgCode" value="${entity.msgCode}" />
						<div class="row-fluid">
							<div class="space20"></div>
							<#if entity.id==null>
								<div class="control-group" style="margin-bottom: 10px;">
									<label class="control-label"></label>
									<div class="controls">
										<a href="javascript:void(0);" class="btn-save btn-upload w140" onclick="chooseTemp();">从模板库中选择</a>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">服务平台</label>
									<div class="controls line">
										<#-- <label class="radio inline check"><input type="radio" name="source" value="1" checked="checked" onclick="change('1')" disabled="disabled">微信</label> -->
										<#list platforms as platform> 
											<label class="radio inline">
												<input type="radio" name="source" appId="${platform.appId}" value="${platform.targetId}" onclick="changePlatform(this);">
												${platform.platformName}
											</label> 
										</#list>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">消息类型</label> 
									<#list platforms as platform>
										<div id="platform_${platform.targetId}" class="platformMsgMode controls line" <#if platform_index != 0>style="display: none;"</#if>> 
											<#list platform.msgModes as msgMode> 
												<label class="radio inline">
													<input type="radio" name="msgTarget" msgCode="${msgMode.code}" value="${msgMode.targetId}" onclick="changeMsgMode('${msgMode.targetId}')">
													${msgMode.name}
												</label>
			                        		</#list>
			                        	</div>
									</#list>
								</div>
								<div class="control-group">
									<label class="control-label">模板编号</label>
									<div class="controls">
										<input type="text" class="span9" name="libraryCode" maxlength="50" value="" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模板标题</label>
									<div class="controls">
										<input type="text" class="span9" name="title" maxlength="50" value="" />
									</div>
								</div>
								<div id="templateId" class="control-group" style="display: none;">
									<label class="control-label">模板ID</label>
									<div class="controls">
										<input type="text" class="span9" name="templateId" maxlength="50" value="" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模板编码</label>
									<div class="controls">
										<input type="text" class="span9" name="code" maxlength="50" value="" />
									</div>
								</div>
							<#else>
								<input type="hidden" name="source" value="${entity.source}" />
								<input type="hidden" name="msgTarget" value="${entity.msgTarget}" />
								<div class="control-group">
									<label class="control-label">服务平台</label>
									<div class="controls">
										<input type="text" class="span9" value="${platformSource}" readonly="readonly" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">消息类型</label>
									<div class="controls">
										<input type="text" class="span9" value="${msgMode}" readonly="readonly" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模板编号</label>
									<div class="controls">
										<input type="text" class="span9" name="libraryCode" maxlength="50" value="${entity.libraryCode}" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模版标题</label>
									<div class="controls">
										<input type="text" class="span9" name="title" maxlength="50" value="${entity.title}" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模板ID</label>
									<div class="controls">
										<input type="text" class="span9" name="templateId" maxlength="50" value="${entity.templateId}" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">模板编码</label>
									<div class="controls">
										<input type="text" class="span9" name="code" maxlength="50" value="${entity.code}" />
									</div>
								</div>
							</#if> 
							
							<div class="control-group" id="icon" style="display: none;">
								<label class="control-label">图 标</label>
								<div class="controls">
									<input type="text" class="span5" id="iconName" name="iconName" value="${entity.iconName}" maxlength="500" />
									<a class="btn-add add-uploads" href="javascript:void(0);" id="iconUpload" onClick="iconUpload()">
										<i class="icons-plus"></i>上传
									</a>
									<input type="file" id="iconFile" name="iconFile" onChange="getIconFileName()" style="display: none;" />
								</div>
							</div>
							<div class="control-group" id="animation" style="display: none;">
								<label class="control-label">动 画</label>
								<div class="controls">
									<input type="text" class="span5" id="animationName" name="animationName" value="${entity.animationName}" maxlength="500" />
									<a class="btn-add add-uploads" href="javascript:void(0);" id="animationUpload" onClick="animationUpload()">
										<i class="icons-plus"></i>上传
									</a>
									<input type="file" id="animationFile" name="animationFile" onChange="getAnimationFileName()" style="display: none;" />
								</div>
							</div>
							
							<div class="control-group" id="topColor" style="display: none;">
								<label class="control-label">顶部颜色</label>
								<div class="controls">
									<div class="my_select">
										<select name="topColor" class="span9">
											<#if entity.topColor=='#008000'>
												<option value="#0000FF">蓝色</option>
												<option value="#ff0000">红色</option>
												<option value="#008000" selected="selected">绿色</option>
											<#elseif entity.topColor=='#ff0000'>
												<option value="#0000FF">蓝色</option>
												<option value="#ff0000" selected="selected">红色</option>
												<option value="#008000">绿色</option>
											<#else>
												<option value="#0000FF" selected="selected">蓝色</option>
												<option value="#ff0000">红色</option>
												<option value="#008000">绿色</option>
											</#if>
										</select>
									</div>
								</div>
							</div>
							
							<div class="control-group" id="url" style="display: none;">
								<label class="control-label">跳链</label>
								<div class="controls">
									<input type="text" class="span9" name="url" maxlength="500" value="${entity.url}" />
								</div>
							</div>
							<div class="space20"></div>
						</div>
						<div class="row-fluid">
							<!--配置内容 str-->
							<div class="totalList">
								<div class="action-row bottom clearfix">
									<div class="row-li" style="width: 10%;">排序号</div>
									<div class="row-li" style="width: 15%;">关键字</div>
									<div class="row-li" style="width: 30%;">值</div>
									<div class="row-li" style="width: 15%;">节点</div>
									<div class="row-li" style="width: 15%;">字体颜色</div>
									<div class="row-li" style="width: 15%;">操作</div>
								</div>
								<div class="space10"></div>
								<div class="msg_template_content action_box clearfix">
									<#if (entity.msgTemplateContents?size>0)> 
										<#list entity.msgTemplateContents as msgTemplateContent>
											<div class="msg_template_content action-row clearfix">
												<div class="row-li" style="width: 10%;">
													<div class="sidepadding">
														<#if msgTemplateContent_has_next> 
															<span class="msg_template_content label_id js_label_id"> 
																<input type="text" class="span12 center" name="msgTemplateContents.sort" readonly="readonly" value="${msgTemplateContent.sort}" />
															</span> 
														<#else> 
															<span class="msg_template_content label_id"> 
																<input type="text" class="span12 center" name="msgTemplateContents.sort" readonly="readonly" value="${msgTemplateContent.sort}" />
															</span> 
														</#if>
													</div>
												</div>
												<div class="row-li" style="width: 15%;">
													<div class="sidepadding">
														<input type="text" class="span12 center" name="msgTemplateContents.keyword" value="${msgTemplateContent.keyword}" />
													</div>
												</div>
												<div class="row-li" style="width: 30%;">
													<div class="sidepadding">
														<input type="text" class="span12 center" name="msgTemplateContents.value" value="${msgTemplateContent.value}" />
													</div>
												</div>
												<div class="row-li" style="width: 15%;">
													<div class="sidepadding">
														<input type="text" class="span12 center" name="msgTemplateContents.node" value="${msgTemplateContent.node}" />
													</div>
												</div>
												<div class="row-li" style="width: 15%;">
													<div class="sidepadding">
														<div class="my_select">
															<select name="msgTemplateContents.fontColor" class="span12">
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
												<div class="row-li textleft" style="width: 15%;">
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
										<div class="msg_template_content action-row clearfix">
											<div class="row-li" style="width: 10%;">
												<div class="sidepadding">
													<span class="msg_template_content label_id js_label_id"><input type="text" class="span12 center" name="msgTemplateContents.sort" readonly="readonly" value="1" /></span>
												</div>
											</div>
											<div class="row-li" style="width: 15%;">
												<div class="sidepadding">
													<input type="text" class="span12 center" name="msgTemplateContents.keyword" value="first" />
												</div>
											</div>
											<div class="row-li" style="width: 30%;">
												<div class="sidepadding">
													<input type="text" class="span12 center" name="msgTemplateContents.value" />
												</div>
											</div>
											<div class="row-li" style="width: 15%;">
												<div class="sidepadding">
													<input type="text" class="span12 center" name="msgTemplateContents.node" value="first" />
												</div>
											</div>
											<div class="row-li" style="width: 15%;">
												<div class="sidepadding">
													<div class="my_select">
														<select name="msgTemplateContents.fontColor" class="span12">
															<option value="#000000">黑色</option>
															<option value="#0000FF">蓝色</option>
															<option value="#ff0000">红色</option>
														</select>
													</div>
												</div>
											</div>
											<div class="row-li textleft" style="width: 15%;">
												<div class="leftpadding">
													<button class="btn btn-tool" onclick="addMsgTemplateContent(this);" type="button">
														<i class="icons-plus"></i>
													</button>
												</div>
											</div>
										</div>
										<!--第一条列表 end-->
										<!--最后一条列表 str-->
										<div class="msg_template_content action-row clearfix">
											<div class="row-li" style="width: 10%;">
												<div class="sidepadding">
													<span class="msg_template_content label_id"><input type="text" class="span12 center" name="msgTemplateContents.sort" readonly="readonly" value="99" /></span>
												</div>
											</div>
											<div class="row-li" style="width: 15%;">
												<div class="sidepadding">
													<input type="text" class="span12 center" name="msgTemplateContents.keyword" value="remark" />
												</div>
											</div>
											<div class="row-li" style="width: 30%;">
												<div class="sidepadding">
													<input type="text" class="span12 center" name="msgTemplateContents.value" value="" />
												</div>
											</div>
											<div class="row-li" style="width: 15%;">
												<div class="sidepadding">
													<input type="text" class="span12 center" name="msgTemplateContents.node" value="remark" />
												</div>
											</div>
											<div class="row-li" style="width: 15%;">
												<div class="sidepadding">
													<div class="my_select">
														<select name="msgTemplateContents.fontColor" class="span12">
															<option value="#000000">黑色</option>
															<option value="#0000FF">蓝色</option>
															<option value="#ff0000">红色</option>
														</select>
													</div>
												</div>
											</div>
											<div class="row-li textleft" style="width: 15%;">
												<div class="leftpadding"></div>
											</div>
										</div>
										<!--最后一条列表 end-->
									</#if>
								</div>
							</div>
							<!--配置内容 end-->
							
							<!--配置功能 str-->
							<#if (entity.source >= 20)>
							<div class="totalList" id="functions" >
								<div class="action-row bottom clearfix">
									<div class="row-li" style="width: 10%;">排序号</div>
									<div class="row-li" style="width: 15%;">功能点名称</div>
									<div class="row-li" style="width: 30%;">功能点代码</div>
									<div class="row-li" style="width: 15%;">功能点类型</div>
									<div class="row-li addfirstMenu" style="width: 15%;">
										<a href="javascript:void(0);" onclick="addMsgTemplateFunction(this);">
											<span class="green"><i class="icons-add"></i>添加功能</span>
										</a>
									</div>
								</div>
								<div class="space10"></div>
								<div class="msg_template_function action_box clearfix">
									<#if (entity.msgTemplateFunctions?size>0)> 
										<#list entity.msgTemplateFunctions as msgTemplateFunction>
											<div class="msg_template_function action-row clearfix">
												<div class="row-li" style="width: 10%;">
													<div class="sidepadding">
														<span class="msg_template_function label_id"> 
															<input type="text" class="span12 center" name="msgTemplateFunctions.sort" readonly="readonly" value="${msgTemplateFunction.sort}" />
														</span>
													</div>
												</div>
												<div class="row-li" style="width: 15%;">
													<div class="sidepadding">
														<input type="text" class="span12 center" name="msgTemplateFunctions.functionName" value="${msgTemplateFunction.functionName}" />
													</div>
												</div>
												<div class="row-li" style="width: 30%;">
													<div class="sidepadding">
														<input type="text" class="span12 center" name="msgTemplateFunctions.functionCode" value="${msgTemplateFunction.functionCode}" />
													</div>
												</div>
												<div class="row-li" style="width: 15%;">
													<div class="sidepadding">
														<div class="my_select">
															<select name="msgTemplateFunctions.functionType" class="span12">
																<#if msgTemplateFunction.functionType==2>
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
												<div class="row-li textleft" style="width: 15%;">
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
							</div>
						</div>
					</form>
				</div>

				<div class="space20"></div>
				<button type="button" class="btn btn-save w130" onclick="save()">保存</button>
			</div>
		</div>
		<!--content end-->
</body>
</html>
