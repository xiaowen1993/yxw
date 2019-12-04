<!DOCTYPE html>
<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>预约记录</title>
</head>
<style type="text/css">
    html,body{
        height: 100%;
    }
</style>
<body>
<div id="body">
	<div class="space15"></div>
	<ul class="yx-list"></ul>
    <form method="post" action="">
		<input type="hidden" name="userId" />
		<input type="hidden" name="appCode" />
		<input type="hidden" name="pageNum" />
		<input type="hidden" name="pageSize" />
		<input type="hidden" name="pages" />
	</form>
</div>
</body>

<script src="${basePath}yxw.app/js/biz/nih/list.js" type="text/javascript"></script>

</html>