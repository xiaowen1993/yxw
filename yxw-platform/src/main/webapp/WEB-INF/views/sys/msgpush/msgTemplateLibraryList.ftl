<html>
<head>
<#include "/sys/common/common.ftl">
    <title>消息模版</title>
<script type="text/javascript" src="${basePath}js/sys/msgpush/sys.msgTemplateLibraryList.js"></script>
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
                	<#--
                    <button type="button" id="wechatLibrary" class="lib_select btn btn-ok w145 select" onclick="change(1)">微信模版库</button>
                    <button type="button" id="alipayLibrary" class="lib_select btn btn-ok w145" onclick="change(2)">支付宝模版库</button>
                    <button type="button" id="easyHealthLibrary" class="lib_select btn btn-ok w145" onclick="change(3)">健康易模版库</button>
                    -->
                    <#list platforms as platform>
                    	<button type="button" id="${platform.platformCode}Library" value="${platform.targetId}" class="lib_select btn btn-ok w145 <#if (platform_index == 0)> select</#if>" onclick="change(${platform.targetId})">${platform.platformName}</button>
                    </#list>
                </div>
                <div class="pull-right" id="search">
                	<form method="post" action="">
                	 	<input type="hidden" name="hospitalId" value="${hospitalId}"/>
	                	<input type="hidden" name="pageNum" />
	                	<input type="hidden" name="pageSize" />
	                	<input type="hidden" name="pages" />
	                	<input type="hidden" name="source"/>
                    	<input type="text" name="search" placeholder="请输入模版标题或者编码"/>
                    	<button class="tip-bottom" type="button" >
                        	<i class="icon-search icon-white" onclick="search()"></i>
                    	</button>
                	</form>
                </div>
            </div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content" style="padding: 0;">
                    <div class="row-fluid">
                        <a class="btn-add pull-right" href="javascript:void(0);" onclick="add()"><i class="caret"></i><i class="icons-plus"></i>添加</a>
                    </div>
                </div>
                <div class="space20"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th width="200">编号</th>
                                <th>编码</th>
                                <th>标题</th>
                                <th>一级行业</th>
                                <th>二级行业</th>
                                <th width="200">操作</th>
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