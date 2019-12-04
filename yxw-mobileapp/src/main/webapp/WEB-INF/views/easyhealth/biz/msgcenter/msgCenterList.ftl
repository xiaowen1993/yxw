<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width ,maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <#include "/easyhealth/common/common.ftl">
    <title>消息中心</title>
    <script type="text/javascript" src="${basePath}yxw.app/js/biz/msgcenter/msgCenterList.js"></script>
</head>
<body>
<div id="body">
	<form method="post" action="">
		<input type="hidden" name="userId" value="${sessionUser.id}"/>
		<input type="hidden" name="pageNum" />
		<input type="hidden" name="pageSize" />
		<input type="hidden" name="pages" />
	</form>
    <section class="mod-notice">
    	<artice class="noticeList">
    	<div class="scrollWrap" style="overflow: auto;">
    			<div class="scrollTouch" style="margin-bottom:62px">
    				<p class="scroll-tip">下拉刷新...</p>
        			<ul class="notice-list" id='refreshList'></ul>
         		</div>
        </div>
        </artice>
        <!-- <article class="noticeBtn">
            <span class="btn1">点击加载更多</span>
        </article> -->
        
    </section>
    <#include "/easyhealth/common/menu_footer.ftl">
</div>
<div id="copyright"></div>
<script>
    $('.notice-list').on('click','li',function(){
    	$(this).removeClass("unread");
        var href = $(this).attr('href');
        location.href = href;
    })
</script>
</body>
</html>