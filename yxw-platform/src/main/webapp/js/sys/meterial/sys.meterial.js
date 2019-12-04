/**
 * 素材管理js
 */

var $meterial = {};

var path='';
var basePath='';
var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;

$meterial.search = function() {
  document.forms[0].submit();
}

/**
 * 分页
 * */
$meterial.changePage = function(pageNum, pageSize) {
	
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
	    $meterial.search();
	  }
}


$meterial.getChkVal = function(){ 
	var chk=[]; 
	if($('input[name="all"]').is(':checked'))
	{
		$('input[name="meterialIds"]').each(function(){ 
			chk.push($(this).val()); 
		}); 
	}else{
		$('input[name="meterialIds"]:checked').each(function(){ 
			chk.push($(this).val()); 
		}); 
	}
	return chk;
} 
/**
 * 跳转编辑页面
 * */
$meterial.toEdit = function(id)
{
	//window.location=path+"/user?method=toEdit&id="+id;
}

$meterial.uploadImg = function()
{
	var hospitalId=$('#hospitalId').val();
	var src=$('#btn-upload').val();
	if(src!='')
	{
		//异步上传到服务器
		$.ajaxFileUpload(  
		{
			  secureuri:false,  
              fileElementId:'btn-upload',  			
              dataType: 'json',	  
              type:'POST',
			  url: $("#basePath").val() +"message/meterial?method=savePic&hospitalId="+hospitalId,
			  success:function(resp)
			  {
					if(resp.status=='OK')
					{
						//showUploadImg(resp.message.id,resp.message.path,resp.message.size);
						window.location= $("#basePath").val() +"/message/meterial?method=listOfPic&hospitalId="+hospitalId;
					}
					else if(resp.status=='ERROR')
					{
						window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
					}
			  }                                                                                                              
		});
	}
}


showUploadImg = function(id,src,size)
{
	var $html=$('<tr><td><label class="checkboxTwo inline marginT23"><input type="checkbox" value="'+id+'"></label></td>'+
         '<td class="textleft"><span class="spaceW15"></span><img class="materImg" src="'+src+'" width="84" height="76"/> </td>'+
         '<td style="vertical-align: middle;">'+size+'KB</td><td><a href="javascript:void (0);" class="red lineH84" onclick="delThisRow(this,\''+id+'\');">删除</a> </td></tr>');
	$('tbody').prepend($html);
}



//删除整行
$meterial.delThisRow = function(obj,id,hospitalId){
	if(id)
	{
		$.ajax({
			url: $("#basePath").val() +"/message/meterial?method=delete",
			data:{	
				id:id
			},
			dataType:'json',
			type:'POST',
			success:function(resp)
			{
				if(resp.status=='OK')
				{
					//window.location= $("#basePath").val() +"/message/meterial?method=listOfPic&hospitalId="+hospitalId;
					var tr = $(obj).parents('tr');
    				tr.remove();
				}
				else if(resp.status=='ERROR')
				{
					window.wxc.xcConfirm("删除失败", window.wxc.xcConfirm.typeEnum.error);
				}
			}
		});
	}
}
//删除全部
$meterial.delAllRow = function(obj,hospitalId){
	var chk=$meterial.getChkVal();
	if(chk.length==0)
	{
		window.wxc.xcConfirm("请勾选您要删除的图片", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	if(!confirm("确定要删除这些图片吗？"))
	{
		return;
	}
	$.ajax({
		url:$('#basePath').val()+"message/meterial?method=deleteByIds",
		data:{ids:chk.toString()},
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
				window.location= $("#basePath").val() +"/message/meterial?method=listOfPic&hospitalId="+hospitalId;
			}
		}
	});
}


$meterial.deleteMixed=function(id)
{
	if(!confirm("确定要删除该图文吗？"))
	{
		return;
	}
	$.ajax({
		url:$('#basePath').val()+"message/mixedMeterial?method=delete",
		data:{id:id},
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
				window.location.reload();
			}
		}
	});

}

//编辑多图文
$meterial.editMixed=function(id,hospitalId)
{

	window.open($('#basePath').val()+"message/mixedMeterial?method=toEditMeterialOnManage&meterialType=2&id="+id+"&hospitalId="+hospitalId,
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}
//编辑单图文
$meterial.editSingle=function(id,hospitalId)
{
	window.open($('#basePath').val()+"message/mixedMeterial?method=toEditMeterialOnManage&id="+id+"&meterialType=1&hospitalId="+hospitalId,
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}

//添加单图文
$meterial.addSingle=function(hospitalId)
{
	window.open($('#basePath').val()+"message/mixedMeterial?method=addSingleText&hospitalId="+hospitalId,
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}

//添加多图文
$meterial.addMixed=function(hospitalId)
{
	window.open($('#basePath').val()+"message/mixedMeterial?method=addMixed&hospitalId="+hospitalId,
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}

/**
 * 单图文页面上传图片
 * */
$meterial.uploadSingleImg = function()
{
	var src=$('#uploadFile').val();
	if(src!='')
	{
		//异步上传到服务器
		$.ajaxFileUpload(  
		{
			  secureuri:false,  
              fileElementId:'uploadFile',  			
              dataType: 'json',	  
              type:'POST',
			  url: $("#basePath").val() +"/uploading?method=uploadMeterial",
			  success:function(resp)
			  {
					if(resp.status=='OK')
					{
						$('#CoverPicPath').val(resp.message);
						$('#showImg').attr('src', resp.message).show();
						$('.default').hide();
						var $cfile =$('#uploadFile').clone();
						$('#uploadFile').remove();
						$('#fileBlock').append($cfile);
					}
					else if(resp.status=='ERROR')
					{
						window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
					}
			  }                                                                                                              
		});
	}
}

/**
 * 保存单图文回复
 * hospitalId 医院ID
 * type 1被关注回复 2 关键字回复
 * index 当前操作的单元ID 
 * third
 * */
$meterial.saveSingle = function(hospitalId)
{
	//提交正文内容
	editor.sync();
	var title=$('#title').val();
	var content=editor.html();
	var coverPicPath=$('#CoverPicPath').val();
	var description=$('#description').val();
	if(title==''||coverPicPath=='')
	{
		window.wxc.xcConfirm("请填写图文信息的必填项", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	if(content.length>100000)
	{
		window.wxc.xcConfirm("正文的内容不能超出100000字", window.wxc.xcConfirm.typeEnum.info);
		return;
	}
	$.ajax({
		url: $("#basePath").val() +"/message/mixedMeterial?method=saveSimple",
		data:{	title:title,
				author:$('#author').val(),
				coverPicPath:coverPicPath,
				description:description,
				content:content,
				link:$('#link').val(),
				type:1,
				state:1,
				hospitalId:hospitalId
		},
		dataType:'json',
		type:'POST',
		success:function(resp)
		{
			if(resp.status=='OK')
			{
				window.opener.location= $("#basePath").val() +"/message/mixedMeterial?method=list&hospitalId="+hospitalId;
				window.close();
			}
			else if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm("添加图文失败", window.wxc.xcConfirm.typeEnum.error);
			}
		}
	});
}
/**
 * 保存单个内容
 * */
$meterial.save_content =function(index)
{
    var obj ={};
    obj.title=$('#title').val();
    obj.content=editor.html();
    obj.coverPicPath=$('#CoverPicPath').val();
    obj.author=$('#author').val();
    obj.description=$('#description').val();
    obj.link=$('#link').val();
    obj.type=2;
    obj.state=1;
    if(edit_index==0)
    {
    	obj.isParent=1;
    }else
    {
    	obj.isParent=0;
    }
    all_data[edit_index]=obj;
    edit_index = index;

}
//每次点击编辑按钮调用
$meterial.js_edit_line = function(obj)
{
    var index = $('.a_item').index(obj);
    //保存上一个单个图文
    $meterial.save_content(index);
    //填充当前的选择图文
    if(all_data[index])
    {
    	$('#title').val(all_data[index].title);
	    editor.html(all_data[index].content);
	    $('#CoverPicPath').val(all_data[index].coverPicPath);
	    $('#author').val(all_data[index].author);
	    $('#description').val(all_data[index].description);
	    $('#link').val(all_data[index].link);
    }
    else
    {
	    $('#title').val('');
	    editor.html('');
	    $('#CoverPicPath').val('');
	    $('#author').val('');
	    $('#description').val('');
	    $('#link').val('');
    }
}

//添加一条
$meterial.addOneText= function(){
    var html= '';
    html += '<div class="inner msg-item js_msg_item">';
    html+='<img class="msg-thumb showImgs" src="../images/demo-single.jpg"/>';
    html+='<i class="msg-thumb-size default"></i>';
    html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">新建子标题</a></h4>';
    html+='<div class="msg-edit-mask">';
    html+='<a class="icons_edit edit-white a_item" href="javascript:void(0)" onClick="$meterial.js_edit_line(this)"></a>';
    html+='<div class="spaceW20"></div>';
    html+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="$meterial.delThisWhite(this);"></a>';
    html+='</div></div>';
    $('.msg-out').append(html);
}



//删除多图文-子图文模块
$meterial.delThisWhite =function(obj){
	$meterial.save_content(edit_index);
 	var index = $('.del-white').index(obj);
 	all_data.splice(index+1,1);
 	//alert(JSON.stringify(all_data));
    $(obj).parents('.msg-item').remove();
    if(all_data[index])
    {
    	$('#title').val(all_data[index].title);
	    editor.html(all_data[index].content);
	    $('#CoverPicPath').val(all_data[index].coverPicPath);
	    $('#author').val(all_data[index].author);
	    $('#description').val(all_data[index].description);
	    $('#link').val(all_data[index].link);
    }
    else
    {
	    $('#title').val('');
	    editor.html('');
	    $('#CoverPicPath').val('');
	    $('#author').val('');
	    $('#description').val('');
	    $('#link').val('');
    }
    edit_index=index;
}

/**
 * 多图文页面上传图片
 * */
$meterial.uploadMultiImg = function()
{
	var src=$('#uploadFile').val();
	if(src!='')
	{
		//异步上传到服务器
		$.ajaxFileUpload(  
				{
					secureuri:false,  
					fileElementId:'uploadFile',  			
					dataType: 'json',	  
					type:'POST',
					url: $("#basePath").val() +"/uploading?method=uploadMeterial",
					success:function(resp)
					{
						if(resp.status=='OK')
						{
							$('#CoverPicPath').val(resp.message);
							$('.showImgs:eq('+edit_index+')').attr('src', resp.message).show();
							$('.default:eq('+edit_index+')').hide();
							var $cfile =$('#uploadFile').clone();
							$('#uploadFile').remove();
							$('#fileBlock').append($cfile);
						}
						else if(resp.status=='ERROR')
						{
							window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
						}
					}                                                                                                              
				});
	}
}


/**
 * 保存多图文回复 
 * hospitalId 医院ID
 * type 1被关注回复 2 关键字回复
 * index 当前操作的单元ID 
 * */
$meterial.saveMutlti = function(hospitalId)
{
	$meterial.save_content(edit_index);
	for(var i=0;i<all_data.length;i++)
	{
		if(all_data[i].coverPicPath==''||all_data[i].title=='')
		{
			window.wxc.xcConfirm("请完善所有图文信息的必填项", window.wxc.xcConfirm.typeEnum.info);
			return;
		}
		if(all_data[i].content.length>100000)
		{
			window.wxc.xcConfirm("正文的内容不能超出100000字", window.wxc.xcConfirm.typeEnum.info);
			return;
		}
	}
    var jsonstr = JSON.stringify(all_data);
    $.ajax({
        url: $("#basePath").val() +"/message/mixedMeterial?method=saveMulti",
        type:"POST",
        dataType:"JSON",
        data:{jsonstr:jsonstr,hospitalId:hospitalId},
        success:function(resp){
            if(resp.status == 'OK')
            {
            	window.opener.location= $("#basePath").val() +"/message/mixedMeterial?method=list&hospitalId="+hospitalId;
            	window.close();
            }
            else
            {
            	window.wxc.xcConfirm("添加失败", window.wxc.xcConfirm.typeEnum.error);
            }
        }
    });
}


/**
 * 选择图文
 * id 图文id
 * hospitalId 医院id
 * thirdType 平台类型（2支付宝 1微信）
 * type 回复类型（1被关注 2关键字）
 * */
$meterial.chooseMete = function(id,hospitalId,thirdType,type)
{
	if(type==1)
	{
		window.opener.location= $("#basePath").val() +"/message/mixedMeterial?method=getSimpleText&id="+id+"&hospitalId="+hospitalId+"&thirdType="+thirdType;
		window.close();
	}else
	{
		window.opener.setMetarial(id);
		window.close();
	}
}

/**
 * 选择图文模板
 * 
 * */
$meterial.chooseMixMetaTemp = function(id)
{
	window.opener.fillUpMixMeta(id);
	window.close();
}
