<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>门诊缴费规则配置</title>
    <style>
	.bangKa .peiZhi .control-group .controls .line .text3 {
	    width: 270px;
	    display: inline-block;
	    zoom: 1;
	}
</style>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
   <div id="content-header">
        <div class="widget-title">
            <h3 class="title">门诊缴费规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg"  id="ruleClinicForm">
                    <input type="hidden" name="hospitalId" value="${ruleClinic.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleClinic.id}"/>
                    <div class="widget-content peiZhi">
                        <div class="row-fluid">
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top: 15px">页面样式及流程配置</label>
                                <div class="controls">
                                    <div class="line">门诊缴费是否开启医保：
                                        <label
                                            <#if ruleClinic.isBasedOnMedicalInsurance == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isBasedOnMedicalInsurance" value="1"
                                            <#if ruleClinic.isBasedOnMedicalInsurance == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleClinic.isBasedOnMedicalInsurance == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isBasedOnMedicalInsurance" value="0"
                                            <#if ruleClinic.isBasedOnMedicalInsurance == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">门诊缴费是否支持合并支付：
                                        <label
                                            <#if ruleClinic.isSupportCombinedPayments == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportCombinedPayments" value="1"
                                            <#if ruleClinic.isSupportCombinedPayments == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持
                                        </label>
                                        <label
                                            <#if ruleClinic.isSupportCombinedPayments == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportCombinedPayments" value="0"
                                            <#if ruleClinic.isSupportCombinedPayments == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支持
                                        </label>
                                    </div>
                                    <div class="line">门诊缴费是否支持医保结算：
                                        <label
                                            <#if ruleClinic.isSupportHealthPayments == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportHealthPayments" value="1"
                                            <#if ruleClinic.isSupportHealthPayments == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持
                                        </label>
                                        <label
                                            <#if ruleClinic.isSupportHealthPayments == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportHealthPayments" value="0"
                                            <#if ruleClinic.isSupportHealthPayments == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支持
                                        </label>
                                    </div>
                                    <div class="line">门诊缴费是否支持支付限制：
                                        <label
                                            <#if ruleClinic.isSupportForbiddenPayment == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportForbiddenPayment" value="1"
                                            <#if ruleClinic.isSupportForbiddenPayment == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持
                                        </label>
                                        <label
                                            <#if ruleClinic.isSupportForbiddenPayment == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportForbiddenPayment" value="0"
                                            <#if ruleClinic.isSupportForbiddenPayment == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支持
                                        </label>
                                    </div>
                                    <div class="line">门诊待缴费详情显示样式：
                                        <label
                                            <#if ruleClinic.showClinicPayDetailStyle == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="showClinicPayDetailStyle" value="0"
                                            <#if ruleClinic.showClinicPayDetailStyle == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />按缴费明细显示
                                        </label>
                                        <label
                                            <#if ruleClinic.showClinicPayDetailStyle == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="showClinicPayDetailStyle" value="1"
                                            <#if ruleClinic.showClinicPayDetailStyle == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />按缴费费别显示
                                        </label>
                                    </div>
                                    <div class="line">门诊医保预结算样式：
                                        <label
                                            <#if ruleClinic.presettleStyle == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="presettleStyle" value="0"
                                            <#if ruleClinic.presettleStyle == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />深圳
                                        </label>
                                        <label
                                            <#if ruleClinic.presettleStyle == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="presettleStyle" value="1"
                                            <#if ruleClinic.presettleStyle == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />汕头
                                        </label>
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认缴费是否弹窗提示[微信医保]：</span>
                                        <label
                                            <#if ruleClinic.isconfirmTipMedicareWechat == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareWechat" value="1"
                                            <#if ruleClinic.isconfirmTipMedicareWechat == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleClinic.isconfirmTipMedicareWechat == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareWechat" value="0"
                                            <#if ruleClinic.isconfirmTipMedicareWechat == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认缴费是否弹窗提示[支付宝医保]：</span>
                                        <label
                                            <#if ruleClinic.isconfirmTipMedicareAlipay == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareAlipay" value="1"
                                            <#if ruleClinic.isconfirmTipMedicareAlipay == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleClinic.isconfirmTipMedicareAlipay == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareAlipay" value="0"
                                            <#if ruleClinic.isconfirmTipMedicareAlipay == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认缴费是否弹窗提示[微信自费]：</span>
                                        <label
                                            <#if ruleClinic.isconfirmTipSelfPayWechat == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayWechat" value="1"
                                            <#if ruleClinic.isconfirmTipSelfPayWechat == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleClinic.isconfirmTipSelfPayWechat == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayWechat" value="0"
                                            <#if ruleClinic.isconfirmTipSelfPayWechat == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认缴费是否弹窗提示[支付宝自费]：</span>
                                        <label
                                            <#if ruleClinic.isconfirmTipSelfPayAlipay == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayAlipay" value="1"
                                            <#if ruleClinic.isconfirmTipSelfPayAlipay == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleClinic.isconfirmTipSelfPayAlipay == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayAlipay" value="0"
                                            <#if ruleClinic.isconfirmTipSelfPayAlipay == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                </div>
                            </div>
                            <div class="control-group w162">
                               <label class="control-label">门诊异常订单自动退费延时时间</label>
                                <div class="controls" style="padding-top: 12px">
                                    <input type="number" class="span2 input33" name="refundDelayAfterException"
                                           id="refundDelayAfterException" value="${ruleClinic.refundDelayAfterException}" />　分钟 （只能是整数，数值为空或小于<b>1</b>表示永不执行自动退费）
                                    <br/>
                                    <br/>
                               		 注:门诊订单在发生异常后轮询处理超过上述时间后才执行自动退费处理
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top:75px">提示语配置</label>
                                <div class="controls">
                                    <div class="line">
                                        <span class="text3">门诊缴费不支持医保结算提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33"name="notSupportHealthPaymentsTip" 
                                            id="notSupportHealthPaymentsTip" value="${ruleClinic.notSupportHealthPaymentsTip}" />
                                    </div>
                                    <div class="line">
                                        <span class="text3">门诊缴费支持医保结算提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33"name="supportHealthPaymentsTip" 
                                            id="supportHealthPaymentsTip" value="${ruleClinic.supportHealthPaymentsTip}" />
                                    </div>
                                    <div class="line">
                                        <span class="text3">门诊缴费支持不允许缴费提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33"name="supportForbiddenPaymentTips" 
                                            id="supportForbiddenPaymentTips" value="${ruleClinic.supportForbiddenPaymentTips}" />
                                    </div>
                                    <div class="line">
                                        <span class="text3">门诊缴费失败提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33"name="outpatientPaymentFailedTip" 
                                            id="outpatientPaymentFailedTip" value="${ruleClinic.outpatientPaymentFailedTip}" />
                                    </div>
                                    <div class="line">
                                        <span class="text3">门诊待缴费提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33"name="outpatientServicePayTips" 
                                            id="outpatientServicePayTips" value="${ruleClinic.outpatientServicePayTips}" />
                                    </div>
                                    <div class="line"> 
                                        <span class="text3" style="width:270px;">确认缴费弹框提示语【微信医保】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipMedicareWechat" 
                                            id="confirmTipMedicareWechat" value="${ruleClinic.confirmTipMedicareWechat}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认缴费弹框提示语【支付宝医保】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipMedicareAlipay" 
                                            id="confirmTipMedicareAlipay" value="${ruleClinic.confirmTipMedicareAlipay}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认缴费弹框提示语【微信自费】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipSelfPayWechat" 
                                            id="confirmTipSelfPayWechat" value="${ruleClinic.confirmTipSelfPayWechat}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认缴费弹框提示语【支付宝自费】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipSelfPayAlipay" 
                                            id="confirmTipSelfPayAlipay" value="${ruleClinic.confirmTipSelfPayAlipay}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3">门诊缴费支付前温馨提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33"name="clinicPrePayWarmTip" 
                                            id="clinicPrePayWarmTip" value="${ruleClinic.clinicPrePayWarmTip}" />
                                    </div>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleClinicForm' , 'RuleClinic')">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>