$(function() {
	search();
});
/**
 *搜索
 */
function search() {
	$.ajax({
		type : "POST",
		url : $("#basePath").val() + "/msgpush/msgTemplate/list",
		data : $("form").serializeArray(),
		dataType : "json",
		error : function(data) {
			alert("系统内部错误,请联系管理员。");
		},
		success : function(data) {
			var tbody = $(".table-bordered tbody");
			//清空
			tbody.empty();
			var msgTemplateUrl = $("#basePath").val() + "/msgpush/msgTemplate/editTemp";
			var msgCustomerUrl=$("#basePath").val() + "/msgpush/msgCustomer/editCustomer";
			var msgTemplateDelUrl = $("#basePath").val() + "/msgpush/msgTemplate/delTemp";
			var msgCustomerDelUrl=$("#basePath").val() + "/msgpush/msgCustomer/delCustomer";
			$(data.list).each(function(i, n) {
				var tr = tbody.append('<tr></tr>');
				tr.append('<td>' + (i+1) + "</td>");
				tr.append('<td>' + n.code + "</td>");
				tr.append('<td>' + n.templateId + "</td>");
				tr.append('<td>' + n.title + "</td>");
				tr.append('<td>' + n.msgType + "</td>");
				tr.append('<td>' + n.source + "</td>");
				if('客服'==n.msgType){
					tr.append("<td>"+
					"<a href='javascript:void(0);' onclick=\"window.location.href='" + msgCustomerUrl + "?id=" + n.id + "'\">编辑</a>"+
					"|<a href='javascript:void(0);' onclick=\"delMsg('" + msgCustomerDelUrl + "', '" + n.id + "')\">删除</a>"+
					"</td>");
				}else if('模板'==n.msgType){
					tr.append("<td>"+
					"<a href='javascript:void(0);' onclick=\"window.location.href='" + msgTemplateUrl + "?id=" + n.id + "'\">编辑</a>"+
					"|<a href='javascript:void(0);' onclick=\"delMsg('" + msgTemplateDelUrl + "', '" + n.id + "')\">删除</a>"+
					"</td>");
				}
			});
			$('form input[name="pages"]').val(data.pages);
			$('form input[name="pageNum"]').val(data.pageNum);
			$('form input[name="pageSize"]').val(data.pageSize);
			pagingInfo(data);
		}
	});
}

function delMsg(url, id) {
	window.wxc.xcConfirm("请确认是否要删除？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		$.ajax({
			type : "POST",
			url : url,
			data : {id: id},
			dataType : "json",
			error : function(data) {
				alert("系统内部错误,请联系管理员。");
			},
			success : function(data) {
				window.wxc.xcConfirm("删除成功", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					search();
				}});
				
			}
		});
	}});
}

/**
 *分页信息
 */
function pagingInfo(data) {
	var root = $(".pagination-centered");
	root.empty();
	var ul = "<ul>";
	ul += "<li><a href='javascript:void(0);' onclick='changePage(" + data.prePage + ")'>上一页</a></li>";
	if (data.pages != 0) {
		for (var i = 1; i <= data.pages; i++) {
			if (i == data.pageNum) {
				ul += "<li class='disabled'><span>" + i + "</span></li>";
			} else {
				ul += "<li><a href='javascript:void(0);' onclick='changePage(" + i + ")'>" + i + "</a></li>";
			}
		}
	} else {
		ul += "<li class='disabled'><span>1</span></li>";
	}
	ul += "<li><a href='javascript:void(0);' onclick='changePage(" + data.nextPage + ")'>下一页</a></li>";
	ul += "<div class='pageGoto'>";
	ul += "<span>转到第</span> <input type='text' id='skipPage' class='goto_input'/><span>页</span>";
	ul += "<a href='javascript:void(0);' class='goto' onclick=\"changePage($('#skipPage').val())\">跳转</a>";
	ul += "</div>";
	ul += "</ul>";
	root.append(ul);
}

/**
 *翻页
 */
function changePage(pageNum) {
	if (pageNum) {
		var pages = $('form input[name="pages"]').val();
		var pageNumInput = $('form input[name="pageNum"]');

		// 如果输入的页数是非数字，则还是跳到当前页
		if (isNaN(pageNum)) {
			pageNum = pageNumInput.val();
		}

		// 如果页数大于总页数，则跳至最后一页，如页数小于最小页数，则跳至第一页
		pageNum = pageNum > pages ? pages : pageNum;
		pageNum = pageNum < 1 ? 1 : pageNum;

		pageNumInput.val(pageNum);

		search();
	} else {

	}
}