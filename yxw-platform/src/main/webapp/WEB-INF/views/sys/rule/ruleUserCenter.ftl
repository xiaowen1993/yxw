<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>个人中心规则配置</title>
</head>
<body>
<!--sidebar-menu end-->
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">个人中心规则</h3>
            <#include "/sys/rule/rule_select.ftl">
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box bangKa">
                <div class="space10"></div>
                <form class="form-horizontal evenBg" id="ruleUserCenterForm">
                    <input type="hidden" name="hospitalId" value="${ruleUserCenter.hospitalId}"/>
                    <input type="hidden" id="id" name="id" value="${ruleUserCenter.id}"/>
                    <div class="widget-content">
                        <div class="row-fluid">
                            <div class="control-group">
                                <label class="control-label">首页显示内容</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("1")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="1"
                                        <#if ruleUserCenter.indexContent?contains("1")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />我的家人
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("2")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="2"
                                        <#if ruleUserCenter.indexContent?contains("2")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />挂号记录
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("3")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="3"
                                        <#if ruleUserCenter.indexContent?contains("3")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />缴费记录
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("4")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="4"
                                        <#if ruleUserCenter.indexContent?contains("4")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />我的医生
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("5")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="5"
                                        <#if ruleUserCenter.indexContent?contains("5")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />我的咨询
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("6")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="6"
                                        <#if ruleUserCenter.indexContent?contains("6")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />我的消息
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("7")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="7"
                                        <#if ruleUserCenter.indexContent?contains("7")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />我的服务
                                    </label>
                                     <label
                                        <#if ruleUserCenter.indexContent?contains("8")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="8"
                                        <#if ruleUserCenter.indexContent?contains("8")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />健康记录
                                    </label>
                                     <label
                                        <#if ruleUserCenter.indexContent?contains("9")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="9"
                                        <#if ruleUserCenter.indexContent?contains("9")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />服务评价
                                    </label>
                                    <label
                                        <#if ruleUserCenter.indexContent?contains("10")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="indexContentArray" value="10"
                                        <#if ruleUserCenter.indexContent?contains("10")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />配送地址管理
                                    </label>
                                </div>
							</div>
                            <div class="control-group">
                                <label class="control-label">条形码格式</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleUserCenter.barcodeStyle == 1>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="1"
                                        <#if ruleUserCenter.barcodeStyle == 1>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />EAN码
                                    </label>
                                    <label
                                        <#if ruleUserCenter.barcodeStyle == 2>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="2"
                                        <#if ruleUserCenter.barcodeStyle == 2>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />UPC码 
                                    </label>
                                    <label
                                        <#if ruleUserCenter.barcodeStyle == 3>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="3"
                                        <#if ruleUserCenter.barcodeStyle == 3>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />39码
                                    </label>
                                    <label
                                        <#if ruleUserCenter.barcodeStyle == 4>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="4"
                                        <#if ruleUserCenter.barcodeStyle == 4>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />128码 
                                    </label>
                                    <label
                                        <#if ruleUserCenter.barcodeStyle == 5>
                                           class="radio inline check">
                                        <#else>
                                           class="radio inline">
                                        </#if> 
                                        <input type="radio" name="barcodeStyle" value="5"
                                        <#if ruleUserCenter.barcodeStyle == 5>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />库德巴码 
                                    </label>
                                </div>
                            </div>
                            <div class="control-group">
                                <label class="control-label">个人信息显示内容</label>
                                <div class="controls ">
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("1")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="1"
                                        <#if ruleUserCenter.userInfoContent?contains("1")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />条形码
                                    </label>
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("2")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="2"
                                        <#if ruleUserCenter.userInfoContent?contains("2")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />证件号
                                    </label>
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("3")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="3"
                                        <#if ruleUserCenter.userInfoContent?contains("3")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />手机号
                                    </label>
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("4")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="4"
                                        <#if ruleUserCenter.userInfoContent?contains("4")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />卡号
                                    </label>
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("5")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="5"
                                        <#if ruleUserCenter.userInfoContent?contains("5")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />住院号
                                    </label>
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("6")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="6"
                                        <#if ruleUserCenter.userInfoContent?contains("6")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />医保卡号
                                    </label>
                                    <label
                                        <#if ruleUserCenter.userInfoContent?contains("7")>
                                           class="checkboxTwo inline check">
                                        <#else>
                                           class="checkboxTwo inline">
                                        </#if> 
                                        <input type="checkbox" name="userInfoContentArray" value="7"
                                        <#if ruleUserCenter.userInfoContent?contains("7")>
                                            checked="checked"
                                        <#else>
                                        </#if>
                                        />住院人身份证
                                    </label>    
                                </div>
							</div>
                        </div>
                    </div>
                    <div class="space20"></div>
                    <button class="btn btn-save" type="button" onclick="ruleJS.saveRule('ruleUserCenterForm' , 'RuleUserCenter')">保存</button>
                </form>
            </div>
        </div>

    </div>
</div>
<!--content end-->
</body>
</html>
