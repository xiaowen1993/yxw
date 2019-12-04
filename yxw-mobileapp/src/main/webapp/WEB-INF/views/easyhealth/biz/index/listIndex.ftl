<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>分级诊疗</title>
</head>
<body>
<div id="body">
    <div id="myCenter">
    	<div class="space15"></div>
        <ul class="yx-list listRow" style=" background-color: #fff; ">
            <li class="arrow" onclick="go('${basePath}easyhealth/communitycenter/communityHealth/getAdministrativeRegion?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',1)"><div class="u-img"><i class="iconfont i-orange">&#xe654;</i></div> 社康中心 </li>
            <li class="arrow" onclick="go('${basePath}easyhealth/communitycenter/communityHealth/getCommunityOrganizaWeekDetail?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',1)"><div class="u-img"><i class="iconfont i-green">&#xe648;</i></div> 巡诊专家 </li>
            <li class="arrow" onclick="go('${basePath}easyhealth/listindex/registerIndex?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}',1)"><div class="u-img"><i class="iconfont i-blue">&#xe647;</i></div> 我要挂号</li>
            <#--
            <li class="arrow" onclick="go('${basePath}easyhealth/building',1)"><div class="u-img"><i class="iconfont i-yellow">&#xe646;</i></div> 双向转诊 </li>
            <li class="arrow" onclick="go('http://minisite.yx129.com/InHospital/Information/index?appid=wx95ac8639496452b8&app_id=10041',1)"><div class="u-img"><i class="iconfont i-green">&#xe645;</i></div> 健康宣教 </li>
            <li class="arrow" onclick="go('${basePath}easyhealth/building',1)"><div class="u-img"><i class="iconfont i-orange">&#xe643;</i></div> 复诊提醒 </li>
            <li class="arrow" onclick="go('${basePath}easyhealth/building',1);"><div class="u-img"><i class="iconfont i-blue">&#xe644;</i></div> 康复管理 </li>
            -->
        </ul>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>
<script src="${basePath}yxw.app/js/biz/index/eh.index.js" type="text/javascript"></script>
