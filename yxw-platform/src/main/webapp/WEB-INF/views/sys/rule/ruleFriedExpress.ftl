<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>代煎配送配置</title>
</head>
<body>
<!--sidebar-menu end-->
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">代煎配送规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="ruleFriedExpressForm">
                    <input type="hidden" name="hospitalId" value="${ruleFriedExpress.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleFriedExpress.id}"/>
                    <div class="widget-content">
                        <div class="row-fluid">
                            <div class="control-group w235">
                                <label class="control-label">门诊缴费是否接入代煎配送</label>
                                <div class="controls">
                                	<label 
                                         <#if ruleFriedExpress.isAccessClinic == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if> 
                                         <input type="radio" name="isAccessClinic"  value="1"
                                               <#if ruleFriedExpress.isAccessClinic == 1>
                                                    checked="checked"
                                               <#else></#if> 
                                         />是
                                    </label>
                                    
                                    <label
	                                    <#if ruleFriedExpress.isAccessClinic == 0>
	                                         class="radio inline check">
	                                    <#else>
	                                         class="radio inline">
	                                    </#if> 
                                         <input type="radio" name="isAccessClinic"  value="0"  
                                               <#if ruleFriedExpress.isAccessClinic == 0>
                                                    checked="checked"
                                               <#else>
                                               </#if>
                                         />否
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group w235">
                                <label class="control-label">缴费记录是否接入代煎配送</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleFriedExpress.isAccessPayed == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isAccessPayed"  value="1" 
                                           <#if ruleFriedExpress.isAccessPayed == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleFriedExpress.isAccessPayed == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isAccessPayed"  value="0"
                                            <#if ruleFriedExpress.isAccessPayed == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w235">
                                <label class="control-label">用户是否可关闭代煎配送</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleFriedExpress.isCanOffByUser == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isCanOffByUser"  value="1" 
                                           <#if ruleFriedExpress.isCanOffByUser == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleFriedExpress.isCanOffByUser == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isCanOffByUser"  value="0"
                                            <#if ruleFriedExpress.isCanOffByUser == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>

                            <div class="control-group w235">
                                <label class="control-label">是否只设置配送信息</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleFriedExpress.isOnlyExpress == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isOnlyExpress"  value="1" 
                                           <#if ruleFriedExpress.isOnlyExpress == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleFriedExpress.isOnlyExpress == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isOnlyExpress"  value="0"
                                            <#if ruleFriedExpress.isOnlyExpress == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group w235">
                                <label class="control-label">是否要对不同的处方单设置代煎配送</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleFriedExpress.isSplitRecipe == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isSplitRecipe"  value="1" 
                                           <#if ruleFriedExpress.isSplitRecipe == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleFriedExpress.isSplitRecipe == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isSplitRecipe"  value="0"
                                            <#if ruleFriedExpress.isSplitRecipe == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group w235">
                                <label class="control-label">代煎配置是否要查询接口</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleFriedExpress.isFriedQuery == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isFriedQuery"  value="1" 
                                           <#if ruleFriedExpress.isFriedQuery == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleFriedExpress.isFriedQuery == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isFriedQuery"  value="0"
                                            <#if ruleFriedExpress.isFriedQuery == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group w235">
                                <label class="control-label">配送配置是否要查询接口</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleFriedExpress.isExpressQuery == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isExpressQuery"  value="1" 
                                           <#if ruleFriedExpress.isExpressQuery == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleFriedExpress.isExpressQuery == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isExpressQuery"  value="0"
                                            <#if ruleFriedExpress.isExpressQuery == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>

							<div class="control-group w235">
                                <label class="control-label">代煎与配送的关系</label>
                                <div class="controls">
                                     <select class="form-control" id="friedExpressRelation" name="friedExpressRelation">
                                     	<!-- 1不限制,2代煎必须配送,3代煎可以不配送 -->
										<option value="1" <#if ruleFriedExpress.friedExpressRelation == 1>selected="selected"</#if> >不限制</option>
										<option value="2" <#if ruleFriedExpress.friedExpressRelation == 2>selected="selected"</#if>>代煎必须配送</option>
										<option value="3" <#if ruleFriedExpress.friedExpressRelation == 3>selected="selected"</#if>>代煎可以不配送</option>
									</select>
                                </div>
                            </div>
                            
                            <div class="control-group w235">
                                <label class="control-label">提示语设置</label>
                                <div class="controls">
                                    <input type="text" placeholder="点击输入提示语" class="span5 input33" name="tipContent" 
                                            id="tipContent" value="${ruleFriedExpress.tipContent}"/>　
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleFriedExpressForm' , 'RuleFriedExpress')">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
