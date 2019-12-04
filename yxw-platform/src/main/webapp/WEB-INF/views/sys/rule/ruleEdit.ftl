<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>编辑规则配置</title>
</head>
<body>
<!--sidebar-menu end-->
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">全局规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="editRuleForm">
                    <input type="hidden" name="hospitalId" value="${ruleEdit.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleEdit.id}"/>
                    <div class="widget-content">
                        <div class="row-fluid">
                            
                           <#-- <div class="control-group">
                                <label class="control-label">是否有分院</label>
                                <div class="controls ">
                                     <label 
                                        <#if ruleEdit.isHasBranch == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isHasBranch"  value="1" 
                                           <#if ruleEdit.isHasBranch == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleEdit.isHasBranch == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isHasBranch"  value="0"
                                            <#if ruleEdit.isHasBranch == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>-->
                            
                            <#--
                            <div class="control-group">
                                <label class="control-label">是否显示平台菜单</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleEdit.isShowAppMenu == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isShowAppMenu"  value="1" 
                                           <#if ruleEdit.isShowAppMenu == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleEdit.isShowAppMenu == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isShowAppMenu"  value="0"
                                            <#if ruleEdit.isShowAppMenu == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>

							<div class="control-group">
                                <label class="control-label">是否兼容医院不修改接口</label>
                                <div class="controls">
                                     <label 
                                        <#if ruleEdit.isCompatibleOtherPlatform == 1>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isCompatibleOtherPlatform"  value="1" 
                                           <#if ruleEdit.isCompatibleOtherPlatform == 1>checked="checked"<#else></#if>
                                        />是
                                    </label>
                                    <label 
                                         <#if ruleEdit.isCompatibleOtherPlatform == 0>
                                            class="radio inline check">
                                         <#else>
                                            class="radio inline">
                                         </#if>
                                        <input type="radio" name="isCompatibleOtherPlatform"  value="0"
                                            <#if ruleEdit.isCompatibleOtherPlatform == 0>checked="checked"<#else></#if>
                                        />否
                                    </label>
                                </div>
                            </div>
                            

                            <div class="control-group">
                                <label class="control-label" style=" padding-top: 0px;">添加就诊人配置</label>
                                <div class="line">同一个账号允许添加就诊人个数：
                                    <input type="text" name="addVpNum" id="addVpNum" value="${ruleEdit.addVpNum}" class="span1 input33"/> 个
                                </div>
                            </div>
                            -->
                            
                            <div class="control-group">
                                <label class="control-label" style=" padding-top: 0px;">his支付失败后子线程退费前等待时间</label>
                                <div class="line">
                                    <input type="text" name="refundWaitingSeconds" id="refundWaitingSeconds" value="${ruleEdit.refundWaitingSeconds}" class="span1 input33"/> 秒
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label" style=" padding-top: 28px;">替诊信息推送配置</label>
                                <div class="controls ">
                                    <div class="line">
                                        <span class="sub_label">接受替诊信息方式：</span>
                                        <label 
                                            <#if ruleEdit.acceptReplaceRegInfoType == 0>
                                                class="radio inline check">
                                            <#else>
                                                class="radio inline">
                                            </#if>
                                           <input type="radio" name="acceptReplaceRegInfoType" value="0"
                                                <#if ruleEdit.acceptReplaceRegInfoType == 0>checked="checked"<#else></#if> 
                                           >医院HIS主动推送
                                        </label>
                                        <label 
                                            <#if ruleEdit.acceptReplaceRegInfoType == 1>
                                                class="radio inline check">
                                            <#else>
                                                class="radio inline">
                                            </#if>
                                            <input type="radio" name="acceptReplaceRegInfoType" value="1"
                                                <#if ruleEdit.acceptReplaceRegInfoType == 1>checked="checked"<#else></#if> 
                                            />医享网定时轮询
                                        </label>
                                    </div>
                                    <div class="line">推送替诊信息时间：
                                        <input type="text" class="span2 input33" id="replaceRegPushInfoTime" 
                                               name="replaceRegPushInfoTime" 
                                            <#if ruleEdit.replaceRegPushInfoTime ??>
                                                 value="${ruleEdit.replaceRegPushInfoTime?string('HH:mm')}"
                                            <#else>
                                                 value=""
                                            </#if> 
                                         /> (例如18:00)
                                    </div>
                                    <div class="line">替诊查询天数：
                                        <input type="text" class="span2 input33" id="replaceRegPushInfoDay" name="replaceRegPushInfoDay" 
                                        	<#if ruleEdit.replaceRegPushInfoDay ??>
                                                 value="${ruleEdit.replaceRegPushInfoDay}"
                                            <#else>
                                                 value="1"
                                            </#if> 
                                         /> (默认一天)
                                    </div>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label" style=" padding-top: 28px;">停诊信息推送配置</label>
                                <div class="controls ">
                                    <div class="line">
                                        <span class="sub_label">接受停诊信息方式：</span>
                                        <label 
                                            <#if ruleEdit.acceptStopInfoType == 0>
                                                class="radio inline check">
                                            <#else>
                                                class="radio inline">
                                            </#if>
                                           <input type="radio" name="acceptStopInfoType" value="0"
                                                <#if ruleEdit.acceptStopInfoType == 0>checked="checked"<#else></#if> 
                                           >医院HIS主动推送
                                        </label>
                                        <label 
                                            <#if ruleEdit.acceptStopInfoType == 1>
                                                class="radio inline check">
                                            <#else>
                                                class="radio inline">
                                            </#if>
                                            <input type="radio" name="acceptStopInfoType" value="1"
                                                <#if ruleEdit.acceptStopInfoType == 1>checked="checked"<#else></#if> 
                                            />医享网定时轮询
                                        </label>
                                    </div>
                                    <div class="line">推送停诊信息时间：
                                        <input type="text" class="span2 input33" id="pushInfoTime" 
                                               name="pushInfoTime" 
                                            <#if ruleEdit.pushInfoTime ??>
                                                 <#--value="${ruleEdit.pushInfoTime?string('HH:mm')}"-->
												 value="${ruleEdit.pushInfoTime}"
                                            <#else>
                                                 value=""
                                            </#if> 
                                         /> (例如18:00)
                                    </div>
                                    <div class="line">停诊查询天数：
                                        <input type="text" class="span2 input33" id="pushInfoDay" name="pushInfoDay" 
                                        	<#if ruleEdit.pushInfoDay ??>
                                                 value="${ruleEdit.pushInfoDay}"
                                            <#else>
                                                 value="1"
                                            </#if> 
                                         /> (默认一天)
                                    </div>
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">停诊是否调用退费确认接口</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.stopRegNeedInvokeAckRefund == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="stopRegNeedInvokeAckRefund" value="0"
                                            <#if ruleEdit.stopRegNeedInvokeAckRefund == 0>checked="checked"<#else></#if> 
                                        >不调用 
                                    </label>
                                    <label 
                                        <#if ruleEdit.stopRegNeedInvokeAckRefund == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="stopRegNeedInvokeAckRefund" value="1"
                                            <#if ruleEdit.stopRegNeedInvokeAckRefund == 1>checked="checked"<#else></#if> 
                                        />调用 
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">停诊是否限制超过就诊开始时间无法停诊</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.overBeginTimeBanStopReg == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="overBeginTimeBanStopReg" value="0"
                                            <#if ruleEdit.overBeginTimeBanStopReg == 0>checked="checked"<#else></#if> 
                                        >不限制
                                    </label>
                                    <label 
                                        <#if ruleEdit.overBeginTimeBanStopReg == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="overBeginTimeBanStopReg" value="1"
                                            <#if ruleEdit.overBeginTimeBanStopReg == 1>checked="checked"<#else></#if> 
                                        />限制（默认）
                                    </label>
                                </div>
                            </div>
                            
                            
                            <div class="control-group">
                                <label class="control-label">是否开启高频停诊</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.highFrequencyStopReg == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="highFrequencyStopReg" value="0"
                                            <#if ruleEdit.highFrequencyStopReg == 0>checked="checked"<#else></#if> 
                                        >否 
                                    </label>
                                    <label 
                                        <#if ruleEdit.highFrequencyStopReg == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="highFrequencyStopReg" value="1"
                                            <#if ruleEdit.highFrequencyStopReg == 1>checked="checked"<#else></#if> 
                                        />是
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">高频停诊查询天数</label>
                                <div class="controls ">
                                        <input type="text" class="span2 input33" id="highFrequencyStopRegDayNum" name="highFrequencyStopRegDayNum" 
                                        	<#if ruleEdit.highFrequencyStopRegDayNum ??>
                                                 value="${ruleEdit.highFrequencyStopRegDayNum}"
                                            <#else>
                                                 value="0"
                                            </#if> 
                                         /> (默认【开始-结束】为【当天-当天】)
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label">获取患者门诊缴费信息的方式</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.paymentInfoGetType == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="paymentInfoGetType" value="0"
                                            <#if ruleEdit.paymentInfoGetType == 0>checked="checked"<#else></#if> 
                                        >医院HIS主动推送
                                    </label>
                                    <label 
                                        <#if ruleEdit.paymentInfoGetType == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="paymentInfoGetType" value="1"
                                            <#if ruleEdit.paymentInfoGetType == 1>checked="checked"<#else></#if> 
                                        />医享网定时轮询
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">当天就诊通知推送哪种支付类型的订单</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.curDayVisitNoticeOrderPayType == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="curDayVisitNoticeOrderPayType" value="0"
                                            <#if ruleEdit.curDayVisitNoticeOrderPayType == 0>checked="checked"<#else></#if> 
                                        >仅已支付
                                    </label>
                                    <label 
                                        <#if ruleEdit.curDayVisitNoticeOrderPayType == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="curDayVisitNoticeOrderPayType" value="1"
                                            <#if ruleEdit.curDayVisitNoticeOrderPayType == 1>checked="checked"<#else></#if> 
                                        />已支付和未支付
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">提前一天就诊通知推送哪种支付类型的订单</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.preDayVisitNoticeOrderPayType == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="preDayVisitNoticeOrderPayType" value="0"
                                            <#if ruleEdit.preDayVisitNoticeOrderPayType == 0>checked="checked"<#else></#if> 
                                        >仅已支付
                                    </label>
                                    <label 
                                        <#if ruleEdit.preDayVisitNoticeOrderPayType == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="preDayVisitNoticeOrderPayType" value="1"
                                            <#if ruleEdit.preDayVisitNoticeOrderPayType == 1>checked="checked"<#else></#if> 
                                        />已支付和未支付
                                    </label>
                                </div>
                            </div>
                            
                            <div class="control-group">
                                <label class="control-label">是否开放模版消息推送接口【对外】</label>
                                <div class="controls" style="padding-top: 20px">
                                    <label 
                                        <#if ruleEdit.opentemplateMsgPush == 0>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="opentemplateMsgPush" value="0"
                                            <#if ruleEdit.opentemplateMsgPush == 0>checked="checked"<#else></#if> 
                                        >不开放
                                    </label>
                                    <label 
                                        <#if ruleEdit.opentemplateMsgPush == 1>
                                             class="radio inline check">
                                        <#else>
                                             class="radio inline">
                                        </#if> 
                                        <input type="radio" name="opentemplateMsgPush" value="1"
                                            <#if ruleEdit.opentemplateMsgPush == 1>checked="checked"<#else></#if> 
                                        />开放
                                    </label>
                                </div>
                            </div>

                            <div class="control-group">
                                <label class="control-label" style="padding-top: 32px">住院功能查询控制</label>
                                <div class="controls ">
                                     <div class="inline">
                                            <#if ruleEdit.inpatientInquiryMode == 0>
                                                <label class="radio check">
                                            <#else>
                                                <label class="radio">
                                            </#if> 
                                            <input type="radio" name="inpatientInquiryMode" value="0"
                                                 <#if ruleEdit.inpatientInquiryMode == 0>checked="checked"<#else></#if> 
                                            />仅通过门诊信息查询住院信息
                                            </label>
                                    </div>
                                    <div class="inline">
                                        <#if ruleEdit.inpatientInquiryMode == 1>
                                             <label class="radio check">
                                        <#else>
                                             <label class="radio">
                                        </#if> 
                                        <input type="radio" name="inpatientInquiryMode" value="1"
                                              <#if ruleEdit.inpatientInquiryMode == 1>checked="checked"<#else></#if>
                                        />仅通过住院号/住院流水号查询住院信息
                                        </label>
                                    </div>
                                    <div class="inline">
                                        <#if ruleEdit.inpatientInquiryMode == 2>
                                             <label class="radio check">
                                        <#else>
                                             <label class="radio">
                                        </#if> 
                                        <input type="radio" name="inpatientInquiryMode" value="2"
                                                <#if ruleEdit.inpatientInquiryMode == 2>checked="checked"<#else></#if>
                                        />可通过以上两种方式查询住院信息
                                        </label>
                                   </div>
                                </div>
                            </div>
                            
                           <#--  <div class="control-group">
                                <label class="control-label" style=" padding-top: 0px;">底部Logo信息</label>
                                <div>
                                    <input type="text" name="footLogoInfo" id="footLogoInfo"  value="${ruleEdit.footLogoInfo}" style="width:75%;" />
                                </div>
                            </div>
                             -->
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('editRuleForm' , 'RuleEdit')">保存</button>
                </form>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
