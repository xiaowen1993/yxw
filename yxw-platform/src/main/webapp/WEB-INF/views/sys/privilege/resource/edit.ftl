<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/resource/sys.resource.js?ooo"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <!--医院及系统设置 end-->
    <div id="content-header">
        <div class="widget-title"><h3 class="title">编辑资源</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <form>
	            <div class="widget-box">
	                <div class="space10"></div>
	                <div class="widget-content">
	                    <div class="row-fluid">
	                            <div class="control-group">
	                            	<h4 class="title">资源名称 <small>(要求唯一)<small></h4>
	                            	<div class="controls"><input class="span4" type="text" value="${resource.name}" name="name" id="name"/></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">编码 <small>(要求唯一,预留)<small></h4>
	                            	<div class="controls"><input class="span4" type="text" value="${resource.code}" name="code" id="code"/></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">资源</h4>
	                            	<div class="controls"><input class="span4" type="text" value="${resource.abstr}" name="abstr" id="abstr"/></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">资源类型</h4>
	                            	<div class="controls">
		                            	<select class="form-control" id="type" name="type">
		                            		<option value="1" <#if resource.type==1>selected</#if>>菜单</option>
		                            		<option value="2" <#if resource.type==2>selected</#if>>按钮</option>
		                            		<option value="3" <#if resource.type==3>selected</#if>>链接</option>
		                            		<option value="4" <#if resource.type==4>selected</#if>>医院</option>
										</select>
									</div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">备注</h4>
	                            	<div class="controls">
	                            		<input class="span4" type="text" value="${resource.memo}" name="memo" id="memo"/>
	                           		</div>
	                            </div>
	                            <input type="hidden" name="id" id="id" value="${resource.id}" />
	                	</div>
	               </div>
	            </div>
                <div class="space20"></div>
                <div class="controls">
                    <button class="btn btn-save" type="button" onclick="$resource.edit();">更新</button>
                    <div class="spaceW15"></div>
                    <button class="btn btn-remove" type="button" onclick="javascript:history.back();" >取消</button>
                </div>
            </form>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
