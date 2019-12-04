<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>接种门诊</title>
</head>
<body>

<div id="body">
    <div id="myCenter">
        <div class="page-title">选择地区</div>
        <ul class="yx-list listRow">
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>福田区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>罗湖区</span></li>
						<!-- <li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>南山区</span></li> -->
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>盐田区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>宝安区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>龙岗区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>光明新区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>坪山新区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>龙华新区</span></li>
						<li class="arrow"><div class="u-img"><i class="iconfont i-green">&#xe63e;</i></div><span>大鹏新区</span></li>
        </ul>
    </div>
    
    <form id="regionForm" method="post" action="clinic" accept-charset="utf-8">
    	<input type="hidden" id="regionName" name="regionName" value="">
    </form>
</div>

<#include "/easyhealth/common/footer.ftl">

<script type="text/javascript">
	$(function() {
		$(".yx-list li").click(function() {
			var regionName = $(this).find("span").text();
			$("#regionName").val(regionName);
			$("#regionForm").submit();
		});
	});
	
	function doGoBack() {
		go('index');
	}
</script>
</body>
</html>
