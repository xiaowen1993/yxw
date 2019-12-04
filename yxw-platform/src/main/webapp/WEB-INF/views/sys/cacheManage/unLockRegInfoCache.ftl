<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/cache/sys.cache.js"></script>
    <title>医院可挂号医生缓存</title>
    <style>
    .table th, .table td {
        padding: 5px 2px;
    }
    </style>
</head>
<body>
<!--sidebar-menu end-->
<!--content str-->
<#include "./sys/common/hospital_setting.ftl">
     <form id="hospitalParamForm" method="post" accept-charset="utf-8">
       <input type="hidden" id="hospitalId" name="hospitalId" value="${commonParams.hospitalId}" />
       <input type="hidden" id="hospitalName" name="hospitalName" value="${commonParams.hospitalName}"/>
       <input type="hidden" id="hospitalCode" name="hospitalCode" value="${commonParams.hospitalCode}"/>
       <input type="hidden" id="cacheType" name="cacheType" value="${commonParams.cacheType}"/>
       <input type="hidden" id="branchCode" name="branchCode" value="${commonParams.branchCode}" />
    </form>
    <div id="content-header">
        <div class="widget-title">
            <h3 class="title">医院可挂号医生缓存</h3>
            <div class="cur_set">
                                           当前医院：${commonParams.hospitalName}
                <span style="margin-left:20px;">当前分院：</span>
                <select name="branchHospitalSelect" id="branchHospitalSelect" class="mini" style="margin-left:0px;"
                    onchange="cacheJS.cacheEdit('${commonParams.hospitalId}')">
                    <#if branchHospitals?exists>
                        <#list branchHospitals as branch>
                            <option value="${branch.code}"  <#if branch.code == commonParams.branchCode>selected="selected"</#if>>${branch.name}</option>
                        </#list>
                    </#if>
                </select>
                <span style="margin-left:20px;"> 缓存类型：</span>
                <select name="cacheTypeSelect" id="cacheTypeSelect" class="mini" style="margin-left:0px;" 
                    onchange="cacheJS.cacheEdit('${commonParams.hospitalId}')">
                    <#if cacheTypeMap?exists>
                        <#list cacheTypeMap?keys as cacheTypeKey > 
                            <option value="${cacheTypeKey}"  <#if commonParams.cacheType == cacheTypeKey>selected="selected"</#if> >
                                ${cacheTypeMap[cacheTypeKey]}
                            </option>
                        </#list>
                    </#if>
                </select>
            </div>
        </div>
    </div>
   <div class="container-fluid">
        <div class="space10"></div>
        <div class="row-fluid">
            <div class="widget-box">
                <div class="space10"></div>
                <div class="widget-content form-check">
                    <div class="row-fluid">
                        <table class="table table-bordered table-textCenter table-striped table-hover" >
                            <tbody>
                                <tr>
                                    <td width="10%">带解锁挂号缓存队列</td>
                                    <td width="88%">
                                        <textarea style="width:100%;height:650px;" class="textarea-pay" readonly="readonly">${unLockRegInfos}</textarea>
                                    </td>
                                </tr>
                                <tr><td colspan="2"><button class="btn btn-save" type="button" onclick="cacheJS.reLoadDataToCache()">获取新数据</button></td></tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
</body>
</html>
