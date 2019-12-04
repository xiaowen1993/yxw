<html>
<head>
<#include "./common/common.ftl">
    <script type="text/javascript" src="${basePath}stats/js/common/hospitalList.js"></script>
    <title>数据统计->医院列表</title>
</head>
<body>
	<div id="content">
	    <div id="content-header">
	        <div class="widget-title"><h3 class="title">${commonVo.bizTitle}<span style="font-size:12px;"> (只有显示使用的医院)</span></h3></div>
	    </div>
	    <div class="container-fluid">
	        <div class="space10"></div>
	        <div class="row-fluid">
	            <div class="space40"></div>
	            <div class="row-fluid">
	                <#-- <div class="pull-left">
	                	<button class="btn btn-save" type="button" onclick="ruleJS.toSysConfig()">系统配置</button>
	                </div> -->
	                <div class="pull-right" id="search">
	                		<form method="post" action="#" accept-charset="utf-8">
	                			<input type="hidden" name="pageNum" value="${hospitals.pageNum}" />
	                            <input type="hidden" name="pageSize" value="${hospitals.pageSize}" />
	                            <input type="hidden" name="pages" value="${hospitals.pages}" />
	                            <input type="hidden" name="total" value="${hospitals.total}" />
	                            <input type="text"  value="${search}" name="search" placeholder="请输入医院名称"/>
	                		</form>
	                    <button class="tip-bottom" type="submit">
	                        <i class="icon-search icon-white"></i>
	                    </button>
	                </div>
	            </div>
	            <div class="widget-box">
	                <div class="space10"></div>
	                <div class="widget-content form-check">
	                    <div class="row-fluid">
	                        <table class="table table-bordered table-textCenter table-striped table-hover">
	                            <thead>
	                            <tr>
	                                <th width="50"></th>
	                                <th>医院全称</th>
	                                <th width="100">操作</th>
	                            </tr>
	                            </thead>
	                            <tbody>
	                                <#list commonEntity as hospital>
		                                <tr>
		                                	<td><span class="badge badge-info">${hospital_index + 1}</span></td>
		                                    <td>${hospital.name}</td>
		                                    <td>
		                                        <a class="red" href="javascript:void(0);" onclick="hospitalList.showDetail('${hospital.name}', '${hospital.code}', ${commonVo.bizType})">查看</a>
		                                    </td>
		                                </tr>
								   </#list>
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</div>

<form id="voForm" name="voForm" method="post" action="">
	<input type="hidden" id="hospitalCode" name="hospitalCode" value="" />
	<input type="hidden" id="hospitalName" name="hospitalName" value="" />
	<input type="hidden" id="bizType" name="bizType" value="${commonVo.bizType}" />
</form>

<!--content end-->
<!-- 版权声明 -->
<!--
<#include "./common/footer.ftl">
-->
</body>
</html>
