<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>在线建档规则配置</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
   <div id="content-header">
        <div class="widget-title">
            <h3 class="title">在线建档规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="onlineFilingForm">
                    <input type="hidden" name="hospitalId" value="${onlineFiling.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${onlineFiling.id}"/>
                    <div class="widget-content">
                        <div class="row-fluid">
                            <div class="control-group w122">
                                <label class="control-label">就诊人类型</label>
                                <div class="controls ">
                                    <label
                                        <#if onlineFiling.visitingPersonType?contains("1")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="visitingPersonTypeArray" value="1"
                                        <#if onlineFiling.visitingPersonType?contains("1")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />本人
                                    </label>
                                    <label
                                        <#if onlineFiling.visitingPersonType?contains("2")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="visitingPersonTypeArray" value="2"
                                        <#if onlineFiling.visitingPersonType?contains("2")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />他人
                                    </label>
                                    <label
                                        <#if onlineFiling.visitingPersonType?contains("3")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="visitingPersonTypeArray" value="3"
                                        <#if onlineFiling.visitingPersonType?contains("3")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />儿童
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w120">
                                <label class="control-label">证件类型</label>
                                <div class="controls">
                                <label
                                    <#if  onlineFiling.certificatesType?contains("1")>
                                        class="checkboxTwo inline check">
                                    <#else>
                                        class="checkboxTwo inline">
                                    </#if>
                                        <input type="checkbox" name="certificatesTypeArray" value="1"
                                            <#if onlineFiling.certificatesType?contains("1")>
                                            checked="checked"
                                            <#else>
                                            </#if>
                                        /> 二代身份证 
                                </label>
                                <label
                                    <#if onlineFiling.certificatesType?contains("2") >
                                        class="checkboxTwo inline check">
                                    <#else>
                                        class="checkboxTwo inline">
                                    </#if>
                                        <input type="checkbox" name="certificatesTypeArray" value="2"
                                            <#if onlineFiling.certificatesType?contains("2")>
                                            checked="checked"
                                            <#else>
                                            </#if>
                                        /> 港澳居民身份证 
                                 </label>
                                 <label
                                    <#if onlineFiling.certificatesType?contains("3")>
                                        class="checkboxTwo inline check">
                                    <#else>
                                        class="checkboxTwo inline">
                                    </#if>
                                        <input type="checkbox" name="certificatesTypeArray" value="3"
                                            <#if onlineFiling.certificatesType?contains("3")>
                                            checked="checked"
                                            <#else>
                                            </#if>
                                        /> 台湾居民身份证 
                                 </label>
                                 <label
                                    <#if onlineFiling.certificatesType?contains("4")>
                                        class="checkboxTwo inline check">
                                    <#else>
                                        class="checkboxTwo inline">
                                    </#if>
                                        <input type="checkbox" name="certificatesTypeArray" value="4"
                                            <#if onlineFiling.certificatesType?contains("4")>
                                            checked="checked"
                                            <#else>
                                            </#if>
                                        /> 护照 
                                 </label>
                                </div>
                            </div>
                            <div class="control-group w122">
                                <label class="control-label">温馨提示</label>
                                <div class="controls ">
                                                                                                    提示语内容　<input type="text" placeholder="点击输入提示语" 
                                     class="span5 input33" name="tipWarmContent" value="${onlineFiling.tipWarmContent}"/>
                                </div>
                            </div>
                            <div class="control-group w122">
                                <label class="control-label">在线建档提示</label>
                                <div class="controls ">
                                                                                                    提示语内容　<textarea style="height:150px;" placeholder="点击输入提示语" class="span5 input33"
                                          id="onlineFilingTip_temp" name="onlineFilingTip_temp" value="${onlineFiling.onlineFilingTip}"/></textarea>
                                     <input type="hidden" value="${onlineFiling.onlineFilingTip}" name="onlineFilingTip" id="onlineFilingTip"/>
                                </div>
                            </div>
                            <div class="control-group w122">
                                <label class="control-label">确认添加按钮</label>
                                <div class="controls ">
                                                                                                    未正确填写信息时提示　<input type="text" placeholder="点击输入提示语" class="span5 input33"
                                         class="span5 input33" name="inputIncorrectTip" value="${onlineFiling.inputIncorrectTip}"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('onlineFilingForm' , 'RuleOnlineFiling')">保存</button>
                </form>
            </div>
        </div>

    </div>
</div>
<!--content end-->
</body>
</html>
