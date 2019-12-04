<html>
<head>
<#include "./sys/common/common.ftl">
<!--ajax上传组件 -->
<script src="${basePath}js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basePath}js/sys/message/sys.msg.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
       	<div class="widget-title"><h3 class="title">编辑单图文消息</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space20"></div>
        <div class="row-fluid">
            <div class="widget-box">
                <div class="text-msg">
                      <div class="t-msg">
                          <div class="inner">
                              <div class="as-thumb">
                                   <h4 class="msg-title js_msgTitle">${mixedMeterial.title}</h4>
                                   <div class="msg-info" >${mixedMeterial.et?string('yyyy-MM-dd HH:mm:ss')}</div>
                                   <div class="msg-thumb-wrap">
                                       <img class="msg-thumb " style="display:block" id="showImg" src="${mixedMeterial.coverPicPath}"/>
                                       <i class="msg-thumb-size default"></i>
                                   </div>
                                   <div class="msg-desc js_msgDesc">${mixedMeterial.description}</div>
                              </div>
                          </div>
                      </div>
                  	<form id="frm">
	                    <div class="t-margin">
	                        <div class="t-area">
	                          <i class="caret"></i>
	                          <div class="inner">
	                              <div class="control-group"><h4 class="title">标题</h4><div class="controls"><input name="title" maxlength="30" class="span6 js_title" type="text"  oninput="OnInput(event)" onpropertychange="OnPropChanged(event)"  value="${mixedMeterial.title}"/></div></div>
	                              <div class="control-group"><h4 class="title">作者<small class="green">(选填)</small></h4><div class="controls"><input class="span6" type="text" value="${mixedMeterial.author}" name="author" maxlength="20"/></div></div>
	                              <div class="control-group"><h4 class="title">封面<small class="green">(大图尺寸建议900像素 * 500像素)</small></h4>
	                                  <div class="controls">
	                                        <div class="btn-save btn-upload">
	                                        	上传图片
	                                        	<div id="fileBlock">
	                                        	<input class="fileupload" type="file" id="uploadFile" name="uploadFile" onchange="$msg.uploadImg();">
	                                        	</div>
	                                        </div>
	                                        <input type="hidden" id="CoverPicPath" name="coverPicPath" value="${mixedMeterial.coverPicPath}">
	                                        <input type="hidden" name="id" value="${mixedMeterial.id}">
	                                        <div class="btn-library">调用素材库</div>
	                                        <div><label class="radio inline check"> <input type="radio" name="radio1">封面图片显示在正文中</label></div>
	                                      <div class="space10"></div>
	                                  </div>
	                              </div>
	                              <div class="control-group"><h4 class="title">摘要<small class="green">(选填)</small></h4>
	                                  <div class="controls">
	                                      <textarea class="span12 js_desc" rows="3" name="description" maxlength="51">${mixedMeterial.description}</textarea>
	                                  </div>
	                              </div>
	                              <div class="control-group"><h4 class="title">正文</h4>
	                                  <div class="controls">
	                                      <textarea rows="5" style=" width:380px;height:230px" id="editor_wx" name="content">${mixedMeterial.content}</textarea>
	                                  </div>
	                                  <div class="space10"></div>
	                              </div>
	                              <div class="control-group"><h4 class="title">添加原文链接<small class="green">（选填）</small></h4><div class="controls"><input class="span6" type="text" value="${mixedMeterial.link}" name="link"  maxlength="100" /></div></div>
	                          </div>
	                      	</div>
	                        <div class="space20"></div>
	                        <button type="button" class="btn btn-save w130" onclick="editSaveSingle();" >保存</button>
	                    </div>
                    </form>
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
    var editor;
    KindEditor.ready(function(K) {
        editor = K.create('#editor_wx', {
            width : '100%',
            items: ['source', '|', 'undo', 'redo', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertunorderedlist', 'indent', 'outdent', 'selectall', 'title', 'fontname', 'fontsize', 'bold', '|',
                'italic', 'underline', 'strikethrough', 'removeformat', '|', 'image',
                'advtable', 'hr', 'emoticons', 'link', 'unlink','vote']
        });
    });

    $(document).ready(function(){
        path='${request.contextPath}';
        //标题
        $('.js_title').bind('input propertychange', function() {
            $('.js_msgTitle').html($(this).val());
        });
        //摘要
        $('.js_desc').bind('input propertychange', function() {
            $('.js_msgDesc').html($(this).val());
        });
      //  $('#currentTime').text(new Date().toLocaleString());
    });
    
    //保存编辑内容
    function editSaveSingle()
    {
    	editor.sync();
	    $.ajax({
			url:path+"/message/mixedMeterial?method=editSingle",
			dataType:'json',
			type:'POST',
			data: $("#frm").serialize(),   
			success:function(resp)
			{
				if(resp.status=='OK')
				{
					window.opener.location.href="${basePath}message/mixedMeterial?method=list&hospitalId=${hospitalId}";
					window.close();
				}
				else if(resp.status=='ERROR')
				{
					window.wxc.xcConfirm('保存失败', window.wxc.xcConfirm.typeEnum.info);
				}
			}
		});
    }
    function formatterDateTime(date) {
        var datetime = date.getFullYear()
                + "-"// "年"
                + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                        + (date.getMonth() + 1))
                + "-"// "月"
                + (date.getDate() < 10 ? "0" + date.getDate() : date
                        .getDate())
                + " "
                + (date.getHours() < 10 ? "0" + date.getHours() : date
                        .getHours())
                + ":"
                + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                        .getMinutes())
                + ":"
                + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                        .getSeconds());
        return datetime;
    }
</script>
