<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>健康管理</title>
</head>
<style type="text/css">
    html,body{ height: 100%;}
</style>
<body style="background-color: #fff;">
	<ul id="health" class="flex-wrap col-flex" style="height: 100%">
        <li class="flex-wrap row-flex flexWidth3">
            <div class="flexWidth2">
                <div class="item" onclick="go('${basePath}easyhealth/healthlist/regrecord/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);">
                    <div class="icon"><i class="iconfont greed">&#xe64c;</i></div>
                    <div class="label">就诊记录</div>
                </div>
            </div>
            <div class="flexWidth2">
                <div class="item" onclick="go('${basePath}easyhealth/healthlist/payment/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);">
                    <div class="icon"><i class="iconfont blue">&#xe64a;</i></div>
                    <div class="label">处方记录</div>
                </div>
            </div>
        </li>
        <li class="flex-wrap row-flex flexWidth3">
            <div class="flexWidth2">
                <div class="item" onclick="go('${basePath}easyhealth/healthlist/regDoctor/index?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);">
                    <div class="icon"><i class="iconfont yellow">&#xe648;</i></div>
                    <div class="label">医生记录</div>
                </div>
            </div>
            <div class="flexWidth2">
                <div class="item" onclick="go('${basePath}easyhealth/healthlist/reports/ehReportIndex?openId=${sessionUser.id}&appCode=${appCode}&areaCode=${areaCode}', true);">
                    <div class="icon"><i class="iconfont greed">&#xe649;</i></div>
                    <div class="label">报告记录</div>
                </div>
            </div>
        </li>
        <li class="flex-wrap row-flex flexWidth3">
            <div class="flexWidth2">
                <div class="item touch" onclick="go(gotoPHPModule('${sessionUser.id}', 'easyHealth', 'easyhealth/toPHP/getHealthRecordParams'), true);">
                    <div class="icon"><i class="iconfont fen">&#xe64b;</i></div>
                    <div class="label">健康监测</div>
                </div>
            </div>
            <#--
            <div class="flexWidth2">
                <div class="item" onclick="go('${basePath}easyhealth/building', true);">
                    <div class="icon"><i class="iconfont orange">&#xe64e;</i></div>
                    <div class="label">用药记录</div>
                </div>
            </div>
            -->
            <div class="flexWidth2">
                <div class="item">
                    <div class="icon"><img class="picThumb des" src="${basePath}yxw.app/images/icon9.png" width="50" height="50"/></div>
                </div>
            </div>
        </li>
    </ul>
</body>
<script src="${basePath}yxw.app/js/biz/tophp.common.js" type="text/javascript"></script>
<script>
	function doGoBack()
	{
		windowClose();
	}
</script>
</html>