<meta charset="UTF-8">
<meta content="width=device-width ,maximum-scale=1" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="none" http-equiv="x-rim-auto-match">
<link rel="stylesheet" href="${basePath}yxw.app/css/style.css?v=1.0<#if appColor?exists>&yxColor=${appColor}</#if>" id="yxColor"/>
<script type="text/javascript" src="${basePath}yxw.app/js/common/jquery.min1.9.js"></script>
<script src="${basePath}yxw.app/js/common/fastclick.js" type="text/javascript"></script>
<script src="${basePath}yxw.app/js/common/common.js?v=1.0" type="text/javascript"></script>
<script type="text/javascript">
    var appPath = '${basePath}';
    var curuser = {
    	openId: '${sessionUser.id}'
    }
    
    var base = {
    	appPath: '${basePath}',
    	openId: '${sessionUser.id}',
    	appCode: '${appCode}',
    	areaCode: '${areaCode}'
    }
</script>
<#include "/common/unionpay.ftl">
