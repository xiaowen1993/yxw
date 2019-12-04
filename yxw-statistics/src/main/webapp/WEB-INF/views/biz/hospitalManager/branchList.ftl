<html>
<head>
<#include "./common/common.ftl">
    <script type="text/javascript" src="${basePath}stats/js/biz/hospitalManager/branchList.js"></script>
    <link rel="stylesheet" href="${basePath}css/style.css"/>
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
    	
    	td div {
    		height: 33px;
    	}
    	
    	a.branchActive {
    		background-color: #005580;
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
		                    <li class="step1">
		                        <a href="#">
		                            <span class="step">医院</span>
		                            <span class="line"></span>
		                        </a>
		                    </li>
		                    <li class="step2 active">
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
		            <div class="divTable clearfix">
		                <div class="leftBox">
		                    <ul class="textList" id="branchList">
		                        
		                    </ul>
		                    <div class="addList" id="newBranch"><a href="javascript: void(0);"><em>+</em>新增</a></div>
		                </div>
		                <div class="rightBox" style="min-height: 510px;">
		                    <table class="optTable">
		                        <tbody>
		                        <tr>
		                            <td width="20%" class="name"><div>医院名称</div></td>
		                            <td width="80%"><input class="ui-form-input" type="text" value="${commonVo.hospitalName}" readonly=true></td>
		                        </tr>
		                        <tr>
		                            <td width="20%" class="name"><div>分院名称</div></td>
		                            <td width="80%"><input class="ui-form-input" type="text" id="inputName" placeholder="请输入分院名称"></td>
		                        </tr>
		                        <tr>
		                            <td width="20%" class="name"><div>分院代码</div></td>
		                            <td width="80%"><input class="ui-form-input" type="text" id="inputCode" placeholder="请输入分院代码"></td>
		                        </tr>
		                        <tr class="optTr">
		                            <td width="20%" class="name"></td>
		                            <td width="80%">
		                            	<div class="ui-btn ui-btn-remove" id="delBranch">删除</div>
		                                <div class="spaceW"></div>
		                                <div class="ui-btn ui-btn-primary" id="saveBranch">保存</div>
		                                <div class="spaceW"></div>
		                                <div class="ui-btn" id="nextStep">下一步</div>
		                            </td>
		                        </tr>
		                        </tbody>
		                    </table>
		                </div>
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

<input type="hidden" name="branchId" id="branchId" value="" />
<input type="hidden" name="branchName" id="branchName" value="" />
<input type="hidden" name="branchCode" id="branchCode" value="" />

<!--content end-->
<!-- 版权声明 -->
<!--
<#include "./common/footer.ftl">
-->
</body>
</html>
