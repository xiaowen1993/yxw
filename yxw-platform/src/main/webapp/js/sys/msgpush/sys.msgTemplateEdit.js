/**
 *保存
 */
function save() {
	// 新版模板消息存储： appId:templatCode:platformType(即为source):msgTarget 
	var hospitalId = $("form input[name='hospitalId']");
	var source = $("form input[name='source']:checked");
	var msgTarget = $("form input[name='msgTarget']:checked");
	var code = $("form input[name='code']");
	var id = $("form input[name='id']");

	if (!$.trim(id.val())) {
		var libraryCode = $("form input[name='libraryCode']");
		var title = $("form input[name='title']");
		if (!$.trim(libraryCode.val())) {
			alert("模板库编码不能为空!");
			return false;
		}
		if (!$.trim(title.val())) {
			alert("标题不能为空!");
			return false;
		}
		if (!$.trim(code.val())) {
			alert("模板编码不能为空!");
			return false;
		} else {
			$.ajax({
				type : "POST",
				url : $("#basePath").val() + "msgpush/msgTemplate/check",
				data : {
					"code" : code.val(),
					"msgTarget" : msgTarget.val(),
					"source" : source.val(),
					"hospitalId" : hospitalId.val()
				},
				dataType : "json",
				error : function(data) {
					alert("系统内部错误,请联系管理员。");
				},
				success : function(data) {
					if ("OK" == data.status) {
						//循环校验模板内容列表
						// $('.action_box').children(".action-row").each(function(index) {
						// var keyword = $(this).find("input[name='msgTemplateContents.keyword']");
						// var value = $(this).find("input[name='msgTemplateContents.value']");
						// var node = $(this).find("input[name='msgTemplateContents.node']");
						// if (!$.trim(keyword.val())) {
						// alert("模板内容的关键字不能为空!");
						// return false;
						// }
						// if (!$.trim(value.val())) {
						// alert("模板内容的值不能为空!");
						// return false;
						// }
						// if (!$.trim(node.val())) {
						// alert("模板内容的节点不能为空!");
						// return false;
						// }
						//
						// });
						//给表单元素的名称加上索引,方便后端程序接收数组类型的数据
						$('.action_box').children(".msg_template_content.action-row").each(function(index) {
							var sort = $(this).find("input[name='msgTemplateContents.sort']");
							var keyword = $(this).find("input[name='msgTemplateContents.keyword']");
							var value = $(this).find("input[name='msgTemplateContents.value']");
							var node = $(this).find("input[name='msgTemplateContents.node']");
							var fontColor = $(this).find("select[name='msgTemplateContents.fontColor']");
							sort.attr("name", "msgTemplateContents[" + index + "].sort");
							keyword.attr("name", "msgTemplateContents[" + index + "].keyword");
							value.attr("name", "msgTemplateContents[" + index + "].value");
							node.attr("name", "msgTemplateContents[" + index + "].node");
							fontColor.attr("name", "msgTemplateContents[" + index + "].fontColor");
						});
						//模板功能
						$('.action_box').children(".msg_template_function.action-row").each(function(index) {
							var sort = $(this).find("input[name='msgTemplateFunctions.sort']");
							var functionName = $(this).find("input[name='msgTemplateFunctions.functionName']");
							var functionCode = $(this).find("input[name='msgTemplateFunctions.functionCode']");
							var functionType = $(this).find("select[name='msgTemplateFunctions.functionType']");
							sort.attr("name", "msgTemplateFunctions[" + index + "].sort");
							functionName.attr("name", "msgTemplateFunctions[" + index + "].functionName");
							functionCode.attr("name", "msgTemplateFunctions[" + index + "].functionCode");
							functionType.attr("name", "msgTemplateFunctions[" + index + "].functionType");
						});
						//提交
						$("#msgTemplateForm").ajaxSubmit({
							type : 'post',
							url : $("#basePath").val() + "msgpush/msgTemplate/saveMsgTemplate",
							success : function(data) {
								if ("OK" == data.status) {
									alert("保存成功!");
									window.location.href = $("#basePath").val() + "msgpush/msgTemplate/listViewByHospital?hospitalId=" + hospitalId.val();
								} else {
									alert(data.message);
								}
							},
							error : function(XmlHttpRequest, textStatus, errorThrown) {
								alert("系统内部错误,请联系管理员。");
							}
						});
					} else {
						alert("模板编码已存在!");
					}
				}
			});
		}

	} else {

		//循环校验模板内容列表
		// $('.action_box').children(".msg_template_content.action-row").each(function(index) {
		// var keyword = $(this).find("input[name='msgTemplateContents.keyword']");
		// var value = $(this).find("input[name='msgTemplateContents.value']");
		// var node = $(this).find("input[name='msgTemplateContents.node']");
		// // if (!$.trim(keyword.val())) {
		// // alert("模板内容的关键字不能为空!");
		// // return false;
		// // }
		// if (!$.trim(value.val())) {
		// alert("模板内容的值不能为空!");
		// return false;
		// }
		// if (!$.trim(node.val())) {
		// alert("模板内容的节点不能为空!");
		// return false;
		// }
		//
		// });
		//给表单元素的名称加上索引,方便后端程序接收数组类型的数据
		$('.msg_template_content.action_box').children(".msg_template_content.action-row").each(function(index) {
			var sort = $(this).find("input[name='msgTemplateContents.sort']");
			var keyword = $(this).find("input[name='msgTemplateContents.keyword']");
			var value = $(this).find("input[name='msgTemplateContents.value']");
			var node = $(this).find("input[name='msgTemplateContents.node']");
			var fontColor = $(this).find("select[name='msgTemplateContents.fontColor']");
			sort.attr("name", "msgTemplateContents[" + index + "].sort");
			keyword.attr("name", "msgTemplateContents[" + index + "].keyword");
			value.attr("name", "msgTemplateContents[" + index + "].value");
			node.attr("name", "msgTemplateContents[" + index + "].node");
			fontColor.attr("name", "msgTemplateContents[" + index + "].fontColor");
		});
		//模板功能
		$('.msg_template_function.action_box').children(".msg_template_function.action-row").each(function(index) {
			var sort = $(this).find("input[name='msgTemplateFunctions.sort']");
			var functionName = $(this).find("input[name='msgTemplateFunctions.functionName']");
			var functionCode = $(this).find("input[name='msgTemplateFunctions.functionCode']");
			var functionType = $(this).find("select[name='msgTemplateFunctions.functionType']");
			sort.attr("name", "msgTemplateFunctions[" + index + "].sort");
			functionName.attr("name", "msgTemplateFunctions[" + index + "].functionName");
			functionCode.attr("name", "msgTemplateFunctions[" + index + "].functionCode");
			functionType.attr("name", "msgTemplateFunctions[" + index + "].functionType");
		});
		//提交
		$("#msgTemplateForm").ajaxSubmit({
			type : 'post',
			url : $("#basePath").val() + "/msgpush/msgTemplate/saveMsgTemplate",
			success : function(data) {
				if ("OK" == data.status) {
					alert("保存成功!");
					window.location.href = $("#basePath").val() + "/msgpush/msgTemplate/listViewByHospital?hospitalId=" + hospitalId.val();
				} else {
					alert(data.message);
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统内部错误,请联系管理员。");
			}
		});
	}

}

/**
 *添加内容配置
 */
function addMsgTemplateContent(obj) {
	var parent = $(obj).parents('.action-row');
	var html = "";
	html += ' <div class="msg_template_content action-row clearfix">';
	html += ' <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="msg_template_content label_id js_label_id"><input type="text" class="span12 center" name="msgTemplateContents.sort" readonly="readonly"/></span></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateContents.keyword" maxlength="20"/></div></div>';
	html += ' <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateContents.value" maxlength="255"/></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input  type="text"  class="span12  center"  name="msgTemplateContents.node" maxlength="20"/></div></div>';
	html += '  <div class="row-li" style="width:15%;"> <div class="sidepadding"><div class="my_select">';
	html += '<select name="msgTemplateContents.fontColor" class="span12"><option value="#0000FF">蓝色</option><option value="#ff0000">红色</option><option value="#000000">黑色</option></select>';
	html += '</div></div> </div>';
	html += '  <div class="row-li textleft" style="width:15%;"> <div class="leftpadding">';
	html += '<button class="btn btn-tool" onclick="addMsgTemplateContent(this);" type="button"><i class="icons-plus"></i></button>';
	html += '<button type="button" class="btn btn-tool" onclick="delMsgTemplateContent(this);"><i class="icons-trash"></i></button>';
	html += ' </div> </div></div>';
	parent.after(html);
	$(".msg_template_content.js_label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
}

/**
 *删除内容配置
 */
function delMsgTemplateContent(obj) {
	$(obj).parents('.action-row').remove();
	$(".msg_template_content.js_label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
}

/**
 *添加模板功能配置
 */
function addMsgTemplateFunction(obj) {
	var parent = $('.msg_template_function.action_box');
	var html = "";
	html += ' <div class="msg_template_function action-row clearfix">';
	html += ' <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="msg_template_function label_id"><input type="text" class="span12 center" name="msgTemplateFunctions.sort" readonly="readonly"/></span></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateFunctions.functionName" maxlength="20"/></div></div>';
	html += ' <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateFunctions.functionCode" maxlength="500"/></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><div class="my_select">';
	html += ' <select name="msgTemplateFunctions.functionType" class="span12"><option value="1">超链接</option><option value="2">JS函数名称</option></select>';
	html += ' </div></div> </div>';
	html += ' <div class="row-li textleft" style="width:15%;"> <div class="leftpadding">';
	html += ' <button class="btn btn-tool" onclick="addMsgTemplateFunction(this);" type="button"><i class="icons-plus"></i></button>';
	html += ' <button type="button" class="btn btn-tool" onclick="delMsgTemplateFunction(this);"><i class="icons-trash"></i></button>';
	html += ' </div> </div></div>';
	parent.append(html);
	$(".msg_template_function.label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
};

/**
 *删除模板功能配置
 */
function delMsgTemplateFunction(obj) {
	$(obj).parents('.action-row').remove();
	$(".msg_template_function.label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
};

function changeMsgMode(targetId) {
	$('input[name="msgTarget"]').prop('checked', false);
	$('input[name="msgTarget"][value="' + targetId + '"]').prop('checked', true);
	$('input[name="msgCode"]').val($('input[name="msgTarget"][value="' + targetId + '"]').attr('msgCode'));
	
	// 目前就只有1，2，3 三种消息类型，都是新平台这边的。如果要找回微信、支付宝，则下面的控制代码需要改动
	if (Number(targetId) < 4) {
		$("#functions").show();
		$("#icon").show();
		$("#animation").show();
		$("#topColor").hide();
		$("#url").hide();
	}
	
	/*
	if ("1" == targetId||"3"==targetId || "4" == targetId || "5" == targetId) {
		$("#templateId").hide();
		$("#icon").hide();
		$("#animation").hide();
		$("#topColor").show();
		$("#url").show();
		$("#functions").hide();
	} else {
		$("#templateId").show();
		$("#functions").hide();
		$("#icon").hide();
		$("#animation").hide();
		$("#topColor").show();
		$("#url").show();
	}
	
	if("3"==targetId || "4" == targetId || "5" == targetId){
		$("#functions").show();
		$("#icon").show();
		$("#animation").show();
		$("#topColor").hide();
		$("#url").hide();
	}
	*/
}

function changePlatform(obj) {
	var targetId = $(obj).val();
	$('input[name="source"]').prop('checked', false);
	$(obj).prop('checked', true);
	
	showMsgMode(targetId);
}

function showMsgMode(obj) {
	$('div#platform_' + obj).show().siblings('div').hide();
	
	// 设定第一个选中
	$('div#platform_' + obj).find('input[name="msgTarget"]:first').prop('checked', true).trigger('click');
}

/**
 *选择模版
 */
function chooseTemp() {
	var source = $("input[name='source']:checked").val();
	new $Y.dialog({
		title : '模板库',
		width : '450px',
		height : '325px',
		content : '',
		callback : function(box) {
			$.ajax({
				url : $("#basePath").val() + '/msgpush/msgTemplateLibrary/findListBySource?source=' + source,
				dataType : 'json',
				cache : false,
				success : function(data) {
					var html = "";
					var index = -1;
					html += "<div class='msgList-dialog'>";
					html += "<div class='widget-content'>";

					html += "<div class='row-fluid center'>";
					html += "<div class='controls mCustomScrollbar_u'>";
					html += "<form>";
					html += "<table class='table table-second'>";
					html += "<tbody>";
					html += "<tr><td>";
					$(data.message).each(function(i, n) {
						html += "<div class='controls-label clearfix'><label class='radio inline'><input type='radio' name='index' value='" + i + "'><span class='definition'>" + n.title + "</span></label></div>";
					});
					html += "</td></tr>";
					html += "</tbody></table></form></div></div></div>";
					html += "<div class='controlsBtnBox rowBg center'><button class='btn-save'>确定</button><div class='spaceW15'></div><button class='btn-remove'>取消</button></div>";
					html += "</div>";
					box.content(html);
					$Y.ScrollBar();
					$(box.id).on('click', '.btn-save', function() {
						index = $("input[name='index']:checked").val();

						if (index != -1 && index != '') {
							$(data.message).each(function(i, n) {
								if (i == index) {
									$("input[name='libraryCode']").val(n.templateId);
									$("input[name='templateId']").val(n.templateId);
									$("input[name='code']").val(n.templateCode);
									$("input[name='title']").val(n.title);
									$("input[name='iconPath']").val(n.iconPath);
									$("input[name='animationPath']").val(n.animationPath);
									$("input[name='animationName']").val(n.animationName);
									$("input[name='iconName']").val(n.iconName);
									$("select[name='topColor'] option[value='" + n.topColor + "']").prop("selected", true);
									$("select[name='topColor'] option[value!='" + n.topColor + "']").prop("selected", false);
									$("input[name='url']").val(n.url);
									var parent = $(".msg_template_content.action_box");
									parent.empty();
									if (n.msgLibraryContents) {
										$(n.msgLibraryContents).each(function(num, msgLibraryContent) {
											var html = "";
											html += ' <div class="msg_template_content action-row clearfix">';
											html += ' <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="label_id js_label_id"><input type="text" class="span12 center" name="msgTemplateContents.sort" readonly="readonly" value="' + msgLibraryContent.sort + '"/></span></div></div>';
											html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateContents.keyword" maxlength="20" value="' + msgLibraryContent.keyword + '"/></div></div>';
											html += ' <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateContents.value" maxlength="255" value="' + msgLibraryContent.value + '"/></div></div>';
											html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input  type="text"  class="span12  center"  name="msgTemplateContents.node" maxlength="20" value="' + msgLibraryContent.node + '"/></div></div>';
											html += '  <div class="row-li" style="width:15%;"> <div class="sidepadding"><div class="my_select">';
											html += '<select name="msgTemplateContents.fontColor" class="span12">';
											if (msgLibraryContent.fontColor == '#0000FF') {
												html += '<option value="#0000FF" selected="selected">蓝色</option><option value="#ff0000">红色</option><option value="#000000">黑色</option>';
											} else if (msgLibraryContent.fontColor == '#ff0000') {
												html += '<option value="#0000FF" >蓝色</option><option value="#ff0000" selected="selected">红色</option><option value="#000000">黑色</option>';
											} else {
												html += '<option value="#0000FF">蓝色</option><option value="#ff0000">红色</option><option value="#000000" selected="selected" >黑色</option>';
											}
											html += '</select></div></div> </div>';
											html += '  <div class="row-li textleft" style="width:15%;"> <div class="leftpadding">';
											html += '<button class="btn btn-tool" onclick="addMsgTemplateContent(this);" type="button"><i class="icons-plus"></i></button>';
											html += '<button type="button" class="btn btn-tool" onclick="delMsgTemplateContent(this);"><i class="icons-trash"></i></button>';
											html += ' </div> </div></div>';
											parent.append(html);
										});
									}
									var parent = $(".msg_template_function.action_box");
									if (n.msgTemplateLibraryFunctions) {
										$(n.msgTemplateLibraryFunctions).each(function(num, msgTemplateLibraryFunction) {
											var html = "";
											html += ' <div class="msg_template_function action-row clearfix">';
											html += ' <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="msg_template_function label_id"><input type="text" class="span12 center" name="msgTemplateFunctions.sort" readonly="readonly" value="' + msgTemplateLibraryFunction.sort + '"/></span></div></div>';
											html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateFunctions.functionName" maxlength="20" value="' + msgTemplateLibraryFunction.functionName + '"/></div></div>';
											html += ' <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateFunctions.functionCode" maxlength="255" value="' + msgTemplateLibraryFunction.functionCode + '"/></div></div>';
											html += '  <div class="row-li" style="width:15%;"> <div class="sidepadding"><div class="my_select">';
											html += '<select name="msgTemplateFunctions.functionType" class="span12">';
											if (msgTemplateLibraryFunction.functionType == '2') {
												html += '<option value="1" >超链接</option><option value="2" selected="selected">JS函数名称</option>';
											} else {
												html += '<option value="1" selected="selected">超链接</option><option value="2" >JS函数名称</option>';
											}
											html += '</select></div></div> </div>';
											html += '  <div class="row-li textleft" style="width:15%;"> <div class="leftpadding">';
											html += '<button class="btn btn-tool" onclick="addMsgTemplateFunction(this);" type="button"><i class="icons-plus"></i></button>';
											html += '<button type="button" class="btn btn-tool" onclick="delMsgTemplateFunction(this);"><i class="icons-trash"></i></button>';
											html += ' </div> </div></div>';
											parent.append(html);
										});
									}
								}
							});
						}
						box.close();
					});
					$(box.id).on('click', '.btn-remove', function() {
						box.close();
					});
				}
			})
		}
	});
}

/**
 *上传图标
 */
function iconUpload() {
	$('#iconFile').trigger('click');
};
/**
 *返回图标名称
 */
function getIconFileName() {
	var name = $("#iconFile").val();
	$("input[name='iconName']").val(name.substring(name.lastIndexOf("\\") + 1, name.length));
};

/**
 *上传动画
 */
function animationUpload() {
	$('#animationFile').trigger('click');
};
/**
 *返回动画名称
 */
function getAnimationFileName() {
	var name = $("#animationFile").val();
	$('#animationName').val(name.substring(name.lastIndexOf("\\") + 1, name.length));
};

$(function() {
	$('input[name="source"]').first().trigger('click');
});
