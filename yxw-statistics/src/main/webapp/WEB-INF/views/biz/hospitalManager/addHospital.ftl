<html>
<head>
	<#include "./common/common.ftl">
	<script type="text/javascript" src="${basePath}stats/js/biz/hospitalManager/addHospital.js"></script>
	<script type="text/javascript" src="${basePath}stats/js/common/jquery.cityselect.js"></script>
	<link rel="stylesheet" href="${basePath}css/style.css"/>
    <title>新增医院</title>
    <style>
    	td div {
    		height: 33px;
    	}
    </style>
</head>
<body>
<div id="content">
	<div class="container-fluid">
		<div class="inner">
			<div class="row-fluid">
				<div class="views">
            <div class="stepBox">
                <ul class="steps">
                    <li class="step1 active">
                        <a href="#">
                            <span class="step">医院</span>
                            <span class="line"></span>
                        </a>
                    </li>
                    <li class="step2">
                        <a href="#">
                            <span class="step">分院</span>
                            <span class="line"></span>
                        </a>
                    </li>
                    <li class="step3">
                        <a href="#">
                            <span class="step">平台</span>
                            <span class="line"></span>
                        </a>
                     </li>
                </ul>
            </div>
            <div class="divTable">
                <table class="optTable">
                    <tbody>
                        <tr>
                            <td width="20%" class="name"><div>医院名称</div></td>
                            <td width="40%"><input id="inputName" class="ui-form-input" type="text" value="${commonVo.hospitalName}" name="" placeholder="请输入医院名称"></td>
                            <td><div id="checkName"><p class="text-info"></p></div></td>
                        </tr>
                        <tr>
                            <td width="20%" class="name"><div>医院code</div></td>
                            <td width="40%"><input id="inputCode" class="ui-form-input" type="text" value="${commonVo.hospitalCode}" name="" placeholder="请输入医院code"></td>
                            <td><div id="checkCode"><p class="text-info"></p></div></td>
                        </tr>
                        <tr>
                            <td width="20%" class="name"><div>医院当前状态</div></td>
                            <td width="40%">
                            	<select id="stateSelect" style="border: 1px solid #dce0e4; padding: 0 0 0 10px;">
                            		<option value="0">已签约</option>
                            		<option value="1">已上线</option>
                            	</select>
                            </td>
                            <td></td>
                        </tr>
                        <tr>
                            <td width="20%" class="name"><div>区域</div></td>
                            <td width="60%">
                            	<div id="area">
	                            	<select class="prov" id="area_prov" style="border: 1px solid #dce0e4; padding: 0 0 0 10px;"></select>
	                            	<select class="city" id="area_city" style="border: 1px solid #dce0e4; padding: 0 0 0 10px;"></select>
	                            </div>
                            </td>
                            <td><div id="checkArea"><p class="text-info"></p></div></td>
                        </tr>
                        <tr class="optTr">
                            <td width="20%" class="name"></td>
                            <td width="40%"><div class="ui-btn" id="saveBtn">下一步</div></td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
	</div>		   	
</div>
</body>

<form id="voForm" method="post">
	<input type="hidden" name="hospitalId" id="hospitalId" value="${commonVo.hospitalId}" />
	<input type="hidden" name="hospitalCode" id="hospitalCode" value="${commonVo.hospitalCode}" />
	<input type="hidden" name="hospitalName" id="hospitalName" value="${commonVo.hospitalName}" />
	<input type="hidden" name="areaCode" id="areaCode" value="${commonVo.areaCode}" />
	<input type="hidden" name="areaName" id="areaName" value="${commonVo.areaName}" />
	<input type="hidden" name="state" id="state" value="${commonVo.state}" />
</form>

<input type="hidden" name="sourceId" id="sourceId" value="${commonVo.hospitalId}" />
<input type="hidden" name="sourceCode" id="sourceCode" value="${commonVo.hospitalCode}" />
<input type="hidden" name="sourceName" id="sourceName" value="${commonVo.hospitalName}" />
<input type="hidden" name="sourceAreaCode" id="sourceAreaCode" value="${commonVo.areaCode}" />
<input type="hidden" name="sourceState" id="sourceState" value="${commonVo.state}" />

</html>