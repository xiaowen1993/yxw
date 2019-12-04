<!DOCTYPE html>
<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>NIH肺癌分型治疗</title>
</head>
<style type="text/css">
    html,body{
        height: 100%;
    }
</style>
<body>
<div id="body">
    <div class="familyInfo">
        <div class="box-tips icontips"><i class="iconfont">&#xe60d;</i>广告展示页面，需要预约登记功能。登记内容：手机号、称呼、病情简介。登记成功后，有弹窗提示。提示内容：“您已登记成功，护士将在2个工作日内联系您”。</div>
        <div class="space15"></div>
    	<ul class="yx-list">
            <li class="flex">
                <div class="">联系人</div>
                <div class="flexItem color666 textRight"><input id="name" type="text" class="yx-input" placeholder="请输入"/></div>
            </li>
            <li class="flex">
                <div class="">联系人号码</div>
                <div class="flexItem color666 textRight"><input id="mobileNo" type="text" class="yx-input" placeholder="请输入" maxlength="11"/></div>
            </li>
            <li class="flex">
                <div class="">病情描述</div>
            </li>
            <li class="flex">
            	<div class="flexItem color666 textRight">
            		<textarea class="yx-input" placeholder="请输入" id="desc" rows="4" style="text-align: left;"></textarea>
            	</div>
            </li>
        </ul>
        <div class="btn-w">
            <div type="button" class="btn btn-ok btn-block">马上预约</div>
        </div>
    </div>
</div>
</body>

<script src="${basePath}yxw.app/js/biz/nih/nih.js" type="text/javascript"></script>

</html>