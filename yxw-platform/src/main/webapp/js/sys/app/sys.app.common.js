/**
 * 分页|判断非空
 */
$app.getBasePath = function() {
	return $("#basePath").val();
}

$app.common = {};

$app.common.changePage = function(pageNum, pageSize) {
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

    // 如果修改了每页显示的数量
    if (pageSize) {
      $('form input[name="pageSize"]').val(pageSize);
    }
    document.forms[0].submit();
  }
}

$app.common.checkNull = function() {
	var nullHint = "";
	
	var items = $("[ext-null-hint]");
	
	for (var i = 0; i < items.length; i++) {
		var item = $(items[i]);
		
		var extVisibleId = item.attr("ext-visible-id");
		
		// 影藏的上级+ ID，如果此ID隐藏，则不做非空验证
		if (extVisibleId) {
			if ($("#"+extVisibleId).is(':hidden')) {
				continue;
			}
		}
		
		var val = item.val();
		if (item.is("input")) {
			if ("checkbox" == item.attr("type")) {
				if (item.parent().find("input:checkbox:checked").length == 0) {
					nullHint = item.attr("ext-null-hint");
					break;
				}
			} else {
				if (!val) {
					nullHint = item.attr("ext-null-hint");
					break;
				}
			}
			
		} else if (item.is("select")) {
			if (!val) {
				nullHint = item.attr("ext-null-hint");
				break;
			}
		} else if (item.is("textarea")) {
			if (!val) {
				nullHint = item.attr("ext-null-hint");
				break;
			}
		}
	}
	
	return nullHint;
}