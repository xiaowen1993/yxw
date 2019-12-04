<html>
<head>
    <#include "/sys/common/common.ftl">
    <title>版本配色</title>
</head>
<body>
<div id="content">
	<#include "./sys/common/hospital_setting.ftl">
	
    <div id="content-header">
        <div class="widget-title"><h3 class="title">版本配色</h3></div>
    </div>

    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th width="200" >版本名称</th>
                                <th>版本代码</th>
                                <th >颜色值</th>
                                <th >配色样例</th>
                                <th >管理</th>
                            </tr>
                            </thead>
                            <#if (colors?size>0)>
	                            <tbody>
	                            	<#list colors as appColor>
		                                <tr>
		                                	<td>${appColor_index + 1}</td>
		                                    <td>${appColor.appName}</td>
		                                    <td>${appColor.appCode}</td>
		                                    <td>${appColor.color}</td>
		                                    <td>
		                                    	<div style="background-color:${appColor.color}; height:30px; width: 70%; margin: auto"></div>
		                                    </td>
		                                    <td>
		                                        <a href="${basePath}sys/app/color/edit?id=${appColor.id}">编辑</a>
		                                    </td>
		                                </tr>
	                                </#list>
	                            </tbody>
	                        <#else>
									<tr><td colspan="10">暂无版本配色数据</td></tr>
                            </#if>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">
<script>
    
</script>
<!--
<script src="${basePath}js/sys/app/location/sys.app.location.js"></script>
-->

</body>
</html>