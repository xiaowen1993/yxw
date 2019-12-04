/**
 * 账户管理js
 */

var $user = {};

$user.search = function() {
  document.forms[0].submit();
}

controllerPath = "/sys/user/";
pageBasePath = path+controllerPath;
listUrl = pageBasePath+"list";

/**
 * 分页
 * */
$user.changePage = function(pageNum, pageSize) {
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
    $user.search();
  }
}
/**
 * 跳转编辑页面
 * */
$user.toEdit = function(id)
{
	window.location=pageBasePath+"toEdit?id="+id;
}

/**
 * 跳转新增页面
 * */
$user.toAdd = function()
{
	window.location=pageBasePath+"toAdd";
}

/**
 * 跳转新增页面
 * */
$user.toModifyPwd = function(id)
{
	window.location=pageBasePath+"toModifyPwd?id="+id;
}

/**
 * 跳转复制页面
 * */
$user.toCopy = function(id)
{
	window.location=pageBasePath+"toCopy?id="+id;
}

/**
 * 修改密码
 * */
$user.modifyPwd = function()
{
	var oldpwd = $('#oldpwd').val();
	var newpwd = $('#newpwd').val();
	var renewpwd = $('#renewpwd').val();
	if(oldpwd=='')
	{
		window.wxc.xcConfirm("请输入正确的原密码！", window.wxc.xcConfirm.typeEnum.warning);
		return;
	}
	if(newpwd==''||renewpwd=='')
	{
		window.wxc.xcConfirm("请输入新密码！", window.wxc.xcConfirm.typeEnum.warning);
		return;
	}
	if(newpwd.length <6||renewpwd.length<6)
	{
		window.wxc.xcConfirm("新密码长度不能少于6位！", window.wxc.xcConfirm.typeEnum.warning);
		return;
	}
	if(newpwd!=renewpwd)
	{
		window.wxc.xcConfirm("两次输入的密码不一致!", window.wxc.xcConfirm.typeEnum.warning);
		return;
	}
	$.ajax({
		url:pageBasePath+'modifyPwd',
		data:{oldpwd:oldpwd,newpwd:newpwd,renewpwd:renewpwd},
		type:'POST',
		dataType:'json',
		success:function(resp)
		{
			if(resp.status=='OK')
			{
				window.wxc.xcConfirm("密码已修改,请重新登录！", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.parent.location=path+"/login/user_tologin";
				}});
			}else
			{
				alert(resp.message);
			}
		}
	});
}

/**
 * 批量删除
 * */
$user.batchDel = function ()
{
	var ids = getChkVal();
	if(ids.length==0)
	{
		window.wxc.xcConfirm("请勾选您要操作的用户", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	window.wxc.xcConfirm("请确认是否要删除所选用户？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchDelAjax(ids.toString());
	}});
}

/**
 * 删除单个账户
 * */
$user.del = function(id){
	var ids = [];
	ids.push(id);
	
	window.wxc.xcConfirm("请确认是否要删除？", window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchDelAjax(ids.toString());
	}});
}

//保存
$user.save = function()
{
	var paramObj = getFormVal();
	if(!vaidForm(paramObj)){
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
				window.wxc.xcConfirm("新建用户失败,"+resp.message, window.wxc.xcConfirm.typeEnum.error);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("新建用户成功", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.location=listUrl;
				}});
			}
		}
	});
}

//保存编辑内容
$user.edit = function()
{
	var paramObj = getFormVal();
	
	if(paramObj.fullName=='')
	{
		window.wxc.xcConfirm("用户姓名不能为空", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='fullName']").focus();
		}});
		return false;
	}
	if(paramObj.userName=='')
	{
		window.wxc.xcConfirm("账户名称不能为空", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='userName']").focus();
		}});
		return false;
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
				window.wxc.xcConfirm("修改用户失败！"+resp.message, window.wxc.xcConfirm.typeEnum.warning);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("修改用户成功！", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
					window.location=listUrl;
				}});
			}
		}
	});
}

$user.updateAvaiable = function(id,avaiable){
	var ids = [];
	ids.push(id);
	
	var msg = "请确认是否要";
	if(avaiable==0){
		msg = msg+"禁用该用户";
	}else{
		msg = msg+"启用该用户";
	}
	
	window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchAvaiableAjax(ids.toString(),avaiable);
	}});
}

$user.batchAvaiable=function(avaiable){
	var ids = getChkVal();
	if(ids.length==0)
	{
		window.wxc.xcConfirm("请勾选您要操作的用户", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	
	var msg = "请确认是否要";
	if(avaiable==0){
		msg = msg+"禁用选中用户";
	}else{
		msg = msg+"启用选中用户";
	}
	
	window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{onOk:function(){
		batchAvaiableAjax(ids.toString(),avaiable);
	}});
}

batchAvaiableAjax = function(ids,available){
	$.ajax({
		url:pageBasePath+"batchAvaiable",
		data:{ids:ids,available:available},
		dataType:'json',
		type:'POST',
		success:function(resp)
		{
			if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
			}
			else if(resp.status=='OK')
			{
				window.wxc.xcConfirm("更新用户状态成功", window.wxc.xcConfirm.typeEnum.success,{onOk:function(){
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
				window.wxc.xcConfirm("删除失败"+resp.message, window.wxc.xcConfirm.typeEnum.error);
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
	if(paramObj.fullName=='')
	{
		window.wxc.xcConfirm("用户姓名不能为空", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='fullName']").focus();
		}});
		return false;
	}
	if(paramObj.userName=='')
	{
		window.wxc.xcConfirm("账户名称不能为空", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='userName']").focus();
		}});
		return false;
	}
	if(paramObj.password=='')
	{
		window.wxc.xcConfirm("密码不能为空", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='password']").focus();
		}});
		return false;
	}
	if(paramObj.password.length<6)
	{
		window.wxc.xcConfirm("密码不能少于6位", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='password']").focus();
		}});
		return false;
	}
	if(paramObj.rePassword=='')
	{
		window.wxc.xcConfirm("确认密码不能为空", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='rePassword']").focus();
		}});
		return false;
	}
	if(paramObj.rePassword!=paramObj.password)
	{
		window.wxc.xcConfirm("两次输入密码不一致", window.wxc.xcConfirm.typeEnum.error,{onOk:function(){
			$("input[name='rePassword']").focus();
		}});
		return false;
	}
	if(paramObj.available=='')
	{
		window.wxc.xcConfirm("是否启动必需选", window.wxc.xcConfirm.typeEnum.error);
		return false;
	}
	if(paramObj.organizationId=='')
	{
		window.wxc.xcConfirm("组织/机构/公司必选", window.wxc.xcConfirm.typeEnum.error);
		return false;
	}
	
	return true;
}

getFormVal = function(){
	//注意,需要引用/js/common.js文件.
	var obj = $("form").serializeObject();
	if(obj.roleIds){
		obj.roleIds = obj.roleIds.toString();
	}
	return obj;
}
