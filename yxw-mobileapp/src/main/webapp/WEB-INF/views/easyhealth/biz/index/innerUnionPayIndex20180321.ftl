<!DOCTYPE html>
<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}yxw.app/js/common/yx-method.js"></script>
    <link rel="stylesheet" href="${basePath}yxw.app/css/style-index.css" />
    <link rel="stylesheet" href="${basePath}yxw.app/css/swiper.min.css">
    <title>健康银包</title>
    <style> 
        /* 记得一定要复制 */
            body {
                background: #f3f6f9
            }
    </style>
</head>

<body ontouchend="">
    <div id="body" v-cloak="">
        <div id="home">
            <!-- Slider main container -->
            <div class="swiper-container">
            	<div class="swiper-wrapper">
					<#if carrieroperators2?exists>
						<#list carrieroperators2 as carrieroperator>
							<div class="swiper-slide">
								<#if carrieroperator.contentType == "url">
			                    	<img src="${carrieroperator.shufflingPic}" data-href="${carrieroperator.content}" width="100%" height="169">
			                    <#else>
			                    	<img src="${carrieroperator.shufflingPic}" width="100%" height="169">
			                    </#if>
			                </div>
						</#list>
					</#if>
	            </div>
                <div class="swiper-pagination" style="text-align: right"></div>
            </div>
            <div class="enter">
                <a href="javascript:go('${basePath}easyhealth/listindex/registerIndex', true)">
                    <i class="icon guohao"></i>
                    <div class="tit">挂号</div>
                    <div class="des">足不出户挂号看病</div>
                </a>
                <a href="javascript:go('${basePath}static/hzsh.html', true)">
                        <i class="icon hezuo"></i>
                        <div class="tit">合作商户</div>
                        <div class="des">天天享折扣 优惠不停</div>
                    </a>
            </div>

            <a class="msg" href="javascript:go('${basePath}app/msgcenter/msgCenterListView?openId=' + base.openId + '&appCode=' + base.appCode + '&menuCode=2', true);">
                <div class="thm"><i class="icon i-msg"></i></div>
                <ol>
                    <li>
                        <span class="ti">缴费通知：你有一笔订单需要支付</span>
                        <span class="des">5分钟前</span>
                    </li>
                    <li>
                        <span class="ti">挂号成功通知：你成功预约XXX服务</span>
                        <span class="des">12分钟前</span>
                    </li>
                </ol>
                <div class="new-msg"></div>
                <div class="r"><i class="icon-right"></i></div>
            </a>
            <div class="pt"><i class="iconfont icon-huangguan"></i>扫码支付 </div>

            <div class="enter t">
                <a href="javascript:go('${basePath}static/zwkt.html', true)">
                    <i class="iconfont icon-yibaojieshao"></i>
                    <div>医保支付</div>
                </a>
                <a href="javascript:go('${basePath}static/zwkt.html', true)">
                    <i class="iconfont icon-hui"></i>
                    <i class="iconfont icon-erweima1"></i>
                    <div>银联卡支付</div>
                </a>
            </div>

            <#include "/easyhealth/common/menu_footer.ftl">
        </div>

    </div>

    <!-- body end -->
    <script src="${basePath}yxw.app/js/common/require.min.js"></script>
    <script src="${basePath}yxw.app/js/common/config.js"></script>
    <script>
        require(['vue', 'jq', 'swiper'], function (Vue, jQ, Swiper) {


            $vm = new Vue({
                el: '#body',
                data: {
                    msg: 'Vue'
                },
                methods: {}
            })
            
            //banner
            var mySwiper = new Swiper('.swiper-container', {
                // 如果需要分页器
                pagination: {
                    el: '.swiper-pagination',
                },


            })

			menuFoot.bindEvent();

        })
    </script>

</body>

</html>