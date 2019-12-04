<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>his轮询数据配置</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">his轮询数据配置</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="ruleHisDataForm">
                    <input type="hidden" name="hospitalId" value="${ruleHisData.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleHisData.id}"/>
                    <div class="widget-content guaHao">
                        <div class="row-fluid">
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top:0px;width:150px;">科室数据设置</label>
                                <div class="controls" style="margin-left: 213px;">
                                    <div class="line">
                                        <span class="text">当班/预约科室是否相同：</span>
                                        <label
                                            <#if ruleHisData.isSameDeptData == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSameDeptData" value="1"
                                            <#if ruleHisData.isSameDeptData == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleHisData.isSameDeptData == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSameDeptData" value="0"
                                            <#if ruleHisData.isSameDeptData == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                 </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top: 0px;width:150px;">医生数据设置</label>
                                <div class="controls" style="margin-left: 213px;">
                                    <div class="line">
                                        <span class="text">当班是否有缓存接口：</span>
                                        <label
                                            <#if ruleHisData.isHadCurDoctorInterface == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadCurDoctorInterface" value="1"
                                            <#if ruleHisData.isHadCurDoctorInterface == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleHisData.isHadCurDoctorInterface == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadCurDoctorInterface" value="0"
                                            <#if ruleHisData.isHadCurDoctorInterface == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约是否有缓存接口：</span>
                                        <label
                                            <#if ruleHisData.isHadAppointmentDoctorInterface == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadAppointmentDoctorInterface" value="1"
                                            <#if ruleHisData.isHadAppointmentDoctorInterface == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleHisData.isHadAppointmentDoctorInterface == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadAppointmentDoctorInterface" value="0"
                                            <#if ruleHisData.isHadAppointmentDoctorInterface == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                 
                                    <div class="line">
                                        <span class="text">当班/预约数据是否相同：</span>
                                        <label
                                            <#if ruleHisData.isSameDoctorData == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSameDoctorData" value="1"
                                            <#if ruleHisData.isSameDoctorData == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleHisData.isSameDoctorData == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isSameDoctorData" value="0"
                                            <#if ruleHisData.isSameDoctorData == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                 </div>
                            </div>
                            <div class="control-group w162">
                                <label class="control-label" style="padding-top: 0px;width:150px;">号源数据设置</label>
                                <div class="controls" style="margin-left: 213px;">
                                    <div class="line">
                                        <span class="text">当班是否有缓存接口：</span>
                                        <label
                                            <#if ruleHisData.isHadCurRegSourceInterface == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadCurRegSourceInterface" value="1"
                                            <#if ruleHisData.isHadCurRegSourceInterface == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleHisData.isHadCurRegSourceInterface == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadCurRegSourceInterface" value="0"
                                            <#if ruleHisData.isHadCurRegSourceInterface == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                    <div class="line">
                                        <span class="text">预约是否有缓存接口：</span>
                                        <label
                                            <#if ruleHisData.isHadAppointmentRegSourceInterface == 1>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadAppointmentRegSourceInterface" value="1"
                                            <#if ruleHisData.isHadAppointmentRegSourceInterface == 1>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />是
                                        </label>
                                        <label
                                            <#if ruleHisData.isHadAppointmentRegSourceInterface == 0>
                                               class="radio inline check">
                                            <#else>
                                               class="radio inline">
                                            </#if> 
                                            <input type="radio" name="isHadAppointmentRegSourceInterface" value="0"
                                            <#if ruleHisData.isHadAppointmentRegSourceInterface == 0>
                                               checked="checked"
                                            <#else>
                                            </#if>
                                            />否
                                        </label>
                                    </div>
                                 </div>
                            </div>
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleHisDataForm' , 'RuleHisData')">保存</button>
                </form>
            </div>
        </div>

    </div>
</div>
<!--content end-->
</body>
</html>