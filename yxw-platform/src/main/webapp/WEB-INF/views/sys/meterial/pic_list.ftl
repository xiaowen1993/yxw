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
        <div class="widget-title"><h3 class="title">素材库</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                   <a href="${basePath}message/mixedMeterial?method=list&hospitalId=${hospitalId}"><button type="button" class="btn btn-ok w130 ">图文消息</button></a> 
                    <a href="${basePath}message/meterial?method=listOfPic&hospitalId=${hospitalId}"><button type="button" class="btn btn-ok w130 active">图片库</button></a>
                </div>
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
                <div class="space10"></div>
                <div class="widget-content" style="padding: 0;">
                    <div class="row-fluid">
                        <div class="upload-box">
                            <a class="btn-add pull-right" href="javascript:void(0);" ><i class="caret"></i><i class="icons-plus"></i>上传图片</a>
                            <input type="file" id="btn-upload" name="uploadFile" onchange="$meterial.uploadImg();"> 
                        </div>
                        <div class="pull-right upload-tips">大小: 不超过2M,    格式: bmp, png, jpeg, jpg, gif</div>
                    </div>
                </div>
                <div class="space10"></div>
                <div class="widget-content form-check meterial">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th width="100">
                                    <label class="checkboxTwoAll inline">
                                        <input type="checkbox" name="all" >
                                    </label>全选
                                </th>
                                <th>缩略图</th>
                                <th>图片名</th>
                                <th>大小</th>
                                <th>存储路径</th>
                                <th width="300"><a href="javascript:void (0);" class="btn btn-del" onclick="$meterial.delAllRow(this,'${hospitalId}');"><i class="del_white"></i>删除</a> </th>
                            </tr>
                            </thead>
	                        <tbody>
                            <#if (meterials.list?size>0)>
                            	<#list meterials.list as meterial>
	                                <tr>
	                                	<td><label class="checkboxTwo inline marginT23"><input type="checkbox" name="meterialIds" value="${meterial.id}"></label></td>
		                                <td class="textleft"><span class="spaceW15"></span><img class="materImg" src="${meterial.path}" width="84" height="76"/> </td>
		                                <td style="vertical-align: middle;"><#if meterial.name!=null>${meterial.name}<#else>图片来自自动回复</#if></td>
		                                <td style="vertical-align: middle;"><#if meterial.size!=null>${meterial.size}KB</#if></td>
		                                <td style="vertical-align: middle;">${meterial.path}</td>
		                                <td>
		                                    <a href="javascript:void (0);" class="red lineH84" onclick="$meterial.delThisRow(this,'${meterial.id}','${hospitalId}');">删除</a>
		                                </td>
	                                </tr>
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
</script>
