/**
 * 消息管理js
 */

var $msg = {};

var path='';
var basePath='';

$msg.search = function() {
  document.forms[0].submit();
}

/**
 * 分页
 * */
$msg.changePage = function(pageNum, pageSize) {
	
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
	    $msg.search();
	  }
}
/**
 * 跳转编辑页面
 * */
$msg.toEdit = function(id)
{
	//window.location=path+"/user?method=toEdit&id="+id;
}

/**
 * 保存单图文回复
 * hospitalId 医院ID
 * type 1被关注回复 2 关键字回复
 * index 当前操作的单元ID 
 * third
 * */
$msg.saveSingle = function(hospitalId,type,index,flagTime,thirdType)
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
				//alert(resp.editTime);
				if(type==1){
					window.location= $("#basePath").val() +"/message/mixedMeterial?method=getSimpleText&id="+resp.id+'&hospitalId='+hospitalId+"&thirdType="+thirdType;
				}else{
					if(flagTime != null && flagTime != ''){
						window.opener.singleText(index,resp.id,title,description,coverPicPath,flagTime,resp.editTime);
					}else{
						window.opener.addSingleUnit(index,resp.id,title,description,coverPicPath);
						window.opener.boxImageText.close();
					}
					window.close();
				}
			}
			else if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm("保存失败", window.wxc.xcConfirm.typeEnum.error);
			}
		}
	});
}

/**
 * 单图文页面上传图片
 * */
$msg.uploadImg = function()
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
/********************多图文JS************************/
/**
 * 多图文页面上传图片
 * */
$msg.uploadMultiImg = function()
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
							boxImageText.close();
						}
						else if(resp.status=='ERROR')
						{
							window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
							boxImageText.close();
						}
					}                                                                                                              
				});
	}
}

//每次点击编辑按钮调用
$msg.js_edit_line = function(obj)
{
    var index = $('.a_item').index(obj);
    //保存上一个单个图文
    $msg.save_content(index);
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



//验证输入
$msg.validate = function()
{
	editor.sync();
	var title=$('#title').val();
	var coverPicPath=$('#CoverPicPath').val();
	if(title==''||coverPicPath=='')
	{
		window.wxc.xcConfirm("请填写图文信息的必填项", window.wxc.xcConfirm.typeEnum.info);
		return false;
	}else{
		return true;
	}
}

//添加一条
$msg.addOneText= function(){
    var html= '';
    html += '<div class="inner msg-item js_msg_item">';
    html+='<img class="msg-thumb showImgs" src="../images/demo-single.jpg"/>';
    html+='<i class="msg-thumb-size default"></i>';
    html+='<h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">新建子标题</a></h4>';
    html+='<div class="msg-edit-mask">';
    html+='<a class="icons_edit edit-white a_item" href="javascript:void(0)" onClick="$msg.js_edit_line(this)"></a>';
    html+='<div class="spaceW20"></div>';
    html+='<a class="icons_edit del-white" href="javascript:void(0)" onclick="$msg.delThisWhite(this);"></a>';
    html+='</div></div>';
    $('.msg-out').append(html);
}



//删除多图文-子图文模块
$msg.delThisWhite =function(obj){
	$msg.save_content(edit_index);
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
 * 保存单个内容
 * */
$msg.save_content =function(index)
{
	//验证有无完善必填信息
    //if(!$msg.validate())
   // {
   // 	return ;
   // }
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

/**
 * 保存多图文回复 
 * hospitalId 医院ID
 * type 1被关注回复 2 关键字回复
 * index 当前操作的单元ID 
 * */
$msg.saveMutlti = function(hospitalId,type,index,flagTime,thirdType)
{
	$msg.save_content(edit_index);
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
            	if(type==1)
            	{
            		//resp.message 返回的是所有的多图文的ID 以逗号分隔开
            		window.location= $("#basePath").val() +"/message/mixedMeterial?method=getMulti&ids="+resp.message+"&hospitalId="+hospitalId+"&thirdType="+thirdType;
            	}
            	else
            	{
            		if(flagTime != null && flagTime != ''){
						window.opener.MultiText(index,resp.message,all_data,flagTime);
					}else{
	            		window.opener.addMultiUnit(index,resp.message,all_data);
	            		window.opener.boxImageText.close();
					}
            		window.close();
            	}
            }
            else
            {
            	window.wxc.xcConfirm("保存失败", window.wxc.xcConfirm.typeEnum.error);
            }
        }
    });
}

/*************************被关注页面JS********************************/
/**
 * 切换到文字tab
 * */
$msg.addWord = function(obj){
	meterialType=1;
	$('#showImg').attr('src','');//清空图片tab
	$('#imageTextDiv').html('');//清空图文tab
    $(obj).parents('li').addClass("select").siblings().removeClass("select");
    $(".textArea").show();
    $(".imgTextArea").hide();
    $(".picArea").hide();
}


/**
 * 切换到图文tab
 * */
$msg.addImageText= function(obj,hospitalId){
	meterialType=3;
	$('#textContent').text('');//清空文字tab
	$('#showImg').attr('src','');//清空图片tab
	$(obj).parents('li').addClass("select").siblings().removeClass("select");
    $(".imgTextArea").show();
    $(".textArea").hide();
    $(".picArea").hide();
   boxImageText = new $Y.dialog({
        title:'选择图文',
        width:'400px',
        height:'300px',
        content:'',
        callback:function(){
            $.ajax({
                url: $("#basePath").val() +'/message/reply?method=toDialogImageText&type=1&hospitalId='+hospitalId+'&thirdType='+thirdType,
                dataType:'html',
                cache:false,
                success:function(html){
	                boxImageText.content(html);
	            	$(boxImageText.id).find('#singleBtn').bind('click',function(){
	            		window.location= $("#basePath").val() +"/message/reply?method=toSingleText&hospitalId="+hospitalId+"&type=1&index=0&thirdType="+thirdType;
	            	})
	            	$(boxImageText.id).find('#multiBtn').bind('click',function(){
	            		window.location= $("#basePath").val() +"/message/reply?method=toMulti&hospitalId="+hospitalId+"&type=1&index=0&thirdType="+thirdType;
	            	});
	            	$(boxImageText.id).find('#metarial').bind('change',function()
                    {
	                 	$('#showImg').attr('src', $(this).val());
	                 	boxPic.close();
                    });
	                $Y.ScrollBar();
//                  boxImageText.close();
                }
            });
        }
    });
}

 /**
  * 切换到图片tab
  * */
$msg.addPic=function(obj,hospitalId){
	meterialType=2;
	$('#textContent').text('');//清空文字tab
	$('#imageTextDiv').html('');//清空图文tab
	$(obj).parents('li').addClass("select").siblings().removeClass("select");
    $(".imgTextArea").hide();
    $(".textArea").hide();
    $(".picArea").show();
     boxPic=new $Y.dialog({
        title:'选择图片',
        width:'400px',
        height:'220px',
        content:'',
        callback:function(boxPic){
            $.ajax({
                url: $("#basePath").val() +'/message/reply?method=toDialogPic&hospitalId='+hospitalId,
                dataType:'html',
                cache:false,
                success:function(html){
                    boxPic.content(html);
                    $(boxPic.id).find('#uploadFile').bind('change',function()
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
                    							$('#showImg').attr('src', resp.message);
                    							boxPic.close();
                    						}
                    						else if(resp.status=='ERROR')
                    						{
                    							window.wxc.xcConfirm(resp.message, window.wxc.xcConfirm.typeEnum.error);
                    							boxPic.close();
                    						}
                    					}                                                                                                              
                    				});
                    	}
            		});
                    $(boxPic.id).find('#metarialPath').bind('change',function()
                    {
                    	$('#showImg').attr('src', $(this).val());
                    	boxPic.close();
                    });
                    $Y.ScrollBar();
//                  boxPic.close();
                }
            });
        }
    });
}

/**
 * 被添加关注页面-----保存回复
 * */
$msg.saveReply=function(type,hospId)
{
	var picPath=$('#showImg').attr('src');
	var textContent=$('#textContent').text();
	if(textContent!=''&&textContent.length>600)
	{
		window.wxc.xcConfirm('文字内容已超出限制的长度', window.wxc.xcConfirm.typeEnum.error);
		return;
	}
	if(picPath==''&&textContent.trim()==''&&singleMeterialId==''&&multiMeterialIds=='')
	{
		window.wxc.xcConfirm('您未设置任何自动回复内容', window.wxc.xcConfirm.typeEnum.error);
		return;
	}
	$.ajax({
		url: $("#basePath").val() +"/message/reply?method=saveOneReply",
		dataType:'json',
		type:'POST',
		data:{
			picPath:picPath,
			textContent:textContent,
			contentType:meterialType,
			singleMeterialId:singleMeterialId,
			multiMeterialIds:multiMeterialIds,
			hospitalId:hospId,
			type:1,
			thirdType:type
		},
		success:function(resp)
		{
			if(resp.status=='OK')
			{
				window.wxc.xcConfirm('保存成功', window.wxc.xcConfirm.typeEnum.success);
				window.location= $("#basePath").val() +"/message/reply?method=list";
			}
			else if(resp.status=='ERROR')
			{
				window.wxc.xcConfirm('保存失败', window.wxc.xcConfirm.typeEnum.error);
			}
		}
	});
}


/**
 * 切换被关注回复
 * */
$msg.toFocusedReply =function()
{
	window.location =  $("#basePath").val() +"/message/reply?method=toFocusedReply&hospitalId="+hospitalId+"&thirdType="+thirdType;
}
/**
 * 切换关键字回复
 * */
$msg.toKeywordReply =function()
{
	window.location =  $("#basePath").val() +"/message/reply?method=toKeywordReply&hospitalId="+hospitalId+"&thirdType="+thirdType;
}

/***
 * 新建图文页面---调用素材库
 * */
$msg.invokeMixMetaLib = function(hospitalId,metarialType)
{
	var iTop = (window.screen.availHeight-30)/2; //获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth-10-1400)/2; //获得窗口的水平位置;
	window.open($('#basePath').val()+"message/meterial?method=choosePicList&hospitalId="+hospitalId+"&metarialType="+metarialType,
			"_blank","toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no,height="+window.screen.availHeight+", width=1400, top="+iTop+", left="+iLeft);
}

/**
 * 设置选中图文的模版
 * */
$msg.fillUpMixMeta = function(id)
{
	$.ajax({
		url:$('#basePath').val()+"message/mixMeterial?method=getMeterialById",
		dataType:'json',
		type:'POST',
		data:{id:id},
		success:function(resp)
		{
			if(resp.status=='OK')
			{
				var obj=resp.message;
				
			}else
			{
				window.wxc.xcConfirm('保存失败', window.wxc.xcConfirm.typeEnum.error);
			}
		}
	});
}

function setMetarial(path,metarialType)
{
	if(metarialType=='single')
	{
		$('#CoverPicPath').val(path);
		$('#showImg').attr('src', path).show();
		$('.default').hide();
	}else{
		$('#CoverPicPath').val(path);
		$('.showImgs:eq('+edit_index+')').attr('src', path).show();
		$('.default:eq('+edit_index+')').hide();
	}
}



