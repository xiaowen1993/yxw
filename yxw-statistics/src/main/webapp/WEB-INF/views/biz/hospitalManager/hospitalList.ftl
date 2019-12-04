<html>
<head>
<#include "./common/common.ftl">
    <script type="text/javascript" src="${basePath}stats/js/biz/hospitalManager/hospitalList.js"></script>
    <title>医院管理->医院列表</title>
    <style>
    	.label {
			display: inline;
		    padding: .6em;
		    margin: .6em;
		    font-size: 75%;
		    font-weight: bold;
		    line-height: 1;
		    color: #fff;
		    text-align: center;
		    white-space: nowrap;
		    vertical-align: baseline;
		    border-radius: .25em;
    	}
    	
    	.label-success {
    		background-color: #37b494;
    	}
    </style>
</head>
<body>

<div id="content">
	<div id="content-header">
		<div class="widget-title"><h3 class="title">医院平台关系</h3></div>
	</div>
    <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="space40"></div>
            <div class="row-fluid">
            	<div class="pull-left">
                	<button class="btn btn-save" type="button">新增</button>
                </div>
                <div class="pull-right" id="search">
                    <input type="text"  value="${search}" name="search" placeholder="请输入医院名称"/>
                    <button class="tip-bottom" type="submit" id="searchBtn">
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
                                <th>医院名称</th>
                                <th>平台名称</th>
                                <th width="100">管理</th>
                            </tr>
                            </thead>
                            <tbody>
                            	<#--
                                <#list commonEntity as hospital>
	                                <tr>
	                                	<td>${hospital_index + 1}</td>
	                                    <td>${hospital.hospitalName}</td>
	                                    <td>${hospital.platformName}</td>
	                                    <td>
	                                        <a class="red" href="javascript:void(0);" onclick="hospitalList.viewHospitalDetail('${hospital.id}');">查看</a>
	                                        <a class="red" href="javascript:void(0);" onclick="hospitalList.deleteHospital('${hospital.id}');">删除</a>
	                                    </td>
	                                </tr>
							   </#list>
							   -->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<form id="voForm" method="post">
	<input type="hidden" name="hospitalId" id="hospitalId" value="" />
	<input type="hidden" name="hospitalCode" id="hospitalCode" value="" />
	<input type="hidden" name="hospitalName" id="hospitalName" value="" />
	<input type="hidden" name="areaCode" id="areaCode" value="" />
	<input type="hidden" name="areaName" id="areaName" value="" />
	<input type="hidden" name="state" id="state" value="" />
</form>

<!--content end-->
<!-- 版权声明 -->
<!--
<#include "./common/footer.ftl">
-->
</body>
</html>
