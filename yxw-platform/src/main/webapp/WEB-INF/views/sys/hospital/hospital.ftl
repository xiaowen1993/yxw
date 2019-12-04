<html>
<head>
    <#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
	<script src="${basePath}js/ajaxfileupload.js"></script>
    <title>医院信息</title>
    <style type="text/css">
        h4.h4{margin: 10px 0}
    </style>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">医院信息</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row-fluid">
                <div class="space10"></div>
                <div class="myStep s1">
                    <div class="myStepClick">
                        <a class="aStepClick a-s1" href="${basePath}sys/hospital/toEdit?id=${hospital.id}"></a>
                        <a class="aStepClick a-s2" href="${basePath}sys/branchHospital/toView?hospitalId=${hospital.id}"></a>
                        <a class="aStepClick a-s3" href="${basePath}sys/platformSettings/toView?hospitalId=${hospital.id}"></a>
                        <#-- <a class="aStepClick a-s4" href="${basePath}sys/paySettings/toView?hospitalId=${hospital.id}"></a>
                        <a class="aStepClick a-s5" href="${basePath}sys/optional/toView?hospitalId=${hospital.id}"></a>
                        <a class="aStepClick a-s6" href="${basePath}sys/customerMenu/toView?hospitalId=${hospital.id}"></a> -->
                    </div>
                </div>
            </div>
            <div class="widget-box">
                <div class="h22"></div>
                <div class="widget-content">
                    <div class="row-fluid">
                        <!--内容-->
                        <form class="form-horizontal">
                            <div class="space30"></div>
                            <input name="id" type="hidden" value="${hospital.id}" />
                            <input name="code" value="${hospital.code}" type="hidden"/>
                            <div class="control-group"><label class="control-label" >医院名称</label><div class="controls"><input name="name" value="${hospital.name}" type="text" class="span5"/></div></div>
                            <div class="control-group"><label class="control-label" >医院编码</label><div class="controls"><input name="code" value="${hospital.code}" type="text" class="span5"/></div></div>
                            <div class="control-group">
                            	<label class="control-label" >区域</label>
                            	<div class="controls">
                            		<select name="areaLevel1" style="width: auto;" onchange="$hospital.loadAreasLevel2(this);"><#-- name="areaCode" -->
                            			<option value="" selected>请选择</option>
	                            		<#-- <#if areaCodeMap?exists>
	                						<#list areaCodeMap?keys as key>
	                							<#if key == hospital.areaCode>
	                            					<option value="${key}" selected>${areaCodeMap[key]}</option>
	                            				<#else>
	                            					<option value="${key}">${areaCodeMap[key]}</option>
	                            				</#if>
	                            			</#list>
	                            		</#if> -->

										<#if oneLevelAreas?exists>
	                						<#list oneLevelAreas as area>
	                							<#if hospital.areaCode?contains(area.id)>
	                            					<option value="${area.id}" selected>${area.shortName}</option>
	                            				<#else>
	                            					<option value="${area.id}">${area.shortName}</option>
	                            				</#if>
	                            			</#list>
	                            		</#if>
                                    </select>
                                    
                                    <select id="areaLevel2" name="areaLevel2" style="width: auto;" onchange="$hospital.loadAreasLevel3(this);">
                            			<option value="" selected>请选择</option>
                            			<#if oneLevelAreas?exists>
	                						<#list twoLevelAreas as area>
	                							<#if hospital.areaCode?contains(area.id)>
	                            					<option value="${area.id}" selected>${area.shortName}</option>
	                            				<#else>
	                            					<option value="${area.id}">${area.shortName}</option>
	                            				</#if>
	                            			</#list>
	                            		</#if>
                                    </select>
                                    <select id="areaLevel3" name="areaCode" style="width: auto;">
                            			<option value="" selected>请选择</option>
                            			<#if oneLevelAreas?exists>
	                						<#list threeLevelAreas as area>
	                							<#if hospital.areaCode?contains(area.id)>
	                            					<option value="${area.idPath}" data-namePath="${area.shortNamePath}" selected>${area.shortName}</option>
	                            				<#else>
	                            					<option value="${area.idPath}" data-namePath="${area.shortNamePath}">${area.shortName}</option>
	                            				</#if>
	                            			</#list>
	                            		</#if>
                                    </select>
                            			
                            	</div>
                            </div>
                            <div class="control-group"><label class="control-label" >联系人</label><div class="controls"><input name="contactName" value="${hospital.contactName}" type="text" class="span5"/></div></div>
                            <div class="control-group"><label class="control-label" >联系人电话</label><div class="controls"><input name="contactTel" value="${hospital.contactTel}" type="text" class="span5"/></div></div>
                            <div class="control-group"><label class="control-label" >就医指南URL</label><div class="controls"><input name="guideURL" value="${hospital.guideURL}" type="text" class="span5"/></div></div>
                            <div class="control-group"><label class="control-label" >云医院URL</label><div class="controls"><input name="cloudURL" value="${hospital.cloudURL}" type="text" class="span5"/></div></div>
                            <div class="control-group"><label class="control-label" >交通指引URL</label><div class="controls"><input name="trafficURL" value="${hospital.trafficURL}" type="text" class="span5"/></div></div>
                            <div class="control-group"><label class="control-label" >上传LOGO</label><div class="controls"><img  id="showImg" width="80" height="80" style="display:block;background-color:white;"  src="${hospital.logo}"/></div></div>
                            <div class="control-group">
                            	<label class="control-label" ></label>
                                <div class="controls">
		                            <div class="btn-save btn-upload"><div id="fileBlock">
		                             	选择图片
		                            	<input class="fileupload" type="file" id="uploadFile" name="uploadFile" onchange="$hospital.uploadImg();"/>
		                            </div></div>
		                            <input type="hidden" id="logo" name="logo" value="${hospital.logo}">
		                        </div>
		                    </div>
                            <div class="space5"></div>
                        </form>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
            <div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-save" onclick="$hospital.save();">下一步</button>
                </div>
            </div>

        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>