<html>
<head>
<#include "./sys/common/common.ftl">
<!--ajax上传组件 -->
<script src="${basePath}js/ajaxfileupload.js"></script>
<script src="${basePath}js/artTxtCount.js"></script>
<script type="text/javascript" src="${basePath}js/sys/message/sys.msg.js"></script>
<style>
#msgtips { color:#999; padding:0 5px; }
#msgtips strong { color:#1E9300; }
#msgtips .js_txtFull strong { color:#F00; }
</style>
<!--content str-->
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">支付宝消息管理</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button class="btn btn-ok w165 select" onclick="$msg.toFocusedReply();">被添加自动回复</button>
                    <button class="btn btn-ok w165" onclick="$msg.toKeywordReply();">关键词自动回复</button>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>

                <!--规则 str-->
                <div class="widget-content ">
                    <div class="row-fluid">
                        <!--新规则 str-->
                        <div class="rule-msg">
                            <div class="r-title ruleBg">
                                <ul class="tab_navs">
                                    <li class="tab_text select"><a href="javascript:void(0);" onclick="$msg.addWord(this);">文字</a></li>
                                   <!-- <li class="tab_pic"><a href="javascript:void(0);" onclick="$msg.addPic(this);">图片</a></li>-->
                                    <li class="tab_imagetext"><a href="javascript:void(0);" onclick="$msg.addImageText(this,'${hospitalId}');">图文消息</a></li>
                                </ul>
                            </div>
                            <div class="r-rule tab_panel">
                                <!--文字 str-->
                                <div class="tab_content textArea">
                                   <div class="no_extra">
                                       <div class="edit_area clearfix" id="textContent" onpaste="replace_fn(this)" contenteditable="true" style="overflow-y: auto; overflow-x: hidden;">
                                       	<#if reply.contentType==1>
                                       		${reply.content}
                                       	</#if>
                                       </div>
                                       <div class="editor_toolbar clearfix"><span class="editor_tip" id="msgtips" >还可以输入 600 字</span></div>
                                   </div>
                                </div>
                                <!--文字 end-->
                                <!--图片 str-->
                               <!-- <div class="tab_content picArea" style="display: none">
                                    <div class="edit_area clearfix">
                                        <img class="replyImg"  src="<#if reply.contentType==2>${reply.picPaths}</#if>" id="showImg" height="100">
                                    </div>
                                </div>-->
                                <!--图片 end-->
                                <!--图文信息 str-->
                                <div class="tab_content imgTextArea"  style="display: none">
                                    <div class="edit_area clearfix" id="imageTextDiv">
                                      <!--单图文 str-->
                                      	<#if mixedMeterial!=null>
                                      		<#if mixedMeterial.type==1>
	                                        <div class="t-msg">
	                                            <div class="inner">
	                                                <div class="as-thumb">
	                                                    <h4 class="msg-title js_msgTitle">${mixedMeterial.title}</h4>
	                                                    <div class="msg-info">${mixedMeterial.ct?string('yyyy-MM-dd HH:mm:ss')}</div>
	                                                    <div class="msg-thumb-wrap">
	                                                        <img class="msg-thumb" style="display:block" src="${mixedMeterial.coverPicPath}"/>
	                                                        <i class="msg-thumb-size default" style="display:none"></i>
	                                                    </div>
	                                                    <div class="msg-desc js_msgDesc">${mixedMeterial.description}</div>
	                                                </div>
	                                            </div>
	                                        </div>
	                                        </#if>
                                      <!--单图文 end-->
                                      <!--多图文 str-->
                                      		<#if mixedMeterial.type==2>
		                                        <div class="t-msg">
	                                            <div class="inner">
	                                                <div class="multi-thumb">
	                                                    <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${mixedMeterial.title}</a></h4>
	                                                    <div class="msg-thumb-wrap">
	                                                        <img class="msg-thumb" style="display:block" src="${mixedMeterial.coverPicPath}"/>
	                                                        <i class="msg-thumb-size default" style="display:none"></i>
	                                                    </div>
	                                                    <div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>
	                                                </div>
	                                            </div>
		                                        <#if mixedMeterial.subMixedMeterialList?if_exists>
	                                      			 <#if (mixedMeterial.subMixedMeterialList?size>0)>
				                                        <#list mixedMeterial.subMixedMeterialList as subMixedMeterial>
					                                            <div class="msg-out clearfix">
					                                                <div class="inner msg-item">
					                                                    <img class="msg-thumb" src="${subMixedMeterial.coverPicPath}" style="display:block"/>
					                                                    <i class="msg-thumb-size default" style="display:none"></i>
					                                                    <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${subMixedMeterial.title}</a></h4>
					                                                    <div class="msg-edit-mask">
					                                                        <a class="icons_edit edit-white" href="javascript:void(0)"></a>
					                                                        <div class="spaceW9"></div>
					                                                        <a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>
					                                                    </div>
					                                                </div>
					                                            </div>
				                                        </#list>
					                                </#if>
					                            </#if>
		                                        </div>
		                                    </#if>
                                        </#if>
                                      <!--多图文 end-->
                                    </div>
                                </div>
                                <!--图文信息 end-->
                            </div>
                        </div>
                        <!--新规则 end-->
                        <div class="space20"></div>
                        <div class="clearfix">
                            <button type="button" class="btn btn-save" id="saveBtn"  onclick="$msg.saveReply(2,'${hospitalId}');">保存</button>
                            <div class="spaceW15"></div>
                            <!--<button type="button" class="btn btn-remove">取消</button>-->
                        </div>
                        <div class="space10"></div>
                    </div>
                </div>
                <!--规则 end-->
                <div class="space20"></div>


            </div>
        </div>

    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
<script type="text/javascript">
	var meterialType=1; //回复所绑定素材的类型 1 文字 2 图片 3 图文
	var backFromImageText='${backFromImageText}';
	var singleMeterialId='${singleMeterialId}';//单图文ID
	var multiMeterialIds='${multiMeterialIds}';//多图文IDS
	path='${request.contextPath}';
	var hospitalId='${hospitalId}';
	var thirdType='${thirdType}';
	var replyType='${reply.contentType}';
	
	$(function(){
		//超出字数限制
		$('#textContent').artTxtCount($('#msgtips'), 600);
		if(backFromImageText==1)
		{
			//切换到图文
			meterialType=3;
			$('.tab_imagetext').addClass("select").siblings().removeClass("select");
	        $(".imgTextArea").show();
	        $(".textArea").hide();
	        $(".picArea").hide();
		}
		if(replyType==1)
		{
			meterialType=1;
			$('li.tab_text').addClass("select").siblings().removeClass("select");
		    $(".imgTextArea").hide();
		    $(".textArea").show();
		    $(".picArea").hide();
		}else if(replyType==2)
		{
			meterialType=2;
			$('li.tab_pic').addClass("select").siblings().removeClass("select");
		    $(".imgTextArea").hide();
		    $(".textArea").hide();
		    $(".picArea").show();
		}else if(replyType==3)
		{
			meterialType=3;
			$('li.tab_imagetext').addClass("select").siblings().removeClass("select");
		    $(".imgTextArea").show();
		    $(".textArea").hide();
		    $(".picArea").hide();
		}
	});
</script>