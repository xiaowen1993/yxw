<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>就诊评价</title>
</head>
<body>
<div id="body">
    <div id="vote-wrap">
        <form id="voteForm" name="voteForm" method="post">
        <div class="voteBox">
            <div class="page-title">请对本次就诊服务进行评价</div>
            <div class="grid_w">
                <div id="yesDiv" data-selected="1" onclick="chooseVote(this);"
                <#if entity.appraiseLevel == 1>
                    class="grid active"
                <#else>
                    class="grid"
                </#if>
                >
                    <div class="box">
                        <div class="first"></div>
                        <div style="display: block;">
                        <span class="txt">赞</span>
                        </div>
                    </div>
                </div>
                <div id="noDiv" data-selected="0" onclick="chooseVote(this)"
                <#if entity.appraiseLevel == 0>
                    class="grid active"
                <#else>
                    class="grid"
                </#if>
                >
                    <div class="box fore">
                        <div class="second"></div>
                        <div style="display: block;">
                        <span class="txt">踩</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="vote-w">
                <div class="e-box" id="serviceLevelDiv">
                    <div class="list">
                        <span class="text">服务态度</span>
                        <div class="evel_box js-first">
                            <ul>
                                <li onclick="chooseServiceLevel(1)" <#if entity.serviceLevel gt 0>class="active"</#if> ></li>
                                <li onclick="chooseServiceLevel(2)" <#if entity.serviceLevel gt 1>class="active"</#if> ></li>
                                <li onclick="chooseServiceLevel(3)" <#if entity.serviceLevel gt 2>class="active"</#if> ></li>
                                <li onclick="chooseServiceLevel(4)" <#if entity.serviceLevel gt 3>class="active"</#if> ></li>
                                <li onclick="chooseServiceLevel(5)" <#if entity.serviceLevel gt 4>class="active"</#if> ></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="e-box" id="skillLevelDiv">
                <div class="list">
                    <span class="text">服务技术</span>
                    <div class="evel_box js-second">
                        <ul>
                            <li onclick="chooseSkillLevel(1)" <#if entity.skillLevel gt 0 >class="active"</#if> ></li>
                            <li onclick="chooseSkillLevel(2)" <#if entity.skillLevel gt 1>class="active"</#if> ></li>
                            <li onclick="chooseSkillLevel(3)" <#if entity.skillLevel gt 2>class="active"</#if> ></li>
                            <li onclick="chooseSkillLevel(4)" <#if entity.skillLevel gt 3>class="active"</#if> ></li>
                            <li onclick="chooseSkillLevel(5)" <#if entity.skillLevel gt 4>class="active"</#if> ></li>
                        </ul>
                    </div>
                </div>
            </div>
            </div>
            <div class="frm-box">
                <textarea id="" class="frm-textarea" rows="3"  placeholder="请输入您的意见和建议" id="suggestion" name="suggestion">${entity.suggestion}</textarea>
            </div>
        </div>
        <div class="section btn-w js_tijiao">
            <div class="btn btn-ok btn-block" onclick="votePrise();"> 提交评价</div>
        </div>
        <input type="hidden" id="id" name="id" value="${entity.id}"/>
        <input type="hidden" id="hospitalId" name="hospitalId" value="${entity.hospitalId}"/>
        <input type="hidden" id="hospitalCode" name="hospitalCode" value="${entity.hospitalCode}"/>
        <input type="hidden" id="hospitalName" name="hospitalName" value="${entity.hospitalName}"/>
        <input type="hidden" id="appId" name="appId" value="${entity.appId}"/>
        <input type="hidden" id="appCode" name="appCode" value="${entity.appCode}"/>
        <input type="hidden" id="openId" name="openId" value="${entity.openId}"/>
        <input type="hidden" id="cardNo" name="cardNo" value="${entity.cardNo}"/>
        <input type="hidden" id="bizCode" name="bizCode" value="${entity.bizCode}"/>
        <input type="hidden" id="appraiseLevel" name="appraiseLevel" value="${entity.appraiseLevel}"/>
        <input type="hidden" id="orderNo" name="orderNo" value="${entity.orderNo}"/>
        <input type="hidden" id="raterCode" name="raterCode" value="${entity.raterCode}"/>
        <input type="hidden" id="fowardUrl" name="fowardUrl" value="${entity.fowardUrl}"/>
        <input type="hidden" id="serviceLevel" name="serviceLevel" value="${entity.serviceLevel}"/>
        <input type="hidden" id="skillLevel" name="skillLevel" value="${entity.skillLevel}"/>
        </form>
    </div>
</div>
<div class="vote-mask"></div>
</body>
<#include "/easyhealth/common/footer.ftl">
</html>
<script type="text/javascript">
    var appraiseLevelVal = '${entity.appraiseLevel}';
    var serviceLevelVal  = '${entity.serviceLevel}';
    var skillLevelVal    = '${entity.skillLevel}';
</script>
<script type="text/javascript" src="${basePath}yxw.app/js/biz/static/vote.js"></script>
