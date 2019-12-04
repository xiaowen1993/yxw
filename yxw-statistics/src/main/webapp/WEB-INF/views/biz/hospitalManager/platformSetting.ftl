<html>
<head>
<#include "./common/common.ftl">
    <script type="text/javascript" src="${basePath}stats/js/biz/hospitalManager/platformSetting.js"></script>
    <link rel="stylesheet" href="${basePath}css/style.css"/>
    <title>医院管理->医院列表</title>
    <style>
    	.ui-select-wrap span {
    		vertical-align: super;
    		height: 100%;
    	}
    </style>
    <script>
    	var tradeMap = {};
    	tradeMap[-1] = '<option value="-1">请选择支付方式</option>';
    	<#list tradeModes as tradeMode>
		tradeMap[${tradeMode.code}] = '<option value="${tradeMode.id}">${tradeMode.name}</option>';
    	</#list>
    </script>
</head>
<body>

<div id="content">
	<div class="container-fluid">
		<div class="inner">
			<div class="row-fluid">
				<div class="views">
		            <div class="stepBox">
		                <ul class="steps">
		                    <li class="step1">
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
		                    <li class="step3 active">
		                        <a href="#">
		                            <span class="step">平台</span>
		                            <span class="line"></span>
		                        </a>
		                     </li>
		                </ul>
		            </div>
		            <div class="termBox">
		                <div class="ui-select-wrap">
		                   	<span> 平台：</span>
		                    <select class="ui-form-select" id="platformSelector">
		                    	<option value="-1" code="-1">请选择平台类型</option>
		                    	<#list platforms as platform>
		                       	  	<option value="${platform.id}" code="${platform.code}">${platform.name}</option>
		                       	</#list>
		                    </select>
		                </div>
		                <div class="ui-select-wrap">
		                  	<span>  支付方式：</span>
		                    <select class="ui-form-select" id="tradeModeSelector">
		                    	<option value="-1">请选择支付方式</option>
		                    	<#-->
		                    	<#list tradeModes as tradeMode>
		                    		<option value="${tradeMode.id}">${tradeMode.name}</option>
		                       	</#list>
		                       	<#-->
		                    </select>
		                </div>
		                <div class="ui-input-wrap">
		                	<span>appId：</span>
		                	<input class="ui-form-input" type="text" value="" id="inputAppId" placeholder="请输入appId">
		                </div>
		                <button class="ui-btn ui-btn-lg" id="saveSetting">保存</button>
		            </div>
		            <div class="divTable">
		                <table>
		                    <thead>
		                    <tr>
		                        <th width="30%">平台</th>
		                        <th width="30%">支付方式</th>
		                        <th width="30%">APPID</th>
		                        <th>管理</th>
		                    </tr>
		                    </thead>
		                    <tbody>
		                    </tbody>
		                </table>
		               <!--
		               <div class="btn-wrap">
		                   <button class="ui-btn">完成</button>
		               </div>
		               -->
		            </div>
		        </div>
		    </div> 
        </div>
	</div>		   	
</div>

<form id="voForm" method="post">
	<input type="hidden" name="hospitalId" id="hospitalId" value="${commonVo.hospitalId}" />
	<input type="hidden" name="hospitalCode" id="hospitalCode" value="${commonVo.hospitalCode}" />
	<input type="hidden" name="hospitalName" id="hospitalName" value="${commonVo.hospitalName}" />
</form>

<!--content end-->
<!-- 版权声明 -->
<!--
<#include "./common/footer.ftl">
-->
</body>
</html>
