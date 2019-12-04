<html>
<head>
    <#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/json_utils.js"></script>
    <script type="text/javascript" src="${basePath}js/sys/hospital/sys.hospital.js"></script>
    <title>院区信息</title>
</head>
<body>
	<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">院区信息</h3></div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="row-fluid">
                <div class="space10"></div>
                <div class="myStep s2">
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
                        <div class="h-Info">
                            <div class="h-name">
                                <ul class="name-list" id="name-Hlist">
                                	<#if hospital.branchHospitals?size == 0>
	                                    <li index="0" onclick="$hospital.setBranchHospitalValues(0);"><i class="caret"></i><span class="text">${hospital.name}</span></li>
	                               	<#else>
		                                <#list hospital.branchHospitals as branchHospital>
		                                    <li index="${branchHospital_index}" onclick="$hospital.setBranchHospitalValues(${branchHospital_index});">
		                                    	<i class="caret"></i>
		                                    	<span class="text">${branchHospital.name}</span>
		                                    	<input type="hidden" name="branchHospital_id" value="${branchHospital.id}">
		                                    </li>
										</#list>
									</#if>
                                </ul>
                                <div class="addHospital" onclick="$hospital.addBranchHospitalItem();" style="cursor: hand;"><i class="icon-plus"></i></div>
                            </div>
                            <div class="h-content">
                            <form class="form-horizontal">
                                <div class="space30"></div>
                                
                                <input type="hidden" name="id" />
                                <input type="hidden" name="hospitalId" value="${hospital.id}"/>
                                <input type="hidden" name="code" class="${hospital.code}"/>
                                <div class="control-group"><label class="control-label" >分院全称</label><div class="controls"><input type="text" name="name" maxlength="14" class="span11 js_branchHospitalName"/></div></div>
                                <div class="control-group"><label class="control-label" >分院代码</label><div class="controls"><input type="text" name="code" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >接口ID</label><div class="controls"><input type="text" name="interfaceId" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >分院地址</label><div class="controls"><input type="text" name="address" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >分院电话</label><div class="controls"><input type="text" name="tel" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >经度</label><div class="controls"><input type="text" name="longitude" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >纬度</label><div class="controls"><input type="text" name="latitude" class="span11"/></div></div>
                                
                                <!--<div class="control-group"><label class="control-label" >上午班开始时间</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >上午班结束时间</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >下午班开始时间</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >下午班结束时间</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >晚班开始时间</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >晚班结束时间</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >预约挂号操作员工号</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >当班挂号操作员工号</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >诊间支付操作员工号</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >住院押金补缴操作员工号</label><div class="controls"><input type="text" class="span11"/></div></div>
                                <div class="control-group"><label class="control-label" >出院结算操作员工号</label><div class="controls"><input type="text" class="span11"/></div></div>-->
                                
                                <div class="space5"></div>
                            </form>
                        </div>
                        </div>
                        <!--内容 end-->
                    </div>
                </div>
            </div>
            <div class="footer-tool">
                <div class="row-fluid">
                    <button class="btn btn-remove" onclick="$hospital.back('${basePath}sys/hospital/toEdit?id=${hospital.id}');">上一步</button>
                    <button class="btn btn-save" onclick="$hospital.deleteBranchHospitals();">删除</button>
                    <button class="btn btn-save" onclick="$hospital.saveBranchHospitals();">下一步</button>
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
<script type="text/javascript">
    $(document).ready(function(){
        $('#name-Hlist li').attr('style', 'cursor: hand;');
    		
        $('.js_branchHospitalName').bind('input propertychange', function() {
            $('.js_branchHospitalItem').html($(this).val());
        });
        
        $hospital.id = '${hospital.id}';
        //编辑的时候初始化分院信息到 js 对象
        $hospital.branchHospitals = [];
        <#list hospital.branchHospitals as branchHospital>
	        var bh = {};
	        bh.id = '${branchHospital.id}';
	        bh.name = '${branchHospital.name}';
	        bh.code = '${branchHospital.code}';
	        bh.interfaceId = '${branchHospital.interfaceId}';
	        bh.address = '${branchHospital.address}';
	        bh.tel = '${branchHospital.tel}';
	        bh.longitude = '${branchHospital.longitude}';
	        bh.latitude = '${branchHospital.latitude}';
	        $hospital.branchHospitals.push(bh);
        </#list>
        
        console.dir($hospital.branchHospitals);
        
        $hospital.setBranchHospitalValues(0);
    });
</script>