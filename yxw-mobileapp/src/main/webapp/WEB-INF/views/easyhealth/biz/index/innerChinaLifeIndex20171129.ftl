<!DOCTYPE html>
<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <link rel="stylesheet" href="${basePath}yxw.app/css/swiper-3.3.0.min.css">
    <script type="text/javascript" src="${basePath}yxw.app/js/common/yx-method.js"></script>
    <title>健康理赔</title>
</head>
<style type="text/css">
    html,body{
        height: 100%;
    }
</style>
<body id="homeBody">
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
	
	
	<section>
        <a href="#" style="background: #fff;margin: 5px 0;padding: 10px 1em;display: block;color: #666">
        	<i class="lipei icon-lingdang" style="color: #f00"></i> 您的理赔申请已受理，请查看详情……
        </a>
    </section>

	<#include "/easyhealth/common/innerChinaLifeMenuFooter.ftl">
</body>

<script src="${basePath}yxw.app/js/biz/index/app.index.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/swiper-3.3.0.jquery.min.js"></script>
<script type="text/javascript" src="${basePath}yxw.app/js/common/swipe.js"></script>
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