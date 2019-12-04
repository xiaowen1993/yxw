<html>
<head>
    <#include "/sys/common/common.ftl">
    <title>新建推送</title>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">新增运营内容</h3></div>
    </div>

    <div class="container-fluid">
        <div class="row-fluid">

            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <!--内容-->
                        <div class="row-layout">
                            <form class="form-horizontal" id="saveForm" method="post">
                                <div class="space30"></div>
                                <div class="control-group">
                                    <label class="control-label" >内容标题</label>
                                    <div class="controls">
                                        <input type="text" class="span5" id="title" name="title" ext-null-hint="请输入标题" value="${carrieroperator.title}" maxlength="15"/>
                                        <input type="hidden" name="id" id="id" value="${carrieroperator.id}"/>
                                        <input type="hidden" name="sortingList" id="sortingList" value="${sortingList}"/>
                                        <input type="hidden" name="sortingBijiao" id="sortingBijiao" value="${carrieroperator.sorting}"/>
                                        <small class="grey">(0/15)</small>
                                    </div>
                                </div>
                                
                      

                                <div class="control-group">
                                    <label class="control-label">运营位置</label>
                                    <div class="controls" style="padding-top: 8px;">
	                                        <label class="radio inline <#if carrieroperator?exists><#if carrieroperator.operationPosition == "1">check</#if><#else>check</#if>"> <input type="radio" <#if carrieroperator?exists><#if carrieroperator.operationPosition == "1">checked</#if><#else>checked</#if> value="1" name="operationPosition">启动页</label>
	                                        <label class="radio inline <#if carrieroperator?exists><#if carrieroperator.operationPosition == "2">check</#if></#if>"> <input type="radio" <#if carrieroperator?exists><#if carrieroperator.operationPosition == "2">checked</#if></#if> value="2" name="operationPosition">首页轮播</label>
	                                    
                                    </div>
                                </div>
                                
 								<div class="control-group" id="operationPosition_1" <#if carrieroperator?exists><#if carrieroperator.operationPosition == "2">style="display: none;"</#if></#if>>
                                    <label class="control-label">启动图</label>
                                    <div style="display: none;"><input type="file" id="uploadFile" name="uploadFile" onchange="$app.carrieroperator.uploadIcon(this);" /></div>
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic640x960" value="${carrieroperator.starPtic640x960}"  id="starPtic640x960" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为640*960的启动图" readonly/>
                                        <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic640x960'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸640*960)</small>
                                    </div>
                                    <div class="space25"></div>
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic640x1136" value="${carrieroperator.starPtic640x1136}"  id="starPtic640x1136" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为640*1136的启动图"  readonly/>
                                       	<span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic640x1136'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸640*1136)</small>
                                    </div>
                                    <div class="space25"></div>
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic750x1334" value="${carrieroperator.starPtic750x1334}"  id="starPtic750x1334" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为750*1334的启动图"  readonly/>
                                        <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic750x1334'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸750*1334)</small>
                                    </div>
                                    <div class="space25"></div>
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic1242x2208" value="${carrieroperator.starPtic1242x2208}"  id="starPtic1242x2208" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为1242*2208的启动图"  readonly/>
                                        <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic1242x2208'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸1242*2208)</small>
                                    </div>
                                    
                                    <div class="space25"></div><div class="space25"></div>
                                    
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic320x480" value="${carrieroperator.starPtic320x480}"  id="starPtic320x480" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为320*480的启动图"  readonly/>
                                       	<span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic320x480'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸320*480)</small>
                                    </div>
                                    <div class="space25"></div>
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic480x800" value="${carrieroperator.starPtic480x800}"  id="starPtic480x800" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为480*800的启动图"  readonly/>
                                        <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic480x800'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸480*800)</small>
                                    </div>
                                    <div class="space25"></div>
                                    <div class="controls">
                                        <input type="text" class="span5" name="starPtic1080x1920" value="${carrieroperator.starPtic1080x1920}"  id="starPtic1080x1920" ext-visible-id="operationPosition_1" ext-null-hint="请上传尺寸为1080*1920的启动图"  readonly/>
                                        <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'starPtic1080x1920'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
                                        <small class="grey">(图片尺寸1080*1920)</small>
                                    </div>

                                </div>
                                <div class="control-group" id="operationPosition_2" <#if carrieroperator?exists><#if carrieroperator.operationPosition == "1">style="display: none;"</#if><#else>style="display: none;"</#if>>
                                    <label class="control-label">轮播图</label>
                                    <div class="controls">
	                                    <input type="text" class="span5" name="shufflingPic" value="${carrieroperator.shufflingPic}"  id="shufflingPic" ext-visible-id="operationPosition_2" ext-null-hint="请上传轮播图"  readonly/>
	                                    <span class="uploadBtn-warp">
                                        	<button class="btn btn-save" onclick="$app.carrieroperator.picId = 'shufflingPic'; $('#uploadFile').click(); return false;">点击上传</button>
                                        </span>
	                                    <small class="grey">(图片尺寸640*260)</small>
                                	</div>
                                </div>


                                
                                <div class="control-group">
                                	<label class="control-label">后续动作</label>
                                	
                                    <div class="controls" style="padding-top: 8px;">
                                        <label class="radio inline <#if carrieroperator?exists><#if carrieroperator.contentType == "html">check</#if><#else>check</#if>"> <input type="radio" <#if carrieroperator?exists><#if carrieroperator.contentType == "html">checked</#if><#else>checked</#if> value="html" name="contentType">阅读内容</label>
                                        <label class="radio inline <#if carrieroperator?exists><#if carrieroperator.contentType == "url">check</#if></#if>"> <input type="radio" <#if carrieroperator?exists><#if carrieroperator.contentType == "url">checked</#if></#if> value="url" name="contentType">打开链接</label>
                                    </div>
                                    <div class="space25"></div>
                                    <div class="controls" id="contentType_html" <#if carrieroperator?exists><#if carrieroperator.contentType == "url">style="display: none;"</#if></#if>>
                                        <div class="span8">
                                            <textarea rows="5" style=" width:380px;height:230px" id="editor_wx"><#if carrieroperator?exists><#if carrieroperator.contentType == "html">${carrieroperator.content}</#if></#if></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="control-group" id="contentType_url" <#if carrieroperator?exists><#if carrieroperator.contentType == "html">style="display: none;"</#if><#else>style="display: none;"</#if>>
                                    <label class="control-label"></label>
                                    <div class="controls">
                                        <input type="text" class="span5" id="url" placeholder="请输入链接地址" value="<#if status?exists><#if carrieroperator.contentType == "url">${carrieroperator.content}</#if></#if>"/>
                                        <small class="grey">请输入链接地址</small>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">内容排序</label>
                                    <div class="controls">
                                        <input type="number" class="span3"  ext-null-hint="请输入内容排序"  onblur="$app.carrieroperator.checkSorting();" value="${carrieroperator.sorting}"  name="sorting" id="sorting" placeholder=""/>
                                        <small class="grey">（数字越小，排序越靠前）</small>
                                    </div>
                                </div>
                                <div class="control-group"><label class="control-label">发布状态</label>
                                    <div class="controls" style="padding-top: 8px;">
                                        <label class="radio inline  <#if carrieroperator?exists><#if carrieroperator.status == "0">check</#if><#else>check</#if>"> <input type="radio"  <#if status?exists><#if carrieroperator.status == "0">checked</#if><#else>checked</#if> value="0" name="status">发布</label>
                                        <label class="radio inline  <#if carrieroperator?exists><#if carrieroperator.status == "1">check</#if></#if>"> <input type="radio"  <#if status?exists><#if carrieroperator.status == "1">checked</#if></#if> value="1" name="status">不发布</label>
                                    </div>
                                </div>

                        
                            </form>
                        </div>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
            

        	<div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-save" onclick="$app.carrieroperator.save()">保存</button>
                </div>
            </div>
     
            

        </div>
    </div>
</div>

<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

<script type="text/javascript" charset="utf-8" src="${basePath}js/kindeditor/kindeditor.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}js/kindeditor/lang/zh_CN.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}js/My97DatePicker/WdatePicker.js"></script>
<script>
	var editor;
    KindEditor.ready(function(K) {
        editor =  K.create('#editor_wx', {
            width : '100%',
            items: ['justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'insertunorderedlist', 
            'indent', 'outdent', 'title', 'fontname', 'fontsize', 
            'bold', '|', 'italic', 'underline', 'strikethrough', 'removeformat', 
            '|', 'image', 'advtable', 'hr', 'link', 'unlink'],
            uploadJson : '${basePath}js/kindeditor/jsp/upload_json.jsp',
            fileManagerJson : '${basePath}js/kindeditor/jsp/file_manager_json.jsp',
            allowFileManager : true,
            allowImageRemote : false
        });
    });
    
    $(function() {
    	$("[name='contentType']").click(function() {
    		var checkVal = $(this).val();
    		
    		$("div[id^='contentType_']").hide();  
    		$("#contentType_"+checkVal).show();
    	});
    	
    	$("[name='operationPosition']").click(function() {
    		var checkVal = $(this).val();
    		
    		$("div[id^='operationPosition_']").hide();  
    		$("#operationPosition_"+checkVal).show();
    	});
    	

    	
    	$("[maxlength]").keyup(function() {
    		var maxlength = $(this).attr("maxlength");
    		var length = $(this).val().length;
    		if (length > maxlength) {
    			$(this).val($(this).val().substring(0, maxlength - 1));
    		} else {
    			$(this).siblings(1).text("(" + length + "/" + maxlength + ")");
    		}
    	});
    });
</script>

<script src="${basePath}js/sys/app/carrieroperator/sys.app.carrieroperator.js"></script>
<script src="${basePath}js/sys/app/sys.app.common.js"></script>
<script src="${basePath}js/ajaxfileupload.js"></script>
</body>
</html>