<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/message/sys.msg.js"></script>
<script type="text/javascript" src="${basePath}mobileApp/js/common/base64.js"></script>
<!--ajax上传组件 -->
<script src="${basePath}js/ajaxfileupload.js"></script>
</head>
<body> 
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">编辑多图文消息</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space20"></div>
        <div class="row-fluid">
            <div class="widget-box">
                <div class="text-msg">
                    <div class="t-msg">
                        <div class="inner js_msg_item">
                            <div class="multi-thumb">
                                <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${mixedMeterial.title}</a></h4>
                                <div class="msg-thumb-wrap">
                                    <img class="msg-thumb showImgs" style="display:block" src="${mixedMeterial.coverPicPath}" />
                                    <input type="hidden" id="parentId" value="${mixedMeterial.id}">
                                    <i class="msg-thumb-size default"></i>
                                </div>
                                <div class="msg-edit-mask"><a class="icons_edit edit-white a_item" href="javascript:void(0)"  onClick="js_edit(this)"></a></div>
                            </div>
                        </div>
                        <#if (mixedMeterial.subMixedMeterialList?size>0)>
                        	<#list mixedMeterial.subMixedMeterialList as subMixedMeterial>
		                        <div class="msg-out clearfix">
		                             <div class="inner msg-item js_msg_item">
			                            <img class="msg-thumb showImgs" style="display:block" src="${subMixedMeterial.coverPicPath}"  />
			                            <i class="msg-thumb-size default" style="display:none"></i>
			                            <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${subMixedMeterial.title}</a></h4>
			                            <div class="msg-edit-mask">
			                                <a class="icons_edit edit-white a_item" href="javascript:void(0)"  onClick="js_edit(this)"></a>
			                                <div class="spaceW9"></div>
			                                <a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>
			                            </div>
		                       		 </div>
		                        </div>
                        	</#list>
                        </#if>
                        <div class="msg-out clearfix">
                             <div class="inner msg-item js_msg_item">
	                            <img class="msg-thumb showImgs" src="../images/demo-single.jpg"  />
	                            <i class="msg-thumb-size default"></i>
	                            <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">新建子标题</a></h4>
	                            <div class="msg-edit-mask">
	                                <a class="icons_edit edit-white a_item" href="javascript:void(0)"  onClick="js_edit(this)"></a>
	                                <div class="spaceW9"></div>
	                                <a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>
	                            </div>
                       		 </div>
                        </div>
                        <div class="inner msg-add">
                            <a href="javascript:void(0);" onclick="$msg.addOneText();">
                                <i class="icons_edit add_gray">增加一条</i>
                            </a>
                        </div>
                    </div>
                    <div class="t-margin">
                        <div class="t-area">
                        <i class="caret"></i>
                        <div class="inner">
                            <div class="control-group"><h4 class="title">标题</h4><div class="controls"><input class="span6 js_title" type="text" id="title"  oninput="OnInput(event)" onpropertychange="OnPropChanged(event)"  value="${mixedMeterial.title}"/></div></div>
                            <div class="control-group"><h4 class="title">作者<small class="green">(选填)</small></h4><div class="controls"><input class="span6" type="text" value="${mixedMeterial.author}" id="author"/></div></div>
                            <div class="control-group"><h4 class="title">封面<small class="green">(大图尺寸建议900像素 * 500像素)</small></h4>
                                <div class="controls">
                                    <div class="btn-save btn-upload">
                                    	上传图片
                                    	<div id="fileBlock">
                                    	<input class="fileupload" type="file" id="uploadFile" name="uploadFile" onchange="$msg.uploadMultiImg();">
                                    	</div>
                                    </div>
                                    <input type="hidden" id="CoverPicPath" value="${mixedMeterial.coverPicPath}">
                                    <input type="hidden" id="mixedId" value="${mixedMeterial.id}">
                                    <div class="btn-library"  onclick="$msg.invokeMixMetaLib('${hospitalId}','multi');">调用素材库</div>
                                    <div><label class="radio inline check"> <input type="radio" name="radio1">封面图片显示在正文中</label></div>
                                    <div class="space10"></div>
                                </div>
                            </div>
                            <div class="control-group"><h4 class="title">摘要<small class="green">(选填)</small></h4>
                                <div class="controls">
                                    <textarea class="span12 js_desc" rows="3"  id="description">${mixedMeterial.description}</textarea>
                                </div>
                            </div>
                            <div class="control-group"><h4 class="title">正文</h4>
                                <div class="controls">
                                    <textarea rows="5" style=" width:380px;height:230px" id="editor_wx" name="content">${mixedMeterial.content}</textarea>
                                </div>
                                <div class="space10"></div>
                            </div>
                            <div class="control-group"><h4 class="title">添加原文链接<small class="green">（选填）</small></h4><div class="controls"><input class="span6" type="text" value="${mixedMeterial.link}"  id="link"/></div></div>
                        </div>
                    </div>
                        <div class="space20"></div>
                        <button type="button" class="btn btn-save w130" onclick="editMutlti('${flagTime}');">保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
<!--优先级不高JS放后面-->
<script type="text/javascript" charset="utf-8" src="../js/kindeditor/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="../js/kindeditor/lang/zh_CN.js"></script>
<script language="javascript">
	//当前编辑到第几个
	var edit_index = 0;
	//所有待提交的图文内容
	//每个元素都是obj,包含title、content、link、desc等属性
	var all_data = [];
	var del_data=new Array();
	var base64 =new Base64();
	path='${request.contextPath}';
	var mixmeterialStr=${mixedMeterial};
	var base64=new Base64();
    var editor;
    KindEditor.ready(function(K) {
        editor = K.create('#editor_wx', {
            width : '100%',
             filterMode:false,
            items: ['source', '|', 'undo', 'redo', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertunorderedlist', 'indent', 'outdent', 'selectall', 'title', 'fontname', 'fontsize', 'bold', '|',
                'italic', 'underline', 'strikethrough', 'removeformat', '|', 'image',
                'advtable', 'hr', 'emoticons', 'link', 'unlink','vote']
        });
    });
    $(document).ready(function(){
        //标题随输入变动
        $('.js_title').bind('input propertychange', function() {
            $('.js_msgTitle:eq('+edit_index+')').html($(this).val());
        });
       
        //填充原来的图文信息
        var obj = null;
        var submixList=mixmeterialStr.subMixedMeterialList;//子图文集合
        if(mixmeterialStr!=null)
        {
        	obj=new Object();
        	obj.id=mixmeterialStr.id;
        	obj.title=base64.decode(mixmeterialStr.title);
		    obj.content=base64.decode(mixmeterialStr.content);
		    obj.coverPicPath=mixmeterialStr.coverPicPath;
		    obj.author=base64.decode(mixmeterialStr.author);
		    obj.description=base64.decode(mixmeterialStr.description);
		    obj.link=mixmeterialStr.link;
		    obj.type=2;
		    obj.state=1;
		    all_data[0]=obj;
        }
        if(submixList!=null&&submixList.length>0)
        {
        	for(var i=0;i<submixList.length;i++)
        	{
        		obj=new Object();
	        	obj.id=submixList[i].id;
	        	obj.title=base64.decode(submixList[i].title);
			    obj.content=base64.decode(submixList[i].content);
			    obj.coverPicPath=submixList[i].coverPicPath;
			    obj.author=base64.decode(submixList[i].author);
			    obj.description=base64.decode(submixList[i].description);
			    obj.link=submixList[i].link;
			    obj.type=2;
			    obj.state=1;
			    all_data[i+1]=obj;
        	}
        }
    });
    
    
	function editMutlti(flagTime)
	{
		save_content(edit_index);
		for(var i=0;i<all_data.length;i++)
		{
			if(all_data[i].coverPicPath==''||all_data[i].title=='')
			{
				window.wxc.xcConfirm("请完善所有图文信息的必填项", window.wxc.xcConfirm.typeEnum.info);
				return;
			}
			if(all_data[i].content.length>100000)
			{
				window.wxc.xcConfirm("正文的内容不能超出10000字", window.wxc.xcConfirm.typeEnum.info);
				return;
			}
		}
	    var jsonstr = JSON.stringify(all_data);
	    var parentId=$('#parentId').val();
	    $.ajax({
	        url:path+"/message/mixedMeterial?method=editMulti",
	        type:"POST",
	        dataType:"JSON",
	        data:{jsonstr:jsonstr,parentId:parentId,delIds:del_data.join()},
	        success:function(resp){
	            if(resp.status == 'OK')
	            {
	            	if(flagTime != null && flagTime != ''){
						window.opener.MultiText(1,parentId,all_data,flagTime);
					}
            		window.close();
	            }
	            else
	            {
	              	window.wxc.xcConfirm('保存失败', window.wxc.xcConfirm.typeEnum.error);
	            }
	        }
	    });
	}
	
	
	function js_edit(obj)
	{
	    var index = $('.a_item').index(obj);
	    //保存上一个单个图文
	   	save_content(index);
	    //填充当前的选择图文
	    if(all_data[index])
	    {
	    	$('#mixedId').val(all_data[index].id);
	    	$('#title').val(all_data[index].title);
		    editor.html(all_data[index].content);
		    $('#CoverPicPath').val(all_data[index].coverPicPath);
		    $('#author').val(all_data[index].author);
		    $('#description').val(all_data[index].description);
		    $('#link').val(all_data[index].link);
	    }
	    else
	    {
		    $('#mixedId').val('');
		    $('#title').val('');
		    editor.html('');
		    $('#CoverPicPath').val('');
		    $('#author').val('');
		    $('#description').val('');
		    $('#link').val('');
	    }
	}
	
	
	/**
	 * 保存单个内容
	 * */
	function save_content(index)
	{
		//验证有无完善必填信息
	    if(!$msg.validate())
	    {
	    	return;
	    }
	    var obj ={};
	    obj.id=$('#mixedId').val();
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
	
	
	function delThisWhite(obj)
	{
		save_content(edit_index);
	 	var index = $('.del-white').index(obj);
	 	console.info(index);
	 	console.info(all_data[index+1].id);
	 	//待删除的子图文ID
	 	if(all_data[index+1].id)
	 	{
	 		del_data.push(all_data[index+1].id);
	 	}
	 	all_data.splice(index+1,1);
	    $(obj).parents('.msg-item').remove();
	    if(all_data[index])
	    {
	    	$('#mixedId').val(all_data[index].id);
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
	
</script>
