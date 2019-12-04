<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>已评价详情</title>
</head>
<body>
<div id="body">
    <div id="vote">
        <div class="space15"></div>
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem w200">就诊人</div>
                <div class="flexItem color666 textRight">${entity.patientName}(${entity.cardNo})</div>
            </li>
            <li class="flex">
                <div class="flexItem w200">日期</div>
                <div class="flexItem color666 textRight">${entity.createTimeStr}</div>
            </li>
            <li class="flex">
                <div class="flexItem w200">总体评价</div>
                <div class="flexItem color666 textRight">
                <#if entity.appraiseLevel == 0>
                                            踩
                <#else>
                                            赞
                </#if>
                </div>
            </li>
            <li class="flex">
                <div class="flexItem w200">服务态度</div>
                <div class="flexItem color666 textRight">
                    <div class="evel_box js-second">
                        <ul>
                            <li <#if entity.serviceLevel gt 0>class="active"</#if> ></li>
                            <li <#if entity.serviceLevel gt 1>class="active"</#if> ></li>
                            <li <#if entity.serviceLevel gt 2>class="active"</#if> ></li>
                            <li <#if entity.serviceLevel gt 3>class="active"</#if> ></li>
                            <li <#if entity.serviceLevel gt 4>class="active"</#if> ></li>
                        </ul>
                    </div>
                </div>
            </li>
            <li class="flex">
                <div class="flexItem w200">服务技术</div>
                <div class="flexItem color666 textRight">
                    <div class="evel_box js-second">
                        <ul>
                            <li <#if entity.skillLevel gt 0>class="active"</#if> ></li>
                            <li <#if entity.skillLevel gt 1>class="active"</#if> ></li>
                            <li <#if entity.skillLevel gt 2>class="active"</#if> ></li>
                            <li <#if entity.skillLevel gt 3>class="active"</#if> ></li>
                            <li <#if entity.skillLevel gt 4>class="active"</#if> ></li>
                        </ul>
                    </div>
                </div>
            </li>
            <li>
                <div class="flexItem w200">评价内容</div>
                <div class="color666 voteEvaluation">${entity.suggestion}</div>
            </li>
        </ul>
        <#-- 
        <div class="space15"></div>
        <ul class="yx-list">
            <li class="flex">
                <div class="flexItem w200">医院回复</div>
                <div class="flexItem color666 textRight">暂无回复</div>
            </li>
            <li>
                <div class="flexItem w200">医院回复</div>
                <div class="color666 voteEvaluation">谢谢对本院的支持以及信任，对每个病人负责是我们的职责，希望您能尽快康复起来。</div>
            </li>
        </ul>
        -->
        <div class="space15"></div>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>