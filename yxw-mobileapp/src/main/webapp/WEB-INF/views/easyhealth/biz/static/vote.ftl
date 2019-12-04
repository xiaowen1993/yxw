<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>就诊评价</title>
</head>
<body>
<div id="body">
    <div id="vote-wrap">
        <div class="voteBox">
            <div class="page-title">请对本次就诊服务进行评价</div>
            <div class="grid_w">
                <div class="grid"  data-selected="1" onclick="chooseVote(this);">
                    <div class="box">
                        <div class="first"></div>
                        <div style="display: block;">
                        <span class="txt">赞</span>
                        </div>
                    </div>
                </div>
                <div class="grid" data-selected="2" onclick="chooseVote(this)">
                    <div class="box fore">
                        <div class="second"></div>
                        <div style="display: block;">
                        <span class="txt">踩</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="vote-w" style="display: none;">
                <div class="e-box">
                    <div class="list">
                        <span class="text">服务态度</span>
                        <div class="evel_box js-first">
                            <ul>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                                <li></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="e-box">
                <div class="list">
                    <span class="text">技术专业</span>
                    <div class="evel_box js-second">
                        <ul>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li>
                        </ul>
                    </div>
                </div>
            </div>
            </div>
            <div class="frm-box" style="display: none;">
                <textarea class="frm-textarea" rows="3"  placeholder="请输入您的意见和建议"></textarea>
            </div>
        </div>
        <div class="section btn-w js_know" style="display: none;">
            <div class="btn btn-ok btn-block" onclick="votePrise()"> 提交评价</div>
        </div>
        <div class="section btn-w js_tijiao"  style="display: none;">
            <div class="btn btn-ok btn-block" onclick="voteTips();"> 提交评价</div>
        </div>
    </div>
</div>
<div class="vote-mask"></div>
</body>
</html>

<script src="${basePath}yxw.app/js/biz/static/vote.js" type="text/javascript"></script>