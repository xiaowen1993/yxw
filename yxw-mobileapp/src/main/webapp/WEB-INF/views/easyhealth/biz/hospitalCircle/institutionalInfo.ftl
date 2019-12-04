<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>机构信息</title>
</head>
<body>
<div id="body">
    <div class="space15"></div>
    <ul class="yx-list">
        <li class="flex-wrap row-flex">
            <div class="title">机构</div>
            <div class="flexWidth1 textRight color666">${hospName}</div>
        </li>
        <li class="flex-wrap row-flex">
            <div class="title">电话</div>
            <div class="flexWidth1 textRight color666"><#if telphone!=''>${telphone}<#else>——</#if></div>
        </li>
        <li class="flex-wrap row-flex">
            <div class="title">地址</div>
            <div class="flexWidth1 textRight color666"><#if address!=''>${address}<#else>——</#if></div>
        </li>
    </ul>

    <div class="box-list fff accordion js-accordion">
        <div class="acc-li">
            <div class="acc-header js-acc-header">介绍</div>
            <ul class="acc-content">
                <li class="item">
                    林书豪首度代表黄蜂出战，以“第六人”的身份出场，得了17分2次助攻。肯巴-沃克19分，艾尔-杰弗森17分5个篮板，马文-威廉姆斯10分10个篮板。尼古拉斯-巴图姆12投仅3中，得9分6个篮板。
                    <br/>
                    两队都是新赛季揭幕战。热火在少了勒布朗-詹姆斯之后，未能进入季后赛，这是他们12年来第二次。伤病成为他们最大的困扰，韦德上赛季歇了20场，而波什因病缺席了赛季的一半。
                </li>

                <li class="item noDataText">
                    暂时没有介绍
                </li>
            </ul>
        </div>
    </div>
    <div class="btn-w">
        <button class="btn btn-ok btn-block">地图指引</button>
    </div>
    <div class="btn-w">
        <button class="btn btn-ok btn-block">去挂号</button>
    </div>
    <div class="space15"></div>
</div>
<div id="copyright"> 医享网出品 </div>
</body>
</html>
<script>
    function showCentent(){
        var obj  = $('#js-threeKmInfo_centent');

        if(obj.hasClass('show')){
            obj.removeClass('show')
            $('#js-more').html('展开详细');
        }else{
            obj.addClass('show')
            $('#js-more').html('收缩详细');
        }
    }

</script>
