<!DOCTYPE html>
<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <script type="text/javascript" src="${basePath}yxw.app/js/common/yx-method.js"></script>
    <link rel="stylesheet" href="${basePath}yxw.app/css/style-index.css?v=20180322" />
    <link rel="stylesheet" href="${basePath}yxw.app/css/swiper.min.css">
    <title>健康理赔</title>
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
							<#if carrieroperator.status == "0">
							<div class="swiper-slide">
								<#if carrieroperator.contentType == "url">
			                    	<img src="${carrieroperator.shufflingPic}" data-href="${carrieroperator.content}" onclick="location.href='${carrieroperator.content}'" width="100%">
			                    <#else>
			                    	<img src="${carrieroperator.shufflingPic}" width="100%">
			                    </#if>
			                </div>
			                </#if>
						</#list>
					</#if>
	            </div>
                <div class="swiper-pagination" style="text-align: right"></div>
            </div>
            <div class="enter">
                <a href="javascript:go('${basePath}easyhealth/listindex/registerIndex', true)" class="a_gh">
                    <i class="icon guohao"></i>
                    <div class="tit">挂号</div>
                    <div class="des">足不出户挂号看病</div>
                </a>
                <div class="enter_right">
                	
                		<div class="enter_right_item m-jf">
                        <a href="javascript:go('${basePath}app/queue/index?openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode, true)">
                            <div class="flex1">
                                <div>
                                    <div class="tit">排队候诊</div>
                                    <div class="des">在线排队 省时省力</div>

                                </div>

                                <div class="icon-right2">
                                    <i class="icon my"></i>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="enter_right_item">
                        <a href="javascript:go('${basePath}app/report/index?openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode, true)">
                            <div class="flex1">
                                <div>
                                    <div class="tit">查报告</div>
                                    <div class="des">在线查询 方便快捷</div>

                                </div>

                                <div class="icon-right2">
                                    <i class="icon i-msg"></i>
                                </div>
                            </div>
                        </a>
                    </div>
                    
                </div>
                
            </div>

            <#-- <a class="msg" href="javascript:go('${basePath}app/msgcenter/msgCenterListView?openId=' + base.openId + '&appCode=' + base.appCode + '&menuCode=2', true);">
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
            </a> -->
            
            <a class="msg" href="javascript:go('${basePath}app/clinic/pay/index?openId=' + base.openId + '&appCode=' + base.appCode + '&areaCode=' + base.areaCode, true)">
                <div>
                    <i class="icon jf"></i>
                </div>
                <div class="flex1">
                    <div class="tit">缴费</div>
                    <div class="des">操作便捷 让缴费更简单</div>
                </div>

                <div class="r">
                    <i class="icon-right"></i>
                </div>
            </a>


            <a class="msg" href="javascript:go('${basePath}easyhealth/toHospitalInfo', true)">
                <div>
                    <i class="icon icon-zn"></i>
                </div>
                <div class="flex1">
                    <div class="tit">就医指南</div>
                    <div class="des">门诊指南 看病须知 就诊指南</div>
                </div>

                <div class="r">
                    <i class="icon-right"></i>
                </div>
            </a>
            
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