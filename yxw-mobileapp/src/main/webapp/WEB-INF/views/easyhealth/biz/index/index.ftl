<!DOCTYPE html>
<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <link rel="stylesheet" href="${basePath}yxw.app/css/swiper-3.3.0.min.css">
    <script type="text/javascript" src="${basePath}yxw.app/js/common/yx-method.js?v=2"></script>
    <title>健康银包</title>
</head>
<style type="text/css">
    html,body{
        height: 100%;
    }
</style>
<body id="homeBody">
	<section class="slides">
	    <!-- Swiper -->
	    <div id="slides">
	        <div class="swiper-container">
	            <div class="swiper-wrapper">
					<#if carrieroperators2?exists>
						<#list carrieroperators2 as carrieroperator>
							<div class="swiper-slide">
								<#if carrieroperator.contentType == "url">
			                    	<img src="${carrieroperator.shufflingPic}" data-href="${carrieroperator.content}" width="100%" height="169">
									<#--
			                    	<img data-src="${carrieroperator.content}" class="swiper-lazy">
			                    	-->
			                    <#else>
			                    	<img src="${carrieroperator.shufflingPic}" width="100%" height="169">
			                    </#if>
			                </div>
						</#list>
					</#if>
	            </div>
	            <!-- Add Pagination -->
	             <div class="swiper-pagination swiper-pagination-red"></div>
	        </div>
	    </div>
	    <!-- Swiper -->
	</section>
	
	<div class="hb2">
		<section class="mainNav2">
			<a class="m_box one" href="javascript: void(0);" onclick="appIndex.${indexNavMap["1"].url}">
		        <span class="icon_box"><img src="${basePath}yxw.app/images/${indexNavMap["1"].iconCss}" width="70" height="70"> </span>
		         	${indexNavMap["1"].name} 
		        <span class="mate">${indexNavMap["1"].description}</span>
			</a>
			<div class="m_box two">
		        <a href="javascript: void(0);" onclick="appIndex.${indexNavMap["2"].url}" class="m_li">
		            <div class="m-img">
		            	<img src="${basePath}yxw.app/images/${indexNavMap["2"].iconCss}" width="50" height="50">
		            </div>
		            <span>${indexNavMap["2"].name} </span> 
		            <span class="mate">${indexNavMap["2"].description}</span>
		        </a>
		        <a href="javascript: void(0);" onclick="appIndex.${indexNavMap["3"].url}" class="m_li">
		            <div class="m-img">
		            	<img src="${basePath}yxw.app/images/${indexNavMap["3"].iconCss}" width="50" height="50">
		            </div>
		            <span>${indexNavMap["3"].name} </span> 
		            <span class="mate">${indexNavMap["3"].description}</span>
		        </a>
		    </div>
		</section>
	</div>
	
	<section class="mainNav">
		<#--
		<#if (indexList?exists && indexList?size > 0)>
			<#list indexList as optional>
				<#if optional_index % 4 == 0>
			    <ul class="flex navList">
			    </#if>
			        <li class="flexItem" onclick="appIndex.${optional.url}">
			            <div class="item">
			                <i class="iconfont ${optional.iconCss}"></i>
			                <p>${optional.name}</p>
			            </div>
			        </li>
			    <#if optional_index % 4 == 3 || optional_index + 1 == indexList?size >
			    </ul>
			    </#if>
			</#list>
		</#if>
		-->
		<ul class="flex navList">
		<#if (indexList?exists && indexList?size > 0)>
			<#list indexList as optional>
		        <li onclick="appIndex.${optional.url}">
		            <div class="item">
		                <i class="iconfont ${optional.iconCss}"></i>
		                <p>${optional.name}</p>
		            </div>
		        </li>
			</#list>
		</#if>
		</ul>
	</section>

	<#include "/easyhealth/common/menu_footer.ftl">
</body>

<script src="${basePath}yxw.app/js/biz/index/app.index.js?v=12" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/swiper-3.3.0.jquery.min.js?v=2"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/swipe.js?v=2"></script>
<#--
<script>
	$(function() {
		// 自动补全
		var obj = $('ul:last');
		if (obj.children().length < 4) {
			for (var i=0; i<4-obj.children().length; i++) {
				$(obj).append('<li class="flexItem" ><div class="item"> <i class="iconfont"></i><p></p> </div> </li>');
			}
		}
	});
</script>
-->
</html>