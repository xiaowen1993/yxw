var $custom = {};

var path='';
var basePath='';

$custom.search = function() {
  document.forms[0].submit();
}

/**
 * 分页
 * */
$custom.changePage = function(pageNum, pageSize) {
	
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
	    $custom.search();
	  }
}

$custom.switchType=function(type){
	if(!type){
		type = "1";
	}
	if($("#feedbackSelect")){
		 $("#feedbackSelect option[value='" +  type + "']").attr("selected","selected");
	}
	
	window.location = $("#basePath").val() + "sys/customService/list?type=" + type;
}


$custom.addReply= function(id){
    boxPicX=new $Y.dialog({
       title:'回复',
       width:'600px',
       height:'300px',
       content:'',
       callback:function(){
       	$.ajax({
       		url: $("#basePath").val() +'/sys/customService/toDialogWords',
               dataType:'html',
               cache:false,
               success:function(html){
               	boxPicX.content(html);
               	$(boxPicX.id).find('#save').bind('click',function(){
               		$.ajax({
               			url: $("#basePath").val() +"/sys/customService/updateFeedback",
       					data:{reply:$('#replyContent').val(),id:id},			
       					dataType: 'json',	  
       					type:'POST',
       					success:function(resp)
       					{
       						if(resp.status=='OK')
       						{
       							window.wxc.xcConfirm("保存成功", window.wxc.xcConfirm.typeEnum.success);
       							boxPicX.close();
       							window.location.reload();
       							
       						}
       						else if(resp.status=='ERROR')
       						{
       							window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
       							boxPicX.close();
       						}
       					}  
               		});
               	});
               	$(boxPicX.id).find('#cancel').bind('click',function(){
               		boxPicX.close();
               	});
              }
       	});
       }
    });
	}

