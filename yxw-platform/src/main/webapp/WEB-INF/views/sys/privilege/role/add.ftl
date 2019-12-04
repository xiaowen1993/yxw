<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/role/sys.role.js?oo"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <!--医院及系统设置 end-->
    <div id="content-header">
        <div class="widget-title"><h3 class="title">新建角色</h3></div>
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
	                            	<h4 class="title">角色名称 <small>(要求唯一)<small></h4>
	                            	<div class="controls"><input class="span4" type="text" value="" name="name" id="name"/></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">编码</h4>
	                            	<div class="controls"><input class="span4" type="text" value="" name="code" id="code"/></div>
	                            </div>
	                            <div class="control-group">
	                            	<h4 class="title">是否可用</h4>
	                            	<div class="controls">
	                            		<label class="radio inline check">
		                            		<input type="radio" value="1" name="available" checked="checked">可用
		                            	</label>
		                            	<label class="radio inline">
		                            		<input type="radio" value="0" name="available">禁用
		                            	</radio>
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
	            
	            <div class="widget-box">
	                <div class="space10"></div>
	                <div class="widget-content form-check">
	                    <div class="row-fluid">
	                            <div class="control-group">
	                            	<h4 class="title">角色资源<small>(角色拥有的资源访问权限)<small></h4>
	                            	<div class="controls" style="overflow-y:scroll;width:100%;height:550">
	                            		<table class="table table-bordered table-textCenter table-striped table-hover">
				                            <thead>
				                            <tr>
				                               <th width="50">
				                                    <label class="checkboxTwoAll inline">
				                                        <input type="checkbox" name="all">
				                                    </label>
				                                </th>
				                                <th>资源名称</th>
				                                <th>编码</th>
				                                <th>资源</th>
				                                <th>资源类型</th>
				                                <th>备注</th>
				                                <th>创建时间</th>
				                            </tr> 
				                            </thead>
				                            <#if (resourceList?size>0)>
					                            <tbody>
							                            <#list resourceList as resource>
						                                <tr>
						                                    <td>
						                                    	<label class="checkboxTwo inline">
						                                    		<input type="checkbox" name="resourceIds" value="${resource.id}"/>
																</label>
															</td>
						                                    <td>${resource.name}</td>
						                                    <td>${resource.code}</td>
						                                    <td>${resource.abstr}</td>
															<td>
																<#if resource.type==1>
																	菜单
																	<#elseif resource.type==2>
																	按钮
																	<#elseif resource.type==3>
																	普通链接
																	<#elseif resource.type==4>
																	医院
																</#if>
															</td>
															<td>${resource.memo}</td>
						                                    <td>
						                                    	 <#if resource.ct??>
						                                              ${resource.ct?string('yyyy-MM-dd HH:mm:ss')}
						                                         </#if>
						                                    </td>
						                                </tr>
						                                </#list>
					                            </tbody>
				                            </#if>
				                            <#if (resourceList?size=0)>
				                                <tbody>	
				                               		<tr><td colspan="9"> 未查询到任何相关内容  <td></tr>
				                                </tbody>
				                            </#if>
				                        </table>
										
	                            	</div>
	                            </div>
	                	</div>
	                </div>
	            </div>
	            
                <div class="space20"></div>
                <div class="controls">
                    <button class="btn btn-save" type="button" onclick="$role.save();">保存</button>
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
