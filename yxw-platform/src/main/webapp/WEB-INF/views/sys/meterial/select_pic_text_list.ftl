<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/meterial/sys.meterial.js"></script>
<script type="text/javascript" src="${basePath}js/sys/user/sys.user.js"></script>
<script src="${basePath}js/ajaxfileupload.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">选择图文</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row-fluid">
                <div class="pull-right" id="search">
                    <form method="post" action="">
            			<input type="hidden" name="pageNum" value="${mixedMeterials.pageNum}" />
            			<input type="hidden" name="pageSize" value="${mixedMeterials.pageSize}" />
            			<input type="hidden" name="pages" value="${mixedMeterials.pages}" />
            			<input type="hidden" name="total" value="${mixedMeterials.total}" />
            			</br></br>
                	</form>
                </div>
            </div>
            <div class="widget-box">
                <div class="widget-content form-check meterial fore">
                    <div class="row-fluid">
                       <!--图文素材 str-->
                        <div class="innerw clearfix">
                        	<#if (mixedMeterials.list?size>0)>
                            	<#list mixedMeterials.list as mixedMeterial>
                            		<#if mixedMeterial.type==1>
                            			<div class="t-msg" style="margin-bottom:8px;">
		                                    <div class="inner">
		                                        <div class="as-thumb">
		                                            <h4 class="msg-title js_msgTitle">${mixedMeterial.title}</h4>
		                                            <div class="msg-info">${mixedMeterial.et?string('yyyy-MM-dd HH:mm:ss')} </div>
		                                            <div class="msg-thumb-wrap">
		                                                <img class="msg-thumb" src="${basePath}${mixedMeterial.coverPicPath}" style="display:block"/>
		                                                <i class="msg-thumb-size default" style="display:none"></i>
		                                            </div>
		                                            <div class="msg-desc js_msgDesc">${mixedMeterial.description}</div>
		                                        </div>
		                                    </div>
		                                    <div class="options">
		                                        <div class="btn-tool first" onclick="$meterial.chooseMete('${mixedMeterial.id}','${hospitalId}','${thirdType}',${type});"><i class="icons-edit"></i>选择</div>
		                                        <div class="btn-tool"></div>
		                                    </div>
		                                </div>
                            		<#else>
                            			 <div class="t-msg">
		                                    <div class="inner">
		                                        <div class="multi-thumb">
		                                            <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${mixedMeterial.title}</a></h4>
		                                            <div class="msg-thumb-wrap">
		                                                <img class="msg-thumb" src="${basePath}${mixedMeterial.coverPicPath}" style="display:block"/>
		                                                <i class="msg-thumb-size default" style="display:none"></i>
		                                            </div>
		                                            <div class="msg-edit-mask"><a class="icons_edit edit-white" href="javascript:void(0)"></a></div>
		                                        </div>
		                                    </div>
		                                    <div class="msg-out clearfix">
			                                   	<#if (mixedMeterial.subMixedMeterialList?size>0)>
	                            					<#list mixedMeterial.subMixedMeterialList as subMixedMeterial>
					                                        <div class="inner msg-item">
					                                            <img class="msg-thumb" src="${basePath}${subMixedMeterial.coverPicPath}" style="display:block"/>
					                                             <i class="msg-thumb-size default" style="display:none"></i>
					                                            <h4 class="msg-title"><a href="javascript:void(0);" class="js_msgTitle">${subMixedMeterial.title}</a></h4>
					                                            <div class="msg-edit-mask">
					                                                <a class="icons_edit edit-white" href="javascript:void(0)"></a>
					                                                <div class="spaceW9"></div>
					                                                <a class="icons_edit del-white" href="javascript:void(0)" onclick="delThisWhite(this);"></a>
					                                            </div>
					                                        </div>
					                            	</#list>
	                            			 	</#if>
		                                    </div>
		                                    <div class="options">
		                                        <div class="btn-tool first"  onclick="$meterial.chooseMete('${mixedMeterial.id}','${hospitalId}','${thirdType}',${type});"><i class="icons-edit"></i>选择</div>
		                                        <div class="btn-tool"></div>
		                                    </div>
		                                </div>
                            		</#if>
                                </#list>
                            </#if>
                        </div>
                       <!--图文素材 end-->
                    </div>
                </div>
            </div>
        </div>

        <div class="pagination pagination-centered">
            <ul>
                <li><a href="javascript:;" onclick="$meterial.changePage(${mixedMeterials.prePage});">上一页</a></li>
                <#if mixedMeterials.pages != 0>
	                <#list 1..mixedMeterials.pages as p>
	                	<#if mixedMeterials.pageNum == p> 
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$meterial.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$meterial.changePage(${mixedMeterials.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$meterial.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<script src="../js/jquery.wookmark.js"></script>
<script type="text/javascript">
    (function ($){
        var handler = $('.meterial  .t-msg');

        handler.wookmark({
            align: 'left',
            // Prepare layout options.
            autoResize: true, // This will auto-update the layout when the browser window is resized.
            container: $('.meterial'), // Optional, used for some extra CSS styling
            offset: 5, // Optional, the distance between grid items
            outerOffset: 20, // Optional, the distance to the containers border
            itemWidth: 260, // Optional, the width of a grid item
            direction:'left',
            offset:20,
            flexibleWidth:true
        });

        // Capture clicks on grid items.
        handler.click(function(){
            // Randomize the height of the clicked item.
            var newHeight = $('.t-msg', this).height() + Math.round(Math.random() * 300 + 30);
            $(this).css('height', newHeight+'px');
            handler.wookmark();
        });
    })(jQuery);

	function showMenu(obj){
        var p = $(obj).parents('.upload-box')
    if(p.hasClass('show')){
        p.removeClass('show');
    }else{
        p.addClass('show');
    }
    
}
</script>
</body>
</html>

