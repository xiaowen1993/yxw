<html>
<#include "/easyhealth/common/common.ftl">
<head>
    <title>我要挂号</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
        <div class="space15"></div>
            <ul class="yx-list listRow">
                <li class="arrow" class="serverGif" onclick="go('${basePath}easyhealth/register/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=2',true);"><div class="u-img"><i class="iconfont i-blue">&#xe669;</i></div>当班挂号</li>
                <li class="arrow" class="serverGif" onclick="go('${basePath}easyhealth/register/hospitalList?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}&regType=1',true);""><div class="u-img"><i class="iconfont i-yellow">&#xe668;</i></div>预约挂号</li>
            </ul>
        <div class="space15"></div>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
