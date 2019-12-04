<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>智能搜索</title>
</head>
<style type="text/css">
    html,body{ height: 100%;}
</style>
<body id="body">
<div id="search-wrap">
    <div id="myCenter">
        <div class="space15"></div>
        <ul class="yx-list listRow">
            <li class="arrow" onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=hospital',1);"><div class="u-img"><i class="iconfont i-orange">&#xe63e;</i></div>搜医院</li>
            <li class="arrow" onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=dept',true);"><div class="u-img"><i class="iconfont i-blue">&#xe63d;</i></div>搜科室</li>
            <li class="arrow" onclick="go('${basePath}easyhealth/search/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&searchType=doctor',true);"><div class="u-img"><i class="iconfont i-yellow">&#xe65c;</i></div>搜医生</li>
        </ul>
    </div>
</div>
<#include "/easyhealth/common/menu_footer.ftl">
</body>
</html>
