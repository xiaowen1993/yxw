<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/user/sys.user.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <!--医院及系统设置 end-->
    <div id="content-header">
        <div class="widget-title"><h3 class="title">新建账户</h3></div>
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
                            	<h4 class="title">用户姓名</h4>
                            	<div class="controls">
                            		<input class="span4" type="text" value="" name="fullName" id="fullName"/>
                            	</div>
                            </div	
                            <div class="control-group">
                            	<h4 class="title">账户名称 <small>（使用该名称可以登录本系统）</small></h4>
                            	<div class="controls">
                            		<input class="span4" type="text" value="" name="userName" id="userName"/>
                            	</div>
                            </div>
                            <div class="control-group">
                            	<h4 class="title">密码 <small>（密码不能少于6位）</small></h4>
                            	<div class="controls">
                            		<input class="span4" type="password" value="" name="password" id="password"/>
                            	</div>
                            </div>
                            <div class="control-group">
                            	<h4 class="title">确认密码</h4>
                            	<div class="controls">
                            		<input class="span4" type="password" value="" name="rePassword" id="rePassword"/>
                            	</div>
                            </div>
                            <div class="control-group">
	                          	<h4 class="title">邮箱</h4>
	                          	<div class="controls">
	                          		<input class="span4" type="text" value="" name="email" id="email"/>
	                         	</div>
	                        </div>
	                        <div class="control-group">
	                           	<h4 class="title">是否可用</h4>
	                           	<div class="controls">
	                           		<label class="radio inline check">
		                           		<input type="radio" value="1" name="available" checked="checked">可用
		                           	</label>
		                           	<label class="radio inline">
		                           		<input type="radio" value="0" name="available">禁用
		                           	</label>
								</div>
	                        </div>
                            <div class="control-group">
	                          	<h4 class="title">备注</h4>
	                          	<div class="controls">
	                          		<input class="span4" type="text" value="" name="memo" id="memo"/>
	                         	</div>
	                        </div>
                            <div class="control-group">
	                           	<h4 class="title">组织/机构/公司</h4>
	                            <div class="controls">
		                           	<select class="form-control" id="organizationId" name="organizationId">
										<#list organizationList as o>
			 								<option value="${o.id}">${o.name}</option>
									  	</#list>
									</select>
								</div>
	                        </div>
	                        
                            <div class="control-group">
                                <h4 class="title">选角色</h4>
                                <div class="controls form-check">
                                    <table class="table table-bordered table-textCenter table-striped table-hover">
				                            <thead>
				                            <tr>
				                               <th width="50">
				                                    <label class="checkboxTwoAll inline">
				                                        <input type="checkbox" name="all">
				                                    </label>
				                                </th>
				                                <th>角色名称</th>
				                                <th>编码</th>
				                                <th>是否可用</th>
				                                <th>备注</th>
				                                <th>创建时间</th>
				                            </tr> 
				                            </thead>
				                            <#if (roleList?size>0)>
					                            <tbody>
							                            <#list roleList as role>
						                                <tr>
						                                    <td>
						                                    	<label class="checkboxTwo inline">
						                                    		<input type="checkbox" name="roleIds" value="${role.id}"/>
																</label>
															</td>
						                                    <td>${role.name}</td>
						                                    <td>${role.code}</td>
															<td>
																<#if role.available==0>
																	禁用
																	<#elseif role.available==1>
																	启用
																</#if>
															</td>
															<td>${role.memo}</td>
						                                    <td>
						                                    	 <#if role.ct??>
						                                              ${role.ct?string('yyyy-MM-dd HH:mm:ss')}
						                                         </#if>
						                                    </td>
						                                </tr>
						                                </#list>
					                            </tbody>
				                            </#if>
				                            <#if (roleList?size=0)>
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
                    <button class="btn btn-save" type="button" onclick="$user.save();">保存</button>
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
