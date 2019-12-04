<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    Double alpha = 0.3;
%>

<!doctype html>
<html><head><meta charset="utf-8">
    <title>数据中心</title>
    <meta name="description" content="数据中心（各个业务数据的统计图表）">
    <link rel="stylesheet" type="text/css" href="/css/styles.css?v=1.0">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, minimal-ui">

</head>
<body>

<div class="content-main">
    <section class="features container">
        <article>
            <h3>累计关注数</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="subscribe-cumulate-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>图表中的数据为截止到对应时间月份累计数； 关注数是指公司所运营的所有微信公众号和支付宝服务窗的关注用户数， 我们可通过系统随时将业务动态或者功能介绍等内容推送到用户的手机上。</p>
        </article>
        <article>
            <h3>每月增长关注数</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="subscribe-month-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>按照每月统计用户关注数。</p>
            <p id="subscribe-month-line-text"></p>
        </article>
        <article>
            <h3>累计绑卡用户数</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="card-cumulate-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>图表中的数据为截止到对应时间月份累计绑卡用户量； 用户首次使用挂号、 缴费、 查询报告等具体业务， 首先必须进行绑卡动作， 将医院的就诊卡录入到系统中， 并验证通过才可以使用其他业务。</p>
        </article>
        <article>
            <h3>每月增长绑卡用户数</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="card-month-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>按照每月统计绑卡用户数。</p>
            <p id="card-month-line-text"></p>
        </article>
        <article>
            <h3>累计交易金额</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="amount-cumulate-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>用户在系统中使用挂号+诊间缴费所支付的金额总和， 说明随着用户的增加和用户习惯的形成， 挂号+诊间缴费逐步被用户所接受。</p>
        </article>
        <article>
            <h3>累计订单量</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="count-cumulate-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>图表中的数据为截止到对应时间月份累计数； 随着移动支付的渗透， 移动支付比例的逐步提高， 支付数量会得到大幅度提升。</p>
        </article>
        <article>
            <h3>每月交易金额</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="amount-month-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>按照每月统计交易金额。</p>
            <p id="amount-month-line-text"></p>
        </article>
        <article>
            <h3>每月订单量</h3>
            <div id="js-carousel" class="canvas-holder hover-highlight carousel position-1"><canvas width="295" height="184" id="count-month-line" style="width: 295px; height: 184px;"></canvas></div>
            <p>按照每月统计订单量。</p>
            <p id="count-month-line-text"></p>
        </article>
        <article>
            <h3>上月使用人群男女比例</h3>
            <div class="labeled-chart-container">
                <div class="canvas-holder">
                    <canvas id="sexProportionDoughnut" width="250" height="250" ></canvas>
                </div>
            </div>
            <p>患者男女比例。</p>
        </article>
    </section>
</div>
<footer>
    
    <div class="owner-notice">
        城市宠儿 
    </div>
</footer>
<script src="js/jQuery/jquery.min.js"></script>
<script src="/js/charts/Chart.min.2.7.3.js"></script>
<!-- <script src="/js/charts/Chart.min.1.0.2.js"></script> -->
<script src="/js/yxw-util.js"></script>
<script src="/js/jsonData.js"></script>
<script src="/js/portalForDatas.js"></script>


</body></html>