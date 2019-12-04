$(function() {
	$('button.lib_select.select').trigger('click');
});
/**
 *搜索
 */
function search() {
	$.ajax({
		type : "POST",
		url : $("#basePath").val() + "/msgpush/msgTemplateLibrary/list",
		data : $("form").serializeArray(),
		dataType : "json",
		success : function(data) {
			var tbody = $(".table-bordered tbody");
			//清空
			tbody.empty();
			var url = $("#basePath").val() + "/msgpush/msgTemplateLibrary/editView";
			$(data.list).each(function(i, n) {
				var tr = tbody.append('<tr></tr>');
				tr.append('<td>' + n.templateId + "</td>");
				tr.append('<td>' + n.templateCode + "</td>");
				tr.append('<td>' + n.title + "</td>");
				tr.append('<td>' + n.primaryIndustry + "</td>");
				tr.append('<td>' + n.secondIndustry + "</td>");
				tr.append("<td><a  href='javascript:void(0);' onclick=\"window.location.href='" + url + "?id=" + n.id + "'\">编辑</a></td>");
			});
			$('form input[name="pages"]').val(data.pages);
			$('form input[name="pageNum"]').val(data.pageNum);
			$('form input[name="pageSize"]').val(data.pageSize);
			pagingInfo(data);
		},
		failure : function(data) {
			alert("系统内部错误,请联系管理员。");
		}
	});
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

/**
 *切换 模板库
 */
function change(source) {
	/*if (source == 3){
		$('.lib_select').removeClass('select');
		$("#easyhealthLibrary").addClass('select');
	}
	else if (source == 2) {
		$('.lib_select').removeClass('select');
		$("#alipayLibrary").addClass('select');
	} else if (source == 1) {
		$('.lib_select').removeClass('select');
		$("#wechatLibrary").addClass('select');
	} else if (source == 4) {
		$('.lib_select').removeClass('select');
		$("#appLibrary").addClass('select');
	} else if (source == 5) {
		$('.lib_select').removeClass('select');
		$("#innerUnionpayLibrary").addClass('select');
	}*/
	
	$('.lib_select').removeClass('select');
	$('.lib_select[value="' + source + '"]').addClass('select');
	$("input[name='source']").val(source);
	search();
}
/**
 *添加模板库模板 
 */
function add(){
	window.location.href=$("#basePath").val()+"/msgpush/msgTemplateLibrary/editView?source="+$("input[name='source']").val();
}

