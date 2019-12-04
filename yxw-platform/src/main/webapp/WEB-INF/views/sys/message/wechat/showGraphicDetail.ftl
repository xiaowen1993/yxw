<html>
<head>
<#include "./sys/common/common.ftl">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width ,maximum-scale=1">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="x-rim-auto-match" content="none">
    <title>正文详情</title>
    <link rel="stylesheet" href="${basePath}css/zsyx.css?v=334"/>
    <script src="${basePath}js/fastclick.js" type="text/javascript"></script>
    <script src="${basePath}js/common.js" type="text/javascript"></script>
</head>
<body style="background-color: #fff">

<div id="body">
    <div class="article">
        <div class="article-title">${mixedMeterial.title}</div>
        <div class="article-mate">${mixedMeterial.et?string('yyyy-MM-dd HH:mm:ss')}   ${mixedMeterial.author}</div>
        <div class="article-des"><#if mixedMeterial.description?if_exists >摘要：${mixedMeterial.description}</#if></div>
        <div class="article-msg">
           <p><img src="${mixedMeterial.coverPicPath}"  /></p>
            <p> ${mixedMeterial.content}</p>
        </div>

    </div>

</div>

<div id="copyright"> 医享网出品 </div>
</body>
</html>