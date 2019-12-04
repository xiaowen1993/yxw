<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js?v=1.0"></script>
    <title>挂号规则配置</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">挂号规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="ruleRegisterForm">
                    <input type="hidden" name="hospitalId" value="${ruleRegister.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleRegister.id}"/>
                    <div class="widget-content guaHao">
                        <div class="row-fluid">
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top:70px;width:150px;">页面样式及流程设置</label>
                                <div class="controls" style="margin-left: 213px;">
                                	<div class="line">
                                        <span class="text">当班挂号是否开启医保：</span>
                                        <label
                                            <#if ruleRegister.isBasedOnMedicalInsuranceToday == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isBasedOnMedicalInsuranceToday" value="1"
                                            <#if ruleRegister.isBasedOnMedicalInsuranceToday == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isBasedOnMedicalInsuranceToday == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isBasedOnMedicalInsuranceToday" value="0"
                                            <#if ruleRegister.isBasedOnMedicalInsuranceToday == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约挂号是否开启医保：</span>
                                        <label
                                            <#if ruleRegister.isBasedOnMedicalInsuranceAppoint == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isBasedOnMedicalInsuranceAppoint" value="1"
                                            <#if ruleRegister.isBasedOnMedicalInsuranceAppoint == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isBasedOnMedicalInsuranceAppoint == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isBasedOnMedicalInsuranceAppoint" value="0"
                                            <#if ruleRegister.isBasedOnMedicalInsuranceAppoint == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">医院是否有分院：</span>
                                        <label
                                            <#if ruleRegister.isHasBranch == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasBranch" value="1"
                                            <#if ruleRegister.isHasBranch == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isHasBranch == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasBranch" value="0"
                                            <#if ruleRegister.isHasBranch == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">医院是否有二级科室：</span>
                                        <label
                                            <#if ruleRegister.isHasGradeTwoDept == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasGradeTwoDept" value="1"
                                            <#if ruleRegister.isHasGradeTwoDept == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isHasGradeTwoDept == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasGradeTwoDept" value="0"
                                            <#if ruleRegister.isHasGradeTwoDept == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <!-- 取值，0：都不支持; 1: 都支持; 2:支持当班 ; 3:支持预约;  -->
                                    <div class="line">
                                    	<input type="hidden" name="isHasSearChDoctor" id="isHasSearChDoctor" value="${ruleRegister.isHasSearChDoctor}" />
                                        <span class="text">医院支持搜索医生功能：</span>
                                        <label
	                                        <#if ruleRegister.isHasSearChDoctor == 1 || ruleRegister.isHasSearChDoctor == 2>
	                                           class="checkboxTwo inline check">
	                                        <#else>
	                                           class="checkboxTwo inline">
	                                        </#if> 
	                                        <input type="checkbox" name="dutySupportSearchDoc" value="2"
	                                        <#if ruleRegister.isHasSearChDoctor == 1 || ruleRegister.isHasSearChDoctor == 2>
	                                            checked="checked"
	                                        <#else>
	                                        </#if>
	                                        />当班支持
	                                    </label>
	                                    <label
	                                        <#if ruleRegister.isHasSearChDoctor == 1 || ruleRegister.isHasSearChDoctor == 3>
	                                           class="checkboxTwo inline check">
	                                        <#else>
	                                           class="checkboxTwo inline">
	                                        </#if> 
	                                        <input type="checkbox" name="appointmentSupportSearchDoc" value="3"
	                                        <#if ruleRegister.isHasSearChDoctor == 1 || ruleRegister.isHasSearChDoctor == 3>
	                                            checked="checked"
	                                        <#else>
	                                        </#if>
	                                        />预约支持
	                                    </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">当班是否显示剩余号源：</span>
                                        <label
                                            <#if ruleRegister.isViewSourceNum == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewSourceNum" value="1"
                                            <#if ruleRegister.isViewSourceNum == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isViewSourceNum == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewSourceNum" value="0"
                                            <#if ruleRegister.isViewSourceNum == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约是否显示剩余号源：</span>
                                        <label
                                            <#if ruleRegister.isViewSourceNumReg == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewSourceNumReg" value="1"
                                            <#if ruleRegister.isViewSourceNumReg == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isViewSourceNumReg == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewSourceNumReg" value="0"
                                            <#if ruleRegister.isViewSourceNumReg == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">医院是否有当班挂号：</span>
                                        <label
                                            <#if ruleRegister.isHasOnDutyReg == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasOnDutyReg" value="1"
                                            <#if ruleRegister.isHasOnDutyReg == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isHasOnDutyReg == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasOnDutyReg" value="0"
                                            <#if ruleRegister.isHasOnDutyReg == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">医院是否有预约挂号：</span>
                                        <label
                                            <#if ruleRegister.isHasAppointmentReg == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasAppointmentReg" value="1"
                                            <#if ruleRegister.isHasAppointmentReg == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isHasAppointmentReg == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHasAppointmentReg" value="0"
                                            <#if ruleRegister.isHasAppointmentReg == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约挂号日历显示天数：</span>
                                        <label
                                            <#if ruleRegister.calendarDaysType == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="calendarDaysType" value="1"
                                            <#if ruleRegister.calendarDaysType == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />7天
                                        </label>
                                             <label
                                            <#if ruleRegister.calendarDaysType == 2>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="calendarDaysType" value="2"
                                            <#if ruleRegister.calendarDaysType == 2>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />15天
                                        </label>
                                        <label
                                            <#if ruleRegister.calendarDaysType == 3>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="calendarDaysType" value="3"
                                            <#if ruleRegister.calendarDaysType == 3>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />30天
                                        </label>
                                        <label
                                            <#if ruleRegister.calendarDaysType == -1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="calendarDaysType" value="-1"
                                            <#if ruleRegister.calendarDaysType == -1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />自定义
                                        </label>
                                        <input type="text" class="span2 input33" name="customCalendarDays" id="customCalendarDays" style="width:80px;"
                                            value="${ruleRegister.customCalendarDays}"/> 天
                                    </div>
                                    <div class="line">
                                        <span class="text">号源缓存天数：</span>
                                        <input type="text" class="span2 input33" name="sourceCacheDays" id="sourceCacheDays" style="width:80px;"
                                            value="${ruleRegister.sourceCacheDays}"/> 天
                                    </div>
                                    <div class="line">
                                        <span class="text">跨平台取号天数：</span>
                                        <input type="text" class="span2 input33" name="unpaidRegDays" id="unpaidRegDays" style="width:80px;"
                                            value="${ruleRegister.unpaidRegDays}"/> 天
                                    </div>
                                    <div class="line">
                                        <span class="text">是否需要选择人群类型：</span>
                                        <label
                                            <#if ruleRegister.isViewPopulationType == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewPopulationType" value="1" onclick="setPopulationType(1)"
                                            <#if ruleRegister.isViewPopulationType == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isViewPopulationType == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewPopulationType" value="0" onclick="setPopulationType(0)"
                                            <#if ruleRegister.isViewPopulationType == 0>
                                               checked="checked" 
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line" id="populationTypeDiv"
                                    <#if ruleRegister.isViewPopulationType == 0>
                                        style="display:none;"
                                    </#if>>
                                        <span class="text">人群类型显示项：</span>
                                        <label
                                           <#if ruleRegister.populationType?contains("1")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="populationType" value="1"
                                           <#if ruleRegister.populationType?contains("1")>
                                               checked="checked"
                                           <#else></#if>
                                           />本地
                                        </label>
                                        <label style="margin-left: 20px;width:80px;padding-left: 30px;"
                                           <#if ruleRegister.populationType?contains("2")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="populationType" value="2"
                                           <#if ruleRegister.populationType?contains("2")>
                                               checked="checked"
                                           <#else>
                                           </#if>
                                           />外地
                                        </label>
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">是否需要选择预约类型：</span>
                                        <label
                                            <#if ruleRegister.isViewAppointmentType == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewAppointmentType" value="1" onclick="setAppointmentType(1)"
                                            <#if ruleRegister.isViewAppointmentType == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isViewAppointmentType == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewAppointmentType" value="0" onclick="setAppointmentType(0)"
                                            <#if ruleRegister.isViewAppointmentType == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line" id="appointmentTypeDiv"
                                    <#if ruleRegister.isViewAppointmentType == 0>
                                        style="display:none;"
                                    </#if>>
                                        <span class="text">预约类型显示项：</span>
                                        <label
                                           <#if ruleRegister.appointmentType?contains("1")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="appointmentType" value="1"
                                           <#if ruleRegister.appointmentType?contains("1")>
                                               checked="checked"
                                           <#else></#if>
                                           />一般
                                        </label>
                                        <label style="margin-left: 20px;width:80px;padding-left: 25px;"
                                           <#if ruleRegister.appointmentType?contains("2")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="appointmentType" value="2"
                                           <#if ruleRegister.appointmentType?contains("2")>
                                               checked="checked"
                                           <#else>
                                           </#if>
                                           />出院后复查
                                        </label>
                                        <label style="margin-left:10px;"
                                           <#if ruleRegister.appointmentType?contains("3")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="appointmentType" value="3"
                                           <#if ruleRegister.appointmentType?contains("3")>
                                               checked="checked"
                                           <#else>
                                           </#if>
                                           />社区转诊
                                        </label>
                                        <label style="margin-left:10px;"
                                           <#if ruleRegister.appointmentType?contains("4")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="appointmentType" value="4"
                                           <#if ruleRegister.appointmentType?contains("4")>
                                               checked="checked"
                                           <#else>
                                           </#if>
                                           />术后复查
                                        </label>
                                        <label style="margin-left:10px;"
                                           <#if ruleRegister.appointmentType?contains("5")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="appointmentType" value="5"
                                           <#if ruleRegister.appointmentType?contains("5")>
                                               checked="checked"
                                           <#else>
                                           </#if>
                                           />产前检查
                                        </label>
                                        <label style="margin-left:10px;"
                                           <#if ruleRegister.appointmentType?contains("6")>
                                               class="checkboxTwo inline check">
                                           <#else>
                                               class="checkboxTwo inline">
                                           </#if> 
                                           <input type="checkbox" name="appointmentType" value="6"
                                           <#if ruleRegister.appointmentType?contains("6")>
                                               checked="checked"
                                           <#else>
                                           </#if>
                                           />慢病预约
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">是否查询患者类型：</span>
                                        <label
                                            <#if ruleRegister.isQueryPatientType == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isQueryPatientType" value="1"
                                            <#if ruleRegister.isQueryPatientType == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isQueryPatientType == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isQueryPatientType" value="0"
                                            <#if ruleRegister.isQueryPatientType == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">是否显示病情描述：</span>
                                        <label
                                            <#if ruleRegister.isViewDiseaseDesc == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewDiseaseDesc" value="1"
                                            <#if ruleRegister.isViewDiseaseDesc == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isViewDiseaseDesc == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewDiseaseDesc" value="0"
                                            <#if ruleRegister.isViewDiseaseDesc == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">当班支付控制：</span>
                                        <label style="width:120px;"
                                            <#if ruleRegister.onlinePaymentControl == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="onlinePaymentControl" value="1"
                                            <#if ruleRegister.onlinePaymentControl == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />必须支付
                                        </label>
                                        <label style="width:120px;"
                                            <#if ruleRegister.onlinePaymentControl == 2>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="onlinePaymentControl" value="2"
                                            <#if ruleRegister.onlinePaymentControl == 2>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支付
                                        </label>
                                        <label style="width:120px;"
                                            <#if ruleRegister.onlinePaymentControl == 3>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="onlinePaymentControl" value="3"
                                            <#if ruleRegister.onlinePaymentControl == 3>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />暂不支付
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约支付控制：</span>
                                        <label style="width:120px;"
                                            <#if ruleRegister.appointmentPaymentControl == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="appointmentPaymentControl" value="1"
                                            <#if ruleRegister.appointmentPaymentControl == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />必须支付
                                        </label>
                                        <label style="width:120px;"
                                            <#if ruleRegister.appointmentPaymentControl == 2>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="appointmentPaymentControl" value="2"
                                            <#if ruleRegister.appointmentPaymentControl == 2>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支付
                                        </label>
                                        <label style="width:120px;"
                                            <#if ruleRegister.appointmentPaymentControl == 3>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="appointmentPaymentControl" value="3"
                                            <#if ruleRegister.appointmentPaymentControl == 3>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />暂不支付
                                        </label>
                                    </div>
                                    <#--<div class="line">
                                        <span class="text">在线支付控制：</span>
                                        <label style="width:120px;"
                                            <#if ruleRegister.onlinePaymentControl == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="onlinePaymentControl" value="1"
                                            <#if ruleRegister.onlinePaymentControl == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />必须在线支付
                                        </label>
                                        <label style="width:120px;"
                                            <#if ruleRegister.onlinePaymentControl == 2>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="onlinePaymentControl" value="2"
                                            <#if ruleRegister.onlinePaymentControl == 2>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不用在线支付
                                        </label>
                                        <label style="width:120px;"
                                            <#if ruleRegister.onlinePaymentControl == 3>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="onlinePaymentControl" value="3"
                                            <#if ruleRegister.onlinePaymentControl == 3>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持暂不支付
                                        </label>
                                    </div>
                                    -->
									<div class="line">
                                        <span class="text">显示费用是否分挂号费和诊查费：</span>
                                        <label
                                            <#if ruleRegister.isViewRegFee == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewRegFee" value="1"
                                            <#if ruleRegister.isViewRegFee == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleRegister.isViewRegFee == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isViewRegFee" value="0"
                                            <#if ruleRegister.isViewRegFee == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">当班挂号是否支持退费：</span>
                                         <label
                                            <#if ruleRegister.isSupportRefundOnDuty == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportRefundOnDuty" value="1"
                                            <#if ruleRegister.isSupportRefundOnDuty == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持
                                        </label>
                                              <label
                                            <#if ruleRegister.isSupportRefundOnDuty == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportRefundOnDuty" value="0"
                                            <#if ruleRegister.isSupportRefundOnDuty == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支持
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约挂号是否支持退费：</span>
                                        <label
                                            <#if ruleRegister.isSupportRefundAppointment == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportRefundAppointment" value="1"
                                            <#if ruleRegister.isSupportRefundAppointment == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持
                                        </label>
                                        <label
                                            <#if ruleRegister.isSupportRefundAppointment == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSupportRefundAppointment" value="0"
                                            <#if ruleRegister.isSupportRefundAppointment == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支持
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">异常订单是否支持退费：</span>
                                        <label
                                            <#if ruleRegister.isExceptionRefundOrder == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isExceptionRefundOrder" value="1"
                                            <#if ruleRegister.isExceptionRefundOrder == 1>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />支持
                                        </label>
                                        <label
                                            <#if ruleRegister.isExceptionRefundOrder == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isExceptionRefundOrder" value="0"
                                            <#if ruleRegister.isExceptionRefundOrder == 0>
                                                checked="checked"
                                            <#else>
                                            </#if>
                                            />不支持
                                        </label>
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">选择医生页面样式：</span>
                                        <label
                                            <#if ruleRegister.chooseDoctorStyle == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="chooseDoctorStyle" value="1"
                                            <#if ruleRegister.chooseDoctorStyle == 1>checked="checked"<#else></#if>/>样式1
                                        </label>
                                        <label
                                            <#if ruleRegister.chooseDoctorStyle == 2>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="chooseDoctorStyle" value="2"
                                            <#if ruleRegister.chooseDoctorStyle == 2>checked="checked" <#else></#if>/>样式2
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">当天就诊详情是否显示时间段：</span>
                                        <label
                                            <#if ruleRegister.showRegPeriod == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="showRegPeriod" value="1"
                                            <#if ruleRegister.showRegPeriod == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleRegister.showRegPeriod == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="showRegPeriod" value="0"
                                            <#if ruleRegister.showRegPeriod == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">是否按照医生职称排序：</span>
                                        <label
                                            <#if ruleRegister.isOrderByDoctorTitle == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isOrderByDoctorTitle" value="1"
                                            <#if ruleRegister.isOrderByDoctorTitle == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleRegister.isOrderByDoctorTitle == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isOrderByDoctorTitle" value="0"
                                            <#if ruleRegister.isOrderByDoctorTitle == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">职称顺序：</span>
                                        <input type="text" class="span8 input33" name="doctorTitleOrder" id="doctorTitleOrder" 
                                            value="${ruleRegister.doctorTitleOrder}"/>
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认挂号是否弹窗提示[微信医保]：</span>
                                        <label
                                            <#if ruleRegister.isconfirmTipMedicareWechat == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareWechat" value="1"
                                            <#if ruleRegister.isconfirmTipMedicareWechat == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleRegister.isconfirmTipMedicareWechat == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareWechat" value="0"
                                            <#if ruleRegister.isconfirmTipMedicareWechat == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认挂号是否弹窗提示[支付宝医保]：</span>
                                        <label
                                            <#if ruleRegister.isconfirmTipMedicareAlipay == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareAlipay" value="1"
                                            <#if ruleRegister.isconfirmTipMedicareAlipay == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleRegister.isconfirmTipMedicareAlipay == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipMedicareAlipay" value="0"
                                            <#if ruleRegister.isconfirmTipMedicareAlipay == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认挂号是否弹窗提示[微信自费]：</span>
                                        <label
                                            <#if ruleRegister.isconfirmTipSelfPayWechat == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayWechat" value="1"
                                            <#if ruleRegister.isconfirmTipSelfPayWechat == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleRegister.isconfirmTipSelfPayWechat == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayWechat" value="0"
                                            <#if ruleRegister.isconfirmTipSelfPayWechat == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">确认挂号是否弹窗提示[支付宝自费]：</span>
                                        <label
                                            <#if ruleRegister.isconfirmTipSelfPayAlipay == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayAlipay" value="1"
                                            <#if ruleRegister.isconfirmTipSelfPayAlipay == 1>checked="checked"<#else></#if>/>是
                                        </label>
                                        <label
                                            <#if ruleRegister.isconfirmTipSelfPayAlipay == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isconfirmTipSelfPayAlipay" value="0"
                                            <#if ruleRegister.isconfirmTipSelfPayAlipay == 0>checked="checked" <#else></#if>/>否
                                        </label> 
                                    </div>
                                    
                                    <div class="line">
                                        <span class="text">挂号费用别名：</span>
                                        <input type="text" class="span8 input33" name="regFeeAlias" id="regFeeAlias" 
                                            value="${ruleRegister.regFeeAlias}"/>
                                    </div>
                                    
                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top: 225px;width:150px;">挂号节点控制</label>
                                <div class="controls" style="margin-left: 213px;">
                                    <div class="line">
                                        <span class="text2" style="width:270px;">当班挂号允许挂号时间段：</span>
                                        <input type="text" class="span2 input33" name="onDutyRegStartTime" id="onDutyRegStartTime" 
                                            value="${ruleRegister.onDutyRegStartTime?string('HH:mm')}"/> -
                                        <input type="text" class="span2 input33" name="onDutyRegEndTime" id="onDutyRegEndTime" 
                                            value="${ruleRegister.onDutyRegEndTime?string('HH:mm')}"/>　(例如00:00 - 16:00)
                                    </div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">允许预约明天号的截止时间点：</span>
                                        <input type="text" class="span2 input33" name="appointmentTomorrowEndTime" id="appointmentTomorrowEndTime" 
                                            value="${ruleRegister.appointmentTomorrowEndTime?string('HH:mm')}"/>　(例如16:00)</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">(必须在线支付)挂号支付时间：</span>
                                        <input type="text" class="span2 input33" name="paymentTimeOutTime" id="paymentTimeOutTime"
                                            value="${ruleRegister.paymentTimeOutTime}"/>　分钟</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">每张就诊卡每天允许挂同一医生号次数：</span>
                                        <input type="text" class="span2 input33" name="regMaximumSameDoctor" id="regMaximumSameDoctor"
                                            value="${ruleRegister.regMaximumSameDoctor}"/>　次</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">每张就诊卡每天允许挂号次数：</span>
                                        <input type="text" class="span2 input33" name="regMaximumInDay" id="regMaximumInDay" 
                                            value="${ruleRegister.regMaximumInDay}"/>　次</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">每张就诊卡每周允许挂号次数：</span>
                                        <input type="text" class="span2 input33" name="regMaximumInWeek" id="regMaximumInWeek" 
                                            value="${ruleRegister.regMaximumInWeek}"/>　次</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">每张就诊卡每月允许挂号次数：</span>
                                        <input type="text" class="span2 input33" name="regMaximumInMonth" id="regMaximumInMonth" 
                                            value="${ruleRegister.regMaximumInMonth}"/>　次</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">每张就诊卡每天允许取消挂号次数，超过则无法再挂号：</span>
                                        <input type="text" class="span2 input33" name="regCancelMaxnumInDay" id="regCancelMaxnumInDay" 
                                            value="${ruleRegister.regCancelMaxnumInDay}"/>　次</div>
                                    <div class="line">
                                        <span class="text2" style="width:270px;">每张就诊卡每月允许取消挂号次数，超过则无法再挂号：</span>
                                        <input type="text" class="span2 input33" name="regCancelMaxnumInMonth" id="regCancelMaxnumInMonth" 
                                            value="${ruleRegister.regCancelMaxnumInMonth}"/>　次</div>
                                    <div class="line"><span class="text2" style="width:270px;">(暂不支付)支付截止时间点：</span>
                                        <div class="my_select">
                                            <select name="notPaidpayOffType" id="notPaidpayOffType" onchange="onchangeNotPaidpayOffType();">
                                                <option value="1" <#if ruleRegister.notPaidpayOffType == 1>selected="selected"</#if>>就诊前一天几点</option>
                                                <option value="2" <#if ruleRegister.notPaidpayOffType == 2>selected="selected"</#if>>就诊当天几点</option>
                                                <option value="3" <#if ruleRegister.notPaidpayOffType == 3>selected="selected"</#if>>就诊时间段前几小时</option>
                                                <option value="4" <#if ruleRegister.notPaidpayOffType == 4>selected="selected"</#if>>就诊时间段开始前多少分钟</option>
                                                <option value="5" <#if ruleRegister.notPaidpayOffType == 5>selected="selected"</#if>>就诊时间段开始的时间</option>
                                                <option value="6" <#if ruleRegister.notPaidpayOffType == 6>selected="selected"</#if>>就诊时间段结束的时间</option>
                                                <option value="7" <#if ruleRegister.notPaidpayOffType == 7>selected="selected"</#if>>就诊时间结束前多少分钟</option>
                                                <option value="8" <#if ruleRegister.notPaidpayOffType == 8>selected="selected"</#if>>不限制</option>
                                            </select>
                                        </div>　
                                        <input type="text" 
                                        <#if  ruleRegister.notPaidpayOffType == 5 || ruleRegister.notPaidpayOffType == 6 || ruleRegister.notPaidpayOffType ==8>
                                        style="display:none" 
                                        </#if>
                                        class="span2 input33" name="notPaidpayOffTime" id="notPaidpayOffTime" 
                                            value="${ruleRegister.notPaidpayOffTime}"/>　
                                    </div>
                                    <div class="line"><span class="text2" style="width:270px;">取消预约挂号截止时间点：</span>
                                        <div class="my_select">
                                            <select name="cancelBespeakCloseType" id="cancelBespeakCloseType" onchange="onchangeBespeakCloseType();">
                                                <option value="1" <#if ruleRegister.cancelBespeakCloseType == 1>selected="selected"</#if>>就诊前一天几点</option>
                                                <option value="2" <#if ruleRegister.cancelBespeakCloseType == 2>selected="selected"</#if>>就诊当天几点</option>
                                                <option value="3" <#if ruleRegister.cancelBespeakCloseType == 3>selected="selected"</#if>>就诊时间段前几小时</option>
                                                <option value="4" <#if ruleRegister.cancelBespeakCloseType == 4>selected="selected"</#if>>就诊时间段开始前多少分钟</option>
                                                <option value="5" <#if ruleRegister.cancelBespeakCloseType == 5>selected="selected"</#if>>就诊时间段开始的时间</option>
                                                <option value="6" <#if ruleRegister.cancelBespeakCloseType == 6>selected="selected"</#if>>就诊时间段结束的时间</option>
                                                <option value="7" <#if ruleRegister.cancelBespeakCloseType == 7>selected="selected"</#if>>就诊时间结束前多少分钟</option>
                                                <option value="8" <#if ruleRegister.cancelBespeakCloseType == 8>selected="selected"</#if>>不限制</option>
                                            </select>
                                        </div>　
                                        <input type="text" 
                                        <#if  ruleRegister.cancelBespeakCloseType == 5 || ruleRegister.cancelBespeakCloseType == 6 || ruleRegister.cancelBespeakCloseType ==8>
                                        style="display:none" 
                                        </#if>
                                            class="span2 input33" name="cancelBespeakEndTime" id="cancelBespeakEndTime" 
                                            value="${ruleRegister.cancelBespeakEndTime}"/>　
                                    </div>
                                    <div class="line"><span class="text2" style="width:270px;">取消当班挂号截止时间点：</span>
                                        <div class="my_select">
                                            <select name="cancelOnDutyCloseType" id="cancelOnDutyCloseType" onchange="onchangeOnDutyCloseType();">
                                                <option value="1" <#if ruleRegister.cancelOnDutyCloseType == 1>selected="selected"</#if>>就诊当天几点</option>
                                                <option value="2" <#if ruleRegister.cancelOnDutyCloseType == 2>selected="selected"</#if>>就诊时间段开始前几小时</option>
                                                <option value="3" <#if ruleRegister.cancelOnDutyCloseType == 3>selected="selected"</#if>>就诊时间段开始前多少分钟</option>
                                                <option value="4" <#if ruleRegister.cancelOnDutyCloseType == 4>selected="selected"</#if>>就诊时间段开始的时间</option>
                                                <option value="5" <#if ruleRegister.cancelOnDutyCloseType == 5>selected="selected"</#if>>就诊时间段结束的时间</option>
                                                <option value="6" <#if ruleRegister.cancelOnDutyCloseType == 6>selected="selected"</#if>>就诊时间段结束前多少分钟</option>
                                                <option value="7" <#if ruleRegister.cancelOnDutyCloseType == 7>selected="selected"</#if>>不限制</option>
                                            </select>
                                        </div>　
                                        <input type="text" 
                                        <#if  ruleRegister.cancelOnDutyCloseType == 4 || ruleRegister.cancelOnDutyCloseType == 5 || ruleRegister.cancelOnDutyCloseType ==7>
                                        style="display:none" 
                                        </#if>
                                        class="span2 input33" name="cancelOnDutyEndTimes" id="cancelOnDutyEndTimes" 
                                            value="${ruleRegister.cancelOnDutyEndTimes}"/>　
                                    </div>

                                </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top: 410px;width:150px;">提示语设置</label>
                                <div class="controls" style="margin-left: 213px;">
                                    <div class="line">
                                        <span class="text3" style="width:270px;">所选日期没有号源信息提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="noSourceTipContent" 
                                            id="noSourceTipContent" value="${ruleRegister.noSourceTipContent}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">当班挂号温馨提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="onDutyRegTipContent" 
                                            id="onDutyRegTipContent" value="${ruleRegister.onDutyRegTipContent}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过当班挂号截止时间点的提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="onDutyRegOverTimeTip" 
                                            id="onDutyRegOverTimeTip" value="${ruleRegister.onDutyRegOverTimeTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过预约明天号截止时间点的提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="bespeakRegOverTimeTip" 
                                            id="bespeakRegOverTimeTip" value="${ruleRegister.bespeakRegOverTimeTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">挂号失败提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="regFailedTip" 
                                            id="regFailedTip" value="${ruleRegister.regFailedTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">可享受医院挂号优惠提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="regDiscountTip" 
                                            id="regDiscountTip" value="${ruleRegister.regDiscountTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过每天挂同一医生号次数提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="overMaximumSameDoctorTip" 
                                            id="overMaximumSameDoctorTip" value="${ruleRegister.overMaximumSameDoctorTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过每天允许挂号次数提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="overMaximumInDayTip" 
                                            id="overMaximumInDayTip" value="${ruleRegister.overMaximumInDayTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过每周允许挂号次数提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="overMaximumInWeekTip" 
                                            id="overMaximumInWeekTip" value="${ruleRegister.overMaximumInWeekTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过每月允许挂号次数提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="overMaximumInMonthTip" 
                                            id="overMaximumInMonthTip" value="${ruleRegister.overMaximumInMonthTip}"/>　
                                    </div>
                                     <div class="line">
                                        <span class="text3" style="width:270px;">达到每天允许取消挂号次数，无法再挂号提示语(挂号时)：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="overCancelMaxnumInDayTip" 
                                            id="overCancelMaxnumInDayTip" value="${ruleRegister.overCancelMaxnumInDayTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">达到每月允许取消挂号次数，无法再挂号提示语(挂号时)：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="overCancelMaxnumInMonthTip" 
                                            id="overCancelMaxnumInMonthTip" value="${ruleRegister.overCancelMaxnumInMonthTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">即将达到每天取消次数上限时提醒信息(取消时)：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="willReachCancelMaxnumInDayTip" 
                                            id="willReachCancelMaxnumInDayTip" value="${ruleRegister.willReachCancelMaxnumInDayTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">即将达到每月取消次数上限时提醒信息(取消时)：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="willReachCancelMaxnumInMonthTip" 
                                            id="willReachCancelMaxnumInMonthTip" value="${ruleRegister.willReachCancelMaxnumInMonthTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">达到每天取消次数上限时提醒信息(取消时)：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="hadReachCancelMaxnumInDayCancelTip" 
                                            id="hadReachCancelMaxnumInDayCancelTip" value="${ruleRegister.hadReachCancelMaxnumInDayCancelTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">达到每月取消次数上限时提醒信息(取消时)：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="hadReachCancelMaxnumInMonthCancelTip" 
                                            id="hadReachCancelMaxnumInMonthCancelTip" value="${ruleRegister.hadReachCancelMaxnumInMonthCancelTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过取消预约挂号截止时间点提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="cancelBespeakTimeOutTip" 
                                            id="cancelBespeakTimeOutTip" value="${ruleRegister.cancelBespeakTimeOutTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">超过取消当班挂号截止时间点提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="cancelOnDutyTimeOutTip" 
                                            id="cancelOnDutyTimeOutTip" value="${ruleRegister.cancelOnDutyTimeOutTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">黑名单用户挂号提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="blacklistUserRegTip" 
                                            id="blacklistUserRegTip" value="${ruleRegister.blacklistUserRegTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认支付信息提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmRegInfoTip" 
                                            id="confirmRegInfoTip" value="${ruleRegister.confirmRegInfoTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">挂号支付超时提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="regPayTimeOutTip" 
                                            id="regPayTimeOutTip" value="${ruleRegister.regPayTimeOutTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">挂号成功（已缴费）时提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="regSuccessHadPayTip" 
                                            id="regSuccessHadPayTip" value="${ruleRegister.regSuccessHadPayTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">挂号成功（未缴费）时提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="regSuccessNoPayTip" 
                                            id="regSuccessNoPayTip" value="${ruleRegister.regSuccessNoPayTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">挂号成功（当班挂号）时提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="regSuccessOnDutyTip" 
                                            id="regSuccessOnDutyTip" value="${ruleRegister.regSuccessOnDutyTip}"/>　
                                    </div>
									<div class="line">
                                        <span class="text3" style="width:270px;">挂号前温馨提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="preRegisterWarmTip" 
                                            id="preRegisterWarmTip" value="${ruleRegister.preRegisterWarmTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">挂号详情-无排队号时提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="serialNumTip" 
                                            id="serialNumTip" value="${ruleRegister.serialNumTip}"/>　
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">患者类型查询处提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="patientTypeTip" 
                                            id="patientTypeTip" value="${ruleRegister.patientTypeTip}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">取号须支付提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="takeNoNeedPayTip" 
                                            id="takeNoNeedPayTip" value="${ruleRegister.takeNoNeedPayTip}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">取号须就诊当天提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="takeNoUntimelyTip" 
                                            id="takeNoUntimelyTip" value="${ruleRegister.takeNoUntimelyTip}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">取号详情页面提示语：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="takeNoDetailTip" 
                                            id="takeNoDetailTip" value="${ruleRegister.takeNoDetailTip}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认挂号弹框提示语【微信医保】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipMedicareWechat" 
                                            id="confirmTipMedicareWechat" value="${ruleRegister.confirmTipMedicareWechat}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认挂号弹框提示语【支付宝医保】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipMedicareAlipay" 
                                            id="confirmTipMedicareAlipay" value="${ruleRegister.confirmTipMedicareAlipay}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认挂号弹框提示语【微信自费】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipSelfPayWechat" 
                                            id="confirmTipSelfPayWechat" value="${ruleRegister.confirmTipSelfPayWechat}"/>
                                    </div>
                                    <div class="line">
                                        <span class="text3" style="width:270px;">确认挂号弹框提示语【支付宝自费】：</span>
                                        <input type="text" placeholder="点击输入提示语" class="span5 input33" name="confirmTipSelfPayAlipay" 
                                            id="confirmTipSelfPayAlipay" value="${ruleRegister.confirmTipSelfPayAlipay}"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleRegisterForm' , 'RuleRegister')">保存</button>
                </form>
            </div>
        </div>

    </div>
</div>
<!--content end-->
</body>
</html>
<script type="text/javascript">
function onchangeOnDutyCloseType(){
   var type =  $("#cancelOnDutyCloseType").val();
   if(type == 4 || type == 5 || type == 7){
        $("#cancelOnDutyEndTimes").hide();
        $("#cancelOnDutyEndTimes").val("");
   }else{
        $("#cancelOnDutyEndTimes").show();
   }
}
function onchangeBespeakCloseType(){
   var type =  $("#cancelBespeakCloseType").val();
   if(type == 5 || type == 6 || type == 8){
        $("#cancelBespeakEndTime").hide();
        $("#cancelBespeakEndTime").val("");
   }else{
        $("#cancelBespeakEndTime").show();
   }
}
function onchangeNotPaidpayOffType(){
   var type =  $("#notPaidpayOffType").val();
   if(type == 5 || type == 6 || type == 8){
        $("#notPaidpayOffTime").hide();
        $("#notPaidpayOffTime").val("");
   }else{
        $("#notPaidpayOffTime").show();
   }
}
function setPopulationType(val){
    var populationTypeDiv = $("#populationTypeDiv");
    if(val == 0){
        $(populationTypeDiv).hide();
    }else{
        $(populationTypeDiv).show();
    }
}

function setAppointmentType(val){
    var appointmentTypeDiv = $("#appointmentTypeDiv");
    if(val == 0){
        $(appointmentTypeDiv).hide();
    }else{
        $(appointmentTypeDiv).show();
    }
}

</script>
