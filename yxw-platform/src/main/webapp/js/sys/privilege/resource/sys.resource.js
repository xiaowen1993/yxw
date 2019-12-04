/**
 * 账户管理js
 */

var $resource = {};

$resource.search = function() {
  document.forms[0].submit();
}

controllerPath = "/sys/resource/";
pageBasePath = path+controllerPath;
listUrl = pageBasePath+"list";

/**
 * 分页
 * */
$resource.changePage = function(pageNum, pageSize) {
  if (pageNum) {
    var pages = $('form input[name="pages"]').val();
    var pageNumInput = $('form input[name="pageNum"]');
    //如果页数大于总页数，则跳至最后一页，如页数小于最小页数，则跳至第一页
    pageNum = pageNum > pages ? pages : pageNum;
    pageNum = pageNum < 1 ? 1 : pageNum;
    pageNumInput.val(pageNum);
    //如果修改了每页显示的数量
    //  if (pageSize) {
    //    $('form input[name="pageSize"]').val(pageSize);
    //  }
    $resource.search();
  }
}
/**
 * 跳转编辑页面
 * */
$resource.toEdit = function(id)
{
	window.location=pageBasePath+"toEdit?id="+id;
}

/**
 * 跳转新增页面
 * */
$resource.toAdd = function()
{
	window.location=pageBasePath+"toAdd";
}

/**
 * 批量删除
 * */
$resource.batchDel = function ()
{
	var chk = getChkVal();
	if(chk.length==0)
	{
		window.wxc.xcConfirm("请勾选您要操作的资源", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	window.wxc.xcConfirm("请确认是否要删除所选资源？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchDelAjax(chk.toString());
	}});
}

/**
 * 删除单个账户
 * */
$resource.del = function(id){
	var ids = [];
	ids.push(id);
	
	window.wxc.xcConfirm("请确认是否要删除？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchDelAjax(ids.toString());
	}});
}

//保存
$resource.save = function()
{
	var paramObj = getFormVal();
	if(!vaidForm(paramObj)){
		window.wxc.xcConfirm("名称、编码、资源抽象都不能为空", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	$.ajax({
		url:pageBasePath+"add",
		data:paramObj,
		dataType:'json',
		type:'POST',
		success:function(resp)
		{
			if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm("新建资源失败,"+resp.message, window.wxc.xcConfirm.typeEnum.error);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("新建资源成功", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.location=listUrl;
				}});
			}
		}
	});
}

//保存编辑内容
$resource.edit = function()
{
	var paramObj = getFormVal();
	if(!vaidForm(paramObj)){
		window.wxc.xcConfirm("名称、编码、资源抽象都不能为空", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	$.ajax({
		url:pageBasePath+"update",
		data:paramObj,
		dataType:'json',
		type:'POST',
		success:function(resp)
		{
			if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm("修改资源失败！,"+resp.message, window.wxc.xcConfirm.typeEnum.warning);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("修改资源成功！", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.location=listUrl;
				}});
			}
		}
	});
}

batchDelAjax = function(ids){
	$.ajax({
		url:pageBasePath+"batchDelete",
		data:{ids:ids},
		dataType:'json',
		type:'POST',
		success:function(resp)
		{
			if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm("删除失败", window.wxc.xcConfirm.typeEnum.error);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("删除成功", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.location=listUrl;
				}});
			}
		}
	});
}

getChkVal = function(){ 
	var chk=[]; 

	if($('input[name="all"]').is(':checked'))
	{
		$('input[name="id"]').each(function(){ 
			chk.push($(this).val()); 
		}); 
	}else{
		$('input[name="id"]:checked').each(function(){ 
			chk.push($(this).val()); 
		}); 
	}
	
	return chk;
} 

vaidForm = function(paramObj){
	if(paramObj.name==''||paramObj.code==''||paramObj.abstr=='')
	{
		return false;
	}
	return true;
}

getFormVal = function(){
	var name=$('#name').val();
	var code=$('#code').val();
	var abstr=$('#abstr').val();
	var type=$('#type').val();
	var memo=$('#memo').val();
	
	var obj = {name:name,code:code,abstr:abstr,type:type,memo:memo};
	
	var idObj = $('#id');
	if(idObj){
		obj.id=idObj.val();
	}
	return obj;
}