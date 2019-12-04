<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/organization/sys.organization.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <!--医院及系统设置 end-->
    <div id="content-header">
        <div class="widget-title"><h3 class="title">新建组织/机构</h3></div>
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
	                            	<h4 class="title">组织/机构/公司名称 <small>(要求唯一)<small></h4>
	                            	<div class="controls"><input class="span4" type="text" value="" name="name" id="name"/></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">简介</h4>
	                            	<div class="controls"><textarea rows="5" id="introduction" name="introduction" style="margin: 0px 0px 10px; height: 122px; width: 465px;"/></textarea></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">父 组织/机构/公司</h4>
	                            	<div class="controls">
		                            	<select class="form-control" id="parentId" name="parentId">
		                            		<option value=""></option>
											<#list organizationList as o>
			 									<option value="${o.id}">${o.name}</option>
										  	</#list>
										</select>
									</div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">编码 <small>(要求唯一)<small></h4>
	                            	<div class="controls">
	                            		<input class="span4" type="text" value="" name="code" id="code"/>
	                           		</div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">备注</h4>
	                            	<div class="controls">
	                            		<input class="span4" type="text" value="" name="memo" id="memo"/>
	                           		</div>
	                            </div>
	                	</div>
	               </div>
	            </div>
                <div class="space20"></div>
                <div class="controls">
                    <button class="btn btn-save" type="button" onclick="$organization.save();">保存</button>
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
