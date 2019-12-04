<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>查询规则配置</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
   <div id="content-header">
        <div class="widget-title">
            <h3 class="title">查询规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="ruleQueryForm">
                    <input type="hidden" name="hospitalId" value="${ruleQuery.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleQuery.id}"/>
                    <div class="widget-content">
                        <div class="row-fluid">
                        	<div class="control-group w162">
                                <label class="control-label">候诊队列查询的类别</label>
                                <div class="controls">
                                    <label
                                        <#if  ruleQuery.queueType?contains("1")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="queueTypeArray" value="1"
                                            <#if ruleQuery.queueType?contains("1")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                        /> 门诊候诊排队信息 
                                    </label>
                                    <label
                                        <#if  ruleQuery.queueType?contains("2")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="queueTypeArray" value="2"
                                            <#if ruleQuery.queueType?contains("2")>
                                            checked="checked"
                                            <#else>
                                            </#if>
                                         /> 检查/检验/体检排队信息
                                    </label>
                                    <label
                                        <#if  ruleQuery.queueType?contains("3")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="queueTypeArray" value="3"
                                            <#if ruleQuery.queueType?contains("3")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 取药排队信息
                                    </label>
                                    <label
                                        <#if  ruleQuery.queueType?contains("3")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="queueTypeArray" value="4"
                                            <#if ruleQuery.queueType?contains("3")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 报告出具进度
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">报告可查询的类别</label>
                                <div class="controls">
                                    <label
                                        <#if  ruleQuery.reportCouldQueryType?contains("1")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="reportCouldQueryTypeArray" value="1"
                                            <#if ruleQuery.reportCouldQueryType?contains("1")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                        /> 检验 
                                    </label>
                                    <label
                                        <#if  ruleQuery.reportCouldQueryType?contains("2")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="reportCouldQueryTypeArray" value="2"
                                            <#if ruleQuery.reportCouldQueryType?contains("2")>
                                            checked="checked"
                                            <#else>
                                            </#if>
                                         /> 检查 
                                    </label>
                                    <label
                                        <#if  ruleQuery.reportCouldQueryType?contains("3")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="reportCouldQueryTypeArray" value="3"
                                            <#if ruleQuery.reportCouldQueryType?contains("3")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 体检
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">报告详情的界面样式</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleQuery.reportViewCssType == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="reportViewCssType" value="1"
                                        <#if ruleQuery.reportViewCssType == 1>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />数据样式
                                    </label>
                                    <label
                                        <#if ruleQuery.reportViewCssType == 2>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="reportViewCssType" value="2"
                                        <#if ruleQuery.reportViewCssType == 2>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />图形样式
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">预约记录可查询的类别</label>
                                <div class="controls">
                                     <label
                                        <#if  ruleQuery.bespeakRecordQueryType?contains("1")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="bespeakRecordQueryTypeArray" value="1"
                                            <#if ruleQuery.bespeakRecordQueryType?contains("1")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 预约挂号
                                    </label>
                                    <label
                                        <#if  ruleQuery.bespeakRecordQueryType?contains("2")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="bespeakRecordQueryTypeArray" value="2"
                                            <#if ruleQuery.bespeakRecordQueryType?contains("2")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 预约体检
                                    </label>
                                    <label
                                        <#if  ruleQuery.bespeakRecordQueryType?contains("3")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="bespeakRecordQueryTypeArray" value="3"
                                            <#if ruleQuery.bespeakRecordQueryType?contains("3")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 预约加床
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">缴费记录可查询的类别</label>
                                <div class="controls">
                                    <label
                                        <#if  ruleQuery.paymentRecordQueryType?contains("1")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="paymentRecordQueryTypeArray" value="1"
                                            <#if ruleQuery.paymentRecordQueryType?contains("1")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 门诊缴费
                                    </label>
                                    <label
                                        <#if  ruleQuery.paymentRecordQueryType?contains("2")>
                                            class="checkboxTwo inline check">
                                        <#else>
                                            class="checkboxTwo inline">
                                        </#if>
                                        <input type="checkbox" name="paymentRecordQueryTypeArray" value="2"
                                            <#if ruleQuery.paymentRecordQueryType?contains("2")>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                         /> 住院押金补缴
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">已缴费详情的显示样式</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleQuery.showClinicPaidDetailStyle == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="showClinicPaidDetailStyle" value="0"
                                        <#if ruleQuery.showClinicPaidDetailStyle == 0>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />按缴费明细显示
                                    </label>
                                    <label
                                        <#if ruleQuery.showClinicPaidDetailStyle == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="showClinicPaidDetailStyle" value="1"
                                        <#if ruleQuery.showClinicPaidDetailStyle == 1>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />按缴费费别显示
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162" >
                                <label class="control-label">详情是否显示条形码</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleQuery.isShowBarcode == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isShowBarcode"  value="1" 
                                           <#if ruleQuery.isShowBarcode == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleQuery.isShowBarcode == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isShowBarcode"  value="0"
                                            <#if ruleQuery.isShowBarcode == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">条形码显示内容</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleQuery.barcodeData == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="barcodeData"  value="1" 
                                           <#if ruleQuery.barcodeData == 1>checked="checked"<#else></#if>
                                        />接口返回的条形码
                                    </label>
                                    <label
                                         <#if ruleQuery.barcodeData == 2>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="barcodeData"  value="2"
                                            <#if ruleQuery.barcodeData == 2>checked="checked"<#else></#if>
                                        />就诊卡号
                                    </label>
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label">条形码格式</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleQuery.barcodeStyle == 1>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="1"
                                        <#if ruleQuery.barcodeStyle == 1>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />EAN码
                                    </label>
                                    <label
                                        <#if ruleQuery.barcodeStyle == 2>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="2"
                                        <#if ruleQuery.barcodeStyle == 2>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />UPC码 
                                    </label>
                                    <label
                                        <#if ruleQuery.barcodeStyle == 3>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="3"
                                        <#if ruleQuery.barcodeStyle == 3>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />39码
                                    </label>
                                    <label
                                        <#if ruleQuery.barcodeStyle == 4>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="4"
                                        <#if ruleQuery.barcodeStyle == 4>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />128码 
                                    </label>
                                    <label
                                        <#if ruleQuery.barcodeStyle == 5>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="5"
                                        <#if ruleQuery.barcodeStyle == 5>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />库德巴码 
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group w162">
                                <label class="control-label">住院一日清单是否有大项</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleQuery.oneDayRecordIsItems == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="oneDayRecordIsItems" value="1"
                                        <#if ruleQuery.oneDayRecordIsItems == 1>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />是
                                    </label>
                                    <label
                                        <#if  ruleQuery.oneDayRecordIsItems==null || ruleQuery.oneDayRecordIsItems == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="oneDayRecordIsItems" value="0"
                                        <#if ruleQuery.oneDayRecordIsItems==null || ruleQuery.oneDayRecordIsItems == 0>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            
                             <div class="control-group w162">
                                <label class="control-label">检验报告显示样式</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleQuery.choicesInspectionReportStyle==null ||ruleQuery.choicesInspectionReportStyle == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="choicesInspectionReportStyle" value="0"
                                        <#if ruleQuery.choicesInspectionReportStyle==null || ruleQuery.choicesInspectionReportStyle == 01>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />样式一
                                    </label>
                                    <label
                                        <#if ruleQuery.choicesInspectionReportStyle == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="choicesInspectionReportStyle" value="1"
                                        <#if ruleQuery.choicesInspectionReportStyle == 1>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />样式二
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top: 80px">查询时间控制</label>
                                <div class="controls">
                                    <div class="line">报告记录允许查询时长 
                                        <input type="text" class="span1 input33" name="reportRecordQueryMaxMonths" 
                                            value="${ruleQuery.reportRecordQueryMaxMonths}" /> 个月
                                    </div>
                                    <div class="line">预约记录允许查询时长
                                        <input type="text" class="span1 input33" name="bespeakRecordQueryMaxMonths"
                                            value="${ruleQuery.bespeakRecordQueryMaxMonths}" /> 个月
                                    </div>
                                    <div class="line">缴费记录允许查询时长 
                                        <input type="text" class="span1 input33" name="paymentRecordQueryMaxMonths" 
                                            value="${ruleQuery.paymentRecordQueryMaxMonths}" /> 个月
                                    </div>
                                    <div class="line">一日清单允许查询时长 
                                        <input type="text" class="span1 input33" name="oneDayRecordMaxMonths" 
                                            value="${ruleQuery.oneDayRecordMaxMonths}" /> 个月
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="control-group w162">
                                <label class="control-label" style="padding-top: 80px">查询次数控制</label>
                                <div class="controls">
                                    <div class="line">是否所有类型的报告查询共享次数限制
                                        <label
                                        <#if ruleQuery.shareQueryTimesLimit == 1>
                                                class="radio inline check">
                                        <#else>
                                            class="radio inline">
                                        </#if>
                                            <input type="radio" name="shareQueryTimesLimit" value="1"
                                            <#if ruleQuery.shareQueryTimesLimit == 2>
                                                   checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                        <#if ruleQuery.shareQueryTimesLimit == 2>
                                                class="radio inline check">
                                        <#else>
                                            class="radio inline">
                                        </#if>
                                            <input type="radio" name="shareQueryTimesLimit" value="2"
                                            <#if ruleQuery.shareQueryTimesLimit == 2>
                                                   checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">每日最大查询报告次数
                                        <input type="text" class="span1 input33" name="maxQueryTimesPerDay"
                                               value="${ruleQuery.maxQueryTimesPerDay}" /> 次
                                    </div>
                                    <div class="line">达到每日最大查询报告次数提示语
                                        <input type="text" placeholder="点击输入提示语"
                                               class="span5 input33" name="reachMaxQueryTimesPerDayTip" value="${ruleQuery.reachMaxQueryTimesPerDayTip}"/>
                                    </div>
                                    <div class="line">每周最大查询报告次数
                                        <input type="text" class="span1 input33" name="maxQueryTimesPerWeek"
                                               value="${ruleQuery.maxQueryTimesPerWeek}" /> 次
                                    </div>
                                    <div class="line">达到每周最大查询报告次数提示语
                                        <input type="text" placeholder="点击输入提示语"
                                               class="span5 input33" name="reachMaxQueryTimesPerWeekTip" value="${ruleQuery.reachMaxQueryTimesPerWeekTip}"/>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="control-group w162">
                                <label class="control-label">报告查询验证码有效时长</label>
                                <div class="controls" style="padding-top: 12px">
                                    <input type="number" class="span2 input33" name="captchaEffectiveTime" id="captchaEffectiveTime" value="${ruleQuery.captchaEffectiveTime}">　秒 （只能是整数，数值为空或小于<b>1</b>表示不启用验证码）
                                    <br>
                                    <br>
                                    		注:从用户最后一次报告查询操作开始经过上述时长后验证码会失效，需要重新输入
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label" style=" padding-top: 0px;">温馨提示</label>
                                <div>
                                    <input type="text" id="tip" name="tip"  value="${ruleQuery.tip}" style="width:75%;" />
                                </div>
                            </div>
                        
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleQueryForm' , 'RuleQuery')">保存</button>
                </form>
            </div>
        </div>

    </div>
</div>
<!--content end-->
</body>
</html>
