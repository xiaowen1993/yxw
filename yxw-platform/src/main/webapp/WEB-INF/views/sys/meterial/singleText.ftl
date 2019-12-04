<html>
<head>
<#include "./sys/common/common.ftl">
<!--ajax上传组件 -->
<script src="${basePath}js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${basePath}js/sys/meterial/sys.meterial.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">新建单图文消息</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space20"></div>
        <div class="row-fluid">
            <div class="widget-box">
                <div class="text-msg">
                      <div class="t-msg">
                          <div class="inner">
                              <div class="as-thumb">
                                   <h4 class="msg-title js_msgTitle">新建标题</h4>
                                   <div class="msg-info" id="currentTime"></div>
                                   <div class="msg-thumb-wrap">
                                       <img class="msg-thumb" id="showImg" src="../images/demo-single.jpg"/>
                                       <i class="msg-thumb-size default"></i>
                                   </div>
                                   <div class="msg-desc js_msgDesc">新建文章摘要</div>
                              </div>
                          </div>
                      </div>
                    <div class="t-margin">
                        <div class="t-area">
                          <i class="caret"></i>
                          <div class="inner">
                              <div class="control-group"><h4 class="title">标题</h4><div class="controls"><input maxlength="30" id="title" class="span6 js_title" type="text"  oninput="OnInput(event)" onpropertychange="OnPropChanged(event)"  value=""/></div></div>
                              <div class="control-group"><h4 class="title">作者<small class="green">(选填)</small></h4><div class="controls"><input  maxlength="20" class="span6" type="text" value="" id="author"/></div></div>
                              <div class="control-group"><h4 class="title">封面<small class="green">(大图尺寸建议900像素 * 500像素)</small></h4>
                                  <div class="controls">
                                        <div class="btn-save btn-upload">
                                        	上传图片
                                        	<div id="fileBlock">
                                        	<input class="fileupload" type="file" id="uploadFile" name="uploadFile" onchange="$meterial.uploadSingleImg();">
                                        	</div>
                                        </div>
                                        <input type="hidden" id="CoverPicPath" >
                                        <div class="btn-library">调用素材库</div>
                                        <div><label class="radio inline check"> <input type="radio" name="radio1">封面图片显示在正文中</label></div>
                                      <div class="space10"></div>
                                  </div>
                              </div>
                              <div class="control-group"><h4 class="title">摘要<small class="green">(选填)</small></h4>
                                  <div class="controls">
                                      <textarea class="span12 js_desc" rows="3" id="description" maxlength="51"></textarea>
                                  </div>
                              </div>
                              <div class="control-group"><h4 class="title">正文<small class="green">（选填）</small></h4>
                                  <div class="controls">
                                      <textarea rows="5" style=" width:380px;height:230px" id="editor_wx" name="content" ></textarea>
                                  </div>
                                  <div class="space10"></div>
                              </div>
                              <div class="control-group"><h4 class="title">添加链接<small class="green">（选填）</small></h4><div class="controls"><input class="span6" type="text" value="" maxlength="100" id="link"/></div></div>
                          </div>
                      </div>
                        <div class="space20"></div>
                        <button type="button" class="btn btn-save w130" onclick="$meterial.saveSingle('${hospitalId}');" >保存</button>
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
        $('#currentTime').text(new Date().toLocaleString());
    });
</script>
