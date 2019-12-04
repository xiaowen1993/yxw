<html>
<head>
<#include "/sys/common/common.ftl">
    <script type="text/javascript" src="${basePath}js/sys/rule/sys.rule.js"></script>
    <title>配置规则->医院列表</title>
</head>
<body>
<#include "./sys/common/hospital_setting.ftl">
    <div id="content-header">
        <div class="widget-title"><h3 class="title">规则配置<span style="font-size:12px;"> (未配置分院信息的医院不显示此列表中)</span></h3></div>
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
                		<form method="post" action="${basePath}sys/ruleIndex/hospitalList" accept-charset="utf-8">
                			<input type="hidden" name="pageNum" value="${hospitals.pageNum}" />
                            <input type="hidden" name="pageSize" value="${hospitals.pageSize}" />
                            <input type="hidden" name="pages" value="${hospitals.pages}" />
                            <input type="hidden" name="total" value="${hospitals.total}" />
                            <input type="text"  value="${search}" name="search" placeholder="请输入医院名称"/>
                		</from>
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
                                <th>医院全称</th>
                                <th>上次修改时间</th>
                                <th >上次发布时间</th>
                                <th >最后操作者</th>
                                <th width="100">状态</th>
                                <th width="100">管理</th>
                            </tr>
                            </thead>
                            <tbody>
                                <#list hospitals.list as hospital>
	                                <tr>
	                                    <td>${hospital.name}</td>
	                                    <td>
	                                       <#if hospital.ruleLastEditTime??>
                                             ${hospital.ruleLastEditTime?string('yyyy-MM-dd HH:mm')}
                                           </#if>
	                                    </td>
	                                    <td id="${hospital.id}_publishTime">
	                                       <#if hospital.rulePublishTime??>
                                             ${hospital.rulePublishTime?string('yyyy-MM-dd HH:mm')}
                                           </#if>
	                                       
	                                    </td>
	                                    <td id="${hospital.id}_lastHandler">${hospital.lastHandler}&nbsp;</td>
	                                    <td id="${hospital.id}_publishstatus">
	                                       <#if hospital.isPublishRule == 1>已发布<#else>未发布</#if>
	                                    </td>
	                                    <td>
	                                        <a href="javascript:;" onclick="ruleJS.ruleEdit('${hospital.id}' , 'ruleEdit')">管理</a>|
	                                        <a class="red" href="javascript:;" onclick="ruleJS.publishRule('${hospital.id}')">发布</a>|
	                                        <a href="javascript:;" onclick="saveDefaultRule('${hospital.id}')">设置默认规则</a>
	                                    </td>
	                                </tr>
							   </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

         <div class="pagination pagination-centered">
            <#if hospitals?exists>
            <ul>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.prePage});">上一页</a></li>
                <#if hospitals.pages != 0>
                    <#list 1..hospitals.pages as p>
                        <#if hospitals.pageNum == p>
                            <li class="disabled"><span>${p}</span></li>
                        <#else>
                            <li><a href="javascript:;" onclick="$hospital.changePage(${p});">${p}</a></li>
                        </#if>
                    </#list>
                </#if>
                <li><a href="javascript:;" onclick="$hospital.changePage(${hospitals.nextPage});">下一页</a></li>
            </ul>
            <div class="pageGoto">
                <span>转到第</span> <input type="text" id="skipPage" class="goto_input"/> <span>页</span>
                <a href="javascript:;" class="goto" onclick="$hospital.changePage($('#skipPage').val());">跳转</a>
            </div>
            </#if>
        </div>
    </div>
</div>
<!--content end-->
<!-- 版权声明 -->
<#include "./sys/common/footer.ftl">

</body>
</html>
<script type="text/javascript">
var $hospital = {};
/**
 * 搜索医院
 */
$hospital.search = function() {
  document.forms[0].submit();
}

$hospital.back = function(url) {
    location.href = url;
}

function saveDefaultRule(hospitalId) {
	var url = $("#basePath").val() + "sys/ruleIndex/saveDefaultRule?hospitalId=" + hospitalId;
	$.ajax( {  
    	type : 'POST',  
    	url : url,  
    	dataType : 'json',  
    	timeout:120000,
    	success : function(data) {  
    		alert(data.message);
    	},  
    	error : function(data) {  
    		alert("设置异常!");  
    	}  
    });  
}

/**
 * 医院管理分页
 */
$hospital.changePage = function(pageNum, pageSize) {
  if (pageNum) {
    var pages = $('form input[name="pages"]').val();
    var pageNumInput = $('form input[name="pageNum"]');

    // 如果输入的页数是非数字，则还是跳到当前页
    if (isNaN(pageNum)) {
      pageNum = pageNumInput.val();
    }

    // 如果页数大于总页数，则跳至最后一页，如页数小于最小页数，则跳至第一页
    pageNum = pageNum > pages ? pages : pageNum;
    pageNum = pageNum < 1 ? 1 : pageNum;

    pageNumInput.val(pageNum);

    // 如果修改了每页显示的数量
    if (pageSize) {
      $('form input[name="pageSize"]').val(pageSize);
    }
    $hospital.search();
  }
}
</script>
