<html>
<head>
    <#include "/easyhealth/common/common.ftl">
    <title>扫码报到</title>
</head>
<style>
    html,body,#body{ width: 100%;  height: 100%; background-color: #fff; padding: 0; margin: 0;}
</style>
<body>
<div id="body">
    <div class="scandownload">
        <div class="scanTic1"></div>
        <div class="scanTic2" >
            <div class="btn-scanDownload" onclick="go('http://a.app.qq.com/o/simple.jsp?pkgname=com.yx129.jiankangyi')"></div>
        </div>
        <div class="scanTic3"></div>
    </div>
</div>
<#include "/easyhealth/common/footer.ftl">
</body>
</html>