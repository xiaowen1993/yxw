            <div class="cur_set">
                                当前医院：${hospitalName}
                <select name="ruleSelect" id="ruleSelect" class="mini" onchange="ruleJS.ruleEdit('${hospitalId}' ,this.value)">
                    <option value="ruleEdit"    <#if ruleType == "ruleEdit">selected="selected"</#if>>全局配置</option>
                    <option value="ruleTiedCard" <#if ruleType == "ruleTiedCard">selected="selected"</#if>>绑卡配置</option>
                    <option value="ruleRegister" <#if ruleType == "ruleRegister">selected="selected"</#if>>挂号配置</option>
                    <option value="ruleClinic" <#if ruleType == "ruleClinic">selected="selected"</#if> >门诊缴费配置</option>
                    <option value="rulePayment" <#if ruleType == "rulePayment">selected="selected"</#if> >支付方式配置</option>
                    <option value="ruleQuery"   <#if ruleType == "ruleQuery">selected="selected"</#if>>查询配置</option>
                    <#--<option value="ruleUserCenter"    <#if ruleType == "ruleUserCenter">selected="selected"</#if>>个人中心配置</option>-->
                    <option value="ruleOnlineFiling" <#if ruleType == "ruleOnlineFiling">selected="selected"</#if>>在线建档配置</option>
                    <option value="rulePush"   <#if ruleType == "rulePush">selected="selected"</#if>>消息推送配置</option>
                    <option value="ruleFriedExpress"   <#if ruleType == "ruleFriedExpress">selected="selected"</#if>>代煎配送配置</option>
                    <option value="ruleInHospital"   <#if ruleType == "ruleInHospital">selected="selected"</#if>>住院规则配置</option>
                    <option value="ruleHisData"   <#if ruleType == "ruleHisData">selected="selected"</#if>>his轮询数据配置</option>
                </select>
            </div>