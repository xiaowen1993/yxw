<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/cache/sys.cache.js"></script>
    <title>号源信息缓存</title>
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
            <h3 class="title">缓存管理</h3>
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
        <div class="space20"></div>
        <div class="row-fluid">
        	<div class="pull-left">
                <ul class="tabs">
                    <#list platforms as platform>
                    	<#if platform.state == 1>
                    		<li><a href="#tab${platform_index + 1}" class="<#if platform_index == 0>select</#if>" style="width: 150px;">${platform.platformName}</a></li>
                    	</#if>
                    </#list>
                </ul>
            </div>
        </div>
        <div class="space10"></div>
        <#list platforms as platform>
        	<#if platform.state == 1>
            <div class="widget-box bangKa tab_content" id="tab${platform_index + 1}">
                <div class="space10"></div>
                <div class="row-fluid">
					<div class="widget-box">
						<div class="space10"></div>
						<div class="widget-content form-check">
							<div class="row-fluid">
								<#if platform.payModes?size &gt; 0>
									<table class="table table-bordered table-textCenter table-striped table-hover" >
										<tbody>
											<#list platform.payModes as payMode>
												<#if hospAndAppMap['${platform.platformCode}:${payMode.code}']?exists>
												<tr>
													<td width="10%" style="vertical-align: middle;">[${payMode.name}]缓存</td>
													<td width="88%">
														<textarea style="width:100%;height:350px;" class="textarea-pay" readonly="readonly">${hospAndAppMap['${platform.platformCode}:${payMode.code}']}</textarea>
													</td>
												</tr>
												</#if>
											</#list>
										</tbody>
									</table>
								<#else>
									平台[${platform.platformName}]没有配置支付信息
								</#if>
							</div>
						</div>
					</div>
				</div>
            </div>
            </#if>
		</#list>
    </div>
</div>
<!--content end-->
</body>
</html>

<script type="text/javascript">
	$('.tabs li a').click(function(){
    	$(this).addClass('select');
    	$(this).parent().siblings().children().removeClass('select');
    });
    
	$(function() {
    	$( ".tab_content" ).hide();
    	$( ".tab_content:first" ).show();
    	
    	$( "ul.tabs li" ).click( function () {
            $( "ul.tabs li" ).removeClass( "active" );  //Remove any "active" class
            $( this ).addClass( "active" );  //Add "active" class to selected tab
            $( ".tab_content" ).hide();  //Hide all tab content
            var  activeTab = $( this ).find( "a" ).attr( "href" );  //Find the rel attribute value to identify the active tab + content
            $(activeTab).fadeIn();  //Fade in the active content
            return   false ;
        });
        
    });
</script>
