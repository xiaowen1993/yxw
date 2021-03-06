
var order = {};

order.search = function() {
  document.forms[0].submit();
}

order.changePage = function(pageNum, pageSize) {
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
    order.search();
  }
}