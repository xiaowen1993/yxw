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
        <div class="widget-title"><h3 class="title">选择图片</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row-fluid">
                <!--<div class="pull-left">
                   <a href="${basePath}message/mixedMeterial?method=list&hospitalId=${hospitalId}"><button type="button" class="btn btn-ok w130 ">图文消息</button></a> 
                    <a href="${basePath}message/meterial?method=choosePicList&hospitalId=${hospitalId}"><button type="button" class="btn btn-ok w130 active">图片库</button></a>
                </div>-->
              	<div class="pull-right" id="search">
                    <form method="post" action="">
            			<input type="hidden" name="pageNum" value="${meterials.pageNum}" />
            			<input type="hidden" name="pageSize" value="${meterials.pageSize}" />
            			<input type="hidden" name="pages" value="${meterials.pages}" />
            			<input type="hidden" name="total" value="${meterials.total}" />
            			</br></br>
                	</form>
                </div>
            </div>
            <input type="hidden" id="hospitalId" value="${hospitalId}">
            <div class="widget-box">
                <div class="widget-content form-check meterial">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th >缩略图</th>
                                <th>图片名</th>
                                <th>操作</th>
                                <th>缩略图</th>
                                <th>图片名</th>
                               <th >操作</th>
                            </tr>
                            </thead>
	                        <tbody>
                            <#if (meterials.list?size>0)>
                            	<#list meterials.list as meterial>
                            		<#if meterial_index%2 ==0>
	                                <tr>
	                                </#if>
	                                <td class="textleft"><span class="spaceW15"></span><img class="materImg" src="${meterial.path}" id="${meterial.id}" width="84" height="76" onclick="choosePic('${meterial.id}','${metarialType}');" style="cursor:pointer;"/> </td>
	                                <td style="vertical-align: middle;"><#if meterial.name!=null>${meterial.name}<#else>图片来自自动回复</#if></td>
	                               	<td>
	                                    <a href="javascript:void (0);" class="green lineH84" onclick="choosePic('${meterial.id}','${metarialType}')">选这张</a>
	                                </td>
	                                <#if meterial_index%2 ==1>
	                                </tr>
	                                </#if>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        
		<div class="pagination pagination-centered">
            <ul>
                <li><a href="javascript:;" onclick="$meterial.changePage(${meterials.prePage});">上一页</a></li>
                <#if meterials.pages != 0>
	                <#list 1..meterials.pages as p>
	                	<#if meterials.pageNum == p> 
	                		<li class="disabled"><span>${p}</span></li>
	                		<#else>
	                    	<li><a href="javascript:;" onclick="$meterial.changePage(${p});">${p}</a></li>
	                  </#if>
	                </#list>
                </#if>
                <li><a href="javascript:;" onclick="$meterial.changePage(${meterials.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$meterial.changePage($('#skipPage').val());">跳转</a>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
<script type="text/javascript">
function choosePic(id,metarialType)
{
	window.opener.setMetarial($('#'+id).attr('src'),metarialType);
	window.close();
}
</script>
