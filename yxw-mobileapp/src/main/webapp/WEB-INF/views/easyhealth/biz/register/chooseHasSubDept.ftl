<html>
<head>
  	<#include "/easyhealth/common/common.ftl">
    <title>${commonParams.hospitalName}</title>
	<style type="text/css">
        html,body{height: 100%;overflow: hidden;}
        .scroller::-webkit-scrollbar{
            display: none;
        }

        .scroller {
            position: absolute;
            z-index: 1;
            -webkit-tap-highlight-color: rgba(0,0,0,0);
            overflow:auto;/* winphone8和android4+ */
            -webkit-overflow-scrolling: touch; /* ios5+ */
            height: 100%;
            width: 100%;
            -webkit-transform: translateZ(0);
            -moz-transform: translateZ(0);
            -ms-transform: translateZ(0);
            -o-transform: translateZ(0);
            transform: translateZ(0);
            -webkit-text-size-adjust: none;
            -moz-text-size-adjust: none;
            -ms-text-size-adjust: none;
            -o-text-size-adjust: none;
            text-size-adjust: none;
        }

	</style>
</head>
<body style="margin: 0;padding: 0;">
<div id="mark"></div>
<div id="body" style="margin: 0;padding: 0;">
	 <div class="search-view">
        <div class="search-select">
            <select name="searchCode" id="searchCode">
               <#if isHasSearChDoctor == 1> 
					<option value="1" selected="selected">医生</option>               
                <#elseif isHasSearChDoctor == 2 && commonParams.regType == 2>
                	<option value="1" selected="selected">医生</option>
            	<#elseif isHasSearChDoctor == 3 && commonParams.regType == 1> 
            		<option value="1" selected="selected">医生</option>
                <#else> 
                </#if>
                <option value="2" >科室</option>
            </select>
        </div>
        <div id="search" class="search-input"><i class="iconfont">&#xe61a;</i>请输入关键字<span class="btn-seaech">搜索</span></div>
    </div>
 
	<div class="ui-ceGuaHao">
	   <div class="ui-ceGuaHao-title">
	       曾挂号医生：
	   </div>
	    <div id="hadRegDoctors" class="ui-ceGuaHao-centent">
		    <#list hadRegDoctors?reverse as hadRegDoctor>
		    	<a href="#" id="${hadRegDoctor.deptCode}:${hadRegDoctor.doctorCode}" class="ui-ceGuaHao-lebel">${hadRegDoctor.doctorName}</a>
	        </#list>
	    </div>
	</div>
    
   <div id="ks-list">
        <div class="ks-list-left">
            <div id="ks-left-list">
				   <div class="scroller">
                <ul>
                    <#list depts as dept>
                        <li id="${dept.deptCode}" <#if dept_index == 0>class="active"</#if>>${dept.deptName}</li>
                    </#list>
                </ul>
				</div>
            </div>
            <div id="left-ks_arrow"><i class="iconfont">&#xe600;</i> </div>
        </div>
        <div class="ks-list-right second">
           <div id="right-list-main" >
		   	   <div class="scroller">
               <ul style="display: block" id="subDeptList">
               <#list subDepts as subDept>
                   <li id="${subDept.deptCode}" name="${subDept.deptName}"><i class="iconfont">&#xe603;</i>${subDept.deptName}</li>
               </#list>
               </ul>
			   </div>
           </div>
            <div id="right-ks_arrow" class="second"><i class="iconfont">&#xe600;</i></div>
        </div>
    </div>
</div>
<form id="paramsForm" method="post">
<input type="hidden" id="openId" name="openId" value="${commonParams.openId}">
<input type="hidden" id="appCode" name="appCode" value="${commonParams.appCode}">
<input type="hidden" id="appId" name="appId" value="${commonParams.appId}">
<input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}" />
<input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}" />
<input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}" />
<input type="hidden" id="branchHospitalId" name="branchHospitalId" value="${commonParams.branchHospitalId}" />
<input type="hidden" id="branchHospitalCode" name="branchHospitalCode" value="${commonParams.branchHospitalCode}" />
<input type="hidden" id="branchHospitalName" name="branchHospitalName" value="${commonParams.branchHospitalName}" />
<input type="hidden" id="deptCode" name="deptCode" value="${commonParams.deptCode}">
<input type="hidden" id="deptName" name="deptName" value="${commonParams.deptName}">
<input type="hidden" id="doctorCode" name="doctorCode" value="${commonParams.doctorCode}">
<input type="hidden" id="regType" name="regType" value="${commonParams.regType}" />
<input type="hidden" id="areaCode" name="areaCode" value="${commonParams.areaCode}" />

<input type="hidden" id="searchType" name="searchType"/>
</form>
<#include "/easyhealth/common/footer.ftl">
<script src="${basePath}yxw.app/js/biz/register/app.chooseHasSubDept.js?v=1.0" type="text/javascript"></script>
</body>
</html>
<script type="text/javascript">
	function doRefresh()
	{
		var freshFrom=$("<form></form>");
		freshFrom.append($('<input type="hidden" name="branchHospitalId" value="${commonParams.branchHospitalId}"/>'));
		freshFrom.append($('<input type="hidden" name="branchHospitalCode" value="${commonParams.branchHospitalCode}"/>'));
		freshFrom.append($('<input type="hidden" name="branchHospitalName" value="${commonParams.branchHospitalName}"/>'));
		freshFrom.append($('<input type="hidden" name="appId" value="${commonParams.appId}"/>'));
		freshFrom.append($('<input type="hidden" name="openId" value="${commonParams.openId}"/>'));
		freshFrom.append($('<input type="hidden" name="appCode" value="${commonParams.appCode}"/>'));
		freshFrom.append($('<input type="hidden" name="areaCode" value="${commonParams.areaCode}"/>'));
		freshFrom.append($('<input type="hidden" name="regType" value="${commonParams.regType}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalId" value="${commonParams.hospitalId}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalCode" value="${commonParams.hospitalCode}"/>'));
		freshFrom.append($('<input type="hidden" name="hospitalName" value="${commonParams.hospitalName}"/>'));
		freshFrom.appendTo("body");
		freshFrom.css('display','none');
		freshFrom.attr("method","post");
		freshFrom.attr("action", base.appPath + "easyhealth/register/refreshDepts");
		freshFrom.submit();
	}
</script>