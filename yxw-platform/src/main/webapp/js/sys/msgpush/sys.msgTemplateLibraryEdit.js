/**
 *保存
 */
function save() {
	// var templateId = $("form input[name='templateId']");
	// var templateCode = $("form input[name='templateCode']");
	// var title = $("form input[name='title']");
	// var primaryIndustry = $("form input[name='primaryIndustry']");
	// var secondIndustry = $("form input[name='secondIndustry']");
	// var source = $("form input[name='source']");
	// var id = $("form input[name='id']");
	// var url = $("form input[name='url']");

	// if ($.trim(templateId.val())=="") {
		// alert("编号不能为空!");
		// return false;
	// }
	// if ($.trim(templateCode.val())=="") {
		// alert("编码不能为空!");
		// return false;
	// }
	// if ($.trim(title.val())=="") {
		// alert("标题不能为空!");
		// return false;
	// }
	// if ($.trim(primaryIndustry.val())=="") {
		// alert("一级行业不能为空!");
		// return false;
	// }
	// if ($.trim(secondIndustry.val())=="") {
		// alert("二级行业不能为空!");
		// return false;
	// }
	// if (!$.trim(url.val())) {
	// alert("跳链不能为空!");
	// return false;
	// }
	//循环校验模板内容列表
	// $(".msg_library_content.action-row").each(function(index) {
		// var keyword = $(this).find("input[name='msgLibraryContents.keyword']");
		// var value = $(this).find("input[name='msgLibraryContents.value']");
		// var node = $(this).find("input[name='msgLibraryContents.node']");
		// if ($.trim(keyword.val())=="") {
			// alert("模板内容的关键字不能为空!");
			// return false;
		// }
		// // if (!$.trim(value.val())) {
		// // alert("模板内容的值不能为空!");
		// // return false;
		// // }
		// // if (!$.trim(node.val())) {
			// // alert("模板内容的节点不能为空!");
			// // return false;
		// // }
// 
	// });
	// var status = true;
	// //循环校验模板功能列表
	// $('.msg_library_function.action-row').each(function(index) {
		// var functionName = $(this).find("input[name='msgTemplateLibraryFunctions.functionName']");
		// var functionCode = $(this).find("input[name='msgTemplateLibraryFunctions.functionCode']");
		// if ($.trim(functionName.val())=="") {
			// alert("模板功能的名称不能为空!");
			// status = false;
			// return false;
		// }
		// // if (!$.trim(value.val())) {
		// // alert("模板内容的值不能为空!");
		// // return false;
		// // }
		// if ($.trim(functionCode.val())=="") {
			// alert("模板功能的代码不能为空!");
			// status = false;
			// return false;
		// }
// 
	// });

	//给表单元素的名称加上索引,方便后端程序接收数组类型的数据
	//模板内容
	//if (status) {
		$(".msg_library_content.action-row").each(function(index) {
			var sort = $(this).find("input[name='msgLibraryContents.sort']");
			var keyword = $(this).find("input[name='msgLibraryContents.keyword']");
			var value = $(this).find("input[name='msgLibraryContents.value']");
			var node = $(this).find("input[name='msgLibraryContents.node']");
			var fontColor = $(this).find("select[name='msgLibraryContents.fontColor']");
			sort.attr("name", "msgLibraryContents[" + index + "].sort");
			keyword.attr("name", "msgLibraryContents[" + index + "].keyword");
			value.attr("name", "msgLibraryContents[" + index + "].value");
			node.attr("name", "msgLibraryContents[" + index + "].node");
			fontColor.attr("name", "msgLibraryContents[" + index + "].fontColor");
		});
		//模板功能
		$(".msg_library_function.action-row").each(function(index) {
			var sort = $(this).find("input[name='msgTemplateLibraryFunctions.sort']");
			var functionName = $(this).find("input[name='msgTemplateLibraryFunctions.functionName']");
			var functionCode = $(this).find("input[name='msgTemplateLibraryFunctions.functionCode']");
			var functionType = $(this).find("select[name='msgTemplateLibraryFunctions.functionType']");
			sort.attr("name", "msgTemplateLibraryFunctions[" + index + "].sort");
			functionName.attr("name", "msgTemplateLibraryFunctions[" + index + "].functionName");
			functionCode.attr("name", "msgTemplateLibraryFunctions[" + index + "].functionCode");
			functionType.attr("name", "msgTemplateLibraryFunctions[" + index + "].functionType");
		});
		//$("#frm").submit();
		//提交
		$("#frm").ajaxSubmit({
			type : 'post',
			url : $("#basePath").val() + "/msgpush/msgTemplateLibrary/saveMsgTemplateLibrary",
			success : function(data) {
				if ("OK" == data.status) {
					// window.location.href = $("#basePath").val() + "/msgpush/msgTemplateLibrary/listView";
					window.location.href = $("#basePath").val() + "/msgpush/msgTemplateLibrary/toListView";
				} else {
					alert("保存出错!");
				}
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				alert("系统内部错误,请联系管理员。");
			}
		});
	//}

};

/**
 *添加模板内容配置
 */
function addMsgTemplateContent(obj) {
	var parent = $(obj).parents('.action-row');
	var html = "";
	html += ' <div class="msg_library_content action-row clearfix">';
	html += ' <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="msg_library_content label_id js_label_id"><input type="text" class="span12 center" name="msgLibraryContents.sort" readonly="readonly"/></span></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.keyword" maxlength="20"/></div></div>';
	html += ' <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgLibraryContents.value" maxlength="255"/></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input  type="text"  class="span12  center"  name="msgLibraryContents.node" maxlength="20"/></div></div>';
	html += '  <div class="row-li" style="width:15%;"> <div class="sidepadding"><div class="my_select">';
	html += '<select name="msgLibraryContents.fontColor" class="span12"><option value="#0000FF">蓝色</option><option value="#ff0000">红色</option><option value="#000000">黑色</option></select>';
	html += '</div></div> </div>';
	html += '  <div class="row-li textleft" style="width:15%;"> <div class="leftpadding">';
	html += '<button class="btn btn-tool" onclick="addMsgTemplateContent(this);" type="button"><i class="icons-plus"></i></button>';
	html += '<button type="button" class="btn btn-tool" onclick="delMsgTemplateContent(this);"><i class="icons-trash"></i></button>';
	html += ' </div> </div></div>';
	parent.after(html);
	$(".msg_library_content.js_label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
};

/**
 *删除模板内容配置
 */
function delMsgTemplateContent(obj) {
	$(obj).parents('.action-row').remove();
	$(".msg_library_content.js_label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
};

/**
 *添加模板功能配置
 */
function addMsgTemplateFunction(obj) {
	var parent = $(".msg_library_function.action_box");
	var html = "";
	html += ' <div class="msg_library_function action-row clearfix">';
	html += ' <div class="row-li" style="width:10%;"><div class="sidepadding"><span class="msg_library_function label_id"><input type="text" class="span12 center" name="msgTemplateLibraryFunctions.sort" readonly="readonly"/></span></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateLibraryFunctions.functionName" maxlength="20"/></div></div>';
	html += ' <div class="row-li" style="width:30%;"><div class="sidepadding"><input type="text" class="span12 center" name="msgTemplateLibraryFunctions.functionCode" maxlength="500"/></div></div>';
	html += ' <div class="row-li" style="width:15%;"><div class="sidepadding"><div class="my_select">';
	html += ' <select name="msgTemplateLibraryFunctions.functionType" class="span12"><option value="1">超链接</option><option value="2">JS函数名称</option></select>';
	html += ' </div></div> </div>';
	html += ' <div class="row-li textleft" style="width:15%;"> <div class="leftpadding">';
	html += ' <button class="btn btn-tool" onclick="addMsgTemplateFunction(this);" type="button"><i class="icons-plus"></i></button>';
	html += ' <button type="button" class="btn btn-tool" onclick="delMsgTemplateFunction(this);"><i class="icons-trash"></i></button>';
	html += ' </div> </div></div>';
	parent.after(html);
	$(".msg_library_function.label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
};

/**
 *删除模板功能配置
 */
function delMsgTemplateFunction(obj) {
	$(obj).parents('.action-row').remove();
	$(".msg_library_function.label_id > input").each(function(index) {
		$(this).val(index + 1);
	});
};

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
	$('#iconName').val(name.substring(name.lastIndexOf("\\") + 1, name.length));
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

