/**
 * 账户管理js
 */

var $organization = {};

$organization.search = function() {
  document.forms[0].submit();
}
controllerPath = "/sys/organization/";
pageBasePath = path+controllerPath;
listUrl = pageBasePath+"list";

/**
 * 分页
 * */
$organization.changePage = function(pageNum, pageSize) {
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
    $organization.search();
  }
}
/**
 * 跳转编辑页面
 * */
$organization.toEdit = function(id)
{
	window.location=pageBasePath+"toEdit?id="+id;
}

/**
 * 跳转新增页面
 * */
$organization.toAdd = function()
{
	window.location=pageBasePath+"toAdd";
}

/**
 * 批量删除
 * */
$organization.batchDel = function ()
{
	var chk = getChkVal();
	if(chk.length==0)
	{
		window.wxc.xcConfirm("请勾选您要操作的组织/机构", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	window.wxc.xcConfirm("请确认是否要删除所选组织/机构？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchDelAjax(chk.toString());
	}});
}

/**
 * 删除单个账户
 * */
$organization.del = function(id){
	var ids = [];
	ids.push(id);
	
	window.wxc.xcConfirm("请确认是否要删除？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchDelAjax(ids.toString());
	}});
}

//保存
$organization.save = function()
{
	var paramObj = getFormVal();
	if(!vaidForm(paramObj)){
		window.wxc.xcConfirm("名称、编码都不能为空", window.wxc.xcConfirm.typeEnum.info);
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
				window.wxc.xcConfirm("新建组织/机构失败", window.wxc.xcConfirm.typeEnum.error);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("新建组织/机构成功", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.location=listUrl;
				}});
			}
		}
	});
}

//保存编辑内容
$organization.edit = function()
{
	var paramObj = getFormVal();
	if(!vaidForm(paramObj)){
		window.wxc.xcConfirm("名称、编码都不能为空", window.wxc.xcConfirm.typeEnum.info);
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
				window.wxc.xcConfirm("修改组织/机构失败！", window.wxc.xcConfirm.typeEnum.warning);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("修改组织/机构成功！", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
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
	if(paramObj.name==''||paramObj.code=='')
	{
		return false;
	}
	return true;
}

getFormVal = function(){
	var name=$('#name').val();
	var introduction=$('#introduction').val();
	var code=$('#code').val();
	var memo=$('#memo').val();
	
	var obj = {name:name,introduction:introduction,code:code,memo:memo};
	
	var idObj = $('#id');
	if(idObj){
		obj.id=idObj.val();
	}
	var parentId=$('#parentId').val();
	if(parentId&&parentId.val()!=''){
		obj.parentId=parentId;
	}
	
	return obj;
}