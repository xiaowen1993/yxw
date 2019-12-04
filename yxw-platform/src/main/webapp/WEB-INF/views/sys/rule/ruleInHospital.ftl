<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>住院规则配置</title>
<style>
	.control-group .controls .line .text3 {
	    width: 180px;
	    display: inline-block;
	    zoom: 1;
	}
</style>
</head>
<body>
<!--sidebar-menu end-->
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">住院规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="ruleInHospitalForm">
                    <input type="hidden" name="hospitalId" value="${ruleInHospital.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleInHospital.id}"/>
                    <div class="widget-content">
                        <div class="row-fluid">
                        
	                       <div class="control-group">
                                <label class="control-label">是否有分院</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleInHospital.hasBranch == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="hasBranch"  value="1" 
                                           <#if ruleInHospital.hasBranch == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleInHospital.hasBranch == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="hasBranch"  value="0"
                                            <#if ruleInHospital.hasBranch == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
	                        </div>
                        
                            <div class="control-group">
                                <label class="control-label">住院绑定是否需要【住院人身份证】</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleInHospital.ifBindNeedIDCard == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="ifBindNeedIDCard"  value="1" 
                                           <#if ruleInHospital.ifBindNeedIDCard == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleInHospital.ifBindNeedIDCard == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="ifBindNeedIDCard"  value="0"
                                            <#if ruleInHospital.ifBindNeedIDCard == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                                <br>	注：选 “否” 将在调用住院接口时传递卡类型为【就诊卡(7)】及就诊卡号；选 “是” 则传住院人身份证及卡类型为【身份证(5)】
                           </div>
                           
                           <div class="control-group">
                                <label class="control-label">住院绑定是否需要【住院号】</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleInHospital.ifBindNeedAdmissionNo == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="ifBindNeedAdmissionNo"  value="1" 
                                           <#if ruleInHospital.ifBindNeedAdmissionNo == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleInHospital.ifBindNeedAdmissionNo == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="ifBindNeedAdmissionNo"  value="0"
                                            <#if ruleInHospital.ifBindNeedAdmissionNo == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                           </div>
                            
                           <div class="control-group">
                                <label class="control-label">是否需要弹窗提示</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleInHospital.isTip == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isTip"  value="1" 
                                           <#if ruleInHospital.isTip == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleInHospital.isTip == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isTip"  value="0"
                                            <#if ruleInHospital.isTip == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                           </div>
                           
                           <div class="control-group">
                                <label class="control-label">住院押金补缴最低缴费金额</label>
                                <div class="controls" >
                                    <input type="text" class="span2 input33" name="hospitalDepositMinMoney" 
                                            id="hospitalDepositMinMoney" value="${ruleInHospital.hospitalDepositMinMoney}" />　元
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">住院押金补缴最高缴费金额</label>
                                <div class="controls" >
                                    <input type="text" class="span2 input33" name="hospitalDepositMaxMoney" 
                                            id="hospitalDepositMaxMoney" value="${ruleInHospital.hospitalDepositMaxMoney}" />　元
                                </div>
                            </div>
                            
                           <div class="control-group">
                                <label class="control-label" >提示语设置</label>
                                <div class="controls" style="margin-left: 213px;">
                                    <div class="line">
                                        <span class="text3">弹窗提示内容：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="tipContent" 
                                            id="tipContent" value="${ruleInHospital.tipContent}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3">住院押金补缴提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="hospitalDepositPaymentsTip" 
                                            id="hospitalDepositPaymentsTip" value="${ruleInHospital.hospitalDepositPaymentsTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3">押金补缴失败提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="depositPaymentFailedTip" 
                                            id="depositPaymentFailedTip" value="${ruleInHospital.depositPaymentFailedTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3">清账提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="clearAccountTip" 
                                            id="clearAccountTip" value="${ruleInHospital.clearAccountTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3">清账下单前提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="clearAccountPreOrderTip" 
                                            id="clearAccountPreOrderTip" value="${ruleInHospital.clearAccountPreOrderTip}"/>　
                                    </div>
                                </div>
                           </div>
                            
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleInHospitalForm' , 'RuleInHospital')">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
