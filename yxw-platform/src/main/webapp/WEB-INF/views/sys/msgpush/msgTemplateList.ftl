<html>
<head>
<#include "/sys/common/common.ftl">
    <title>模版列表</title>
    <script type="text/javascript" src="${basePath}js/sys/msgpush/sys.msgTemplateList.js"></script>
</head>
<body>
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">消息模版</h3></div>
    </div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
                <div class="pull-left">
                    <button type="button" class="btn btn-ok w140" onclick="window.location.href='${basePath}msgpush/msgTemplate/editTemp?hospitalId=${hospitalId}'">新增模板消息</button>
                	<#--<button type="button" class="btn btn-ok w140" onclick="window.location.href='${basePath}msgpush/msgCustomer/editCustomer?hospitalId=${hospitalId}'">新增客服消息</button>-->
                </div>
                <div class="pull-right" id="search">
                	<form method="post">
                    <input type="text" name="search" placeholder="请输入模版标题、模版编码查询模版"/>
                    <input type="hidden" name="hospitalId" value="${hospitalId}"/>
                    <input type="hidden" name="pageNum" />
	                <input type="hidden" name="pageSize" />
	                <input type="hidden" name="pages" />
                    <button class="tip-bottom" type="button" >
                        <i class="icon-search icon-white" onclick="search();"></i>
                    </button>
                    </form>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th width="50">序号</th>
                                <th width="80">模版编码</th>
                                <th>模版ID</th>
                                <th>模版标题</th>
                                <th>类型</th>
                                <th>服务平台</th>
                                <th width="70">管理</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

        <div class="pagination pagination-centered"></div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
</body>
</html>
