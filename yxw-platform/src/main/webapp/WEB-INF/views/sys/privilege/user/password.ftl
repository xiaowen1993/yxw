<html>
<head>
<#include "./sys/common/common.ftl">
<script type="text/javascript" src="${basePath}js/sys/privilege/user/sys.user.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">修改密码</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <form>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <div class="control-group"><h4 class="title">原密码</h4><div class="controls"><input class="span4" id="oldpwd" type="password" value=""/></div></div>
                        <div class="control-group"><h4 class="title">新密码</h4><div class="controls"><input class="span4" id="newpwd" type="password" value=""/></div></div>
                        <div class="control-group"><h4 class="title">确认新密码</h4><div class="controls"><input class="span4" id="renewpwd"type="password" value=""/></div></div>
                        <div class="space20"></div>
                </div>
               </div>
            </div>
                <div class="space20"></div>
                <div class="controls">
                    <button class="btn btn-save" type="button" onclick="$user.modifyPwd();" >保存</button>
                </div>
            </form>
        </div>

    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>

