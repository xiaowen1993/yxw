<html>
<head>
<#include "/sys/common/common.ftl">
<title>白名单设置</title>
</head>
<body>
<!--content str-->
<div id="content">
    <div id="content-top">
        <div class="container-fluid">
            <div class="box">
                <!--top-header-menu str -->
                <div id="user-nav" class="navbar">
                </div>
                <!--top-header-menu end -->
                <!--settings str-->
                <div id="settings">
                    <div class="set-msg dropdown">
                        <a class="dropdown-toggle" href="javascript:void(0);" data-toggle="dropdown" >
                            <span class="text">系统设置</span>
                            <i class="icons_settings icons-set"></i>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><span class="text">修改密码</span><i class="icons_settings icons-password"></i></a></li>
                            <li><a href="#"><span class="text">退出登录</span><i class="icons_settings icons-loginout"></i></a></li>
                        </ul>
                    </div>
                </div>
                <!--settings end-->
            </div>
        </div>
    </div>
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">白名单<span class="white-des">(开启白名单之后，前台功能只有白名单用户可以访问使用)</span></h3>
        </div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="pull-left">
                <ul class="tabs">
                    <li id="wechat"><a href="#" class="select">微信</a></li>
                    <li id="alipay"><a href="#">支付宝</a></li>
                </ul>
            </div>
        </div>
        <iframe src="${basePath}sys/whiteListSetings/toView?hospitalId=${entity.hospitalId}&appCode=${entity.appCode}&hospitalCode=${entity.hospitalCode}" 
            width="100%" align="center" height="400px" id="win" name="win" 
            onload="Javascript:SetWinHeight(this)" frameborder="0" scrolling="auto">
        </iframe>
     <div>
</div>
<!--content end-->
<!-- 版权声明 -->
</body>
</html>
<#include "./sys/common/footer.ftl">
<script type="text/javascript">
    $(document).ready( function () {
        $( "ul.tabs li" ).click( function () {
            $( "ul.tabs li" ).removeClass( "active" );  //Remove any "active" class
            $( "ul.tabs li" ).find('a').removeClass( "select" );  //Remove any "active" class
            $( this ).addClass( "active" );  //Add "active" class to selected tab
            $( this ).find('a').addClass("select");
            var appCode = $( this ).attr("id");
            var urlPath = "${basePath}sys/whiteListSetings/toView?hospitalId=${entity.hospitalId}&hospitalCode=${entity.hospitalCode}&appCode=" + appCode;
            $("#win").attr("src",urlPath);
            return false ;
        });
    });
    
    /*iframe 自适应*/
    function SetWinHeight(obj) { 
       var win=obj; 
       if (document.getElementById) { 
           if (win && !window.opera) { 
              if (win.contentDocument && win.contentDocument.body.offsetHeight) {
                 win.height = win.contentDocument.body.offsetHeight; 
              }else if(win.Document && win.Document.body.scrollHeight){ 
                 win.height = win.Document.body.scrollHeight; 
              }
           } 
       } 
    }
</script>